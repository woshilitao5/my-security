package com.toby.security.demo.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

//@Component	//声明为Bean，然后过滤器就会自动生效，否则此过滤器不会生效
public class TimeFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("time filter start");
		
		long start = new Date().getTime();	//调用方法前记录开始时间
		chain.doFilter(request, response);	//执行控制器的方法（有其他拦截器也可能先执行其他拦截器）
		long end = new Date().getTime();	//记录结束时间
		System.out.println("time filter 耗时：" + (end - start));
		
		System.out.println("time filter finish");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("time filter init.");
	}

	@Override
	public void destroy() {
		System.out.println("time filter destroy.");
	}
}
