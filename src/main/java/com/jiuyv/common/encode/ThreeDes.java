package com.jiuyv.common.encode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-2-16
 * Time: 14:33:57
 * To change this template use File | Settings | File Templates.
 */
/*
 字符串 DESede(3DES) 加密
 */
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreeDes {
	/**
	 * private constructor
	 */
	private ThreeDes() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(ThreeDes.class);

	public static final String LPAD_STR = "0";
	public static final int STR_LENGTH = 6;
	public static final int RANDOM_BASE_INT = 1000000;

	private static final String ERROR_LOG = "encrypt error,message {}";

	private static Random random = new Random();
	
	/**
	 * 定义 加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String ALGORITHM = "DESede";

	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);

			// 加密
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
			LOG.error(ERROR_LOG, e);
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, ALGORITHM);
			// 解密
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e) {
			LOG.error(ERROR_LOG, e);
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer("");
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {

				hs.append("0");
				hs.append(stmp);
			} else {
				hs.append(stmp);
			}

		}
		return hs.toString().toUpperCase();
	}

	public static String genRandomSixPwd() {
		String value = String.valueOf(random.nextInt(RANDOM_BASE_INT));
		return lpadStr(value, null, STR_LENGTH);
	}

	public static String lpadStr(String str, String lpad, int length) {
		String lpadtemp = null;
		if (null == str || str.trim().length() == 0) {
			return null;
		}
		if (null == lpad || lpad.trim().length() == 0) {
			lpadtemp = LPAD_STR;
		} else {
			lpadtemp = lpad;
		}
		StringBuffer sb = new StringBuffer();
		int strLength = str.length();
		if (strLength >= length) {
			return str;
		} else {
			for (int i = 0; i < length - strLength; i++) {
				sb.append(lpadtemp);
			}
			return sb.append(str).toString();
		}
	}
}
