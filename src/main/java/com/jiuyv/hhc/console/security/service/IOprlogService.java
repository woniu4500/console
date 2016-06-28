package com.jiuyv.hhc.console.security.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.security.SysOprlogVo;

public interface IOprlogService {

	/**
	 * 查询日志
	 * 
	 * @param filters
	 * @param page
	 * @return
	 * @throws Exception
	 */
	ExtData<SysOprlogVo> findOprlogList(List<Filter> filters, Page page)
			throws Exception;

	/**
	 * 查询日志明细信息
	 * @param oprNo
	 * @return
	 * @throws Exception
	 */
	ExtData<SysOprlogVo> findOprlogDetail(Long oprNo) throws Exception;
}
