package com.moseeker.rpccenter.pool;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.listener.ZKPath;
import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * rpc服务调用客户端对象池 
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 * @param <T>
 */
public class TMultiServicePoolFactory<T> extends BaseKeyedPoolableObjectFactory<ZKPath, T> {
    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** {@link TServiceClientFactory }<{@link TServiceClient}> */
    private final TServiceClientFactory<TServiceClient> clientFactory;

    /** 超时时间 */
    private final int timeout;
    
    private final int initialBufferCapacity;

    private final int  maxLength ;

    /**
     * 
     * @param clientFactory
     * @param timeout
     */
    public TMultiServicePoolFactory(TServiceClientFactory<TServiceClient> clientFactory, int timeout,
                                    int initialBufferCapacity, int maxLength) {
        this.clientFactory = clientFactory;
        this.timeout = timeout;
        if(initialBufferCapacity > 0) {
            this.initialBufferCapacity = initialBufferCapacity;
        } else {
            this.initialBufferCapacity = 1024;
        }
        if(maxLength > 0) {
            this.maxLength = maxLength;
        } else {
            this.maxLength = 1024*1024*1024;
        }
    }

    /**
     * 生成对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T makeObject(ZKPath path) throws Exception {
        // 生成client对象
        if (path != null && path.getData() != null) {
            TTransport transport = new TFastFramedTransport(new TSocket(path.getData().getIp(), path.getData().getPort(), timeout), initialBufferCapacity, maxLength);
            TProtocol protocol = new TCompactProtocol(transport);
            TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, path.getData().getService());
            transport.open();
            TServiceClient client = clientFactory.getClient(mulProtocol);
            return (T) client;
        }
        LOGGER.error("Not find a vilid server!");
        if(path != null) {
        	LOGGER.error(path.getData().toString());
	    } else {
	       	 LOGGER.error("path is null");
	    }
        throw new RpcException("Not find a vilid server!");
    }

    @Override
    public void activateObject(ZKPath key, T obj) throws Exception {
        if (obj != null) {
            TTransport tp = ((TServiceClient) obj).getInputProtocol().getTransport();
            if (!tp.isOpen()) {
                tp.open();
            }

            TTransport output = ((TServiceClient) obj).getOutputProtocol().getTransport();
            if(!output.isOpen()) {
                output.open();
            }
        }
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(ZKPath path, T client) throws Exception {
        TTransport tp = ((TServiceClient) client).getInputProtocol().getTransport();
        tp.close();
    }

    @Override
    public boolean validateObject(ZKPath path, T client) {
        TTransport tp = ((TServiceClient) client).getInputProtocol().getTransport();
        return tp.isOpen();
    }
}
