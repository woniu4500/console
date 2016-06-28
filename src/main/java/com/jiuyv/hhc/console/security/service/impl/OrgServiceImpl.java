package com.jiuyv.hhc.console.security.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notSame;
import static com.jiuyv.common.validate.BizCheck.same;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.security.service.IOrgService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.security.SysOrgVo;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.TreeOrg;
import com.jiuyv.hhc.model.security.dao.SysOrgDao;

/**
 * The Class OrgServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class OrgServiceImpl extends AssistService implements IOrgService {

	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** Constant String STATUS_DEL: String :. */
	private static final String STATUS_DEL = "1";
	
	/** The dao. */
	private SysOrgDao sysOrgDao;

	/**
	 * 删除机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysOrgVo> doDeleteOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception {
		notNull(orgVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		// 不允许删除-1机构
		notSame(orgVo.getOrgCode(),TreeUtil.ROOT_NODE,ErrorCode.CODE_1012,ErrorCode.CODE_1012_MSG);
		String orgCode = orgVo.getOrgCode();
		SysOrgVo pvo = sysOrgDao.findByKey(orgCode);
		notNull(pvo,ErrorCode.CODE_1011,ErrorCode.CODE_1011_MSG_1);
		same(pvo.getStatus(),STATUS_NORMAL,ErrorCode.CODE_1013,ErrorCode.CODE_1013_MSG);
		pvo.setStatus(STATUS_DEL);
		pvo.setVersion(orgVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(sysOrgDao, pvo));
	}

	/**
	 * 新增机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysOrgVo> doInsertOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception {
		notNull(orgVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		// 不允许新增 -1 机构
		notSame(orgVo.getOrgCode(),TreeUtil.ROOT_NODE,ErrorCode.CODE_1012,ErrorCode.CODE_1012_MSG);
		try {
			orgVo.setStatus(STATUS_NORMAL);
			orgVo.setLastUptAcc(userVo.getLoginId());
			orgVo.setLastUptOrg(userVo.getOrgCode());
			DBLogUtil.addLogInfo(orgVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(sysOrgDao, orgVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_1014, ErrorCode.CODE_1014_MSG, e);
		}
	}

	/**
	 * 修改机构.
	 *
	 * @param orgVo the org vo
	 * @param filters the filters
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysOrgVo> doUpdateOrg(SysOrgVo orgVo, List<Filter> filters, SysUserVo userVo)
			throws Exception {
		notNull(orgVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		// 不允许更新 -1 机构
		notSame(orgVo.getOrgCode(),TreeUtil.ROOT_NODE,ErrorCode.CODE_1012,ErrorCode.CODE_1012_MSG);
		String orgCode = orgVo.getOrgCode();
		SysOrgVo pvo = sysOrgDao.findByKey(orgCode);
		notNull(pvo,ErrorCode.CODE_1011,ErrorCode.CODE_1011_MSG_1);
		same(pvo.getStatus(),STATUS_NORMAL,ErrorCode.CODE_1013,ErrorCode.CODE_1013_MSG_1);
		
		pvo.setEmail(orgVo.getEmail());
		pvo.setFatherOrgCode(orgVo.getFatherOrgCode());
		pvo.setOrgAddr(orgVo.getOrgAddr());
		pvo.setOrgName(orgVo.getOrgName());
		pvo.setOrgType(orgVo.getOrgType());
		pvo.setPersName(orgVo.getPersName());
		pvo.setPersPhone(orgVo.getPersPhone());
		pvo.setVersion(orgVo.getVersion());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(sysOrgDao, pvo));
	}

	/**
	 * 查询机构.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< org vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysOrgVo> findOrg(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(SysOrgDao.MAPPED_FIND, filters, page);
	}

	/**
	 * 查询所有机构.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<TreeOrg> findAllOrg(List<Filter> filters, Page page) throws Exception {
		return getQueryAssist().list(SysOrgDao.MAPPED_TREEORG_FIND, filters, page);
	}

	/**
	 * 设置 the dao.
	 *
	 * @param sysOrgDao the new dao
	 */
	public void setSysOrgDao(SysOrgDao sysOrgDao) {
		this.sysOrgDao = sysOrgDao;
	}

}
