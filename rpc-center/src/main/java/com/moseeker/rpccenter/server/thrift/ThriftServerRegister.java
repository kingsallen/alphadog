package com.moseeker.rpccenter.server.thrift;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.config.ThriftConfig;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.proxy.DynamicServiceHandler;
import com.moseeker.rpccenter.server.IServer;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * thrift服务注册工具
 * Created by jack on 06/02/2017.
 */
public class ThriftServerRegister implements IServer {

    private Logger logger = LoggerFactory.getLogger(ThriftServerRegister.class);

    private ThriftConfig config;            //thrift 配置信息
    private TServer server;                 //thrift 服务
    private volatile boolean isStart;       //thrift服务是否开启
    private Thread thread;

    /**
     * 初始化thrift服务注册工具
     * @param config thift服务配置信息
     * @throws IncompleteException 配置信息不全，可能会引起服务无法正常初始化
     * @throws RpcException 传输通道建立失败
     */
    public ThriftServerRegister(ThriftConfig config) throws IncompleteException, RpcException {

        if(!config.check()) {
            throw new IncompleteException();
        }
        this.config = config;

        initServer();

    }

    /**
     * 初始化thrift服务
     * @throws RpcException 传输通道建立失败
     */
    private void initServer() throws RpcException {
        TNonblockingServerTransport serverTransport;
        try {
            // 传输通道 - 非阻塞方式
            serverTransport = new TNonblockingServerSocket(config.getPort());
            logger.info("NOC ThriftServerRegister initServer port:{}", config.getPort());
        } catch (Exception e) {
            throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
        }

        // 异步IO，需要使用TFramedTransport，它将分块缓存读取。
        TTransportFactory transportFactory = new TFastFramedTransport.Factory(config.getInitialCapacity(), config.getMaxLength());

        // 使用高密度二进制协议
        TProtocolFactory proFactory = new TCompactProtocol.Factory();
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport).protocolFactory(proFactory).transportFactory(transportFactory)
                .processor(reflectProcessor(config.getServers()));
        args.selectorThreads(config.getSelector());
        args.workerThreads(config.getWorker());
        args.maxReadBufferBytes = Integer.MAX_VALUE;
        // 创建服务器
        server = new TThreadedSelectorServer(args);
    }

    /**
     * 开启服务
     * @throws IncompleteException 配置信息不全，可能会引起服务无法正常初始化
     * @throws RpcException 传输通道建立失败
     */
    @Override
    public void start() throws IncompleteException, RpcException {
        logger.info("Server is start!");
        if(server != null) {
            logger.info("ThriftServerRegister start server != null");
            if(thread != null && !thread.isInterrupted()) {
                logger.info("ThriftServerRegister start before thread.isInterrupted()");
                thread.interrupt();
                logger.info("ThriftServerRegister start after thread.isInterrupted()");
                thread = null;
            }
            logger.info("ThriftServerRegister start before new thread");
            thread = new Thread(() -> server.serve());
            logger.info("ThriftServerRegister start after new thread");
            thread.start();
            logger.info("ThriftServerRegister start start()");
        } else {
            logger.info("ThriftServerRegister start server is null");
            if(thread != null && !thread.isInterrupted()) {
                logger.info("ThriftServerRegister start server is null before thread.isInterrupted()");
                thread.interrupt();
                logger.info("ThriftServerRegister start server is null after thread.isInterrupted()");
                thread = null;
            }
            initServer();
            thread = new Thread(() -> server.serve());
            thread.start();
            logger.info("ThriftServerRegister start server start()");
        }
        isStart = true;
    }

    /**
     * 停止服务
     */
    @Override
    public void stop() {
        if(server != null) {
            logger.info("ThriftServerRegister stop before server.stop()");
            server.stop();
            server = null;
            logger.info("ThriftServerRegister stop after server.stop()");
            if(thread != null && !thread.isInterrupted()) {
               /* logger.info("ThriftServerRegister stop before thread.interrupt()");
                thread.interrupt();
                logger.info("ThriftServerRegister stop after thread.interrupt()");
                try {
                    logger.info("ThriftServerRegister stop before thread.join()");
                    thread.join();
                    logger.info("ThriftServerRegister stop after thread.join()");
                } catch (InterruptedException e) {
                    logger.info("ThriftServerRegister stop InterruptedException e");
                    logger.error(e.getMessage(), e);
                    thread.interrupt();
                }*/
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    thread.interrupt();
                    logger.error(e.getMessage(), e);
                }
                thread = null;
            }
        }
        isStart = false;
    }

    /**
     * 查看服务是否启动
     * @return true 服务已经启动;false 服务未启动
     */
    @Override
    public boolean isStarted() {
        int i = 0;
        while (!isStart && i< config.getRetry()) {
            try {
                Thread.sleep(100 * i++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isStart;
    }

    /**
     * 创建TMultiplexedProcessor
     * @param map thrift服务名称和实现类
     * @return 创建TMultiplexedProcessor
     * @throws RpcException 代理了反射涉及的一些异常，如 ClassNotFoundException,NoSuchMethodException,
     * IllegalAccessException,InvocationTargetException,InstantiationException
     */
    private TProcessor reflectProcessor(Map<String, Object> map) throws RpcException {
        TMultiplexedProcessor multiProcessor = new TMultiplexedProcessor();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(map == null || map.size() == 0) {
            throw new RpcException("服务不存在!");
        }
        map.forEach((serverName, obj) -> {
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            if (interfaces.length == 0) {
                throw new RpcException("Service class should implements Iface!");
            }

            for (Class c : interfaces) {
                String cname = c.getSimpleName();
                if (!cname.equals("Iface")) {
                    continue;
                }
                String pname = c.getEnclosingClass().getName() + "$Processor";
                try {
                    Class<?> pclass = classLoader.loadClass(pname);
                    Constructor constructor = pclass.getConstructor(c);
                    TProcessor processor = (TProcessor) constructor.newInstance(getProxy(classLoader, c, obj, null));
                    multiProcessor.registerProcessor(serverName, processor);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                        InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                    throw new RpcException("初始化Processor失败!");
                }
            }
        });
        return multiProcessor;
    }

    /**
     * 获取处理类代理
     * @param classLoader
     * @param interfaces
     * @param object
     * @param serverNode
     * @return
     * @throws ClassNotFoundException
     */
    private Object getProxy(ClassLoader classLoader, Class<?> interfaces, Object object, ServerNode serverNode) throws ClassNotFoundException {
        DynamicServiceHandler dynamicServiceHandler = new DynamicServiceHandler();
        return dynamicServiceHandler.bind(classLoader, interfaces, object, serverNode);
    }
}
