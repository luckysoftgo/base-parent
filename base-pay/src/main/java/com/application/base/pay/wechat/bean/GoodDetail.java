package com.application.base.pay.wechat.bean;

/**
 * @desc 商品支付详情
 * @author 孤狼
 */
public class GoodDetail {

	/**
	 * 商品的编号
	 */
	private String goodsId;
	/**
	 * 微信支付定义的统一商品编号
	 */
	private String wxpayGoodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品数量
	 */
	private String quantity;
	/**
	 * 商品单价，单位为分
	 */
	private String price;
	/**
	 * 商品类目ID
	 */
	private String goodsCategory;
	/**
	 * 商品描述信息
	 */
	private String body;
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getWxpayGoodsId() {
		return wxpayGoodsId;
	}
	public void setWxpayGoodsId(String wxpayGoodsId) {
		this.wxpayGoodsId = wxpayGoodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
