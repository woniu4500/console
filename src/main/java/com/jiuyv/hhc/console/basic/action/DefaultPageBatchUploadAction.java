package com.jiuyv.hhc.console.basic.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.excel.HSSFXLSReader;
import com.jiuyv.common.txt.TextResolver;

/**
 * 批量上传页面基本action.
 *

 * @author 
 * @since 2014-5-26 14:35:25
 * @version 1.0.0
 */
public abstract class DefaultPageBatchUploadAction extends
		DefaultPageSupportAction {

	/** 0: 不排序. */
	public static final int SORT_MODE_UNSORT = 0;
	
	/** 1: 有效数据靠前 */
	public static final int SORT_MODE_VALID_FORWARD = 1;
	
	/** 2: 有效数据靠后. */
	public static final int SORT_MODE_VALID_REARWARD = 2;
	
	/** EXCEL. */
	public static final String FILE_TYPE_EXCEL = "excel";
	
	/** TXT. */
	public static final String FILE_TYPE_TXT = "txt";

	/** 上传文件名. */
	private String uploadFileName;
	
	/** 上传的文件. */
	private File upload;

	// ==========================可配置数据=========================
	/** Excel文件中是否第一行为标题. */
	private boolean headTitleExisted = true;
	
	/** 无效数据是否靠前. */
	private int sortMode = SORT_MODE_VALID_REARWARD;

	/**
	 * 获取 上传文件名.
	 *
	 * @return the 上传文件名
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * 设置 上传文件名.
	 *
	 * @param uploadFileName the new 上传文件名
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * 获取 无效数据是否靠前.
	 *
	 * @return the 无效数据是否靠前
	 */
	public int getSortMode() {
		return sortMode;
	}

	/**
	 * 设置 无效数据是否靠前.
	 *
	 * @param sortMode the new 无效数据是否靠前
	 */
	public void setSortMode(int sortMode) {
		this.sortMode = sortMode;
	}

	/**
	 * 获取 上传的文件.
	 *
	 * @return the 上传的文件
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * 设置 上传的文件.
	 *
	 * @param upload the new 上传的文件
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * 是(否) excel文件中是否第一行为标题.
	 *
	 * @return the excel文件中是否第一行为标题
	 */
	public boolean isHeadTitleExisted() {
		return headTitleExisted;
	}

	/**
	 * 设置 excel文件中是否第一行为标题.
	 *
	 * @param headTitleExisted the new excel文件中是否第一行为标题
	 */
	public void setHeadTitleExisted(boolean headTitleExisted) {
		this.headTitleExisted = headTitleExisted;
	}

	/**
	 * 根据sortMode对List<? extends BatchUploadBaseVo>进行排序.
	 *
	 * @param list the list
	 * @return the list<? extends batch upload base vo>
	
	 * @author cowyk
	 * @since 2014-5-26 14:35:22
	 * @version 1.0.0
	 */
	protected final List<? extends BatchUploadBaseVo> sortList(
			List<? extends BatchUploadBaseVo> list) {
		if (sortMode != SORT_MODE_UNSORT) {
			List<BatchUploadBaseVo> validList = new ArrayList<BatchUploadBaseVo>();
			List<BatchUploadBaseVo> inValidList = new ArrayList<BatchUploadBaseVo>();
			for (BatchUploadBaseVo batchUploadBaseVo : list) {
				if (batchUploadBaseVo.isValid()) {
					validList.add(batchUploadBaseVo);
				} else {
					inValidList.add(batchUploadBaseVo);
				}
			}
			if (sortMode == SORT_MODE_VALID_FORWARD) {
				validList.addAll(inValidList);
				return validList;
			} else if (sortMode == SORT_MODE_VALID_REARWARD) {
				inValidList.addAll(validList);
				return inValidList;
			}
		}
		return list;
	}

	/**
	 * 将文件内容解析为List<ArrayList<String>>.
	 *
	 * @param fileType the file type
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<ArrayList<String>> resolveFile(String fileType)
			throws Exception {
		List<ArrayList<String>> dataLists;
		if (fileType.equals(FILE_TYPE_EXCEL)) {
			dataLists = HSSFXLSReader.inputXls(getUpload()).getList();
		} else if (fileType.equals(FILE_TYPE_TXT)) {
			dataLists = TextResolver.resolveTextFile(getUpload());
		} else {
			throw new BaseException("", "解析模式错误");
		}
		return dataLists;
	}

	/**
	 * 处理数据，去掉不必要的数据.
	 *
	 * @param dataList the data list
	 * @param fType 文件类型
	 * @throws Exception the exception
	 */
	private void doDataFilter(List<ArrayList<String>> dataList, String fType)
			throws Exception {
		// 如果第一行是标题的话，去掉
		if (isHeadTitleExisted()) {
			dataList.remove(0);
		}
		// 去掉空行
		int index = dataList.size() - 1;
		while (index >= 0) {
			if (dataList.get(index).size() == 0) {
				dataList.remove(index);
			}
			index--;
		}
		doOtherDataFilter(dataList, fType);

	}

	/**
	 * 解析上传文件数据.
	 *
	 * @param fileType 文件类型 excel or txt
	 * @return the ext data
	 * @throws Exception the exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final ExtData resloveFileData(String fileType) throws Exception {
		// User user = getUserInfo();
		List<? extends BatchUploadBaseVo> list = null;
		ExtData data = new ExtData();
		data.setSuccess(false);
		if (getUpload() == null) {
			data.setSyserr("文件不能为空");
			return data;
		}
		List<ArrayList<String>> dataLists = null;
		try {
			dataLists = resolveFile(fileType);
		} catch (Exception e) {
			data.setSyserr("文件读取失败");
			return data;
		}
		try {
			doDataFilter(dataLists, fileType);
		} catch (Exception e) {
			data.setSyserr("数据过滤失败");
			return data;
		}
		try {
			list = checkData(dataLists, fileType);
		} catch (BaseException e) {
			data.setSyserr(e.getMessage());
			return data;
		} catch (Exception e) {
			data.setSyserr("数据验证失败");
			return data;
		}
		data.setRoot(list);
		data.setTotalProperty(list.size());
		data.setSuccess(true);
		return data;
	}

	/**
	 * 返回上传文件大小.
	 *
	 * @param f the f
	 * @return the file sizes
	 * @throws Exception the exception
	 */
	protected long getFileSizes(File f) throws Exception {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			try{
			s = fis.available();
			}finally{
			fis.close();
			}
		}
		return s;
	}

	/**
	 * 检查数据格式，并将数据处理.
	 *
	 * @param dataLists the data lists
	 * @param fileType the file type
	 * @return the list<? extends batch upload base vo>
	 * @throws Exception the exception
	
	 * @author cowyk
	 * @since 2014-5-26 14:35:22
	 * @version 1.0.0
	 */
	private List<? extends BatchUploadBaseVo> checkData(
			List<ArrayList<String>> dataLists, String fileType)
			throws Exception {
		if (dataLists.size() == 0) {
			throw new BaseException("", "文件错误，请检查上传文件");
		}
		return sortList((List<? extends BatchUploadBaseVo>) getDataVoList(
				dataLists, fileType));
	}

	/**
	 * 其他数据过滤处理.
	 *
	 * @param dataList the data list
	 * @param fType the f type
	 * @throws Exception the exception
	 */
	protected void doOtherDataFilter(List<ArrayList<String>> dataList,
			String fType) throws Exception {
		// 默认不处理，可覆盖
	}

	/**
	 * 自定义具体数据解析方式.
	 *
	 * @param dataLists the data lists
	 * @param fileType the file type
	 * @return the data vo list
	 * @throws Exception the exception
	
	 * @author cowyk
	 * @since 2014-5-26 14:35:22
	 * @version 1.0.0
	 */
	protected abstract List<? extends BatchUploadBaseVo> getDataVoList(
			List<ArrayList<String>> dataLists, String fileType)
			throws Exception;

}
