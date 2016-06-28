package com.jiuyv.hhc.console.security.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.console.security.action.ErrorAction;
import com.jiuyv.hhc.console.security.action.LoginAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * The Class Client Proxy Interceptor.
 * 
 * 客户端参数处理拦截器
 * 

 * @author 
 * @since 2014-3-16 23:32:05
 * @version 1.0.0
 */
public class ClientProxyInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -788218310515483632L;

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientProxyInterceptor.class);

	/** The Map url map. */
	private Map<String, String> urlMap = new HashMap<String, String>();
	/**
	 * @param invocation
	 * @return
	 * @throws Exception
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// 处理客户端访问参数
		if ( WebRequestUtil.isAppClient(ServletActionContext.getRequest()) ){
			ActionContext ac = invocation.getInvocationContext();
			Map<String,Object> param = ac.getParameters();
			
			String cmd = ServletActionContext.getRequest().getParameter("cmd");
			if ( invocation.getAction() instanceof ErrorAction ) {
				return invocation.invoke(); 
			}
			if ( !(invocation.getAction() instanceof LoginAction) &&  !urlMap.containsKey(cmd) ) {
				LOGGER.error("Invalid cmd{} from client: ", cmd);
				return "cmdError";
			}
			String[] dataArr = (String[]) param.get("data");
			String data = dataArr!=null&&dataArr.length>0 ? dataArr[0]:null;
			if ( StringUtils.isNotBlank(data) ) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(data);
				Iterator<String> iterator = node.fieldNames();
				while( iterator.hasNext() ) {
					String fieldName = iterator.next();
					param.put(fieldName, node.get(fieldName).toString());
				}
				ac.setParameters(param);
			}
			ServletActionContext.getRequest().setAttribute(WebRequestUtil.XFLAG, "mobile");
			String result = invocation.invoke();
			if ( "success".equals(result) ) {
				return "appsuccess";
			}
			return result;
		}
		return invocation.invoke(); 
	}
	
	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}
	

	

}
