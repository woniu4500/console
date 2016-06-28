package com.jiuyv.hhc.console.security.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.model.security.LogInfo;
import com.jiuyv.hhc.model.security.SysOprlogVo;
import com.jiuyv.hhc.model.security.SysResourceVo;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.jiuyv.hhc.model.security.dao.SysOprlogDao;

/**
 * 数据库日志工具.
 *

 * @author 
 * @since 2014-6-20 13:42:04
 * @version 1.0.0
 */
public final class DBLogUtil  {
	
	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DBLogUtil.class);

	/** 日志相关信息. */
	private static final ThreadLocal<LogInfo> LOG_INFO = new ThreadLocal<LogInfo>();
	
	/** Constant String BOOLEAN_TRUE_STRING: String :. */
	private static final String BOOLEAN_TRUE_STRING = "true";
	
	/** Constant Long FAKE_USER_ID: Long :. */
	private static final Long FAKE_USER_ID = -9999l;
	
	/** 需要记录日志的action请求. */
	private static Map<String, String> defaultActionMap = new HashMap<String, String>();

	/**
	 * Instantiates a new dB log util.
	 */
	private DBLogUtil() {
		
	}
	
	/**
	 * 获取当前的日志有关信息. 主要存放的有操作日志的流水号，清算日期，数据库时间14位（当前线程中）。
	 * 
	 * @return the log info
	 */
	public static LogInfo getLogInfo() {
		return LOG_INFO.get();
	}
	
	/**
	 * 增加日志记录信息.
	 * 将传入对象转换成json数组格式保存到oprResult字段
	 * 
	 * @param obj 对象数组
	 */
	public static void addLogInfo(Object... obj) {
		if ( LOG_INFO.get() == null ) {
			LOGGER.info("This operation donot record log. ");
			return ;
		}
		LogInfo log = LOG_INFO.get();
	
		try {
			if ( obj != null && obj.length > 0 ) {
				String logInfo = JSONArray.fromObject(obj).toString();
				if ( logInfo.length() > 2000 ) {
					log.setResultInfo(logInfo.substring(0, 2000));
					LOGGER.info("Loginfo is too long :" + logInfo );
				} else {
					log.setResultInfo(logInfo);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occur in set resultInfo", e);
		}
	}
	/**
	 * 存储日志有关信息.
	 * 
	 * @param logInfo
	 *            the new log info
	 */
	public static void setLogInfo(LogInfo logInfo) {
		DBLogUtil.LOG_INFO.set(logInfo);
	}

	/**
	 * 将当前线程中的对象 置空。.
	 */
	public static void clear() {
		DBLogUtil.LOG_INFO.remove();
	}



	static {
		defaultActionMap.put("success", BOOLEAN_TRUE_STRING);
		defaultActionMap.put("errAjax", BOOLEAN_TRUE_STRING);
		defaultActionMap.put("errPage", BOOLEAN_TRUE_STRING);
		defaultActionMap.put("input"  , BOOLEAN_TRUE_STRING);
	}

	/**
	 * 根据资源列表中的actionUrl 判断 是否需要记录日志.
	 * 
	 * @param resourceCache
	 *            所有资源的 cache
	 * 
	 * @return true, if need log in db
	 */
	public static boolean needLogInDB(ResourceCache resourceCache) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String actionUrl = request.getServletPath().replaceFirst("/", "");
		Map<String, SysResourceVo> resMap = resourceCache.getUrlResMap();
		SysResourceVo resource = resMap.get(actionUrl);
		// if resource is not
		if (resource == null || resource.getLogFlag().equals("0")) {
			LOGGER.debug("Not need record in database. ");
			return false;
		}
		LOGGER.debug("Need record in database. ");
		return true;
	}

	/**
	 * 判断是否为该次action chain中返回页面的请求.
	 *
	 * @param action 请求名
	 * @return true, if is forward2 page
	 */
	public static boolean isForward2Page(String action) {
		if (defaultActionMap.get(action) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 记录日志.
	 *
	 * @param oprlogDao the oprlog dao
	 * @param logVo the log vo
	 */
	public static void recordCurrentLogInDB(SysOprlogDao oprlogDao, SysOprlogVo logVo) {
		if (logVo == null) {
			LOGGER.error("OprlogVo is null. ");
			return;
		}
		try {
			oprlogDao.insert(logVo);
		} catch (Exception e) {
			LOGGER.error("Error Record Current Log", e);
		}
	}

	/**
	 * 登录失败时使用的用户.
	 *
	 * @param req the req
	 * @return the sys user vo
	 */
	public static SysUserVo generateFakeUser(HttpServletRequest req) {
		SysUserVo fakeUser = new SysUserVo();
		HttpServletRequest request = req == null ? ServletActionContext.getRequest() : req;
		HttpSession session = request.getSession();
		String logId = (String) session.getAttribute("SPRING_SECURITY_LAST_USERNAME");
		fakeUser.setLoginId(StringUtils.trimToEmpty(logId));
		fakeUser.setOrgCode("");
		fakeUser.setUserId(FAKE_USER_ID);
		return fakeUser;
	}

	/**
	 * 登录失败时使用的用户.
	 *
	 * @param req the req
	 * @param loginNm the login nm
	 * @return the sys user vo
	 */
	public static SysUserVo generateFakeUser(HttpServletRequest req, String loginNm) {
		if ( loginNm == null ) {
			return generateFakeUser(req);
		}
		SysUserVo fakeUser = new SysUserVo();
		fakeUser.setLoginId(loginNm);
		fakeUser.setOrgCode("");
		fakeUser.setUserId(FAKE_USER_ID);
		return fakeUser;
	}

	/**
	 * 获取日志对象.
	 *
	 * @param resourceId 资源ID
	 * @param resourceZh 资源中文描述
	 * @param exception 异常信息 为空时判断操作成功
	 * @param fakeUser 假造用户 为空时使用security的用户对象
	 * @return the sys oprlog vo
	 */
	public static SysOprlogVo generateLog(String resourceId, String resourceZh,
			String exception, SysUserVo fakeUser) {
		// get http servlet request for use
		HttpServletRequest request = ServletActionContext.getRequest();
		// get current request url
		String url = request.getRequestURI();

		// get resMap from url
		SysUserVo user = fakeUser == null ? (SysUserVo) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal() : fakeUser;
		String ip = WebRequestUtil.getIpAddr(request);
		LogInfo log = DBLogUtil.getLogInfo();

		SysOprlogVo vo = new SysOprlogVo();

		if (exception == null) {
			// 00: 操作成功
			vo.setOprSuccess("00"); 
		} else {
			// 99: 操作失败
			vo.setOprSuccess("99"); 
			vo.setOprRemark(exception);
		}

		vo.setOprIp(ip);
		if (log != null) {
			vo.setOprResultInfo(log.getResultInfo());
		}

		vo.setOprSysCode("");
		vo.setOprUrl(url);
		vo.setResId(resourceId);
		vo.setResZh(resourceZh);

		vo.setOprLoginId(user.getLoginId());
		vo.setOprOrgCode(user.getOrgCode());
		vo.setOprUserId(user.getUserId());

		return vo;
	}
	
	/**
	 * Generate log.
	 *
	 * @param resourceId the resource id
	 * @param resourceZh the resource zh
	 * @param exception the exception
	 * @param fakeUser the fake user
	 * @param request the request
	 * @return the sys oprlog vo
	 */
	public static SysOprlogVo generateLog(String resourceId, String resourceZh,
			String exception, SysUserVo fakeUser, HttpServletRequest request) {
		// get current request url
		String url = request.getRequestURI();

		// get resMap from url
		SysUserVo user = fakeUser == null ? (SysUserVo) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal() : fakeUser;
		String ip = WebRequestUtil.getIpAddr(request);
		LogInfo log = DBLogUtil.getLogInfo();

		SysOprlogVo vo = new SysOprlogVo();

		if (exception == null) {
			// 00: 操作成功
			vo.setOprSuccess("0000");
		} else {
			// 01: 操作失败
			vo.setOprSuccess("9999");
			vo.setOprRemark(exception);
		}
		vo.setOprIp(ip);
		if (log != null) {
			vo.setOprResultInfo(log.getResultInfo());
		}
		vo.setOprSysCode("");
		vo.setOprUrl(url);
		vo.setResId(resourceId);
		vo.setResZh(resourceZh);

		vo.setOprLoginId(user.getLoginId());
		vo.setOprOrgCode(user.getOrgCode());
		vo.setOprUserId(user.getUserId());

		return vo;
	}
}
