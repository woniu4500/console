package com.jiuyv.hhc.console.information.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.information.CmArticleVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IcmArtCatService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface ICmArticleService {
	
	/**
	 * 新增.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doInsert(CmArticleVo vo,SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doUpdate(CmArticleVo CmArticleVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 删除.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doDelete(CmArticleVo CmArticleVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 提交.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doCommit(CmArticleVo CmArticleVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 驳回.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doReject(CmArticleVo CmArticleVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 发布.
	 *
	 * @param CmArticleVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> doRelease(CmArticleVo CmArticleVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArticleVo> find(List<Filter> filters, Page page) throws Exception;

	/**
	 * 查询单条记录
	 * @param artSeq
	 * @return
	 * @throws Exception
	 */
	ExtData<CmArticleVo> findDetail(Long artSeq) throws Exception;
	
}
