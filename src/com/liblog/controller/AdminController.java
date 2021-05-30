package com.liblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.Book;
import com.liblog.service.IBookService;
import com.liblog.util.Json;
import com.liblog.util.Options;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 后台
 * Created by linzhi on 2016/12/15.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    IBookService bookService;

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

    /**
     * 获取荐书列表
     * @param options 选项
     * @return 包含数据与结果信息
     */
    @RequestMapping("/getBookList")
    @ResponseBody
    public Json getBookList(Options options) {
        //参数校验
        if (options.getPageNo() == null) {
            options.setPageNo(1);
        }
        if (options.getPageSize() == null) {
            options.setPageSize(Options.OPTIONS_PAGESIZE_DEFAULT);
        }

        Json json = new Json();

        JSONObject myPagination = bookService.findBooksByPagination(options);
        json.setData(myPagination);
        json.setStatus(true);
        return json;
    }

    /**
     * 书的status+1
     * @param book
     * @return
     */
    @RequestMapping("/addBookStatus")
    @ResponseBody
    public Json addBookStatus(Book book) {
        Json json = new Json();
        bookService.addBookStatus(book);
        json.setStatus(true);
        return json;
    }

    /**
     * 书的status-1
     * @param book
     * @return
     */
    @RequestMapping("/reduceBookStatus")
    @ResponseBody
    public Json reduceBookStatus(Book book) {
        Json json = new Json();
        bookService.reduceBookStatus(book);
        json.setStatus(true);
        return json;
    }
}
