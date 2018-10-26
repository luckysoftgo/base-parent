package com.application.base.pay.youdian.bean;

import com.application.base.pay.common.bean.TransactionType;

/**
 * @desc 友店交易类型
 * @author 孤狼.
 */
public enum  YoudianTransactionType implements TransactionType {
    
    /**
     * 扫码付
     */
    NATIVE("unifiedorder","扫码付"),
    
    /**
     * 刷卡付
     */
    MICROPAY("micropay","刷卡付"),

    ;

    private String method;
    private String desc;

    YoudianTransactionType(String method) {
        this.method = method;
    }
    
    YoudianTransactionType(String method,String desc) {
        this.method = method;
        this.desc = desc;
    }
    
    @Override
    public String getType() {
        return this.name();
    }

    /**
     * 获取接口名称
     * @return 接口名称
     */
    @Override
    public String getMethod() {
        return this.method;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
