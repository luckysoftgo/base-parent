package ${JavaBeanControllerPath};

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.base.core.obj.Pagination;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.UUIDProvider;
import com.application.base.core.common.BaseController;
import com.application.base.core.constant.Constants;
<#if saveLog == "YES">
import com.javabase.system.entity.SystemLog;
import com.javabase.system.entity.SystemUser;
import com.javabase.system.service.SystemLogService;
import com.javabase.system.utils.CommonUtils;
</#if>
import ${JavaBeanPath}.${poName};
import ${JavaBeanServicePath}.${poName}Service;

/**
 * @desc ${poName}Controller实现
 *
 * @author 系统生成
 *
 */

@Controller
@RequestMapping("/background/${firstLowerPoName}/")
public class ${poName}Controller extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ${poName}Service ${firstLowerPoName}Service;
	<#if saveLog == "YES">
	@Autowired
	private SystemLogService systemLogService;
	private SystemUser systemUser;
	</#if>

	@RequestMapping("find")
	public String find(Model model) {
		Map<String, Object> param = getRequestParams(request);
		Pagination<${poName}> resultPage = ${firstLowerPoName}Service.paginationObjects(param, StringDefaultValue.intValue(param.get("pageNo")), StringDefaultValue.intValue(param.get("pageSize")));
		model.addAttribute("pageView", resultPage);
		return "/background/${firstLowerPoName}/list";
	}


	@RequestMapping("findByName")
	public void findByName(Model model, String objName) {
		String data = null;
		try {
			${poName} object = new ${poName}();
			//object.setName(objName);
			object = ${firstLowerPoName}Service.findObjectByPros(object);
			if (object != null) {
				data = "({msg:'Y',content:'该对象名字已经存在,请重新添加!'})";
			}
			else {
				data = "({msg:'N',content:'可以放心使用!'})";
			}
			// 返回.
			ajaxJson(data);
		}
		catch (Exception e) {
			logger.info("查找出错!" + e.getLocalizedMessage());
		}
	}


	@RequestMapping("findByProps")
	public void findByProps(Model model, ${poName} object ) {
		String data = null;
		try {
			object = ${firstLowerPoName}Service.findObjectByPros(object);
			if (object.getId() > 0 ) {
				data = "({msg:'Y',content:'按需求填写!'})";
			}
			else {
				data = "({msg:'N',content:'按需求填写!'})";
			}
			// 返回.
			ajaxJson(data);
		}
		catch (Exception e) {
			logger.info("查找出错!" + e.getLocalizedMessage());
		}
	}

	@RequestMapping("addUI")
	public String addUI(Model model) {
		return "/background/${firstLowerPoName}/add";
	}

	@RequestMapping("addOne")
	public String addOne(Model model, ${poName} object) {
		object.setUuid(UUIDProvider.uuid());
		object.setIsDelete(Constants.DeleteStatus.ENABLED);
		object.setCreateTime(new Date());
		object.setUpdateTime(new Date());
		${firstLowerPoName}Service.saveObject(object);
		<#if saveLog == "YES">
		// 记录日志
		noteOperator("添加");
		</#if>
		return "redirect:find.html";
	}

	@RequestMapping("findById")
	public String findById(Model model, String objId, int type) {
		${poName} object = ${firstLowerPoName}Service.getObjectByID(Integer.parseInt(objId));
		model.addAttribute("object", object);
		if (type == 1) {
			return "/background/${firstLowerPoName}/edit";
		}
		else {
			return "/background/${firstLowerPoName}/show";
		}
	}

	<#if saveLog == "YES">
	@RequestMapping("deleteAll")
	public void deleteAll(Model model, String pks) {
		List<String> primks = CommonUtils.removeSameItem(Arrays.asList(pks.split(",")));
		boolean result = false;
		try {
			for (String pk : primks) {
				${firstLowerPoName}Service.deleteObjectByID(pk);
			}
			result = true;
		}
		catch (Exception e) {
			result = false;
		}

		String data = null;
		if (result) {
			data = "({msg:'Y',content:'删除成功!'})";
		}
		else {
			data = "({msg:'N',content:'删除失败!'})";
		}
		// 记录日志
		noteOperator("删除选择的删除项.");
		// 返回.
		ajaxJson(data);
	}
	</#if>

	@RequestMapping("deleteById")
	public void deleteById(Model model, String objId) {
		int count = ${firstLowerPoName}Service.deleteObjectByID(objId);
		// 记录日志
		String data = null;
		if (count > 0) {
			data = "({msg:'Y',content:'删除成功!'})";
		}
		else {
			data = "({msg:'N',content:'删除失败!'})";
		}
		<#if saveLog == "YES">
		// 记录日志
		noteOperator("删除选择的删除项.");
		</#if>
		// 返回.
		ajaxJson(data);
	}

	@RequestMapping("updateById")
	public String updateById(Model model, ${poName} object) {
		${firstLowerPoName}Service.updateObjectByID(object, object.getId());
		<#if saveLog == "YES">
		noteOperator("修改");
		</#if>
		return "redirect:find.html";
	}

	<#if saveLog == "YES">
	/**
	 * 记录日志
	 *
	 * @param tag:分配,添加,删除,修改
	 */
	public void noteOperator(String tag) {
		// 记录日志
		SystemLog sysLogs = SystemLog.getInstance(SystemLog.class);
		SystemUser tUser = getSysUsers();
		sysLogs.setUserId(tUser.getId());
		sysLogs.setUserName(tUser.getUserName());
		//sysLogs.setModule("系统资源管理"); 		//实际情况填写
		//sysLogs.setAction("系统资源管理——" + tag + "资源"); 	//实际情况填写
		sysLogs.setFromIp(CommonUtils.toIpAddr(request));
		systemLogService.saveObject(sysLogs);
	}

	public SystemUser getSysUsers() {
		if (systemUser == null) {
			systemUser = (SystemUser) request.getSession().getAttribute("userSession");
		}
		return systemUser;
	}
	</#if>
}


