package com.moseeker.rpccenter.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * Created by zzh on 16/3/28.
 */
public class MultiTServerThread extends Thread {

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
	public MultiTServerThread(TProcessor processor, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads)
			throws RpcException {

		// TServerSocket serverTransport;
		// try {
		// serverTransport = new TServerSocket(serverNode.getPort());
		// } catch (TTransportException e) {
		// throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
		// }
		//
		// TProtocolFactory proFactory = new TCompactProtocol.Factory();
		//
		// Args args = new Args(serverTransport);
		// args.processor(processor);
		// args.protocolFactory(proFactory);
		// args.maxWorkerThreads(maxWorkerThreads);
		// args.minWorkerThreads(minWorkerThreads);
		//
		// server = new TThreadPoolServer(args);
		// TNonblockingServerSocket tnbSocketTransport;
		// try {
		// tnbSocketTransport = new TNonblockingServerSocket(12345);
		//
		// } catch (TTransportException e) {
		// // TODO Auto-generated catch block
		// throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
		// }
		//
		// TNonblockingServer.Args tnbArgs = new
		// TNonblockingServer.Args(tnbSocketTransport);
		// tnbArgs.processor(processor);
		// tnbArgs.transportFactory(new TFramedTransport.Factory());
		// tnbArgs.protocolFactory(new TCompactProtocol.Factory());
		//
		// // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
		// server = new TNonblockingServer(tnbArgs);
		// server.serve();
		TNonblockingServerTransport serverTransport;
		try {
			// 传输通道 - 非阻塞方式
			serverTransport = new TNonblockingServerSocket(serverNode.getPort());

		} catch (Exception e) {
			throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
		}

		// 异步IO，需要使用TFramedTransport，它将分块缓存读取。
		TTransportFactory transportFactory = new TFastFramedTransport.Factory();
		
		// 使用高密度二进制协议
		TProtocolFactory proFactory = new TCompactProtocol.Factory();
		Args args = new Args(serverTransport).protocolFactory(proFactory).transportFactory(transportFactory)
				.processor(processor);
		args.selectorThreads(minWorkerThreads);
		args.workerThreads(maxWorkerThreads);

		// 创建服务器
		server = new TThreadedSelectorServer(args);
		//server.serve();

	}

	/**
	 * @param tServer
	 *            {@link TServer}
	 */
	public MultiTServerThread(TServer tServer) {
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
		LOGGER.info("MultiTServerThread Server stopServer!");
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
