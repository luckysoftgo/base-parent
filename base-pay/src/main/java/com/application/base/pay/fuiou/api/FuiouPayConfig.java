package com.application.base.pay.fuiou.api;

import com.application.base.pay.common.api.BasePayConfig;

/**
 * @desc 富有的配置
 * @author 孤狼
 */
public class FuiouPayConfig extends BasePayConfig {
    
    /**
     * 商户代码
     */
    public String mchntCd;

    /**
     *  应用id
     * @return 空
     */
    @Override
    public String getAppId() {
        return null;
    }


    /**
     * 合作商唯一标识
     *
     */
    @Override
    public String getPId () {
        return mchntCd;
    }

    public String getMchntCd () {
        return mchntCd;
    }

    public void setMchntCd (String mchntCd) {
        this.mchntCd = mchntCd;
    }

    @Override
    public String getSeller() {
        return null;
    }

}
