package com.toby.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @author Toby.li
 */
public class ValidateCode implements Serializable{
	private static final long serialVersionUID = -3331904491259683014L;
	
	private String code;
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, int expireSecond) {
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireSecond);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime) {
		this.code = code;
		this.expireTime = expireTime;
	}
	
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}
	
	/**
	 * 验证码的内容
	 * @return
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 过期的时间点，这个点之前的时间没过期，否则就过期了
	 * @return
	 */
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
}
