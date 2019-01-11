package com.toby.security.core.validate.code;

import javax.servlet.ServletRequest;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 
 * @author Toby.li
 */
public interface ValidateCodeGenerator {
	ValidateCode generate(ServletWebRequest request);
}
