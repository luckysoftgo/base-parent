package com.application.base.utils.freemaker;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * 根据模板内容去解析
 * 
 * StringTemplateLoader
 *
 * @author 孤狼
 *
 */
public class StringTemplateLoader implements TemplateLoader {

	/**
	 * 模板内容
	 */
	private String templateContent;

	public StringTemplateLoader(String templateContent) {
		this.templateContent = templateContent;
		if (templateContent == null) {
			this.templateContent = "";
		}
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(templateContent);
	}

	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return (Reader) templateSource;
	}

}
