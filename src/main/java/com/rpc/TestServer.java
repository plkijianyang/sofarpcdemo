package com.rpc;

import api.PayMessage;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.test.ActivelyDestroyTest;
import com.test.HelloService;
import com.test.HelloServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:wjy
 * @Date: 2018/10/22.
 */
public class TestServer  extends ActivelyDestroyTest{

	@Test
	public void test(){
		// 只有1个线程 执行
		ServerConfig serverConfig = new ServerConfig()
				.setStopTimeout(60000)
				.setPort(22222)
				.setProtocol(RpcConstants.PROTOCOL_TYPE_BOLT)
				.setQueues(100).setCoreThreads(1).setMaxThreads(2);

		// 发布一个服务，每个请求要执行1秒
		ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
				.setInterfaceId(HelloService.class.getName())
				.setRef(new HelloServiceImpl(1000))
				.setServer(serverConfig)
				.setRepeatedExportLimit(-1)
				.setRegister(false);
		providerConfig.export();

		ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
				.setInterfaceId(HelloService.class.getName())
				.setDirectUrl("bolt://127.0.0.1:22222")
				.setTimeout(30000)
				.setRegister(false);
		final HelloService helloService = consumerConfig.refer();

		int times = 5;
		final CountDownLatch latch = new CountDownLatch(times);
		final AtomicInteger count = new AtomicInteger();

		// 瞬间发起5个请求，那么服务端肯定在排队
		for (int i = 0; i < times; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(helloService.sayHello("xxx", 22));
						count.incrementAndGet();
					} catch (Exception e) {
						// TODO
					} finally {
						latch.countDown();
					}
				}
			}, "thread" + i);
			thread.start();
			System.out.println("send " + i);
			try {
				Thread.sleep(100);
			} catch (Exception ignore) {
			}
		}

		// 然后马上关闭服务端，此时应该5个请求都还没执行完
		try {
			serverConfig.destroy();
		} catch (Exception e) {
		}

		// 应该执行了5个请求
		try {
			// 服务端完成最后一个请求，但是由于睡了一定时间，所以这样等待最后一个客户端响应返回
			latch.await(1500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
		Assert.assertTrue(count.get() == times);
	}
}
