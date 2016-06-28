/**
 * 
 */
package com.jiuyv.common.encode;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
/*******************************************************************************
 * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
 ******************************************************************************/

public final class Md5 {
	private static Logger logger = LoggerFactory.getLogger(Md5.class);

	private static final int NUM_4 = 4;
	private static final int NUM_16 = 16;
	private static final int HEX_F0 = 0xF0;
	private static final int HEX_F = 0xF;
	private static final String MD5 = "MD5";
	private static final String ERROR_CHN = "异常类型:";
	private static final String LOAN = "youdaitong";

	private Md5() {

	}

	private static String getMD5LowerCase(byte[] source) {
		String s = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance(MD5);
			md.update(source);
			// MD5 的计算结果是一个 128 位的长整数，
			byte tmp[] = md.digest(); 
			// 用字节表示就是 16 个字节
			// 每个字节用 16 进制表示的话，使用两个字符，
			char str[] = new char[NUM_16 * 2]; 
			// 所以表示成 16 进制需要 32 个字符
			// 表示转换结果中对应的字符位置
			int k = 0; 
			// 从第一个字节开始，对 MD5 的每一个字节
			for (int i = 0; i < NUM_16; i++) { 
				// 转换成 16 进制字符的转换
				// 取第 i 个字节
				byte byte0 = tmp[i]; 
				// 取字节中高 4 位的数字转换,
				str[k++] = hexDigits[byte0 >>> NUM_4 & HEX_F]; 
				// >>> 为逻辑右移，将符号位一起右移
				// 取字节中低 4 位的数字转换
				str[k++] = hexDigits[byte0 & HEX_F]; 
			}
			// 换后的结果转换为字符串
			s = new String(str); 

		} catch (Exception e) {
			logger.error(ERROR_CHN + e.getMessage(), e);
		}
		return s;
	}

	private static String getMD5UpperCase(byte[] source) {
		String s = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance(MD5);
			md.update(source);
			// MD5 的计算结果是一个 128 位的长整数，
			byte tmp[] = md.digest(); 
			// 用字节表示就是 16 个字节
			// 每个字节用 16 进制表示的话，使用两个字符，
			char str[] = new char[NUM_16 * 2]; 
			// 所以表示成 16 进制需要 32 个字符
			// 表示转换结果中对应的字符位置
			int k = 0; 
			// 从第一个字节开始，对 MD5 的每一个字节
			for (int i = 0; i < NUM_16; i++) { 
				// 转换成 16 进制字符的转换
				// 取第 i 个字节
				byte byte0 = tmp[i]; 
				// 取字节中高 4 位的数字转换,
				str[k++] = hexDigits[byte0 >>> NUM_4 & HEX_F]; 
				// >>> 为逻辑右移，将符号位一起右移
				// 取字节中低 4 位的数字转换
				str[k++] = hexDigits[byte0 & HEX_F]; 
			}
			// 换后的结果转换为字符串
			s = new String(str); 

		} catch (Exception e) {
			logger.error(ERROR_CHN + e.getMessage(),e );
		}
		return s;
	}

	/**
	 * 获取MD5值，小写
	 * 
	 * @author 张斌
	 * @param str
	 * @return
	 */
	public static String getMD5ofStrByLowerCase(String str) {
		String reStr = null;
		try {
			reStr = getMD5LowerCase(str.getBytes("GB18030"));
		} catch (Exception e) {
			logger.error(ERROR_CHN + e.getMessage(),e);
		}

		return reStr;
	}

	/**
	 * 获取MD5值，大写
	 * 
	 * @author 张斌
	 * @param str
	 * @return
	 */
	public static String getMD5ofStrByUpperCase(String str) {
		String reStr = null;

		try {
			reStr = getMD5UpperCase(str.getBytes("GB18030"));
		} catch (Exception e) {
			logger.error(ERROR_CHN + e.getMessage(),e);
		}
		return reStr;
	}

	/**
	 * 获取MD5 加密大写 密文
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encodeMD5Hex(String text) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5);
		md.update(text.getBytes("utf-8"));
		byte[] digest = md.digest();
		StringBuffer md5 = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			md5.append(Character.forDigit((digest[i] & HEX_F0) >> NUM_4, NUM_16));
			md5.append(Character.forDigit((digest[i] & HEX_F), NUM_16));
		}
		return md5.toString().toUpperCase();
	}

	public static String encodeMD5(String text) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5);
		md.update(text.getBytes("utf-8"));
		byte[] digest = md.digest();
		StringBuffer md5 = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			md5.append(Character.forDigit((digest[i] & HEX_F0) >> NUM_4, NUM_16));
			md5.append(Character.forDigit((digest[i] & HEX_F), NUM_16));
		}
		return md5.toString();
	}
	
	public static String encodeLoanPassMD5(String str) throws Exception{
		return encodeMD5(encodeMD5(str)+LOAN);
	}

}
