package com.moseeker.rpccenter.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerManagerZKConfig;

public class ListenerManager {

	private CuratorFramework zookeeper;

	private ZKPath zkPath;

	private ServerManagerZKConfig config;

	public ListenerManager(ServerManagerZKConfig config) {
		this.config = config;
	}

	public ZKPath search() {
		ZKPath zkPath = new ZKPath(config.getNamespace());
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
				.connectionTimeoutMs(config.getConnectionTimeout()).namespace(config.getNamespace())
				.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
		zookeeper.start();
		GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
		try {
			List<String> services = getChildrenBuilder.forPath("");
			if (services != null && services.size() > 0) {
				List<ZKPath> childrenPaths = new ArrayList<>();
				for (String service : services) {
					ZKPath chirldrenPath = new ZKPath(service);
					List<String> chirldrenServices = getChildrenBuilder.forPath(service);
					if (chirldrenServices != null && chirldrenServices.size() > 0) {
						for (String childrenService : chirldrenServices) {
							List<String> grandChirldrenServices = getChildrenBuilder
									.forPath(service + Constants.ZK_SEPARATOR_DEFAULT + childrenService);
							if (grandChirldrenServices != null && grandChirldrenServices.size() > 0) {
								List<ZKPath> grandChirldrenPaths = new ArrayList<>();
								for (String grandChirldrenService : grandChirldrenServices) {
									ZKPath grandChirldrenPath = new ZKPath(config.getNamespace()
											+ Constants.ZK_SEPARATOR_DEFAULT + service + Constants.ZK_SEPARATOR_DEFAULT
											+ childrenService + Constants.ZK_SEPARATOR_DEFAULT + grandChirldrenService);
									CuratorFramework grandChirld = builder.connectString(config.getConnectstr())
											.sessionTimeoutMs(config.getTimeout())
											.connectionTimeoutMs(config.getConnectionTimeout())
											.namespace(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + service
													+ Constants.ZK_SEPARATOR_DEFAULT + childrenService
													+ Constants.ZK_SEPARATOR_DEFAULT + grandChirldrenService)
											.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
									grandChirld.start();
									GetDataBuilder dataBuilder = grandChirld.getData();
									String json = new String(dataBuilder.forPath(""), "utf8");
									ThriftData data = JSON.parseObject(json, ThriftData.class);
									grandChirldrenPath.setData(data);
									grandChirldrenPaths.add(grandChirldrenPath);
								}
								chirldrenPath.setChirldren(grandChirldrenPaths);
							}
						}
					}
					childrenPaths.add(chirldrenPath);
				}
				zkPath.setChirldren(childrenPaths);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return zkPath;
	}
	
	public void addListener(ZKPath path) {
		
	}

	public static void main(String[] args) {
		ServerManagerZKConfig config = ServerManagerZKConfig.config;
		ListenerManager lm = new ListenerManager(config);
		ZKPath path = lm.search();
		printPath(path);
	}

	private static void printPath(ZKPath path) {
		if(path != null) {
			System.out.println(path.getName());
			if(path.getData() != null) {
				System.out.println(path.getData());
			}
			if(path.getChirldren() != null && path.getChirldren().size() > 0) {
				path.getChirldren().forEach(chirld -> {
					printPath(chirld);
				});
			}
		}
	}
}
