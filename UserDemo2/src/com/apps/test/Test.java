package com.apps.test;

import com.apps.pojo.User;
import com.apps.service.IUserService;
import com.apps.service.impl.UserServiceImpl;

public class Test {

	public static void main(String[] args) {

		IUserService service = new UserServiceImpl();
		
		User user = new User();
		user.setUid(14);
		user.setUname("尹志平");
		user.setAge(35);
		user.setAddress("退出全真派");
		
		int row = service.updateUser(user);
		
//		int row = service.addUser("周芷若", 22, "峨眉派");
		
		System.out.println(row);

	}

}
