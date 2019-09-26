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
import com.moseeker.common.annotation.iface.CounterIface;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2018/12/17.
 */
@Service
@CounterIface
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

    private static final long TIMEOUT = 3L;

    @Override
    public void addFriendRelation(int startUserId, int endUserId, int shareChainId) throws CommonException {
        /**
         * 不允许建立自己和自己的关系
         */
        if (startUserId == endUserId) {
            throw CommonException.PROGRAM_EXCEPTION;
        }

        List<UserNode> userNodes = insertIfNotExistNode(startUserId, endUserId);
        UserNode firstUserStatus;
        UserNode secordUserStatus;
        if (userNodes != null && userNodes.size() == 2) {
            firstUserStatus = userNodes.get(0);
            secordUserStatus = userNodes.get(1);
        } else {
            throw CommonException.PROGRAM_EXCEPTION;
        }

        CandidateShareChainDO chain = candidateShareChainDao.getCandidateShareChainById(shareChainId);
        if (chain != null && firstUserStatus != null && secordUserStatus != null) {
            logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserFriend  startUserId:{}, endUserId:{}, positionId:{}", startUserId, endUserId, chain.getPositionId());
            LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserFriend  datetime:{}", beforeGetTwoUserFriend.toString());
            Future<List<Forward>> forwardFuture = tp.startTast(() -> forwardNeo4jDao.getTwoUserFriend(startUserId, endUserId, chain.getPositionId()));
            List<Forward> forwards;
            try {
                forwards = forwardFuture.get(TIMEOUT, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw CommonException.PROGRAM_EXCEPTION;
            }
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j 调用日志 after forwardNeo4jDao.getTwoUserFriend  datetime:{} and time:{}", afterGetTwoUserFriend.toString(), Duration.between(beforeGetTwoUserFriend, afterGetTwoUserFriend).toMillis());
            if (StringUtils.isEmptyList(forwards)) {
                Forward forward = new Forward();
                forward.setShare_chain_id(shareChainId);
                forward.setStartNode(firstUserStatus);
                forward.setEndNode(secordUserStatus);
                forward.setPosition_id(chain.getPositionId());
                forward.setParent_id(chain.getParentId());
                forward.setRoot_user_id(chain.getRootRecomUserId());
                forward.setCreate_time(chain.getClickTime());
                LocalDateTime beforeSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 before forwardNeo4jDao.save datetime:{}", beforeSave.toString());
                Future<Forward> sigleForwardFuture = tp.startTast(() -> forwardNeo4jDao.save(forward));
                Forward forwar;
                try {
                    forwar = sigleForwardFuture.get();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw CommonException.PROGRAM_EXCEPTION;
                }
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after forwardNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
                kafkaSender.sendForwardView(chain);
                logger.info("proceed friend:" + JSON.toJSONString(forwar));
            }
        }
    }

    @Override
    public void addConnRelation(int startUserId, int endUserId, int connChainId, int positionId) throws CommonException {
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
            Future<List<Connection>> connFuture = tp.startTast(() -> connNeo4jDao.getTwoUserConn(startUserId, endUserId, positionId));
            List<Connection> conns;
            try {
                conns = connFuture.get(TIMEOUT, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw CommonException.PROGRAM_EXCEPTION;
            }
            LocalDateTime afterGetTwoUserConn = LocalDateTime.now();
            logger.info("neo4j 调用日志 after connNeo4jDao.getTwoUserConn datetime:{}, time:{}", afterGetTwoUserConn.toString(), Duration.between(beforeGetTwoUserConn, afterGetTwoUserConn).toMillis());
            if (StringUtils.isEmptyList(conns)) {
                LocalDateTime beforeSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 before connNeo4jDao.save datetime:{}", beforeSave.toString());
                Future<Connection> connectionFuture = tp.startTast(() -> connNeo4jDao.save(conn));
                Connection connection;
                try {
                    connection = connectionFuture.get(TIMEOUT, TimeUnit.SECONDS);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw CommonException.PROGRAM_EXCEPTION;
                }
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after connNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
                kafkaSender.sendConnectionLink(conn, endUserId);
                logger.info("proceed forward:" + JSON.toJSONString(connection));
            }
        }
    }

    @Override
    public List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId) throws CommonException {
        logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserShortFriend startUserId:{}, endUserId:{}, companyId:{}", startUserId, endUserId, companyId);
        LocalDateTime beforeGetTwoUserShortFriend = LocalDateTime.now();
        logger.info("neo4j 调用日志 before forwardNeo4jDao.getTwoUserShortFriend datetime:{}", beforeGetTwoUserShortFriend.toString());
        Future<List<Relation>> relationFuture = tp.startTast(() -> forwardNeo4jDao.getTwoUserShortFriend(startUserId, endUserId, companyId));
        List<Relation> relationList;
        try {
            relationList = relationFuture.get(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
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

    @Override
    public boolean updateUserEmployeeCompany(int userId, int companyId) throws CommonException {
        logger.info("nupdateUserEmployeeCompany userId:{}, companyId:{}", userId, companyId);
        UserNode node;
        LocalDateTime beforeUpdateUserEmployeeCompany = LocalDateTime.now();
        logger.info("neo4j 调用日志 before userNeo4jDao.updateUserEmployeeCompany datetime:{}", beforeUpdateUserEmployeeCompany.toString());
        Future<UserNode> nodeFuture = tp.startTast(() -> userNeo4jDao.updateUserEmployeeCompany(userId, companyId));
        try {
            node = nodeFuture.get(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("neo4j 调用日志 after userNeo4jDao.updateUserEmployeeCompany datetime:{}", LocalDateTime.now().toString());
            logger.error(e.getMessage(), e);
            return false;
        }
        LocalDateTime afterUpdateUserEmployeeCompany = LocalDateTime.now();
        logger.info("neo4j 调用日志 after userNeo4jDao.updateUserEmployeeCompany datetime:{}, time:{}", afterUpdateUserEmployeeCompany.toString(), Duration.between(beforeUpdateUserEmployeeCompany, afterUpdateUserEmployeeCompany).toMillis());
        if(node != null && node.getEmployee_company() == companyId){
            return true;
        }
        return false;
    }

    @Override
    public void updateUserEmployeeCompany(List<Integer> userIds, int companyId) throws CommonException {
        if(StringUtils.isEmptyList(userIds) || companyId<0){
            return;
        }
        tp.startTast(() -> {
            logger.info("neo4j 调用日志 before forwardNeo4jDao.updateUserEmployeeCompanyList userIds:{}, companyId:{}", JSONObject.toJSONString(userIds), companyId);
            LocalDateTime beforeUpdateUserEmployeeCompanyList = LocalDateTime.now();
            logger.info("neo4j 调用日志 before forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}", beforeUpdateUserEmployeeCompanyList.toString());
            userNeo4jDao.updateUserEmployeeCompanyList(userIds, companyId);
            LocalDateTime afterUpdateUserEmployeeCompanyList = LocalDateTime.now();
            logger.info("neo4j 调用日志 after forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}, time:{}", afterUpdateUserEmployeeCompanyList.toString(), Duration.between(beforeUpdateUserEmployeeCompanyList, afterUpdateUserEmployeeCompanyList).toMillis());
            return true;
        });
    }

    @Override
    public List<EmployeeCompanyVO> fetchUserThreeDepthEmployee(int userId, int companyId) throws CommonException {
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
                List<EmployeeCompanyVO> postUserIdList;
                LocalDateTime beforeFetchUserThreeDepthEmployee = LocalDateTime.now();
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchUserThreeDepthEmployee datetime:{}", beforeFetchUserThreeDepthEmployee.toString());
                Future<List<EmployeeCompanyVO>> listFuture = tp.startTast(() -> userNeo4jDao.fetchUserThreeDepthEmployee(userId, rootUserIdList));
                postUserIdList = listFuture.get(TIMEOUT, TimeUnit.SECONDS);
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

    @Override
    public List<UserDepthVO> fetchEmployeeThreeDepthUser(int userId) throws CommonException {
        List<ConfigOmsSwitchManagementDO>  managementDOList =  managementDao.fetchRadarStatus(7, 1);
        logger.info("fetchEmployeeThreeDepthUser managementDOList:{}",managementDOList);
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
        try {
            logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchEmployeeThreeDepthUser userId:{}, peresentUserIdList:{}, companyId:{}", userId, JSONObject.toJSONString(peresentUserIdList), employee.getCompanyId());
            List<UserDepthVO> depthUser;
            logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}", beforeFetchEmployeeThreeDepthUser.toString());
            Future<List<UserDepthVO>> listFuture = tp.startTast(() -> userNeo4jDao.fetchEmployeeThreeDepthUser(userId, peresentUserIdList, employee.getCompanyId()));
            depthUser = listFuture.get(TIMEOUT, TimeUnit.SECONDS);
            LocalDateTime afterFetchEmployeeThreeDepthUser = LocalDateTime.now();
            logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}, time:{}", afterFetchEmployeeThreeDepthUser.toString(), Duration.between(beforeFetchEmployeeThreeDepthUser, afterFetchEmployeeThreeDepthUser).toMillis());
            return depthUser;
        }catch (Exception e){
            LocalDateTime afterFetchEmployeeThreeDepthUser = LocalDateTime.now();
            logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}, time:{}", afterFetchEmployeeThreeDepthUser.toString(), Duration.between(beforeFetchEmployeeThreeDepthUser, afterFetchEmployeeThreeDepthUser).toMillis());
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }

    }

    @Override
    public List<UserDepthVO> fetchDepthUserList(int userId, int companyId, List<Integer> userIdList) throws CommonException {
        if(userId >0 && companyId >0 && !StringUtils.isEmptyList(userIdList)){
            LocalDateTime beforeFetchDepthUserList = LocalDateTime.now();
            try {
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchDepthUserList userId:{}, companyId:{}, userIdList:{}", userId, companyId, JSONObject.toJSON(userIdList));
                logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchDepthUserList datetime:{}", beforeFetchDepthUserList.toString());
                List<UserDepthVO> list;
                Future<List<UserDepthVO>> listFuture = tp.startTast(() -> userNeo4jDao.fetchDepthUserList(userId, userIdList, companyId));
                list = listFuture.get(TIMEOUT, TimeUnit.SECONDS);
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

    /**
     * 添加节点。
     * 不存在则添加节点，存在则直接返回
     * @param userId1 用户1
     * @param userId2 用户2
     * @return 用户节点数据
     */
    private List<UserNode> insertIfNotExistNode(int userId1, int userId2){
        if(userId1>0 && userId2 > 0) {
            List<UserNode> userNodes = userNeo4jDao.listUserNodeById(userId1, userId2);
            if (userNodes != null && userNodes.size() == 2) {
                /**
                 * 如果数据库中已经存在直接返回
                 */
                return userNodes;
            } else if (userNodes == null || userNodes.size() == 0) {
                /**
                 * 如果数据库中不存在，则新增元素
                 */
                List<UserNode> list = new ArrayList<>(2);
                UserNode userNode1 = generateUserNode(userId1);
                if (userNode1 != null) {
                    list.add(userNode1);
                }
                UserNode userNode2 = generateUserNode(userId2);
                if (userNode2 != null) {
                    list.add(userNode2);
                }
                if (list.size() > 0) {
                    userNeo4jDao.save(list);
                }
                return list;
            } else {
                /**
                 * 如果只存在一个，则新增不存在的数据，并按照参数顺序返回节点的集合
                 */
                List<UserNode> result = new ArrayList<>(2);
                UserNode userNode;
                if (userNodes.get(0).getUser_id() == userId1) {
                    result.add(userNodes.get(0));
                    userNode = generateUserNode(userId2);
                    userNode = userNeo4jDao.save(userNode);
                    result.add(userNode);
                } else {
                    userNode = generateUserNode(userId1);
                    userNode = userNeo4jDao.save(userNode);
                    result.add(userNode);
                    result.add(userNodes.get(0));
                }
                return result;
            }
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 构建用户节点数据
     * @param userId 用户编号
     * @return
     */
    private UserNode generateUserNode(int userId) {
        if(userId>0) {
            UserUserRecord record1 = userUserDao.getUserById(userId);


            if (record1 != null) {
                UserNode node = new UserNode();
                node.setUser_id(userId);
                node.setNickname(record1.getNickname());
                node.setHeadimgurl(record1.getHeadimg());

                UserWxUserRecord wxUser = wxUserDao.getWXUserByUserId(userId);
                if (wxUser != null) {
                    node.setWxuser_id(wxUser.getId().intValue());
                }
                UserEmployeeRecord employee = employeeDao.getActiveEmployeeByUserId(userId);
                if (employee != null) {
                    node.setEmployee_id(employee.getId());
                    node.setEmployee_company(employee.getCompanyId());
                }
                return node;
            }

        }
        return null;
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
            UserNode nodes;
            Future<UserNode> userNodeFuture = tp.startTast(() -> userNeo4jDao.save(node));
            try {
                nodes = userNodeFuture.get(TIMEOUT, TimeUnit.SECONDS);
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after userNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
            } catch (Exception e){
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j 调用日志 after userNeo4jDao.save datetime:{}, time:{}", afterSave.toString(), Duration.between(beforeSave, afterSave).toMillis());
                logger.error(e.getMessage(), e);
                return null;
            }
            logger.info(JSON.toJSONString(nodes));
            return node;
        }
        return null;
    }
}