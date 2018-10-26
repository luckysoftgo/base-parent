package com.application.base.pay.common.bean.outbuilder;

import com.application.base.pay.common.bean.FileType;
import com.application.base.pay.common.bean.PayOutMessage;

/**
 * @author 孤狼
 */
public class PayJsonOutMessage extends PayOutMessage {

    public PayJsonOutMessage() {
        this.msgType = FileType.JSON.getKey();
    }

    @Override
    public String toMessage() {
        return getContent();
    }


}
