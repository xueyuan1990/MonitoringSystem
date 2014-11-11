package com.mz.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mz.entity.User;
import com.mz.test.common.SpringBaseTest;

public class UserServiceTest extends SpringBaseTest {
	@Autowired
	UserService userService;

	@Test
	public void userServiceTest() throws Exception {
		User u = new User();
		u.setUsername("user1");
		u.setPassword("123");
		System.out.println(userService.loginCheck(u));
	}
}
