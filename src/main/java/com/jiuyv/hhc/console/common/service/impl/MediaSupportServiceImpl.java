package com.jiuyv.hhc.console.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.image.ImageUtil;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.common.service.IMediaSupportService;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.common.IMedia;
import com.jiuyv.hhc.model.common.dao.CmMediaResDao;
import com.jiuyv.hhc.model.security.SysUserVo;

/**
 * The Class MediaSupportServiceImpl.
 *

 * @author 
 * @since 2014-3-11 23:20:12
 * @version 1.0.0
 */
public class MediaSupportServiceImpl implements IMediaSupportService,IMediaImageService {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaSupportServiceImpl.class);

	/** mediaDao. */
	private CmMediaResDao cmMediaResDao;
	
	/** 目录前缀 */
	private static final String DIR_PREFIX = "/upload/";
	
	/** 日期格式化. */
	private static final String DATE_FORMAT = "yyyyMM";

	/** 文件名编码格式 */
	private static final String NAME_ENCODE = "UTF-8";//"ISO-8859-1"
	
	/** 默认缩放宽度 (尺寸过小在某些比例的缩略图中会出现黑边) */
	private static final Integer ZOOM_WIDTH  = 140;
	
	/** 默认缩放高度 (尺寸过小在某些比例的缩略图中会出现黑边) */
	private static final Integer ZOOM_HEIGHT = 140;
	/**  缓存大小 */
	public static final int BUFFER = 1024 ;    
	
	/** MIME类型. */
	private static Map<String,String> mimeTypeMap = new HashMap<String, String>();
	/** 格式转换类型 */
	private static Map<String,String> formatTypeMap = new HashMap<String, String>();
	
	static {
		mimeTypeMap.put(".bmp", "image/bmp");
		mimeTypeMap.put(".gif", "image/gif");
		mimeTypeMap.put(".jpeg", "image/jpeg");
		mimeTypeMap.put(".jpg", "image/jpeg");
		mimeTypeMap.put(".png", "image/png");
		mimeTypeMap.put(".apk", "application/vnd.android");
		mimeTypeMap.put(".zip", "application/zip");
		
		formatTypeMap.put(".bmp", "BMP");
		formatTypeMap.put(".gif", "GIF");
		formatTypeMap.put(".jpeg","JPEG");
		formatTypeMap.put(".jpg", "JPEG");
		formatTypeMap.put(".png", "PNG");
		formatTypeMap.put(".apk", "APK");
		formatTypeMap.put(".zip", "ZIP");
	}
	
	/**
	 * Find media.
	 *
	 * @param filePath the file path
	 * @param fileType the file type
	 * @return the i media
	 * @see com.jiuyv.hhc.console.common.service.IMediaSupportService#findMedia(java.lang.String, java.lang.String)
	 */
	public IMedia findMedia(String filePath, String fileType) {
		if (StringUtils.equalsIgnoreCase("thumbnail", fileType)) {
			return cmMediaResDao.findThumbByPath(filePath);
		} else {
			return cmMediaResDao.findContentByPath(filePath);
		}
	}

	/**
	 * Do insert image.
	 *
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.common.service.IMediaImageService#doInsertImage(com.jiuyv.hhc.model.security.SysUserVo, java.io.File, java.lang.String)
	 */
	public  CmMediaResVo doInsertImage(SysUserVo userVo, File file, String xfileName)
			throws BaseException {
		CmMediaResVo mediaVo = new  CmMediaResVo();
		mediaVo.setRecCrtAcc(userVo.getLoginId());
		String medDir = DIR_PREFIX + new SimpleDateFormat(DATE_FORMAT).format(new Date()) + "/" + UUID.randomUUID() + "/";
		String neFileName = StringUtils.defaultString(xfileName,"pic.jpg");
		String fileName = neFileName.indexOf("\\")>0?neFileName.substring(neFileName.lastIndexOf("\\")+1):neFileName;
		fileName = fileName.indexOf("/")>0?fileName.substring(fileName.lastIndexOf("/")+1):fileName;
		String extName = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
		if(!extName.matches("\\.(png|jpg|bmp|gif|jpeg)")) {
			throw new BaseException("", "请上传格式为jpg、png、jpeg、bmp、gif的图片");
		}
		// set image content
		FileInputStream fis = null;
		try {
			// set fileName
			String oriName = fileName.substring(0, fileName.lastIndexOf('.'));
			oriName = oriName.length() > 160 ? oriName.substring(0, 160):oriName;
			String medName = URLEncoder.encode( oriName, NAME_ENCODE)  + extName;
			mediaVo.setMedName(medName);
			mediaVo.setMedDir(medDir);
			mediaVo.setMedPath(mediaVo.getMedDir() + mediaVo.getMedName());
			mediaVo.setMimeType(mimeTypeMap.get(extName));
			fis = new FileInputStream(file);
			ByteArrayOutputStream thumbnailsByteStream = new ByteArrayOutputStream();
			BufferedImage bufImg = ImageUtil.readImage(fis);
			ImageUtil.zoomFixed(bufImg, thumbnailsByteStream, formatTypeMap.get(extName),ZOOM_HEIGHT, ZOOM_WIDTH, false, null);
			mediaVo.setMedThumb(thumbnailsByteStream.toByteArray());
			mediaVo.setMedContent(ImageUtil.toByteArray(bufImg, formatTypeMap.get(extName)));		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close file
			try {
				if ( fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.delete();
		}
		cmMediaResDao.insert(mediaVo);
		return mediaVo;
	}

	/**
	 * Do insert image.
	 *
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.common.service.IMediaImageService#doInsertImage(com.jiuyv.hhc.model.security.SysUserVo, java.io.File, java.lang.String)
	 */
	public  CmMediaResVo doInsertFile(SysUserVo userVo, File file, String xfileName)
			throws BaseException {
		 CmMediaResVo mediaVo = new  CmMediaResVo();
		mediaVo.setRecCrtAcc(userVo.getLoginId());
		String medDir = DIR_PREFIX + new SimpleDateFormat(DATE_FORMAT).format(new Date()) + "/" + UUID.randomUUID() + "/";
		String neFileName = StringUtils.defaultString(xfileName,"android.apk");
		String fileName = neFileName.indexOf("\\")>0?neFileName.substring(neFileName.lastIndexOf("\\")+1):neFileName;
		fileName = fileName.indexOf("/")>0?fileName.substring(fileName.lastIndexOf("/")+1):fileName;
		String extName = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
		if(!extName.matches("\\.(apk|zip)") ) {
			throw new BaseException("", "请上传格式为apk文件或zip压缩包");
		}
		// set image content
		FileInputStream fis = null;
		try {
			// set fileName
			String oriName = fileName.substring(0, fileName.lastIndexOf('.'));
			oriName = oriName.length() > 160 ? oriName.substring(0, 160):oriName;
			String medName = URLEncoder.encode( oriName, NAME_ENCODE)  + extName;
			mediaVo.setMedName(medName);
			mediaVo.setMedDir(medDir);
			mediaVo.setMedPath(mediaVo.getMedDir() + mediaVo.getMedName());
			mediaVo.setMimeType(mimeTypeMap.get(extName));
			fis = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(fis.available());  
            byte[] b = new byte[fis.available()];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                out.write(b, 0, n);  
            }  
            fis.close();  
            out.close();  
			mediaVo.setMedContent(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close file
			try {
				if ( fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.delete();
		}
		cmMediaResDao.insert(mediaVo);
		return mediaVo;
	}

	/**
	 * Do insert image.
	 *
	 * @param userVo the user vo
	 * @param file the file
	 * @param fileName the file name
	 * @return the res media vo
	 * @throws BaseException the base exception
	 * @see com.jiuyv.hhc.console.common.service.IMediaImageService#doInsertImage(com.jiuyv.hhc.model.security.SysUserVo, java.io.File, java.lang.String)
	 */
	public  CmMediaResVo doInsertZipFile(SysUserVo userVo, File file, String xfileName)
			throws BaseException {
		CmMediaResVo mediaVo = new  CmMediaResVo();
		mediaVo.setRecCrtAcc(userVo.getLoginId());
		String medDir = DIR_PREFIX + new SimpleDateFormat(DATE_FORMAT).format(new Date()) + "/" + UUID.randomUUID() +"/" ;
		String neFileName = StringUtils.defaultString(xfileName,"mchnt.zip");
		String fileName = neFileName.indexOf("\\")>0?neFileName.substring(neFileName.lastIndexOf("\\")+1):neFileName;
		fileName = fileName.indexOf("/")>0?fileName.substring(fileName.lastIndexOf("/")+1):fileName;
		String extName = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
		if(!extName.matches("\\.(zip)") ) {
			throw new BaseException("", "请上传zip压缩包");
		}
		// set image content
		FileInputStream fis = null;
		try {
			// set fileName
			String oriName = fileName.substring(0, fileName.lastIndexOf('.'));
			oriName = oriName.length() > 26 ? oriName.substring(0, 26):oriName;
			String medName = URLEncoder.encode( oriName, NAME_ENCODE)  + extName;
			mediaVo.setMedName(medName);
			mediaVo.setMedDir(medDir);
			mediaVo.setMedPath(mediaVo.getMedDir() + mediaVo.getMedName());
			mediaVo.setMimeType(mimeTypeMap.get(extName));
			fis = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(fis.available());  
			byte[] b = new byte[fis.available()];  
			int n;  
			while ((n = fis.read(b)) != -1) {  
				out.write(b, 0, n);  
			}  
			fis.close();  
			out.close();  
			mediaVo.setMedContent(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close file
			try {
				if ( fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			file.delete();
		}
		cmMediaResDao.insert(mediaVo);
		return mediaVo;
	}
	
	/**
	 * Do generate zip file.
	 * 生成压缩文件
	 * @param medUrls the med urls
	 * @param fileName the file name
	 * @return the cm media res vo
	 * @throws BaseException the base exception
	 */
	public CmMediaResVo doGenerateZipFile(SysUserVo userVo, String medUrls, String fileName, String folder) throws BaseException {
		String baseDir = System.getProperty("java.io.tmpdir")  + folder + "/"; 
		File dir = new File(baseDir);
		dir.mkdirs();
		// 处理文件名，增加扩展名zip
		String zipFileName = fileName ;
		if ( zipFileName.contains(".") ) {
			String extName = zipFileName.substring(zipFileName.lastIndexOf('.')).toLowerCase();
			if(!extName.matches("\\.(zip)") ) {
				zipFileName = zipFileName + ".zip";
			}
		} else {
			zipFileName = zipFileName + ".zip";
		}
		// 生成zip文件
		// 对页面回传的链接进行预处理
		String[] urls = medUrls.split(",");
		Map<String,Integer> nameCount = new HashMap<String, Integer>();
		for ( String url: urls) {
			url = url.trim();
			if(!url.startsWith("/")){ 
				url = "/"+url;
			}
			// 查询出图片文件，并写到临时目录下
			CmMediaResVo med = cmMediaResDao.findContentByPath(url);
			FileOutputStream fos = null;
			try {
				String medName = med.getMedName();
				Integer count = nameCount.get(medName);
				if ( count == null) {
					nameCount.put(medName, 1);
				} else {
					Integer no = count+1;
					nameCount.put(medName, no);
					if ( medName.contains(".") ) {
						String oriName = medName.substring(0, medName.lastIndexOf('.'));
						String extName = medName.substring(medName.lastIndexOf('.'));
						medName = oriName + '(' + no + ')' + extName; 
					} else {
						medName = medName + '(' + no + ')';
					}
				}
				File medFile = new File( dir, medName );
				fos = new FileOutputStream( medFile );
				fos.write(med.getMedContent());
			} catch (Exception e) {
				LOGGER.error("Error write file:" + url, e);
			} finally {
				if ( fos != null ) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						LOGGER.error("Error close stream", e);
					}
				}
			}
		}
		try {
			zipfile(baseDir, baseDir + zipFileName);
		} catch (Exception e) {
			throw new BaseException("","创建压缩文件失败", e);
		}
		CmMediaResVo res = insertFile(zipFileName, new File(baseDir, zipFileName), userVo.getLoginId());
		try {
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			LOGGER.error("删除临时目录出错", e);
		}
		return res;
	}
	
	
	private CmMediaResVo insertFile(String fileName, File zipfile, String loginId) {
		CmMediaResVo mediaVo = new  CmMediaResVo();
		mediaVo.setRecCrtAcc(loginId);
		String medDir = DIR_PREFIX + new SimpleDateFormat(DATE_FORMAT).format(new Date()) + "/" + UUID.randomUUID() + "/";
		String extName = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
		// set image content
		FileInputStream fis = null;
		try {
			// set fileName
			String oriName = fileName.substring(0, fileName.lastIndexOf('.'));
			oriName = oriName.length() > 160 ? oriName.substring(0, 160):oriName;
			String medName = URLEncoder.encode( oriName, NAME_ENCODE)  + extName;
			mediaVo.setMedName(medName);
			mediaVo.setMedDir(medDir);
			mediaVo.setMedPath(mediaVo.getMedDir() + mediaVo.getMedName());
			mediaVo.setMimeType(mimeTypeMap.get(extName));
			fis = new FileInputStream(zipfile);
			ByteArrayOutputStream out = new ByteArrayOutputStream(fis.available());  
            byte[] b = new byte[fis.available()];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                out.write(b, 0, n);  
            }  
            fis.close();  
            out.close();  
			mediaVo.setMedContent(out.toByteArray());
		} catch (IOException e) {
			LOGGER.error("Error save media file", e);
		} finally {
			// close file
			try {
				if ( fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error close stream", e);
			}
			zipfile.delete();
		}
		cmMediaResDao.insert(mediaVo);
		return mediaVo;
	}
	
	/**
	 * 压缩文件
	 * @param baseDir
	 * @param fileName
	 * @throws Exception
	 */
	private static void zipfile (String baseDir, String fileName) throws Exception {
		File dir = new File(baseDir);
		File[] files = dir.listFiles();
		ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(fileName));  
        ZipEntry ze=null;  
        byte[] buf=new byte[BUFFER];  
        int readLen=0;  
        for(int i = 0; i < files.length; i++) {  
            File f= files[i];
            if ( !f.isFile() ) {
            	continue;
            }
            ze = new ZipEntry(f.getName());  
            ze.setSize(f.length());  
            ze.setTime(f.lastModified());     
            zos.putNextEntry(ze);  
            InputStream is=new BufferedInputStream(new FileInputStream(f));  
            while ((readLen=is.read(buf, 0, BUFFER))!=-1) {  
                zos.write(buf, 0, readLen);  
                zos.flush();
            }  
            is.close();  
        }  
        zos.close();
	}
	
	
	/**
	 * 设置 mediaDao.
	 *
	 * @param CmMediaResDao the new mediaDao
	 */
	public void setCmMediaResDao(CmMediaResDao cmMediaResDao) {
		this.cmMediaResDao = cmMediaResDao;
	}

}
