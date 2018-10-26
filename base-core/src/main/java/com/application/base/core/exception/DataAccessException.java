package com.application.base.core.exception;

import com.application.base.core.constant.CoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 数据访问异常
 * @ClassName: DataAccessException
 * @author 孤狼
 */
public class DataAccessException extends BusinessException {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		setExceptionKey(CoreConstants.CommonMsgResult.DATA_ACCESS_EXCEPTION_MSG);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Exception ex) {
		super(ex);
		ex.printStackTrace();
		logger.error("DataAccessException:{}", ex.getMessage());
	}
}
