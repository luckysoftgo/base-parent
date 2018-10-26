package ${baseFilePackage}.dao.${entityPackage}.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.application.base.mongo.spring.api.BaseQueryService;
import com.application.base.utils.page.PageView;
import ${baseFilePackage}.dao.${entityPackage}.${className}Dao;
import ${baseFilePackage}.entity.${entityPackage}.${className};
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 接口实现.
 * @author 孤狼
 */
@Service("${lowerName}Dao")
public class ${className}DaoImpl extends BaseQueryService<${className}> implements ${className}Dao{
	
	private Logger logger = LoggerFactory.getLogger(${className}DaoImpl.class.getName());

	#if(${writeOrRead}=='Y')
	
	@Autowired
	private MongoTemplate mongoTemplateRead;
	@Autowired
	private MongoTemplate mongoTemplateWrite;
	#else
	
	@Autowired
	private MongoTemplate mongoTemplate;
	#end
	

	@SuppressWarnings("rawtypes")
	public Class getClassName() {
		return ${className}.class;
	}

	@SuppressWarnings("unchecked")
	public ${className} findObjById(Object id) {
		try {
		  #if(${writeOrRead}=='Y')
		  
			 return (${className}) this.mongoTemplateRead.findById(id, getClassName());
		  #else
		   
			 return (${className}) this.mongoTemplate.findById(id, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("添加一个对象到mongodb中去失败,error=" + e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ${className} findObjByName(String proKey, String proValue) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(proKey).is(proValue));
		  #if(${writeOrRead}=='Y')
		  
		  	return (${className}) this.mongoTemplateRead.findOne(query, getClassName());
		  #else
		   
		  	return (${className}) this.mongoTemplate.findOne(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过key=" + proKey + ",value=" + proValue + "查找一个Document对象失败.");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ${className} findObjByProps(${className} t) {
		try {
			Query query = buildBaseQuery(t, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return (${className}) this.mongoTemplateRead.findOne(query, getClassName());
		  #else
		   
		  	return (${className}) this.mongoTemplate.findOne(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过属性查找对象失败,", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ${className} findObjByProps(Map<String, Object> params) {
		try {
			Query query = buildBaseQuery(params, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return (${className}) this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	return (${className}) this.mongoTemplate.find(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过属性查找对象失败,", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<${className}> findObjList(${className} t) {
		try {
			Query query = buildBaseQuery(t, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return  this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	return  this.mongoTemplate.find(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("查找List对象失败了", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<${className}> findObjList(Map<String, Object> params) {
		try {
			Query query = buildBaseQuery(params, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return  this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	return  this.mongoTemplate.find(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过属性查找List失败了", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PageView findObjsByPage(PageView pageView, ${className} t) {
		try {
			int pageNow = pageView.getPageNow();
			int startIndex = 0;
			int pageSize = pageView.getPageSize();
			if (pageNow == 1) startIndex = 0;
			else {
				startIndex = (pageNow - 1) * pageView.getPageSize();
			}
			Query query = buildBaseQuery(t, "eq", null);
			query.skip(startIndex);
			query.limit(pageSize);
			List<${className}> list = null;
		  #if(${writeOrRead}=='Y')
		  
		  	list = this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	list = this.mongoTemplate.find(query, getClassName());
		  #end
		  
			pageView.setRecords(list);
			pageView.setRowCount(getObjsByProsCount(t));
			return pageView;
			
		}
		catch (Exception e) {
			this.logger.error("分页查找对象失败了", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PageView findObjsByPage(PageView pageView, Map<String, Object> params) {
		try {
			int pageNow = pageView.getPageNow();
			int startIndex = 0;
			int pageSize = pageView.getPageSize();
			if (pageNow == 1) startIndex = 0;
			else {
				startIndex = (pageNow - 1) * pageView.getPageSize();
			}
			Query query = buildBaseQuery(params, "eq", null);
			query.skip(startIndex);
			query.limit(pageSize);
			List<${className}> list = null;
		  #if(${writeOrRead}=='Y')
		  
		  	list = this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	list = this.mongoTemplate.find(query, getClassName());
		  #end
		  
			pageView.setRecords(list);
			pageView.setRowCount(getObjsByProsCount(params));
			return pageView;
		}
		catch (Exception e) {
			this.logger.error("分页查找对象失败了", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<${className}> findObjAll() {
		try {
			Query query = new Query();
		  #if(${writeOrRead}=='Y')
		  
		  	return  this.mongoTemplateRead.find(query, getClassName());
		  #else 
		  
		  	return  this.mongoTemplate.find(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("查找所有失败了", e);
			return null;
		}
	}
	
	public boolean addObjOne(${className} obj) {
		try {
		  #if(${writeOrRead}=='Y')
		  
		  	this.mongoTemplateWrite.insert(obj);
		  #else 
		  
		  	this.mongoTemplate.insert(obj);
		  #end
		  
			return true;
		}
		catch (Exception e) {
			this.logger.error("保存对象失败了", e);
			return false;
		}
	}

	public boolean addObjAll(List<${className}> ts) {
		try {
		  #if(${writeOrRead}=='Y')
		  
		  	this.mongoTemplateWrite.insertAll(ts);
		  #else 
		  
		  	this.mongoTemplate.insertAll(ts);
		  #end
		  
			return true;
		}
		catch (Exception e) {
			this.logger.error("添加所有失败了", e);
			return false;
		}
	}

	public int updateObjOne(${className} t, Object id) {
		try {
			Query query = new Query(new Criteria("_id").is(id));
			Update update = buildBaseUpdate(t);
			WriteResult result = null;
		  #if(${writeOrRead}=='Y')
		  
		  	result = this.mongoTemplateWrite.updateFirst(query, update, getClassName());
		  #else 
		  
		  	result = this.mongoTemplate.updateFirst(query, update, getClassName());
		  #end
		  
			if (result != null) {
				return 1;
			}
			return -1;
		}
		catch (Exception e) {
			this.logger.error("修改对象失败了", e);
			return -1;
		}
	}

	public int updateObjOne(Map<String, Object> params, Object id) {
		try {
			Query query = new Query(new Criteria("_id").is(id));
			Update update = buildBaseUpdate(params);
			WriteResult result = null;
		  #if(${writeOrRead}=='Y')
		  
		  	result = this.mongoTemplateWrite.updateFirst(query, update, getClassName());
		  #else 
		  
		  	result = this.mongoTemplate.updateFirst(query, update, getClassName());
		  #end
		  
			if (result != null) {
				return 1;
			}
			return -1;
		}
		catch (Exception e) {
			this.logger.error("修改对象失败了", e);
			return -1;
		}
	}

	public boolean updateObjAll(List<Map<String, Object>> ts, List<Object> ids) {
		try {
			boolean result = true;
			int i = 0;
			for (int  j = 0; i < ids.size(); j++) {
				Object id = ids.get(i);
				Map<String, Object> param = ts.get(j);
				updateObjOne(param, id);
				i++;
			}
			return result;
		}
		catch (Exception e) {
			this.logger.error("修改所有对象失败了", e);
			return false;
		}
	}

	public boolean deleteByObjId(Object id) {
		try {
			Query query = new Query(new Criteria("_id").is(id));
		  #if(${writeOrRead}=='Y')
		  
		  	this.mongoTemplateWrite.remove(query, getClassName());
		  #else 
		  
		  	this.mongoTemplate.remove(query, getClassName());
		  #end
		  
			return true;
		}
		catch (Exception e) {
			this.logger.error("删除对象失败了,id=" + id);
			return false;
		}
	}

	public boolean deleteAll(List<${className}> ts) {
		try {
			for (${className} t : ts) {
				Object id = t.get_id();
				deleteByObjId(id);
			}
			return true;
		}
		catch (Exception e) {
			this.logger.error("删除所有失败了", e);
			return false;
		}
	}

	public long getObjsCount() {
		try {
		  #if(${writeOrRead}=='Y')
		  
		  	return this.mongoTemplateWrite.count(new Query(), getClassName());
		  #else 
		  
		  	return this.mongoTemplate.count(new Query(), getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("查找所有记录失败了", e);
			return 0L;
		}
	}

	public long getObjsByProsCount(${className} t) {
		try {
			Query query = buildBaseQuery(t, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return this.mongoTemplateWrite.count(query, getClassName());
		  #else 
		  
		  	return this.mongoTemplate.count(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过属性查找对象失败了", e);
			return 0L;
		}
	}

	public long getObjsByProsCount(Map<String, Object> params) {
		try {
			Query query = buildBaseQuery(params, "eq", null);
		  #if(${writeOrRead}=='Y')
		  
		  	return this.mongoTemplateWrite.count(query, getClassName());
		  #else 
		  
		  	return this.mongoTemplate.count(query, getClassName());
		  #end
		  
		}
		catch (Exception e) {
			this.logger.error("通过属性查找对象失败了", e);
			return 0L;
		}
	}
}
