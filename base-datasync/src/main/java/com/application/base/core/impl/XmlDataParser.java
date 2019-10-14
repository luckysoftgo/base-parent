package com.application.base.core.impl;

import com.application.base.util.sql.PkProvider;
import com.application.base.util.xml.TableInfo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @author : 孤狼
 * @NAME: XmlDataParser
 * @DESC: 处理xml
 **/
@Component
public class XmlDataParser extends CommonDataParser{
	

	public LinkedList<String> getInsertSql(String xmlContent,String companyId, TableInfo tableInfo) {
		return null;
	}
	
	public LinkedList<String> getInsertSql(String xmlContent,String companyId, TableInfo tableInfo, PkProvider provider) {
		return null;
	}

	public LinkedList<String> getCreateTableSql(String xmlContent, TableInfo tableInfo) {
		return null;
	}
}
