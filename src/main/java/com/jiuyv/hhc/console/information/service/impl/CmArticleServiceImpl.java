package com.jiuyv.hhc.console.information.service.impl;
import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notSame;
import static com.jiuyv.common.validate.BizCheck.same;
import static com.jiuyv.common.validate.BizCheck.isTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.web.util.htmlUtil;
import com.jiuyv.hhc.console.information.service.ICmArticleService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.common.dao.ISysTimeDao;
import com.jiuyv.hhc.model.information.CmArticleVo;
import com.jiuyv.hhc.model.information.CmsDict.ArtStatus;
import com.jiuyv.hhc.model.information.dao.CmArticleDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * <h1>The Class CmArticleServiceImpl.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public class CmArticleServiceImpl extends AssistService implements ICmArticleService {
	
	/** The Cm article dao cm article dao. */
	private CmArticleDao cmArticleDao;
	private ISysTimeDao iSysTimeDao;
	/**
	 * @param vo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doInsert(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doInsert(CmArticleVo cmArticleVo, SysUserVo userVo)
			throws Exception {
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		if(StringUtils.isBlank(cmArticleVo.getArtAuthor())){
			cmArticleVo.setArtAuthor("匿名");
		}
		if(StringUtils.isBlank(cmArticleVo.getArtRemark())){
			if(StringUtils.isBlank(cmArticleVo.getArtContent())){
				cmArticleVo.setArtRemark("");
			}else if(htmlUtil.html2Text(cmArticleVo.getArtContent()).length()<=280){
				cmArticleVo.setArtRemark(htmlUtil.html2Text(cmArticleVo.getArtContent()).trim());
			}else{
				cmArticleVo.setArtRemark(htmlUtil.html2Text(cmArticleVo.getArtContent()).substring(0,280).trim()+"...");
			}	
		}
		cmArticleVo.setRecCrtAcc(userVo.getOrgCode());
		cmArticleVo.setLastUptAcc(userVo.getLoginId());
		cmArticleVo.setLastUptOrg(userVo.getOrgCode());
		DBLogUtil.addLogInfo(cmArticleVo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(cmArticleDao, cmArticleVo));
	}

	
	/**
	 * @param CmArticleVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doUpdate(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doUpdate(CmArticleVo cmArticleVo,
			SysUserVo userVo) throws Exception {
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long artSeq = cmArticleVo.getArtSeq();
		CmArticleVo pvo = cmArticleDao.findByKey(artSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		notSame(pvo.getArtStatus(),ArtStatus.DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_3);
		notSame(pvo.getArtStatus(),ArtStatus.TO_DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_3);
		notSame(pvo.getArtStatus(),ArtStatus.SUBMIT,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_4);
		// set object info
		pvo.setCatSeq      ( cmArticleVo.getCatSeq     ());
		pvo.setArtRemark   ( htmlUtil.html2Text(cmArticleVo.getArtRemark  ()).trim());
		pvo.setArtContent  ( cmArticleVo.getArtContent ());
		pvo.setArtTitle    ( cmArticleVo.getArtTitle   ());
		pvo.setArtAuthor   ( cmArticleVo.getArtAuthor  ());
		pvo.setArtPubTime  ( cmArticleVo.getArtPubTime ());
		pvo.setIsRecmd     ( cmArticleVo.getIsRecmd    ());
		pvo.setIsTops      ( cmArticleVo.getIsTops     ());
		pvo.setMetaWords   ( cmArticleVo.getMetaWords  ());
		pvo.setMetaDes     ( cmArticleVo.getMetaDes    ());
		pvo.setArtImg(cmArticleVo.getArtImg());
//		pvo.setArtHits     ( cmArticleVo.getArtHits    ());
//		pvo.setArtUrl      ( cmArticleVo.getArtUrl     ());
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArticleVo.getVersion());
		// 04,01 to 01  , 00 to 00
		if(!pvo.getArtStatus().equals(ArtStatus.FIRST_DRAFT)){
			pvo.setArtStatus(ArtStatus.DRAFT);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArticleDao, pvo));
	}

	
	/**
	 * @param CmArticleVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doDelete(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doDelete(CmArticleVo cmArticleVo,
			SysUserVo userVo) throws Exception {
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long artSeq = cmArticleVo.getArtSeq();
		CmArticleVo pvo = cmArticleDao.findByKey(artSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);
		if(!pvo.getArtStatus().equals(ArtStatus.FIRST_DRAFT)
				&&!pvo.getArtStatus().equals(ArtStatus.DRAFT)
				&&!pvo.getArtStatus().equals(ArtStatus.ISSUE)
				&&!pvo.getArtStatus().equals(ArtStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_2);
		}
		
		if ( pvo.getArtStatus().equals(ArtStatus.FIRST_DRAFT) ) {
			// 初稿状态直接删除
			pvo.setArtStatus(ArtStatus.DELETE);
		} else {
			// 发布后及草稿状态需提交审核
			pvo.setArtStatus(ArtStatus.TO_DELETE);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArticleVo.getVersion());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArticleDao, pvo));
	}

	
	/**
	 * @param CmArticleVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doCommit(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doCommit(CmArticleVo cmArticleVo,
			SysUserVo userVo) throws Exception {
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long artSeq = cmArticleVo.getArtSeq();
		CmArticleVo pvo = cmArticleDao.findByKey(artSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		if(!pvo.getArtStatus().equals(ArtStatus.FIRST_DRAFT)
				&&!pvo.getArtStatus().equals(ArtStatus.DRAFT)
				&&!pvo.getArtStatus().equals(ArtStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_1);
		}
		pvo.setArtStatus(ArtStatus.SUBMIT);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArticleVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArticleDao, pvo));
	}

	
	/**
	 * @param CmArticleVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doReject(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doReject(CmArticleVo cmArticleVo,
			SysUserVo userVo) throws Exception {
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long artSeq = cmArticleVo.getArtSeq();
		CmArticleVo pvo = cmArticleDao.findByKey(artSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
		isTrue(pvo.getArtStatus().equals(ArtStatus.SUBMIT)||pvo.getArtStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_3);
		
		int verCount = cmArticleDao.countVerByKey(artSeq);
		if ( verCount == 0  ){
			// 未发布驳回后返回初稿状态
			pvo.setArtStatus(ArtStatus.FIRST_DRAFT);
		} else {
			// 返回驳回状态
			pvo.setArtStatus(ArtStatus.REJECT);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArticleVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArticleDao, pvo));
	}

	
	/**
	 * @param CmArticleVo
	 * @param userVo
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#doRelease(com.jiuyv.hhc.model.information.CmArticleVo, com.jiuyv.hhc.model.security.SysUserVo)
	 */
	public ExtData<CmArticleVo> doRelease(CmArticleVo cmArticleVo,
			SysUserVo userVo) throws Exception {
		// precheck
		notNull(cmArticleVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long artSeq = cmArticleVo.getArtSeq();
		CmArticleVo pvo = cmArticleDao.findContentByKey(artSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG_1);	
		isTrue(pvo.getArtStatus().equals(ArtStatus.SUBMIT)||pvo.getArtStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_2);
		// setvalue
		if ( pvo.getArtStatus().equals(ArtStatus.SUBMIT) ) {
			pvo.setArtStatus(ArtStatus.ISSUE);
		} else {
			pvo.setArtStatus(ArtStatus.DELETE);
		}
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArticleVo.getVersion());
		pvo.setArtPubTime(iSysTimeDao.findDatabaseTime().getDataBaseTime());
		CmArticleVo dvo = getQueryAssist().updateFetch(cmArticleDao, pvo);
		dvo.setArtContent(pvo.getArtContent());
		// save to version table
		int verCount = cmArticleDao.countVerByKey(artSeq);
		if ( verCount == 0  ){
			cmArticleDao.insertVer(dvo);
		} else {
			cmArticleDao.updateVerByKey(dvo);
		}
		if ( pvo.getArtStatus().equals(ArtStatus.DELETE) ) {
			cmArticleDao.deleteVer(dvo);
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
	public ExtData<CmArticleVo> find(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(CmArticleDao.MAPPED_FIND, filters, page);
	}

	
	/**
	 * @param artSeq
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.information.service.ICmArticleService#findDetail(java.lang.Long)
	 */
	public ExtData<CmArticleVo> findDetail(Long artSeq) throws Exception {
		notNull(artSeq, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		return ExtDataUtil.genWithSingleData(cmArticleDao.findContentByKey(artSeq));
	}


	/**
	 * @param cmArticleDao the cmArticleDao to set
	 */
	public void setCmArticleDao(CmArticleDao cmArticleDao) {
		this.cmArticleDao = cmArticleDao;
	}


	public void setiSysTimeDao(ISysTimeDao iSysTimeDao) {
		this.iSysTimeDao = iSysTimeDao;
	}

}
