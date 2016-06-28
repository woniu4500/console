package com.jiuyv.hhc.console.security.action;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.security.service.AuthorityService;
import com.jiuyv.hhc.console.security.service.IWebSecCodeService;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.sms.model.WebSecCodeVo;

public class SignUpAction extends DefaultPageSupportAction{
	
	private IWebSecCodeService webSecCodeService;
	
	/** The I sys param service sys param service. */
	private ISysParamService sysParamService ;
	
	private AuthorityService authorityService ;
	
//	/**
//	 * 绑定(姓名，营运证号，证件类型，证件号码，推广员工代码)
//	 * @return
//	 * @throws IOException
//	 */
//	public String signUp() throws IOException{
//		String mobile = getParameter("mobile");
//		String name = getParameter("name");
//		String lic = getParameter("lic");
//		String cardtype = getParameter("cardtype");
//		String cardno = getParameter("cardno");
//		String recomcode = getParameter("recomcode");
//		
//		ExtData<WebMemberInfoVo> data ;
//		try{
//			data = authorityService.doSignup(mobile, name, lic, cardtype, cardno, recomcode);
//		}catch(Exception e){
//			data = ExtDataUtil.genWithExceptions(e.getMessage());
//		}
//		return resultData(data);
//		
//	}

	/**
	 * 获取手机验证码操作，注册用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doSendRegVaild() throws Exception {
		String mobile = getParameter("mobile");
		if(StringUtils.isBlank(mobile)||mobile.length() != 11){
			return resultData(ExtDataUtil.genWithExceptions("手机号为空或不是11位"));
		}
		WebSecCodeVo vo=new WebSecCodeVo();
		vo.setMobileNo(mobile);
		vo.setOprType("00");
		ExtData<WebSecCodeVo> data ;
		try{
			data = webSecCodeService.doSendVaild(vo);
		}catch(Exception e){
			data = ExtDataUtil.genWithExceptions(e.getMessage());
		}
		return resultData(data);
	}
	
	/**
	 * 找回密码时，获取短信验证码
	 * @return
	 * @throws Exception
	 */
	public String doSendFindPwdVaild() throws Exception {
		String mobile = getParameter("mobile");
		if(StringUtils.isBlank(mobile)||mobile.length() != 11){
			return resultData(ExtDataUtil.genWithExceptions("手机号为空或不是11位"));
		}
		WebSecCodeVo vo=new WebSecCodeVo();
		vo.setMobileNo(mobile);
		vo.setOprType("02");
		ExtData data = webSecCodeService.doSendVaild(vo);
		return resultData(ExtDataUtil.genWithSingleData(data));
	}
	
	
	/**
	 * 找回密码时，获取短信验证码
	 * @return
	 * @throws Exception
	 */
	public String doFindVaild() throws Exception {
		String mobile = getParameter("mobile");
		String password = getParameter("password");
		String vaildCode = getParameter("vaildCode");
		if(StringUtils.isBlank(mobile)||mobile.length() != 11){
			return resultData(ExtDataUtil.genWithExceptions("手机号为空或不是11位"));
		}
		if(StringUtils.isBlank(password)){
			return resultData(ExtDataUtil.genWithExceptions("密码不能为空"));
		}
		if(StringUtils.isBlank(vaildCode)){
			return resultData(ExtDataUtil.genWithExceptions("验证码不能为空"));
		}
		ExtData data = authorityService.doFindVaild(mobile, password, vaildCode);
		return resultData(ExtDataUtil.genWithSingleData(data));
	}
	
	
	/**
	 * 用户注册
	 * @return
	 * @throws Exception
	 */
	public String doRegister() throws Exception {
		String mobile = getParameter("mobile");
		String password = getParameter("password");
		String vaildCode = getParameter("vaildCode");
		if(StringUtils.isBlank(mobile)||mobile.length() != 11){
			return resultData(ExtDataUtil.genWithExceptions("手机号为空或不是11位"));
		}
		if(StringUtils.isBlank(password)){
			return resultData(ExtDataUtil.genWithExceptions("密码不能为空"));
		}
		if(StringUtils.isBlank(vaildCode)){
			return resultData(ExtDataUtil.genWithExceptions("验证码不能为空"));
		}
		ExtData data = authorityService.doRegister(mobile, password, vaildCode);
		return resultData(ExtDataUtil.genWithSingleData(data));
	}
	
	/**
	 * 修改密码时
	 * @return
	 * @throws Exception
	 */
	public String doSendPwdVaild() throws Exception {
		String mobile = getParameter("mobile");
		String repassword = getParameter("repassword");
		String oldpassword = getParameter("oldpassword");	
		/*
		 * 校验参数
		 * 
		 */
		ExtData data = authorityService.doChangePwdMember(mobile, repassword, oldpassword);
		return resultData(ExtDataUtil.genWithSingleData(data));
	}
	
	
	/**
	 * 校验验证码，生成默认登录密码（手机后6位）
	 *
	 * 
	 */
	public String verifyCode() throws Exception {
		String mobile = getParameter("mobile");
		String validCode = getParameter("validCode");
		ExtData data;
		try{
			data = authorityService.verifyCode(mobile, validCode);
		}catch(Exception e){
			data = ExtDataUtil.genWithExceptions(e.getMessage());
		}
		return resultData(data);
	}
	
	/**
	 * 验证用户名，密码判断是否成功登录
	 * 
	 */
	public String applogin() throws Exception{
		String mobile = getParameter("mobile");
		String password = getParameter("password");
		ExtData data;
		try{
			data = authorityService.checkLogin(mobile, password);
		}catch(Exception e){
			data = ExtDataUtil.genWithExceptions(e.getMessage());
		}
		return resultData(data);
	}
	
	
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * 设置 the I sys param service sys param service.
	 *
	 * @param sysParamService the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	
	public void setWebSecCodeService(IWebSecCodeService webSecCodeService) {
		this.webSecCodeService = webSecCodeService;
	}
}
