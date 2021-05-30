package com.liblog.util;

/**
 * 查询荐书列表的选项（当参数有多个并且以后有可能增加的时候，使用该类）
 * Created by linzhi on 2016/12/14.
 */
public class Options {
    private Integer pageNo;
    private Integer pageSize;
    private Integer loginUserId;//当前登录的用户id
    private Integer listType;//以何种方式查询荐书列表
    private String searchStr;//查询字符串
    private Integer userId;//谁荐购的书
    private Integer status;//书的状态
    private Integer language;//书的语言
    private Integer lastNDays;//时间筛选（过去N天:N=1今天，N=7最近一周，null全部）
    //常量
    public static final Integer OPTIONS_LISTTYPE_DEFAULT = 0;//最新荐书列表（默认）

    public static final Integer OPTIONS_LISTTYPE_RANKING = 1;//投票榜
    public static final Integer OPTIONS_LISTTYPE_VOTE = 2;//某用户投票的荐书
    public static final Integer OPTIONS_LISTTYPE_COLLECT = 3;//某用户收藏的荐书
    public static final Integer OPTIONS_LISTTYPE_INDEX = 4;//登录用户的主页
    public static final Integer OPTIONS_PAGESIZE_DEFAULT = 5;//默认页大小

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Integer loginUserId) {
        this.loginUserId = loginUserId;
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getLastNDays() {
        return lastNDays;
    }

    public void setLastNDays(Integer lastNDays) {
        this.lastNDays = lastNDays;
    }
}
