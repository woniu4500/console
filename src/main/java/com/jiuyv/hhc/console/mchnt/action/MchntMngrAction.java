package com.jiuyv.hhc.console.mchnt.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.mchnt.service.IMchntService;


/**
 * <h1>The Class MchntMngrAction.</h1>
 * <p>Descriptions:</p>
 *

 * @author
 * @since 2015
 * @version 1.0.0
 */
public class MchntMngrAction extends DefaultPageSupportAction {

	/** The I mchnt service mchnt service. */
	private IMchntService mchntService ;
	
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
	public String findMngrMchnt() throws Exception {
		return resultData(mchntService.findMchnt(getFilters(), getPageInfo()));
	}
	
	/**
	 * 商户查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMngrMchntExcel() throws Exception {
		List<Filter> filters = getFilters();
		String mchntCode = getParameter("mchntCode");
		if ( StringUtils.isNotBlank(mchntCode) ) {
			filters.add(new Filter("mchntCode", Filter.STRING, mchntCode, Filter.Comparison.EQ));
		}
		return defaultExportXLS("商户信息", MER_COL , mchntService.findMchnt(filters, getExcelPageInfo()));
	}

	/**
	 * @param mchntService the mchntService to set
	 */
	public void setMchntService(IMchntService mchntService) {
		this.mchntService = mchntService;
	}
	
}
