package com.apps.dao;

import java.util.List;

import com.apps.pojo.User;

public interface IUserDao {

	List<User> selectAll();
	
	List<User> selectUsersByUnameAndAddr(String name, String addr);

	User selectById(int id);

	int insertUser(User user);

	int deleteUser(int id);

	int insertUser(String uname, int age, String address);

	int updateUser(User user);

}
