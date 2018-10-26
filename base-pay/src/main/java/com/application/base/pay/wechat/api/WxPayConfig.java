package com.application.base.pay.wechat.api;


import com.application.base.pay.common.api.BasePayConfig;

/**
 * @desc 微信配置存储
 * @author  孤狼
 */
public class WxPayConfig extends BasePayConfig {

    /**
     * 应用id
     */
    private   String appId ;
    /**
     *  商户号 合作者id
     */
    private  String mchId;

    @Override
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 合作商唯一标识
     */
    @Override
    public String getPId() {
        return mchId;
    }

    /**
     * 合作商唯一标识
     */
    public void setPid(String mchId) {
         this.mchId = mchId;
    }
    
    @Override
    public String getSeller() {
        return null;
    }
    
    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

}
