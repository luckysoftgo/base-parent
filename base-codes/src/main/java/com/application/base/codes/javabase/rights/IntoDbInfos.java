package com.application.base.codes.javabase.rights;

import com.application.base.codes.javabase.db.MysqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 放入DB
 * @author 孤狼
 */
public class IntoDbInfos {

	/**
	 * 完善功能项.
	 * @param tableName
	 * @param codeName
	 * @param codePrefix
	 */
	public static void addMenus(String tableName,String codeName,String codePrefix) {
		try {
			List<SystemSource> systemSource = new ArrayList<SystemSource>();
			List<SystemSourceRole> ssrRoles = new ArrayList<SystemSourceRole>();
			int index = getMaxSid();
			String className = getTablesNameToClassName(tableName);
			//文件的路径.
			String lowerName = className.substring(0, 1).toLowerCase()+ className.substring(1, className.length());
			
			//资源.
			SystemSource source1 = new SystemSource(index+1,codeName,1010,tableName,"0",tableName,index+1,codeName);
			SystemSource source2 = new SystemSource(index+2,codePrefix+"列表",index+1,tableName+"_find","1","/background/"+lowerName+"/find.html",index+2,codePrefix+"列表");
			SystemSource source3 = new SystemSource(index+3,"添加"+codePrefix,index+2,tableName+"_add","2","/background/"+lowerName+"/add.html",index+3,"添加"+codePrefix);
			SystemSource source4 = new SystemSource(index+4,"编辑"+codePrefix,index+2,tableName+"_edit","2","/background/"+lowerName+"/findById.html",index+4,"编辑"+codePrefix);
			SystemSource source5 = new SystemSource(index+5,"删除"+codePrefix,index+2,tableName+"_delete","2","/background/"+lowerName+"/deleteById.html",index+5,"删除"+codePrefix);
			SystemSource source6 = new SystemSource(index+6,"详细信息",index+2,tableName+"_info","2",tableName+"_info",index+6,"详细信息");
			
			systemSource.add(source1); systemSource.add(source2);	systemSource.add(source3);
			systemSource.add(source4); systemSource.add(source5);	systemSource.add(source6);
			
			//关联.
			SystemSourceRole ssrRoles1 = new SystemSourceRole(index+1,1);
			SystemSourceRole ssrRoles2 = new SystemSourceRole(index+2,1);
			SystemSourceRole ssrRoles3 = new SystemSourceRole(index+3,1);
			SystemSourceRole ssrRoles4 = new SystemSourceRole(index+4,1);
			SystemSourceRole ssrRoles5 = new SystemSourceRole(index+5,1);
			SystemSourceRole ssrRoles6 = new SystemSourceRole(index+6,1);
			ssrRoles.add(ssrRoles1); ssrRoles.add(ssrRoles2); ssrRoles.add(ssrRoles3);
			ssrRoles.add(ssrRoles4); ssrRoles.add(ssrRoles5); ssrRoles.add(ssrRoles6);
			
			boolean result = addSystemSource(systemSource);
			if (result) {
				addSystemSourceRole(ssrRoles);
			}
			System.out.println("注册功能表单完成!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 获得资源表最大的资源id
	 * @return
	 */
	private static int getMaxSid(){
		try {
			Connection connection = MysqlConnection.newConnection();
			String sql = "select max(id) from system_source ";
			Statement stmt= connection.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			if (res.next()) {
				return res.getInt(1);
			}
			MysqlConnection.closeConnection(connection);
		}
		catch (Exception e) {
			return -1;
		}
		return -1;
	}
	
	/**
	 * 添加资源的 sql 拼装.
	 * @param systemSources
	 * @return
	 */
	private static boolean addSystemSource(List<SystemSource> systemSources) {
		try {
			Connection connection = MysqlConnection.newConnection();
			Statement stmt = connection.createStatement();
			for (SystemSource source : systemSources) {
				StringBuffer sql = new StringBuffer( "insert into SYSTEM_SOURCE (source_name,parent_id,source_key,source_type,source_url,source_level,description) values (");
				sql.append("'"+source.getSourceName()+"',");
				sql.append(source.getParentId()+",");
				sql.append("'"+source.getSourceKey()+"',");
				sql.append("'"+source.getSourceType()+"',");
				sql.append("'"+source.getSourceUrl()+"',");
				sql.append(source.getSourceLevel()+",");
				sql.append("'"+source.getDescription()+"'");
				sql.append(");");
				stmt.addBatch(sql.toString());
			}
			stmt.executeBatch();
			stmt.close();
			MysqlConnection.closeConnection(connection);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加资源角色的 sql 拼装.
	 * @param ssrRoles
	 * @return
	 */
	private static void addSystemSourceRole(List<SystemSourceRole> ssrRoles) {
		try {
			Connection connection = MysqlConnection.newConnection();
			Statement stmt = connection.createStatement();
			for (SystemSourceRole ssrRole : ssrRoles) {
				StringBuffer sql = new StringBuffer( "insert into SYSTEM_SOURCE_ROLE (source_id,role_id) values (");
				sql.append(ssrRole.getSourceId()+",");
				sql.append(ssrRole.getRoleId());
				sql.append(");");
				stmt.addBatch(sql.toString());
			}
			
			stmt.executeBatch();
			stmt.close();
			MysqlConnection.closeConnection(connection);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得类名字.
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getTablesNameToClassName(String tableName)
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
	
}
