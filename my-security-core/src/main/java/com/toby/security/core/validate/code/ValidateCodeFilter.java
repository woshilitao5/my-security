package com.toby.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toby.security.core.properties.SecurityConstants;
import com.toby.security.core.properties.SecurityProperties;
import com.toby.security.core.validate.code.image.ImageCode;

/**
 * 校验验证码的过滤器
 * @author Toby.li
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	/**
	 * 系统配置信息（读取application.properties配置以及默认配置）
	 */
	@Autowired
	private SecurityProperties securityProperties;
	/**
	 * 校验码处理器的管理器（通过类型来获得具体的校验码处理器）
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	/**
	 * 存放所有需要校验验证码的url（key是url，value是验证码类型）
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 初始化要拦截的url配置信息，并都存放到Map里面
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

		// 将图片验证码对应的默认提交URL，以及配置文件中配置的需要验证码的URL给存放到urlMap中
		urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

		// 短信验证码对应的URL等放到urlMap中
		urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
	}

	/**
	 * 将系统中配置的需要校验验证码的URL及其校验码类型放入map
	 * 
	 * @param urlString
	 * @param type
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] curUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : curUrls) {
				urlMap.put(url, type);
			}
		}
	}

	/**
	 * 过滤器的真正校验逻辑在这里，合并以后主要根据校验码类型来判断
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//获取验证码类型
		ValidateCodeType type = getValidateCodeType(request);
		
		//根据验证码类型来获取对应的处理器，且validate校验方法放到通用类里，因为校验逻辑相同
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * 根据请求url获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
}
