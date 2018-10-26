package com.application.base.utils.freemaker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc freemarker模板、内容合成工具类 FreeMarkerUtils
 * @author 孤狼
 */
@SuppressWarnings("deprecation")
public class FreeMarkerUtils {

	private Configuration cfg = new Configuration();
	private Template template = null;
	private static final String UTF8 = "UTF-8";

	/**
	 * 以模板内容为参数构造模板工具<br>
	 * 创建一个新的实例 FreeMarkerUtils.
	 * 
	 * @param templateContent
	 */
	public FreeMarkerUtils(String templateContent) {
		cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
		cfg.setDefaultEncoding(UTF8);
		cfg.setOutputEncoding(UTF8);
		try {
			template = cfg.getTemplate("");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * map类型数据-合并模板
	 * 
	 * @param paramMap
	 * @return
	 */
	public String getResult(Map<String, Object> paramMap) {
		StringWriter writer = new StringWriter();
		try {
			template.process(paramMap, writer);
		}
		catch (TemplateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * JSON类型数据-合并模板
	 * 
	 * @param jsonData
	 * @return
	 */
	public String getResult(String jsonData) throws Exception {
		Template template = cfg.getTemplate("");
		Map<String, Object> root = new HashMap<String, Object>(16);
		root.put("json", jsonData);
		StringWriter writer = new StringWriter();
		template.process(root, writer);
		return writer.toString();
	}

	/**
	 * JSON类型数据-合并模板
	 * 
	 * @param jsonData
	 * @return
	 */
	public String getResult(String key, String jsonData) throws Exception {
		Template template = cfg.getTemplate("");
		Map<String, Object> root = new HashMap<String, Object>(16);
		root.put(key, jsonData);
		StringWriter writer = new StringWriter();
		template.process(root, writer);
		return writer.toString();
	}

	/**
	 * 指定内容和数据进行合并
	 * 
	 * @param template
	 * @param obj
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String getResult(String template, Object obj) throws Exception {

		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(template));
		cfg.setDefaultEncoding(UTF8);
		cfg.setOutputEncoding(UTF8);
		try {
			Template templateobj = cfg.getTemplate("");
			templateobj.setEncoding(UTF8);
			StringWriter writer = new StringWriter();
			templateobj.process(obj, writer);
			
			return writer.toString();
			
		}
		catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * map数据替换 @Title: getResultOfMap @return String 返回类型 @throws
	 */
	public String getResultOfMap(Map<String, Object> root) throws Exception {
		// 在servlet中，输出流一定要从response中获取
		StringWriter writer = new StringWriter();
		template.process(root, writer);
		return writer.toString();
	}

}
