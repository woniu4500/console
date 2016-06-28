package com.jiuyv.hhc.console.basic.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Filter.Comparison;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.excel.DefaultHSSOutput;
import com.jiuyv.common.excel.HSSFXLSWriter;
import com.jiuyv.common.excel.XLSSheetTemplate;
import com.jiuyv.common.json.INotConvert;
import com.jiuyv.common.web.action.ActionTextProvider;
import com.jiuyv.common.web.util.WebRequestUtil;
import com.jiuyv.hhc.model.ErrorCode;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysUserVo;
import com.opensymphony.xwork2.Action;

/**
 * 默认的分页支持类。 继承了ActionSupport类。
 * 并且提供可直接获得分页信息，条件集合，用户信息，语言代码的操作。.
 *

 * @author 
 * @since 2013-12-24 11:47:28
 * @version 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultPageSupportAction extends ActionTextProvider implements Action {
	
	/** Logger for this class. */
	private static final Logger LOG = LoggerFactory.getLogger(DefaultPageSupportAction.class);
	
	/** 应用返回路径 */
	public static final String APP_SUCC = "appsuccess";
	/** 返回EXCEL: excel. */
	private static final String EXCEL = "excel";
	

	/** 日期格式 yyyy-MM-dd. */
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	/** 时间格式 yyyy-MM-dd HH:mm:ss */
	private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";

	/** 无数据. */
	public static final String NO_DATA = "nodata";
	/** mime type html: text/html */
	private static final String MIME_HTML = "text/html";
	/** 系统管理结构代码  */
	public static final String ROOT_ORG_CODE = "-1";
	/** The Constant TOKEN_TIME. */
	private static final String TOKEN_TIME = "page.output.xls.time";
	/** The Constant TOKEN_USER. */
	private static final String TOKEN_USER = "page.output.xls.user";
	/** json config. */
	private static final JsonConfig CONFIG = new JsonConfig();
	
	static {
		CONFIG.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (value instanceof String
						&& "".equals(String.valueOf(value).trim())) {
					return true;
				}
				return false;
			}
		});
	}

	/** 页面传递过来的列表信息. */
	private String columns ;

	/** 排序域. */
	private String dir;

	/** 导出文件名. */
	private String fileName;
	
	/** 导出文件的大小 */
	private Integer fileSize; 

	/** 输入流字节数组. */
	private byte[] inputStreamBytes;
	
	/** Action 中存储的对象列表信息. */
	private String jsonArray;

	/** 过滤器信息存储域. */
	private String jsonfilter;
	
	/** Action 中存储的对象信息. */
	private String jsonObject;

	/** 每页记录数. */
	private String limit;

	/** 数据存储域. */
	private String resData;

	/** 升序/降序. */
	private String sort;

	/** 起始记录数. */
	private String start;

	/**
	 * 添加属性到RequestScope
	 * @param key
	 * @param value
	 */
	public void addRequestAttr(String key, Object value) {
		if ( key != null && value != null ) {
			HttpServletRequest req = ServletActionContext.getRequest();
			req.setAttribute(key, value);
		}
	}

	/**
	 * 添加属性到SessionScope
	 * @param key
	 * @param value
	 */
	public void addSessionAttr(String key, Object value) {
		if ( key != null && value != null ) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute(key, value);
		}
	}

	/**
	 * ajax响应.
	 *
	 * @param content the content
	 * @param type the type
	 * @return the string
	 */
	public String ajax(String content, String type) {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			LOG.error("Error write ajax to response. ", e );
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				LOG.error("Error close response writer. ");
			}
		}
		return null;
	}

	/**
	 * AJAX输出HTML，返回null.
	 *
	 * @param html the html
	 * @return the string
	 */
	public String ajaxHtml(String html) {
		return ajax(html, MIME_HTML);
	}

	/**
	 * AJAX输出json数据.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public String ajaxJson( Object obj ) {
		if (obj != null) {
			if (obj instanceof List) {
				return ajax(JSONArray.fromObject(obj).toString(), MIME_HTML);
			} else {
				if (!(obj instanceof INotConvert || obj instanceof String)) {
					return ajax(JSONObject.fromObject(obj).toString(), MIME_HTML);
				}
				return ajax(String.valueOf(obj), MIME_HTML);
			}
		}
		return null;
	}

	/**
	 * AJAX输出文本，返回null.
	 *
	 * @param text the text
	 * @return the string
	 */
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}
	
	/**
	 * AJAX输出XML，返回null.
	 *
	 * @param xml the xml
	 * @return the string
	 */
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}
	
	/**
	 * 默认excel文件导出。
	 * 工作页标签名称和工作表中名称一致。.
	 *
	 * @param title 工作表标题
	 * @param props 输出属性
	 * @param data ExtData对象
	 * @throws IOException Signals that an I/O exception has occurred
	 * @throws BaseException 
	 */
	public String defaultExportXLS(String title, List<CellDataType> props,
			ExtData data) throws IOException, BaseException {
		return this.defaultExportXLS(title, title, title, props, data == null ? new ArrayList():data.getRoot());
	}
	
	/**
	 * 默认excel文件导出。
	 * 工作页标签名称和工作表中名称一致。.
	 *
	 * @param title 工作表标题
	 * @param props 输出属性
	 * @param data 输出数据
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BaseException 
	 */
	public String defaultExportXLS(String title, List<CellDataType> props,
			List data) throws IOException, BaseException {
		xlsName(title);
		return this.defaultExportXLS(title, title, title, props, data);
	}

	/**
	 * Default export xls.
	 * 默认excel文件导出。
	 * 
	 * @param fileName 文件名
	 * @param sheetName 工作页名
	 * @param title 工作表标题
	 * @param props 输出属性
	 * @param data 输出数据
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BaseException 
	 */
	public String defaultExportXLS(String fname, String sheetName, String title, List<CellDataType> props,
			List data) throws IOException, BaseException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SysUserVo user = getUserInfo();
		String prefixUser = getText(TOKEN_USER);
		String prefixTime = getText(TOKEN_TIME)
				+ new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (user != null) {
			prefixUser += user.getUserName();
		}
		XLSSheetTemplate sheet = new XLSSheetTemplate(sheetName, title, prefixUser,
				prefixTime, data, getLocalName(props));
		try {
			HSSFXLSWriter.outputXLS(sheet, out);
		} catch (Exception e) {
			LOG.error("Error write to xls file. ",e);
		}
		this.inputStreamBytes = out.toByteArray();
		xlsName(fname);
		return EXCEL;
	}
	
	/**
	 * Export xls.
	 * 
	 * @param title
	 *            the title
	 * @param propName
	 *            the prop name
	 * @param resKey
	 *            the res key
	 * @param data
	 *            the data
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String defaultExportXLS(String title, String[] propName,
			String[] resKey, List data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Map<String, String> meta = new HashMap<String, String>();
		meta.put("title", title);
		meta.put("sheet", title);
		try {
			DefaultHSSOutput.outputTitledXLS(meta, data, propName,
					getLocalName(resKey), out);
		} catch (Exception e) {
			LOG.error("Output XLS error: ", e);
		}
		this.inputStreamBytes = out.toByteArray();
		xlsName(title);
		return EXCEL;
	}

	/**
	 * 设置输出文件名称（支持中文名称文件设置）
	 * 对文件名进行编码并设置到fileName属性
	 * @param name
	 * @return
	 */
	protected String encodeFileName(String name ) {
		String fileName = name ; 
		try {
			fileName = WebRequestUtil.encodeFileName(ServletActionContext.getRequest(), name);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Error in convert fileName ",e);
		}
		setFileName(fileName);
		return fileName;
	}

	/**
	 * Execute. 
	 * Struts Action的默认执行方法
	 * @return success
	 * @throws Exception
	 *             the exception
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 获取到处数据列定义.
	 *
	 * @param defCellDef the def cell def
	 * @return the list
	 */
	public List<CellDataType> exportCellDef(List<CellDataType> defCellDef) {
		if ( this.columns != null ) {
			try{
				JSONArray jsonArr = JSONArray.fromObject(this.columns);
				return new ArrayList(JSONArray.toCollection(jsonArr, CellDataType.class));
			} catch ( Exception e) {
				LOG.error("Invalid cell definition in page. ", e);
			}
		}
		return defCellDef;
	}

	/**
	 * Gets the bean list.
	 * 
	 * @param field request.getParameter(field)
	 * @param beanType Bean.class
	 * @return List<beanType>
	 */
	public <T> List<T> getBeanList(String field, Class beanType) {
		String json = ServletActionContext.getRequest().getParameter(field);
		if ( json == null || json.trim().length() == 0 ) {
			return null ; 
		}
		JSONArray jsonArr = JSONArray.fromObject(json);
		return new ArrayList(JSONArray.toCollection(jsonArr, beanType));
	}

	/**
	 * 获取 页面传递过来的列表信息.
	 *
	 * @return the columns
	 */
	public String getColumns() {
		return columns;
	}
	
	/**
	 * 获取 排序域.
	 *
	 * @return the 排序域
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * 获得分页信息(导出报表Excel用的)。.
	 * 
	 * @return 分页信息
	 */
	public Page getExcelPageInfo() {
		Page page = getPageInfo();
		page.setLimit(EXCEL);
		return page;
	}

	/**
	 * 获取 导出文件名.
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * 获得条件集合信息。.
	 * 
	 * @return 条件集合
	 */
	public List<Filter> getFilters() {
		List<Filter> filters = new ArrayList<Filter>();
		if (jsonfilter != null && !"".equals(jsonfilter.trim())) {
			JSONArray arr = JSONArray.fromObject(jsonfilter, CONFIG);
			if (arr == null) {
				return filters;
			}
			for (Object object : arr) {
				Object obj = JSONObject.toBean((JSONObject) object, Filter.class);
				filters.add((Filter) obj);
			}
		}
		return filters;
	}

	/**
	 * Gets the input stream.
	 * 
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		return new ByteArrayInputStream(inputStreamBytes);
	}

	/**
	 * 获取 action 中存储的对象列表信息.
	 *
	 * @return the action 中存储的对象列表信息
	 */
	public String getJsonArray() {
		return jsonArray;
	}

	/**
	 * 获取 过滤器信息存储域.
	 *
	 * @return the 过滤器信息存储域
	 */
	public String getJsonfilter() {
		return jsonfilter;
	}

	/**
	 * 获取 action 中存储的对象信息.
	 *
	 * @return the action 中存储的对象信息
	 */
	public String getJsonObject() {
		return jsonObject;
	}

	/**
	 * 获得当前请求中的语言代码.
	 * 
	 * @return 语言代码
	 */
	public String getLang() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getLocale().toString();
	}
	
	/**
	 * 获取 每页记录数.
	 *
	 * @return the 每页记录数
	 */
	public String getLimit() {
		return limit;
	}
	
	/**
	 * 获取国际化文件名称
	 * @param type 列定义列表
	 * @return 更新国际化后的列定义列表
	 */
	public List<CellDataType> getLocalName(List<CellDataType> type) {
		if (type == null) {
			return null;
		}
		List<CellDataType> nType = new ArrayList<CellDataType>();
		for (CellDataType cellDataType : type) {
			nType.add(new CellDataType(cellDataType.getPropName(),
					getText(cellDataType.getShowName()), cellDataType
							.getRender()));
		}
		return nType;
	}
	
	/**
	 * Gets the local name.
	 * 
	 * @param resKey
	 *            the res key
	 * 
	 * @return the local name
	 */
	public String[] getLocalName(String[] resKey) {
		if (resKey == null || resKey.length < 1) {
			return resKey;
		}
		String[] colNames = new String[resKey.length];
		int i = 0;
		for (String res : resKey) {
			colNames[i++] = getText(res);
		}
		return colNames;
	}
	
	/**
	 * 获得条件集合信息(有机构约束)。.
	 * 
	 * @param orgCodeProp 用户所属机构在对象中的属性字段
	 * @return 条件集合
	 */
	public List<Filter> getOrgFilters(String orgCodeProp) {
		List<Filter> filters = getFilters();
		/*if (filters == null) {
			filters = new ArrayList<Filter>();
		}*/
		SecurityUserDetail userDtl = this.getUserDetailInfo();
		if ( !ROOT_ORG_CODE.equals(userDtl.getOrgCode()) ) {
			Filter orgfilter = new Filter(orgCodeProp, Filter.LIST, new ArrayList(this.getUserDetailInfo().getChildOrgId()), Filter.Comparison.EQ);
			filters.add(orgfilter);
		}
		return filters;
	}
	

	/**
	 * 获得分页信息。.
	 * 
	 * @return 分页信息
	 */
	public Page getPageInfo() {
		Page page = new Page();
		page.setDir(this.dir);
		page.setLimit(this.limit);
		page.setSort(this.sort);
		page.setStart(this.start);
		return page;
	}

	/**
	 * 获取request中的信息.
	 * 需要连续获取request中参数时，请使用 {@link org.apache.struts2.ServletActionContext#getRequest()}方法获取javax.servlet.HttpServletRequest 对象的引用。
	 * @param paramName the param name
	 * @return the parameter
	 */
	public String getParameter(String paramName  ){
		return ServletActionContext.getRequest().getParameter(paramName);
	}

	/**
	 * 获取 数据存储域.
	 *
	 * @return the 数据存储域
	 */
	public String getResData() {
		return resData;
	}

	/**
	 * 获取 升序/降序.
	 *
	 * @return the 升序/降序
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * 获取 起始记录数.
	 *
	 * @return the 起始记录数
	 */
	public String getStart() {
		return start;
	}

	/**
	 * 获取jsonObject对象中域的字符串值.
	 *
	 * @param fieldName the field name
	 * @return the string field
	 */
	public String getStringField (String fieldName ) {
		JSONObject jsonObj = JSONObject.fromObject(getJsonObject());
		return jsonObj.getString(fieldName);
	}

	/**
	 * 获取用户信息. 当用户信息为空时抛出异常
	 *
	 * @return the user detail info
	 */
	public SecurityUserDetail getUserDetailInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ( principal == null || principal instanceof String ) {
			throw new RuntimeException(getText("prompt.sessionExpired"));
		}
		return (SecurityUserDetail) principal;
	}

	/**
	 * 获得条件集合信息(有用户约束)。.
	 * 
	 * @param userProperty 用户Id在对象中的属性字段
	 * @return 条件集合
	 * @throws BaseException 
	 */
	public List<Filter> getUserFilters(String userProperty) throws BaseException {
		List<Filter> filters = getFilters();
		/*if (filters == null) {
			filters = new ArrayList<Filter>();
		}*/
		Filter userfilter = new Filter(userProperty, Filter.STRING, this.getUserInfo().getUserId(), Comparison.EQ);

		filters.add(userfilter);
		return filters;
	}

	/**
	 * 获得条件集合信息(有用户约束)。.
	 * 
	 * @param userProperty 用户登录账号在对象中的属性字段
	 * @return 条件集合
	 * @throws BaseException 
	 */
	public List<Filter> getUserLoginIdFilters(String userProperty) throws BaseException {
		List<Filter> filters = getFilters();
		Filter userfilter = new Filter(userProperty, Filter.STRING, this.getUserInfo().getLoginId(), Comparison.EQ);
		filters.add(userfilter);
		return filters;
	}

	/**
	 * 是否包含权限
	 * @param resId ROLE_开头的权限信息
	 * @return
	 * @throws BaseException
	 */
	public boolean containAuthor (String resId) throws BaseException {
		if ( StringUtils.isBlank(resId) ) {
			return false;
		}
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		Map<String, Boolean> authors = (Map<String, Boolean>) session.getAttribute("_AUTHORS");
		if ( authors == null ) {
			authors = new HashMap<String, Boolean>();
			session.setAttribute("_AUTHORS", authors);
		}
		Boolean auth = authors.get(resId);
		SecurityUserDetail user = getUserDetailInfo();
		if ( user == null ) {
			return false;
		}
		if ( auth == null ) {
			auth = false;
			for ( GrantedAuthority authority: user.getAuthorities()) {
				if ( StringUtils.equals(authority.getAuthority(), resId)) {
					auth = true;
					break;
				}
			}
			authors.put(resId, auth);
		} 
		return auth;
	}
	
	/**
	 * 获得简单用户信息。（用于后台的操作） 登录时，不能使用此方法来获得用户信息。.
	 * 
	 * @return 获得用户信息
	 * @throws BaseException 
	 */
	public SysUserVo getUserInfo() throws BaseException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ( principal == null || principal instanceof String ) {
			throw new BaseException(ErrorCode.CODE_9903, getText("prompt.sessionExpired"));
		}
		return (SysUserVo) principal;
	}
	
	/**
	 * Gets the validate bean.
	 *
	 * @param <T> the generic type
	 * @param beanClass the bean class
	 * @return the validate bean
	 * @throws BaseException the base exception
	 */
	public <T> T getValidateBean(Class<T> beanClass) throws BaseException {
		String json = this.getJsonObject();
		JSONObject jsonObj = JSONObject.fromObject(json);
		return (T) JSONObject.toBean(jsonObj, beanClass);
	}

	/**
	 * Gets the validate list.
	 *
	 * @param <T> the generic type
	 * @param beanClass the bean class
	 * @return the validate list
	 * @throws BaseException the base exception
	 */
	public <T> List<T> getValidateList(Class<T> beanClass) throws BaseException {
		String json = this.getJsonArray();
		JSONArray jsonArr = JSONArray.fromObject(json);
		return new ArrayList(JSONArray.toCollection(jsonArr,beanClass));
	}

	/**
	 * 转换对象为json格式存储到resData，默认完成返回 success 结果页面
	 *
	 * @param resData 数据对象
	 */
	public String resultData(Object resData) {
		String retStr = null;
		if (resData != null) {

			if (resData instanceof List) {
				retStr = JSONArray.fromObject(resData).toString();
			} else {
				if (!(resData instanceof INotConvert || resData instanceof String)) {
					retStr = JSONObject.fromObject(resData).toString();
				}
			}
		}
		this.resData = retStr;
		return SUCCESS;
	}

	/**
	 * 设置 数据存储域.
	 *
	 * @param resData the new 数据存储域
	 */
	public String resultData(String resData) {
		this.resData = resData;
		return SUCCESS;
	}
	
	/**
	 * 验证属性配置是否合法，去除无法获取的字段属性。.
	 * 用于列名来自页面的导出功能（过滤页面传递的无效列名）
	 *
	 * @param fileName 文件名
	 * @param sheetName 工作页名
	 * @param title 工作表标题
	 * @param props 属性
	 * @param data 数据
	 * @param clazz 目标数据类型
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BaseException 
	 */
	public void safeCheckExportXLS(String fileName, String sheetName, String title, List<CellDataType> props,
			List data, Class clazz) throws IOException, BaseException {
		List<CellDataType> checkedProps = new ArrayList<CellDataType>();
		if ( clazz != null && props != null) {
			for (CellDataType cdt : props) {
				String propName = cdt.getPropName();
				if ( StringUtils.isBlank(propName) ) {
					LOG.error("Excel export check error: blank property. ");
					continue;
				}
				try {
					clazz.getDeclaredField(propName);
					checkedProps.add(cdt);
				} catch (Exception e) {
					// unable to access prop in class
					LOG.error("Excel export check error: property ["+propName+"] cannot be accessed in ["+clazz.getName()+"]");
				}
			}
		}
		this.defaultExportXLS(fileName, sheetName, title, checkedProps, data);
	}
	
	/**
	 * 获取会话属性
	 * @param key
	 * @return
	 */
	public Object sessionAttr(String key) {
		if ( key != null  ) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			return session.getAttribute(key);
		}
		return null;
	}

	/**
	 * 设置 页面传递过来的列表信息.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

	/**
	 * 设置 排序域.
	 *
	 * @param dir the new 排序域
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * 设置 导出文件名.
	 *
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 设置 输入流字节数组.
	 *
	 * @param inputStreamBytes the new 输入流字节数组
	 */
	public void setInputStreamBytes(byte[] inputStreamBytes) {
		if(inputStreamBytes!=null){
			this.inputStreamBytes = inputStreamBytes.clone();
			setFileSize(this.inputStreamBytes.length);
		}
	}

	/**
	 * 设置 action 中存储的对象列表信息.
	 *
	 * @param jsonArray the new action 中存储的对象列表信息
	 */
	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}


	/**
	 * 设置 过滤器信息存储域.
	 *
	 * @param jsonfilter the new 过滤器信息存储域
	 */
	public void setJsonfilter(String jsonfilter) {
		try {
			this.jsonfilter = java.net.URLDecoder.decode(jsonfilter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("Error decode jsonfilter", e);
		}
	}

	/**
	 * 设置 action 中存储的对象信息.
	 *
	 * @param jsonObjects the new action 中存储的对象信息
	 */
	public void setJsonObject(String jsonObjects) {
		this.jsonObject = jsonObjects;
	}

	/**
	 * 设置 每页记录数.
	 *
	 * @param limit the new 每页记录数
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * 设置 升序/降序.
	 *
	 * @param sort the new 升序/降序
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 设置 起始记录数.
	 *
	 * @param start the new 起始记录数
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * 获取下载的xls文件名
	 * title[yyyy-MM-dd HH:mm:ss].xls
	 * @param title
	 * @return
	 */
	protected String xlsName(String title) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		String fnm = String.format("%s-%s.xls", title,sdf.format(new Date()));
		return encodeFileName(fnm);
	}

	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
}
