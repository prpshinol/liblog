package com.liblog.service;

import com.alibaba.fastjson.JSONArray;
import com.liblog.entity.Comment;

/**
 * Created by linzhi on 2016/12/14.
 */
public interface ICommentService extends IBaseService<Comment> {
    /**
     * 根据bookId查找评论列表
     * @param bookId
     * @return
     */
    JSONArray findByBookId(Integer bookId);
}
