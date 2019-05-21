package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.configdb.ConfigOmsSwitchManagementDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.Neo4jThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigOmsSwitchManagementDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.kafka.KafkaSender;
import com.moseeker.useraccounts.pojo.neo4j.*;
import com.moseeker.useraccounts.repository.ConnectionNeo4jDao;
import com.moseeker.useraccounts.repository.ForwardNeo4jDao;
import com.moseeker.useraccounts.repository.UserNeo4jDao;
import com.moseeker.useraccounts.service.Neo4jService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2018/12/17.
 */
@Service
public class Neo4jServiceImpl implements Neo4jService {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jServiceImpl.class);

    @Autowired
    UserNeo4jDao userNeo4jDao;

    @Autowired
    ForwardNeo4jDao forwardNeo4jDao;

    @Autowired
    ConnectionNeo4jDao connNeo4jDao;

    @Autowired
    ConfigOmsSwitchManagementDao managementDao;

    @Autowired
    UserUserDao userUserDao;

    @Autowired
    UserWxUserDao wxUserDao;

    @Autowired
    UserEmployeeDao employeeDao;

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    CandidateShareChainDao candidateShareChainDao;

    @Autowired
    HrGroupCompanyRelDao companyRelDao;

    @Autowired
    PositionEntity positionEntity;

    Neo4jThreadPool tp = Neo4jThreadPool.Instance;

    private static final int timeout = 60;

    @Override
    public void addFriendRelation(int startUserId, int endUserId, int shareChainId) throws CommonException {
        logger.info("neo4j 调用日志 method addFriendRelation  startUserId:{}, endUserId:{}, positionId:{}", startUserId, endUserId, shareChainId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before addFriendRelation datetime:{}", beforeGetTwoUserFriend.toString());
        try {
            tp.startTast(() -> {
                addFriendRelationInner(startUserId, endUserId, shareChainId);
                return true;
            });

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after addFriendRelation datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after addFriendRelation datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
    }

    @Override
    public void addConnRelation(int startUserId, int endUserId, int connChainId, int positionId) throws CommonException {
        logger.info("neo4j 调用日志 method addConnRelation  startUserId:{}, endUserId:{}, connChainId:{}, positionId:{}", startUserId, endUserId, connChainId, positionId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before addConnRelation datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            tp.startTast(() -> {
                addConnRelationInner(startUserId, endUserId, connChainId, positionId);
                return true;
            });

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after addConnRelation datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after addConnRelation datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
    }

    @Override
    public List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId) throws CommonException {
        logger.info("neo4j 调用日志 method fetchShortestPath  startUserId:{}, endUserId:{}, companyId:{}", startUserId, endUserId, companyId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before fetchShortestPath datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            Future<List<Integer>> listFuture = tp.startTast(() -> fetchShortestPathInner(startUserId, endUserId, companyId));
            List<Integer> list;
            try {
                list = listFuture.get(timeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw e;
            }

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchShortestPath datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchShortestPath datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
    }

    @Override
    public boolean updateUserEmployeeCompany(int userId, int companyId) throws CommonException {
        logger.info("neo4j 调用日志 method updateUserEmployeeCompany  userId:{}, companyId:{}", userId, companyId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before updateUserEmployeeCompany datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            Future<Boolean> booleanFuture = tp.startTast(() -> updateUserEmployeeCompanyInner(userId, companyId));
            boolean result;
            try {
                result = booleanFuture.get(timeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw e;
            }

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after updateUserEmployeeCompany datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after updateUserEmployeeCompany datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }

    }

    @Override
    public void updateUserEmployeeCompany(List<Integer> userIds, int companyId) throws CommonException {
        if(StringUtils.isEmptyList(userIds) || companyId<0){
            return;
        }
        logger.info("neo4j 调用日志 method updateUserEmployeeCompany  userIds:{}, companyId:{}", JSONObject.toJSONString(userIds), companyId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before updateUserEmployeeCompany datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            tp.startTast(() -> {
                updateUserEmployeeCompanyInner(userIds, companyId);
                return true;
            });

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after updateUserEmployeeCompany datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after updateUserEmployeeCompany datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
    }

    @Override
    public List<EmployeeCompanyVO> fetchUserThreeDepthEmployee(int userId, int companyId) throws CommonException {
        logger.info("neo4j 调用日志 method fetchUserThreeDepthEmployee  userId:{}, companyId:{}", userId, companyId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before fetchUserThreeDepthEmployee datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            Future<List<EmployeeCompanyVO>> listFuture = tp.startTast(() -> fetchUserThreeDepthEmployeeInner(userId, companyId));

            List<EmployeeCompanyVO> list;
            try {
                list = listFuture.get(timeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw e;
            }

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchUserThreeDepthEmployee datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchUserThreeDepthEmployee datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
    }

    @Override
    public List<UserDepthVO> fetchEmployeeThreeDepthUser(int userId) throws CommonException {
        logger.info("neo4j 调用日志 method fetchEmployeeThreeDepthUser  userId:{}", userId);
        LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 method before fetchEmployeeThreeDepthUser datetime:{}", beforeGetTwoUserFriend.toString());
        try {

            Future< List<UserDepthVO>> listFuture = tp.startTast(() -> fetchEmployeeThreeDepthUserInner(userId));
            List<UserDepthVO> list;
            try {
                list = listFuture.get(timeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw e;
            }

            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchEmployeeThreeDepthUser datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 method after fetchEmployeeThreeDepthUser datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }

    }

    @Override
    public List<UserDepthVO> fetchDepthUserList(int userId, int companyId, List<Integer> userIdList) throws CommonException {
        logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchDepthUserList userId:{}, companyId:{}, userIdList:{}", userId, companyId, JSONObject.toJSON(userIdList));
        if(userId >0 && companyId >0 && !StringUtils.isEmptyList(userIdList)){
            LocalDateTime beforeFetchDepthUserList = LocalDateTime.now();
            try {
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchDepthUserList datetime:{}", beforeFetchDepthUserList.toString());
                List<UserDepthVO> list;
                Future<List<UserDepthVO>> listFuture = tp.startTast(() -> userNeo4jDao.fetchDepthUserList(userId, userIdList, companyId));
                list = listFuture.get(timeout, TimeUnit.SECONDS);
                LocalDateTime afterFetchDepthUserList = LocalDateTime.now();
                logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchDepthUserList datetime:{}, time:{}", afterFetchDepthUserList.toString(), Duration.between(beforeFetchDepthUserList, afterFetchDepthUserList).toMillis());
                return list;
            }catch (Exception e){
                LocalDateTime afterFetchDepthUserList = LocalDateTime.now();
                logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchDepthUserList datetime:{}, time:{}", afterFetchDepthUserList.toString(), Duration.between(beforeFetchDepthUserList, afterFetchDepthUserList).toMillis());
                logger.error(e.getMessage()+"userid:{}, userIdList:{}, companyId:{}", userId, userIdList, companyId);

            }
        }
        return new ArrayList<>();
    }

    @Transactional
    private List<UserDepthVO> fetchEmployeeThreeDepthUserInner(int userId) throws CommonException {
        List<ConfigOmsSwitchManagementDO>  managementDOList =  managementDao.fetchRadarStatus(7, 1);
        logger.info("fetchEmployeeThreeDepthUser managementDOList.size:{}",managementDOList.size());
        if(StringUtils.isEmptyList(managementDOList)){
            return new ArrayList<>();
        }
        Set<Integer> companyIdSet = managementDOList.stream().map(m -> m.getCompanyId()).collect(Collectors.toSet());
        UserEmployeeRecord employee = employeeDao.getEmployeeByIdAndCompanyIds(userId, companyIdSet);
        logger.info("fetchEmployeeThreeDepthUser employee:{}",employee);
        if(employee == null ){
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        list.add(employee.getCompanyId());
        List<Integer> companyIds = companyRelDao.getGroupCompanyRelDoByCompanyIds(list);
        List<Integer> positionIds = positionEntity.getPositionIdListByCompanyIdListAndStatus(companyIds);
        List<Integer> peresentUserIdList = candidateShareChainDao.fetchRootIdByRootUserId(userId, positionIds);
        logger.info("fetchEmployeeThreeDepthUser peresentUserIdList:{}",peresentUserIdList);
        if(StringUtils.isEmptyList(peresentUserIdList)){
            return new ArrayList<>();
        }
        LocalDateTime beforeFetchEmployeeThreeDepthUser = LocalDateTime.now();
        logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchEmployeeThreeDepthUser userId:{}, peresentUserIdList:{}, companyId:{}", userId, JSONObject.toJSONString(peresentUserIdList), employee.getCompanyId());
        logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}", beforeFetchEmployeeThreeDepthUser.toString());
        List<UserDepthVO> depthUser = userNeo4jDao.fetchEmployeeThreeDepthUser(userId, peresentUserIdList, employee.getCompanyId());
        LocalDateTime afterFetchEmployeeThreeDepthUser = LocalDateTime.now();
        logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}, time:{}", afterFetchEmployeeThreeDepthUser.toString(), Duration.between(beforeFetchEmployeeThreeDepthUser, afterFetchEmployeeThreeDepthUser).toMillis());
        return depthUser;

    }

    @Transactional
    private List<EmployeeCompanyVO> fetchUserThreeDepthEmployeeInner(int userId, int companyId) throws CommonException {
        Future<List<ConfigOmsSwitchManagementDO>>  managementDOListFuture = tp.startTast(
                ()-> managementDao.fetchRadarStatus(7, 1));
        List<Integer> rootUserList = candidateShareChainDao.fetchRootIdByPresentee(userId);
        if(StringUtils.isEmptyList(rootUserList)){
            return new ArrayList<>();
        }
        try {
            if(StringUtils.isEmptyList(managementDOListFuture.get())){
                return new ArrayList<>();
            }
            Set<Integer> companyIdSet = managementDOListFuture.get().stream().map(m -> m.getCompanyId()).collect(Collectors.toSet());
            if(companyId >0 && companyIdSet.contains(companyId)) {
                List<UserEmployeeDO> employeeList = employeeDao.getActiveEmployee(rootUserList, companyId);
                rootUserList.clear();
                if(StringUtils.isEmptyList(employeeList)){
                    return new ArrayList<>();
                }
                List<Integer> rootUserIdList = employeeList.stream().map(m -> m.getSysuserId()).collect(Collectors.toList());
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchUserThreeDepthEmployee userId:{}, rootUserList:{}", userId, JSONObject.toJSONString(rootUserList));
                LocalDateTime beforeFetchUserThreeDepthEmployee = LocalDateTime.now();
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchUserThreeDepthEmployee datetime:{}", beforeFetchUserThreeDepthEmployee.toString());
                List<EmployeeCompanyVO> postUserIdList = userNeo4jDao.fetchUserThreeDepthEmployee(userId, rootUserIdList);
                LocalDateTime afterFetchUserThreeDepthEmployee = LocalDateTime.now();
                logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchUserThreeDepthEmployee datetime:{}, time:{}", LocalDateTime.now().toString(), Duration.between(beforeFetchUserThreeDepthEmployee, afterFetchUserThreeDepthEmployee).toMillis());

                return postUserIdList;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    private void updateUserEmployeeCompanyInner(List<Integer> userIds, int companyId) throws CommonException {
        LocalDateTime beforeUpdateUserEmployeeCompanyList = LocalDateTime.now();
        logger.info("neo4j 调用日志 before forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}", beforeUpdateUserEmployeeCompanyList.toString());
        userNeo4jDao.updateUserEmployeeCompanyList(userIds, companyId);
        LocalDateTime afterUpdateUserEmployeeCompanyList = LocalDateTime.now();
        logger.info("neo4j 调用日志 after forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}, time:{}", afterUpdateUserEmployeeCompanyList.toString(), Duration.between(beforeUpdateUserEmployeeCompanyList, afterUpdateUserEmployeeCompanyList).toMillis());
    }

    @Transactional(rollbackFor = Exception.class)
    private boolean updateUserEmployeeCompanyInner(int userId, int companyId) throws CommonException {
        LocalDateTime beforeUpdateUserEmployeeCompany = LocalDateTime.now();
        logger.info("neo4j 调用日志 before userNeo4jDao.updateUserEmployeeCompany datetime:{}", beforeUpdateUserEmployeeCompany.toString());
        UserNode node = userNeo4jDao.updateUserEmployeeCompany(userId, companyId);
        LocalDateTime afterUpdateUserEmployeeCompany = LocalDateTime.now();
        logger.info("neo4j 调用日志 after userNeo4jDao.updateUserEmployeeCompany datetime:{}, time:{}", afterUpdateUserEmployeeCompany.toString(), Duration.between(beforeUpdateUserEmployeeCompany, afterUpdateUserEmployeeCompany).toMillis());
        if(node != null && node.getEmployee_company() == companyId){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    private List<Integer> fetchShortestPathInner(int startUserId, int endUserId, int companyId) throws CommonException {
        LocalDateTime beforeGetTwoUserShortFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserShortFriend datetime:{}", beforeGetTwoUserShortFriend.toString());
        List<Relation> relationList = forwardNeo4jDao.getTwoUserShortFriend(startUserId, endUserId, companyId);
        LocalDateTime afteGetTwoUserShortFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 after forwardNeo4jDao.getTwoUserShortFriend datetime:{}, time:{}", afteGetTwoUserShortFriend.toString(), Duration.between(beforeGetTwoUserShortFriend, afteGetTwoUserShortFriend).toMillis());
        if(!StringUtils.isEmptyList(relationList)){
            List<Integer> idList = new ArrayList<>();
            Map<Integer, Integer> keyMap = new IdentityHashMap<>();
            Map<Integer, Integer> valueMap = new IdentityHashMap<>();
            for(Relation relation: relationList){
                keyMap.put(relation.getStartNode().getUser_id(), relation.getEndNode().getUser_id());
                valueMap.put(relation.getEndNode().getUser_id(), relation.getStartNode().getUser_id());
            }
            idList.add(startUserId);
            int tempId=startUserId;
            for(int i =0; i< relationList.size(); i++){
                int id = 0;
                for(Map.Entry<Integer, Integer> entry : keyMap.entrySet()){
                    if(entry.getKey().intValue() == tempId) {
                        id = entry.getValue();
                        if (!idList.contains(id)) {
                            idList.add(id);
                            tempId = id;
                            continue;
                        }
                    }
                }
                for(Map.Entry<Integer, Integer> entry : valueMap.entrySet()) {
                    if (entry.getKey().intValue() == tempId) {
                        id = entry.getValue();
                        if (!idList.contains(id)) {
                            idList.add(id);
                            tempId = id;
                            continue;
                        }
                    }
                }
            }
            return idList;
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    private void addConnRelationInner(int startUserId, int endUserId, int connChainId, int positionId) throws CommonException {
        UserNode firstUserStatus = addUserNode(startUserId);
        UserNode secordUserStatus = addUserNode(endUserId);
        if (firstUserStatus != null && secordUserStatus != null && startUserId != endUserId) {
            Connection conn = new Connection();
            conn.setConn_chain_id(connChainId);
            conn.setStartNode(firstUserStatus);
            conn.setEndNode(secordUserStatus);
            conn.setPosition_id(positionId);
            LocalDateTime beforeGetTwoUserConn = LocalDateTime.now();
            logger.info("neo4j 调用日志 before connNeo4jDao.getTwoUserConn datetime:{}", beforeGetTwoUserConn.toString());
            List<Connection> conns = connNeo4jDao.getTwoUserConn(startUserId, endUserId, positionId);
            LocalDateTime afterGetTwoUserConn = LocalDateTime.now();
            logger.info("neo4j 调用日志 after connNeo4jDao.getTwoUserConn datetime:{}, time:{}", afterGetTwoUserConn.toString(), Duration.between(beforeGetTwoUserConn, afterGetTwoUserConn).toMillis());
            if (StringUtils.isEmptyList(conns)) {
                LocalDateTime beforeSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 before connNeo4jDao.save datetime:{}", beforeSave.toString());
                Connection connection = connNeo4jDao.save(conn);
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after connNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
                kafkaSender.sendConnectionLink(conn, endUserId);
                logger.info("proceed forward:" + JSON.toJSONString(connection));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void addFriendRelationInner(int startUserId, int endUserId, int shareChainId) throws CommonException {
        UserNode firstUserStatus = addUserNode(startUserId);
        UserNode secordUserStatus = addUserNode(endUserId);
        CandidateShareChainDO chain = candidateShareChainDao.getCandidateShareChainById(shareChainId);
        if (firstUserStatus != null && secordUserStatus != null && startUserId != endUserId) {
            Forward forward = new Forward();
            forward.setShare_chain_id(shareChainId);
            forward.setStartNode(firstUserStatus);
            forward.setEndNode(secordUserStatus);
            forward.setPosition_id(chain.getPositionId());
            forward.setParent_id(chain.getParentId());
            forward.setRoot_user_id(chain.getRootRecomUserId());
            forward.setCreate_time(chain.getClickTime());
            LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserFriend  datetime:{}", beforeGetTwoUserFriend.toString());
            List<Forward> forwards = forwardNeo4jDao.getTwoUserFriend(startUserId, endUserId, chain.getPositionId());
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 after forwardNeo4jDao.getTwoUserFriend  datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            if (StringUtils.isEmptyList(forwards)) {
                LocalDateTime beforeSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 before forwardNeo4jDao.save datetime:{}", beforeSave.toString());
                Forward forwar = forwardNeo4jDao.save(forward);
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after forwardNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
                kafkaSender.sendForwardView(chain);
                logger.info("proceed friend:" + JSON.toJSONString(forwar));
            }
        }
    }

    private UserNode addUserNode(int userId){
        if(userId>0) {
            UserUserRecord record1 = userUserDao.getUserById(userId);
            UserWxUserRecord wxUser = wxUserDao.getWXUserByUserId(userId);
            UserEmployeeRecord employee = employeeDao.getActiveEmployeeByUserId(userId);
            UserNode node = new UserNode();
            node.setUser_id(userId);
            if (record1 != null) {
                node.setNickname(record1.getNickname());

            }
            if (wxUser != null) {
                node.setWxuser_id(wxUser.getId().intValue());
                node.setHeadimgurl(wxUser.getHeadimgurl());
            }
            if (employee != null) {
                node.setEmployee_id(employee.getId());
                node.setEmployee_company(employee.getCompanyId());
            }
            logger.info("neo4j 调用日志 before userNeo4jDao.save node:{}", JSONObject.toJSONString(node));
            LocalDateTime beforeSave = LocalDateTime.now();
            logger.info("neo4j 调用日志 before userNeo4jDao.save datetime:{}", beforeSave.toString());
            UserNode nodes = userNeo4jDao.save(node);
            LocalDateTime afterSave = LocalDateTime.now();
            logger.info("neo4j 调用日志 after userNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
            return node;
        }
        return null;
    }
}
