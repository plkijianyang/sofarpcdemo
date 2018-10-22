package com.test;

/**
 * @Author:wjy
 * @Date: 2018/10/22.
 */
public class HelloServiceImpl  implements HelloService {

	private int    sleep;

	private String result;

	public HelloServiceImpl() {

	}

	public HelloServiceImpl(String result) {
		this.result = result;
	}

	public HelloServiceImpl(int sleep) {
		this.sleep = sleep;
	}

	@Override
	public String sayHello(String name, int age) {
		if (sleep > 0) {
			try {
				Thread.sleep(sleep);
			} catch (Exception ignore) {
			}
		}
		return result != null ? result : "hello " + name + " from server! age: " + age;
	}
}
