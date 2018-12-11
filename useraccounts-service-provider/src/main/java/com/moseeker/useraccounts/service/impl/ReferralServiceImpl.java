package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionShareRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.dictdb.DictReferralEvaluateDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.referraldb.CustomReferralEmployeeBonusDao;
import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictReferralEvaluateRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.PageUtil;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
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
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.ReferralService;
import com.moseeker.useraccounts.service.impl.activity.Activity;
import com.moseeker.useraccounts.service.impl.activity.ActivityType;
import com.moseeker.useraccounts.service.impl.biztools.HBBizTool;
import com.moseeker.useraccounts.service.impl.vo.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
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

    @Autowired
    private UserWxUserDao wxUserDao;

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
    private JobPositionDao positionDao;

    @Autowired
    private CandidateShareChainDao shareChainDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private CandidatePositionShareRecordDao positionShareRecordDao;

    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private Environment environment;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateDao;

    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;

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
        Long timstamp = cardInfo.getTimestamp();
        Timestamp tenMinite = new Timestamp(timstamp);
        Timestamp beforeTenMinite = new Timestamp(timstamp - 1000 * 60 * 10);
        // 获取指定时间前十分钟内的职位浏览人
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getRadarCards(cardInfo.getUserId(), beforeTenMinite, tenMinite);
        // 获取浏览人的userId
        Set<Integer> beRecomUserIds = shareChainDOS.stream().map(CandidateShareChainDO::getPresenteeUserId).collect(Collectors.toSet());
        // 将员工过滤掉，获取职位浏览人中非员工的userId
        beRecomUserIds = getUnEmployeeUserIds(beRecomUserIds);
        // 后边需要用到员工头像和昵称，在这里一并查出来
        beRecomUserIds.add(cardInfo.getUserId());
        // 将过滤后的员工id对应员工信息，用于后续数据组装
        List<UserWxUserDO> userUserDOS = userWxUserDao.getWXUserMapByUserIds(beRecomUserIds);
        Map<Integer, UserWxUserDO> idUserMap = userUserDOS.stream().collect(Collectors.toMap(userWxUserDO->(int)userWxUserDO.getId(), userWxUserDO->userWxUserDO));
        // 通过职位浏览人idSet和10分钟时间段获取转发的信息
        List<CandidatePositionShareRecordDO> positionShareDOS = positionShareRecordDao.fetchPositionShareByUserIds(beRecomUserIds, cardInfo.getUserId(), beforeTenMinite, tenMinite);
        List<Integer> positionIds = positionShareDOS.stream().map(positionShareDO -> (int)positionShareDO.getPositionId()).collect(Collectors.toList());
        List<JobPositionDO> jobPositions = positionDao.getPositionListWithoutStatus(positionIds);
        // 将职位id和职位映射，用于后续数据组装
        Map<Integer, JobPositionDO> idPositionMap = jobPositions.stream().collect(Collectors.toMap(JobPositionDO::getId, jobPositionDO -> jobPositionDO));
        //
        List<CandidatePositionDO> candidatePositionDOS = candidatePositionDao.fetchRecentViewedByUserIdsAndPids(beRecomUserIds, positionIds);
        List<JSONObject> cards = new ArrayList<>();
        int startIndex = (cardInfo.getPageNumber()-1)*cardInfo.getPageSize();
        for(int i = startIndex; i < candidatePositionDOS.size() && i < cardInfo.getPageNumber()*cardInfo.getPageSize();i++){
            CandidatePositionDO candidatePositionDO = candidatePositionDOS.get(i);
            if(candidatePositionDO != null){
                // 构造单个职位浏览人的卡片
                cards.add(doCreateCard(cardInfo, candidatePositionDO, idPositionMap.get(candidatePositionDO.getPositionId()), shareChainDOS, idUserMap));
            }
        }
        return JSON.toJSONString(cards);
    }


    @Override
    public String inviteApplication(ReferralInviteInfo inviteInfo) {
        // 发送消息模板
        boolean isSent = sendInviteTemplate();
        // 查询最短路径

        // 生成人脉连连看链路

        // 修改shareChain处理状态
        return null;
    }

    private boolean sendInviteTemplate(int sysUserId, int positionId, int endUserId, int companyId) throws ConnectException, BIZException {
        UserEmployeeDO employee = employeeEntity.getCompanyEmployee(sysUserId, companyId);
        JobPositionDO jobPosition = positionDao.getJobPositionById(positionId);
        HrCompanyDO hrCompanyDO = companyDao.getCompanyById(companyId);

        InviteTemplateVO inviteTemplateVO = initTemplateVO(jobPosition, hrCompanyDO, employee);
        sendInviteTemplate(endUserId, companyId, inviteTemplateVO);
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

    private void sendInviteTemplate(int endUserId, int companyId, InviteTemplateVO inviteTemplateVO) throws ConnectException, BIZException {
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(), companyId).buildQuery());
        UserWxUserRecord userWxUserRecord = userWxUserDao.getWxUserByUserIdAndWechatId(endUserId, hrWxWechatDO.getId());
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO, inviteTemplateVO.getTemplateId());
        String requestUrl = environment.getProperty("template.referral.invite.url");
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(inviteTemplateVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", userWxUserRecord.getOpenid());
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", );
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        // 插入模板消息发送记录
        insertLogWxMessageRecord(inviteTemplateVO.getTemplateId(), hrWxWechatDO.getId(), requestMap);
    }

    private void insertLogWxMessageRecord(int templateId, int wechatId, Map<String, Object> templateValueMap) {
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
        messageRecord.setTemplateId(templateId);
        messageRecord.setOpenId(String.valueOf(templateValueMap.get("touser")));
        messageRecord.setAccessToken(String.valueOf(templateValueMap.get("accessToken")));
        messageRecord.setJsondata(JSON.toJSONString(templateValueMap.get("data")));
        messageRecord.setUrl(String.valueOf(templateValueMap.get("url")));
        messageRecord.setWechatId(wechatId);
        messageRecord.setTopcolor(String.valueOf(templateValueMap.get("topcolor")));
        Map<String, Object> response = (Map<String, Object>)templateValueMap.get("response");
        String errcode = String.valueOf(response.get("errcode"));
        if("0".equals(errcode)){
            messageRecord.setSendstatus("success");
            messageRecord.setMsgid(Long.parseLong(String.valueOf(response.get("msgid"))));
        }else {
            messageRecord.setSendstatus("failed");
        }
        messageRecord.setSendtime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setUpdatetime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setSendtype(0);
        messageRecord.setErrcode(Integer.parseInt(errcode));
        messageRecord.setErrmsg(String.valueOf(response.get("errmsg")));
        wxMessageRecordDao.addData(messageRecord);
    }

    private Map<String,TemplateBaseVO> createDataMap(InviteTemplateVO inviteTemplateVO) {
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        TemplateBaseVO first = createTplVO(inviteTemplateVO.getFirst());
        TemplateBaseVO keyWord1 = createTplVO(inviteTemplateVO.getKeyWord1());
        TemplateBaseVO keyWord2 = createTplVO(inviteTemplateVO.getKeyWord2());
        TemplateBaseVO keyWord3 = createTplVO(inviteTemplateVO.getKeyWord3());
        TemplateBaseVO remark = createTplVO(inviteTemplateVO.getRemark());
        dataMap.put("first", first);
        dataMap.put("keyword1", keyWord1);
        dataMap.put("keyword2", keyWord2);
        dataMap.put("keyword3", keyWord3);
        dataMap.put("remark", remark);
        return dataMap;
    }

    private TemplateBaseVO createTplVO(String value){
        return createTplVO(COLOR, value);
    }

    private TemplateBaseVO createTplVO(String color, String value){
        TemplateBaseVO templateBaseVO = new TemplateBaseVO();
        templateBaseVO.setColor(color);
        templateBaseVO.setValue(value);
        return templateBaseVO;
    }

    private HrWxTemplateMessageDO getHrWxTemplateMessageByWechatIdAndSysTemplateId(HrWxWechatDO hrWxWechatDO, int sysTemplateId) throws BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", sysTemplateId).and("disable","0").buildQuery());
        if(hrWxTemplateMessageDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_TEMPLATE_SWITCH_CLOSE);
        }
        return hrWxTemplateMessageDO;
    }

    @Override
    public void ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) {
        Long timstamp = ignoreInfo.getTimestamp();
        Timestamp tenMinite = new Timestamp(timstamp);
        Timestamp beforeTenMinite = new Timestamp(timstamp - 1000 * 60 * 10);
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getRadarCards(ignoreInfo.getUserId(), beforeTenMinite, tenMinite);
        int parentId = 0;
        for(CandidateShareChainDO candidateShareChainDO : shareChainDOS){
            if(candidateShareChainDO.getPresenteeUserId() == ignoreInfo.getEndUserId()){
                parentId = candidateShareChainDO.getParentId();
            }
        }
        List<RadarCard> radarCards = new ArrayList<>();
        for(CandidateShareChainDO candidateShareChainDO : shareChainDOS) {
            // 由于目前数据库中的数据有重复的，如果关键信息相同，则跳过
            RadarCard radarCard = new RadarCard();
            radarCard.cloneFromCandidateShareChainDO(candidateShareChainDO);
            radarCard.setId(candidateShareChainDO.getId());
            radarCards.add(radarCard);
        }
        int chainId = findByPid(shareChainDOS, ignoreInfo.getEndUserId(), parentId);
        List<RadarCard> updateCards = new ArrayList<>();
        RadarCard destCard = null;
        for(RadarCard radarCard : radarCards){
            if(radarCard.getId() == chainId){
                destCard = radarCard;
                break;
            }
        }
        if(destCard == null){
            return;
        }
        for(RadarCard radarCard1 : radarCards){
            if(radarCard1.equals(destCard)){
                updateCards.add(radarCard1);
            }
        }
        List<Integer> chainIds = updateCards.stream().map(RadarCard::getId).collect(Collectors.toList());
        shareChainDao.updateTypeByIds(chainIds, 1);
    }

    private int findByPid(List<CandidateShareChainDO> shareChainDOS, int endUserId, int parentId) {
        int id = 0;
        for(CandidateShareChainDO candidateShareChainDO : shareChainDOS){
            if(candidateShareChainDO.getPresenteeUserId() == endUserId){
                parentId = candidateShareChainDO.getParentId();
                if(parentId != 0){
                    return findByPid(shareChainDOS, endUserId, parentId);
                }else{
                    id = candidateShareChainDO.getId();
                    break;
                }
            }
        }
        return id;
    }

    @Override
    public String connectRadar(ConnectRadarInfo radarInfo) {
        return null;
    }

    private Set<Integer> getUnEmployeeUserIds(Set<Integer> beRecomUserIds) {
        // 查找浏览人中的员工
        List<UserEmployeeDO> userEmployeeDOS = userEmployeeDao.getUserEmployeeForidList(beRecomUserIds);
        // 获取员工的userId
        Set<Integer> userEmployeeIds = userEmployeeDOS.stream().map(UserEmployeeDO::getId).collect(Collectors.toSet());
        // 将员工过滤掉
        beRecomUserIds.removeAll(userEmployeeIds);
        return beRecomUserIds;
    }

    private JSONObject doCreateCard(ReferralCardInfo cardInfo, CandidatePositionDO candidatePositionDO, JobPositionDO jobPosition,
                                    List<CandidateShareChainDO> shareChainDOS, Map<Integer, UserWxUserDO> idUserMap) {
        JSONObject card = new JSONObject();

        card.put("position", doInitPosition(jobPosition, candidatePositionDO));
        JSONObject user = doInitUser(idUserMap, candidatePositionDO);
        card.put("user", user);
        List<JSONObject> chain = new ArrayList<>();
        chain.add(doInitEmployee(idUserMap, cardInfo));
        List<RadarCard> radarCards = new ArrayList<>();
        for(CandidateShareChainDO candidateShareChainDO : shareChainDOS){
            // 由于目前数据库中的数据有重复的，如果关键信息相同，则跳过
            RadarCard radarCard = new RadarCard();
            radarCard.cloneFromCandidateShareChainDO(candidateShareChainDO);
            if(radarCards.contains(radarCard)){
                continue;
            }else {
                radarCards.add(radarCard);
            }
            if(candidateShareChainDO.getPositionId() == jobPosition.getId() && candidateShareChainDO.getRootRecomUserId() == cardInfo.getUserId()){
                UserWxUserDO userWxUserDO = idUserMap.get(candidateShareChainDO.getPresenteeUserId());
                if(chain.size() == (CHAIN_LIMIT-1)){
                    if(chain.get(CHAIN_LIMIT-2).getString("uid").equals(user.getString("uid"))){
                        break;
                    }
                    chain.add(user);
                    break;
                }
                // 链路中的用户信息
                JSONObject presenteeUser = new JSONObject();
                presenteeUser.put("nickname", userWxUserDO.getNickname());
                presenteeUser.put("avatar", userWxUserDO.getHeadimgurl());
                presenteeUser.put("uid", userWxUserDO.getSysuserId());
                chain.add(presenteeUser);
            }
        }
        card.put("chain", chain);
        return card;
    }

    private JSONObject doInitEmployee(Map<Integer, UserWxUserDO> idUserMap, ReferralCardInfo cardInfo) {
        JSONObject employee = new JSONObject();
        UserWxUserDO wxUserDO = idUserMap.get(cardInfo.getUserId());
        employee.put("nickname", wxUserDO.getNickname());
        employee.put("avatar", wxUserDO.getHeadimgurl());
        return employee;
    }

    /**
     * 组装链路结束的用户信息
     * @author  cjm
     * @date  2018/12/10
     * @return JSONObject
     */
    private JSONObject doInitUser(Map<Integer, UserWxUserDO> idUserMap, CandidatePositionDO candidatePositionDO) {
        JSONObject user = new JSONObject();
        UserWxUserDO userWxUserDO = idUserMap.get(candidatePositionDO.getUserId());
        user.put("nickname", userWxUserDO.getNickname());
        user.put("avatar", userWxUserDO.getHeadimgurl());
        user.put("uid", userWxUserDO.getSysuserId());
        // todo neo4j查询
        user.put("degree", 0);
        return user;
    }

    private JSONObject doInitPosition(JobPositionDO jobPosition, CandidatePositionDO candidatePositionDO) {
        JSONObject position = new JSONObject();
        position.put("title", jobPosition.getTitle());
        position.put("pid", jobPosition.getId());
        position.put("pv", candidatePositionDO.getViewNumber());
        return position;
    }

}
