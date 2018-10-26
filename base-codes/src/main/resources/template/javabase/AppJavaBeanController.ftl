package ${JavaBeanControllerPath};

import com.application.base.common.BaseStringUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.application.base.common.web.BaseCommController;
import com.application.base.common.page.Pagination;

import ${JavaBeanPath}.${poName};
import ${JavaBeanServicePath}.${poName}Service;
import java.util.Map;

/**
 * @desc ${poName}Controller实现
 * @author 孤狼
 */
@RestController
@RequestMapping("/${firstLowerPoName}")
public class ${poName}Controller extends BaseCommController {

    @Autowired
    private ${poName}Service ${firstLowerPoName}Service;

    /**
    * 添加对象.
    *
    */
    @RequestMapping(value="/add${poName}",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void add${poName}(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        ${poName} object = null;
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }

    /**
    * 通过主键获得对象.
    *
    */
    @RequestMapping(value="/get${poName}ById",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void get${poName}ById(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        <#if "${primaryKeyType}" == "NUMERIC">
        Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        ${poName} object = null;
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }

    <#if existUuid == "0">
    /**
    * 通过UUId获得对象.
    *
    */
    @RequestMapping(value="/get${poName}ByUUId",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void get${poName}ByUUId(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        ${poName} object = null;
        String resultStr = successResultJSON(object);
        printJsonWriter(response, resultStr);
    }
    </#if>

    /**
    * 通过id修改对象.
    *
    */
    @RequestMapping(value="/update${poName}ById",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void update${poName}ById(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        <#if "${primaryKeyType}" == "NUMERIC">
        Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        printJsonWriter(response, successResultJSON("通过Id="+${tablePKVal}+"修改成功"));
    }

    <#if existUuid == "0">
    /**
    * 通过uuid修改对象.
    *
    */
    @RequestMapping(value="/update${poName}ByUUId",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void update${poName}ByUUId(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        printJsonWriter(response, successResultJSON("通过UUId="+uuid+"修改成功"));
    }
    </#if>

    /**
    * 通过Id删除消息
    *
    */
    @RequestMapping(value="/del${poName}ById",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void del${poName}ById(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        <#if "${primaryKeyType}" == "NUMERIC">
        Integer ${tablePKVal} = BaseStringUtil.intValue(param.get("${tablePKVal}"));
        <#else>
        String ${tablePKVal} = BaseStringUtil.stringValue(param.get("${tablePKVal}"));
        </#if>
        //根据实际情况去验证 objId 的类型的合法性。
        printJsonWriter(response, successResultJSON("通过Id="+${tablePKVal}+"删除成功"));
    }

    <#if existUuid == "0">
    /**
    * 通过UUId删除消息
    *
    */
    @RequestMapping(value="/del${poName}ByUUId",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void del${poName}ByUUId(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        String uuid = BaseStringUtil.stringValue(param.get("uuid"));
        printJsonWriter(response, successResultJSON("通过UUId="+uuid+"删除成功"));
    }
    </#if>

    /**
    * 获得所有消息
    *
    */
    @RequestMapping(value="/get${poName}s",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void get${poName}s(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        printJsonWriter(response, successResultJSON(""));
    }

    /**
    * 分页获得消息
    *
    */
    @RequestMapping(value="/get${poName}sByPage",method = { RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public void get${poName}sByPage(@RequestBody Map<String,Object> param) {
        // 根据实际情况填写.
        int pageNo = BaseStringUtil.intValue(param.get(PAGE_NO));
        int pageSize = BaseStringUtil.intValue(param.get(PAGE_SIZE));
        Pagination<${poName}> result = null;
        printJsonWriter(response, successResultJSON(result));
    }

}


