package com.toby.security.browser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.toby.security.core.service.IUserService;

/**
 * 
 * @author Toby.li
 */
@Component
public class DBUserDetailsService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(DBUserDetailsService.class);
	
	@Autowired
	IUserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("当前登录用户名：" + username);
		
		String password = passwordEncoder.encode("123456");
		logger.info("当前用户密码：" + password);
		
		//下面的4个boolean值参数分别代表：账户是否可用、是否账号没有过期、是否密码没有过期、是否账号没有锁定
		//只要有一个是false都会抛出响应的异常并返回前端进行提醒
//		return new User(username ,password , true, true, true, true, 
//				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		
		//参考User自定义一个User类
		return new DBUser(username ,password , true, true, true, true, 
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
