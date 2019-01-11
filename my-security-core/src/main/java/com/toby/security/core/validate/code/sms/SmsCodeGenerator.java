package com.toby.security.core.validate.code.sms;

import java.util.Random;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.toby.security.core.properties.SecurityProperties;
import com.toby.security.core.validate.code.ValidateCode;
import com.toby.security.core.validate.code.ValidateCodeGenerator;

/**
 * 短信验证码生成器
 * @author Toby.li
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		Random random = new Random();
		
		String sRand = "";
		for (int i = 0; i < securityProperties.getCode().getSms().getLength(); i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		logger.info("确实根据bean名字进入到smsCodeGenerator中，随机数字是："+sRand);
		return new ValidateCode(sRand, securityProperties.getCode().getSms().getExpireSecond());
	}
}
