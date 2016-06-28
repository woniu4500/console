package com.jiuyv.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * The Class SimpleAuthenticator.
 *

 * @author 
 * @since 2014-2-25 13:13:45
 * @version 1.0.0
 */
public class SimpleAuthenticator extends Authenticator {

	/** 用户名. */
	private String username ;
	
	/** 密码. */
	private String password ;
	
	/**
	 * Instantiates a new simple authenticator.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public SimpleAuthenticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * @return
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
 
	
	
	
}
