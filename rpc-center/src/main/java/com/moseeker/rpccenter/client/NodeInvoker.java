package com.moseeker.rpccenter.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.listener.NodeManager;
import com.moseeker.rpccenter.listener.ZKPath;
import com.moseeker.rpccenter.loadbalance.NodeLoadBalance;

public class NodeInvoker<T> implements Invoker {
	
	private int retry = 3;		//重试次数
	private GenericKeyedObjectPool<ZKPath, T> pool;
	private String parentName;
	
	public NodeInvoker(GenericKeyedObjectPool<ZKPath, T> pool, String parentName, int retry) {
		this.pool = pool;
		this.parentName = parentName;
		this.retry = retry;
	}

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object invoke(Method method, Object[] args) throws RpcException {
		T client = null;
        ZKPath root = null;
		try {
			root = NodeManager.NODEMANAGER.getRoot();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RpcException("服务超时，请稍候再试!", e1);
		} finally {
			
		}
        Throwable exception = null;
        ZKPath node = null;
        for (int i = 0; i == 0 || i < retry + 1; i++) {
            try {
                node = NodeLoadBalance.LoadBalance.getNextNode(root, parentName);
                if (node == null) {
                    continue;
                }
                LOGGER.info(node.toString());
                client = pool.borrowObject(node);
                System.out.println("after borrowObject getNumActive:"+pool.getNumActive());
                Object result = method.invoke(client, args);
                
                return result;
            } catch (InvocationTargetException ite) {// XXX:InvocationTargetException异常发生在method.invoke()中
                Throwable cause = ite.getCause();
                
              //  cause.getMessage()
                if (cause != null) {
                    if (cause instanceof TTransportException) {

                        // 超时
                        // hostSet.addDeadInstance(serverNode); // 加入dead集合中

                        exception = cause;
                        try {
                            // XXX:这里直接清空pool,否则会出现连接慢恢复的现象
                            // 发送socket异常时，证明socket已经失效，需要重新创建
                            if (cause.getCause() != null && cause.getCause() instanceof SocketException) {
                                pool.clear(node);
                                //Notification.sendThriftConnectionError(serverNode+"  socket已经失效, error:"+ite.getMessage());
                                LOGGER.error(node+"  socket已经失效, error:"+ite.getMessage(), ite);
                                LOGGER.debug("after clear getNumActive:"+pool.getNumActive());
                            } else {
                                // XXX:其他异常的情况，需要将当前链接置为无效
                                pool.invalidateObject(node, client);
                                //Notification.sendThriftConnectionError(serverNode+"  链接置为无效, error:"+ite.getMessage());
                                LOGGER.error(node+"  链接置为无效, error:"+ite.getMessage(), ite);
                                LOGGER.debug("after invalidateObject getNumActive:"+pool.getNumActive());
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        client = null; // 这里是为了防止后续return回pool中
                    } else {
                        exception = cause;
                    }
                } else {
                    exception = ite;
                }
                LOGGER.error(ite.getMessage(), ite);
            } catch (Throwable e) {
            	e.printStackTrace();
            	LOGGER.debug("after returnObject getNumActive:"+pool.getNumActive());
            	LOGGER.error(e.getMessage(), e);
                exception = e;
            } finally {
                if (client != null) {
                    try {
                        pool.returnObject(node, client);
                        LOGGER.debug("after returnObject getNumActive:"+pool.getNumActive());
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
        throw new RpcException("服务超时，请稍候再试!", exception);
	}

	
}
