package com.jiuyv.hhc.console.information.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.information.CmArtCatVo;
import com.jiuyv.hhc.model.information.TreeCat;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IcmArtCatService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface ICmArtCatService {
	
	/**
	 * 新增.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doInsertCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doUpdateCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;

	/**
	 * 删除.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doDeleteCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 提交.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doCommitCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 驳回.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doRejectCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;
	
	/**
	 * 发布.
	 *
	 * @param cmArtCatVo the cmArtCat vo
	 * @param filters the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> doReleaseCmArtCat(CmArtCatVo cmArtCatVo,SysUserVo userVo)
			throws Exception;
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	ExtData<CmArtCatVo> findCmArtCat(List<Filter> filters, Page page) throws Exception;
	
	/**
	 * 查询.
	 *
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception the exception
	 */
	List<TreeCat> findAllCat(List<Filter> filters, Page page) throws Exception;
}
