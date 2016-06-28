package com.jiuyv.common.tree;

import java.util.List;

/**
 * The Interface TreeObject.
 *
 * @param <T> the generic type

 * @author 
 * @since 2014-5-26 10:18:18
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public interface TreeObject<T extends TreeObject> {
	
	/**
	 * Adds the.
	 *
	 * @param child the child
	 */
	void add(T child);
	
	/**
	 * Clean child.
	 */
	void cleanChild();
	
	/**
	 * 获取子节点列表.
	 *
	 * @return the children
	 */
	List<T> getChildren();
	
	/**
	 * 是否有子节点.
	 *
	 * @return true, if successful
	 */
	boolean hasChild();
	
	/**
	 * 是否为叶节点.
	 *
	 * @return true, if is leaf
	 */
	boolean isLeaf();
	
	/**
	 * 获取节点Id.
	 *
	 * @return the node id
	 */
	String getNodeId();
	
	/**
	 * 获取父节点Id.
	 *
	 * @return the parent id
	 */
	String getParentId();
	
	
}
