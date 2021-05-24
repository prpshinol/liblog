package com.liblog.controller;

import com.liblog.entity.Admin;
import com.liblog.entity.User;
import com.liblog.exception.FileUploadException;
import com.liblog.exception.LoginErrorException;
import com.liblog.exception.UserRepeatException;
import com.liblog.service.IAdminService;
import com.liblog.service.IUserService;
import com.liblog.util.AuthImageUtil;
import com.liblog.constant.Constant;
import com.liblog.util.FileUtil;
import com.liblog.util.LoginCookieUtil;
import com.liblog.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 登录注册
 * Created by linzhi on 2016/12/12.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IUserService userService;
    @Resource
    private IAdminService adminService;

    /**
     * 跳转到注册页面
     */
    @RequestMapping("/registerUI")
    public String registerUI() {
        return "login/register";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping("/loginUI")
    public String loginUI() {
        return "login/login";
    }

    /**
     * 获取验证码
     */
    @RequestMapping("/getCode")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthImageUtil.getCode(response, request, AuthImageUtil.CODE);
    }

    /**
     * 注册
     * @param user
     * @param request
     * @param password2
     * @param imgStr
     * @param code
     * @return
     */
    @RequestMapping("/register")
    public ModelAndView register(User user, HttpServletRequest request, String password2, String imgStr, String code){

        try {

            if (user.getUsername()==null
                    ||user.getUsername().equals("")
                    ||user.getUsername().length()<3||user.getUsername().length()>16) {
                throw new IllegalArgumentException("用户名长度在3到16位之间!");
            }

            if(user.getPassword()==null||user.getPassword().equals("")
                    ||!user.getPassword().equals(password2)){
                throw new IllegalArgumentException("密码必填，两次密码输入要一致！");
            }

            //判断验证码是否正确
            if (code==null||!code.equals(request.getSession().getAttribute(AuthImageUtil.CODE))) {
                request.setAttribute("msg", "验证码不正确");
                return new ModelAndView("login/register");
            }



            //1.封装参数到user
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            //2.上传图片，封装图片路径到user
            String imgPath = FileUtil.base64ToImg(imgStr, request);
            if (imgPath!=null) {
                user.setImgPath(imgPath);
            }else {
                user.setImgPath(Constant.DEFAULT_USER_IMG);
            }

            //注册之前md5加密
            user.setPassword(MD5Util.getMD5(user.getPassword()));

            //3.调用service保存用户
            userService.saveUser(user);

            return new ModelAndView("redirect:/login/loginUI.do");
        } catch (FileUploadException e) {
            e.printStackTrace();
            request.setAttribute("msg", "上传图片出错！");
            return new ModelAndView("login/register");
        } catch (UserRepeatException e) {
            e.printStackTrace();
            request.setAttribute("msg", "用户名重复!");
            return new ModelAndView("login/register");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());
            return new ModelAndView("login/register");
        }
    }

    /**
     * 登陆
     * @param user
     * @param request
     * @param response
     * @param remember
     * @param code
     * @return
     * @throws IOException
     */
    @RequestMapping("/login")
    public ModelAndView login(User user,HttpServletRequest request,HttpServletResponse response,String remember,String code) throws IOException{
        try {

            //判断验证码是否正确
            if (code==null||!code.toLowerCase().equals(request.getSession().getAttribute(AuthImageUtil.CODE))) {
                request.setAttribute("msg", "验证码不正确");
                return new ModelAndView("login/login");
            }

            //验证之前md5加密
            user.setPassword(MD5Util.getMD5(user.getPassword()));

            //1.调用service
            User loginUser = userService.login(user);

            //判断是否需要保存登陆信息
            if (remember!=null&&remember.equals("1")) {
                LoginCookieUtil.saveCookie(loginUser, response);
            }

            //2.保存用户信息到session
            request.getSession().setAttribute("loginUser", loginUser);
            //3.跳转到首页
            return new ModelAndView("redirect:/home/indexUI.do");
        } catch (LoginErrorException e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());
            return new ModelAndView("login/login");
        }
    }


    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,HttpSession session){
        session.setAttribute("loginUser", null);
        //清除自动登陆cookie
        LoginCookieUtil.clearCookie(response);
        return new ModelAndView("redirect:/login/loginUI.do");
    }

    /**
     * 跳转到管理员登录页面
     */
    @RequestMapping("/adminLoginUI")
    public String adminLoginUI() {
        return "login/admin_login";
    }

    /**
     * 管理员登录
     * @param admin 用户名、密码
     * @param code 验证码
     */
    @RequestMapping("/adminLogin")
    public String adminLogin(Admin admin, String code, HttpServletRequest request) {
        if(!code.toLowerCase().equals(request.getSession().getAttribute(AuthImageUtil.CODE))) { //判断验证码是否正确
            request.setAttribute("msg","验证码不正确!");
        } else {
            //登录
            try {
                //验证之前md5加密
                admin.setPassword(MD5Util.getMD5(admin.getPassword()));

                Admin loginAdmin = adminService.login(admin);

                //把用户信息设置到session中
                request.getSession().setAttribute("loginAdmin",loginAdmin);

                return "redirect:/admin/indexUI.do";
            } catch (LoginErrorException e) {
                request.setAttribute("msg",e.getMessage());
            }
        }
        return "login/admin_login";
    }

    /**
     * 管理员注销
     */
    @RequestMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginAdmin");
        return "redirect:/login/adminLoginUI.do";
    }
}
