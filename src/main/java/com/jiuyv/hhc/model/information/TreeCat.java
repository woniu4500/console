package com.jiuyv.hhc.model.information;

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
public class TreeCat extends CmArtCatVo implements TreeObject<TreeCat>  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7868462996804307525L;

	/** The children. */
	private List<TreeCat> children ;

	/** The level. */
	private int level ; 
	
	/** The leaf. */
	private boolean leaf ; 
	
	/**
	 * Instantiates a new tree org.
	 */
	public TreeCat(){
		super();
		this.children = new ArrayList<TreeCat>();
		this.leaf = true; 
	}
	
	/**
	 * Adds the.
	 *
	 * @param child the child
	 */
	public void add(TreeCat child) {
		if ( child == null ) {
			return ;
		}
		if ( this.children == null ) {
			this.children = new ArrayList<TreeCat>();
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
	public List<TreeCat> getChildren() {
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
	
	/**
	 * Get catSeq : 内部序号. CAT_SEQ: NUMBER(18) 
	 */
	public String getNodeId(){
		return String.valueOf(getCatSeq());	
	}

	public String getParentId(){
		return String.valueOf(getCatParent());	
	}

}
