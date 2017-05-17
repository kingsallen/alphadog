package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupationRel;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRelRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationRelDO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobOccupationRelDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobOccupationRelDao extends JooqCrudImpl<JobOccupationRelDO, JobOccupationRelRecord> {

    public JobOccupationRelDao() {
        super(JobOccupationRel.JOB_OCCUPATION_REL, JobOccupationRelDO.class);
    }

    public JobOccupationRelDao(TableImpl<JobOccupationRelRecord> table, Class<JobOccupationRelDO> jobOccupationRelDOClass) {
        super(table, jobOccupationRelDOClass);
    }
    /*
     * 按照id删除JOB_OCCUPATION_REL
     */
    public void delJobOccupationRelByPids(List<Integer> pids) {
        try {
            if (pids != null && pids.size() > 0) {
                Condition condition = JobOccupationRel.JOB_OCCUPATION_REL.PID.in(pids);
                create.deleteFrom(JobOccupationRel.JOB_OCCUPATION_REL).where(condition).execute();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
    }
}
