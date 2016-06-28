package com.jiuyv.hhc.console.customer.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.same;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.encode.DES;
import com.jiuyv.hhc.console.customer.service.IMemberService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.customer.MemberInfoVo;
import com.jiuyv.hhc.model.customer.dao.MemberInfoDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class MemberServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class MemberServiceImpl extends AssistService implements IMemberService {

	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** Constant String STATUS_DEL: String :. */
	private static final String STATUS_DEL = "1";
	/** 默认密码:1111 */
	private static final String DEF_PASSWD = "1111";
	/** The dao. */
	private MemberInfoDao memberInfoDao;

	/**
	 * 删除.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> doRecoverMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception {
		notNull(memberVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = memberVo.getCustomerCode();
		MemberInfoVo pvo = memberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getIsActive(),STATUS_DEL,ErrorCode.CODE_2002,ErrorCode.CODE_2002_MSG);
		pvo.setIsActive(STATUS_NORMAL);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(memberInfoDao, pvo));
	}

	/**
	 * 解锁.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> doDeleteMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception {
		notNull(memberVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = memberVo.getCustomerCode();
		MemberInfoVo pvo = memberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getIsActive(),STATUS_NORMAL,ErrorCode.CODE_2002,ErrorCode.CODE_2002_MSG);
		pvo.setIsActive(STATUS_DEL);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(memberInfoDao, pvo));
	}
	
	/**
	 * 新增.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> doInsertMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception {
		notNull(memberVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		try {
			memberVo.setIsActive(STATUS_NORMAL);
			memberVo.setLastUptAcc(userVo.getLoginId());
			memberVo.setLastUptOrg(userVo.getOrgCode());
			DBLogUtil.addLogInfo(memberVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(memberInfoDao, memberVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_2003, ErrorCode.CODE_2003_MSG, e);
		}
	}

	/**
	 * 修改.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> doUpdateMember(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception {
		notNull(memberVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = memberVo.getCustomerCode();
		MemberInfoVo pvo = memberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getIsActive(),STATUS_NORMAL,ErrorCode.CODE_2002,ErrorCode.CODE_2002_MSG_1);
		
		pvo.setAddress(memberVo.getAddress());
		pvo.setCustomerType(memberVo.getCustomerType());
		pvo.setEmail(memberVo.getEmail());
		pvo.setFace(memberVo.getFace());
		pvo.setIdNumber(memberVo.getIdNumber());
		pvo.setIdType(memberVo.getIdType());
		pvo.setLastLoginIp(memberVo.getLastLoginIp());
		pvo.setLastLoginTime(memberVo.getLastLoginTime());
		pvo.setLinkCustomerSeq(memberVo.getLinkCustomerSeq());
		pvo.setPassword(memberVo.getPassword());
		pvo.setPhone(memberVo.getPhone());
		pvo.setRealName(memberVo.getRealName());
		pvo.setRegisterIp(memberVo.getRegisterIp());
		pvo.setZip(memberVo.getZip());
		pvo.setFatherCustomerCode(memberVo.getFatherCustomerCode());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(memberInfoDao, pvo));
	}

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> findMember(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(MemberInfoDao.MAPPED_FIND, filters, page);
	}

	/**
	 * 重置密码.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	public ExtData<MemberInfoVo> doResetMemberPasswd(MemberInfoVo memberVo,SysUserVo userVo)
			throws Exception{
		notNull(memberVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long customerCode = memberVo.getCustomerCode();
		MemberInfoVo pvo = memberInfoDao.findByKey(customerCode);
		notNull(pvo,ErrorCode.CODE_2001,ErrorCode.CODE_2001_MSG);
		same(pvo.getIsActive(),STATUS_NORMAL,ErrorCode.CODE_2002,ErrorCode.CODE_2002_MSG_1);
		pvo.setVersion(memberVo.getVersion());
		pvo.setPassword(DES.encrypt(DEF_PASSWD));
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		MemberInfoVo mvo=new MemberInfoVo();
		mvo.setCustomerCode(pvo.getCustomerCode());
		DBLogUtil.addLogInfo(mvo);
		return ExtDataUtil.genWithSingleData( getQueryAssist().updateFetch(memberInfoDao, pvo));
	}
			
	/**
	 * 设置 the dao.
	 *
	 * @param memberInfoDao the new dao
	 */
	public void setMemberInfoDao(MemberInfoDao memberInfoDao) {
		this.memberInfoDao = memberInfoDao;
	}

}
