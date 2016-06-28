package com.jiuyv.common.database.exception;

/**
 * The Class UpdateException.
 *

 * @author 
 * @since 2014-1-2 14:15:34
 * @version 1.0.0
 */
public class UpdateException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8002493089377560095L;

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param throwable Throwable
	 */
	public UpdateException(String errorCode, Throwable throwable) {
		super(errorCode, throwable);
	}

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param errorMessage 错误信息
	 */
	public UpdateException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param errorMessage 错误信息
	 * @param throwable the throwable
	 */
	public UpdateException(String errorCode, String errorMessage,
			Throwable throwable) {
		super(errorCode, errorMessage, throwable);
	}
	
}
