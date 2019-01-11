package com.toby.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;
/**
 * 校验码异常，主要用来方便标记是校验码这块报的异常，提高解决问题的效率
 * @author Toby.li
 */
public class ValidateCodeException extends AuthenticationException {
	private static final long serialVersionUID = -5706084401113983869L;

	/**
	 * @param msg
	 */
	public ValidateCodeException(String msg) {
		super(msg);
	}
}
