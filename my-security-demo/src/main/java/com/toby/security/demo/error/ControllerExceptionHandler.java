package com.toby.security.demo.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(UserNotExistException.class) // 设置此方法处理哪个类型的异常
	@ResponseBody // 将返回结果处理成Json格式
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 此异常返回时的状态码，这里就是500服务器内部错误
	public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
		System.out.println("=控制器通知==异常处理===");
		Map<String, Object> result = new HashMap<>();
		result.put("id", ex.getId());
		result.put("message", ex.getMessage());
		return result;
	}
}
