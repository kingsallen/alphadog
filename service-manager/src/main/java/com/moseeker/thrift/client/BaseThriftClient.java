package com.moseeker.thrift.client;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public abstract class BaseThriftClient {

	protected CuratorFramework zooclient;

	public abstract List callThriftServerGet(Object query);

	public abstract int callThriftServerPost(Object query);

	public abstract int callThriftServerPut(Object query);

	public abstract int callThriftServerDelete(Object query);

	protected abstract String getZooKeeperNamespace();

	public String getThriftServer() throws Exception {
		this.zooclient = CuratorFrameworkFactory
				.builder()
				.connectString("127.0.0.1:2181")
				.sessionTimeoutMs(30000)
				.connectionTimeoutMs(30000)
				.canBeReadOnly(false)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.namespace(
						"services/"
								+ this.getZooKeeperNamespace().toLowerCase())
				.defaultData(null).build();
		zooclient.start();

		// CuratorTempFramework zooclient = CuratorFrameworkFactory
		// .builder()
		// .connectString("127.0.0.1:2181")
		// .sessionTimeoutMs(30000)
		// .connectionTimeoutMs(30000)
		// .canBeReadOnly(false)
		// .retryPolicy(new ExponentialBackoffRetry(1000, 290))
		// .namespace("Companyfollowers")
		// .defaultData(null)
		// .buildTemp();

		List<String> iplist = this.zooclient.getChildren().forPath("/servers");
		String thriftserver = iplist.get(0); // 临时用第一个;
		return thriftserver;

	}

}
