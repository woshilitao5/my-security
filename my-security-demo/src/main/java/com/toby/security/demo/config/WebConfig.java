package com.toby.security.demo.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.toby.security.demo.web.filter.TimeFilter;
import com.toby.security.demo.web.interceptor.TimeInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	TimeInterceptor timeInterceptor;
	
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {	//异步处理相关配置
//		configurer.setTaskExecutor(taskExecutor)
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {	//Spring拦截器
//		registry.addInterceptor(timeInterceptor);
	}

//	@Bean
	public FilterRegistrationBean<Filter> timeFilter() {	//Filter
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);

		List<String> urls = new ArrayList<>();
		urls.add("/*"); // 添加生效的URL
		registrationBean.setUrlPatterns(urls);
		return registrationBean;
	}
}
