package com.moseeker.position.utils;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 批量更新ES Search Engine
 * Created by yuyunfeng on 2017/3/14.
 */
public class UpdataESThread implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface searchengineServices;

    private List<JobPositionRecord> list;

    public UpdataESThread(SearchengineServices.Iface searchengineServices, List<JobPositionRecord> list) {
        this.searchengineServices = searchengineServices;
        this.list = list;
    }

    @Override
    public void run() {
        logger.info("---Start ES Search Engine---");
        if (list != null && list.size() > 0) {
            for (JobPositionRecord jobPositionRecord : list) {
                try {
                    searchengineServices.updateposition(JSONObject.toJSONString(jobPositionRecord.intoMap()), jobPositionRecord.getId());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        logger.info("--- ES Search Engine end---");
    }


    public SearchengineServices.Iface getSearchengineServices() {
        return searchengineServices;
    }

    public void setSearchengineServices(SearchengineServices.Iface searchengineServices) {
        this.searchengineServices = searchengineServices;
    }


    public List<JobPositionRecord> getList() {
        return list;
    }

    public void setList(List<JobPositionRecord> list) {
        this.list = list;
    }
}
