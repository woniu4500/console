package com.jiuyv.common.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jiuyv.common.json.INotConvert;


/**
 * The Class TreeNode.
 */
public class TreeNode implements INotConvert {

	/** 是否勾选的 */
	private boolean checked;
	
	/** The id. */
	private String id;

	/** The parent id. */
	private String parentId;

	/** The children. */
	private List<TreeNode> children;

	/** The text. */
	private String text;

	/** The node show name. */
	private String nodeShowName;

	/** The leaf. */
	private boolean leaf;

	/** The cls. */
	private String cls;

	/** The href. */
	private String href;

	/** The otherproperty. */
	private Object otherproperty;

	/** The icon cls. */
	private String iconCls;

	private String sort;
	
	private String showType ; 
	/** 定义长度 */
	private static final int THELENGTH = 17;

	/**
	 * Gets the icon cls.
	 * 
	 * @return the icon cls
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * Sets the icon cls.
	 * 
	 * @param iconCls
	 *            the new icon cls
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the parent id.
	 * 
	 * @return the parent id
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 * 
	 * @param parentId
	 *            the new parent id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 * 
	 * @param children
	 *            the new children
	 */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Checks if is leaf.
	 * 
	 * @return true, if is leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * Sets the leaf.
	 * 
	 * @param leaf
	 *            the new leaf
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * Gets the cls.
	 * 
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}

	/**
	 * Sets the cls.
	 * 
	 * @param cls
	 *            the new cls
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}

	/**
	 * Gets the href.
	 * 
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Sets the href.
	 * 
	 * @param href
	 *            the new href
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Gets the otherproperty.
	 * 
	 * @return the otherproperty
	 */
	public Object getOtherproperty() {
		return otherproperty;
	}

	/**
	 * Sets the otherproperty.
	 * 
	 * @param otherproperty
	 *            the new otherproperty
	 */
	public void setOtherproperty(Object otherproperty) {
		this.otherproperty = otherproperty;
	}


	/**
	 * Gets the node show name.
	 * 
	 * @return the node show name
	 */
	public String getNodeShowName() {
		return nodeShowName;
	}

	/**
	 * Sets the node show name.
	 * 
	 * @param nodeShowName
	 *            the new node show name
	 */
	public void setNodeShowName(String nodeShowName) {
		this.nodeShowName = nodeShowName;
	}

	/**
	 * 覆盖的toString方法.
	 * 
	 * @return 字符串
	 */
	public String toString() {
		String ss = JSONObject.fromObject(this).toString();
		int ii = 0;
		int jj = 0;
		int startpos = 0;
		StringBuffer buffer = new StringBuffer();
		while (true) {
			ii = ss.indexOf("\"otherproperty\":{", startpos);
			if (ii < 0) {
				buffer.append(ss.substring(startpos));
				break;
			}
			jj = ss.indexOf("},", ii);

			buffer.append(ss.substring(startpos, ii));
			buffer.append(ss.substring(ii + THELENGTH, jj));

			startpos = jj + 1;
		}
		String retStr = new String(buffer);
		// 單列目錄樹拼寫的數據，最後需要加入兩個[]
		retStr = "[" + retStr + "]";
		return retStr;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	// jstree attributes
	public String getData() {
		return this.text;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("id", this.getId());
		attributes.put("check", this.isChecked());
		attributes.put("iclass", "0".equals(this.showType)?"menu":"button");
		return attributes;
	}
	
}
