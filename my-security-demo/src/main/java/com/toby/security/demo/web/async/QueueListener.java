package com.toby.security.demo.web.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 模拟MQ消息监听器的类
 * 1. Spring的监听器都要实现ApplicationListener接口
 * 2. ApplicationListener接口里面的泛型参数表示要监听的事件
 * 3. ContextRefreshedEvent代表的是整个Spring初始化完成的事件，监听它就表示当整个应用启动后要做的事情
 * @author Toby.li
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = LoggerFactory.getLogger(MockQueue.class);
	
	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DeferredResultHold deferredResultHold;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(() -> {	//这里创建一个新的线程，防止阻塞Tomcat主线程
			while (true) {	//无限循环
				if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {	//如果队列里面有结果返回就去处理响应
					String orderno = mockQueue.getCompleteOrder();
					DeferredResult<String> result = deferredResultHold.getMap().get(orderno);//在映射列表中找到DeferredResult对象
					
					logger.info("返回订单处理结果到Respons：" + orderno);
					result.setResult("DeferredResult异步处理完成，订单：" + orderno);
					
					mockQueue.setCompleteOrder(null);	//处理响应后要清空，否则同一个订单号会反复不停的被处理
				} else {
					try {
						Thread.sleep(200);	//每次停顿0.2秒
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
