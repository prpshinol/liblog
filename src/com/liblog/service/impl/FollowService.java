package com.liblog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liblog.dao.IBaseDao;
import com.liblog.entity.Follow;
import com.liblog.entity.User;
import com.liblog.service.IFollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by linzhi on 2016/12/14.
 */
@Transactional
@Service
public class FollowService extends BaseService<Follow> implements IFollowService {
    @Resource
    private IBaseDao<Follow> followDao;
    @Resource
    private IBaseDao<User> userDao;

    private List<Follow> getFansListByUserId(Integer userId,Integer limit) {
        String hql="from Follow f "
                + "where f.user.userId=?";
        if(limit!=null){
            return followDao.findByMax(hql, new Object[]{userId},limit);
        }else {
            return followDao.find(hql, new Object[]{userId});
        }
    }

    private List<Follow> getFollowingListByFansId(Integer fansId,Integer limit) {
        String hql="from Follow f "
                + "where f.fans.userId=?";
        if(limit!=null){
            return followDao.findByMax(hql, new Object[]{fansId},limit);
        }else {
            return followDao.find(hql, new Object[]{fansId});
        }
    }

    @Override
    public JSONObject getFansList(User user, Integer limit, Integer page) {
        List<Follow> fansList=null;
        Long count = null;
        if(page==null){
            fansList = this.getFansListByUserId(user.getUserId(),limit);
        }else {
            String hql="from Follow f "
                    + "where f.user.userId=?";
            fansList = followDao.find(hql, new Object[]{user.getUserId()}, page, limit);
            count = followDao.count("select count(1) "+hql, new Object[]{user.getUserId()});
        }
        JSONArray jFansList=new JSONArray();
        //需要获取粉丝的用户名，头像，粉丝数量，关注数量，性别
        for (Follow follow : fansList) {
            JSONObject jFans=new JSONObject();

            User fans=follow.getFans();
            jFans.put("id", fans.getUserId());
            jFans.put("username", fans.getUsername());
            jFans.put("userImg", fans.getImgPath());
            jFans.put("gender", fans.getGender());
            jFans.put("fansCount", fans.getUsers().size());
            jFans.put("followCount", fans.getFanses().size());
            jFans.put("bookCount", fans.getBooks().size());
            jFansList.add(jFans);
        }
        JSONObject result=new JSONObject();
        result.put("fansList", jFansList);
        if (page!=null) {
            result.put("page", page);
            result.put("rows", limit);
            result.put("count", count);
        }
        return result;
    }

    @Override
    public JSONObject getFollowingList(User fans,Integer limit,Integer page) {
        List<Follow> followList=null;
        Long count = null;
        if (page==null) {
            followList = this.getFollowingListByFansId(fans.getUserId(),limit);
        }else {
            String hql="from Follow f "
                    + "where f.fans.userId=?";
            followList = followDao.find(hql, new Object[]{fans.getUserId()}, page, limit);
            count = followDao.count("select count(1) "+hql, new Object[]{fans.getUserId()});
        }
        JSONArray jUserList=new JSONArray();
        //需要获取粉丝的用户名，头像，粉丝数量，关注数量，性别
        for (Follow follow : followList) {
            JSONObject jUser=new JSONObject();

            User user=follow.getUser();
            jUser.put("id", user.getUserId());
            jUser.put("username", user.getUsername());
            jUser.put("userImg", user.getImgPath());
            jUser.put("gender", user.getGender());
            jUser.put("fansCount", user.getUsers().size());
            jUser.put("followCount", user.getFanses().size());
            jUser.put("bookCount", user.getBooks().size());
            jUserList.add(jUser);
        }

        JSONObject result=new JSONObject();
        result.put("followingList", jUserList);
        if (page!=null) {
            result.put("page", page);
            result.put("rows", limit);
            result.put("count", count);
        }

        return result;
    }

    @Override
    public boolean isFollow(int id1, int id2) {
        if(id1==id2){
            return false;
        }
        User user1 = userDao.get(User.class, id1);
        Set<Follow> users = user1.getUsers();
        for (Follow follow : users) {
            if (follow.getUser().getUserId().equals(id2)) {
                return true;
            }
        }
        return false;
    }
}
