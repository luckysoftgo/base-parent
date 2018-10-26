package com.application.base.pay.common.bean;

/**
 * @desc 返回的文件类型
 * @author 孤狼
 */
public enum FileType {
    
    /**
     * text 格式
     */
    TEXT("text","文本文件"),
    /**
     * xml 格式
     */
    XML("xml","xml文件"),
    /**
     * json 格式
     */
    JSON("json","Json文件格式"),
    ;
    
    private String key;
    private String value;
    
    FileType(String key,String value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
