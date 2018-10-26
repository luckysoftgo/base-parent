package com.application.base.common.result;

import java.io.InputStream;

/**
 * @desc 结果消息容器接口
 * @author 孤狼
 */
public interface MessageContext {
	
	/**
	 * 通过键值获取结果信息对象
	 * @param key
	 * @return
	 */
	ResultInfo getResultInfo(String key);

	/**
	 * 通过路径加载结果消息
	 * @param path
	 */
	void addMessageResource(String path);

	/**
	 * 通过输入流加载结果消息
	 * @param in
	 */
	void addMessageResourceFromStream(InputStream in);

}

