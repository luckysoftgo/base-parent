package com.application.base.pay;

import com.application.base.pay.common.api.PayService;
import com.application.base.pay.common.bean.MethodType;
import com.application.base.pay.common.bean.PayOrder;
import com.application.base.pay.wechat.api.WxPayConfig;
import com.application.base.pay.wechat.api.WxPayService;
import com.application.base.pay.wechat.bean.WxTransactionType;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * @author 孤狼.
 */
public class WeChatTest {
	
	public static void main(String[] args) {
		WxPayConfig wxPayConfigStorage = new WxPayConfig();
		wxPayConfigStorage.setMchId("合作者id（商户号）");
		wxPayConfigStorage.setAppId("应用id");
		wxPayConfigStorage.setKeyPublic("密钥");
		wxPayConfigStorage.setKeyPrivate("密钥");
		wxPayConfigStorage.setNotifyUrl("异步回调地址");
		wxPayConfigStorage.setReturnUrl("同步回调地址");
		wxPayConfigStorage.setSignType("签名方式");
		wxPayConfigStorage.setInputCharset("utf-8");
		//是否为测试账号，沙箱环境 此处暂未实现
		wxPayConfigStorage.setTest(true);
		//支付服务
		PayService service =  new WxPayService(wxPayConfigStorage);
		//支付订单基础信息
		PayOrder payOrder = new PayOrder("订单title", "摘要",  new BigDecimal(0.01) , UUID.randomUUID().toString().replace("-", ""));
        /*-----------扫码付-------------------*/
		payOrder.setTransactionType(WxTransactionType.NATIVE);
		//获取扫码付的二维码
		BufferedImage image = service.genQrPay(payOrder);
        /*-----------/扫码付-------------------*/

        /*-----------APP-------------------*/
		payOrder.setTransactionType(WxTransactionType.APP);
		//获取APP支付所需的信息组，直接给app端就可使用
		Map appOrderInfo = service.orderInfo(payOrder);
        /*-----------/APP-------------------*/

        /*----------- WAP 网页支付-------------------*/
		
		payOrder.setTransactionType(WxTransactionType.MWEB); //  网页支付
		//获取支付所需的信息
		Map directOrderInfo = service.orderInfo(payOrder);
		//获取表单提交对应的字符串，将其序列化到页面即可,
		String directHtml = service.buildRequest(directOrderInfo, MethodType.POST);
        /*-----------/ WAP 网页支付-------------------*/



        /*-----------条码付 刷卡付-------------------*/
		payOrder.setTransactionType(WxTransactionType.MICROPAY);//条码付
		payOrder.setAuthCode("条码信息");
		// 支付结果
		Map params = service.microPay(payOrder);

        /*-----------/条码付 刷卡付-------------------*/

        /*-----------回调处理-------------------*/
//        HttpServletRequest request
//        params = service.getParameter2Map(request.getParameterMap(), request.getInputStream());
		if (service.verify(params)){
			System.out.println("支付成功");
			return;
		}
		System.out.println("支付失败");


        /*-----------回调处理-------------------*/
	}
}
