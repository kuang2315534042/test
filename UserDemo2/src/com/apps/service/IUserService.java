package com.apps.service;

import java.util.List;

import com.apps.pojo.User;

public interface IUserService {
	List<User> findAll();
	User findById(int id);
	List<User> selectUsersByUnameAndAddr(String name, String addr);
	int removeUser(int id);
	int addUser(String uname, int age, String address);
	int addUser(User user);
	int updateUser(User user);
}
