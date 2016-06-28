package com.jiuyv.common.httpclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuyv.common.database.exception.BaseException;

public abstract class HttpClientUtil {
	
	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpClientUtil.class);

	public static final String DEFAULT_ENCODING = "UTF-8";
	
	
	/**
	 * 使用Map进行 POST方法
	 * @param address
	 * @param params
	 * @return
	 */
	public static String post(String address, Map<String, String> params) throws BaseException{
		return post(address, createSortLinkBean(params).getList());
	}
	
	/**
	 * 
	 * @param address
	 * @param pairList
	 * @return
	 * @throws BaseException
	 */
	public static String post(String address, List<NameValuePair> pairList)
			throws BaseException {
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		cm.setMaxTotal(100);
		DefaultHttpClient httpclient = new DefaultHttpClient(cm);
		HttpPost httppost = new HttpPost(address);
		String result = null;
		try {
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(pairList,
					HTTP.UTF_8);
			httppost.setEntity(urlEntity);
			// 发送重试次数
			DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler();
			httpclient.setHttpRequestRetryHandler(handler);
			// 超时时间
			// httpclient.getParams().setIntParameter( CoreConnectionPNames.CONNECTION_TIMEOUT, prop.getHttpclientConnectTime());
			// httpclient.getParams().setIntParameter( CoreConnectionPNames.SO_TIMEOUT, prop.getHttpclientResponseTime());
			LOGGER.info("-----begin httpclient post-----");
			HttpResponse response = httpclient.execute(httppost);
			LOGGER.info("-----httpclient post connect success-----");
			HttpEntity entity = response.getEntity();
			// if (entity.getContentLength() > 0) {
			result = EntityUtils.toString(entity, HTTP.UTF_8);
			LOGGER.info("response content is: {}", result);
			// } else {
			// LOGGER.info("merchant no response");
			// }

		} catch (Exception e) {
			LOGGER.error("-----httpclient post connect error or timeout-----");
			LOGGER.error("post request has a error [{}]", e.getMessage());
			// throw new BaseException(ErrorCode.HTTP_POST_ERROR,
			// ErrorCode.HTTP_POST_ERROR_DESC, e);

		} finally {
			httppost.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * 
	 * @param url
	 * @param parameter
	 * @param cookiePolicy
	 * @return
	 */
	public static String post(String url, HttpEntity parameter,
			String cookiePolicy) {
		String response = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		if (cookiePolicy != null) {
			HttpClientParams.setCookiePolicy(httpclient.getParams(),cookiePolicy);
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(parameter);
			response = httpclient.execute(httpPost, responseHandler);
		} catch (Exception e) {
			LOGGER.error("-----httpclient post connect error or timeout-----");
			LOGGER.error("post request has a error [{}]", e);
		} finally {
			httpPost.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return response;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws BaseException
	 */
	public static String get(String url) throws BaseException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		String result = null;
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// if (entity.getContentLength() > 0) {
			result = EntityUtils.toString(entity);
			// } else {
			// LOGGER.info("get no response string");
			// }
		} catch (Exception e) {
			LOGGER.error("-----httpclient get connect error or timeout-----");
			LOGGER.error(e.getMessage());
			throw new BaseException("","", e);
		} finally {
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
	
	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createSortLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		int keyLen = keys.size();
		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < keyLen; i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			if (i == keyLen - 1) {// 拼接时，不包括最后一个&字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("&");
			}
		}

		return prestr.toString();
	}
	
	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static RequestBean createSortLinkBean(Map<String, ? extends Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		int keyLen = keys.size();
		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < keyLen; i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			pairList.add(new BasicNameValuePair(key, value));

			if (i == keyLen - 1) {// 拼接时，不包括最后一个&字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("&");
			}
		}
		RequestBean bean = new RequestBean();
		bean.setList(pairList);
		bean.setDelegateUrl(prestr.toString());
		return bean;
	}
	
	/**
	 * 
	 * @param url
	 * @param context
	 * @return
	 */
	public static String postXmlStr(String url, String context, String encode) {
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
		cm.setMaxTotal(100);
		DefaultHttpClient client = null;
		HttpPost post = null;
		String reqEnc = encode == null ? DEFAULT_ENCODING:encode; 
		try {
			post = new HttpPost(url);
			client = new DefaultHttpClient(cm);
			LOGGER.info(context);
			post.setEntity(new StringEntity(context, "text/xml", reqEnc));
			post.addHeader("Accept-Language", "zh-cn");
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, reqEnc);
			LOGGER.info(result);
			return result;
		} catch (Exception e) {
			LOGGER.error("Error post xml.", e);
		} finally {
			post.abort();
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	
}	
