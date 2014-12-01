package com.mz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.entity.User;

/**
 * Service:user相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Service
public class UserService {
    @Autowired
    SqlSession sqlSession;


    public boolean isLogin(User u) {//用户名、密码是否正确，如果正确则登录成功，返回true
        boolean flag = false;
        if (u == null) {
            return flag;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", u.getUsername());
        params.put("password", u.getPassword());
        int cnt = (Integer) sqlSession.selectOne("user.isLogin", params);
        if (cnt > 0) {
            flag = true;
        }
        return flag;
    }


    public User getUser(String username) {//用户是否已经存在，如果存在返回true
        if (username == null) {
            return null;
        }
        username = username.trim();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        User user = new User();
        user = (User) sqlSession.selectOne("user.getUser", params);
        return user;
    }


    public List<User> getUsers(int start, int limit) {
        List<User> list = new ArrayList<User>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", start);
        params.put("limit", limit);
        list = sqlSession.selectList("user.getUsers", params);
        return list;
    }


    public int getUsersNum() {
        int count = (Integer) sqlSession.selectOne("user.getUsersNum");
        return count;
    }


    public boolean addUser(User u) {
        boolean flag = false;
        if (u == null) {
            return flag;
        }
        String username = u.getUsername().trim();
        String password = u.getPassword().trim();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(now).toString();
        if (username.length() == 0 || password.length() == 0 || getUser(username) != null) {//如果用户名已经存在，则添加用户失败，返回false
            return false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("createTime", createTime);
        int i = sqlSession.insert("user.addUser", params);
        if (i > 0) {
            flag = true;
        }
        return flag;
    }


    public boolean deleteUser(String username) {
        boolean flag = false;
        if (username == null) {
            return flag;
        }
        username = username.trim();
        if (username.length() != 0 && getUser(username) != null) {//用户名已经存在
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("username", username);
            int i = sqlSession.delete("user.deleteUser", params);
            if (i > 0) {
                flag = true;
            }
        }

        return flag;
    }
}
