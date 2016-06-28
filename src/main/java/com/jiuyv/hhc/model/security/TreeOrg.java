package com.jiuyv.hhc.model.security;

import java.util.ArrayList;
import java.util.List;

import com.jiuyv.common.tree.TreeObject;

/**
 * The Class TreeOrg.
 *

 * @author 
 * @since 2014-1-2 16:47:18
 * @version 1.0.0
 */
public class TreeOrg extends SysOrgVo implements TreeObject<TreeOrg>  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7868462996804307525L;

	/** The children. */
	private List<TreeOrg> children ;

	/** The level. */
	private int level ; 
	
	/** The leaf. */
	private boolean leaf ; 
	
	/**
	 * Instantiates a new tree org.
	 */
	public TreeOrg(){
		super();
		this.children = new ArrayList<TreeOrg>();
		this.leaf = true; 
	}
	
	/**
	 * Adds the.
	 *
	 * @param child the child
	 */
	public void add(TreeOrg child) {
		if ( child == null ) {
			return ;
		}
		if ( this.children == null ) {
			this.children = new ArrayList<TreeOrg>();
		}
		this.leaf = false; 
		this.children.add(child);
	}
	
	/**
	 * Clean child.
	 */
	public void cleanChild(){
		if ( this.children == null ) {
			return ; 
		}
		this.children.clear();
		this.leaf = true ; 
	}
	
	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<TreeOrg> getChildren() {
		return children;
	} 
	
	/**
	 * Checks for child.
	 *
	 * @return true, if successful
	 */
	public boolean hasChild() {
		if ( children != null && children.size() > 0 ) {
			return true; 
		}
		return false;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	public boolean isLeaf(){
		return this.leaf;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	public String getNodeId() {
		return getOrgCode();
	}

	public String getParentId() {
		return getFatherOrgCode();
	}

}
