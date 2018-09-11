package com.moseeker.application.infrastructure;


import static com.moseeker.baseorm.db.hrdb.tables.HrCompany.HR_COMPANY;
import static com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount.HR_COMPANY_ACCOUNT;
import com.moseeker.baseorm.db.hrdb.tables.daos.HrCompanyAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.daos.HrCompanyDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Result;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 HrCompanyDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 24/01/2018.
 */
public class HrCompanyAccountJOOQDao extends HrCompanyAccountDao {

    public HrCompanyAccountJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据Hr编号查找公司
     * @param accountIdList
     * @return 公司信息集合
     */
    public List<HrCompanyAccountDO> fetchByIdList(List<Integer> accountIdList) {
        if (accountIdList != null && accountIdList.size() > 0) {
            List<HrCompanyAccountDO> result = using(configuration())
                    .selectFrom(HR_COMPANY_ACCOUNT)
                    .where(HR_COMPANY_ACCOUNT.ACCOUNT_ID.in(accountIdList))
                    .fetchInto(HrCompanyAccountDO.class);
            if (result != null && result.size() > 0) {
                return result;
            }
        }
        return new ArrayList<>();
    }
}
