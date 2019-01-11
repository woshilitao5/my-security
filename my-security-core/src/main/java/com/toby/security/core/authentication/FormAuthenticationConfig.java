package com.toby.security.core.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.toby.security.core.properties.SecurityConstants;

/**
 * 表单登录配置
 * @author Toby.li
 */
@Component
public class FormAuthenticationConfig {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler myAuthenticationFailureHandler;
	
	public void configure(HttpSecurity http) throws Exception {
		logger.info("000000000000000000000000000000000000000000000000");
		http.formLogin()	// 使用表单登录，不设置参数的话，默认security会提供一个登录表单
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)	// 设置登录页面的默认url
			.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)//登录表单提交的URL
			.successHandler(myAuthenticationSuccessHandler)// 自定义登录成功的处理
			.failureHandler(myAuthenticationFailureHandler);// 自定义登录失败的处理
	}
}
