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
	private ZKPath parentNode;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZKPath other = (ZKPath) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

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

	public ZKPath getParentNode() {
		return parentNode;
	}

	public void setParentNode(ZKPath parentNode) {
		this.parentNode = parentNode;
	}
}
