package com.jiuyv.hhc.console.basic.interceptor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Locale;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.json.IConverter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;

/**
 * The Class DefaultExceptionConverter.
 * 
 * @version 0.1
 * @author 
 * 
 *         <h2>The Class DefaultExceptionConverter.</h2>
 *         <p>
 *         </p>
 */
public class DefaultExceptionConverter implements IConverter, Serializable,
		LocaleProvider {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 691690103881470904L;

	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultExceptionConverter.class);

	private static final String UPLOAD_FILEMAX_ERR = "error.certificateImport.fileMax";

	/** The Constant UNKNOWN_ERR. */
	private static final String UNKNOWN_ERR = "common.error.unknown.exception";

	/** The text provider. */
	private transient TextProvider textProvider = new TextProviderFactory()
			.createInstance(getClass(), this);

	/**
	 * 转换Exception到JSONString.
	 * 
	 * @param attrObj
	 *            源物件
	 * @return Object 目标物件
	 * @see com.jiuyv.common.json.huateng.expo.web.interceptor.IConverter#convertFromAttrObject(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	public Object convertFromAttrObject(Object attrObj) {
		if (attrObj == null) {
			return null;
		}
		if (attrObj instanceof String) {
			return attrObj;
		}
		ExtData data = null;
		if (attrObj instanceof SizeLimitExceededException) {
			LOGGER.error("SizeLimitExceededException error",
					(Exception) attrObj);
			data = ExtDataUtil.genWithExceptions(textProvider
					.getText(UPLOAD_FILEMAX_ERR));
			return JSONObject.fromObject(data).toString();
		}
		// convert busincess exception
		if (attrObj instanceof BaseException) {
			LOGGER.error("Base Exception ", (Exception) attrObj);
			BaseException be = (BaseException) attrObj;
			data = ExtDataUtil.genWithExceptions(textProvider.getText(be
					.getMessage()));
			return JSONObject.fromObject(data).toString();
		}
		if (attrObj instanceof SQLException) {
			LOGGER.error("SQLException ", (Exception) attrObj);
			SQLException be = (SQLException) attrObj;
			data = ExtDataUtil.genWithExceptions(textProvider.getText(be
					.getMessage()));
			return JSONObject.fromObject(data).toString();
		}
		if (attrObj instanceof JSONException) {
			LOGGER.error("JSONException ", (Exception) attrObj);
			JSONException be = (JSONException) attrObj;
			data = ExtDataUtil.genWithExceptions(be.getMessage());
			return JSONObject.fromObject(data).toString();
		}
		// convert system exception
		if (attrObj instanceof Exception) {
			Exception be = (Exception) attrObj;
			LOGGER.error("System unknow error", be);
			data = ExtDataUtil.genWithExceptions(textProvider.getText(UNKNOWN_ERR));
			return JSONObject.fromObject(data).toString();
		}
		data = ExtDataUtil.genWithExceptions(textProvider.getText(UNKNOWN_ERR));
		return JSONObject.fromObject(data).toString();
	}

	/**
	 * 实现LocaleProvide方法.
	 * 
	 * @return Locale
	 */
	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}
}
