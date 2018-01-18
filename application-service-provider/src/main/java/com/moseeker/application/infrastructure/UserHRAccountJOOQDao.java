package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.config.HRAccountActivationType;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.Result;

import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 UserHrAccountDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 16/01/2018.
 */
public class UserHRAccountJOOQDao extends com.moseeker.baseorm.db.userdb.tables.daos.UserHrAccountDao {

    public UserHRAccountJOOQDao(Configuration configuration) {
        super(configuration);
    }


    /**
     * 根据HR id 查询 已经激活的，未被禁用的HR信息。
     * 请注意：只查找id、account_type、username、company_id 四个属性，其他属性都是空
     * @param hrId
     * @return
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount fetchActiveHRByID(int hrId) {

        com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount userHrAccount = using(configuration())
                .select(UserHrAccount.USER_HR_ACCOUNT.ID, UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE,
                        UserHrAccount.USER_HR_ACCOUNT.USERNAME, UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID)
                .from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(hrId))
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(AbleFlag.OLDENABLE.getValue()))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq((byte) HRAccountActivationType.Actived.getValue()))
                .fetchOneInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount.class);

        return userHrAccount;
    }

    /**
     * 查找公司下主账号的编号
     * @param companyId 公司编号
     * @return 主账号编号
     */
    public int fetchSuperAccountIdByCompanyId(Integer companyId) {
        if (companyId != null) {
            Result<Record1<Integer>> result = using(configuration())
                    .select(UserHrAccount.USER_HR_ACCOUNT.ID)
                    .from(UserHrAccount.USER_HR_ACCOUNT)
                    .where(UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID.eq(companyId))
                    .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq((byte) HRAccountActivationType.Actived.getValue()))
                    .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(AbleFlag.ENABLE.getValue()))
                    .and(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.eq(HRAccountType.SupperAccount.getType()))
                    .limit(1)
                    .fetch();
            if (result != null && result.get(0) != null) {
                return result.get(0).value1();
            } else {
                return 0;
            }
        }
        return 0;
    }
}
