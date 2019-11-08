package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateRecomRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.referraldb.ReferralEmployeeNetworkResourcesDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeNetworkResourcesRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.WxMessageFrequency;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.PaginationUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.*;
import com.moseeker.entity.pojos.EmployeeCardViewData;
import com.moseeker.entity.pojos.EmployeeRadarData;
import com.moseeker.entity.pojos.RadarUserInfo;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVOPageVO;
import com.moseeker.useraccounts.annotation.RadarSwitchLimit;
import com.moseeker.useraccounts.domain.AwardEntity;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.infrastructure.AwardRepository;
import com.moseeker.useraccounts.pojo.PositionReferralInfo;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import com.moseeker.useraccounts.service.Neo4jService;
import com.moseeker.useraccounts.service.ReferralRadarService;
import com.moseeker.useraccounts.service.aggregate.ApplicationsAggregateId;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import com.moseeker.useraccounts.service.impl.ats.employee.EmployeeBatchHandler;
import com.moseeker.useraccounts.service.impl.biztools.EmployeeBizTool;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import com.moseeker.useraccounts.service.impl.pojos.*;
import com.moseeker.useraccounts.service.impl.vo.RadarConnectResult;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
@CounterIface
public class UserEmployeeServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    private UserWxEntity userWxEntity;

    @Autowired
    private ReferralEntity referralEntity;

    @Autowired
    private CandidateRecomRecordDao candidateRecomRecordDao;

    @Autowired
    private ApplicationEntity applicationEntity;

    @Autowired
    private SearchengineEntity searchengineEntity;

    @Autowired
    private HrWxWechatDao wechatDao;
    @Autowired
    private HrWxNoticeMessageDao noticeDao;


    @Autowired
    private UserUserDao userDao;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private Neo4jService neo4jService;

    @Autowired
    PositionEntity positionEntity;

    @Autowired
    private UserCenterBizTools bizTools;

    @Autowired
    private ReferralRadarService radarService;

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    ReferralEmployeeNetworkResourcesDao networkResourcesDao;

    @Autowired
    private EmployeeBatchHandler employeeBatchHandler;

    private ThreadPool threadPool = ThreadPool.Instance;


    public Response getUserEmployee(CommonQuery query) throws TException {
        return getResource(query);
    }

    public Response getUserEmployees(CommonQuery query) throws TException {
        return getResources(query);
    }

    public Response delUserEmployee(CommonQuery query) throws TException {
        return delResource(query);
    }

    public Response postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws TException {
        try {
            if (batchForm.as_task) {
                new Thread(() -> {
                    try {
                        employeeBatchHandler.postPutUserEmployeeBatch(batchForm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                Response response = new Response();
                response.setStatus(0);
                response.setMessage("任务已提交，数据处理中");
                response.setData(JSON.toJSONString(new int[0]));
                return response;
            } else {
                return ResponseUtils.success(employeeBatchHandler.postPutUserEmployeeBatch(batchForm));
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResource(CommonQuery query) throws TException {
        try {
            UserEmployeeRecord result = userEmployeeDao.getRecord(QueryConvert.commonQueryConvertToQuery(query));
            if (result != null) {
                return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result.into(UserEmployeeStruct.class)));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            List<UserEmployeeStruct> result = userEmployeeDao.getDatas(QueryConvert.commonQueryConvertToQuery(query),UserEmployeeStruct.class);
//            List<UserEmployeeRecord> result = userEmployeeDao.getRecords(QueryConvert.commonQueryConvertToQuery(query));
            if (result != null) {
//                List<UserEmployeeStruct> userEmployeeStructs = new ArrayList<>(result.size());
                List<Map<String,Object>> list=new ArrayList<>();
                for (UserEmployeeStruct uer : result) {
//                    userEmployeeStructs.add(uer.into(UserEmployeeStruct.class));
                    String employees=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(uer);
                    Map<String,Object> employeeData= JSON.parseObject(employees, Map.class);
//                    logger.info(JSON.toJSONString(employeeData));
                    list.add(employeeData);
                }
                return ResponseUtils.success(list);
//                return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeStructs));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResourceCount(CommonQuery query) throws TException {
        try {
            int count = userEmployeeDao.getCount(QueryConvert.commonQueryConvertToQuery(query));
            return ResponseUtils.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response postResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.addRecord(BeanUtils.structToDB(record, UserEmployeeRecord.class)).getId();
            if (result > 0) {
                return ResponseUtils.success(result);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    private List<UserEmployeeRecord> convertDB(List<UserEmployeeStruct> employeeStructs) {
        List<UserEmployeeRecord> list = new ArrayList<>();
        for (UserEmployeeStruct struct : employeeStructs) {
            list.add(BeanUtils.structToDB(struct, UserEmployeeRecord.class));
        }
        return list;
    }

    public Response postResources(List<UserEmployeeStruct> records) throws TException {
        try {
            List<UserEmployeeRecord> employeeRecords = userEmployeeDao.addAllRecord(convertDB(records));
            return ResponseUtils.success(employeeRecords);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response putResource(UserEmployeeStruct record) throws TException {
        try {
            int result = userEmployeeDao.updateRecord(BeanUtils.structToDB(record, UserEmployeeRecord.class));
            if (result > 0) {
                return ResponseUtils.success(result);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response putResources(List<UserEmployeeStruct> records) throws TException {
        try {
            int[] result = userEmployeeDao.updateRecords(convertDB(records));
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response delResource(CommonQuery query) throws TException {
        try {
            Query newQuery = QueryConvert.commonQueryConvertToQuery(query);
            if (newQuery.getConditions() == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }

            List<UserEmployeeDO> employeeDOS = userEmployeeDao.getDatas(newQuery);

            if(employeeDOS.size() == 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }
            List<Integer> employeeIds = employeeDOS.stream().map(item->item.getId()).collect(Collectors.toList());
            employeeEntity.removeEmployee(employeeIds);

            return ResponseUtils.success(true);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 积分事件中，涉及申请的事件出发积分添加
     * @param applicationIdList 申请编号集合
     * @param awardEvent 积分事件
     */
    public void addEmployeeAward(List<Integer> applicationIdList, AwardEvent awardEvent) {
        /** 初始化业务编号 */
        ApplicationsAggregateId applicationsAggregateId = new ApplicationsAggregateId(applicationIdList, awardEvent);

        //添加积分
        AwardEntity awardEntity = awardRepository.loadAwardEntity(applicationsAggregateId);
        awardEntity.addAward();
    }
    /*
     获取经过认证的员工信息
     */
    public UserEmployeeVOPageVO  getUserEmployeeEmailValidate(int companyId, String email, int pageNum, int pageSize){
        UserEmployeeVOPageVO VO=userWxEntity.getFordEmployeeData(companyId,email,pageNum,pageSize);
        return VO;
    }


    /*
     获取最近转发过的员工
     */
    public List<Map<String,Object>> getPastUserEmployeeEmail(int companyId){
        String result=client.get(Constant.APPID_ALPHADOG, KeyIdentifier.PAST_USER_EMPLOYEE_VALIDATE.toString(),String.valueOf(companyId));
        if(StringUtils.isNotNullOrEmpty(result)){
            List<Map<String,Object>> list=(List<Map<String,Object>>)JSON.parse(result);
            return list;
        }
        return new ArrayList<>();
    }


    /**
     * 分页查找员工的内推数据
     * @param companyId 公司编号
     * @param pageNum 页码
     * @param pageSize 每页数据
     * @return 分页的内推数据
     */
    public PaginationUtil<ContributionDetail> getContributions(int companyId, String sendFrequency,int pageNum, int pageSize) {

        if(StringUtils.isNotNullOrEmpty(sendFrequency)){
            HrWxNoticeMessageRecord messageRecord = noticeDao.getHrWxNoticeMessageDOByWechatId(wechatDao.getHrWxWechatByCompanyId(companyId).getId(), Constant.AWARD_RANKING);
            if(messageRecord != null){
                sendFrequency = messageRecord.getSendFrequency();
            }
        }

        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        int totalRow = employeeEntity.countActiveEmployeeByCompanyIds(companyIdList);
        PaginationUtil<ContributionDetail> paginationUtil = new PaginationUtil<>();
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0 && pageSize > Constant.DATABASE_PAGE_SIZE) {
            pageSize = 10;
        }
        paginationUtil.setTotalRow(totalRow);
        paginationUtil.setPageNum(pageNum);
        paginationUtil.setPageSize(pageSize);

        List<UserEmployeeDO> employeeDOS = employeeEntity.getActiveEmployeeDOList(companyIdList, pageNum, pageSize);
        List<Integer> positionIdList = positionEntity.getPositionIdList(companyIdList);

        logger.info("getContributions positionIdList:{}", positionIdList);
        if (employeeDOS != null && employeeDOS.size() > 0) {

            //用户与员工关系
            Map<Integer, Integer> userEmployeeMap = new HashMap<>();
            List<Integer> employeeIdList = new ArrayList<>();
            List<Integer> userIdList = new ArrayList<>();
            for (UserEmployeeDO employeeDO: employeeDOS) {
                userEmployeeMap.put(employeeDO.getSysuserId(), employeeDO.getId());
                employeeIdList.add(employeeDO.getId());
                userIdList.add(employeeDO.getSysuserId());

            }
            logger.info("getContributions userIdList:{}", userIdList);

            logger.info("getContributions userEmployeeMap:{}", userEmployeeMap);

            LocalDateTime[] dates = getDatePeriod( sendFrequency);
            LocalDateTime sinceDate = dates[0],currentFriday = dates[1];

            logger.info("getContributions start date:{}", sinceDate.toString());
            logger.info("getContributions end date:{}", currentFriday.toString());

            //查找转发数量
            Future<Map<Integer,Integer>> forwardCountFuture = threadPool.startTast(() ->
                    referralEntity.countEmployeeForward(userIdList, positionIdList, sinceDate, currentFriday));
            //查找申请数量
            Future<Map<Integer, Integer>> applyCountFuture = threadPool.startTast(() ->
                    applicationEntity.countEmployeeApply(userIdList, positionIdList, sinceDate, currentFriday));

            //查找积分数量
            Future<Map<Integer, Integer>> awardsCountFuture = threadPool.startTast(() ->
                    referralEntity.countEmployeeAwards(employeeIdList, sinceDate, currentFriday));
            //查找微信账号信息
            Future<List<UserWxUserRecord>> wxUserFuture = threadPool.startTast(() ->
                    userWxEntity.getUserWxUserData(userIdList));

            Future<Map<Integer, Integer>> sortsFuture = threadPool.startTast(() ->
                    searchengineEntity.getCurrentMonthList(employeeIdList, companyIdList));

            Future<List<HrWxWechatDO>> wechatsFuture = threadPool.startTast(() ->
                    wechatDao.getHrWxWechatByCompanyIds(companyIdList));

            Map<Integer,Integer> forwardCount = new HashMap<>();
            Map<Integer, Integer> applyCount = new HashMap<>();
            Map<Integer, Integer> awardsCount = new HashMap<>();
            List<UserWxUserRecord> wxUserRecords = new ArrayList<>();
            Map<Integer, Integer> sorts = new HashMap<>();
            List<HrWxWechatDO> wechatDOList = new ArrayList<>();

            try {
                forwardCount = forwardCountFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                applyCount = applyCountFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                awardsCount = awardsCountFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                wxUserRecords = wxUserFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                sorts = sortsFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            try {
                wechatDOList = wechatsFuture.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            Map<Integer, Integer> userWechatMap = new HashMap<>();
            Map<Integer, String> userWechatTokenMap = new HashMap<>();   //用户->员工->公众号
            if (wechatDOList != null && wechatDOList.size() > 0) {
                for (UserEmployeeDO employeeDO: employeeDOS) {
                    Optional<HrWxWechatDO> wechatDOOptional = wechatDOList
                            .stream()
                            .filter(hrWxWechatDO -> hrWxWechatDO.getCompanyId() == employeeDO.getCompanyId())
                            .findAny();
                    if (wechatDOOptional.isPresent()) {
                        userWechatMap.put(employeeDO.getSysuserId(), wechatDOOptional.get().getId());
                        userWechatTokenMap.put(employeeDO.getId(), wechatDOOptional.get().getAccessToken());
                    }
                }
            }

            final Map<Integer, UserWxUserRecord> wxUserMap = new HashMap<>();
            if (wxUserRecords != null && wxUserRecords.size() > 0) {

                wxUserRecords.forEach(userWxUserRecord -> {
                    if (userWechatMap.get(userWxUserRecord.getSysuserId()) != null) {
                        if (userWxUserRecord.getWechatId().equals(userWechatMap.get(userWxUserRecord.getSysuserId()))) {
                            wxUserMap.put(userWxUserRecord.getSysuserId(), userWxUserRecord);
                        }

                    }
                    wxUserMap.putIfAbsent(userWxUserRecord.getSysuserId(), userWxUserRecord);
                });
            }

            logger.info("getContributions applyCountFuture:{}", applyCount);
            List<ContributionDetail> list = new ArrayList<>();
            for (UserEmployeeDO userEmployeeDO: employeeDOS) {
                ContributionDetail contributionDetail = new ContributionDetail();
                contributionDetail.setCompanyId(userEmployeeDO.getCompanyId());
                contributionDetail.setUserId(userEmployeeDO.getSysuserId());

                if (forwardCount.get(contributionDetail.getUserId()) != null) {
                    contributionDetail.setForwardCount(forwardCount.get(contributionDetail.getUserId()));
                }
                if (applyCount.get(contributionDetail.getUserId()) != null) {
                    contributionDetail.setDeliverCount(applyCount.get(contributionDetail.getUserId()));
                }
                if (awardsCount.get(userEmployeeDO.getId()) != null) {
                    contributionDetail.setPoint(awardsCount.get(userEmployeeDO.getId()));
                }
                if (wxUserMap.get(contributionDetail.getUserId()) != null) {
                    contributionDetail.setOpenid(wxUserMap.get(contributionDetail.getUserId()).getOpenid());
                }
                if (sorts.get(userEmployeeDO.getId()) != null) {
                    contributionDetail.setRank(sorts.get(userEmployeeDO.getId()));
                }
                if (userWechatTokenMap.get(userEmployeeDO.getId()) != null) {
                    contributionDetail.setAccessToken(userWechatTokenMap.get(userEmployeeDO.getId()));
                }
                list.add(contributionDetail);
            }
            paginationUtil.setList(list);
        }

        return paginationUtil;
    }


    /**
     * 获取积分同时周期
     * @param sendFrequency
     * @return
     */
    private static LocalDateTime[] getDatePeriod(String sendFrequency){
        WxMessageFrequency wxMessageFrequency = WxMessageFrequency.getWxMessageFrequency(sendFrequency);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sinceDate = null,currentFriday = null;
        switch (wxMessageFrequency){
            case EveryTwoWeeks:{
                sinceDate = today.minusWeeks(2).with(DayOfWeek.FRIDAY);
                currentFriday = today.with(DayOfWeek.FRIDAY);
                break;
            }
            case EveryMonth: {
                sinceDate = today.minusDays(today.getDayOfMonth());
                currentFriday = today;
                break;
            }
            case EveryWeek:
            default: {
                sinceDate = today.with(DayOfWeek.MONDAY).minusDays(3);
                currentFriday = today.with(DayOfWeek.FRIDAY);
            }
        }
        sinceDate = sinceDate.withHour(17).withMinute(0).withSecond(0).withNano(0);
        currentFriday = currentFriday.withHour(17).withMinute(0).withSecond(0).withNano(0);
        return new LocalDateTime[]{sinceDate,currentFriday};
    }

    public PositionReferralInfo getPositionReferralInfo(int userId, int positionId){
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("用户编号", userId, 1,null);
        vu.addIntTypeValidate("职位编号", positionId, 1, null);
        String message = vu.validate();
        if(StringUtils.isNotNullOrEmpty(message)){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        JobPositionRecord position = positionEntity.getPositionByID(positionId);
        if(position==null){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        PositionReferralInfo info = new PositionReferralInfo();
        info.setPositionId(position.getId());
        info.setPositionName(position.getTitle());

        UserEmployeeDO employeeDO = employeeEntity.getCompanyEmployee(userId, position.getCompanyId());
        UserWxUserRecord wxUserRecord = wxUserDao.getWXUserByUserId(userId);
        logger.info("getPositionReferralInfo wxUserRecord:{}",wxUserRecord);
        UserUserDO user = userDao.getUser(userId);
        info.setUserId(userId);
        info.setEmployeeName(user.getName());
        if(employeeDO != null) {
            info.setEmployeeId(employeeDO.getId());
            if (StringUtils.isNotNullOrEmpty(employeeDO.getCname())) {
                info.setEmployeeName(employeeDO.getCname());
            }
        }
        if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
            info.setNickname(user.getNickname());
        }else{
            info.setNickname(wxUserRecord.getNickname());
        }
        if(wxUserRecord != null){
            info.setEmployeeIcon(wxUserRecord.getHeadimgurl());
        }
        logger.info("getPositionReferralInfo info:{}",JSON.toJSONString(info));
        return info;
    }

    @RadarSwitchLimit
    public RadarInfoVO fetchRadarIndex(int companyId, int userId,  int page, int size){
        Future<Set<Integer>> employeeUserFuture =  threadPool.startTast(() -> employeeEntity.getActiveEmployeeUserIdList(companyId));
        RadarInfoVO result = new RadarInfoVO();
        if(page == 0){
            page=1;
        }
        if(size ==0){
            size = 5;
        }
        if(!employeeEntity.isEmployee(userId, companyId)) {
            throw UserAccountException.PERMISSION_DENIED;
        }
        List<ReferralEmployeeNetworkResourcesRecord> resourcesRecordList = null;
        try {
            resourcesRecordList = networkResourcesDao.fetchByPostUserIdPage(userId,
                    employeeUserFuture.get(), page, size);
            if(StringUtils.isEmptyList(resourcesRecordList)){
                return result;
            }
            logger.info("fetchRadarIndex user:{},employeeUserFuture:{}",userId, employeeUserFuture.get());
            Future<Integer> countFuture = threadPool.startTast(
                 //   () -> networkResourcesDao.fetchByPostUserIdCount(userId, employeeUserFuture.get());
                    () -> networkResourcesDao.fetchByPostUserIdCount(userId, employeeUserFuture.get(),companyId));

           logger.info("人脉雷达浏览次数统计"+networkResourcesDao.fetchByPostUserIdCount(userId, employeeUserFuture.get(),companyId));

            List<Integer> userIdList = resourcesRecordList.stream().map(m -> m.getPresenteeUserId()).collect(Collectors.toList());
            EmployeeRadarData data = referralEntity.fetchEmployeeRadarData(resourcesRecordList, userId,companyId);
            List<UserDepthVO> depthList = neo4jService.fetchDepthUserList(userId, companyId, userIdList);
            result.setPage(page);
            logger.info("fetchRadarIndex countFuture:{}",countFuture.get());
            result.setTotalCount(countFuture.get());
            List<RadarUserVO> list = new ArrayList<>();
            for(Integer id : userIdList){
                list.add(EmployeeBizTool.packageRadarUser(data, depthList, id));
            }
            result.setUserList(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @RadarSwitchLimit
    public RadarInfoVO fetchEmployeeSeekRecommend(int companyId,int userId, int page, int size){
        RadarInfoVO result = new RadarInfoVO();
        if(page == 0){
            page=1;
        }
        if(size ==0){
            size = 10;
        }
        result.setPage(page);
        Future<Set<Integer>> employeeUserFuture =  threadPool.startTast(() -> employeeEntity.getActiveEmployeeUserIdList(companyId));
        if(!employeeEntity.isEmployee(userId, companyId)) {
            throw UserAccountException.PERMISSION_DENIED;
        }
        List<Integer> positionIdList = bizTools.listPositionIdByUserIdAndStatus(userId);
        if (StringUtils.isEmptyList(positionIdList)) {
            return result;
        }
        List<ReferralSeekRecommendRecord> list = null;
        try {
            list = referralEntity.fetchEmployeeSeekRecommend(userId, positionIdList, employeeUserFuture.get());
            if(StringUtils.isEmptyList(list)){
                return result;
            }
            result.setTotalCount(list.size());
            int index = (page-1)*size;
            int end = page*size;
            if(end > list.size()){
                end=list.size();
            }
            result.setTotalCount(list.size());
            if(index >= list.size()){
                return result;
            }
            list = list.subList((page-1)*size, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        List<Integer> userIdList = list.stream().map(m ->m.getPresenteeId()).collect(Collectors.toList());
        Future<List<UserDepthVO>> depthListFuture = threadPool.startTast(()->neo4jService.fetchDepthUserList(userId, companyId, userIdList));
        EmployeeCardViewData data = referralEntity.fetchEmployeeSeekRecommendCardData(list, userId, companyId);
        this.fetchEmployeePostConnection(data);
        List<RadarUserVO> viewPages = new ArrayList<>();
        try {
            for(ReferralSeekRecommendRecord record: list){
                viewPages.add(EmployeeBizTool.packageEmployeeSeekRecommendVO(data, record, depthListFuture.get()));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        result.setUserList(viewPages);
        return result;
    }

    @RadarSwitchLimit
    public EmployeeForwardViewVO fetchEmployeeForwardView(int companyId, int userId, String positionTitle, String order, int page, int size){
        logger.info("fetchEmployeeForwardView( companyId:{} ,userId:{}, positionTitle:{}) ",companyId,userId,positionTitle);
        long startTime = System.currentTimeMillis();
        EmployeeForwardViewVO result = new EmployeeForwardViewVO();
        if(page == 0){
            page=1;
        }
        if(size ==0){
            size = 10;
        }
        result.setPage(page);
        if(!employeeEntity.isEmployee(userId, companyId)) {
            throw UserAccountException.PERMISSION_DENIED;
        }
        long employeeTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView employeeTime:{}",employeeTime - startTime);
        Future<Set<Integer>> employeeUserFuture =  threadPool.startTast(() -> employeeEntity.getActiveEmployeeUserIdList(companyId));
        List<Integer> positionIdList = bizTools.listPositionIdByUserIdAndStatus(userId);
        long positionTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView positionTime:{}",positionTime- employeeTime);
        if(StringUtils.isNotNullOrEmpty(positionTitle)){
            positionIdList = positionEntity.getPositionIdListByTitle(positionIdList, positionTitle);
        }
        if (StringUtils.isEmptyList(positionIdList)) {
            return result;
        }
        long positionIdListTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView positionIdListTime:{}",positionIdListTime-employeeTime);
        List<CandidateRecomRecordRecord> list = null;
        try {
            list = this.pagePositionById(positionIdList, employeeUserFuture.get(), userId, companyId, order, page, size, result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }if(StringUtils.isEmptyList(list)){
            return result;
        }
        long listTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView listTime:{}",listTime - positionIdListTime);
        EmployeeCardViewData data = referralEntity.fetchEmployeeViewCardData(list, userId, companyId);
        long dataTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView dataTime:{}",dataTime - listTime);
        this.fetchEmployeePostConnection(data);
        long chainTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView chainTime:{}",chainTime -dataTime);
        List<Integer> userIdList = list.stream().map(m ->m.getPresenteeUserId()).collect(Collectors.toList());
        List<UserDepthVO> depthList = neo4jService.fetchDepthUserList(userId, companyId, userIdList);
        long neo4jTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView neo4jTime:{}",neo4jTime-chainTime);
        int wechatId = wechatDao.getHrWxWechatByCompanyId(companyId).getId();
        List<EmployeeForwardViewPageVO> viewPages = new ArrayList<>();
        Set<Integer> users = new HashSet<>();

        for(CandidateRecomRecordRecord record: list){
            EmployeeForwardViewPageVO pageVO = EmployeeBizTool.packageEmployeeForwardViewVO(data, record, depthList);
            users.add(pageVO.getUserId());
            viewPages.add(pageVO);
        }

        Map<Integer,Boolean> userFocusMap = new HashMap<>();
        users.forEach((userid)->{
            // 如果已关注公众号，可以建立IM聊天
            userFocusMap.put(userid,wxUserDao.getWxUserByUserIdAndWechatIdAndSubscribe(userid,wechatId) != null);
        });
        viewPages.forEach(viewPage->viewPage.setCanChat(userFocusMap.getOrDefault(viewPage.getUserId(),false)));

        long dataBizTime = System.currentTimeMillis();
        logger.info("fetchEmployeeForwardView dataBizTime:{}",dataBizTime-neo4jTime);
        result.setUserList(viewPages);
        return result;
    }



    private void fetchEmployeePostConnection(EmployeeCardViewData data){
        List<ReferralConnectionLogRecord> connectionLogList = data.getConnectionLogList();
        if(StringUtils.isEmptyList(connectionLogList)){
            return;
        }
        Map<Integer, List<RadarUserInfo>> connectionMap = new HashMap<>();
        Map<Integer, Integer> chainStatusMap = new HashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(connectionLogList.size());
        for(ReferralConnectionLogRecord logRecord:connectionLogList){
            threadPool.startTast(()->{
                try {
                    ConnectRadarInfo info = new ConnectRadarInfo();
                    info.setChainId(logRecord.getId());
                    info.setParentId(0);
                    info.setRecomUserId(logRecord.getRootUserId());
                    info.setNextUserId(logRecord.getRootUserId());
                    RadarConnectResult result = radarService.connectRadar(info);
                    connectionMap.put(logRecord.getRootChainId(), result.getChain());
                    chainStatusMap.put(logRecord.getRootChainId(), result.getState());
                }catch (Exception e){
                    logger.info("fetchEmployeePostConnection:{}", e.getMessage());
                }
                countDownLatch.countDown();
                return 0;
            });
        }
        try {
            countDownLatch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.info("fetchEmployeePostConnection overTime");
        }
        data.setConnectionMap(connectionMap);
        data.setChainStatus(chainStatusMap);

    }


    public List<CandidateRecomRecordRecord> pagePositionById(List<Integer> positionIds, Set<Integer> employeeUsers, int postUserId, int companyId,
                                                         String order, int page, int size, EmployeeForwardViewVO result){
        List<CandidateRecomRecordRecord> list = new ArrayList<>();
        switch (order){
            case "time": list = bizTools.listCandidateRecomRecords(postUserId, positionIds, employeeUsers, companyId);
                break;
            case "view": list = listCandidateRecomRecordsByViewCount(postUserId, positionIds, companyId, employeeUsers);
                break;
            case "depth": list = listCandidateRecomRecordsByDepth(postUserId, companyId, positionIds, employeeUsers);
                break;
        }
        if(!StringUtils.isEmptyList(list)){
            int index = (page-1)*size;
            int end = page*size;
            if(end > list.size()){
                end=list.size();
            }
            result.setTotalCount(list.size());
            if(index >= list.size()){
                return new ArrayList<>();
            }
            list = list.subList((page-1)*size, end);
        }
        return list;
    }


    public List<CandidateRecomRecordRecord> listCandidateRecomRecordsByViewCount(int userId, List<Integer> positionIdList, int companyId, Set<Integer> employeeUsers){
        List<CandidateRecomRecordRecord> recomRecordDOList = bizTools.listCandidateRecomRecords(userId, positionIdList, employeeUsers, companyId);
        if(StringUtils.isEmptyList(recomRecordDOList)){
            return new ArrayList<>();
        }
        List<Integer>  positionIds= recomRecordDOList.stream().map(m -> m.getPositionId()).collect(Collectors.toList());
        List<Integer> presenteeUserIds = recomRecordDOList.stream().map(m -> m.getPresenteeUserId()).collect(Collectors.toList());
        List<CandidatePositionDO> candidatePositionList = candidatePositionDao.fetchViewedByUserIdsAndPids(presenteeUserIds, positionIds);
        List<CandidateRecomRecordRecord> list = new ArrayList<>();

        for (CandidatePositionDO candidatePosition : candidatePositionList) {
            for(CandidateRecomRecordRecord record: recomRecordDOList){
                if(candidatePosition.getUserId() == record.getPresenteeUserId().intValue()
                        && candidatePosition.getPositionId() == record.getPositionId().intValue()){
                    list.add(record);
                    break;
                }
            }

        }
        return list;
    }

    public List<CandidateRecomRecordRecord> listCandidateRecomRecordsByDepth(int userId, int companyId, List<Integer> positionIdList, Set<Integer> employeeUsers){
        List<CandidateRecomRecordRecord> recomRecordDOList = bizTools.listCandidateRecomRecords(userId, positionIdList, employeeUsers, companyId);
        if(StringUtils.isEmptyList(recomRecordDOList)){
            return new ArrayList<>();
        }

        List<Integer> presenteeUserIds = recomRecordDOList.stream().map(m -> m.getPresenteeUserId()).collect(Collectors.toList());
        List<UserDepthVO> depthList = neo4jService.fetchDepthUserList(userId, companyId, presenteeUserIds);
        List<CandidateRecomRecordRecord> list = new ArrayList<>();
        for (UserDepthVO depth : depthList) {
            for(CandidateRecomRecordRecord record: recomRecordDOList){
                if(depth.getUserId() == record.getPresenteeUserId().intValue()){
                    list.add(record);
                }
            }
        }
        return list;
    }


    public List<UserEmployee> getEmployeeByUserIdListAndCompanyList(List<Integer> userIdList,List<Integer> companyIdList){
        List<UserEmployee> list=userEmployeeDao.getDataListByCidListAndUserIdList(userIdList,companyIdList);
        return list;
    }

    public List<UserEmployee> getuserEmployeeList(int companyId,List<Integer> userIdList){
        List<UserEmployee> result= userEmployeeDao.getEmployeeList(userIdList,companyId);
        return result;
    }

    public UserEmployee getSingleUserEmployee(int userId){
        UserEmployee result= userEmployeeDao.getSingleEmployeeByUserId(userId);
        return result;
    }

}
