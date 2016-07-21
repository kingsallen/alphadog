package com.moseeker.rpccenter.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.moseeker.rpccenter.config.ServerManagerZKConfig;

public class ListenerManager {

	private CuratorFramework zookeeper;

	private List<String> listenerList = new ArrayList<>();

	private ServerManagerZKConfig config;

	public ListenerManager(ServerManagerZKConfig config) {
		this.config = config;
	}

	public void searchChildrenNodes(String parentNodePath) {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
				.connectionTimeoutMs(config.getConnectionTimeout()).namespace(config.getNamespace())
				.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
		GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
		try {
			List<String> paths = getChildrenBuilder.forPath("service");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExistsBuilder existBuilder = zookeeper.checkExists();
	}
	
	public static void main(String[] args) {
		ServerManagerZKConfig config = ServerManagerZKConfig.config;
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		CuratorFramework zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
				.connectionTimeoutMs(config.getConnectionTimeout()).namespace(config.getNamespace())
				.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
		zookeeper.start();
		GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
		try {
			List<String> services = getChildrenBuilder.forPath("");
			services.forEach(service -> {
				System.out.println("service:"+service);
				try {
					List<String> chirldrenServices = getChildrenBuilder.forPath(service);
					chirldrenServices.forEach(childrenService -> {
						System.out.println("childrenService:"+childrenService);
						try {
							List<String> grandChirldrenServices = getChildrenBuilder.forPath(service+"/"+childrenService);
							grandChirldrenServices.forEach(grandChirldrenService -> {
								System.out.println("grandChirldrenService:"+grandChirldrenService);
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
