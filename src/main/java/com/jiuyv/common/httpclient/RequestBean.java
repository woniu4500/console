package com.jiuyv.common.httpclient;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * The Class RequestBean.
 * 
 * @author
 */
public class RequestBean {

	/** Url. */
	private String delegateUrl;
	
	/** 键值对. */
	private List<NameValuePair> list;

	/**
	 * Gets the list.
	 * 
	 * @return the list
	 */
	public List<NameValuePair> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 * 
	 * @param list
	 *            the new list
	 */
	public void setList(List<NameValuePair> list) {
		this.list = list;
	}

	/**
	 * Gets the delegate url.
	 * 
	 * @return the delegateUrl
	 */
	public String getDelegateUrl() {
		return delegateUrl;
	}

	/**
	 * Sets the delegate url.
	 * 
	 * @param delegateUrl
	 *            the delegateUrl to set
	 */
	public void setDelegateUrl(String delegateUrl) {
		this.delegateUrl = delegateUrl;
	}

}
