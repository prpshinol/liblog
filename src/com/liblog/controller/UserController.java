package com.liblog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.User;
import com.liblog.service.IFollowService;
import com.liblog.service.IUserService;
import com.liblog.util.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IFollowService followService;
	
	/**
	 * 获取用户简介，包括用户名，头像，用户粉丝数，关注数，分享数
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping("/getProfile")
	@ResponseBody
	public Json getProfile(Integer userId, HttpSession session){
		Json result=new Json();
		try {
			JSONObject profile = userService.getProfile(userId);
			User user=(User)session.getAttribute("loginUser");
			
			//添加下面的信息，用于生成关注/取消关注按钮
			if(!userId.equals(user.getUserId())){
				profile.put("isMe", false);
				//查询我是否关注该用户
				boolean isFollow = followService.isFollow(user.getUserId(), userId);
				profile.put("isFollow", isFollow);
			}else {
				profile.put("isMe", true);
			}
			result.setData(profile);
			result.setStatus(true);
			result.setMsg("获取个人简介成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("获取个人简介失败！"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据用户名和微博内容搜索相关用户
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getSearchUsers")
	@ResponseBody
	public Json getSearchUsers(String condition){
		Json result=new Json();
		try {
			JSONArray searchUsers = userService.getSearchUsers(condition);
			result.setData(searchUsers);
			result.setStatus(true);
			result.setMsg("搜素用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("搜索用户失败！"+e.getMessage());
		}
		
		return result;
	}
	

}
