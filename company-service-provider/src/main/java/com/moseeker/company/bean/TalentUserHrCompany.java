package com.moseeker.company.bean;

/**
 * Created by zztaiwll on 18/10/30.
 */
public class TalentUserHrCompany {
    private int userId;
    private int hrId;
    private int companyId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public TalentUserHrCompany(){}
    public TalentUserHrCompany(int userId, int hrId, int companyId) {
        this.userId = userId;
        this.hrId = hrId;
        this.companyId = companyId;
    }
}
