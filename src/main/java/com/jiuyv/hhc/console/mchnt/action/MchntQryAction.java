package com.jiuyv.hhc.console.mchnt.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.exception.BaseException;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.httpclient.HttpClientUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.customer.service.IWebMemberService;
import com.jiuyv.hhc.console.mchnt.service.IMchntService;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.mchnt.BizMerchantVo;

/**
 * <h1>The Class MchntQryAction.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class MchntQryAction extends DefaultPageSupportAction {

	/** The I mchnt service mchnt service. */
	private IMchntService mchntService ;
	
	/** The I web member service web member service. */
	private IWebMemberService webMemberService;
	/** The I sys param service sys param service. */
	private ISysParamService sysParamService ;
	
	/** 图片处理Service. */
	private IMediaImageService mediaImageService ; 
	
	/** 上传文件. */
	private File uploadFile ; 
	
	/** 上传文件名. */
	private String uploadFileName ;
	
	
	/** The OR g_ list. */
	private static List<CellDataType> MER_COL = new ArrayList<CellDataType>();
	
	static {
		MER_COL.add(new CellDataType("mchntCode", "内部商户号"));
		MER_COL.add(new CellDataType("licNo", "营业执照号码"));
		MER_COL.add(new CellDataType("mchntCd", "银联商户号"));
		MER_COL.add(new CellDataType("reveRegCd", "税务登记代码"));
		MER_COL.add(new CellDataType("mchntCnName", "商户中文名称"));
		MER_COL.add(new CellDataType("regAddr", "注册地址"));
		MER_COL.add(new CellDataType("artifNm", "法人代表姓名"));
		MER_COL.add(new CellDataType("artifCertifId", "法人代表证件号"));
		MER_COL.add(new CellDataType("mchntTp", "商户类型"));
		MER_COL.add(new CellDataType("mchntGrp", "商户组别"));
		MER_COL.add(new CellDataType("phone", "联系电话"));
		MER_COL.add(new CellDataType("mchntCrtDt", "商户入网时间"));
		MER_COL.add(new CellDataType("posNum", "装机数量"));
		MER_COL.add(new CellDataType("mchntStDesc", "商户状态"));
		MER_COL.add(new CellDataType("dispMchntStDesc", "客户端状态"));
		MER_COL.add(new CellDataType("lastCupResp", "查询响应"));
		MER_COL.add(new CellDataType("customerCode", "会员代码"));
		MER_COL.add(new CellDataType("customerMobile", "用户手机号"));
		MER_COL.add(new CellDataType("recAccDesc", "提交用户"));
		MER_COL.add(new CellDataType("recTime", "录入时间", CellDataRender.time));
		MER_COL.add(new CellDataType("auditTime", "提交资质审核时间", CellDataRender.time));
		MER_COL.add(new CellDataType("beforeTime", "获取贷前数据时间", CellDataRender.time));
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
		List<Filter> filters = getFilters();
		filters.add(new Filter("mchntSt", Filter.STRING, "09", Filter.Comparison.NE));
		if ( containAuthor("ROLE_MEM_CUP_QRYALL")) { 
			// 查询全部商户权限
			return resultData(mchntService.findMchnt(filters, getPageInfo()));
		} else {
			return resultData(mchntService.findMchnt(getUserLoginIdFilters("recAcc"), getPageInfo()));
		}
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
		List<Filter> filters = getFilters();
		filters.add(new Filter("mchntSt", Filter.STRING, "09", Filter.Comparison.NE));
		if ( containAuthor("ROLE_MEM_CUP_QRYALL")) { 
			String mchntCode = getParameter("mchntCode");
			if ( StringUtils.isNotBlank(mchntCode) ) {
				filters.add(new Filter("mchntCode", Filter.STRING, mchntCode, Filter.Comparison.EQ));
			}
			return defaultExportXLS("商户信息", MER_COL , mchntService.findMchnt(filters, getExcelPageInfo()));
		} else {
			List<Filter> acfilters = getUserLoginIdFilters("recAcc");
			String mchntCode = getParameter("mchntCode");
			if ( StringUtils.isNotBlank(mchntCode) ) {
				acfilters.add(new Filter("mchntCode", Filter.STRING, mchntCode, Filter.Comparison.EQ));
			}
			return defaultExportXLS("商户信息", MER_COL , mchntService.findMchnt(acfilters, getExcelPageInfo()));
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
		return resultData(mchntService.findMchntDetail(filters, getPageInfo()));
	}
	
	/**
	 * 查询会员商户列表.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMchntbyCustomerCode() throws Exception {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("customerCode", Filter.STRING, getStringField("customerCode"), Filter.Comparison.EQ));
		return resultData(mchntService.findMchnt(filters, getPageInfo()));
	}

	/**
	 * 查询代理用户下所有商户信息.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findMchntbyAgentCustomerCode() throws Exception {
		return resultData(mchntService.findMchntbyAgentCustomerCode(Long.valueOf(getStringField("customerCode")), getPageInfo()));
	}
	
	/**
	 * Update mchnt info.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateMchntInfo() throws Exception {
		return resultData(mchntService.doUpdMchnt(getValidateBean(BizMerchantVo.class), getUserInfo()));
	}
	
	
	/**
	 * Mchnt info.
	 * 联机获取银联商户信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String cupsMchntInfo() throws Exception {
		BizMerchantVo vo = getValidateBean(BizMerchantVo.class);
		String url = sysParamService.findParam("cups.getmchntinfo");
		Map<String, String> params = new HashMap<String, String>();
		params.put("licNo", vo.getLicNo());
		params.put("mchntCd", vo.getMchntCd());
		params.put("artifCertifId", vo.getArtifCertifId());
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		// 判断是否插入新商户
		if( vo.getMchntCode()!= null ) {
			params.put("mchntCode", String.valueOf(vo.getMchntCode()));
			mchntService.doUpdMchnt(vo, getUserInfo());
		} else {
			ExtData<BizMerchantVo> mdt = mchntService.doAddMchnt(vo, getUserInfo());
			params.put("mchntCode",  String.valueOf(mdt.getRoot().get(0).getMchntCode()));
		}
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}
	
	/**
	 * Risk data.
	 * 联机获取银联商户风险数据
	 * @return the string
	 * @throws Exception the exception
	 */
	public String cupsRiskData() throws Exception {
		BizMerchantVo vo = getValidateBean(BizMerchantVo.class);
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
	
	/**
	 * Send info complete sms.
	 * 发送信息补全短信
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sendInfoCompleteSMS() throws Exception {
		String fields = getParameter("fields");
		String mchntCd = getParameter("mchntCd");
		String customerMobile = getParameter("customerMobile");
		if ( StringUtils.isBlank(fields) ) {
			return resultData(ExtDataUtil.genWithExceptions("请填写补充信息内容。"));
		}
		if ( StringUtils.isBlank(mchntCd) ) {
			return resultData(ExtDataUtil.genWithExceptions("银联商户号为空。"));
		}
		if ( StringUtils.isBlank(customerMobile) ) {
			return resultData(ExtDataUtil.genWithExceptions("请填写手机号。"));
		}
		Map<String,String> pmap = sysParamService.findParamMap("sys.mes");
		String apikey = pmap.get("sys.mes.apikey");
		String url = pmap.get("sys.mes.url.utf8");
		// String company = pmap.get("sys.mes.company");
		String tplid = pmap.get("sys.mes.infocomp.tplid");
		// 您申请的商户[#mchntCd]需要补填信息#fields。【#company】
		Map<String, String> param = new HashMap<String, String>();
		// param.put("company", company);
		param.put("fields", StringUtils.defaultString(fields,""));
		param.put("mchntCd", mchntCd);
		String result = com.jiuyv.hhc.sms.util.HttpClientUtil.sendMessage( apikey, tplid, url, customerMobile, param );
		
		if ( StringUtils.equalsIgnoreCase("success", result)) {
			return resultData(ExtDataUtil.genWithSingleData(""));
		} else {
			return resultData(ExtDataUtil.genWithExceptions("发送失败，原因为"+result));
		}
	}

	/**
	 * Send info complete sms.
	 * 发送商户注册成功短信
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sendRegistSMS() throws Exception {
		String mchntCd = getParameter("mchntCd");
		String customerMobile = getParameter("customerMobile");
		if ( StringUtils.isBlank(mchntCd) ) {
			return resultData(ExtDataUtil.genWithExceptions("银联商户号为空。"));
		}
		if ( StringUtils.isBlank(customerMobile) ) {
			return resultData(ExtDataUtil.genWithExceptions("请填写手机号。"));
		}
		Map<String,String> pmap = sysParamService.findParamMap("sys.mes");
		String apikey = pmap.get("sys.mes.apikey");
		String url = pmap.get("sys.mes.url.utf8");
		// String company = pmap.get("sys.mes.company");
		String tplid = pmap.get("sys.mes.inforegist.tplid");
		// 您申请的商户[#mchntCd]注册成功，可进行申请贷款。【#company】
		Map<String, String> param = new HashMap<String, String>();
		// param.put("company", company);
		param.put("mchntCd", mchntCd);
		String result = com.jiuyv.hhc.sms.util.HttpClientUtil.sendMessage( apikey, tplid, url, customerMobile, param );
		
		if ( StringUtils.equalsIgnoreCase("success", result)) {
			return resultData(ExtDataUtil.genWithSingleData(""));
		} else {
			return resultData(ExtDataUtil.genWithExceptions("发送失败，原因为"+result));
		}
	}

	
	/**
	 * Upload attach file.
	 * 上传附件
	 * @return the string
	 * @throws BaseException the base exception
	 */
	public String uploadAttachFile () throws BaseException {
		String mchntCode = getParameter("mchntCode");
		String attachType = getParameter("attachType");
		String fileName = StringUtils.defaultString(uploadFileName, "attach.jpg");
		CmMediaResVo mediaVo = mediaImageService.doInsertImage(getUserInfo(), uploadFile, fileName);
		String medPath = mediaVo.getMedPath();
		if(medPath.startsWith("/")){
			mediaVo.setMedPath(medPath.replaceFirst("\\/", ""));
		}
		mediaVo.setMedThumb(null);
		mediaVo.setMedContent(null);
		return resultData(mchntService.doInsertAttachFile(mediaVo, mchntCode, attachType, getUserInfo()));
	}
	
	
	/**
	 * Delete attach file.
	 * 删除附件
	 * @return the string
	 * @throws BaseException the base exception
	 */
	public String deleteAttachFile() throws BaseException {
		String attachSeqs = getParameter("attachSeqs");
		return resultData(mchntService.doDeleteAttachFile(attachSeqs, getUserInfo()));
	}
	
	
	/**
	 * 设置 the I mchnt service mchnt service.
	 *
	 * @param mchntService the mchntService to set
	 */
	public void setMchntService(IMchntService mchntService) {
		this.mchntService = mchntService;
	}

	/**
	 * Sets the web member service.
	 *
	 * @param webMemberService the webMemberService to set
	 */
	public void setWebMemberService(IWebMemberService webMemberService) {
		this.webMemberService = webMemberService;
	}

	/**
	 * 设置 the I sys param service sys param service.
	 *
	 * @param sysParamService the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	/**
	 * 获取 上传文件.
	 *
	 * @return the uploadFile
	 */
	public File getUploadFile() {
		return uploadFile;
	}

	/**
	 * 设置 上传文件.
	 *
	 * @param uploadFile the uploadFile to set
	 */
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * 获取 上传文件名.
	 *
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * 设置 上传文件名.
	 *
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * 设置 图片处理Service.
	 *
	 * @param mediaImageService the mediaImageService to set
	 */
	public void setMediaImageService(IMediaImageService mediaImageService) {
		this.mediaImageService = mediaImageService;
	}
	
}
