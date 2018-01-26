package com.moseeker.position.pojo;

import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;

/**
 * Created by zztaiwll on 18/1/24.
 */
public class CompanyAccount {
    private HrCompany hrCompany;
    private UserHrAccount userHrAccount;

    public HrCompany getHrCompany() {
        return hrCompany;
    }

    public void setHrCompany(HrCompany hrCompany) {
        this.hrCompany = hrCompany;
    }

    public UserHrAccount getUserHrAccount() {
        return userHrAccount;
    }

    public void setUserHrAccount(UserHrAccount userHrAccount) {
        this.userHrAccount = userHrAccount;
    }

}
