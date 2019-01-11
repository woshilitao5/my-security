package com.toby.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.toby.security.core.validate.code.ValidateCode;
import com.toby.security.core.validate.code.ValidateCodeException;
import com.toby.security.core.validate.code.ValidateCodeGenerator;
import com.toby.security.core.validate.code.ValidateCodeProcessor;
import com.toby.security.core.validate.code.ValidateCodeRepository;
import com.toby.security.core.validate.code.ValidateCodeType;

/**
 * 校验码处理器的抽象实现
 * <p>
 * 1. 将一个接口方法里面的主干逻辑抽象成几个通用的步骤，每个步骤对应一个方法；
 * 2. 业务处理相同的步骤直接在此类实现即可，业务处理不相同的步骤，则定义成抽象方法由此类的子类去分别实现；
 * 3. 利用Spring的“依赖搜索”（或者叫依赖查找）功能能够获取到所有业务类别的子类的唯一Bean实例，
 *    然后根据业务类型挑选一个Bean实例作为调用接口方法的载体
 * </p>
 * @author Toby.li
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的Bean实现。
	 * <p>
	 * 这种方法在启动的时候会查找Spring容器里面所有实现ValidateCodeGenerator接口的Bean，
	 * 然后找到以后将bean的名字作为key，bean的实例对象作为value放到这个map中去，
	 * 我们这里有两个生成器一个是图片验证码的一个是短信验证码的，所以启动之后这个map里面有两条记录。
	 * 后面需要使用时，可根据bean的名字来获取到具体的实例，从而实现按需调用，使得代码更加简洁（只调接口方法）、可重用（更多类型的实现都不怕）
	 * </p>
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	
	/**
	 * 存取校验码的Repository，是由browser和app项目去实现的，因为app不支持session，所以不能写死
	 */
	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	
	/* （非 Javadoc）
	 * @see com.toby.security.core.validate.code.ValidateCodeProcessor#create(org.springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		//下面就是将主干逻辑抽象成三个步骤（方法）
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}
	
	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) throws ValidateCodeException {
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	
	/**
	 * 根据请求的URL获取校验码的类型
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		ValidateCodeType codeType = getValidateCodeType(request);

		//获取存储的校验码（app从Redis中，浏览器从session中）
		C codeInSession = (C) validateCodeRepository.get(request, codeType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					codeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(codeType + "请填写验证码");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(codeType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(request, codeType);
			throw new ValidateCodeException(codeType + "验证码已过期，请重新获取");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(codeType + "验证码不正确");
		}
		
		validateCodeRepository.remove(request, codeType);
		
	}
}
