package com.mz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter:登录过滤
 * 
 * @author xueyuan
 * @since 1.0
 **/
public class LoginFilter implements Filter {
    static Logger logger = LoggerFactory.getLogger(LoginFilter.class);


    public LoginFilter() {
    }


    @Override
    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession ses = req.getSession();
        if (ses.getAttribute("username") != null) {
            try {
                chain.doFilter(request, response);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } catch (ServletException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            try {
                ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
