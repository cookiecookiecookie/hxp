package com.coshine.batsys.web;

import org.springframework.util.StringUtils;

public final class JspFunctions {

	public static boolean checkPermission(String permission) {
		return WebContext.checkPermission(permission);
	}
	
	public static boolean stringContains(String fullString, String unit) {
		return ("," + fullString + ",").indexOf("," + unit + ",") >= 0;
	}
	
	public static String calcGender(String idCard) {
		if (StringUtils.isEmpty(idCard)) {
			return "未知";
		}
		int size = idCard.length();
		int sex = Integer.parseInt(idCard.substring(size - 2, size - 1));
		return sex % 2 == 0 ? "女" : "男";
	}
}
