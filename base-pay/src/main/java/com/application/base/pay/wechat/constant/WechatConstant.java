package com.application.base.pay.wechat.constant;

/**
 * @desc 常量
 * @author 孤狼
 */
public class WechatConstant {

	/**
	 * 请求方式.
	 */
	public static final String REQUEST_METHOD_POST = "POST";
	
	public static final String REQUEST_METHOD_GET = "GET";
	
	/**
	 * 编码方式.
	 */
	public static final String ENCODING = "UTF-8";
	
	/**
	 * 货币类型.
	 */
	public static final String FEE_TYPE = "CNY";
	
	/**
	 * 货币类型.
	 */
	public static final String SIGN_TYPE = "MD5";
	
	/**
	 * 设备号.
	 */
	public static final String DEVICE_INFO = "WEB";
	
	/**
	 * 支付方式.
	 */
	public static final String TRADE_TYPE= "APP";
	
	/**
	 * 返回的code
	 */
	public interface ReturnCode {
		/**
		 * 成功.
		 */
		String SUCCESS = "SUCCESS";
		/**
		 * 失败
		 */
		String FAIL = "FAIL";
		/**
		 * 失败
		 */
		String OK = "OK";
	}
	
	/**
	 * 个人还是所有
	 */
	public interface CommentTag {
		/**
		 * //所有.
		 */
		String ALL = "ALL";
		/**
		 * //个人.
		 */
		String ONE = "ONE";
	}
	
	/**
	 * 构建xml文件节点
	 */
	public interface BuilderXml {
		/**
		 * xml 中要的描述符号
		 */
		String CDATA_START = "<![CDATA[";
		String CDATA_END = "]]>";
		/**
		 * xml
		 */
		String XML_START = "<xml>";
		String XML_END = "</xml>";
		/**
		 * appId
		 */
		String APPID_START = "<appId>";
		String APPID_END = "</appId>";

		/**
		 * mchId
		 */
		String MCH_ID_START = "<mchId>";
		String MCH_ID_END = "</mchId>";

		/**
		 * nonce_str
		 */
		String NONOCE_STR_START = "<nonce_str>";
		String NONOCE_STR_END = "</nonce_str>";

		/**
		 * sign
		 */
		String SIGN_START = "<sign>";
		String SIGN_END = "</sign>";

		/**
		 * body
		 */
		String BODY_START = "<body>";
		String BODY_END = "</body>";

		/**
		 * detail
		 */
		String DETAIL_START = "<detail>";
		String DETAIL_END = "</detail>";

		/**
		 * attach
		 */
		String ATTACH_START = "<attach>";
		String ATTACH_END = "</attach>";

		/**
		 * out_trade_no
		 */
		String OUT_TRADE_NO_START = "<out_trade_no>";
		String OUT_TRADE_NO_END = "</out_trade_no>";

		/**
		 * total_fee
		 */
		String TOTAL_FEE_START = "<total_fee>";
		String TOTAL_FEE_END = "</total_fee>";

		/**
		 * spbill_create_ip
		 */
		String SPBILL_CREATE_IP_START = "<spbill_create_ip>";
		String SPBILL_CREATE_IP_END = "</spbill_create_ip>";

		/**
		 * time_start
		 */
		String TIME_START_START = "<time_start>";
		String TIME_START_END = "</time_start>";

		/**
		 * time_expire
		 */
		String TIME_EXPIRE_START = "<time_expire>";
		String TIME_EXPIRE_END = "</time_expire>";

		/**
		 * notifyUrl
		 */
		String NOTIFY_URL_START = "<notifyUrl>";
		String NOTIFY_URL_END = "</notifyUrl>";

		/**
		 * trade_type
		 */
		String TRADE_TYPE_START = "<trade_type>";
		String TRADE_TYPE_END = "</trade_type>";

		/**
		 * goods_tag
		 */
		String GOODS_TAG_START = "<goods_tag>";
		String GOODS_TAG_END = "</goods_tag>";

		/**
		 * sign_type
		 */
		String SIGN_TYPE_START = "<sign_type>";
		String SIGN_TYPE_END = "</sign_type>";

		/**
		 * transaction_id
		 */
		String TRANSACTION_ID_START = "<transaction_id>";
		String TRANSACTION_ID_END = "</transaction_id>";

		/**
		 * out_refund_no
		 */
		String OUT_REFUND_NO_START = "<out_refund_no>";
		String OUT_REFUND_NO_END = "</out_refund_no>";

		/**
		 * fee_type
		 */
		String FEE_TYPE_START = "<fee_type>";
		String FEE_TYPE_END = "</fee_type>";

		/**
		 * refund_fee
		 */
		String REFUND_FEE_START = "<refund_fee>";
		String REFUND_FEE_END = "</refund_fee>";

		/**
		 * product_id
		 */
		String PRODUCT_ID_START = "<product_id>";
		String PRODUCT_ID_END = "</product_id>";

		/**
		 * limit_pay
		 */
		String LIMIT_PAY_START = "<limit_pay>";
		String LIMIT_PAY_END = "</limit_pay>";

		/**
		 * openid
		 */
		String OPEN_ID_START = "<openid>";
		String OPEN_ID_END = "</openid>";

		/**
		 * device_info
		 */
		String DEVICE_INFO_START = "<device_info>";
		String DEVICE_INFO_END = "</device_info>";

		/**
		 * refund_fee_type
		 */
		String REFUND_FEE_TYPE_START = "<refund_fee_type>";
		String REFUND_FEE_TYPE_END = "</refund_fee_type>";

		/**
		 * op_user_id
		 */
		String OP_USER_ID_START = "<op_user_id>";
		String OP_USER_ID_END = "</op_user_id>";

		/**
		 * refund_account
		 */
		String REFUND_ACCOUNT_START = "<refund_account>";
		String REFUND_ACCOUNT_END = "</refund_account>";

		/**
		 * result_code
		 */
		String RESULT_CODE_START = "<result_code>";
		String RESULT_CODE_END = "</result_code>";

		/**
		 * err_code
		 */
		String ERR_CODE_START = "<err_code>";
		String ERR_CODE_END = "</err_code>";

		/**
		 * err_code_des
		 */
		String ERR_CODE_DES_START = "<err_code_des>";
		String ERR_CODE_DES_END = "</err_code_des>";

		/**
		 * is_subscribe
		 */
		String IS_SUBSCRIBE_START = "<is_subscribe>";
		String IS_SUBSCRIBE_END = "</is_subscribe>";

		/**
		 * trade_state
		 */
		String TRADE_STATE_START = "<trade_state>";
		String TRADE_STATE_END = "</trade_state>";

		/**
		 * bank_type
		 */
		String BANK_TYPE_START = "<bank_type>";
		String BANK_TYPE_END = "</bank_type>";

		/**
		 * cash_fee
		 */
		String CASH_FEE_START = "<cash_fee>";
		String CASH_FEE_END = "</cash_fee>";

		/**
		 * cash_fee_type
		 */
		String CASH_FEE_TYPE_START = "<cash_fee_type>";
		String CASH_FEE_TYPE_END = "</cash_fee_type>";

		/**
		 * coupon_fee
		 */
		String COUPON_FEE_START = "<coupon_fee>";
		String COUPON_FEE_END = "</coupon_fee>";

		/**
		 * coupon_count
		 */
		String COUPON_COUNT_START = "<coupon_count>";
		String COUPON_COUNT_END = "</coupon_count>";

		/**
		 * time_end
		 */
		String TIME_END_START = "<time_end>";
		String TIME_END_END = "</time_end>";

		/**
		 * trade_state_desc
		 */
		String TRADE_STATE_DESC_START = "<trade_state_desc>";
		String TRADE_STATE_DESC_END = "</trade_state_desc>";

		/**
		 * return_code
		 */
		String RETURN_CODE_START = "<return_code>";
		String RETURN_CODE_END = "</return_code>";

		/**
		 * return_msg
		 */
		String RETURN_MSG_START = "<return_msg>";
		String RETURN_MSG_END = "</return_msg>";

		/**
		 * prepay_id
		 */
		String PREPAY_ID_START = "<prepay_id>";
		String PREPAY_ID_END = "</prepay_id>";

		/**
		 * code_url
		 */
		String CODE_URL_START = "<code_url>";
		String CODE_URL_END = "</code_url>";

		/**
		 * refund_id
		 */
		String REFUND_ID_START = "<refund_id>";
		String REFUND_ID_END = "</refund_id>";

		/**
		 * settlement_refund_fee
		 */
		String SETTLEMENT_REFUND_FEE_START = "<settlement_refund_fee>";
		String SETTLEMENT_REFUND_FEE_END = "</settlement_refund_fee>";

		/**
		 * settlement_total_fee
		 */
		String SETTLEMENT_TOTAL_FEE_START = "<settlement_total_fee>";
		String SETTLEMENT_TOTAL_FEE_END = "</settlement_total_fee>";

		/**
		 * cash_refund_fee
		 */
		String CASH_REFUND_FEE_START = "<cash_refund_fee>";
		String CASH_REFUND_FEE_END = "</cash_refund_fee>";

		/**
		 * coupon_refund_fee
		 */
		String COUPON_REFUND_FEE_START = "<coupon_refund_fee>";
		String COUPON_REFUND_FEE_END = "</coupon_refund_fee>";

		/**
		 * coupon_refund_count
		 */
		String COUPONE_REFUND_COUNT_START = "<coupon_refund_count>";
		String COUPONE_REFUND_COUNT_END = "</coupon_refund_count>";

		/**
		 * bill_date
		 */
		String BILL_DATE_START = "<bill_date>";
		String BILL_DATE_END = "</bill_date>";

		/**
		 * bill_type
		 */
		String BILL_TYPE_START = "<bill_type>";
		String BILL_TYPE_END = "</bill_type>";
	}
}
