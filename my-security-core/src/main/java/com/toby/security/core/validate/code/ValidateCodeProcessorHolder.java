package com.toby.security.core.validate.code;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校验码处理器的管理器
 * <p>
 * 		主要用于通过校验码处理器的类型来获得具体的校验码处理器
 * </p>
 * @author Toby.li
 */
@Component
public class ValidateCodeProcessorHolder {
	/**
	 * 收集系统中所有的 {@link ValidateCodeProcessor} 接口的Bean实现。
	 * <p>
	 * 用这种方法后，Spring在启动的时候会查找Spring容器里面所有实现ValidateCodeProcessor接口的Bean，
	 * 然后找到以后将bean的名字作为key，bean的实例对象作为value放到这个map中去，
	 * 我们这里有两个验证码的处理器一个是图片验证码的一个是短信验证码的，所以启动之后这个map里面有两条记录。
	 * 后面需要使用时，可根据bean的名字来获取到具体的实例，从而实现按需调用，使得代码更加简洁（只调接口方法）、可重用（更多类型的实现都不怕）
	 * </p>
	 */
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	/**
	 * @param type
	 * @return
	 */
	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	/**
	 * @param type
	 * @return
	 */
	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}
}
