package com.application.base.pay.wechat.bean;

/**
 * @desc 统一下单提交为微信的参数,字段描述参照 :
 * https:pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 * @author 孤狼
 */
public class WechatRequestData {

	/**
	 *  应用ID:商户开通支付之后,会发邮件给你告诉生成的appId. 微信分配的公众账号ID（企业号corpid即为此appId）,例如：wxd678efh567hg6787
	 */
	private String appId;
	/**
	 *  商户id,在商户的登录后台就可以看见
	 */
	private String mchId;
	/**
	 *  终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	 */
	private String deviceInfo;
	/**
	 *  随机字符串:数字+大写字母的组合，32位
	 */
	private String nonceStr;
	/**
	 *  签名,签名算法纪要:https:pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
	 */
	private String sign;
	/**
	 *  签名类型,目前支持HMAC-SHA256和MD5，默认为MD5
	 */
	private String signType;
	/**
	 * 微信生成的订单号，在支付通知中有返回
	 */
	private String transactionId;
	/**
	 *  商品或支付单简要描述:商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	 */
	private String body;
	/**
	 *  商品名称明细列表,是一个 GoodDetail 信息展示.
	 */
	private String detail;
	/**
	 *  附加参数 : 在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 */
	private String attach;
	/**
	 *  商户系统内部的订单号:商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@且在同一个商户号下唯一
	 */
	private String outTradeNo;
	/**
	 * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
	 */
	private String outRefundNo;
	/**
	 *  货币类型:符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 */
	private String feeType;
	/**
	 *  订单总金额，单位为分
	 */
	private int totalFee;
	/**
	 * 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
	 */
	private int refundFee;
	/**
	 *  APP和网页支付提交[用户端ip]，Native 支付填调用微信支付API的机器IP
	 */
	private String spbillCreateIp;
	/**
	 *  交易起始时间(订单生成时间)，格式为 yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	 */
	private String timeStart;
	/**
	 *  交易结束时间(订单失效时间)，格式为 yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010;最短失效时间间隔必须大于5分钟[支付宝是30分钟，同样30分钟]
	 */
	private String timeExpire;
	/**
	 *  商品标记，代金券或立减优惠功能的参数
	 */
	private String goodsTag;
	/**
	 *  接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 */
	private String notifyUrl;
	/**
	 *  交易类型:JSAPI，NATIVE，APP
	 */
	private String tradeType;
	/**
	 *  tradeType=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	 */
	private String productId;
	/**
	 *  no_credit--指定不能使用信用卡支付
	 */
	private String limitPay;
	/**
	 *  tradeType=JSAPI，此参数必传，用户在商户appid下的唯一标识
	 */
	private String openId;
	/**
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String refundFeeType;
	/**
	 * 操作员帐号, 默认为商户号
	 */
	private String opUserId;
	/**
	 * 仅针对老资金流商户使用,REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）,REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款(限非当日交易订单的退款）
	 */
	private String refundAccount;
	/**
	 * 微信退款单号:微信生成的退款单号，在申请退款接口有返回
	 */
	private String refundId;
	/**
	 * 对账单日期:下载对账单的日期，格式：20140603
	 */
	private String billDate;
	/**
	 * 账单类型:ALL(默认),SUCCESS,REFUND,RECHARGE_REFUND
	 */
	private String billType;
	
	
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
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
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
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public int getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}
	public String getGoodsTag() {
		return goodsTag;
	}
	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLimitPay() {
		return limitPay;
	}
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRefundFeeType() {
		return refundFeeType;
	}
	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}
	public String getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}
	public String getRefundAccount() {
		return refundAccount;
	}
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
}
