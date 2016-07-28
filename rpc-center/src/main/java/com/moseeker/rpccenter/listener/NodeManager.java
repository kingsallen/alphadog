package com.moseeker.rpccenter.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.Constant;
import com.moseeker.rpccenter.common.Constants;
import com.moseeker.rpccenter.config.ServerManagerZKConfig;

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
 * @version
 */
public enum NodeManager {

	NODEMANAGER;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);	//读写锁
	
	private ServerManagerZKConfig config;													//zookeeper配置信息
	private ZKPath path = null;																//根节点
	
	NodeManager() {
		ServerManagerZKConfig config = ServerManagerZKConfig.config;
		this.config = config;
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
		return path;
	}
	
	public String converToString() {
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
		if(path != null && this.path != null && this.path.getChirldren() != null) {
			for(ZKPath parentPath : this.path.getChirldren()) {
				if(path.equals(parentPath)) {
					removeParentPath(path);
				}
			}
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
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * 刷新节点数据
	 */
	public void refresh() {
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
		lock.readLock().lock();
		ZKPath zkPath = null;
		CuratorFramework zookeeper = null;
		try {
			zkPath = new ZKPath(config.getNamespace());
			CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
			zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
					.connectionTimeoutMs(config.getConnectionTimeout()).namespace(config.getNamespace())
					.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
			zookeeper.start();
			Stat stat = zookeeper.checkExists().forPath(Constants.ZK_SEPARATOR_DEFAULT);
			if(stat == null) {
				zookeeper.create().forPath(Constants.ZK_SEPARATOR_DEFAULT);
			}
			GetChildrenBuilder getChildrenBuilder = zookeeper.getChildren();
			List<String> services = getChildrenBuilder.forPath(Constants.ZK_SEPARATOR_DEFAULT);
			if (services != null && services.size() > 0) {
				List<ZKPath> childrenPaths = new ArrayList<>();
				for (String service : services) {
					ZKPath chirldrenPath = new ZKPath(service);
					chirldrenPath.setParentNode(zkPath);
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
			zookeeper.close();
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
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
						.sessionTimeoutMs(config.getTimeout()).connectionTimeoutMs(config.getConnectionTimeout())
						.namespace(config.getNamespace())
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				root.setZookeeper(zookeeper);
				// 监控服务节点的增减
				PathChildrenCache pathChildrenCache = new PathChildrenCache(zookeeper, Constants.ZK_SEPARATOR_DEFAULT, false);
				pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
						switch (event.getType()) {
						case CHILD_ADDED:
							System.out.println("CHILD_ADDED");
							addNewNodesListener(root);
							break;
						case CHILD_UPDATED:
							System.out.println("CHILD_UPDATED");
							break;
						case CHILD_REMOVED:
							System.out.println("CHILD_REMOVED");
							refreshParentNode(root);
							break;
						case CONNECTION_SUSPENDED:
							System.out.println("CONNECTION_SUSPENDED");
							//clear(root);
							break;
						case CONNECTION_RECONNECTED:
							System.out.println("CONNECTION_RECONNECTED");
							refreshParentNode(root);
							break;
						case CONNECTION_LOST:
							System.out.println("CONNECTION_LOST");
							clear(root);
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
					root.setChirldren(new ArrayList<>());
				} else if(root.getChirldren() != null && root.getChirldren().size() > 0) {
					for(ZKPath parentPath : root.getChirldren()) {
						addListenerToParentPath(parentPath);
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
					izkpath = null;
				}
			}
			root.getChirldren().clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			GetChildrenBuilder getChildrenBuilder = root.getZookeeper().getChildren();
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
					izkpath = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			GetChildrenBuilder getChildrenBuilder = root.getZookeeper().getChildren();
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
							Constants.ZK_SEPARATOR_DEFAULT,
							false);
					pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

						@Override
						public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
							switch (event.getType()) {
							case CHILD_ADDED:
								System.out.println("CHILD_ADDED");
								refreshServerNodes(parentPath);
								break;
							case CHILD_UPDATED:
								System.out.println("CHILD_UPDATED");
								break;
							case CHILD_REMOVED:
								System.out.println("CHILD_REMOVED");
								refreshServerNodes(parentPath);
								break;
							case CONNECTION_SUSPENDED:
								System.out.println("CONNECTION_SUSPENDED");
								//removeParentPath(parentPath);
								break;
							case CONNECTION_RECONNECTED:
								System.out.println("CONNECTION_RECONNECTED");
								break;
							case CONNECTION_LOST:
								System.out.println("CONNECTION_LOST");
								removeParentPath(parentPath);
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			if(parentPath.getZookeeper() == null) {
				CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
				CuratorFramework zookeeper = builder.connectString(config.getConnectstr()).sessionTimeoutMs(config.getTimeout())
						.connectionTimeoutMs(config.getConnectionTimeout())
						.namespace(config.getNamespace() + Constants.ZK_SEPARATOR_DEFAULT + parentPath.getName()
								+ Constants.ZK_SEPARATOR_DEFAULT + Constants.ZK_NAMESPACE_SERVERS)
						.retryPolicy(new ExponentialBackoffRetry(1000, config.getRetry())).build();
				zookeeper.start();
				parentPath.setZookeeper(zookeeper);
			}
			GetChildrenBuilder getChildrenBuilder = parentPath.getZookeeper().getChildren();
			List<String> grandChirldrenServices = getChildrenBuilder
					.forPath(Constants.ZK_SEPARATOR_DEFAULT);
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
					try {
						GetDataBuilder dataBuilder = grandChirld.getData();
						String json = new String(dataBuilder.forPath("/"), "utf8");
						System.out.println(grandChirldrenPath.getName()+"-data:"+json);
						ThriftData data = JSON.parseObject(json, ThriftData.class);
						grandChirldrenPath.setData(data);
					} catch (Exception e) {
						//报警
						e.printStackTrace();
					} finally {
						//
					}
					grandChirldrenPath.setParentNode(parentPath);
					grandChirld.close();
					nodes.add(grandChirldrenPath);
					
				}
				parentPath.setChirldren(nodes);
			} else {
				parentPath.setChirldren(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
}
