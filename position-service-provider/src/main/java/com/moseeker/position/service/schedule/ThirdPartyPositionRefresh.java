package com.moseeker.position.service.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdPartyPositionRefresh {

    private static List<ParamRefresh> refreshList=new ArrayList<>();

    @PostConstruct
    public void init(){
        //增加需要刷新的渠道


        //服务启动先刷新一次
        refresh();
    }

    @Scheduled(cron = "* * 1 * * SAT")
    public void refresh(){
        refreshList.forEach(r->r.refresh());
    }
}
