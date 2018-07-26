package com.moseeker.profile.service.impl.talentpoolmvhouse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.config.Sender;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveConstant;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseOperationVO;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.talentpool.struct.ProfileMoveForm;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 简历搬家service
 *
 * @author cjm
 * @date 2018-07-18 11:55
 **/
@Service
public class ProfileMoveService {

    private Logger logger = LoggerFactory.getLogger(ProfileMoveService.class);

    private SerializeConfig serializeConfig = new SerializeConfig();

    private final Sender sender;

    private final UserUserDao userUserDao;

    private final TalentPoolProfileMoveDao profileMoveDao;

    private final UserHrAccountDao userHrAccountDao;

    private final HRThirdPartyAccountDao hrThirdPartyAccountDao;

    private final WholeProfileService wholeProfileService;

    private ThreadPool pool = ThreadPool.Instance;

    private static final int TRY_TIMES = 3;

    @Autowired
    public ProfileMoveService(Sender sender, UserUserDao userUserDao, TalentPoolProfileMoveDao profileMoveDao,
                              UserHrAccountDao userHrAccountDao, HRThirdPartyAccountDao hrThirdPartyAccountDao,
                              WholeProfileService wholeProfileService) {
        this.sender = sender;
        this.userUserDao = userUserDao;
        this.profileMoveDao = profileMoveDao;
        this.userHrAccountDao = userHrAccountDao;
        this.hrThirdPartyAccountDao = hrThirdPartyAccountDao;
        this.wholeProfileService = wholeProfileService;
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    /**
     * 简历搬家用户登录
     *
     * @param form 简历搬家所需参数
     * @return response 是chaos返回的结果，没有对结果处理
     * @author cjm
     * @date 2018/7/18
     */
    @Transactional
    public Response moveHouseLogin(ProfileMoveForm form) throws BIZException {
        logger.info("====================form:{}", form);
        int hrId = form.getHr_id();
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if (userHrAccountDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_NOT_EXISTS);
        }
        // 验证hr_id和company_id是否正确
        if (form.getCompany_id() != userHrAccountDO.getCompanyId()) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_INFO_ERROR);
        }
        // 获取用户名密码
        int channel = form.getChannel();
        HrThirdPartyAccountDO hrThirdPartyAccountDO = hrThirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, channel);
        if (hrThirdPartyAccountDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        requireValidDateFormat(form);
        TalentpoolProfileMoveRecord record = JSONObject.parseObject(JSON.toJSONString(form), TalentpoolProfileMoveRecord.class);
        record = profileMoveDao.insertInitDO(record);
        // 映射简历搬家参数
        MvHouseVO mvHouseVO = createLoginParams(userHrAccountDO, hrThirdPartyAccountDO, record, form);

        sender.sendMqRequest(mvHouseVO, ProfileMoveConstant.PROFILE_MOVE_ROUTING_KEY_REQUEST, ProfileMoveConstant.PROFILE_MOVE_EXCHANGE_NAME);

        logger.info("推送RabbitMQ成功");

        return ResponseUtils.success(new HashMap<>(1 >> 4));
    }

    /**
     * 检验起始、结束日期格式是否正确
     * @param   form 页面填写数据
     * @author  cjm
     * @date  2018/7/25
     */
    private void requireValidDateFormat(ProfileMoveForm form) throws BIZException {
        String startDate = form.getStart_date();
        String endDate = form.getEnd_date();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.parse(startDate);
            format.parse(endDate);
        } catch (Exception e) {
            throw new BIZException(1, "传入日期格式错误");
        }

    }

    /**
     * 获取简历搬家操作记录（分页查询）
     *
     * @param hrId       hr.id
     * @param pageNumber 页码
     * @param pageSize   页面大小
     * @return response
     * @author cjm
     * @date 2018/7/18
     */
    public Response getMoveOperationList(int hrId, int pageNumber, int pageSize) {
        int rows = profileMoveDao.getTotalCount(hrId);
        int startIndex = pageSize * (pageNumber - 1);
        // 如果起始下标大于总行数，重置起始下标为最后一页第一个数据
        if (startIndex > rows) {
            pageNumber = rows / pageSize + 1;
            startIndex = pageSize * (pageNumber - 1);
        }
        // 如果起始下标等于总行数，由于limit是从0开始，所以此下标是没有数据的，再减去一个pageSize
        if (startIndex == rows) {
            startIndex = startIndex - pageSize;
        }
        List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getMoveOperationList(hrId, startIndex, pageSize);
        List<MvHouseOperationVO> operationList = new ArrayList<>();
        for (TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS) {
            byte status = profileMoveDO.getStatus();
            if (status == ProfileMoveStateEnum.MOVING.getValue()) {
                profileMoveDO.setStatusDisplay(ProfileMoveStateEnum.MOVING.getName());
            } else if (status == ProfileMoveStateEnum.SUCCESS.getValue()) {
                profileMoveDO.setStatusDisplay(ProfileMoveStateEnum.SUCCESS.getName());
            } else {
                profileMoveDO.setStatusDisplay(ProfileMoveStateEnum.FAILED.getName());
            }
            MvHouseOperationVO operationVO = new MvHouseOperationVO();
            BeanUtils.copyProperties(profileMoveDO, operationVO);
            operationList.add(operationVO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation_list", JSONArray.parseArray(JSON.toJSONString(operationList, serializeConfig)));
        jsonObject.put("total", rows);
        return ResponseUtils.success(jsonObject);
    }

    /**
     * 简历搬家
     *
     * @param profile         解析后的简历json串
     * @param operationId     profileMove表主键id
     * @param currentEmailNum 当前是第几封邮件
     * @return Response 返回是否调用成功
     * @author cjm
     * @date 2018/7/18
     */
    public Response profileMove(String profile, int operationId, int currentEmailNum) throws TException, InterruptedException, ExecutionException, TimeoutException {

        logger.info("profile:{}, operationId:{}, currentEmailNum:{}", profile, operationId, currentEmailNum);
        TalentpoolProfileMoveRecord record = profileMoveDao.getProfileMoveById(operationId);
        logger.info("=======================简历搬家record:{}", record);
        if (record == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
        }
        int companyId = record.getCompanyId();
        // 简历合并
//        Future<Response> combineFuture = pool.startTast(() -> wholeProfileService.combinationProfile(profile, companyId));
//        Response combineResponse = combineFuture.get(60, TimeUnit.SECONDS);
//        logger.info("===========================简历搬家combineResponse:{}", combineResponse);
//        if (combineResponse.getStatus() == 0) {
//            String resume = combineResponse.getData();
            UserUserDO user = getUserInfoByMobile(profile);// resume
            // 简历入库
            Future<Response> preserveFuture =
                    pool.startTast(() -> wholeProfileService.preserveProfile(profile, null, record.getHrId(), record.getCompanyId(), user.getId()));//resume
            Response preserveResponse = preserveFuture.get(60, TimeUnit.SECONDS);
            logger.info("===========================简历搬家preserveResponse:{}", preserveResponse);
            if (preserveResponse.getStatus() == 0) {
                // 邮件总数不为空、总邮件数等于当前邮件数，当前操作记录还未成功时，修改操作状态为已完成
                if (null != record.getTotalEmailNum() && currentEmailNum == record.getTotalEmailNum() && record.getStatus() != ProfileMoveStateEnum.SUCCESS.getValue()) {
                    // 如果当前邮件数等于邮件总数
                    record.setStatus(ProfileMoveStateEnum.SUCCESS.getValue());
                }
                updateProfileMove(record, currentEmailNum, 1);
                return ResponseUtils.success(new HashMap<>(1 >> 4));
            }
            return preserveResponse;
//        }
//        return combineResponse;
    }

    /**
     * 简历搬家操作记录表更新
     *
     * @param record          简历搬家操作记录record
     * @param currentEmailNum 当前简历合并操作包括的邮件数量
     * @author cjm
     * @date 2018/7/23
     */
    private void updateProfileMove(TalentpoolProfileMoveRecord record, int currentEmailNum, int retryTimes) throws BIZException {
        if (retryTimes > TRY_TIMES) {
            throw new BIZException(1, "简历搬家操作记录数据库更新失败");
        }
        record.setCurrentEmailNum(currentEmailNum);
        int row = profileMoveDao.updateRecordWithPositiveLock(record);
        if (row == 0) {
            updateProfileMove(record, currentEmailNum, ++retryTimes);
        }
    }

    /**
     * 通过手机号获取用户id，如果传来的简历手机号在库里能查得到，就认为是同一份简历
     *
     * @param resume 简历json串
     * @return UserUserDO
     * @author cjm
     * @date 2018/7/19
     */
    private UserUserDO getUserInfoByMobile(String resume) throws BIZException {
        JSONObject resumeObj = JSONObject.parseObject(resume);

        JSONObject jsonObject = resumeObj.getJSONObject("user");
        String mobile = jsonObject.getString("mobile");
        String countryCode = "86";
        logger.info("ProfileMoveService profileMove mobile:{}", mobile);
        if (StringUtils.isNullOrEmpty(mobile)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        } else {
            String[] mobileArray = mobile.split("-");
            if (mobileArray.length > 1) {
                jsonObject.put("countryCode", mobileArray[0]);
                jsonObject.put("mobile", mobileArray[1]);
                countryCode = mobileArray[0];
                mobile = mobileArray[1];
            }
        }
        Query findRetrieveUserQU = new Query.QueryBuilder().where("mobile", mobile).and("country_code", countryCode).and("source", UserSource.RETRIEVE_PROFILE.getValue()).buildQuery();
        UserUserDO user = userUserDao.getData(findRetrieveUserQU);
        logger.info("ProfileBS retrieveProfile user:{}", user);
        if (user == null) {
            user = new UserUserDO();
        }
        return user;
    }

    /**
     * 构造用户登录时需要的数据
     *
     * @param userHrAccountDO       userHrAccount表
     * @param hrThirdPartyAccountDO hrThirdPartyAccount表
     * @param record                profileMove表
     * @param form                  简历搬家所需参数的vo
     * @return MvHouseVO 简历搬家所需参数的vo
     * @author cjm
     * @date 2018/7/18
     */
    private MvHouseVO createLoginParams(UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO, TalentpoolProfileMoveRecord record, ProfileMoveForm form) {
        MvHouseVO mvHouseVO = new MvHouseVO();
        mvHouseVO.setAccount_id(form.getHr_id());
        mvHouseVO.setChannel(form.getChannel());
        mvHouseVO.setMember_name(hrThirdPartyAccountDO.getExt());
        mvHouseVO.setMobile(userHrAccountDO.getMobile());
        mvHouseVO.setOperation_id(record.getId());
        mvHouseVO.setUser_name(hrThirdPartyAccountDO.getUsername());
        mvHouseVO.setPassword(hrThirdPartyAccountDO.getPassword());
        mvHouseVO.setOperation_info(JSON.toJSONString(form));
        return mvHouseVO;
    }

//    /**
//     * 如果返回状态码是100，说明需要短信验证，不需要删除redis缓存
//     *
//     * @param data     chaos返回数据
//     * @param cacheKey redis的key值
//     * @return JSONObject chaos返回值的格式转换
//     * @author cjm
//     * @date 2018/7/18
//     */
//    private JSONObject handleResult(String data, String cacheKey) {
//        JSONObject result = JSONObject.parseObject(data);
//        int status = result.getIntValue("status");
//        if (status != 100) {
//            // 如果不需要验证，则删除redis缓存，需要验证的话，在验证时删除缓存
//            redisClient.del(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER, cacheKey);
//        } else{
//            // status == 100 需要短信验证，发送消息模板
//        }
//        return result;
//    }
//
//    /**
//     * 检验redis缓存是否存在
//     *
//     * @param appid      项目id
//     * @param identifier key修饰符
//     * @param key        redis的key
//     * @param timeout    超时时间
//     * @author cjm
//     * @date 2018/7/18
//     */
//    private void checkRedisCache(int appid, String identifier, String key, int timeout) throws BIZException {
//        long check = redisClient.incrIfNotExist(appid, identifier, key);
//        if (check > 1) {
//            //绑定中
//            throw new BIZException(-1, "正在尝试绑定该账号，请5分钟后再次尝试");
//        }
//        redisClient.expire(appid, identifier, key, timeout);
//    }
//
//    /**
//     * 获取redis的key值
//     *
//     * @param form 简历搬家所需参数的vo
//     * @return redis的key值
//     * @author cjm
//     * @date 2018/7/18
//     */
//    private String getCacheKey(ProfileMoveForm form) {
//        return form.getCompany_id() + "_" + form.getChannel() + "_" + form.getUsername();
//    }
}
