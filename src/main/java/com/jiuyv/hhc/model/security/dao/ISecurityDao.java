package com.jiuyv.hhc.model.security.dao;

import java.util.List;

import com.jiuyv.hhc.model.security.SecurityAuthority;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.SysOrgVo;
import com.jiuyv.hhc.model.security.SysResourceVo;


/**
 * 
 * @author
 * 
 */
public interface ISecurityDao {

	/**
	 * 根据登录名查找用戶信息
	 * 
	 * @param userName
	 * @return
	 */
	List<SecurityUserDetail> findUserByLoginId(String loginId);
	
	/**
	 * 根据机构代码查找机构信息
	 * 
	 * @param orgCode
	 * @return
	 */
	SysOrgVo findOrgByCode(String orgCode);

	/**
	 * 根据登录名获取资源信息
	 * 
	 * @param userName
	 * @return
	 */
	List<SecurityAuthority> findResByLoginId(String loginId);

	/**
	 * 查询出所有的资源
	 * 
	 * @return
	 */
	List<SysResourceVo> findFullRes();


	/**
	 * 查询系统时间
	 * @return
	 */
	String findSysTime();

}
