package com.jiuyv.hhc.console.security.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.model.security.SysResourceVo;


/**
 * 
 * 
 * 
 * @author 
 *
 */
public class ResCacheFactory implements FactoryBean<ResourceCache> {

	private ISecurityService securityService ; 
	
	public ResourceCache getObject() throws Exception {
		Map<String, SysResourceVo> resMap = securityService.findFullResMapWithResId();
		ResourceCache cache = new ResourceCache();
		cache.setRoleResMap(resMap);
		cache.setUrlResMap(new HashMap<String, SysResourceVo>());
		// add url key map, for duplicate 
		for (Entry<String, SysResourceVo> e: resMap.entrySet()) {
			SysResourceVo r = e.getValue();
			String urls = r.getResUrl();
			if ( StringUtils.isNotBlank(urls) ) {
				String[] urlArr = urls.split(",");
				for ( String url : urlArr ) {
					if ( StringUtils.isNotBlank(url)) {
						url = url.trim();
						SysResourceVo res = newResVo(r);
						res.setResUrl(url);
						cache.getUrlResMap().put(url, res);
					}
				}
			}
		}
		return cache;
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	private SysResourceVo newResVo(SysResourceVo vo ) {
		SysResourceVo n = new SysResourceVo();
		
		n.setFatherId(vo.getFatherId());
		n.setLogFlag(vo.getLogFlag());
		n.setOrderNum(vo.getOrderNum());
		n.setRemarks(vo.getRemarks());
		n.setResId	(vo.getResId());
		n.setResEn(vo.getResEn());
		n.setResZh(vo.getResZh());
		n.setResUrl	(vo.getResUrl());
		n.setShowType(vo.getShowType());
		n.setSysCode(vo.getSysCode());
		return n ; 
	}

	public Class<?> getObjectType() {
		return ResourceCache.class;
	}

	/**
	 * 创建单例的ResourceCache对象
	 * @return
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

}
