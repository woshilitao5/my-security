package com.toby.security.core.properties;

/**
 * 
 * @author Toby.li
 */
public class ValidateCodeProperties {
	private ImageCodeProperties image;
	private SmsCodeProperties sms;

	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}
}
