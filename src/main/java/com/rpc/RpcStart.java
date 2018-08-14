package com.rpc;

import api.PayMessage;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;

/**
 * @Author:wjy
 * @Date: 2018/8/14.
 */
public class RpcStart {
	public static void main(String[] args) {
		ServerConfig serverConfig = new ServerConfig()
				.setProtocol("bolt")
				.setPort(12345)
				.setDaemon(false);

		RegistryConfig registryConfig = new RegistryConfig()
				.setAddress("140.143.244.92:2181")
				.setRegister(true)
				.setSubscribe(true)
				.setProtocol("zookeeper");


		ProviderConfig<PayMessage> providerConfig = new ProviderConfig<PayMessage>()
				.setInterfaceId(PayMessage.class.getName())
				.setRef(new PayServiceImpl())
				.setUniqueId("diysoon.pay")
				.setRegister(true)
				.setRegistry(registryConfig)
				.setServer(serverConfig);

		//发布服务
		providerConfig.export();
	}
}
