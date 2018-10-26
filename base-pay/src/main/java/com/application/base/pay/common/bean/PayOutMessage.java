package com.application.base.pay.common.bean;

import com.alibaba.fastjson.JSONObject;
import com.application.base.pay.common.bean.outbuilder.JsonBuilder;
import com.application.base.pay.common.bean.outbuilder.TextBuilder;
import com.application.base.pay.common.bean.outbuilder.XmlBuilder;

import java.io.Serializable;

/**
 * @desc 支付回调通知返回消息
 * @author 孤狼
 */
public abstract class PayOutMessage implements Serializable {
    
    protected String content;
    protected String msgType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 获得文本消息builder
     * @return 文本消息builder
     */
    public static TextBuilder text() {
        return new TextBuilder();
    }
    /**
     * 获得XML消息builder
     * @return XML消息builder
     */
    public static XmlBuilder xml() {
        return new XmlBuilder();
    }
    /**
     * 获得Json消息builder
     * @return Json消息builder
     */
    public static JsonBuilder json() {
        return new JsonBuilder(new JSONObject());
    }
    
    public abstract String toMessage();
}
