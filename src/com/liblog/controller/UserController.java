package com.liblog.controller;

import com.liblog.entity.User;
import com.liblog.service.IUserService;
import com.liblog.util.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;

	/**
	 * 注册界面
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Json list(){
		User u = new User();
		User user = userService.get(1);
		u.setUserId(user.getUserId());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		Json json = new Json();
		json.setData(u);
		json.setStatus(true);
		return json;
	}
	

}
