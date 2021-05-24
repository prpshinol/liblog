package com.liblog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liblog.dao.IBaseDao;
import com.liblog.entity.User;
import com.liblog.exception.LoginErrorException;
import com.liblog.exception.UserRepeatException;
import com.liblog.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserService extends BaseService<User> implements IUserService {

	@Resource
	private IBaseDao<User> userDao;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveUser(User user) throws UserRepeatException {
		//1.判断用户名是否重复
		if (this.findByUsername(user.getUsername())!=null) {
			throw new UserRepeatException("用户名重复！");
		}
		//2.保存用户
		userDao.save(user);
	}

	@Override
	public User login(User user) throws LoginErrorException {
		//1.判读用户名是否存在
		User userReturn = this.findByUsername(user.getUsername());
		if (userReturn==null) {
			throw new LoginErrorException("用户不存在!");
		}
		//2.判读密码是否正确
		if (!userReturn.getPassword().equals(user.getPassword())) {
			throw new LoginErrorException("密码不正确!");
		}
		return userReturn;
	}

	@Override
	public User findByUsername(String username) {
		String hql="from User u where u.username=?";
		List<User> result = userDao.find(hql, new Object[]{username});
		if (result!=null&&result.size()>0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public JSONObject getUserList(int page, int rows, String username) {
		String hql="from User u where 1=1 ";
		List<Object> params=new ArrayList<Object>();
		if(username!=null&&!username.equals("")){
			hql+=" and u.username like ? ";
			params.add("%"+username+"%");
		}
		List<User> users = userDao.find(hql, params.toArray(), page, rows);
		Long count = userDao.count(hql, params.toArray());
		JSONArray jUserList=new JSONArray();
		for (User user : users) {
			JSONObject jUser=new JSONObject();
			jUser.put("id", user.getUserId());
			jUser.put("username", user.getUsername());
			jUser.put("imgPath", user.getImgPath());
			jUser.put("gender", user.getGender());
			jUserList.add(jUser);
		}
		JSONObject data=new JSONObject();
		data.put("data", jUserList);
		data.put("count", count);
		return data;
	}

	@Override
	public JSONObject getProfile(int id) {
		JSONObject profile=new JSONObject();
		User user = userDao.get(User.class, id);
		profile.put("id", user.getUserId());
		profile.put("username", user.getUsername());
		profile.put("imgPath", user.getImgPath());
		profile.put("bookCount", user.getBooks().size());
		profile.put("fansCount", user.getUsers().size());
		profile.put("followCount", user.getFanses().size());
		return profile;
	}

	@Override
	public JSONArray getSearchUsers(String condition) {
		JSONArray userList=new JSONArray();
		if(condition==null||condition.length()==0){
			return userList;
		}
		//根据用户名搜索用户
		String hql="from User u where u.username like ?";
		List<User> users = userDao.find(hql, new Object[]{"%"+condition+"%"});
		
		for (User user : users) {
			JSONObject jUser=new JSONObject();
			jUser.put("id", user.getUserId());
			jUser.put("username", user.getUsername());
			jUser.put("imgPath", user.getImgPath());
			jUser.put("gender", user.getGender());
			userList.add(jUser);
		}
		return userList;
	}

}
