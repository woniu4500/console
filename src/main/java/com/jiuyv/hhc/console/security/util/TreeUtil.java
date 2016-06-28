package com.jiuyv.hhc.console.security.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import com.jiuyv.common.tree.TreeNode;
import com.jiuyv.common.tree.TreeObject;
import com.jiuyv.hhc.model.security.ResBean;
import com.jiuyv.hhc.model.security.SysResourceVo;

/**
 * The Class TreeUtil.
 *

 * @author 
 * @since 2014-1-9 13:01:23
 * @version 1.0.0
 */
public final class TreeUtil {

	/** Constant String DISP_MENU: String : 0. */
	public static final String DISP_MENU = "0";
	
	/** Constant String ROOT_NODE: String : -1. */
	public static final String ROOT_NODE = "-1";
	
	/** Constant String ROOT_TEXT: String : 系统. */
	public static final String ROOT_TEXT = "系统";

	/**
	 * Instantiates a new tree util.
	 */
	private TreeUtil() {
		
	}
	
	/**
	 * 根据.
	 *
	 * @param <T> the generic type
	 * @param node the node
	 * @param nodeId the node id
	 * @param rootNode the root node
	 * @return the list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends TreeObject> List<T> removeNode(Collection<T> node, String nodeId, T rootNode) {
		// form map. 
		Map<String, T> treeMap = new HashMap<String, T>();
		for ( T l: node) {
			treeMap.put(l.getNodeId(), l);
		}
		T root = treeMap.get(ROOT_NODE);
		if ( root == null ) {
			root = rootNode;
			treeMap.put(root.getNodeId(), root);
		}
		// form a tree without nodeId
		for ( T l : treeMap.values() ) {
			String fc = l.getParentId();
			if ( fc == null || fc.equals(l.getNodeId()) || l.getNodeId().equals(nodeId) ){
				continue;
			}
			T fo = treeMap.get(fc);
			if ( fo != null ) {
				fo.add(l);
			}
		}
		Set<String> orgIds = new HashSet<String>();
		List<T> res = new ArrayList<T>();
		LinkedList<T> q = new LinkedList<T>();
		q.add(root);
		while(!q.isEmpty()) {
			T t = q.poll();
			if ( orgIds.contains(t.getNodeId()) ) {
				continue ; 
			}
			orgIds.add(t.getNodeId());
			res.add(t);
			if( t.hasChild() ) {
				q.addAll(t.getChildren());
				t.cleanChild();
			}
		} 
		return res;
	}

	/**
	 * 构建资源树，返回 -1 的节点.
	 *
	 * @param resMap the res map
	 * @param authorities the authorities
	 * @return the tree node
	 */
	public static TreeNode buildResTree(Map<String, SysResourceVo> resMap,
			Collection<? extends GrantedAuthority> authorities) {
		if (resMap == null || authorities == null) {
			return null;
		}
		Map<String, TreeNode> pmap = new HashMap<String, TreeNode>();
		for (GrantedAuthority auth : authorities) {
			SysResourceVo res = resMap.get(auth.getAuthority());
			if (res != null && DISP_MENU.equals(res.getShowType())) {
				TreeNode tn = newTreeNode(res);
				pmap.put(res.getResId().toLowerCase(), tn);
			}
		}
		// Add root node
		TreeNode root = newTreeNode(ROOT_NODE, true, null, null, null, null);
		pmap.put(ROOT_NODE, root);
		for (Entry<String, TreeNode> e : pmap.entrySet()) {
			TreeNode n = e.getValue();
			String pid = n.getParentId();
			TreeNode pn = pmap.get(pid);
			if (pn != null) {
				pn.getChildren().add(n);
				pn.setLeaf(false);
			}
		}
		return root;
	}

	/**
	 * 根据资源Bean生成资源树.
	 *
	 * @param resMap the res map
	 * @param ress the ress
	 * @return the tree node
	 */
	public static TreeNode buildChkResTree(Map<String, SysResourceVo> resMap,
			Collection<? extends ResBean> ress) {
		if (resMap == null) {
			return null;
		}
		Map<String, TreeNode> pmap = new LinkedHashMap<String, TreeNode>();
		for (SysResourceVo res : resMap.values()) {
			if ( res != null ) {
				TreeNode tn = newTreeNode(res);
				pmap.put(res.getResId().toLowerCase(), tn);
			}
		}
		// Add root node
		TreeNode root = newTreeNode(ROOT_NODE, true, null, null, null, null);
		pmap.put(ROOT_NODE, root);
		if (ress != null) {
			for (ResBean res : ress) {
				TreeNode tn = pmap.get(res.getResId().toLowerCase());
				if (tn != null) {
					tn.setChecked(true);
				}
			}
		}
		// Build tree
		for (Entry<String, TreeNode> e : pmap.entrySet()) {
			TreeNode n = e.getValue();
			String pid = n.getParentId();
			TreeNode pn = pmap.get(pid);
			if (pn != null) {
				pn.getChildren().add(n);
				pn.setLeaf(false);
				// If childNode is checked , parentNode should also checked.
				if (n.isChecked() && !pn.isChecked()) {
					pn.setChecked(true);
				}
			}
		}
		return root;
	}

	/**
	 * 构建节点.
	 *
	 * @param id the id
	 * @param isleaf the isleaf
	 * @param parentId the parent id
	 * @param href the href
	 * @param text the text
	 * @param sort the sort
	 * @return the tree node
	 */
	public static TreeNode newTreeNode(String id, boolean isleaf,
			String parentId, String href, String text, String sort) {
		TreeNode n = new TreeNode();
		n.setId(id);
		n.setLeaf(isleaf);
		n.setParentId(parentId);
		n.setText(text);
		n.setHref(href);
		n.setSort(sort);
		n.setChildren(new ArrayList<TreeNode>());
		n.setChecked(false);
		return n;
	}

	/**
	 * 构建节点.
	 *
	 * @param vo the vo
	 * @return the tree node
	 */
	private static TreeNode newTreeNode(SysResourceVo vo) {
		TreeNode n = new TreeNode();
		n.setId(vo.getResId().toLowerCase());
		n.setLeaf(true);
		n.setParentId(vo.getFatherId().toLowerCase());
		n.setNodeShowName(vo.getResZh());
		n.setHref(furl(vo.getResUrl()));
		n.setText(vo.getResZh());
		n.setSort(String.valueOf(vo.getOrderNum()));
		n.setChildren(new ArrayList<TreeNode>());
		n.setChecked(false);
		n.setShowType(vo.getShowType());
		return n;
	}

	/**
	 * Furl.
	 *
	 * @param url the url
	 * @return the string
	 */
	private static String furl(String url) {
		if (StringUtils.isNotBlank(url)) {
			String nurl = url;
			if (url.charAt(0) == '/') {
				nurl = url.substring(1);
			}
			String[] urls = nurl.split(",");
			return StringUtils.trim(urls[0]);
		}
		return StringUtils.trim(url);
	}

}
