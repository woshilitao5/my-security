package com.toby.security.demo.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.toby.security.demo.error.UserNotExistException;

//@Aspect
//@Component
public class TimeAspect {
	/**
	 * 处理控制器计时的AOP通知（方法）
	 * 
	 * @param pjp：@Around必需带且必须第一个的参数
	 * @return：被切入方法的返回值，可以处理后再返回，就相当于改变了目标方法的返回值
	 * @throws Throwable
	 */
	@Around("execution(* com.toby.security.demo.web.controller.UserController.*(..))") // 表示切入UserController类下面的所有方法
	public Object doControllerTiming(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("time aspect start");
		long start = new Date().getTime();

		Object[] args = pjp.getArgs(); // 所有参数
		for (Object arg : args) {
			System.out.println("参数是：" + arg);
		}
		
		Object retVal;
		try {
			retVal = pjp.proceed(); // 真正执行被切入的方法，不调用这个，目标方法都不会执行，返回值就是目标方法的返回值
		}catch(UserNotExistException ex) {
			System.out.println("time aspect throw exception");
			throw ex;
		}
		long end = new Date().getTime();
		System.out.println("time aspect 耗时：" + (end - start));
		return retVal;
	}
}
