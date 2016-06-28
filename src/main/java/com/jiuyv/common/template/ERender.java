package com.jiuyv.common.template;


/**
 * The Enum ERender.
 *

 * @author 
 * @since 2014-5-26 11:08:36
 * @version 1.0.0
 */
public enum ERender implements RenderType{

	 /** 日期类型. */
 	date (new com.jiuyv.common.template.render.DateRender())
	,
	/** 金额类型. */
	money(new com.jiuyv.common.template.render.MoneyRender())
	,
	/** 百分比类型. */
	rate (new com.jiuyv.common.template.render.RateRender())
	,
	/** 时间类型. */
	time (new com.jiuyv.common.template.render.TimeRender())
	,
	/** 人民币类型. */
	rmb (new com.jiuyv.common.template.render.ChineseMoneyRender()) ;
	
	/** The Render type type. */
	private RenderType type ;
	
	/**
	 * Instantiates a new e render.
	 *
	 * @param type the type
	 */
	private ERender (RenderType type) {
		this.type = type;
	}
	
	public RenderType getType(){
		return this.type;
	}
	
	/**
	 * @param value
	 * @return
	 * @see com.jiuyv.common.template.RenderType#render(java.lang.String)
	 */
	@Override
	public String render(String value) {
		return this.type.render(value);
	}
	
}
