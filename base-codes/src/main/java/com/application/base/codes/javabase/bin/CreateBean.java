package com.application.base.codes.javabase.bin;

import com.application.base.codes.javabase.db.MysqlConnection;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc bean 设计.
 * @author 孤狼
 */
public class CreateBean {
	
	private Connection connection = null;
	private String sqltables = " show tables ";
	String url;
	String username;
	String password;
	static String rt = "\r\t";
	private String method;
	private String argv;
	static String selectStr;
	static String from;
	
	/**
	 * 主键
	 */
	static String tablePK = null;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		selectStr = " select ";
		from = " from ";
	}
	
	static String nStr="N",datetime="datetime",time="time",date="date",intStr="int",number="number",floatStr="float",
			doubleStr="double",decimal="decimal",charStr="char",clob="clob",AUTO="AUTO",YES="YES",Y="Y",NO="NO",N="N";
	
	static int num1=6;
	
	public Connection getConnection() throws SQLException {
	    return MysqlConnection.newConnection();
	}

	String[] coloums = new String[]{"id","uuid","isDelete","createTime","createUser","updateTime","updateUser"};
	
	List<String> coloumsList = Arrays.asList(coloums);
	
	public List<String> getTables() throws SQLException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(this.sqltables);
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		rs=null;
		ps.close();
		ps=null;
		con.close();
		con=null;
		return list;
	}

	/**
	 * OK
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnData> getColumnDatas(String tableName, String databaseName)
			throws SQLException {
		String sqlcolumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
				+ tableName
				+ "' "
				+ "and table_schema =  '" + databaseName + "'";

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(sqlcolumns);
		List<ColumnData> columnList = new LinkedList<ColumnData>();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			//sys_user_id;
			String sqlCloumnName = rs.getString(1);
			//sysUserId
			String javaColumnName = turnUpperStr(sqlCloumnName);
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = (rs.getString(6) == null) ? "" : rs.getString(6);
			String nullable = getNullAble(rs.getString(7));
			type = getType(type, precision, scale);

			ColumnData cd = new ColumnData();
			cd.setJavaColumnName(javaColumnName);
			cd.setSqlCloumnName(sqlCloumnName);
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			formatFieldClassType(cd);
			columnList.add(cd);
		}
		this.argv = str.toString();
		this.method = getset.toString();
		rs.close();
		rs=null;
		ps.close();
		ps=null;
		con.close();
		con=null;
		return columnList;
	}

	/**
	 * OK
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getBeanFeilds(String tableName,String databaseName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName,databaseName);
		StringBuffer str = new StringBuffer();
		str.append("private static final long serialVersionUID = 1L;");
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			String name = d.getJavaColumnName();
			//类型
			String type = d.getDataType();
			//注释
			String comment = d.getColumnComment();

			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
			//换行
			String method = maxChar + name.substring(1, name.length());
			getset.append("\r\t");
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
		if (nStr.equalsIgnoreCase(columnt.getNullable())) {
			columnt.setOptionType("required:true");
		}
		if ((datetime.equalsIgnoreCase(fieldType)) || (time.equalsIgnoreCase(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		} else if (date.equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		} else if (intStr.equalsIgnoreCase(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		} else if (number.equalsIgnoreCase(fieldType)) {
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
		} else if ((floatStr.equalsIgnoreCase(fieldType)) || (doubleStr.equalsIgnoreCase(fieldType))|| (decimal.equalsIgnoreCase(fieldType))) {
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
		if (dataType.contains(charStr)){
			dataType = "String";
		}
		else if (dataType.contains(intStr)){
			dataType = "Integer";
		}
		else if (dataType.contains(floatStr)){
			dataType = "Float";
		}
		else if (dataType.contains(doubleStr)){
			dataType = "Double";
		}
		else if (dataType.contains(number)) {
			if ((StringUtils.isNotBlank(scale))&& (Integer.parseInt(scale) > 0)){
				dataType = "BigDecimal";
			}
			else if ((StringUtils.isNotBlank(precision))&& (Integer.parseInt(precision) > num1)){
				dataType = "Long";
			}
			else{
				dataType = "Integer";
			}
		} else if (dataType.contains(decimal)){
			dataType = "BigDecimal";
		}
		/* 2015-09-28
		else if (dataType.contains("date") || dataType.contains("time")){
			dataType = "Date";
		}
		*/
		else if (dataType.contains(date) || dataType.contains(time)){
			dataType = "String";
		}
		else if (dataType.contains(clob)){
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
	public Map<String, Object> getAutoCreateSql(String tableName,String autoType,String lowerName,String databaseName)
			throws Exception {
		Map<String,Object> sqlMap = new HashMap<String,Object>(16);
		List<ColumnData> columnDatas = getColumnDatas(tableName,databaseName);
		String columnFields = getColumnFields(columnDatas);
		String selectOptsOne = getSelectOptsOne(tableName,columnDatas);
		String selectOptsList = selectOptsList(tableName,columnDatas);
		
		//界面展示问题.
		sqlMap.put("tableName", tableName);
		sqlMap.put("listPageCloumns", getListPageShowCloumns(columnDatas));
		sqlMap.put("listPageContent", getListPageShowContent(columnDatas,tableName,lowerName));
		sqlMap.put("addOneContent", getAddPageContent(columnDatas,autoType));
		sqlMap.put("editOneContent", getEditPageContent(columnDatas,autoType));
		sqlMap.put("showOneContent", getShowPageContent(columnDatas));
		
		//sql语句。
		String findById = findObjById(tableName);
		//insert
		String insertOne = getInsertOne(tableName,columnDatas,autoType);
		String insertAll = getInsertAll(tableName,columnDatas,autoType);
		//update
		String updateOne = getUpdateOne(tableName,columnDatas);
		String updateAll = getUpdateAll(tableName);
		//delete
		String deleteById = getDeleteById(tableName);
		String deleteAll = getDeleteAll(tableName);
		
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("selectOptsOne", selectOptsOne);
		sqlMap.put("selectOptsList", selectOptsList);
		//主键
		sqlMap.put("tablePK", turnUpperStr(getTablePK(tableName)));
		
		insertOne = insertOne.replace("#{createTime}", "now()").replace("#{create_time}", "now()").replace("#{updateTime}", "now()").replace("#{update_time}", "now()")
				.replace("#{login_time}", "now()").replace("#{loginTime}", "now()").replace("#{register_time}", "now()").replace("#{registerTime}", "now()")
				.replace("#{operator_time}", "now()").replace("#{operatorTime}", "now()");
		insertAll = insertAll.replace("#{createTime}", "now()").replace("#{create_time}", "now()").replace("#{updateTime}", "now()").replace("#{update_time}", "now()")
				.replace("#{login_time}", "now()").replace("#{loginTime}", "now()").replace("#{register_time}", "now()").replace("#{registerTime}", "now()")
				.replace("#{operator_time}", "now()").replace("#{operatorTime}", "now()");
		
		
		updateOne = updateOne.replace("#{updateTime}", "now()").replace("#{update_time}", "now()")
				.replace("#{login_time}", "now()").replace("#{loginTime}", "now()")
				.replace("#{operator_time}", "now()").replace("#{operatorTime}", "now()");
		
		updateAll = updateAll.replace("#{updateTime}", "now()").replace("#{update_time}", "now()")
				.replace("#{login_time}", "now()").replace("#{loginTime}", "now()")
				.replace("#{operator_time}", "now()").replace("#{operatorTime}", "now()");
	
		sqlMap.put("findById", findById);
		
		sqlMap.put("insertOne", insertOne);
		sqlMap.put("insertAll", insertAll);
		
		sqlMap.put("updateOne", updateOne);
		sqlMap.put("updateAll", updateAll);

		sqlMap.put("deleteById", deleteById);
		sqlMap.put("deleteAll", deleteAll);
		
		tablePK = null;
		return sqlMap;
	}

	/**
	 * 多条插入.
	 *
	 * @param tableName
	 * @param columnDatas
	 * @param autoType
	 * @return
	 */
	private String getInsertAll(String tableName, List<ColumnData> columnDatas,
			String autoType) {
		StringBuffer buffer = new  StringBuffer(" insert into "+tableName +" (");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer javaBuffer = new StringBuffer();
		if (AUTO.equalsIgnoreCase(autoType)) {
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (!getTablePK(tableName).equals(data.getSqlCloumnName())) {
					if (i==columnDatas.size()-1) {
						sqlBuffer.append(data.getSqlCloumnName());
					}else {
						sqlBuffer.append(data.getSqlCloumnName()+",");
					}
				}
			}
			buffer.append(sqlBuffer.toString()).append(")\n\t     values  ");
			buffer.append("\n\t    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">");
			buffer.append("\n\t      ( ");
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (!getTablePK(tableName).equals(data.getSqlCloumnName())) {
					if (i==columnDatas.size()-1) {
						javaBuffer.append("#{item."+data.getJavaColumnName()+"}");
					}else {
						javaBuffer.append("#{item."+data.getJavaColumnName()+"},");
					}
				}
			}
			buffer.append("\n\t         "+javaBuffer.toString());
			buffer.append("\n\t      )");
			buffer.append("\n\t    </foreach>");
			
		}else {
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (i==columnDatas.size()-1) {
					sqlBuffer.append(data.getSqlCloumnName());
				}else {
					sqlBuffer.append(data.getSqlCloumnName()+",");
				}
			}
			
			buffer.append(sqlBuffer.toString()).append(")\n\t     values  ");
			buffer.append("\n\t    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">");
			buffer.append("\n\t      ( ");
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (i==columnDatas.size()-1) {
					javaBuffer.append(" #{item."+data.getJavaColumnName()+"} ");
				}else {
					javaBuffer.append(" #{item."+data.getJavaColumnName()+"}, ");
				}
			}
			buffer.append("\n\t         "+javaBuffer.toString());
			buffer.append("\n\t      )");
			buffer.append("\n\t    </foreach>");
		}
		
		return buffer.toString();
	}

	/**
	 * 单条插入.
	 *
	 * @param tableName
	 * @param columnDatas
	 * @param autoType
	 * @return
	 */
	private String getInsertOne(String tableName, List<ColumnData> columnDatas, String autoType) {
		StringBuffer buffer = new  StringBuffer("insert into "+tableName +" (");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer javaBuffer = new StringBuffer();
		if (AUTO.equalsIgnoreCase(autoType)) {
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (!getTablePK(tableName).equals(data.getSqlCloumnName())) {
					if (i==columnDatas.size()-1) {
						sqlBuffer.append(data.getSqlCloumnName());
						javaBuffer.append("#{"+data.getJavaColumnName()+"}");
					}else {
						sqlBuffer.append(data.getSqlCloumnName()+",");
						javaBuffer.append("#{"+data.getJavaColumnName()+"},");
					}
				}
			}
			buffer.append(sqlBuffer.toString()).append(")\n\t     values \n\t     (");
			buffer.append("\n\t        "+javaBuffer.toString()).append("\n\t     )");
		}else {
			for (int i = 0; i < columnDatas.size(); i++) {
				ColumnData data = columnDatas.get(i);
				if (i==columnDatas.size()-1) {
					sqlBuffer.append(data.getSqlCloumnName());
					javaBuffer.append(" #{"+data.getJavaColumnName()+"} ");
				}else {
					sqlBuffer.append(data.getSqlCloumnName()+",");
					javaBuffer.append(" #{"+data.getJavaColumnName()+"}, ");
				}
			}
			buffer.append(sqlBuffer.toString()).append(")\n\t     values \n\t     (");
			buffer.append("\n\t        "+javaBuffer.toString()).append("\n\t     )");
		}
		
		return buffer.toString();
	}

	/**
	 * 查询条件 one
	 * where元素的作用是会在写入where元素的地方输出一个where，另外一个好处是你不需要考虑where元素里面的条件输出是什么样子的，MyBatis会智能的帮你处理，如果所有的条件都不满足那么MyBatis就会查出所有的记录，如果输出后是and 开头的，MyBatis会把第一个and忽略，当然如果是or开头的，MyBatis也会把它忽略；此外，在where元素中你不需要考虑空格的问题，MyBatis会智能的帮你加上。像上述例子中，如果title=null， 而content != null，那么输出的整个语句会是select * from t_blog where content = #{content}，而不是select * from t_blog where and content = #{content}，因为MyBatis会智能的把首个and 或 or 给忽略
	 * @param tableName
	 * @param columnDatas
	 * @return
	 */
	private String getSelectOptsOne(String tableName,List<ColumnData> columnDatas) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<where> \n\t      1=1  \n\t ");
		String tablePK = turnUpperStr(getTablePK(tableName));
		for (int i = 0; i < columnDatas.size(); ++i) {
			ColumnData data = columnDatas.get(i);
			String columnName = data.getJavaColumnName();
			String sqlCloumn = data.getSqlCloumnName();
			if (columnName.equalsIgnoreCase(tablePK)) {
				continue;
			}else {
				buffer.append("\t  <if test=\"").append(columnName).append(" != null ");
				if ("String".equals(data.getDataType())) {
					buffer.append(" and ").append(columnName).append(" != ''");
				}else if ("Integer".equals(data.getDataType()) || "Float".equals(data.getDataType()) || "Double".equals(data.getDataType()) || "Long".equals(data.getDataType())) {
					buffer.append(" and ").append(columnName).append(" !=-1 ");
				}
				buffer.append(" \">\n\t\t");
				buffer.append("     and "+sqlCloumn + "=#{" + columnName + "} ");
				buffer.append("\n\t      </if>\n\t");
			}
		}
		buffer.append("\t</where>");
		return buffer.toString();
	
	}
	
	/**
	 * 查询条件 list
	 * where元素的作用是会在写入where元素的地方输出一个where，另外一个好处是你不需要考虑where元素里面的条件输出是什么样子的，MyBatis会智能的帮你处理，如果所有的条件都不满足那么MyBatis就会查出所有的记录，如果输出后是and 开头的，MyBatis会把第一个and忽略，当然如果是or开头的，MyBatis也会把它忽略；此外，在where元素中你不需要考虑空格的问题，MyBatis会智能的帮你加上。像上述例子中，如果title=null， 而content != null，那么输出的整个语句会是select * from t_blog where content = #{content}，而不是select * from t_blog where and content = #{content}，因为MyBatis会智能的把首个and 或 or 给忽略
	 * @param tableName
	 * @param columnDatas
	 * @return
	 */
	private String selectOptsList(String tableName,List<ColumnData> columnDatas) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<where> \n\t      1=1  \n\t ");
		String tablePK = turnUpperStr(getTablePK(tableName));
		for (int i = 0; i < columnDatas.size(); ++i) {
			ColumnData data = columnDatas.get(i);
			String columnName = data.getJavaColumnName();
			String sqlCloumn = data.getSqlCloumnName();
			if (columnName.equalsIgnoreCase(tablePK)) {
				continue;
			}else {
				buffer.append("\t  <if test=\"").append("t."+columnName).append(" != null ");
				if ("String".equals(data.getDataType())) {
					buffer.append(" and ").append("t."+columnName).append(" != ''");
				}else if ("Integer".equals(data.getDataType()) || "Float".equals(data.getDataType()) || "Double".equals(data.getDataType()) || "Long".equals(data.getDataType())) {
					buffer.append(" and ").append("t."+columnName).append(" !=-1 ");
				}
				buffer.append(" \">\n\t\t");
				buffer.append("     and "+sqlCloumn + "=#{" + "t."+columnName + "} ");
				buffer.append("\n\t      </if>\n\t");
			}
		}
		buffer.append("\t</where>");
		return buffer.toString();
	}
	
	
	/**
	 * 删除的单个
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getDeleteById(String tableName)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from ").append(tableName).append("  where  ");
		sb.append(getTablePK(tableName)).append(" = #{").append(turnUpperStr(getTablePK(tableName))).append("}");
		return sb.toString();
	}
	
	/**
	 * 删除的单个
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getDeleteAll(String tableName)
			throws SQLException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" delete from  ").append(tableName).append("  where  ");
		buffer.append(getTablePK(tableName)).append(" in ");
		buffer.append("\n\t   <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\" >");
        buffer.append("\n\t     #{item}");
        buffer.append("\n\t   </foreach>");
        
		return buffer.toString();
	}

	/**
	 * 通过主键查找.
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String findObjById(String tableName)
			throws SQLException {
		StringBuffer sb = new StringBuffer(getTablePK(tableName)).append(" = #{").append(turnUpperStr(getTablePK(tableName))).append("}");
		return sb.toString();
	}
	
	/**
	 * SQL COLUMNS
	 * @param columnDatas
	 * @return
	 * @throws SQLException
	 */
	public String getColumnFields(List<ColumnData> columnDatas) throws SQLException {
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < columnDatas.size(); i++) {
			ColumnData data  = columnDatas.get(i);
			if (i==columnDatas.size()-1) {
				buffer.append(data.getSqlCloumnName()+ " as "+ data.getJavaColumnName()+"\n\t");
			}else {
				buffer.append(data.getSqlCloumnName()+ " as "+ data.getJavaColumnName() +",\n\t");
			}
		}
		return buffer.toString();
	}

	public String[] getColumnList(String columns) throws SQLException {
		String[] columnList = columns.split("[|]");
		return columnList;
	}

	/**
	 * 修改全部.
	 *
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getUpdateAll(String tableName) throws SQLException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" update " + tableName + "  set  updateCloumn = #{updateCloumn} ");
		buffer.append("\n\t    where "+ getTablePK(tableName) + "  in  ");
		buffer.append("\n\t    <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\" >");
        buffer.append("\n\t      #{item}");
        buffer.append("\n\t    </foreach>");
		return  buffer.toString();
	}

	/**
	 * 根据条件修改.
	 *
	 * @param tableName
	 * @param columnDatas
	 * @return
	 * @throws SQLException
	 */
	public String getUpdateOne(String tableName,
			List<ColumnData> columnDatas) throws SQLException {
		StringBuffer buffer = new StringBuffer();
		//get select opts
		buffer.append("    \t<set> \n");
		for (int i = 1; i < columnDatas.size(); ++i) {
			ColumnData data = columnDatas.get(i);
			String columnName = data.getJavaColumnName();
			String sqlCloumn = data.getSqlCloumnName();
			buffer.append("        \t <if test=\"").append(columnName).append(" != null ");
			if ("String".equals(data.getDataType())) {
				buffer.append(" and ").append(columnName).append(" != ''");
			}else if ("Integer".equals(data.getDataType()) || "Float".equals(data.getDataType()) || "Double".equals(data.getDataType()) || "Long".equals(data.getDataType())) {
				buffer.append(" and ").append(columnName).append(" !=-1 ");
			}
			buffer.append(" \">\n\t\t");
			
			/* 2015-09-28
			if (i==columnDatas.size()-1) {
				if (sqlCloumn.equals("updateTime") || sqlCloumn.equals("update_time")|| sqlCloumn.equals("operator_time")|| sqlCloumn.equals("operator_time")) {
					buffer.append("      "+sqlCloumn + "= now() \n");
				}else{
					buffer.append("      "+sqlCloumn + "=#{" + columnName + "} \n");
				}
			}else {
				if (sqlCloumn.equals("updateTime") || sqlCloumn.equals("update_time")|| sqlCloumn.equals("operator_time")|| sqlCloumn.equals("operator_time")) {
					buffer.append("      "+sqlCloumn + "= now() , \n");
				}else{
					buffer.append("      "+sqlCloumn + "=#{" + columnName + "}, \n");
				}
			}
			*/
			if (i==columnDatas.size()-1) {
				buffer.append("      "+sqlCloumn + "=#{" + columnName + "} \n");
			}else {
				buffer.append("      "+sqlCloumn + "=#{" + columnName + "}, \n");
			}
			
			buffer.append("        \t</if> \n");
		}
		buffer.append("    \t</set> ");
		String update = " update " + tableName + " \n" + buffer.toString() + " \n\t    where  " + getTablePK(tableName) + " = #{" + turnUpperStr(getTablePK(tableName)) + "}";
		return update;
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
	
	
	private String getTablePK(String tableName) {
		if (tablePK!= null) {
			return tablePK;
		}
		Connection conn = null ;
		try {
			conn = getConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		String  sql= " SELECT TABLE_NAME,COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME <> 'dtproperties' AND TABLE_NAME = '"+tableName+"'";
		String resultPK = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				resultPK = res.getString("COLUMN_NAME");
				tablePK = resultPK;
			}
			res.close();
			res = null;
			
			pstmt.close();
			pstmt=null;
			
			conn.close();
			conn=null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultPK;
	}
	
	private String getNullAble(String nullable) {
		if ((YES.equalsIgnoreCase(nullable)) || (Y.equalsIgnoreCase(nullable))) {
			return Y;
		}
		
		if ((NO.equalsIgnoreCase(nullable)) || (N.equalsIgnoreCase(nullable))) {
			return N;
		}
		
		return null;
	}
	
/********************************************************************** 页面的显示***********************************************************************/
	
	/**
	 * 展示.
	 *
	 * @param columnDatas
	 * @return
	 */
	public String getShowPageContent(List<ColumnData> columnDatas) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			if (coloumsList.contains(columnData.getJavaColumnName())) {
				continue;
			}
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
				tdbuBuffer.append("				${fn:substring(object."+columnData.getJavaColumnName()+",0,19)}\n\t");
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
	public String getEditPageContent(List<ColumnData> columnDatas, String autoType) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			if (coloumsList.contains(columnData.getJavaColumnName())) {
				continue;
			}
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
	@SuppressWarnings("unused")
	public String getAddPageContent(List<ColumnData> columnDatas, String autoType) {
		StringBuffer tdbuBuffer = new StringBuffer();
		int count = columnDatas.size();
		for (int i = 0; i < count; i++) {
			ColumnData columnData = columnDatas.get(i);
			if (coloumsList.contains(columnData.getJavaColumnName())) {
				continue;
			}
			
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
	public String getListPageShowCloumns(List<ColumnData> columnDatas) {
		StringBuffer buffer = new StringBuffer();
		for (ColumnData columnData : columnDatas) {
			if (coloumsList.contains(columnData.getJavaColumnName())) {
				continue;
			}
			buffer.append("						<th>"+columnData.getColumnComment()+"</th>\n\t");
		}
		return buffer.toString();
	}
	
	/**
	 * 显示的内容.
	 * @param columnDatas
	 * @return
	 */
	public Object getListPageShowContent(List<ColumnData> columnDatas, String tableName, String lowerName) {
		StringBuffer buffer = new StringBuffer();
		//objId
		String unique =  "${key."+columnDatas.iterator().next().getJavaColumnName()+"}";
		int count = columnDatas.size();
		buffer.append("					<td align=\"center\" >\n\t");
		buffer.append("					<input type=\"checkbox\" name=\"check\" value=\""+unique+"\" />\n\t");
		buffer.append("					</td>\n\t");
		buffer.append("					<td align=\"center\" ><a href=\"<%=request.getContextPath()%>/background/"+lowerName+"/findById.html?objId="+ unique +"&type=0\">${key."+columnDatas.iterator().next().getJavaColumnName()+"}</a></td>\n\t");
		StringBuffer sb = new StringBuffer();
		ColumnData columnData = null;
		for (int i = 1; i < count; i++) {
			columnData = columnDatas.get(i);
			if (coloumsList.contains(columnData.getJavaColumnName())) {
				continue;
			}
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

