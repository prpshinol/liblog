package com.liblog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liblog.dao.IBaseDao;
import com.liblog.entity.Comment;
import com.liblog.entity.User;
import com.liblog.entity.Vote;
import com.liblog.service.ICommentService;
import com.liblog.service.IVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by linzhi on 2016/12/14.
 */
@Transactional
@Service
public class CommentService extends BaseService<Comment> implements ICommentService {
    @Resource
    private IBaseDao<Comment> commentDao;

    @Override
    public JSONArray findByBookId(Integer bookId) {
        List<Comment> commentList = commentDao.find("from Comment c where c.book.bookId=? ", new Object[]{bookId});
        JSONArray jList = new JSONArray();

        if (commentList != null) {
            for (Comment comment : commentList) {
                if (comment.getComment() == null) {
                    jList.add(getFirstComment(comment));
                }
            }
        }

        return jList;
    }

    /**
     * 得到第一层的Comment的JSONObject
     *
     * @param comment
     * @return
     */
    private JSONObject getFirstComment(Comment comment) {

        JSONObject jComment = toComment(comment);

        //获取后代
        JSONArray childs = new JSONArray();
        getChilds(comment, childs);

        jComment.put("childs", childs);

        return jComment;
    }

    /**
     * 获取后代节点，保存到JSONArray中
     *
     * @param comment
     * @param childs
     */
    private void getChilds(Comment comment, JSONArray childs) {
        String hql="from Comment c where c.comment.id=?";
        List<Comment> comments = commentDao.find(hql,new Object[]{comment.getCommentId()});
        for (Comment c : comments) {
            childs.add(toComment(c));
            getChilds(c, childs);
        }
    }

    /**
     * 将一个Comment实体转为JSONObject
     *
     * @param comment
     * @return
     */
    private JSONObject toComment(Comment comment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        JSONObject jComment = new JSONObject();
        jComment.put("id", comment.getCommentId());
        jComment.put("content", comment.getContent());
        jComment.put("createTime", sdf.format(new Date(comment.getCreateTime().getTime())));
        User user = comment.getUser();
        jComment.put("userId", user.getUserId());
        jComment.put("username", user.getUsername());
        jComment.put("userImg", user.getImgPath());
        jComment.put("bookId", comment.getBook().getBookId());
        if (comment.getComment() != null) {
            Comment parent = comment.getComment();
            jComment.put("pId", parent.getCommentId());
            User pUser = parent.getUser();
            jComment.put("pUserId", pUser.getUserId());
            jComment.put("pUsername", pUser.getUsername());
            jComment.put("pUserImg", pUser.getImgPath());
        }
        return jComment;
    }
}
