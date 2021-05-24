package com.liblog.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 * Created by linzhi on 2016/12/15.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains("/login")) {//登录Controller，不需要过滤
            filterChain.doFilter(request, response);
        } else {
            if (request.getRequestURI().contains("/admin")) {//后台
                if (request.getSession().getAttribute("loginAdmin") != null) {//管理员已登录
                    filterChain.doFilter(request, response);
                } else {//管理员未登录
                    response.sendRedirect(request.getContextPath() + "/login/adminLoginUI.do");
                }
            } else if(request.getRequestURI().contains("/book")){//前后台共用
                if (request.getSession().getAttribute("loginUser") != null || request.getSession().getAttribute("loginAdmin") != null) {//已登录
                    filterChain.doFilter(request, response);
                } else {//未登录
                    response.sendRedirect(request.getContextPath() + "/login/loginUI.do");
                }
            } else {//前台 + 如http://localhost:8080/library/
                if (request.getSession().getAttribute("loginUser") != null) {//已登录
                    filterChain.doFilter(request, response);
                } else {//未登录
                    response.sendRedirect(request.getContextPath() + "/login/loginUI.do");
                }
            }

        }

    }

    @Override
    public void destroy() {

    }
}
