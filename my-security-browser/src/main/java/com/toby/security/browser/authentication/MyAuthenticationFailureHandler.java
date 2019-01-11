package com.toby.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toby.security.browser.process.SimpleResponse;
import com.toby.security.core.properties.LoginType;
import com.toby.security.core.properties.SecurityProperties;

/**
 * 
 * @author Toby.li
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ObjectMapper objectMapper;// SpringMVC自动生成的一个object转json的工具类

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
//		logger.info("有一个认证失败了");
		if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 返回500异常
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}
}
