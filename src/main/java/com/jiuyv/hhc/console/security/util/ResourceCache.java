package com.jiuyv.hhc.console.security.util;

import java.util.Map;

import com.jiuyv.hhc.model.security.SysResourceVo;


public class ResourceCache {

	/** key:resId */
	private Map<String, SysResourceVo> roleResMap;
	/** key:url */
	private Map<String, SysResourceVo> urlResMap;
	
	public Map<String, SysResourceVo> getRoleResMap() {
		return roleResMap;
	}
	public void setRoleResMap(Map<String, SysResourceVo> roleResMap) {
		this.roleResMap = roleResMap;
	}
	public Map<String, SysResourceVo> getUrlResMap() {
		return urlResMap;
	}
	public void setUrlResMap(Map<String, SysResourceVo> urlResMap) {
		this.urlResMap = urlResMap;
	}


}
