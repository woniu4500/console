package com.jiuyv.hhc.console.security.service.impl;

import java.util.List;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.validate.BizCheck;
import com.jiuyv.hhc.console.security.service.IOprlogService;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.security.SysOprlogVo;
import com.jiuyv.hhc.model.security.dao.SysOprlogDao;

/**
 * The Class OrgServiceImpl.
 *

 * @author 
 * @since 2014-1-3 15:19:56
 * @version 1.0.0
 */
public class OprlogServiceImpl extends AssistService implements IOprlogService {
	
	/** 日志Dao */
	private SysOprlogDao sysOprlogDao ; 
	
	/**
	 * 查询日志信息（不含操作结果信息）
	 * @param filters
	 * @param page
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.security.service.IOprlogService#findOprlogList(java.util.List, com.jiuyv.common.database.Page)
	 */
	public ExtData<SysOprlogVo> findOprlogList(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(SysOprlogDao.MAPPED_FIND_BRIEF, filters, page);
	}
	
	/**
	 * 查询日志明细信息
	 * @param oprNo
	 * @return
	 * @throws Exception
	 */
	public ExtData<SysOprlogVo> findOprlogDetail(Long oprNo) throws Exception {
		BizCheck.notNull(oprNo, ErrorCode.CODE_1031, ErrorCode.CODE_1031_MSG);
		SysOprlogVo logVo = sysOprlogDao.findByKey(oprNo);
		BizCheck.notNull(logVo, ErrorCode.CODE_1032, ErrorCode.CODE_1032_MSG);
		return ExtDataUtil.genWithSingleData(logVo);
	}
	
	/**
	 * 设置 日志Dao.
	 *
	 * @param sysOprlogDao the sysOprlogDao to set
	 */
	public void setSysOprlogDao(SysOprlogDao sysOprlogDao) {
		this.sysOprlogDao = sysOprlogDao;
	}
}
