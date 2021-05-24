package com.liblog.controller;

import com.liblog.entity.Book;
import com.liblog.entity.Collect;
import com.liblog.entity.CollectId;
import com.liblog.entity.User;
import com.liblog.service.ICollectService;
import com.liblog.util.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by linzhi on 2017/3/13.
 */
@Controller
@RequestMapping("/collect")
public class CollectController {
    @Resource
    private ICollectService collectService;

    /**
     * 收藏
     * @param book 收藏的书
     */
    @RequestMapping("/collectBook")
    @ResponseBody
    public Json collectBook(Book book, HttpServletRequest request) {
        Json json = new Json();
        User user = (User) request.getSession().getAttribute("loginUser");

        //保存收藏实体
        Collect collect = new Collect();
        CollectId id = new CollectId();
        id.setUserId(user.getUserId());
        id.setBookId(book.getBookId());
        collect.setCreateTime(new Timestamp(new Date().getTime()));
        collect.setId(id);
        collectService.save(collect);

        json.setStatus(true);
        return json;
    }

    /**
     * 取消收藏
     * @param book 收藏的书
     */
    @RequestMapping("/disCollectBook")
    @ResponseBody
    public Json disCollectBook(Book book,HttpServletRequest request) {
        Json json = new Json();
        User user = (User) request.getSession().getAttribute("loginUser");

        //删除收藏实体
        Collect collect = new Collect();
        CollectId id = new CollectId();
        id.setUserId(user.getUserId());
        id.setBookId(book.getBookId());
        collect.setId(id);
        collectService.delete(collect);

        json.setStatus(true);
        return json;
    }
}
