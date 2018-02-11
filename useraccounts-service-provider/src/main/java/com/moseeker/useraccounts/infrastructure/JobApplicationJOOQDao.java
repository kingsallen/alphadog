package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.jobdb.tables.daos.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import org.jooq.Configuration;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 JobApplicationDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 22/01/2018.
 */
public class JobApplicationJOOQDao extends JobApplicationDao {

    public JobApplicationJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据申请编号查找有推荐的申请记录
     * @param applicationIdList 申请编号
     * @return 有推荐记录的申请记录集合
     */
    public List<JobApplication> fetchByIdList(List<Integer> applicationIdList) {

        List<JobApplication> applicationList = null;

        if (applicationIdList != null && applicationIdList.size() > 0) {
            Result<JobApplicationRecord> result = using(configuration())
                    .selectFrom(JOB_APPLICATION)
                    .where(JOB_APPLICATION.ID.in(applicationIdList))
                    .and(JOB_APPLICATION.RECOMMENDER_USER_ID.gt(0))
                    .fetch();
            if (result != null && result.size() > 0) {
                return result.map(mapper());
            }
        }

        return new ArrayList<>();
    }
}
