package com.moseeker.rpccenter.pool;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.listener.ZKPath;

/**
 * Thrift连接池
 * Created by zzh on 16/3/30.
 */
public class TServicePoolFactory<T> extends BaseKeyedPoolableObjectFactory<ZKPath, T> {
    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** {@link TServiceClientFactory }<{@link TServiceClient}> */
    private final TServiceClientFactory<TServiceClient> clientFactory;

    /** 超时时间 */
    private final int timeout;

    /**
     * @param clientFactory
     * @param timeout
     */
    public TServicePoolFactory(TServiceClientFactory<TServiceClient> clientFactory, int timeout) {
        this.clientFactory = clientFactory;
        this.timeout = timeout;
    }

    /**
     * 生成对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T makeObject(ZKPath path) throws Exception {
        // 生成client对象
        if (path != null && path.getData() != null) {
        	TTransport transport = new TFramedTransport(new TSocket(path.getData().getIP(), path.getData().getPort(), timeout));
            TProtocol protocol = new TCompactProtocol(transport);
            transport.open();
            TServiceClient client = clientFactory.getClient(protocol);
            return (T) client;
        }
        LOGGER.error("Not find a vilid server!");
        throw new RpcException("Not find a vilid server!");
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(ZKPath path, T client) throws Exception {
        TTransport tp = ((TServiceClient) client).getInputProtocol().getTransport();
        tp.close();
    }

    /**
     * 验证链接有效性 <br/>
     * 注意：在服务端口异常关闭的情况下，<code>tp.isOpen()</code>
     * 仍然返回true,所以，正常情况下应该进行socket验证，考虑到服务异常时使用了deadmark算法切换服务，故这里就不需要下面的验证代码了。
     *
     * <pre>
     * <code>
     *    Socket socket = null;
     *         try {
     *             InetSocketAddress socketAddress = new InetSocketAddress(key.getIp(), key.getPort());
     *             socket = new Socket();
     *             socket.connect(socketAddress, 1000);
     *         } catch (IOException e) {
     *             LOGGER.warn(e.getMessage(), e);
     *             return false;
     *         } finally {
     *             if (socket != null) {
     *                 try {
     *                     socket.close();
     *                 } catch (IOException e) {
     *                     LOGGER.warn(e.getMessage(), e);
     *                 }
     *             }
     *         }
     * </code>
     * </pre>
     */
    @Override
    public boolean validateObject(ZKPath path, T client) {
        TTransport tp = ((TServiceClient) client).getInputProtocol().getTransport();
        return tp.isOpen();
    }
}
