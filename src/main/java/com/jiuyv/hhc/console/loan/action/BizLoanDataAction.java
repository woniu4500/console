package com.jiuyv.hhc.console.loan.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.common.database.Filter;
import com.jiuyv.common.database.Page;
import com.jiuyv.common.database.util.ExtDataUtil;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.common.httpclient.HttpClientUtil;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.common.service.ISysParamService;
import com.jiuyv.hhc.console.customer.service.IWebMemberService;
import com.jiuyv.hhc.console.loan.service.IBizLoanDataService;
import com.jiuyv.hhc.model.customer.WebMemberInfoVo;
import com.jiuyv.hhc.model.loan.BizLoanDataVo;
import com.jiuyv.hhc.model.security.SysUserVo;

public class BizLoanDataAction extends DefaultPageSupportAction {

	private IBizLoanDataService bizLoanDataService;

	private ISysParamService sysParamService;

	private IWebMemberService webMemberService;

	/** The OR g_ list. */
	private static List<CellDataType> loanList = new ArrayList<CellDataType>();
	static {
		loanList.add(new CellDataType("loanId", "贷款编号"));
		loanList.add(new CellDataType("licNo", "营业执照号码"));
		loanList.add(new CellDataType("creditStDesc", "贷款状态"));
		loanList.add(new CellDataType("cancelFlagDesc", "用户撤销"));

		loanList.add(new CellDataType("artifNm", "姓名"));
		loanList.add(new CellDataType("artifCertifId", "证件号"));

		loanList.add(new CellDataType("reqLoanAt", "申请贷款金额",
				CellDataRender.money));
		loanList.add(new CellDataType("loanAt", "贷款金额", CellDataRender.money));
		loanList.add(new CellDataType("loanBalance", "贷款余额",
				CellDataRender.money));
		loanList.add(new CellDataType("loanCycle", "贷款周期"));
		loanList.add(new CellDataType("loanRt", "贷款利率", CellDataRender.rate));

		loanList.add(new CellDataType("recAccDesc", "提交账户"));
		loanList.add(new CellDataType("loanReqTime", "申请时间",
				CellDataRender.time));
		loanList.add(new CellDataType("loanSuccTm", "放款成功时间",
				CellDataRender.time));
	}

	/**
	 * 查询贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoan() throws Exception {
		return resultData(bizLoanDataService.findLoan(getFilters(),
				getPageInfo()));
	}

	/**
	 * 贷款查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanExcel() throws Exception {
		return defaultExportXLS("贷款信息", loanList,
				bizLoanDataService.findLoan(getFilters(), getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertLoan() throws Exception {

		String mchntCode = getParameter("mchntCode");
		String name = getParameter("name");
		String mobile = getParameter("mobile");
		String loanAt = getParameter("loanAt");
		String creditNo = getParameter("creditNo");
		BizLoanDataVo bliv = new BizLoanDataVo();

		bliv.setMchntCode(Long.parseLong(mchntCode));
		bliv.setReqLoanAt(Long.parseLong(loanAt) * 100);
		bliv.setLoanAt(Long.parseLong(loanAt));
		bliv.setCardNo(creditNo);

		// 根据会员信息填写
		WebMemberInfoVo wmb = webMemberService
				.findUserByCustomerCode(mchntCode);
//		wmb.setMobile(mobile);
//		wmb.setRealName(name);
//		webMemberInfoDao.updateByKey(wmb);	
		if (wmb.getAuditState().equals("已审核")) {
			SysUserVo userVo = new SysUserVo();
			userVo.setLoginId("web");
			userVo.setCrdNo(wmb.getIdNumber());
			userVo.setCrdType(wmb.getIdType());
			return resultData(bizLoanDataService.doInsertLoan(bliv, userVo));
		} else {
			return resultData(ExtDataUtil.genWithExceptions("会员信息未审核通过，请等待审核"));
		}
//		SysUserVo userVo = new SysUserVo();
//		userVo.setLoginId("web");
//		userVo.setCrdNo(wmb.getIdNumber());
//		userVo.setCrdType(wmb.getIdType());
//		return resultData(bizLoanDataService.doInsertLoan(bliv, userVo));
	}
	
	
	
	
	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String applyLoan() throws Exception {

		String mchntCode = getParameter("mchntCode");
		String name = getParameter("name");
		String mobile = getParameter("mobile");
		String loanAt = getParameter("loanAt");
		String creditNo = getParameter("creditNo");
		BizLoanDataVo bliv = new BizLoanDataVo();

		bliv.setMchntCode(Long.parseLong(mchntCode));
		bliv.setReqLoanAt(Long.parseLong(loanAt) * 100);
		bliv.setLoanAt(Long.parseLong(loanAt)*100);
		bliv.setCardNo(creditNo);
		bliv.setArtifNm(name);
		bliv.setCustomerMobile(mobile);

		// 根据会员信息填写
		WebMemberInfoVo wmb = webMemberService
				.findUserByCustomerCode(mchntCode);
//		wmb.setMobile(mobile);
//		wmb.setRealName(name);
//		webMemberInfoDao.updateByKey(wmb);	
		if (wmb.getAuditState().equals("已审核")) {
			SysUserVo userVo = new SysUserVo();
			userVo.setLoginId("web");
			userVo.setCrdNo(wmb.getIdNumber());
			userVo.setCrdType(wmb.getIdType());
			return resultData(bizLoanDataService.doInsertLoan(bliv, userVo));
		} else {
			return resultData(ExtDataUtil.genWithExceptions("会员信息未审核通过，请等待审核"));
		}
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateLoan() throws Exception {
		String mchntCode = getParameter("mchntCode");
		String loanCycle = getParameter("loanCycle");
		String loanAt = getParameter("loanAt");
		String creditNo = getParameter("creditNo");
		BizLoanDataVo bliv = new BizLoanDataVo();

		bliv.setMchntCode(Long.parseLong(mchntCode));
		bliv.setLoanAt(Long.parseLong(loanAt));
		bliv.setLoanCycle(Long.parseLong(loanCycle));
		bliv.setCardNo(creditNo);
		// 根据会员信息填写
		SysUserVo userVo = new SysUserVo();
		userVo.setLoginId("web");
		return resultData(bizLoanDataService.doUpdateLoan(bliv, userVo));

	}

	/**
	 * 取消.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCancleLoan() throws Exception {
		String phoneflag = getParameter("isapp");
		String loanId = getParameter("loanId");
		BizLoanDataVo bliv = bizLoanDataService.findBizLoanDataByLoanId(loanId);
		bliv.setCancelFlag("1");
		String loanstatus = bliv.getCreditSt();
		if (StringUtils.isNotEmpty(loanstatus)
				&& (loanstatus.equals("00") || loanstatus.equals("01"))) {
			// 根据会员信息填写
			SysUserVo userVo = new SysUserVo();
			userVo.setLoginId("APP");
			return resultData(bizLoanDataService.doUpdateLoan(bliv, userVo));
		} else {
			return resultData(ExtDataUtil.genWithSingleData("状态不为申请，不能取消贷款申请"));
		}
	}

	/**
	 * 收取服务费
	 */
	public String doPayLoanBenefit() throws Exception {
		String phoneflag = getParameter("isapp");
		String loanId = getParameter("loanId");
		String svcFeeBenefit = getParameter("svcFeeBenefit");
		BizLoanDataVo bliv = bizLoanDataService.findBizLoanDataByLoanId(loanId);
		String loanstatus = bliv.getCreditSt();
		if (StringUtils.isNotEmpty(loanstatus) && (loanstatus.equals("04"))) {
			bliv.setSvcFeeBenefit(Long.parseLong(svcFeeBenefit));
			// 根据会员信息填写
			SysUserVo userVo = new SysUserVo();
			userVo.setLoginId("APP");
			return resultData(bizLoanDataService.doUpdateLoan(bliv, userVo));
		} else {
			return resultData(ExtDataUtil.genWithSingleData("贷款未成功无须支付"));
		}
	}

	/**
	 * 贷后信息维护.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doMaintainLoan() throws Exception {
		return resultData(bizLoanDataService.doMaintainLoan(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * 提交.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doSubmitLoan() throws Exception {
		return resultData(bizLoanDataService.doSubmitLoan(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * 手工标记状态为商户认证.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCertifyLoan() throws Exception {
		return resultData(bizLoanDataService.doCertifyLoan(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * 手工标记状态为审核驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDenyLoan() throws Exception {
		return resultData(bizLoanDataService.doDenyLoan(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	
	
	/**
	 * 手工标记状态为审核驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doAuditLoan() throws Exception {
		return resultData(bizLoanDataService.doAuditLoan(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}
	
	/**
	 * 贷款提交银行 认证商户提交银行，补充银行信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doSend2Bank() throws Exception {
		//
		return resultData(bizLoanDataService.doSend2Bank(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * 贷款申请成功
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doLoanSuccess() throws Exception {
		return resultData(bizLoanDataService.doLoanSuccess(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * 贷款申请失败
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doLoanFailed() throws Exception {
		return resultData(bizLoanDataService.doLoanFailed(
				getValidateBean(BizLoanDataVo.class), getUserDetailInfo()));
	}

	/**
	 * Send audit.
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String doSendLoanInfo() throws Exception {
		BizLoanDataVo vo = getValidateBean(BizLoanDataVo.class);
		//
		// 保存修改信息
		bizLoanDataService.doMaintainLoan(vo, getUserInfo());

		String url = sysParamService.findParam("cups.sendloan");
		Map<String, String> params = new HashMap<String, String>();
		params.put("loanId", vo.getLoanId());
		params.put("remark", "");
		params.put("oprAcct", getUserInfo().getLoginId());
		String result = HttpClientUtil.post(url, params);
		return resultData(result);
	}

	/**
	 * 查询贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanbyCustomerCode() throws Exception {
		String mchntCode = getParameter("mchntCode");
		String start = getParameter("start");
		String limit = getParameter("limit");
		if(StringUtils.isNotBlank(mchntCode)&&StringUtils.isNotBlank(start)&&StringUtils.isNotBlank(limit)){
			Long customercode = Long.parseLong(mchntCode);
			
			Page page = new Page(start,limit,"loanReqTime","DESC");
			return resultData(bizLoanDataService.findBizLoanDataCustomerCode(
					customercode, page));
		}else{
			List<Filter> filters = new ArrayList<Filter>();
			BizLoanDataVo vo = getValidateBean(BizLoanDataVo.class);
			filters.add(new Filter("customerCode", Filter.NUMERIC, vo
					.getCustomerCode(), Filter.Comparison.EQ));
			return resultData(bizLoanDataService.findLoan(filters,
					getPageInfo()));
		}
	}

	/**
	 * 查询代理商户下的所有贷款信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findLoanbyAgentCustomerCode() throws Exception {
		BizLoanDataVo vo = getValidateBean(BizLoanDataVo.class);
		return resultData(bizLoanDataService.findLoanbyAgentCustomerCode(
				vo.getCustomerCode(), getPageInfo()));
	}

	/**
	 * Audit mchnt. 审核通过的发送邮件到北银
	 * 
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String zipLoanFileAndSendMaill() throws Exception {
		/**
		 * TODO 生成excel放在当前文件夹里
		 * 
		 */
		String loanIds = getParameter("loanIds");
		String mchntCodes = getParameter("mchntCodes");
		String cardNos = getParameter("cardNos");
		System.out.println(loanIds);
		System.out.println(mchntCodes);
		System.out.println(cardNos);
		String[] loanIdArray = loanIds.split(",");
		String[] mchntCodeArray = mchntCodes.split(",");
		String[] cardNoArray = cardNos.split(",");
		Map map = new HashMap();
		for (int i = 0; i < mchntCodeArray.length; i++) {
			map.put(mchntCodeArray[i], cardNoArray[i]);
		}

		ExtData<BizLoanDataVo> extData = new ExtData<BizLoanDataVo>();
		List<BizLoanDataVo> bizLoanDataVoList = new ArrayList<BizLoanDataVo>();
		for (String loanId : loanIdArray) {
			BizLoanDataVo bizLoanData = bizLoanDataService
					.findBizLoanDataByLoanId(loanId);
			bizLoanDataVoList.add(bizLoanData);

		}
		extData.setRoot(bizLoanDataVoList);

		String datestr = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		String savePath = System.getProperty("java.io.tmpdir") + datestr + "/";

		File dir = new File(savePath);
		dir.mkdirs();

		String str = defaultExportXLS("贷款信息", loanList, extData);
		return resultData(bizLoanDataService.zipFileAndSendMaill(dir, map,
				datestr));
	}

	/**
	 * @param bizLoanDataService
	 *            the bizLoanDataService to set
	 */
	public void setBizLoanDataService(IBizLoanDataService bizLoanDataService) {
		this.bizLoanDataService = bizLoanDataService;
	}

	/**
	 * @param sysParamService
	 *            the sysParamService to set
	 */
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public void setWebMemberService(IWebMemberService webMemberService) {
		this.webMemberService = webMemberService;
	}

}
