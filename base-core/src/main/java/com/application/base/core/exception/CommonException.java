package com.application.base.core.exception;

/**
 * @desc 错误信息描述
 * @author 孤狼
 */
public class CommonException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public CommonException() {
	}

	public CommonException(String exceptionKey) {
		setExceptionKey(exceptionKey);
	}
	
	public CommonException(int exceptionCode) {
		setExceptionCode(exceptionCode);
	}
	
	public CommonException(String exceptionKey, String exceptionMsg) {
		setExceptionKey(exceptionKey);
		setExceptionMsg(exceptionMsg);
	}
	
	public CommonException(int exceptionCode, String exceptionMsg) {
		setExceptionCode(exceptionCode);
		setExceptionMsg(exceptionMsg);
	}
	
	public CommonException(String exceptionKey, String... args) {
		setExceptionKey(exceptionKey);
		setArgs(args);
	}
}
