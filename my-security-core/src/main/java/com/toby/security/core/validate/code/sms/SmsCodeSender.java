package com.toby.security.core.validate.code.sms;

/**
 * 
 * @author Toby.li
 */
public interface SmsCodeSender {
	/**
	 * 用来发送短信的接口，方便客户端应用自定义配置发送短信的逻辑，来实现用不同的短信供应商来发短信
	 * @param mobile	要发送的手机号
	 * @param code	短信中的验证码
	 */
	void send(String mobile, String code);
}
