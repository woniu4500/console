package com.jiuyv.hhc.console.message.service;

import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.message.MsgMailVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IMailSendService.
 *

 * @author 
 * @since 2014-2-26 10:40:10
 * @version 1.0.0
 */
public interface IMailService {

	/**
	 * 
	 * @param filters
	 * @param page
	 * @param user
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMailVo> findMail(List<Filter> filters, Page page, SysUserVo user) throws BaseException;
	
	/**
	 * 发送邮件
	 * @return
	 * @throws BaseException
	 */
	ExtData<MsgMailVo> doSendMail(SysUserVo user, MsgMailVo mailVo) throws BaseException ;
	
	/**
	 * 插入待发送信息
	 * @return
	 * @throws BaseException
	 */
	MsgMailVo doInsertToSendMail(MsgMailVo mailVo) throws BaseException ; 
	
}
