package com.application.base.pay.wechat.bean;

import com.application.base.pay.common.bean.TransactionType;

/**
 * @desc 微信交易类型
 * @author 孤狼
 */
public enum  WxTransactionType implements TransactionType {
    
    /**
     * 公众号支付
     */
    JSAPI("pay/unifiedorder","公众号支付"),
    
    /**
     * 扫码付
     */
    NATIVE("pay/unifiedorder","扫码付"),
    
    /**
     * 移动支付
     */
    APP("pay/unifiedorder","移动支付"),
    
    /**
     * H5支付
     */
    MWEB("pay/unifiedorder","H5支付"),
    
    /**
     * 刷卡付
     */
    MICROPAY("pay/micropay","刷卡付"),
    
    /**
     * 查询订单
     */
    QUERY("pay/orderquery","查询订单"),
    
    /**
     * 关闭订单
     */
    CLOSE("pay/closeorder","关闭订单"),
    
    /**
     * 申请退款
     */
    REFUND("secapi/pay/refund","申请退款"),
    
    /**
     * 查询退款
     */
    REFUNDQUERY("pay/refundquery","查询退款"),
    
    /**
     * 下载对账单
     */
    DOWNLOADBILL("pay/downloadbill","下载对账单"),
    
    ;

    WxTransactionType(String method,String desc) {
        this.method = method;
        this.desc = desc;
    }

    private String method;
    
    private String desc;

    @Override
    public String getType() {
        return this.name();
    }
    
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
