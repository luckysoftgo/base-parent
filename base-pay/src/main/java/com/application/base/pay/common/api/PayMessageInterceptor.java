package com.application.base.pay.common.api;


import com.application.base.pay.common.bean.PayMessage;
import com.application.base.pay.common.exception.PayErrorException;

import java.util.Map;

/**
 * 支付消息拦截器，可以用来做验证
 * @author 孤狼
 */
public interface PayMessageInterceptor {

    /**
     * 拦截微信消息
     *
     * @param payMessage 支付消息
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return true代表OK，false代表不OK
     */
     boolean intercept(PayMessage payMessage,
					   Map<String, Object> context,
					   PayService payService
	 ) throws PayErrorException;

}
