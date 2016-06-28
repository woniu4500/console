package com.jiuyv.hhc.console.information.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.information.CmFrdLinkVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IcmArtCatService.
 * 

 * @author
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface ICmFrdLinkService {

	/**
	 * 新增.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doInsert(CmFrdLinkVo vo, SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doUpdate(CmFrdLinkVo CmFrdLinkVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 删除.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doDelete(CmFrdLinkVo CmFrdLinkVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 提交.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doCommit(CmFrdLinkVo CmFrdLinkVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 驳回.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doReject(CmFrdLinkVo CmFrdLinkVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 发布.
	 * 
	 * @param CmFrdLinkVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> doRelease(CmFrdLinkVo vo, SysUserVo userVo)
			throws Exception;

	/**
	 * 查询.
	 * 
	 * @param filters
	 *            the filters
	 * @param page
	 *            the page
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmFrdLinkVo> find(List<Filter> filters, Page page) throws Exception;

}
