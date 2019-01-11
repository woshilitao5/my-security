package com.toby.security.demo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * MQ模拟类
 * @author Toby.li
 */
@Component
public class MockQueue {
	private Logger logger = LoggerFactory.getLogger(MockQueue.class);
	
	private String requestOrder;	//队列请求信息（等待被处理的订单号）
	private String completeOrder;	//队列处理结果（已经处理完成的订单号）
	
	public String getRequestOrder() {
		return requestOrder;
	}
	public void setRequestOrder(String requestOrder) {	//用线程方式模拟MQ的处理和应用2
		new Thread(() -> {
			logger.info("MQ/应用2，接到下单请求：" + requestOrder);
			try {
				Thread.sleep(1000);	//假装应用2业务逻辑处理花了1秒钟
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.completeOrder = requestOrder;	//假装处理完后将处理结果放到队列
			logger.info("应用2请求处理完成，并插入结果队列：" + requestOrder);
		}).start();
	}
	public String getCompleteOrder() {
		return completeOrder;
	}
	public void setCompleteOrder(String completeOrder) {
		this.completeOrder = completeOrder;
	}
}
