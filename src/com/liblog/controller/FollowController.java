package com.liblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.liblog.entity.Follow;
import com.liblog.entity.FollowId;
import com.liblog.entity.User;
import com.liblog.service.IFollowService;
import com.liblog.service.IUserService;
import com.liblog.util.Json;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@Scope("prototype")
@RequestMapping("/follow")
public class FollowController {
	
	@Resource
	IFollowService followService;
	
	@Resource
	IUserService userService;

	/**
	 * 关注用户
	 * @param user 被关注的用户
	 * @return
	 */
	@RequestMapping("/addFollow")
	@ResponseBody
	public Json addFollow(User user,HttpSession session){
		Json result=new Json();
		try {
			Follow follow=new Follow();
			User fans=(User)session.getAttribute("loginUser");
			FollowId followId = new FollowId();
			followId.setUserId(user.getUserId());
			followId.setFansId(fans.getUserId());
			follow.setId(followId);
			follow.setCreateTime(new Timestamp(System.currentTimeMillis()));
			followService.save(follow);
			result.setStatus(true);
			result.setMsg("关注用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("关注用户失败！");
		}
		return result;
	}
	
	/**
	 * 取消关注
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteFollow")
	@ResponseBody
	public Json deleteFollow(User user,HttpSession session){
		Json json=new Json();
		try {
			User fans=(User)session.getAttribute("loginUser");
			Follow follow=new Follow();
			FollowId followId = new FollowId();
			followId.setUserId(user.getUserId());
			followId.setFansId(fans.getUserId());
			follow.setId(followId);
			followService.delete(follow);
			json.setStatus(true);
			json.setMsg("取消关注成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(false);
			json.setMsg("取消关注失败！");
		}
		return json;
	}
	
	/**
	 * 获取粉丝列表
	 * @param user
	 * @param limit 最大显示行数/页号存在时，变成每页显示行数
	 * @param page	页号	
	 * @return
	 */
	@RequestMapping("/getFansList")
	@ResponseBody
	public Json getFansList(User user,Integer limit,Integer page){
		Json result=new Json();
		try {
			JSONObject pagination= followService.getFansList(user,limit,page);
			
			user=userService.get(user.getUserId());
			JSONObject jUser=new JSONObject();
			jUser.put("username", user.getUsername());
			jUser.put("id", user.getUserId());
			jUser.put("imgPath", user.getImgPath());
			jUser.put("gender", user.getGender());
			jUser.put("fansList", pagination.get("fansList"));
			if (page!=null) {
				jUser.put("page", page);
				jUser.put("rows", limit);
				jUser.put("count", pagination.get("count"));
			}
			result.setData(jUser);
			
			result.setStatus(true);
			result.setMsg("获取粉丝列表成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("获取粉丝列表失败！");
			
		}
		return result;
	}
	
	/**
	 * 获取关注列表
	 * @param user
	 * @return
	 */
	@RequestMapping("/getFollowingList")
	@ResponseBody
	public Json getFollowingList(User user,Integer limit,Integer page){
		Json result=new Json();
		try {
			JSONObject pagination = followService.getFollowingList(user,limit,page);
			JSONObject jUser=new JSONObject();
			user = userService.get(user.getUserId());
			jUser.put("username", user.getUsername());
			jUser.put("id", user.getUserId());
			jUser.put("imgPath", user.getImgPath());
			jUser.put("gender", user.getGender());
			jUser.put("followingList", pagination.get("followingList"));
			if(page!=null){
				jUser.put("page", page);
				jUser.put("rows", limit);
				jUser.put("count", pagination.get("count"));
			}
			result.setData(jUser);
			result.setStatus(true);
			result.setMsg("获取关注列表成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMsg("获取关注列表失败！");
			
		}
		return result;
	}

}
