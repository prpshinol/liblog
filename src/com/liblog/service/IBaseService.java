package com.liblog.service;

import java.io.Serializable;

/**
 * IBaseService,封装了逻辑简单的通用业务方法
 *
 * @param <T>
 * @author 21584
 */
public interface IBaseService<T> {
    //---------session的API-------------
    public Serializable save(T t);

    public void delete(T t);

    public void update(T t);

    public void saveOrUpdate(T t);

    public T get(Serializable id);

}
