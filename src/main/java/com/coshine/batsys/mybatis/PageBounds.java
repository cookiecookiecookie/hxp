package com.coshine.batsys.mybatis;

import org.apache.ibatis.session.RowBounds;

public class PageBounds extends RowBounds {

	public PageBounds() {
		super();
	}

	public PageBounds(Integer offset, Integer limit) {
		super(parseInt(offset, 1), parseInt(limit, 20));
	}

	private static int parseInt(Integer value, int defaultValue) {
		return value == null || value == 0 ? defaultValue : value;
	}
	
}
