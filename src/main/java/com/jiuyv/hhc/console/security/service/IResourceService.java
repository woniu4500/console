package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.hhc.model.security.SysResourceVo;

/**
 * The Interface IResourceService.
 *

 * @author 
 * @since 2014-1-2 16:36:09
 * @version 1.0.0
 */
public interface IResourceService {

	/**
	 * Find resource list.
	 *
	 * @param filters the filters
	 * @return the ext data
	 * @throws Exception the exception
	 */
	ExtData<SysResourceVo> findResourceList(List<Filter> filters) throws Exception;
}
