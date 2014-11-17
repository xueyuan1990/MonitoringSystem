package com.mz.service;

import org.jtester.annotations.DbFit;
import org.jtester.testng.JTester;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mz.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-datasources.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class UserServiceTest extends JTester {
    //注入 
    @Autowired
    UserService userService;


    @Test
    @DbFit(when = { "loginCheck.when.wiki" })
    public void testLoginCheck() {
        User user = new User();
        user.setUsername("u1");
        user.setPassword("p1");
        Assert.assertEquals(true, userService.loginCheck(user));
    }

}
