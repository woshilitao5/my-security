package com.toby.security.browser.process;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.toby.security.core.properties.SecurityProperties;

/**
 * 
 * @author Toby.li
 */
@RestController
public class BrowserSecurityController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	// Security在触发认证失败要跳转之前会先将原来的请求信息放到这个RequestCache缓存里，以便登录成功后跳转到正确的url
	// 所以可以用来判断原请求是html还是json
	private RequestCache requestCache = new HttpSessionRequestCache();
	// String中用来页面跳转的工具类
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 返回状态码为401认证失败
	// 因为能进来这个方法就肯定是没有登录的，这里指定状态码401就是为了告诉前端当前没有登录
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("==========");
		logger.info("配置获取页面" + securityProperties.getBrowser().getLoginPage());
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();// 原来请求的url
			logger.info("引发跳转的请求是：" + targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				// 原请求是html，所以这里跳转到登录页
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("需要认证，请前端引导用户到登录页面");
	}
}
