package com.toby.security.demo.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.toby.security.demo.entity.User;
import com.toby.security.demo.service.UserService;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {
	@Autowired
	private UserService userService;

	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		//初始化的方法，视需要可以什么都不写
		System.out.println("===开始校验===");
	}

	/**
	 * 返回值：true表示校验通过，False表示校验失败
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return false;
		//可以做任何事情，包括调用service来从数据库获取数据
		List<User> user = userService.findByUserName(value);
		if(user != null && user.size()>0)
			return false;	//数据库已存在就表示 校验失败
		else
			return true;
	}
}
