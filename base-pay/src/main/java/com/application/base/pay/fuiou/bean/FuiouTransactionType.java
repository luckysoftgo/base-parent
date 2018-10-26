package com.application.base.pay.fuiou.bean;

import com.application.base.pay.common.bean.TransactionType;

/**
 * @desc 支付方式
 * @author 孤狼
 */
public enum FuiouTransactionType implements TransactionType {
    /**
     *B2B
     */
    B2B("B2B","B2B"),
    
    /**
     *B2C
     */
    B2C("B2C","B2C"),
    
    ;
    
    
    private String name;
    
    private String method;

    FuiouTransactionType(String name,String method) {
        this.name = name;
        this.method = method;
    }

    @Override
    public String getType() {
        return this.name();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取接口名称
     * @return 接口名称
     */
    @Override
    public String getMethod() {
        return this.method;
    }
}
