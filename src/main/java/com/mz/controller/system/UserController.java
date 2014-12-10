package com.mz.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mz.common.BaseController;
import com.mz.entity.User;
import com.mz.service.UserService;

/**
 * Controller:user相关操作
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    static Logger       logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("/getUsers")
    public void getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<User> list = new ArrayList<User>();
            int count = 0;
            boolean searchErrorFlag = false;//默认无错，但是如果搜索的用户不存在时会提示错误
            String errorMsg = "";
            String username = request.getParameter("username");
            int start = Integer.parseInt(request.getParameter("start"));
            int limit = Integer.parseInt(request.getParameter("limit"));

            if (username == null || username.trim().length() == 0) {
                list = userService.getUsers(start, limit);
                count = userService.getUsersNum();

            } else {
                User user = userService.getUser(username);
                if (user != null) {//存在该用户
                    list.add(user);
                    count = 1;
                } else {//不存在该用户
                    searchErrorFlag = true;
                    count = 0;
                    errorMsg = "不存在用户  " + username;
                }
            }

            map.put("rows", list);
            map.put("results", count);
            map.put("hasError", searchErrorFlag);
            map.put("error", errorMsg);
            BaseController.writeJson(response, map);//json
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, map);
        }
    }


    @RequestMapping("/addUser")
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> error = new HashMap<String, Object>();
        try {
            User u = new User();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String userRights = request.getParameter("userRights");
            try {
                if (userRights == null || userRights.trim().length() == 0) {
                    userRights = "normal user";
                }
                u.setUsername(username);
                u.setPassword(password);
                u.setUserRights(userRights);

                String usernameOfLoginuser = (String) request.getSession().getAttribute("username");
                if (!userService.isAdmin(usernameOfLoginuser)) {// 如果登录的不是有admin权限用户，则不允许其添加新用户
                    error.put("code", 198000);//未授权
                    error.put("message", "您没有操作权限!");
                    error.put("value", false);
                    BaseController.writeJson(response, error);
                    return;
                }
                userService.addUser(u);
                logger.info(usernameOfLoginuser + " add User: " + username);
                error.put("code", 200);
                error.put("message", "成功添加新用户  " + username);
                error.put("value", true);
            } catch (Exception e) {// 添加用户失败
                error.put("code", 120000);
                error.put("message", "用户  " + username + "  已存在!");
                error.put("value", false);
            }
            BaseController.writeJson(response, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, error);
        }
    }


    @RequestMapping("/deleteUser")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> error = new HashMap<String, Object>();
        try {
            String username = request.getParameter("username");
            if (username == null) {
                username = "";
            }
            boolean deleteSuccess = false;

            String usernameOfLoginuser = (String) request.getSession().getAttribute("username");
            if (!userService.isAdmin(usernameOfLoginuser) || username.equals(usernameOfLoginuser)) {// 如果登录的不是有admin权限用户，则不允许其删除用户
                error.put("code", 198000);//未授权
                error.put("message", "您没有操作权限!");
                error.put("value", false);
                BaseController.writeJson(response, error);
                return;
            }
            deleteSuccess = userService.deleteUser(username);
            if (deleteSuccess) {// 删除用户成功
                logger.info(usernameOfLoginuser + " delete User: " + username);
                error.put("code", 200);
                error.put("message", "成功删除用户  " + username);
                error.put("value", true);
            } else {// 删除用户失败
                error.put("code", 120000);
                error.put("message", "用户  " + username + "  不存在!");
                error.put("value", false);
            }
            BaseController.writeJson(response, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, error);
        }

    }

}
