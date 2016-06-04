package com.moseeker.rpccenter.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * Created by zzh on 16/3/28.
 */
public class TServerThread extends Thread {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** {@link TServer} */
    private final TServer server;

    /**
     *
     * @param serverNode
     *            {@link ServerNode}
     * @param maxWorkerThreads
     *            最大工作线程数
     * @param minWorkerThreads
     *            最小工作线程数
     * @throws RpcException
     */
    public TServerThread(TProcessor processor, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads) throws RpcException {

        TServerSocket serverTransport;
        try {
            serverTransport = new TServerSocket(serverNode.getPort());
        } catch (TTransportException e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
        }

        TProtocolFactory proFactory = new TCompactProtocol.Factory(); 
        
        Args args = new Args(serverTransport);
        args.processor(processor);
        args.protocolFactory(proFactory);
        args.maxWorkerThreads(maxWorkerThreads);
        args.minWorkerThreads(minWorkerThreads);

        server = new TThreadPoolServer(args);
//    	TNonblockingServerSocket tnbSocketTransport;
//		try {
//			tnbSocketTransport = new TNonblockingServerSocket(12345);
//
//		} catch (TTransportException e) {
//			// TODO Auto-generated catch block
//			throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
//		}
//
//		TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
//		tnbArgs.processor(processor);
//		tnbArgs.transportFactory(new TFramedTransport.Factory());
//		tnbArgs.protocolFactory(new TCompactProtocol.Factory());
//
//		// 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
//		server = new TNonblockingServer(tnbArgs);
//		server.serve();  	 
    	
    	
    	

    }
    
    /**
     * @param tServer
     *            {@link TServer}
     */
    public TServerThread(TServer tServer) {
        this.server = tServer;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Server is start!");
            server.serve();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 停止服务
     * <p>
     */
    public void stopServer() {
        server.stop();
        LOGGER.info("Server is stop!");
    }

    /**
     * TServer是否启动
     * <p>
     *
     * @return
     */
    public boolean isStarted() {
        return server.isServing();
    }

}
