package com.moseeker.rpccenter.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 对应zookeeper中的节点。其中 name表示节点的名称，
 * chirldren表示节点下的子节点， 
 * data存储是rpc服务注册的特定的节点数据（如果不符合预定的格式，data将无法保存）
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ZKPath {

	private String name;						//路径名称
	private List<ZKPath> chirldren; 			//子节点
	private ThriftData data;					//rpc服务注册的特定的数据结构。存储rpc服务所在的ip地址和端口号等重要信息
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
	
	/**
	 * 将有意义的数据存入map中，方便生成json
	 * @return
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", this.getName());
		List<HashMap<String, Object>> chirldren = new ArrayList<>();
		if(this.getChirldren() != null && this.getChirldren().size() > 0) {
			this.getChirldren().forEach(chirld -> {
				chirldren.add(chirld.toHashMap());
			});
		}
		map.put("chirldren", chirldren);
		map.put("data", data);
		return map;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(toHashMap());
	}
}
