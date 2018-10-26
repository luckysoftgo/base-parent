package com.application.base.codes.mongo.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.application.base.codes.mongo.factory.MonGoCodeGenerateFactory;


public class CommonPageParser {

	private static VelocityEngine vEngine;
	
	private static final String CONTENT_ENCODING = "UTF-8";
	
	private static final Log log = LogFactory.getLog(CommonPageParser.class);

	private static boolean isReplace = true;

	static {
		try {
			
			String templateBasePath = MonGoCodeGenerateFactory.getProjectPath() + "/src/main/resources/template/mongo";
			
			Properties properties = new Properties();
			
			properties.setProperty("resource.loader", "file");
			
			properties.setProperty("file.resource.loader.description","Velocity File Resource Loader");
			
			properties.setProperty("file.resource.loader.path",templateBasePath);
			
			properties.setProperty("file.resource.loader.cache", "true");
			
			properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
			
			properties.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.Log4JLogChute");
			
			properties.setProperty("runtime.log.logsystem.log4j.logger","org.apache.velocity");
			
			properties.setProperty("directive.set.null.allowed", "true");
			
			VelocityEngine velocityEngine = new VelocityEngine();
			
			velocityEngine.init(properties);
			
			vEngine = velocityEngine;
			
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void WriterPage(VelocityContext context, String templateName,
			String fileDirPath, String targetFile) {
		try {
			File file = new File(fileDirPath + targetFile);
			if (!(file.exists())) {
				new File(file.getParent()).mkdirs();
			} else if (isReplace) {
				log.info("替换文件:" + file.getAbsolutePath());
			}

			Template template = vEngine.getTemplate(templateName, CONTENT_ENCODING);
			
			FileOutputStream fos = new FileOutputStream(file);
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
			
			template.merge(context, writer);
			
			writer.flush();
			writer.close();
			writer=null;
			
			fos.close();
			fos=null;
			
			log.info("生成文件：" + file.getAbsolutePath());
		} catch (Exception e) {
			log.error(e);
		}
	}
}
