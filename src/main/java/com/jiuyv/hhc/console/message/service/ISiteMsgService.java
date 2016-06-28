package com.jiuyv.hhc.console.message.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.message.MsgMessageVo;
import com.jiuyv.hhc.model.message.MsgRecvVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface ISiteMsgService. 站内信Service
 * 

 * @author cowyk
 * @since 2014-2-16 15:01:44
 * @version 1.0.0
 */
public interface ISiteMsgService {

	/**
	 * 查询消息页(不含内容)
	 * 
	 * @param userFilters
	 * @param page
	 * @param userInfo
	 * @return
	 */
	ExtData<MsgMessageVo> findMsgPage(List<Filter> userFilters, Page page,
			SysUserVo userInfo) throws BaseException;

	/**
	 * 查询消息页(不含内容)　发送方信息
	 * 
	 * @param userFilters
	 * @param page
	 * @param userInfo
	 * @return
	 */
	ExtData<MsgMessageVo> findSendMsgPage(List<Filter> userFilters, Page page,
			SysUserVo userInfo) throws BaseException;

	/**
	 * 查询信息（包含内容）
	 * @param userFilters
	 * @param userInfo
	 * @param msgId
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMessageVo> findMsgDetail(List<Filter> userFilters,
			SysUserVo userInfo, String msgId) throws BaseException;

	/**
	 * 查询信息的接收信息
	 * @param userFilters
	 * @param userInfo
	 * @param msgId
	 * @return
	 */
	ExtData<MsgRecvVo> findMsgRecvList(List<Filter> userFilters, SysUserVo userInfo,
			String msgId) throws BaseException;

	/**
	 * 保存信息模板
	 * @param userFilters
	 * @param userInfo
	 * @param msgBean
	 * @return
	 */
	ExtData<MsgMessageVo> doSaveMsgDraft(List<Filter> userFilters, SysUserVo userInfo,
			MsgMessageVo msgBean) throws BaseException;

	/**
	 * 发送信息
	 * @param userFilters
	 * @param userInfo
	 * @param validateBean
	 * @param recvList 
	 * @return
	 */
	ExtData<MsgMessageVo> doSendMsg(List<Filter> userFilters, SysUserVo userInfo,
			MsgMessageVo validateBean, List<MsgRecvVo> recvList) throws BaseException;

	/**
	 * 删除信息草稿
	 * @param userFilters
	 * @param userInfo
	 * @param validateBean
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMessageVo> doDeleteMsgDraft(List<Filter> userFilters, SysUserVo userInfo,
			MsgMessageVo validateBean) throws BaseException;

	/**
	 * 读取信息
	 * @param userFilters
	 * @param userInfo
	 * @param parameter
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMessageVo> doReadMsg(List<Filter> userFilters, SysUserVo userInfo,
			String msgId) throws BaseException;

	/**
	 * 标记删除邮件
	 * @param userFilters
	 * @param userInfo
	 * @param parameter
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMessageVo> doRecycleMsg(List<Filter> userFilters, SysUserVo userInfo,
			String msgIds) throws BaseException;

	/**
	 * 查询页面显示消息tip
	 * @param filters
	 * @param excelPageInfo
	 * @param userInfo
	 * @return
	 */
	ExtData<MsgMessageVo> findUserTipMsg(List<Filter> filters, Page pageInfo,
			SysUserVo userInfo) throws BaseException;


}
