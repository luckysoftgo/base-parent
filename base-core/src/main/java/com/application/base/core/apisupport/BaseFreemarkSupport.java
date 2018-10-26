package com.application.base.core.apisupport;

import java.io.File;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * freemark模版生成支持类，需要使用时注入，调用creatResult即可
 * 模版必须放在类路径的templates文件夹中
 * BaseFreemarkSupport.java
 * @author 孤狼
 */
@Service
public class BaseFreemarkSupport {
	
	/**
	 * template 模板
	 */
	private Configuration cfg = null;

	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}
	
	/**
	 * 创建一个FreeMarker实例
	 * @throws IOException
	 */
	@PostConstruct
	private void start() throws IOException{
		cfg = new Configuration(Configuration.VERSION_2_3_21);
		// 指定FreeMarker模板文件的位置
		String path=this.getClass().getClassLoader().getResource("/").getPath()+"templates";
		cfg.setDirectoryForTemplateLoading(new File(path));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	
	/**
	 *  输出
	 * @param response
	 * @param result 输出结果
	 * @param teplateFileName 模版文件名，文件需放在模版必须放在类路径的templates文件夹中
	 * @throws IOException
	 */
	public void output(HttpServletResponse response,Object result,String teplateFileName) throws IOException{

		// 获取模板文件
		Template t = cfg.getTemplate(teplateFileName);
		// 使用模板文件的Charset作为本页面的charset
		// 使用text/html MIME-type
		response.setContentType("text/html; charset=" + t.getEncoding());
		Writer out = response.getWriter();
		// 合并数据模型和模板，并将结果输出到out中
		try {
			// 往模板里写数据
			t.process(result, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
