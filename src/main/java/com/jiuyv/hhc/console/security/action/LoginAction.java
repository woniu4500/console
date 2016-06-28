package com.jiuyv.hhc.console.security.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.tree.TreeNode;
import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.ISecurityService;
import com.jiuyv.hhc.console.security.util.DBLogUtil;
import com.jiuyv.hhc.console.security.util.ResourceCache;
import com.jiuyv.hhc.console.security.util.TreeUtil;
import com.jiuyv.hhc.model.security.SecurityUserDetail;


/**
 * The Class LoginAction.
 *

 * @author 
 * @since 2014-1-3 16:23:37
 * @version 1.0.0
 */
public class LoginAction extends DefaultPageSupportAction {

	/** log4j日志对象. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAction.class);

	private static final String AJAX = "ajaxSuccess";
	
	/** The Resource cache resource. */
	private ResourceCache resource ;

	/** The I security service security service. */
	private ISecurityService securityService;

	/** The Json config config. */
	private JsonConfig config = new JsonConfig();

	{
		this.config.setExcludes(new String[]{"checked"});
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginPage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		resultData(ExtDataUtil.genWithExceptions(getText("prompt.sessionfail")));
		if ( WebRequestUtil.isAjax(request)) {
			return AJAX;
		}
		if ( WebRequestUtil.isAppClient(request) ) {
			return APP_SUCC;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 登录成功后显示主界面.
	 * 
	 * @return 成功界面
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String execute() throws Exception {
		// 得到用户信息和用户的权限列表
		SecurityUserDetail userDetail = (SecurityUserDetail) this.getUserInfo();
		// 形成菜单树
		TreeNode rootnode = TreeUtil.buildResTree(resource.getRoleResMap(), userDetail.getAuthorities());
		String tempnode = JSONArray.fromObject(rootnode.getChildren(), config).toString();
		LOGGER.debug(tempnode);
		// generate buttonAuthor
		StringBuilder buf = new StringBuilder();
		Set<String> authorSet = new HashSet<String>();
		for ( GrantedAuthority auth: userDetail.getAuthorities() ) {
			buf.append(auth.getAuthority()).append(",");
			authorSet.add(auth.getAuthority());
		}
		addSessionAttr("AUTHORS", authorSet);
		addRequestAttr("userDetail", userDetail);
		addRequestAttr("buttonsAuthor", buf.toString());
		resultData(tempnode);
		// 'ROLE_CONNECT'
		securityService.logRecord(DBLogUtil.generateLog("ROLE_CONNECT", "系统登录", null, null));
		// add for mobile client, return a ajax type result page
		if ( WebRequestUtil.isAppClient(null) ) {
			resultData(ExtDataUtil.genWithSingleData(""));
			return APP_SUCC;
		}
		return SUCCESS;
	}

	/**
	 * 注销.
	 * 
	 * @return 成功界面
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String loginOut() throws Exception {
		if ( WebRequestUtil.isAppClient(null) ) {
			resultData(ExtDataUtil.genWithSingleData(""));
			return APP_SUCC;
		}
		return SUCCESS;
	}
	
	/**
	 * 获取系统时间.
	 *
	 * @return the string
	 */
	public String findSysInfoTime() {
		String time = securityService.findSysTime();
		return ajaxJson(ExtDataUtil.genWithSingleData(time));
	}

	/**
	 * Sets the resource.
	 *
	 * @param resource the new resource
	 */
	public void setResource(ResourceCache resource) {
		this.resource = resource;
	}

	/**
	 * Sets the security service.
	 *
	 * @param securityService the new security service
	 */
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

}
