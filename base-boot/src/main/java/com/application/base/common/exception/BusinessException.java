package com.application.base.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc业务逻辑异常
 * @ClassName: BusinessException
 * @author 孤狼
 */
public class BusinessException extends RuntimeException {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 错误的 key
	 */
	protected String exceptionKey;
	
	/**
	 * 错误的 code
	 */
	protected int exceptionCode;
	
	/**
	 * 错误的 消息
	 */
	protected String exceptionMsg;
	
	/**
	 * 参数设置
	 */
	protected String[] args;

	public BusinessException() {
        if(this.getLocalizedMessage()!=null){
        	logger.error(this.getLocalizedMessage());
        }
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception ex, String message) {
		super(message, ex);
	}

	public BusinessException(Exception ex) {
		super(ex);
		exceptionKey = CoreConstants.CommonMsgResult.SYSTEM_ERROR_MSG;
	}

	public BusinessException(DataAccessException ex) {
		setExceptionKey(ex.getExceptionKey());
	}

	public String getExceptionKey() {
		if (StringUtils.isEmpty(exceptionKey)) {
			exceptionKey = CoreConstants.CommonMsgResult.SYSTEM_ERROR_MSG;
		}
		return exceptionKey;
	}

	protected void setExceptionKey(String exceptionKey) {
		this.exceptionKey = exceptionKey;
	}
	
	public String getExceptionMsg() {
		if (StringUtils.isEmpty(exceptionMsg)) {
			exceptionMsg = "系统出现异常";
		}
		return exceptionMsg;
	}

	protected void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	public int getExceptionCode() {
		return exceptionCode;
	}
	
	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
