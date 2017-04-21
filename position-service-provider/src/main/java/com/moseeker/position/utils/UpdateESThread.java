package com.moseeker.position.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.dao.JobPositionDao;
import com.moseeker.position.service.fundationbs.PositionService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private JobPositionDao jobPositionDao;

    @Autowired
    private PositionService positionService;

    private List<Integer> list;

    public UpdateESThread(SearchengineServices.Iface searchengineServices, CompanyServices.Iface companyServices, List<Integer> list, JobPositionDao jobPositionDao) {
        this.searchengineServices = searchengineServices;
        this.companyServices = companyServices;
        this.list = list;
        this.jobPositionDao = jobPositionDao;
    }

    @Override
    public void run() {
        logger.info("---Start ES Search Engine---");
        if (list != null && list.size() > 0) {
            logger.info("需要更新ES总条数：" + list.size());
            logger.info("需要更新ESJobPostionIDs：" + list.toString());
            for (Integer jobPositionId : list) {
                JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(jobPositionId);
                Integer companyId = jobPositionRecord.getCompanyId().intValue();

                QueryUtil query = new QueryUtil();
                query.addEqualFilter("id", String.valueOf(companyId));
                Response company_resp = null;
                try {
                    company_resp = companyServices.getAllCompanies(query);
                } catch (TException e) {
                    logger.error(e.getMessage(), e);
                }

                String company = company_resp.data;
                logger.info("company:" + company);

                List company_maps = (List) JSON.parse(company);
                Map company_map = (Map) company_maps.get(0);
                String company_name = (String) company_map.get("name");
                String scale = (String) company_map.get("scale");
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
                    logger.info("-- JobPositionJOSN -- :", JSONObject.toJSONString(map));
                    logger.info("-- JobPositionId -- :", jobPositionRecord.getId());
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


    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public JobPositionDao getJobPositionDao() {
        return jobPositionDao;
    }

    public void setJobPositionDao(JobPositionDao jobPositionDao) {
        this.jobPositionDao = jobPositionDao;
    }
}
