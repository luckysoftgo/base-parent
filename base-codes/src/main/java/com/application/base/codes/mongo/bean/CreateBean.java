package com.application.base.codes.mongo.bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.application.base.codes.mongo.def.MonGoCodeGoHelper;

public class CreateBean {
	
	private Connection connection = null;
	String url;
	String username;
	String password;
	static String rt = "\r\t";
	private String method;
	private String argv;
	static String selectStr;
	static String from;

	//主键
	static String tablePK = null;

	/**
	 * MySQL的链接.
	 * @param url
	 * @param username
	 * @param password
	 */
	public void setMySqlInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * MonGo的链接.
	 * @param url
	 * @param username
	 * @param password
	 */
	public void setMonGoInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {
		if (connection==null) {
			return DriverManager.getConnection(url, username, password);
		}
	    return connection;
	}

	/**
	 * OK
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnData> getColumnDatas(String tableName)
			throws SQLException {
		List<ColumnData> columnList = new LinkedList<ColumnData>();
		List<String> resultList = MonGoCodeGoHelper.getTableColumn(tableName);
		for (String column : resultList) {
			ColumnData data = new ColumnData();
			if ("_id".equalsIgnoreCase(column)) {
				data.setClassType("ObjectId");
				data.setJavaColumnName(column);
				data.setMongoCloumnName(column);
				data.setColumnComment(column);
				data.setColumnType("ObjectId");
				data.setDataType("ObjectId");
			}else {
				data.setClassType("String");
				data.setJavaColumnName(column);
				data.setMongoCloumnName(column);
				data.setColumnComment(column);
				data.setColumnType("String");
				data.setDataType("String");
			}
			//add 添加方式.
			columnList.add(data);
		}
		return columnList;
	}

	/**
	 * OK
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getBeanFeilds(String tableName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		str.append("private static final long serialVersionUID = 1L;");
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			String name = d.getJavaColumnName();
			String type = d.getDataType(); //类型
			String comment = d.getColumnComment(); //注释
			String maxChar = name.substring(0, 1).toUpperCase();
			if ("_id".equalsIgnoreCase(name)) {
				str.append("\r\t@Id");
			}
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
			String method = maxChar + name.substring(1, name.length());
			getset.append("\r\t"); //换行
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		this.argv = str.toString();
		this.method = getset.toString();
		return this.argv + this.method;
	}

	/**
	 * OK
	 * @param columnt
	 */
	private void formatFieldClassType(ColumnData columnt) {
		String fieldType = columnt.getColumnType();
		String scale = columnt.getScale();
		if ("N".equals(columnt.getNullable())) {
			columnt.setOptionType("required:true");
		}
		if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		} else if ("date".equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		} else if ("int".equals(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		} else if ("number".equals(fieldType)) {
			if ((StringUtils.isNotBlank(scale))&& (Integer.parseInt(scale) > 0)) {
				columnt.setClassType("easyui-numberbox");
				if (StringUtils.isNotBlank(columnt.getOptionType())) {
					columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
				} else {
					columnt.setOptionType("precision:2,groupSeparator:','");
				}
			} else {
				columnt.setClassType("easyui-numberbox");
			}
		} else if (("float".equals(fieldType)) || ("double".equals(fieldType))|| ("decimal".equals(fieldType))) {
			columnt.setClassType("easyui-numberbox");
			if (StringUtils.isNotBlank(columnt.getOptionType())) {
				columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
			} else {
				columnt.setOptionType("precision:2,groupSeparator:','");
			}
		} else {
			columnt.setClassType("easyui-validatebox");
		}
	}

	/**
	 * 获得类型.
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char")){
			dataType = "String";
		}			
		else if (dataType.contains("int")){
			dataType = "Integer";
		}
		else if (dataType.contains("float")){
			dataType = "Float";
		}
		else if (dataType.contains("double")){
			dataType = "Double";
		}
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale))&& (Integer.parseInt(scale) > 0)){
				dataType = "BigDecimal";
			}
			else if ((StringUtils.isNotBlank(precision))&& (Integer.parseInt(precision) > 6)){
				dataType = "Long";
			}
			else{
				dataType = "Integer";
			}
		} else if (dataType.contains("decimal")){
			dataType = "BigDecimal";
		}
		/* 2015-09-28
		else if (dataType.contains("date") || dataType.contains("time")){
			dataType = "Date";
		}
		*/
		else if (dataType.contains("date") || dataType.contains("time")){
			dataType = "String";
		}
		else if (dataType.contains("clob")){
			dataType = "Clob";
		}
		else {
			dataType = "Object";
		}
		return dataType;
	}
	
	public void getPackage(int type, String createPath, String content,
			String packageName, String className, String extendsClassName,
			String[] importName) throws Exception {
		if (packageName == null) {
			packageName = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; ++i) {
			sb.append("import ").append(importName[i]).append(";\r");
		}
		sb.append("\r");
		sb.append("/**\r *  entity. @author bruce \n Date:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null) {
			sb.append(" extends ").append(extendsClassName);
		}
		if (type == 1) {
			sb.append(" ").append("implements java.io.Serializable {\r");
		} else {
			sb.append(" {\r");
		}
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1) {
			sb.append("private " + className + " " + temp + "; // entity ");
		}
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		//创建文件选项。
		createFile(createPath, "", sb.toString());
	}

	/**
	 * 获得表名字.
	 * 
	 * @param tableName
	 * @return
	 */
	public String getTablesNameToClassName(String tableName)
	  {
	    String[] split = tableName.split("_");
	    if (split.length > 1) {
	      StringBuffer sb = new StringBuffer();
	      int i = 0;
	      while (true) { 	    	  
	    	String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());	    
	        sb.append(tempTableName);
	        ++i; 
	        if (i >= split.length)
	        {
	          return sb.toString(); 
	        } 
	       }
	    }
	    String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
	    return tempTables;
	  }

	/**
	 *  创建文件
	 * @param path
	 * @param fileName
	 * @param str
	 * @throws IOException
	 */
	public void createFile(String path, String fileName, String str)
			throws IOException {
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("UTF-8")));
		writer.flush();
		writer.close();
		writer=null;
	}

	/**
	 * 自动生成SQL.
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAutoCreateSql(String tableName,String lowerName ,String autoType)
			throws Exception {
		Map<String,Object> sqlMap = new HashMap<String,Object>();
		List<ColumnData> columnDatas = getColumnDatas(tableName);
		//界面展示问题.
		sqlMap.put("tableName", tableName);		
		sqlMap.put("listPageCloumns", getListPageShowCloumns(columnDatas));
		sqlMap.put("listPageContent", getListPageShowContent(columnDatas,tableName,lowerName));
		sqlMap.put("addOneContent", getAddPageContent(columnDatas,autoType));
		sqlMap.put("editOneContent", getEditPageContent(columnDatas,autoType));
		sqlMap.put("showOneContent", getShowPageContent(columnDatas));
		
		sqlMap.put("tablePK", "_id"); //主键
		
		tablePK = null;
		return sqlMap;
	}

	public String[] getColumnList(String columns) throws SQLException {
		String[] columnList = columns.split("[|]");
		return columnList;
	}

	private String turnUpperStr(String input){
		String[] strs = input.split("_");
		StringBuffer buffer = new  StringBuffer();
		buffer.append(strs[0]);
		for (int i = 1 ; i<strs.length;i++) {
			String temp = strs[i];
			String before = temp.substring(0,1).toUpperCase();
			String after = temp.substring(1,temp.length());
			buffer.append(before+after);
		}
		return buffer.toString();
	}
	
/********************************************************************** 页面的显示***********************************************************************/
	
	/**
	 * 展示.
	 * 
	 * @param columnDatas
	 * @return
	 */
	private String getShowPageContent(List<ColumnData> columnDatas) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			
			tdbuBuffer.append("			<tr>\n\t");
			tdbuBuffer.append("				<td class=\"td_right\">\n\t");
			tdbuBuffer.append("					").append(columnData.getColumnComment()+":\n\t");
			tdbuBuffer.append("				</td>\n\t");
			tdbuBuffer.append("				<td class=\"td_left\" >\n\t");
			/*2015-09-28
			//时间的设置.
			if (columnData.getColumnType().equalsIgnoreCase("Date") || columnData.getColumnType().contains("time")) {
				tdbuBuffer.append("				<fmt:formatDate value=\"${object."+columnData.getJavaColumnName()+"}\" pattern=\"yyyy-MM-dd HH:MM:ss\"/>\n\t");
			}else {
				tdbuBuffer.append("				${object."+columnData.getJavaColumnName()+"}\n\t");
			}
			*/
			//时间的设置.
			if ("Date".equalsIgnoreCase(columnData.getColumnType()) || columnData.getColumnType().contains("time")) {
				tdbuBuffer.append("				${fn:substring(object."+columnData.getJavaColumnName()+",0,19)}\"\n\t");
			}else {
				tdbuBuffer.append("				${object."+columnData.getJavaColumnName()+"}\n\t");
			}
			tdbuBuffer.append("				</td>\n\t");
			tdbuBuffer.append("			</tr>\n\t");
		}
		return tdbuBuffer.toString();
	}

	/**
	 * 
	 * @param columnDatas
	 * @param autoType
	 * @return
	 */
	private String getEditPageContent(List<ColumnData> columnDatas,String autoType) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			if (i==0 && "AUTO".equalsIgnoreCase(autoType)) {

			}else {
				
				tdbuBuffer.append("			<tr>\n\t");
				tdbuBuffer.append("				<td class=\"td_right\">\n\t");
				tdbuBuffer.append("					").append(columnData.getColumnComment()+":\n\t");
				tdbuBuffer.append("				</td>\n\t");
				tdbuBuffer.append("				<td class=\"td_left\" >\n\t");
				/* 2015-09-28
				//时间的设置.
				if (columnData.getColumnType().equalsIgnoreCase("Date") || columnData.getColumnType().contains("time")) {
					tdbuBuffer.append("				<input class=\"input-text lh30\" size=\"40\" name=\""+columnData.getJavaColumnName()+"\" id=\""+columnData.getJavaColumnName()+"\" ");
					tdbuBuffer.append("value='<fmt:formatDate value=\"${object."+columnData.getJavaColumnName()+"}\" pattern=\"yyyy-MM-dd HH:MM:ss\"/>'");
					tdbuBuffer.append("></input>\n\t");
					tdbuBuffer.append("				<font color=\"red\"> <span id=\""+columnData.getJavaColumnName()+"Tag\""+" /></font>\n\t");
				}else {
					tdbuBuffer.append("				<input class=\"input-text lh30\" size=\"40\" name=\""+columnData.getJavaColumnName()+"\" id=\""+columnData.getJavaColumnName()+"\" value=\"${object."+columnData.getJavaColumnName()+"}\"").append("></input>\n\t");
					tdbuBuffer.append("				<font color=\"red\"> <span id=\""+columnData.getJavaColumnName()+"Tag\""+" /></font>\n\t");
				}
				*/
				//时间的设置.
				if ("Date".equalsIgnoreCase(columnData.getColumnType()) || columnData.getColumnType().contains("time")) {
					tdbuBuffer.append("				<input class=\"input-text lh30\" size=\"40\" name=\""+columnData.getJavaColumnName()+"\" id=\""+columnData.getJavaColumnName()+"\" value=\"${fn:substring(object."+columnData.getJavaColumnName()+",0,19)}\"").append("></input>\n\t");
					tdbuBuffer.append("				<font color=\"red\"> <span id=\""+columnData.getJavaColumnName()+"Tag\""+" /></font>\n\t");
				}else {
					tdbuBuffer.append("				<input class=\"input-text lh30\" size=\"40\" name=\""+columnData.getJavaColumnName()+"\" id=\""+columnData.getJavaColumnName()+"\" value=\"${object."+columnData.getJavaColumnName()+"}\"").append("></input>\n\t");
					tdbuBuffer.append("				<font color=\"red\"> <span id=\""+columnData.getJavaColumnName()+"Tag\""+" /></font>\n\t");
				}
				tdbuBuffer.append("				</td>\n\t");
				tdbuBuffer.append("			</tr>\n\t");
			}
		}
		return tdbuBuffer.toString();
	}

	/**
	 * 添加页面
	 * 
	 * @param columnDatas
	 * @param autoType
	 * @return
	 */
	private String getAddPageContent(List<ColumnData> columnDatas,String autoType) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			if (i==0  && "AUTO".equalsIgnoreCase(autoType)) {
				//不用主键的列.
			}else{
				tdbuBuffer.append("			<tr>\n\t");
				tdbuBuffer.append("				<td class=\"td_right\">\n\t");
				tdbuBuffer.append("					").append(columnData.getColumnComment()+":\n\t");
				tdbuBuffer.append("				</td>\n\t");
				tdbuBuffer.append("				<td class=\"td_left\" >\n\t");
				tdbuBuffer.append("					<input class=\"input-text lh30\" size=\"40\" name=\""+columnData.getJavaColumnName()+"\" id=\""+columnData.getJavaColumnName()+"\" />\n\t");
				tdbuBuffer.append("					<font color=\"red\"> <span id=\""+columnData.getJavaColumnName()+"Tag\""+" /></font>\n\t");
				tdbuBuffer.append("				</td>\n\t");
				tdbuBuffer.append("			</tr>\n\t");
			}
		}
		return tdbuBuffer.toString();
	}
	
	/**
	 * 显示的列
	 * @return
	 */
	private String getListPageShowCloumns(List<ColumnData> columnDatas) {
		StringBuffer buffer = new StringBuffer();
		for (ColumnData columnData : columnDatas) {
			buffer.append("						<th>"+columnData.getColumnComment()+"</th>\n\t");
		}
		return buffer.toString();
	}
	
	/**
	 * 显示的内容.
	 * @param columnDatas
	 * @return
	 */
	private Object getListPageShowContent(List<ColumnData> columnDatas,String tableName,String lowerName) {
		StringBuffer buffer = new StringBuffer();
		String unique =  "${key."+columnDatas.iterator().next().getJavaColumnName()+"}"; //objId
		int count = columnDatas.size();
		buffer.append("					<td align=\"center\" >\n\t");
		buffer.append("					<input type=\"checkbox\" name=\"check\" value=\""+unique+"\" />\n\t");
		buffer.append("					</td>\n\t");
		buffer.append("					<td align=\"center\" ><a href=\"<%=request.getContextPath()%>/background/"+lowerName+"/findById.html?objId="+ unique +"&type=0\">${key."+columnDatas.iterator().next().getJavaColumnName()+"}</a></td>\n\t");
		StringBuffer sb = new StringBuffer(); 
		ColumnData columnData = null;
		for (int i = 1; i < count; i++) {
			columnData = columnDatas.get(i);
			/* 20156-09-28
			//时间的设置.
			if (columnData.getColumnType().equalsIgnoreCase("Date") || columnData.getColumnType().contains("time")) {
				sb.append("					<td align=\"center\" >");
				sb.append("<fmt:formatDate value=\"${key."+columnData.getJavaColumnName()+"}\" pattern=\"yyyy-MM-dd HH:MM:ss\"/>");
				sb.append("</td>\n\t");
			}else {
				sb.append("					<td align=\"center\" >${key."+columnData.getJavaColumnName()+"}</td>\n\t");
			}
			*/
			
			if ("Date".equalsIgnoreCase(columnData.getColumnType()) || columnData.getColumnType().contains("time")) {
				sb.append("					<td align=\"center\" >${fn:substring(key."+columnData.getJavaColumnName()+",0,19)}</td>\n\t");
			}else {
				sb.append("					<td align=\"center\" >${key."+columnData.getJavaColumnName()+"}</td>\n\t");
			}
		}
		buffer.append(sb.toString());
		buffer.append("					<td align=\"center\" >\n\t");
		buffer.append("					\n\t");
		buffer.append("					<sec:authorize ifAnyGranted=\"ROLE_"+tableName+"_info\">"+"\n\t");
		buffer.append("						<img src=\"<%=request.getContextPath()%>/images/admin.gif\" width=\"10\" height=\"10\" />\n\t");
		buffer.append("						<a href=\"<%=request.getContextPath()%>/background/"+lowerName+"/findById.html?objId="+unique+"&type=0\">"+"\n\t");
		buffer.append("						详细信息</a>\n\t");
		buffer.append("					</sec:authorize>\n\t");
		buffer.append("					<sec:authorize ifAnyGranted=\"ROLE_"+tableName+"_edit\">"+"\n\t");
		buffer.append("						<img src=\"<%=request.getContextPath()%>/images/edt.gif\" width=\"12\" height=\"12\" />\n\t");
		buffer.append("						<a href=\"<%=request.getContextPath()%>/background/"+lowerName+"/findById.html?objId="+unique+"&type=1\">编辑</a>\n\t");
		buffer.append("					</sec:authorize>\n\t");
		buffer.append("					<sec:authorize ifAnyGranted=\"ROLE_"+tableName+"_delete\">"+"\n\t");
		buffer.append("						<img src=\"<%=request.getContextPath()%>/images/del.gif\" width=\"16\" height=\"16\" />\n\t");
		buffer.append("						<a href=\"javascript:void(0);\" onclick=\"deleteId('<%=request.getContextPath()%>/background/"+lowerName+"/deleteById.html','"+unique+"')\">删除</a>"+"\n\t");
		buffer.append("					</sec:authorize>\n\t");
		buffer.append("					\n\t");
		buffer.append("					</td>");
		
		return buffer.toString();
	}	
}

