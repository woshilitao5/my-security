package com.toby.security.demo.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryCondition {
	@ApiModelProperty("用户名")
	private String userName;
	@ApiModelProperty("角色")
	private String role;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
