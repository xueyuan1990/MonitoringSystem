package com.mz.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mz.common.BaseController;

/**
 * Controller:用户注销
 * 
 * @author xueyuan
 * @since 1.0
 **/
@Controller
@RequestMapping("/system/logoff")
public class LogoffController {
    static Logger logger = LoggerFactory.getLogger(LogoffController.class);


    @RequestMapping("/logoff")
    public void logoff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> error = new HashMap<String, Object>();
        try {
            request.getSession().removeAttribute("username");
            String username = (String) request.getSession().getAttribute("username");

            if (username == null) {
                error.put("code", 200);
                error.put("message", "");
                error.put("value", true);
            } else {
                error.put("code", 198000);
                error.put("message", "注销失败!");
                error.put("value", false);
            }

            BaseController.writeJson(response, error);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            BaseController.writeJson(response, error);
        }
    }
}
