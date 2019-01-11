package com.toby.security.demo.web.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Toby.li
 *
 */
@RestController
@RequestMapping("/order")
public class AsyncController {
	private Logger logger = LoggerFactory.getLogger(AsyncController.class);
	
	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DeferredResultHold deferredResultHold;
	
	@GetMapping("/sync")
	public String orderSync() throws Exception {	//同步处理
		logger.info("同步方式：主线程开始");
		Thread.sleep(1000);
		logger.info("同步方式：主线程返回");
		return "同步方式success";
	}
	
	/**
	 * Runnable方式的异步处理，Callable是Runnable的子接口
	 * @return Callable<String>，表示已callale方式处理，返回给前端的内容是String类型的
	 * @throws Exception
	 */
	@GetMapping("/runnable")
	public Callable<String> orderAsync() throws Exception {
		logger.info("Runnalbe方式：主线程开始");
		
		Callable<String> result = new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("runnable 副线程开始");
				Thread.sleep(1000);
				logger.info("runnable 副线程返回");
				return "Runnable异步方式success";	//这个字符串是作为最终响应给客户端的
			}
		};
		
		logger.info("Runnalbe方式：主线程返回");
		return result;
	}
	
	/**
	 * DeferredResult方式的异步处理
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deferred")
	public DeferredResult<String> orderDeferredResult() throws Exception {
		logger.info("DeferredResult方式：主线程开始");
		
		String orderNum = RandomStringUtils.randomNumeric(8);	//模拟一个订单号出来
		mockQueue.setRequestOrder(orderNum);	//放到队列 请求中（应用2会订阅这个队列，里面有值就会去处理）
		
		DeferredResult<String> result = new DeferredResult<String>();
		deferredResultHold.getMap().put(orderNum, result);	//主线程1中，设置订单号与DeferredResult的映射，方便主线程2处理响应
		
		logger.info("DeferredResult方式：主线程返回");
		return result;
	}
}
