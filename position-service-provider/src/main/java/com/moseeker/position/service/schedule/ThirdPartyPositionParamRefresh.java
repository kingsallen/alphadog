package com.moseeker.position.service.schedule;


import com.moseeker.position.service.position.base.ParamRefresh;
import com.moseeker.position.service.position.veryeast.VeryEastParamRefresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdPartyPositionParamRefresh {
    Logger logger= LoggerFactory.getLogger(ThirdPartyPositionParamRefresh.class);

    private static List<ParamRefresh> refreshList=new ArrayList<>();

    @Autowired
    public ThirdPartyPositionParamRefresh(List<ParamRefresh> list){
        refreshList.addAll(list);
    }

    //服务启动先刷新一次
    @PostConstruct
    public void init(){
        logger.info("Refresh third party position param when server start");
        refresh();
    }

    @Scheduled(cron = "* * 1 * * SAT")
    public void refresh(){
        refreshList.forEach(r->r.refresh());
    }
}
