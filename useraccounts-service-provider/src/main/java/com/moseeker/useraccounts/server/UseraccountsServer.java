package com.moseeker.useraccounts.server;

import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.useraccounts.thrift.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 服务启动入口。
 * <p>
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
            MoServer server = new MoServer(acac, "",
                    acac.getBean(UserHrAccountServiceImpl.class),
                    acac.getBean(UsersettingsServicesImpl.class),
                    acac.getBean(UserCommonThriftService.class),
                    acac.getBean(UserCenterThriftService.class),
                    acac.getBean(UseraccountsServiceImpl.class),
                    acac.getBean(ThirdPartyUserServiceImpl.class),
                    acac.getBean(UserEmployeeServiceImpl.class),
                    acac.getBean(EmployeeServiceImpl.class));
            server.startServer();


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
        acac.scan("com.moseeker.common.aop.notify");
        acac.refresh();
        return acac;
    }
}
