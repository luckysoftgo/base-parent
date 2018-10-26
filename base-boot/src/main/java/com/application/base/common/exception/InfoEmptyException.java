package com.application.base.common.exception;

/**
 * @desc 异常信息
 * @author 孤狼
 */
public class InfoEmptyException extends BusinessException {

    public InfoEmptyException() {
        super();
        super.exceptionKey = CoreConstants.CommonMsgResult.PARAMS_MISSING_ERROR;
    }

}
