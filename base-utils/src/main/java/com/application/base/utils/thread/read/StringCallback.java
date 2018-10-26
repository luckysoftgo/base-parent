package com.application.base.utils.thread.read;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc 读取文件的 callback 函数.
 * @author 孤狼.
 */
public abstract class StringCallback {
	
	/**
	 * 编码方式.
	 */
	private String charsetName;
	/**
	 * 线程池.
	 */
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	/**
	 * 构造函数.
	 * @param charsetName
	 */
	public StringCallback(String charsetName) {
		this.charsetName = charsetName;
	}
	/**
	 * 主方法.
	 * @param data
	 */
	public void mainCallback(String data) {
		executorService.execute(() -> {
			try {
				//需要格式化的时候,格式化,不需要的时候,就不要操作啦.
				callback(new String(data.getBytes("ISO-8859-1"), charsetName));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 主要要实现的回调方法的描述.(public 方便在外部使用的时候调用回调函数)
	 * @param data
	 */
	public abstract void callback(String data);

}
