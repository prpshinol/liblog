package com.liblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.*;
import com.liblog.exception.BookRepeatException;
import com.liblog.service.IBookService;
import com.liblog.util.Options;
import com.liblog.util.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 书籍处理
 * Created by linzhi on 2016/12/6.
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Resource
    private IBookService bookService;

    /**
     * 荐书
     * @param book 书的信息
     */
    @RequestMapping("/save")
    public String save(Book book, HttpServletRequest request) {
        //获取荐书的用户（当前登录的用户）
        User user = (User) request.getSession().getAttribute("loginUser");

        book.setUser(user);
        book.setCreateTime(new Timestamp(new Date().getTime()));
        book.setStatus(Book.BOOK_STATUS_RECOMMENDED);
        try {
            bookService.saveBook(book);
        } catch (BookRepeatException e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());
            return "home/add";
        }
        return "redirect:/home/indexUI.do";
    }

    /**
     * 获取荐书列表
     * @param options 选项
     * @return 包含数据与结果信息
     */
    @RequestMapping("/getBookList")
    @ResponseBody
    public Json getBookList(Options options, HttpServletRequest request) {
        //参数校验
        if (options.getPageNo() == null) {
            options.setPageNo(1);
        }
        if (options.getPageSize() == null) {
            options.setPageSize(Options.OPTIONS_PAGESIZE_DEFAULT);
        }

        Json json = new Json();
        //为了判断荐书列表书目中当前用户有没有投票过
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            options.setLoginUserId(user.getUserId());
        }

        JSONObject myPagination = bookService.findBooksByPagination(options);
        json.setData(myPagination);
        json.setStatus(true);
        return json;
    }

}
