package com.application.base.pay.common.bean.outbuilder;

import com.application.base.pay.common.bean.FileType;
import com.application.base.pay.common.bean.PayOutMessage;

/**
 * @author 孤狼
 */
public class PayTextOutMessage extends PayOutMessage {

    public PayTextOutMessage() {
        this.msgType = FileType.TEXT.getKey();
    }

    @Override
    public String toMessage() {
        return getContent();
    }
}
