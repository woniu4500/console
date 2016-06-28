package com.jiuyv.hhc.console.common.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.hhc.model.common.SysCodeVo;

public interface ISecService {

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
	ExtData<SysCodeVo> findSecCodeList(List<Filter> filters) throws Exception;

	ExtData<SysCodeVo> findSecCodeList(List<Filter> filters, Page page) throws Exception;

}
