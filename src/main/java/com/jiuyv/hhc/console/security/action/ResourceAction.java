package com.jiuyv.hhc.console.security.action;

import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Filter.Comparison;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IResourceService;


/**
 * The Class ResourceAction.
 *

 * @author 
 * @since 2014-1-3 16:04:26
 * @version 1.0.0
 */
public class ResourceAction extends DefaultPageSupportAction{

	/** The Service. */
	private IResourceService service;

	/** 参数的过滤参数 *. */
	private String resourceID;

	/**
	 * Find resource list.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findResourceList() throws Exception {
		List<Filter> tempFilters = getFilters();
		tempFilters.add(new Filter("logFlg", Filter.STRING, "1", Comparison.EQ));
		return resultData(service.findResourceList(tempFilters));
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IResourceService service) {
		this.service = service;
	}

	/**
	 * 获取 参数的过滤参数 *.
	 *
	 * @return the 参数的过滤参数 *
	 */
	public String getResourceID() {
		return resourceID;
	}

	/**
	 * 设置 参数的过滤参数 *.
	 *
	 * @param resourceID the new 参数的过滤参数 *
	 */
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
}
