package com.toby.security.demo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	@GetMapping("/hello")
	public String hello() {
//		TestQo qo = new TestQo();
//		qo.setMessage("Hello spring security, By Qo!");
		return "Hello spring security, By Qo!";
	}
}
