package com.toby.security.core.entity;

public interface IUser {
	Long getId();
	void setId(Long id);
	String getUserName();
	void setUserName(String userName);
	String getRolename();
	void setRolename(String rolename);
	String getPassword();
	void setPassword(String password);
}
