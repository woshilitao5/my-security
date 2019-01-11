package com.toby.security.core.service;

import com.toby.security.core.entity.IUser;

/**
 * 这里不实现用户信息的查询，让客户端去实现
 * 客户端对用户信息的数据库操作的service必须实现此类，这样就能够通用了
 * @author Toby.li
 */
public interface IUserService {
	IUser findUserByUserName(String userName);
	IUser findUserByMobile(String mobile);
}
