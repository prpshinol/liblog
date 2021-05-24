package com.liblog.service;

import com.liblog.entity.Admin;
import com.liblog.exception.LoginErrorException;

/**
 * Created by linzhi on 2017/3/13.
 */
public interface IAdminService extends IBaseService<Admin> {
    /**
     * 管理员登陆
     */
    public Admin login(Admin admin) throws LoginErrorException;

    /**
     * 根据用户名查找管理员
     */
    public Admin findByUsername(String username);
}
