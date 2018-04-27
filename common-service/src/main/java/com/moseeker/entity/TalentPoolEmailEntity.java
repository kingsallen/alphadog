package com.moseeker.entity;

import com.moseeker.baseorm.constant.TalentPoolStatus;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyEmailInfoDao;
import com.moseeker.baseorm.dao.logdb.LogTalentpoolEmailDailyLogDao;
import com.moseeker.baseorm.dao.logdb.LogTalentpoolEmailLogDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailDailyLogRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.entity.exception.TalentPoolException;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumption;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountInfo;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentPoolEmailEntity {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HrCompanyEmailInfoDao hrCompanyEmailInfoDao;
    @Autowired
    private ConfigSysTemplateMessageLibraryDao configSysTemplateMessageLibraryDao;
    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;
    @Autowired
    private HrCompanyEmailInfoDao companyEmailInfoDao;
    @Autowired
    private LogTalentpoolEmailLogDao emailLogDao;
    @Autowired
    private LogTalentpoolEmailDailyLogDao logTalentpoolEmailDailyLogDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyConfDao companyConfDao;

    public HrCompanyEmailInfo getHrCompanyEmailInfoByCompanyId(int company_id){
        HrCompanyEmailInfo info = hrCompanyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(company_id);
        return  info;
    }

    public List<ConfigSysTemplateMessageLibraryDO> getConfigSysTemplateMessageLibraryDOByIdList(List<Integer> idList){
        return  configSysTemplateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(idList, 0);
    }

    public ConfigSysTemplateMessageLibraryDO getConfigSysTemplateMessageLibraryDOById(Integer id){
        Query query = new Query.QueryBuilder().where(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID.getName(), id)
                .buildQuery();
        return  configSysTemplateMessageLibraryDao.getData(query);
    }

    public List<TalentpoolEmail> getTalentpoolEmailByCompanyId(Integer company_id){
        return talentpoolEmailDao.getTalentpoolEmailByCompanyId(company_id);
    }

    public List<TalentpoolEmail> getTalentpoolEmailByCompanyIdAndConfigId(Integer company_id, int config_id){
        return talentpoolEmailDao.getTalentpoolEmailByCompanyIdAndConfigId(company_id, config_id);
    }

    public int updateEmailInfo( int company_id, int type, int disable, String context, String inscribe){
        Query query = new Query.QueryBuilder().where("company_id", company_id)
                .and("config_id", type).buildQuery();
        TalentpoolEmailRecord emailRecord = talentpoolEmailDao.getRecord(query);
        if(disable>=0){
            emailRecord.setDisable(disable);
        }
        if(StringUtils.isNotNullOrEmpty(context)){
            emailRecord.setContext(context);
        }
        if(StringUtils.isNotNullOrEmpty(inscribe)){
            emailRecord.setInscribe(inscribe);
        }
        int result = talentpoolEmailDao.updateRecord(emailRecord);
        return result;
    }

    public int handerTalentpoolEmailLogAndBalance(int useCount, int type, int company_id, int hr_id) throws TalentPoolException {
        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(company_id);
        if (companyConfDO == null || companyConfDO.getTalentpoolStatus() != TalentPoolStatus.HighLevel.getValue()) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_NO_PERMISSION;
        }
        HrCompanyEmailInfo companyEmailInfo = companyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(company_id);
        int id;
        switch (type) {
            case 0:  id = recharge(useCount, company_id, companyEmailInfo.getBalance(), 0); break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:  id = consumption(useCount, type, company_id, hr_id, companyEmailInfo.getBalance(), 0); break;
            default: id = consumption(useCount, type, company_id, hr_id, companyEmailInfo.getBalance(), 0);
        }

        return id;
    }

    private int consumption(int useCount, int type, int company_id, int hr_id, int balance, int index) throws TalentPoolException {
        if (index >= Constant.RETRY_UPPER_LIMIT) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_BALANCE_UPDATE_FIALED;
        }
        index ++;
        if (balance < useCount) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_OVER_BALANCE;
        }

        int count = companyEmailInfoDao.updateHrCompanyEmailInfoListByCompanyIdAndBalance(company_id, balance - useCount, balance);
        if (count == 0) {
            HrCompanyEmailInfo companyEmailInfo = companyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(company_id);
            consumption(useCount, type, company_id, hr_id, companyEmailInfo.getBalance(), index);
        }

        long timeAtStartOfDay = new DateTime().withTimeAtStartOfDay().getMillis();
        logTalentpoolEmailDailyLogDao.upsertDailyLog(timeAtStartOfDay, company_id, useCount, EmailAccountConsumptionType.COMSUMPTION.getValue(), 0);

        LogTalentpoolEmailLogRecord record = new LogTalentpoolEmailLogRecord();
        record.setCompanyId(company_id);
        record.setType(type);
        record.setLost(useCount);
        record.setBalance(balance - useCount);
        record.setHrId(hr_id);
        return emailLogDao.addRecord(record).getId();
    }

    private int recharge(int useCount, int companyId, int balance, int index) throws TalentPoolException {
        if (index >= Constant.RETRY_UPPER_LIMIT) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_BALANCE_UPDATE_FIALED;
        }
        index ++;

        int count = companyEmailInfoDao.updateHrCompanyEmailInfoListByCompanyIdAndBalance(companyId, balance+useCount, balance);
        if (count == 0) {
            HrCompanyEmailInfo companyEmailInfo = companyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(companyId);
            recharge(useCount, companyId, companyEmailInfo.getBalance(), index);
        }

        long timeAtStartOfDay = new DateTime().withTimeAtStartOfDay().getMillis();
        logTalentpoolEmailDailyLogDao.upsertDailyLog(timeAtStartOfDay, companyId, useCount, EmailAccountConsumptionType.RECHARRGE.getValue(), 0);

        LogTalentpoolEmailLogRecord record = new LogTalentpoolEmailLogRecord();
        record.setCompanyId(companyId);
        record.setType(0);
        record.setLost(useCount);
        record.setBalance(balance + useCount);
        record.setHrId(0);
        return emailLogDao.addRecord(record).getId();
    }

    public void updateEmailAccountRecharge(int id, int lost) throws CommonException {
        LogTalentpoolEmailLogRecord record = emailLogDao.getById(id);
        if (record == null || record.getType().byteValue() != EmailAccountConsumptionType.RECHARRGE.getValue()) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_RECHARGE_NOT_EXIST;
        }

        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(record.getCompanyId());
        if (companyConfDO == null || companyConfDO.getTalentpoolStatus() != TalentPoolStatus.HighLevel.getValue()) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_NO_PERMISSION;
        }

        int balance = lost - record.getLost();
        if (balance == 0) {
            return;
        }

        DateTime dateTime = new DateTime(record.getCreateTime());
        long timeAtStartOfDay = dateTime.withTimeAtStartOfDay().getMillis();
        logTalentpoolEmailDailyLogDao.upsertDailyLog(timeAtStartOfDay, record.getCompanyId(), balance, EmailAccountConsumptionType.RECHARRGE.getValue(), 0);

        HrCompanyEmailInfo companyEmailInfo = companyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(record.getCompanyId());
        int count = companyEmailInfoDao.updateHrCompanyEmailInfoListByCompanyIdAndBalance(record.getCompanyId(), balance+companyEmailInfo.getBalance(), companyEmailInfo.getBalance());
        if (count == 0) {
            retryUpdateCompanyEmailInfo(record.getCompanyId(), balance, 0);
        }

        if (emailLogDao.updateLostById(id, lost)) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_RECHARGE_UPDATE_FAILD;
        }
    }

    private void retryUpdateCompanyEmailInfo(Integer companyId, int balance, int index) throws CommonException {
        if (index >= Constant.RETRY_UPPER_LIMIT) {
            throw TalentPoolException.TALENT_POOL_EMAIL_ACCOUNT_BALANCE_UPDATE_FIALED;
        }
        index++;
        HrCompanyEmailInfo companyEmailInfo = companyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(companyId);
        int count = companyEmailInfoDao.updateHrCompanyEmailInfoListByCompanyIdAndBalance(companyId, balance+companyEmailInfo.getBalance(), companyEmailInfo.getBalance());
        if (count == 0) {
            retryUpdateCompanyEmailInfo(companyId, balance, index);
        }
    }

    public EmailAccountForm fetchEmailAccounts(int companyId, String companyName, int pageNumber,
                                               int pageSize) throws CommonException {

        EmailAccountForm emailAccountForm = new EmailAccountForm();
        emailAccountForm.setCompany_id(companyId);

        Query.QueryBuilder queryBuilder1 = new Query.QueryBuilder();
        if (org.apache.commons.lang.StringUtils.isNotBlank(companyName)) {
            queryBuilder1.where(HrCompany.HR_COMPANY.NAME.getName(), companyName).or(HrCompany.HR_COMPANY.ABBREVIATION.getName(), companyName);
        }

        List<HrCompanyDO> companyDOList = hrCompanyDao.getDatas(queryBuilder1.buildQuery());
        List<Integer> companyIdList = new ArrayList<>();
        if (companyDOList != null && companyDOList.size() > 0) {
            companyIdList.addAll(companyDOList.stream().filter(hrCompanyDO -> hrCompanyDO.getId() > 0).map(HrCompanyDO::getId).collect(Collectors.toList()));
        }
        if (companyId > 0) {
            companyIdList.add(companyId);
        }
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageSize >= Constant.DATABASE_PAGE_SIZE) {
            pageSize =  Constant.DATABASE_PAGE_SIZE;
        }
        int index = (pageNumber - 1) * pageSize;


        int total = hrCompanyEmailInfoDao.countEmailAccounts(companyIdList);
        emailAccountForm.setPage_number(pageNumber);
        emailAccountForm.setPage_size(pageSize);
        emailAccountForm.setTotal(total);

        List<HrCompanyEmailInfo> emailInfoList = hrCompanyEmailInfoDao.fetchOrderByCreateTime(companyIdList, index, pageSize);
        if (emailInfoList != null && emailInfoList.size() > 0) {

            List<Integer> companyIds = emailInfoList
                    .stream()
                    .map(hrCompanyEmailInfo -> hrCompanyEmailInfo.getCompanyId())
                    .collect(Collectors.toList());

            Condition condition = new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder
                    .select(HrCompany.HR_COMPANY.ID.getName())
                    .select(HrCompany.HR_COMPANY.NAME.getName())
                    .select(HrCompany.HR_COMPANY.ABBREVIATION.getName())
                    .where(condition);
            List<HrCompanyDO> hrCompanyDOList = hrCompanyDao.getDatas(queryBuilder.buildQuery());

            logger.info("fetchEmailAccounts hrCompanyDOList:{}", hrCompanyDOList);

            List<EmailAccountInfo> accounts = emailInfoList.stream().map(hrCompanyEmailInfo -> {
                EmailAccountInfo emailAccountInfo = new EmailAccountInfo();
                emailAccountInfo.setTotal(hrCompanyEmailInfo.getTotal());
                emailAccountInfo.setCompany_id(hrCompanyEmailInfo.getCompanyId());
                emailAccountInfo.setBalance(hrCompanyEmailInfo.getBalance());
                emailAccountInfo.setUse_num(hrCompanyEmailInfo.getTotal() - hrCompanyEmailInfo.getBalance());

                String abbreviation = "";
                Optional<HrCompanyDO> hrCompanyDOOptional = hrCompanyDOList
                        .stream()
                        .filter(hrCompanyDO1 -> hrCompanyDO1.getId() ==
                                hrCompanyEmailInfo.getCompanyId().intValue())
                        .findAny();
                if (hrCompanyDOOptional.isPresent()) {
                    abbreviation = hrCompanyDOOptional.get().getAbbreviation();
                    if (org.apache.commons.lang.StringUtils.isBlank(abbreviation)) {
                        abbreviation = hrCompanyDOOptional.get().getName();
                    }
                }
                logger.info("fetchEmailAccounts abbreviation:{}", abbreviation);
                emailAccountInfo.setAbbersive(abbreviation);
                return emailAccountInfo;
            }).collect(Collectors.toList());
            emailAccountForm.setEmail_accounts(accounts);
        } else {
            emailAccountForm.setEmail_accounts(new ArrayList<>());
        }
        logger.info("fetchEmailAccounts emailAccountForm:{}", emailAccountForm);
        return emailAccountForm;
    }

    public EmailAccountConsumptionForm fetchEmailAccountConsumption(int companyId, EmailAccountConsumptionType emailAccountConsumptionType,
                                                                    int pageNumber, int pageSize, DateTime startDate, DateTime endDate) {
        EmailAccountConsumptionForm emailAccountConsumptionForm = new EmailAccountConsumptionForm();
        emailAccountConsumptionForm.setCompany_id(companyId);
        if (pageNumber <= 0) {
            pageNumber = 1;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageSize >= Constant.DATABASE_PAGE_SIZE) {
            pageSize =  Constant.DATABASE_PAGE_SIZE;
        }

        emailAccountConsumptionForm.setPage_number(pageNumber);
        emailAccountConsumptionForm.setPage_size(pageSize);

        int index = (pageNumber - 1) * pageSize;

        Timestamp startTime = null;
        if (startDate != null) {
            startTime = new Timestamp(startDate.getMillis());
        }
        Timestamp endTime = null;
        if (endDate != null) {
            endTime = new Timestamp(endDate.getMillis());
        }
        int total = 0;
        List<EmailAccountConsumption> emailAccountConsumptionList = new ArrayList<>();

        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(companyId);
        if (companyConfDO == null && companyConfDO.getTalentpoolStatus() == TalentPoolStatus.HighLevel.getValue()) {
            switch (emailAccountConsumptionType) {
                case RECHARRGE:
                    total = emailLogDao.countRecharge(companyId, startTime, endTime);
                    List<LogTalentpoolEmailLogRecord> logRecordList = emailLogDao.fetchEmailAccountRechargeRecords(companyId, index, pageSize, startTime, endTime);
                    if (logRecordList != null && logRecordList.size() > 0) {
                        emailAccountConsumptionList = logRecordList.stream()
                                .map(logTalentpoolEmailLogRecord -> {
                                    EmailAccountConsumption emailAccountConsumption = new EmailAccountConsumption();
                                    emailAccountConsumption.setCompany_id(logTalentpoolEmailLogRecord.getCompanyId());
                                    emailAccountConsumption.setCreate_time(new DateTime(logTalentpoolEmailLogRecord.getCreateTime().getTime()).toString("YYYY-MM-dd"));
                                    emailAccountConsumption.setId(logTalentpoolEmailLogRecord.getId());
                                    emailAccountConsumption.setLost(logTalentpoolEmailLogRecord.getLost());
                                    return emailAccountConsumption;
                                })
                                .collect(Collectors.toList());
                        emailAccountConsumptionForm.setPurchases(emailAccountConsumptionList);
                    }
                    break;
                case COMSUMPTION:
                    total = logTalentpoolEmailDailyLogDao.countEmailAccountConsumption(companyId, emailAccountConsumptionType.getValue(), startTime, endTime);
                    List<LogTalentpoolEmailDailyLogRecord> logTalentpoolEmailDailyLogRecordList =
                            logTalentpoolEmailDailyLogDao.fetchEmailAccountConsumption(companyId,
                                    emailAccountConsumptionType.getValue(), index, pageSize, startTime, endTime);
                    if (logTalentpoolEmailDailyLogRecordList != null && logTalentpoolEmailDailyLogRecordList.size() > 0) {
                        emailAccountConsumptionList = logTalentpoolEmailDailyLogRecordList.stream()
                                .map(logTalentpoolEmailDailyLogRecord -> {
                                    EmailAccountConsumption emailAccountConsumption = new EmailAccountConsumption();
                                    emailAccountConsumption.setCompany_id(logTalentpoolEmailDailyLogRecord.getCompanyId());
                                    emailAccountConsumption.setCreate_time(new DateTime(logTalentpoolEmailDailyLogRecord.getCreateTime().getTime()).toString("YYYY-MM-dd"));
                                    emailAccountConsumption.setId(logTalentpoolEmailDailyLogRecord.getId());
                                    emailAccountConsumption.setLost(logTalentpoolEmailDailyLogRecord.getLost());
                                    return emailAccountConsumption;
                                })
                                .collect(Collectors.toList());
                        emailAccountConsumptionForm.setPurchases(emailAccountConsumptionList);
                    }
                    break;
                default:
            }
        }
        emailAccountConsumptionForm.setPurchases(emailAccountConsumptionList);
        emailAccountConsumptionForm.setTotal(total);
        return emailAccountConsumptionForm;
    }
}
