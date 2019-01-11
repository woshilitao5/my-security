package com.toby.security.demo.web.async;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Toby.li
 *
 */
@Component
public class DeferredResultHold {
	//用来记录 订单号跟DeferredResult对象的映射，方便根据订单号查找DeferredResult对象来进行处理
	private Map<String, DeferredResult<String>> map = new HashMap<String, DeferredResult<String>>();

	public Map<String, DeferredResult<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, DeferredResult<String>> map) {
		this.map = map;
	}
}
