package com.jiuyv.hhc.console.security.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiuyv.hhc.console.basic.action.DefaultReportAction;
import com.jiuyv.hhc.console.basic.action.RptVo;
import com.jiuyv.hhc.model.security.dao.SecurityStatisticDao;
import com.jiuyv.hhc.model.security.rpt.LoginStatisticBean;

/**
 * 登录次数统计报表
 * @author cowyk
 *
 */
public class LoginDayStatisticReport extends DefaultReportAction {

	@Autowired
	private SecurityStatisticDao securityStatisticDao;
	
	private static final String RPT_PATH = RPT_BASE + "/security/LoginDayStatistic.jasper";
	
	@SuppressWarnings("rawtypes")
	protected RptVo processData() throws Exception {
		List<LoginStatisticBean> rptData = securityStatisticDao.loginDayStatistic();
		Map rptParam = new HashMap();
		return new RptVo(RPT_PATH, rptParam, rptData);
	}

}
