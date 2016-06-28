package com.jiuyv.hhc.console.security.service.impl;

import static com.jiuyv.common.validate.BizCheck.notBlank;
import static com.jiuyv.common.validate.BizCheck.notEmpty;
import static com.jiuyv.common.validate.BizCheck.notNull;
import static com.jiuyv.common.validate.BizCheck.notSame;
import static com.jiuyv.common.validate.BizCheck.same;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.encode.DES;
import com.jiuyv.hhc.console.security.service.IUserService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.security.PassWordVo;
import com.jiuyv.hhc.model.security.SysUserRoleVo;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.dao.SysUserDao;
import com.jiuyv.hhc.model.security.dao.SysUserRoleDao;


/**
 * 用户管理.
 *

 * @author 
 * @since 2014-1-3 14:07:38
 * @version 1.0.0
 */
public class UserServiceImpl extends AssistService implements IUserService {

	/** Constant String LOGIN_ID: String :. */
	private static final String LOGIN_ID = "loginId";
	
	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** Constant String STATUS_DEL: String :. */
	private static final String STATUS_DEL = "1";
	
	/** 默认密码:1111 */
	private static final String DEF_PASSWD = "1111";
	
	/** The dao. */
	private SysUserDao sysUserDao;
	
	/** The Sys user role dao sys user role dao. */
	private SysUserRoleDao sysUserRoleDao ; 
	

	
	/**
	 * 新增用户.
	 *
	 * @param userVo the user vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doInsertUser(SysUserVo userVo, List<SysUserRoleVo> list,
			List<Filter> filters, SysUserVo oprUser) throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG );
		notEmpty(list, ErrorCode.CODE_1024, ErrorCode.CODE_1024_MSG );
		notBlank(userVo.getLoginId(), ErrorCode.CODE_1024, ErrorCode.CODE_1024_MSG_1);

		List<Filter> fltLoginNm =new ArrayList<Filter>();
		fltLoginNm.add(new Filter(LOGIN_ID,Filter.STRING, userVo.getLoginId(), Filter.Comparison.EQ));
		
		List<SysUserVo> lstLoginNm = getQueryAssist().list(SysUserDao.MAPPED_FIND, fltLoginNm); 
		if ( lstLoginNm != null && lstLoginNm.size() > 0 ) {
			throw new BaseException(ErrorCode.CODE_1023, ErrorCode.CODE_1023_MSG_1);
		}
		// 将用户的密码加密
		userVo.setLoginPasswd(DES.encrypt(userVo.getLoginPasswd()));
		userVo.setStatus(STATUS_NORMAL);
		userVo.setLastUptAcc(oprUser.getLoginId());
		userVo.setLastUptOrg(oprUser.getOrgCode());
		SysUserVo dbuser;
		try {
			dbuser = getQueryAssist().insertFetch(sysUserDao, userVo);
		} catch (DuplicateKeyException e) {
			throw new BaseException(ErrorCode.CODE_1023, ErrorCode.CODE_1023_MSG, e);
		}
		Long userId = dbuser.getUserId();
		sysUserRoleDao.deleteByUser(userId);
		for (SysUserRoleVo u : list) {
			u.setUserId(userId);
		}
		DBLogUtil.addLogInfo(dbuser,list);
		getQueryAssist().insertList(sysUserRoleDao, list);
		return ExtDataUtil.genWithSingleData( dbuser);
	}

	/**
	 * 删除用户.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doDeleteUser(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser)
			throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		Long userId = userVo.getUserId();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		same(dbuser.getStatus(), STATUS_NORMAL, ErrorCode.CODE_1022, ErrorCode.CODE_1022_MSG);
		dbuser.setVersion(userVo.getVersion());
		dbuser.setStatus(STATUS_DEL);
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		DBLogUtil.addLogInfo(dbuser);
		return ExtDataUtil.genWithSingleData( getQueryAssist().updateFetch(sysUserDao, dbuser) );
	}

	/**
	 * 解冻用户.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doRecoverUser(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser)
			throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		Long userId = userVo.getUserId();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		same(dbuser.getStatus(), STATUS_DEL, ErrorCode.CODE_1022, ErrorCode.CODE_1022_MSG_1);
		dbuser.setVersion(userVo.getVersion());
		dbuser.setStatus(STATUS_NORMAL);
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		DBLogUtil.addLogInfo(dbuser);
		return ExtDataUtil.genWithSingleData( getQueryAssist().updateFetch(sysUserDao, dbuser));
	}

	/**
	 * 修改用户.
	 *
	 * @param userVo the user vo
	 * @param list the list
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doUpdateUser(SysUserVo userVo, List<SysUserRoleVo> list,
			List<Filter> filters, SysUserVo oprUser) throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		notEmpty(list, ErrorCode.CODE_1024, ErrorCode.CODE_1024_MSG);
		Long userId = userVo.getUserId();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		same(dbuser.getStatus(), STATUS_NORMAL, ErrorCode.CODE_1022, ErrorCode.CODE_1022_MSG);
		
		dbuser.setCrdNo(     userVo.getCrdNo()    );
		dbuser.setCrdType(   userVo.getCrdType()  );
		dbuser.setOrgCode(   userVo.getOrgCode()  );
		dbuser.setUserAddr(  userVo.getUserAddr() );
		dbuser.setUserEmail( userVo.getUserEmail());
		dbuser.setUserName(  userVo.getUserName()   );
		dbuser.setUserPhone( userVo.getUserPhone());
		dbuser.setVersion(   userVo.getVersion()  );
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		
		SysUserVo puser = getQueryAssist().updateFetch(sysUserDao, dbuser); 
		for ( SysUserRoleVo ur: list ) {
			ur.setUserId(userId);
		}
		sysUserRoleDao.deleteByUser(userId);
		getQueryAssist().insertList(sysUserRoleDao, list);
		DBLogUtil.addLogInfo(puser,list);
		return ExtDataUtil.genWithSingleData(puser);
	}

	
	/**
	 * 更新个人信息
	 * @param validateBean
	 * @param filters
	 * @param oprUser
	 * @return
	 */
	public ExtData<SysUserVo> doUpdateSelfInfo(SysUserVo validateBean, List<Filter> filters, SysUserVo oprUser) throws Exception{
		notNull(validateBean, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		Long userId = oprUser.getUserId();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		same(dbuser.getStatus(), STATUS_NORMAL, ErrorCode.CODE_1022, ErrorCode.CODE_1022_MSG);
		
		dbuser.setCrdNo(     validateBean.getCrdNo()    );
		dbuser.setCrdType(   validateBean.getCrdType()  );
		dbuser.setOrgCode(   validateBean.getOrgCode()  );
		dbuser.setUserAddr(  validateBean.getUserAddr() );
		dbuser.setUserEmail( validateBean.getUserEmail());
		dbuser.setUserName(  validateBean.getUserName()   );
		dbuser.setUserPhone( validateBean.getUserPhone());
		dbuser.setVersion(   validateBean.getVersion()  );
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		SysUserVo puser = getQueryAssist().updateFetch(sysUserDao, dbuser); 
		DBLogUtil.addLogInfo(puser);
		return ExtDataUtil.genWithSingleData(puser);
	}
	
	/**
	 * 修改用户密码.
	 *
	 * @param passWordVo the pass word vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doUpdateUserPasswd(PassWordVo passWordVo,
			List<Filter> filters, SysUserVo oprUser) throws Exception {
		notNull(passWordVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		notSame(passWordVo.getOldPasswd(), passWordVo.getNewPasswd(), ErrorCode.CODE_1025, ErrorCode.CODE_1025_MSG);
		Long userId = passWordVo.getUserID();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		same(dbuser.getStatus(), STATUS_NORMAL, ErrorCode.CODE_1022, ErrorCode.CODE_1022_MSG);
		String oPasswd = DES.encrypt(passWordVo.getOldPasswd()) ;
		same(oPasswd,dbuser.getLoginPasswd(), ErrorCode.CODE_1025,ErrorCode.CODE_1025_MSG_1);
		dbuser.setLoginPasswd(DES.encrypt(passWordVo.getNewPasswd()));
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		DBLogUtil.addLogInfo(new SysUserVo(userId));
		return ExtDataUtil.genWithSingleData( getQueryAssist().updateFetch(sysUserDao, dbuser) );
	}

	/**
	 * 重置用户密码.
	 *
	 * @param userVo the user vo
	 * @param filters the filters
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> doResetUserPasswd(SysUserVo userVo, List<Filter> filters, SysUserVo oprUser)
			throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		Long userId = userVo.getUserId();
		SysUserVo dbuser = sysUserDao.findByKey(userId);
		notNull(dbuser, ErrorCode.CODE_1021, ErrorCode.CODE_1021_MSG);
		dbuser.setVersion(userVo.getVersion());
		dbuser.setLoginPasswd(DES.encrypt(DEF_PASSWD));
		dbuser.setLastUptAcc(oprUser.getLoginId());
		dbuser.setLastUptOrg(oprUser.getOrgCode());
		DBLogUtil.addLogInfo(new SysUserVo(userId));
		return ExtDataUtil.genWithSingleData( getQueryAssist().updateFetch(sysUserDao, dbuser));

	}

	/**
	 * 查询用户.
	 * 
	 * @param filters the filters
	 * @param page the page
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public ExtData<SysUserVo> findUser(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(SysUserDao.MAPPED_FIND, filters, page);
	}
	
	public ExtData<SysUserVo> findUserInfoById(List<Filter> filters,SysUserVo userVo)
			throws Exception{
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		SysUserVo sysUserInfo =	sysUserDao.findByKey(userVo);
		return	ExtDataUtil.genWithSingleData(sysUserInfo);
	}

	/**
	 * 查询用户对应的角色信息.
	 *
	 * @param userVo the user vo
	 * @return the ext data< role vo>
	 * @throws Exception the exception
	 */
	public List<SysUserRoleVo> findRoleListByUser(SysUserVo userVo) throws Exception {
		notNull(userVo, ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		notNull(userVo.getUserId(), ErrorCode.CODE_9901, ErrorCode.CODE_9901_MSG);
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("userId", Filter.STRING, userVo.getUserId(), Filter.Comparison.EQ ));
		return getQueryAssist().list(SysUserRoleDao.MAPPED_FIND, filters);
	}

	/**
	 * 查询用户-角色信息.
	 * 
	 * @param filters
	 *            the filters
	 * @param page
	 *            the page
	 * @return the ext data< UserRoleVo>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public ExtData<SysUserRoleVo> findUserRole(List<Filter> filters, Page page)
			throws Exception {
		return getQueryAssist().page(SysUserRoleDao.MAPPED_FIND, filters, page);
	}
	
	/**
	 * 根据当前系统时间 计算推后 delayDate 天数的时间.
	 *
	 * @param delayDate 延长天数
	 * @return 14位时间 yyyyMMddHHmmss
	 */
	public static String timeDelay(int delayDate){
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		Calendar calendar = Calendar.getInstance();
		//设置当前日期, 日期加1
		calendar.setTime(new Date());   
		calendar.add(Calendar.DATE, delayDate);
        Date date = calendar.getTime();
        return dateFormat.format(date);
	}

	/**
	 * 设置 the dao.
	 *
	 * @param sysUserDao the new dao
	 */
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	/**
	 * Sets the sys user role dao.
	 *
	 * @param sysUserRoleDao the new sys user role dao
	 */
	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

	
	
}
