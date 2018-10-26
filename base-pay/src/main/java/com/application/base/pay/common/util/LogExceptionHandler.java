package com.application.base.pay.common.util;

import com.application.base.pay.common.api.PayErrorExceptionHandler;
import com.application.base.pay.common.exception.PayErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @desc 日志处理器
 * @author 孤狼
 */
public class LogExceptionHandler implements PayErrorExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(PayErrorExceptionHandler.class);

    @Override
    public void handle(PayErrorException e) {
        logger.error("Error happens", e);
    }

}
