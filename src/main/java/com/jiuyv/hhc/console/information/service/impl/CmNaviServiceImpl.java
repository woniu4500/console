package com.jiuyv.hhc.console.information.service.impl;
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
import com.jiuyv.hhc.console.information.service.ICmNaviService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.information.CmNaviVo;
import com.jiuyv.hhc.model.information.dao.CmNaviDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Class CmArticleServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class CmNaviServiceImpl extends AssistService implements ICmNaviService {
	
	/** The Cm article dao cm article dao. */
	private CmNaviDao cmNaviDao;
	
	/**
	 * @param vo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doInsert(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doInsert(CmNaviVo CmNaviVo, SysUserVo userVo)
			throws Exception {
		notNull(CmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		CmNaviVo.setRecCrtAcc(userVo.getOrgCode());
		CmNaviVo.setLastUptAcc(userVo.getLoginId());
		CmNaviVo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(CmNaviVo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(cmNaviDao, CmNaviVo));
	}

	
	/**
	 * @param cmNaviVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doUpdate(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doUpdate(CmNaviVo cmNaviVo,
			SysUserVo userVo) throws Exception {
		notNull(cmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long naviSeq = cmNaviVo.getNaviSeq();
		CmNaviVo pvo = cmNaviDao.findByKey(naviSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		notSame(pvo.getNaviStatus(),NaviStatus.DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_3);
		notSame(pvo.getNaviStatus(),NaviStatus.SUBMIT,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_4);
		// set object info
		pvo.setIsBlankTarget( cmNaviVo.getIsBlankTarget  ());
		pvo.setIsVisible    ( cmNaviVo.getIsVisible      ());
		pvo.setNaviLogo(cmNaviVo.getNaviLogo());
		pvo.setNaviBanner(cmNaviVo.getNaviBanner());
		pvo.setNaviTitle(cmNaviVo.getNaviTitle());
		pvo.setIsHret       ( cmNaviVo.getIsHret         ());
		pvo.setNaviName     ( cmNaviVo.getNaviName       ());
		pvo.setNaviStyle    ( cmNaviVo.getNaviStyle      ());
		pvo.setNaviPosition ( cmNaviVo.getNaviPosition   ());
		pvo.setNaviOrderList( cmNaviVo.getNaviOrderList  ());
		pvo.setNaviUrl      ( cmNaviVo.getNaviUrl        ());
		pvo.setNaviContent(cmNaviVo.getNaviContent());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmNaviVo.getVersion());
		// 04,01 to 01  , 00 to 00
		if(!pvo.getNaviStatus().equals(NaviStatus.FIRST_DRAFT)){
			pvo.setNaviStatus(NaviStatus.DRAFT);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmNaviDao, pvo));
	}

	
	/**
	 * @param cmNaviVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doDelete(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doDelete(CmNaviVo cmNaviVo,
			SysUserVo userVo) throws Exception {
		notNull(cmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long naviSeq = cmNaviVo.getNaviSeq();
		CmNaviVo pvo = cmNaviDao.findByKey(naviSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		
		pvo.setNaviStatus(NaviStatus.DELETE);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmNaviVo.getVersion());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmNaviDao, pvo));
	}

	
	/**
	 * @param cmNaviVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doCommit(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doCommit(CmNaviVo cmNaviVo,
			SysUserVo userVo) throws Exception {
		notNull(cmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long naviSeq = cmNaviVo.getNaviSeq();
		CmNaviVo pvo = cmNaviDao.findByKey(naviSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		if(!pvo.getNaviStatus().equals(NaviStatus.FIRST_DRAFT)
				&&!pvo.getNaviStatus().equals(NaviStatus.DRAFT)
				&&!pvo.getNaviStatus().equals(NaviStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_1);
		}
		pvo.setNaviStatus(NaviStatus.SUBMIT);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmNaviVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmNaviDao, pvo));
	}

	
	/**
	 * @param cmNaviVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doReject(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doReject(CmNaviVo cmNaviVo,
			SysUserVo userVo) throws Exception {
		notNull(cmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long naviSeq = cmNaviVo.getNaviSeq();
		CmNaviVo pvo = cmNaviDao.findByKey(naviSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
		same(pvo.getNaviStatus(),NaviStatus.SUBMIT,ErrorCode.CODE_3005,ErrorCode.CODE_3005_MSG_3);
		pvo.setNaviStatus(NaviStatus.REJECT);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmNaviVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmNaviDao, pvo));
	}

	
	/**
	 * @param cmNaviVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doRelease(com.jiuyv.hhc.model.information.CmNaviVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmNaviVo> doRelease(CmNaviVo cmNaviVo,
			SysUserVo userVo) throws Exception {
		// precheck
		notNull(cmNaviVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long naviSeq = cmNaviVo.getNaviSeq();
		CmNaviVo pvo = cmNaviDao.findByKey(naviSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
		same(pvo.getNaviStatus(),NaviStatus.SUBMIT, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_2);
		// setvalue
		pvo.setNaviStatus(NaviStatus.ISSUE);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmNaviVo.getVersion());
		
		CmNaviVo dvo = getQueryAssist().updateFetch(cmNaviDao, pvo);
		// save to version table
		int verCount = cmNaviDao.countVerByKey(naviSeq);
		if ( verCount == 0  ){
			cmNaviDao.insertVer(dvo);
		} else {
			cmNaviDao.updateVerByKey(dvo);
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
	public ExtData<CmNaviVo> find(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(CmNaviDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * @param cmNaviDao the cmNaviDao to set
	 */
	public void setCmNaviDao(CmNaviDao cmNaviDao) {
		this.cmNaviDao = cmNaviDao;
	}

}
