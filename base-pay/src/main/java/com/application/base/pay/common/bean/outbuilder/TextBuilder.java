package com.application.base.pay.common.bean.outbuilder;

import com.application.base.pay.common.bean.PayOutMessage;

/**
 * @desc text创建
 * @author 孤狼
 */
public class TextBuilder extends BaseBuilder<TextBuilder, PayOutMessage> {
    private String content;

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public PayOutMessage build() {
        PayTextOutMessage message = new PayTextOutMessage();
        setCommon(message);
        message.setContent(content);
        return message;
    }
}
