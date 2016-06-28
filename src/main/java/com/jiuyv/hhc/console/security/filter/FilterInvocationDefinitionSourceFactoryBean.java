package com.jiuyv.hhc.console.security.filter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.jiuyv.hhc.console.security.service.ISecurityService;


/**
 * The Class FilterInvocationDefinitionSourceFactoryBean.
 *

 * @author 
 * @since 2014-1-2 16:39:02
 * @version 1.0.0
 */
public class FilterInvocationDefinitionSourceFactoryBean implements FactoryBean<FilterInvocationSecurityMetadataSource> {
	
	/** 严格校验，未配制的url也不允许访问，默认为false不校验. */
	private boolean strictUrl = false;

	/** The security service. */
	private ISecurityService securityService ; 
	
	/**
	 * 设置 严格校验，未配制的url也不允许访问，默认为false不校验.
	 *
	 * @param strictUrl the new 严格校验，未配制的url也不允许访问，默认为false不校验
	 */
	public void setStrictUrl(boolean strictUrl) {
		this.strictUrl = strictUrl;
	}

	/**
	 * @return
	 * @throws Exception
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public FilterInvocationSecurityMetadataSource getObject() throws Exception {
		return new DefaultFilterInvocationSecurityMetadataSource(this.buildRequestMap());
	}

	/**
	 * @return
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class<FilterInvocationSecurityMetadataSource> getObjectType() {
		return FilterInvocationSecurityMetadataSource.class;
	}

	/**
	 * @return
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * Builds the request map.
	 *
	 * @return the linked hash map
	 */
	protected LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		// 获取所有资源
		Map<String, String> resourceMap = securityService.findFullResMapWithUrlKey(); 
		if ( strictUrl ) {
			resourceMap.put("/**", "ROLE_-1XYZ");
		}
		if (resourceMap != null) {
			for (Map.Entry<String, String> entry : resourceMap.entrySet()) {
				RequestMatcher matcher=new AntPathRequestMatcher(entry.getKey());
				requestMap.put(matcher, SecurityConfig.createListFromCommaDelimitedString(entry.getValue()));
			}
		}
		return requestMap;
	}

	/**
	 * Sets the security service.
	 *
	 * @param securityService the new security service
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}


}
