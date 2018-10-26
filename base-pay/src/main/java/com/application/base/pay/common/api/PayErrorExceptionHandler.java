package com.application.base.pay.common.api;


import com.application.base.pay.common.exception.PayErrorException;

/**
 * PayErrorExceptionHandler处理器
 * @author 孤狼
 */
public interface PayErrorExceptionHandler {

    /**
     * 异常统一处理器
     * @param e 支付异常
     */
     void handle(PayErrorException e);

}
