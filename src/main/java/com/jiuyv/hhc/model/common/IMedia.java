package com.jiuyv.hhc.model.common;

public interface IMedia {

	/**
	 * 获取返回类型(necessary for response)
	 * @return
	 */
	String getContentType();

	/**
	 * 获取编码格式(not necessary)
	 * @return
	 */
	String getEncode();

	/**
	 * 获取内容 (necessary for response)
	 * 不能返回null
	 * @return
	 */
	byte[] getContent();
	
}
