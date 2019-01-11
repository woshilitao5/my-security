package com.toby.security.core.validate.code;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.toby.security.core.validate.code.image.ImageCodeGenerator;
import com.toby.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.toby.security.core.validate.code.sms.SmsCodeSender;

/**
 * 
 * @author Toby.li
 */
@Configuration
public class ValidateCodeBeanConfig {
	/**
	 * 短信验证码发送器
	 */
	@Bean
	@ConditionalOnMissingBean(name="smsCodeSender")
	public SmsCodeSender smsCodeSender() {
		SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
		return smsCodeSender; 
	}
	
	/**
	 * 图片验证码图片生成器
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name="imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
		return imageCodeGenerator; 
	}
}
