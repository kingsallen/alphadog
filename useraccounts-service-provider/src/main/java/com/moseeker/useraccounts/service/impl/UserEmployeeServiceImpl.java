package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.PaginationUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.*;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVOPageVO;
import com.moseeker.useraccounts.domain.AwardEntity;
import com.moseeker.useraccounts.infrastructure.AwardRepository;
import com.moseeker.useraccounts.service.aggregate.ApplicationsAggregateId;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import com.moseeker.useraccounts.service.impl.ats.employee.EmployeeBatchHandler;
import com.moseeker.useraccounts.service.impl.pojos.ContributionDetail;
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
import java.util.concurrent.Future;
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
    private ApplicationEntity applicationEntity;

    @Autowired
    private SearchengineEntity searchengineEntity;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    PositionEntity positionEntity;

    @Resource(name = "cacheClient")
    private RedisClient client;

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
    public PaginationUtil<ContributionDetail> getContributions(int companyId, int pageNum, int pageSize) {

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

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime lastFriday = today.with(DayOfWeek.MONDAY).minusDays(3).withHour(17).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime currentFriday = today.with(DayOfWeek.FRIDAY).withHour(17).withMinute(0).withSecond(0).withNano(0);

            //查找转发数量
            Future<Map<Integer,Integer>> forwardCountFuture = threadPool.startTast(() ->
                    referralEntity.countEmployeeForward(userIdList, positionIdList, lastFriday, currentFriday));
            //查找申请数量
            Future<Map<Integer, Integer>> applyCountFuture = threadPool.startTast(() ->
                    applicationEntity.countEmployeeApply(userIdList, positionIdList, lastFriday, currentFriday));
            //查找积分数量
            Future<Map<Integer, Integer>> awardsCountFuture = threadPool.startTast(() ->
                    referralEntity.countEmployeeAwards(employeeIdList, lastFriday, currentFriday));
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
}
