package com.moseeker.rpccenter.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ServerManagerZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * 基础服务节点管理工具。提供节点数据同步，节点管理等功能。
 * 
 * 将来可能需要一个容错机制：可能节点监听机制无法生效，需要定时自动抓取节点数据，
 * 并和监听中的节点数据比较。如果出现差异，需要修复监听中的数据
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 26, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version 0.1.0
 */
public enum NodeManager {

	/**
	 * 节点管理工具
	 */
	NODE_MANAGER;

	private Logger logger = LoggerFactory.getLogger(NodeManager.class);
	/**
	 * 读写锁
	 */
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	/**
	 * zookeeper配置信息
	 */
	private ServerManagerZKConfig config;
	/**
	 * 根节点
	 */
	private ZKPath path = null;

	/**
	 * 异常节点
	 */
	private Map<ZKPath, Long> errorStorage = new HashMap<>();

	/**
	 * 任务过期时间
	 */
	private int expiredTime = 10*60*1000;

	/**
	 * 定时任务工具
	 */
	private ScheduledThread scheduledThread = ScheduledThread.Instance;

	NodeManager() {
		this.config = ServerManagerZKConfig.config;
		initRoot();
		startTaskWithFixedRate();
	}

	/**
	 * 初始化根节点
	 */
	private void initRoot() {
		if(path == null) {
			path = search();
			addListener(path);
		}
	}
	
	/**
	 * 获取根节点
	 * @return 根节点
	 */
	public ZKPath getRoot() {
		if(path == null) {
			int i = 0;
			while(i < config.getRetry() && path == null) {
				initRoot();
				try {
					Thread.sleep(1000 * i++);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return path;
	}
	
	public String convertToString() {
		if(path != null) {
			return path.toString();
		} else {
			return Constant.NONE_JSON;
		}
	}
	
	/**
	 * 删除二级节点
	 * @param path 二级节点
	 */
	public void removePath(ZKPath path) {
		try {
			lock.writeLock().lock();
			if(path != null && path.getParentNode() != null && this.path != null && this.path.getChirldren() != null) {
                for(ZKPath parentPath : this.path.getChirldren()) {
                    if(path.getParentNode().equals(parentPath)) {
                    	if (parentPath.getChirldren() != null) {
							Iterator<ZKPath> zkPathIterator = parentPath.getChirldren().iterator();
							while (zkPathIterator.hasNext()) {
								ZKPath zkPath = zkPathIterator.next();
								if (zkPath.equals(path)) {
									zkPathIterator.remove();
									errorStorage.put(zkPath, System.currentTimeMillis());
									break;
								}
							}
						}
						break;
                    }
                }
            }
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 清空数据
	 */
	public void clear() {
		lock.writeLock().lock();
		try {
			if(path != null) {
				clear(path);
				if(path.getChirldren() != null) {
					path.setChirldren(null);
				}
				if(path.getChirldrenCache() != null) {
					path.getChirldrenCache().clear();
					path.setChirldrenCache(null);
				}
				if(path.getZookeeper() != null) {
					path.getZookeeper().close();
					path.setZookeeper(null);
				}
				path = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 刷新节点数据
	 */
	public void refresh() {
		clearErrorStorage();
		if(path == null) {
			path = search();
			addListener(path);
		} else {
			refreshParentNode(path);
		}
	}
	
	/**
	 * 查询基础服务节点，如果根节点不存在，创建根节点。此时并不创建节点监听。
	 * @return 根服务节点
	 */
	private ZKPath search() {
		lock.writeLock().lock();
		ZKPath zkPath;
		CuratorFramework zookeeper = null;
		try {
			clearErrorStorage();
			zkPath = new ZKPath(config.getRoot());
			CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
			zookeeper = builder.connectString(config.getIP()+":"+config.getPort()).sessionTimeoutMs(config.getSessionTimeOut())
					.connectionTimeoutMs(config.getConnectionTimeOut()).namespace(config.getRoot())
					.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
			zookeeper.start();
			Stat stat = zookeeper.checkExists().forPath(config.getZkSeparator());
			if(stat == null) {
				zookeeper.create().forPath(config.getZkSeparator());
			}
			GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
			List<String> services = getChildrenBuilder.forPath(config.getZkSeparator());
			if (services != null && services.size() > 0) {
				List<ZKPath> childrenPaths = new CopyOnWriteArrayList<>();
				for (String service : services) {
					ZKPath chirldrenPath = new ZKPath(service);
					chirldrenPath.setParentNode(zkPath);
					List<String> chirldrenServices = getChildrenBuilder
							.forPath(config.getZkSeparator() + service);
					if (chirldrenServices != null && chirldrenServices.size() > 0) {
						for (String childrenService : chirldrenServices) {
							List<String> grandChirldrenServices = getChildrenBuilder
									.forPath(config.getZkSeparator() + service + config.getZkSeparator()
											+ childrenService);
							if (grandChirldrenServices != null && grandChirldrenServices.size() > 0) {
								List<ZKPath> grandChirldrenPaths = new CopyOnWriteArrayList<>();
								for (String grandChirldrenService : grandChirldrenServices) {
									ZKPath grandChirldrenPath = new ZKPath(grandChirldrenService);
									CuratorFrameworkFactory.Builder builder1 = CuratorFrameworkFactory.builder();
									CuratorFramework grandChirld = builder1.connectString(config.getConnectstr())
											.sessionTimeoutMs(config.getSessionTimeOut())
											.connectionTimeoutMs(config.getConnectionTimeOut())
											.namespace(config.getRoot() + config.getZkSeparator() + service
													+ config.getZkSeparator() + childrenService
													+ config.getZkSeparator() + grandChirldrenService)
											.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
									grandChirld.start();
									GetDataBuilder dataBuilder = grandChirld.getData();
									String json = new String(dataBuilder.forPath("/"), "utf8");
									ServerData data = JSON.parseObject(json, ServerData.class);
									grandChirldrenPath.setData(data);
									grandChirldrenPath.setParentNode(chirldrenPath);
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
			zkPath = null;
			if (zookeeper != null) {
				zookeeper.close();
			}
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}

		return zkPath;
	}
	
	/**
	 * 为整个树形节点添加监听
	 * @param root 根节点
	 */
	private void addListener(ZKPath root) {
		lock.writeLock().lock();
		try {
			if (root != null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				CuratorFramework zookeeper = builder.connectString(config.getConnectstr())
						.sessionTimeoutMs(config.getSessionTimeOut()).connectionTimeoutMs(config.getConnectionTimeOut())
						.namespace(config.getRoot())
						.retryPolicy(new ExponentialBackoffRetry(config.getBaseSleepTimeMS(), config.getRetry())).build();
				zookeeper.start();
				root.setZookeeper(zookeeper);
				// 监控服务节点的增减
				PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeper, config.getZkSeparator(), false);
				pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
						switch (event.getType()) {
						case CHILD_ADDED:
							System.out.println("CHILD_ADDED in addListener");
							addNewNodesListener(root);
							break;
						case CHILD_UPDATED:
							System.out.println("CHILD_UPDATED in addListener");
							break;
						case CHILD_REMOVED:
							System.out.println("CHILD_REMOVED in addListener");
							refreshParentNode(root);
							break;
						case CONNECTION_SUSPENDED:
							System.out.println("CONNECTION_SUSPENDED in addListener");
							//clear(root);
							break;
						case CONNECTION_RECONNECTED:
							System.out.println("CONNECTION_RECONNECTED in addListener");
							refreshParentNode(root);
							break;
						case CONNECTION_LOST:
							System.out.println("CONNECTION_LOST in addListener");
							//clear(root); // commented by yaofeng, 临时避开 can't find node问题,待修复后再开启.
							break;
						case INITIALIZED:
							break;
						default:
						}

					}
				});
				pathChildrenCache.start();
				root.setChirldrenCache(pathChildrenCache);
				
				if(root.getChirldren() == null) {
					root.setChirldren(new CopyOnWriteArrayList<>());
				} else if(root.getChirldren() != null && root.getChirldren().size() > 0) {
					for(ZKPath parentPath : root.getChirldren()) {
						addListenerToParentPath(parentPath);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 清空除根节点下的所有子节点
	 * @param root 根节点
	 */
	private void clear(ZKPath root) {
		lock.writeLock().lock();
		try {
			if(root.getChirldren() != null && root.getChirldren().size() > 0) {
				Iterator<ZKPath> iZKPath = root.getChirldren().iterator();
				while(iZKPath.hasNext()) {
					ZKPath izkpath = iZKPath.next();
					removeParentPath(izkpath);
					iZKPath.remove();
				}
			}
			root.getChirldren().clear();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 刷新根节点下的子节点
	 * @param root 根节点
	 */
	private void refreshParentNode(ZKPath root) {
		lock.writeLock().lock();
		try {
			clearErrorStorage();
			GetChildrenBuilder getChildrenBuilder = root.getZookeeper().getChildren();
			List<String> services = getChildrenBuilder.forPath(config.getZkSeparator());
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
						parentPath.setParentNode(root);
						addListenerToParentPath(parentPath);
						refreshServerNodes(parentPath);
					}
				}
				Iterator<ZKPath> iZKPath = root.getChirldren().iterator();
				while(iZKPath.hasNext()) {
					ZKPath izkpath = iZKPath.next();
					boolean notOldZKPathFlag = false;
					for (String service : services) {
						if(izkpath.getName().equals(service)) {
							notOldZKPathFlag = true;
							break;
						}
					}
					if(!notOldZKPathFlag) {
						removeParentPath(izkpath);
						iZKPath.remove();
					}
				}
			} else {
				Iterator<ZKPath> iZKPath = root.getChirldren().iterator();
				while(iZKPath.hasNext()) {
					ZKPath izkpath = iZKPath.next();
					removeParentPath(izkpath);
					iZKPath.remove();
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 刷新根节点下的新的子节点
	 * @param root 根节点
	 */
	private void addNewNodesListener(ZKPath root) {
		try {
			lock.writeLock().lock();
			clearErrorStorage();
			GetChildrenBuilder getChildrenBuilder = root.getZookeeper().getChildren();
			List<String> services = getChildrenBuilder.forPath(config.getZkSeparator());
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
						parentPath.setParentNode(root);
						addListenerToParentPath(parentPath);
						refreshServerNodes(parentPath);
						root.getChirldren().add(parentPath);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 为根节点下的子节点添加监听
	 * @param parentPath 根节点下的具体子节点
	 */
	private void addListenerToParentPath(ZKPath parentPath) {
		try {
			lock.writeLock().lock();
			CuratorFramework zookeeper = null;
			if(parentPath.getZookeeper() == null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getSessionTimeOut())
						.connectionTimeoutMs(config.getConnectionTimeOut())
						.namespace(config.getRoot() + config.getZkSeparator() + parentPath.getName()
								+ config.getZkSeparator() + config.getServers())
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				parentPath.setZookeeper(zookeeper);
				
				PathChildrenCache pathChildrenCache = null;
				if(parentPath.getChirldrenCache() != null) {
					pathChildrenCache = parentPath.getChirldrenCache();
				} else {
					pathChildrenCache = new PathChildrenCache(zookeeper,
							config.getZkSeparator(),
							false);
					pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

						@Override
						public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
							switch (event.getType()) {
							case CHILD_ADDED:
								System.out.println("CHILD_ADDED in addListenerToParentPath");
								refreshServerNodes(parentPath);
								break;
							case CHILD_UPDATED:
								System.out.println("CHILD_UPDATED in addListenerToParentPath");
								break;
							case CHILD_REMOVED:
								System.out.println("CHILD_REMOVED in addListenerToParentPath");
								refreshServerNodes(parentPath);
								break;
							case CONNECTION_SUSPENDED:
								System.out.println("CONNECTION_SUSPENDED in addListenerToParentPath");
								//removeParentPath(parentPath);
								break;
							case CONNECTION_RECONNECTED:
								System.out.println("CONNECTION_RECONNECTED in addListenerToParentPath");
								refreshServerNodes(parentPath);  // added by yaofeng
								break;
							case CONNECTION_LOST:
								System.out.println("CONNECTION_LOST in addListenerToParentPath");
								// removeParentPath(parentPath); // commented by yaofeng, 临时避开 can't find node问题,待修复后再开启.
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
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 删除二级节点
	 * @param parentPath 二级子节点
	 */
	private void removeParentPath(ZKPath parentPath) {
		lock.writeLock().lock();
		try {
			if(parentPath != null) {
				if(parentPath.getChirldren() != null) {
					parentPath.setChirldren(null);
				}
				if(parentPath.getChirldrenCache() != null) {
					parentPath.getChirldrenCache().clear();
					parentPath.setChirldrenCache(null);
				}
				if(parentPath.getZookeeper() != null) {
					parentPath.getZookeeper().close();
					parentPath.setZookeeper(null);
				}
				if(parentPath.getParentNode() != null) {
					Iterator<ZKPath> zki = parentPath.getParentNode().getChirldren().iterator();
					while(zki.hasNext()) {
						ZKPath samePath = zki.next();
						if(parentPath.equals(samePath)) {
							zki.remove();
							break;
						}
					}
				}
				parentPath = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 刷新指定的一级节点下的二级节点
	 * @param parentPath 一级节点
	 */
	private void refreshServerNodes(ZKPath parentPath) {
		if(parentPath == null) {
			return;
		}
		lock.writeLock().lock();
		try {
			clearErrorStorage();
			if(parentPath.getZookeeper() == null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				CuratorFramework zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getSessionTimeOut())
						.connectionTimeoutMs(config.getConnectionTimeOut())
						.namespace(config.getRoot() + config.getZkSeparator() + parentPath.getName()
								+ config.getZkSeparator() + config.getServers())
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				parentPath.setZookeeper(zookeeper);
			}
			GetChildrenBuilder getChildrenBuilder = parentPath.getZookeeper().getChildren();
			List<String> grandChirldrenServices = getChildrenBuilder
					.forPath(config.getZkSeparator());
			if(grandChirldrenServices != null && grandChirldrenServices.size() > 0) {
				List<ZKPath> nodes = new CopyOnWriteArrayList<>();
				for(String nodeName : grandChirldrenServices) {
					ZKPath grandChirldrenPath = new ZKPath(nodeName);
					CuratorFrameworkFactory.Builder builder1 = CuratorFrameworkFactory.builder();
					CuratorFramework grandChild = builder1.connectString(config.getConnectstr())
							.sessionTimeoutMs(config.getSessionTimeOut())
							.connectionTimeoutMs(config.getConnectionTimeOut())
							.namespace(config.getRoot() + config.getZkSeparator() + parentPath.getName()
									+ config.getZkSeparator() + config.getServers()
									+ config.getZkSeparator() + nodeName)
							.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
					grandChild.start();
					try {
						GetDataBuilder dataBuilder = grandChild.getData();
						String json = new String(dataBuilder.forPath("/"), "utf8");
						System.out.println(grandChirldrenPath.getName()+"-data:"+json);
						ServerData data = JSON.parseObject(json, ServerData.class);
						grandChirldrenPath.setData(data);
					} catch (Exception e) {
						//报警
						logger.error(e.getMessage(), e);
					} finally {
						//
					}
					grandChirldrenPath.setParentNode(parentPath);
					grandChild.close();
					nodes.add(grandChirldrenPath);
					
				}
				parentPath.setChirldren(nodes);
			} else {
				parentPath.setChirldren(null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 提供NodeManager锁
	 * @return NodeManager读写锁
	 */
	public ReentrantReadWriteLock getLock() {
		return lock;
	}

	/**
	 * 清空错误节点信息
	 */
	private void clearErrorStorage() {
		lock.writeLock().lock();
		if (errorStorage.size() != 0) {
			errorStorage.clear();
		}
		lock.writeLock().unlock();
	}

	private void startTaskWithFixedRate() {
		/**
		 * 首次任务的延迟事件
		 */
		long initialDelay = 10*60*1000;
		/**
		 * 告警任务的时间间隔
		 */
		long warnPeriod = 5*60*1000;
		/**
		 * 归还任务的时间间隔
		 */
		long turnBackPeriod = 10*60*1000;
		scheduledThread.scheduleWithFixedDelay(() -> warn(), initialDelay, warnPeriod, TimeUnit.MILLISECONDS);
		scheduledThread.scheduleWithFixedDelay(() -> turnBack(expiredTime), initialDelay, turnBackPeriod, TimeUnit.MILLISECONDS);
	}

	/**
	 * 如果有错误节点，那么告警！
	 */
	private void warn() {
		lock.readLock().lock();
		try {
			if (errorStorage.size() > 0) {
				if (errorStorage.size() > 0) {
					logger.error("NodeManager 有错误节点！！！");
					for (Map.Entry<ZKPath, Long> entry : errorStorage.entrySet()) {
						if (entry.getKey() == null) {
							continue;
						}
						logger.error("NodeManager 错误节点名称：{}， 错误节点内容：{}",
								entry.getKey().getName(),
								JSONObject.toJSONString(entry.getKey().getData()));
					}
				}
            }
		} finally {
			logger.info("NodeManager warn end!");
			lock.readLock().unlock();
		}
	}

	/**
	 * 将时间超过指定时间之外的节点归还
	 * @param millisecond 指定的时间 （单位毫秒）
	 */
	private void turnBack(int millisecond) {
		lock.writeLock().lock();
		try {
			if (errorStorage.size() > 0) {
                errorStorage.keySet().removeIf(Objects::nonNull);
                if (errorStorage.size() > 0) {
                	Iterator<Map.Entry<ZKPath, Long>> zkPathIterator = errorStorage.entrySet().iterator();
                	long now = System.currentTimeMillis();
                	while (zkPathIterator.hasNext()) {
                		Map.Entry<ZKPath, Long> entry = zkPathIterator.next();
                		if (now - entry.getValue() >= millisecond) {
							ZKPath root = getRoot();
							if (root != null && root.getChirldren() != null) {
								Optional<ZKPath> parentOptional =
										root.getChirldren()
												.stream()
												.filter(zkPath -> zkPath.equals(entry.getKey().getParentNode()))
												.findAny();
								if (parentOptional.isPresent()) {
									ZKPath parent = parentOptional.get();
									if (parent.getChirldren() != null) {
										Optional<ZKPath> childOptional = parent.getChirldren()
												.stream()
												.filter(zkPath -> zkPath.equals(entry.getKey()))
												.findAny();
										if (!childOptional.isPresent()) {
											parent.getChirldren().add(entry.getKey());
										}
									} else {
										parent.setChirldren(new ArrayList<ZKPath>(1){{add(entry.getKey());}});
									}
									break;
								}
							}
						}
					}
				}
            }
		} finally {
			logger.info("NodeManager turnBack end!");
			lock.writeLock().unlock();
		}
	}
}
