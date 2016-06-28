package com.jiuyv.common.validate;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.exception.BaseException;

/**
 * The Class BizCheck.
 *

 * @author cowyk
 * @since 2014-2-17 15:41:44
 * @version 1.0.0
 */
public abstract class BizCheck {

	/**
	 * 验证为非空对象.
	 *
	 * @param toChk the to chk
	 * @param errorCode 错误代码
	 * @param errorMsg 错误信息
	 * @throws BaseException the base exception
	 */
	public static void notNull(Object toChk, String errorCode, String errorMsg) throws BaseException {
		if ( toChk == null ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}
	
	/**
	 * 验证集合对象非空.
	 *
	 * @param toChk the to chk
	 * @param errorCode 错误代码
	 * @param errorMsg 错误信息
	 * @throws BaseException the base exception
	 */
	@SuppressWarnings("rawtypes")
	public static void notEmpty(Collection toChk, String errorCode, String errorMsg) throws BaseException {
		if ( toChk == null || toChk.size() == 0 ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}
	
	/**
	 * 验证字符串为非空串.
	 *
	 * @param toChk the to chk
	 * @param errorCode 错误代码
	 * @param errorMsg 错误信息
	 * @throws BaseException the base exception
	 */
	public static void notBlank(String toChk, String errorCode, String errorMsg) throws BaseException {
		if ( StringUtils.isBlank(toChk) ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}
	
	/**
	 * 验证对象相等 调用equals方法，为null时也报错.
	 *
	 * @param toChk the to chk
	 * @param target the target
	 * @param errorCode 错误代码
	 * @param errorMsg 错误信息
	 * @throws BaseException the base exception
	 */
	public static void same(Object toChk, Object target, String errorCode, String errorMsg) throws BaseException {
		if ( toChk == null || target ==  null ) {
			throw new BaseException(errorCode, errorMsg);
		}
		if ( !toChk.equals(target) ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}
	
	/**
	 * 验证对象不相等 调用equals方法，比较对象相同时报错,
	 * 为null时也报错.
	 *
	 * @param toChk the to chk
	 * @param target the target
	 * @param errorCode the error code
	 * @param errorMsg the error msg
	 * @throws BaseException the base exception
	 */
	public static void notSame(Object toChk, Object target, String errorCode, String errorMsg) throws BaseException {
		if ( toChk == null || target ==  null ) {
			throw new BaseException(errorCode, errorMsg);
		}
		if ( toChk.equals(target) ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}

	/**
	 * 验证结果为True, false时抛出异常.
	 *
	 * @param toChk the to chk
	 * @param errorCode the error code
	 * @param errorMsg the error msg
	 * @throws BaseException the base exception
	 */
	public static void isTrue(boolean toChk, String errorCode,
			String errorMsg) throws BaseException  {
		if ( !toChk ) {
			throw new BaseException(errorCode, errorMsg);
		}
	}
	
}
