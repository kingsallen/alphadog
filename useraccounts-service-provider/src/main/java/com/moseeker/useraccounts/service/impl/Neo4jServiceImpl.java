package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.PositionEntity;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.kafka.KafkaSender;
import com.moseeker.useraccounts.pojo.neo4j.*;
import com.moseeker.useraccounts.repository.ConnectionNeo4jDao;
import com.moseeker.useraccounts.repository.ForwardNeo4jDao;
import com.moseeker.useraccounts.repository.UserNeo4jDao;
import com.moseeker.useraccounts.service.Neo4jService;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public void addFriendRelation(int startUserId, int endUserId, int shareChainId) throws CommonException {
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
            List<Forward> forwards = forwardNeo4jDao.getTwoUserFriend(startUserId, endUserId, chain.getPositionId());
            if (StringUtils.isEmptyList(forwards)) {
                Forward forwar = forwardNeo4jDao.save(forward);
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
            List<Connection> conns = connNeo4jDao.getTwoUserConn(startUserId, endUserId, positionId);
            if (StringUtils.isEmptyList(conns)) {
                Connection connection = connNeo4jDao.save(conn);
                kafkaSender.sendConnectionLink(conn, endUserId);
                logger.info("proceed forward:" + JSON.toJSONString(connection));
            }
        }
    }

    @Override
    public List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId) throws CommonException {
        List<Relation> relationList = forwardNeo4jDao.getTwoUserShortFriend(startUserId, endUserId, companyId);
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
        UserNode node = userNeo4jDao.updateUserEmployeeCompany(userId, companyId);
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
        userNeo4jDao.updateUserEmployeeCompanyList(userIds, companyId);
    }

    @Override
    public List<EmployeeCompanyVO> fetchUserThreeDepthEmployee(int userId, int companyId) throws CommonException {
        List<Integer> rootUserList = candidateShareChainDao.fetchRootIdByPresentee(userId);
        if(StringUtils.isEmptyList(rootUserList)){
            return new ArrayList<>();
        }
        if(companyId >0) {
            List<UserEmployeeDO> employeeList = employeeDao.getActiveEmployee(rootUserList, companyId);
            rootUserList.clear();
            if(StringUtils.isEmptyList(employeeList)){
                return new ArrayList<>();
            }
            rootUserList = employeeList.stream().map(m -> m.getSysuserId()).collect(Collectors.toList());
        }
        List<EmployeeCompanyVO> postUserIdList = userNeo4jDao.fetchUserThreeDepthEmployee(userId, rootUserList);
        return postUserIdList;
    }

    @Override
    public List<UserDepthVO> fetchEmployeeThreeDepthUser(int userId) throws CommonException {
        UserEmployeeDO employee = employeeEntity.getActiveEmployeeDOByUserId(userId);
        if(employee == null){
            throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
        }
        List<Integer> list = new ArrayList<>();
        list.add(employee.getCompanyId());
        List<Integer> companyIds = companyRelDao.getGroupCompanyRelDoByCompanyIds(list);
        List<Integer> positionIds = positionEntity.getPositionIdListByCompanyIdListAndStatus(companyIds);
        List<Integer> peresentUserIdList = candidateShareChainDao.fetchRootIdByRootUserId(userId, positionIds);
        if(StringUtils.isEmptyList(peresentUserIdList)){
            return new ArrayList<>();
        }
        if(employee == null ){
            return new ArrayList<>();
        }
        try {
            List<UserDepthVO> depthUser = userNeo4jDao.fetchEmployeeThreeDepthUser(userId, peresentUserIdList, employee.getCompanyId());
            return depthUser;
        }catch (Exception e){
            throw UserAccountException.NEO4J_STATUS_ERROR;
        }

    }

    @Override
    public List<UserDepthVO> fetchDepthUserList(int userId, int companyId, List<Integer> userIdList) throws CommonException {
        if(userId >0 && companyId >0 && !StringUtils.isEmptyList(userIdList)){
            try {
                List<UserDepthVO> list = userNeo4jDao.fetchDepthUserList(userId, userIdList, companyId);
                return list;
            }catch (Exception e){
                logger.error(e.getMessage()+"userid:{}, userIdList:{}, companyId:{}", userId, userIdList, companyId);

            }
        }
        return new ArrayList<>();
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
            UserNode nodes = userNeo4jDao.save(node);
            logger.info(JSON.toJSONString(nodes));
            return node;
        }
        return null;
    }
}
