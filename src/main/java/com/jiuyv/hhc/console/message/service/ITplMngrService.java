package com.jiuyv.hhc.console.message.service;

import java.util.List;
import java.util.Map;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.message.MsgTplParamVo;
import com.jiuyv.hhc.model.message.MsgTplVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface ITplMngrService.
 * 模板管理 Service

 * @author 
 * @since 2014-2-21 11:37:23
 * @version 1.0.0
 */
public interface ITplMngrService {

	/**
	 * 分页查询模板
	 * @param filters
	 * @param pageInfo
	 * @param userInfo
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgTplVo> findTplPage(List<Filter> filters, Page pageInfo, SysUserVo userInfo) throws BaseException;

	/**
	 * 加载模板详情
	 * @param filters
	 * @param userInfo
	 * @param tplId
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgTplVo> findTplDetail(List<Filter> filters, SysUserVo userInfo,
			String tplId) throws BaseException;

	/**
	 * 查询模板参数信息
	 * @param filters
	 * @param userInfo
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgTplParamVo> findTplParams(List<Filter> filters, SysUserVo userInfo) throws BaseException;

	/**
	 * 插入模板
	 * @param filters
	 * @param userInfo
	 * @param tplVo
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgTplVo> doInsertTpl(List<Filter> filters, SysUserVo userInfo,
			MsgTplVo tplVo) throws BaseException;

	/**
	 * 更新模板
	 * @param filters
	 * @param userInfo
	 * @param tplVo
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgTplVo> doUpdateTpl(List<Filter> filters, SysUserVo userInfo,
			MsgTplVo tplVo) throws BaseException;

	/**
	 * 获取测试参数
	 * @param filters
	 * @param userInfo
	 * @return
	 * @throws BaseException
	 */
	Map<String, Object> findTestTplParams(List<Filter> filters, SysUserVo userInfo) throws BaseException;
	
	

}
