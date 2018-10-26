package com.application.base.core.exception;

import com.application.base.core.constant.CoreConstants;

/**
 * @desc 异常信息
 * @author 孤狼
 */
public class ValideErrorException extends BusinessException {

    public ValideErrorException() {
        super();
        super.exceptionKey= CoreConstants.CommonMsgResult.VALIDATED_PARAMS_ERROR;
    }

}
