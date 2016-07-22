package com.moseeker.rpccenter.listener;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

public class ZKPath {

	private String name;						//路径名称
	private List<ZKPath> chirldren; 			//子节点
	private ThriftData data;
	private PathChildrenCache chirldrenCache;
	private CuratorFramework zookeeper;
	
	public ZKPath(String name) {
		this.name = name;
	}
	
	public boolean isThriftServer() {
		return data != null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ZKPath> getChirldren() {
		return chirldren;
	}
	public void setChirldren(List<ZKPath> chirldren) {
		this.chirldren = chirldren;
	}

	public ThriftData getData() {
		return data;
	}

	public void setData(ThriftData data) {
		this.data = data;
	}

	public CuratorFramework getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(CuratorFramework zookeeper) {
		this.zookeeper = zookeeper;
	}

	public PathChildrenCache getChirldrenCache() {
		return chirldrenCache;
	}

	public void setChirldrenCache(PathChildrenCache chirldrenCache) {
		this.chirldrenCache = chirldrenCache;
	}
}
