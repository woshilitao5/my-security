package com.toby.security.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})	//@Target表示注解可以用在什么位置上，比如包、类、方法、字段
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=MyConstraintValidator.class)
public @interface MyConstraint {
	//没有设置default属性的话，使用时就必须要赋值，否则无法使用
	String message() default "数据校验失败";	//这里可以看注解的主要用途给一个默认消息，在用注解时没有指定message属性时会用默认的

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
