package com.jiuyv.hhc.console.security.service;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jiuyv.common.database.AssistService;
import com.jiuyv.common.encode.DES;
import com.jiuyv.hhc.model.security.SecurityAuthority;
import com.jiuyv.hhc.model.security.SecurityUserDetail;
import com.jiuyv.hhc.model.security.TreeOrg;
import com.jiuyv.hhc.model.security.dao.ISecurityDao;
import com.jiuyv.hhc.model.security.dao.SysOrgDao;


/**
 * The Class UserDetailsServiceImpl.
 *

 * @author 
 * @since 2014-1-2 16:36:37
 * @version 1.0.0
 */
public class UserDetailsServiceImpl extends AssistService implements UserDetailsService {
	
	/** Security DAO. */
	private ISecurityDao securityDao ; 
	
	/**
	 * @param userName
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String userName) {
		if (userName == null || "".equals(userName.trim())) {
			throw new BadCredentialsException("用户名不能为空");
		}
		try {
			if (userName.getBytes("UTF-8").length > 10) {
				throw new UsernameNotFoundException("用户名过长");
			}
		} catch (UnsupportedEncodingException e1) {
			throw new UsernameNotFoundException("用户名编码出错", e1);
		}
		// 首先找到符合登陆id的用户的资料，
		// 密码判断是由spring security 通过读出user内加密过的密码自行比较的，无需再写代码判断
		List<SecurityUserDetail> users = securityDao.findUserByLoginId( userName ); 
		if (users.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(
					"More than one user found with name '" + userName + "'", 1);
		}
		if (users.size() == 0) {
			throw new UsernameNotFoundException("no user found with name '" + userName + "'");
		}
		SecurityUserDetail user = users.get(0);
		// 如果自行设置加密算法（除了spring security的md5 ，md4等）
		// 需在这里要将user的密码域解密，供spring
		// security验证
		String password=null;
		try {
			password = DES.decrypt(user.getLoginPasswd());
		} catch (Exception e) {
			throw new UsernameNotFoundException("密码加密错误!",e);
		}
		user.setLoginPasswd(password);
	
		// 得到用户可以访问的资源列表，供安全的http url过滤使用
		List<SecurityAuthority> authorites = securityDao.findResByLoginId( userName );
		user.setAuthorites( authorites );
		// 得到用户的机构及其后代的机构列表
		user.setChildOrgId(childOrgId(user.getOrgCode()));
		// 设置权限信息
		return user;
	}
	
	/**
	 * Child org id.
	 *
	 * @param nodeId the node id
	 * @return the collection
	 */
	private Collection<String> childOrgId(String nodeId){
		Map<String, TreeOrg> resMap = getQueryAssist().map(SysOrgDao.MAPPED_TREEORG_FIND, null, null, null, "orgCode");
		// Build tree
		for (TreeOrg org : resMap.values() ) {
			String parentId = org.getFatherOrgCode();
			if ( parentId != null && parentId.equals(org.getOrgCode())) {
				// skip when parentId is the same with id 
				continue; 
			}
			TreeOrg porg = resMap.get(parentId);		
			if ( porg != null ) {
				porg.add(org);
			}
		}
		TreeOrg norg = resMap.get(nodeId);
		Set<String> orgIds = new LinkedHashSet<String>();
		if ( norg == null ) {
			orgIds.add(nodeId);
			return orgIds;
		}
		// Fetch all orgIds 
		LinkedList<TreeOrg> q = new LinkedList<TreeOrg>();
		q.add(norg);
		while(!q.isEmpty()) {
			TreeOrg t = q.poll();
			if ( orgIds.contains(t.getOrgCode()) ) {
				// skip processed org.
				continue ; 
			}
			orgIds.add(t.getOrgCode());
			if( t.hasChild() ) {
				q.addAll(t.getChildren());
			}
		}
		return orgIds; 
	}

	/**
	 * 设置 security DAO.
	 *
	 * @param securityDao the new security DAO
	 */
	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

}
