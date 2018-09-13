package com.moseeker.profile.service.impl.talentpoolmvhouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDetailDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveRecordDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveDetailRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.common.constants.*;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.config.Sender;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.CrawlTypeEnum;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveChannelEnum;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveConstant;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseOperationVO;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;

import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.ProfileMoveOperationInfoVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveRecordDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.talentpool.struct.ProfileMoveForm;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 简历搬家service
 *
 * @author cjm
 * @date 2018-07-18 11:55
 **/
public abstract class AbstractProfileMoveService implements IChannelType {

    @Autowired
    protected Sender sender;
    @Autowired
    protected TalentPoolProfileMoveDao profileMoveDao;
    @Autowired
    protected TalentPoolProfileMoveRecordDao profileMoveRecordDao;
    @Autowired
    protected UserHrAccountDao userHrAccountDao;
    @Autowired
    protected HRThirdPartyAccountDao hrThirdPartyAccountDao;
    @Autowired
    protected HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;
    @Autowired
    protected WholeProfileService wholeProfileService;
    @Autowired
    protected ThirdpartyAccountCompanyDao thirdCompanyDao;
    @Autowired
    protected TalentPoolProfileMoveDetailDao poolProfileMoveDetailDao;
    @Autowired
    protected HrCompanyAccountDao hrCompanyAccountDao;

    protected Logger logger = LoggerFactory.getLogger(AbstractProfileMoveService.class);

    protected ThreadPool pool = ThreadPool.Instance;

    protected static final int TRY_TIMES = 3;

    private static final long SIX_MONTH = 6L * 30 * 24 * 60 * 60 * 1000;

    /**
     * 简历搬家用户登录
     *
     * @param form 简历搬家所需参数
     * @return response 是chaos返回的结果，没有对结果处理
     * @author cjm
     * @date 2018/7/18
     */
    @Transactional(rollbackFor = Exception.class)
    public Response moveHouseLogin(ProfileMoveForm form) throws Exception {
        logger.info("====================form:{}", form);
        int hrId = form.getHr_id();
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if (userHrAccountDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_NOT_EXISTS);
        }
        // 获取用户名密码
        int channel = form.getChannel();
        HrThirdPartyAccountDO hrThirdPartyAccountDO = hrThirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, channel);
        if (hrThirdPartyAccountDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        // 查询下之前有没有搬家过，如果搬过家并且成功状态，起止时间为上次的结束时间
        List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getListByHrIdAndChannel(hrId, channel);
        List<Integer> moveIds = profileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS = profileMoveRecordDao.getListByMoveIds(moveIds);
        checkIsMoving(profileMoveRecordDOS);
        // 过滤出搬家成功的list，去除第一个的创建时间为本次搬家的起始时间，如果list为空，则减去6个月为起始时间
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            if (profileMoveRecordDOS.size() != 0) {
                int successMoveId = getSuccessMoveId(profileMoveRecordDOS);
                for (TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS) {
                    if (profileMoveDO.getId() == successMoveId) {
                        startDate = new SimpleDateFormat("yyyy-MM-dd").parse(profileMoveDO.getEndDate());
                        break;
                    }
                }
            } else {
                // 当前时间减去六个月
                startDate = new Date(System.currentTimeMillis() - SIX_MONTH);
            }
        } catch (Exception e) {
            startDate = new Date(System.currentTimeMillis() - SIX_MONTH);
        }
        // 映射简历搬家参数
        List<MvHouseVO> mvHouseVOs = handleRequestParams(userHrAccountDO, hrThirdPartyAccountDO, startDate, endDate);
        for (MvHouseVO mvHouseVO : mvHouseVOs) {
            sender.sendMqRequest(mvHouseVO, ProfileMoveConstant.PROFILE_MOVE_ROUTING_KEY_REQUEST, ProfileMoveConstant.PROFILE_MOVE_EXCHANGE_NAME);
            Thread.sleep(10000);
        }
        return ResponseUtils.success(new HashMap<>(1 >> 4));
    }

    private void checkIsMoving(List<TalentPoolProfileMoveRecordDO> profileMoveDOS) throws BIZException {
        List<Byte> stateList = profileMoveDOS.stream().map(TalentPoolProfileMoveRecordDO::getStatus).distinct().collect(Collectors.toList());
        if (stateList != null && stateList.contains(ProfileMoveStateEnum.MOVING.getValue())) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVING);
        }
    }

    private int getSuccessMoveId(List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS) {
        List<Integer> moveIds = profileMoveRecordDOS.stream().map(TalentPoolProfileMoveRecordDO::getProfileMoveId).collect(Collectors.toList());
        for (Integer moveId : moveIds) {
            boolean flag = true;
            for (TalentPoolProfileMoveRecordDO recordDO : profileMoveRecordDOS) {
                if (moveId == recordDO.getProfileMoveId()) {
                    if (recordDO.getStatus() != 1) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                return moveId;
            }
        }
        return moveIds.get(moveIds.size() - 1);

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
    public Response getMoveOperationList(int hrId, int pageNumber, int pageSize) throws ParseException {
        int rows = profileMoveDao.getTotalCount(hrId);
        int startIndex = pageSize * (pageNumber - 1);
        // 如果起始下标大于总行数，重置起始下标为最后一页第一个数据
        if (startIndex > rows) {
            pageNumber = rows / pageSize + 1;
            startIndex = pageSize * (pageNumber - 1);
        }
        // 如果起始下标等于总行数，由于limit是从0开始，所以此下标是没有数据的，再减去一个pageSize
        if (startIndex == rows && rows >= pageSize) {
            startIndex = startIndex - pageSize;
        } else if (startIndex == rows) {
            startIndex = 0;
        }
        List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getMoveOperationList(hrId, startIndex, pageSize);
        List<Integer> profileMoveIds = profileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS = profileMoveRecordDao.getListByMoveIds(profileMoveIds);
        Map<Integer, List<TalentPoolProfileMoveRecordDO>> map = new HashMap<>(1 >> 6);
        for (TalentPoolProfileMoveRecordDO profileMoveRecordDO : profileMoveRecordDOS) {
            if (map.get(profileMoveRecordDO.getProfileMoveId()) == null) {
                List<TalentPoolProfileMoveRecordDO> list = new ArrayList<>();
                list.add(profileMoveRecordDO);
                map.put(profileMoveRecordDO.getProfileMoveId(), list);
            } else {
                List<TalentPoolProfileMoveRecordDO> list = map.get(profileMoveRecordDO.getProfileMoveId());
                list.add(profileMoveRecordDO);
                map.put(profileMoveRecordDO.getProfileMoveId(), list);
            }
        }
        JSONObject jsonObject = new JSONObject();
        List<MvHouseOperationVO> operationList = getOperationList(map, profileMoveDOS);

        jsonObject.put("operation_list", JSONArray.parseArray(JSON.toJSONString(operationList)));
        jsonObject.put("total", rows);
        return ResponseUtils.success(jsonObject);
    }

    /**
     * 简历搬家
     * todo 事务注解需要添加
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
        TalentpoolProfileMoveRecord profileMove = profileMoveDao.fetchRecordById(operationId);
        if (profileMove == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
        }
        int hrId = profileMove.getHrId();
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = profileMoveRecordDao.getListByMoveId(operationId);
        logger.info("=======================简历搬家record:{}", profileMoveRecordRecords);
        if (profileMoveRecordRecords == null || profileMoveRecordRecords.size() == 0) {
//            HrThirdPartyAccountHrDO hrDO = hrThirdPartyAccountHrDao.getHrAccountInfo(hrId, profileMove.getChannel());
//             通过第三方账号获取第三方公司名称
//            List<ThirdpartyAccountCompanyDO> thirdpartyAccountCompanyDOS = thirdCompanyDao.getCompanyByAccountId(hrDO.getHrAccountId());
//             插入简历搬家操作TalentpoolProfileMoveRecord表
            profileMoveRecordRecords = insertProfileMoveRecordRecord(operationId);
        }
        JSONObject resumeObj = JSONObject.parseObject(profile);
        int crawlType = getCrawlTypeByOrigin(resumeObj);
        TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = new TalentpoolProfileMoveRecordRecord();
        for(TalentpoolProfileMoveRecordRecord one : profileMoveRecordRecords){
            if(one.getCrawlType() == crawlType){
                profileMoveRecordRecord = one;
                break;
            }
        }
        // 如果当前邮件数与总邮件数相同，认为本次搬家成功
        boolean flag = false;
        int totalEmailNum = profileMoveRecordRecord.getTotalEmailNum();
        if (currentEmailNum == totalEmailNum && profileMoveRecordRecord.getStatus() == ProfileMoveStateEnum.MOVING.getValue()) {
            profileMoveRecordRecord.setStatus(ProfileMoveStateEnum.SUCCESS.getValue());
            flag = true;
        }
        // todo 一些参数的检验暂时没写
        HrCompanyDO hrCompanyDO = hrCompanyAccountDao.getHrCompany(hrId);
        if (hrCompanyDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        int companyId = hrCompanyDO.getId();
        // 简历入库
        Future<Response> preserveFuture =
                pool.startTast(() -> wholeProfileService.preserveProfile(profile, null, hrId, companyId, 0, UserSource.MV_HOUSE.getValue()));
        Response preserveResponse = preserveFuture.get(120, TimeUnit.SECONDS);
//        Response preserveResponse = new Response(0, "");
        logger.info("===========================简历搬家preserveResponse:{}", preserveResponse);
        if (preserveResponse.getStatus() == 0) {
            String mobileStr = getUserMobileByProfile(resumeObj);
            long mobile = Long.parseLong(mobileStr);
            // 判断上一次是否入库过，如果搬过一次，是否搬成功了
            int currentCrawlNum = profileMoveRecordRecord.getCrawlNum();
            TalentpoolProfileMoveDetailRecord profileMoveDetailRecord = poolProfileMoveDetailDao.getByMobile(mobile);
            if (profileMoveDetailRecord == null) {
                // 如果第一次搬该简历，搬家简历数+1
                currentCrawlNum = currentCrawlNum + 1;
                // 入库成功时，插入一条合并记录，状态为成功，如果之后chaos发送搬家状态失败，则将本次合并记录置为失败，这里做连表插入判断：exists
                insertCombineRecord(mobile, operationId);
            } else if (profileMoveDetailRecord.getProfileMoveStatus() == ProfileMoveStateEnum.SUCCESS.getValue()) {
                // 如果是以前搬成功的简历，搬家简历数 + 1，由于可能多处修改这里，加乐观锁
                currentCrawlNum = currentCrawlNum + 1;
                updateMoveDetailWithPositiveLock(profileMoveDetailRecord, operationId, ProfileMoveStateEnum.SUCCESS.getValue(), 1);
            } else {
                // 如果上次搬失败了，本次搬的简历在上次已经计算在内，此次搬家简历数不+1，这次将其操作id和状态都修改掉
                boolean isSameCompany = checkSameCompany(profileMoveDetailRecord.getProfileMoveId(), companyId);
                if (!isSameCompany) {
                    currentCrawlNum = currentCrawlNum + 1;
                }
                updateMoveDetailWithPositiveLock(profileMoveDetailRecord, operationId, ProfileMoveStateEnum.SUCCESS.getValue(), 1);
            }
            profileMoveRecordRecord.setCurrentEmailNum(currentEmailNum);
            updateProfileMove(profileMoveRecordRecord, currentCrawlNum, 1);
            return ResponseUtils.success(new HashMap<>(1 >> 4));
        }
        // 当第一次出现当前邮件数等于总邮件数但是简历入库却失败时，也认为简历搬家成功
        if (flag) {
            profileMoveRecordDao.updateRecord(profileMoveRecordRecord);
        }
        return preserveResponse;
    }

    private int getCrawlTypeByOrigin(JSONObject resumeObj) throws BIZException {
        String origin = resumeObj.getString("origin");
        if (StringUtils.isNullOrEmpty(origin)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_ORIGIN_NULL);
        }
        if (origin.equals(ChannelType.MVHOUSEJOB51DOWNLOAD.getOrigin("")) || origin.equals(ChannelType.MVHOUSEZHILIANDOWNLOAD.getOrigin(""))) {
            return 1;
        } else {
            return 0;
        }

    }

    public Response getMoveOperationState(int hrId) throws BIZException {
        long timeout = 4 * 60 * 60 * 1000;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - timeout);
        List<Map<String, Byte>> resultList = new ArrayList<>();
        List<TalentPoolProfileMoveDO> talentPoolProfileMoveDOS = profileMoveDao.getProfileMoveDOByHrId(hrId, timestamp);
        if (talentPoolProfileMoveDOS == null || talentPoolProfileMoveDOS.size() == 0) {
            return defaultState(resultList);
        }
        Map<Integer, TalentPoolProfileMoveDO> talentPoolProfileMoveDOMap = talentPoolProfileMoveDOS.stream().collect(Collectors.toMap(TalentPoolProfileMoveDO::getId, talentPoolProfileMoveDO -> talentPoolProfileMoveDO));
        List<Integer> profileMoveIds = talentPoolProfileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<Byte> channels = talentPoolProfileMoveDOS.stream().map(TalentPoolProfileMoveDO::getChannel).distinct().collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> talentPoolProfileMoveRecordDOS = profileMoveRecordDao.getProfileMoveRecordByMoveIds(profileMoveIds);
        for (byte channel : channels) {
            Map<String, Byte> map = new HashMap<>(1 >> 4);
            map.put("channel", channel);
            boolean flag = false;
            for (TalentPoolProfileMoveRecordDO profileMoveRecordDO : talentPoolProfileMoveRecordDOS) {
                if (talentPoolProfileMoveDOMap.get(profileMoveRecordDO.getProfileMoveId()).getChannel() == channel) {
                    if (profileMoveRecordDO.getStatus() == ProfileMoveStateEnum.MOVING.getValue()) {
                        map.put("status", ProfileMoveStateEnum.MOVING.getValue());
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                map.put("status", (byte) 1);
            }
            resultList.add(map);
        }
        return defaultState(resultList);
    }

    private Response defaultState(List<Map<String, Byte>> resultList) {
        List<Byte> currentChannels = new ArrayList<>();
        for (Map<String, Byte> map : resultList) {
            currentChannels.add(map.get("channel"));
        }
        for (int i = 0; i < ProfileMoveChannelEnum.values().length; i++) {
            byte channel = (byte) ProfileMoveChannelEnum.values()[i].getChannel();
            if (currentChannels.contains(channel)) {
                continue;
            }
            Map<String, Byte> map = new HashMap<>(1 >> 4);
            map.put("channel", channel);
            map.put("status", (byte) 1);
            resultList.add(map);
        }
        return ResponseUtils.success(resultList);
    }

    private void updateMoveDetailWithPositiveLock(TalentpoolProfileMoveDetailRecord profileMoveDetailRecord, int operationId, byte status, int retryTimes) throws BIZException {
        if (retryTimes > 3) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_UPDATE_FAILED);
        }
        int row = poolProfileMoveDetailDao.updateRecordWithPositiveLock(profileMoveDetailRecord, operationId, status);
        if (row == 0) {
            updateMoveDetailWithPositiveLock(profileMoveDetailRecord, operationId, status, ++retryTimes);
        }
    }

    private boolean checkSameCompany(Integer profileMoveId, int companyId) {
        TalentpoolProfileMoveRecordRecord profileMoveRecord = profileMoveRecordDao.getProfileMoveRecordById(profileMoveId);
        if (profileMoveRecord == null) {
            return false;
        }
        TalentpoolProfileMoveRecord profileMove = profileMoveDao.fetchRecordById(profileMoveRecord.getProfileMoveId());
        if (profileMove == null) {
            return false;
        }
        int hrId = profileMove.getHrId();
        HrCompanyDO hrCompanyDO = hrCompanyAccountDao.getHrCompany(hrId);
        return hrCompanyDO != null && hrCompanyDO.getId() == companyId;
    }

    /**
     * @param userHrAccountDO       hr账号do
     * @param hrThirdPartyAccountDO hr第三方账号do
     * @param startDate             简历搬家起始时间
     * @param endDate               简历搬家结束时间
     * @return 简历搬家请求vo集合
     * @author cjm
     * @date 2018/9/9
     */
    private List<MvHouseVO> handleRequestParams(UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO, Date startDate, Date endDate) throws Exception {
        HrThirdPartyAccountHrDO hrDO = hrThirdPartyAccountHrDao.getHrAccountInfo(userHrAccountDO.getId(), hrThirdPartyAccountDO.getChannel());
        if (hrDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_NOT_EXISTS);
        }
        // 插入简历搬家TalentpoolProfileMove表
        TalentpoolProfileMoveRecord profileMoveRecord = insertProfileMoveRecord(userHrAccountDO.getId(), hrThirdPartyAccountDO.getChannel(), startDate, endDate);
        int profileMoveId = profileMoveRecord.getId();
        int thirdPartAccountId = hrDO.getThirdPartyAccountId();
        // 通过第三方账号获取第三方公司名称
//        List<ThirdpartyAccountCompanyDO> thirdpartyAccountCompanyDOS = thirdCompanyDao.getCompanyByAccountId(thirdPartAccountId);
        // 插入简历搬家操作TalentpoolProfileMoveRecord表
        insertProfileMoveRecordRecord(profileMoveId);
        List<MvHouseVO> mvHouseVOS = new ArrayList<>();
        String password = hrThirdPartyAccountDO.getPassword();
//        if (profileMoveRecordRecords.size() > 0) {
        ProfileMoveOperationInfoVO operationInfoVO = new ProfileMoveOperationInfoVO();
        operationInfoVO.setStart_date(new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        operationInfoVO.setEnd_date(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
        MvHouseVO mvHouseVO = new MvHouseVO();
        mvHouseVO.setAccount_id(userHrAccountDO.getId());
        mvHouseVO.setChannel(hrThirdPartyAccountDO.getChannel());
        mvHouseVO.setMember_name(hrThirdPartyAccountDO.getExt());
        mvHouseVO.setMobile(userHrAccountDO.getMobile());
        mvHouseVO.setOperation_id(profileMoveId);
        mvHouseVO.setUser_name(hrThirdPartyAccountDO.getUsername());
        mvHouseVO.setPassword(password);
        mvHouseVO.setOperation_info(operationInfoVO);
        mvHouseVOS.add(mvHouseVO);
//        }
        return mvHouseVOS;
    }

    private List<TalentpoolProfileMoveRecordRecord> insertProfileMoveRecordRecord(int profileMoveId) {
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = new ArrayList<>();
        for (int i = 0; i < CrawlTypeEnum.values().length; i++) {
            TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = new TalentpoolProfileMoveRecordRecord();
            profileMoveRecordRecord.setProfileMoveId(profileMoveId);
            profileMoveRecordRecord.setCrawlType(CrawlTypeEnum.values()[i].getStatus());
            profileMoveRecordRecord = profileMoveRecordDao.addRecord(profileMoveRecordRecord);
            profileMoveRecordRecords.add(profileMoveRecordRecord);
        }
        // 51不需要多个公司名
        logger.info("profileMoveRecordRecords:{}", profileMoveRecordRecords);
        return profileMoveRecordRecords;
    }

    private TalentpoolProfileMoveRecord insertProfileMoveRecord(int hrId, int channel, Date startDate, Date endDate) {
        TalentpoolProfileMoveRecord record = new TalentpoolProfileMoveRecord();
        record.setStartDate(new java.sql.Date(startDate.getTime()));
        record.setEndDate(new java.sql.Date(endDate.getTime()));
        record.setChannel((byte) channel);
        record.setHrId(hrId);
        record = profileMoveDao.addRecord(record);
        return record;
    }


    private List<MvHouseOperationVO> getOperationList(Map<Integer, List<TalentPoolProfileMoveRecordDO>> map, List<TalentPoolProfileMoveDO> profileMoveDOS) throws ParseException {
        List<MvHouseOperationVO> operationList = new ArrayList<>();
        for (TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS) {
            MvHouseOperationVO mvHouseOperationVO = new MvHouseOperationVO();
            byte status = getMvHouseState(profileMoveDO.getId(), map);
            int crawlNum = getMvProfileNum(profileMoveDO.getId(), map);
            mvHouseOperationVO.setStatus(status);
            if (status == ProfileMoveStateEnum.MOVING.getValue()) {
                mvHouseOperationVO.setStatus_display(ProfileMoveStateEnum.MOVING.getName());
            } else {
                // 应产品要求，如果搬家出错依然显示搬家完成
                mvHouseOperationVO.setStatus_display(ProfileMoveStateEnum.SUCCESS.getName());
            }
            mvHouseOperationVO.setCrawl_num(crawlNum);
            String startDate = formatDatePattern(profileMoveDO.getStartDate(), null, null);
            mvHouseOperationVO.setStart_date(startDate);
            String endDate = formatDatePattern(profileMoveDO.getEndDate(), null, null);
            mvHouseOperationVO.setEnd_date(endDate);
            String oldPattern = "yyyy-MM-dd HH:mm";
            String newPattern = "yyyy/MM/dd HH:mm";
            String createDate = formatDatePattern(profileMoveDO.getCreateTime(), oldPattern, newPattern);
            mvHouseOperationVO.setCreate_time(createDate);
            mvHouseOperationVO.setChannel(profileMoveDO.getChannel());
            operationList.add(mvHouseOperationVO);
        }
        return operationList;
    }

    private String formatDatePattern(String startDate, String oldPattern, String newPattern) throws ParseException {
        if (StringUtils.isNullOrEmpty(oldPattern) || StringUtils.isNullOrEmpty(newPattern)) {
            oldPattern = "yyyy-MM-dd";
            newPattern = "yyyy/MM/dd";
        }
        DateFormat dateFormat = new SimpleDateFormat(oldPattern);
        Date date = dateFormat.parse(startDate);
        DateFormat dateFormat1 = new SimpleDateFormat(newPattern);
        return dateFormat1.format(date);
    }

    private int getMvProfileNum(int id, Map<Integer, List<TalentPoolProfileMoveRecordDO>> map) {
        List<TalentPoolProfileMoveRecordDO> list = map.get(id);
        int num = 0;
        if (list != null && list.size() > 0) {
            for (TalentPoolProfileMoveRecordDO profileMoveRecordDO : list) {
                num = num + profileMoveRecordDO.getCrawlNum();
            }
        }
        return num;
    }

    private byte getMvHouseState(int id, Map<Integer, List<TalentPoolProfileMoveRecordDO>> map) {
        List<TalentPoolProfileMoveRecordDO> list = map.get(id);
        byte status = ProfileMoveStateEnum.SUCCESS.getValue();
        if (list != null && list.size() > 0) {
            List<Byte> statusList = list.stream().map(TalentPoolProfileMoveRecordDO::getStatus).distinct().collect(Collectors.toList());
            if (statusList.contains(ProfileMoveStateEnum.MOVING.getValue())) {
                status = ProfileMoveStateEnum.MOVING.getValue();
            }
        }
        return status;
    }

    /**
     * 记录profile_move_detail表
     *
     * @param mobile      手机号
     * @param operationId profile_move_record.id
     * @author cjm
     * @date 2018/9/10
     */
    private void insertCombineRecord(long mobile, int operationId) {
        TalentpoolProfileMoveDetailRecord record = new TalentpoolProfileMoveDetailRecord();
        record.setMobile(mobile);
        record.setProfileMoveId(operationId);
        record.setProfileMoveStatus(ProfileMoveStateEnum.SUCCESS.getValue());
        int row = poolProfileMoveDetailDao.addWhereExistStatus(record, ProfileMoveStateEnum.MOVING.getValue());
        if (row == 0) {
            TalentpoolProfileMoveRecordRecord recordRecord = profileMoveRecordDao.getProfileMoveRecordById(operationId);
            poolProfileMoveDetailDao.addWhereExistStatus(record, recordRecord.getStatus());
        }
    }

    /**
     * 简历搬家操作记录表更新
     *
     * @param record 简历搬家操作记录record
     * @author cjm
     * @date 2018/7/23
     */
    private void updateProfileMove(TalentpoolProfileMoveRecordRecord record, int currentCrawlNum, int retryTimes) throws BIZException {
        if (retryTimes > TRY_TIMES) {
            throw new BIZException(1, "简历搬家操作记录数据库更新失败");
        }
        int row = profileMoveRecordDao.updateRecordWithPositiveLock(record, currentCrawlNum);
        if (row == 0) {
            record = profileMoveRecordDao.getProfileMoveRecordById(record.getId());
            currentCrawlNum = record.getCrawlNum() + 1;
            updateProfileMove(record, currentCrawlNum, ++retryTimes);
        }
    }

    /**
     * 获取简历中的手机号
     *
     * @param resumeObj 简历jsonObj
     * @return 手机号
     * @author cjm
     * @date 2018/9/6
     */
    private String getUserMobileByProfile(JSONObject resumeObj) throws BIZException {

        JSONObject jsonObject = resumeObj.getJSONObject("user");
        String mobile = jsonObject.getString("mobile");
        if (StringUtils.isNullOrEmpty(mobile)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        } else {
            String[] mobileArray = mobile.split("-");
            if (mobileArray.length > 1) {
                mobile = mobileArray[1];
            }
        }
        return mobile;
    }


}
