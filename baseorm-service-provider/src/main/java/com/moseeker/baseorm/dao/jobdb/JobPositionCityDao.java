package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;

import java.util.*;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 *         JobPositionCityDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class JobPositionCityDao extends JooqCrudImpl<JobPositionCityDO, JobPositionCityRecord> {

    @Autowired
    DictCityDao dictCityDao;

    public JobPositionCityDao() {
        super(JobPositionCity.JOB_POSITION_CITY, JobPositionCityDO.class);
    }

    public JobPositionCityDao(TableImpl<JobPositionCityRecord> table, Class<JobPositionCityDO> jobPositionCityDOClass) {
        super(table, jobPositionCityDOClass);
    }

    public void delJobPostionCityByPids(List<Integer> pids) {
        try {
            if (pids != null && pids.size() > 0) {
                Condition condition = JobPositionCity.JOB_POSITION_CITY.PID.in(pids);
                create.deleteFrom(JobPositionCity.JOB_POSITION_CITY).where(condition).execute();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
    }

    public List<DictCityDO> getPositionCitys(int id) {
        Query query = new Query.QueryBuilder().where("pid", id).buildQuery();
        List<JobPositionCityDO> jobPositionCityDOS = getDatas(query);

        if (jobPositionCityDOS == null || jobPositionCityDOS.size() == 0) {
            return new ArrayList<>();
        }

        Set<Integer> cityIds = new HashSet<>();

        for (JobPositionCityDO positionCityDO : jobPositionCityDOS) {
            cityIds.add(positionCityDO.getCode());
        }

        query = new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition("code", cityIds, ValueOp.IN)).buildQuery();
        List<DictCityDO> dictCityDOS = dictCityDao.getDatas(query);

        if (dictCityDOS == null || dictCityDOS.size() == 0) {
            return new ArrayList<>();
        }

        return dictCityDOS;
    }

    /**
     * 根据职位id查询job_position_city数据
     * @param id 职位ID
     * @return
     */
    public List<JobPositionCityDO> getPositionCitysByPid(int id) {
        Query query = new Query.QueryBuilder().where("pid", id).buildQuery();
        List<JobPositionCityDO> jobPositionCityDOS = getDatas(query);

        if (jobPositionCityDOS == null) {
            return new ArrayList<>();
        }

        return jobPositionCityDOS;
    }

    /** 
     *
     * @param   
     * @author  cjm
     * @date  2018/6/20 
     * @return   
     */ 
    public List<JobPositionCityDO> getPositionCityBypid(int id) {
        return create.selectFrom(JobPositionCity.JOB_POSITION_CITY)
                .where(JobPositionCity.JOB_POSITION_CITY.PID.eq(id))
                .fetchInto(JobPositionCityDO.class);
    }

    public List<JobPositionCityDO> getPositionCityBypids(List<Integer> pids){
        return create.selectFrom(JobPositionCity.JOB_POSITION_CITY)
                .where(JobPositionCity.JOB_POSITION_CITY.PID.in(pids))
                .fetchInto(JobPositionCityDO.class);
    }
}
