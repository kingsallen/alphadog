package com.moseeker.rpccenter.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerManagerZKConfig;

public class ListenerManager {

	private CuratorFramework zookeeper;

	private ZKPath zkPath;

	private PathChildrenCache serviceListener;

	private ServerManagerZKConfig config;

	public ListenerManager(ServerManagerZKConfig config) {
		this.config = config;
	}

	public ZKPath search() {
		ZKPath zkPath = new ZKPath(config.getNamespace());
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		CuratorFramework zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
				.connectionTimeoutMs(config.getConnectionTimeout()).namespace(config.getNamespace())
				.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
		zookeeper.start();
		GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
		try {
			List<String> services = getChildrenBuilder.forPath(Constants.ZK_SEPARATOR_DEFAULT);
			if (services != null && services.size() > 0) {
				List<ZKPath> childrenPaths = new ArrayList<>();
				for (String service : services) {
					ZKPath chirldrenPath = new ZKPath(service);
					List<String> chirldrenServices = getChildrenBuilder
							.forPath(Constants.ZK_SEPARATOR_DEFAULT + service);
					if (chirldrenServices != null && chirldrenServices.size() > 0) {
						for (String childrenService : chirldrenServices) {
							List<String> grandChirldrenServices = getChildrenBuilder
									.forPath(Constants.ZK_SEPARATOR_DEFAULT + service + Constants.ZK_SEPARATOR_DEFAULT
											+ childrenService);
							if (grandChirldrenServices != null && grandChirldrenServices.size() > 0) {
								List<ZKPath> grandChirldrenPaths = new ArrayList<>();
								for (String grandChirldrenService : grandChirldrenServices) {
									ZKPath grandChirldrenPath = new ZKPath(grandChirldrenService);
									CuratorFrameworkFactory.Builder builder1 = CuratorFrameworkFactory.builder();
									CuratorFramework grandChirld = builder1.connectString(config.getConnectstr())
											.sessionTimeoutMs(config.getTimeout())
											.connectionTimeoutMs(config.getConnectionTimeout())
											.namespace(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + service
													+ Constants.ZK_SEPARATOR_DEFAULT + childrenService
													+ Constants.ZK_SEPARATOR_DEFAULT + grandChirldrenService)
											.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
									grandChirld.start();
									GetDataBuilder dataBuilder = grandChirld.getData();
									String json = new String(dataBuilder.forPath("/"), "utf8");
									ThriftData data = JSON.parseObject(json, ThriftData.class);
									grandChirldrenPath.setData(data);
									grandChirldrenPaths.add(grandChirldrenPath);
									grandChirld.close();
								}
								chirldrenPath.setChirldren(grandChirldrenPaths);
							}
						}
					}
					childrenPaths.add(chirldrenPath);
				}
				zkPath.setChirldren(childrenPaths);
			}
			zookeeper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return zkPath;
	}

	public void addListener(ZKPath path) {
		try {
			if (path != null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				CuratorFramework zookeeper = builder.connectString(config.getConnectstr())
						.sessionTimeoutMs(config.getTimeout()).connectionTimeoutMs(config.getConnectionTimeout())
						.namespace(config.getNamespace())
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				// 监控服务节点的增减
				PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeper, config.getNamespace(), false);
				pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
						switch (event.getType()) {
						case CHILD_ADDED:
							
							break;
						case CHILD_UPDATED:
							break;
						case CHILD_REMOVED:
							break;
						case CONNECTION_SUSPENDED:
							break;
						case CONNECTION_RECONNECTED:
							break;
						case CONNECTION_LOST:
							break;
						case INITIALIZED:
							break;
						default:
						}

					}

				});
				pathChildrenCache.start();
				serviceListener = pathChildrenCache;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addNewNodesListener(ZKPath root) {
		try {
			GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
			List<String> services = getChildrenBuilder.forPath(Constants.ZK_SEPARATOR_DEFAULT);
			if (services != null && services.size() > 0) {
				for (String service : services) {
					boolean newNode = true;
					List<ZKPath> parentPaths = root.getChirldren();
					for (ZKPath parentPath : parentPaths) {
						if (service.equals(parentPath.getName())) {
							newNode = false;
							break;
						}
					}
					if (newNode) {
						ZKPath parentPath = new ZKPath(service);
						addListenerToParentPath(parentPath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addListenerToParentPath(ZKPath parentPath) {
		try {
			CuratorFramework zookeeper = null;
			if(parentPath.getZookeeper() == null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
						.connectionTimeoutMs(config.getConnectionTimeout())
						.namespace(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + parentPath.getName()
								+ Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS)
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				parentPath.setZookeeper(zookeeper);
				
				PathChildrenCache pathChildrenCache = null;
				if(parentPath.getChirldrenCache() != null) {
					pathChildrenCache = parentPath.getChirldrenCache();
				} else {
					pathChildrenCache = new PathChildrenCache(zookeeper,
							config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + parentPath.getName()
									+ Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS,
							false);
					pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

						@Override
						public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
							switch (event.getType()) {
							case CHILD_ADDED:
								refreshServerNodes(parentPath);
								break;
							case CHILD_UPDATED:
								break;
							case CHILD_REMOVED:
								refreshServerNodes(parentPath);
								break;
							case CONNECTION_SUSPENDED:
								break;
							case CONNECTION_RECONNECTED:
								break;
							case CONNECTION_LOST:
								break;
							case INITIALIZED:
								break;
							default:
							}

						}

					});
					pathChildrenCache.start();
					parentPath.setChirldrenCache(pathChildrenCache);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshServerNodes(ZKPath parentPath) {
		if(parentPath != null && parentPath.getZookeeper() != null) {
			try {
				GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
				List<String> grandChirldrenServices = getChildrenBuilder
						.forPath(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + parentPath.getName()
								+ Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS);
				if(grandChirldrenServices != null && grandChirldrenServices.size() > 0) {
					List<ZKPath> nodes = new ArrayList<>();
					for(String nodeName : grandChirldrenServices) {
						ZKPath grandChirldrenPath = new ZKPath(nodeName);
						CuratorFrameworkFactory.Builder builder1 = CuratorFrameworkFactory.builder();
						CuratorFramework grandChirld = builder1.connectString(config.getConnectstr())
								.sessionTimeoutMs(config.getTimeout())
								.connectionTimeoutMs(config.getConnectionTimeout())
								.namespace(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + parentPath.getName()
										+ Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS
										+ Constants.ZK_SEPARATOR_DEFAULT + nodeName)
								.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
						grandChirld.start();
						GetDataBuilder dataBuilder = grandChirld.getData();
						String json = new String(dataBuilder.forPath("/"), "utf8");
						ThriftData data = JSON.parseObject(json, ThriftData.class);
						grandChirldrenPath.setData(data);
						grandChirld.close();
						nodes.add(grandChirldrenPath);
					}
					parentPath.setChirldren(nodes);
				} else {
					parentPath.setChirldren(null);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ServerManagerZKConfig config = ServerManagerZKConfig.config;
		ListenerManager lm = new ListenerManager(config);
		ZKPath path = lm.search();
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		CuratorFramework zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
				.connectionTimeoutMs(config.getConnectionTimeout()).namespace(path.getName())
				.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
		zookeeper.start();
		path.setZookeeper(zookeeper);
		// 监控服务节点的增减
		PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeper, Constants.ZK_SEPARATOR_DEFAULT, false);
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED");
					lm.addNewNodesListener(path);
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED");
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED");
					break;
				case CONNECTION_SUSPENDED:
					System.out.println("CONNECTION_SUSPENDED");
					break;
				case CONNECTION_RECONNECTED:
					System.out.println("CONNECTION_RECONNECTED");
					break;
				case CONNECTION_LOST:
					System.out.println("CONNECTION_LOST");
					break;
				case INITIALIZED:
					System.out.println("INITIALIZED");
					break;
				default:
				}
			}
		});
		try {
			pathChildrenCache.start();
			path.setChirldrenCache(pathChildrenCache);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		while (true) {
			synchronized (ListenerManager.class) {
				while (true) {
					try {
						ListenerManager.class.wait();
					} catch (Exception e) {
						try {
							pathChildrenCache.close();
							zookeeper.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			}
		}
		// printPath(path);
	}

	private static void printPath(ZKPath path) {
		if (path != null) {
			System.out.println(path.getName());
			if (path.getData() != null) {
				System.out.println(path.getData());
			}
			if (path.getChirldren() != null && path.getChirldren().size() > 0) {
				path.getChirldren().forEach(chirld -> {
					printPath(chirld);
				});
			}
		}
	}
}
