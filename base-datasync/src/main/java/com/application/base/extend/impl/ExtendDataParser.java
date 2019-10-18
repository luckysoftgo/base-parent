package com.application.base.extend.impl;

import com.application.base.core.SyncContextPrivder;
import com.application.base.extend.ExtendParser;
import com.application.base.util.ClassLoadUtils;
import com.application.base.util.sql.ExecuateDbPrivder;
import com.application.base.util.xml.ExtendInfo;
import com.application.base.util.xml.XmlDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author : 孤狼
 * @NAME: ExtendDataParser
 * @DESC: 处理生成的解析
 **/
@Component
public class ExtendDataParser {
	
	@Autowired
	private ExecuateDbPrivder execuateDbPrivder;
	
	@Autowired
	private SyncContextPrivder syncContextPrivder;
	
	/**
	 * 执行操作(烧苗jar文件下的实现,通过具体路径,获得对应的接口实例,通过实例,设置属性获得结果).
	 * @param data
	 * @param extendInfo
	 * @param relationId
	 * @return
	 */
	public boolean executeExtend(String data, ExtendInfo extendInfo, String relationId) {
		String classPath = extendInfo.getClassPath();
		ExtendParser extendParser = null;
		try {
			extendParser = (ExtendParser) Class.forName(classPath).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ClassLoadUtils.loadHttpHeadersFactoryJar();
			try {
				extendParser = (ExtendParser) Class.forName(classPath).newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		boolean result =false;
		if (extendParser!=null){
			extendParser.setApiKey(extendInfo.getApiKey());
			extendParser.setTableNames(extendInfo.getTableName());
			extendParser.setRelationId(relationId);
			extendParser.setDataJson(data);
			LinkedList<String> sqls= extendParser.toInsertSqls();
			String settingId = extendInfo.getSetttingId();
			XmlDataInfo dataInfo = syncContextPrivder.getSetting(settingId);
			if (dataInfo!=null){
				try {
					result=execuateDbPrivder.executeSql(dataInfo.getDestDbInfo(),sqls);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
