package com.moseeker.position.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 批量更新ES Search Engine
 * Created by yuyunfeng on 2017/3/14.
 */
public class UpdateESThread implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface searchengineServices;

    private com.moseeker.thrift.gen.company.service.CompanyServices.Iface companyServices;


    private List<JobPositionRecord> list;

    public UpdateESThread(SearchengineServices.Iface searchengineServices, CompanyServices.Iface companyServices, List<JobPositionRecord> list) {
        this.searchengineServices = searchengineServices;
        this.companyServices = companyServices;
        this.list = list;
    }

    @Override
    public void run() {
        logger.info("---Start ES Search Engine---");
        if (list != null && list.size() > 0) {
            String companyId = BeanUtils.converToString(list.get(0).getCompanyId().intValue());

            QueryUtil query = new QueryUtil();
            query.addEqualFilter("id", companyId);
            Response company_resp = null;
            try {
                company_resp = companyServices.getAllCompanies(query);
            } catch (TException e) {
                logger.error(e.getMessage(), e);
            }

            String company = company_resp.data;
            List company_maps = (List) JSON.parse(company);
            Map company_map = (Map) company_maps.get(0);
            String company_name = (String) company_map.get("name");
            String scale = (String) company_map.get("scale");

            for (JobPositionRecord jobPositionRecord : list) {
                try {
                    Map map = jobPositionRecord.intoMap();
                    map.put("company_name", company_name);
                    map.put("scale", scale);
                    String degree_name = BeanUtils.converToString(map.get("degree_name"));
                    Integer degree_above = BeanUtils.converToInteger(map.get("degree_above"));
                    if (degree_above == 1) {
                        degree_name = degree_name + "及以上";
                    }
                    map.put("degree_name", degree_name);
                    searchengineServices.updateposition(JSONObject.toJSONString(map), jobPositionRecord.getId());
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
