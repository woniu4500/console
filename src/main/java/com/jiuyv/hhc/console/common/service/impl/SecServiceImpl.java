package com.jiuyv.hhc.console.common.service.impl;

import java.util.List;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.common.service.ISecService;
import com.jiuyv.hhc.model.common.SysCodeVo;
import com.jiuyv.hhc.model.common.dao.ISysCodeDao;

/**
 * The Class OrgServiceImpl.
 */
public class SecServiceImpl extends AssistService implements ISecService {
	
	/**
	 * 查询系统参数
	 * 
	 * @param secCodeVo
	 *            the sec code vo
	 * 
	 * @return the list< sec code vo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public ExtData<SysCodeVo> findSecCodeList(List<Filter> filters)
			throws Exception {
		List<SysCodeVo> res = getQueryAssist().list(ISysCodeDao.MAPPED_FIND, filters);
		return ExtDataUtil.genWithData(res);
	}

	public ExtData<SysCodeVo> findSecCodeList(List<Filter> filters, Page page)
			throws Exception {
		List<SysCodeVo> res = getQueryAssist().list(ISysCodeDao.MAPPED_FIND, filters, page);
		return ExtDataUtil.genWithData(res); 
	}
}
