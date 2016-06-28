package com.jiuyv.common.encode;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin
 * 
 */
public abstract class DES {

	private static final Logger LOG = LoggerFactory.getLogger(DES.class);
	/** DES Key */
	private static final String KEY = "huateng0x00";
	
	private static final String ENCODE = "UTF-8";
	private static final String FORMATION = "DES";

	/**
	 * 
	 * Encrypt DES. 
	 * 初始化key信息
	 * 
	 * @param cipher
	 * @param strkey
	 * @return
	 * @throws Exception
	 */
	private static Key initEncrypt(String strkey) throws Exception {
		byte[] secretKey = strkey.getBytes(ENCODE);
		SecretKeyFactory kf = SecretKeyFactory.getInstance(FORMATION);
		DESKeySpec keySpec = new DESKeySpec(secretKey);
		return kf.generateSecret(keySpec);
	}

	/**
	 * 解密内容
	 * @param obj
	 * @return
	 */
	public static String decrypt(String obj) {
		String resultStr = null;
		if (obj != null) {
			try {
				Cipher cipher = Cipher.getInstance(FORMATION);
				cipher.init(Cipher.DECRYPT_MODE, initEncrypt(KEY));
				Base64 base64 = new Base64();
				byte[] objByte = base64.decode(obj.getBytes(ENCODE));
				byte[] result = cipher.doFinal(objByte);
				resultStr = new String(result, ENCODE);
			} catch (Exception e) {
				LOG.error("DES decrypt error. ", e);
				return null;
			}
			return resultStr;
		}
		return resultStr;
	}

	/**
	 * 加密内容
	 * @param obj
	 * @return
	 */
	public static String encrypt(String obj) {
		String resultStr = null;
		if (obj != null) {
			try {
				Cipher cipher = Cipher.getInstance(FORMATION);
				cipher.init(Cipher.ENCRYPT_MODE, initEncrypt(KEY));
				Base64 base64 = new Base64();
				byte[] objByte = obj.getBytes(ENCODE);
				byte[] result = cipher.doFinal(objByte);
				resultStr = new String(base64.encode(result), ENCODE);
				if (resultStr != null && resultStr.lastIndexOf("\r\n") != -1) {
					resultStr = resultStr.substring(0, resultStr.lastIndexOf("\r\n"));
				} else {
					resultStr = "";
				}
			} catch (Exception e) {
				LOG.error("DES encrypt error. ", e);
				return resultStr;
			}
		}
		return resultStr;
	}

}
