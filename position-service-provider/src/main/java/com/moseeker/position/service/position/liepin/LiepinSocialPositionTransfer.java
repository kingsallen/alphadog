package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCityLiePinDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyFeatureDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.liepin.LiePinPositionDegree;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import com.moseeker.position.constants.position.liepin.LiepinPositionState;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.schedule.constant.LiepinPositionAuditState;
import com.moseeker.position.service.schedule.delay.LiepinSyncStateRefresh;
import com.moseeker.position.service.schedule.delay.PositionTaskQueueDaemonThread;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityLiePinDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LiepinSocialPositionTransfer extends LiepinPositionTransfer<LiePinPositionVO, LiePinPositionVO> {

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private HRThirdPartyAccountHrDao hrThirdPartyDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private DictCityLiePinDao dictCityLiePinDao;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private DictLiepinOccupationDao liepinOccupationDao;

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private LiePinReceiverHandler receiverHandler;

    @Autowired
    private HrCompanyFeatureDao featureDao;

    @Autowired
    private PositionEmailNotification emailNotification;

    @Autowired
    private PositionTaskQueueDaemonThread delayQueueThread;

    private Random random = new Random();

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition extPosition) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
        String feature = jsonObject.getString("feature");
        if (StringUtils.isNotNullOrEmpty(feature)) {
            feature = feature.replaceAll("\\s*", "");
            jsonObject.put("feature", feature.split(","));
        }
        String occupation = jsonObject.getString("occupation");
        if (StringUtils.isNotNullOrEmpty(occupation)) {
            occupation = occupation.replaceAll("\\s*", "");
            jsonObject.put("occupation", occupation.split(","));
        }
        return jsonObject;
    }

    @Override
    public LiePinPositionVO changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition, HrThirdPartyAccountDO account) throws Exception {
        LiePinPositionVO liePinPositionVO = new LiePinPositionVO();
        liePinPositionVO.setEjob_extRefid(String.valueOf(moseekerJobPosition.getId()));
        liePinPositionVO.setEjob_extfield(null);
        liePinPositionVO.setCount((int) moseekerJobPosition.getCount());
        liePinPositionVO.setPublisher(moseekerJobPosition.getPublisher());
        liePinPositionVO.setEjob_title(moseekerJobPosition.getTitle());
        liePinPositionVO.setCompanyId(moseekerJobPosition.getCompanyId());
        // 设置职能
        List<String> occupationList = positionForm.getOccupation();
        liePinPositionVO.setEjob_jobtitle(requireValidOccupation(occupationList));
        // 获取城市code，多个城市用逗号隔开
        int positionId = moseekerJobPosition.getId();
        liePinPositionVO.setPositionId(positionId);
        // 映射城市code
        liePinPositionVO.setEjob_dq(mappingCityCode(positionId));
        liePinPositionVO.setEjob_privacyreq(null);
        liePinPositionVO.setEjob_salarydiscuss(positionForm.isSalaryDiscuss() ? "1" : "0");
        // 单位 : 万/年
        liePinPositionVO.setEjob_salarylow((float) positionForm.getSalaryBottom() * positionForm.getSalaryMonth() / 10);
        liePinPositionVO.setEjob_salaryhigh((float) positionForm.getSalaryTop() * positionForm.getSalaryMonth() / 10);
        liePinPositionVO.setDetail_workyears(moseekerJobPosition.getExperience());
        liePinPositionVO.setDetail_sex(moseekerJobPosition.getGender() == 0 ? "女" : moseekerJobPosition.getGender() == 1 ? "男" : null);
        liePinPositionVO.setDetail_agelow(moseekerJobPosition.getAge() == 0 ? 20 : (int) moseekerJobPosition.getAge());
        liePinPositionVO.setDetail_agehigh(65);
        // 映射学位
        int degree = (int) moseekerJobPosition.getDegree();
        liePinPositionVO.setDetail_edulevel(LiePinPositionDegree.getPositionDegree(degree).getLiePinDegreeNo());
        liePinPositionVO.setDetail_edulevel_tz(null);
        liePinPositionVO.setDetail_report2(null);
        liePinPositionVO.setDetail_subordinate(moseekerJobPosition.getUnderlings() == 0 ? 0 : (int) moseekerJobPosition.getUnderlings());
        String duty = moseekerJobPosition.getAccountabilities();
        String requirement = moseekerJobPosition.getRequirement();
        duty = filterSpecialSign(duty);
        requirement = filterSpecialSign(requirement);

        liePinPositionVO.setDetail_duty(duty);
        liePinPositionVO.setDetail_require(requirement);
        // 映射部门名称
        liePinPositionVO.setDetail_dept(getDepartmentName(positionForm, moseekerJobPosition));
        // 每个亮点八个字，最多十六个
        positionForm.setCompanyId(moseekerJobPosition.getCompanyId());
        String feature = getValidFeature(positionForm);
        liePinPositionVO.setDetail_tags(feature);
        // 映射语言要求
        mappingLanguageRequire(liePinPositionVO, moseekerJobPosition);
        liePinPositionVO.setDetail_special(moseekerJobPosition.getMajorRequired());
        liePinPositionVO.setEmail_list_array(null);
        liePinPositionVO.setEjob_level(null);
        liePinPositionVO.setCount((int) moseekerJobPosition.getCount());
        return liePinPositionVO;
    }


    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition thirdPartyPosition, LiePinPositionVO pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        logger.info("================syncTime:{}===============", syncTime);
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setOccupation(pwa.getEjob_jobtitle());
        data.setChannel(getChannel().getValue());
        data.setDepartment(pwa.getDetail_dept());
        data.setThirdPartyAccountId(thirdPartyPosition.getThirdPartyAccountId());
        data.setPositionId(Integer.parseInt(pwa.getEjob_extRefid()));
        data.setIsSynchronization((byte) PositionSync.binding.getValue());
        data.setSalaryMonth(thirdPartyPosition.getSalaryMonth());
        data.setFeedbackPeriod(thirdPartyPosition.getFeedbackPeriod());
        data.setSalaryDiscuss(thirdPartyPosition.isSalaryDiscuss() ? 1 : 0);
        data.setSalaryBottom(thirdPartyPosition.getSalaryBottom() * 1000);
        data.setSalaryTop(thirdPartyPosition.getSalaryTop() * 1000);
        data.setPracticeSalary(thirdPartyPosition.getPracticeSalary());
        data.setPracticePerWeek(thirdPartyPosition.getPracticePerWeek());
        data.setPracticeSalaryUnit(thirdPartyPosition.getPracticeSalaryUnit());
        data.setCompanyId(pwa.getCompanyId());
        data.setCompanyName(thirdPartyPosition.getCompanyName());
        data.setAddressId(thirdPartyPosition.getAddressId());
        data.setAddressName(thirdPartyPosition.getAddressName());
        data.setDepartmentId(thirdPartyPosition.getDepartmentId());
        data.setDepartmentName(thirdPartyPosition.getDepartmentName());
        data.setFeature(pwa.getDetail_tags());
        data.setCount(pwa.getCount());

        logger.info("回写到第三方职位对象:{}", data);
        return data;
    }

    @Override
    public boolean extTransferCheck(JobPositionDO positionDB) {
        return positionDB.getCandidateSource() == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSyncRequest(TransferResult<LiePinPositionVO, LiePinPositionVO> result) throws TException {
        LiePinPositionVO liePinPositionVO = result.getPositionWithAccount();
        try {
            // 这个city是citycodes字符串，是要发布的城市
            String citys = liePinPositionVO.getEjob_dq();

            String[] cityCodesArr = citys.split("[,，]");
            if (cityCodesArr.length < 1) {
                return;
            }
            List<String> errCityCodeList = new ArrayList<>();

            // 当出现token失效导致发布失败时，认为该职位在猎聘尚未发布，不在jobliepinmapping表中保存数据，
            // 但是由于需要现在表中生成主键id，所以当发生业务异常时，会对新插入的记录进行物理删除，flag默认true
            boolean flag = true;

            Integer channel = ChannelType.LIEPIN.getValue();

            // 发布职位的hrid
            Integer publisher = liePinPositionVO.getPublisher();

            // 获取第三方账号token
            HrThirdPartyAccountDO thirdPartyAccountDO = getThirdPartyAccount(publisher, channel);
            if (thirdPartyAccountDO == null) {
                logger.info("=================无第三方hr账号数据===============");
                return;
            }
            String liePinToken = thirdPartyAccountDO.getExt2();
            String liePinUserIdStr = thirdPartyAccountDO.getExt();
            if (StringUtils.isNullOrEmpty(liePinToken) || StringUtils.isNullOrEmpty(liePinUserIdStr)) {
                logger.info("=================账号未绑定猎聘, thirdPartyAccountDO:{}===============", thirdPartyAccountDO);
                return;
            }
            int liePinUserId = Integer.parseInt(liePinUserIdStr);
            int thirdAccountId = thirdPartyAccountDO.getId();

            HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();
            hrThirdPartyPositionDO.setThirdPartyAccountId(thirdAccountId);

            int cityNum = cityCodesArr.length;
            // 当发布职位时多个城市时，如果每个新的城市都发布成功，才视为仟寻职位同步成功，否则视为同步失败
            int successSyncNum = 0;
            // 发布职位时如果存在之前发布过但是下架的职位，需要将这类职位上架并修改
            int successRePublishNum = 0;
            // 发布职位时如果存在之前发布过并且状态正常的职位，需要对职位同步修改
            int editNum = 0;
            Integer positionId = liePinPositionVO.getPositionId();
            List<String> cityCodesList = Arrays.asList(cityCodesArr);
            List<Integer> cityCodesIntList = cityCodesList.stream().map(cityCode -> Integer.parseInt(cityCode)).collect(Collectors.toList());

            // 查出所有本次编辑城市职位jobpostionmapping表数据
            List<JobPositionLiepinMappingDO> liepinMappingDOList =
                    liepinMappingDao.getMappingDataByTitleAndUserId(liePinPositionVO.getEjob_title(), liePinUserId, cityCodesIntList, positionId);

            // 获取本次职位发布中已存在且状态为0的职位，需要执行先上架后编辑操作
            List<String> republishCity = new ArrayList<>();
            Map<String, Object> repubCityIdCodeMap = new HashMap<>();
            for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                // 该title下的职位处于下架、删除状态，用于重新发布到猎聘
                if (mappingDO.getState() != 1) {
                    republishCity.add(String.valueOf(mappingDO.getCityCode()));
                    repubCityIdCodeMap.put(String.valueOf(mappingDO.getCityCode()), mappingDO.getId());
                }
            }

            // 获取本次职位发布中已存在且状态为1的职位，需要执行编辑操作
            List<String> editCity = new ArrayList<>();
            Map<String, Object> editCityIdCodeMap = new HashMap<>();
            for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                if (mappingDO.getState() == 1 && cityCodesList.contains(String.valueOf(mappingDO.getCityCode()))) {
                    editCity.add(String.valueOf(mappingDO.getCityCode()));
                    editCityIdCodeMap.put(String.valueOf(mappingDO.getCityCode()), mappingDO.getId());
                }
            }

            List<String> cityCodesListDb = new ArrayList<>();
            // 获取本次职位发布中的所有数据库中的职位
            for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                cityCodesListDb.add(String.valueOf(mappingDO.getCityCode()));
            }

            String errorMsg = "";
            StringBuilder republishIds = new StringBuilder();
            // 成功发布职位的mapping表主键ids
            List<Integer> successSyncIds = new ArrayList<>();
            for (String cityCode : cityCodesList) {
                // 只要有相同title和城市的职位，就不发布
                if (liepinMappingDOList.size() > 0) {
                    // 如果存在已同步记录信息，需要判断下是否要是否是新的地区或者title
                    if (cityCodesListDb.contains(cityCode)) {
                        // 状态不为1的城市中如果包括本次发布城市
                        if (republishCity.contains(cityCode)) {
                            republishIds.append(repubCityIdCodeMap.get(cityCode)).append(",");
                        }
                        continue;
                    }
                }

                String liepinCityCode = getValidLiepinDictCode(cityCode);

                // 生成职位发布到猎聘时需要的id,插入一条mapping数据
                JobPositionLiepinMappingDO jobPositionLiepinMappingDO = getNewJobPositionLiepinMapping(liePinPositionVO, cityCode, liePinUserId);
                liePinPositionVO.setEjob_extRefid(jobPositionLiepinMappingDO.getId() + "");
                liePinPositionVO.setEjob_dq(liepinCityCode);

                String httpResultJson = null;
                try {
                    httpResultJson = sendSyncRequestToLiepin(liePinPositionVO, liePinToken);
                    String thirdPositionId = "";
                    byte state = (byte)LiepinPositionState.UNPUBLISH.getValue();
                    try {
                        JSONObject httpResult = JSONObject.parseObject(httpResultJson);
                        logger.info("==============httpResult:{}===============", httpResult);

                        if (null != httpResult && httpResult.getIntValue("code") == 0) {
                            String data = httpResult.getString("data");
                            thirdPositionId = data.substring(1, data.length() - 1);
                            state = (byte)LiepinPositionState.PUBLISH.getValue();
                            successSyncNum++;
                            successSyncIds.add(jobPositionLiepinMappingDO.getId());
                        } else if (null != httpResult) {
                            if (httpResult.getIntValue("code") == 1007) {
                                //token失效
                                errorMsg = "会员名、用户名或密码错误，请重新绑定账号";
                            }else{
                                errorMsg = httpResult.getString("message");
                            }
                            flag = false;
                        } else {
                            flag = false;
                            errorMsg = "http请求失败";
                            errCityCodeList.add(cityCode);
                        }
                    } catch (Exception e) {
                        flag = false;
                        logger.error(e.getMessage(), e);
                        emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e, null);
                    }

                    // 如果同步失败，将mapping记录删除
                    int id = jobPositionLiepinMappingDO.getId();
                    if (!flag) {
                        liepinMappingDao.deleteData(jobPositionLiepinMappingDO);
                        emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, null, httpResultJson);
                    } else {
                        // 更改数据库mapping的状态
                        liepinMappingDao.updateJobInfoById(id, StringUtils.isNullOrEmpty(thirdPositionId) ? null : Integer.parseInt(thirdPositionId), state, errorMsg);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    errCityCodeList.add(cityCode);
                    emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e, null);
                    continue;
                }

            }

            try {
                // 发布职位时如果是已经存在的职位，但是状态为0，则执行先上架后修改
                successRePublishNum = updateExistPosition(republishIds, liePinToken, positionId, liePinPositionVO);

                // 编辑修改职位
                editNum = editExistPosition(positionId, editCity, editCityIdCodeMap, liePinPositionVO, liePinToken);

                // 职位发布后可能发布成功但是需要审核，该操作是发布后获取职位审核状态
                for(int mappingId : successSyncIds){
                    JSONObject positionInfoDetail = getPositionAuditState(mappingId, liePinToken, positionId, channel);
                    logger.info("=======================positionInfoDetail:{}", positionInfoDetail);
                    if(positionInfoDetail != null){
                        String audit = positionInfoDetail.getString("ejob_auditflag");
                        if(StringUtils.isNullOrEmpty(audit)){
                            continue;
                        }
                        // 根据职位审批状态进行不同的同步状态处理，如果待审核，同步状态设置为2，如果审核通过，同步状态设置为1，如果审核失败，同步状态设置为3，设置errmsg
                        if(LiepinPositionAuditState.WAITCHECK.getValue().equals(audit)){
                            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.binding.getValue());
                            liepinMappingDao.updateState(mappingId, (byte) LiepinPositionState.UNPUBLISH.getValue());
                            // 放入延时队列去更新职位审核状态
                            LiepinSyncStateRefresh liepinSyncStateRefresh = new LiepinSyncStateRefresh(thirdAccountId);
                            delayQueueThread.put(liepinSyncStateRefresh.timeout + random.nextInt(5 * 60 * 1000), liepinSyncStateRefresh);
                        }else if(LiepinPositionAuditState.PASS.getValue().equals(audit)){
                            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.bound.getValue());
                        }else if(LiepinPositionAuditState.NOTPASS.getValue().equals(audit)){
                            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.failed.getValue());
                            errorMsg = "审核不通过，请修改职位信息后重新发布。(审核失败原因可能是:1、校招职位在社招渠道发布;2、职位信息中包含网站链接等。)";
                            liepinMappingDao.updateState(mappingId, (byte) LiepinPositionState.UNPUBLISH.getValue());
                        }
                    }
                }

            }catch (BIZException e){
                logger.warn(e.getMessage(), e);
                errorMsg = e.getMessage();
                emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, e, null);
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
                emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e1, null);
            }

            logger.info("============cityNum:{},successRePublishNum:{},successSyncNum:{},editNum:{}==============", cityNum, successRePublishNum, successSyncNum, editNum);
            if (successSyncNum + successRePublishNum + editNum != cityNum) {
                // 获取职位审核状态，true表示待审核，将第三方职位同步状态设置为2，false表示
                if (StringUtils.isNullOrEmpty(errorMsg)) {
                    hrThirdPartyPositionDO.setIsSynchronization(4);
                    hrThirdPartyPositionDO.setSyncFailReason("http请求失败，城市code:" + errCityCodeList.toString());
                } else {
                    hrThirdPartyPositionDO.setIsSynchronization(3);
                    hrThirdPartyPositionDO.setSyncFailReason(errorMsg);
                }

            }
            hrThirdPartyPositionDO.setPositionId(positionId);
            hrThirdPartyPositionDO.setOccupation(liePinPositionVO.getEjob_jobtitle());
            hrThirdPartyPositionDO.setChannel(2);
            TwoParam<HrThirdPartyPositionDO, HrThirdPartyPositionDO> twoParam = new TwoParam<>(hrThirdPartyPositionDO, null);
            thirdPartyPositionDao.upsertThirdPartyPosition(twoParam);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e, null);
        }

    }

    /**
     * 插入一条新的jobpositionmapping数据，用来获取主键id
     * @param
     * @author  cjm
     * @date  2018/7/2
     * @return
     */
    private JobPositionLiepinMappingDO getNewJobPositionLiepinMapping(LiePinPositionVO liePinPositionVO, String cityCode, int liePinUserId) {
        JobPositionLiepinMappingDO jobPositionLiepinMappingDO = new JobPositionLiepinMappingDO();
        jobPositionLiepinMappingDO.setPositionId(liePinPositionVO.getPositionId());
        jobPositionLiepinMappingDO.setCityCode(Integer.parseInt(cityCode));
        jobPositionLiepinMappingDO.setJobTitle(liePinPositionVO.getEjob_title());
        jobPositionLiepinMappingDO.setLiepinUserId(liePinUserId);
        return liepinMappingDao.addData(jobPositionLiepinMappingDO);
    }

    private HrThirdPartyAccountDO getThirdPartyAccount(Integer publisher, Integer channel) {
        // 获取第三方账号和hr账号关联表数据
        HrThirdPartyAccountHrDO hrThirdDO = hrThirdPartyDao.getHrAccountInfo(publisher, channel);

        if (hrThirdDO == null) {
            logger.info("=================无第三方hr账号关联数据===============");
            return null;
        }
        int thirdAccountId = hrThirdDO.getThirdPartyAccountId();

        return thirdPartyAccountDao.getAccountById(thirdAccountId);

    }

    /**
     * 获取猎聘职位审核状态
     * @param
     * @author  cjm
     * @date  2018/7/2
     * @return
     */
    public JSONObject getPositionAuditState(int jobPositionMappingId, String liePinToken, int positionId, int channel) throws Exception {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("ejob_extRefids", jobPositionMappingId);
        String httpResultJson = receiverHandler.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionGet);
        logger.info("=======================获取职位信息结果httpResultJson:{}", httpResultJson);
        JSONObject httpResult = receiverHandler.requireValidResult(httpResultJson, positionId, channel);
        JSONObject positionInfo = httpResult.getJSONObject("data");
        if(positionInfo == null){
            emailNotification.sendRefreshSyncStateFailEmail("mappingId:" + jobPositionMappingId + "</br>" + "httpResultJson:" + httpResultJson, null);
            return null;
        }
        return positionInfo.getJSONObject("data");
    }

    private int editExistPosition(int positionId, List<String> editCity, Map<String, Object> editCityIdCodeMap, LiePinPositionVO liePinPositionVO, String liePinToken) throws Exception {
        if (editCity.size() > 0) {

            for (String citycode : editCity) {
                String mappingId = String.valueOf(editCityIdCodeMap.get(citycode));
                String editResponse = "";
                try {
                    String liepinCityCode = getValidLiepinDictCode(citycode);
                    liePinPositionVO.setEjob_extRefid(mappingId);
                    liePinPositionVO.setEjob_dq(liepinCityCode);
                    editResponse = receiverHandler.sendRequest2LiePin((JSONObject) JSONObject.toJSON(liePinPositionVO), liePinToken, LiepinPositionOperateConstant.liepinPositionEdit);
                    logger.info("==================editResponse:{}==================", editResponse);

                    receiverHandler.requireValidResult(editResponse, positionId, ChannelType.LIEPIN.getValue());
                } catch (BIZException e) {
                    e.printStackTrace();
                    logger.warn("============同步已存在的城市，修改该城市的职位信息时未操作成功，message:{}===========", e.getMessage());
                    emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, e, editResponse);
                    throw e;
                }
            }
            return editCity.size();
        }
        return 0;
    }

    /**
     * 发布职位时如果是已经存在的职位，但是状态为0，则执行先上架后修改
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/22
     */
    public int updateExistPosition(StringBuilder republishIds, String liePinToken, Integer positionId, LiePinPositionVO liePinPositionVO) throws Exception {
        // 更新上架后的状态
        if (republishIds.length() > 0) {
            List<Integer> republishIdList = new ArrayList<>();
            // 上架后返回此次上架职位的数量
            try{
                republishIdList = upshelfJobPosition(republishIds, liePinToken, positionId);
            }catch (Exception e){
                logger.error(e.getMessage(), e);
                emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, e, null);
            }

            logger.info("===========上架后返回此次上架职位的数量republishIdList:{}============", republishIdList);
            // 上架后向猎聘发送修改职位
            for (Integer republishId : republishIdList) {
                String editResponse = "";
                try {
                    logger.info("===========上架后向猎聘发送修改职位republishId:{}============", republishId);
                    liePinPositionVO.setEjob_extRefid(String.valueOf(republishId));

                    editResponse = receiverHandler.sendRequest2LiePin((JSONObject) JSONObject.toJSON(liePinPositionVO), liePinToken, LiepinPositionOperateConstant.liepinPositionEdit);
                    logger.info("==================editResponse:{}==================", editResponse);

                    receiverHandler.requireValidResult(editResponse, positionId, ChannelType.LIEPIN.getValue());
                } catch (BIZException e) {
                    e.printStackTrace();
                    logger.warn("============同步已存在的城市，修改该城市的职位信息时未操作成功，message:{}===========", e.getMessage());
                    emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, e, editResponse);
                    throw e;
                }
            }
            return republishIdList.size();
        }
        return 0;
    }

    private String sendSyncRequestToLiepin(LiePinPositionVO liePinPositionVO, String liePinToken) throws Exception {
        // 构造请求数据
        JSONObject liePinJsonObject = (JSONObject) JSONObject.toJSON(liePinPositionVO);
        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
        liePinJsonObject.put("t", t);
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
        liePinJsonObject.put("sign", sign);
        logger.info("=============liePinJsonObject:{}=============", liePinJsonObject);
        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", LiepinPositionOperateConstant.liepinChannel);
        headers.put("token", liePinToken);

        return HttpClientUtil.sentHttpPostRequest(LiepinPositionOperateConstant.liepinPositionSync, headers, liePinJsonObject);
    }

    /**
     * 通过仟寻citycode获取猎聘citycode
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/21
     */
    public String getValidLiepinDictCode(String cityCode) throws BIZException {
        DictCityLiePinDO dictCityLiePinDO = dictCityLiePinDao.getLiepinDictCodeByCode(cityCode);

        if (dictCityLiePinDO == null) {
            DictCityDO dictCityDO = new DictCityDO();
            dictCityDO.setCode(Integer.parseInt(cityCode));
            List<DictCityDO> dictCityDOS = dictCityDao.getMoseekerLevels(dictCityDO);

            if (dictCityDOS != null && dictCityDOS.size() > 1) {
                for (int i = 1; i < dictCityDOS.size() && dictCityLiePinDO == null; i++) {
                    dictCityLiePinDO = dictCityLiePinDao.getLiepinDictCodeByCode(String.valueOf(dictCityDOS.get(i).getCode()));
                }
            } else {
                logger.info("===============citycode:{}=============", cityCode);
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_CITYCODE_INVALID);
            }
        }

        if (dictCityLiePinDO != null) {

            return dictCityLiePinDO.getCode();

        } else {
            throw ExceptionUtils.getBizException("错误的仟寻citycode，查不到该code的所有城市level");
        }
    }


    public List<Integer> upshelfJobPosition(StringBuilder republishIds, String liePinToken, Integer positionId) throws Exception {
        byte state = 1;
        JSONObject liePinJsonObject = new JSONObject();
        // 去掉末尾的逗号
        liePinJsonObject.put("ejob_extRefids", republishIds.substring(0, republishIds.length() - 1));

        String httpResultJson = receiverHandler.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionRepub);

        receiverHandler.requireValidResult(httpResultJson, positionId, ChannelType.LIEPIN.getValue());

        List<Integer> republishIdList = getRepublishList(republishIds.toString());

        liepinMappingDao.updateStateAndJobId(republishIdList, state, positionId);

        return republishIdList;
    }


    private List<Integer> getRepublishList(String republishIds) {
        String[] ids = republishIds.split(",");
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            if (StringUtils.isNotNullOrEmpty(id)) {
                list.add(Integer.parseInt(id));
            }
        }
        return list;
    }

    /**
     * 将前端传来的职能组合成以逗号隔开的格式
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/19
     */
    private String requireValidOccupation(List<String> occupationList) throws BIZException {
        logger.info("=================occupationList:{}=============", occupationList.toString());
        StringBuilder occupation = new StringBuilder();
        // 获取所有猎聘社招职能
        List<DictLiepinOccupationDO> allSocialOccupation = liepinOccupationDao.getAllSocialOccupation();

        List<String> allSocialCode = allSocialOccupation.stream().map(socialOccupation -> socialOccupation.getOtherCode()).collect(Collectors.toList());

        List<String> moseekerCodeList = new ArrayList<>();
        int index = 0;

        for (String moseekerCode : occupationList) {
            logger.info("===========moseekerCode:{}==========", moseekerCode);
            if (StringUtils.isNullOrEmpty(moseekerCode)) {
                continue;
            }
            String code = moseekerCode;

            if (moseekerCode.startsWith("[")) {
                moseekerCodeList = JSONArray.parseArray(moseekerCode, String.class);
                if (moseekerCodeList.size() < 2) {
                    logger.info("============单个职能数组长度小于2==============");
                    continue;
                }
                code = moseekerCodeList.get(moseekerCodeList.size() - 1).trim();
            }

            if (allSocialCode.contains(code) && index < 3) {
                occupation.append(code).append(",");
                index++;
            }
        }
        if (occupation.length() > 0) {
            return occupation.substring(0, occupation.length() - 1);
        }
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_OCCUPATION_INVALID);
    }

    /**
     * 映射部门名字，本是由第三方页面手动填入，若为空，使用仟寻职位部门
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/11
     */
    private String getDepartmentName(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition) {
        String departmentName = null;
        if (StringUtils.isNotNullOrEmpty(positionForm.getDepartmentName())) {
            departmentName = positionForm.getDepartmentName();
        } else {
            departmentName = moseekerJobPosition.getDepartment();
        }
        return departmentName;
    }

    private String mappingCityCode(int positionId) throws BIZException {
        List<JobPositionCityDO> dicCityList = jobPositionCityDao.getPositionCitysByPid(positionId);
        StringBuilder citysCode = new StringBuilder();
        if (!dicCityList.isEmpty()) {
            for (JobPositionCityDO dictCity : dicCityList) {
                citysCode.append(dictCity.getCode()).append(",");
            }
        } else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_PUBLISHCITY_INVALID);
        }
        return citysCode.substring(0, citysCode.length() - 1);
    }

    private void mappingLanguageRequire(LiePinPositionVO liePinPositionVO, JobPositionDO moseekerJobPosition) {
        String language = moseekerJobPosition.getLanguage();
        if (StringUtils.isNullOrEmpty(language)) {
            return;
        }
        int english = 0;
        if (language.contains("英语") || language.toLowerCase().contains("english")) {
            english = 1;
        }
        liePinPositionVO.setDetail_language_english(english);
        int japanese = 0;
        if (language.contains("日语") || language.toLowerCase().contains("japanese")) {
            japanese = 1;
        }
        liePinPositionVO.setDetail_language_japanese(japanese);
        int french = 0;
        if (language.contains("法语") || language.toLowerCase().contains("french")) {
            french = 1;
        }
        liePinPositionVO.setDetail_language_french(french);
        int putong = 0;
        if (language.contains("普通话") || language.toLowerCase().contains("putonghua")) {
            putong = 1;
        }
        liePinPositionVO.setDetail_language_putong(putong);
        int yueyu = 0;
        if (language.contains("粤语") || language.toLowerCase().contains("yueyu") || language.contains("广东话")) {
            yueyu = 1;
        }
        liePinPositionVO.setDetail_language_yueyu(yueyu);
        liePinPositionVO.setDetail_language_other(1);
        if (language.length() > 79) {
            language = language.substring(0, 79);
        }
        liePinPositionVO.setDetail_language_content(language);
    }

    /**
     * 将职位亮点格式化为猎聘api的格式
     *
     * @param positionForm JobPositionDO
     * @return String
     * @author cjm
     * @date 2018/6/7
     */
    private String getValidFeature(ThirdPartyPosition positionForm) {
        List<String> features = positionForm.getFeature();
        StringBuilder tags = new StringBuilder();

        if (features == null || features.size() < 1) {
            List<HrCompanyFeature> hrCompanyFeatureList = featureDao.getFeatureListByCompanyId(positionForm.getCompanyId());
            features = hrCompanyFeatureList.stream().map(hrCompanyFeature -> hrCompanyFeature.getFeature()).collect(Collectors.toList());
        }
        int index = 0;

        for (String str : features) {
            if (StringUtils.isNullOrEmpty(str)) {
                continue;
            }
            // 16个字符的不参与
            if (str.length() > 8) {
                continue;
            }
            tags.append(str).append(",");
            if (++index >= 16) {
                break;
            }
        }
        if(tags.length() < 1){
            return "";
        }
        return tags.substring(0, tags.length() - 1);
    }

    /**
     * 过滤掉中文特殊字符，否则猎聘收不到特殊字符导致验签失败
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/22
     */
    private String filterSpecialSign(String str) throws BIZException {
        if (StringUtils.isNullOrEmpty(str)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        return str;
    }
}
