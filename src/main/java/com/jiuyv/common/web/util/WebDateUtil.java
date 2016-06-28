/*
 * 
 */
package com.jiuyv.common.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jiuyv.common.database.exception.BaseException;

/**
 * The Class SimpleDateCheck.
 * 简单的日期转换类.
 *

 * @author 
 * @since 2014-1-3 17:40:38
 * @version 1.0.0
 */
public final class WebDateUtil {
	
	/** Constant Int NUM_3: int :. */
	private static final int NUM_3 = 3;
	
	/** Constant Int NUM_4: int :. */
	private static final int NUM_4 = 4;
	
	/** Constant Int NUM_6: int :. */
	private static final int NUM_6 = 6;
	
	/** Constant Int NUM_8: int :. */
	private static final int NUM_8 = 8;
	
	/** Constant Int NUM_12: int :. */
	private static final int NUM_12 = 12;
	
	/** Constant Int NUM_14: int :. */
	private static final int NUM_14 = 14;
	
	/** temp 数组的splite数量. */
	private static final int TEMP_TOKEN_SIZE = 3;

	/** 非数字表达式. */
	private static final String TOKEN_NOT_LETTER = "\\D";

	/** 空串. */
	private static final String TOKEN_EMPTY = "";

	/** 长时间格式分割后的字段数. */
	private static final int LONG_TIME_TOKENS = NUM_6;

	/** 默认长日期时间长度. */
	private static final int DEFAULT_LONG_DATE_LEN = NUM_14;

	/** 投资期限单位 天. */
	private static final String UNIT_DAY = "1";
	
	/** 投资期限单位 个月. */
	private static final String UNIT_MONTH = "2";
	
	/** 投资期限单位 天 年. */
	private static final String UNIT_YEAR = "3";
	
	/** Constant String BEAN_COPY_ERROR: String :. */
	private static final String BEAN_COPY_ERROR = "0021";
	
	/** Constant String BEAN_COPY_ERROR_DESC: String :. */
	private static final String BEAN_COPY_ERROR_DESC = "对象转换错误";
	
	/**
	 * Instantiates a new simple date check.
	 */
	private WebDateUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 转换类型格式 yyyy-mm-ddT00:00:00 到 yyyymmdd.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String parseDateFromVo2Db(String date) {
		if (!isNotBlank(date)) {
			return null;
		}
		String[] tmp = date.trim().split(TOKEN_NOT_LETTER);
		if (tmp.length < TEMP_TOKEN_SIZE) {
			return null;
		}

		return tmp[0] + tmp[1] + tmp[2];
	}

	/**
	 * 把日期转为１４位数据库字符串 convert from type yyyy-mm-ddT00:00:00 to yyyymmddhhmiss.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String parseDateFromVo2Db14(String date) {
		if (!isNotBlank(date)) {
			return null;
		}

		String[] tmp = date.trim().split(TOKEN_NOT_LETTER);

		if (tmp.length < LONG_TIME_TOKENS) {
			return null;
		}

		String resDate = date.trim().replaceAll(TOKEN_NOT_LETTER, TOKEN_EMPTY);

		if (resDate.length() != DEFAULT_LONG_DATE_LEN) {
			resDate = null;
		}

		return resDate;
	}

	/**
	 * 把日期转为１４位数据库字符串 convert from type yyyy-mm-ddT00:00:00 to yyyymmddhhmiss.
	 * 
	 * @param date
	 *            the date
	 * @param oper
	 *            the oper
	 * @return the string
	 */
	public static String parseDateFromVo2Db14(String date, String oper) {
		if (!isNotBlank(date)) {
			return null;
		}

		String[] tmp = date.trim().split(TOKEN_NOT_LETTER);

		if (tmp.length < LONG_TIME_TOKENS) {
			return null;
		}

		String resDate = date.trim().replaceAll(TOKEN_NOT_LETTER, TOKEN_EMPTY);

		if (resDate.length() != DEFAULT_LONG_DATE_LEN) {
			resDate = null;
		}

		if (">=".equals(oper) || ">".equals(oper)) {
			resDate = resDate.substring(0, NUM_8) + "000000";
		}
		if ("<=".equals(oper) || "<".equals(oper)) {
			resDate = resDate.substring(0, NUM_8) + "235959";
		}

		return resDate;
	}

	/**
	 * 转换日期 mm/dd/yyyy.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String fromMDY2YMD(String date) {
		if (!isNotBlank(date)) {
			return null;
		}

		String temp = date.trim();
		String[] tmp = temp.split(TOKEN_NOT_LETTER);

		if (tmp.length < TEMP_TOKEN_SIZE) {
			return null;
		}

		return tmp[2] + tmp[0] + tmp[1];
	}

	/**
	 * 判断字符串是否为空.
	 * 
	 * @param token
	 *            the token
	 * 
	 * @return true, if checks if is not blank
	 */
	public static boolean isNotBlank(String token) {
		if ((token == null) || TOKEN_EMPTY.equals(token.trim())) {
			return false;
		}

		return true;
	}

	/**
	 * 日期格式转换.
	 * 
	 * @param strDate
	 *            日期
	 * @param inputFormat
	 *            输入格式
	 * @param outputFormat
	 *            输出格式
	 * @return the string
	 * @throws ParseException
	 *             the parse exception
	 */
	public static String displayDateFormat(String strDate, String inputFormat,
			String outputFormat) throws ParseException {

		String strReturn = "";
		if (null == strDate) {
			return strReturn;
		}

		SimpleDateFormat dbFormat = new SimpleDateFormat(inputFormat);
		SimpleDateFormat displayFormat = new SimpleDateFormat(outputFormat);
		strReturn = displayFormat.format(dbFormat.parse(strDate));

		return strReturn;
	}

	/**
	 * Date2 month.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String date2Month(String date) {
		return (date == null || date.length() != NUM_8) ? null : date.substring(0,
				NUM_6)
				+ "00";
	}

	/**
	 * Date2 quarter.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String date2Quarter(String date) {
		if (date == null || date.length() != NUM_8){
			return null;
		}
		int month = Integer.valueOf(date.substring(NUM_4, NUM_6));
		return date.substring(0, NUM_4) + String.format("%02d00", NUM_3 * ((month-1)/NUM_3 +1));
	}
	
	/**
	 * Date2 half year.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String date2HalfYear(String date) {
		if (date == null || date.length() != NUM_8){
			return null;
		}
		int month = Integer.valueOf(date.substring(NUM_4, NUM_6));
		String quarter = null;
		if (month > 0 && month <= NUM_6) {
			quarter = date.substring(0, NUM_4) + "0600";
		}
		if (month > NUM_6 && month <= NUM_12) {
			quarter = date.substring(0, NUM_4) + "1200";
		}
		return quarter;
	}

	/**
	 * Date2 year.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String date2Year(String date) {
		if (date == null || date.length() != NUM_8){
			return null;
		}
		return date.substring(0, NUM_4) + "0000";
	}
	
	/**
	 * yyyyMMdd转化为yyyy-MM-dd.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String string2date(String date) {
		if (date == null || date.length() != NUM_8){
			return null;
		}
		return date.substring(0, NUM_4) + "-" +  date.substring(NUM_4, NUM_6) + "-" + date.substring(NUM_6, NUM_8);
	}
	
	/**
	 * 根据当前系统时间 计算推后 delayDate 天数的时间.
	 *
	 * @param delayDate 延长天数
	 * @return 14位时间 yyyyMMddHHmmss
	 */
	public static String sysTimeDelay(int delayDate){
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		Calendar calendar = Calendar.getInstance();
		//设置当前日期, 日期加1
		calendar.setTime(new Date());   
		calendar.add(Calendar.DATE, delayDate);
        Date date = calendar.getTime();
        return dateFormat.format(date);
	}
	
	/**
	 * 根据传入8位时间延后X天.
	 *
	 * @param sdate the sdate
	 * @param delayDate 延长天数
	 * @return 14位时间 yyyyMMddHHmmss
	 * @throws BaseException the base exception
	 */
	public static String timeDelay(String sdate,int delayDate) throws BaseException{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		//设置当前日期, 日期加1
		try{
			calendar.setTime(dateFormat.parse(sdate)); 
		} catch(ParseException e){
			throw new BaseException(BEAN_COPY_ERROR, BEAN_COPY_ERROR_DESC,e);
		}
		calendar.add(Calendar.DATE, delayDate);
        Date date = calendar.getTime();
        return dateFormat.format(date);
	}

	/**
	 * 根据投资期限单位将投资期限转换成天数.
	 *
	 * @param source 原始数字
	 * @param unit 投资期限单位
	 * @return 天数
	 */
	public static int covertToDay(int source, String unit) {
		if (unit.equals(UNIT_DAY)) {
			return source;
		} else if (unit.equals(UNIT_MONTH)) {
			return source * 30;
		} else if (unit.equals(UNIT_YEAR)) {
			return source * 360;
		} else {
			return -1;
		}
	}
	
	public static void main(String [] args){
		System.out.println(string2date("20160510"));
	}
}
