package com.jiuyv.hhc.console.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jiuyv.common.SystemErrorCode;
import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.encode.DES;
import com.jiuyv.common.encode.Md5;
import com.jiuyv.hhc.console.loan.service.IBizLoanDataService;
import com.jiuyv.hhc.console.security.service.AuthorityService;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.customer.dao.WebAgentRcmdDao;
import com.jiuyv.hhc.model.customer.dao.WebMemberInfoDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMchntPropDao;
import com.jiuyv.hhc.model.mchnt.dao.BizMerchantDao;
import com.jiuyv.hhc.model.security.dao.WebSecCodeDao;
import com.jiuyv.hhc.sms.model.SMSData;
import com.jiuyv.hhc.sms.model.WebSecCodeVo;
import com.jiuyv.hhc.sms.util.SecCodeUtil;

/**
 * <h1>The Class AuthoritySeviceImpl.</h1>
 * <p>
 * Descriptions:
 * </p>
 *
 * @company Shanghai JiuYu Software Systems CO.,LTD.
 * @author
 * @since 2014
 * @version 1.0.0
 */
public class AuthorityServiceImpl extends AssistService implements
		AuthorityService {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorityServiceImpl.class);

	/** 用户名默认长度 */
	private static int USER_NAME_MAX_LENGTH = 100;
	final static String SUCCESS = "操作成功";
	final static String SIGN_SUCCESS = "注册成功";
	final static String FAILED = "操作失败";
	/** The Web member info dao web member info dao. */
	private WebMemberInfoDao webMemberInfoDao;

	private WebSecCodeDao webSecCodeDao;

	private WebAgentRcmdDao webAgentRcmdDao;

	private BizMerchantDao bizMerchantDao;

	// private ISysTimeDao iSysTimeDao;

	private BizMchntPropDao bizMchntPropDao;
	

	/**
	 * 校验验证码，生成默认的用户登录密码
	 * 
	 * @throws Exception
	 * 
	 */
	public ExtData verifyCode(String mobile, String vaildCode) throws Exception {
		WebSecCodeVo vo = new WebSecCodeVo();
		vo.setMobileNo(mobile);
		vo.setOprType("00");
		vo.setSecCode(vaildCode);
		vo.setSendState("1");
		// TODO
		SMSData smsData = SecCodeUtil.doCheck(webSecCodeDao, vo);
		boolean res = smsData.isSuccess();
		if (res) {
			String password = mobile.substring(5, 11);
			System.out.println("password----" + password);
			WebMemberInfoVo webMemberInfoVo = new WebMemberInfoVo();
			webMemberInfoVo.setMobile(mobile);
			webMemberInfoVo.setPassword(Md5.encodeLoanPassMD5(password));
			System.out.println("MD5前:" + password);
			System.out.println("MD5后:" + Md5.encodeLoanPassMD5(password));
			webMemberInfoVo.setCustomerType("0");
			webMemberInfoVo.setUserState("0");
			webMemberInfoVo.setLastUptAcc("sys");
			webMemberInfoVo.setLastUptOrg("-1");
			WebMemberInfoVo urvo;
			try {
				urvo = getQueryAssist().insertFetch(webMemberInfoDao,
						webMemberInfoVo);
				return ExtDataUtil.genWithSingleData(SIGN_SUCCESS);
			} catch (DuplicateKeyException e) {
				LOGGER.error(e.getMessage(), e);
				throw new BaseException("", FAILED
						+ SystemErrorCode.CODE_2003_MSG);
			}
		} else {
			throw new BaseException("", "验证码错误");
		}
	}

	/**
	 * 校验用户名，密码，判断是否可以成功登录
	 * 
	 * @throws Exception
	 * 
	 */
	public ExtData checkLogin(String mobile, String password) throws Exception {
		WebMemberInfoVo memberVo = new WebMemberInfoVo();

		memberVo.setMobile(mobile);
//		memberVo.setPassword(DES.encrypt(password));
		memberVo.setPassword(password);
		WebMemberInfoVo dbvo1 = webMemberInfoDao.findByMobile(mobile);
		if(dbvo1 == null){
			throw new BaseException("", SystemErrorCode.CODE_2007_MSG);
		}
		
		WebMemberInfoVo dbvo = webMemberInfoDao.findByMobileandPwd(memberVo);
		if (dbvo == null) {
			throw new BaseException("", SystemErrorCode.CODE_2005_MSG);
		}
//		dbvo.setPassword(DES.encrypt(password));
		dbvo.setPassword(password);
		int i = webMemberInfoDao.updateByKey(dbvo);
		if (i != 1) {
			throw new BaseException("", SystemErrorCode.CODE_2006_MSG);
		}
		return ExtDataUtil.genWithSingleData(dbvo);
	}

	/**
	 * 用户登陆.
	 * 
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	// public UserDetails loadUserByUsername(String userName)
	// throws UsernameNotFoundException {
	// if ( StringUtils.isBlank(userName)) {
	// throw new BadCredentialsException("用户名不能为空");
	// }
	// if (userName.getBytes().length > USER_NAME_MAX_LENGTH ) {
	// throw new UsernameNotFoundException("用户名过长");
	// }
	// List<WebUser> users = webMemberInfoDao.findUserInfo(userName);
	// if ( users.size() > 1 ) {
	// LOGGER.debug("More than one user found with name {}", userName);
	// throw new
	// IncorrectResultSizeDataAccessException("More than one user found with name '"
	// + userName + "'", 1);
	// }
	// if ( users.size() == 0 ) {
	// LOGGER.debug("No user found with name {}", userName);
	// throw new UserNotFoundException("No user found with name '" + userName +
	// "'");
	// }
	// // 得到用户可以访问的资源列表，供安全的http url过滤使用
	// WebUser user = users.get(0);
	// if(user.getPasswdErr() >= 6){
	// LOGGER.debug("尝试次数过多请进行找回密码流程");
	// throw new PasswordLockedException("尝试次数过多请进行找回密码流程");
	// }
	// // 如果自行设置加密算法（除了spring security的md5 ，md4等），则需在这里要将user的密码域解密，供spring
	// user.setPassword(DES.decrypt(user.getPassword()));
	// //user.setPassword(user.getPassword());
	// return user;
	// }

	@Override
	public ExtData doSignup(String mobile, String name, String lic,
			String cardtype, String cardno, String recomcode) throws Exception {
		WebMemberInfoVo dbvo = webMemberInfoDao.findByMobile(mobile);
		if (dbvo != null) {
			if(StringUtils.isNotBlank(mobile)){
				dbvo.setMobile(mobile);
			}
			if(StringUtils.isNotBlank(name)){
				dbvo.setRealName(name);
			}
			if(StringUtils.isNotBlank(cardno)){
				dbvo.setIdNumber(cardno);
			}
			if(StringUtils.isNotBlank(cardtype)){
				dbvo.setIdType(cardtype);
			}
			if(StringUtils.isNotBlank(recomcode)){
				dbvo.setRcmdCode(recomcode);
			}
			if(StringUtils.isNotBlank(lic)){
				dbvo.setLic(lic);
			}
			webMemberInfoDao.updateByKey(dbvo);			
			return ExtDataUtil.genWithSingleData(dbvo);
		} else {
			return ExtDataUtil.genWithSingleData("此手机号未注册，请注册");
		}

	}

	/**
	 * 用户网站注册.
	 *
	 * @param mobile
	 *            the mobile
	 * @param vaildCode
	 *            the vaildate code
	 * @param agentCode
	 *            the agent code
	 * @param merName
	 *            the mer name
	 * @param address
	 *            the address
	 * @param uid
	 *            the uid
	 * @param lat
	 *            the lat
	 * @param lng
	 *            the lng
	 * @param tag
	 *            the tag
	 */
	// public ExtData doSignup(String mobile, String password, String vaildCode,
	// String rcmdCode, String mchntCd, String licNo, String artifCertifId)
	// throws Exception {
	// WebSecCodeVo vo = new WebSecCodeVo();
	// vo.setMobileNo(mobile);
	// vo.setOprType("00");
	// vo.setSecCode(vaildCode);
	// vo.setSendState("1");
	// // TODO
	// boolean res = SecCodeUtil.doCheck(webSecCodeDao, vo).isSuccess();
	// // boolean res = true;
	// if (res) {
	// WebMemberInfoVo webUserInfoVo = new WebMemberInfoVo();
	// webUserInfoVo.setMobile(mobile);
	// webUserInfoVo.setPassword(DES.encrypt(password));
	// LOGGER.debug("DES前:" + password);
	// LOGGER.debug("DES后:" + DES.encrypt(password));
	// webUserInfoVo.setCustomerType("0");
	// webUserInfoVo.setUserState("0");
	// webUserInfoVo.setLastUptAcc("sys");
	// webUserInfoVo.setLastUptOrg("-1");
	// if (!StringUtils.isBlank(rcmdCode)) {
	// WebAgentRcmdVo rcmdVo = webAgentRcmdDao.findByKey(rcmdCode);
	// if (rcmdVo != null) {
	// webUserInfoVo.setFatherCustomerCode(rcmdVo
	// .getCustomerCode());
	// }
	// }
	// webUserInfoVo.setRcmdCode(rcmdCode);
	// WebMemberInfoVo urvo;
	// try {
	// urvo = getQueryAssist().insertFetch(webMemberInfoDao,
	// webUserInfoVo);
	// } catch (DuplicateKeyException e) {
	// LOGGER.error(e.getMessage(), e);
	// throw new BaseException("", FAILED
	// + SystemErrorCode.CODE_2003_MSG);
	// }
	// BizMerchantVo qvo = new BizMerchantVo();
	// qvo.setMchntCd(mchntCd);
	// qvo.setCustomerCode(urvo.getCustomerCode());
	// Long noramlCount = bizMerchantDao
	// .countNormalByMchntCdandCustmerCode(qvo);
	// if (noramlCount == 0) {
	// BizMerchantVo merVo = new BizMerchantVo();
	// merVo.setArtifCertifId(artifCertifId);
	// merVo.setCustomerCode(urvo.getCustomerCode());
	// merVo.setDispMchntSt("01");
	// merVo.setRecAcc("APP");
	// merVo.setLastUptAcc(urvo.getCustomerCode().toString());
	// merVo.setLicNo(licNo);
	// merVo.setMchntCd(mchntCd);
	// merVo.setMchntSt("01");
	// try {
	// BizMerchantVo dbvo = getQueryAssist().insertFetch(
	// bizMerchantDao, merVo);
	// BizMchntPropVo merPropVo = new BizMchntPropVo();
	// merPropVo.setMchntCode(dbvo.getMchntCode());
	// bizMchntPropDao.insert(merPropVo);
	// } catch (Exception e) {
	// LOGGER.error(e.getMessage(), e);
	// throw new BaseException("", "商户绑定失败");
	// }
	// Long autedCount = bizMerchantDao.countAutedByMchntCd(mchntCd);
	// if (autedCount == 0) {
	// return ExtDataUtil.genWithInfo(SIGN_SUCCESS);
	// } else {
	// return ExtDataUtil.genWithInfo(SIGN_SUCCESS + "但此商户已被认证");
	// }
	// } else {
	// throw new BaseException("", "您已经绑定过此商户");
	// }
	// } else {
	// throw new BaseException("", "验证码错误");
	// }
	// }

	/**
	 * 修改密码
	 * 
	 * @param memberVo
	 * @param password
	 * @param opassword
	 * @param vaildCode
	 * @return
	 * @throws Exception
	 */
	public ExtData doChangePwdMember(String mobile, String password,
			String opassword) throws Exception {
		WebMemberInfoVo memberVo = new WebMemberInfoVo();
		memberVo.setMobile(mobile);
//		memberVo.setPassword(DES.encrypt(opassword));
		memberVo.setPassword(opassword);
		WebMemberInfoVo dbvo = webMemberInfoDao.findByMobileandPwd(memberVo);
		if (dbvo == null) {
			throw new BaseException("", SystemErrorCode.CODE_2005_MSG);
		}
		dbvo.setPassword(password);
		int i = webMemberInfoDao.updateByKey(dbvo);
		if (i != 1) {
			throw new BaseException("", SystemErrorCode.CODE_2006_MSG);
		}
		return ExtDataUtil.genWithSingleData(SUCCESS);
	}

	/**
	 * 设置新密码(找回密码)
	 * 
	 * @param memberVo
	 * @param password
	 * @param opassword
	 * @param vaildCode
	 * @return
	 * @throws Exception
	 */
	public ExtData doFindVaild(String mobile, String password, String vaildCode)
			throws Exception {
		WebSecCodeVo vo = new WebSecCodeVo();
		vo.setMobileNo(mobile);
		vo.setOprType("00");
		vo.setSecCode(vaildCode);
		vo.setSendState("1");
		// TODO
		boolean res = SecCodeUtil.doCheck(webSecCodeDao, vo).isSuccess();
		if (res) {
			WebMemberInfoVo dbvo = webMemberInfoDao.findByMobile(mobile);
			if (dbvo == null) {
				throw new BaseException("", SystemErrorCode.CODE_2005_MSG);
			}
			dbvo.setPassword(password);
//			dbvo.setPassword(DES.encrypt(password));
			int i = webMemberInfoDao.updateByKey(dbvo);
			if (i != 1) {
				throw new BaseException("", SystemErrorCode.CODE_2006_MSG);
			}
		} else {
			throw new BaseException("", SystemErrorCode.CODE_2004_MSG);
		}
		return ExtDataUtil.genWithSingleData(SUCCESS);
	}
	
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
	public ExtData doRegister(String mobile, String password, String vaildCode)
			throws Exception {
		WebSecCodeVo vo = new WebSecCodeVo();
		vo.setMobileNo(mobile);
		vo.setOprType("00");
		vo.setSecCode(vaildCode);
		vo.setSendState("1");
		// TODO
		boolean res = SecCodeUtil.doCheck(webSecCodeDao, vo).isSuccess();
		if (res) {
			WebMemberInfoVo dbvo = webMemberInfoDao.findByMobile(mobile);
			if (dbvo != null) {
				throw new BaseException("", SystemErrorCode.CODE_2003_MSG);
			}else{
				WebMemberInfoVo dbo = new WebMemberInfoVo();
				dbo.setMobile(mobile);
				dbo.setPassword(password);
				dbo.setCustomerType("0");
				dbo.setUserState("0");
				dbo.setLastUptAcc("sys");
				dbo.setLastUptOrg("-1");
				webMemberInfoDao.insert(dbo);
			}
		} else {
			throw new BaseException("", SystemErrorCode.CODE_2004_MSG);
		}
		return ExtDataUtil.genWithSingleData(SUCCESS);
	}
	

	/**
	 * 获取用户信息
	 * 
	 * @param userSeq
	 * @return
	 * @throws Exception
	 */

	public WebMemberInfoVo findUserByUserSeq(Long userSeq) throws Exception {
		return webMemberInfoDao.findByKey(userSeq);
	}

	/**
	 * 获取用户信息并设置密码修改标识
	 * 
	 * @param userSeq
	 * @return
	 * @throws Exception
	 */
	// public WebMemberInfoVo findUserByUserSeqWithPwdFlag(Long userSeq) throws
	// Exception {
	// WebMemberInfoVo qvo = webMemberInfoDao.findByKey(userSeq);
	// if(qvo == null){
	// return null;
	// }
	// if(!StringUtils.isBlank(qvo.getExpireTime())){
	// String sysTime=iSysTimeDao.findDatabaseTime().getDataBaseTime();
	// DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	// Calendar calendar = Calendar.getInstance();
	// try {
	// calendar.setTime(dateFormat.parse(qvo.getExpireTime()));
	// } catch (ParseException e) {
	// LOGGER.error("", e);
	// }
	// if (calendar.getTime().before(dateFormat.parse(sysTime))) {
	// qvo.setPwdFlag(true);
	// }else{
	// qvo.setPwdFlag(false);
	// }
	// }else{
	// qvo.setPwdFlag(false);
	// }
	// return qvo;
	// }

	// public ExtData doUpdateUser (WebMemberInfoVo vo) throws BaseException{
	// WebMemberInfoVo qvo = webMemberInfoDao.findByKey(vo.getCustomerCode());
	// if(qvo == null){
	// throw new BaseException("","未找到此用户");
	// }
	// WebMemberInfoVo dbvo = new WebMemberInfoVo();
	// dbvo.setCustomerCode(vo.getCustomerCode());
	// dbvo.setRealName(vo.getRealName());
	// dbvo.setPhone(vo.getPhone());
	// dbvo.setAddress(vo.getAddress());
	// dbvo.setZip(vo.getZip());
	// dbvo.setIdNumber(vo.getIdNumber());
	// dbvo.setSexState(vo.getSexState());
	// dbvo.setMarriedState(vo.getMarriedState());
	// dbvo.setLocalState(vo.getLocalState());
	// dbvo.setUserState(vo.getUserState());
	// int i = 0;
	// try{
	// i = webMemberInfoDao.updateByKey(dbvo);
	// }catch(Exception e){
	// throw new BaseException("","更新会员信息失败",e);
	// }
	// if( i != 1){
	// throw new BaseException("","更新会员信息失败");
	// }
	// return
	// ExtDataUtil.genWithSingleData(webMemberInfoDao.findByKey(vo.getCustomerCode()));
	// }

	/**
	 * 获取用户信息
	 * 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public WebMemberInfoVo findUserByMobile(String mobile) throws Exception {
		return webMemberInfoDao.findByMobile(mobile);
	}

	public WebSecCodeDao getWebSecCodeDao() {
		return webSecCodeDao;
	}

	public void setWebSecCodeDao(WebSecCodeDao webSecCodeDao) {
		this.webSecCodeDao = webSecCodeDao;
	}

	public WebMemberInfoDao getWebMemberInfoDao() {
		return webMemberInfoDao;
	}

	/**
	 * @param webUserInfoDao
	 *            the webUserInfoDao to set
	 */
	public void setWebMemberInfoDao(WebMemberInfoDao webMemberInfoDao) {
		this.webMemberInfoDao = webMemberInfoDao;
	}

	public void setWebAgentRcmdDao(WebAgentRcmdDao webAgentRcmdDao) {
		this.webAgentRcmdDao = webAgentRcmdDao;
	}

	public void setBizMerchantDao(BizMerchantDao bizMerchantDao) {
		this.bizMerchantDao = bizMerchantDao;
	}

	// public void setiSysTimeDao(ISysTimeDao iSysTimeDao) {
	// this.iSysTimeDao = iSysTimeDao;
	// }

	public void setBizMchntPropDao(BizMchntPropDao bizMchntPropDao) {
		this.bizMchntPropDao = bizMchntPropDao;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
