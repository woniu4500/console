package com.jiuyv.hhc.console.common.service;

import com.jiuyv.hhc.model.common.IMedia;

/**
 * 
 * @author 
 *
 */
public interface IMediaSupportService {

	/**
	 * 根据路径查询出对应的资源
	 * 
	 * 
	 * @param filePath
	 * @return
	 */
	IMedia findMedia(String filePath, String fileType);

}
