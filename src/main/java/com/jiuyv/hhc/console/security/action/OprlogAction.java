/**
 * 
 */
package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.excel.CellDataRender;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IOprlogService;
import com.jiuyv.hhc.model.security.SysOprlogVo;



/**
 * The Class OprlogAction.
 *

 * @author 
 * @since 2014-1-3 16:00:24
 * @version 1.0.0
 */
public class OprlogAction extends DefaultPageSupportAction {
	
	private static final String FLD_OPR_ORG_CODE = "oprOrgCode";
	private static final String FLD_OPR_USER_ID = "oprUserId";

	/** The Service. */
	private IOprlogService service;

	/** The OR g_ list. */
	private static List<CellDataType> logList = new ArrayList<CellDataType>();
	static {
		logList.add(new CellDataType("oprNo", "序号"));
		logList.add(new CellDataType("oprTime", "时间", CellDataRender.time));
		logList.add(new CellDataType("oprLoginId", "登录账号"));
		logList.add(new CellDataType("oprUserName", "操作员编号"));
		logList.add(new CellDataType("resZh", "操作名称"));
		logList.add(new CellDataType("oprIp", "操作所在IP"));
		logList.add(new CellDataType("oprSuccess", "操作结果"));
		logList.add(new CellDataType("oprRemark", "备注"));
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IOprlogService service) {
		this.service = service;
	}

	/**
	 * 机构日志查询.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findOprlog() throws Exception {
		return resultData(service.findOprlogList( getOrgFilters(FLD_OPR_ORG_CODE), getPageInfo()));
	}

	/**
	 * 查询日志明细信息
	 * @return
	 * @throws Exception
	 */
	public String findOprlogDtl() throws Exception {
		return resultData(service.findOprlogDetail(getValidateBean(SysOprlogVo.class).getOprNo()));
	}
	
	/**
	 * 机构日志查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findOprlogExcel() throws Exception {
		return defaultExportXLS("机构日志信息", logList
				, service.findOprlogList(getOrgFilters(FLD_OPR_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 用户日志查询.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String findOprlogUser() throws Exception {
		return resultData(service.findOprlogList(getUserFilters(FLD_OPR_USER_ID), getPageInfo()));
	}

	/**
	 * 用户日志查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findOprlogUserExcel() throws Exception {
		return defaultExportXLS("用户日志信息", logList, service.findOprlogList(
				getUserFilters(FLD_OPR_USER_ID), getExcelPageInfo()));
	}

}
