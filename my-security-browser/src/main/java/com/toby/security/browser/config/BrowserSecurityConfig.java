package com.toby.security.browser.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.toby.security.core.authentication.FormAuthenticationConfig;
import com.toby.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.toby.security.core.properties.SecurityConstants;
import com.toby.security.core.properties.SecurityProperties;
import com.toby.security.core.validate.code.ValidateCodeFilter;
import com.toby.security.core.validate.code.ValidateCodeSecurityConfig;

/**
 * 
 * @author Toby.li
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private DataSource dataSource; // 配置了数据库后，Spring会自动注入一个dataSource进来
	@Autowired
	private UserDetailsService userdetailsService;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private FormAuthenticationConfig formAuthenticationConfig;
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * SpringSecurity操作数据库来读写token所需要的Bean
	 * JdbcTokenRepositoryImpl中有创建表的sql，可以手动复制语句去执行
	 * 
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 表单登录相关配置挪到这里了，就是之前的http.formLogin()...封装到了单独的配置文件
		formAuthenticationConfig.configure(http);

		// 下面代码中authorizeRequests()前面的部分相当于认证的设置（form认证还是httpBasic认证），后面的部分相当于授权的设置（什么权限能访问什么）
		http.apply(validateCodeSecurityConfig)	//校验码相关配置（包括短信校验码、图片校验码）
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)	//短信登录相关配置
				.and()
			.rememberMe()	//记住我相关配置
				.tokenRepository(persistentTokenRepository()) // 这几句是rememberMe相关
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userdetailsService)
				.and()
			.authorizeRequests() // 对请求进行授权操作（表示下面开始都是授权的配置）
				.antMatchers(
						SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,	//需要身份认证时，默认跳转的url，或叫登录页
						SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,//手机验证码登录提交的url
						securityProperties.getBrowser().getLoginPage(),	//客户端配置的自定义登录页面
						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",//获取校验码的url
						"/error")	//将错误页面授权，否则错误无法跑出去
						.permitAll()	//放权
				.anyRequest() // 任何请求（需要认证的范围）
				.authenticated() // 需要身份认证才能访问（限制条件）
				.and()
			.csrf().disable() // 暂时先禁用csrf攻击防护，后面完善后再放开
		;
	}
}
