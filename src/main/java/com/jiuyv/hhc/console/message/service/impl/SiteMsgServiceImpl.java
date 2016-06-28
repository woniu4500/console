/*
 * 
 */
package com.jiuyv.hhc.console.message.service.impl;
import static com.jiuyv.hhc.model.ErrorCode.*;
import static com.jiuyv.hhc.model.message.MsgDict.*;
import static com.jiuyv.common.validate.BizCheck.isTrue;
import static com.jiuyv.common.validate.BizCheck.notBlank;
import static com.jiuyv.common.validate.BizCheck.notNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.message.service.ISiteMsgService;
import com.jiuyv.hhc.model.common.dao.ISysTimeDao;
import com.jiuyv.hhc.model.message.MsgMessageVo;
import com.jiuyv.hhc.model.message.MsgRecvVo;
import com.jiuyv.hhc.model.message.dao.MsgMessageDao;
import com.jiuyv.hhc.model.message.dao.MsgRecvDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class SiteMsgServiceImpl.
 * 站内信Service

 * @author 
 * @since 2014-2-17 11:40:05
 * @version 1.0.0
 */
public class SiteMsgServiceImpl extends AssistService implements ISiteMsgService {

	/** 消息ID字段 */
	private static final String FLD_MSG_ID = "msgId";
	
	/** 消息状态 :. */
	private static final String FLD_READ_FLAG = "readFlag";
	/** 消息显示标识  */
	private static final String FLD_SHOW_FLAG = "showFlag";
	/** 发送时间. */
	private static final String FLD_SEND_TIME = "sendTime";
	/** 过期时间. */
	private static final String FLD_EXPIRE_TIME = "expireTime";
	
	/** 默认过期日期  */
	private static final Integer EXPIRE_DAYS = 7;
	/** 默认时间格式化模式 */
	private static final String DATETIME_FMT = "yyyyMMddHHmmss";
	
	/** 消息Dao */
	private MsgMessageDao msgMessageDao;
	/** 发送人Dao */
	private MsgRecvDao msgRecvDao;
	/** 系统时间Dao */
	private ISysTimeDao sysTimeDao;
	
	/**
	 * 查询消息页(不含内容).
	 *
	 * @param userFilters the user filters
	 * @param page the page
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> findMsgPage(List<Filter> filters,
			Page page, SysUserVo userInfo) throws BaseException {
		return getQueryAssist().page(MsgMessageDao.MAPPED_FIND_NC, filters, page);
	}

	
	/**
	 * 查询页面显示消息tip
	 * @param filters
	 * @param excelPageInfo
	 * @param userInfo
	 * @return
	 */
	public ExtData<MsgMessageVo> findUserTipMsg(List<Filter> userfilters, Page pageInfo,
			SysUserVo userInfo) throws BaseException {
		List<Filter> filters = userfilters == null ? new ArrayList<Filter>():new ArrayList<Filter>(userfilters);
		filters.add(new Filter(FLD_READ_FLAG, Filter.STRING, ReadFlag.UNREAD, Filter.Comparison.EQ));
		filters.add(new Filter(FLD_SHOW_FLAG, Filter.STRING, ShowFlag.TIP, Filter.Comparison.EQ));
		filters.add(new Filter(FLD_EXPIRE_TIME, Filter.STRING, new SimpleDateFormat(DATETIME_FMT).format(new Date()), Filter.Comparison.GE));
		pageInfo.setSort(FLD_SEND_TIME);
		List<MsgMessageVo> list = getQueryAssist().list(MsgMessageDao.MAPPED_FIND_NC, filters, pageInfo);
		ExtData<MsgMessageVo> data = ExtDataUtil.genWithData(list);
		List<Filter> countfilters = userfilters == null ? new ArrayList<Filter>():new ArrayList<Filter>(userfilters);
		countfilters.add(new Filter(FLD_READ_FLAG, Filter.STRING, ReadFlag.UNREAD, Filter.Comparison.EQ));
		data.setTotalProperty(getQueryAssist().count(MsgMessageDao.MAPPED_FIND_NC, countfilters));
		return data;
	}
	
	/**
	 * 查询消息页(不含内容).
	 *
	 * @param userFilters the user filters
	 * @param page the page
	 * @param userInfo the user info
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> findSendMsgPage(List<Filter> filters,
			Page page, SysUserVo userInfo) throws BaseException {
		return getQueryAssist().page(MsgMessageDao.MAPPED_FIND_NCS, filters, page);
	}

	/**
	 * 查询信息（包含内容）.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param msgId the msg id
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> findMsgDetail(List<Filter> userFilters,
			SysUserVo userInfo, String msgId) throws BaseException {
		MsgMessageVo vo = msgMessageDao.findByKey(NumberUtils.toLong(msgId));
		return ExtDataUtil.genWithSingleData(vo);
	}

	/**
	 * 查询信息的接收信息.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param msgId the msg id
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgRecvVo> findMsgRecvList(List<Filter> userFilters,
			SysUserVo userInfo, String msgId) throws BaseException {
		List<Filter> filters = userFilters == null ? new ArrayList<Filter>() : userFilters;
		filters.add(new Filter(FLD_MSG_ID, Filter.STRING, msgId, Filter.Comparison.EQ));
		List<MsgRecvVo> list =  getQueryAssist().list(MsgRecvDao.MAPPED_FIND, filters);
		return ExtDataUtil.genWithData(list);
	}

	/**
	 * 保存信息模板.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param msgBean the msg bean
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> doSaveMsgDraft(List<Filter> userFilters,
			SysUserVo userInfo, MsgMessageVo msgBean) throws BaseException {
		Long msgId = msgBean.getMsgId();
		// not null field check 
		notBlank(msgBean.getMsgTitle(), CODE_4003, CODE_4003_MSG);
		notBlank(msgBean.getMsgContent(), CODE_4003, CODE_4003_MSG_1);
		// set default info
		msgBean.setSendAcct(userInfo.getLoginId());
		msgBean.setSendId(userInfo.getUserId());
		msgBean.setShowFlag(StringUtils.defaultIfEmpty(msgBean.getShowFlag(), ShowFlag.LIST));
		msgBean.setExpireTime(StringUtils.defaultIfEmpty(msgBean.getExpireTime(), new SimpleDateFormat(DATETIME_FMT).format(DateUtils.addDays(new Date(), EXPIRE_DAYS))));
		if ( msgId == null || msgId <= 0) {
			// insert new draft
			msgBean.setStatus(MsgStatus.DRAFT);
			return ExtDataUtil.genWithSingleData(getQueryAssist().insertFetch(msgMessageDao, msgBean));
		} else {
			// check no null
			MsgMessageVo dbVo = msgMessageDao.findByKey(msgId);
			notNull(dbVo, CODE_4002, CODE_4002_MSG);
			// check status
			String status = dbVo.getStatus();
			isTrue(StringUtils.equals(status, MsgStatus.DRAFT), CODE_4002, CODE_4002_MSG);
			// copy field 
			dbVo.setMsgTitle(msgBean.getMsgTitle());
			dbVo.setMsgContent(msgBean.getMsgContent());
			dbVo.setVersion(msgBean.getVersion());
			dbVo.setSendAcct(msgBean.getSendAcct());
			dbVo.setSendId(msgBean.getSendId());
			dbVo.setShowFlag(msgBean.getShowFlag());
			dbVo.setExpireTime(msgBean.getExpireTime());
			// update msg
			return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(msgMessageDao, dbVo));
		}
	}

	/**
	 * 发送信息.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param validateBean the validate bean
	 * @param recvList the recv list
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> doSendMsg(List<Filter> userFilters,
			SysUserVo userInfo, MsgMessageVo msgBean,
			List<MsgRecvVo> recvList) throws BaseException {
		Long msgId = msgBean.getMsgId();
		// not null field check 
		notBlank(msgBean.getMsgTitle(), CODE_4003, CODE_4003_MSG);
		notBlank(msgBean.getMsgContent(), CODE_4003, CODE_4003_MSG_1);
		isTrue(recvList!=null&&recvList.size()>0, CODE_4003, CODE_4003_MSG_2);
		// set default info
		msgBean.setSendAcct(userInfo.getLoginId());
		msgBean.setSendId(userInfo.getUserId());
		msgBean.setShowFlag(StringUtils.defaultIfEmpty(msgBean.getShowFlag(), ShowFlag.LIST));
		msgBean.setExpireTime(StringUtils.defaultIfEmpty(msgBean.getExpireTime()
				, new SimpleDateFormat(DATETIME_FMT).format(DateUtils.addDays(new Date(), EXPIRE_DAYS))));
		msgBean.setSendTime(sysTimeDao.findDatabaseTime().getDataBaseTime());
		if ( msgId == null || msgId <= 0) {
			// insert new message and set to send
			msgBean.setStatus(MsgStatus.SENDED);
			MsgMessageVo dbVo = getQueryAssist().insertFetch(msgMessageDao, msgBean);
			msgId = dbVo.getMsgId();
			for (MsgRecvVo msgRecvVo : recvList) {
				msgRecvVo.setMsgId(msgId);
			}
			getQueryAssist().insertBatch(msgRecvDao, recvList);
			return ExtDataUtil.genWithSingleData(dbVo);
		} else {
			// check no null
			MsgMessageVo dbVo = msgMessageDao.findByKey(msgId);
			notNull(dbVo, CODE_4002, CODE_4002_MSG);
			// check status
			String status = dbVo.getStatus();
			isTrue(StringUtils.equals(status, MsgStatus.DRAFT), CODE_4002, CODE_4002_MSG);
			// copy field 
			dbVo.setMsgTitle(msgBean.getMsgTitle());
			dbVo.setMsgContent(msgBean.getMsgContent());
			dbVo.setVersion(msgBean.getVersion());
			dbVo.setSendAcct(msgBean.getSendAcct());
			dbVo.setSendId(msgBean.getSendId());
			dbVo.setShowFlag(msgBean.getShowFlag());
			dbVo.setExpireTime(msgBean.getExpireTime());
			dbVo.setSendTime(msgBean.getSendTime());
			dbVo.setStatus(MsgStatus.SENDED);
			// update and send msg
			msgRecvDao.deleteByMsgId(msgId);
			getQueryAssist().insertBatch(msgRecvDao, recvList);
			return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(msgMessageDao, dbVo));
		}
	}

	/**
	 * 删除信息草稿.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param validateBean the validate bean
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> doDeleteMsgDraft(List<Filter> userFilters,
			SysUserVo userInfo, MsgMessageVo msgBean) throws BaseException {
		Long msgId = msgBean.getMsgId();
		notNull(msgId, CODE_4003, CODE_4003_MSG_3);
		MsgMessageVo dbVo = msgMessageDao.findByKey(msgId);
		notNull(dbVo, CODE_4001, CODE_4001_MSG);
		isTrue(StringUtils.equals(dbVo.getStatus(), MsgStatus.DRAFT), CODE_4002, CODE_4002_MSG);
		dbVo.setVersion(msgBean.getVersion());
		dbVo.setStatus(MsgStatus.DELETE);
		return ExtDataUtil.genWithSingleData(getQueryAssist().updateFetch(msgMessageDao, dbVo));
	}

	/**
	 * 读取信息.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param msgId the msg id
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> doReadMsg(List<Filter> userFilters,
			SysUserVo userInfo, String msgId) throws BaseException {
		isTrue(NumberUtils.isNumber(msgId) , CODE_4003, CODE_4003_MSG_3);
		Long msgIdL = NumberUtils.toLong(msgId);
		MsgRecvVo dbRecvVo = msgRecvDao.findByKey(new MsgRecvVo(msgIdL, userInfo.getUserId()));
		notNull(dbRecvVo, CODE_4001, CODE_4001_MSG);
		if ( StringUtils.equals(dbRecvVo.getReadFlag(), ReadFlag.UNREAD)) {
			// mark readed 
			dbRecvVo.setReadFlag(ReadFlag.READED);
			dbRecvVo = getQueryAssist().updateFetch(msgRecvDao,dbRecvVo);
		}
		isTrue(!StringUtils.equals(dbRecvVo.getReadFlag(), ReadFlag.DELETE), CODE_4004, CODE_4004_MSG);
		// return messageVo
		MsgMessageVo dbVo = msgMessageDao.findByKey(msgIdL);
		notNull(dbVo, CODE_4001, CODE_4001_MSG);
		return ExtDataUtil.genWithSingleData(copyRecvInfo(dbVo, dbRecvVo));
	}

	/**
	 * 标记删除邮件.
	 *
	 * @param userFilters the user filters
	 * @param userInfo the user info
	 * @param msgIds the msg ids
	 * @return the ext data
	 * @throws BaseException the base exception
	 */
	public ExtData<MsgMessageVo> doRecycleMsg(List<Filter> userFilters,
			SysUserVo userInfo, String msgId) throws BaseException {
		isTrue(NumberUtils.isNumber(msgId) , CODE_4003, CODE_4003_MSG_3);
		Long msgIdL = NumberUtils.toLong(msgId);
		MsgRecvVo dbRecvVo = msgRecvDao.findByKey(new MsgRecvVo(msgIdL, userInfo.getUserId()));
		notNull(dbRecvVo, CODE_4001, CODE_4001_MSG);
		isTrue(!StringUtils.equals(dbRecvVo.getReadFlag(), ReadFlag.DELETE), CODE_4004, CODE_4004_MSG);
		dbRecvVo.setReadFlag(ReadFlag.DELETE);
		dbRecvVo = getQueryAssist().updateFetch(msgRecvDao,dbRecvVo);
		MsgMessageVo dbVo = msgMessageDao.findByKey(msgIdL);
		notNull(dbVo, CODE_4001, CODE_4001_MSG);
		return ExtDataUtil.genWithSingleData(copyRecvInfo(dbVo, dbRecvVo));
	}

	/**
	 * 复制单个收信人信息到消息对象
	 * @param msgVo
	 * @param recvVo
	 * @return
	 */
	private MsgMessageVo copyRecvInfo( MsgMessageVo msgVo, MsgRecvVo recvVo) {
		if ( msgVo == null || recvVo == null ) {
			return msgVo;
		}
		msgVo.setRecvId(recvVo.getRecvId());
		msgVo.setReadFlag(recvVo.getReadFlag());
		msgVo.setReadFlagDesc(recvVo.getReadFlagDesc());
		return msgVo;
	}
	
	/**
	 * 设置 消息Dao.
	 *
	 * @param msgMessageDao the new 消息Dao
	 */
	public void setMsgMessageDao(MsgMessageDao msgMessageDao) {
		this.msgMessageDao = msgMessageDao;
	}

	/**
	 * 设置 发送人Dao.
	 *
	 * @param msgRecvDao the new 发送人Dao
	 */
	public void setMsgRecvDao(MsgRecvDao msgRecvDao) {
		this.msgRecvDao = msgRecvDao;
	}

	/**
	 * 设置 系统时间Dao.
	 *
	 * @param sysTimeDao the new 系统时间Dao
	 */
	public void setSysTimeDao(ISysTimeDao sysTimeDao) {
		this.sysTimeDao = sysTimeDao;
	}
}
