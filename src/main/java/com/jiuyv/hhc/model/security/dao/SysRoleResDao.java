package com.jiuyv.hhc.model.security.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.security.SysRoleResVo;

// Generated by AutoCode4J
/**
 * Interface: 控制台角色权限关系表 : TBL_SYS_ROLE_RES
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface SysRoleResDao extends BaseDao<SysRoleResVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.security.dao.SysRoleResDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.security.dao.SysRoleResDao.filterQuery";

	/**
	 * Find SysRoleResVo by Primary Key. 
	 * 
	 * @param roleId : 角色ID
	 * @param resId : 资源ID	 
	 * @return SysRoleResVo : 控制台角色权限关系表
	 */
	SysRoleResVo findByKey(String roleId , String resId ) ;
	
	/**
	 * 根据RoleId删除数据
	 * @param roleId
	 */
	void deleteByRole(Long roleId);



}