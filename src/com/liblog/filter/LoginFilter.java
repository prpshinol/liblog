package com.liblog.filter;

import com.liblog.util.LoginCookieUtil;

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

        if (request.getRequestURI().contains("/login/admin")) {//后台登录注册
            filterChain.doFilter(request, response);
        } else if (request.getRequestURI().contains("/admin")) {//后台页面
            if (request.getSession().getAttribute("loginAdmin") != null) {//管理员已登录
                filterChain.doFilter(request, response);
            } else {//管理员未登录
                response.sendRedirect(request.getContextPath() + "/login/adminLoginUI.do");
            }
        } else {//前台
            //执行自动登陆的操作
            boolean autoLogin = LoginCookieUtil.readCookieAndLogin(request, response);
            if(autoLogin){
                if (isPublicForUser(request)) {
                    //自动登陆的情况下，这些uri不可访问，直接跳转到首页
                    response.sendRedirect(request.getContextPath()+"/home/indexUI.do");
                }else {
                    //其他情况下，已经登陆，放行
                    filterChain.doFilter(request, response);
                }
            } else if(isPublicForUser(request)){//前台登录注册
                filterChain.doFilter(request, response);
            } else if (request.getSession().getAttribute("loginUser") != null) {//已登录
                filterChain.doFilter(request, response);
            } else {//未登录
                response.sendRedirect(request.getContextPath() + "/login/loginUI.do");
            }
        }
    }

    /**
     * 判断uri是否是用户登陆注册时用到的(自动登陆时这些uri不可通过)
     * @param request
     * @return
     */
    private boolean isPublicForUser(HttpServletRequest request){
        if (request.getRequestURI().contains("login/loginUI")
                ||request.getRequestURI().contains("login/registerUI")
                ||request.getRequestURI().contains("login/login")
                ||request.getRequestURI().contains("login/register")
                ||request.getRequestURI().contains("login/getCode")) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
