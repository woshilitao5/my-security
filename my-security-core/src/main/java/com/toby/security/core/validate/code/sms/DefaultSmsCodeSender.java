package com.toby.security.core.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Toby.li
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void send(String mobile, String code) {
		logger.info("短信验证码：" + code + "  要发往的手机号：" + mobile);
	}

}
