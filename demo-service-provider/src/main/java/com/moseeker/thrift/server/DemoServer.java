package com.moseeker.thrift.server;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.thrift.service.EchoServiceImpl;

/**
 * Created by zzh on 16/3/31.
 */
public class DemoServer {

    /** 是否保持启动 */
    private static boolean running = true;

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            EchoServiceImpl impl = new EchoServiceImpl();
            Server server = new Server(DemoServer.class, impl, ServerNodeUtils.getPort(args));
            server.start(); // 启动服务，非阻塞

            synchronized (DemoServer.class) {
                while (running) {
                    try {
                        System.out.println("release thread pool before");
                        DemoServer.class.wait();
                        System.out.println("release thread pool after");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            ServerNodeUtils.providerUsagePrint();
            e.printStackTrace();
            System.exit(1);
        }
    }
}

