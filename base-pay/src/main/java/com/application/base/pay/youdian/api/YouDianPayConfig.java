package com.application.base.pay.youdian.api;


import com.application.base.pay.common.api.BasePayConfig;

/**
 * @desc 支付客户端配置存储
 * @author  孤狼
 */
public class YouDianPayConfig extends BasePayConfig {

    /**
     * 账号
     */
    public volatile String seller;

    @Override
    public String getAppId() {
        return null;
    }

    @Override
    public String getPId() {
        return null;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String getSeller() {
        return seller;
    }

    public void setToken(String accessToken) {
       setAccessToken(accessToken);
    }

    @Override
    public String getToken() {
        return getAccessToken();
    }

}
