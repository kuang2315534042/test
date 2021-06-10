package com.apps.service.impl;

import java.util.List;

import com.apps.dao.IUserDao;
import com.apps.pojo.User;
import com.apps.service.IUserService;
import com.apps.utils.ProxyDaoFactory;

public class UserServiceImpl implements IUserService {
	// 通过动态代理技术创建接口的实现对象
	// cglib   不需要接口，采用继承实现代理
	// javasist  JDK动态代理
	IUserDao dao = (IUserDao) new ProxyDaoFactory("src/config.xml").getProxy(IUserDao.class);
	
	@Override
	public List<User> findAll() {
		return dao.selectAll();
	}

	@Override
	public User findById(int id) {
		return dao.selectById(id);
	}
	
	@Override
	public int removeUser(int id) {
		return dao.deleteUser(id);
	}

	@Override
	public List<User> selectUsersByUnameAndAddr(String name, String addr) {
		return dao.selectUsersByUnameAndAddr(name, addr);
	}

	@Override
	public int addUser(User user) {
		return dao.insertUser(user);
	}

	@Override
	public int addUser(String uname, int age, String address) {
		return dao.insertUser(uname, age, address);
	}

	@Override
	public int updateUser(User user) {
		
		return dao.updateUser(user);
	}

	

}
