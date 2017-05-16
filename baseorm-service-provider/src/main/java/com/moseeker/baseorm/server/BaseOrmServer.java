package com.moseeker.baseorm.server;

import com.moseeker.baseorm.Thriftservice.*;
import com.moseeker.rpccenter.main.MoServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * baseorm-service-provider的启动类
 */
public class BaseOrmServer {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseOrmServer.class);

    public static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
        annConfig.scan("com.moseeker.baseorm");
        annConfig.refresh();
        return annConfig;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AnnotationConfigApplicationContext acac = initSpring();
        try {
            MoServer server = new MoServer(
                    acac, "",
                    acac.getBean(HRAccountDaoThriftService.class),
                    acac.getBean(WordpressDaoThriftService.class),
                    acac.getBean(CompanyThriftService.class),
                    acac.getBean(PositionDaoThriftService.class),
                    acac.getBean(UserDBDaoThriftService.class),
                    acac.getBean(DictDaoThriftService.class),
                    acac.getBean(DictDaoMapThriftService.class),
                    acac.getBean(JobDBDaoThriftService.class),
                    acac.getBean(ApplicationDaoThriftService.class),
                    acac.getBean(ConfigDBDaoThriftService.class),
                    acac.getBean(HrDBThriftService.class),
                    acac.getBean(CandidateDaoThriftService.class),
                    acac.getBean(SearchConditionDaoThriftService.class),
                    acac.getBean(ProfileDBDaoThriftService.class),
                    acac.getBean(WxUserDaoThriftService.class),
                    acac.getBean(LogDBDaoThriftService.class),
                    acac.getBean(ThirdPartyUserDaoThriftService.class),
                    acac.getBean(UserEmployeeDaoThriftService.class),
                    acac.getBean(ProfileProfileDaoThriftService.class),
                    acac.getBean(TalentpoolDaoThriftService.class),
                    acac.getBean(CampaignDaoThriftService.class));
            server.startServer();
            server.shutDownHook();
            synchronized (BaseOrmServer.class) {
                while (true) {
                    try {
                        BaseOrmServer.class.wait();
                    } catch (Exception e) {
                        LOGGER.error(" service provider BaseOrmServer error", e);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error", e);
        }
    }

}
