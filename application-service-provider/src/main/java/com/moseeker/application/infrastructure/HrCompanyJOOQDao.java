package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrCompanyDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import org.jooq.Configuration;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.baseorm.db.hrdb.tables.HrCompany.HR_COMPANY;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 HrCompanyDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 24/01/2018.
 */
public class HrCompanyJOOQDao extends HrCompanyDao {

    public HrCompanyJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据公司编号查找公司
     * @param companyIdList
     * @return 公司信息集合
     */
    public List<HrCompany> fetchByIdList(List<Integer> companyIdList) {
        if (companyIdList != null && companyIdList.size() > 0) {
            Result<HrCompanyRecord> result = using(configuration())
                    .selectFrom(HR_COMPANY)
                    .where(HR_COMPANY.ID.in(companyIdList))
                    .fetch();
            if (result != null && result.size() > 0) {
                return result.map(mapper());
            }
        }
        return new ArrayList<>();
    }
}
