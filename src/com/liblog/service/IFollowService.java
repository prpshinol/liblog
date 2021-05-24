package com.liblog.service;

import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.Follow;
import com.liblog.entity.User;

/**
 * Created by linzhi on 2016/12/14.
 */
public interface IFollowService extends IBaseService<Follow> {

    /**
     * 获取粉丝列表
     * @param user
     * @return
     */
    public JSONObject getFansList(User user, Integer limit, Integer page);

    /**
     * 获取关注列表
     * @param
     * @return
     */
    public JSONObject getFollowingList(User fans,Integer limit,Integer page);

    /**
     * 判断user1是否关注了user2
     * @param id1
     * @param id2
     * @return
     */
    public boolean isFollow(int  id1,int id2);
}
