package com.application.base.generate.javabase.bin;

import com.application.base.generate.javabase.constant.CommonConstant;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.Constants;
import com.application.base.utils.common.FileUtils;
import com.application.base.utils.freemaker.FreeMarkerUtils;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 文件生成对象
 * @author 孤狼
 */
public class FileBuilder {
	
	/**
	 * java文件相对目录
	 */
	public static String COMMON_JAVA_PATH = CommonConstant.COMMON_JAVA_PATH;
	
	/**
	 * jsp 文件相对目录
	 */
	public static String JSP_JAVA_PATH = CommonConstant.JSP_JAVA_PATH;
	
	/**
	 * 资源文件相对目录
	 */
	public static String COMMON_RESOURCES_PATH = CommonConstant.COMMON_RESOURCES_PATH;
    
    /** sqlMap 生成的文件路径*/
    public static  String SQLMAP_FILE_PATH = "SQLMAP_FILE_PATH";
    /** Bean 生成的文件路径*/
    public static  String BEAN_FILE_PATH = "BEAN_FILE_PATH";
    
    /** Dao 生成的文件路径*/
    public static  String DAO_FILE_PATH = "DAO_FILE_PATH";
    
    /** DaoImpl 生成的文件路径*/
    public static  String DAO_IMPL_FILE_PATH = "DAO_IMPL_FILE_PATH";
    
    /** Service 生成的文件路径*/
    public static  String SERVICE_FILE_PATH = "SERVICE_FILE_PATH";
    
    /** ServiceImpl 生成的文件路径*/
    public static  String SERVICE_IMPL_FILE_PATH = "SERVICE_IMPL_FILE_PATH";
    
    /** Controller 生成的文件路径*/
    public static  String CONTROLLER_FILE_PATH = "CONTROLLER_FILE_PATH";
    
    /** list jsp 页面生成的文件路径*/
    public static  String LIST_JSP_FILE_PATH = "LIST_JSP_FILE_PATH";
    /** add jsp 页面生成的文件路径*/
    public static  String ADD_JSP_FILE_PATH = "ADD_JSP_FILE_PATH";
    /** edit jsp 页面生成的文件路径*/
    public static  String EDIT_JSP_FILE_PATH = "EDIT_JSP_FILE_PATH";
    /** show jsp 页面生成的文件路径*/
    public static  String SHOW_JSP_FILE_PATH = "SHOW_JSP_FILE_PATH";
    
    public static Map<String,String> filePathMap = new HashMap<String, String>();
    
    /**相对于在项目跟路径下文件夹*/
    public static  String TEMPLATE_ROOT = CommonConstant.TEMPLATE_ROOT ;
	
	/**
	 * SQLMAP 模板数据
	 */
	public static  String TEMPLATE_SQL = CommonConstant.TEMPLATE_SQL;
	
	/**
	 * BEAN 模板数据
	 */
	public static  String TEMPLATE_BEAN = CommonConstant.TEMPLATE_BEAN;
	
	/**
	 * DAO 模板数据
	 */
	public static  String TEMPLATE_DAO = CommonConstant.TEMPLATE_DAO;
	
	/**
	 * DAO_IMPL 模板数据
	 */
	public static  String TEMPLATE_DAO_IMPL = CommonConstant.TEMPLATE_DAO_IMPL;
	
	/**
	 * SERVICE 模板数据
	 */
	public static  String TEMPLATE_SERVICE = CommonConstant.TEMPLATE_SERVICE;
	
	/**
	 * SERVICE_IMPL 模板数据
	 */
	public static  String TEMPLATE_SERVICE_IMPL = CommonConstant.TEMPLATE_SERVICE_IMPL;
	
	/**
	 * CONTROLLER 模板数据
	 */
	public static  String TEMPLATE_APP_CONTROLLER = CommonConstant.TEMPLATE_APP_CONTROLLER;
	
	/**
	 * CONTROLLER 模板数据
	 */
	public static  String TEMPLATE_JSP_CONTROLLER = CommonConstant.TEMPLATE_JSP_CONTROLLER;
	
	/**
	 * 根目录设置
	 */
	public static String COM_DIR = CommonConstant.COM_DIR;
	
	/**
	 * list jsp 模板数据
	 */
	public static  String LIST_JSP = CommonConstant.LIST_JSP;
	/**
	 * add jsp 模板数据
	 */
	public static  String ADD_JSP = CommonConstant.ADD_JSP;
	/**
	 * edit jsp 模板数据
	 */
	public static  String EDIT_JSP = CommonConstant.EDIT_JSP;
	/**
	 * show jsp 模板数据
	 */
	public static  String SHOW_JSP = CommonConstant.SHOW_JSP;
    
    static String NUMERIC="NUMERIC";
    
    /**斜杠*/
    public static String SLASH = "/";
    /**点*/
    public static String DOT=".";
	
	/**
	 * 主要配置信息设置
	 */
	private GenerateConfig config;

    public FileBuilder(GenerateConfig config) {
        this.config = config;
        initFilePath();
    }

    private void initFilePath(){
    	
        //项目子系统
        if(!BaseStringUtil.isEmpty(config.getSystemName())){
	        String tempPath= config.getPackagePath();
	        if (!StringUtils.isEmpty(tempPath)){
		        COMMON_JAVA_PATH=tempPath;
	        }
        	String interfaceProjectRoot = config.getWorkspacesPath() + config.getSystemName() + COMMON_JAVA_PATH + config.getPackageName() + Constants.Separator.SLASH;
            
        	String jspProjectRoot = config.getWorkspacesPath() + config.getSystemName() + JSP_JAVA_PATH ; 
        	
        	//sqlmap文件路径
            filePathMap.put(SQLMAP_FILE_PATH, config.getWorkspacesPath() + config.getSystemName() + COMMON_RESOURCES_PATH + CommonConstant.COMMON_MAPPER_PATH + config.getPoName() + Constants.FileType.XML_SUFFIX);
            
            if (!BaseStringUtil.isEmpty(config.getInstancePackage())) {
            	 //Bean文件路径
                filePathMap.put(BEAN_FILE_PATH,  interfaceProjectRoot +"entity"+Constants.Separator.SLASH + config.getInstancePackage()+Constants.Separator.SLASH + config.getPoName() + Constants.FileType.JAVA_SUFFIX);
                
            	//Dao文件路径
                filePathMap.put(DAO_FILE_PATH,  interfaceProjectRoot +"dao"+Constants.Separator.SLASH + config.getInstancePackage()+Constants.Separator.SLASH + config.getPoName()+"Dao" + Constants.FileType.JAVA_SUFFIX);
                
                //DaoImpl文件路径
                filePathMap.put(DAO_IMPL_FILE_PATH,  interfaceProjectRoot +"dao"+Constants.Separator.SLASH + config.getInstancePackage()+Constants.Separator.SLASH +"impl"+Constants.Separator.SLASH + config.getPoName()+"DaoImpl" + Constants.FileType.JAVA_SUFFIX);
                
                //Service文件路径
                filePathMap.put(SERVICE_FILE_PATH,  interfaceProjectRoot +"service"+Constants.Separator.SLASH  + config.getInstancePackage()+Constants.Separator.SLASH + config.getPoName()+"Service" + Constants.FileType.JAVA_SUFFIX);
                
                //ServiceImpl文件路径
                filePathMap.put(SERVICE_IMPL_FILE_PATH,  interfaceProjectRoot +"service"+Constants.Separator.SLASH  + config.getInstancePackage()+Constants.Separator.SLASH +"impl"+Constants.Separator.SLASH + config.getPoName() +"ServiceImpl"+ Constants.FileType.JAVA_SUFFIX);
                
                //Controller文件路径
                filePathMap.put(CONTROLLER_FILE_PATH,  interfaceProjectRoot +"web"+Constants.Separator.SLASH  + config.getInstancePackage()+Constants.Separator.SLASH + config.getPoName()+"Controller" + Constants.FileType.JAVA_SUFFIX);
                
                //生成 jsp 的路径.
                filePathMap.put(LIST_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "list" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(ADD_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "add" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(EDIT_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "edit" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(SHOW_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "show" + Constants.FileType.JSP_SUFFIX );
                
			}else{
				//Bean文件路径
                filePathMap.put(BEAN_FILE_PATH,  interfaceProjectRoot +"entity"+Constants.Separator.SLASH + config.getPoName() + Constants.FileType.JAVA_SUFFIX);
                
				//Dao文件路径
                filePathMap.put(DAO_FILE_PATH,  interfaceProjectRoot +"dao"+Constants.Separator.SLASH + config.getPoName()+"Dao" + Constants.FileType.JAVA_SUFFIX);
                
                //DaoImpl文件路径
                filePathMap.put(DAO_IMPL_FILE_PATH,  interfaceProjectRoot +"dao"+Constants.Separator.SLASH +"impl"+Constants.Separator.SLASH + config.getPoName()+"DaoImpl" + Constants.FileType.JAVA_SUFFIX);
              
                //Service文件路径
                filePathMap.put(SERVICE_FILE_PATH,  interfaceProjectRoot +"service"+Constants.Separator.SLASH + config.getPoName()+"Service" + Constants.FileType.JAVA_SUFFIX);
                
                //ServiceImpl文件路径
                filePathMap.put(SERVICE_IMPL_FILE_PATH,  interfaceProjectRoot +"service"+Constants.Separator.SLASH +"impl"+Constants.Separator.SLASH + config.getPoName() +"ServiceImpl"+ Constants.FileType.JAVA_SUFFIX);
               
                //Controller文件路径
                filePathMap.put(CONTROLLER_FILE_PATH,  interfaceProjectRoot +"web"+Constants.Separator.SLASH + config.getPoName()+"Controller" + Constants.FileType.JAVA_SUFFIX);
                
                //生成 jsp 的路径.
                filePathMap.put(LIST_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "list" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(ADD_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "add" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(EDIT_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "edit" + Constants.FileType.JSP_SUFFIX );
                filePathMap.put(SHOW_JSP_FILE_PATH, jspProjectRoot + config.getFirstLowerPoName() + Constants.Separator.SLASH + "show" + Constants.FileType.JSP_SUFFIX );
                
			}
        }
    }

	/**
	 * 获得 bean 对象 path
	 * @return
	 */
	private String getJavaBeanPath(){
		 String filePath = filePathMap.get(BEAN_FILE_PATH);
         return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
    
    /**
     * 创建 JavaBean.
     * @return
     */
    public boolean createJavaBean(){
        if(filePathMap.containsKey(BEAN_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(BEAN_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_BEAN);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
        return true;
    }

    /**
     * 创建 SqlMapper.
     * @return
     */
    public boolean createJavaBeanMapper(){
        if(filePathMap.containsKey(SQLMAP_FILE_PATH)){
            if(config != null && config.getParamMaps().size() > 0){
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_SQL);
                template = template.replaceAll("#\\{", "00000\\{");
                template = template.replaceAll("%%\\{", "99999\\{");
                
                Map<String, Object> paramsMap = config.getParamMaps();
	
				//daoNameSpace 地址.
                paramsMap.put("daoNameSpace", getJavaBeanDaoPath());
				//daoNameSpace 地址.
                paramsMap.put("resultType", getJavaBeanPath());

                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sqlMapContext = sqlMapContext.replaceAll("00000\\{", "#\\{");
                sqlMapContext = sqlMapContext.replaceAll("99999\\{", "\\$\\{");
                String filePath = filePathMap.get(SQLMAP_FILE_PATH);
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建SQLMap文件：【成功】"+filePath);
                }else{
                    System.out.println("创建SQLMap文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成sqlmap异常");
            }
        }
        return true;
    }

	/**
	 * 获得 dao 对象 path
	 * @return
	 */
	private String getJavaBeanDaoPath(){
		String filePath = filePathMap.get(DAO_FILE_PATH);
        return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
    
    /**
     * 创建 Dao
     */
	public void createJavaBeanDao() {
        if(filePathMap.containsKey(DAO_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(DAO_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_DAO);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanDaoPath", getJavaBeanDaoPath());
                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}
	
	/**
	 * 获得 daoImpl 对象 path
	 * @return
	 */
	private String getJavaBeanDaoImplPath(){
		String filePath = filePathMap.get(DAO_IMPL_FILE_PATH);
        return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
    
	/**
	 * 创建 DaoImpl
	 */
	public void createJavaBeanDaoImpl() {
        if(filePathMap.containsKey(DAO_IMPL_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(DAO_IMPL_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_DAO_IMPL);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanDaoPath", getJavaBeanDaoPath());
                paramsMap.put("JavaBeanDaoImplPath", getJavaBeanDaoImplPath());
                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}


	/**
	 * 获得 Service 对象 path
	 * @return
	 */
	private String getJavaBeanServicePath(){
		String filePath = filePathMap.get(SERVICE_FILE_PATH);
        return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
	
    /**
     * 创建 Service
     */
	public void createJavaBeanService() {
        if(filePathMap.containsKey(SERVICE_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(SERVICE_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_SERVICE);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanDaoPath", getJavaBeanDaoPath());
                paramsMap.put("JavaBeanServicePath", getJavaBeanServicePath());

                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}

	/**
	 * 获得 Service 对象 path
	 * @return
	 */
	private String getJavaBeanServiceImplPath(){
		String filePath = filePathMap.get(SERVICE_IMPL_FILE_PATH);
        return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
	
	 /**
     * 创建 ServiceImpl
     */
	public void createJavaBeanServiceImpl() {
        if(filePathMap.containsKey(SERVICE_IMPL_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(SERVICE_IMPL_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_SERVICE_IMPL);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanDaoPath", getJavaBeanDaoPath());
                paramsMap.put("JavaBeanServicePath", getJavaBeanServicePath());
                paramsMap.put("JavaBeanServiceImplPath", getJavaBeanServiceImplPath());

                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}


	/**
	 * 获得 Controller 对象 path
	 * @return
	 */
	private String getJavaBeanControllerPath(){
		String filePath = filePathMap.get(CONTROLLER_FILE_PATH);
        return filePath.substring(filePath.indexOf(COM_DIR)+1, filePath.lastIndexOf(SLASH)).replaceAll(SLASH, DOT);
	}
	
	/**
	 * 创建 Controller
	 */
	public void createAppJavaBeanController() {
        if(filePathMap.containsKey(CONTROLLER_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(CONTROLLER_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_APP_CONTROLLER);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanServicePath", getJavaBeanServicePath());
                paramsMap.put("JavaBeanControllerPath", getJavaBeanControllerPath());
                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}


	/**
	 * 创建 Controller
	 */
	public void createJspJavaBeanController() {
        if(filePathMap.containsKey(CONTROLLER_FILE_PATH)){
            if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                String filePath = filePathMap.get(CONTROLLER_FILE_PATH);
                String template = FileUtils.readFile(TEMPLATE_ROOT + TEMPLATE_JSP_CONTROLLER);
                Map<String, Object> paramsMap = config.getParamMaps();
                paramsMap.put("JavaBeanPath", getJavaBeanPath());
                paramsMap.put("JavaBeanServicePath", getJavaBeanServicePath());
                paramsMap.put("JavaBeanControllerPath", getJavaBeanControllerPath());
                String sqlMapContext = null;
                try {
                    sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                if(status){
                    System.out.println("创建PO文件：【成功】"+filePath);
                }else{
                    System.out.println("创建PO文件：【失败】"+filePath);
                }
            }else{
                System.out.println("生成PO异常");
            }
        }
	}
	
	/********************************************************************************************创建页面的 code ********************************************************************************************/
	
	/**
	 * 创建 list 页面的jsp  
	 */
	public void createListJSPForJavaBean() {
        try {
        	if(filePathMap.containsKey(LIST_JSP_FILE_PATH)){
                if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                    String filePath = filePathMap.get(LIST_JSP_FILE_PATH);
                    String template = FileUtils.readFile(TEMPLATE_ROOT + LIST_JSP);
                    Map<String, Object> paramsMap = config.getParamMaps();
                    CreateBean bean = new CreateBean();
                	paramsMap.put("listPageCloumns", bean.getListPageShowCloumns(getColumnDatas(config.getTableName(), config.getDatabaseName())));
                	paramsMap.put("listPageContent", bean.getListPageShowContent(getColumnDatas(config.getTableName(), config.getDatabaseName()),config.getTableName(),config.getFirstLowerPoName()));
                	
                    Map<String, Object> tMap = new HashMap<>(16);
                    tMap.put("pageView.data", "pageView.data");
                    paramsMap.put("Test", tMap);
                	
                    String sqlMapContext = null;
                    try {
                        sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                    if(status){
                        System.out.println("创建 list.jsp 文件：【成功】"+filePath);
                    }else{
                        System.out.println("创建 list.jsp 文件：【失败】"+filePath);
                    }
                }else{
                    System.out.println("生成 list.jsp 异常");
                }
            }
		}
		catch (Exception e) {
			System.out.println("创建 list.jsp 页面失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建 add 页面的jsp  
	 */
	public void createAddJSPForJavaBean() {
        try {
        	if(filePathMap.containsKey(ADD_JSP_FILE_PATH)){
                if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                    String filePath = filePathMap.get(ADD_JSP_FILE_PATH);
                    
                    String template = FileUtils.readFile(TEMPLATE_ROOT + ADD_JSP);
                    Map<String, Object> paramsMap = config.getParamMaps();
                    CreateBean bean = new CreateBean();
                    if (NUMERIC.equalsIgnoreCase(config.getPrimaryKeyStyle())) {
                    	paramsMap.put("SQLAddOneContent", bean.getAddPageContent(getColumnDatas(config.getTableName(), config.getDatabaseName()), "AUTO"));
    				}else{
    					paramsMap.put("SQLAddOneContent", bean.getAddPageContent(getColumnDatas(config.getTableName(), config.getDatabaseName()), "CREATE"));
    				}
                    String sqlMapContext = null;
                    try {
                        sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                    if(status){
                        System.out.println("创建 add.jsp 文件：【成功】"+filePath);
                    }else{
                        System.out.println("创建 add.jsp 文件：【失败】"+filePath);
                    }
                }else{
                    System.out.println("生成 add.jsp 异常");
                }
            }
		}
		catch (Exception e) {
			System.out.println("创建 add.jsp 页面失败");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 创建 edit 页面的jsp  
	 */
	public void createEditJSPForJavaBean() {
        try {
        	if(filePathMap.containsKey(EDIT_JSP_FILE_PATH)){
                if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                    String filePath = filePathMap.get(EDIT_JSP_FILE_PATH);
                    
                    String template = FileUtils.readFile(TEMPLATE_ROOT + EDIT_JSP);
                    Map<String, Object> paramsMap = config.getParamMaps();
                    CreateBean bean = new CreateBean();
                    if (NUMERIC.equalsIgnoreCase(config.getPrimaryKeyStyle())) {
                    	paramsMap.put("editOneContent", bean.getEditPageContent(getColumnDatas(config.getTableName(), config.getDatabaseName()), "AUTO"));
    				}else{
    					paramsMap.put("editOneContent", bean.getEditPageContent(getColumnDatas(config.getTableName(), config.getDatabaseName()), "CREATE"));
    				}
                    String sqlMapContext = null;
                    try {
                        sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                    if(status){
                        System.out.println("创建 edit.jsp 文件：【成功】"+filePath);
                    }else{
                        System.out.println("创建 edit.jsp 文件：【失败】"+filePath);
                    }
                }else{
                    System.out.println("生成 edit.jsp 异常");
                }
            }
		}
		catch (Exception e) {
			System.out.println("创建 list.jsp 页面失败");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 创建 show 页面的jsp  
	 */
	public void createShowJSPForJavaBean() {
        try {
        	if(filePathMap.containsKey(SHOW_JSP_FILE_PATH)){
                if(config != null && config.getParamMaps() != null && config.getParamMaps().size() > 0){
                    String filePath = filePathMap.get(SHOW_JSP_FILE_PATH);
                    String template = FileUtils.readFile(TEMPLATE_ROOT + SHOW_JSP);
                    Map<String, Object> paramsMap = config.getParamMaps();
            		CreateBean bean = new CreateBean();
                    paramsMap.put("showOneContent",bean.getShowPageContent(getColumnDatas(config.getTableName(), config.getDatabaseName())));
                    String sqlMapContext = null;
                    try {
                        sqlMapContext = FreeMarkerUtils.getResult(template, paramsMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean status = FileUtils.writeFile(filePath, sqlMapContext);
                    if(status){
                        System.out.println("创建 show.jsp 文件：【成功】"+filePath);
                    }else{
                        System.out.println("创建 show.jsp 文件：【失败】"+filePath);
                    }
                }else{
                    System.out.println("生成 show.jsp 异常");
                }
            }
		}
		catch (Exception e) {
			System.out.println("创建 show.jsp 页面失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得列信息.
	 */
	List<ColumnData> columnDatas = new ArrayList<>();
	private List<ColumnData> getColumnDatas(String tableName, String databaseName){
		CreateBean bean = new CreateBean();
		if (columnDatas==null || columnDatas.size()==0) {
			try {
				columnDatas = bean.getColumnDatas(config.getTableName(), config.getDatabaseName());
			}
			catch (SQLException e) {
				System.out.println("获得列信息失败啦");
				e.printStackTrace();
			}
		}
		return columnDatas;
	}
	
}
