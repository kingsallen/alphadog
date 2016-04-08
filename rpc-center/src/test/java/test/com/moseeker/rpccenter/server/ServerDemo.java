package test.com.moseeker.rpccenter.server;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import test.com.moseeker.rpccenter.demo.EchoServiceImpl;

/**
 * Created by zzh on 16/3/29.
 */
public class ServerDemo {

    /** 是否保持启动 */
    private static boolean running = true;

    /**
     * @param args
     */
    public static void main(String[] args) {

        EchoServiceImpl impl = new EchoServiceImpl();

        try {
            Server server = new Server(ServerDemo.class, impl, ServerNodeUtils.getPort(args));
            server.start(); // 启动服务，非阻塞

            synchronized (ServerDemo.class) {
                while (running) {
                    try {
                        System.out.println("release thread pool before");
                        ServerDemo.class.wait();
                        System.out.println("release thread pool after");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            ServerNodeUtils.providerUsagePrint();
            e.printStackTrace();
        }
    }
}
