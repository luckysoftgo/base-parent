package com.application.base.core.apisupport.impl;

import com.application.base.core.datasource.dao.BaseSystemDao;
import com.application.base.utils.page.PageView;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		return getSqlSession().selectOne(this.getClassName()+"."+OperateMethodName.FINDBYID.getName(),pk);
	}
	
	@Override
	public T findByName(String objName) {
		return getSqlSession().selectOne(this.getClassName()+"."+OperateMethodName.FINDBYNAME.getName(),objName);
	}
	
	@Override
	public T findByProps(T t) {
		return getSqlSession().selectOne(this.getClassName()+"."+OperateMethodName.FINDBYPROPS.getName(),t);
	}
	
	@Override
	public List<T> find(PageView pageView, T t) {
		Map<Object, Object> map = new HashMap<Object, Object>(mapSize);
		map.put("paging", pageView);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName()+"."+OperateMethodName.FIND.getName(),map);
	}
	
	@Override
	public List<T> findAll() {
		return getSqlSession().selectList(this.getClassName()+"."+OperateMethodName.FINDALL.getName());
	}
	
	@Override
	public List<T> findAllByPros(T t) {
		Map<Object, Object> map = new HashMap<Object, Object>(mapSize);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName()+"."+OperateMethodName.FINDALLBYPROS.getName(),map);
	}
	
	@Override
	public int addOne(T t) {
		return getSqlSession().insert(this.getClassName()+"."+OperateMethodName.ADDONE.getName(),t);
	}
	
	@Override
	public boolean addAll(List<T> ts) {
		int count =  getSqlSession().insert(this.getClassName()+"."+OperateMethodName.ADDALL.getName(),ts);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int updateOne(T t) {
		return getSqlSession().update(this.getClassName()+"."+OperateMethodName.UPDATEONE.getName(),t);
	}
	
	@Override
	public boolean updateAll(List<T> ts) {
		int count = getSqlSession().update(this.getClassName()+"."+OperateMethodName.UPDATEALL.getName(),ts);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int deleteById(String pk) {
		return getSqlSession().delete(this.getClassName()+"."+OperateMethodName.DELETEBYID.getName(),pk);
	}
	
	@Override
	public boolean deleteAll(List<String> pks) {
		int count = getSqlSession().delete(this.getClassName()+"."+OperateMethodName.DELETEALL.getName(),pks);
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public int getObjsCount() {
		return getSqlSession().selectOne(this.getClassName()+"."+OperateMethodName.GETOBJSCOUNT.getName());
	}
	
	@Override
	public int getObjsByProsCount(T t) {
		return getSqlSession().selectOne(this.getClassName()+"."+OperateMethodName.GETOBJSBYPROSCOUNT.getName(),t);
	}

}
