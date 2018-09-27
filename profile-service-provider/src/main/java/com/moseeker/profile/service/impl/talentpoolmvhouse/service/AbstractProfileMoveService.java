package com.moseeker.profile.service.impl.talentpoolmvhouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.joda.time.DateTime;
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
    protected TalentPoolProfileMoveDetailDao profileMoveDetailDao;
    @Autowired
    protected HrCompanyAccountDao hrCompanyAccountDao;

    protected Logger logger = LoggerFactory.getLogger(AbstractProfileMoveService.class);

    private ThreadPool pool = ThreadPool.Instance;

    private static final int TRY_TIMES = 3;

    protected long firstTime;

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
        // 非主账号无法操作简历搬家
        if(userHrAccountDO.getAccountType() != 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVING_MAIN_ACCOUNT);
        }
        // 获取用户名密码
        int channel = form.getChannel();
        HrThirdPartyAccountDO hrThirdPartyAccountDO = hrThirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, channel);
        if (hrThirdPartyAccountDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        HrThirdPartyAccountHrDO hrDO = hrThirdPartyAccountHrDao.getHrAccountInfo(hrId, channel);
//             通过第三方账号获取第三方公司名称
        List<ThirdpartyAccountCompanyDO> thirdpartyAccountCompanyDOS = thirdCompanyDao.getCompanyByAccountId(hrDO.getThirdPartyAccountId());
        // 查询下之前有没有搬家过，如果搬过家并且成功状态，起止时间为上次的结束时间
        List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getListByHrIdAndChannel(hrId, channel);
        List<Integer> moveIds = profileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS = profileMoveRecordDao.getListByMoveIds(moveIds);
        checkIsMoving(profileMoveRecordDOS);
        // 判断是否是第一次搬家
        int successMoveId = 0;
        boolean isFirstMove = false;
        if (profileMoveRecordDOS.size() != 0) {
            successMoveId = getSuccessMoveId(profileMoveRecordDOS);
            if(successMoveId == 0){
                isFirstMove = true;
            }
        }else{
            isFirstMove = true;
        }

        Date startDate = getProfileMoveStartDate(successMoveId, profileMoveDOS);

        Date endDate = new Date();
        // 插入简历搬家talentpool_profile_move表
        TalentpoolProfileMoveRecord profileMoveRecord = insertProfileMoveRecord(hrId, channel, startDate, endDate);
        int profileMoveId = profileMoveRecord.getId();
        // 插入talentpool_profile_move_record表
        insertProfileMoveRecordRecord(profileMoveId);
        // 映射简历搬家参数
        MvHouseVO mvHouseVO = handleRequestParams(thirdpartyAccountCompanyDOS, userHrAccountDO, hrThirdPartyAccountDO, startDate, endDate, profileMoveId, isFirstMove);
        sender.sendMqRequest(mvHouseVO, ProfileMoveConstant.PROFILE_MOVE_ROUTING_KEY_REQUEST, ProfileMoveConstant.PROFILE_MOVE_EXCHANGE_NAME);
        return ResponseUtils.success(new HashMap<>(1 >> 4));
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
     *
     * @param profile         解析后的简历json串
     * @param operationId     profileMove表主键id
     * @param currentEmailNum 当前是第几封邮件
     * @return Response 返回是否调用成功
     * @author cjm
     * @date 2018/7/18
     */
    @Transactional(rollbackFor = Exception.class)
    public Response profileMove(String profile, int operationId, int currentEmailNum) throws TException, InterruptedException, ExecutionException, TimeoutException {

        logger.info("profile:{}, operationId:{}, currentEmailNum:{}", profile, operationId, currentEmailNum);
        TalentpoolProfileMoveRecord profileMove = profileMoveDao.fetchRecordById(operationId);
        if (profileMove == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
        }
        int hrId = profileMove.getHrId();
        HrCompanyDO hrCompanyDO = hrCompanyAccountDao.getHrCompany(hrId);
        if (hrCompanyDO == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        int companyId = hrCompanyDO.getId();
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = profileMoveRecordDao.getListByMoveId(operationId);
        JSONObject resumeObj = JSONObject.parseObject(profile);
        // 通过简历origin获取映射简历类型
        int crawlType = getCrawlTypeByOrigin(resumeObj);
        // 根据简历类型获取对应的操作记录
        TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = getProfileMoveRecordByCrawlType(profileMoveRecordRecords, crawlType);
        int state = profileMoveRecordRecord.getStatus();
        // 如果当前邮件数与总邮件数相同，认为本次搬家成功
        int totalEmailNum = 0;
        for(TalentpoolProfileMoveRecordRecord recordRecord : profileMoveRecordRecords){
            totalEmailNum += recordRecord.getTotalEmailNum();
        }
        // 同一渠道搬家状态是同时成功或失败的，如果有一个成功，就说明已经全部成功，不用再修改搬家状态
        boolean flag = true;
        if (currentEmailNum == totalEmailNum) {
            for(TalentpoolProfileMoveRecordRecord recordRecord : profileMoveRecordRecords){
                if(recordRecord.getStatus() == ProfileMoveStateEnum.SUCCESS.getValue()){
                    flag = false;
                    break;
                }
                recordRecord.setStatus(ProfileMoveStateEnum.SUCCESS.getValue());
            }
        }
        // 简历入库
        Future<Response> preserveFuture =
                pool.startTast(() -> wholeProfileService.preserveProfile(profile, null, hrId, companyId, 0, UserSource.MV_HOUSE.getValue()));
        Response preserveResponse = preserveFuture.get(120, TimeUnit.SECONDS);
        logger.info("===========================简历搬家preserveResponse:{}", preserveResponse);
        if (preserveResponse.getStatus() == 0) {
            String mobileStr = getUserMobileByProfile(resumeObj);
            long mobile = Long.parseLong(mobileStr);
            // 判断上一次是否入库过，如果搬过一次，是否搬成功了
            int currentCrawlNum = profileMoveRecordRecord.getCrawlNum();
            TalentpoolProfileMoveDetailRecord profileMoveDetailRecord = profileMoveDetailDao.getByMobile(mobile);
            if (profileMoveDetailRecord == null) {
                // 如果第一次搬该简历，搬家简历数+1
                currentCrawlNum = currentCrawlNum + 1;
                // 入库成功时，插入一条合并记录，状态为成功，如果之后chaos发送搬家状态失败，则将本次合并记录置为失败，这里做连表插入判断：exists
                insertCombineRecord(mobile, profileMoveRecordRecord.getId(), state, 1);
            } else{
                profileMoveDetailRecord.setUpdateTime(null);
                if (profileMoveDetailRecord.getProfileMoveStatus() == ProfileMoveStateEnum.SUCCESS.getValue()) {
                    // 如果是以前搬成功的简历，搬家简历数 + 1，由于可能多处修改这里，加乐观锁
                    currentCrawlNum = currentCrawlNum + 1;
                    updateProfileDetail(profileMoveDetailRecord, profileMoveRecordRecord.getId(), (byte)state, 1);
                } else {
                    // 如果上次搬失败了，本次搬的简历在上次已经计算在内，此次搬家简历数不+1，这次将其操作id和状态都修改掉
                    boolean isSameCompany = checkSameCompany(profileMoveDetailRecord.getProfileMoveRecordId(), companyId);
                    if (!isSameCompany) {
                        currentCrawlNum = currentCrawlNum + 1;
                    }
                    updateProfileDetail(profileMoveDetailRecord, profileMoveRecordRecord.getId(), (byte)state, 1);
                }
            }
            // 当currentEmailNum>当前简历类型的总email数量，将当前email减去当前简历类型的总email数量
//            if(profileMoveRecordRecord.getTotalEmailNum() < currentEmailNum){
//                currentEmailNum = currentEmailNum - profileMoveRecordRecord.getTotalEmailNum();
//            }
            profileMoveRecordRecord.setCurrentEmailNum(currentEmailNum);
            updateProfileMoveRecord(profileMoveRecordRecord, currentCrawlNum, 1);
            if(flag){
                profileMoveRecordDao.updateRecords(profileMoveRecordRecords);
            }
            return ResponseUtils.success(new HashMap<>(1 >> 4));
        }
        profileMoveRecordDao.updateRecords(profileMoveRecordRecords);
        return preserveResponse;
    }

    private TalentpoolProfileMoveRecordRecord getProfileMoveRecordByCrawlType(List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords, int crawlType) {
        TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = new TalentpoolProfileMoveRecordRecord();
        for(TalentpoolProfileMoveRecordRecord one : profileMoveRecordRecords){
            if(one.getCrawlType() == crawlType){
                profileMoveRecordRecord = one;
                break;
            }
        }
        return profileMoveRecordRecord;
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
        return 0;

    }

    /**
     * 计算简历搬家起始时间
     * @param   successMoveId 最近一次简历搬家成功的id
     * @param   profileMoveDOS 简历搬家操作记录DOs
     * @author  cjm
     * @date  2018/9/16
     * @return 返回简历搬家起始时间
     */
    private Date getProfileMoveStartDate(int successMoveId, List<TalentPoolProfileMoveDO> profileMoveDOS){
        // 过滤出搬家成功的list，去除第一个的创建时间为本次搬家的起始时间，如果list为空，则减去6个月为起始时间
        Date startDate = new Date();
        try {
            if(successMoveId == 0){
                logger.info("firstTime:{}", firstTime);
                startDate = new Date(System.currentTimeMillis() - firstTime);
            } else {
                for (TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS) {
                    if (profileMoveDO.getId() == successMoveId) {
                        startDate = new SimpleDateFormat("yyyy-MM-dd").parse(profileMoveDO.getEndDate());
                        break;
                    }
                }
            }

        } catch (Exception e) {
            startDate = new Date(System.currentTimeMillis() - firstTime);
        }
        return startDate;
    }

    private int getCrawlTypeByOrigin(JSONObject resumeObj) throws BIZException {
        String origin = resumeObj.getString("origin");
        if (StringUtils.isNullOrEmpty(origin)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_ORIGIN_NULL);
        }
        if (origin.equals(ChannelType.MVHOUSEJOB51DOWNLOAD.getOrigin("")) || origin.equals(ChannelType.MVHOUSEZHILIANDOWNLOAD.getOrigin(""))) {
            return 1;
        } else if(origin.equals(ChannelType.MVHOUSEJOB51UPLOAD.getOrigin("")) || origin.equals(ChannelType.MVHOUSEZHILIANUPLOAD.getOrigin(""))){
            return 0;
        } else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_ORIGIN_UPSUPORT);
        }

    }

    public Response getMoveOperationState(int hrId) {
        List<Map<String, Byte>> resultList = new ArrayList<>();
        List<TalentPoolProfileMoveDO> talentPoolProfileMoveDOS = profileMoveDao.getProfileMoveDOByHrId(hrId);
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

    private void updateProfileDetail(TalentpoolProfileMoveDetailRecord profileMoveDetailRecord, int profileMoveRecordId, byte state, int retryTimes) throws BIZException {
        byte status = (state == ProfileMoveStateEnum.MOVING.getValue() ? ProfileMoveStateEnum.SUCCESS.getValue() : state);
        profileMoveDetailRecord.setProfileMoveStatus(status);
        if (retryTimes > TRY_TIMES) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_UPDATE_FAILED);
        }
        profileMoveDetailRecord.setUpdateTime(null);
        int row = profileMoveDetailDao.updateWhereExist(profileMoveDetailRecord, profileMoveRecordId, state);
        if (row == 0) {
            TalentpoolProfileMoveRecordRecord recordRecord = profileMoveRecordDao.getOneProfileMoveRecordById(profileMoveRecordId);
            updateProfileDetail(profileMoveDetailRecord, profileMoveRecordId, recordRecord.getStatus(), ++retryTimes);
        }
    }

    private boolean checkSameCompany(Integer profileMoveId, int companyId) {
        TalentpoolProfileMoveRecordRecord profileMoveRecord = profileMoveRecordDao.getOneProfileMoveRecordById(profileMoveId);
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
     * 处理请求参数
     * @param companyDOS            第三方公司集合
     * @param userHrAccountDO       hr账号do
     * @param hrThirdPartyAccountDO hr第三方账号do
     * @param startDate             简历搬家起始时间
     * @param endDate               简历搬家结束时间
     * @param profileMoveId         简历搬家操作id
     * @param isFirstMove           是否是第一次搬家
     * @return 简历搬家请求vo
     * @author cjm
     * @date 2018/9/9
     * @throws BIZException bizException
     */
    public abstract MvHouseVO handleRequestParams(List<ThirdpartyAccountCompanyDO> companyDOS, UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO,
                                                  Date startDate, Date endDate, int profileMoveId, boolean isFirstMove) throws BIZException;

    /**
     * 插入简历搬家详细操作记录表 talentpool_profile_move_record
     * @param   profileMoveId 简历搬家id
     * @author  cjm
     * @date  2018/9/17
     */
    private void insertProfileMoveRecordRecord(int profileMoveId) {
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = new ArrayList<>();
        for (int i = 0; i < CrawlTypeEnum.values().length; i++) {
            TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = new TalentpoolProfileMoveRecordRecord();
            profileMoveRecordRecord.setProfileMoveId(profileMoveId);
            profileMoveRecordRecord.setCrawlType(CrawlTypeEnum.values()[i].getStatus());
            // 这里将创建时间和更新时间保持一致，由于chaos失败时会返回邮件数为0，并且修改更新时间，所以定时任务刷新时会根据这两个字段是否相等判断是搬家成功还是失败
            long currentTime = DateTime.now().getMillis();
            profileMoveRecordRecord.setCreateTime(new Timestamp(currentTime));
            profileMoveRecordRecord.setUpdateTime(new Timestamp(currentTime));
            profileMoveRecordRecords.add(profileMoveRecordRecord);
        }
        logger.info("profileMoveRecordRecords:{}", profileMoveRecordRecords);
        profileMoveRecordDao.addAllRecord(profileMoveRecordRecords);
    }

    /**
     * 插入简历搬家记录表  talentpool_profile_move
     * @param   hrId      hr.id
     * @param   channel   简历搬家渠道
     * @param   startDate 简历搬家起始时间
     * @param   endDate   简历搬家结束时间
     * @author  cjm
     * @date  2018/9/17
     * @return   返回插入的结果，主要是为了获取回填的主键id
     */
    private TalentpoolProfileMoveRecord insertProfileMoveRecord(int hrId, int channel, Date startDate, Date endDate) {
        TalentpoolProfileMoveRecord record = new TalentpoolProfileMoveRecord();
        record.setStartDate(new java.sql.Date(startDate.getTime()));
        record.setEndDate(new java.sql.Date(endDate.getTime()));
        record.setChannel((byte) channel);
        record.setHrId(hrId);
        record = profileMoveDao.addRecord(record);
        return record;
    }

    /**
     * 获取简历搬家操作列表，按照指定的时间规则转化时间格式
     * @author  cjm
     * @date  2018/9/17
     */
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
     * @param profileMoveRecordId profile_move_record.id
     * @param retryTimes 当前是第几次重试
     * @author cjm
     * @date 2018/9/10
     */
    private void insertCombineRecord(long mobile, int profileMoveRecordId, int state, int retryTimes) throws BIZException {
        if(retryTimes >= TRY_TIMES){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DB_UPDATE_FAILED);
        }
        TalentpoolProfileMoveDetailRecord record = new TalentpoolProfileMoveDetailRecord();
        record.setMobile(mobile);
        record.setProfileMoveRecordId(profileMoveRecordId);
        record.setProfileMoveStatus(state == ProfileMoveStateEnum.MOVING.getValue() ? ProfileMoveStateEnum.SUCCESS.getValue() : (byte)state);
        int row = profileMoveDetailDao.addWhereExistStatus(record, (byte)state);
        if (row == 0) {
            TalentpoolProfileMoveRecordRecord recordRecord = profileMoveRecordDao.getOneProfileMoveRecordById(profileMoveRecordId);
            insertCombineRecord(mobile, profileMoveRecordId, recordRecord.getStatus(), ++retryTimes);
        }
    }

    /**
     * 简历搬家操作记录表更新
     *
     * @param record 简历搬家操作记录record
     * @author cjm
     * @date 2018/7/23
     */
    private void updateProfileMoveRecord(TalentpoolProfileMoveRecordRecord record, int currentCrawlNum, int retryTimes) throws BIZException {
        if (retryTimes > TRY_TIMES) {
            throw new BIZException(1, "简历搬家操作记录数据库更新失败");
        }
        int row = profileMoveRecordDao.updateRecordWithPositiveLock(record, currentCrawlNum);
        if (row == 0) {
            record = profileMoveRecordDao.getOneProfileMoveRecordById(record.getId());
            currentCrawlNum = record.getCrawlNum() + 1;
            updateProfileMoveRecord(record, currentCrawlNum, ++retryTimes);
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
