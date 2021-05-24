package com.liblog.util;


import com.liblog.service.IUserService;

public class ServiceUtil {

	private static IUserService userService;
	
	public static IUserService getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService userService) {
		ServiceUtil.userService = userService;
	}

}
