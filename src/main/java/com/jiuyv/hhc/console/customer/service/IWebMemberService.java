package com.jiuyv.hhc.console.customer.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.customer.WebAgentRcmdVo;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Interface IWebMemberService.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public interface IWebMemberService {

	/**
	 * Find user by page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> findUserByPage(List<Filter> filters, Page pageInfo) throws BaseException;
	
	/**
	 * Find agent user by page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> findAgentByPage(List<Filter> filters, Page pageInfo) throws BaseException;

	
	/**
	 * Do lock web user.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> doLockWebUser(SysUserVo userInfo, WebMemberInfoVo webMember) throws BaseException;

	/**
	 * Do unlock web user.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> doUnlockWebUser(SysUserVo userInfo, WebMemberInfoVo webMember) throws BaseException;

	/**
	 * Do reset web user passwd.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> doResetWebUserPasswd(SysUserVo userInfo, WebMemberInfoVo webMember) throws BaseException;

	
	/**
	 * Find rcmd list.
	 *
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebAgentRcmdVo> findRcmdList(WebMemberInfoVo webMember) throws BaseException;

	/**
	 * Do add web agent.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> doAddWebAgent(SysUserVo userInfo, WebMemberInfoVo webMember) throws BaseException;

	/**
	 * Do upd web agent.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebMemberInfoVo> doUpdWebAgent(SysUserVo userInfo, WebMemberInfoVo webMember ) throws BaseException;

	/**
	 * Do add web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebAgentRcmdVo> doAddWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException;
	
	/**
	 * Do upd web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebAgentRcmdVo> doUpdWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException;
	
	/**
	 * Do del web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	ExtData<WebAgentRcmdVo> doDelWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException;

	/**
	 * 根据CustomerCode查询用户
	 * @param customerCode
	 * @return
	 */
	WebMemberInfoVo findUserByCustomerCode(String customerCode);
	
}
