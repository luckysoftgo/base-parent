package com.application.base.pay.fuiou.bean;

import com.application.base.pay.common.bean.CurType;

/**
 * 货币类型
 * @author 孤狼
 */
public enum FuiouCurType  implements CurType {
    
    /**
     * 人民币
     */
    CNY("cny","人民币"),
    /**
     * 美元
     */
    USD("usd","美元"),
    /**
     *港币
     */
    HKD("hkd","港币"),
    /**
     *
     */
    MOP("mop","澳门元"),
    /**
     *欧元
     */
    EUR("eur","欧元"),
    /**
     *新台币
     */
    TWD("twd","新台币"),
    /**
     *韩元
     */
    KRW("krw","韩元"),
    /**
     *日元
     */
    JPY("jpy","日元"),
    /**
     *新加坡元
     */
    SGD("sgd","新加坡元"),
    /**
     *澳大利亚元
     */
    AUD("aud","澳大利亚元"),
    
    ;
    
    /**
     * 币种key
     */
    private String key;
    /**
     * 币种名称
     */
    private String name;

    /**
     * 构造函数
     * @param name
     */
    FuiouCurType(String key,String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * 获取币种名称
     * @return 币种名称
     */
    @Override
    public String getCurType(){
        return this.name();
    }
}
