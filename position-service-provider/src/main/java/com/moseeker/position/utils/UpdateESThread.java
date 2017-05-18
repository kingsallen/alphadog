package com.moseeker.position.utils;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.CompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
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
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private CompanyDao companyDao;

    private List<Integer> list;

    public UpdateESThread(SearchengineServices.Iface searchengineServices, List<Integer> list, JobPositionDao jobPositionDao) {
        this.searchengineServices = searchengineServices;
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
            	Query qu=new Query.QueryBuilder().where("id", jobPositionId).buildQuery();
                JobPositionRecord jobPositionRecord = jobPositionDao.getRecord(qu);
                Integer companyId = jobPositionRecord.getCompanyId().intValue();
                Query query = new Query.QueryBuilder().where("id",companyId).buildQuery();
                List<Hrcompany> company_maps = null;
                try {
                	company_maps=companyDao.getCompanies(query);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

                logger.info("company:" + company_maps);
                Hrcompany company_map=company_maps.get(0);
                String company_name =company_map.getName();
                String scale =company_map.getScale();
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
