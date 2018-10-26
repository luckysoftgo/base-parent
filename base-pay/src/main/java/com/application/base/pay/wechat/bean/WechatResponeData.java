package com.application.base.pay.wechat.bean;

/**
 * @desc 下单微信返回的参数组,字段描述参照 :
 * https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 * @author 孤狼
 */
public class WechatResponeData {

	/**
	 * 公众账号ID,微信开放平台审核通过的应用APPID
	 */
	private String appId;
	/**
	 * 微信支付分配的商户号,
	 */
	private String mchId;
	/**
	 * 微信支付分配的终端设备号
	 */
	private String deviceInfo;
	/**
	 * 随机字符串，32位：数字+大写字母组合
	 */
	private String nonceStr;
	/**
	 * 签名：回调的时候需要反校验
	 */
	private String sign;
	/**
	 * SUCCESS/FAIL
	 */
	private String resultCode;
	/**
	 * 错误返回的信息code
	 */
	private String errCode;
	/**
	 * 错误返回的信息描述
	 */
	private String errCodeDes;
	/**
	 * 用户在商户appid下的唯一标识
	 */
	private String openId;
	/**
	 * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	 */
	private String isSubscribe;
	/**
	 * 交易类型:JSAPI、NATIVE、APP
	 */
	private String tradeType;
	/**
	 * //SUCCESS—支付成功;REFUND—转入退款;NOTPAY—未支付;CLOSED—已关闭;REVOKED—已撤销（刷卡支付）;USERPAYING--用户支付中;PAYERROR--支付失败(其他原因，如银行返回失败)
	 */
	private String tradeState;
	/**
	 * 银行类型，采用字符串类型的银行标识，银行类型见银行列表
	 */
	private String bankType;
	/**
	 * 订单总金额，单位为分;1元=100分；
	 */
	private int totalFee;
	/**
	 * 货币种类，符合ISO4217标准的三位字母代码，默认人民币：CNY
	 */
	private String feeType;
	/**
	 * 实际支付：现金支付金额订单现金支付金额
	 */
	private int cashFee;
	/**
	 * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
	 */
	private String cashFeeType;
	/**
	 * 优惠券总金额：代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额
	 */
	private int couponFee;
	/**
	 * 代金券或立减优惠使用数量
	 */
	private int couponCount;
	/**
	 * 微信支付订单号:1217752501201407033233368018
	 */
	private String transactionId;
	/**
	 * 商户系统的订单号，与请求一致。交易单号：1212321211201407033568112322
	 */
	private String outTradeNo;
	/**
	 * //商户退款单号,商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
	 */
	private String outRefundNo;
	/**
	 * 商家数据包，原样返回:比如阿迪达斯的篮球鞋，白色，35
	 */
	private String attach;
	/**
	 * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	 */
	private String timeEnd;
	/**
	 * //交易状态描述,对当前查询订单状态的描述和下一步操作的指引
	 */
	private String tradeStateDesc;
	/**
	 * 	SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	private String returnCode;
	/**
	 * 返回信息，如非空，为错误原因
	 */
	private String returnMsg;
	/**
	 * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	private String prepayId;
	/**
	 * tradeType 为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
	 */
	private String codeUrl;
	/**
	 * //微信退款单号
	 */
	private String refundId;
	/**
	 *  //退款总金额,单位为分,可以做部分退款
	 */
	private int refundFee;
	/**
	 * //去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
	 */
	private int settlementRefundFee;
	/**
	 * //去掉非充值代金券金额后的订单总金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
	 */
	private int settlementTotalFee;
	/**
	 * //现金退款金额，单位为分，只能为整数，详见支付金额
	 */
	private int cashRefundFee;
	/**
	 * //代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
	 */
	private int couponRefundFee;
	/**
	 * //退款代金券使用数量
	 */
	private int couponRefundCount;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeState() {
		return tradeState;
	}
	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public int getCashFee() {
		return cashFee;
	}
	public void setCashFee(int cashFee) {
		this.cashFee = cashFee;
	}
	public String getCashFeeType() {
		return cashFeeType;
	}
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	public int getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(int couponFee) {
		this.couponFee = couponFee;
	}
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getTradeStateDesc() {
		return tradeStateDesc;
	}
	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public int getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}
	public int getSettlementRefundFee() {
		return settlementRefundFee;
	}
	public void setSettlementRefundFee(int settlementRefundFee) {
		this.settlementRefundFee = settlementRefundFee;
	}
	public int getSettlementTotalFee() {
		return settlementTotalFee;
	}
	public void setSettlementTotalFee(int settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}
	public int getCashRefundFee() {
		return cashRefundFee;
	}
	public void setCashRefundFee(int cashRefundFee) {
		this.cashRefundFee = cashRefundFee;
	}
	public int getCouponRefundFee() {
		return couponRefundFee;
	}
	public void setCouponRefundFee(int couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}
	public int getCouponRefundCount() {
		return couponRefundCount;
	}
	public void setCouponRefundCount(int couponRefundCount) {
		this.couponRefundCount = couponRefundCount;
	}
	
}
