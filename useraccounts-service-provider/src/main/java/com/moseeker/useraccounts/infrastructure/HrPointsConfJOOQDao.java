package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrPointsConfDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import org.jooq.Configuration;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.baseorm.db.hrdb.tables.HrPointsConf.HR_POINTS_CONF;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 HrPointsConfDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 22/01/2018.
 */
public class HrPointsConfJOOQDao extends HrPointsConfDao {

    public HrPointsConfJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据公司编号查找公司配置
     * 如果没有配置，返回空集合
     * @param companyIdList 公司编号集合
     * @return 公司配置集合
     */
    public List<HrPointsConf> fetchByCompanyIdList(List<Integer> companyIdList) {

        Result<HrPointsConfRecord> result =  using(configuration())
                .selectFrom(HR_POINTS_CONF)
                .where(HR_POINTS_CONF.COMPANY_ID.in(companyIdList))
                .and(HR_POINTS_CONF.REWARD.gt((long) 0))
                .fetch();
        if (result != null && result.size() > 0) {
            return result.map(mapper());
        }
        return new ArrayList<>();
    }
}
