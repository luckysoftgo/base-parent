package com.application.base.boot.exception;

import com.application.base.boot.Constants;

/**
 * @desc 常量数据类
 * @ClassName: Constants
 * @author 孤狼
 */
public class CoreConstants extends Constants {
	/**
	 * 通用的 message 设置.
	 */
	public interface CommonMsgResult {
		/**
		 * 系统消息设置.
		 */
		/**
		 * 登录超时100.
		 */
		String SYSTEM_AUTH_LOST_MSG = "SYSTEM_AUTH_LOST_MSG";
		
		/**
		 * 系统消息设置.
		 */
		/**
		 *  操作成功  200
		 */
		String  SYSTEM_SUCCESS_MSG = "SYSTEM_SUCCESS_MSG";
		/**
		 * 系统错误   500
		 */
		String SYSTEM_ERROR_MSG = "SYSTEM_ERROR_MSG";
		/**
		 * 接口不存在   404
		 */
		String INTERFACE_IS_NULL_MSG = "INTERFACE_IS_NULL_MSG";
		/**
		 *  数据访问异常. 525
		 */
		String DATA_ACCESS_EXCEPTION_MSG = "DATA_ACCESS_EXCEPTION_MSG";
		/**
		 * 数据操作异常. 535
		 */
		String DATA_OPERATE_EXCEPTION_MSG = "DATA_OPERATE_EXCEPTION_MSG";
		
		/**
		 * 通用消息设置.
		 */
		/**
		 * 参数不完整,请检查参数  100001
		 */
		String PARAMS_MISSING_ERROR = "PARAMS_MISSING_MSG";
		/**
		 * 参数格式不符合要求,请检查参数  100002
		 */
		String VALIDATED_PARAMS_ERROR = "VALIDATED_PARAMS_MSG";
		/**
		 * 对象之间属性拷贝异常  100003
		 */
		String COPY_BEAN_PROP_ERROR = "COPY_BEAN_PROP_MSG";
		/**
		 * Bean对象转换Map异常  100004
		 */
		String CONVERT_BEAN_TO_MAP_ERROR = "CONVERT_BEAN_TO_MAP_MSG";
		/**
		 * Map转换Bean对象异常  100005
		 */
		String CONVERT_MAP_TO_BEAN_ERROR = "CONVERT_MAP_TO_BEAN_MSG";
		/**
		 * 远程调用失败  100006
		 */
		String REMOTE_CALL_FAILED_ERROR = "REMOTE_CALL_FAILED_MSG";
		/**
		 * 远程调用超时 100007
 		 */
		String REMOTE_CALL_TIMEOUT_ERROR = "REMOTE_CALL_TIMEOUT_MSG";
		/**
		 * 结果对象转换异常 100008
		 */
		String RESULT_OBJECT_CONVERT_FAILED_ERROR = "RESULT_OBJECT_CONVERT_FAILED_MSG";
		
		/**
		 * 增删改提示信息.
		 */
		/**
		 * 保存对象操作失败 200001
		 */
		String ADD_DATA_TO_DB_FAIL = "ADD_DATA_TO_DB_FAIL_MSG" ;
		/**
		 * 保存对象操作成功 200002
		 */
		String ADD_DATA_TO_DB_SUCCESS = "ADD_DATA_TO_DB_SUCCESS_MSG" ;
		/**
		 * 修改对象操作失败 200003
		 */
		String UPDATE_DATA_TO_DB_FAIL = "UPDATE_DATA_TO_DB_FAIL_MSG" ;
		/**
		 * 修改对象操作成功 200004
		 */
		String UPDATE_DATA_TO_DB_SUCCESS = "UPDATE_DATA_TO_DB_SUCCESS_MSG" ;
		/**
		 * 删除对象操作失败 200005
		 */
		String DELETE_DATA_TO_DB_FAIL = "DELETE_DATA_TO_DB_FAIL_MSG" ;
		/**
		 * 删除对象操作成功 200006
		 */
		String DELETE_DATA_TO_DB_SUCCESS = "DELETE_DATA_TO_DB_SUCCESS_MSG" ;
		
		/**
		 * 查询提示信息.
		 */
		/**
		 * 操作数据已经存在  200007
		 */
		String DATA_ALREADY_EXIST = "DATA_ALREADY_EXIST_MSG";
		/**
		 * 查询数据失败   200008
		 */
		String SELECT_DATA_FROM_DB_FAIL = "SELECT_DATA_FROM_DB_FAIL_MSG";
		/**
		 * 查询数据成功   200009
		 */
		String SELECT_DATA_FROM_DB_SUCCESS = "SELECT_DATA_FROM_DB_SUCCESS_MSG";
		/**
		 * 没有找到符合条件的结果 200010
		 */
		String QUERY_DB_NO_DATA = "QUERY_DB_NO_DATA_MSG";
		/**
		 * 分页查询失败 200011
		 */
		String QUERY_PAGE_DATA_FAIL = "QUERY_PAGE_DATA_FAIL_MSG";
		/**
		 * 分页查询成功 200012
		 */
		String QUERY_PAGE_DATA_SUCCESS = "QUERY_PAGE_DATA_SUCCESS_MSG";
		/**
		 * 查询总条数失败 200013
		 */
		String QUERY_TOTAL_DATA_FAIL = "QUERY_TOTAL_DATA_FAIL_MSG";
		/**
		 * 查询总条数成功 200014
		 */
		String QUERY_TOTAL_DATA_SUCCESS = "QUERY_TOTAL_DATA_SUCCESS_MSG";
		
 	}
}
