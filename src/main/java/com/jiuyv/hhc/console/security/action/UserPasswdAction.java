package com.jiuyv.hhc.console.security.action;

import java.util.ArrayList;

import com.jiuyv.common.database.ExtData;
import com.jiuyv.hhc.console.basic.action.DefaultPageSupportAction;
import com.jiuyv.hhc.console.security.service.IUserService;
import com.jiuyv.hhc.model.security.PassWordVo;
import com.jiuyv.hhc.model.security.SysUserVo;


/**
 * 用户信息管理.
 *

 * @author 
 * @since 2014-1-3 16:20:55
 * @version 1.0.0
 */
public class UserPasswdAction extends DefaultPageSupportAction {

	/** The Service. */
	private IUserService service;

	/**
	 * 修改用户密码信息.
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public String doUpdateUserPasswd() throws Exception {
		PassWordVo passwordvo = getValidateBean(PassWordVo.class);
		passwordvo.setUserID(this.getUserInfo().getUserId());
		ExtData<SysUserVo> data = service.doUpdateUserPasswd(passwordvo,
				getFilters(), getUserDetailInfo());
		data.setRoot(new ArrayList<SysUserVo>());
		return resultData(data);
	}

	/**
	 * 设置 Service.
	 *
	 * @param service the new Service
	 */
	public void setService(IUserService service) {
		this.service = service;
	}

}
