package com.moseeker.thrift.server;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.Server;
import com.moseeker.thrift.service.impl.CompanyfollowerServicesImpl;

/**
 * 
 * 服务启动入口。服务启动依赖所需的配置文件serviceprovider.properties中的配置信息。务必保证配置信息正确
 *  
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class CompanyfollowersServer {

	public static void main(String[] args) {
		
        try {
            Server server = new Server(CompanyfollowersServer.class, new CompanyfollowerServicesImpl(), ServerNodeUtils.getPort(args));
            server.start(); // 启动服务，非阻塞

            synchronized (CompanyfollowersServer.class) {
                while (true) {
                    try {
                        System.out.println("release thread pool before");
                        CompanyfollowersServer.class.wait();
                        System.out.println("release thread pool after");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
