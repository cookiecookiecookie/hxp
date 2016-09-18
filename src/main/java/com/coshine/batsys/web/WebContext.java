package com.coshine.batsys.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.coshine.batsys.util.Requests;

final class WebContext {
	
	public static final String ACTION_SUCCESS_TIP = "__ACTION_SUCCESS_TIP__";
	public static final String ACTION_FAILURE_TIP = "__ACTION_FAILURE_TIP__";
	
	public static final String GUEST_PERMISSIONS = ",ROOT_LOGIN,ROOT_LOGOUT,";
	
	/**
	 * 根据提交的参数前缀获取请求值并转换成 Map 返回，转换过程中会删除参数前缀
	 * 
	 * @param perifx 参数的前缀
	 * @return Map
	 */
	public static Map<String, String> getParametersMap(String perifx) {
		HttpServletRequest req = Requests.getRequest();
		Map<String, String> params = new HashMap<>();
		Enumeration<String> names = req.getParameterNames();
		String name = null;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith(perifx)) {
				params.put(name.substring(perifx.length() + 1), req.getParameter(name));
			}
		}
		return params;
	}

	public static boolean checkPermission(final String permission) {
		String fn = "," + permission + ",";
		if (WebContext.GUEST_PERMISSIONS.indexOf(fn) >= 0) {
			return true;
		}
		Object user = Requests.getSession().getAttribute("user");
		return null != user;
	}
	
	public static String loggedUser() {
		Object user = Requests.getSession().getAttribute("user");
		return null == user ? null : (String) user;
	}
}
