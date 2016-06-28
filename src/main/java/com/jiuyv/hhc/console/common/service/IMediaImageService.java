package com.jiuyv.hhc.console.common.service;

import java.io.File;

import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Interface IMediaImageService.
 *

 * @author 
 * @since 2014-3-11 23:29:10
 * @version 1.0.0
 */
public interface IMediaImageService {

	
	/**
	 * Do insert image.
	 * 
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 */
	CmMediaResVo doInsertImage(SysUserVo userVo, File file, String fileName) throws BaseException;
	
	/**
	 * Do insert file.
	 * 
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 */
	CmMediaResVo doInsertFile(SysUserVo userVo, File file, String fileName) throws BaseException;

	/**
	 * Do insert zipfile.
	 * 
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 */
	CmMediaResVo doInsertZipFile(SysUserVo userVo, File file, String fileName) throws BaseException;

	
	/**
	 * Do generate zip file.
	 * 生成压缩文件
	 * @param medUrls the med urls
	 * @param fileName the file name
	 * @return the cm media res vo
	 * @throws BaseException the base exception
	 */
	CmMediaResVo doGenerateZipFile(SysUserVo userVo, String medUrls, String fileName, String folder) throws BaseException;
}
