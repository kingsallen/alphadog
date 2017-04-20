package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.userdb.UserCollectPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserCollectPositionVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserSearchConditionListVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserSearchConditionVO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lucky8987 on 17/4/20.
 */
@Service
public class UserQxService {

    private static final Logger logger = LoggerFactory.getLogger(UserQxService.class);

    com.moseeker.thrift.gen.dao.service.UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.UserDBDao.Iface.class);

    public UserSearchConditionListVO userSearchConditionList(int userId) throws TException {
        logger.info("[Thread-id = {}] getUserSearchCondition params: userId = {}", Thread.currentThread().getId(), userId);
        UserSearchConditionListVO result = new UserSearchConditionListVO();
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        CommonQuery query = null;
        try {
            query = new CommonQuery();
            query.setEqualFilter(new HashMap<>());
            query.getEqualFilter().put("user_id", String.valueOf(userId));
            query.getEqualFilter().put("disable", String.valueOf(0)); // 0: 不禁用 1: 禁用
            result.setSearchConditionList(userDao.getUserSearchConditions(query));
        } catch (Exception e) {
            jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            logger.error(e.getMessage(), e);
        }
        result.setStatus(jsonObject.getIntValue("status"));
        result.setMessage(jsonObject.getString("message"));
        logger.info("[Thread-id = {}] getUserSearchCondition response: {}", Thread.currentThread().getId(), result);
        return result;
    }

    public UserSearchConditionVO postUserSearchCondition(UserSearchConditionDO userSearchCondition) throws TException {
        logger.info("postUserSearchCondition params: userSearchCondition={}", userSearchCondition);
        UserSearchConditionVO result = new UserSearchConditionVO();
        result.setSearchCondition(new UserSearchConditionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            userSearchCondition = userDao.saveUserSearchCondition(userSearchCondition);
            if (userSearchCondition != null && userSearchCondition.getId() > 0) {
                result.setSearchCondition(userSearchCondition);
            } else {
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
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

    public UserSearchConditionVO delUserSearchCondition(int userId, int id) throws TException {
        logger.info("delUserSearchCondition params: userId={}, id={}", userId, id);
        CommonQuery query = new CommonQuery();
        UserSearchConditionVO result = new UserSearchConditionVO();
        result.setSearchCondition(new UserSearchConditionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            query.setEqualFilter(new HashMap<>());
            query.getEqualFilter().put("user_id", String.valueOf(userId));
            query.getEqualFilter().put("id", String.valueOf(id));
            List<UserSearchConditionDO> conditions = userDao.getUserSearchConditions(query);
            if(conditions != null && conditions.get(0) != null && conditions.get(0).getId() != 0) {
                result.setSearchCondition(conditions.get(0).getDisable() == 1 ? conditions.get(0) : userDao.updateUserSearchCondition(conditions.get(0)).setDisable((byte)1));
            } else {
                jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
                logger.error("用户(user_id={})不存在该筛选项(筛选项id={})", userId, id);
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

    public UserCollectPositionVO getUserCollectPosition(int userId, int positionId) throws TException {
        logger.info("getUserCollectPosition params: userId={}, positionId={}", userId, positionId);
        UserCollectPositionVO result = new UserCollectPositionVO();
        result.setUserCollectPosition(new UserCollectPositionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(new HashMap<>());
            query.getEqualFilter().put("user_id", String.valueOf(userId));
            query.getEqualFilter().put("positionId", String.valueOf(positionId));
            UserCollectPositionDO entity = userDao.getUserCollectPosition(query);
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

    public UserCollectPositionVO putUserCollectPosition(int userId, int positionId, int status) throws TException {
        logger.info("putUserCollectPosition params: userId={}, positionId={}, status={}", userId, positionId, status);
        UserCollectPositionVO result = new UserCollectPositionVO();
        result.setUserCollectPosition(new UserCollectPositionDO());
        JSONObject jsonObject = JSONObject.parseObject(ConstantErrorCodeMessage.SUCCESS);
        try {
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(new HashMap<>());
            query.getEqualFilter().put("user_id", String.valueOf(userId));
            query.getEqualFilter().put("positionId", String.valueOf(positionId));
            UserCollectPositionDO entity = userDao.getUserCollectPosition(query);
            if (entity != null && entity.getId() > 0) {
                if (entity.getStatus() == status) {
                    result.setUserCollectPosition(entity);
                } else {
                    entity.setStatus(status);
                    entity.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
                    result.setUserCollectPosition(userDao.updateUserCollectPosition(entity));
                }
            } else {
                entity = new UserCollectPositionDO();
                entity.setStatus(status);
                entity.setUserId(userId);
                entity.setPositionId(positionId);
                entity.setCreateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
                result.setUserCollectPosition(userDao.saveUserCollectPosition(entity));
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
}
