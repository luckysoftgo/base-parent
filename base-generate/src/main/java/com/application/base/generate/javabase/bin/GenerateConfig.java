package com.application.base.generate.javabase.bin;

import com.application.base.generate.javabase.bean.BaseEntity;
import com.application.base.generate.javabase.bean.GenerateColumn;
import com.application.base.generate.javabase.utils.GenerateUtils;
import com.application.base.utils.common.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 生成信息
 * @author 孤狼
 */
public class GenerateConfig {

	private String tablePrefix;

	private String tableName;

	private String poName;

	private String firstLowerPoName;

	private String tableMiddle;

	private String workspacesPath;
	
	/**
	 * //代码生成的parent
	 */
	private String parentName;
	
	/**
	 * //系统名称
	 */
	private String systemName;

	private String packageName;
	/**
	 *包路径
	 */
	private String packagePath;
	/**
	 * // 单独的包结构 .
	 */
	private String instancePackage;
	
	/**
	 * // 表的主键 .
	 */
	private String tablePK;
	
	/**
	 * // 表的主键字段 .
	 */
	private String tablePKVal;
	
	/**
	 *  // 主键生成方式.
	 */
	private String primaryKeyStyle;
	
	/**
	 * // 主键类型.
	 */
	private String primaryKeyType;
	
	/**
	 * // 主键set
	 */
	private String primaryKeySet;
	
	/**
	 * //数据库配置标识
	 */
	private String factoryTag;
	
	/**
	 * //是否使用注解事务
	 */
	private String useTransactional;
	
	/**
	 * //是否使用缓存
	 */
	private String useCache;
	
	/**
	 * //所在的数据集的名称
	 */
	private String databaseName;
	
	/**
	 * //是否记录系统日志.
	 */
	private String saveLog;
	
	/**
	 * //是否是多数据源
	 */
	private String moreDbTag;


	private Map<String, String> typeMap = new HashMap<String, String>();

	/** 包路径 */
	private List<String> importList = new ArrayList<String>();

	/** 字段列 */
	private List<GenerateColumn> columnList = new ArrayList<GenerateColumn>();

	/** po定义的变量 */
	private List<GenerateColumn> poColumnList = new ArrayList<GenerateColumn>();

	/** 是否存在启用字段 */
	private String existIsDelete = Constants.DeleteStatus.DISABLED.toString();

	/** 是否存在UUID字段 */
	private String existUuid = Constants.DeleteStatus.DISABLED.toString();

	private Map<String, Object> paramMaps = new HashMap<String, Object>();

	private Map<String, String> excludeColumnMap = new HashMap<String, String>();

	public GenerateConfig(String databaseName,String tableName,String parentName, String systemName, String packageName, String primaryKeyStyle,String factoryTag,String useTransactional,String useCache,String saveLog,String moreDbTag,String packagePath)
			throws Exception {

		// 表名不做处理
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.parentName = parentName;
		this.systemName = systemName;
		this.packageName = packageName;
		this.primaryKeyStyle = primaryKeyStyle.toUpperCase();
		this.factoryTag = factoryTag.toUpperCase();
		this.useTransactional = useTransactional;
		this.useCache = useCache;
		this.saveLog = saveLog;
		this.moreDbTag =moreDbTag;
		this.packagePath = packagePath;
		
		// 初始化
		initProperties(parentName,systemName);
	}

	public GenerateConfig(String databaseName,String tableName, String parentName,String systemName, String packageName, String primaryKeyStyle,
			String instancePackage,String factoryTag,String useTransactional,String useCache,String saveLog,String moreDbTag,String packagePath) throws Exception {

		// 表名不做处理
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.parentName = parentName;
		this.systemName = systemName;
		this.packageName = packageName;
		this.instancePackage = instancePackage;
		this.primaryKeyStyle = primaryKeyStyle.toUpperCase();
		this.factoryTag = factoryTag.toUpperCase();
		this.useTransactional = useTransactional;
		this.useCache = useCache;
		this.saveLog = saveLog;
		this.moreDbTag =moreDbTag;
		this.packagePath = packagePath;
		// 初始化
		initProperties(parentName,systemName);
	}

	/**
	 * init infos.
	 * 
	 * @throws Exception
	 */
	private void initProperties(String parentName,String systemName) throws Exception {

		// 提取表的前缀
		this.tablePrefix = this.tableName.substring(0, this.tableName.indexOf(Constants.Separator.UPDERLINE));

		this.poName = GenerateUtils.tableToPoName(tableName);

		this.tableMiddle = this.poName.toLowerCase().substring(tablePrefix.length());

		// 首字母小写
		this.firstLowerPoName = this.poName.substring(0, 1).toLowerCase() + this.poName.substring(1);

		Map<String, Object> primaryKey = GenerateUtils.getTablePK(tableName);

		this.tablePK = primaryKey.get("tablePK").toString();

		this.tablePKVal = primaryKey.get("tablePKVal").toString();

		this.primaryKeySet = this.tablePKVal.substring(0, 1).toUpperCase() + this.tablePKVal.substring(1);
		
		// 初始化项目跟路径
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:", "").replaceAll("%20", " ").trim();

		// 构建项目的名称为Parent,截取parent之前的工作空间目录
		path = path.substring(0, path.indexOf("/"+parentName) + 1);
		
		this.workspacesPath = path;

		/**
		 * 初始化每个表的列.
		 */
		initColumn();

		this.primaryKeyType = GenerateUtils.primaryKeyType;

		/**
		 * 初始化每个表的Map集合.
		 */
		initMap();

		/**
		 * 排除不包括的列.
		 */
		initExcludeColumn();
	}

	/**
	 * 得到文件的具体路径.
	 * @return
	 */
	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

	/**
	 * 初始化字段列
	 * 
	 * @throws Exception
	 */
	private void initColumn() throws Exception {
		if (columnList == null || columnList.size() == 0) {
			// 初始化表中所有字段信息
			this.columnList = GenerateUtils.columnToList(tableName);
			for (GenerateColumn column : this.columnList) {
				typeMap.put(column.getType(), column.getType());
				if ("disabled".equals(column.getName())) {
					existIsDelete = Constants.DeleteStatus.ENABLED.toString();
				}
				else if ("uuid".equals(column.getName())) {
					existUuid = Constants.DeleteStatus.ENABLED.toString();
				}
			}
		}
	}

	/**
	 * 初始化 map参数，主要数据与模板合成
	 */
	private void initMap() {

		paramMaps.put("poName", this.poName);

		paramMaps.put("firstLowerPoName", this.firstLowerPoName);

		// paramMaps.put("childSystemName", this.childSystemName);
		// paramMaps.put("childSystemAPIName", this.childSystemAPIName);
		// paramMaps.put("childSystemWeb", this.childSystemWeb);

		paramMaps.put("existIsDelete", this.existIsDelete);

		paramMaps.put("tableName", this.tableName);

		paramMaps.put("tablePK", this.tablePK);

		paramMaps.put("primaryKeySet", this.primaryKeySet);

		paramMaps.put("tablePKVal", this.tablePKVal);

		paramMaps.put("primaryKeyStyle", this.primaryKeyStyle);

		paramMaps.put("primaryKeyType", this.primaryKeyType);
		
		paramMaps.put("factoryTag", this.factoryTag);

		paramMaps.put("tablePrefix", this.tablePrefix);

		paramMaps.put("tableMiddle", this.tableMiddle);

		paramMaps.put("existUuid", this.existUuid);

		paramMaps.put("tableMiddle", this.tableMiddle);

		paramMaps.put("columnList", this.columnList);

		paramMaps.put("poColumnList", this.poColumnList);

		paramMaps.put("importList", this.importList);
		
		paramMaps.put("useTransactional", this.useTransactional.toUpperCase());
		
		paramMaps.put("useCache", this.useCache.toUpperCase());
		
		paramMaps.put("saveLog", this.saveLog.toUpperCase());
		
		paramMaps.put("moreDbTag", this.moreDbTag.toUpperCase());
		
	}

	/***
	 * 初始化排除的列
	 */
	private void initExcludeColumn() {
		Field[] fields = BaseEntity.class.getDeclaredFields();
		for (Field f : fields) {
			if (!"serialVersionUID".equals(f.getName())) {
				this.excludeColumnMap.put(f.getName(), f.getName());
			}
		}
		Map<String, String> importMap = new HashMap<String, String>(16);
		for (GenerateColumn column : this.columnList) {
			if (!excludeColumnMap.containsKey(column.getName())) {
				poColumnList.add(column);
				String importStr = GenerateUtils.dbTypeToImportPath(column.getType());
				if (!importMap.containsKey(importStr)) {
					importMap.put(importStr, null);
					importList.add(importStr);
				}
			}
		}
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public Map<String, Object> getParamMaps() {
		return paramMaps;
	}

	public void setParamMaps(Map<String, Object> paramMaps) {
		this.paramMaps = paramMaps;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPoName() {
		return poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public String getFirstLowerPoName() {
		return firstLowerPoName;
	}

	public void setFirstLowerPoName(String firstLowerPoName) {
		this.firstLowerPoName = firstLowerPoName;
	}

	public String getTableMiddle() {
		return tableMiddle;
	}

	public void setTableMiddle(String tableMiddle) {
		this.tableMiddle = tableMiddle;
	}

	public String getWorkspacesPath() {
		return workspacesPath;
	}

	public void setWorkspacesPath(String workspacesPath) {
		this.workspacesPath = workspacesPath;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}

	public List<String> getImportList() {
		return importList;
	}

	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public List<GenerateColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<GenerateColumn> columnList) {
		this.columnList = columnList;
	}

	public List<GenerateColumn> getPoColumnList() {
		return poColumnList;
	}

	public void setPoColumnList(List<GenerateColumn> poColumnList) {
		this.poColumnList = poColumnList;
	}

	public String getExistIsDelete() {
		return existIsDelete;
	}

	public void setExistIsDelete(String existIsDelete) {
		this.existIsDelete = existIsDelete;
	}

	public String getExistUuid() {
		return existUuid;
	}

	public void setExistUuid(String existUuid) {
		this.existUuid = existUuid;
	}

	public Map<String, String> getExcludeColumnMap() {
		return excludeColumnMap;
	}

	public void setExcludeColumnMap(Map<String, String> excludeColumnMap) {
		this.excludeColumnMap = excludeColumnMap;
	}

	public String getInstancePackage() {
		return instancePackage;
	}

	public void setInstancePackage(String instancePackage) {
		this.instancePackage = instancePackage;
	}

	public String getTablePK() {
		return tablePK;
	}

	public void setTablePK(String tablePK) {
		this.tablePK = tablePK;
	}

	public String getPrimaryKeySet() {
		return primaryKeySet;
	}

	public void setPrimaryKeySet(String primaryKeySet) {
		this.primaryKeySet = primaryKeySet;
	}

	public String getUseTransactional() {
		return useTransactional;
	}
	
	public void setUseTransactional(String useTransactional) {
		this.useTransactional = useTransactional;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getTablePKVal() {
		return tablePKVal;
	}

	public void setTablePKVal(String tablePKVal) {
		this.tablePKVal = tablePKVal;
	}

	public String getPrimaryKeyStyle() {
		return primaryKeyStyle;
	}

	public void setPrimaryKeyStyle(String primaryKeyStyle) {
		this.primaryKeyStyle = primaryKeyStyle;
	}

	public String getPrimaryKeyType() {
		return primaryKeyType;
	}

	public void setPrimaryKeyType(String primaryKeyType) {
		this.primaryKeyType = primaryKeyType;
	}

	public String getUseCache() {
		return useCache;
	}

	public void setUseCache(String useCache) {
		this.useCache = useCache;
	}

	public String getSaveLog() {
		return saveLog;
	}

	public void setSaveLog(String saveLog) {
		this.saveLog = saveLog;
	}

	public String getMoreDbTag() {
		return moreDbTag;
	}
	
	public void setMoreDbTag(String moreDbTag) {
		this.moreDbTag = moreDbTag;
	}
	
	public String getPackagePath() {
		return packagePath;
	}
	
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
}
