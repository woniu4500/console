package com.jiuyv.hhc.console.security.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiuyv.hhc.console.basic.action.DefaultReportAction;
import com.jiuyv.hhc.console.basic.action.RptVo;
import com.jiuyv.hhc.console.security.service.ISecurityStatisticService;
import com.jiuyv.hhc.model.security.rpt.LoginCompStatisticBean;

/**
 * 操作统计报表.
 *

 * @author 
 * @since 2014-1-21 11:18:35
 * @version 1.0.0
 */
public class LoginCompStatisticReport extends DefaultReportAction {

	
	/** Constant String RPT_PATH: String :. */
	private static final String RPT_PATH = RPT_BASE + "/security/LoginCompStatistic.jasper";
	/** 子报表路径 */
	private static final String SUB_PATH = RPT_BASE + "/security/LoginStatisticChart.jasper";
	/** security statistic service */
	private ISecurityStatisticService securityStatisticService;
	
	/**
	 * @return
	 * @throws Exception
	 * @see com.jiuyv.hhc.console.basic.action.DefaultReportAction#processData()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected RptVo processData() throws Exception {
		List<LoginCompStatisticBean> rptData = securityStatisticService.findLoginCompStatisticData(getFilters(), getUserInfo());
		Map rptParam = new HashMap();
		rptParam.put("SUBPATH_LS", SUB_PATH);
		return new RptVo(RPT_PATH, rptParam, rptData);
	}

	/**
	 * 设置 security statistic service.
	 *
	 * @param securityStatisticService the new security statistic service
	 */
	public void setSecurityStatisticService(
			ISecurityStatisticService securityStatisticService) {
		this.securityStatisticService = securityStatisticService;
	}

}
