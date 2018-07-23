package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeeReferralPolicy;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeReferralPolicyRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeReferralPolicyDO;
import org.jooq.impl.TableImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 11/04/2017.
 */
@Repository
public class UserEmployeeReferralPolicyDao extends JooqCrudImpl<UserEmployeeReferralPolicyDO, UserEmployeeReferralPolicyRecord> {


    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserEmployeeReferralPolicyDao() {
        super(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY, UserEmployeeReferralPolicyDO.class);
    }

    public UserEmployeeReferralPolicyDao(TableImpl<UserEmployeeReferralPolicyRecord> table, Class<UserEmployeeReferralPolicyDO> userEmployeeReferralPolicyDOClass) {
        super(table, userEmployeeReferralPolicyDOClass);
    }

    public UserEmployeeReferralPolicyDO getEmployeeReferralPolicyDOByEmployeeId(int employeeId){
        return create.selectFrom(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY)
                .where(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.EMPLOYEE_ID.eq(employeeId))
                .fetchOneInto(UserEmployeeReferralPolicyDO.class);
    }

    public int  updateReferralPolicyByEmployeeIdAndCount(int employeeId, int count){
        int result = create.update(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY)
                .set(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.COUNT,count+1)
                .where(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.EMPLOYEE_ID.eq(employeeId))
                .and(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.COUNT.eq(count))
                .execute();
        return result;
    }

    public int upsertCompanyReferralConfCount(int employeeId){
        int result = create.insertInto(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY, UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.EMPLOYEE_ID,
                UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.COUNT)
                .values(employeeId,1)
                .onDuplicateKeyIgnore()
                .execute();
        return result;

    }

}
