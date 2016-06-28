package com.jiuyv.hhc.model.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Security 中使用的权限对象.
 *

 * @author 
 * @since 2014-1-2 16:46:55
 * @version 1.0.0
 */
public class SecurityAuthority extends SysResourceVo implements GrantedAuthority {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7182597138105523752L;

	/**
	 * @return
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	public String getAuthority() {
		return this.getResId();
	}

}
