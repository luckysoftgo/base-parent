package ${JavaBeanControllerPath};

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.application.base.utils.page.Pagination;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.core.common.BaseController;


import ${JavaBeanPath}.${poName};
import ${JavaBeanServicePath}.${poName}Service;

/**
 * ${poName}Controller实现
 * @author 孤狼
 */
@RestController
@RequestMapping("/${firstLowerPoName}")
public class ${poName}Controller extends BaseController {
	
	@Autowired
	private ${poName}Service ${firstLowerPoName}Service;

	/**
     * 添加对象.
     *
     * @param request
     * @param response
     */
    @RequestMapping("/add${poName}")
    public void add${poName}(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,null,null);
        ${poName} object = ${firstLowerPoName}Service.saveObject(param);
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }
    
    /**
     * 通过主键获得对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}ById")
    public void get${poName}ById(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}");
        <#if "${primaryKeyType}" == "NUMERIC">
		Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        ${poName} object = ${firstLowerPoName}Service.getObjectById(${tablePKVal});
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }
    
    <#if existUuid == "0">
    /**
     * 通过UUId获得对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}ByUUId")
    public void get${poName}ByUUId(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"uuid");
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        ${poName} object = ${firstLowerPoName}Service.getObjectByUUId(uuid);
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }
    </#if> 
    
    /**
     * 通过id修改对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/update${poName}ById")
    public void update${poName}ById(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}");
        <#if "${primaryKeyType}" == "NUMERIC">
		Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        ${firstLowerPoName}Service.updateObjectById(param, ${tablePKVal});
        printJsonWriter(response, successResultJSON("通过Id="+${tablePKVal}+"修改成功"));
    }
    
    <#if existUuid == "0">
    /**
     * 通过uuid修改对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/update${poName}ByUUId")
    public void update${poName}ByUUId(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"uuid");
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        ${firstLowerPoName}Service.updateObjectByUUId(param, uuid);
        printJsonWriter(response, successResultJSON("通过UUId="+uuid+"修改成功"));
    }
    </#if> 
    
    /**
     * 通过Id删除消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/del${poName}ById")
    public void del${poName}ById(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}");
        <#if "${primaryKeyType}" == "NUMERIC">
		Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        ${firstLowerPoName}Service.deleteObjectById(${tablePKVal});
        printJsonWriter(response, successResultJSON("通过Id="+${tablePKVal}+"删除成功"));
    }
    
    <#if existUuid == "0">
    /**
     * 通过UUId删除消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/del${poName}ByUUId")
    public void del${poName}ByUUId(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,"uuid");
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        ${firstLowerPoName}Service.deleteObjectByUUId(uuid);
        printJsonWriter(response, successResultJSON("通过UUId="+uuid+"删除成功"));
    }
    </#if> 
    
    /**
     * 获得所有消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}s")
    public void get${poName}s(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
        Map<String, Object> param = nullAbleValidation(request,null,null);
        String resultStr = successResultJSON(${firstLowerPoName}Service.findObjectByPros(param));
        printJsonWriter(response, resultStr);
    }
    
    /**
     * 分页获得消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}sByPage")
    public void get${poName}sByPage(HttpServletRequest request, HttpServletResponse response) {
        // 根据实际情况填写.
      	Map<String, Object> param = nullAbleValidation(request,null,null);
        int pageNo = BaseStringUtil.intValue(param.get(PAGE_NO));
        int pageSize = BaseStringUtil.intValue(param.get(PAGE_SIZE));
        Pagination<${poName}> result = ${firstLowerPoName}Service.paginationObjects(param, pageNo, pageSize);
        printJsonWriter(response, successResultJSON(result));
    }
    
}


