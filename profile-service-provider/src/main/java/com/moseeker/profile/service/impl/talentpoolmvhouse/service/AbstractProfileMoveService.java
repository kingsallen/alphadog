package com.moseeker.profile.service.impl.talentpoolmvhouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
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
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveConstant;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseOperationVO;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;

import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.ProfileMoveOperationInfoVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
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

    protected Logger logger = LoggerFactory.getLogger(AbstractProfileMoveService.class);

    protected SerializeConfig serializeConfig = new SerializeConfig();

    protected ThreadPool pool = ThreadPool.Instance;

    protected static final int TRY_TIMES = 3;

    /**
     * 简历搬家用户登录
     *
     * @param form 简历搬家所需参数
     * @return response 是chaos返回的结果，没有对结果处理
     * @author cjm
     * @date 2018/7/18
     */
    @Transactional(rollbackFor = Exception.class)
    public Response moveHouseLogin(ProfileMoveForm form) throws BIZException {
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
        List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getListByHrIdDesc(hrId);
        List<Integer> moveIds = profileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS = profileMoveRecordDao.getListByMoveIds(moveIds);
        // 过滤出搬家成功的list，去除第一个的创建时间为本次搬家的起始时间，如果list为空，则减去6个月为起始时间
        profileMoveRecordDOS = profileMoveRecordDOS.stream()
                .filter(profileMoveRecordDO -> profileMoveRecordDO.getStatus() == 1).collect(Collectors.toList());
        Date startDate = new Date();
        Date endDate = new Date();
        try{
            if(profileMoveRecordDOS.size() != 0){
                int firstId = profileMoveRecordDOS.get(0).getProfileMoveId();
                for(TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS){
                    if(profileMoveDO.getId() == firstId){
                        startDate = new SimpleDateFormat("yyyy-MM-dd").parse(profileMoveDOS.get(0).getEndDate());
                        break;
                    }
                }
            }else{
                // 当前时间减去六个月
                startDate = new Date(System.currentTimeMillis() - 6 * 30 * 24 * 3600);
            }
        }catch (Exception e){
            startDate = new Date(System.currentTimeMillis() - 6 * 30 * 24 * 3600);
        }
        // 映射简历搬家参数
        List<MvHouseVO>  mvHouseVOs = handleRequestParams(userHrAccountDO, hrThirdPartyAccountDO, startDate, endDate);
        for(MvHouseVO mvHouseVO : mvHouseVOs){
            sender.sendMqRequest(mvHouseVO, ProfileMoveConstant.PROFILE_MOVE_ROUTING_KEY_REQUEST, ProfileMoveConstant.PROFILE_MOVE_EXCHANGE_NAME);
        }
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
        List<Integer> profileMoveIds = profileMoveDOS.stream().map(TalentPoolProfileMoveDO::getId).collect(Collectors.toList());
        List<TalentPoolProfileMoveRecordDO> profileMoveRecordDOS = profileMoveRecordDao.getListByMoveIds(profileMoveIds);
        Map<Integer, List<TalentPoolProfileMoveRecordDO>> map = new HashMap<>(1 >> 6);
        for(TalentPoolProfileMoveRecordDO profileMoveRecordDO : profileMoveRecordDOS){
            if(map.get(profileMoveRecordDO.getProfileMoveId()) == null){
                List<TalentPoolProfileMoveRecordDO> list = new ArrayList<>();
                list.add(profileMoveRecordDO);
                map.put(profileMoveRecordDO.getProfileMoveId(), list);
            }else {
                List<TalentPoolProfileMoveRecordDO> list = map.get(profileMoveRecordDO.getProfileMoveId());
                list.add(profileMoveRecordDO);
                map.put(profileMoveRecordDO.getProfileMoveId(), list);
            }
        }
        JSONObject jsonObject = new JSONObject();
        List<MvHouseOperationVO> operationList = getOperationList(map, profileMoveDOS);

        jsonObject.put("operation_list", JSONArray.parseArray(JSON.toJSONString(operationList, serializeConfig)));
        jsonObject.put("total", rows);
        return ResponseUtils.success(jsonObject);
    }

    /**
     * 简历搬家
     * todo 事务注解需要添加
     * @param profile         解析后的简历json串
     * @param operationId     profileMove表主键id
     * @param currentEmailNum 当前是第几封邮件
     * @return Response 返回是否调用成功
     * @author cjm
     * @date 2018/7/18
     */
    public Response profileMove(String profile, int operationId, int currentEmailNum) throws TException, InterruptedException, ExecutionException, TimeoutException {

        logger.info("profile:{}, operationId:{}, currentEmailNum:{}", profile, operationId, currentEmailNum);
        TalentpoolProfileMoveRecordRecord profileMoverecord = profileMoveRecordDao.getProfileMoveRecordById(operationId);
        logger.info("=======================简历搬家record:{}", profileMoverecord);
        if (profileMoverecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
        }
        String mobileStr = getUserMobileByProfile(profile);
        int profileMoveId = profileMoverecord.getProfileMoveId();
        // todo 一些参数的检验暂时没写
        TalentpoolProfileMoveRecord profileMove = profileMoveDao.fetchRecordById(profileMoveId);
        int hrId = profileMove.getHrId();
        HrThirdPartyAccountDO hrThirdPartyAccountDO = hrThirdPartyAccountDao.getAccountById(hrId);
        int companyId = hrThirdPartyAccountDO.getCompanyId();
        // 简历入库
        Future<Response> preserveFuture =
                pool.startTast(() -> wholeProfileService.preserveProfile(profile, null, hrId, companyId, 0, UserSource.MV_HOUSE.getValue(), 0));
        Response preserveResponse = preserveFuture.get(60, TimeUnit.SECONDS);
        logger.info("===========================简历搬家preserveResponse:{}", preserveResponse);
        if (preserveResponse.getStatus() == 0) {
            // 判断上一次是否入库过，如果搬过一次，是否搬成功了
            long mobile = Long.parseLong(mobileStr);
            TalentpoolProfileMoveDetailRecord profileMoveDetailRecord = poolProfileMoveDetailDao.getByMobileAndState(mobile,  ProfileMoveStateEnum.FAILED.getValue());
            if(profileMoveDetailRecord == null){
                // 如果上次成功了或者这是第一次搬该简历，搬家简历数+1，如果失败了，本次搬的简历在上次已经计算在内，此次搬家简历数不+1
                profileMoverecord.setCrawlNum(profileMoverecord.getCrawlNum() + 1);
                // 入库成功时，插入一条合并记录，状态为成功，如果之后chaos发送搬家状态失败，则将本次合并记录置为失败，这里做连表插入判断：exists
                insertCombineRecord(mobile, operationId, ProfileMoveStateEnum.SUCCESS.getValue());
            }else {
                // 如果上次搬失败了，这次将其操作id和状态都修改掉
                profileMoveDetailRecord.setProfileMoveId(operationId);
                profileMoveDetailRecord.setProfileMoveStatus(ProfileMoveStateEnum.SUCCESS.getValue());
            }
            updateProfileMove(profileMoverecord, currentEmailNum, 1);
            return ResponseUtils.success(new HashMap<>(1 >> 4));
        }
        return preserveResponse;
    }

    /**
     * @param  userHrAccountDO  hr账号do
     * @param  hrThirdPartyAccountDO hr第三方账号do
     * @param  startDate 简历搬家起始时间
     * @param  endDate  简历搬家结束时间
     * @author  cjm
     * @date  2018/9/9
     * @return 简历搬家请求vo集合
     */
    private List<MvHouseVO> handleRequestParams(UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO, Date startDate, Date endDate) throws BIZException {
        HrThirdPartyAccountHrDO hrDO = hrThirdPartyAccountHrDao.getHrAccountInfo(userHrAccountDO.getId(), hrThirdPartyAccountDO.getChannel());
        if(hrDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_NOT_EXISTS);
        }
        // 插入简历搬家TalentpoolProfileMove表
        TalentpoolProfileMoveRecord profileMoveRecord = insertProfileMoveRecord(userHrAccountDO.getId(), hrThirdPartyAccountDO.getChannel(), startDate, endDate);
        int profileMoveId = profileMoveRecord.getId();
        int thirdPartAccountId = hrDO.getThirdPartyAccountId();
        // 通过第三方账号获取第三方公司名称
        List<ThirdpartyAccountCompanyDO> thirdpartyAccountCompanyDOS = thirdCompanyDao.getCompanyByAccountId(thirdPartAccountId);
        // 获取公司id对应的名字
        Map<Integer, String> companyNames = thirdpartyAccountCompanyDOS.stream().collect(Collectors.toMap(ThirdpartyAccountCompanyDO::getId, ThirdpartyAccountCompanyDO :: getCompanyName));
        // 插入简历搬家操作TalentpoolProfileMoveRecord表
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = insertProfileMoveRecordRecord(profileMoveId, thirdpartyAccountCompanyDOS);
        List<MvHouseVO> mvHouseVOS = new ArrayList<>();
        for(TalentpoolProfileMoveRecordRecord record : profileMoveRecordRecords){
            ProfileMoveOperationInfoVO operationInfoVO = new ProfileMoveOperationInfoVO();
            operationInfoVO.setStart_date(new SimpleDateFormat("yyyy-MM-dd").format(startDate));
            operationInfoVO.setEnd_date(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
            operationInfoVO.setCrawl_type(record.getCrawlType());
            operationInfoVO.setCompany_name(companyNames.get(record.getThirdpartyCompanyId()));
            MvHouseVO mvHouseVO = new MvHouseVO();
            mvHouseVO.setAccount_id(userHrAccountDO.getId());
            mvHouseVO.setChannel(hrThirdPartyAccountDO.getChannel());
            mvHouseVO.setMember_name(hrThirdPartyAccountDO.getExt());
            mvHouseVO.setMobile(userHrAccountDO.getMobile());
            mvHouseVO.setOperation_id(record.getId());
            mvHouseVO.setUser_name(hrThirdPartyAccountDO.getUsername());
            mvHouseVO.setPassword(hrThirdPartyAccountDO.getPassword());
            mvHouseVO.setOperation_info(JSON.toJSONString(operationInfoVO));
            mvHouseVOS.add(mvHouseVO);
        }
        return mvHouseVOS;
    }

    private List<TalentpoolProfileMoveRecordRecord> insertProfileMoveRecordRecord(int profileMoveId, List<ThirdpartyAccountCompanyDO> thirdpartyAccountCompanyDOS) {
        List<TalentpoolProfileMoveRecordRecord> profileMoveRecordRecords = new ArrayList<>();
        // 暂时无法批量插入时获取返回的主键id，所以使用单条插入，这里for循环里只有四个数据，连接4次数据库
        for (ThirdpartyAccountCompanyDO companyDO : thirdpartyAccountCompanyDOS) {
            for(int i=0;i<CrawlTypeEnum.values().length;i++){
                TalentpoolProfileMoveRecordRecord profileMoveRecordRecord = new TalentpoolProfileMoveRecordRecord();
                profileMoveRecordRecord.setProfileMoveId(profileMoveId);
                profileMoveRecordRecord.setThirdpartyCompanyId(companyDO.getId());
                profileMoveRecordRecord.setCrawlType(CrawlTypeEnum.values()[i].getStatus());
                profileMoveRecordRecord = profileMoveRecordDao.addRecord(profileMoveRecordRecord);
                profileMoveRecordRecords.add(profileMoveRecordRecord);
            }
        }
        logger.info("profileMoveRecordRecords:{}", profileMoveRecordRecords);
        return profileMoveRecordRecords;
    }

    private TalentpoolProfileMoveRecord insertProfileMoveRecord(int hrId, int channel, Date startDate, Date endDate) {
        TalentpoolProfileMoveRecord record = new TalentpoolProfileMoveRecord();
        record.setStartDate(new java.sql.Date(startDate.getTime()));
        record.setEndDate(new java.sql.Date(endDate.getTime()));
        record.setChannel((byte)channel);
        record.setHrId(hrId);
        record = profileMoveDao.addRecord(record);
        return record;
    }


    private List<MvHouseOperationVO> getOperationList(Map<Integer, List<TalentPoolProfileMoveRecordDO>> map, List<TalentPoolProfileMoveDO> profileMoveDOS) {
        List<MvHouseOperationVO> operationList = new ArrayList<>();
        for(TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS){
            MvHouseOperationVO mvHouseOperationVO = new MvHouseOperationVO();
            byte status = getMvHouseState(profileMoveDO.getId(), map);
            int crawlNum = getMvProfileNum(profileMoveDO.getId(), map);
            mvHouseOperationVO.setStatus(status);

            if (status == ProfileMoveStateEnum.MOVING.getValue()) {
                mvHouseOperationVO.setStatusDisplay(ProfileMoveStateEnum.MOVING.getName());
            } else {
                // 应产品要求，如果搬家出错依然显示搬家完成
                mvHouseOperationVO.setStatusDisplay(ProfileMoveStateEnum.SUCCESS.getName());
            }
            mvHouseOperationVO.setCrawlNum(crawlNum);
            mvHouseOperationVO.setStartDate(profileMoveDO.getStartDate());
            mvHouseOperationVO.setChannel(profileMoveDO.getChannel());
            mvHouseOperationVO.setEndDate(profileMoveDO.getEndDate());
            operationList.add(mvHouseOperationVO);
        }
        return operationList;
    }

    private int getMvProfileNum(int id, Map<Integer, List<TalentPoolProfileMoveRecordDO>> map) {
        List<TalentPoolProfileMoveRecordDO> list = map.get(id);
        int num = 0;
        if(list != null && list.size() > 0){
            for(TalentPoolProfileMoveRecordDO profileMoveRecordDO : list){
                num = num + profileMoveRecordDO.getCrawlNum();
            }
        }
        return num;
    }

    private byte getMvHouseState(int id, Map<Integer, List<TalentPoolProfileMoveRecordDO>> map) {
        List<TalentPoolProfileMoveRecordDO> list = map.get(id);
        byte status = ProfileMoveStateEnum.SUCCESS.getValue();
        if(list != null && list.size() > 0){
            List<Byte> statusList = list.stream().map(TalentPoolProfileMoveRecordDO::getStatus).distinct().collect(Collectors.toList());
            if(statusList.contains(ProfileMoveStateEnum.MOVING.getValue())){
                status = ProfileMoveStateEnum.MOVING.getValue();
            }
        }
        return status;
    }


    private void insertCombineRecord(long mobile, int operationId, int status) {
        TalentpoolProfileMoveDetailRecord record = new TalentpoolProfileMoveDetailRecord();
        record.setMobile(mobile);
        record.setProfileMoveId(operationId);
        record.setProfileMoveStatus((byte)status);
        int row = poolProfileMoveDetailDao.addWhereExistStatus(record);
        if(row == 0){
            record.setProfileMoveStatus(ProfileMoveStateEnum.FAILED.getValue());
            poolProfileMoveDetailDao.addRecord(record);
        }
    }

    /**
     * 简历搬家操作记录表更新
     *
     * @param record          简历搬家操作记录record
     * @param currentEmailNum 当前简历合并操作包括的邮件数量
     * @author cjm
     * @date 2018/7/23
     */
    private void updateProfileMove(TalentpoolProfileMoveRecordRecord record, int currentEmailNum, int retryTimes) throws BIZException {
        if (retryTimes > TRY_TIMES) {
            throw new BIZException(1, "简历搬家操作记录数据库更新失败");
        }
        // todo 可能用不到乐观锁
        record.setCurrentEmailNum(currentEmailNum);
        // todo 需要判断是否
        int row = profileMoveRecordDao.updateRecordWithPositiveLock(record);
        if (row == 0) {
            record = profileMoveRecordDao.getProfileMoveRecordById(record.getId());
            updateProfileMove(record, currentEmailNum, ++retryTimes);
        }
    }

    /**
     * 获取简历中的手机号
     * @param  resume 简历json串
     * @author  cjm
     * @date  2018/9/6
     * @return  手机号
     */
    private String getUserMobileByProfile(String resume) throws BIZException {
        JSONObject resumeObj = JSONObject.parseObject(resume);
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
