package com.jiuyv.hhc.console.common.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.common.SysParamVo;

/**
 * The Class SysParamAction.
 * 系统参数Action
 * 

 * @author 
 * @since 2014-2-20 17:02:52
 * @version 1.0.0
 */
public class SysParamAction extends DefaultPageSupportAction {

	/** 系统参数Service */
	private ISysParamService sysParamService ; 
	/** 参数文件名 */
	private static final String SYS_PARAM_FN = "系统参数";
	/** 参数列 */
	private static List<CellDataType> sysParamCol = new ArrayList<CellDataType>(); 
	
	/** 图片处理Service. */
	private IMediaImageService mediaImageService ; 

	/** 上传文件. */
	private File uploadFile ; 
	
	/** 上传文件名. */
	private String uploadFileName ;
	
	private String paramCode;
	
	private String paramValue;
	
	/**
	 * 查询系统参数
	 * @return
	 * @throws Exception
	 */
	public String findSysParamList() throws Exception {
		return resultData(sysParamService.findSysParamList(getFilters(), getUserInfo()));
	}
	
	/**
	 * 获取生成二维码字符串
	 * */
	public String selectQrcodeText()throws Exception{
		SysParamVo paramVo = getValidateBean(SysParamVo.class);
		return resultData(sysParamService.selectQrcodeText(paramVo));
	}
	
	/**
	 * 导出系统参数
	 * @return
	 * @throws Exception
	 */
	public String findSysParamExcel() throws Exception {
		return defaultExportXLS(SYS_PARAM_FN, sysParamCol, sysParamService.findSysParamList(getFilters(), getUserInfo()));
	}
	
	/**
	 * 更新系统参数
	 * @return
	 * @throws Exception
	 */
	public String updateSysParam() throws Exception {
		ExtData<SysParamVo> data = sysParamService.doUpdateSysParam(getFilters(), getUserInfo(), getValidateList(SysParamVo.class));
		refreshApplicationParams(data.getRoot());
		return resultData(data);
	}
	
	/**
	 * 加载主题
	 * 
	 * */
	public String loadTheme() throws Exception{
		ExtData<SysParamVo> data = sysParamService.loadTheme();
		refreshApplicationParams(data.getRoot());
		return resultData(data);
	}
	
	/**
	 * 更新系统配置的环境变量
	 * @param params
	 */
	private void refreshApplicationParams (List<SysParamVo> params) {
		if ( params == null ) {
			return ; 
		}
		ServletContext application = ServletActionContext.getServletContext();
		for ( SysParamVo p : params ) {
			application.setAttribute(p.getParamCode(), p.getParamValue());
		}
	}
	
	/**
	 * 更新主题信息
	 * 
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateTheme()throws Exception{
		SysParamVo paramVo = new SysParamVo();
		paramVo.setParamCode(paramCode);
		paramVo.setParamValue(paramValue);
		if(paramVo.getParamCode().contains("img")){
			CmMediaResVo mediaVo = mediaImageService.doInsertImage(getUserInfo(), uploadFile, uploadFileName);
			String medPath = mediaVo.getMedPath();
			if(medPath.startsWith("/")){
				paramVo.setParamValue(medPath.replaceFirst("\\/", " "));
			}
		}
		ExtData<SysParamVo> data = sysParamService.updateTheme(paramVo,getUserInfo());
		refreshApplicationParams(data.getRoot());
		return resultData(data);
	}
	
	/**
	 * 上传图片
	 * 
	 * @return the string
	 * @throws Exception the exception
	 */
	public String uploadImg()throws Exception{
		CmMediaResVo mediaVo = mediaImageService.doInsertImage(getUserInfo(), uploadFile, uploadFileName);
			String medPath = mediaVo.getMedPath();
			if(medPath.startsWith("/")){
				mediaVo.setMedPath(medPath.replaceFirst("\\/", " "));
			}
		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);
		return resultData(data);
	}
	
	/**
	 * 上传文件
	 * 
	 * @return the string
	 * @throws Exception the exception
	 */
	public String uploadFile()throws Exception{
		CmMediaResVo mediaVo = mediaImageService.doInsertFile(getUserInfo(), uploadFile, uploadFileName);
		String medPath = mediaVo.getMedPath();
		if(medPath.startsWith("/")){
			mediaVo.setMedPath(medPath.replaceFirst("\\/", " "));
		}
		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);
		return resultData(data);
	}
	
	/**
	 * 生成压缩文件
	 * @return
	 * @throws Exception
	 */
	public String genZipFile() throws Exception {
		String medUrls = getParameter("medUrls");
		String fileName = getParameter("fileName");
		CmMediaResVo mediaVo = mediaImageService.doGenerateZipFile(getUserInfo(), medUrls, fileName, ServletActionContext.getRequest().getSession().getId());
		String medPath = mediaVo.getMedPath();
		if(medPath.startsWith("/")){
			mediaVo.setMedPath(medPath.replaceFirst("\\/", ""));
		}
		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);
		return resultData(data);
	}
	
	/**
	 * 设置 系统参数Service.
	 * @param sysParamService the new 系统参数Service
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public void setMediaImageService(IMediaImageService mediaImageService) {
		this.mediaImageService = mediaImageService;
	}
	
}
