package com.toby.security.core.social.qq.api;

/**
 * 用来封装QQ官方API调用的接口
 * @author Randy
 * Created: 2019年1月17日
 */
public interface QQ {
	QQUserInfo getUserInfo() throws Exception;
}
