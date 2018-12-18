package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionShareRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.dictdb.DictReferralEvaluateDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.referraldb.CustomReferralEmployeeBonusDao;
import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfDao;
import com.moseeker.baseorm.dao.referraldb.ReferralConnectionChainDao;
import com.moseeker.baseorm.dao.referraldb.ReferralConnectionLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.referraldb.ReferralSeekRecommendDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictReferralEvaluateRecord;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.PageUtil;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.pojos.BonusData;
import com.moseeker.entity.pojos.HBData;
import com.moseeker.entity.pojos.ReferralProfileData;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionShareRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.referral.struct.CheckEmployeeInfo;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.ReferralService;
import com.moseeker.useraccounts.service.constant.RadarStateEnum;
import com.moseeker.useraccounts.service.impl.activity.Activity;
import com.moseeker.useraccounts.service.impl.activity.ActivityType;
import com.moseeker.useraccounts.service.impl.biztools.HBBizTool;
import com.moseeker.useraccounts.service.impl.vo.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private JobPositionDao positionDao;

    @Autowired
    private CandidateShareChainDao shareChainDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private CandidatePositionShareRecordDao positionShareRecordDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private TemplateHelper templateHelper;

    @Autowired
    private ReferralConnectionChainDao connectionChainDao;

    @Autowired
    private ReferralConnectionLogDao connectionLogDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private Environment environment;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    ReferralTemplateSender templateSender;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Integer CHAIN_LIMIT = 5;

    @Override
    public RedPackets getRedPackets(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException {
        if (pageSize > Constant.DATABASE_PAGE_SIZE) {
            throw UserAccountException.PROGRAM_FETCH_TOO_MUCH;
        }
        RedPackets redPackets = new RedPackets();
        UserEmployeeDO userEmployeeDO = employeeEntity.getCompanyEmployee(userId,companyId);
        if (userEmployeeDO != null) {
            BigDecimal bonus = new BigDecimal(userEmployeeDO.getBonus());
            redPackets.setTotalBonus(bonus.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        List<UserWxUserRecord> wxUserRecords = wxUserDao.getWXUsersByUserId(userId);
        if (wxUserRecords != null && wxUserRecords.size() > 0) {

            List<Integer> wxUserIdList = wxUserRecords
                    .stream()
                    .map(userWxUserRecord -> userWxUserRecord.getId().intValue())
                    .collect(Collectors.toList());
            //计算红包总额
            redPackets.setTotalRedpackets(itemsDao.sumOpenedRedPacketsByWxUserIdList(wxUserIdList, companyId));

            PageUtil pageUtil = new PageUtil(pageNum, pageSize);

            List<HrHbItems> itemsRecords = itemsDao.fetchItemsByWxUserIdList(wxUserIdList, companyId,
                    pageUtil.getIndex(), pageUtil.getSize());
            if (itemsRecords != null && itemsRecords.size() > 0) {

                HBData data = referralEntity.fetchHBData(itemsRecords);

                List<RedPacket> list = new ArrayList<>();
                for (HrHbItems hrHbItems : itemsRecords) {
                    list.add(HBBizTool.packageRedPacket(hrHbItems, data));
                }
                redPackets.setRedpackets(list);
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

                List<Bonus> bonuses = new ArrayList<>();
                for (ReferralEmployeeBonusRecord referralEmployeeBonusRecord : referralEmployeeBonusRecordList) {
                    bonuses.add(HBBizTool.packageBonus(referralEmployeeBonusRecord, bonusData));
                }
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
        List<ReferralLog> logList = referralEntity.fetchReferralLog(userId, employeeEntity.getCompanyIds(companyId), hrId);
        ReferralProfileData profileData = referralEntity.fetchReferralProfileData(logList);
        List<ReferralProfileTab> profileTabs = new ArrayList<>();
        if(profileData != null){
            for(ReferralLog log : logList){
                profileTabs.add(HBBizTool.packageReferralTab(log, profileData));
            }
            return profileTabs.stream().filter(f -> StringUtils.isNotNullOrEmpty(f.getFilePath()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void updateActivity(ActivityVO activityVO) throws UserAccountException {

        Activity activity = ActivityType.buildActivity(activityVO.getId(), configDao, positionBindingDao, itemsDao,
                positionDao);
        if (activityVO.getStatus() != null) {
            ActivityStatus activityStatus = ActivityStatus.instanceFromValue(activityVO.getStatus().byteValue());

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
                        activity.updateInfo(activityVO, false);break;
                    case Checked:
                    case UnStart:
                        activity.updateInfo(activityVO, true); break;
                }
            }
        } else {
            activity.updateInfo(activityVO, true);
        }
    }

    @Override
    public List<ReferralReasonInfo> getReferralReasonInfo(int userId, int companyId, int hrId) throws UserAccountException {
        List<JobApplicationRecord> applicationRecords = applicationDao.getByApplierIdAndCompanyId(userId, companyId);
        if(!StringUtils.isEmptyList(applicationRecords)){
            List<Integer> applicationIds = applicationRecords.stream().map(m -> m.getId()).collect(Collectors.toList());
            List<ReferralRecomEvaluationRecord> evaluationRecords = referralEntity.fetchEvaluationListByUserId(userId, applicationIds);
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
    public String getRadarCards(ReferralCardInfo cardInfo) {
        Timestamp tenMinite = new Timestamp(cardInfo.getTimestamp());
        Timestamp beforeTenMinite = new Timestamp(cardInfo.getTimestamp() - 1000 * 60 * 10);
        // 获取指定时间前十分钟内的职位浏览人
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getRadarCards(cardInfo.getUser_id(), beforeTenMinite, tenMinite);
        // 获取浏览人的userId
        Set<Integer> beRecomUserIds = shareChainDOS.stream().map(CandidateShareChainDO::getPresenteeUserId).collect(Collectors.toSet());
        // 将员工过滤掉，获取职位浏览人中非员工的userId
        getUnEmployeeUserIds(beRecomUserIds);
        if(beRecomUserIds.size() == 0){
            return null;
        }
        // 后边需要用到员工头像和昵称，在这里一并查出来
        Set<Integer> allUsers = new HashSet<>(beRecomUserIds);
        allUsers.add(cardInfo.getUser_id());
        // 将过滤后的员工id对应员工信息，用于后续数据组装
        HrWxWechatDO hrWxWechatDO = wechatDao.getHrWxWechatByCompanyId(cardInfo.getCompany_id());
        List<UserWxUserDO> userUserDOS = wxUserDao.getWXUserMapByUserIds(allUsers, hrWxWechatDO.getId());
        Map<Integer, UserWxUserDO> idUserMap = userUserDOS.stream().collect(Collectors.toMap(UserWxUserDO::getSysuserId, userWxUserDO->userWxUserDO));
        // 通过职位浏览人idSet和10分钟时间段获取转发的信息
        List<CandidatePositionShareRecordDO> positionShareDOS = positionShareRecordDao.fetchPositionShareByUserIds(beRecomUserIds, cardInfo.getUser_id(), beforeTenMinite, tenMinite);
        if(positionShareDOS.size() == 0){
            return null;
        }
        List<Integer> positionIds = positionShareDOS.stream().map(positionShareDO -> (int)positionShareDO.getPositionId()).distinct().collect(Collectors.toList());
        List<JobPositionDO> jobPositions = positionDao.getPositionListWithoutStatus(positionIds);
        // 将职位id和职位映射，用于后续数据组装
        Map<Integer, JobPositionDO> idPositionMap = jobPositions.stream().collect(Collectors.toMap(JobPositionDO::getId, jobPositionDO -> jobPositionDO));
        // 通过职位id和userid获取职位转发记录
        List<CandidatePositionDO> candidatePositionDOS = candidatePositionDao.fetchRecentViewedByUserIdsAndPids(beRecomUserIds, positionIds);
        List<JSONObject> cards = new ArrayList<>();
        int startIndex = (cardInfo.getPage_number() - 1) * cardInfo.getPage_size();
        for(int i = startIndex; i < candidatePositionDOS.size() && i < cardInfo.getPage_number() * cardInfo.getPage_size();i++){
            CandidatePositionDO candidatePositionDO = candidatePositionDOS.get(i);
            if(candidatePositionDO != null){
                // 构造单个职位浏览人的卡片
                cards.add(doCreateCard(cardInfo, candidatePositionDO, idPositionMap.get(candidatePositionDO.getPositionId()), shareChainDOS, idUserMap));
            }
        }
        logger.info(JSON.toJSONString(cards));
        return JSON.toJSONString(cards);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inviteApplication(ReferralInviteInfo inviteInfo) {
        JSONObject result = new JSONObject();
        // 检验是否关注公众号
        if(!checkIsSubscribe(inviteInfo.getEndUserId(), inviteInfo.getCompanyId())){
            return null;
        }
        // 先查询之前是否存在，是否已完成，如果是员工触发则生成连连看链路，遍历每个员工入库
        ReferralConnectionLogRecord connectionLogRecord = connectionLogDao.fetchChainLogRecord(inviteInfo.getUserId(), inviteInfo.getEndUserId(), inviteInfo.getPid());
        // 查询最短路径 todo neo4j
        List<Integer> shortestChain = getShortestChain(inviteInfo.getUserId(), inviteInfo.getEndUserId());
        // 只有两度和三度的情况下才会产生连连看链路
        if(shortestChain.size() >= 3 && shortestChain.size() <= 4 && (connectionLogRecord == null || connectionLogRecord.getState() == 1)){
            // 如果之前该职位没有连接过连连看，生成一条最短链路记录
            result.put("chain_id", doInsertConnection(connectionLogRecord, shortestChain, inviteInfo));
        }
        // 组装连连看链路返回数据
        result.put("chain", doInitRadarUsers(shortestChain));
        // 邀请投递后，将该候选人标记为已处理，下次该职位的候选人卡片中不再包括此人
        candidatePositionDao.updateTypeByPidAndUid(inviteInfo.getPid(), inviteInfo.getEndUserId());
        // 发送消息模板
        boolean isSent = sendInviteTemplate(inviteInfo);
        result.put("notified", isSent ? 1 : 0);
        result.put("degree", shortestChain.size());
        return JSON.toJSONString(result);
    }

    @Override
    public void ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) {
        candidatePositionDao.updateTypeByPidAndUid(ignoreInfo.getPid(), ignoreInfo.getEndUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String connectRadar(ConnectRadarInfo radarInfo) {
        ReferralConnectionLogRecord connectionLogRecord = connectionLogDao.fetchByChainId(radarInfo.getChainId());
        if(connectionLogRecord == null){
            return null;
        }
        List<ReferralConnectionChainRecord> chainRecords = connectionChainDao.fetchChainsByRootChainId(connectionLogRecord.getRootChainId());
        Set<Integer> userIds = getChainRecordsUserIds(chainRecords);
        // 检验当前参数：recomUserId/nextUserId是否在链路中
        if(!userIds.contains(radarInfo.getNextUserId()) || !userIds.contains(radarInfo.getRecomUserId())){
            return null;
        }
        // todo 校验当前链接是否合法
        ReferralConnectionChainRecord currentRecord = null;
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getRecomUserId() == radarInfo.getRecomUserId() && radarInfo.getNextUserId() == chainRecord.getNextUserId()){
                currentRecord = chainRecord;
            }
        }
        // 检验是否需要新增链路 todo chainRecords有可能要重新查一遍
        if(currentRecord == null){
            chainRecords.add(insertExtraRecord(radarInfo, chainRecords));
        }else {
            if(currentRecord.getState() != 1){
                // 已有链路，修改连接状态
                currentRecord.setState((byte)1);
                connectionChainDao.updateRecord(currentRecord);
            }
        }
        UserEmployeeRecord userEmployee = userEmployeeDao.getActiveEmployee(connectionLogRecord.getRootUserId(), connectionLogRecord.getCompanyId());
        // 获取排好序并包括连接状态的人脉连连看链路
        List<RadarUserInfo> userChains = getOrderedChains(userIds, chainRecords, connectionLogRecord.getCompanyId());
        // 填充员工姓名
        fillEmployeeName(userEmployee, userChains);
        // 修改连连看是否连接完成的状态
        updateConnectionLog(radarInfo, connectionLogRecord, chainRecords);
        RadarConnectResult result = new RadarConnectResult();
        result.setDegree(connectionLogRecord.getDegree().intValue());
        result.setPid(connectionLogRecord.getPositionId());
        result.setState(connectionLogRecord.getState().intValue());
        result.setRadarUserInfos(userChains);
        return JSON.toJSONString(result);
    }

    @Override
    public String checkEmployee(CheckEmployeeInfo checkInfo) throws BIZException {
        JSONObject result = new JSONObject();
        int parentChainId = checkInfo.getParentChainId();
        int pid = checkInfo.getPid();
        int recomUserId = checkInfo.getRecomUserId();
        if(parentChainId != 0){
            CandidateShareChainDO shareChainDO = shareChainDao.getRecordById(parentChainId);
            if(shareChainDO == null){
                throw UserAccountException.REFERRAL_CHAIN_NONEXISTS;
            }
            recomUserId = shareChainDO.getRootRecomUserId();
        }
        UserEmployeeRecord employeeRecord = userEmployeeDao.getActiveEmployeeByUserId(recomUserId);
        if(employeeRecord == null){
            result.put("employee", 0);
            return JSON.toJSONString(result);
        }
        int companyId = employeeRecord.getCompanyId();
        JobPositionDO jobPositionDO = positionDao.getJobPositionById(pid);
        if(jobPositionDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
        }
        if(companyId != jobPositionDO.getCompanyId()){
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        HrWxWechatDO hrWxWechatDO = wechatDao.getHrWxWechatByCompanyId(companyId);
        UserWxUserRecord wxUserRecord = wxUserDao.getWxUserByUserIdAndWechatId(recomUserId, hrWxWechatDO.getId());
        result.put("employee", 1);
        RadarUserInfo userInfo = new RadarUserInfo();
        userInfo.setUid(recomUserId);
        userInfo.setName(employeeRecord.getCname());
        userInfo.setAvatar(wxUserRecord.getHeadimgurl());
        result.put("user", userInfo);
        return JSON.toJSONString(result);
    }

    private void updateConnectionLog(ConnectRadarInfo radarInfo, ReferralConnectionLogRecord connectionLogRecord, List<ReferralConnectionChainRecord> chainRecords) {
        // 更新degree
        if(radarInfo.getNextUserId() == connectionLogRecord.getEndUserId()){
            connectionLogRecord.setState((byte)RadarStateEnum.Finished.getState());
            updateExtraConnectionChain(radarInfo.getNextUserId(), chainRecords);
        }else if(radarInfo.getNextUserId() == 0 && radarInfo.getRecomUserId() == connectionLogRecord.getRootUserId()){
            return;
        }else if(radarInfo.getNextUserId() != connectionLogRecord.getEndUserId() && radarInfo.getNextUserId() != 0){
            connectionLogRecord.setState((byte)RadarStateEnum.Connecting.getState());
        }

        connectionLogRecord.setUpdateTime(null);
        connectionLogDao.updateRecord(connectionLogRecord);
    }

    private void updateExtraConnectionChain(int nextUserId, List<ReferralConnectionChainRecord> chainRecords) {
        int parentId = 0;
        List<Integer> chainRecordIds = new ArrayList<>();
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(nextUserId == chainRecord.getNextUserId()){
                chainRecordIds.add(chainRecord.getId());
                parentId = chainRecord.getParentId();
                break;
            }
        }
        chainRecordIds.addAll(getChainRecordIdsByRecurrence(parentId, chainRecordIds, chainRecords));
        List<ReferralConnectionChainRecord> linkedRecords = chainRecords.stream().filter(record -> record.getState() == 1).collect(Collectors.toList());
        List<Integer> linkedIds = linkedRecords.stream().map(ReferralConnectionChainRecord::getId).collect(Collectors.toList());
        linkedIds.removeAll(chainRecordIds);
        connectionChainDao.updateStateByIds(linkedIds);
    }

    private List<Integer> getChainRecordIdsByRecurrence(int parentId, List<Integer> chainRecordIds, List<ReferralConnectionChainRecord> chainRecords) {
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getId() == parentId){
                chainRecordIds.add(chainRecord.getId());
                if(chainRecord.getParentId() == 0){
                    return chainRecordIds;
                }else {
                    return getChainRecordIdsByRecurrence(chainRecord.getParentId(), chainRecordIds, chainRecords);
                }
            }
        }
        logger.info("====================应该不会走到这");
        return chainRecordIds;
    }

    private void fillEmployeeName(UserEmployeeRecord employee, List<RadarUserInfo> userChains) {
        for(RadarUserInfo userInfo : userChains){
            if(userInfo.getUid().equals(employee.getSysuserId())){
                userInfo.setName(employee.getCname());
                break;
            }
        }
    }

    /**
     * 获取排序后的连连看链路
     *
     * @param userIds 用户id
     * @param chainRecords 连连看链路记录
     * @param companyId
     * @return 组装前端需要展示的数据
     */
    private List<RadarUserInfo> getOrderedChains(Set<Integer> userIds, List<ReferralConnectionChainRecord> chainRecords, Integer companyId) {
        HrWxWechatDO hrWxWechatDO = wechatDao.getHrWxWechatByCompanyId(companyId);
        List<UserWxUserDO> userDOS = wxUserDao.getWXUserMapByUserIds(userIds, hrWxWechatDO.getId());
        List<RadarUserInfo> userChains = new ArrayList<>();
        List<ReferralConnectionChainRecord> linkedChain = getLinkedList(chainRecords);

        for(UserWxUserDO userDO : userDOS){
            RadarUserInfo userInfo = new RadarUserInfo();
            // 初始化连连看信息
            userInfo = userInfo.initFromChainsRecord(userDO, chainRecords);
            // 填充连连看排序
            userInfo = userInfo.fillOrderFromChainsRecord(userDO, linkedChain);
            userChains.add(userInfo);
        }
        return userChains;
    }

    private List<ReferralConnectionChainRecord> getLinkedList(List<ReferralConnectionChainRecord> chainRecords) {
        List<ReferralConnectionChainRecord> linkedChain = new ArrayList<>();
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getState() == 1){
                linkedChain.add(chainRecord);
            }
        }
        return linkedChain;
    }

    private ReferralConnectionChainRecord insertExtraRecord(ConnectRadarInfo radarInfo, List<ReferralConnectionChainRecord> chainRecords) {
        int parentId = 0;
        for(ReferralConnectionChainRecord connectionChain : chainRecords){
            if(connectionChain.getRecomUserId() == radarInfo.getRecomUserId()){
                if(connectionChain.getParentId() != 0){
                    parentId = connectionChain.getParentId();
                }
                break;
            }
        }
        ReferralConnectionChainRecord newChainRecord = new ReferralConnectionChainRecord();
        newChainRecord.setRecomUserId(radarInfo.getRecomUserId());
        newChainRecord.setNextUserId(radarInfo.getNextUserId());
        newChainRecord.setParentId(parentId);
        newChainRecord.setClickTime(new Timestamp(System.currentTimeMillis()));
        newChainRecord.setRootParentId(chainRecords.get(0).getRootParentId());
        newChainRecord.setState((byte)1);
        return connectionChainDao.insertRecord(newChainRecord);
    }

    private Set<Integer> getChainRecordsUserIds(List<ReferralConnectionChainRecord> chainRecords) {
        Set<Integer> userIds = chainRecords.stream().map(ReferralConnectionChainRecord::getRecomUserId).collect(Collectors.toSet());
        userIds.addAll(chainRecords.stream().map(ReferralConnectionChainRecord::getNextUserId).collect(Collectors.toSet()));
        return userIds;
    }

    private boolean checkIsSubscribe(int userId, int companyId) {
        HrWxWechatDO hrWxHrChatDO = wechatDao.getHrWxWechatByCompanyId(companyId);
        UserWxUserRecord endUserRecord = wxUserDao.getWxUserByUserIdAndWechatId(userId, hrWxHrChatDO.getId());
        // 候选人未关注公众号，返回为空
        return endUserRecord != null && endUserRecord.getIsSubscribe() != 0;
    }

    private List<RadarUserInfo> doInitRadarUsers(List<Integer> shortestChain) {
        // 最短路径的userChains信息
        List<RadarUserInfo> userChains = new ArrayList<>();
        List<UserWxUserRecord> userRecords = wxUserDao.getWXUserMapByUserIds(shortestChain);
        for(UserWxUserRecord userRecord : userRecords){
            RadarUserInfo userInfo = new RadarUserInfo();
            userChains.add(userInfo.initFromUserWxUser(userRecord));
        }
        return userChains;
    }

    private void handleRadarCardType(ReferralInviteInfo inviteInfo) {
        candidatePositionDao.updateTypeByPidAndUid(inviteInfo.getPid(), inviteInfo.getEndUserId());
//        List<Integer> chainIdList = new ArrayList<>();
//        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getRadarCardsByPid(inviteInfo);
//        for(CandidateShareChainDO shareChainDO : shareChainDOS){
//            if(shareChainDO.getPresenteeUserId() == inviteInfo.getEndUserId()){
//                chainIdList.add(shareChainDO.getId());
//            }
//        }
//        shareChainDao.updateTypeByIds(chainIdList, 1);
    }

    private int doInsertConnection(ReferralConnectionLogRecord connectionLogRecord, List<Integer> shortestChain, ReferralInviteInfo inviteInfo) {
        if(connectionLogRecord == null){
            // 插入连连看链路记录
            int rootParentId = doCreateConnectionChainRecords(shortestChain);
            // 反填后入库
            connectionLogRecord = doCreateConnectionLogRecord(rootParentId, inviteInfo, shortestChain.size());
        }else{
            // 如果之前该职位已经连接过连连看，那么直接将连连看链路返回
            connectionChainDao.fetchChainsByRootChainId(connectionLogRecord.getRootChainId());
        }
        return connectionLogRecord.getId();
    }

    private void fillParentChainId(List<ReferralConnectionChainRecord> connectionChainRecords, int rootParentId) {
        for(int i=0;i<connectionChainRecords.size();i++){
            ReferralConnectionChainRecord connectionChainRecord = connectionChainRecords.get(i);
            int parentId;
            if(i == 0){
                parentId = 0;
                connectionChainRecord.setRootParentId(connectionChainRecord.getId());
                connectionChainRecord.setParentId(parentId);
                continue;
            }
            parentId = connectionChainRecords.get(i-1).getId();
            connectionChainRecord.setRootParentId(rootParentId);
            connectionChainRecord.setParentId(parentId);
        }
    }

    private int doCreateConnectionChainRecords(List<Integer> shortestChain) {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        List<ReferralConnectionChainRecord> connectionChainRecords = new ArrayList<>();
        for(int i=0;i<shortestChain.size()-1;i++){
            ReferralConnectionChainRecord connectionChainRecord = new ReferralConnectionChainRecord();
            connectionChainRecord.setNextUserId(shortestChain.get(i+1));
            connectionChainRecord.setRecomUserId(shortestChain.get(i));
            // 这里要保证一条链路生成时间是相同的，在连连看连接时需要获取该原始路径
            connectionChainRecord.setCreateTime(current);
            connectionChainRecord.setUpdateTime(current);
            connectionChainRecords.add(connectionChainRecord);
        }
        connectionChainDao.insertRecords(connectionChainRecords);
        int rootParentId = connectionChainRecords.get(0).getId();
        // 填充parentId, rootParentId
        fillParentChainId(connectionChainRecords, rootParentId);
        return rootParentId;
    }

    private ReferralConnectionLogRecord doCreateConnectionLogRecord(int rootChainId, ReferralInviteInfo inviteInfo, int degree) {
        ReferralConnectionLogRecord connectionLogRecord = new ReferralConnectionLogRecord();
        connectionLogRecord.setRootChainId(rootChainId);
        connectionLogRecord.setRootUserId(inviteInfo.getUserId());
        connectionLogRecord.setPositionId(inviteInfo.getPid());
        connectionLogRecord.setEndUserId(inviteInfo.getEndUserId());
        connectionLogRecord.setCompanyId(inviteInfo.getCompanyId());
        connectionLogRecord.setState((byte)0);
        connectionLogRecord.setDegree((byte)(degree - 1));
        connectionLogRecord = connectionLogDao.insertRecord(connectionLogRecord);
        return connectionLogRecord;
    }

    /**
     * neo4j获取员工最短路径，并按度数排序
     * @param rootUserId 链路开始用户id
     * @param endUserId 链路结束用户id
     * @author  cjm
     * @date  2018/12/13
     * @return 返回最短路径
     */
    private List<Integer> getShortestChain(int rootUserId, int endUserId) {
        return new ArrayList<>();
    }

    /**
     * 邀请投递发送消息模板
     * @param  inviteInfo 邀请投递相关参数
     * @author  cjm
     * @date  2018/12/13
     * @return  是否发送成功
     */
    private boolean sendInviteTemplate(ReferralInviteInfo inviteInfo) {
        try{
            UserEmployeeDO employee = employeeEntity.getCompanyEmployee(inviteInfo.getUserId(), inviteInfo.getCompanyId());
            JobPositionDO jobPosition = positionDao.getJobPositionById(inviteInfo.getPid());
            HrCompanyDO hrCompanyDO = companyDao.getCompanyById(inviteInfo.getCompanyId());
            InviteTemplateVO inviteTemplateVO = initTemplateVO(jobPosition, hrCompanyDO, employee);
            String redirectUrl = environment.getProperty("template.referral.show.url");
            String requestUrl = environment.getProperty("template.referral.invite.url");
            templateHelper.sendInviteTemplate(inviteInfo.getEndUserId(), inviteInfo.getCompanyId(), inviteTemplateVO, requestUrl, redirectUrl);
            return true;
        }catch (Exception e){
            logger.info("发送邀请模板消息errmsg:{}", e.getMessage());
        }
        return false;
    }

    private InviteTemplateVO initTemplateVO(JobPositionDO jobPosition, HrCompanyDO hrCompanyDO, UserEmployeeDO employee) {
        InviteTemplateVO inviteTemplateVO = new InviteTemplateVO();
        DateTime dateTime = DateTime.now();
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String current = dateFormat.format(dateTime.toDate());
        String title = "内推大使【%s】邀请您投递简历，不要错过这个内推机会哦~";
        String templateTile = String.format(title, employee.getCname());
        inviteTemplateVO.setFirst(templateTile);
        inviteTemplateVO.setKeyWord1(jobPosition.getTitle());
        inviteTemplateVO.setKeyWord2(hrCompanyDO.getName());
        inviteTemplateVO.setKeyWord3(current);
        inviteTemplateVO.setRemark("点击查看职位详情并投递简历");
        inviteTemplateVO.setTemplateId(87);
        return inviteTemplateVO;
    }



    private void getUnEmployeeUserIds(Set<Integer> beRecomUserIds) {
        // 查找浏览人中的员工
        List<UserEmployeeDO> userEmployeeDOS = userEmployeeDao.getUserEmployeeForidList(beRecomUserIds);
        // 获取员工的userId
        Set<Integer> userEmployeeIds = userEmployeeDOS.stream().map(UserEmployeeDO::getId).collect(Collectors.toSet());
        // 将员工过滤掉
        beRecomUserIds.removeAll(userEmployeeIds);
    }

    private JSONObject doCreateCard(ReferralCardInfo cardInfo, CandidatePositionDO candidatePositionDO, JobPositionDO jobPosition,
                                    List<CandidateShareChainDO> shareChainDOS, Map<Integer, UserWxUserDO> idUserMap) {
        JSONObject card = new JSONObject();
        // 查找来自【】转发
        int recomUser = getRecomUser(candidatePositionDO, jobPosition, shareChainDOS);
        // 组装该粉丝信息
        RadarUserInfo user = doInitUser(idUserMap, cardInfo.getUser_id(), candidatePositionDO.getUserId());
        List<RadarUserInfo> chain = new ArrayList<>();
        // 链路第一个放员工信息
        chain.add(doInitEmployee(idUserMap, cardInfo));
        // 递归找到转发链路的所有被推荐人id
        List<Integer> chainBeRecomIds = getChainIdsByRecurrence(shareChainDOS, cardInfo, candidatePositionDO);
        for(Integer beRecomId : chainBeRecomIds){
            UserWxUserDO userWxUserDO = idUserMap.get(beRecomId);
            if(chain.size() == (CHAIN_LIMIT-1)){
                chain.add(user);
                break;
            }
            // 链路中的用户信息
            RadarUserInfo userInfo = new RadarUserInfo();
            chain.add(userInfo.initFromUserWxUser(userWxUserDO));
            // 如果链路人数不到限制时，已经触及到邀请的粉丝，则退出循环
            if(beRecomId == candidatePositionDO.getUserId()){
                break;
            }
        }
        card.put("position", doInitPosition(jobPosition, candidatePositionDO));
        card.put("user", user);
        card.put("recom_user", idUserMap.get(recomUser).getNickname());
        card.put("chain", chain);
        return card;
    }

    private List<Integer> getChainIdsByRecurrence(List<CandidateShareChainDO> shareChainDOS, ReferralCardInfo cardInfo, CandidatePositionDO candidatePositionDO) {
        List<Integer> chainBeRecomIds = new ArrayList<>();
        int parentId = 0;
        int depth = 0;
        int presenteeUserId = 0;
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getRootRecomUserId() == cardInfo.getUser_id() && shareChainDO.getPresenteeUserId() == candidatePositionDO.getUserId()){
                presenteeUserId = shareChainDO.getPresenteeUserId();
                if(depth < shareChainDO.getDepth()) {
                    depth = shareChainDO.getDepth();
                    parentId = shareChainDO.getParentId();
                }
            }
        }
        chainBeRecomIds.add(presenteeUserId);
        chainBeRecomIds = getChainIdsByRecurrence(parentId, chainBeRecomIds, shareChainDOS);
        // 由于是反向查找的，需要倒序将degree小的放前边
        Collections.reverse(chainBeRecomIds);
        return chainBeRecomIds;
    }

    private List<Integer> getChainIdsByRecurrence(int parentId, List<Integer> chainIds, List<CandidateShareChainDO> shareChainDOS) {
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getId() == parentId){
                chainIds.add(shareChainDO.getPresenteeUserId());
                if(shareChainDO.getParentId() == 0){
                    return chainIds;
                }else {
                    return getChainIdsByRecurrence(shareChainDO.getParentId(), chainIds, shareChainDOS);
                }
            }
        }
        return chainIds;
    }

    private int getRecomUser(CandidatePositionDO candidatePosition, JobPositionDO jobPosition, List<CandidateShareChainDO> shareChainDOS) {
        int userId = 0;
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getPresenteeUserId() == candidatePosition.getUserId() && jobPosition.getId() == shareChainDO.getPositionId()){
                userId = shareChainDO.getRecomUserId();
            }
        }
        return userId;
    }

    private RadarUserInfo doInitEmployee(Map<Integer, UserWxUserDO> idUserMap, ReferralCardInfo cardInfo) {
        RadarUserInfo employee = new RadarUserInfo();
        UserWxUserDO wxUserDO = idUserMap.get(cardInfo.getUser_id());
        return employee.initFromUserWxUser(wxUserDO);
    }

    /**
     * 组装链路结束的用户信息
     * @author  cjm
     * @date  2018/12/10
     * @return JSONObject
     */
    private RadarUserInfo doInitUser(Map<Integer, UserWxUserDO> idUserMap, int rootUserId, int endUserId) {
        RadarUserInfo user = new RadarUserInfo();
        UserWxUserDO userWxUserDO = idUserMap.get(endUserId);
        user.initFromUserWxUser(userWxUserDO);
        // todo neo4j查询
        List<Integer> shortestChain = getShortestChain(rootUserId, endUserId);
        user.setDegree(shortestChain.size());
        return user;
    }

    private JSONObject doInitPosition(JobPositionDO jobPosition, CandidatePositionDO candidatePositionDO) {
        JSONObject position = new JSONObject();
        position.put("title", jobPosition.getTitle());
        position.put("pid", jobPosition.getId());
        position.put("pv", candidatePositionDO.getViewNumber());
        return position;
    }

    @Override
    public void addReferralSeekRecommend(int userId, int postUserId, int positionId, int origin) throws CommonException {
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
        record.setPresenteeUserId(userId);
        record.setPositionId(positionId);
        record.setOrigin(origin);
        int i =0;
        int id = 0;
        while (i<3 && id ==0){
            id = recommendDao.insertIfNotExist(record);
            i++;
        }
        if(id<=0){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_FAIL;
        }
        templateSender.publishSeekReferralEvent(postUserId, id, userId, positionId);
    }

    @Override
    public ContactPushInfo fetchSeekRecommend(int referralId, int postUserId) throws CommonException {
        ContactPushInfo info = new ContactPushInfo();
        ReferralSeekRecommendRecord record = recommendDao.fetchByIdAndPostUserId(referralId, postUserId);
        if(record == null){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_NULL;
        }
        UserUserRecord user = userDao.getUserById(record.getPresenteeUserId());
        if(user == null){
            throw UserAccountException.NODATA_EXCEPTION;
        }
        info.setUserId(user.getId());
        info.setUsername(user.getNickname());
        if(StringUtils.isNullOrEmpty(user.getNickname())){
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
        return info;
    }

    @Transactional
    @Override
    public void employeeReferralReason(int userId, int positionId, int postUserId, int referralId, List<String> referralReasons,
                                       byte relationship, String recomReasonText) throws CommonException {

        JobPositionDO positionDO = positionDao.getJobPositionById(positionId);
        UserUserDO user = userDao.getUser(userId);
        ReferralSeekRecommendRecord recommendRecord = recommendDao.fetchById(referralId);
        if(positionDO == null || positionDO.getStatus() != 0 || recommendRecord ==null || user == null){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        if(recommendRecord.getPostUserId()!= postUserId){
            throw UserAccountException.REFERRAL_SEEK_RECOMMEND_NULL;
        }
        if(!employeeEntity.isEmployee(recommendRecord.getPostUserId(), positionDO.getCompanyId())){
            throw UserAccountException.PERMISSION_DENIED;
        }
        UserEmployeeDO employee = employeeEntity.getCompanyEmployee(recommendRecord.getPostUserId(), positionDO.getCompanyId());
        int origin = recommendRecord.getOrigin()==1 ? ApplicationSource.SEEK_REFERRAL.getValue():
                ApplicationSource.INVITE_REFERRAL.getValue();
        int applicationId = 0;
        try {
            applicationId = createJobApplication(userId, positionDO.getCompanyId(), positionId, user.getName(), origin, recommendRecord.getPostUserId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw CommonException.PROGRAM_EXCEPTION;
        }
        if(applicationId > 0) {
            recommendDao.updateReferralSeekRecommendRecordForAppId(referralId, applicationId);
            referralEntity.logReferralOperation(positionId, applicationId, referralReasons, String.valueOf(user.getMobile()), employee, userId, relationship, recomReasonText);
            sender.addRecommandReward(employee, userId, applicationId, positionId);
            sender.publishReferralEvaluateEvent(referralId, userId, positionId, applicationId, employee.getId());
        }

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
        }
        return applicationId;
    }
}
