package com.moseeker.talentpool;

import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.main.MoServer;
import com.moseeker.talentpool.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 人才库服务启动入口
 * Created by jack on 27/11/2017.
 */
public class TalentPoolServer {

    private static Logger logger = LoggerFactory.getLogger(TalentPoolServer.class);

    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext acac = initSpring();
            MoServer server = new MoServer(acac,
                    "", null);
            // 启动服务，非阻塞
            try {
                server.startServer();
                server.shutDownHook();
                synchronized (TalentPoolServer.class) {
                    while (true) {
                        try {
                            TalentPoolServer.class.wait();
                        } catch (Exception e) {
                            logger.error(" service provider ProfileServer error", e);
                        }
                    }
                }
            } catch (IncompleteException | RpcException | RegisterException | BeansException e) {
                e.printStackTrace();
                server.stopServer();
            } finally {
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    /**
     * 初始化spring容器
     * @return
     */
    private static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.register(AppConfig.class);
        acac.refresh();
        return acac;
    }
}
