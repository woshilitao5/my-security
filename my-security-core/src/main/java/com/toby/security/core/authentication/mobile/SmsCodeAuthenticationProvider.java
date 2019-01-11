package com.toby.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 
 * @author Toby.li
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	private UserDetailsService userdetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;
		//这里是根据前面过滤器放入principal中的信息（手机号）来调用loadUserByUsername方法来读取用户信息
		//强转String不会报异常只是返回null，toString()则可能报空指针异常
		UserDetails user = userdetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());
		
		if(user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		//如果成功读到了用户信息，就构造一个认证成功的Token
		SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserdetailsService() {
		return userdetailsService;
	}

	public void setUserdetailsService(UserDetailsService userdetailsService) {
		this.userdetailsService = userdetailsService;
	}
}
