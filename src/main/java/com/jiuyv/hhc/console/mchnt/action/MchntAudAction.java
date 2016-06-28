package com.jiuyv.hhc.console.mchnt.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.httpclient.HttpClientUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.IMediaImageService;
import com.jiuyv.hhc.console.common.service.IMediaSupportService;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.mchnt.service.IMchntService;
import com.jiuyv.hhc.model.common.CmMediaResVo;
import com.jiuyv.hhc.model.mchnt.BizMerchantVo;

/**
 * <h1>The Class MchntAudAction.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class MchntAudAction extends DefaultPageSupportAction {

	/** The Service. */
	private IMchntService mchntService;
	
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
	 * 查询已注册的商户信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findRegistMchnt() throws Exception {
		List<Filter> filters = getFilters();
		filters.add(new Filter("mchntSt", Filter.LIST, "02,03,04,05,06,07", Filter.Comparison.EQ));
		if ( containAuthor("ROLE_MEM_CUP_AUDALL")) { 
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
	public String findRegistMchntExcel() throws Exception {
		List<Filter> filters = getFilters();
		filters.add(new Filter("mchntSt", Filter.LIST, "02,03,04,05,06,07", Filter.Comparison.EQ));
		if ( containAuthor("ROLE_MEM_CUP_AUDALL")) { 
			// 查询全部商户权限
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
	 * Find attach def.
	 * 查询附件定义信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findAttachDef() throws Exception {
		return resultData(mchntService.findAttachDef());
	}
	
	/**
	 * Find mchnt attach.
	 * 查询商户附件信息
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findMchntAttach() throws Exception {
		return resultData(mchntService.findMchntAttach(getValidateBean(BizMerchantVo.class)));
	}
	
	public String uploadAuditZipFile() throws Exception {
		String fileName = StringUtils.defaultString(uploadFileName, "mchnt.zip");
		CmMediaResVo mediaVo = mediaImageService.doInsertZipFile(getUserInfo(), uploadFile, fileName);
		String medPath = mediaVo.getMedPath();
		if(medPath.startsWith("/")){
			mediaVo.setMedPath(medPath.replaceFirst("\\/", ""));
		}
		ExtData<CmMediaResVo> data = ExtDataUtil.genWithSingleData(mediaVo);
		mediaVo.setMedThumb(null);
		mediaVo.setMedContent(null);
		return resultData(data);
	}
	
	/**
	 * Audit mchnt.
	 * 商户信息审核通过
	 * @return the string
	 * @throws Exception the exception
	 */
	public String auditMchnt() throws Exception {
		return resultData(mchntService.doAuditMchnt(getValidateBean(BizMerchantVo.class), getUserInfo()));
	}

	
	/**
	 * Deny mchnt.
	 * 商户信息审核驳回
	 * @return the string
	 * @throws Exception the exception
	 */
	public String denyMchnt() throws Exception {
		return resultData(mchntService.doDenyMchnt(getValidateBean(BizMerchantVo.class), getUserInfo()));
	}
	
	/**
	 * Send audit.
	 * 提交资质申请
	 * @return the string
	 * @throws Exception the exception
	 */
	public String cupsSendAudit() throws Exception {
		BizMerchantVo vo = getValidateBean(BizMerchantVo.class);
		// 查询系统中除此商户外，是否有提交资质审核、审核成功、已认证、未还款的商户
		/*ExtData<BizMerchantVo> checked = mchntService.findValidMchntForAudit(vo);
		if ( !checked.isSuccess() ) {
			return resultData(checked);
		}*/
		// 文件序号
		String medSeq = getParameter("medSeq");
		// 文件路径
		String medUrl = getParameter("medUrl");
		if ( StringUtils.isNotBlank(medUrl)) {
			medUrl = StringUtils.trim(medUrl);
			mchntService.doMergeZipAttach(vo, medUrl, getUserInfo());
			// medSeq为空时使用medUrl获取
			if ( StringUtils.isBlank(medSeq) ) {
				IMediaSupportService supportService = (IMediaSupportService) mediaImageService;
				String filePath = !medUrl.startsWith("/")?("/" + medUrl):medUrl ; 
				CmMediaResVo res = (CmMediaResVo) supportService.findMedia(filePath, null);
				if ( res != null ) {
					medSeq = String.valueOf(res.getMedSeq());
				}
			}
		}
		
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
	 * 获取商户贷前数据
	 * @return the string
	 * @throws Exception the exception
	 */
	public String cupsFetchTransdata() throws Exception {
		BizMerchantVo vo = getValidateBean(BizMerchantVo.class);
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
	public String cupsFetchTransdataAfter() throws Exception {
		String url = sysParamService.findParam("cups.getmchntloandataafterloan");
		Map<String, String> params = new HashMap<String, String>();
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params );
		return resultData(result);
	}

	/**
	 * @param mchntService the mchntService to set
	 */
	public void setMchntService(IMchntService mchntService) {
		this.mchntService = mchntService;
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
