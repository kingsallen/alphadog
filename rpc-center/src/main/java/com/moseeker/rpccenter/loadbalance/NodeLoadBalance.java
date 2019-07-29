package com.moseeker.rpccenter.loadbalance;

import com.moseeker.rpccenter.listener.ZKPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

	/**
	 * 轮询负载均衡
	 */
	LoadBalance;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HashMap<String, Integer> index = new HashMap<>();

	public ZKPath getNextNode(ZKPath root, String name, ReentrantReadWriteLock lock) {
		lock.readLock().lock();
		ZKPath node = null;
		try {
			if (root != null && root.getChirldren() != null && root.getChirldren().size() > 0) {
                for (ZKPath parentPath : root.getChirldren()) {
                    if (parentPath.getName().equals(name) && parentPath.getChirldren() != null
                            && parentPath.getChirldren().size() > 0) {
                        int position = calculatePosition(parentPath.getChirldren().size(), parentPath.getName());
                        node = parentPath.getChirldren().get(position);
                        break;
                    }
                }
            } else {
                logger.info("index.clear()");
                index.clear();
                //warning
            }
			if (node == null){
                logger.info("getNextNode name:{},root:{},children:{}",name,root,root.getChirldren());
                for (ZKPath parentPath : root.getChirldren()) {
                    logger.debug("parentPath:{},children:{}",parentPath,parentPath.getChirldren());
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
                        node = parentPath.getChirldren().get(position);
                        logger.debug("position:{},node:{}",position,node);
                        if(position+1 >= parentPath.getChirldren().size()) {
                            //index.put(name, 0);
                        } else {
                            //index.put(name, position+1);
                        }
                        break;
                    }
                }

            }
		} finally {
			lock.readLock().unlock();
		}

		return node;
	}

	/**
	 * 计算轮询的位置
	 * @param size 可用节点的数量
	 * @param name 服务名称
	 * @return 轮询到的位置
	 */
	private int calculatePosition(int size, String name) {
		if(!index.containsKey(name)) {
			index.put(name, 0);
		}
		int position = index.get(name);
		if(position >= size) {
			position = 0;
			index.put(name, 0);
		} else {
			index.put(name, position+1);
		}
		return position;
	}
}
