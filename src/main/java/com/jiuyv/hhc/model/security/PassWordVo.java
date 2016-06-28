package com.jiuyv.hhc.model.security;

/**
 * The Class PassWordVo.
 *

 * @author 
 * @since 2014-1-2 16:46:36
 * @version 1.0.0
 */
public class PassWordVo {

	/** The user id. */
	private Long userID;
	
	/** The expire date. */
	private String expireDate;
	
	/** 原始密码. */
	private String oldPasswd;

	/** 新密码. */
	private String newPasswd;

	/** 确认密码. */
	private String confirmPasswd;

	/**
	 * 获取 原始密码.
	 *
	 * @return the 原始密码
	 */
	public String getOldPasswd() {
		return oldPasswd;
	}

	/**
	 * 设置 原始密码.
	 *
	 * @param oldPasswd the new 原始密码
	 */
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	/**
	 * 获取 新密码.
	 *
	 * @return the 新密码
	 */
	public String getNewPasswd() {
		return newPasswd;
	}

	/**
	 * 设置 新密码.
	 *
	 * @param newPasswd the new 新密码
	 */
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	/**
	 * 获取 确认密码.
	 *
	 * @return the 确认密码
	 */
	public String getConfirmPasswd() {
		return confirmPasswd;
	}

	/**
	 * 设置 确认密码.
	 *
	 * @param confirmPasswd the new 确认密码
	 */
	public void setConfirmPasswd(String confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserID() {
		return userID;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userID the new user id
	 */
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	/**
	 * Gets the expire date.
	 *
	 * @return the expire date
	 */
	public String getExpireDate() {
		return expireDate;
	}

	/**
	 * Sets the expire date.
	 *
	 * @param expireDate the new expire date
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

}
