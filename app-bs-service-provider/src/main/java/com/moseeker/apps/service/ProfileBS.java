package com.moseeker.apps.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ProfileBS {

    UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICEMANAGER
            .getService(UseraccountsServices.Iface.class);

    WholeProfileServices.Iface wholeProfileService = ServiceManager.SERVICEMANAGER
            .getService(WholeProfileServices.Iface.class);

    JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
            .getService(JobApplicationServices.Iface.class);


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private UserUserDao userUserDao;

    @SuppressWarnings("unchecked")
    @CounterIface
    public Response retrieveProfile(int positionId, String profile, int channel) throws TException {

        logger.info("ProfileBS retrieveProfile positionId:{}, channel:{}", positionId, channel);

        if (positionId == 0 || StringUtils.isNullOrEmpty(profile)) {
            return ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse();
        }
        Query qu = new Query.QueryBuilder().where("id", positionId).buildQuery();
        Position position;
        try {
            position = jobPositionDao.getData(qu, Position.class);
            logger.info("ProfileBS retrieveProfile position:{}", position);
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error(e1.getMessage(), e1);
            return ResultMessage.PROGRAM_EXCEPTION.toResponse();
        } finally {
            //do nothing
        }
        if (position == null) {
            return ResultMessage.POSITION_NOT_EXIST.toResponse();
        }
        Map<String, Object> resume = JSON.parseObject(profile);
        Map<String, Object> map = (Map<String, Object>) resume.get("user");
        String mobile = (String) map.get("mobile");
        logger.info("ProfileBS retrieveProfile mobile:{}", mobile);
        if (StringUtils.isNullOrEmpty(mobile)) {
            return ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse();
        }

        //更新profile数据
        resume.put("channel", channel);
//		try {
        //查询是否存在相同手机号码的C端帐号
        Query findRetrieveUserQU = new Query.QueryBuilder().where("mobile", mobile).and("source", UserSource.RETRIEVE_PROFILE.getValue()).buildQuery();
        UserUserDO user = userUserDao.getData(findRetrieveUserQU); //userDao.getUser(findRetrieveUserQU);
        logger.info("ProfileBS retrieveProfile user:{}", user);
        if (user == null) {
            user = new UserUserDO();
        }
        if (user.getId() > 0) {
            logger.info("ProfileBS retrieveProfile user exist");
            //查找该帐号是否有profile
            int origin = ApplicationSource.channelToOrigin(channel);
            JobApplication application = initApplication(user.getId(), positionId, position.getCompany_id(), origin);
            logger.info("ProfileBS retrieveProfile application:{}", application);
            //更新用户数据
            map.put("id", user.getId());
            HashMap<String, Object> profileProfile = new HashMap<String, Object>();
            profileProfile.put("user_id", user.getId());
            profileProfile.put("source", 0);
            resume.put("profile", profileProfile);

            //如果有profile，进行profile合并
            if (useraccountsServices.ifExistProfile(mobile)) {
                logger.info("ProfileBS retrieveProfile profile exist");
                Response improveProfile = wholeProfileService.improveProfile(JSON.toJSONString(resume));
                if (improveProfile.getStatus() == 0) {
                    Response getApplyResult = applicationService.getApplicationByUserIdAndPositionId(user.getId(), positionId, position.getCompany_id());
                    if (getApplyResult.getStatus() == 0 && !Boolean.valueOf(getApplyResult.getData())) {
                        Response response = applicationService.postApplication(application);
                        return response;
                    }
                    return ResultMessage.SUCCESS.toResponse();
                } else {
                    return improveProfile;
                }
            } else {
                logger.info("ProfileBS retrieveProfile profile not exist");
                //如果不存在profile，进行profile创建
                Response response = wholeProfileService.createProfile(JSON.toJSONString(resume));
                if (response.getStatus() == 0) {
                    Response getApplyResult = applicationService.getApplicationByUserIdAndPositionId(user.getId(), positionId, position.getCompany_id());
                    if (getApplyResult.getStatus() == 0 && !Boolean.valueOf(getApplyResult.getData())) {
                        applicationService.postApplication(application);
                    }
                    return ResultMessage.SUCCESS.toResponse();
                } else {
                    return response;
                }
            }
        } else {
            logger.info("ProfileBS retrieveProfile user not exist");
            //如果不存在C端帐号，创建帐号
            UserUserDO user1 = BeanUtils.MapToRecord(map, UserUserDO.class);
            logger.info("ProfileBS retrieveProfile user:{}", user1);
            user1.setSource((byte) UserSource.RETRIEVE_PROFILE.getValue());
            int userId = useraccountsServices.createRetrieveProfileUser(user1);
            logger.info("ProfileBS retrieveProfile userId:{}", userId);
            //创建profile
            if (userId > 0) {
                map.put("id", userId);

                HashMap<String, Object> profileProfile = new HashMap<String, Object>();
                profileProfile.put("user_id", userId);
                profileProfile.put("source", 0);
                resume.put("profile", profileProfile);

                Response response = wholeProfileService.createProfile(JSON.toJSONString(resume));
                logger.info("ProfileBS retrieveProfile response:{}", response);
                //创建申请
                if (response.getStatus() == 0) {
                    // 判断来源
                    int origin = ApplicationSource.channelToOrigin(channel);
                    JobApplication application = initApplication(userId, positionId, position.getCompany_id(), origin);
                    Response getApplyResult = applicationService.getApplicationByUserIdAndPositionId(userId, positionId, position.getCompany_id());
                    if (getApplyResult.getStatus() == 0 && !Boolean.valueOf(getApplyResult.getData())) {
                        applicationService.postApplication(application);
                    }
                    return ResultMessage.SUCCESS.toResponse();
                } else {
                    return response;
                }
            }
        }
//		} catch (TException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e);
//		} finally {
//			//do nothing
//		}
        return ResponseUtils.success(null);
    }


    private JobApplication initApplication(int applierId, int positionId, int companyId, int origin) {
        JobApplication application = new JobApplication();
        application.setPosition_id(positionId);
        application.setApplier_id(applierId);
        application.setCompany_id(companyId);
        application.setOrigin(origin);
        return application;
    }

    public UseraccountsServices.Iface getUseraccountsServices() {
        return useraccountsServices;
    }

    public void setUseraccountsServices(UseraccountsServices.Iface useraccountsServices) {
        this.useraccountsServices = useraccountsServices;
    }

    public WholeProfileServices.Iface getWholeProfileService() {
        return wholeProfileService;
    }

    public void setWholeProfileService(WholeProfileServices.Iface wholeProfileService) {
        this.wholeProfileService = wholeProfileService;
    }

    public JobApplicationServices.Iface getApplicationService() {
        return applicationService;
    }

    public void setApplicationService(JobApplicationServices.Iface applicationService) {
        this.applicationService = applicationService;
    }

}
