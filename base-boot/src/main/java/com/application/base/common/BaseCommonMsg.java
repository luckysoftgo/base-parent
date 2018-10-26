package com.application.base.common;

/**
 * @desc 通用信息描述定义.
 *
 * @author 孤狼
 */
public enum BaseCommonMsg {
	
	/**
	 * 系统消息设置
	 */
	SYSTEM_AUTH_LOST_MSG(100,"登录超时"),
	SYSTEM_SUCCESS_MSG(200,"操作成功"),
	SYSTEM_ERROR_MSG(500,"服务器内部错误"),
	INTERFACE_IS_NULL_MSG(404,"请求接口不存在"),
	DATA_ACCESS_EXCEPTION_MSG(525,"数据访问异常"),
	DATA_OPERATE_EXCEPTION_MSG(535,"数据操作异常"),
	
	/**
	 * 通用消息设置
	 */
	SESSION_ID_LOST_MSG(100000,"缺少sessionId参数"),
	PARAMS_MISSING_MSG(100001,"参数输入不完整,请检查输入参数"),
	VALIDATED_PARAMS_MSG(100002,"参数格式不符合要求,请检查参数"),
	COPY_BEAN_PROP_MSG(100003,"对象之间属性拷贝异常"),
	CONVERT_BEAN_TO_MAP_MSG(100004,"Bean对象转换Map异常"),
	CONVERT_MAP_TO_BEAN_MSG(100005,"Map转换Bean对象异常"),
	REMOTE_CALL_FAILED_MSG(100006,"远程调用失败"),
	REMOTE_CALL_TIMEOUT_MSG(100007,"远程调用超时"),
	RESULT_OBJECT_CONVERT_FAILED_MSG(100008,"结果对象转换异常"),
	LOGIN_TYPE_LOST_MSG(100009,"缺少登录类型"),
	
	
	/**
	 * 增删改提示信息
	 */
	ADD_DATA_TO_DB_FAIL_MSG(200001,"保存对象操作失败"),
	ADD_DATA_TO_DB_SUCCESS_MSG(200002,"保存对象操作成功"),
	UPDATE_DATA_TO_DB_FAIL_MSG(200003,"修改数据对象操作失败"),
	UPDATE_DATA_TO_DB_SUCCESS_MSG(200004,"修改数据对象操作成功"),
	DELETE_DATA_TO_DB_FAIL_MSG(200005,"删除数据对象操作失败"),
	DELETE_DATA_TO_DB_SUCCESS_MSG(200006,"删除数据对象操作成功"),
	
	/**
	 * 查询提示信息
	 */
	DATA_ALREADY_EXIST_MSG(200007,"操作数据已经存在"),
	SELECT_DATA_FROM_DB_FAIL_MSG(200008,"查询数据失败"),
	SELECT_DATA_FROM_DB_SUCCESS_MSG(200009,"查询数据成功"),
	QUERY_DB_NO_DATA_MSG(200010,"没有找到符合条件的结果"),
	QUERY_PAGE_DATA_FAIL_MSG(200011,"分页查询失败"),
	QUERY_PAGE_DATA_SUCCESS_MSG(200012,"分页查询成功"),
	QUERY_TOTAL_DATA_FAIL_MSG(200013,"查询总条数失败"),
	QUERY_TOTAL_DATA_SUCCESS_MSG(200014,"查询总条数成功"),
	
	;
	
	BaseCommonMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 获得实例.
	 * @param key
	 * @return
	 */
	public static BaseCommonMsg getData(String key) {
		for (BaseCommonMsg msg : BaseCommonMsg.values()) {
			if (key.equalsIgnoreCase(msg.toString())){
				return msg;
			}
		}
		return null;
	}
	
	
	private int code;
	
	private String msg;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
