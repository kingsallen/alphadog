package com.moseeker.useraccounts.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.moseeker.rpccenter.common.ServerNodeUtils;
import com.moseeker.rpccenter.main.MultiRegServer;
import com.moseeker.useraccounts.thrift.UserCommonThriftService;
import com.moseeker.useraccounts.thrift.UserHrAccountServiceImpl;
import com.moseeker.useraccounts.thrift.UseraccountsServiceImpl;
import com.moseeker.useraccounts.thrift.UsersettingsServicesImpl;

/**
 * 
 * 服务启动入口。
 * 
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Mar 27, 2016
 * </p>
 * 
 * @author yaofeng
 * @version Beta
 */
public class UseraccountsServer {
    
    private static Logger LOGGER = LoggerFactory.getLogger(UseraccountsServer.class);
    
    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext acac = initSpring();
            MultiRegServer server = new MultiRegServer(UseraccountsServer.class,
                    ServerNodeUtils.getPort(args),
                    acac.getBean(UserHrAccountServiceImpl.class),
                    acac.getBean(UsersettingsServicesImpl.class),
                    acac.getBean(UserCommonThriftService.class),
                    acac.getBean(UseraccountsServiceImpl.class));
            
            server.start(); // 阻塞式IO + 多线程处理

            synchronized (UseraccountsServer.class) {
                while (true) {
                    try {
                        UseraccountsServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider UseraccountsServer error", e);
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            LOGGER.error("error", e);
        }
    }

    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.scan("com.moseeker.useraccounts");
        acac.scan("com.moseeker.common.aop.iface");
        acac.refresh();
        return acac;
    }
}
