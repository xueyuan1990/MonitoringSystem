package com.mz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.reflectionassert.ReflectionAssert;

import com.mz.entity.User;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserServiceTest {

    @Mock
    @InjectIntoByType(target = "userService")
    private SqlSession  sqlSession;
    @InjectMocks
    private UserService userService; //被测对象


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testIsLogin() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", "admin");
        params.put("password", "123");
        User u = new User("admin", "123");
        //mock
        Mockito.when(sqlSession.selectOne("user.isLogin", params)).thenReturn(1);

        //test
        boolean flag = userService.isLogin(u);

        // verify
        ReflectionAssert.assertReflectionEquals(true, flag);
        Mockito.verify(sqlSession).selectOne("user.isLogin", params);
    }


    @Test
    public void testIsAdmin() {
        String username = "admin";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        //mock
        Mockito.when(sqlSession.selectOne("user.getUser", params)).thenReturn(
                new User("admin", "123", "admin"));

        //test
        boolean flag = userService.isAdmin(username);

        // verify
        ReflectionAssert.assertReflectionEquals(true, flag);
        Mockito.verify(sqlSession).selectOne("user.getUser", params);
    }


    @Test
    public void testGetUser() {
        String username = "admin";
        User uExp = new User("admin", "123", "admin");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        //mock
        Mockito.when(sqlSession.selectOne("user.getUser", params)).thenReturn(uExp);

        //test
        User u = userService.getUser(username);

        // verify
        ReflectionAssert.assertReflectionEquals(uExp, u);
        Mockito.verify(sqlSession).selectOne("user.getUser", params);
    }


    @Test
    public void testGetUsers() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", 0);
        params.put("limit", 10);
        User u1 = new User("u1", "123", "admin");
        User u2 = new User("u2", "123", "admin");
        List listExp = new ArrayList();
        listExp.add(u1);
        listExp.add(u2);

        //mock
        Mockito.when(sqlSession.selectList("user.getUsers", params)).thenReturn(listExp);

        //test
        List list = userService.getUsers(0, 10);

        // verify
        ReflectionAssert.assertReflectionEquals(listExp, list);
        Mockito.verify(sqlSession).selectList("user.getUsers", params);
    }


    @Test
    public void testGetUsersNum() {
        //mock
        Mockito.when(sqlSession.selectOne("user.getUsersNum")).thenReturn(5);

        //test
        int num = userService.getUsersNum();

        // verify
        ReflectionAssert.assertReflectionEquals(5, num);
        Mockito.verify(sqlSession).selectOne("user.getUsersNum");
    }


    @Test
    public void testAddUser() throws Exception {
        User u = new User("u1", "123", "2014-01-01 00:00:00", "admin");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", "u1");
        params.put("password", "123");
        params.put("userRights", "admin");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(now).toString();
        params.put("createTime", createTime);
        //mock
        Mockito.when(sqlSession.insert("user.addUser", params)).thenReturn(1);

        //test
        boolean flag = userService.addUser(u);

        // verify
        ReflectionAssert.assertReflectionEquals(true, flag);
        Mockito.verify(sqlSession).insert("user.addUser", params);
    }


    @Test
    public void testDeleteUser() throws Exception {
        String username = "admin";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        //mock
        Mockito.when(sqlSession.delete("user.deleteUser", params)).thenReturn(1);

        //test
        boolean flag = userService.deleteUser(username);

        // verify
        ReflectionAssert.assertReflectionEquals(true, flag);
        Mockito.verify(sqlSession).delete("user.deleteUser", params);
    }
}
