package com.application.base.core.apisupport.impl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.base.core.datasource.dao.BaseSystemDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.application.base.utils.page.PageView;

/**
 * 集合持久层的公用的增，删，改，查类
 * <T> 表示传入实体类
 * @author 孤狼
 * @version 1.0v
 * @param <T>
 */
public class BaseSystemDaoImpl<T> extends SqlSessionDaoSupport implements BaseSystemDao<T> {
	
	/**
	 * 
	 * 获取传过来的泛型类名字
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClassName(){
		//在父类中得到子类声明的父类的泛型信息  
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		return clazz.getSimpleName().toString().toLowerCase();
	}
	
	int mapSize =16;
	
	@Override
	public T findById(String pk){
		return getSqlSession().selectOne(this.getClassName()+".findById",pk); 
	}
	
	@Override
	public T findByName(String objName) {
		return getSqlSession().selectOne(this.getClassName()+".findByName",objName); 
	}
	
	@Override
	public T findByProps(T t) {
		return getSqlSession().selectOne(this.getClassName()+".findByProps",t); 
	}
	
	@Override
	public List<T> find(PageView pageView, T t) {
		Map<Object, Object> map = new HashMap<Object, Object>(mapSize);
		map.put("paging", pageView);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName()+".find",map);  
	}
	
	@Override
	public List<T> findAll() {
		return getSqlSession().selectList(this.getClassName()+".findAll"); 
	}
	
	@Override
	public List<T> findAllByPros(T t) {
		Map<Object, Object> map = new HashMap<Object, Object>(mapSize);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName()+".findAllByPros",map); 
	}
	
	@Override
	public int addOne(T t) {
		return getSqlSession().insert(this.getClassName()+".addOne",t); 
	}
	
	@Override
	public boolean addAll(List<T> ts) {
		int count =  getSqlSession().insert(this.getClassName()+".addAll",ts); 
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int updateOne(T t) {
		return getSqlSession().update(this.getClassName()+".updateOne",t);  
	}
	
	@Override
	public boolean updateAll(List<T> ts) {
		int count = getSqlSession().update(this.getClassName()+".updateAll",ts);  
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int deleteById(String pk) {
		return getSqlSession().delete(this.getClassName()+".deleteById",pk); 
	}
	
	@Override
	public boolean deleteAll(List<String> pks) {
		int count = getSqlSession().delete(this.getClassName()+".deleteAll",pks); 
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int getObjsCount() {
		return getSqlSession().selectOne(this.getClassName()+".getObjsCount"); 
	}
	
	@Override
	public int getObjsByProsCount(T t) {
		return getSqlSession().selectOne(this.getClassName()+".getObjsByProsCount",t); 
	}

}
