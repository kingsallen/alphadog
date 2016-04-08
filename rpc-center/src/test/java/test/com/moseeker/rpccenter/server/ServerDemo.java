package test.com.moseeker.rpccenter.server;

import com.moseeker.rpccenter.main.Server;
import test.com.moseeker.rpccenter.demo.EchoServiceImpl;

/**
 * Created by zzh on 16/3/29.
 */
public class ServerDemo {

    /** 配置文件路径，配置说明参考 {@link Server#Server(String , Object )} */
    public static final String CONFIG_FILE_PATH = "classpath:demo/demo-server.properties";

    /** 是否保持启动 */
    private static boolean running = true;

    /**
     * @param args
     */
    public static void main(String[] args) {

//        EchoServiceImpl impl = new EchoServiceImpl();
//
//        try {
//            Server server = new Server(CONFIG_FILE_PATH, impl);
//            server.start(); // 启动服务，非阻塞
//
//            synchronized (ServerDemo.class) {
//                while (running) {
//                    try {
//                        System.out.println("release thread pool before");
//                        ServerDemo.class.wait();
//                        System.out.println("release thread pool after");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
