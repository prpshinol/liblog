package com.liblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台
 * Created by linzhi on 2016/12/15.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * 后台首页
     */
    @RequestMapping("/indexUI")
    public String indexUI() {
        return "admin/index";
    }
    /**
     * 搜索
     */
    @RequestMapping("/searchUI")
    public String searchUI(String searchStr, HttpServletRequest request) {
        //防止js中'${searchStr}'当为'content'conent'时出错
        request.setAttribute("searchStr", searchStr.replace("'", "\\'"));
        return "admin/search";
    }
}
