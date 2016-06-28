package com.jiuyv.hhc.console.security.service.impl;

import java.util.List;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.security.service.IResourceService;
import com.jiuyv.hhc.model.security.SysResourceVo;
import com.jiuyv.hhc.model.security.dao.SysResourceDao;

/**
 * The Class ResourceServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:45:52
 * @version 1.0.0
 */
public class ResourceServiceImpl extends AssistService implements IResourceService{

	/** The Sys resource dao resource dao. */
	private SysResourceDao resourceDao;

	/**
	 * @param filters
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.security.service.IResourceService#findResourceList(java.util.List)
	 */
	public ExtData<SysResourceVo> findResourceList(List<Filter> filters)
			throws Exception {
		List<SysResourceVo> result = getQueryAssist().list(SysResourceDao.MAPPED_FIND, filters);
		return ExtDataUtil.genWithData(result);
	}

	/**
	 * Gets the resource dao.
	 *
	 * @return the resource dao
	 */
	public SysResourceDao getResourceDao() {
		return resourceDao;
	}

	/**
	 * Sets the resource dao.
	 *
	 * @param resourceDao the new resource dao
	 */
	public void setResourceDao(SysResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

}
