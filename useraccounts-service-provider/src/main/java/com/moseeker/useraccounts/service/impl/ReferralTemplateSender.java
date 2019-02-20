package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateTemplateShareChainDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.referraldb.ReferralSeekRecommendDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import static com.moseeker.common.constants.Constant.EMPLOYEE_REFERRAL_EVALUATE;
import static com.moseeker.common.constants.Constant.EMPLOYEE_SEEK_REFERRAL_TEMPLATE;
import static com.moseeker.common.constants.Constant.REFERRAL_RADAR_TEMPLATE;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.biz.RadarUtils;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateTemplateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.useraccounts.service.impl.vo.InviteTemplateVO;
import com.moseeker.useraccounts.service.impl.vo.TemplateBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 目前该类中发消息模板有两种方式，一种是异步的不关心发送结果，一种是同步的需要知道是否发送成功
 * Created by moseeker on 2018/12/10.
 */
@Component
public class ReferralTemplateSender {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SEEK_REFERRAL_EXCHNAGE = "referral_seek_exchange";

    private static final String REFERRAL_RADAR_SAVE_TEMP = "referral_radar_exchange";

    private static final Integer TEN_MINUTE = 3*60*1000;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    JobApplicationDao applicationDao;

    @Autowired
    HrOperationRecordDao operationRecordDao;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateDao;

    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;

    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private CandidateTemplateShareChainDao templateShareChainDao;

    @Autowired
    private CandidateShareChainDao shareChainDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private ReferralSeekRecommendDao seekRecommendDao;

    ScheduledThread scheduledThread = ScheduledThread.Instance;

    public void publishSeekReferralEvent(int postUserId, int referralId, int userId, int positionId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position_id", positionId);
        jsonObject.put("post_user_id", postUserId);
        jsonObject.put("referral_id", referralId);
        jsonObject.put("user_id", userId);
        logger.info("publishSeekReferralEvent json:{}", jsonObject);
        amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                EMPLOYEE_SEEK_REFERRAL_TEMPLATE, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());
    }

    public void publishReferralEvaluateEvent( int referralId, int userId, int positionId, int applicationId, int employeeId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position_id", positionId);
        jsonObject.put("referral_id", referralId);
        jsonObject.put("application_id", applicationId);
        jsonObject.put("user_id", userId);
        jsonObject.put("employee_id", employeeId);
        logger.info("publishReferralEvaluateEvent json:{}", jsonObject);
        amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                EMPLOYEE_REFERRAL_EVALUATE, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());
    }

    public void addRecommandReward(UserEmployeeDO employeeDO, int userId, int applicationId,
                                    int positionId) throws CommonException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeId", employeeDO.getId());
            jsonObject.put("companyId", employeeDO.getCompanyId());
            jsonObject.put("positionId", positionId);
            jsonObject.put("berecomUserId", userId);
            jsonObject.put("applicationId", applicationId);
            jsonObject.put("appid", AppId.APPID_ALPHADOG.getValue());
            MessageProperties mp = new MessageProperties();
            mp.setAppId(String.valueOf(AppId.APPID_ALPHADOG.getValue()));
            mp.setReceivedExchange("user_action_topic_exchange");
            jsonObject.put("templateId", Constant.RECRUIT_STATUS_EMPLOYEE_RECOMMEND);
            amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
                    MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.APPLICATION_REFERRAL_REWARD_CREATE_FAILED;
        }

    }

    public void sendTenMinuteTemplate(ReferralCardInfo cardInfo) {
        scheduledThread.startTast(() -> sendTenMinuteTemplateIfNecessary(cardInfo), TEN_MINUTE);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendTenMinuteTemplateIfNecessary(ReferralCardInfo cardInfo) {
        long timestamp = System.currentTimeMillis();
        cardInfo.setTimestamp(timestamp);
        Timestamp tenMinite = new Timestamp(cardInfo.getTimestamp());
        Timestamp beforeTenMinite = new Timestamp(cardInfo.getTimestamp() - TEN_MINUTE);
        // 获取指定时间前十分钟内的职位浏览人
        List<CandidateShareChainDO> factShareChainDOS = shareChainDao.getRadarCards(cardInfo.getUserId(), beforeTenMinite, tenMinite);
        List<CandidateShareChainDO> shareChainDOS = getCompleteShareChains(cardInfo.getUserId(), factShareChainDOS);
        List<CandidateTemplateShareChainDO> templateShareChainDOS = new ArrayList<>();
        List<Integer> factShareChainIds = factShareChainDOS.stream().map(CandidateShareChainDO::getId).distinct().collect(Collectors.toList());
        shareChainDOS.forEach(candidateShareChainDO -> templateShareChainDOS.add(initTemplateShareChain(cardInfo.getTimestamp(), candidateShareChainDO, factShareChainIds)));
        factShareChainDOS.removeIf(record -> record.getType() != 0);
        //
        Set<Integer> userIds = factShareChainDOS.stream().map(CandidateShareChainDO::getPresenteeUserId).collect(Collectors.toSet());
        List<Integer> positionIds = factShareChainDOS.stream().map(CandidateShareChainDO::getPositionId).distinct().collect(Collectors.toList());
        List<JobApplicationDO> jobApplicationDOS = applicationDao.getApplicationsByApplierAndPosition(positionIds, new ArrayList<>(userIds));
        userIds = filterAppliedUsers(jobApplicationDOS, factShareChainDOS);
        int visitNum = userIds.size();
        logger.info("======sendTenMinuteTemplateIfNecessary, visitNum:{}", visitNum);
        if(visitNum > 0){
            UserEmployeeDO employee = employeeEntity.getCompanyEmployee(cardInfo.getUserId(), cardInfo.getCompanyId());
            templateShareChainDao.addAllData(templateShareChainDOS);
            List<Integer> newPositionIds = new ArrayList<>();
            if(positionIds.size() > 2){
                newPositionIds.add(positionIds.get(0));
                newPositionIds.add(positionIds.get(1));
            }else {
                newPositionIds = positionIds;
            }
            JSONObject request = new JSONObject();
            request.put("pids", JSON.toJSONString(newPositionIds));
            request.put("employeeId", employee.getId());
            request.put("visitNum", visitNum);
            request.put("companyId", cardInfo.getCompanyId());
            request.put("timestamp", cardInfo.getTimestamp());
            logger.info("=======tenminuteTemplate:{}", JSON.toJSONString(request));
            amqpTemplate.sendAndReceive(REFERRAL_RADAR_SAVE_TEMP,
                    REFERRAL_RADAR_TEMPLATE, MessageBuilder.withBody(request.toJSONString().getBytes())
                            .build());
        }
    }

    private Set<Integer> filterAppliedUsers(List<JobApplicationDO> jobApplicationDOS, List<CandidateShareChainDO> factShareChainDOS) {
        Set<Integer> userIds = new HashSet<>();
        for(CandidateShareChainDO candidateShareChainDO : factShareChainDOS){
            boolean flag = true;
            for(int i=0;i<jobApplicationDOS.size()&&flag;i++){
                JobApplicationDO jobApplicationDO = jobApplicationDOS.get(i);
                if(candidateShareChainDO.getPositionId() == jobApplicationDO.getPositionId()
                && candidateShareChainDO.getPresenteeUserId() == jobApplicationDO.getApplierId()){
                    flag = false;
                }
            }
            if(flag){
                userIds.add(candidateShareChainDO.getPresenteeUserId());
            }
        }
        return userIds;
    }

    private List<CandidateShareChainDO> getCompleteShareChains(int userId, List<CandidateShareChainDO> currentShareChainDOS) {
        List<Integer> pids = currentShareChainDOS.stream().map(CandidateShareChainDO::getPositionId).distinct().collect(Collectors.toList());
        List<CandidateShareChainDO> allShareChains = shareChainDao.getShareChainsByUserIdAndPosition(userId, pids);
        List<CandidateShareChainDO> returnShareChains = new ArrayList<>();

        for(CandidateShareChainDO current : currentShareChainDOS){
            boolean flag = true;
            for(int i=0;i<allShareChains.size()&&flag;i++){
                CandidateShareChainDO complete = allShareChains.get(i);
                // 点击人和职位id都相等时
                if(current.getPositionId() == complete.getPositionId() && current.getPresenteeUserId() == complete.getPresenteeUserId()){
                    flag = false;
                    if(!RadarUtils.contains(current, returnShareChains)){
                        // 如果不存在，认为该链路是父链路
                        returnShareChains.add(complete);
                    }
                    returnShareChains = RadarUtils.getCompleteShareChainsByRecurrence(complete.getParentId(), allShareChains, returnShareChains);
                }
            }
        }
        return returnShareChains;
    }

    private CandidateTemplateShareChainDO initTemplateShareChain(long timestamp, CandidateShareChainDO candidateShareChainDO, List<Integer> factShareChainIds) {
        CandidateTemplateShareChainDO templateShareChainDO = new CandidateTemplateShareChainDO();
        templateShareChainDO.setDepth((byte)candidateShareChainDO.getDepth());
        templateShareChainDO.setChainId(candidateShareChainDO.getId());
        templateShareChainDO.setParentId(candidateShareChainDO.getParentId());
        templateShareChainDO.setPositionId(candidateShareChainDO.getPositionId());
        templateShareChainDO.setPresenteeUserId(candidateShareChainDO.getPresenteeUserId());
        templateShareChainDO.setType(candidateShareChainDO.getType());
        templateShareChainDO.setSendTime(timestamp);
        templateShareChainDO.setRoot2UserId(candidateShareChainDO.getRoot2RecomUserId());
        templateShareChainDO.setRecomUserId(candidateShareChainDO.getRecomUserId());
        templateShareChainDO.setRootUserId(candidateShareChainDO.getRootRecomUserId());
        if(factShareChainIds.contains(candidateShareChainDO.getId())){
            templateShareChainDO.setStatus(0);
        }else {
            templateShareChainDO.setStatus(1);
        }
        return templateShareChainDO;
    }


    /**
     * 邀请投递时发送消息模板
     * @param   hrWxWechatDO 公众号信息
     * @param   openId 要发送消息模板人的openid
     * @param   templateVO 模板填充属性
     * @param   requestUrl 消息模板请求url
     * @param   redirectUrl 点击消息模板转向的地址
     * @author  cjm
     * @date  2018/12/19
     * @return   返回微信响应结果
     */
    public Map<String, Object> sendTemplate(HrWxWechatDO hrWxWechatDO, String openId, JSONObject templateVO, String requestUrl, String redirectUrl) throws ConnectException, BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO, templateVO.getIntValue("templateId"));
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(templateVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", openId);
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", redirectUrl);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        // 插入模板消息发送记录
        wxMessageRecordDao.insertLogWxMessageRecord(templateVO.getIntValue("templateId"), hrWxWechatDO.getId(), requestMap);
        return params;
    }

    private Map<String,TemplateBaseVO> createDataMap(JSONObject templateVO) {
        ConfigSysTemplateMessageLibraryRecord record = templateDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(templateVO.getIntValue("templateId"));
        JSONObject color = JSONObject.parseObject(record.getColorJson());
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        String first = templateVO.getString("first");
        if(StringUtils.isNotNullOrEmpty(first)){
            dataMap.put("first", createTplVO(color.getString("first"), first));
        }
        String keyWord1 = templateVO.getString("keyWord1");
        if(StringUtils.isNotNullOrEmpty(keyWord1)){
            dataMap.put("keyword1", createTplVO(color.getString("keyWord1"), keyWord1));
        }
        String keyWord2 = templateVO.getString("keyWord2");
        if(StringUtils.isNotNullOrEmpty(keyWord2)){
            dataMap.put("keyword2", createTplVO(color.getString("keyWord2"), keyWord2));
        }
        String keyWord3 = templateVO.getString("keyWord3");
        if(StringUtils.isNotNullOrEmpty(keyWord3)){
            dataMap.put("keyword3", createTplVO(color.getString("keyWord3"), keyWord3));
        }
        String keyWord4 = templateVO.getString("keyWord4");
        if(StringUtils.isNotNullOrEmpty(keyWord4)){
            dataMap.put("keyword4", createTplVO(color.getString("keyWord4"), keyWord4));
        }
        String remark = templateVO.getString("remark");
        if(StringUtils.isNotNullOrEmpty(remark)){
            dataMap.put("remark", createTplVO(color.getString("remark"), remark));
        }
        return dataMap;
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
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        return hrWxTemplateMessageDO;
    }
}
