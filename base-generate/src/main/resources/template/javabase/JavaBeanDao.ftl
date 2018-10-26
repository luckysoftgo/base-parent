package ${JavaBeanDaoPath};

import ${JavaBeanPath}.${poName};
<#if moreDbTag == "YES">
import com.application.base.core.datasource.dao.MultiStrutsBaseDao;
<#else>
import com.application.base.core.datasource.dao.SingleStrutsBaseDao;
</#if>

/**
 * ${poName}实体
 * @author 孤狼
 */
<#if moreDbTag == "YES">
public interface ${poName}Dao extends MultiStrutsBaseDao<${poName}> {
<#else>
public interface ${poName}Dao extends SingleStrutsBaseDao<${poName}> {
</#if>
	
	//可以填写自己需要的 DAO 方法.
	
	//要是查询 VO 对象, 则到 CustomerSQL.xml 文件中去写对应的SQL,并在此 DAO 中进行申明,在 DAOImpl中去实现就好。
	
}