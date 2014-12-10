package com.mz.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * 提供json操作统一方法
 * 
 * @author xueyuan
 * @since 1.0
 **/

public class BaseController {

    public static void writeJson(HttpServletResponse response, Map<String, Object> map)
            throws IOException {
        String info = JSON.toJSONString(map);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        out = response.getWriter();
        out.print(info);
        out.flush();
    }


    public static void writeJson(HttpServletResponse response, List list) throws IOException {
        String info = JSON.toJSONString(list);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        out = response.getWriter();
        out.print(info);
        out.flush();
    }


    public static void writeJson(HttpServletResponse response, String info) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        out = response.getWriter();
        out.print(info);
        out.flush();
    }


    public static String getJson(HttpServletResponse response, Map<String, Object> map)
            throws IOException {
        String info = JSON.toJSONString(map);
        return info;
    }

}
