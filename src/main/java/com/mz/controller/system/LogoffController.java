package com.mz.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller:用户注销
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/system/logoff")
public class LogoffController {

    @RequestMapping("/logoff")
    public String logoff(String time, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("username");
        return "/login.jsp";

    }
}
