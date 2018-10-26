package com.application.base.utils.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @desc 公共严重类.
 * @author 孤狼.
 */
public class CommonVaildUtil {
	
	/**
	 * 校验输入.
	 * @param vailParams
	 * @param validColoums
	 * @param message
	 * @return
	 */
	public Map<String,Object> validParams(Map<String,Object> vailParams, Set<String> validColoums,String message){
		Map<String,Object> resultMap = new HashMap<>(16);
		if (message==null || message.length()==0){
			message = "输入的值为空,请按要求输入";
		}
		for (Map.Entry<String,Object>  entry : vailParams.entrySet()) {
			String key = entry.getKey();
			String value = BaseStringUtil.stringValue(entry.getValue());
			if (validColoums.contains(key)){
				if (BaseStringUtil.isEmpty(value)){
					resultMap.put(key,message);
				}
			}
		}
		return resultMap;
	}
}
