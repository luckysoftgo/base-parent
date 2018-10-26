package com.application.base.pay.youdian.bean;

import com.application.base.pay.common.bean.result.PayError;

/**
 * @author: 孤狼
 */
public class YdPayError implements PayError {

    private int errorCode;
    private String msg;
    private String content;

    @Override
    public String getErrorCode() {
        return errorCode + "";
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }

    public YdPayError(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public YdPayError(int errorCode, String msg, String content) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.content = content;
    }

    @Override
    public String getString() {
        return "支付错误: errcode=" + errorCode + ", msg=" + msg + (null == content ? "" : "\n content:" + content);
    }
}
