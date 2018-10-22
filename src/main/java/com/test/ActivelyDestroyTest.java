package com.test;

import com.alipay.sofa.rpc.context.RpcInternalContext;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.context.RpcRunningState;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @Author:wjy
 * @Date: 2018/10/22.
 */
public class ActivelyDestroyTest {

	@BeforeClass
	public static void adBeforeClass() {
		RpcRunningState.setUnitTestMode(true);
	}


	@AfterClass
	public static void adAfterClass() {
		RpcRuntimeContext.destroy();
		RpcInternalContext.removeContext();
		RpcInvokeContext.removeContext();
	}
}
