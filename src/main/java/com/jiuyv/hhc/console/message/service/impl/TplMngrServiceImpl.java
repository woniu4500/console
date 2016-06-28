package com.jiuyv.hhc.console.message.service.impl;

import static com.jiuyv.common.validate.BizCheck.notNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import static com.jiuyv.hhc.model.ErrorCode.*;
import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.message.service.ITplMngrService;
import com.jiuyv.hhc.model.message.MsgTplParamVo;
import com.jiuyv.hhc.model.message.MsgTplVo;
import com.jiuyv.hhc.model.message.dao.MsgTplDao;
import com.jiuyv.hhc.model.message.dao.MsgTplParamDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class TplMngrServiceImpl.
 * 模板管理Service

 * @author 
 * @since 2014-2-21 14:37:45
 * @version 1.0.0
 */
public class TplMngrServiceImpl extends AssistService implements ITplMngrService{

	/** 模板 Dao */
	private MsgTplDao msgTplDao ;
	
	/**
	 * 分页查询模板.
	 *
	 * @param filters the filters
	 * @param pageInfo the page info
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgTplVo> findTplPage(List<Filter> filters, Page page, SysUserVo userInfo) throws BaseException {
		return getQueryAssist().page(MsgTplDao.MAPPED_FIND_NC, filters, page);
	}

	/**
	 * 加载模板详情.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @param tplId the tpl id
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgTplVo> findTplDetail(List<Filter> filters, SysUserVo userInfo,
			String tplId) throws BaseException {
		Long tplIdL = NumberUtils.toLong(tplId);
		MsgTplVo dbVo = msgTplDao.findByKey(tplIdL);
		notNull(dbVo, CODE_4011, CODE_4011_MSG);
		return ExtDataUtil.genWithSingleData(dbVo);
	}

	/**
	 * 查询模板参数信息.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgTplParamVo> findTplParams(List<Filter> filters, SysUserVo userInfo) throws BaseException {
		List<MsgTplParamVo> reslist = getQueryAssist().list(MsgTplParamDao.MAPPED_FIND, filters);
		return ExtDataUtil.genWithData(reslist);
	}

	/**
	 * 获取测试参数
	 * @param filters
	 * @param userInfo
	 * @return
	 * @throws BaseException
	 */
	public Map<String, Object> findTestTplParams(List<Filter> filters, SysUserVo userInfo) throws BaseException {
		List<MsgTplParamVo> reslist = getQueryAssist().list(MsgTplParamDao.MAPPED_FIND, filters);
		Map<String, Object> params = new HashMap<String, Object>();
		for ( MsgTplParamVo tp : reslist ) {
			params.put(tp.getParamCode(), tp.getParamDemo());
		}
		return params;
	}
	
	
	/**
	 * 插入模板.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @param tplVo the tpl vo
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgTplVo> doInsertTpl(List<Filter> filters, SysUserVo userInfo,
			MsgTplVo tplVo) throws BaseException{
		notNull(tplVo,CODE_4013,CODE_4013_MSG);
		tplVo.setRecCrtAcc(userInfo.getLoginId());
		tplVo.setLastUptAcc(userInfo.getLoginId());
		tplVo.setLastUptTime(userInfo.getLoginId());
		return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(msgTplDao, tplVo));
	}

	/**
	 * 更新模板.
	 *
	 * @param filters the filters
	 * @param userInfo the user info
	 * @param tplVo the tpl vo
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgTplVo> doUpdateTpl(List<Filter> filters, SysUserVo userInfo,
			MsgTplVo tplVo) throws BaseException {
		notNull(tplVo, CODE_4013, CODE_4013_MSG);
		Long tplId = tplVo.getTplId();
		notNull(tplId, CODE_4013, CODE_4013_MSG);
		MsgTplVo dbVo = msgTplDao.findByKey(tplId);
		notNull(dbVo, CODE_4011, CODE_4011_MSG);
		dbVo.setVersion(tplVo.getVersion());
		dbVo.setTplName(tplVo.getTplName());
		dbVo.setTplContent(tplVo.getTplContent());
		dbVo.setTplRemark(tplVo.getTplRemark());
		dbVo.setLastUptAcc(userInfo.getLoginId());
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(msgTplDao, dbVo));
	}

	/**
	 * 设置 模板 Dao.
	 *
	 * @param msgTplDao the new 模板 Dao
	 */
	public void setMsgTplDao(MsgTplDao msgTplDao) {
		this.msgTplDao = msgTplDao;
	}
	
}
