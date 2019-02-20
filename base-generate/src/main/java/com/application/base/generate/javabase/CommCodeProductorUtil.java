package com.application.base.generate.javabase;

import com.application.base.generate.javabase.bin.FileBuilder;
import com.application.base.generate.javabase.bin.GenerateConfig;
import com.application.base.generate.javabase.constant.CommonConstant;
import com.application.base.generate.javabase.utils.GenerateUtils;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.PropertiesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 代码生成器.
 * @author 孤狼
 */
public class CommCodeProductorUtil {
	
	static String YES = "YES";
	
	/**
	 * 生成代码.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		PropertiesUtils prop = new PropertiesUtils();
		List<String> configFilePath = new ArrayList<String>();
		configFilePath.add(CommonConstant.GENERATE_FILE_PATH);
		prop.setUrls(configFilePath);

		String parentName = prop.get("generate.parentName");
		//工程名字.
		String systemName = prop.get("generate.systemName");
		//包.
		String packageName = prop.get("generate.packageName");
		//是否批量生成.
		String generateAll = prop.get("generate.all");
		//包结构.
		String instancePackage = prop.get("generate.instancePackage");
		//主键生成策略.
		String primaryKeyStyle = prop.get("generate.primaryKeyStyle");
		//数据库配置标志.
		String factoryTag = prop.get("generate.factoryTag");
		//是否使用注解事务.
		String useTransactional = prop.get("generate.useTransactional");
		//是否使用缓存.
		String useCache = prop.get("generate.useCache");
		if (BaseStringUtil.isEmpty(useCache)) {
			useCache = YES;
		}
		//包名地址
		String packagePath = prop.get("generate.java.path");
		//对应数据库的名称
		String databaseName = prop.get("generate.databaseName");

		//是操作 APP 还是 JSP 页面
		String forOne = prop.get("generate.forOne");
		if (BaseStringUtil.isEmpty(forOne)) {
			forOne = "APP".toUpperCase();
		}
		//操作日志添加
		String saveLog = prop.get("generate.saveLog");
		if (BaseStringUtil.isEmpty(saveLog)) {
			saveLog = "";
		}
		
		//是否使用多数据
		String moreDbTag = prop.get("generate.moreDbTag");
		if (BaseStringUtil.isEmpty(moreDbTag)) {
			moreDbTag = "";
		}
				
		List<String> list = new ArrayList<String>();
		if (YES.equalsIgnoreCase(generateAll)) {
			// 全库生成
			list = GenerateUtils.getTable();
		} else {
			String tableNames = prop.get("generate.tableNames");
			// 部分生成
			String[] tables = tableNames.split(",");
			for (String table : tables) {
				list.add(table);
			}
		}
		try {
			for (String tableName : list) {
				GenerateConfig config = null;
				if (BaseStringUtil.isEmpty(instancePackage)) {
					 config = new GenerateConfig(databaseName,tableName,parentName,systemName, packageName,primaryKeyStyle,factoryTag,useTransactional,useCache,saveLog,moreDbTag,packagePath);
				}else{
					 config = new GenerateConfig(databaseName,tableName,parentName,systemName, packageName,primaryKeyStyle,instancePackage,factoryTag,useTransactional,useCache,saveLog,moreDbTag,packagePath);
				}
					
				FileBuilder builder = new FileBuilder(config);
				builder.createJavaBeanMapper(); // sqlMapper.
				builder.createJavaBean(); // entityBean.
				builder.createJavaBeanDao(); // entityDao.
				builder.createJavaBeanDaoImpl(); // entityDaoImpl.
				builder.createJavaBeanService(); //entityService.
				builder.createJavaBeanServiceImpl(); //entityServiceImpl.
				
				//controller 对应的设置
				if ("APP".equalsIgnoreCase(forOne)) {
					builder.createAppJavaBeanController(); //appEntityController.
				}else {
					builder.createJspJavaBeanController(); //appEntityController.
				}
				
				/*
				//创建list.jsp页面.
				builder.createListJSPForJavaBean();
				//创建add.jsp页面.
				builder.createAddJSPForJavaBean();
				//创建edit.jsp页面.
				builder.createEditJSPForJavaBean();
				//创建show.jsp页面.
				builder.createShowJSPForJavaBean();
				*/
				
				/**
				 * 添加到权限表中去.
				 */
				/*
				String codeName = "测试数据管理";
			    String codePrefix = "测试数据列表";
				IntoDBInfos.addMenus(tableName, codeName, codePrefix);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成代码.
	 * 
	 * @throws Exception
	 */
	public static void codeGenerate(String databaseName,String tableNames,String parentName,String systemName,String packageName,String generateAll,String instancePackage,String primaryKeyStyle,String factoryTag,String useTransactional,String useCache,String saveLog,String moreDbTag,String packagePath) throws Exception {
		if (BaseStringUtil.isEmpty(useCache)) {
			useCache = YES;
		}
		List<String> list = new ArrayList<String>();
		if (YES.equalsIgnoreCase(generateAll)) {
			// 全库生成
			list = GenerateUtils.getTable();
		} else {
			// 部分生成
			String[] tables = tableNames.split(",");
			for (String table : tables) {
				list.add(table);
			}
		}
		try {
			for (String tableName : list) {
				GenerateConfig config = null;
				if (BaseStringUtil.isEmpty(instancePackage)) {
					 config = new GenerateConfig(databaseName,tableName,parentName,systemName, packageName,primaryKeyStyle,factoryTag,useTransactional,useCache,saveLog,moreDbTag,packagePath);
				}else{
					 config = new GenerateConfig(databaseName,tableName,parentName,systemName, packageName,primaryKeyStyle,instancePackage,factoryTag,useTransactional,useCache,saveLog,moreDbTag,packagePath);
				}
					
				FileBuilder builder = new FileBuilder(config);
				builder.createJavaBeanMapper(); // sqlMapper.
				builder.createJavaBean(); // entityBean.
				builder.createJavaBeanDao(); // entityDao.
				builder.createJavaBeanDaoImpl(); // entityDaoImpl.
				builder.createJavaBeanService(); //entityService.
				builder.createJavaBeanServiceImpl(); //entityServiceImpl.
				builder.createAppJavaBeanController(); //appEntityController.
				builder.createJspJavaBeanController(); //appEntityController.
				
				//创建list.jsp页面.
				builder.createListJSPForJavaBean();
				//创建add.jsp页面.
				builder.createAddJSPForJavaBean();
				//创建edit.jsp页面.
				builder.createEditJSPForJavaBean();
				//创建show.jsp页面.
				builder.createShowJSPForJavaBean();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
