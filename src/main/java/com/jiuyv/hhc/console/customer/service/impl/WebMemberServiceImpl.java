package com.jiuyv.hhc.console.customer.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.isTrue;
import static com.jiuyv.common.validate.BizCheck.same;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.encode.DES4WebUser;
import com.jiuyv.hhc.console.customer.service.IWebMemberService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.customer.CustomerDict;
import com.jiuyv.hhc.model.customer.MemberInfoVo;
import com.jiuyv.hhc.model.customer.WebAgentRcmdVo;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.customer.dao.WebAgentRcmdDao;
import com.jiuyv.hhc.model.customer.dao.WebMemberInfoDao;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.customer.CustomerDict.*;


/**
 * <h1>The Class WebMemberServiceImpl.</h1>
 * <p>Descriptions: </p>
 * 

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class WebMemberServiceImpl extends AssistService implements
		IWebMemberService {

	
	/** The Web member info dao web member info dao. */
	private WebMemberInfoDao webMemberInfoDao;
	
	/** The Web agent rcmd dao web agent rcmd dao. */
	private WebAgentRcmdDao webAgentRcmdDao ; 
	
	
	/**
	 * Find user by page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#findUserByPage(java.util.List, com.jiuyv.common.database.Page)
	 */
	public ExtData<WebMemberInfoVo> findUserByPage(List<Filter> filters,
			Page pageInfo) throws BaseException {
		filters = filters==null?new ArrayList<Filter>():filters;
		filters.add(new Filter(WebCustomerField.CUSTOMER_TYPE, Filter.STRING, MemberType.USER, Filter.Comparison.EQ));
		return getQueryAssist().page(WebMemberInfoDao.MAPPED_FIND, filters, pageInfo);
	}

	
	/**
	 * Find agent by page.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#findAgentByPage(java.util.List, com.jiuyv.common.database.Page)
	 */
	public ExtData<WebMemberInfoVo> findAgentByPage(List<Filter> filters,
			Page pageInfo) throws BaseException {
		filters = filters==null?new ArrayList<Filter>():filters;
		filters.add(new Filter(WebCustomerField.CUSTOMER_TYPE, Filter.STRING, MemberType.AGENT, Filter.Comparison.EQ));
		return getQueryAssist().page(WebMemberInfoDao.MAPPED_FIND, filters, pageInfo);
	}

	
	/**
	 * Find rcmd list.
	 *
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#findRcmdList(com.jiuyv.hhc.model.customer.WebMemberInfoVo)
	 */
	public ExtData<WebAgentRcmdVo> findRcmdList(WebMemberInfoVo webMember)
			throws BaseException {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter(WebCustomerField.CUSOMTER_CODE, Filter.NUMERIC, webMember.getCustomerCode(), Filter.Comparison.EQ));
		List<WebAgentRcmdVo> list = getQueryAssist().list(WebAgentRcmdDao.MAPPED_FIND, filters);
		return ExtDataUtil.genWithData(list);
	}
	
	/**
	 * Do lock web user.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#doLockWebUser(com.jiuyv.hhc.model.security.SysUserVo, com.jiuyv.hhc.model.customer.WebMemberInfoVo)
	 */
	public ExtData<WebMemberInfoVo> doLockWebUser(SysUserVo userInfo,
			WebMemberInfoVo webMember) throws BaseException {
		// precheck member state and exist
		notNull(webMember, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = webMember.getCustomerCode();
		WebMemberInfoVo pvo = webMemberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getUserState(), UserState.NORMAL, ErrorCode.CODE_2002, ErrorCode.CODE_2002_MSG_2);
		// set state to delete
		pvo.setVersion(webMember.getVersion());
		pvo.setUserState(UserState.DELETE);
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		// log and update
		WebMemberInfoVo mvo = new WebMemberInfoVo();
		mvo.setCustomerCode(pvo.getCustomerCode());
		mvo.setLastUptAcc(pvo.getLastUptAcc());
		mvo.setLastUptOrg(pvo.getLastUptOrg());
		mvo.setUserState(pvo.getUserState());
		DBLogUtil.addLogInfo(mvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(webMemberInfoDao, pvo));
	}

	
	/**
	 * Do unlock web user.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#doUnlockWebUser(com.jiuyv.hhc.model.security.SysUserVo, com.jiuyv.hhc.model.customer.WebMemberInfoVo)
	 */
	public ExtData<WebMemberInfoVo> doUnlockWebUser(SysUserVo userInfo,
			WebMemberInfoVo webMember) throws BaseException {
		// precheck member state and exist
		notNull(webMember, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = webMember.getCustomerCode();
		WebMemberInfoVo pvo = webMemberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getUserState(), UserState.DELETE, ErrorCode.CODE_2002, ErrorCode.CODE_2002_MSG_2);
		// set state to delete
		pvo.setVersion(webMember.getVersion());
		pvo.setUserState(UserState.NORMAL);
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		// log and update
		WebMemberInfoVo mvo = new WebMemberInfoVo();
		mvo.setCustomerCode(pvo.getCustomerCode());
		mvo.setLastUptAcc(pvo.getLastUptAcc());
		mvo.setLastUptOrg(pvo.getLastUptOrg());
		mvo.setUserState(pvo.getUserState());
		DBLogUtil.addLogInfo(mvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(webMemberInfoDao, pvo));
	}

	
	/**
	 * Do reset web user passwd.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#doResetWebUserPasswd(com.jiuyv.hhc.model.security.SysUserVo, com.jiuyv.hhc.model.customer.WebMemberInfoVo)
	 */
	public ExtData<WebMemberInfoVo> doResetWebUserPasswd(SysUserVo userInfo,
			WebMemberInfoVo webMember) throws BaseException {
		// precheck member state and exist
		notNull(webMember, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = webMember.getCustomerCode();
		WebMemberInfoVo pvo = webMemberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		// set default password
		pvo.setPassword(DES4WebUser.encrypt(pvo.getMobile().substring(pvo.getMobile().length()-6)));
		pvo.setVersion(webMember.getVersion());
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		// log and update
		WebMemberInfoVo mvo = new WebMemberInfoVo();
		mvo.setCustomerCode(pvo.getCustomerCode());
		mvo.setLastUptAcc(pvo.getLastUptAcc());
		mvo.setLastUptOrg(pvo.getLastUptOrg());
		DBLogUtil.addLogInfo(mvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(webMemberInfoDao, pvo));
	}

	
	

	
	/**
	 * Do add web agent.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#doAddWebAgent(com.jiuyv.hhc.model.security.SysUserVo, com.jiuyv.hhc.model.customer.WebMemberInfoVo)
	 */
	public ExtData<WebMemberInfoVo> doAddWebAgent(SysUserVo userInfo,
			WebMemberInfoVo webMember) throws BaseException {
		notNull(webMember, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		isTrue(StringUtils.length(webMember.getMobile()) >= 11 , ErrorCode.CODE_2004, ErrorCode.CODE_2004_MSG);
		webMember.setCustomerType(MemberType.AGENT);
		webMember.setUserState(UserState.NORMAL);
		webMember.setPasswdErr(0l);
		webMember.setPassword(DES4WebUser.encrypt(webMember.getMobile().substring(webMember.getMobile().length()-6)));
		webMember.setLastUptAcc(userInfo.getLoginId());
		webMember.setLastUptOrg(userInfo.getOrgCode());
		
		try {
			WebMemberInfoVo dbvo = getQueryAssist().insertFetch(webMemberInfoDao, webMember);
			// add default recommand code
			WebAgentRcmdVo rcmdvo = new WebAgentRcmdVo();
			rcmdvo.setRcmdCode(dbvo.getMobile());
			rcmdvo.setCustomerCode(dbvo.getCustomerCode());
			rcmdvo.setRcmdDesc(CustomerDict.DEF_RCMD_DESC);
			rcmdvo.setRcmdCnt(0l);
			webAgentRcmdDao.insert(rcmdvo);
			
			DBLogUtil.addLogInfo(dbvo);
			return ExtDataUtil.genWithSingleData(dbvo);
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2003, ErrorCode.CODE_2003_MSG, e);
		}
		
	}

	
	/**
	 * Do upd web agent.
	 *
	 * @param userInfo the user info
	 * @param webMember the web member
	 * @param rcmdList the rcmd list
	 * @return the ext data
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.customer.service.IWebMemberService#doUpdWebAgent(com.jiuyv.hhc.model.security.SysUserVo, com.jiuyv.hhc.model.customer.WebMemberInfoVo, java.util.List)
	 */
	public ExtData<WebMemberInfoVo> doUpdWebAgent(SysUserVo userInfo,
			WebMemberInfoVo webMember )
			throws BaseException {
		// precheck member state and exist
		notNull(webMember, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = webMember.getCustomerCode();
		WebMemberInfoVo pvo = webMemberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getUserState(), UserState.NORMAL, ErrorCode.CODE_2002, ErrorCode.CODE_2002_MSG_2);
		
		pvo.setVersion(webMember.getVersion());
		pvo.setAddress(webMember.getAddress());
		pvo.setEmail(webMember.getEmail());
		pvo.setFace(webMember.getFace());
		pvo.setIdNumber(webMember.getIdNumber());
		pvo.setIdType(webMember.getIdType());
		pvo.setPhone(webMember.getPhone());
		pvo.setRealName(webMember.getRealName());
		pvo.setZip(webMember.getZip());
		
		pvo.setLastUptAcc(userInfo.getLoginId());
		pvo.setLastUptOrg(userInfo.getOrgCode());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(webMemberInfoDao, pvo));
	}

	
	/**
	 * Do add web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<WebAgentRcmdVo> doAddWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException {
		notNull(agentRcmd, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notNull(agentRcmd.getCustomerCode(), ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		isTrue(StringUtils.length(agentRcmd.getRcmdCode()) >= 15, ErrorCode.CODE_2005, ErrorCode.CODE_2005_MSG );
		agentRcmd.setRcmdCnt(0l);
		try{	
			WebAgentRcmdVo vo = getQueryAssist().insertFetch(webAgentRcmdDao, agentRcmd);
			DBLogUtil.addLogInfo(vo);
			return ExtDataUtil.genWithSingleData(vo);
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2006, ErrorCode.CODE_2006_MSG, e);
		}
	}
	
	/**
	 * Do upd web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<WebAgentRcmdVo> doUpdWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException {
		notNull(agentRcmd, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notNull(agentRcmd.getCustomerCode(), ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		String rcmdCode = agentRcmd.getRcmdCode();
		notNull(rcmdCode, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		WebAgentRcmdVo dbvo = webAgentRcmdDao.findByKey(rcmdCode);
		notNull(dbvo, ErrorCode.CODE_2007, ErrorCode.CODE_2007_MSG);
		
		dbvo.setRcmdDesc(agentRcmd.getRcmdDesc());
		
		WebAgentRcmdVo vo = getQueryAssist().updateFetch(webAgentRcmdDao, dbvo);
		DBLogUtil.addLogInfo(vo);
		return ExtDataUtil.genWithSingleData(vo);
		
	}
	
	/**
	 * Do del web agent rcmd.
	 *
	 * @param userInfo the user info
	 * @param agentRcmd the agent rcmd
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<WebAgentRcmdVo> doDelWebAgentRcmd(SysUserVo userInfo, WebAgentRcmdVo agentRcmd) throws BaseException {
		notNull(agentRcmd, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notNull(agentRcmd.getCustomerCode(), ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = agentRcmd.getCustomerCode();
		WebMemberInfoVo pvo = webMemberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		
		String rcmdCode = agentRcmd.getRcmdCode();
		isTrue(!StringUtils.equals(rcmdCode, pvo.getMobile()), "", "手机号为预置的推荐码，不能删除");
		notNull(rcmdCode, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		WebAgentRcmdVo dbvo = webAgentRcmdDao.findByKey(rcmdCode);
		notNull(dbvo, ErrorCode.CODE_2007, ErrorCode.CODE_2007_MSG);
		
		Long referCnt = webAgentRcmdDao.countRefered(rcmdCode);
		isTrue(referCnt.compareTo(0l)==0, ErrorCode.CODE_2008, ErrorCode.CODE_2008_MSG);
		webAgentRcmdDao.deleteByKey(rcmdCode);
		DBLogUtil.addLogInfo(dbvo);
		return ExtDataUtil.genWithSingleData(dbvo);
	}

	
	/**
	 * 根据CustomerCode查询用户
	 * @param customerCode
	 * @return
	 */
	public WebMemberInfoVo findUserByCustomerCode(String customerCode) {
		Long custCode = NumberUtils.toLong(customerCode);
		return webMemberInfoDao.findByKey(custCode);
	}

	/**
	 * 设置 the Web member info dao web member info dao.
	 *
	 * @param webMemberInfoDao the webMemberInfoDao to set
	 */
	public void setWebMemberInfoDao(WebMemberInfoDao webMemberInfoDao) {
		this.webMemberInfoDao = webMemberInfoDao;
	}


	/**
	 * @param webAgentRcmdDao the webAgentRcmdDao to set
	 */
	public void setWebAgentRcmdDao(WebAgentRcmdDao webAgentRcmdDao) {
		this.webAgentRcmdDao = webAgentRcmdDao;
	}

}
