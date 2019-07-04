package com.moseeker.rpccenter.loadbalance;

import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.rpccenter.listener.ZKPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
	 * 负载均衡工具
	 */
	LoadBalance;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private HashMap<String, Integer> index = new HashMap<>();
	private Map<ZKPath, Long> errorStorage = new HashMap<>();

	/**
	 * 定时报错
	 */
	private ScheduledThread scheduledThread = ScheduledThread.Instance;

	public synchronized ZKPath getNextNode(ZKPath root, String name) {
		ZKPath node = null;
		if (root != null && root.getChirldren() != null && root.getChirldren().size() > 0) {
			for (ZKPath parentPath : root.getChirldren()) {
				if (parentPath.getName().equals(name) && parentPath.getChirldren() != null
						&& parentPath.getChirldren().size() > 0) {
					int position = calculatePosition(parentPath.getChirldren().size(), parentPath.getName());
					node = getNode(position, parentPath, 0);
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
		}
		return position;
	}

	/**
	 * 根据位置查找可用节点
	 * 如果查找的节点是不可用节点，那么跳过该节点，获取下一个节点。
	 *
	 * @param position 位置
	 * @param parentPath 父节点
	 * @param operationCount 操作次数
	 * @return
	 */
	private ZKPath getNode(int position, ZKPath parentPath, int operationCount) {
		if (operationCount >= parentPath.getChirldren().size()) {
			return null;
		}
		ZKPath node = parentPath.getChirldren().get(position);
		if (errorStorage.containsKey(node)) {
			return getNode(position+1, parentPath, operationCount+1);
		} else {
			return parentPath.getChirldren().get(position);
		}
	}

	/**
	 * 添加错误节点
	 * 如果发生ConnectionException，那么该节点进入异常节点。
	 * 对外提供可用节点时，过滤这些异常节点
	 * @param zkPath 节点数据
	 */
	public void addErrorNode(ZKPath zkPath) {
		this.errorStorage.put(zkPath, System.currentTimeMillis());
	}
}
