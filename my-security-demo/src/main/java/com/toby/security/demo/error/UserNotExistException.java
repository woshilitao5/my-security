package com.toby.security.demo.error;

public class UserNotExistException extends RuntimeException {
	private static final long serialVersionUID = -7257710642151025645L;

	public UserNotExistException(String id) {
		super("找不到用户异常");
		this.setId(id);
	}
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
