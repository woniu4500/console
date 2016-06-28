package com.jiuyv.hhc.console.customer.action;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.database.Filter;
import com.jiuyv.common.excel.CellDataType;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.customer.service.IMemberService;
import com.jiuyv.hhc.model.customer.MemberInfoVo;

/**
 * 会员信息的增、删、改、查.
 *

 * @author 
 * @since 2014-1-3 15:59:50
 * @version 1.0.0
 */
@Deprecated
public class MemberAction extends DefaultPageSupportAction {

	/** Constant String FLD_ORG_CODE: String :. */
	private static final String FLD_ORG_CODE = "orgCode";
	
	/** Constant String FLD_STATUS: String :. */
	private static final String FLD_STATUS = "status";
	
	/** Constant String STATUS_NORMAL: String :. */
	private static final String STATUS_NORMAL = "0";
	
	/** The Service. */
	private IMemberService service;

	/** The OR g_ list. */
	private static List<CellDataType> memberList = new ArrayList<CellDataType>();
	static {
		memberList.add(new CellDataType("customerCode", "会员代码"));
		memberList.add(new CellDataType("mobile", "手机号码"));
		memberList.add(new CellDataType("realName", "姓名"));
		memberList.add(new CellDataType("address", "地址"));
		memberList.add(new CellDataType("email", "电子邮件"));
		memberList.add(new CellDataType("customerType", "会员类型"));
		memberList.add(new CellDataType("lastLoginTime", "最后登录时间"));
		memberList.add(new CellDataType("lastLoginIp", "最后登录IP"));
		memberList.add(new CellDataType("idNumber", "证件号码"));
		memberList.add(new CellDataType("idType", "证件类型"));
		memberList.add(new CellDataType("isActiveDesc", "是否有效"));
	}

	/**
	 * 查询会员信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMember() throws Exception {
		return resultData(service.findMember( getFilters(), getPageInfo()));
	}

	/**
	 * 会员查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMemberExcel() throws Exception {
		return defaultExportXLS("会员信息", memberList
				, service.findMember(getOrgFilters(FLD_ORG_CODE), getExcelPageInfo()));
	}

	/**
	 * 会员查询导出excel.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String findMemberCombo() throws Exception {
		List<Filter> filters = getOrgFilters(FLD_ORG_CODE);
		filters.add(new Filter(FLD_STATUS, Filter.STRING, STATUS_NORMAL, Filter.Comparison.EQ));
		return resultData(service.findMember(filters, getExcelPageInfo()));
	}

	/**
	 * 新增.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doInsertAgentMember() throws Exception {
		return resultData(service.doInsertMember(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}

	/**
	 * 修改.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateAgentMember() throws Exception {
		return resultData(service.doUpdateMember(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}
	
	/**
	 * 解锁.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRecoverMember() throws Exception {
		return resultData(service.doRecoverMember(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}
	/**
	 *重置密码s.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doResetAgentMemberPasswd() throws Exception {
		return resultData(service.doResetMemberPasswd(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}

	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteAgentMember() throws Exception {
		return resultData(service.doDeleteMember( getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}
	
	
	/**
	 * 解锁.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doRecoverAgentMember() throws Exception {
		return resultData(service.doRecoverMember(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}
	/**
	 *重置密码s.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doResetMemberPasswd() throws Exception {
		return resultData(service.doResetMemberPasswd(getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}

	/**
	 * 删除.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doDeleteMember() throws Exception {
		return resultData(service.doDeleteMember( getValidateBean(MemberInfoVo.class),getUserDetailInfo()));
	}

	/**
	 * 设置 the Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IMemberService service) {
		this.service = service;
	}

}
