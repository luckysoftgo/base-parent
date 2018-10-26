package com.application.base.pay.common.bean.result;

/**
 * @desc 支付错误码说明
 * @author 孤狼
 */
public interface PayError {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    String getErrorCode();

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    String getErrorMsg();

    /**
     * 获取异常信息
     * @return 异常信息
     */
    String getString();

}
