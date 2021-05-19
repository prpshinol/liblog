package com.liblog.service.impl;

import com.liblog.dao.IBaseDao;
import com.liblog.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * BaseService实现，必须配置@Transactional,否则事务配置出错
 * @author 21584
 *
 * @param <T>
 */
@Transactional
public class BaseService<T> implements IBaseService<T> {

    @Resource
    protected IBaseDao<T> baseDao;

    private Class<T> clazz;

    //反射泛型获取泛型参数的实际类型，拼接得到dao名称
    public BaseService() {
        ParameterizedType parentType = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) parentType.getActualTypeArguments()[0];
    }

   /* //初始化，反射设置baseDao为具体的类型
    @PostConstruct
    public void init() {
        try {
            //1.反射获取baseDao域
            Field baseField = this.getClass().getSuperclass().getDeclaredField("baseDao");
            baseField.setAccessible(true);
            //2.反射获取daoName域
            String daoName = clazz.getSimpleName().substring(0, 1).toLowerCase()
                    + clazz.getSimpleName().substring(1)
                    + "Dao";
            Field actualField = clazz.getDeclaredField(daoName);
            actualField.setAccessible(true);
            //3.反射设置属性值
            baseField.set(this, actualField.get(this));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/

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
