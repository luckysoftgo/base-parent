package com.application.base.pay.common.bean.outbuilder;

import com.application.base.pay.common.bean.PayOutMessage;

/**
 * @desc 接口
 * @author 孤狼
 */
public abstract class BaseBuilder<BuilderType, ValueType> {


    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }

}