package com.liblog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liblog.dao.IBaseDao;
import com.liblog.entity.Book;
import com.liblog.entity.Collect;
import com.liblog.entity.Vote;
import com.liblog.exception.BookRepeatException;
import com.liblog.exception.LoginErrorException;
import com.liblog.service.IBookService;
import com.liblog.util.Options;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linzhi on 2016/12/12.
 */
@Service
@Transactional
public class BookService extends BaseService<Book> implements IBookService {
    @Resource
    private IBaseDao<Book> bookDao;
    @Resource
    private IBaseDao<Vote> voteDao;
    @Resource
    private IBaseDao<Collect> collectDao;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public JSONObject findBooksByPagination(Options options) {
        StringBuilder hql = new StringBuilder();
        List<Object> params = new LinkedList<>();
        JSONObject myPagination = new JSONObject();
        JSONArray myData = new JSONArray();
        Long count = null;

        //一、默认
        if (options.getListType() == null || options.getListType().equals(Options.OPTIONS_LISTTYPE_DEFAULT)) {
            /****************根据条件拼接hql********************/
            hql.append("from Book b where 1=1 ");
            //搜索条件
            if (!StringUtils.isEmpty(options.getSearchStr())) {
                hql.append("and (b.title like ? or b.author like ? or b.isbn like ?) ");
                params.add("%" + options.getSearchStr() + "%");
                params.add("%" + options.getSearchStr() + "%");
                params.add("%" + options.getSearchStr() + "%");
            }
            //谁荐购的书
            if (options.getUserId() != null) {
                hql.append("and b.user.userId=? ");
                params.add(options.getUserId());
            }
            //书的状态
            if (options.getStatus() != null) {
                hql.append("and b.status=? ");
                params.add(options.getStatus());
            }
            //书的语言
            if (options.getLanguage() != null) {
                hql.append("and b.language=? ");
                params.add(options.getLanguage());
            }
            //查询总记录数
            count = bookDao.count("select count(1) " + hql.toString(), params.toArray());

            hql.append("order by b.createTime desc");

            //查询分页数据
            List<Book> books = bookDao.find(hql.toString(), params.toArray(), options.getPageNo(), options.getPageSize());

            /****************封装Json********************/
            //封装data
            for (Book book : books) {
                JSONObject myBook = getJsonBook(book, options);
                myData.add(myBook);
            }
        }
        //二、投票榜
        else if (options.getListType().equals(Options.OPTIONS_LISTTYPE_RANKING)) {
            hql.append("from Book b where b.status=1 ");

            hql.append("and b.votes.size > 0  ");

            //查询总记录数
            count = bookDao.count("select count(1) " + hql.toString());

            hql.append("order by b.votes.size desc,b.createTime desc ");

            //查询分页数据
            List<Book> books = bookDao.find(hql.toString(), options.getPageNo(), options.getPageSize());

            /****************封装Json********************/
            //封装data
            for (Book book : books) {
                JSONObject myBook = getJsonBook(book, options);
                myData.add(myBook);
            }
        }
        //三、某用户投票的荐书列表
        else if (options.getListType().equals(Options.OPTIONS_LISTTYPE_VOTE)) {
            /****************根据条件拼接hql********************/
            hql.append("from Vote v where 1=1 ");
            //谁投票的书
            if (options.getUserId() != null) {
                hql.append("and v.user.userId=? ");
                params.add(options.getUserId());
            }
            //书的状态
            if (options.getStatus() != null) {
                hql.append("and v.book.status=? ");
                params.add(options.getStatus());
            }
            //查询总记录数
            count = voteDao.count("select count(1) " + hql.toString(), params.toArray());

            hql.append("order by v.createTime desc");

            //查询分页数据
            List<Vote> votes = voteDao.find(hql.toString(), params.toArray(), options.getPageNo(), options.getPageSize());

            /****************封装Json********************/
            //封装data
            for (Vote vote : votes) {
                Book book = vote.getBook();
                JSONObject myBook = getJsonBook(book, options);
                myData.add(myBook);
            }
        }
        //四、某用户收藏的荐书列表
        else if (options.getListType().equals(Options.OPTIONS_LISTTYPE_COLLECT)) {
            /****************根据条件拼接hql********************/
            hql.append("from Collect c where 1=1 ");
            //谁投票的书
            if (options.getUserId() != null) {
                hql.append("and c.user.userId=? ");
                params.add(options.getUserId());
            }
            //书的状态
            if (options.getStatus() != null) {
                hql.append("and c.book.status=? ");
                params.add(options.getStatus());
            }
            //查询总记录数
            count = collectDao.count("select count(1) " + hql.toString(), params.toArray());

            hql.append("order by c.createTime desc");

            //查询分页数据
            List<Collect> collects = collectDao.find(hql.toString(), params.toArray(), options.getPageNo(), options.getPageSize());

            /****************封装Json********************/
            //封装data
            for (Collect collect : collects) {
                Book book = collect.getBook();
                JSONObject myBook = getJsonBook(book, options);
                myData.add(myBook);
            }
        }
        //五、登录用户的主页
        else if (options.getListType() == null || options.getListType().equals(Options.OPTIONS_LISTTYPE_INDEX)) {
            /****************根据条件拼接hql********************/
            hql.append("from Book b where b.user.userId=? or b.user.userId in (select f.id.userId from Follow f where f.id.fansId=?) ");
            params.add(options.getLoginUserId());
            params.add(options.getLoginUserId());

            //查询总记录数
            count = bookDao.count("select count(1) " + hql.toString(), params.toArray());

            hql.append("order by b.createTime desc");

            //查询分页数据
            List<Book> books = bookDao.find(hql.toString(), params.toArray(), options.getPageNo(), options.getPageSize());

            /****************封装Json********************/
            //封装data
            for (Book book : books) {
                JSONObject myBook = getJsonBook(book, options);
                myData.add(myBook);
            }
        }

        myPagination.put("books", myData);
        //封装分页数据
        myPagination.put("totalCount", count);

        return myPagination;
    }

    @Override
    public void addBookStatus(Book book) {
        Book book1 = bookDao.get(Book.class, book.getBookId());
        if (book1.getStatus() < 3) {
            book1.setStatus(book1.getStatus() + 1);
        }
    }

    @Override
    public void reduceBookStatus(Book book) {
        Book book1 = bookDao.get(Book.class, book.getBookId());
        if (book1.getStatus() > 1) {
            book1.setStatus(book1.getStatus() - 1);
        }
    }

    @Override
    public void saveBook(Book book) throws BookRepeatException {
        //1.判断图书是否存在
        Book book2 = this.findByISBN(book.getIsbn());
        if (book2!=null) {
            throw new BookRepeatException("该图书已存在!");
        }
        //2.保存图书
        bookDao.save(book);
    }

    private Book findByISBN(String isbn) {
        String hql="from Book b where b.isbn=?";
        List<Book> result = bookDao.find(hql, new Object[]{isbn});
        if (result!=null&&result.size()>0) {
            return result.get(0);
        }
        return null;
    }

    //封装单个Book为JsonObject
    private JSONObject getJsonBook(Book book, Options options) {
        JSONObject myBook = new JSONObject();
        myBook.put("id", book.getBookId());
        myBook.put("title", book.getTitle());
        myBook.put("author", book.getAuthor());
        myBook.put("publisher", book.getPublisher());
        myBook.put("pubdate", book.getPubdate());
        myBook.put("isbn", book.getIsbn());
        myBook.put("image", book.getImage());
        myBook.put("url", book.getUrl());
        myBook.put("voteCount", book.getVotes().size());
        myBook.put("createTime", sdf.format(book.getCreateTime()));
        myBook.put("status", book.getStatus());
        myBook.put("language", book.getLanguage());
        myBook.put("reason", book.getReason());

        myBook.put("userId", book.getUser().getUserId());
        myBook.put("userName", book.getUser().getUsername());
        myBook.put("userImg", book.getUser().getImgPath());

        myBook.put("voteCount", book.getVotes().size());
        myBook.put("commentCount", book.getComments().size());
        myBook.put("collectCount", book.getCollects().size());

        //当前登录用户是否投票过
        for (Vote vote : book.getVotes()) {
            if (vote.getUser().getUserId().equals(options.getLoginUserId())) {
                myBook.put("isVote", true);
                break;
            }
        }
        //当前用户是否收藏过
        for (Collect collect : book.getCollects()) {
            if (collect.getUser().getUserId().equals(options.getLoginUserId())) {
                myBook.put("isCollect", true);
                break;
            }
        }
        return myBook;
    }

}
