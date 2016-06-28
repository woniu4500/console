package com.jiuyv.hhc.console.message.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.mail.MailSenderInfo;
import com.jiuyv.common.mail.SendResult;
import com.jiuyv.common.mail.SimpleMailSender;
import com.jiuyv.hhc.console.message.service.IMailService;
import com.jiuyv.hhc.model.common.SysParamVo;
import com.jiuyv.hhc.model.common.dao.SysParamDao;
import com.jiuyv.hhc.model.message.MsgDict;
import com.jiuyv.hhc.model.message.MsgMailVo;
import com.jiuyv.hhc.model.message.dao.MsgMailDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class MailServiceImpl.
 *

 * @author 
 * @since 2014-2-26 13:42:28
 * @version 1.0.0
 */
public class MailServiceImpl extends AssistService implements IMailService{

	/** 邮件Dao */
	private MsgMailDao msgMailDao ; 
	
	private static final String DELIMIT = ";";
	
	private static final String SYSTEM_USER = "system";
	
	private static final String MAIL_HOST = "sys.email.host";
	private static final String MAIL_PORT = "sys.email.port";
	private static final String MAIL_USER = "sys.email.username";
	private static final String MAIL_PASSWD = "sys.email.password";
	private static final String MAIL_ADDR = "sys.email.address";
	
	/**
	 * 
	 * @param filters
	 * @param page
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	public ExtData<MsgMailVo> findMail(List<Filter> filters, Page page, SysUserVo user) throws BaseException {
		return getQueryAssist().page(MsgMailDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * 发送邮件
	 * @return
	 * @throws BaseException
	 */
	public ExtData<MsgMailVo> doSendMail(SysUserVo user, MsgMailVo mailVo) throws BaseException  {
		MailSenderInfo mailInfo = systemMailSenderInfo();
		mailInfo.setSubject(mailVo.getSubject());
		mailInfo.setContent(mailVo.getContent());
		if ( StringUtils.isNotBlank(mailVo.getToAddrs()) ) {
			mailInfo.setToAddress(mailVo.getToAddrs().split(DELIMIT));
		}
		if ( StringUtils.isNotBlank(mailVo.getCcAddrs()) ) {
			mailInfo.setCcAddress(mailVo.getCcAddrs().split(DELIMIT));
		}
		SendResult result = SimpleMailSender.sendHtmlMail(mailInfo );
		mailVo.setSendTime(result.getSendTime());
		mailVo.setStatus(result.isSuccess()?MsgDict.MailStatus.SENDED:MsgDict.MailStatus.FAILED);
		StringBuffer procinfo = new StringBuffer();
		for ( String error: result.getErrors()) {
			procinfo.append(error).append(DELIMIT);
		}
		mailVo.setProcInfo(procinfo.toString());
		mailVo.setRecCrtAcc(user.getLoginId());
		mailVo.setLastUptAcc(user.getLoginId());
		msgMailDao.insert(mailVo);
		ExtData<MsgMailVo> data = ExtDataUtil.genWithSingleData(mailVo);
		data.setSuccess(result.isSuccess()) ;
		data.setSyserr(mailVo.getProcInfo());
		return data;
	}

	/**
	 * 获取系统默认的邮件参数信息
	 * @return
	 */
	private MailSenderInfo systemMailSenderInfo() {
		MailSenderInfo mailInfo = new MailSenderInfo();
		Map<String, SysParamVo> params = getQueryAssist().map(SysParamDao.MAPPED_FIND, null, null, null, "paramCode");
		mailInfo.setMailServerHost(params.get(MAIL_HOST).getParamValue());
		mailInfo.setMailServerPort(params.get(MAIL_PORT).getParamValue());
		mailInfo.setUserName(params.get(MAIL_USER).getParamValue());
		mailInfo.setPassword(params.get(MAIL_PASSWD).getParamValue());
		mailInfo.setFromAddress(params.get(MAIL_ADDR).getParamValue());
		return mailInfo ; 
	}
	
	
	/**
	 * 插入待发送信息
	 * @return
	 * @throws BaseException
	 */
	public MsgMailVo doInsertToSendMail(MsgMailVo mailVo) throws BaseException {
		mailVo.setStatus(MsgDict.MailStatus.TO_SEND);
		mailVo.setRecCrtAcc(SYSTEM_USER); 
		mailVo.setLastUptAcc(SYSTEM_USER);
		msgMailDao.insert(mailVo);
		return mailVo;
	}

	public void setMsgMailDao(MsgMailDao msgMailDao) {
		this.msgMailDao = msgMailDao;
	}
}
