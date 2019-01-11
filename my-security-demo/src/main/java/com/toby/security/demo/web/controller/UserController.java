package com.toby.security.demo.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.toby.security.demo.dto.UserQueryCondition;
import com.toby.security.demo.entity.User;
import com.toby.security.demo.error.UserNotExistException;
import com.toby.security.demo.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@ApiOperation("根据ID查询用户信息")
	@GetMapping(value="/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getUser(@ApiParam("用户ID") @PathVariable("id") Long id){
//		throw new UserNotExistException("500");
//		throw new RuntimeException("===手动抛出一个异常===");
		
		System.out.println("=====id==" + id);
		User oneuser = userService.findOne(id);
		System.out.println(ReflectionToStringBuilder.toString(oneuser, ToStringStyle.MULTI_LINE_STYLE));
		return oneuser;
	}
	
//	@GetMapping("/user")
//	public List<User> getAllUser(@RequestParam String userName){
//		System.out.println("1122===userName==" + userName);
//		return userService.findAll();
//	}
	
	@ApiOperation("根据条件对象查询所有用户信息")
	@GetMapping
	@JsonView(User.UserSimpleView.class)
	public List<User> getAllUser(UserQueryCondition condition
			,@PageableDefault(page=1,size=10,sort = {"userName","role"},direction=Sort.Direction.DESC) Pageable pageable){
//		System.out.println("===condition==" + condition.getUserName());
		//用apache的commons包中的反射工具打印对象
		System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
		//分页信息
		System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
//		pageable.getPageNumber();pageable.getPageSize();pageable.getSort();
		
		return userService.findAll();
	}
	
	@PostMapping
	public User addUser(@Valid @RequestBody User user) throws Exception{
		System.out.println("==后端日期==" + user.getBirthDay());
		System.out.println("===请求参数suser===");
		System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
		User updUser = userService.save(user);
		System.out.println("==后端日期=222=" + updUser.getBirthDay());
		if(updUser!=null && updUser.getId()>0) {
			return updUser;
		}else
			throw new Exception("新增失败");
	}
	
	@PutMapping(value="/{id:\\d+}")
	public User updateUser(@Valid @RequestBody User user, BindingResult result) throws Exception{
//		if(result.hasErrors()) {
//			result.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
//		}
		//下面将error强化一下，将字段名也显示出来（如果在校验注解上使用message属性指定了异常信息就没必要用下面的方式，上面的即可）
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(error -> {
				FieldError fieldError = (FieldError)error;
				String message = fieldError.getField() + ": " + error.getDefaultMessage();
				System.out.println(message);
			});
		}
		
		System.out.println("=============继续执行了么================");
		User updUser = userService.save(user);
		return updUser;
	}
	
	@DeleteMapping(value="/{id:\\d+}")
	public void delete(@PathVariable Long id) throws Exception{
		userService.deleteById(id);;
	}
}
