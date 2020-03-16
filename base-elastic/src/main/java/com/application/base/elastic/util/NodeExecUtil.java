package com.application.base.elastic.util;

import com.application.base.elastic.entity.NodeInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: NodeExecUtil
 * @DESC: ExecuteUtil解析配置参数
 **/
public class NodeExecUtil {
	
	/**
	 * 符号分解:
	 */
	private static final String COLON=":";
	private static final String SEMICOLON=",";
	private static final String SCHEMA="http";
	
	/**
	 * 获取node信息
	 * @param serverInfos
	 * @return
	 */
	public static List<NodeInfo> getNodes(String serverInfos){
		if (StringUtils.isBlank(serverInfos)){
			return null;
		}
		List<NodeInfo> nodeInfos =new ArrayList<>();
		String[] instances = serverInfos.split(SEMICOLON);
		for (String instance : instances) {
			String[] server = instance.split(COLON);
			nodeInfos.add(new NodeInfo(server[0],Integer.parseInt(server[1]),SCHEMA));
		}
		return nodeInfos;
	}
}
