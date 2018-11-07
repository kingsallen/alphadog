package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.baseorm.db.userdb.tables.UserPositionEmail;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* JobPositionExtDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionExtDao extends JooqCrudImpl<JobPositionExtDO, JobPositionExtRecord> {

    public JobPositionExtDao() {
        super(JobPositionExt.JOB_POSITION_EXT, JobPositionExtDO.class);
    }

    public JobPositionExtDao(TableImpl<JobPositionExtRecord> table, Class<JobPositionExtDO> jobPositionExtDOClass) {
        super(table, jobPositionExtDOClass);
    }
    //如果有更新，没有则插入
    public int insertOrUpdateData(int pid,int jobId){
        int result=create.insertInto(JobPositionExt.JOB_POSITION_EXT, JobPositionExt.JOB_POSITION_EXT.PID, JobPositionExt.JOB_POSITION_EXT.ALIPAY_JOB_ID)
                .values(pid, jobId)
                .onDuplicateKeyUpdate()
                .set(JobPositionExt.JOB_POSITION_EXT.PID, pid)
                .set( JobPositionExt.JOB_POSITION_EXT.ALIPAY_JOB_ID,jobId)
                .execute();
        return result;
    }

    public List<JobPositionExtDO> getDatasByPids(List<Integer> pids){
        return create.selectFrom(JobPositionExt.JOB_POSITION_EXT)
                .where(JobPositionExt.JOB_POSITION_EXT.PID.in(pids))
                .fetchInto(JobPositionExtDO.class);
    }
}
