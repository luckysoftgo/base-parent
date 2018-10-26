package com.application.base.pay.common.api;

import com.application.base.pay.common.bean.PayMessage;
import com.application.base.pay.common.bean.PayOutMessage;
import com.application.base.pay.common.exception.PayErrorException;

import java.util.Map;

/**
 * 处理支付回调消息的处理器接口
 * @author 孤狼
 */
public interface PayMessageHandler {

    /**
     * @param payMessage 支付消息
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return xml,text格式的消息，如果在异步规则里处理的话，可以返回null
     */
    PayOutMessage handle(PayMessage payMessage,
						 Map<String, Object> context,
						 PayService payService
	) throws PayErrorException;

}
