package com.toby.security.core.social.qq.api;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * QQ接口的实现类
 * @author Randy
 * Created: 2019年1月17日
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
	//access_token是协议流程前5步拿到的令牌，这里父类来执行前5步并拿到令牌了，所以不需要自己去拿这些
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	//父类里面会自动将access_token参数挂到getuserinfo的url后面，所以这里可以删掉access_token参数（qq官方是有，这里父类提供了）
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	private String appId;
	private String openId;	//是通过accessToken去上面的url获取的，所以不用在构造传进来
	
	private ObjectMapper objectMapper = new ObjectMapper();//json转对象的工具类
	/**
	 * 构造
	 * @param accessToken 走完OAuth协议流程后拿到的令牌，需要传过来
	 * @param appId 是qq提供的固定值，需要作为配置信息传进来
	 */
	public QQImpl(String accessToken, String appId) {
		//父类一个参数的构造默认是用TokenStrategy.AUTHORIZATION_HEADER请求头的方式
		//但是qq官网要求accessToken放在url查询参数里面，所以用下面的构造
		//调用父类构造，就是对accessToken进行处理
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		
		//openId的获取
		//将%s替换成accessToken
		String openIdUrl = String.format(URL_GET_OPENID, accessToken);
		//调用父类的RestTemplate来执行Rest API请求的操作，这里是用来获取openId
		String result = getRestTemplate().getForObject(openIdUrl, String.class);
		System.out.println("result="+result);
		//qq官网返回的是特殊字符串callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		//所以这里需要特殊处理一下，来截取真正的openId（截取方法后面再重构下）
		this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
	}
	
	/* （非 Javadoc）
	 * @see com.toby.security.core.social.qq.api.QQ#getUserInfo()
	 */
	@Override
	public QQUserInfo getUserInfo() throws Exception {
		//替换掉两个%s参数，accessToken因为父类自动拼接，所以这里不用处理
		String getUserURL = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(getUserURL, String.class);
		System.out.println("result=" + result);
		//将返回的result转成一个对象，也就是前面创建的QQUserInfo，字段对应qq接口文档的返回字段
		
		return this.objectMapper.readValue(result, QQUserInfo.class);
	}

}
