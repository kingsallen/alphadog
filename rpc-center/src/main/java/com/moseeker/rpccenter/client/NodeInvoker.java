package com.moseeker.rpccenter.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketException;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.listener.NodeManager;
import com.moseeker.rpccenter.listener.ZKPath;
import com.moseeker.rpccenter.loadbalance.NodeLoadBalance;

/**
 *
 * 代理类的具体执行类
 * <p>Company: MoSeeker</P>
 * <p>date: Jul 27, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 * @param <T>
 */
public class NodeInvoker<T> implements Invoker {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 重试次数
     */
	private int retry = 1;
    /**
     * 节点对象池
     */
	private GenericKeyedObjectPool<ZKPath, T> pool;
    /**
     * 二级节点名称(/services(一级节点名称)/com.moseeker.thrift.gen.profile.service.WholeProfileServices(二级节点名称)/servers
     */
	private String parentName;

	/**
	 * 初始化执行类
	 * @param pool zookeeper可用节点的对象池
	 * @param parentName 二级节点名称
	 * @param retry	重试次数
	 */
	public NodeInvoker(GenericKeyedObjectPool<ZKPath, T> pool, String parentName, int retry) {
		this.pool = pool;
		this.parentName = parentName;
		this.retry = retry;
	}

	/**
	 * 代理类的逻辑代码
	 * 先从节点管理中心获取完整的节点数据（树形结构），
	 * 并根据二级节点路径和利用负载均衡器查找二级节点下的三级节点
	 * 根据查找到的三级节点，从rpc客户端中获取一个rpc客户端
	 * 执行具体的调用方法
	 *
	 */
	@Override
	public Object invoke(Method method, Object[] args) throws CURDException, BIZException, RpcException {
		T client = null;
        ZKPath root = null;
		try {
			root = NodeManager.NODE_MANAGER.getRoot();
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error(e1.getMessage(), e1);
			throw new RpcException("服务超时，请稍候再试!", e1);
		} finally {

		}
        Throwable exception = null;
        ZKPath node = null;
        for (int i = 0; i < 2; i++) {
            try {
                node = NodeLoadBalance.LoadBalance.getNextNode(root, parentName, NodeManager.NODE_MANAGER.getLock());
                if (node == null || node.getData() == null) {
                	LOGGER.error("retry:"+(i+1));
                	LOGGER.error(parentName+"  Can't find node!");
                	//warning
                    continue;
                }
                LOGGER.debug("NodeInvoker invoke node.name:{}, node.data:{}", JSONObject.toJSONString(node.getName()), JSONObject.toJSONString(node.getData()));
                client = pool.borrowObject(node);
                LOGGER.debug("node:{}, getNumActive:{}",node,pool.getNumActive());
                Object result = method.invoke(client, args);

                return result;
            } catch (CURDException | BIZException ce) {
                throw ce;
            } catch (ConnectException | TTransportException ce) {
                LOGGER.error("ConnectException:"+ce.getMessage(), ce);
            	pool.clear(node);
                NodeManager.NODE_MANAGER.removePath(node);
                client = null;
            } catch (InvocationTargetException ite) {
                Throwable cause = ite.getCause();
                if (cause != null) {
                    if (cause instanceof CURDException) {
                        throw  (CURDException)cause;
                    }
                    if (cause instanceof BIZException) {
                        throw  (BIZException)cause;
                    }
                    if (cause instanceof TTransportException || cause instanceof TApplicationException) {

                        // 超时
                        exception = cause;
                        try {
                            if (cause.getCause() != null && cause.getCause() instanceof SocketException) {
                                pool.clear(node);
                                LOGGER.error(node+"  socket已经失效, error:"+ite.getMessage(), ite);
                                LOGGER.error("parentName:{}  node:{}", parentName, node);
                                LOGGER.debug("after clear getNumActive:{}",pool.getNumActive());
                            } else {
                                LOGGER.error(node+"  链接置为无效, error:"+ite.getMessage(), ite);
                                LOGGER.debug("after invalidateObject getNumActive:{}",pool.getNumActive());
                                pool.invalidateObject(node, client);
                            }
                            client = null;
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        //client = null; // 这里是为了防止后续return回pool中
                    } else {
                        exception = cause;
                    }
                } else {
                    exception = ite;
                }
                LOGGER.error(ite.getMessage(), ite);
            } catch (Throwable e) {
            	LOGGER.debug("after returnObject getNumActive:{}",pool.getNumActive());
            	LOGGER.error(e.getMessage(), e);
                exception = e;
            } finally {
                if (client != null) {
                    try {
                        pool.returnObject(node, client);
                        LOGGER.debug("after returnObject getNumActive:{}",pool.getNumActive());
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
        throw new RpcException(exception.getMessage(), exception);
	}


}
