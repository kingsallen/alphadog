package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import org.springframework.stereotype.Repository;

/**
 * HR帐号数据库持久类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 9, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 */
@Repository
public class HRThirdPartyAccountHrDao extends JooqCrudImpl<HrThirdPartyAccountHrDO, HrThirdPartyAccountHrRecord> {

    public HRThirdPartyAccountHrDao() {
        super(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR, HrThirdPartyAccountHrDO.class);
    }

}
