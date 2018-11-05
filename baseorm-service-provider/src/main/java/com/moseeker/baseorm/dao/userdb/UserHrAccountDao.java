package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.config.HRAccountActivationType;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrNps;
import com.moseeker.baseorm.db.hrdb.tables.HrNpsRecommend;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrNpsRecommendRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrNpsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HR帐号数据库持久类
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 9, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
@Repository
public class UserHrAccountDao extends JooqCrudImpl<UserHrAccountDO, UserHrAccountRecord> {

    public UserHrAccountDao() {
        super(UserHrAccount.USER_HR_ACCOUNT, UserHrAccountDO.class);
    }

    public UserHrAccountDao(TableImpl<UserHrAccountRecord> table, Class<UserHrAccountDO> userHrAccountDOClass) {
        super(table, userHrAccountDOClass);
    }

    public UserHrAccountDO getValidAccount(int id) {
        Query query = new Query.QueryBuilder()
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), id)
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.getName(), 1)
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.getName(), 1).buildQuery();
        return getData(query);
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
                            bean.setTemplateId((int) (cspcr.getId()));
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


    /**
     * 调研统计列表
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param pageSize
     * @return
     * @throws BIZException
     * @throws TException
     */
    public HrNpsStatistic npsList(String startDate, String endDate, int page, int pageSize) throws Exception {
        //默认当前季度的起止时间
        LocalDateTime dateStart = DateUtils.getCurrentTwoMonthStartTime();
        LocalDateTime dateEnd = DateUtils.getCurrentTwoMonthEndTime();

        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            try {
                dateStart = LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                dateEnd = LocalDateTime.parse(endDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                throw new BIZException(-1, "日期格式错误,正确的格式：yyyy-MM-dd");
            }
        }

        int total;

        if (page <= 0) {
            page = 1;
        }

        if (pageSize <= 0) {
            pageSize = 500;
        }

        total = create.selectCount().from(HrNps.HR_NPS)
                .where(HrNps.HR_NPS.CREATE_TIME.ge(Timestamp.valueOf(dateStart)))
                .and(HrNps.HR_NPS.CREATE_TIME.le(Timestamp.valueOf(dateEnd)))
                .fetchOneInto(Integer.class);

        List<HrNpsRecord> npsRecords = create.select().
                from(HrNps.HR_NPS)
                .where(HrNps.HR_NPS.CREATE_TIME.ge(Timestamp.valueOf(dateStart)))
                .and(HrNps.HR_NPS.CREATE_TIME.le(Timestamp.valueOf(dateEnd)))
                .orderBy(HrNps.HR_NPS.CREATE_TIME.desc())
                .offset((page - 1) * pageSize)
                .limit(pageSize)
                .fetchInto(HrNpsRecord.class);

        Set<Integer> hrIds = new HashSet<>();
        Set<Integer> npsIds = new HashSet<>();
        Set<Integer> companyIds = new HashSet<>();

        Map<Integer, HrNpsRecommendRecord> hrNpsRecommendRecordMap = new HashMap<>();
        Map<Integer, UserHrAccountRecord> hrAccountRecordMap = new HashMap<>();
        Map<Integer, HrCompanyRecord> companyRecordMap = new HashMap<>();

        if (npsRecords.size() > 0) {
            for (HrNpsRecord record : npsRecords) {
                npsIds.add(record.getId());
                hrIds.add(record.getHrAccountId());
            }

            List<HrNpsRecommendRecord> npsRecommendRecords = create.select()
                    .from(HrNpsRecommend.HR_NPS_RECOMMEND)
                    .where(HrNpsRecommend.HR_NPS_RECOMMEND.HR_NPS_ID.in(npsIds))
                    .fetchInto(HrNpsRecommendRecord.class);


            for (HrNpsRecommendRecord record : npsRecommendRecords) {
                hrNpsRecommendRecordMap.put(record.getHrNpsId(), record);
            }

            if (hrIds.size() > 0) {
                List<UserHrAccountRecord> hrAccountRecords = create.select()
                        .from(UserHrAccount.USER_HR_ACCOUNT)
                        .where(UserHrAccount.USER_HR_ACCOUNT.ID.in(hrIds))
                        .fetchInto(UserHrAccountRecord.class);

                for (UserHrAccountRecord record : hrAccountRecords) {
                    hrAccountRecordMap.put(record.getId(), record);
                }

                for (UserHrAccountRecord record : hrAccountRecords) {
                    companyIds.add(record.getCompanyId());
                }

                List<HrCompanyRecord> companyRecords = create.select()
                        .from(HrCompany.HR_COMPANY)
                        .where(HrCompany.HR_COMPANY.ID.in(companyIds))
                        .fetchInto(HrCompanyRecord.class);

                for (HrCompanyRecord record : companyRecords) {
                    companyRecordMap.put(record.getId().intValue(), record);
                }

            }
        }

        List<HrNpsInfo> hrNpsInfos = new ArrayList<>();
        HrNpsInfo info;
        for (HrNpsRecord record : npsRecords) {
            info = new HrNpsInfo();
            info.setId(record.getId());
            info.setDate(record.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            info.setIntention(record.getIntention());
            info.setAccept_contact(record.getAcceptContact());
            HrNpsRecommendRecord recommendRecord = hrNpsRecommendRecordMap.get(record.getId());

            if (recommendRecord != null) {
                info.setRecommend_user(recommendRecord.getUsername());
                info.setRecommend_mobile(recommendRecord.getMobile());
                info.setRecommend_company(recommendRecord.getCompany());
            }

            info.setHr_account_id(record.getHrAccountId());
            UserHrAccountRecord hrAccountRecord = hrAccountRecordMap.get(record.getHrAccountId());
            if (hrAccountRecord != null) {
                info.setHr_mobile(hrAccountRecord.getMobile());
                info.setHr_account_type(hrAccountRecord.getAccountType().byteValue());
                HrCompanyRecord companyRecord = companyRecordMap.get(hrAccountRecord.getCompanyId());
                if (companyRecord != null) {
                    info.setCompany(companyRecord.getName());
                    info.setCompany_id(companyRecord.getId().intValue());
                    info.setCompany_type(companyRecord.getType().intValue());
                }
            }

            hrNpsInfos.add(info);
        }

        HrNpsStatistic statistic = new HrNpsStatistic();
        statistic.setTotal(total);
        statistic.setPage(page);
        statistic.setPage_size(pageSize);
        statistic.setData(hrNpsInfos);
        return statistic;
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
    public HrNpsResult npsStatus(int userId, String startDate, String endDate) throws Exception {
        //默认当前季度的起止时间
        LocalDateTime dateStart = DateUtils.getCurrentTwoMonthStartTime();
        LocalDateTime dateEnd = DateUtils.getCurrentTwoMonthEndTime();

        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            try {
                dateStart = LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                dateEnd = LocalDateTime.parse(endDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                throw new BIZException(-1, "日期格式错误,正确的格式：yyyy-MM-dd");
            }
        }

        Record userHrAccount = create.select()
                .from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(userId))
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(1))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq(Byte.valueOf("1")))
                .fetchAny();
        if (userHrAccount == null) {
            throw new BIZException(-1, "hr账号不存在");
        }
        HrNpsRecord npsRecord = create.select().from(HrNps.HR_NPS).where(HrNps.HR_NPS.HR_ACCOUNT_ID.eq(userId))
                .and(HrNps.HR_NPS.CREATE_TIME.ge(Timestamp.valueOf(dateStart)))
                .and(HrNps.HR_NPS.CREATE_TIME.le(Timestamp.valueOf(dateEnd)))
                .orderBy(HrNps.HR_NPS.CREATE_TIME.desc())
                .fetchAnyInto(HrNpsRecord.class);

        HrNpsDO npsDO = null;
        HrNpsRecommendDO recommendDO = null;

        if (npsRecord != null) {
            npsDO = BeanUtils.DBToStruct(HrNpsDO.class, npsRecord);

            HrNpsRecommendRecord recommendRecord = create.select().from(HrNpsRecommend.HR_NPS_RECOMMEND).where(HrNpsRecommend.HR_NPS_RECOMMEND.HR_NPS_ID.eq(npsRecord.getId()))
                    .fetchAnyInto(HrNpsRecommendRecord.class);

            if (recommendRecord != null) {
                recommendDO = BeanUtils.DBToStruct(HrNpsRecommendDO.class, recommendRecord);
            }
        }

        HrNpsResult hrNpsResult = new HrNpsResult();
        hrNpsResult.setHr_nps(npsDO);
        hrNpsResult.setHr_nps_recommend(recommendDO);
        return hrNpsResult;
    }

    /**
     * 更新调研状态
     *
     * @param npsUpdate
     * @return
     * @throws Exception
     */
    @Transactional
    public HrNpsResult npsUpdate(HrNpsUpdate npsUpdate) throws Exception {
        //默认当前季度的起止时间
        LocalDateTime dateStart = DateUtils.getCurrentTwoMonthStartTime();
        LocalDateTime dateEnd = DateUtils.getCurrentTwoMonthEndTime();
        LocalDateTime dateNow = LocalDateTime.now();
        if (!StringUtils.isEmpty(npsUpdate.getStart_date()) && !StringUtils.isEmpty(npsUpdate.getEnd_date())) {
            try {
                try {
                    dateStart = LocalDateTime.parse(npsUpdate.getStart_date() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    if (dateStart.isAfter(dateNow)) {
                        throw new BIZException(-1, "开始时间不能在当前时间之后!");
                    }
                    dateEnd = LocalDateTime.parse(npsUpdate.getEnd_date() + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    if (dateEnd.isBefore(dateNow)) {
                        throw new BIZException(-1, "结束时间不能在当前时间之前!");
                    }
                } catch (Exception e) {
                    throw new BIZException(-1, "日期格式错误,正确的格式：yyyy-MM-dd");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HrNpsRecord npsRecord = create.select().from(HrNps.HR_NPS).where(HrNps.HR_NPS.HR_ACCOUNT_ID.eq(npsUpdate.getUser_id()))
                .and(HrNps.HR_NPS.CREATE_TIME.ge(Timestamp.valueOf(dateStart)))
                .and(HrNps.HR_NPS.CREATE_TIME.le(Timestamp.valueOf(dateEnd)))
                .fetchAnyInto(HrNpsRecord.class);

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

            npsRecord = new HrNpsRecord();
            npsRecord.setHrAccountId(npsUpdate.getUser_id());
            npsRecord.setIntention(npsUpdate.getIntention());
            create.attach(npsRecord);
            npsRecord.insert();
        }

        if (npsUpdate.isSetUsername()) {
            //检查本季度时候已经推荐过了
            HrNpsRecommendRecord record = create.select().from(HrNpsRecommend.HR_NPS_RECOMMEND)
                    .where(HrNpsRecommend.HR_NPS_RECOMMEND.HR_NPS_ID.eq(npsRecord.getId()))
                    .orderBy(HrNpsRecommend.HR_NPS_RECOMMEND.CREATE_TIME.desc())
                    .fetchAnyInto(HrNpsRecommendRecord.class);
            if (record != null) {
                //本季度已经推荐过了
                throw new BIZException(-1, "本季度已经推荐过了!");
            }
            HrNpsRecommendRecord recommendRecord = new HrNpsRecommendRecord();
            recommendRecord.setHrNpsId(npsRecord.getId());
            recommendRecord.setUsername(npsUpdate.getUsername());
            recommendRecord.setMobile(npsUpdate.getMobile());
            recommendRecord.setCompany(npsUpdate.getCompany());
            create.attach(recommendRecord);
            recommendRecord.insert();
            npsRecord.setUpdateTime(recommendRecord.getCreateTime());
            npsRecord.update();
        }

        HrNpsResult hrNpsResult = npsStatus(npsUpdate.getUser_id(), npsUpdate.getStart_date(), npsUpdate.getEnd_date());
        return hrNpsResult;
    }

    /**
     * 采用乐观锁方式修改手机号码(TODO)
     * 如果不存在任何可用的HR的手机号码和新手机号码相同，那么根据HR编号和老手机号码修改HR的手机号码。
     * @param hr HR账号信息
     * @param mobile 手机号码
     * @return 执行的条数
     */
    public int updateMobile(UserHrAccountDO hr, String mobile) {
        int result = create.update(UserHrAccount.USER_HR_ACCOUNT)
                .set(UserHrAccount.USER_HR_ACCOUNT.MOBILE, mobile)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(hr.getId()))
                .and(UserHrAccount.USER_HR_ACCOUNT.MOBILE.eq(hr.getMobile()))
                .execute();

        return result;
    }

    /**
     * 如果账号不存在则添加，否则不做添加
     * @param userHrAccountRecord 账号信息
     * @return 账号编号。如果未能够正常添加，则返回0。
     */
    public int addIfNotExist(UserHrAccountRecord userHrAccountRecord) {
        int id = 0;

        List<Field<?>> changedFieldList
                = Arrays.stream(userHrAccountRecord.fields())
                .filter(f -> userHrAccountRecord.changed(f)).collect(Collectors.toList());
        if (changedFieldList!= null && changedFieldList.size() > 0) {
            StringBuffer sb = new StringBuffer(200);
            sb.append("insert into userdb.user_hr_account ");
            sb.append(changedFieldList.stream().map(m -> m.getName()).collect(Collectors.joining(",", "(", ")")));
            sb.append(" select ");
            sb.append(Stream.generate(() -> "?").limit(changedFieldList.size()).collect(Collectors.joining(",")));
            sb.append(" from dual where not exists ( ");
            sb.append(" select id from userdb.user_hr_account where mobile = ? and disable = 1 limit 1");
            sb.append(")");

            List<Object> params = changedFieldList.stream()
                    .map(m -> userHrAccountRecord.getValue(m))
                    .collect(Collectors.toList());
            params.add(userHrAccountRecord.getMobile());

            logger.info("addIfNotExist userHRAccount sql: {}", sb.toString());
            int result = create.execute(sb.toString(), params.toArray());
            if (result > 0) {
                Record1<Integer> recordResult = create.select(UserHrAccount.USER_HR_ACCOUNT.ID)
                        .from(UserHrAccount.USER_HR_ACCOUNT)
                        .where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.eq(userHrAccountRecord.getMobile()))
                        .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(1))
                        .limit(1)
                        .fetchOne();
                if (recordResult != null && recordResult.value1() != null) {
                    id = recordResult.value1();
                }
            }
        }

        return id;
    }

    /*
    通过id获取user_hr_account
    */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount getHrAccount(int accountId){
        List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount> accounts=
        create.selectFrom(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.equal(accountId))
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(1))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq((byte)1))
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount.class);
        if(com.moseeker.common.util.StringUtils.isEmptyList(accounts)){
            return null;
        }
        return accounts.get(0);
    }

    public UserHrAccountRecord fetchSuperHR(int company_id) {
        UserHrAccountRecord record = create.selectFrom(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID.eq(company_id))
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.eq(AbleFlag.ENABLE.getValue()))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.eq((byte) HRAccountActivationType.Actived.getValue()))
                .orderBy(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.asc())
                .limit(1)
                .fetchOne();
        return record;

    }

    public int fetchSuperHRByHrId(int id) {
        Record1<Integer> companyIdRecord = create
                .select(UserHrAccount.USER_HR_ACCOUNT.COMPANY_ID)
                .from(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(id))
                .fetchOne();
        if (companyIdRecord != null) {
            UserHrAccountRecord record = fetchSuperHR(companyIdRecord.value1());
            if (record != null) {
                return record.getId();
            }
        }
        return 0;
    }

    /**
     * CAS设置是否托管给智能招聘助手
     * @param accountId
     * @param expect
     * @param leaveToMobot
     * @return
     */
    public int switchChatLeaveToMobot(int accountId, byte expect, byte leaveToMobot){
        return create.update(UserHrAccount.USER_HR_ACCOUNT)
                .set(UserHrAccount.USER_HR_ACCOUNT.LEAVE_TO_MOBOT,leaveToMobot)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(accountId))
                .and(UserHrAccount.USER_HR_ACCOUNT.LEAVE_TO_MOBOT.eq(expect))
                .execute();
    }


    public UserHrAccountDO getUserHrAccountById(int id){
        return create.selectFrom(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.eq(id))
                .fetchOneInto(UserHrAccountDO.class);
    }

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount> getAccountByIdList(List<Integer> hrIdList){
        return create.selectFrom(UserHrAccount.USER_HR_ACCOUNT)
                .where(UserHrAccount.USER_HR_ACCOUNT.ID.in(hrIdList))
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount.class);
    }

}
