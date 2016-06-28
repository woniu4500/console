package com.jiuyv.hhc.console.information.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.information.service.ICmFrdLinkService;
import com.jiuyv.hhc.model.information.CmFrdLinkVo;

public class CmFrdLinkAction extends DefaultPageSupportAction {
	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
private static List<CellDataType> columns = new ArrayList<CellDataType>();
	
	static {
		columns.add(new CellDataType("frdSeq", "内部序号"));
		columns.add(new CellDataType("version", "VERSION"));
		columns.add(new CellDataType("frdName", "名称"));
		columns.add(new CellDataType("frdType", "类型"));
		columns.add(new CellDataType("frdStyle", "样式"));
		columns.add(new CellDataType("frdLogo", "图片"));
		columns.add(new CellDataType("frdLogoHover", "图片浮动"));
		columns.add(new CellDataType("frdSort", "排序"));
		columns.add(new CellDataType("frdUrl", "链接地址"));
		columns.add(new CellDataType("isVisible", "是否显示"));
		columns.add(new CellDataType("frdStatus", "状态"));
		columns.add(new CellDataType("recCrtTime", "记录创建时间"));
		columns.add(new CellDataType("recCrtAcc", "记录创建用户帐号"));
		columns.add(new CellDataType("lastUptOrg", "最后更新机构"));
		columns.add(new CellDataType("lastUptTime", "最后修改时间"));
		columns.add(new CellDataType("lastUptAcc", "最后修改用户帐号"));
	}
	
	private ICmFrdLinkService service ;

	/**
	 * 查询友情链接信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmFrdLink() throws Exception {
		return resultData(service.find(getOrgFilters(FLD_ORG_CODE), getPageInfo()));
	}

	/**
	 * 友情链接查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findCmFrdLinkExcel() throws Exception {
		return defaultExportXLS("友情链接信息", columns
				, service.find(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}


	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertCmFrdLink() throws Exception {
		return resultData(service.doInsert(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateCmFrdLink() throws Exception {
		return resultData(service.doUpdate(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteCmFrdLink() throws Exception {
		return resultData(service.doDelete(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 提交.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCommitCmFrdLink() throws Exception {
		return resultData(service.doCommit(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 驳回.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRejectCmFrdLink() throws Exception {
		return resultData(service.doReject(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 发布.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doReleaseCmFrdLink() throws Exception {
		return resultData(service.doRelease(getValidateBean(CmFrdLinkVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 审核页面跳转.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doCheckPage() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * @param service the service to set
	 */
	public void setService(ICmFrdLinkService service) {
		this.service = service;
	} 
	
}
