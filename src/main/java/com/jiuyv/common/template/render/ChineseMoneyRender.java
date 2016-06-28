package com.jiuyv.common.template.render;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.template.RenderAfter;
import com.jiuyv.common.template.RenderType;

public class ChineseMoneyRender implements RenderType,RenderAfter {

	private static final String UPPERNUM = "零壹贰叁肆伍陆柒捌玖";
	private static final String UNIT = "个拾佰仟";
	private static final String GRADEUNIT = "万亿兆";
	private static final int GRADE = 4;
	private static final String DECIMAL = "角分";
	// 对输入的金额数值进行格式化
	private static final NumberFormat nf = new DecimalFormat("0.00");

	@Override
	public String render(String value) {
		if (StringUtils.isNumeric(value)) {
			value = String.valueOf((Double.valueOf(value) / 100.00));
		}
		return value;
	}
	
	/**
	 * After.
	 *
	 * @param propName the prop name
	 * @param propValue the prop value
	 * @param namePattern the name pattern
	 * @param storeMap the store map
	 */
	public void after(String propName, String propValue, String namePattern, Map<String, String> storeMap) {
		if ( StringUtils.isNumeric(propValue) ) {
			String value = toBigMoney(Double.valueOf(propValue) / 100.00);
			if ( StringUtils.isNotBlank(namePattern) ) {
				storeMap.put(String.format(namePattern, propName+"CN"), value);
			} else {
				storeMap.put(propName+"CN", value);
			}
		}
	}
	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static String toBigMoney(double amount) {
		// 格式化金额数值（保留两位小数）
		String amt = nf.format(amount);
		// 整数位
		String intPart = "";
		// 小数位
		String dotPart = "";
		// 小数点位置
		int dotPos;
		if ((dotPos = amt.indexOf(".")) != -1) {
			// 存在小数点
			// 提取整数部分
			intPart = amt.substring(0, dotPos);
			// 提取小数部分
			dotPart = amt.substring(dotPos + 1);
		} else {
			// 不存在小数点
			intPart = amt;
		}
		// 数值过大抛出异常
		if (intPart.length() > 16) {
			throw new java.lang.Error("The Money is too big.");
		}
		// 转化整数部分为大写
		String intStr = intToBig(intPart);
		// 转化小数部分为大写
		String dotStr = dotToBig(dotPart);
		if (intStr.length() == 0 && dotStr.length() == 0) {
			return "零元";
		} else if (intStr.length() != 0 && dotStr.length() == 0) {
			return intStr + "元整";
		} else if (intStr.length() != 0 && dotStr.length() != 0) {
			return intStr + "元" + dotStr;
		} else {
			return dotStr;
		}
	}

	/**
	 * 将整数部分转化为大写
	 * @param intPart
	 * @return
	 */
	private static String intToBig(String intPart) {
		// 获取级长
		int grade = intPart.length() / GRADE;
		// 获取某级字符串
		String strTmp = "";
		// 获取转化为大写的结果
		StringBuffer sb=new StringBuffer();
		StringBuffer sbr=new StringBuffer();
		if (intPart.length() % GRADE != 0) {
			grade = grade + 1;
		}
		// 对每级数字处理，先处理最高级，然后再处理低级的
		for (int i = grade; i > 0; i--) {
			// 获取某级金额数值字符串
			strTmp = getNowGradeStr(intPart, i);
			// 转化为大写
			sb.append(changeToSub(strTmp));
			// 去除连续的零
			sbr.append(delZero(sb.toString()));
			if (i > 1) {
				if (changeToSub(strTmp).equals("零零零零")) {
					sbr.append("零");
				} else {
					sbr.append(GRADEUNIT.substring(i - 2, i - 1));
				}
			}
		}
		return sbr.toString();
	}

	/**
	 * 去除连续的零
	 * @param result
	 * @return
	 */
	private static String delZero(String result) {
		String strBefore = "";
		String strNow = "";
		String strResult = result.substring(0, 1);
		strBefore = strResult;
		StringBuffer sb=new StringBuffer();
		StringBuffer sbr=new StringBuffer();
		sb.append(strResult);
		for (int i = 1; i < result.length(); i++) {
			strNow = result.substring(i, i + 1);
			if (strBefore.equals("零") && strNow.equals("零")) {
				;
			} else {
				sb.append(strNow);
			}
			strBefore = strNow;
		}
		strResult=sb.toString();
		if (strResult.substring(strResult.length() - 1, strResult.length())
				.equals("零")) {
			strResult=strResult.substring(0, strResult.length() - 1);
		}
		// System.out.println(strResult+"g");
		return strResult;
	}

	/** 
	 * 获取转化后的大写结果
	 * @param strTmp
	 * @return
	 */
	private static String changeToSub(String strTmp) {
		StringBuffer sbr=new StringBuffer();
		int strLength = strTmp.length();
		for (int i = 0; i < strLength; i++) {
			int num = Integer.parseInt(String.valueOf(strTmp.charAt(i)));
			if (num == 0) {
				sbr.append("零");
			} else {
				sbr.append(UPPERNUM.substring(num, num + 1));
				if (i != strLength - 1) {
					sbr.append(UNIT.substring(strLength - 1 - i, strLength - i));
				}
			}
		}
		return sbr.toString();
	}

	/**
	 * 获取某级金额数值字符串
	 * @param intPart
	 * @param grade
	 * @return
	 */
	private static String getNowGradeStr(String intPart, int grade) {
		String rsStr = "";
		if (intPart.length() <= grade * GRADE) {
			rsStr = intPart
					.substring(0, intPart.length() - (grade - 1) * GRADE);
		} else {
			rsStr = intPart.substring(intPart.length() - grade * GRADE,
					intPart.length() - (grade - 1) * GRADE);
		}
		return rsStr;
	}

	/**
	 * 将小数部分转化为大写
	 * @param dotPart
	 * @return
	 */
	private static String dotToBig(String dotPart) {
		StringBuffer dotStr = new StringBuffer();
		for (int i = 0; i < dotPart.length(); i++) {
			int num = Integer.parseInt(dotPart.substring(i, i + 1));
			if (num != 0) {
				dotStr .append( UPPERNUM.substring(num, num + 1));
				dotStr .append( DECIMAL.substring(dotPart.length() - 2 + i,
								dotPart.length() + i - 1));
			}
		}
		return dotStr.toString();
	}
	public static void main(String args[]) {
		Double old=1004.03;
		System.out.println(toBigMoney(old));
	}
}