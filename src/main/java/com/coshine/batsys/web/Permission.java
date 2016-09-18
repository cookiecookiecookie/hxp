package com.coshine.batsys.web;

public enum Permission {
	
	ROOT_LOGIN("员工安全登录"), 
	ROOT_LOGOUT("员工安全退出"),
	ROOT_AUTH_FAIL("操作授权失败"),
	
	NEED_AUTH("需要授权"),
	;//TODO THE END
 
	private String description;

	Permission(String description) {
		this.description = description;
	}
	
	public String description() {
		return description;
	}
}
