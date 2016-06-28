package com.jiuyv.hhc.model.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The Class SecurityUserDetail.
 *

 * @author 
 * @since 2013-12-24 14:02:35
 * @version 1.0.0
 */
public class SecurityUserDetail extends SysUserVo implements UserDetails {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8859497761991619921L;

	/** The authorites. */
	private Collection<? extends GrantedAuthority> authorites;

	/** 用户子机构代码. */
	private Collection<String> childOrgId;
	
	/** 用户子商户代码 *. */
	private Collection<Long> childMerchantId;
	
	/** The child city id. */
	private Collection<String> childCityId;
	
	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorites;
	}

	/**
	 * Sets the authorites.
	 *
	 * @param authorites the new authorites
	 */
	public void setAuthorites(Collection<? extends GrantedAuthority> authorites) {
		this.authorites = authorites;
	}

	/**
	 * 获取 用户子机构代码.
	 *
	 * @return the childOrgId
	 */
	public Collection<String> getChildOrgId() {
		return childOrgId;
	}

	
	/**
	 * 设置 用户子机构代码.
	 *
	 * @param childOrgId the childOrgId to set
	 */
	public void setChildOrgId(Collection<String> childOrgId) {
		this.childOrgId = childOrgId;
	}
	
	
	/**
	 * Gets the child city id.
	 *
	 * @return the childOrgId
	 */
	public Collection<String> getChildCityId() {
		return childCityId;
	}


	
	/**
	 * Sets the child city id.
	 *
	 * @param childCityId the new child city id
	 */
	public void setChildCityId(Collection<String> childCityId) {
		this.childCityId = childCityId;
	}
	
	/**
	 * 获取 用户子商户代码 *.
	 *
	 * @return the 用户子商户代码 *
	 */
	public Collection<Long> getChildMerchantId() {
		return childMerchantId;
	}

	/**
	 * 设置 用户子商户代码 *.
	 *
	 * @param childMerchantId the new 用户子商户代码 *
	 */
	public void setChildMerchantId(Collection<Long> childMerchantId) {
		this.childMerchantId = childMerchantId;
	}
	
	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return this.getLoginPasswd();
	}

	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return this.getLoginId();
	}

	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

}
