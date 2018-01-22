package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.jobdb.tables.daos.JobPositionDao;
import com.moseeker.useraccounts.context.constant.PositionStatus;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.Set;

import static com.moseeker.baseorm.db.jobdb.tables.JobPosition.JOB_POSITION;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 JobPositionDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 22/01/2018.
 */
public class JobPositionJOOQDao extends JobPositionDao {

    public JobPositionJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据职位编号查找职位与公司的关系
     * @param positionIdList 职位编号集合
     * @return 职位与公司的关系
     */
    public Result<Record2<Integer,Integer>> fetchPositionIdAndCompanyIdByPositionIdList(Set<Integer> positionIdList) {

        return using(configuration())
                .select(JOB_POSITION.ID, JOB_POSITION.COMPANY_ID)
                .from(JOB_POSITION)
                .where(JOB_POSITION.ID.in(positionIdList))
                .and(JOB_POSITION.STATUS.notEqual((byte) PositionStatus.DELETED.getValue()))
                .fetch();
    }
}
