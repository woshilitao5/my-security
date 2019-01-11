/**
 * 这个类跟图片验证码的过滤器因为代码重合度高，所以重构时整合成一个类：ValidateCodeFilter
 * 同时校验逻辑封装到ValidateCodeProcessorHolder
 * 然后校验码的存取功能封装到ValidateCodeRepository中
 */

//package com.toby.security.core.authentication.mobile;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.social.connect.web.HttpSessionSessionStrategy;
//import org.springframework.social.connect.web.SessionStrategy;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.bind.ServletRequestBindingException;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.toby.security.core.properties.SecurityProperties;
//import com.toby.security.core.validate.code.ValidateCode;
//import com.toby.security.core.validate.code.ValidateCodeController;
//import com.toby.security.core.validate.code.ValidateCodeException;
//import com.toby.security.core.validate.code.image.ImageCode;
//
///**
// * 
// * @author Toby.li
// */
//@Component("smsCodeFilter")
//public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
//	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();// 这个不需要设置，构造的时候就拿到了要的
//	
//	public static final String SESSION_KEY_PREFIX = "SESSION_KEY_CODE_SMS";
//
//	@Autowired
//	private AuthenticationFailureHandler authenticationFailureHandler;
//
//	@Autowired
//	private SecurityProperties securityProperties;
//	
//	private AntPathMatcher pathMatcher = new AntPathMatcher();
//
//	private Set<String> urls = new HashSet<>();
//
//	@Override
//	public void afterPropertiesSet() throws ServletException {
//		super.afterPropertiesSet();
//		String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(), ",");
//		for(String url : configUrls) {
//			urls.add(url);
//		}
//		urls.add("/authentication/mobile");
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		boolean action = false;
//		for(String url : this.urls) {
//			if(pathMatcher.match(url, request.getRequestURI())) {
//				action = true;
//			}
//		}
////		if (StringUtils.equals("/authentication/form", request.getRequestURI())
////				&& StringUtils.equalsIgnoreCase("post", request.getMethod())) {
//		if(action) {
//			try {
//				validate(new ServletWebRequest(request));
//			} catch (ValidateCodeException ex) { // 之所以定义一个ValidateCodeException，主要是为了方便定位异常，跟Security自带的区分
//				authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
//				return;// 这里验证失败就不往下执行了，不然验证失败还往后继续验证浪费时间
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
//		ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request, SESSION_KEY_PREFIX);
//		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
//
//		if (StringUtils.isBlank(codeInRequest)) {
//			throw new ValidateCodeException("验证码不能为空");
//		}
//		if (codeInSession == null) {
//			throw new ValidateCodeException("验证码不存在");
//		}
//		if (codeInSession.isExpried()) {
//			throw new ValidateCodeException("验证码已经过期");
//		}
//		if (!StringUtils.equals(codeInRequest, codeInSession.getCode())) {
//			throw new ValidateCodeException("验证码输入错误");
//		}
//		sessionStrategy.removeAttribute(request, SESSION_KEY_PREFIX);
//	}
//
//	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
//		return authenticationFailureHandler;
//	}
//
//	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
//		this.authenticationFailureHandler = authenticationFailureHandler;
//	}
//
//	public SecurityProperties getSecurityProperties() {
//		return securityProperties;
//	}
//
//	public void setSecurityProperties(SecurityProperties securityProperties) {
//		this.securityProperties = securityProperties;
//	}
//}
