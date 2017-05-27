package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.userdb.UserCollectPositionDao;
import com.moseeker.baseorm.dao.userdb.UserSearchConditionDao;
import com.moseeker.baseorm.dao.userdb.UserViewedPositionDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserCollectPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserViewedPositionDO;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lucky8987 on 17/4/20.
 */
@Service
public class UserQxService {

    private static final Logger logger = LoggerFactory.getLogger(UserQxService.class);

    @Autowired
    private UserCollectPositionDao collectPositionDao;

    @Autowired
    private UserSearchConditionDao searchConditionDao;

    @Autowired
    private UserViewedPositionDao userViewedPositionDao;

    @Autowired
    private JobApplicationDao jobApplicationDao;

    /**
     * 用户获取筛选条件列表
     * @param userId
     * @return
     * @throws TException
     */
    public UserSearchConditionListVO userSearchConditionList(int userId) throws TException {
        logger.info("[Thread-id = {}] getUserSearchCondition params: userId = {}", Thread.currentThread().getId(), userId);
        UserSearchConditionListVO result = new UserSearchConditionListVO();
        result.setSearchConditionList(new ArrayList<>());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        Query.QueryBuilder query;
        try {
            query = new Query.QueryBuilder();
            query.where("user_id", String.valueOf(userId));
            query.and("disable", String.valueOf(0)); // 0: 不禁用 1: 禁用
            result.setSearchConditionList(searchConditionDao.getDatas(query.buildQuery()));
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("[Thread-id = {}] getUserSearchCondition response: {}", Thread.currentThread().getId(), result);
        return result;
    }

    /**
     * 用户添加常用筛选条件
     * @param userSearchCondition
     * @return
     * @throws TException
     */
    public UserSearchConditionVO postUserSearchCondition(UserSearchConditionDO userSearchCondition) throws TException {
        logger.info("postUserSearchCondition params: userSearchCondition={}", userSearchCondition);
        UserSearchConditionVO result = new UserSearchConditionVO();
        result.setSearchCondition(new UserSearchConditionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            if (userSearchCondition.getUserId() == 0 || StringUtils.isNullOrEmpty(userSearchCondition.getKeywords()) || StringUtils.isNullOrEmpty(userSearchCondition.getName())) {
                logger.error("postUserSearchCondition 请求参数为空，请检查相关参数, userSearchCondition={}", userSearchCondition);
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            } else {
                userSearchCondition = searchConditionDao.addData(userSearchCondition);
                if (userSearchCondition != null && userSearchCondition.getId() > 0) {
                    result.setSearchCondition(userSearchCondition);
                } else {
                    jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
                }
            }
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("postUserSearchCondition response: {}", result);
        return result;
    }

    /**
     * 用户删除常用筛选条件
     * @param userId
     * @param id
     * @return
     * @throws TException
     */
    public UserSearchConditionVO delUserSearchCondition(int userId, int id) throws TException {
        logger.info("delUserSearchCondition params: userId={}, id={}", userId, id);
        Query.QueryBuilder query = new Query.QueryBuilder();
        UserSearchConditionVO result = new UserSearchConditionVO();
        result.setSearchCondition(new UserSearchConditionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            if (userId == 0 || id == 0) {
                logger.error("delUserSearchCondition 请求参数为空，请检查相关参数, userId={}, id={}", userId, id);
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            } else {
                query.where("user_id", String.valueOf(userId));
                query.and("id", String.valueOf(id));
                UserSearchConditionDO condition = searchConditionDao.getData(query.buildQuery());
                if(condition != null && condition.getId() > 0) {
                    if (condition.getDisable() == 0) {
                        searchConditionDao.updateData(condition.setDisable((byte) 1));
                    }
                    result.setSearchCondition(condition);
                } else {
                    jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
                    logger.error("用户(user_id={})不存在该筛选项(筛选项id={})", userId, id);
                }
            }
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("delUserSearchCondition response: {}", result);
        return result;
    }

    /**
     * 用户查询收藏的职位
     * @param userId
     * @param positionId
     * @return
     * @throws TException
     */
    public UserCollectPositionVO getUserCollectPosition(int userId, int positionId) throws TException {
        logger.info("getUserCollectPosition params: userId={}, positionId={}", userId, positionId);
        UserCollectPositionVO result = new UserCollectPositionVO();
        result.setUserCollectPosition(new UserCollectPositionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("user_id", String.valueOf(userId));
            query.and("position_id", String.valueOf(positionId));
            UserCollectPositionDO entity = collectPositionDao.getData(query.buildQuery());
            if (entity != null && entity.getId() > 0) {
                result.setUserCollectPosition(entity);
            } else {
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", "未找到收藏记录"));
            }
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("getUserCollectPosition response: {}", result);
        return result;
    }

    /**
     * 用户收藏职位
     * @param userId
     * @param positionId
     * @param status
     * @return
     * @throws TException
     */
    public UserCollectPositionVO putUserCollectPosition(int userId, int positionId, int status) throws TException {
        logger.info("putUserCollectPosition params: userId={}, positionId={}, status={}", userId, positionId, status);
        UserCollectPositionVO result = new UserCollectPositionVO();
        result.setUserCollectPosition(new UserCollectPositionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            if (userId == 0 || positionId == 0) {
                logger.error("putUserCollectPosition 请求参数为空，请检查相关参数, userId={}, positionId={}, status={}", userId, positionId, status);
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            } else {
                Query.QueryBuilder query = new Query.QueryBuilder();
                query.where("user_id", String.valueOf(userId));
                query.and("position_id", String.valueOf(positionId));
                UserCollectPositionDO entity = collectPositionDao.getData(query.buildQuery());
                if (entity != null && entity.getId() > 0) {
                    if (entity.getStatus() == status) {
                        result.setUserCollectPosition(entity);
                    } else {
                        entity.setStatus(status);
                        entity.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
                        int resultRow = collectPositionDao.updateData(entity);
                        if (resultRow == 0) {
                            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                        } else {
                            result.setUserCollectPosition(entity);
                        }
                    }
                } else {
                    entity = new UserCollectPositionDO();
                    entity.setStatus(status);
                    entity.setUserId(userId);
                    entity.setPositionId(positionId);
                    entity.setCreateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
                    result.setUserCollectPosition(collectPositionDao.addData(entity));
                }
            }
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("putUserCollectPosition response: {}", result);
        return result;
    }

    /**
     * 批量获取用户与职位的状态<br/>
     * 状态 {0: 未阅，1：已阅，2：已收藏，3：已投递}
     * @param userId
     * @param positionIds
     * @return
     * @throws TException
     */
    public UserPositionStatusVO getUserPositionStatus(int userId, List<Integer> positionIds) throws TException {
        logger.info("getUserPositionStatus params: userId={}, positionIds={}", userId, Arrays.toString(positionIds.toArray()));
        UserPositionStatusVO result = new UserPositionStatusVO();
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        Query.QueryBuilder query = new Query.QueryBuilder();
        Map<Integer, Integer> positionStatus = new HashMap<>();
        result.setPositionStatus(positionStatus);
        try {
            if (userId == 0 || StringUtils.isEmptyObject(positionIds)) {
                logger.error("getUserPositionStatus 请求参数为空，请检查相关参数, userId={}, positionIds={}", userId, Arrays.toString(positionIds.toArray()));
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            } else {
                // 查询已投递的职位
                query.where("applier_id", String.valueOf(userId));
                query.and(new Condition("position_id", positionIds, ValueOp.IN));
                query.and("disable", String.valueOf(0));
                List<JobApplicationDO> applications = jobApplicationDao.getApplications(query.buildQuery());
                Map<Integer, Integer> isApplication = applications.stream().map(m -> m.getPositionId()).collect(Collectors.toMap(k -> k, v -> 3));
                positionIds.removeAll(isApplication.keySet());
                // 查询已收藏的职位
                query.clear();
                query.where("user_id", String.valueOf(userId));
                query.and(new Condition("position_id", positionIds, ValueOp.IN));
                query.and("status", String.valueOf(0));
                List<UserCollectPositionDO> collectPositions = collectPositionDao.getDatas(query.buildQuery());
                Map<Integer, Integer> isCollect = collectPositions.stream().map(m -> m.getPositionId()).collect(Collectors.toMap(k -> k, v -> 2));
                positionIds.removeAll(isCollect.keySet());
                // 查询已查看的职位
                query.clear();
                query.where("user_id", String.valueOf(userId));
                query.and(new Condition("position_id", positionIds, ValueOp.IN));
                List<UserViewedPositionDO> userViewedPositions = userViewedPositionDao.getDatas(query.buildQuery());
                Map<Integer, Integer> isViewed = userViewedPositions.stream().map(m -> m.getPositionId()).collect(Collectors.toMap(k -> k, v -> 1));
                positionIds.removeAll(isViewed.keySet());
                // 剩下都是未阅读的职位
                Map<Integer, Integer> dotViewed = positionIds.stream().collect(Collectors.toMap(k -> k, v -> 0));
                // 结果汇总
                positionStatus.putAll(isApplication);
                positionStatus.putAll(isCollect);
                positionStatus.putAll(isViewed);
                positionStatus.putAll(dotViewed);
            }

        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("getUserPositionStatus response: {}", result);
        return result;
    }

    /**
     * 记录用户已阅读的职位
     * @param userId
     * @param positionId
     * @return
     * @throws TException
     */
    public UserViewedPositionVO userViewedPosition(int userId, int positionId) throws TException {
        logger.info("userViewedPosition params: userId={}, positionId={}", userId, positionId);
        UserViewedPositionVO result = new UserViewedPositionVO();
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            if (userId > 0 && positionId > 0 ){
                Query.QueryBuilder query = new Query.QueryBuilder();
                query.where("user_id", String.valueOf(userId));
                query.and("position_id", String.valueOf(positionId));
                UserViewedPositionDO userViewedPosition = userViewedPositionDao.getData(query.buildQuery());
                if (userViewedPosition == null || userViewedPosition.getId() == 0 ) {
                    UserViewedPositionDO entity = new UserViewedPositionDO();
                    entity.setUserId(userId);
                    entity.setPositionId(positionId);
                    UserViewedPositionDO viewedPositionDO = userViewedPositionDao.addData(entity);
                    if (viewedPositionDO == null || viewedPositionDO.getId() <= 0) {
                        jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
                    }
                }
            } else {
                logger.error("userViewedPosition 请求参数为空，请检查相关参数, userId={}, positionId={}", userId, positionId);
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("userViewedPosition response: {}", result);
        return result;
    }
}
