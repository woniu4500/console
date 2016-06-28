package com.jiuyv.common.template.render;

import com.jiuyv.common.template.RenderType;


public class TimeRender implements RenderType {

	@Override
	public String render(String value) {
		if (value.length() == 14) {
			value=value
					.substring(0, 4)
					+ '-'
					+ value.substring(4, 6)
					+ '-'
					+ value.substring(6, 8)
					+ ' '
					+ value.substring(8, 10)
					+ ':'
					+ value.substring(10, 12)
					+ ':'
					+ value.substring(12);
		} 
		return value;
	}

}
