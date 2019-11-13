package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.constant.TalentPoolStatus;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyCsDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyEmailInfoDao;
import com.moseeker.baseorm.dao.logdb.LogTalentpoolEmailDailyLogDao;
import com.moseeker.baseorm.dao.logdb.LogTalentpoolEmailLogDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyCs;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailDailyLogRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ConditionOp;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.entity.Constant.TalentpoolEmailType;
import com.moseeker.entity.exception.TalentPoolException;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumption;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountInfo;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailListStruct;

import java.sql.*;
import java.util.Date;
import java.util.jar.JarEntry;
import org.apache.commons.collections.ArrayStack;
import org.apache.thrift.Option;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
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
    private HrCompanyCsDao companyCsDao;
    @Autowired
    private LogTalentpoolEmailLogDao emailLogDao;
    @Autowired
    private LogTalentpoolEmailDailyLogDao logTalentpoolEmailDailyLogDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyConfDao companyConfDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private MandrillMailListConsumer mandrillMailListConsumer;

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

    public String updateEmailInfo( int company_id, int type, int disable, String context, String inscribe){
        Query query = new Query.QueryBuilder().where("company_id", company_id)
                .and("config_id", type).buildQuery();
        TalentpoolEmailRecord emailRecord = talentpoolEmailDao.getRecord(query);
        if(disable>=0){
            if(TalentpoolEmailType.instanceFromByte(type).getStatus() || disable == 1) {
                emailRecord.setDisable(disable);
            }else {
                return "手动触发，开关不能关闭!";
            }
        }
        if(StringUtils.isNotNullOrEmpty(context)){
            ValidateUtil vu = new ValidateUtil();
            vu.addSensitiveValidate("正文内容", context, null, null);
            vu.addStringLengthValidate("正文内容", context, null, null, 0, 10001);
            String message = vu.validate();
            if(StringUtils.isNotNullOrEmpty(message)){
                return message;
            }
            emailRecord.setContext(context);
        }
        if(StringUtils.isNotNullOrEmpty(inscribe)){
            ValidateUtil vu = new ValidateUtil();
            vu.addSensitiveValidate("落款", inscribe, null, null);
            vu.addStringLengthValidate("落款", inscribe,null,null,0, 50);
            String message = vu.validate();
            if(StringUtils.isNotNullOrEmpty(message)){
                return message;
            }
            emailRecord.setInscribe(inscribe);
        }
        int result = talentpoolEmailDao.updateRecord(emailRecord);
        return "OK";
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

    public int handerTalentpoolEmailLogAndBalanceNew(int useCount, int type, int company_id, int hr_id) throws TalentPoolException {
        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(company_id);
        if (companyConfDO == null) {
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

        if(balance == useCount){
            List<TalentpoolEmailRecord> emailRecordList = talentpoolEmailDao.getTalentpoolEmailRecordByCompanyId(company_id);
            for(TalentpoolEmailRecord record : emailRecordList){
                logger.info("handerTalentpoolEmailLogAndBalance email status id:{};disable:{}",record.getId(),record.getDisable());
                logger.info("handerTalentpoolEmailLogAndBalance bool:{}",TalentpoolEmailType.instanceFromByte(record.getConfigId()).getStatus());
                if(TalentpoolEmailType.instanceFromByte(record.getConfigId()).getStatus()) {
                    record.setDisable(0);
                }
            }
            talentpoolEmailDao.updateRecords(emailRecordList);
        }
        long timeAtStartOfDay = new DateTime().withTimeAtStartOfDay().getMillis();
        logTalentpoolEmailDailyLogDao.upsertDailyLog(timeAtStartOfDay, company_id, useCount);
        if(balance>100&&balance-useCount<=100){
            sendEmailToHrAndCs(balance-useCount, company_id);
        }
        LogTalentpoolEmailLogRecord record = new LogTalentpoolEmailLogRecord();
        record.setCompanyId(company_id);
        record.setType(type);
        record.setLost(useCount);
        record.setBalance(balance - useCount);
        record.setHrId(hr_id);
        return emailLogDao.addRecord(record).getId();
    }
    /*
     向hr和cs发送邮件
     */
    private void sendEmailToHrAndCs(int balance,int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyRecord record=hrCompanyDao.getRecord(query);
        if(record==null){
          return ;
        }
        Query query1=new Query.QueryBuilder().where("id",record.getHraccountId()).buildQuery();
        UserHrAccountRecord userHrAccountRecord=userHrAccountDao.getRecord(query1);

        String companyName=record.getName();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy-MM-dd");
        String sendDate=ff.format(new Date());
        MandrillEmailListStruct struct=new MandrillEmailListStruct();
        struct.setFrom_email("info@moseeker.net");
        struct.setFrom_name("仟寻MoSeeker");
        struct.setSubject("【仟寻招聘】邮件额度不足");
        struct.setTemplateName("insufficient-amount-of-email");
        HrCompanyCs companyCs = companyCsDao.getHrCompanyCsByCompanyId(companyId);
        List<Map<String,String>> tos=new ArrayList<>();
        List<Map<String,String>> merges=new ArrayList<>();
        if(userHrAccountRecord!=null){
            Map<String,String> to1=new HashMap<>();
            to1.put("to_email",userHrAccountRecord.getEmail());
            to1.put("to_name",userHrAccountRecord.getUsername());
            tos.add(to1);
            Map<String,String> map=new HashMap<>();
            map.put("company",companyName);
            map.put("email_amount",balance+"");
            map.put("send_date",sendDate);
            map.put("rcpt",userHrAccountRecord.getEmail());
            merges.add(map);
        }
       if(companyCs != null){
           Map<String,String> to1=new HashMap<>();
           to1.put("to_email",companyCs.getEmail());
           to1.put("to_name",companyCs.getName());
           tos.add(to1);
           Map<String,String> map=new HashMap<>();
           map.put("company",companyName);
           map.put("email_amount",balance+"");
           map.put("send_date",sendDate);
           map.put("rcpt",companyCs.getEmail());
           merges.add(map);
       }
        struct.setTo(tos);
        String mergeJson = JSON.toJSONString(merges);
        struct.setMergeVars(mergeJson);
        try {
            mandrillMailListConsumer.sendMailList(struct);
        } catch (Exception e) {
            logger.error("邮件剩余额度发送邮件失败：{}",e.getMessage());
        }
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
        logTalentpoolEmailDailyLogDao.upsertDailyLog(timeAtStartOfDay, record.getCompanyId(), balance);

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

        logger.info("fetchEmailAccounts companyId:{}, companyName:{}, pageNumber:{}, pageSize:{}", companyId, companyName, pageNumber, pageSize);
        EmailAccountForm emailAccountForm = new EmailAccountForm();
        emailAccountForm.setCompany_id(companyId);

        List<Integer> companyIdList = new ArrayList<>();

        Condition condition = null;

        if (companyId > 0) {
            condition = new Condition(HrCompany.HR_COMPANY.ID.getName(), companyId);
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(companyName)) {
            if (condition != null) {
                Condition condition1 = new Condition(HrCompany.HR_COMPANY.NAME.getName(), companyName);
                condition1.addCondition(new Condition(HrCompany.HR_COMPANY.ABBREVIATION.getName(), companyName), ConditionOp.OR);
                condition.addInnerCondition(condition1);
                //condition.addInnerCondition(new Condition(HrCompany.HR_COMPANY.NAME.getName(), companyName).addCondition(new Condition(HrCompany.HR_COMPANY.ABBREVIATION.getName(), companyName), ConditionOp.OR), ConditionOp.AND);
            } else {
                condition = new Condition(HrCompany.HR_COMPANY.NAME.getName(), companyName);
                condition.addCondition(new Condition(HrCompany.HR_COMPANY.ABBREVIATION.getName(), companyName), ConditionOp.OR);
            }
        }

        if (condition != null) {
            logger.info("fetchEmailAccounts condition:{}", condition);
            Query.QueryBuilder queryBuilder1 = new Query.QueryBuilder();
            queryBuilder1.where(condition);
            List<HrCompanyDO> companyDOList = hrCompanyDao.getDatas(queryBuilder1.buildQuery());
            if (companyDOList != null && companyDOList.size() > 0) {
                companyIdList = companyDOList.stream().filter(hrCompanyDO -> hrCompanyDO.getId() > 0).map(HrCompanyDO::getId).collect(Collectors.toList());
            }
        }
        emailAccountForm.setPage_number(pageNumber);
        emailAccountForm.setPage_size(pageSize);

        if (condition != null && (companyIdList == null || companyIdList.size() == 0)) {
            emailAccountForm.setTotal(0);
            return emailAccountForm;
        }
        if (condition == null) {
            companyIdList = null;
        }

        logger.info("fetchEmailAccounts companyIdList:{}", companyIdList);

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
        emailAccountForm.setTotal(total);

        List<HrCompanyEmailInfo> emailInfoList = hrCompanyEmailInfoDao.fetchOrderByCreateTime(companyIdList, index, pageSize);
        if (emailInfoList != null && emailInfoList.size() > 0) {

            List<Integer> companyIds = emailInfoList
                    .stream()
                    .map(hrCompanyEmailInfo -> hrCompanyEmailInfo.getCompanyId())
                    .collect(Collectors.toList());

            Condition condition1 = new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder
                    .select(HrCompany.HR_COMPANY.ID.getName())
                    .select(HrCompany.HR_COMPANY.NAME.getName())
                    .select(HrCompany.HR_COMPANY.ABBREVIATION.getName())
                    .where(condition1);
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
                    abbreviation = org.apache.commons.lang.StringUtils.isNotBlank(hrCompanyDOOptional.get().getName())?hrCompanyDOOptional.get().getName():hrCompanyDOOptional.get().getAbbreviation();
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

        int total = 0;
        List<EmailAccountConsumption> emailAccountConsumptionList = new ArrayList<>();

        HrCompanyConfDO companyConfDO = companyConfDao.getHrCompanyConfByCompanyId(companyId);
        if (companyConfDO != null && companyConfDO.getTalentpoolStatus() == TalentPoolStatus.HighLevel.getValue()) {
            switch (emailAccountConsumptionType) {
                case RECHARRGE:
                    Timestamp startTime = null;
                    if (startDate != null) {
                        startTime = new Timestamp(startDate.getMillis());
                    }
                    Timestamp endTime = null;
                    if (endDate != null) {
                        endTime = new Timestamp(endDate.getMillis());
                    }
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
                    java.sql.Date startTime1 = null;
                    if (startDate != null) {
                        startTime1 = new java.sql.Date(startDate.getMillis());
                    }
                    java.sql.Date endTime1 = null;
                    if (endDate != null) {
                        endTime1 = new java.sql.Date(endDate.getMillis());
                    }
                    total = logTalentpoolEmailDailyLogDao.countEmailAccountConsumption(companyId, startTime1, endTime1);
                    List<LogTalentpoolEmailDailyLogRecord> logTalentpoolEmailDailyLogRecordList =
                            logTalentpoolEmailDailyLogDao.fetchEmailAccountConsumption(companyId, index, pageSize,
                                    startTime1, endTime1);
                    if (logTalentpoolEmailDailyLogRecordList != null && logTalentpoolEmailDailyLogRecordList.size() > 0) {
                        emailAccountConsumptionList = logTalentpoolEmailDailyLogRecordList.stream()
                                .map(logTalentpoolEmailDailyLogRecord -> {
                                    EmailAccountConsumption emailAccountConsumption = new EmailAccountConsumption();
                                    emailAccountConsumption.setCompany_id(logTalentpoolEmailDailyLogRecord.getCompanyId());
                                    emailAccountConsumption.setCreate_time(new DateTime(logTalentpoolEmailDailyLogRecord.getDate().getTime()).toString("YYYY-MM-dd"));
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
