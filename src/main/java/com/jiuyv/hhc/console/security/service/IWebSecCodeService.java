package com.jiuyv.hhc.console.security.service;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.hhc.sms.model.WebSecCodeVo;

/**
 * The Interface IMemberService.
 *

 * @author 
 * @since 2014-1-2 16:36:06
 * @version 1.0.0
 */
public interface IWebSecCodeService {

	/**
	 * 发送.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<WebSecCodeVo> doSendRegVaild(WebSecCodeVo vo)
			throws Exception;
	

	/**
	 * 发送.
	 *
	 * @param memberVo the member vo
	 * @param filters the filters
	 * @return the ext data< member vo>
	 * @throws Exception the exception
	 */
	ExtData<WebSecCodeVo> doSendVaild(WebSecCodeVo vo)
			throws Exception;
	
	
}
