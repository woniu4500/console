package com.jiuyv.hhc.model.security.dao ;

import com.jiuyv.common.database.BaseDao;
import com.jiuyv.hhc.model.security.SysUserRoleVo;

/**
 * Interface: 用户角色关系表 : TBL_SYS_USER_ROLE
 * This is a interface. 
 * Description: 
 * 
 * @author AutoCode4J
 * @version 1.0
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleVo> {
	
	/** mapped key is : com.jiuyv.hhc.model.security.dao.SysUserRoleDao.filterQuery */
	String MAPPED_FIND = "com.jiuyv.hhc.model.security.dao.SysUserRoleDao.filterQuery";

	/**
	 * Find SysUserRoleVo by Primary Key. 
	 * 
	 * @param userId : 用户id
	 * @param roleId : 角色ID	 
	 * @return SysUserRoleVo : 用户角色关系表
	 */
	SysUserRoleVo findByKey(String userId , String roleId ) ;
	
	/**
	 * 根据userId删除数据
	 * @param userId
	 */
	void deleteByUser(Long userId);



}