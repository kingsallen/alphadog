package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import java.util.ArrayList;
import org.apache.thrift.TException;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * HR帐号数据库持久类
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 9, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Repository
public class UserHrAccountDao extends JooqCrudImpl<UserHrAccountDO, UserHrAccountRecord> {

    public UserHrAccountDao() {
        super(UserHrAccount.USER_HR_ACCOUNT, UserHrAccountDO.class);
    }

    public UserHrAccountDao(TableImpl<UserHrAccountRecord> table, Class<UserHrAccountDO> userHrAccountDOClass) {
        super(table, userHrAccountDOClass);
    }

    public List<UserHrAccountDO> listHRFromCompany(int comanyId) throws TException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", String.valueOf(comanyId));
        return this.getDatas(qu.buildQuery());
    }

    public int deleteUserHrAccount(int id) {
        UserHrAccountRecord record = new UserHrAccountRecord();
        record.setId(id);
        try {
            return this.deleteRecord(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int createHRAccount(UserHrAccountRecord userHrAccountRecord, HrCompanyRecord companyRecord)
            throws Exception {
        int result = 0;

        Result<Record1<Integer>> repeatAccount = create.selectCount().from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.equal(userHrAccountRecord.getMobile())).fetch();
        if (repeatAccount != null && repeatAccount.size() > 0
                && repeatAccount.get(0).value1() != null
                && repeatAccount.get(0).value1() > 0) {
        } else {
            create.attach(userHrAccountRecord);
            int insertResult = userHrAccountRecord.insert();
            if (insertResult > 0) {
                Result<Record1<Integer>> verifyCompanyNameResult = create.selectCount().from(HrCompany.HR_COMPANY)
                        .join(UserHrAccount.USER_HR_ACCOUNT)
                        .on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID))
                        .where(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
                        .and(HrCompany.HR_COMPANY.NAME.equal(companyRecord.getName())).fetch();
                if (verifyCompanyNameResult != null && verifyCompanyNameResult.size() > 0
                        && verifyCompanyNameResult.get(0).value1() != null
                        && verifyCompanyNameResult.get(0).value1() > 0) {
                } else {
                    create.attach(companyRecord);
                    companyRecord.setHraccountId(userHrAccountRecord.getId());
                    companyRecord.insert();
                    userHrAccountRecord.setCompanyId(companyRecord.getId().intValue());
                    userHrAccountRecord.update();
                    Result<Record> result1=create.select().from(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
                            .where(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD.gt(0)).fetch();
                    List<HrPointsConfRecord> list=new ArrayList<HrPointsConfRecord>();
                    HrPointsConfRecord bean=null;
                    if(result1!=null){
                        for(Record r : result1){
                            bean=new HrPointsConfRecord();
                            ConfigSysPointsConfTplRecord cspcr=(ConfigSysPointsConfTplRecord) r;
                            bean.setStatusName(cspcr.getStatus());
                            bean.setReward((long)cspcr.getAward());
                            bean.setDescription(cspcr.getDescription());
                            bean.setTemplateId((int)(cspcr.getId()));
                            bean.setTag(String.valueOf(cspcr.getTag()));
                            bean.setCompanyId(companyRecord.getId().intValue());
                            list.add(bean);
                        }
                        create.batchInsert(list).execute();
                    }
                    result = userHrAccountRecord.getId().intValue();;
                }
            }
        }
        return result;
    }

    public boolean verifyCompanyName(String company_name) throws Exception {
        boolean verifyCompany = false;
        Result<Record1<Integer>> result = create.selectCount().from(HrCompany.HR_COMPANY)
                .join(UserHrAccount.USER_HR_ACCOUNT)
                .on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID))
                .where(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
                .and(HrCompany.HR_COMPANY.NAME.equal(company_name)).fetch();
        if (result != null && result.size() > 0 && result.get(0).value1() != null && result.get(0).value1() > 0) {
            verifyCompany = true;
        }
        return verifyCompany;
    }

    public boolean verifyRepeatMobile(String mobile) throws Exception {
        boolean repeatMobile = false;
        Result<Record1<Integer>> repeatAccount = create.selectCount().from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.equal(mobile)).fetch();
        if (repeatAccount != null && repeatAccount.size() > 0
                && repeatAccount.get(0).value1() != null
                && repeatAccount.get(0).value1() > 0) {
            repeatMobile = true;
        }
        return repeatMobile;
    }
}
