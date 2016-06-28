package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IUserService;


/**
 * The Class UserRoleAction.
 *

 * @author 
 * @since 2014-1-3 16:21:50
 * @version 1.0.0
 */
public class UserRoleAction extends DefaultPageSupportAction {
	
	/** The Service. */
	private IUserService service;

	/** The List org list. */
	private static List<CellDataType> orgList = new ArrayList<CellDataType>();
	static {
		orgList.add(new CellDataType("userName", "用户名"));
		orgList.add(new CellDataType("roleName", "角色名"));
		orgList.add(new CellDataType("userId", "用户id"));
		orgList.add(new CellDataType("roleId", "角色id"));
	}

	/**
	 * 用户角色列表.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findUserRole() throws Exception {
		return resultData(service.findUserRole(getFilters(), getPageInfo()));
	}

	/**
	 * Find user role excel.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findUserRoleExcel() throws Exception {
		return defaultExportXLS("用户角色信息", orgList
				, service.findUserRole(getFilters(), getExcelPageInfo()));
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IUserService service) {
		this.service = service;
	}
}
