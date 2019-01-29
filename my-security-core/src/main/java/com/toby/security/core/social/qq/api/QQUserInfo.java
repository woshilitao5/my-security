package com.toby.security.core.social.qq.api;

/**
 * 封装QQ获取用户接口获取到的用户信息（字段对应官方接口返回的字段）
 * 这里是当前最新的qq互联的api字段封装
 * @author Randy
 * Created: 2019年1月17日
 */
public class QQUserInfo {
	/**
	 * 返回码
	 */
	private String ret;
	/**
	 * 如果ret<0，相应的错误信息提示
	 */
	private String msg;
	/**
	 * 用户在QQ空间的昵称
	 */
	private String nickname;
	/**
	 * 大小为30×30像素的QQ空间头像URL
	 */
	private String figureurl;
	/**
	 * 大小为50×50像素的QQ空间头像URL
	 */
	private String figureurl_1;
	/**
	 * 大小为100×100像素的QQ空间头像URL
	 */
	private String figureurl_2;
	/**
	 * 大小为40×40像素的QQ头像URL，所有用户一定都有
	 */
	private String figureurl_qq_1;
	/**
	 * 大小为100×100像素的QQ头像URL，不是所有的用户都有
	 */
	private String figureurl_qq_2;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * @return ret
	 */
	public String getRet() {
		return ret;
	}
	/**
	 * @param ret 要设置的 ret
	 */
	public void setRet(String ret) {
		this.ret = ret;
	}
	/**
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg 要设置的 msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname 要设置的 nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return figureurl
	 */
	public String getFigureurl() {
		return figureurl;
	}
	/**
	 * @param figureurl 要设置的 figureurl
	 */
	public void setFigureurl(String figureurl) {
		this.figureurl = figureurl;
	}
	/**
	 * @return figureurl_1
	 */
	public String getFigureurl_1() {
		return figureurl_1;
	}
	/**
	 * @param figureurl_1 要设置的 figureurl_1
	 */
	public void setFigureurl_1(String figureurl_1) {
		this.figureurl_1 = figureurl_1;
	}
	/**
	 * @return figureurl_2
	 */
	public String getFigureurl_2() {
		return figureurl_2;
	}
	/**
	 * @param figureurl_2 要设置的 figureurl_2
	 */
	public void setFigureurl_2(String figureurl_2) {
		this.figureurl_2 = figureurl_2;
	}
	/**
	 * @return figureurl_qq_1
	 */
	public String getFigureurl_qq_1() {
		return figureurl_qq_1;
	}
	/**
	 * @param figureurl_qq_1 要设置的 figureurl_qq_1
	 */
	public void setFigureurl_qq_1(String figureurl_qq_1) {
		this.figureurl_qq_1 = figureurl_qq_1;
	}
	/**
	 * @return figureurl_qq_2
	 */
	public String getFigureurl_qq_2() {
		return figureurl_qq_2;
	}
	/**
	 * @param figureurl_qq_2 要设置的 figureurl_qq_2
	 */
	public void setFigureurl_qq_2(String figureurl_qq_2) {
		this.figureurl_qq_2 = figureurl_qq_2;
	}
	/**
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender 要设置的 gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
}
