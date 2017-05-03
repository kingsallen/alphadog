package com.moseeker.rpccenter.loadbalance;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.listener.ZKPath;

/**
 * 
 * 节点负载均衡管理工具，目前只有循环策略 
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum NodeLoadBalance {

	LoadBalance;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HashMap<String, Integer> index = new HashMap<>();

	public ZKPath getNextNode(ZKPath root, String name) {
		ZKPath node = null;
		if (root != null && root.getChirldren() != null && root.getChirldren().size() > 0) {
			for (ZKPath parentPath : root.getChirldren()) {
				if (parentPath.getName().equals(name) && parentPath.getChirldren() != null
						&& parentPath.getChirldren().size() > 0) {
					if(!index.containsKey(name)) {
						index.put(name, 0);
					}
					int position = index.get(name);
					if(position >= parentPath.getChirldren().size()) {
						position = 0;
						index.put(name, 0);
					}
					node = parentPath.getChirldren().get(position);
					logger.info("loadbalance position:"+position);
					logger.info(node.toString());
					if(position+1 >= parentPath.getChirldren().size()) {
						index.put(name, 0);
					} else {
						index.put(name, position+1);
					}
					break;
				}
			}
		} else {
			logger.info("index.clear()");
			index.clear();
			//warning
		}
		if (node == null){
			logger.info("getNextNode null,root:" + root + ",name:" + name);
			logger.info("root.getChirldren():" + root.getChirldren());
			for (ZKPath parentPath : root.getChirldren()) {
				logger.info("parentPath:" + parentPath);
				logger.info("parentPath.getName().equals(name):" + parentPath.getName().equals(name));
				logger.info("parentPath.getChirldren():" + parentPath.getChirldren());

				
				if (parentPath.getName().equals(name) && parentPath.getChirldren() != null
						&& parentPath.getChirldren().size() > 0) {
					if(!index.containsKey(name)) {
						//index.put(name, 0);
					}
					int position = index.get(name);
					if(position >= parentPath.getChirldren().size()) {
						position = 0;
						//index.put(name, 0);
					}
					logger.info("position:" + position);
					node = parentPath.getChirldren().get(position);
					logger.info("node, parentPath.getChirldren().get(position):" + parentPath.getChirldren().get(position));
					logger.info("loadbalance position:"+position);
					logger.info(node.toString());
					if(position+1 >= parentPath.getChirldren().size()) {
						//index.put(name, 0);
					} else {
						//index.put(name, position+1);
					}
					break;
				}
			}			
			
		}
		
		return node;
	}
}
