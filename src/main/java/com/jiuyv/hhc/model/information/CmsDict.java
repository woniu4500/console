package com.jiuyv.hhc.model.information;

public interface CmsDict {
	
	interface BaseStatus {
		/** 00:初稿. */
		String FIRST_DRAFT = "00";
		/** 01:草稿. */
		String DRAFT = "01";
		/** 02:已删除. */
		String DELETE = "02";
		/** 03:提交. */
		String SUBMIT = "03";
		/** 04:发布. */
		String ISSUE = "04";
		/** 05:驳回. */
		String REJECT = "05";
		/** 12:待删除 */
		String TO_DELETE = "12";
	}
	
	interface CatStatus extends BaseStatus {}
	
	interface ArtStatus extends BaseStatus {
	
	}

	interface FrdStatus extends BaseStatus {}
	
	interface NaviStatus extends BaseStatus {}
}
