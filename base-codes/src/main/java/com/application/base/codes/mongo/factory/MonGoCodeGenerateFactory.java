package com.application.base.codes.mongo.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import com.application.base.utils.common.PropStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.application.base.codes.mongo.bean.CommonPageParser;
import com.application.base.codes.mongo.bean.CreateBean;
import com.application.base.codes.mongo.def.CodeResourceUtil;

public class MonGoCodeGenerateFactory {

	private static final Log logger = LogFactory.getLog(MonGoCodeGenerateFactory.class);
	
	//mongo链接.
	private static String mongoUrl = CodeResourceUtil.URL;
	private static String mongoUserName = CodeResourceUtil.USERNAME;
	private static String mongoPassWord = CodeResourceUtil.PASSWORD;
	//mysql链接.
	private static String mysqlUrl = null;
	private static String mysqlUserName = null;
	private static String mysqlPassWord = null;
	
	private static CreateBean createBean = null;
	private static String buss_package = CodeResourceUtil.bussiPackage;
	private static String projectPath = getProjectPath();
	
	static{
		if (mysqlUrl==null && mysqlUserName==null && mysqlPassWord==null) {
			initMySql();
		}
	}
	
	/**
	 * 初始化MySql信息.
	 */
	public static void initMySql(){
		try {
			 Properties props = PropStringUtils.getProperties("/config/java_generate.properties");
		     mysqlUrl = props.getProperty("mysql.url");
		     mysqlUserName = props.getProperty("mysql.username");
		     mysqlPassWord = props.getProperty("mysql.password");
		     System.out.println("name=" + mysqlUserName);
		     System.out.println("pass=" + mysqlPassWord);
		     System.out.println("url=" + mysqlUrl);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化MySql信息,"+e.getMessage());
		}
	}
	
	/**
	 * 初始化数据bean.
	 */
	private static void initCreateBean(){
		if (createBean==null) {
			createBean = new CreateBean();
			createBean.setMonGoInfo(mongoUrl, mongoUserName, mongoPassWord); //设置数据库连接
			createBean.setMySqlInfo(mysqlUrl, mysqlUserName, mysqlPassWord);
		}
	}
	
	/**
	  * 生成相应代码的地方。
	  * 
	  * @param tableName:表名字.
	  * @param codeName:模块名字.
	  * @param entityPackage:包名后边需要加上"\\".
	  */
	public static void codeGenerate(String tableName,String systemName, String codeName,
			String entityPackage ,String autoType,String writeOrRead) {

		initCreateBean();

		String className = createBean.getTablesNameToClassName(tableName);
		
		String lowerName = className.substring(0, 1).toLowerCase()+ className.substring(1, className.length());

		String srcPath = projectPath + CodeResourceUtil.source_root_package+ "/";

		String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + "/";
		//获得项目路径.
		pckPath = pckPath.replace("base-parent/base-generate", systemName);

		String pagePath = projectPath+CodeResourceUtil.web_root_package+"/WEB-INF/jsp/background/";

		String beanPath = "/entity/"+ entityPackage +"/"+ className + ".java";
		
		String mapperPath = "/dao/"+ entityPackage +"/"+ className + "Dao.java";
		
		String mapperImplPath = "/dao/"+ entityPackage +"/"+  "impl/" + className + "DaoImpl.java";
		
		String servicePath = "/service/"+ entityPackage +"/"+  className + "Service.java";
		
		String serviceImplPath = "/service/"+entityPackage +"/"+ "impl/" + className + "ServiceImpl.java";
		
		String controllerPath = "/web/" + entityPackage +"/"+ className + "Controller.java";
		
		String listSavePath = lowerName+"/list.jsp";
		
		String addSavePath = lowerName+"/add.jsp";
		
		String editSavePath = lowerName+"/edit.jsp";
		
		String showSavePath = lowerName+"/show.jsp";
		
		VelocityContext context = new VelocityContext();
		context.put("className", className);
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		context.put("nameSpaceName", className.toLowerCase());
		context.put("bussPackage", buss_package);
		context.put("entityPackage", entityPackage);
		context.put("keyType", autoType);
		context.put("writeOrRead", writeOrRead.toUpperCase());
		
		//包名的设置.
		
		String basePackage = null;
		if (entityPackage.replace("\"", "\\").contains("\\")) {
			basePackage = entityPackage.replace("\"", "\\").replace("\\", ".").substring(0, entityPackage.length()-1);
			context.put("basePackage", basePackage);
		}else {
			context.put("basePackage", entityPackage);
		}
		
		String basicPackage = buss_package.replaceAll("/", ".");
		System.out.println(basicPackage);
		context.put("baseFilePackage",basicPackage.substring(10, basicPackage.length()));
		
		try {
			context.put("feilds", createBean.getBeanFeilds(tableName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Map sqlMap = createBean.getAutoCreateSql(tableName,lowerName,autoType);
			context.put("SQL", sqlMap);
		} catch (Exception sqlMap) {
			sqlMap.printStackTrace();
			return;
		}

		CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath,beanPath);
		CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath,mapperPath);
		CommonPageParser.WriterPage(context, "DaoImplTemplate.ftl", pckPath,mapperImplPath);
		CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath,servicePath);
		CommonPageParser.WriterPage(context, "ServiceImplTemplate.ftl", pckPath,serviceImplPath);
		CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath,controllerPath);
		
		//界面生成.
		/*
		CommonPageParser.WriterPage(context, "JavaBeanListTemplate.ftl", pagePath,listSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanAddTemplate.ftl", pagePath,addSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanEditTemplate.ftl", pagePath,editSavePath);
		CommonPageParser.WriterPage(context, "JavaBeanShowTemplate.ftl", pagePath,showSavePath);
		*/
		
		//生成住注册的,可控性不好,不做自动生成.		
		logger.info("----------------------------代码生成完毕---------------------------");
		System.out.println("恭喜,项目中的工具类代码生成完成!");
	}

	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	/**
	 * 获得资源表最大的资源id
	 * @return
	 */
	private static int getMaxSid(){
		try {
			Connection connection = createBean.getConnection();
			String sql = "select max(source_id) from sys_sources ";
			Statement stmt= connection.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			if (res.next()) {
				return res.getInt(1);
			}
		}
		catch (Exception e) {
			logger.error("获得最大资源表的id失败!"+e.getMessage());
			return -1;
		}
		return -1;
	}
	
}
