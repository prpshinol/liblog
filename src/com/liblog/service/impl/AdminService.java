package com.liblog.service.impl;

import com.liblog.dao.IBaseDao;
import com.liblog.entity.Admin;
import com.liblog.exception.LoginErrorException;
import com.liblog.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by linzhi on 2016/12/15.
 */
@Service
public class AdminService extends BaseService<Admin> implements IAdminService {
    @Resource
    private IBaseDao<Admin> adminDao;

    @Override
    public Admin login(Admin admin) throws LoginErrorException {
        //1.判读用户名是否存在
        Admin adminReturn = this.findByUsername(admin.getUsername());
        if (adminReturn==null) {
            throw new LoginErrorException("管理员不存在!");
        }
        //2.判读密码是否正确
        if (!adminReturn.getPassword().equals(admin.getPassword())) {
            throw new LoginErrorException("密码不正确!");
        }
        return adminReturn;
    }

    @Override
    public Admin findByUsername(String username) {
        String hql="from Admin a where a.username=?";
        List<Admin> result = adminDao.find(hql, new Object[]{username});
        if (result!=null&&result.size()>0) {
            return result.get(0);
        }
        return null;
    }

}
