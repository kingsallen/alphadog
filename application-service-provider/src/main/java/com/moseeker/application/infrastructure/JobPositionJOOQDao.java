package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 JobPositionDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 17/01/2018.
 */
public class JobPositionJOOQDao extends com.moseeker.baseorm.db.jobdb.tables.daos.JobPositionDao {
    public JobPositionJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 查找发布人下的所有职位编号。
     * 本次查找不会过滤撤下和删除的职位。
     * 如果HR编号无意义，则直接返回
     * @param id HR编号
     * @return
     */
    public List<Integer> fetchIdByPublisher(Integer id) {
        if (id == null || id == 0) {
            return new ArrayList<>();
        }
        Result<Record1<Integer>> result = using(configuration())
                .select(JobPosition.JOB_POSITION.ID)
                .from(JobPosition.JOB_POSITION)
                .where(JobPosition.JOB_POSITION.PUBLISHER.eq(id))
                .fetch();
        if (result != null && result.size() > 0) {
            return result.stream().map(record -> record.value1()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 查找申请和发布人的信息
     * @param positionIdList 职位编号
     * @return 职位信息（其实是需要获取申请和发布人的关系）
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition> fetchPublisherByAppIds(List<Integer> positionIdList) {
        if (positionIdList != null && positionIdList.size() > 0) {
            Result<Record2<Integer, Integer>> result = using(configuration())
                    .select(JobPosition.JOB_POSITION.ID, JobPosition.JOB_POSITION.PUBLISHER)
                    .from(JobPosition.JOB_POSITION)
                    .where(JobPosition.JOB_POSITION.ID.in(positionIdList))
                    .fetch();
            if (result != null) {
                return result.into(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition.class);
            }
        }
        return new ArrayList<>();
    }
}
