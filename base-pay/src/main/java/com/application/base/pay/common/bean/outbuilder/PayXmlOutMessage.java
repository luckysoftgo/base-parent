package com.application.base.pay.common.bean.outbuilder;

import com.application.base.pay.common.bean.FileType;
import com.application.base.pay.common.bean.PayOutMessage;

/**
 * @author 孤狼
 */
public class PayXmlOutMessage extends PayOutMessage {

    private String code;

    public PayXmlOutMessage() {
        this.msgType = FileType.XML.getKey();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toMessage() {
       return "<xml><return_code><![CDATA[" + code + "]]></return_code><return_msg><![CDATA[" + content
                + "]]></return_msg></xml>";
    }
}
