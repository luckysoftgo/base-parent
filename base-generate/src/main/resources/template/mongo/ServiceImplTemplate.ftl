package ${baseFilePackage}.service.${entityPackage}.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${baseFilePackage}.entity.${entityPackage}.${className};
import ${baseFilePackage}.dao.${entityPackage}.${className}Dao;
import ${baseFilePackage}.service.${entityPackage}.${className}Service;
import com.application.base.utils.page.PageView;

/**
 * 接口定义实现
 * @author 孤狼
 */
@Service("${lowerName}Service")
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
	private ${className}Dao ${lowerName}Dao;

	public ${className} findObjById(Object id) {
		return ${lowerName}Dao.findObjById(id);
	}

	public ${className} findObjByName(String proKey, String proValue) {
		return ${lowerName}Dao.findObjByName(proKey, proValue);
	}

	public ${className} findObjByProps(Map<String, Object> params) {
		return ${lowerName}Dao.findObjByProps(params);
	}

	public List<${className}> findObjList(Map<String, Object> params) {
		return ${lowerName}Dao.findObjList(params);
	}

	public PageView findObjsByPage(PageView pageView, Map<String, Object> params) {
		return ${lowerName}Dao.findObjsByPage(pageView, params);
	}

	public List<${className}> findObjAll() {
		return ${lowerName}Dao.findObjAll();
	}

	public boolean addObjOne(${className} obj) {
		return ${lowerName}Dao.addObjOne(obj);
	}

	public boolean addObjAll(List<${className}> ts) {
		return ${lowerName}Dao.addObjAll(ts);
	}

	public int updateObjOne(Map<String, Object> params, Object id) {
		return ${lowerName}Dao.updateObjOne(params, id);
	}

	public boolean updateObjAll(List<Map<String, Object>> ts, List<Object> ids) {
		return ${lowerName}Dao.updateObjAll(ts, ids);
	}

	public boolean deleteByObjId(Object id) {
		return ${lowerName}Dao.deleteByObjId(id);
	}

	public boolean deleteAll(List<${className}> ts) {
		return ${lowerName}Dao.deleteAll(ts);
	}

	public long getObjsCount() {
		return ${lowerName}Dao.getObjsCount();
	}

	public long getObjsByProsCount(Map<String, Object> params) {
		return ${lowerName}Dao.getObjsByProsCount(params);
	}

	public ${className} findObjByProps(${className} t) {
		return ${lowerName}Dao.findObjByProps(t);
	}

	public List<${className}> findObjList(${className} t) {
		return ${lowerName}Dao.findObjList(t);
	}

	public PageView findObjsByPage(PageView pageView, ${className} t) {
		return ${lowerName}Dao.findObjsByPage(pageView, t);
	}

	public int updateObjOne(${className} t, Object id) {
		return ${lowerName}Dao.updateObjOne(t, id);
	}

	public long getObjsByProsCount(${className} t) {
		return ${lowerName}Dao.getObjsByProsCount(t);
	}

}
