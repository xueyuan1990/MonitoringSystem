package com.mz.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingFilter implements Filter {
    static Logger logger = LoggerFactory.getLogger(EncodingFilter.class);


    public EncodingFilter() {
    }


    @Override
    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ServletException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
