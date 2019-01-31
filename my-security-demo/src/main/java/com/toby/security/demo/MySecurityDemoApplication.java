package com.toby.security.demo;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Demo启动程序
 * @author Toby.li
 */
@SpringBootApplication
@ComponentScan(basePackages="com.toby.security")	//保证被引用的子模块的组件也能被正常扫描，否则无法用来注入
@EnableSwagger2	//启用Swagger2文档
public class MySecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySecurityDemoApplication.class, args);
	}
}
