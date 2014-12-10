package com.mz.entity;

/**
 * 定义User类
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class User {
    private String username;  //用户名
    private String password;  //密码
    private String createTime; //添加用户时间
    private String userRights; //用户权限


    public User() {

    }


    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }


    public User(final String username, final String password, final String userRights) {
        this.username = username;
        this.password = password;
        this.userRights = userRights;
    }


    public User(final String username, final String password, final String createTime,
                final String userRights) {
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.userRights = userRights;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getCreateTime() {
        return createTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getUserRights() {
        return userRights;
    }


    public void setUserRights(String userRights) {
        this.userRights = userRights;
    }

}
