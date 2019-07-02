package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.dictdb.DictReferralEvaluateDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.referraldb.CustomReferralEmployeeBonusDao;
import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfDao;
import com.moseeker.baseorm.dao.referraldb.ReferralRecomEvaluationDao;
import com.moseeker.baseorm.dao.referraldb.ReferralSeekRecommendDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictReferralEvaluateRecord;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.PageUtil;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.SensorSend;
import com.moseeker.entity.pojos.BonusData;
import com.moseeker.entity.pojos.HBData;
import com.moseeker.entity.pojos.ReferralProfileData;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.useraccounts.annotation.RadarSwitchLimit;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.kafka.KafkaSender;
import com.moseeker.useraccounts.service.ReferralRadarService;
import com.moseeker.useraccounts.service.ReferralService;
import com.moseeker.useraccounts.service.impl.activity.Activity;
import com.moseeker.useraccounts.service.impl.activity.ActivityType;
import com.moseeker.useraccounts.service.impl.biztools.HBBizTool;
import com.moseeker.useraccounts.service.impl.pojos.KafkaAskReferralPojo;
import com.moseeker.useraccounts.service.impl.vo.*;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
@Service
@CounterIface
public class ReferralServiceImpl implements ReferralService {

    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private ThemeDao themeDao;

    @Autowired
    private ReferralTemplateSender sender;

    @Autowired
    private CustomReferralEmployeeBonusDao referralEmployeeBonusDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private ReferralEntity referralEntity;

    @Autowired
    private ReferralCompanyConfDao companyConfDao;

    @Autowired
    private HrHbConfigDao configDao;

    @Autowired
    private HrHbPositionBindingDao positionBindingDao;

    @Autowired
    private HrHbItemsDao itemsDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private DictReferralEvaluateDao evaluateDao;

    @Autowired
    private ReferralSeekRecommendDao recommendDao;

    @Autowired
    private ReferralRecomEvaluationDao recomEvaluationDao;
    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    ReferralTemplateSender templateSender;

    @Autowired
    private ReferralRadarService radarService;

    @Autowired
    private KafkaSender kafkaSender;

    ThreadPool tp = ThreadPool.Instance;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private SensorSend sensorSend;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RedPackets getRedPackets(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException {
        logger.info("ReferralServiceImpl getRedPackets id:{}, companyId:{}, pageNum:{}, pageSize:{}", userId, companyId, pageNum, pageSize);
        if (pageSize > Constant.DATABASE_PAGE_SIZE) {
            throw UserAccountException.PROGRAM_FETCH_TOO_MUCH;
        }
        RedPackets redPackets = new RedPackets();
        UserEmployeeDO userEmployeeDO = employeeEntity.getCompanyEmployee(userId,companyId);
        if (userEmployeeDO != null) {
            BigDecimal bonus = new BigDecimal(userEmployeeDO.getBonus());
            redPackets.setTotalBonus(bonus.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        logger.info("ReferralServiceImpl getRedPackets userEmployeeDO:{}", userEmployeeDO);
        List<UserWxUserRecord> wxUserRecords = wxUserDao.getWXUsersByUserId(userId);
        if (wxUserRecords != null && wxUserRecords.size() > 0) {


            List<Integer> wxUserIdList = wxUserRecords
                    .stream()
                    .map(userWxUserRecord -> userWxUserRecord.getId().intValue())
                    .collect(Collectors.toList());
            logger.info("ReferralServiceImpl getRedPackets wxUserIdList:{}", wxUserIdList
                    .stream()
                    .map(integer -> String.valueOf(integer))
                    .collect(Collectors.joining(",")));
            //计算红包总额
            redPackets.setTotalRedpackets(itemsDao.sumOpenedRedPacketsByWxUserIdList(wxUserIdList, companyId));
            logger.info("ReferralServiceImpl getRedPackets totalRedPackets:{}", redPackets.getTotalRedpackets());
            PageUtil pageUtil = new PageUtil(pageNum, pageSize);

            List<HrHbItems> itemsRecords = itemsDao.fetchItemsByWxUserIdList(wxUserIdList, companyId,
                    pageUtil.getIndex(), pageUtil.getSize());
            if (itemsRecords != null && itemsRecords.size() > 0) {

                logger.info("ReferralServiceImpl getRedPackets itemsRecords.size:{}", itemsRecords.size());
                HBData data = referralEntity.fetchHBData(itemsRecords);
                logger.info("ReferralServiceImpl getRedPackets data.candidateNameMap:{}", data.getCandidateNameMap());

                List<RedPacket> list = new ArrayList<>();
                for (HrHbItems hrHbItems : itemsRecords) {
                    list.add(HBBizTool.packageRedPacket(hrHbItems, data));
                }
                logger.info("ReferralServiceImpl getRedPackets list:{}", JSON.toJSONString(list));
                redPackets.setRedpackets(list);
            } else {
                logger.info("ReferralServiceImpl getRedPackets itemsRecords is null!");
            }
        }
        return redPackets;
    }

    @Override
    public BonusList getBonus(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException {
        if (pageSize > Constant.DATABASE_PAGE_SIZE) {
            throw UserAccountException.PROGRAM_FETCH_TOO_MUCH;
        }
        BonusList bonusList = new BonusList();
        UserEmployeeDO userEmployeeDO = employeeEntity.getCompanyEmployee(userId,companyId);
        if (userEmployeeDO != null) {
            BigDecimal bonus = new BigDecimal(userEmployeeDO.getBonus());
            bonusList.setTotalBonus(bonus.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());

            PageUtil pageUtil = new PageUtil(pageNum, pageSize);

            List<ReferralEmployeeBonusRecord> referralEmployeeBonusRecordList
                    = referralEmployeeBonusDao.fetchByEmployeeIdOrderByClaim(userEmployeeDO.getId(),
                    pageUtil.getIndex(), pageUtil.getSize());
            if (referralEmployeeBonusRecordList != null && referralEmployeeBonusRecordList.size() > 0) {

                BonusData bonusData = referralEntity.fetchBonusData(referralEmployeeBonusRecordList);
                logger.info("ReferralServiceImpl getBonus bonusData bonusData.getEmploymentDateMap:{}", bonusData.getEmploymentDateMap());

                List<Bonus> bonuses = new ArrayList<>();
                for (ReferralEmployeeBonusRecord referralEmployeeBonusRecord : referralEmployeeBonusRecordList) {
                    bonuses.add(HBBizTool.packageBonus(referralEmployeeBonusRecord, bonusData));
                }
                logger.info("ReferralServiceImpl getBonus bonuses:{}", JSON.toJSONString(bonuses));
                bonusList.setBonus(bonuses);
            }
        }
        List<UserWxUserRecord> wxUserRecords = wxUserDao.getWXUsersByUserId(userId);
        if (wxUserRecords != null && wxUserRecords.size() > 0) {
            List<Integer> wxUserIdList = wxUserRecords
                    .stream()
                    .map(UserWxUserRecord::getSysuserId)
                    .collect(Collectors.toList());
            double count = itemsDao.sumOpenedRedPacketsByWxUserIdList(wxUserIdList, companyId);
            bonusList.setTotalRedpackets(count);
        }
        return bonusList;
    }

    @Override
    public List<ReferralProfileTab> getReferralProfileTabList(int userId, int companyId, int hrId) throws UserAccountException {
        logger.info("ReferralServiceImpl getReferralProfileTabList userId:{}, companyId:{}, hrId:{}", userId, companyId, hrId);
        long startTime = System.currentTimeMillis();
        List<ReferralLog> logList = referralEntity.fetchReferralLog(userId, employeeEntity.getCompanyIds(companyId), hrId);
        long logListTime = System.currentTimeMillis();
        logger.info("profile tab getReferralProfileTabList groupCompanyRelTime:{}", logListTime- startTime);
        ReferralProfileData profileData = referralEntity.fetchReferralProfileData(logList);
        logger.info("ReferralServiceImpl getReferralProfileTabList profileData:{}", JSONObject.toJSONString(profileData));
        long profileDataTime = System.currentTimeMillis();
        logger.info("profile tab getReferralProfileTabList profileDataTime:{}", profileDataTime- logListTime);

        List<ReferralProfileTab> profileTabs = new ArrayList<>();
        if(profileData != null){
            for(ReferralLog log : logList){
                profileTabs.add(HBBizTool.packageReferralTab(log, profileData));
            }
            logger.info("ReferralServiceImpl getReferralProfileTabList profileTabs:{}", JSONObject.toJSONString(profileTabs));

            return profileTabs.stream().filter(f -> StringUtils.isNotNullOrEmpty(f.getFilePath()))
                    .collect(Collectors.toList());
        }
        long endTime = System.currentTimeMillis();
        logger.info("profile tab getReferralProfileTabList endTime:{}", endTime-profileDataTime);
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void updateActivity(ActivityVO activityVO) throws UserAccountException {

        Activity activity = ActivityType.buildActivity(activityVO.getId(), configDao, positionBindingDao, itemsDao,
                themeDao, positionDao);
        logger.info("ReferralServiceImpl updateActivity activityVO:{}", JSON.toJSONString(activityVO));
        if (activityVO.getStatus() != null) {
            ActivityStatus activityStatus = ActivityStatus.instanceFromValue(activityVO.getStatus().byteValue());
            logger.info("ReferralServiceImpl updateActivity activityStatus:{}", activityStatus);
            if (activityStatus == null) {
                activity.updateInfo(activityVO, true);
            } else {
                switch (activityStatus) {
                    case Deleted:
                        activity.delete();break;
                    case Finish:
                        activity.finish();break;
                    case Running:
                        activity.start(activityVO); break;
                    case Pause:
                        activity.pause(); break;
                    case UnChecked:
                    case Checked:
                    case UnStart:
                        activity.updateInfo(activityVO, true); break;
                }
            }
        } else {
            activity.updateInfo(activityVO, true);
        }
    }

    @CounterIface
    @Override
    public List<ReferralReasonInfo> getReferralReasonInfo(int userId, int companyId, int hrId) throws UserAccountException {
        List<JobApplicationRecord> applicationRecords = applicationDao.getByApplierIdAndCompanyId(userId, companyId);
        logger.info("getReferralReasonInfo applicationRecords:{}",applicationRecords);
        logger.info("getReferralReasonInfo applicationRecords:{}",applicationRecords.size());
        if(!StringUtils.isEmptyList(applicationRecords)){
            List<Integer> applicationIds = applicationRecords.stream().map(m -> m.getId()).collect(Collectors.toList());
            List<ReferralRecomEvaluationRecord> evaluationRecords = referralEntity.fetchEvaluationListByUserId(userId, applicationIds);
            logger.info("getReferralReasonInfo evaluationRecords:{}",evaluationRecords);
            if(!StringUtils.isEmptyList(evaluationRecords)){
                List<DictReferralEvaluateRecord> dictReferralEvaluateDOS = evaluateDao.getDictReferralEvaluate();
                List<ReferralReasonInfo> list = new ArrayList<>();
                for(ReferralRecomEvaluationRecord record: evaluationRecords){
                    ReferralReasonInfo info = new ReferralReasonInfo();
                    info.setId(record.getAppId());
                    info.setRecomReasonText(record.getRecomReasonText());
                    info.setRelationship(record.getRelationship());
                    List<String> referralReasons = StringUtils.stringToList(record.getRecomReasonTag(), ",");
                    List<String> reasons = new ArrayList<>();
                    //把国际化的标签英文转化为中文展示
                    if(!StringUtils.isEmptyList(referralReasons)) {
                        for (String reason : referralReasons) {
                            boolean status = true;
                            for(DictReferralEvaluateRecord evaluateRecord : dictReferralEvaluateDOS){
                                if(reason.equals(evaluateRecord.getTagEn())){
                                    reasons.add(evaluateRecord.getTag());
                                    status = false;
                                    break;
                                }
                            }
                            if(status){
                                reasons.add(reason);
                            }
                        }
                    }
                    info.setReferralReasons(reasons);
                    list.add(info);
                };
                return list;
            }
        }

        return new ArrayList<>();
    }

    @Override
    public void handerKeyInformationStatus(int companyId, int keyInformation) throws UserAccountException {
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("状态", keyInformation, 0, 2);
        String message = vu.validate();
        if(StringUtils.isNotNullOrEmpty(message)){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        HrCompanyDO company = companyDao.getCompanyById(companyId);
        if(company!= null && company.getParentId()>0 ){
            companyId= company.getParentId();
        }
        ReferralCompanyConf companyConf = companyConfDao.fetchOneByCompanyId(companyId);
        if(companyConf!=null){
            companyConf.setReferralKeyInformation((byte) keyInformation);
            companyConfDao.update(companyConf);
        }else{
            companyConf = new ReferralCompanyConf();
            companyConf.setCompanyId(companyId);
            companyConf.setReferralKeyInformation((byte)keyInformation);
            companyConfDao.insertReferralCompanyConf(companyConf);
        }
    }

    @Override
    public int fetchKeyInformationStatus(int companyId) throws UserAccountException {
        HrCompanyDO company = companyDao.getCompanyById(companyId);
        if(company!= null && company.getParentId()>0 ){
            companyId= company.getParentId();
        }
        ReferralCompanyConf companyConf = companyConfDao.fetchOneByCompanyId(companyId);
        if(companyConf != null){
            return companyConf.getReferralKeyInformation();
        }
       return 1;
    }

    @Override
    @RadarSwitchLimit
    public void addReferralSeekRecommend(int companyId, int userId, int postUserId, int positionId, int origin) throws CommonException {
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("候选人编号", userId, 1, null);
        vu.addIntTypeValidate("员工C端编号", postUserId, 1, null);
        vu.addIntTypeValidate("职位编号", userId, 1, null);
        if(StringUtils.isNotNullOrEmpty(vu.validate())){
            logger.info("参数错误：{}",vu.validate());
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        JobPositionDO position = positionDao.getJobPositionById(positionId);
        if(position == null || position.getStatus() != Constant.POSITION_STATUS_START){
            throw UserAccountException.AWARD_POSITION_ALREADY_DELETED;
        }
        ReferralSeekRecommendRecord record = new ReferralSeekRecommendRecord();
        record.setPostUserId(postUserId);
        record.setPresenteeId(userId);
        record.setPositionId(positionId);
        record.setOrigin(origin);
        ReferralSeekRecommendRecord recommendRecord = new ReferralSeekRecommendRecord();
        int i =0;
        while (i<3 && (recommendRecord.getId() == null || recommendRecord.getId() ==0)){
            recommendRecord = recommendDao.insertIfNotExist(record);
            i++;
        }
        if(recommendRecord.getId()<=0){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_FAIL;
        }
        logger.info("recommendRecord:{}", recommendRecord);
        if(recommendRecord.getAppId()<=0) {
            logger.info("==========updateReferralSeekRecommendRecordForRecommendTime");
            recommendDao.updateReferralSeekRecommendRecordForRecommendTime(recommendRecord.getId());
            logger.info("==========publishSeekReferralEvent");
            try {
                templateSender.publishSeekReferralEvent(postUserId, recommendRecord.getId(), userId, positionId);
            }catch (InvalidArgumentException e) {
                logger.error(e.getMessage());
            }
            logger.info("==========updateCandidateShareChainTemlate");
            KafkaAskReferralPojo kafkaAskReferralPojo = initKafkaAskReferralPojo(position.getCompanyId(), userId, positionId);
            kafkaSender.sendMessage(Constant.KAFKA_TOPIC_ASK_REFERRAL, JSON.toJSONString(kafkaAskReferralPojo));
        }
    }

    private KafkaAskReferralPojo initKafkaAskReferralPojo(int companyId, int userId, int positionId) {
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        KafkaAskReferralPojo kafkaAskReferralPojo = new KafkaAskReferralPojo();
        kafkaAskReferralPojo.setAsked(1);
        kafkaAskReferralPojo.setCompany_id(companyId);
        kafkaAskReferralPojo.setEvent("ask_for_referral");
        kafkaAskReferralPojo.setEvent_time(sdf.format(new Date(current)));
        kafkaAskReferralPojo.setPosition_id(positionId);
        kafkaAskReferralPojo.setUser_id(userId);
        return kafkaAskReferralPojo;
    }

    @RadarSwitchLimit
    @Override
    public ContactPushInfo fetchSeekRecommend(int companyId, int referralId, int postUserId) throws CommonException {
        ContactPushInfo info = new ContactPushInfo();
        ReferralSeekRecommendRecord record = recommendDao.fetchByIdAndPostUserId(referralId, postUserId);
        if(record == null){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_NULL;
        }
        UserUserRecord user = userDao.getUserById(record.getPresenteeId());
        if(user == null){
            throw UserAccountException.NODATA_EXCEPTION;
        }
        info.setUserId(user.getId());
        info.setUsername(user.getName());
        if(StringUtils.isNullOrEmpty(user.getName())){
            UserWxUserRecord wxUser = wxUserDao.getWXUserByUserId(user.getId());
            if(wxUser!= null && StringUtils.isNotNullOrEmpty(wxUser.getNickname())){
                info.setUsername(wxUser.getNickname());
            }
        }
        JobPositionDO position = positionDao.getJobPositionById(record.getPositionId());
        if(position != null){
            info.setPositionName(position.getTitle());
        }
        info.setPositionId(record.getPositionId());
        info.setApplicationId(record.getAppId());
        return info;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @RadarSwitchLimit
    @Override
    public void employeeReferralReason(int companyId, int postUserId, int positionId, int referralId, List<String> referralReasons,
                                       byte relationship, String recomReasonText) {

        JobPositionRecord positionDO = positionDao.getPositionById(positionId);

        ReferralSeekRecommendRecord recommendRecord = recommendDao.getById(referralId);
        if(positionDO == null || positionDO.getStatus() != 0 || recommendRecord ==null ){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        Future<UserUserDO> user = tp.startTast(()->userDao.getUser(recommendRecord.getPresenteeId()));
        if(recommendRecord.getPostUserId()!= postUserId){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_NULL;
        }
        if(!employeeEntity.isEmployee(recommendRecord.getPostUserId(), positionDO.getCompanyId())){
            throw UserAccountException.PERMISSION_DENIED;
        }
        Future<UserEmployeeDO> employee = tp.startTast(()->employeeEntity.getCompanyEmployee(recommendRecord.getPostUserId(), positionDO.getCompanyId()));

        //候选人联系内推记录表(referral_seek_recommend)中的origin
        int recommendOrigin = recommendRecord.getOrigin();

        int origin = 0;

        //神策埋点 加入 properties
        Map<String, Object> properties = new HashMap<String, Object>();
        if(2==recommendOrigin){
            //邀请投递
            origin = ApplicationSource.INVITE_REFERRAL.getValue();
            properties.put("apply_origin",2);
        }else{
            //联系内推
            origin = ApplicationSource.SEEK_REFERRAL.getValue();
            properties.put("apply_origin",1);
        }

        int applicationId = 0;
        try {
            applicationId = createJobApplication(user.get().getId(), positionDO.getCompanyId(), positionId, user.get().getName(), origin, recommendRecord.getPostUserId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw CommonException.PROGRAM_EXCEPTION;
        }
        if(applicationId > 0) {
            recommendDao.updateReferralSeekRecommendRecordForAppId(referralId, applicationId);
            try {
                referralEntity.logReferralOperation(positionId, applicationId, referralReasons, String.valueOf(user.get().getMobile()),
                        postUserId, user.get().getId(), relationship, recomReasonText);
                sender.addRecommandReward(employee.get(), user.get().getId(), applicationId, positionId);
                sender.publishReferralEvaluateEvent(referralId, user.get().getId(), positionId, applicationId, employee.get().getId());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }

        //神策埋点
        try{
            String distinctId =String.valueOf(user.get().getId());
            sensorSend.send(distinctId,"inDirectReferral",properties);

            // 求推荐（对应联系内推）漏斗最终投递节点：完成推荐评价
            if(recommendOrigin != 2){     // 联系内推
                sensorSend.send(distinctId,"SeekReferralEvaluate");
            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @RadarSwitchLimit
    @Override
    public void employeeReferralRecomEvaluation(int companyId, int postUserId, int positionId, int presenteeId, List<String> referralReasons, byte relationship, String recomReasonText) throws CommonException, TException {
        ReferralRecomEvaluationRecord record = recomEvaluationDao.fetchByPostPresenteePosition(postUserId, presenteeId, positionId);
        if (record == null) {
            com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication application = applicationDao.getByUserIdAndPositionId(presenteeId, positionId);
            UserUserDO user = userDao.getUser(presenteeId);
            if( user != null ) {
                if(application!= null) {
                    record = new ReferralRecomEvaluationRecord();
                    referralEntity.logReferralOperation(positionId, application != null ? application.getId() : 0,
                            referralReasons, String.valueOf(user.getMobile()), postUserId, user.getId(), relationship, recomReasonText);
                    application.setOrigin(ApplicationSource.FORWARD_APPLICATION.getValue());
                    JobApplicationDO jobApplicationDO = new JobApplicationDO();
                    BeanUtils.copyProperties(application,jobApplicationDO);
                    applicationDao.updateData(jobApplicationDO);
                    updateApplicationESIndex(presenteeId);
                    return;
                }
                throw UserAccountException.NODATA_EXCEPTION;
            }
            throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
        }
        throw UserAccountException.REFERRAL_RECOM_EVALUATION_EXISTS;

    }

    private int createJobApplication(int userId, int companyId, int positionId, String name, int origin,
                                     int employeeSysUserId) throws TException {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setCompany_id(companyId);
        jobApplication.setAppid(0);
        jobApplication.setApplier_id(userId);
        jobApplication.setPosition_id(positionId);
        jobApplication.setApplier_name(name);
        jobApplication.setOrigin(origin);
        jobApplication.setRecommender_user_id(employeeSysUserId);
        jobApplication.setApp_tpl_id(Constant.RECRUIT_STATUS_EMPLOYEE_RECOMMEND);
        Response response = applicationService.postApplication(jobApplication);
        int applicationId = 0;
        if (response.getStatus() == 0) {
            JSONObject jsonObject1 = JSONObject.parseObject(response.getData());
            applicationId = jsonObject1.getInteger("jobApplicationId");
            updateApplicationESIndex(userId);
        }

        return applicationId;
    }

    private void updateApplicationESIndex(int userId) {
        logger.info("==========更新data/profile==============");
        redisClient.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_PROFILE_COMPANY_USER_IDS",String.valueOf(userId));
        logger.info("==========更新data/profile===userId=={}==============",userId);
    }
}
