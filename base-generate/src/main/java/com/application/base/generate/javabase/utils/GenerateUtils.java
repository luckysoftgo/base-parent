package com.application.base.generate.javabase.utils;

import com.application.base.generate.javabase.bean.GenerateColumn;
import com.application.base.generate.javabase.db.MysqlConnection;
import com.application.base.utils.common.BaseStringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 生成的util
 * @author 孤狼
 */
public class GenerateUtils {

	private static String DB_INT_TYPE = ",BIGINT,BIT,INT,SMALLINT,TINYINT,MEDIUMINT,INT UNSIGNED,";
	private static String DB_DATE_TYPE = ",DATETIME,DATE,TIME,TIMESTAMP,";
	private static String DB_STRING_TYPE = ",VARCHAR,CHAR,TEXT,LONGTEXT,BLOB,LONGBLOB,MEDIUMBLOB,MEDIUMTEXT,TINYBLOB,TINYTEXT,";
	private static String DB_DECIMAL_TYPE = ",DECIMAL,BIGDECIMAL,";
	private static String DB_DOUBLE_TYPE = ",DOUBLE,FLOAT";
	
	private static String INTEGER_TYPE = "Integer";
	private static String DATE_TYPE = "Date";
	private static String STRING_TYPE = "String";
	private static String DECIMAL_TYPE = "BigDecimal";
	private static String DOUBLE_TYPE = "Double";

	private static String SQLMAP_INTEGER_TYPE = "NUMERIC";
	private static String SQLMAP_DATE_TYPE = "TIMESTAMP";
	private static String SQLMAP_STRING_TYPE = "VARCHAR";
	private static String SQLMAP_DECIMAL_TYPE = "DECIMAL";
	private static String SQLMAP_DOUBLE_TYPE = "DOUBLE";

	private static String IMPORT_DATE = " java.util.Date";
	private static String IMPORT_BIGDECIMAL = " java.math.BigDecimal";

	private static String UNDERLINE="_";
	private static String COMMA = ",";
	private static String COLUMN ="COLUMN_NAME";
	private static String TYPE_NAME = "TYPE_NAME";
	private static String REMARKS = "REMARKS";
	/**
	 * //主键类型.
	 */
	public static String primaryKeyType;

	/**
	 * 将表名转化为PO类名
	 * @param tableName
	 * @return
	 * @throws Exception 
	 */
	public static String tableToPoName(String tableName) throws Exception{
		try {
			if(!BaseStringUtil.isEmpty(tableName)){
				//将表名翻译成PO名称
				String[] strArray = tableName.split(UNDERLINE);
				StringBuffer buff = new StringBuffer();
				for(String str : strArray){
					//第一个单词大写，多个单词时，首字母都大写
					buff.append(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase());
				}
				return buff.toString();
			}else{
				throw new Exception("表名不能为空");
			}
		} catch (Exception e) {
			System.out.println("表名定义规则错误 tableName【"+tableName+"】");
			throw e;
		}
	}

	/**
	 * 将表自己名转换为变量名
	 * @return
	 */
	public static String columnNameToVariable(String columnName){
		if(!BaseStringUtil.isEmpty(columnName)){
			String[] array = columnName.toUpperCase().split(UNDERLINE);

			StringBuffer buff = new StringBuffer();
			for(int i=0;i<array.length;i++){
				String str = array[i];
				if(i<1){
					buff.append(str.toLowerCase());
				}else{
					//第一个单词大写，多个单词时，首字母都大写
					buff.append(str.substring(0, 1) + str.substring(1).toLowerCase());
				}
			}
			return buff.toString();
		}
		return null;
	}

	/**
	 * 将变量转换成数据库表字段
	 * @param variableName
	 * @return
	 */
	public static String variableToColumnName(String variableName){
		if(!BaseStringUtil.isEmpty(variableName)){
			StringBuffer buff = new StringBuffer();
			for(int i=0;i<variableName.length();i++){
				char str = variableName.charAt(i);
				//判断是否是大写字母
				if(!Character.isLowerCase(str)){
					buff.append(UNDERLINE);
				}
				buff.append(str);
			}
			return buff.toString().toUpperCase();
		}
		return null;
	}

	/**
	 * 表字段类型转换为java变量类型
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	public static String dbTypeToVariableType(String dbType) throws Exception {
		if (BaseStringUtil.isEmpty(dbType)) {
			return null;
		}
		if(DB_INT_TYPE.contains(COMMA + dbType + COMMA)){
			return INTEGER_TYPE;
		}else if(DB_DATE_TYPE.contains(COMMA + dbType + COMMA)){
			return DATE_TYPE;
		}else if(DB_STRING_TYPE.contains(COMMA + dbType + COMMA)){
			return STRING_TYPE;
		}else if(DB_DECIMAL_TYPE.contains(COMMA + dbType + COMMA)){
			return DECIMAL_TYPE;
		}else if(DB_DOUBLE_TYPE.contains(COMMA + dbType + COMMA)){
			return DOUBLE_TYPE;
		}
		else{
			throw new RuntimeException("数据库字段类型没有找到对应的变量类型");
		}
	}

    public static String buildDefaultValue(String dbType) throws Exception {
        if (BaseStringUtil.isEmpty(dbType)) {
            return null;
        }
        if(DB_INT_TYPE.contains(COMMA + dbType + COMMA)){
            return "null";
        }else if(DB_DATE_TYPE.contains(COMMA + dbType + COMMA)){
            return "null";
        }else if(DB_STRING_TYPE.contains(COMMA + dbType + COMMA)){
            return "null"; 
        }else if(DB_DECIMAL_TYPE.contains(COMMA + dbType + COMMA)){
            return "null";
        }else if(DB_DOUBLE_TYPE.contains(COMMA + dbType + COMMA)){
            return "null";
        }
        else{
            throw new RuntimeException("数据库字段类型没有找到对应的变量类型");
        }
    }

	/**
	 * 表字段名转换为sqlmap
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	public static String dbTypeToSqlMapType(String dbType) throws Exception {
		if (BaseStringUtil.isEmpty(dbType)) {
			return null;
		}
		if(DB_INT_TYPE.contains(COMMA + dbType + COMMA)){
			return SQLMAP_INTEGER_TYPE;
		}else if(DB_DATE_TYPE.contains(COMMA + dbType + COMMA)){
			return SQLMAP_DATE_TYPE;
		}else if(DB_STRING_TYPE.contains(COMMA + dbType + COMMA)){
			return SQLMAP_STRING_TYPE;
		}else if(DB_DECIMAL_TYPE.contains(COMMA + dbType + COMMA) ){
			return SQLMAP_DECIMAL_TYPE;
		}else if(DB_DOUBLE_TYPE.contains(COMMA + dbType + COMMA)){
			return SQLMAP_DOUBLE_TYPE;
		}else{
			throw new RuntimeException("数据库字段类型没有找到对应的变量类型");
		}
	}
	/**
	 * 数据库字段类型是否需要引入包
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String dbTypeToImportPath(String type){
		if(!BaseStringUtil.isEmpty(type) && DB_DATE_TYPE.indexOf(COMMA + type.toUpperCase() + COMMA) != -1
				){
			return IMPORT_DATE;
		}else if(!BaseStringUtil.isEmpty(type) && DB_DECIMAL_TYPE.indexOf(COMMA + type.toUpperCase() + COMMA) != -1){
			return IMPORT_BIGDECIMAL;
		}
		return null;
	}

	/**
	 * 将表字段转化为list
	 * @param tableName
	 * @return
	 * @throws Exception 
	 */
	public static List<GenerateColumn> columnToList(String tableName) throws Exception{
		Connection conn = MysqlConnection.newConnection();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
			List<GenerateColumn> columnList = new ArrayList<GenerateColumn>();
			Map<Integer,String> columnMap = new HashMap<Integer,String>(16);
			int i = 0;
			while (rs.next()) {
				columnMap.put(i, rs.getString(COLUMN));
				GenerateColumn column = new GenerateColumn();
				String columnName = rs.getString(COLUMN) ;
				String columnType = GenerateUtils.dbTypeToSqlMapType(rs.getString(TYPE_NAME));
				Map<String, Object> resultMap = getTablePK(tableName);
				if (columnName.equals(resultMap.get("tablePK"))) {
					setPrimaryKeyType(columnType);
				}
				column.setName(GenerateUtils.columnNameToVariable(rs.getString(COLUMN)));
				column.setType(GenerateUtils.dbTypeToVariableType(rs.getString(TYPE_NAME)));
				column.setStaticFinalName(rs.getString(COLUMN).toUpperCase());
				column.setFirstUpperName(column.getName().substring(0, 1).toUpperCase()+column.getName().substring(1));
				column.setDbName(columnName);
                column.setDefaultValue(GenerateUtils.buildDefaultValue(rs.getString(TYPE_NAME)));
				column.setSqlMapColumnType(columnType);
				column.setDbType(rs.getString(TYPE_NAME));
				column.setRemarks(rs.getString(REMARKS));
				columnList.add(column);
				i++;
			}

			if(!(columnList != null && columnList.size()>0)){
				throw new Exception("请检查code项目下的JDBC配置文件数据库信息是否正确");
			}
			//验证是否满足条件
			validateColumn(columnMap);

			return columnList;
		} catch (Exception e) {
			throw e;
		}finally{
			//关闭数据库连接
			MysqlConnection.closeConnection(conn);
		}
	}
	
	private static void setPrimaryKeyType(String columnType) {
		primaryKeyType = columnType;
	}

	public static void validateColumn(Map<Integer,String> columnMap) throws Exception{
		boolean createTimeStatus = true;
		StringBuffer buff = new StringBuffer();
		//判断createTime字段是否存在
		for(int i=0;i<columnMap.size();i++){
			String columnName = columnMap.get(i);
			if("CREATE_TIME".equals(columnName.toUpperCase())){
				createTimeStatus = false;
			}
			for(int j=1;j<columnName.length();j++){
				char str = columnName.charAt(j);
				// 当前字符的前一个必须是小写字符并且不能等于下划线，当前字符是大写字符，而且不是数字
				if (str != '_' && !Character.isLowerCase(str) && Character.isLowerCase(columnName.charAt(j - 1))
						&& columnName.charAt(j - 1) != '_' && !Character.isDigit(columnName.charAt(j))) {
					buff.append(columnName).append(COMMA);
					break;
				}
			}
		}
		if(createTimeStatus){
			throw new Exception("表中缺少CREATE_TIME字段");
		}
		if(buff.length() > 0){
			throw new Exception("字段名称不符合规则，多个单词之间应该用下划线分割，表中字段名称：【"+buff.substring(0, buff.length()-1)+"】");
		}
	}
    public static List<String> getTable(){
        Connection conn = MysqlConnection.newConnection();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            String [ ] t = { "TABLE"};
            ResultSet rs = dbmd.getTables(null, "HR", "%", t);
            List<String> tables = new ArrayList<String>(rs.getFetchSize());
            while(rs.next()){
               tables.add(rs.getString(3));
            }
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //关闭数据库连接
            MysqlConnection.closeConnection(conn);
        }
        return null;
    }
	public static void main(String[] args){
		List<String> a = getTable();
        for (String b :a){
            System.out.println(b);
        }
	}

	/**
	 * 获得表的主键
	 * @param tableName
	 * @return
	 */
	public static Map<String, Object> getTablePK(String tableName) {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		Connection conn = null ;
		try {
			conn = MysqlConnection.newConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String  sql= " SELECT TABLE_NAME,COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME <> 'dtproperties' AND TABLE_NAME = '"+tableName+"'";
		String resultPK = null;
		try {
			//执行sql语句
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				resultPK = res.getString("COLUMN_NAME");
			}
			res.close();
			res = null;
			pstmt.close();
			pstmt=null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("tablePK", resultPK);
		resultMap.put("tablePKVal", turnUpperStr(resultPK));
		MysqlConnection.closeConnection(conn);
		//返回.
		return resultMap;
	}
	
	public static String turnUpperStr(String str) {
		try {
			String[] strs = str.split("_");
			return strs[0] + strs[1].substring(0, 1).toUpperCase() + strs[1].substring(1);
		}
		catch (Exception e) {
			return str;
		}
	}

	
}
