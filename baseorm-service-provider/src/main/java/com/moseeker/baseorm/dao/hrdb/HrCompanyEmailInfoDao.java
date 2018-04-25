package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import static com.moseeker.baseorm.db.hrdb.tables.HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyEmailInfoRecord;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 18/3/14.
 */
@Service
public class HrCompanyEmailInfoDao extends JooqCrudImpl<HrCompanyEmailInfo, HrCompanyEmailInfoRecord> {
    public HrCompanyEmailInfoDao() {
        super(HR_COMPANY_EMAIL_INFO, HrCompanyEmailInfo.class);
    }
    public HrCompanyEmailInfoDao(TableImpl<HrCompanyEmailInfoRecord> table, Class<HrCompanyEmailInfo> hrCompanyEmailInfoClass) {
        super(table, hrCompanyEmailInfoClass);
    }
    /*
     根据公司id获取公司邮箱剩余额度

     */
    public HrCompanyEmailInfo getHrCompanyEmailInfoListByCompanyId(int companyId){
        List<HrCompanyEmailInfo> list=create.selectFrom(HR_COMPANY_EMAIL_INFO).where(HR_COMPANY_EMAIL_INFO.COMPANY_ID.eq(companyId))
               .fetchInto(HrCompanyEmailInfo.class);
        if(list != null && list.size()>0){
            return  list.get(0);
        }
        return null;
    }

    public int  updateHrCompanyEmailInfoListByCompanyIdAndBalance(int companyId, int banlance, int oldBalance){
       int result = create.update(HR_COMPANY_EMAIL_INFO).set(HR_COMPANY_EMAIL_INFO.BALANCE,banlance)
               .where(HR_COMPANY_EMAIL_INFO.COMPANY_ID.eq(companyId))
               .and(HR_COMPANY_EMAIL_INFO.BALANCE.eq(oldBalance))
               .execute();
        return result;
    }

    public int countEmailAccounts(List<Integer> companyIdList) {
        if (companyIdList != null && companyIdList.size() > 0) {
            return create.selectCount()
                    .from(HR_COMPANY_EMAIL_INFO)
                    .where(HR_COMPANY_EMAIL_INFO.COMPANY_ID.in(companyIdList))
                    .fetchOne()
                    .value1();

        } else {
            return create.selectCount()
                    .from(HR_COMPANY_EMAIL_INFO)
                    .fetchOne()
                    .value1();
        }
    }

    public List<HrCompanyEmailInfo> fetchOrderByCreateTime(List<Integer> companyIdList, int index, int pageSize) {
        if (companyIdList != null && companyIdList.size() > 0) {
            Result<HrCompanyEmailInfoRecord> result = create.selectFrom(HR_COMPANY_EMAIL_INFO)
                    .where(HR_COMPANY_EMAIL_INFO.COMPANY_ID.in(companyIdList))
                    .orderBy(HR_COMPANY_EMAIL_INFO.CREATE_TIME.desc())
                    .limit(index, pageSize)
                    .fetch();
            if (result != null) {
                return result.into(HrCompanyEmailInfo.class);
            } else {
                return new ArrayList<>();
            }

        } else {
            Result<HrCompanyEmailInfoRecord> result = create.selectFrom(HR_COMPANY_EMAIL_INFO)
                    .orderBy(HR_COMPANY_EMAIL_INFO.CREATE_TIME.desc())
                    .limit(index, pageSize)
                    .fetch();
            if (result != null) {
                return result.into(HrCompanyEmailInfo.class);
            } else {
                return new ArrayList<>();
            }
        }
    }
}
