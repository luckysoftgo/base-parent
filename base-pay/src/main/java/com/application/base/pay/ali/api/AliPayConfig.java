package com.application.base.pay.ali.api;

import com.application.base.pay.common.api.BasePayConfig;

/**
 * @desc 支付配置存储.
 * @author  孤狼.
 *
 */
public class AliPayConfig extends BasePayConfig {

    /**
     *  商户应用id
     */
    private volatile  String appId ;
    
    /**
     *  商户签约拿到的pid,partner_id的简称，合作伙伴身份等同于 partner
     */
    private volatile  String pId;
    
    /**
     * 商户收款账号
     */
    private volatile  String seller;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    @Override
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
