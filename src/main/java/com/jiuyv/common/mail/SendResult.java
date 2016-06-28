package com.jiuyv.common.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SendResult.
 *

 * @author 
 * @since 2014-2-25 15:36:50
 * @version 1.0.0
 */
public class SendResult {

	/** The List errors. */
	private List<String> errors;
	
	/** 发送时间 */
	private String sendTime ; 
	
	/** 结果是否成功. */
	private boolean success ;
	
	/**
	 * Instantiates a new error info.
	 */
	public SendResult () {
		errors = new ArrayList<String>();
	}
	
	/**
	 * Adds the error.
	 *
	 * @param errorInfo the error info
	 */
	public void addError(String errorInfo ) {
		this.errors.add(errorInfo);
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * 是(否) 结果是否成功.
	 *
	 * @return the 结果是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置 结果是否成功.
	 *
	 * @param success the new 结果是否成功
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
