package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.HrNps;
import com.moseeker.db.hrdb.tables.HrRecommend;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.hrdb.tables.records.HrNpsRecord;
import com.moseeker.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.db.hrdb.tables.records.HrRecommendRecord;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.useraccounts.struct.HrNpsDO;
import com.moseeker.thrift.gen.useraccounts.struct.HrNpsResult;
import com.moseeker.thrift.gen.useraccounts.struct.HrNpsUpdate;
import com.moseeker.thrift.gen.useraccounts.struct.HrRecommendDO;
import com.moseeker.useraccounts.dao.UserHrDao;
import com.sun.org.apache.regexp.internal.RE;

/**
 * HR账号
 * <p>
 * <p>
 * Created by zzh on 16/5/31.
 */
@Repository
public class UserHrDaoImpl extends BaseDaoImpl<UserHrAccountRecord, UserHrAccount> implements UserHrDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserHrAccount.USER_HR_ACCOUNT;
    }

    @Override
    public int createHRAccount(UserHrAccountRecord userHrAccountRecord, HrCompanyRecord companyRecord)
            throws Exception {
        int result = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

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
                        Result<Record> result1 = create.select().from(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
                                .where(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD.gt(0)).fetch();
                        List<HrPointsConfRecord> list = new ArrayList<HrPointsConfRecord>();
                        HrPointsConfRecord bean = null;
                        if (result1 != null) {
                            for (Record r : result1) {
                                bean = new HrPointsConfRecord();
                                ConfigSysPointsConfTplRecord cspcr = (ConfigSysPointsConfTplRecord) r;
                                bean.setStatusName(cspcr.getStatus());
                                bean.setReward((long) cspcr.getAward());
                                bean.setDescription(cspcr.getDescription());
                                bean.setTemplateId(UInteger.valueOf(cspcr.getId()));
                                bean.setTag(String.valueOf(cspcr.getTag()));
                                bean.setCompanyId(companyRecord.getId().intValue());
                                list.add(bean);
                            }
                            create.batchInsert(list).execute();
                        }
                        result = userHrAccountRecord.getId().intValue();
                        ;
                    }
                }
            }
        } catch (Exception e) {
            conn.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return result;
    }

    @Override
    public boolean verifyCompanyName(String company_name) throws Exception {
        boolean verifyCompany = false;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<Record1<Integer>> result = create.selectCount().from(HrCompany.HR_COMPANY)
                    .join(UserHrAccount.USER_HR_ACCOUNT)
                    .on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID))
                    .where(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
                    .and(HrCompany.HR_COMPANY.NAME.equal(company_name)).fetch();
            if (result != null && result.size() > 0 && result.get(0).value1() != null && result.get(0).value1() > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            conn.rollback();
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return verifyCompany;
    }

    @Override
    public boolean verifyRepeatMobile(String mobile) throws Exception {
        boolean repeatMobile = false;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Result<Record1<Integer>> repeatAccount = create.selectCount().from(UserHrAccount.USER_HR_ACCOUNT)
                    .where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.equal(mobile)).fetch();
            if (repeatAccount != null && repeatAccount.size() > 0
                    && repeatAccount.get(0).value1() != null
                    && repeatAccount.get(0).value1() > 0) {
                repeatMobile = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return repeatMobile;
    }

    /**
     * 调研状态
     *
     * @param userId    hrID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     * @throws Exception
     */
    @Override
    public HrNpsResult npsStatus(int userId, String startDate, String endDate) throws Exception {
        //默认当前季度的起止时间
        LocalDateTime dateStart = DateUtils.getCurrentQuarterStartTime();
        LocalDateTime dateEnd = DateUtils.getCurrentQuarterEndTime();

        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            try {
                dateStart = LocalDateTime.parse(startDate);
                dateEnd = LocalDateTime.parse(endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            HrNpsRecord npsRecord = create.select().from(HrNps.HR_NPS).where(HrNps.HR_NPS.HR_ACCOUNT_ID.eq(userId))
                    .and(HrNps.HR_NPS.CREATE_TIME.between(Timestamp.valueOf(dateStart), Timestamp.valueOf(dateEnd)))
                    .orderBy(HrNps.HR_NPS.CREATE_TIME.desc())
                    .fetchAnyInto(HrNpsRecord.class);

            HrNpsDO npsDO = null;

            if (npsRecord != null) {
                npsDO = BeanUtils.DBToStruct(HrNpsDO.class, npsRecord);
            }

            HrRecommendRecord recommendRecord = create.select().from(HrRecommend.HR_RECOMMEND).where(HrRecommend.HR_RECOMMEND.HR_ACCOUNT_ID.eq(userId))
                    .and(HrRecommend.HR_RECOMMEND.CREATE_TIME.between(Timestamp.valueOf(dateStart), Timestamp.valueOf(dateEnd)))
                    .orderBy(HrRecommend.HR_RECOMMEND.CREATE_TIME.desc())
                    .fetchAnyInto(HrRecommendRecord.class);

            HrRecommendDO recommendDO = null;
            if(recommendRecord != null) {
                recommendDO = BeanUtils.DBToStruct(HrRecommendDO.class, recommendRecord);
            }

            HrNpsResult hrNpsResult = new HrNpsResult();
            hrNpsResult.setHr_nps(npsDO);
            hrNpsResult.setHr_recommend(recommendDO);
            return hrNpsResult;
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新调研状态
     *
     * @param npsUpdate
     * @return
     * @throws Exception
     */
    @Override
    public HrNpsResult npsUpdate(HrNpsUpdate npsUpdate) throws Exception {
        //默认当前季度的起止时间
        LocalDateTime dateStart = DateUtils.getCurrentQuarterStartTime();
        LocalDateTime dateEnd = DateUtils.getCurrentQuarterEndTime();
        LocalDateTime dateNow = LocalDateTime.now();
        if (!StringUtils.isEmpty(npsUpdate.getStart_date()) && !StringUtils.isEmpty(npsUpdate.getEnd_date())) {
            try {
                dateStart = LocalDateTime.parse(npsUpdate.getStart_date());
                if (dateStart.isAfter(dateNow)) {
                    throw new BIZException(-1, "开始时间不能在当前时间之后!");
                }
                dateEnd = LocalDateTime.parse(npsUpdate.getEnd_date());
                if (dateEnd.isBefore(dateNow)) {
                    throw new BIZException(-1, "结束时间不能在当前时间之前!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            conn.setAutoCommit(false);
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            HrNpsRecord npsRecord = create.select().from(HrNps.HR_NPS).where(HrNps.HR_NPS.HR_ACCOUNT_ID.eq(npsUpdate.getUser_id()))
                    .and(HrNps.HR_NPS.CREATE_TIME.between(Timestamp.valueOf(dateStart), Timestamp.valueOf(dateEnd))).fetchAnyInto(HrNpsRecord.class);

            if (npsRecord != null) { //本季度已经有调研记录

                if (npsUpdate.isSetIntention() && npsUpdate.getIntention() > -1) {
                    throw new BIZException(-1, "本季度已经填写过推荐意愿了!");
                }

                if (npsRecord.getAcceptContact() > 0 && npsUpdate.isSetAccept_contact() && npsUpdate.getAccept_contact() > 0) {
                    throw new BIZException(-1, "本季度已经参加过调研了！");
                }

                if (npsUpdate.isSetAccept_contact()) {
                    npsRecord.setAcceptContact(npsUpdate.getAccept_contact());
                    create.attach(npsRecord);
                    npsRecord.update();
                }
            } else { //添加一条新的记录

                HrNpsRecord newRecord = new HrNpsRecord();
                newRecord.setHrAccountId(npsUpdate.getUser_id());
                newRecord.setIntention(npsUpdate.getIntention());
                create.attach(newRecord);
                newRecord.insert();
            }

            if (npsUpdate.isSetUsername()) {
                //检查本季度时候已经推荐过了
                HrRecommendRecord record = create.select().from(HrRecommend.HR_RECOMMEND).where(HrRecommend.HR_RECOMMEND.HR_ACCOUNT_ID.eq(npsUpdate.getUser_id()))
                        .and(HrRecommend.HR_RECOMMEND.CREATE_TIME.between(Timestamp.valueOf(dateStart), Timestamp.valueOf(dateEnd)))
                        .orderBy(HrRecommend.HR_RECOMMEND.CREATE_TIME.desc())
                        .fetchAnyInto(HrRecommendRecord.class);
                if (record != null) {
                    //本季度已经推荐过了
                    throw new BIZException(-1, "本季度已经推荐过了!");
                }
                HrRecommendRecord recommendRecord = new HrRecommendRecord();
                recommendRecord.setHrAccountId(npsUpdate.getUser_id());
                recommendRecord.setUsername(npsUpdate.getUsername());
                recommendRecord.setMobile(npsUpdate.getMobile());
                recommendRecord.setCompany(npsUpdate.getCompany());
                create.attach(recommendRecord);
                recommendRecord.insert();
            }
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        HrNpsResult hrNpsResult = npsStatus(npsUpdate.getUser_id(), npsUpdate.getStart_date(), npsUpdate.getEnd_date());
        return hrNpsResult;
    }
}
