package com.liblog.controller;

import com.alibaba.fastjson.JSONArray;
import com.liblog.entity.Book;
import com.liblog.entity.Comment;
import com.liblog.entity.User;
import com.liblog.service.IBookService;
import com.liblog.service.ICommentService;
import com.liblog.util.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Created by linzhi on 2017/3/13.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;
    @Resource
    private IBookService bookService;

    /**
     * 评论分享/回复评论
     * @param comment	评论，开始只有内容
     * @param bookId	评论的分享的id
     * @param parentId	父评论的id(回复评论时有值)
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Json saveComment(Comment comment, Integer bookId, Integer parentId, HttpSession session){

        Json result=new Json();

        try {

            //验证参数
            if(comment.getContent()==null||comment.getContent().equals("")){
                throw new IllegalArgumentException("评论不能为空!");
            }

            if(comment.getContent().length()>100){
                throw new IllegalArgumentException("评论字数必须小于100!");
            }

            //1.调用shareService根据id获取Share
            Book book = bookService.get(bookId);
            comment.setBook(book);
            //2.调用commentService根据id获取Comment
            if (parentId==null||parentId.equals("")) {
                comment.setComment(null);
            }else {
                Comment parent = commentService.get(parentId);
                comment.setComment(parent);
            }
            //3.封装基本信息到comment中
            comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
            comment.setUser((User)session.getAttribute("loginUser"));
            //4.调用commentService保存评论
            commentService.save(comment);
            result.setStatus(true);
            result.setMsg("保存评论成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("保存评论失败,原因:"+e.getMessage());
        }
        return result;
    }

    @RequestMapping("/getCommentList")
    @ResponseBody
    public Json getCommentList(Integer bookId){

        Json result=new Json();

        try {
            //获取评论列表，有两层，第一层是直接评论分享，第二层是回复评论，
            JSONArray data = commentService.findByBookId(bookId);
            result.setData(data);
            result.setStatus(true);
            result.setMsg("获取评论列表成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("获取评论列表失败！");
        }

        return result;
    }
}
