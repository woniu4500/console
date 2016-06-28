package com.jiuyv.hhc.console.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;

/**
 * <h1>The Interface AuthorityService.</h1>
 * <p>Descriptions: 网站用户的授权模型 </p>
 *

 * @author
 * @since 2014
 * @version 1.0.0
 */
public interface AuthorityService extends UserDetailsService{
	
	/**
	 * 校验验证码，生成默认的用户登录密码
	 * 
	 */
	public ExtData verifyCode(String mobile,String vaildCode)throws Exception;
	
	/**
	 * 校验用户名，密码，判断是否可以成功登录
	 * @throws Exception 
	 * 
	 */
	public ExtData checkLogin(String mobile, String password) throws Exception;
	
	/**
	 * 用户信息完善.
	 *
	 * @param mobile the mobile
	 * @param agentCode the agent code
	 * @return 
	 */
	public ExtData doSignup(String mobile,String name, String lic,String cardtype,String cardno,String recomcode) throws Exception;

	
	
//	/**
//	 * 网站用户注册.
//	 *
//	 * @param mobile the mobile
//	 * @param agentCode the agent code
//	 * @return 
//	 */
//	ExtData doSignup(String mobile,String password, String vaildCode, String rcmdCode, String mchntCd,String licNo,String artifCertifId) throws Exception;
//
	
	/**
	 * 用户注册
	 * 
	 * @param memberVo
	 * @param password
	 * @param opassword
	 * @param vaildCode
	 * @return
	 * @throws Exception
	 */
	public ExtData doRegister(String mobile, String password, String vaildCode) throws Exception;
	
	
	/**
	 * 修改密码
	 * @param memberVo
	 * @param password
	 * @param opassword
	 * @param vaildCode
	 * @return
	 * @throws Exception
	 */
	public ExtData doChangePwdMember(String mobile, String password, String opassword) throws Exception;
	
	
//	
//	/**
//	 * 获取用户信息
//	 * 
//	 * @param userSeq
//	 * @return
//	 * @throws Exception
//	 */
//	WebMemberInfoVo findUserByUserSeq(Long userSeq)
//			throws Exception;
//	
//	/**
//	 * 获取用户信息
//	 * 
//	 * @param mobile
//	 * @return
//	 * @throws Exception
//	 */
//	WebMemberInfoVo findUserByMobile(String mobile)
//			throws Exception;
//	
//	/**
//	 * 获取用户信息并设置密码修改标识
//	 * 
//	 * @param mobile
//	 * @return
//	 * @throws Exception
//	 */
//	WebMemberInfoVo findUserByUserSeqWithPwdFlag(Long userSeq) throws Exception;
//	
//
	/**
	 * 设置新密码(找回密码)
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	ExtData doFindVaild (String mobile,String password,String vaildCode) throws Exception;
//	
//	
//	ExtData doUpdateUser (WebMemberInfoVo vo) throws BaseException;
	
}

