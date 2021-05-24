package com.liblog.service;

import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.Book;
import com.liblog.exception.BookRepeatException;
import com.liblog.util.Options;

/**
 * Created by linzhi on 2016/12/12.
 */
public interface IBookService extends IBaseService<Book> {
    /**
     * 分页查询荐书列表
     * @param options 选项
     * @return 相当于分页bean
     */
    JSONObject findBooksByPagination(Options options);

    /**
     * 书的状态值加一
     * @param book
     */
    void addBookStatus(Book book);

    /**
     * 书的状态值减一
     * @param book
     */
    void reduceBookStatus(Book book);

    /**
     * 荐书，需要检查isbn是否重复
     * @param book
     */
    void saveBook(Book book) throws BookRepeatException;
}
