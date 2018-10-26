package com.application.base.pay.common.bean;

/**
 * @desc 请求方式
 * @author 孤狼
 */
public enum MethodType {
    /**
     *请求指定的资源
     */
    GET("get","请求指定的资源"),
    /**
     *向指定的资源提交需要处理的数据
     */
    POST("post","向指定的资源提交需要处理的数据"),
    /**
     *上传指定资源
     */
    PUT("put","上传指定资源"),
    /**
     *删除指定的资源
     */
    DELETE("delete","删除指定的资源"),
    
    ;
    
    private String key;
    private String value;
    
    MethodType(String key,String value) {
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
