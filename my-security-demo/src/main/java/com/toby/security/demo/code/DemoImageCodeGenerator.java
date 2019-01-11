package com.toby.security.demo.code;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.toby.security.core.validate.code.ValidateCodeGenerator;
import com.toby.security.core.validate.code.image.ImageCode;

/**
 * 
 * @author Toby.li
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ImageCode generate(ServletWebRequest request) {
		logger.info("==覆盖了默认的验证码生成器==");
		return null;
	}

}
