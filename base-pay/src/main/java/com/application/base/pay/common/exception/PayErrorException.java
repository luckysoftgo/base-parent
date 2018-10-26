package com.application.base.pay.common.exception;

import com.application.base.pay.common.bean.result.PayError;

/**
 * @desc 支付异常.
 * @author 孤狼.
 */
public class PayErrorException extends RuntimeException {

    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}
