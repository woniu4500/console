/*
 * Created on 2008-11-4
 *
 * 基础异常类
 */
package com.jiuyv.common.database.exception;

/**
 * 基础异常.
 *
 * @author 
 * @since 2013-12-19 15:36:26
 * @version 1.0.0
 */
public class BaseException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2856575469726587413L;

	/** 错误结果代码. */
	private String errorCode;

	/** 错误信息. */
	private String errorMessage;

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param throwable Throwable
	 */
	public BaseException(String errorCode, Throwable throwable) {
		super(throwable.getMessage());
		this.errorCode = errorCode;
		this.errorMessage = throwable.getMessage();
	}

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param errorMessage 错误信息
	 */
	public BaseException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 构造函数.
	 *
	 * @param errorCode 错误代码
	 * @param errorMessage 错误信息
	 * @param throwable the throwable
	 */
	public BaseException(String errorCode, String errorMessage,
			Throwable throwable) {
		super(errorMessage, throwable);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 得到错误代码
	 * 
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 得到错误信息.
	 *
	 * @return String
	 */
	public String getMessage() {
		return errorMessage;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
