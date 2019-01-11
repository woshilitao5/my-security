package com.toby.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Toby.li
 */
@ConfigurationProperties(prefix="my.security")//表示这个类会读取属性文件中以my.security开头配置，自动注入到当前类中的同名属性中
@Component	//声明为Bean，方便其他类来使用，引入spring-boot-configuration-processor包就不用额外创建一个配置来
public class SecurityProperties {
	//表示my.security.browser 开头的属性都会注入到BrowserProperties类中（browser就是实例名跟	属性文件的配置名，必须一致才能关联起来）
	private BrowserProperties browser = new BrowserProperties();//实例名必须与属性名一致
	
	private ValidateCodeProperties code;

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}
}
