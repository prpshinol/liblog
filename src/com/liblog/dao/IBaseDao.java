package com.liblog.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 参数都使用Object[]的形式，如果需要后面可添加Map<String,Object>(命名参数)的版本
 * @author 21584
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	
	//---------session的API-------------
	public Serializable save(T t);
	
	public void delete(T t);
	
	public void update(T t);

	public void saveOrUpdate(T t);

	public T get(Class<T> clazz, Serializable id);

	//--------hql查询--------------------
	public T get(String hql);

	public T get(String hql, Object[] params);

	public List<T> find(String hql);
	
	public List<T> find(String hql, Object[] params);

	public List<T> find(String hql, int page, int rows);
		
	public List<T> find(String hql, Object[] params, int page, int rows);
	
	public List<T> findByMax(String hql, Object[] params, int max);
	
/*	//-------hql分页查询----------------------
	public Pagination<T> findPagination(String hql, int page, int rows);

	public Pagination<T> findPagination(String hql, Object[] params, int page, int rows);*/
	
	//------hql统计----------------------
	public Long count(String hql);

	public Long count(String hql, Object[] params);
	
	//------hql更新----------------------
	public int executeHql(String hql);
	public int executeHql(String hql, Object[] params); 
	
	//-----sql查询-----------------------
	public List<Object[]> findBySql(String sql);

	public List<Object[]> findBySql(String sql, int page, int rows);

	public List<Object[]> findBySql(String sql, Object[] params);

	public List<Object[]> findBySql(String sql, Object[] params, int page, int rows);
	
	/*//-----sql分页------------------------
	public <K> Pagination<K> findSqlPagination(String querySql, String countSql, int page, int rows);
	
	public <K> Pagination<K> findSqlPagination(String querySql, String countSql, Object[] params, int page, int rows);
*/
	public int executeSql(String sql);

	public int executeSql(String sql, Object[] params);

	public Long countBySql(String sql);

	public Long countBySql(String sql, Object[] params);
}
