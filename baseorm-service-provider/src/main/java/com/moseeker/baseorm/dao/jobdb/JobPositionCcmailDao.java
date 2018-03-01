package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCcmail;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCcmailRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxx
 *         JobPositionCityDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class JobPositionCcmailDao extends JooqCrudImpl<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionCcmail, JobPositionCcmailRecord> {

    @Autowired
    DictCityDao dictCityDao;

    public JobPositionCcmailDao() {
        super(JobPositionCcmail.JOB_POSITION_CCMAIL, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionCcmail.class);
    }

    public JobPositionCcmailDao(TableImpl<JobPositionCcmailRecord> table, Class<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionCcmail> jobPositionCcmailDOClass) {
        super(table, jobPositionCcmailDOClass);
    }

    public int deleteByPositionId(int positionId){
        Condition condition=new Condition(JobPositionCcmail.JOB_POSITION_CCMAIL.POSITION_ID.getName(),positionId);

        return delete(condition);
    }

    public int deleteByPositionIds(List<Integer> positionId){

        Condition condition=new Condition(JobPositionCcmail.JOB_POSITION_CCMAIL.POSITION_ID.getName(),positionId, ValueOp.IN);

        return delete(condition);
    }
}
