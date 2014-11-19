package com.mz.service;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import com.mz.entity.User;

@Test
@SpringApplicationContext({ "spring-test-datasources.xml" })
public class UserServiceTest extends JTester {
    @SpringBeanByName("userService")
    UserService userService;


    @Test
    @DbFit(when = "UserService.loginCheck.when.wiki")
    public void testLoginCheck() {
        User user1 = new User();//用户名、密码都匹配
        user1.setUsername("userLogin");
        user1.setPassword("p1");
        User user2 = new User();//用户名匹配，密码不匹配
        user2.setUsername("userLogin");
        user2.setPassword("p2");
        User user3 = new User();//用户名不匹配、密码匹配
        user3.setUsername("u3");
        user3.setPassword("p1");
        User user4 = new User();//用户名、密码都不匹配
        user4.setUsername("u4");
        user4.setPassword("p4");
        want.bool(userService.loginCheck(user1)).is(true);
        want.bool(userService.loginCheck(user2)).is(false);
        want.bool(userService.loginCheck(user3)).is(false);
        want.bool(userService.loginCheck(user4)).is(false);
    }


    @Test
    @DbFit(when = "UserService.selectByUsername.when.wiki")
    public void testSelectByUsername() {
        String username = "userSelect";//该用户存在
        String username2 = "username2";//该用户不存在

        want.object(userService.selectByUsername(username)).propertyEq("username", "userSelect");
        want.object(userService.selectByUsername(username)).propertyEq("password", "p1");
        want.object(userService.selectByUsername(username2)).isNull();

    }


    @Test
    @DbFit(when = { "UserService.addUser.when.wiki" }, then = { "UserService.addUser.then.wiki" })
    public void testAddUser() {
        User user = new User();//添加合法用户
        user.setUsername("userAdd");
        user.setPassword("p1");
        User user2 = new User();//添加已存在用户
        user2.setUsername("u2");
        user2.setPassword("p2");
        User user3 = new User();//用户名不合法
        user3.setUsername("");
        user3.setPassword("p2");
        User user4 = new User();//密码不合法
        user4.setUsername("u4");
        user4.setPassword("");

        want.bool(userService.addUser(user)).is(true);
        want.bool(userService.addUser(user2)).is(false);
        want.bool(userService.addUser(user3)).is(false);
        want.bool(userService.addUser(user4)).is(false);

    }


    @Test
    @DbFit(when = { "UserService.deleteUser.when.wiki" }, then = { "UserService.deleteUser.then.wiki" })
    public void testDeleteUser() {
        String username = "userDelete";//存在该用户
        String username2 = "username2";//用户不存在

        want.bool(userService.deleteUser(username)).is(true);
        want.bool(userService.deleteUser(username2)).is(false);

    }


    @Test
    @DbFit(when = { "UserService.countUser.when.wiki" })
    public void testCountUser() {
        int cnt = userService.countUser();
        want.number(cnt).eq(4);
    }

}
