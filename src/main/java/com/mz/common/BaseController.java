package com.mz.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;

public class BaseController {

    public static void writeJson(Logger logger, HttpServletResponse response,
                                 Map<String, Object> map) {

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        om.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        String info = null;
        try {
            info = om.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        out.print(info);
        out.flush();
    }


    public static void writeJson(Logger logger, HttpServletResponse response, List list) {

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        om.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        String info = null;
        try {
            info = om.writeValueAsString(list);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        out.print(info);
        out.flush();
    }


    public static void writeJson(Logger logger, HttpServletResponse response, String info) {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        out.print(info);
        out.flush();
    }


    public static String getJson(Logger logger, HttpServletResponse response,
                                 Map<String, Object> map) {

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        om.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        String info = null;
        try {
            info = om.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return info;
    }

}
