package com.toby.security.demo.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component	//声明为Bean
public class TimeInterceptor implements HandlerInterceptor {

	/**
	 * 控制器方法处理之前调用，注意这里的返回值必须返回true才会往下执行，否则控制器也不会执行
	 * 所以这个方法可以用来控制是否要调用后面的方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("拦截器方法=======preHandle");
		
		//下面的代码，如果对于文件上传的请求就会报错，因为是ResourceHttpRequestHandler类型的
//		System.out.println("控制器名称" + ((HandlerMethod)handler).getBean().getClass().getName());
//		System.out.println("控制器方法名" + ((HandlerMethod)handler).getMethod().getName());
		
		request.setAttribute("startTime", new Date().getTime());
		
		return true;
	}

	/**
	 * 控制器方法执行之后调用，抛异常时不会调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("拦截器方法=======postHandle");
		long start = Long.parseLong(request.getAttribute("startTime").toString());
		long times = new Date().getTime() - start;
		System.out.println("time interceptor 耗时：" + times);
	}

	/**
	 * 无论控制器是否抛异常，方法执行之后都会调用此方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("拦截器方法=======afterCompletion");
		long start = Long.parseLong(request.getAttribute("startTime").toString());
		long times = new Date().getTime() - start;
		System.out.println("time interceptor 耗时：" + times);
		System.out.println("异常：" + ex);
	}
}
