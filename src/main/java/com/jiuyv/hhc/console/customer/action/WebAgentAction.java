package com.jiuyv.hhc.console.customer.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.customer.service.IWebMemberService;
import com.jiuyv.hhc.model.customer.WebAgentRcmdVo;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;

/**
 * <h1>The Class WebAgentAction.</h1>
 * <p>Descriptions:网站代理用户管理Action</p>
 * 

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class WebAgentAction extends DefaultPageSupportAction {

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
	 * Web agent page.<br/>
	 * 分页查询代理会员信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String webAgentPage() throws Exception {
		return resultData(webMemberService.findAgentByPage(getFilters(), getPageInfo()));
	}
	
	
	/**
	 * Web user excel.<br/>
	 * 会员查询导出excel.
	 * @return the string
	 * @throws Exception the exception
	 */
	public String webAgentExcel() throws Exception {
		return defaultExportXLS("代理会员信息", MEB_COL, webMemberService.findAgentByPage(getFilters(), getExcelPageInfo()));
	}
	
	
	/**
	 * Web agent rcmd list.
	 * 会员推荐码列表查询
	 * @return the string
	 * @throws Exception the exception
	 */
	public String webAgentRcmdList () throws Exception {
		return resultData(webMemberService.findRcmdList(getValidateBean(WebMemberInfoVo.class)));
	}
	
	/**
	 * Adds the web agent rcmd.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String addWebAgentRcmd () throws Exception {
		return resultData(webMemberService.doAddWebAgentRcmd(getUserInfo(), getValidateBean(WebAgentRcmdVo.class)));
	}
	
	/**
	 * Upd web agent rcmd.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updWebAgentRcmd () throws Exception {
		return resultData(webMemberService.doUpdWebAgentRcmd(getUserInfo(), getValidateBean(WebAgentRcmdVo.class)));
	}
	
	/**
	 * Del web agent rcmd.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String delWebAgentRcmd () throws Exception {
		return resultData(webMemberService.doDelWebAgentRcmd(getUserInfo(), getValidateBean(WebAgentRcmdVo.class)));
	}
	
	/**
	 * Random code.
	 * 生成随机的推荐码
	 * @return the string
	 * @throws Exception the exception
	 */
	public String randomCode () throws Exception {
		return resultData(ExtDataUtil.genWithSingleData(UUID.randomUUID().toString().replaceAll("-", "")));
	}
	
	
	/**
	 * Adds the web agent.
	 * 新增代理会员，
	 * 默认插入一条和手机号一致的推荐号
	 * 默认密码为手机号后六位
	 * 代理会员手机号如果已经是注册会员，则将原注册会员转为代理会员。
	 * @return the string
	 * @throws Exception the exception
	 */
	public String addWebAgent() throws Exception {
		return resultData(webMemberService.doAddWebAgent(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	}
	
	
	/**
	 * Upd web agent.
	 * 更新代理会员信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updWebAgent() throws Exception {
		return resultData(webMemberService.doUpdWebAgent(getUserInfo(), getValidateBean(WebMemberInfoVo.class) ));
	}
	
	
	/**
	 * Lock web user.<br/>
	 * 锁定用户，锁定后用户不能登陆，不能进行找回密码操作
	 * @return the string
	 * @throws Exception the exception
	 */
	public String lockWebAgent() throws Exception {
		return resultData(webMemberService.doLockWebUser(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	} 
	
	
	/**
	 * Unlock web user.<br/>
	 * 解锁用户
	 * @return the string
	 * @throws Exception the exception
	 */
	public String unlockWebAgent() throws Exception {
		return resultData(webMemberService.doUnlockWebUser(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	}
	
	
	/**
	 * Reset web user passwd.<br/>
	 * 密码重置，默认为手机号码的后六位
	 * @return the string
	 * @throws Exception the exception
	 */
	public String resetWebAgentPasswd() throws Exception {
		return resultData(webMemberService.doResetWebUserPasswd(getUserInfo(), getValidateBean(WebMemberInfoVo.class)));
	}


	/**
	 * @param webMemberService the webMemberService to set
	 */
	public void setWebMemberService(IWebMemberService webMemberService) {
		this.webMemberService = webMemberService;
	}
	
}
