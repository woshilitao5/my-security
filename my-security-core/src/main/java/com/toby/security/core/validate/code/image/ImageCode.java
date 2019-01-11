package com.toby.security.core.validate.code.image;

import java.awt.image.BufferedImage;

import com.toby.security.core.validate.code.ValidateCode;

/**
 * 验证码信息的封装
 * @author Toby.li
 */
public class ImageCode extends ValidateCode {
	private static final long serialVersionUID = 2777447012956963498L;
	
	private BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, int expireSecond) {
		super(code, expireSecond);
		this.image = image;
	}
	
	/**
	 * 生成的验证码图片
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
