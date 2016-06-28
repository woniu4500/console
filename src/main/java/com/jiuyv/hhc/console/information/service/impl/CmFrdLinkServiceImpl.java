package com.jiuyv.hhc.console.information.service.impl;
import static com.jiuyv.common.validate.BizCheck.isTrue;
import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notSame;
import static com.jiuyv.common.validate.BizCheck.same;
import static com.jiuyv.hhc.model.information.CmsDict.*;
import java.util.List;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.information.service.ICmFrdLinkService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.information.CmFrdLinkVo;
import com.jiuyv.hhc.model.information.CmsDict.ArtStatus;
import com.jiuyv.hhc.model.information.CmsDict.CatStatus;
import com.jiuyv.hhc.model.information.dao.CmFrdLinkDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Class CmArticleServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class CmFrdLinkServiceImpl extends AssistService implements ICmFrdLinkService {
	
	/** The Cm article dao cm article dao. */
	private CmFrdLinkDao cmFrdLinkDao;
	
	/**
	 * @param vo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doInsert(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doInsert(CmFrdLinkVo cmFrdLinkVo, SysUserVo userVo)
			throws Exception {
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		cmFrdLinkVo.setRecCrtAcc(userVo.getOrgCode());
		cmFrdLinkVo.setLastUptAcc(userVo.getLoginId());
		cmFrdLinkVo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(cmFrdLinkVo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(cmFrdLinkDao, cmFrdLinkVo));
	}

	
	/**
	 * @param CmFrdLinkVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doUpdate(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doUpdate(CmFrdLinkVo cmFrdLinkVo,
			SysUserVo userVo) throws Exception {
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long frdSeq = cmFrdLinkVo.getFrdSeq();
		CmFrdLinkVo pvo = cmFrdLinkDao.findByKey(frdSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		notSame(pvo.getFrdStatus(),FrdStatus.DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_3);
		notSame(pvo.getFrdStatus(),FrdStatus.TO_DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_3);
		notSame(pvo.getFrdStatus(),FrdStatus.SUBMIT,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_4);
		// set object info
		pvo.setFrdName  (cmFrdLinkVo.getFrdName  ());
		pvo.setFrdType  (cmFrdLinkVo.getFrdType  ());
		pvo.setFrdStyle (cmFrdLinkVo.getFrdStyle ());
		pvo.setFrdLogo  (cmFrdLinkVo.getFrdLogo  ());
		pvo.setFrdSort  (cmFrdLinkVo.getFrdSort  ());
		pvo.setFrdUrl   (cmFrdLinkVo.getFrdUrl   ());
		pvo.setIsVisible(cmFrdLinkVo.getIsVisible());
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmFrdLinkVo.getVersion());
		// 04,01 to 01  , 00 to 00
		if(!pvo.getFrdStatus().equals(FrdStatus.FIRST_DRAFT)){
			pvo.setFrdStatus(FrdStatus.DRAFT);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmFrdLinkDao, pvo));
	}

	
	/**
	 * @param CmFrdLinkVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doDelete(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doDelete(CmFrdLinkVo cmFrdLinkVo,
			SysUserVo userVo) throws Exception {
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long frdSeq = cmFrdLinkVo.getFrdSeq();
		CmFrdLinkVo pvo = cmFrdLinkDao.findByKey(frdSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		
		if(!pvo.getFrdStatus().equals(ArtStatus.FIRST_DRAFT)
				&&!pvo.getFrdStatus().equals(ArtStatus.DRAFT)
				&&!pvo.getFrdStatus().equals(ArtStatus.ISSUE)
				&&!pvo.getFrdStatus().equals(ArtStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_4);
		}
		if ( pvo.getFrdStatus().equals(ArtStatus.FIRST_DRAFT) ) {
			// 初稿状态直接删除
			pvo.setFrdStatus(CatStatus.DELETE);
		} else {
			// 发布后及草稿状态需提交审核
			pvo.setFrdStatus(CatStatus.TO_DELETE);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmFrdLinkVo.getVersion());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmFrdLinkDao, pvo));
	}

	
	/**
	 * @param CmFrdLinkVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doCommit(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doCommit(CmFrdLinkVo cmFrdLinkVo,
			SysUserVo userVo) throws Exception {
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long frdSeq = cmFrdLinkVo.getFrdSeq();
		CmFrdLinkVo pvo = cmFrdLinkDao.findByKey(frdSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		if(!pvo.getFrdStatus().equals(FrdStatus.FIRST_DRAFT)
				&&!pvo.getFrdStatus().equals(FrdStatus.DRAFT)
				&&!pvo.getFrdStatus().equals(FrdStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_1);
		}
		pvo.setFrdStatus(FrdStatus.SUBMIT);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmFrdLinkVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmFrdLinkDao, pvo));
	}

	
	/**
	 * @param CmFrdLinkVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doReject(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doReject(CmFrdLinkVo cmFrdLinkVo,
			SysUserVo userVo) throws Exception {
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long frdSeq = cmFrdLinkVo.getFrdSeq();
		CmFrdLinkVo pvo = cmFrdLinkDao.findByKey(frdSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
//		same(pvo.getFrdStatus(),FrdStatus.SUBMIT,ErrorCode.CODE_3005,ErrorCode.CODE_3005_MSG_3);
		isTrue(pvo.getFrdStatus().equals(ArtStatus.SUBMIT)||pvo.getFrdStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_5);
		
		
		int verCount = cmFrdLinkDao.countVerByKey(frdSeq);
		if ( verCount == 0  ){
			// 未发布驳回后返回初稿状态
			pvo.setFrdStatus(FrdStatus.FIRST_DRAFT);
		} else {
			// 返回驳回状态
			pvo.setFrdStatus(FrdStatus.REJECT);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmFrdLinkVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmFrdLinkDao, pvo));
	}

	
	/**
	 * @param CmFrdLinkVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doRelease(com.jiuyv.hhc.model.information.CmFrdLinkVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmFrdLinkVo> doRelease(CmFrdLinkVo cmFrdLinkVo,
			SysUserVo userVo) throws Exception {
		// precheck
		notNull(cmFrdLinkVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long frdSeq = cmFrdLinkVo.getFrdSeq();
		CmFrdLinkVo pvo = cmFrdLinkDao.findByKey(frdSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
		// same(pvo.getFrdStatus(),FrdStatus.SUBMIT, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_2);
		// setvalue
		isTrue(pvo.getFrdStatus().equals(ArtStatus.SUBMIT)||pvo.getFrdStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_4);
		
		if ( pvo.getFrdStatus().equals(ArtStatus.SUBMIT) ) {
			pvo.setFrdStatus(ArtStatus.ISSUE);
		} else {
			pvo.setFrdStatus(ArtStatus.DELETE);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmFrdLinkVo.getVersion());
		
		CmFrdLinkVo dvo = getQueryAssist().updateFetch(cmFrdLinkDao, pvo);
		// save to version table
		int verCount = cmFrdLinkDao.countVerByKey(frdSeq);
		if ( verCount == 0  ){
			cmFrdLinkDao.insertVer(dvo);
		} else {
			cmFrdLinkDao.updateVerByKey(dvo);
		}
		if ( pvo.getFrdStatus().equals(ArtStatus.DELETE) ) {
			cmFrdLinkDao.deleteVer(dvo);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(dvo);
	}

	
	/**
	 * @param filters
	 * @param page
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#find(java.util.List, com.jiuyv.common.database.Page)
	 */
	public ExtData<CmFrdLinkVo> find(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(CmFrdLinkDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * @param cmFrdLinkDao the cmFrdLinkDao to set
	 */
	public void setCmFrdLinkDao(CmFrdLinkDao cmFrdLinkDao) {
		this.cmFrdLinkDao = cmFrdLinkDao;
	}

}
