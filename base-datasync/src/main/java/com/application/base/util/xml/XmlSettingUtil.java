package com.application.base.util.xml;

import com.application.base.conts.DataConstant;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


/**
 * @Author: 孤狼.
 * @desc: xml解析操作.
 */
public class XmlSettingUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XmlSettingUtil.class.getName());
	
	/**
	 * 获取根节点的id
	 * @param content
	 * @param isPath
	 * @return
	 * @throws IOException
	 */
	public static String getSettingId(String content,boolean isPath) throws IOException {
		Element root=null;
		if (isPath){
			root = getPathRoot(content);
		}else {
			root = getStrRoot(content);
		}
		return root.attributeValue("settingId");
	}
	
	/**
	 * 获得根信息.
	 *
	 * @param xmlContent
	 * @return
	 */
	private static Element getStrRoot(String xmlContent) throws IOException {
		if (logger.isDebugEnabled()){
			logger.debug("xml content:"+xmlContent);
		}
		if (xmlContent == null || xmlContent.length() == 0) {
			return null;
		}
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			InputStream stream = new ByteArrayInputStream(xmlContent.getBytes(DataConstant.ENCODING));
			document = sax.read(stream);
			document.setXMLEncoding(DataConstant.ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document.getRootElement();
	}
	
	/**
	 * 获得根信息.
	 *
	 * @param filePath
	 * @return
	 */
	private static Element getPathRoot(String filePath) throws IOException {
		if (logger.isDebugEnabled()){
			logger.debug("xml filePath:"+filePath);
		}
		if (filePath == null) {
			return null;
		}
		File file = new File(filePath);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			document = sax.read(in);
			document.setXMLEncoding(DataConstant.ENCODING);
			return document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return null;
	}
	
	/**
	 * 获得data的列表信息.
	 *
	 * @param filePath
	 */
	public static LinkedList<ExtendInfo> getExtendsInfo(String filePath,String settingId) throws IOException {
		Element root = getPathRoot(filePath);
		Element convert = root.element("convert");
		Element datas = convert.element("extends");
		if (datas==null){
			return null;
		}
		LinkedList<ExtendInfo> tables = new LinkedList<>();
		List<Element> instance = datas.elements("extend");
		for (Element element : instance) {
			ExtendInfo extendInfo = new ExtendInfo();
			extendInfo.setApiKey(element.attributeValue("apiKey"));
			extendInfo.setClassPath(element.attributeValue("classPath"));
			extendInfo.setSetttingId(settingId);
			tables.add(extendInfo);
		}
		return tables;
	}
	
	
	/**
	 * 获得data的列表信息.
	 *
	 * @param filePath
	 */
	public static LinkedList<TableInfo> getDatasInfo(String filePath) throws IOException {
		Element root = getPathRoot(filePath);
		Element convert = root.element("convert");
		Element datas = convert.element("datas");
		LinkedList<TableInfo> tables = new LinkedList<>();
		List<Element> instance = datas.elements("data");
		for (Element element : instance) {
			TableInfo tableInfo = new TableInfo();
			tableInfo.setApiKey(element.attributeValue("apiKey"));
			tableInfo.setKey(element.attributeValue("key"));
			tableInfo.setUniqueKey(element.attributeValue("uniqueKey"));
			tableInfo.setPath(element.attributeValue("path"));
			String dataKey = element.attributeValue("dataKey");
			tableInfo.setDataKey(dataKey.split(","));
			tableInfo.setDataArray(element.attributeValue("dataArray"));
			tableInfo.setTableName(element.attributeValue("tableName"));
			tableInfo.setComments(element.attributeValue("comments"));
			tableInfo.setGlobal(element.attributeValue("global"));
			tableInfo.setAutoPk(element.attributeValue("autoPk"));
			String columnLen = element.attributeValue("columnLen");
			if (StringUtils.isEmpty(columnLen)){
				columnLen = "0";
			}
			tableInfo.setColumnLen(Integer.parseInt(columnLen));
			String textField = element.attributeValue("textField");
			tableInfo.setTextField(textField.split(","));
			tableInfo.setPrimKey(element.attributeValue("primKey"));
			tableInfo.setPrimType(element.attributeValue("primType"));
			String primLen = element.attributeValue("primLen");
			if (StringUtils.isEmpty(primLen)) {
				primLen = "0";
			}
			tableInfo.setPrimLen(Integer.parseInt(primLen));
			//列信息.
			LinkedList<ColumnInfo> columns = new LinkedList<>();
			List<Element> fields = element.elements("field");
			if (fields!=null && fields.size()>0){
				for (Element field : fields) {
					ColumnInfo info = new ColumnInfo();
					info.setName(field.attributeValue("name"));
					info.setType(field.attributeValue("type"));
					info.setLength(field.attributeValue("length"));
					info.setDefaultValue(field.attributeValue("defaultValue"));
					info.setComments(field.attributeValue("comments"));
					info.setRequired(field.attributeValue("required"));
					columns.add(info);
				}
				tableInfo.setColumns(columns);
			}
			//二级项目信息.
			Element items = element.element("items");
			if (items==null){
				tableInfo.setItems(false);
			}else{
				String deleteItem = element.attributeValue("deleteItem");
				if (StringUtils.isNotBlank(deleteItem)){
					tableInfo.setDeleteItem(deleteItem.split(","));
				}
				tableInfo.setItems(true);
				tableInfo.setItemInfos(getItemsInfo(tableInfo,items));
			}
			tables.add(tableInfo);
		}
		return tables;
	}
	
	/**
	 * 获取二级配置信息.
	 * @param tableInfo
	 * @param items
	 * @return
	 */
	private static LinkedList<ItemInfo> getItemsInfo(TableInfo tableInfo,Element items) {
		LinkedList<ItemInfo> itemInfos = new LinkedList<>();
		List<Element> itemList = items.elements("data-item");
		int index=0;
		if (itemList!=null && itemList.size()>0){
			for (Element item : itemList) {
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setApiKey(tableInfo.getApiKey());
				itemInfo.setKey(tableInfo.getKey());
				itemInfo.setUniqueKey(item.attributeValue("uniqueKey"));
				itemInfo.setPath(item.attributeValue("path"));
				String dataKey = item.attributeValue("dataKey");
				itemInfo.setDataKey(dataKey.split(","));
				itemInfo.setDataArray(item.attributeValue("dataArray"));
				String tableName=item.attributeValue("tableName");
				if (StringUtils.isEmpty(tableName)){
					itemInfo.setTableName(tableInfo.getTableName()+"_item"+index);
					index++;
				}else {
					itemInfo.setTableName(tableName);
				}
				itemInfo.setComments(item.attributeValue("comments"));
				itemInfo.setGlobal(item.attributeValue("global"));
				itemInfo.setAutoPk(item.attributeValue("autoPk"));
				String columnLen = item.attributeValue("columnLen");
				if (StringUtils.isEmpty(columnLen)){
					columnLen = "0";
				}
				itemInfo.setColumnLen(Integer.parseInt(columnLen));
				String textField = item.attributeValue("textField");
				itemInfo.setTextField(textField.split(","));
				itemInfo.setPrimKey(item.attributeValue("primKey"));
				itemInfo.setPrimType(item.attributeValue("primType"));
				String primLen = item.attributeValue("primLen");
				if (StringUtils.isEmpty(primLen)) {
					primLen = "0";
				}
				itemInfo.setPrimLen(Integer.parseInt(primLen));
				//列信息.
				LinkedList<ColumnInfo> columns = new LinkedList<>();
				List<Element> fields = item.elements("field");
				if (fields!=null && fields.size()>0){
					for (Element field : fields) {
						ColumnInfo info = new ColumnInfo();
						info.setName(field.attributeValue("name"));
						info.setType(field.attributeValue("type"));
						info.setLength(field.attributeValue("length"));
						info.setDefaultValue(field.attributeValue("defaultValue"));
						info.setComments(field.attributeValue("comments"));
						info.setRequired(field.attributeValue("required"));
						columns.add(info);
					}
					itemInfo.setColumns(columns);
				}
				itemInfos.add(itemInfo);
			}
		}
		return itemInfos;
	}
	
	
	/**
	 * 获得data的列表信息.
	 *
	 * @param filePath
	 */
	public static DestDbInfo getDestDbInfo(String filePath) throws IOException {
		Element root = getPathRoot(filePath);
		Element dest = root.element("dest");
		DestDbInfo dbInfo = new DestDbInfo();
		dbInfo.setUrl(dest.element("url").getText());
		dbInfo.setUsername(dest.element("username").getText());
		dbInfo.setPassword(dest.element("password").getText());
		dbInfo.setDbtype(dest.element("dbtype").getText());
		dbInfo.setDbname(dest.element("dbname").getText());
		dbInfo.setDriver(dest.element("driver").getText());
		return dbInfo;
	}
	
	/**
	 * 获得data的列表信息.
	 *
	 * @param filePath
	 */
	public static OrginInfo getOrginInfo(String filePath) throws IOException {
		Element root = getPathRoot(filePath);
		Element dest = root.element("orgin");
		OrginInfo orgin = new OrginInfo();
		orgin.setSourceDesc(dest.attributeValue("name"));
		Element url = dest.element("url");
		orgin.setUrl(url.getText());
		Element auth = dest.element("auth");
		orgin.setClientId(auth.attributeValue("clientId"));
		orgin.setClientSecret(auth.attributeValue("clientSecret"));
		Element uflag = dest.element("uflag");
		orgin.setReqUrl(uflag.attributeValue("reqUrl"));
		orgin.setCtype(uflag.attributeValue("ctype"));
		return orgin;
	}
}
