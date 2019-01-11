package com.toby.security.core.validate.code;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.toby.security.core.properties.SecurityConstants;

/**
 * 处理验证码请求的控制器
 * @author Toby.li
 */
@RestController
public class ValidateCodeController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 * @param request
	 * @param response
	 * @param type	URL中传的后半部分的标志，比如/image、/sms
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response
			, @PathVariable String type) throws Exception {
		logger.info("请求的验证码类型：" + type);
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}
	
	
	/**
	 * 下面的代码是没有重构、合并之前的代码
	 */
	
//	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
//	@Autowired
//	private ValidateCodeGenerator imageCodeGenerator;
//	@Autowired
//	private ValidateCodeGenerator smsCodeGenerator;
//	
//	@Autowired
//	private SmsCodeSender smsCodeSender;
	
	
	
//	@GetMapping("/code/image")
//	public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		ImageCode imgeCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request, response));
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imgeCode);
//		ImageIO.write(imgeCode.getImage(), "JPEG", response.getOutputStream());
//	}
//	
//	@GetMapping("/code/sms")
//	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//		ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request, response));
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//		
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//		smsCodeSender.send(mobile, smsCode.getCode());
//	}
}
