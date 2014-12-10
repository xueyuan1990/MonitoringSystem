package com.mz.controller;

import java.util.HashMap;
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
 * Controller:用户登录
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/login")
public class LoginController {
    static Logger       logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("/isLogin")
    public void isLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> error = new HashMap<String, Object>();
        try {
            User u = new User();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            u.setUsername(username);
            u.setPassword(password);
            boolean loginSuccess = false;
            loginSuccess = userService.isLogin(u);

            if (loginSuccess) {
                request.getSession().setAttribute("username", u.getUsername());
                error.put("code", 200);
                error.put("message", "");
                error.put("value", true);
            } else {
                error.put("code", 198000);//未授权
                error.put("message", "用户名或密码错误!");
                error.put("value", false);
            }

            BaseController.writeJson(response, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, error);
        }
    }

}
