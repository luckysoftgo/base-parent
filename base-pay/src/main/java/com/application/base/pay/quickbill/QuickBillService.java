package com.application.base.pay.quickbill;

import java.util.Map;

/**
 * 快钱所有的接口
 * @author 孤狼
 */
public interface QuickBillService {

	/**
	 * 动态产生费用
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDynByExpense(Map<String, Object> param);

	/**
	 * 支付操作
	 * @param param
	 * @return
	 */
	public Map<String, Object> doPay(Map<String, Object> param);

	/**
	 * 快速支付
	 * @param param
	 * @return
	 */
	public Map<String, Object> quickPay(Map<String, Object> param);

	/**
	 * 单独绑卡(鉴权时候,用来关联上银行卡和快钱服务)
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDynByBind(Map<String, Object> param);

	/**
	 * 快钱绑卡
	 * @param param
	 * @return
	 */
	public Map<String, Object> bindCard(Map<String, Object> param);

	/**
	 * 动态验证码支付
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDynamicCodeByRecharge(Map<String, Object> param);

	/**
	 * 首次支付
	 * @param param
	 * @return
	 */
	public Map<String, Object> firstRecharge(Map<String, Object> param);

	/**
	 * 快钱支付
	 * @param param
	 * @return
	 */
	public Map<String, Object> quickRecharge(Map<String, Object> param);

	/**
	 * 移除快钱和银行卡之间的关系
	 * @param param
	 * @return
	 */
	public Map<String, Object> removeBindCard(Map<String, Object> param);
	
	/**
	 * 根据订单号检查快钱订单状态
	 * @param orderNo
	 * @return
	 */
	public Map<String, Object> checkOrderStatus(String orderNo);

}
