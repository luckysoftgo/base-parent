package com.application.base.pay.ali.bean;

import com.application.base.pay.common.bean.TransactionType;

/**
 * @desc 阿里交易类型
 * <pre>
 * 说明交易类型主要用于支付接口调用参数所需
 * </pre>
 * @author 孤狼
 */
public enum  AliTransactionType implements TransactionType {
    /**
     * 即时到帐
     */
    DIRECT("alipay.trade.page.pay","即时到帐"),
    
    /**
     * APP支付
     */
    APP("alipay.trade.app.pay","APP支付"),
    
    /**
     * 手机网站支付
     */
    WAP("alipay.trade.wap.pay","手机网站支付"),

    /**
     *  扫码付
     */
    SWEEPPAY("alipay.trade.precreate","扫码付"),
    
    /**
     * 条码付
     */
    BAR_CODE("alipay.trade.pay","条码付"),
    
    /**
     * 声波付
     */
    WAVE_CODE("alipay.trade.pay","声波付"),

    /**
     * 交易订单查询
     */
    QUERY("alipay.trade.query","交易订单查询"),
    
    /**
     * 交易订单关闭
     */
    CLOSE("alipay.trade.close","交易订单关闭"),
    
    /**
     * 退款
     */
    REFUND("alipay.trade.refund","退款"),
    
    /**
     * 退款查询
     */
    REFUNDQUERY("alipay.trade.fastpay.refund.query","退款查询"),
    
    /**
     * 下载对账单
     */
    DOWNLOADBILL("alipay.data.dataservice.bill.downloadurl.query","下载对账单")

    ;

    private String method;
    
    private String desc;
    
    private AliTransactionType(String method,String desc) {
        this.method = method;
        this.desc = desc;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
 
    @Override
    public String getType() {
        return this.name();
    }

    /* *
     * 获取接口名称
     * @return 接口名称
     */
    @Override
    public String getMethod() {
        return this.method;
    }

}
