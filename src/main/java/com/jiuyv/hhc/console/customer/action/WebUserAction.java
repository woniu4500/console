package com.jiuyv.hhc.console.customer.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.customer.service.IWebMemberService;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;

/**
 * <h1>The Class WebUserAction.</h1>
 * <p>Descriptions:网站注册用户管理Action</p>
 * 

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class WebUserAction extends DefaultPageSupportAction {

	/** The I web member service web member service. */
	private IWebMemberService webMemberService ; 
	
	/** The List me b_ col. */
	private static List<CellDataType> MEB_COL = new ArrayList<CellDataType>();
	
	static {
		MEB_COL.add(new CellDataType("customerCode", "会员代码"));
		MEB_COL.add(new CellDataType("mobile", "手机号码"));
		MEB_COL.add(new CellDataType("realName", "姓名"));
		MEB_COL.add(new CellDataType("address", "地址"));
		MEB_COL.add(new CellDataType("email", "电子邮件"));
		MEB_COL.add(new CellDataType("customerTypeDesc", "会员类型"));
		MEB_COL.add(new CellDataType("idNumber", "证件号码"));
		MEB_COL.add(new CellDataType("idType", "证件类型"));
		MEB_COL.add(new CellDataType("userStateDesc", "状态"));
	}
	/**
	 * Web user page.<br/>
	 * 分页查询会员信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String webUserPage() throws Exception {
		return resultData(webMemberService.findUserByPage(getFilters(), getPageInfo()));
	}
	
	
	/**
	 * Web user excel.<br/>
	 * 会员查询导出excel.
	 * @return the string
	 * @throws Exception the exception
	 */
	public String webUserExcel() throws Exception {
		return defaultExportXLS("会员信息", MEB_COL, webMemberService.findUserByPage(getFilters(), getExcelPageInfo()));
	}
	
	
	/**
	 * Lock web user.<br/>
	 * 锁定用户，锁定后用户不能登陆，不能进行找回密码操作
	 * @return the string
	 * @throws Exception the exception
	 */
	public String lockWebUser() throws Exception {
		return resultData(webMemberService.doLockWebUser(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	} 
	
	
	/**
	 * Unlock web user.<br/>
	 * 解锁用户
	 * @return the string
	 * @throws Exception the exception
	 */
	public String unlockWebUser() throws Exception {
		return resultData(webMemberService.doUnlockWebUser(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	}
	
	
	/**
	 * Reset web user passwd.<br/>
	 * 密码重置，默认为手机号码的后六位
	 * @return the string
	 * @throws Exception the exception
	 */
	public String resetWebUserPasswd() throws Exception {
		return resultData(webMemberService.doResetWebUserPasswd(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	}


	/**
	 * @param webMemberService the webMemberService to set
	 */
	public void setWebMemberService(IWebMemberService webMemberService) {
		this.webMemberService = webMemberService;
	}
	
}
