package com.jiuyv.hhc.console.customer.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.httpclient.HttpClientUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.customer.service.IMchntService;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.customer.MhtBaseInfoVo;

/**
 * 机构信息的增、删、改、查.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
@Deprecated
public class MchntAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** The Service. */
	private IMchntService service;
	
	private ISysParamService sysParamService ; 
	
	/** 上传文件. */
	private File uploadFile ; 
	
	/** 上传文件名. */
	private String uploadFileName ;
	
	/** 图片处理Service. */
	private IMediaImageService mediaImageService ; 

	/** The OR g_ list. */
	private static List<CellDataType> memberList = new ArrayList<CellDataType>();
	static {
		memberList.add(new CellDataType("licNo", "营业执照号码"));
		memberList.add(new CellDataType("customerCode", "会员代码"));
		memberList.add(new CellDataType("mchntCd", "商户代码"));
		memberList.add(new CellDataType("reveRegCd", "税务登记代码"));
		memberList.add(new CellDataType("mchntCnName", "商户中文名称"));
		memberList.add(new CellDataType("regAddr", "注册地址"));
		memberList.add(new CellDataType("artifNm", "法人代表姓名"));
		memberList.add(new CellDataType("artifCertifId", "法人代表证件号"));
		memberList.add(new CellDataType("mchntTp", "商户类型"));
		memberList.add(new CellDataType("mchntGrp", "商户组别"));
		memberList.add(new CellDataType("phone", "联系电话"));
		memberList.add(new CellDataType("mchntCrtDt", "商户入网时间"));
		memberList.add(new CellDataType("posNum", "装机数量"));
		memberList.add(new CellDataType("mchntStDesc", "商户状态"));
		memberList.add(new CellDataType("respInfo", "查询响应"));
		memberList.add(new CellDataType("recAcc", "提交用户"));
		memberList.add(new CellDataType("recTime", "录入时间", CellDataRender.time));
		memberList.add(new CellDataType("auditTime", "提交资质审核时间", CellDataRender.time));
		memberList.add(new CellDataType("beforeTime", "获取贷前数据时间", CellDataRender.time));
	}
	
	/**
	 * 查询商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchnt() throws Exception {
		if ( containAuthor("ROLE_MEM_CUP_QRYALL")) { 
			return resultData(service.findMchnt(getFilters(), getPageInfo()));
		} else {
			return resultData(service.findMchnt(getUserLoginIdFilters("recAcc"), getPageInfo()));
		}
	}
	
	/**
	 * 查询商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchntbyMchntCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCode", Filter.STRING, getStringField("mchntCode"), Filter.Comparison.EQ));
		return resultData(service.findMchnt(filters, getPageInfo()));
	}
	
	/**
	 * 查询商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchntbyCustomerCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("customerCode", Filter.STRING, getStringField("customerCode"), Filter.Comparison.EQ));
		return resultData(service.findMchnt(filters, getPageInfo()));
	}

	/**
	 * 查询代理用户下所有商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchntbyAgentCustomerCode() throws Exception {
		return resultData(service.findMchntbyAgentCustomerCode(Long.valueOf(getStringField("customerCode")), getPageInfo()));
	}
	

	/**
	 * 新增商户(后台测试功能).
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String addMchnt() throws Exception {
		return resultData(service.doAddMchnt(getValidateBean(MhtBaseInfoVo.class), getUserDetailInfo()));
	}
	
	/**
	 * Mchnt info.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String mchntInfo() throws Exception {
		MhtBaseInfoVo vo = getValidateBean(MhtBaseInfoVo.class);
		String url = sysParamService.findParam("cups.getmchntinfo");
		Map<String, String> params = new HashMap<String, String>();
		params.put("licNo", vo.getLicNo());
		params.put("mchntCd", vo.getMchntCd());
		params.put("artifCertifId", vo.getArtifCertifId());
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		// if mchntCode == null then insert new mchnt. 
		if( vo.getMchntCode()!= null ) {
			params.put("mchntCode", String.valueOf(vo.getMchntCode()));
		} else {
			ExtData<MhtBaseInfoVo> mdt = service.doAddMchnt(vo, getUserInfo());
			params.put("mchntCode",  String.valueOf(mdt.getRoot().get(0).getMchntCode()));
		}
		// 检查银联商户号是否已经注册
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("mchntCd", Filter.STRING, vo.getMchntCd(), Filter.Comparison.EQ));
		ExtData<MhtBaseInfoVo> data = service.findMchnt(filters, getPageInfo());
		if ( data.getRoot()!=null && data.getRoot().size() > 0 ) {
			MhtBaseInfoVo mchntVo = data.getRoot().get(0);
			if ( vo.getMchntCode()==null || !mchntVo.getMchntCode().equals(vo.getMchntCode()) ) {
				return resultData(ExtDataUtil.genWithExceptions("该银联商户号["+vo.getMchntCd()+"]已注册，请操作原商户记录。"));
			}
		}
		
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	/**
	 * Risk data.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String riskData() throws Exception {
		MhtBaseInfoVo vo = getValidateBean(MhtBaseInfoVo.class);
		String url = sysParamService.findParam("cups.getriskdata");
		Map<String, String> params = new HashMap<String, String>();
		params.put("licNo", vo.getLicNo());
		params.put("mchntCd", vo.getMchntCd());
		params.put("artifCertifId", vo.getArtifCertifId());
		params.put("remark", "");
		params.put("mchntCode", String.valueOf(vo.getMchntCode()));
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	public String uploadAuditZipFile() throws Exception {
		String fileName = StringUtils.defaultString(uploadFileName,"mchnt.zip");
		CmMediaResVo mediaVo = mediaImageService.doInsertZipFile(getUserInfo(), uploadFile, fileName);
		String medPath = mediaVo.getMedPath();
		if(medPath.startsWith("/")){
			mediaVo.setMedPath(medPath.replaceFirst("\\/", " "));
		}
		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);
		mediaVo.setMedThumb(null);
		mediaVo.setMedContent(null);
		return resultData(data);
	}
	
	/**
	 * Send audit.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sendAudit() throws Exception {
		MhtBaseInfoVo vo = getValidateBean(MhtBaseInfoVo.class);
		String medSeq = getParameter("medSeq");
		String url = sysParamService.findParam("cups.sendaudit");
		Map<String, String> params = new HashMap<String, String>();
		params.put("licNo", vo.getLicNo());
		params.put("mchntCd", vo.getMchntCd());
		params.put("artifCertifId", vo.getArtifCertifId());
		params.put("remark", "");
		params.put("medSeq", medSeq);
		params.put("mchntCode", String.valueOf(vo.getMchntCode()));
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	/**
	 * Fetch transdata.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String fetchTransdata() throws Exception {
		MhtBaseInfoVo vo = getValidateBean(MhtBaseInfoVo.class);
		String url = sysParamService.findParam("cups.gettransdatabefore");
		Map<String, String> params = new HashMap<String, String>();
		params.put("mchntCd", vo.getMchntCd());
		params.put("remark", "");
		params.put("mchntCode", String.valueOf(vo.getMchntCode()));
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}

	/**
	 * Fetch transdata.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String fetchTransdataAfter() throws Exception {
		String url = sysParamService.findParam("cups.getmchntloandataafterloan");
		Map<String, String> params = new HashMap<String, String>();
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	
	/**
	 * Do check mchnt.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String doCheckMchnt() throws Exception {
		return resultData(service.doUpdateMchnt(getValidateBean(MhtBaseInfoVo.class), getUserDetailInfo()));
	}
	
	/**
	 * 商户查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchntExcel() throws Exception {
		if ( containAuthor("ROLE_MEM_CUP_QRYALL")) { 
			return defaultExportXLS("商户信息", memberList
						, service.findMchnt(getFilters(), getExcelPageInfo()));
		} else {
			return defaultExportXLS("商户信息", memberList
						, service.findMchnt(getUserLoginIdFilters("recAcc"), getExcelPageInfo()));
		}
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IMchntService service) {
		this.service = service;
	}
	/**
	 * @param sysParamService the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	/**
	 * @return the uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}
	/**
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	/**
	 * @param mediaImageService the mediaImageService to set
	 */
	public void setMediaImageService(IMediaImageService mediaImageService) {
		this.mediaImageService = mediaImageService;
	}

}
