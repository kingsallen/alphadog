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
import java.util.concurrent.*;
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

    /**
     * 超时时间
     */
    private static final long TIMEOUT = 10L;
    /**
     * 节点的数量
     */
    private static final int NODE_NUM = 2;

    @Override
    public void addFriendRelation(int startUserId, int endUserId, int shareChainId) throws CommonException {
        handlerFutureTask("addFriendRelation", () -> {
            try {
                addFriendRelationInternal(startUserId, endUserId, shareChainId);
                return true;
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, startUserId, endUserId, shareChainId);
    }

    @Override
    public void addConnRelation(int startUserId, int endUserId, int connChainId, int positionId) throws CommonException {
        handlerFutureTask("addConnRelation", () -> {
            try {
                addConnRelationInternal(startUserId, endUserId, connChainId, positionId);
                return true;
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, startUserId, endUserId, connChainId, positionId);
    }

    @Override
    public List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId) throws CommonException {
        return handlerFutureTask("fetchShortestPath", () -> {
            try {
                return fetchShortestPathInternal(startUserId, endUserId, companyId);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, startUserId, endUserId, companyId);
    }

    @Override
    public boolean updateUserEmployeeCompany(int userId, int companyId) throws CommonException {
        return handlerFutureTask("updateUserEmployeeCompany", () -> {
            try {
                List<Integer> userIds = new ArrayList<>(1);
                userIds.add(userId);
                return updateUserEmployeeCompanyInternal(userIds, companyId);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, userId, companyId);
    }

    @Override
    public void updateUserEmployeeCompany(List<Integer> userIds, int companyId) throws CommonException {
        handlerFutureTask("updateUserEmployeeCompanyBatch", () -> {
            try {
                return updateUserEmployeeCompanyInternal(userIds, companyId);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, userIds, companyId);
    }

    @Override
    public List<EmployeeCompanyVO> fetchUserThreeDepthEmployee(int userId, int companyId) throws CommonException {
        return handlerFutureTask("fetchUserThreeDepthEmployee", () -> {
            try {
                return fetchUserThreeDepthEmployeeInternal(userId, companyId);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, userId, companyId);
    }

    @Override
    public List<UserDepthVO> fetchEmployeeThreeDepthUser(int userId) throws CommonException {
        return handlerFutureTask("fetchEmployeeThreeDepthUser", () -> {
            try {
                return fetchEmployeeThreeDepthUserInternal(userId);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, userId);
    }

    @Override
    public List<UserDepthVO> fetchDepthUserList(int userId, int companyId, List<Integer> userIdList) throws CommonException {
        return handlerFutureTask("fetchDepthUserList", () -> {
            try {
                return fetchDepthUserListInternal(userId, companyId, userIdList);
            } catch (CommonException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }, userId, companyId, userIdList);
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
                } else {
                    throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
                }
                UserNode userNode2 = generateUserNode(userId2);
                if (userNode2 != null) {
                    list.add(userNode2);
                } else {
                    throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
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
                    if (userNode == null) {
                        throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
                    }
                    userNode = userNeo4jDao.save(userNode);
                    result.add(userNode);
                } else {
                    userNode = generateUserNode(userId1);
                    userNode = userNeo4jDao.save(userNode);
                    if (userNode == null) {
                        throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
                    }
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
            Future<UserNode> userNodeFuture = tp.startSubmit(() -> userNeo4jDao.save(node));
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

    /**
     * 查找给定用户和用户集之间的关系度数
     * 最多查找三层（3度）关系。
     * @param userId 用户编号
     * @param companyId 公司编号
     * @param userIdList 用户集编号
     * @return 有度数关系的
     * @throws CommonException
     */
    private List<UserDepthVO> fetchDepthUserListInternal(int userId, int companyId, List<Integer> userIdList) throws CommonException {
        LocalDateTime beforeFetchDepthUserList = LocalDateTime.now();
        logger.info("neo4j 调用日志 before forwardNeo4jDao.fetchDepthUserList datetime:{}", beforeFetchDepthUserList.toString());
        List<UserDepthVO> list = userNeo4jDao.fetchDepthUserList(userId, userIdList, companyId);
        LocalDateTime afterFetchDepthUserList = LocalDateTime.now();
        logger.info("neo4j 调用日志 after forwardNeo4jDao.fetchDepthUserList datetime:{}, time:{}", afterFetchDepthUserList.toString(), Duration.between(beforeFetchDepthUserList, afterFetchDepthUserList).toMillis());
        return list;
    }

    /**
     * 查找最近三度的人脉关系
     * @param userId 用户编号
     * @return 最近三度的人脉关系
     * @throws CommonException 业务异常
     */
    private List<UserDepthVO> fetchEmployeeThreeDepthUserInternal(int userId) throws CommonException {
        //TODO: 调整成枚举常量
        List<ConfigOmsSwitchManagementDO>  managementDOList =  managementDao.fetchRadarStatus(7, 1);
        if(StringUtils.isEmptyList(managementDOList)){
            return new ArrayList<>();
        }
        Set<Integer> companyIdSet = managementDOList.stream().map(ConfigOmsSwitchManagementDO::getCompanyId).collect(Collectors.toSet());
        UserEmployeeRecord employee = employeeDao.getEmployeeByIdAndCompanyIds(userId, companyIdSet);
        if(employee == null ){
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        list.add(employee.getCompanyId());
        List<Integer> companyIds = companyRelDao.getGroupCompanyRelDoByCompanyIds(list);
        List<Integer> positionIds = positionEntity.getPositionIdListByCompanyIdListAndStatus(companyIds);
        List<Integer> peresentUserIdList = candidateShareChainDao.fetchRootIdByRootUserId(userId, positionIds);
        if(StringUtils.isEmptyList(peresentUserIdList)){
            return new ArrayList<>();
        }
        LocalDateTime beforeFetchEmployeeThreeDepthUser = LocalDateTime.now();
        logger.info("neo4j fetchEmployeeThreeDepthUserInternal before forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}", beforeFetchEmployeeThreeDepthUser.toString());
        List<UserDepthVO> depthUser = userNeo4jDao.fetchEmployeeThreeDepthUser(userId, peresentUserIdList, employee.getCompanyId());
        LocalDateTime afterFetchEmployeeThreeDepthUser = LocalDateTime.now();
        logger.info("neo4j fetchEmployeeThreeDepthUserInternal after forwardNeo4jDao.fetchEmployeeThreeDepthUser datetime:{}, time:{}", afterFetchEmployeeThreeDepthUser.toString(), Duration.between(beforeFetchEmployeeThreeDepthUser, afterFetchEmployeeThreeDepthUser).toMillis());
        return depthUser;
    }

    /**
     * 查找用户在一家公司下有关联关系的员工
     * @param userId 用户编号
     * @param companyId 公司编号
     * @return 有关联关系的员工数据
     * @throws CommonException
     */
    private List<EmployeeCompanyVO> fetchUserThreeDepthEmployeeInternal(int userId, int companyId) throws CommonException {
        List<ConfigOmsSwitchManagementDO>  managementDOList = managementDao.fetchRadarStatus(7, 1);
        List<Integer> rootUserList = candidateShareChainDao.fetchRootIdByPresentee(userId);
        if(StringUtils.isEmptyList(rootUserList)){
            return new ArrayList<>(0);
        }
        if(StringUtils.isEmptyList(managementDOList)){
            return new ArrayList<>(0);
        }
        Optional<ConfigOmsSwitchManagementDO> companyIdExists = managementDOList
                .stream()
                .filter(configOmsSwitchManagementDO -> configOmsSwitchManagementDO.getCompanyId() == companyId)
                .findAny();
        if (companyIdExists.isPresent()) {
            List<UserEmployeeDO> employeeList = employeeDao.getActiveEmployee(rootUserList, companyId);
            rootUserList.clear();
            if(StringUtils.isEmptyList(employeeList)){
                return new ArrayList<>();
            }
            List<Integer> rootUserIdList = employeeList
                    .stream()
                    .filter(userEmployeeDO -> userEmployeeDO.getSysuserId() != userId)
                    .map(UserEmployeeDO::getSysuserId)
                    .collect(Collectors.toList());
            LocalDateTime beforeFetchUserThreeDepthEmployee = LocalDateTime.now();
            logger.info("neo4j fetchUserThreeDepthEmployeeInternal before forwardNeo4jDao.fetchUserThreeDepthEmployee datetime:{}", beforeFetchUserThreeDepthEmployee.toString());
            List<EmployeeCompanyVO> postUserIdList = userNeo4jDao.fetchUserThreeDepthEmployee(userId, rootUserIdList);
            LocalDateTime afterFetchUserThreeDepthEmployee = LocalDateTime.now();
            logger.info("neo4j fetchUserThreeDepthEmployeeInternal after forwardNeo4jDao.fetchUserThreeDepthEmployee datetime:{}, time:{}", LocalDateTime.now().toString(), Duration.between(beforeFetchUserThreeDepthEmployee, afterFetchUserThreeDepthEmployee).toMillis());

            return postUserIdList;
        } else {
            return new ArrayList<>(0);
        }
    }

    private boolean updateUserEmployeeCompanyInternal(List<Integer> userIds, int companyId) throws CommonException {
        if(StringUtils.isEmptyList(userIds) || companyId<0){
            return false;
        }
        LocalDateTime beforeUpdateUserEmployeeCompanyList = LocalDateTime.now();
        logger.info("neo4j updateUserEmployeeCompanyInternal before forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}", beforeUpdateUserEmployeeCompanyList);
        userNeo4jDao.updateUserEmployeeCompanyList(userIds, companyId);
        LocalDateTime afterUpdateUserEmployeeCompanyList = LocalDateTime.now();
        logger.info("neo4j updateUserEmployeeCompanyInternal after forwardNeo4jDao.updateUserEmployeeCompanyList datetime:{}, time:{}", afterUpdateUserEmployeeCompanyList, Duration.between(beforeUpdateUserEmployeeCompanyList, afterUpdateUserEmployeeCompanyList).toMillis());
        return true;

    }

    /**
     * 查找两个节点间的最短路径
     * @param startUserId 开始节点
     * @param endUserId 结束节点
     * @param companyId 公司编号，用于关系的类型过滤
     * @return 最短路径
     * @throws CommonException
     */
    private List<Integer> fetchShortestPathInternal(int startUserId, int endUserId, int companyId) throws CommonException {
        LocalDateTime startFetchShortestPathInternal = LocalDateTime.now();
        logger.info("neo4j addFriendRelationInternal before getTwoUserShortFriend datetime:{}", startFetchShortestPathInternal);
        List<Relation> relationList = forwardNeo4jDao.getTwoUserShortFriend(startUserId, endUserId, companyId);
        LocalDateTime endFetchShortestPathInternal = LocalDateTime.now();
        logger.info("neo4j addFriendRelationInternal after getTwoUserShortFriend datetime:{}, time:{}",
                endFetchShortestPathInternal,
                Duration.between(startFetchShortestPathInternal, endFetchShortestPathInternal).toMillis());
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

    /**
     * 添加节点的朋友关系
     * @param startUserId 起始节点
     * @param endUserId 结束节点
     * @param shareChainId 分享链路的编号
     * @throws CommonException 业务异常
     */
    private void addFriendRelationInternal(int startUserId, int endUserId, int shareChainId) throws CommonException {
        /**
         * 不允许建立自己和自己的关系
         */
        if (startUserId == endUserId) {
            throw CommonException.PROGRAM_EXCEPTION;
        }
        LocalDateTime beforeInsertIfNotExistNode = LocalDateTime.now();
        logger.info("neo4j addFriendRelationInternal before insertIfNotExistNode datetime:{}", beforeInsertIfNotExistNode);
        List<UserNode> userNodes = insertIfNotExistNode(startUserId, endUserId);
        LocalDateTime afterInsertIfNotExistNode = LocalDateTime.now();
        logger.info("neo4j addFriendRelationInternal after insertIfNotExistNode datetime:{}", afterInsertIfNotExistNode);
        UserNode firstUserStatus;
        UserNode secordUserStatus;
        if (userNodes != null && userNodes.size() == NODE_NUM) {
            firstUserStatus = userNodes.get(0);
            secordUserStatus = userNodes.get(1);
        } else {
            throw CommonException.PROGRAM_EXCEPTION;
        }

        CandidateShareChainDO chain = candidateShareChainDao.getCandidateShareChainById(shareChainId);
        if (chain != null && firstUserStatus != null && secordUserStatus != null) {
            LocalDateTime beforeGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j addFriendRelationInternal before forwardNeo4jDao.getTwoUserFriend  datetime:{}", beforeGetTwoUserFriend);
            List<Forward> forwards = forwardNeo4jDao.getTwoUserFriend(startUserId, endUserId, chain.getPositionId());
            LocalDateTime afterGetTwoUserFriend = LocalDateTime.now();
            logger.info("neo4j addFriendRelationInternal after forwardNeo4jDao.getTwoUserFriend  datetime:{}", afterGetTwoUserFriend);
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
                logger.info("neo4j addFriendRelationInternal before forwardNeo4jDao.save datetime:{}", beforeSave);
                Forward forwardConnection = forwardNeo4jDao.save(forward);
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j addFriendRelationInternal after forwardNeo4jDao.save datetime:{}", afterSave);
                kafkaSender.sendForwardView(chain);
                logger.debug("proceed friend:" + JSON.toJSONString(forwardConnection));
            }
        }
    }

    /**
     * 添加节点关联关系
     * @param startUserId 开始节点
     * @param endUserId 结束节点
     * @param connChainId 链路关系编号
     * @param positionId 职位编号
     */
    private void addConnRelationInternal(int startUserId, int endUserId, int connChainId, int positionId) {
        /**
         * 不允许建立自己和自己的关系
         */
        if (startUserId == endUserId) {
            throw CommonException.PROGRAM_EXCEPTION;
        }
        LocalDateTime beforeInsertIfNotExistNode = LocalDateTime.now();
        logger.info("neo4j addConnRelationInternal before insertIfNotExistNode datetime:{}", beforeInsertIfNotExistNode);
        List<UserNode> userNodes = insertIfNotExistNode(startUserId, endUserId);
        LocalDateTime afterInsertIfNotExistNode = LocalDateTime.now();
        logger.info("neo4j addConnRelationInternal after insertIfNotExistNode datetime:{}", afterInsertIfNotExistNode);
        UserNode firstUserStatus;
        UserNode secordUserStatus;
        if (userNodes != null && userNodes.size() == NODE_NUM) {
            firstUserStatus = userNodes.get(0);
            secordUserStatus = userNodes.get(1);
        } else {
            throw CommonException.PROGRAM_EXCEPTION;
        }
        if (firstUserStatus != null && secordUserStatus != null) {
            Connection conn = new Connection();
            conn.setConn_chain_id(connChainId);
            conn.setStartNode(firstUserStatus);
            conn.setEndNode(secordUserStatus);
            conn.setPosition_id(positionId);
            LocalDateTime beforeGetTwoUserConn = LocalDateTime.now();
            logger.info("neo4j addConnRelationInternal before connNeo4jDao.getTwoUserConn datetime:{}", beforeGetTwoUserConn);
            List<Connection> conns = connNeo4jDao.getTwoUserConn(startUserId, endUserId, positionId);
            LocalDateTime afterGetTwoUserConn = LocalDateTime.now();
            logger.info("neo4j addConnRelationInternal after connNeo4jDao.getTwoUserConn datetime:{}", afterGetTwoUserConn);
            if (StringUtils.isEmptyList(conns)) {
                LocalDateTime beforeSave = LocalDateTime.now();
                logger.info("neo4j addConnRelationInternal before connNeo4jDao.save datetime:{}", beforeSave);
                Connection connection = connNeo4jDao.save(conn);
                LocalDateTime afterSave = LocalDateTime.now();
                logger.info("neo4j addConnRelationInternal after connNeo4jDao.save datetime:{}", afterSave);
                kafkaSender.sendConnectionLink(conn, endUserId);
                logger.debug("proceed connection:" + JSON.toJSONString(connection));
            }
        }
    }

    /**
     * 异步处理业务
     * 1. 记录执行时间
     * 2. 如果超时，记录超时时的参数
     * 3. 如果发生其他异常，
     * @param method 方法名称
     * @param callable 线程任务
     * @param <T> 返回值类型
     * @return 任务执行结果
     * @throws CommonException 业务异常
     */
    private <T> T handlerFutureTask(String method, Callable<T> callable, Object... params)
            throws CommonException {
        LocalDateTime startTime = LocalDateTime.now();
        logger.info("neo4j handlerFutureTask start. method:{}, startTime:{}", method, startTime);
        Future<T> future = tp.startSubmit(callable);
        T t;
        try {
            t = future.get(TIMEOUT, TimeUnit.SECONDS);
            return t;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage(), e);
            throw UserAccountException.USER_NEO4J_TASK_INTERRUPTED;
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw UserAccountException.USER_NEO4J_TASK_EXECUTION_FAILED;
        } catch (TimeoutException e) {
            future.cancel(true);
            String poolInfo = tp.getPoolInfo();
            logger.info("neo4j handlerFutureTask timeout! method:{} poolInfo:{}, params:{}", method, poolInfo, JSONObject.toJSONString(params));
            throw UserAccountException.USER_NEO4J_TASK_EXECUTION_TIMEOUT;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            logger.info("neo4j handlerFutureTask Exception! method:{} params:{}", method, JSONObject.toJSONString(params));
            logger.error(e.getMessage(), e);
            throw UserAccountException.PROGRAM_EXCEPTION;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logger.info("neo4j handlerFutureTask end. method:{}, endTime:{}. duration:{}", method, endTime, Duration.between(startTime, endTime).toMillis());
        }
    }
}