package com.jiuyv.hhc.console.information.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.information.CmNaviVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IcmArtCatService.
 * 

 * @author
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface ICmNaviService {

	/**
	 * 新增.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doInsert(CmNaviVo vo, SysUserVo userVo)
			throws Exception;

	/**
	 * 修改.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doUpdate(CmNaviVo CmNaviVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 删除.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doDelete(CmNaviVo CmNaviVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 提交.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doCommit(CmNaviVo CmNaviVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 驳回.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doReject(CmNaviVo CmNaviVo, SysUserVo userVo)
			throws Exception;

	/**
	 * 发布.
	 * 
	 * @param CmNaviVo
	 *            the cmArtCat vo
	 * @param filters
	 *            the filters
	 * @return the ext data< cmArtCat vo>
	 * @throws Exception
	 *             the exception
	 */
	ExtData<CmNaviVo> doRelease(CmNaviVo vo, SysUserVo userVo)
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
	ExtData<CmNaviVo> find(List<Filter> filters, Page page) throws Exception;

}
