package com.liblog.controller;

import com.liblog.entity.Book;
import com.liblog.entity.User;
import com.liblog.entity.Vote;
import com.liblog.entity.VoteId;
import com.liblog.service.IVoteService;
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
@RequestMapping("/vote")
public class VoteController {
    @Resource
    private IVoteService voteService;

    /**
     * 投票
     * @param book 投票的书
     */
    @RequestMapping("/voteBook")
    @ResponseBody
    public Json voteBook(Book book, HttpServletRequest request) {
        Json json = new Json();
        User user = (User) request.getSession().getAttribute("loginUser");

        //保存投票实体
        Vote vote = new Vote();
        VoteId id = new VoteId();
        id.setUserId(user.getUserId());
        id.setBookId(book.getBookId());
        vote.setCreateTime(new Timestamp(new Date().getTime()));
        vote.setId(id);
        voteService.save(vote);

        json.setStatus(true);
        return json;
    }

    /**
     * 取消投票
     * @param book 投票的书
     */
    @RequestMapping("/disVoteBook")
    @ResponseBody
    public Json disVoteBook(Book book,HttpServletRequest request) {
        Json json = new Json();
        User user = (User) request.getSession().getAttribute("loginUser");

        //删除投票实体
        Vote vote = new Vote();
        VoteId id = new VoteId();
        id.setUserId(user.getUserId());
        id.setBookId(book.getBookId());
        vote.setId(id);
        voteService.delete(vote);

        json.setStatus(true);
        return json;
    }
}
