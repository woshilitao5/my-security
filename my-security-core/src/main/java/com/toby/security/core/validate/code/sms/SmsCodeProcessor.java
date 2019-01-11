package com.toby.security.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.toby.security.core.validate.code.ValidateCode;
import com.toby.security.core.validate.code.impl.AbstractValidateCodeProcessor;

/**
 * 短信验证码处理器
 * @author Toby.li
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	/**
	 * 直接通过短信验证码的发送器来发送短信
	 */
	@Override
	protected void send(ServletWebRequest request, ValidateCode smsCode) throws Exception {
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		smsCodeSender.send(mobile, smsCode.getCode());
	}

}
