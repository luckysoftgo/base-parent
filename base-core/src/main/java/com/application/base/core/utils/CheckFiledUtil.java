package com.application.base.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * @desc 输入检查
 * @author 孤狼
 */
public class CheckFiledUtil {
	
	private static final Log log = LogFactory.getLog(CheckFiledUtil.class);
	
	public static final String I_TYPE = "I";
	
	public static final String U_TYPE = "U";
	
	/**
	 * 不检测的属性集合（属性字段名字集合），和 I_TYPE 搭配使用
	 */
	public Set<String> ignoreFields = new HashSet<String>();
	/**
	 * 只检测的属性集合（属性字段名字集合），和U_TYPE 搭配使用
	 */
	public Set<String> checkFields = new HashSet<String>();
	
	/**
	 *
	 * @param br  br  BindingResult实体，封装了整个检测结果
	 * @param type type 检测类型；I 可以和 ignoreField搭配，过滤掉某些不检测的属性； U 可以和onlyCheckFiled搭配，检测只需要检测的属性
	 * @return
	 */
	public GeneralResult validateFieldVO(BindingResult br, String type){
		return validateFieldVO(br,type,ignoreFields,checkFields);
	}
	
    /**
     * 检测 传过来的 数据的准确性工具方法
     * @param @param br	BindingResult实体，封装了整个检测结果
     * @param @param type	检测类型；I 可以和 ignoreField搭配，过滤掉某些不检测的属性； U 可以和onlyCheckFiled搭配，检测只需要检测的属性
     * @param @param ignoreFields 不检测的属性集合
     * @param @param checkFields	只检测的属性集合
     * @param @return   返回为空的话说明检测通过，否则 返回检测错误的结果对象
     */
    public GeneralResult validateFieldVO(BindingResult br,String type,Set<String> ignoreFields,Set<String> checkFields) {
    	int maxSize = 16;
    	if(br.hasErrors()){
    		boolean flag =false;
    		StringBuffer buffer = new StringBuffer("");
    		List<Map<String,String>> lm= new ArrayList<Map<String,String>>();
            List<FieldError> list = br.getFieldErrors();  
            for (FieldError error : list) {
            	String fName = error.getField();
            	String err = error.getDefaultMessage();
            	if(type.equalsIgnoreCase(I_TYPE)){
            		if(ignoreFields!=null && ignoreFields.contains(fName)){
            			continue;
            		}
            		Map<String,String> tm = new HashMap<String,String>(maxSize);
                	tm.put(fName, err);
                	lm.add(tm);
					buffer.append(fName+":"+err+";");
                	flag = true;
            	}
            	if(type.equalsIgnoreCase(U_TYPE)){
            		if(checkFields!=null && checkFields.contains(fName)){
            			Map<String,String> tm = new HashMap<String,String>(maxSize);
                    	tm.put(fName, err);
                    	lm.add(tm);
                    	buffer.append(fName+":"+err+";");
            			flag = true;
            		}
            	}
            }
            if(flag){
				return new GeneralResult(100001,"请求参数错误,必传的参数未传递!",buffer.toString());
            }
    	}
    	return null;
	}
}
