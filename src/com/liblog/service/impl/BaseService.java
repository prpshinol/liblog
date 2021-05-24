package com.liblog.service.impl;

import com.liblog.dao.IBaseDao;
import com.liblog.entity.*;
import com.liblog.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * BaseService实现，必须配置@Transactional,否则事务配置出错
 *
 * @param <T>
 * @author 21584
 */
@Transactional
public class BaseService<T> implements IBaseService<T> {

    @Resource
    private IBaseDao<T> baseDao;

    private Class<T> clazz;

    //反射泛型获取泛型参数的实际类型
    public BaseService() {
        ParameterizedType parentType = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) parentType.getActualTypeArguments()[0];
    }

    @Override
    public Serializable save(T t) {
        return baseDao.save(t);
    }

    @Override
    public void delete(T t) {
        baseDao.delete(t);
    }

    @Override
    public void update(T t) {
        baseDao.delete(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        baseDao.saveOrUpdate(t);
    }

    @Override
    public T get(Serializable id) {
        return baseDao.get(clazz, id);
    }

}
