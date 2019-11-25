package com.application.base.sync.core;

import com.application.base.sync.util.sql.ExecuateDbPrivder;
import com.application.base.sync.util.xml.DestDbInfo;
import com.application.base.sync.util.xml.ExtendInfo;
import com.application.base.sync.util.xml.OrginInfo;
import com.application.base.sync.util.xml.XmlDataInfo;
import com.application.base.sync.util.xml.XmlSettingUtil;
import com.google.common.collect.Maps;
import com.application.base.sync.conts.DataConstant;
import com.application.base.sync.util.xml.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 孤狼
 * @NAME: SyncContext
 * @DESC: 内存存放配置信息.
 **/
@Component
public class SyncContextPrivder {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 存储 xml 中配置信息.
	 */
	final ConcurrentHashMap<String, XmlDataInfo> settingIdMap = new ConcurrentHashMap();
	/**
	 * 存储 xml 中配置信息.
	 */
	final ConcurrentHashMap<String, Object> context = new ConcurrentHashMap();
	
	@Autowired
	private ExecuateDbPrivder execuateDbPrivder;
	
	/**
	 * 清理数据.
	 */
	public void clearContext(){
		context.clear();
	}
	
	/**
	 * 清理数据.
	 */
	public void clearSetting(){
		settingIdMap.clear();
	}
	
	/**
	 * 存放数据.
	 * @param key
	 * @param object
	 */
	public void put( String key,  Object object){
		context.put(key,object);
	}
	
	/**
	 * 获取数据.
	 * @param key
	 */
	public Object get( String key){
		return context.get(key);
	}
	
	/**
	 * 存放数据.
	 * @param key
	 * @param dataInfo
	 */
	public void putSetting( String key,  XmlDataInfo dataInfo){
		settingIdMap.put(key,dataInfo);
	}
	
	/**
	 * 获取数据.
	 * @param key
	 */
	public XmlDataInfo getSetting( String key){
		return settingIdMap.get(key);
	}
	
	/**
	 * 读取指定配置文件的内容.
	 */
	public void initUsedInfo(final String xmlPath, String srouceId) throws Exception{
		if (StringUtils.isEmpty(xmlPath)){
			logger.error("传递的xmlPath:{}为空!",xmlPath);
			throw new Exception("传递的xmlPath为空!");
		}
		if (!isXml(new File(xmlPath))){
			logger.error("传递的文件非xml文件!,文件为:{}",xmlPath);
			throw new Exception("传递的文件非xml文件!");
		}
		XmlDataInfo dataInfo = new XmlDataInfo();
		String settingId = null;
		try {
			if (StringUtils.isNotBlank(srouceId)) {
				settingId = srouceId;
			} else {
				settingId = XmlSettingUtil.getSettingId(xmlPath,true);
			}
			dataInfo.setSettingId(settingId);
			put(settingId,settingId);
		} catch (IOException e) {
			logger.error("读取xml中的settingId信息出错了");
			e.printStackTrace();
		}
		try {
			OrginInfo orginInfo = XmlSettingUtil.getOrginInfo(xmlPath);
			dataInfo.setOrginInfo(orginInfo);
			put(DataConstant.ORGIN_INFO,orginInfo);
			put(settingId+DataConstant.SPLIT_TAG+DataConstant.ORGIN_INFO,orginInfo);
		} catch (IOException e) {
			logger.error("读取xml中的orgin信息出错了");
			e.printStackTrace();
		}
		try {
			DestDbInfo destDbInfo = XmlSettingUtil.getDestDbInfo(xmlPath);
			dataInfo.setDestDbInfo(destDbInfo);
			put(DataConstant.DESTDB_INFO,destDbInfo);
			put(settingId+DataConstant.SPLIT_TAG+DataConstant.DESTDB_INFO,destDbInfo);
			intDBInfo(destDbInfo);
		} catch (IOException e) {
			logger.error("读取xml中的dest信息出错了");
			e.printStackTrace();
		}
		try {
			LinkedList<ExtendInfo> extendInfos = XmlSettingUtil.getExtendsInfo(xmlPath,settingId);
			if (extendInfos!=null){
				dataInfo.setExtendInfos(extendInfos);
				put(settingId+DataConstant.SPLIT_TAG+DataConstant.EXTEND_LIST,extendInfos);
				put(DataConstant.EXTEND_LIST,extendInfos);
			}
		}catch (Exception e){
			logger.error("读取xml中的扩展的信息出错了");
			e.printStackTrace();
		}
		try {
			LinkedList<TableInfo> tableInfos = XmlSettingUtil.getDatasInfo(xmlPath);
			dataInfo.setTableInfos(tableInfos);
			List<Map<String, String>> keys = new ArrayList<>();
			for (TableInfo table : tableInfos) {
				Map<String, String> keyMap = Maps.newHashMap();
				keyMap.put(DataConstant.KEY,table.getKey());
				keyMap.put(DataConstant.UNIQUEKEY,table.getUniqueKey());
				keyMap.put(DataConstant.APIKEY,table.getApiKey());
				keyMap.put(table.getUniqueKey(), table.getApiKey());
				keys.add(keyMap);
				put(table.getKey(),table);
				put(table.getApiKey(),table);
				put(table.getUniqueKey(),table);
				put(settingId+DataConstant.SPLIT_TAG+table.getKey(),table);
				put(settingId+DataConstant.SPLIT_TAG+table.getApiKey(),table);
				put(settingId+DataConstant.SPLIT_TAG+table.getUniqueKey(),table);
			}
			//keyList
			put(settingId+DataConstant.SPLIT_TAG+DataConstant.KEY_LIST,keys);
			put(DataConstant.KEY_LIST,keys);
			//tableInfos
			put(settingId+DataConstant.SPLIT_TAG+DataConstant.TABLE_INFOS,tableInfos);
			put(DataConstant.TABLE_INFOS,tableInfos);
		} catch (IOException e) {
			logger.error("读取xml中的表的信息出错了");
			e.printStackTrace();
		}
		/**
		 * 缓存中放入信息.
		 */
		putSetting(settingId,dataInfo);
	}
	
	/**
	 * 初始化连接池.
	 * @param destDbInfo
	 */
	private void intDBInfo(DestDbInfo destDbInfo) {
		boolean result = execuateDbPrivder.initPool(destDbInfo);
		if (result){
			logger.info("数据链接池创建成功!");
		}
	}
	
	/**
	 * 是否是xml
	 * @param file
	 * @return
	 */
	public boolean isXml(File file){
		boolean flag = false;
		try {
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (suffix.equalsIgnoreCase("xml")){
				flag = true;
			}else{
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 获得orgin信息.
	 */
	public OrginInfo getOrgin(String settingId){
		XmlDataInfo dataInfo = settingIdMap.get(settingId);
		if (dataInfo!=null){
			return dataInfo.getOrginInfo();
		}
		return null;
	}
	
	/**
	 * 获得orgin信息.
	 */
	public Map<String,String> getMapOrgin() throws Exception{
		OrginInfo orginInfo = (OrginInfo) get(DataConstant.ORGIN_INFO);
		return orginMap(orginInfo);
	}
	
	/**
	 * 获得orgin信息.
	 */
	public Map<String,String> getMapOrgin(final String settingId) throws Exception{
		OrginInfo orginInfo = (OrginInfo) get(settingId+DataConstant.SPLIT_TAG+DataConstant.ORGIN_INFO);
		return orginMap(orginInfo);
	}
	
	private Map<String, String> orginMap(OrginInfo orginInfo) throws Exception {
		if (orginInfo == null) {
			throw new Exception("读取xml中配置orgin的信息出错!");
		} else {
			Map<String, String> result = new HashMap<>();
			result.put("name", orginInfo.getSourceDesc());
			result.put("url", orginInfo.getUrl());
			result.put("clientId", orginInfo.getClientId());
			result.put("clientSecret", orginInfo.getClientSecret());
			result.put("reqUrl", orginInfo.getReqUrl());
			return result;
		}
	}
	
	/**
	 * 获得dest信息.
	 */
	public DestDbInfo getDest(String settingId){
		XmlDataInfo dataInfo = settingIdMap.get(settingId);
		if (dataInfo!=null){
			return dataInfo.getDestDbInfo();
		}
		return null;
	}
	
	/**
	 * 获得tables信息.
	 */
	public List<TableInfo> getTables(String settingId){
		XmlDataInfo dataInfo = settingIdMap.get(settingId);
		if (dataInfo!=null){
			return dataInfo.getTableInfos();
		}
		return null;
	}
}
