package com.toby.security.core.properties;

/**
 * 
 * @author Toby.li
 */
public class SmsCodeProperties {
	private int length = 4;
	private int expireSecond = 60;
	private String url = "";
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getExpireSecond() {
		return expireSecond;
	}
	public void setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
