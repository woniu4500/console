package com.jiuyv.hhc.console.information.service.impl;

import static com.jiuyv.common.validate.BizCheck.isTrue;
import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notSame;
import static com.jiuyv.common.validate.BizCheck.same;
import static com.jiuyv.hhc.model.information.CmsDict.*;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.information.service.ICmArtCatService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.information.CmArtCatVo;
import com.jiuyv.hhc.model.information.TreeCat;
import com.jiuyv.hhc.model.information.CmsDict.ArtStatus;
import com.jiuyv.hhc.model.information.dao.CmArtCatDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class CmArtCatServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:38
 * @version 1.0.0
 */
public class CmArtCatServiceImpl extends AssistService implements ICmArtCatService {

	/** The dao. */
	private CmArtCatDao cmArtCatDao;
	
	/**
	 * 新增.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doInsertCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		try {
			cmArtCatVo.setRecCrtAcc(userVo.getOrgCode());
			cmArtCatVo.setLastUptAcc(userVo.getLoginId());
			cmArtCatVo.setLastUptOrg(userVo.getOrgCode());
			DBLogUtil.addLogInfo(cmArtCatVo);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(cmArtCatDao, cmArtCatVo));
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_3003, ErrorCode.CODE_3003_MSG, e);
		}
	}

	/**
	 * 修改.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doUpdateCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long catSeq = cmArtCatVo.getCatSeq();
		CmArtCatVo pvo = cmArtCatDao.findByKey(catSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);
		notSame(pvo.getCatSeq(),TreeUtil.ROOT_NODE, ErrorCode.CODE_3006,ErrorCode.CODE_3006_MSG);
		notSame(pvo.getCatStatus(),CatStatus.DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_1);
		notSame(pvo.getCatStatus(),CatStatus.TO_DELETE,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_1);
		notSame(pvo.getCatStatus(),CatStatus.SUBMIT,ErrorCode.CODE_3002,ErrorCode.CODE_3002_MSG_2);
		// set object info
		pvo.setCatName(cmArtCatVo.getCatName());
		pvo.setCatTitle(cmArtCatVo.getCatTitle());
		pvo.setCatCode(cmArtCatVo.getCatCode());
		pvo.setCatLogo(cmArtCatVo.getCatLogo());
		pvo.setCatBanner(cmArtCatVo.getCatBanner());
		
		pvo.setIsVisible(cmArtCatVo.getIsVisible());
		pvo.setCatParent(cmArtCatVo.getCatParent());
		pvo.setCatSort(cmArtCatVo.getCatSort());
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArtCatVo.getVersion());
		
		// 04,01 to 01  , 00 to 00
		if(!pvo.getCatStatus().equals(CatStatus.FIRST_DRAFT)){
			pvo.setCatStatus(CatStatus.DRAFT);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArtCatDao, pvo));
	}
	
	/**
	 * 提交.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doCommitCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long catSeq = cmArtCatVo.getCatSeq();
		CmArtCatVo pvo = cmArtCatDao.findByKey(catSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		notSame(pvo.getCatSeq(),TreeUtil.ROOT_NODE,ErrorCode.CODE_3006,ErrorCode.CODE_3006_MSG);
		if(!pvo.getCatStatus().equals(CatStatus.FIRST_DRAFT)
				&&!pvo.getCatStatus().equals(CatStatus.DRAFT)
				&&!pvo.getCatStatus().equals(CatStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG);
		}
		pvo.setCatStatus(CatStatus.SUBMIT);
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArtCatVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArtCatDao, pvo));
	}

	/**
	 * 驳回.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doRejectCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long catSeq = cmArtCatVo.getCatSeq();
		CmArtCatVo pvo = cmArtCatDao.findByKey(catSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		notSame(pvo.getCatSeq(),TreeUtil.ROOT_NODE,ErrorCode.CODE_3006,ErrorCode.CODE_3006_MSG);
//		same(pvo.getCatStatus(),CatStatus.SUBMIT,ErrorCode.CODE_3005,ErrorCode.CODE_3005_MSG_1);
		isTrue(pvo.getCatStatus().equals(ArtStatus.SUBMIT)||pvo.getCatStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG_3);
		
		int verCount = cmArtCatDao.countVerByKey(catSeq);
		if ( verCount == 0  ){
			// 未发布驳回后返回初稿状态
			pvo.setCatStatus(CatStatus.FIRST_DRAFT);
		} else {
			// 返回驳回状态
			pvo.setCatStatus(CatStatus.REJECT);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArtCatVo.getVersion());
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArtCatDao, pvo));
	}
	
	/**
	 * 发布.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doReleaseCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long catSeq = cmArtCatVo.getCatSeq();
		CmArtCatVo pvo = cmArtCatDao.findByKey(catSeq);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);	
		notSame(pvo.getCatSeq(),TreeUtil.ROOT_NODE,ErrorCode.CODE_3006,ErrorCode.CODE_3006_MSG);
//		same(pvo.getCatStatus(),CatStatus.SUBMIT,ErrorCode.CODE_3005,ErrorCode.CODE_3005_MSG);
		isTrue(pvo.getCatStatus().equals(ArtStatus.SUBMIT)||pvo.getCatStatus().equals(ArtStatus.TO_DELETE)
				, ErrorCode.CODE_3005, ErrorCode.CODE_3005_MSG);
		// setvalue
		if ( pvo.getCatStatus().equals(ArtStatus.SUBMIT) ) {
			pvo.setCatStatus(CatStatus.ISSUE);
		} else {
			pvo.setCatStatus(CatStatus.DELETE);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArtCatVo.getVersion());
		CmArtCatVo dvo=getQueryAssist().updateFetch(cmArtCatDao, pvo);
		int verCount = cmArtCatDao.countVerByKey(catSeq);
		if ( verCount == 0  ){
			cmArtCatDao.insertVer(dvo);
		} else {
			cmArtCatDao.updateVerByKey(dvo);
		}
		if ( pvo.getCatStatus().equals(ArtStatus.DELETE) ) {
			cmArtCatDao.deleteVer(dvo);
		}
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(dvo);
	}
	
	/**
	 * 删除.
	 *
	 * @param CmArtCatVo the CmArtCat vo
	 * @param filters the filters
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> doDeleteCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception {
		notNull(cmArtCatVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		Long catSeq = cmArtCatVo.getCatSeq();
		CmArtCatVo pvo = cmArtCatDao.findByKey(catSeq);
		// 不允许删除-1机构
		notSame(pvo.getCatSeq(),TreeUtil.ROOT_NODE,ErrorCode.CODE_3006,ErrorCode.CODE_3006_MSG);
		notNull(pvo,ErrorCode.CODE_3001,ErrorCode.CODE_3001_MSG);
		
		if(!pvo.getCatStatus().equals(ArtStatus.FIRST_DRAFT)
				&&!pvo.getCatStatus().equals(ArtStatus.DRAFT)
				&&!pvo.getCatStatus().equals(ArtStatus.ISSUE)
				&&!pvo.getCatStatus().equals(ArtStatus.REJECT)){
			throw new BaseException(ErrorCode.CODE_3004,ErrorCode.CODE_3004_MSG_3);
		}
		if ( pvo.getCatStatus().equals(ArtStatus.FIRST_DRAFT) ) {
			// 初稿状态直接删除
			pvo.setCatStatus(CatStatus.DELETE);
		} else {
			// 发布后及草稿状态需提交审核
			pvo.setCatStatus(CatStatus.TO_DELETE);
		}
		
		pvo.setLastUptAcc(userVo.getLoginId());
		pvo.setLastUptOrg(userVo.getOrgCode());
		pvo.setVersion(cmArtCatVo.getVersion());
		
		DBLogUtil.addLogInfo(pvo);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(cmArtCatDao, pvo));
	}
	
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public ExtData<CmArtCatVo> findCmArtCat(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(CmArtCatDao.MAPPED_FIND, filters, page);
	}		

	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< CmArtCat vo>
	 * @throws Exception the exception
	 */
	public List<TreeCat> findAllCat(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().list(CmArtCatDao.CAT_TREE_FIND, filters, page);
	}
	
	/**
	 * 设置 the dao.
	 *
	 * @param CmArtCatDao the new dao
	 */
	public void setCmArtCatDao(CmArtCatDao cmArtCatDao) {
		this.cmArtCatDao = cmArtCatDao;
	}

}
