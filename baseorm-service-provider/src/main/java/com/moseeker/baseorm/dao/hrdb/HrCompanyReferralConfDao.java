package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyReferralConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyReferralConfRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import java.sql.Timestamp;
import java.util.Date;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrCompanyReferralConfDao extends JooqCrudImpl<HrCompanyReferralConfDO, HrCompanyReferralConfRecord> {

    public HrCompanyReferralConfDao() {
        super(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF, HrCompanyReferralConfDO.class);
    }

	public HrCompanyReferralConfDao(TableImpl<HrCompanyReferralConfRecord> table, Class<HrCompanyReferralConfDO> hrCompanyReferralConfDoClass) {
		super(table, hrCompanyReferralConfDoClass);
	}
	
	/*
	获取hrcompanyConf的列表
	 */
	public int upsertHrCompanyReferralConf(HrCompanyReferralConfDO confDO){
		if(confDO == null ){
			return 0;
		}
		Timestamp time = null;
		if(confDO.getPriority() == 2){
            time = new Timestamp(new Date().getTime());
        }
		int result = create.insertInto(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF, HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.COMPANY_ID,
                HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.LINK, HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT,
                HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.PRIORITY,HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT_UPDATE_TIME)
                .values(confDO.getCompanyId(),confDO.getLink(),confDO.getText(),confDO.getPriority(), time)
                .onDuplicateKeyUpdate()
                .set(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.LINK,confDO.getLink())
                .set(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT,confDO.getText())
                .set(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.PRIORITY,confDO.getPriority())
                .set(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT_UPDATE_TIME,time)
                .execute();
		return result;
		
	}


}
