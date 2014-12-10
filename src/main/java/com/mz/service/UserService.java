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


    /**
     * 验证登录是否成功
     * 
     * @param u 用户信息
     * @return true 登录成功; false 登录失败
     * @author xueyuan
     * @since 1.0
     **/
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


    /**
     * 验证用户是否是管理员
     * 
     * @param username 用户名
     * @return true 是管理员; false 非管理员
     * @author xueyuan
     * @since 1.0
     **/
    public boolean isAdmin(String username) {
        if (username == null || username.trim().length() == 0) {
            return false;
        }
        final String USER_RIGHTS_OF_ADMIN = "admin";// 管理员的权限
        String userRights = getUser(username).getUserRights();// 已登录用户的权限
        return USER_RIGHTS_OF_ADMIN.equals(userRights);
    }


    /**
     * 按用户名查询用户
     * 
     * @param username 用户名
     * @return 存在该用户则返回用户信息，不存在该用户返回null
     * @author xueyuan
     * @since 1.0
     **/
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


    /**
     * 分页查询所有用户
     * 
     * @param start 起始点
     * @param limit 每页信息条数
     * @return 分页返回用户集合
     * @author xueyuan
     * @since 1.0
     **/
    public List<User> getUsers(int start, int limit) {
        List<User> list = new ArrayList<User>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", start);
        params.put("limit", limit);
        list = sqlSession.selectList("user.getUsers", params);
        return list;
    }


    /**
     * 查询用户总数
     * 
     * @return 用户总数
     * @author xueyuan
     * @since 1.0
     */
    public int getUsersNum() {
        int count = (Integer) sqlSession.selectOne("user.getUsersNum");
        return count;
    }


    /**
     * * 添加用户
     * 
     * @param u 用户信息
     * @return true 添加成功; false 添加失败
     * @throws Exception 插入用户失败时抛出异常，比如已存在同名用户时
     * @author xueyuan
     * @since 1.0
     */
    public boolean addUser(User u) throws Exception {
        if (u == null) {
            return false;
        }
        String username = u.getUsername().trim();
        String password = u.getPassword().trim();
        String userRights = u.getUserRights();
        if (username.length() == 0 || password.length() == 0) {
            return false;
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(now).toString();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        params.put("createTime", createTime);
        params.put("userRights", userRights);
        sqlSession.insert("user.addUser", params);
        return true;
    }


    /**
     * 删除用户
     * 
     * @param username 用户名
     * @return true 删除成功; false 删除失败
     * @author xueyuan
     * @since 1.0
     */
    public boolean deleteUser(String username) {
        boolean flag = false;
        if (username == null) {
            return flag;
        }
        username = username.trim();
        if (username.length() != 0) {
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
