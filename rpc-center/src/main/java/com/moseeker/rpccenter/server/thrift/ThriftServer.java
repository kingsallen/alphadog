package com.moseeker.rpccenter.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.server.IServer;
import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * Created by zzh on 16/3/28.
 */
public class ThriftServer implements IServer {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** 服务线程 */
    private final TServerThread serverThread;

    /** 是否已经启动 */
    private boolean isStarted = false;

    /**
     * @param server
     *            {@link TServer}
     */
    public ThriftServer(TServer server) {
        this.serverThread = new TServerThread(server);
    }

    /**
     * @param processor
     *            {@link TProcessor}
     * @param serverNode
     *            {@link ServerNode}
     * @param maxWorkerThreads
     *            最大工作线程数
     * @param minWorkerThreads
     *            最小工作线程数
     */
    public ThriftServer(TProcessor processor, ServerNode serverNode, int maxWorkerThreads, int minWorkerThreads) throws RpcException {
        this.serverThread = new TServerThread(processor, serverNode, maxWorkerThreads, minWorkerThreads);
    }

    @Override
    public void start() {
        if (!isStarted) {
            serverThread.start();
            isStarted = serverThread.isStarted();
        }
    }

    @Override
    public void stop() {
        if (isStarted) {
            serverThread.stopServer();
            isStarted = false;
        }
    }

    @Override
    public boolean isStarted() {
        int i = 0;
        while (!isStarted && i < 3) {
            isStarted = serverThread.isStarted();
            try {
                Thread.sleep(100 * i++);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return isStarted;
    }
}
