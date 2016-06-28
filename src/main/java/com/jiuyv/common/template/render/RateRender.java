package com.jiuyv.common.template.render;

import org.apache.commons.lang.StringUtils;

import com.jiuyv.common.template.RenderType;


public class RateRender implements RenderType {

	@Override
	public String render(String value) {
		if (StringUtils.isNumeric(value)) {
			value = String.valueOf((Double.valueOf(value) / 100.00))+"%";
		}
		return value;
	}

}
