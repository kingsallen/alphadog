package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.referraldb.ReferralConnectionChainDao;
import com.moseeker.baseorm.dao.referraldb.ReferralConnectionLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
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
import com.moseeker.useraccounts.constant.TemplateConstant;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.Neo4jService;
import com.moseeker.useraccounts.service.ReferralRadarService;
import com.moseeker.useraccounts.service.constant.RadarStateEnum;
import com.moseeker.useraccounts.service.impl.vo.InviteTemplateVO;
import com.moseeker.useraccounts.service.impl.vo.RadarConnectResult;
import com.moseeker.useraccounts.service.impl.vo.RadarUserInfo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-12-19 10:22
 **/
@Service
public class ReferralRadarServiceImpl implements ReferralRadarService {

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private CandidateShareChainDao shareChainDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private ReferralTemplateSender templateHelper;

    @Autowired
    private ReferralConnectionChainDao connectionChainDao;

    @Autowired
    private ReferralConnectionLogDao connectionLogDao;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    private Neo4jService neo4jService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Integer CHAIN_LIMIT = 5;


    @Override
    public String getRadarCards(ReferralCardInfo cardInfo) {
        logger.info("ReferralCardInfo:{}", cardInfo);
        Timestamp tenMinite = new Timestamp(cardInfo.getTimestamp());
        Timestamp beforeTenMinite = new Timestamp(cardInfo.getTimestamp() - 1000 * 60 * 10);
        // 获取指定时间前十分钟内的职位浏览人
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getRadarCards(cardInfo.getUser_id(), beforeTenMinite, tenMinite);
        // 获取浏览人的userId
        Set<Integer> beRecomUserIds = shareChainDOS.stream().map(CandidateShareChainDO::getPresenteeUserId).collect(Collectors.toSet());
        // 将员工过滤掉，获取职位浏览人中非员工的userId
        getUnEmployeeUserIds(beRecomUserIds);
        if(beRecomUserIds.size() == 0){
            return "";
        }
        // 后边需要用到员工头像和昵称，在这里一并查出来
        Set<Integer> allUsers = new HashSet<>(beRecomUserIds);
        allUsers.add(cardInfo.getUser_id());
        // 将过滤后的员工id对应员工信息，用于后续数据组装
        HrWxWechatDO hrWxWechatDO = wechatDao.getHrWxWechatByCompanyId(cardInfo.getCompany_id());
        List<UserWxUserDO> userUserDOS = wxUserDao.getWXUserMapByUserIds(allUsers, hrWxWechatDO.getId());
        Map<Integer, UserWxUserDO> idUserMap = userUserDOS.stream().collect(Collectors.toMap(UserWxUserDO::getSysuserId, userWxUserDO->userWxUserDO));
        // 获取十分钟内转发的职位
        List<Integer> positionIds = shareChainDOS.stream().map(CandidateShareChainDO::getPositionId).distinct().collect(Collectors.toList());
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
        logger.info("getRadarCards:{}", JSON.toJSONString(cards));
        return JSON.toJSONString(cards);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inviteApplication(ReferralInviteInfo inviteInfo) {
        logger.info("inviteInfo:{}", inviteInfo);
        // todo 消息模板发不出去，neo4j沙河环境查不了
        JSONObject result = new JSONObject();
        // 检验是否关注公众号
        if(!checkIsSubscribe(inviteInfo.getEndUserId(), inviteInfo.getCompanyId())){
            return "";
        }
        // 先查询之前是否存在，是否已完成，如果是员工触发则生成连连看链路，遍历每个员工入库
        ReferralConnectionLogRecord connectionLogRecord = connectionLogDao.fetchChainLogRecord(inviteInfo.getUserId(), inviteInfo.getEndUserId(), inviteInfo.getPid());
        // 查询最短路径
        List<Integer> shortestChain = neo4jService.fetchShortestPath(inviteInfo.getUserId(), inviteInfo.getEndUserId(), inviteInfo.getCompanyId());
        // 只有两度和三度的情况下才会产生连连看链路
        if(shortestChain.size() >= 3 && shortestChain.size() <= 4 && (connectionLogRecord == null || connectionLogRecord.getState() == 1)){
            // 如果之前该职位没有连接过连连看，生成一条最短链路记录
            result.put("chain_id", doInsertConnection(connectionLogRecord, shortestChain, inviteInfo));
        }
        // 组装连连看链路返回数据
        Set<Integer> userIds = new HashSet<>(shortestChain);
        HrWxWechatDO hrWxWechatDO = wechatDao.getHrWxWechatByCompanyId(inviteInfo.getCompanyId());
        List<UserWxUserDO> userUserDOS = wxUserDao.getWXUserMapByUserIds(userIds, hrWxWechatDO.getId());
        Map<Integer, UserWxUserDO> idUserMap = userUserDOS.stream().collect(Collectors.toMap(UserWxUserDO::getSysuserId, userWxUserDO->userWxUserDO));
        result.put("chain", doInitRadarUsers(shortestChain, idUserMap));
        // 发送消息模板
        boolean isSent = sendInviteTemplate(inviteInfo, hrWxWechatDO, userUserDOS);
        // 邀请投递后，将该候选人标记为已处理，下次该职位的候选人卡片中不再包括此人
        candidatePositionDao.updateTypeByPidAndUid(inviteInfo.getPid(), inviteInfo.getEndUserId());
        result.put("notified", isSent ? 1 : 0);
        result.put("degree", shortestChain.size()-1);
        logger.info("inviteApplication:{}", JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) {
        logger.info("ignoreUserId:{}", ignoreInfo.getEndUserId());
        candidatePositionDao.updateTypeByPidAndUid(ignoreInfo.getPid(), ignoreInfo.getEndUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String connectRadar(ConnectRadarInfo radarInfo) {
        ReferralConnectionLogRecord connectionLogRecord = connectionLogDao.fetchByChainId(radarInfo.getChainId());
        if(connectionLogRecord == null){
            return "";
        }
        List<ReferralConnectionChainRecord> chainRecords = connectionChainDao.fetchChainsByRootChainId(connectionLogRecord.getRootChainId());
        Set<Integer> userIds = getChainRecordsUserIds(chainRecords);
        // 检验当前参数：recomUserId/nextUserId是否在链路中
        if(!userIds.contains(radarInfo.getNextUserId()) || !userIds.contains(radarInfo.getRecomUserId())){
            return "";
        }
        //
        boolean isReverseLink = false;
        ReferralConnectionChainRecord currentRecord = null;
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getRecomUserId() == radarInfo.getRecomUserId() && radarInfo.getNextUserId() == chainRecord.getNextUserId()){
                currentRecord = chainRecord;
                break;
            }else if(chainRecord.getRecomUserId() == radarInfo.getNextUserId() && radarInfo.getRecomUserId() == chainRecord.getNextUserId()){
                //如果是反向连接，不插入新记录
                isReverseLink = true;
                break;
            }
        }
        // 检验是否需要新增链路
        if(currentRecord == null && !isReverseLink){
            ReferralConnectionChainRecord extraRecord = insertExtraRecord(radarInfo, chainRecords);
            chainRecords.add(extraRecord);
            logger.info("addConnection，userId:{}，endUserId:{}", radarInfo.getRecomUserId(), radarInfo.getNextUserId());
            neo4jService.addConnRelation(radarInfo.getRecomUserId(), radarInfo.getNextUserId(), extraRecord.getId(), connectionLogRecord.getPositionId());
        }else {
            if(currentRecord != null && currentRecord.getState() != 1){
                // 已有链路，修改连接状态
                currentRecord.setState((byte)1);
                currentRecord.setClickTime(new Timestamp(System.currentTimeMillis()));
                connectionChainDao.updateRecord(currentRecord);
            }
        }
        UserEmployeeRecord userEmployee = userEmployeeDao.getActiveEmployee(connectionLogRecord.getRootUserId(), connectionLogRecord.getCompanyId());
        // 如果是连连看链路连接顺序发生变化，当连接完成时，将链路之外已连接的链路设为未连接
        chainRecords = updateChangedConnectionChain(radarInfo, connectionLogRecord, chainRecords);
        // 修改连连看是否连接完成的状态
        updateConnectionLog(radarInfo, connectionLogRecord, chainRecords);
        // 获取排好序并包括连接状态的人脉连连看链路
        List<RadarUserInfo> userChains = getOrderedChains(userIds, chainRecords, connectionLogRecord.getCompanyId());
        // 填充员工姓名
        fillEmployeeName(userEmployee, userChains);
        RadarConnectResult result = new RadarConnectResult();
        result.setDegree(connectionLogRecord.getDegree().intValue());
        result.setPid(connectionLogRecord.getPositionId());
        result.setState(connectionLogRecord.getState().intValue());
        result.setChain(userChains);
        logger.info("connectRadar:{}", JSON.toJSONString(result));
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
            if(shareChainDO.getPresenteeUserId() != checkInfo.getRecomUserId()){
                throw UserAccountException.REFERRAL_CHAIN_NONEXISTS;
            }
            recomUserId = shareChainDO.getRootRecomUserId();
        }
        UserEmployeeRecord employeeRecord = userEmployeeDao.getActiveEmployeeByUserId(recomUserId);
        if(employeeRecord == null){
            result.put("employee", 0);
            logger.info("起始推荐人非员工RecomUserId:{}", checkInfo.getRecomUserId());
            logger.info("checkEmployee:{}", JSON.toJSONString(result));
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
        logger.info("connectRadar:{}", JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }

    private void updateConnectionLog(ConnectRadarInfo radarInfo, ReferralConnectionLogRecord connectionLogRecord, List<ReferralConnectionChainRecord> chainRecords) {
        boolean noNeedUpdate = true;
        // 更新degree
        if(radarInfo.getNextUserId() == connectionLogRecord.getEndUserId()){
            if(connectionLogRecord.getState() != RadarStateEnum.Finished.getState()){
                noNeedUpdate = false;
                connectionLogRecord.setState((byte) RadarStateEnum.Finished.getState());
                connectionLogRecord.setDegree((byte)chainRecords.size());
            }
        }else if(radarInfo.getNextUserId() == 0 && radarInfo.getRecomUserId() == connectionLogRecord.getRootUserId()){
            return;
        }else if(radarInfo.getNextUserId() != connectionLogRecord.getEndUserId() && radarInfo.getNextUserId() != 0){
            if(connectionLogRecord.getState() != RadarStateEnum.Connecting.getState()){
                noNeedUpdate = false;
                connectionLogRecord.setState((byte)RadarStateEnum.Connecting.getState());
            }
        }
        if(!noNeedUpdate){
            connectionLogRecord.setUpdateTime(null);
            connectionLogDao.updateRecord(connectionLogRecord);
        }
    }


    private List<ReferralConnectionChainRecord> updateChangedConnectionChain(ConnectRadarInfo radarInfo, ReferralConnectionLogRecord connectionLogRecord,
                                       List<ReferralConnectionChainRecord> chainRecords) {
        // 如果没有连接完成，返回neo4j最短路径
        if(connectionLogRecord.getEndUserId() != radarInfo.getNextUserId() && connectionLogRecord.getState() != 1){
            return chainRecords;
        }
        // 如果连接完成，返回实际用户连接最短路径
        List<ReferralConnectionChainRecord> linkedRecords = chainRecords.stream().filter(record -> record.getState() == 1).collect(Collectors.toList());
        int parentId = 0;
        // 实际的连接路径下ids
        List<Integer> factChainRecordIds = new ArrayList<>();
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(radarInfo.getNextUserId() == chainRecord.getNextUserId() && chainRecord.getState() == 1){
                parentId = chainRecord.getParentId();
                factChainRecordIds.add(chainRecord.getId());
                break;
            }
        }
        factChainRecordIds.addAll(getChainRecordIdsByRecurrence(parentId, factChainRecordIds, chainRecords));
        List<Integer> linkedIds = linkedRecords.stream().map(ReferralConnectionChainRecord::getId).collect(Collectors.toList());
        linkedIds.removeAll(factChainRecordIds);
        if(connectionLogRecord.getState() != 1) {
            // 如果用户点击之前连接未完成，当本次连接完成时，将链路之外已连接的链路设为未连接
            connectionChainDao.updateStateByIds(linkedIds);
        }
        return linkedRecords.stream().filter(record -> factChainRecordIds.contains(record.getId())).collect(Collectors.toList());
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
     * @param companyId 公司id
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
        Timestamp current = new Timestamp(System.currentTimeMillis());
        ReferralConnectionChainRecord newChainRecord = new ReferralConnectionChainRecord();
        newChainRecord.setRecomUserId(radarInfo.getRecomUserId());
        newChainRecord.setNextUserId(radarInfo.getNextUserId());
        newChainRecord.setParentId(parentId);
        newChainRecord.setClickTime(current);
        newChainRecord.setCreateTime(current);
        newChainRecord.setUpdateTime(current);
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

    private List<RadarUserInfo> doInitRadarUsers(List<Integer> shortestChain, Map<Integer, UserWxUserDO> idUserMap) {
        // 最短路径的userChains信息
        List<RadarUserInfo> userChains = new ArrayList<>();
        for(Integer userId : shortestChain){
            RadarUserInfo userInfo = new RadarUserInfo();
            userChains.add(userInfo.initFromUserWxUser(idUserMap.get(userId)));
        }
        return userChains;
    }

    private int doInsertConnection(ReferralConnectionLogRecord connectionLogRecord, List<Integer> shortestChain, ReferralInviteInfo inviteInfo) {
        if(connectionLogRecord == null){
            // 插入连连看链路记录
            int rootParentId = doCreateConnectionChainRecords(shortestChain);
            // 反填后入库
            connectionLogRecord = doCreateConnectionLogRecord(rootParentId, inviteInfo, shortestChain.size());
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
        connectionChainRecords = connectionChainDao.insertRecords(connectionChainRecords);
        int rootParentId = connectionChainRecords.get(0).getId();
        // 填充parentId, rootParentId
        fillParentChainId(connectionChainRecords, rootParentId);
        connectionChainDao.updateRecords(connectionChainRecords);
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
     * 邀请投递发送消息模板
     * @param  inviteInfo 邀请投递相关参数
     * @param hrWxWechatDO 微信号
     * @author  cjm
     * @date  2018/12/13
     * @return  是否发送成功
     */
    private boolean sendInviteTemplate(ReferralInviteInfo inviteInfo, HrWxWechatDO hrWxWechatDO, List<UserWxUserDO> userWxUserDOS) {
        try{
            UserWxUserDO userWxUserDO = getWxUser(inviteInfo.getEndUserId(), userWxUserDOS);
            if(userWxUserDO == null){
                return false;
            }
            UserEmployeeDO employee = employeeEntity.getCompanyEmployee(inviteInfo.getUserId(), inviteInfo.getCompanyId());
            JobPositionDO jobPosition = positionDao.getJobPositionById(inviteInfo.getPid());
            HrCompanyDO hrCompanyDO = companyDao.getCompanyById(inviteInfo.getCompanyId());
            InviteTemplateVO inviteTemplateVO = initTemplateVO(jobPosition, hrCompanyDO, employee);
            String redirectUrl = TemplateConstant.inviteApplyRedirectUrl.replace("{}", String.valueOf(inviteInfo.getPid()));
            String requestUrl = TemplateConstant.inviteApplyRequestUrl.replace("{}", hrWxWechatDO.getAccessToken());
            Map<String, Object> response = templateHelper.sendInviteTemplate(hrWxWechatDO, userWxUserDO.getOpenid(), inviteTemplateVO, requestUrl, redirectUrl);
            return "0".equals(response.get("errcode"));
        }catch (Exception e){
            logger.info("发送邀请模板消息errmsg:{}", e.getMessage());
        }
        return false;
    }

    private UserWxUserDO getWxUser(int endUserId, List<UserWxUserDO> userWxUserDOS) {
        for(UserWxUserDO userWxUserDO : userWxUserDOS){
            if(userWxUserDO.getSysuserId() == endUserId){
                return userWxUserDO;
            }
        }
        return null;
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
        inviteTemplateVO.setTemplateId(Constant.REFERRAL_INVITE_APPLICATION);
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
        RadarUserInfo user = doInitUser(idUserMap, cardInfo.getUser_id(), candidatePositionDO.getUserId(), cardInfo.getCompany_id());
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
            if(shareChainDO.getRootRecomUserId() == cardInfo.getUser_id() && shareChainDO.getPresenteeUserId() == candidatePositionDO.getUserId()
                && shareChainDO.getPositionId() == candidatePositionDO.getPositionId()){
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
    private RadarUserInfo doInitUser(Map<Integer, UserWxUserDO> idUserMap, int rootUserId, int endUserId, int companyId) {
        RadarUserInfo user = new RadarUserInfo();
        UserWxUserDO userWxUserDO = idUserMap.get(endUserId);
        user.initFromUserWxUser(userWxUserDO);
        List<Integer> shortestChain = neo4jService.fetchShortestPath(rootUserId, endUserId, companyId);
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
}
