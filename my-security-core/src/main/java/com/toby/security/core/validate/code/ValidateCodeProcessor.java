package com.toby.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * @author Toby.li
 */
public interface ValidateCodeProcessor {
	/**
	 * 创建校验码
	 * 
	 * @param request	名字虽然叫request，但是ServletWebRequest里面封装了request和response两个对象
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;
	
	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);
}
