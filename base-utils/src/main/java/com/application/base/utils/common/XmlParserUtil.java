package com.application.base.utils.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Author: 孤狼.
 * @desc: xml解析操作.
 */
public class XmlParserUtil {
	
	/**
	 * 结果集.
	 */
	public static Map<String,Map<String, String>> xmlDataMaps=new LinkedHashMap<String,Map<String, String>>();
	/**
	 * 节点个数.
	 */
	private static Map<String, String> nodeMap=new HashMap<String,String>();
	/**
	 * 编码设置
	 */
	private static final String ENCODING="UTF-8";
	/**
	 * 数据集合.
	 */
	private static final String MAPTAG="dataMap";
	/**
	 * tagVal.
	 */
	public static final String TAG="_";
	
	/**
	 * 通过字符串获得结果.
	 * @param strVal:xml 字符串
	 * @param nodeName:节点名称
	 */
	public static void getXmlValByStr(String strVal,String nodeName) {
		if (strVal==null || strVal.length()==0) {
			return;
		}
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			InputStream stream=new ByteArrayInputStream(strVal.getBytes(ENCODING));
			document = sax.read(stream);
			document.setXMLEncoding(ENCODING);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		//获得节点个数.
		getXmlNodes(root,nodeName);
		int nodes=nodeMap.size();
		//初始化map集合
		initMaps(nodes);
		//获得数据.
		getXmlNodesVal(root,nodeName);
	}
	
	/**
	 * 通过流获得结果.
	 * @param stream:流对象
	 * @param nodeName:节点名称
	 */
	public static void getXmlValByStream(InputStream stream,String nodeName) {
		if (stream==null) {
			return;
		}
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
			document = sax.read(stream);
			document.setXMLEncoding(ENCODING);
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		//获得节点个数.
		getXmlNodes(root,nodeName);
		int nodes=nodeMap.size();
		//初始化map集合
		initMaps(nodes);
		//获得数据.
		getXmlNodesVal(root,nodeName);
	}
	
	/**
	 * 通过文件获得对象.
	 * @param filePath:文件绝对路径
	 * @param nodeName:节点名称
	 */
	public static void getXmlValByFile(String filePath,String nodeName) {
		if (filePath==null || filePath.length()==0) {
			return;
		}
		SAXReader sax = new SAXReader();
		File file=new File(filePath);
		Document document = null;
		try {
			document = sax.read(file);
			document.setXMLEncoding(ENCODING);
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		//获得节点个数.
		getXmlNodes(root,nodeName);
		int nodes=nodeMap.size();
		//初始化map集合
		initMaps(nodes);
		//获得数据.
		getXmlNodesVal(root,nodeName);
	}
	
	/**
	 * 初始化map集合.
	 * @param nodes:节点数据.
	 */
	private static void initMaps(int nodes) {
		for (int i = 0; i < nodes; i++) {
			Map<String, String> tempValMap=new LinkedHashMap<>();
			xmlDataMaps.put(MAPTAG+i,tempValMap);
		}
	}
	
	/**
	 * 递归遍历所有子节点value
	 * @param node
	 * @param nodeName
	 */
	@SuppressWarnings("unchecked")
	private static void getXmlNodesVal(Element node,String nodeName) {
		String tagName=node.getName();
		String tagVal=node.getTextTrim();
		String attrName ="";
		String attrValue ="";
		List<Attribute> listAttrs = node.attributes();
		for (Attribute attribute : listAttrs) {
			attrName=attribute.getName();
			attrValue=attribute.getValue();
		}
		if (tagName.equalsIgnoreCase(nodeName) && attrName.equalsIgnoreCase("ID")) {
			Map<String, String> actualVals=new LinkedHashMap<String, String>();
			String prex=nodeName+TAG;
			String key=attrValue.substring(prex.length(), attrValue.length());
			List<Element> listElement = node.elements();
			for (Element element : listElement) {
				tagName=element.getName();
				tagVal=element.getTextTrim();
				actualVals.put(tagName, tagVal);
			}
			xmlDataMaps.put(MAPTAG+key,actualVals);
		}
		List<Element> listElement = node.elements();
		for (Element element : listElement) {
			getXmlNodesVal(element,nodeName);
		}
	}
	
	/**
	 * 获得节点的个数.
	 * @param root
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static void getXmlNodes(Element root,String nodeName) {
		String tagName=root.getName();
		String attrName ="";
		String attrValue ="";
		List<Attribute> listAttrs = root.attributes();
		for (Attribute attribute : listAttrs) {
			attrName=attribute.getName();
			attrValue=attribute.getValue();
		}
		if (tagName.equalsIgnoreCase(nodeName) && attrName.equalsIgnoreCase("ID") && attrValue.contains(nodeName)) {
			String prex=nodeName+TAG;
			String key=attrValue.substring(prex.length(), attrValue.length());
			nodeMap.put(key,attrValue);
		}
		List<Element> listElement = root.elements();
		for (Element element : listElement) {
			getXmlNodes(element,nodeName);
		}
	}
	
	/**
	 * 清除内存中的数据.
	 */
	public static void clearData() {
		nodeMap.clear();
		xmlDataMaps.clear();
	}

}
