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
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.liepin.LiePinPositionDegree;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.PositionTaskQueueDaemonThread;
import com.moseeker.position.utils.HttpClientUtil;
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
    HttpClientUtil httpClientUtil;

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
        // 映射仟寻的城市code，此时还未转换成猎聘的citycode
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
    public TwoParam<HrThirdPartyPositionDO, EmptyExtThirdPartyPosition> sendSyncRequest(TransferResult<LiePinPositionVO, EmptyExtThirdPartyPosition> result) throws TException {

        LiePinPositionVO liePinPositionVO = result.getPositionWithAccount();

        HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();

        // 当发布职位时多个城市时，如果每个新的城市都发布成功，才视为仟寻职位同步成功，否则视为同步失败
        int successSyncNum;
        // 发布职位时如果存在之前发布过但是下架的职位，需要将这类职位上架并修改
        int successRePublishNum;
        // 发布职位时如果存在之前发布过并且状态正常的职位，需要对职位同步修改
        int editNum;

        Integer channel = ChannelType.LIEPIN.getValue();
        try {
            // 这个city是citycodes字符串，是要发布的城市
            String citys = liePinPositionVO.getEjob_dq();

            String[] cityCodesArr = citys.split("[,，]");

            // 获取第三方账号token
            HrThirdPartyAccountDO thirdPartyAccountDO = getThirdPartyAccount(liePinPositionVO.getPublisher(), channel);
            String liePinToken = thirdPartyAccountDO.getExt2();
            String liePinUserIdStr = thirdPartyAccountDO.getExt();
            if (StringUtils.isNullOrEmpty(liePinToken) || StringUtils.isNullOrEmpty(liePinUserIdStr)) {
                logger.info("=================账号未绑定猎聘, thirdPartyAccountDO:{}===============", thirdPartyAccountDO);
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_PWD_ERROR);
            }
            int liePinUserId = Integer.parseInt(liePinUserIdStr);

            int cityNum = cityCodesArr.length;
            Integer positionId = liePinPositionVO.getPositionId();
            List<String> cityCodesList = Arrays.asList(cityCodesArr);
            List<Integer> cityCodesIntList = cityCodesList.stream().map(Integer::parseInt).collect(Collectors.toList());

            // 查出所有本次编辑城市职位jobpostionmapping表数据
            List<JobPositionLiepinMappingDO> liepinMappingDOList =
                    liepinMappingDao.getMappingDataByTitleAndUserId(liePinPositionVO.getEjob_title(), liePinUserId, cityCodesIntList, positionId);

            // 获取本次职位发布中数据库中没有发布过的职位city
            List<String> syncCityList = getSyncCityList(liepinMappingDOList, cityCodesList);

            // 获取本次职位发布中已存在且状态为0的职位，需要执行先上架后编辑操作
            List<JobPositionLiepinMappingDO> republishCityList = getRepublishCityList(liepinMappingDOList, cityCodesList);

            // 获取本次职位发布中已存在且状态为1或2的职位，需要执行编辑操作
            List<JobPositionLiepinMappingDO> editCityList = getEditCityList(liepinMappingDOList, cityCodesList);

            // 发布职位时，如果之前未发布过，就发布新的职位，title在此步骤中截取
            successSyncNum = receiverHandler.syncNewPosition(syncCityList, liePinPositionVO, liePinUserId, liePinToken);

            // 发布职位时如果是已经存在的职位，但是状态为0，则执行先上架后修改
            successRePublishNum = updateExistPosition(republishCityList, liePinToken, positionId, liePinPositionVO);

            // 编辑修改职位
            editNum = editExistPosition(editCityList, liePinPositionVO, liePinToken);
            // 将职位同步状态设置为2，待审核
            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.binding.getValue());

            logger.info("============cityNum:{},successRePublishNum:{},successSyncNum:{},editNum:{}==============", cityNum, successRePublishNum, successSyncNum, editNum);
        } catch (BIZException e) {
            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.failed.getValue());
            hrThirdPartyPositionDO.setSyncFailReason(e.getMessage());
            logger.warn(e.getMessage(), e);
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, liePinPositionVO, e, null);
        } catch (Exception e) {
            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.bindingError.getValue());
            hrThirdPartyPositionDO.setSyncFailReason("后台异常");
            logger.error(e.getMessage(), e);
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e, null);
        }

        hrThirdPartyPositionDO.setPositionId(liePinPositionVO.getPositionId());
        hrThirdPartyPositionDO.setOccupation(liePinPositionVO.getEjob_jobtitle());
        hrThirdPartyPositionDO.setChannel(2);
        TwoParam<HrThirdPartyPositionDO, EmptyExtThirdPartyPosition> twoParam = new TwoParam<>(hrThirdPartyPositionDO, EmptyExtThirdPartyPosition.EMPTY);
        twoParam = thirdPartyPositionDao.upsertThirdPartyPosition(twoParam);
        hrThirdPartyPositionDO = twoParam.getR1();
        logger.info("====================hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);

        return twoParam;
    }

    /**
     * 获取需要同步的cityCodesList
     *
     * @param liepinMappingDOList job_position_mapping 集合
     * @param cityCodesList       cityCode 集合
     * @return List<String> cityCodes
     * @author cjm
     * @date 2018/7/5
     */
    private List<String> getSyncCityList(List<JobPositionLiepinMappingDO> liepinMappingDOList, List<String> cityCodesList) {

        List<String> syncCityList = new ArrayList<>();

        if (liepinMappingDOList.size() > 0) {
            // 获取本次职位发布中的所有数据库中的职位
            List<String> cityCodesListDb = liepinMappingDOList.stream().map(jobPositionLiepinMappingDO -> String.valueOf(jobPositionLiepinMappingDO.getCityCode()))
                    .collect(Collectors.toList());
            for (String cityCode : cityCodesList) {
                // 如果存在已同步记录信息，需要判断下是否要是否是新的地区或者title
                if (!cityCodesListDb.contains(cityCode)) {
                    syncCityList.add(cityCode);
                }
            }
            return syncCityList;
        }
        return cityCodesList;

    }

    /**
     * 获取需要编辑的job_position_mapping DO 实体
     *
     * @param liepinMappingDOList job_position_mapping 集合
     * @param cityCodesList       cityCode 集合
     * @return List<JobPositionLiepinMappingDO> job_position_mapping DO 实体
     * @author cjm
     * @date 2018/7/5
     */
    private List<JobPositionLiepinMappingDO> getEditCityList(List<JobPositionLiepinMappingDO> liepinMappingDOList, List<String> cityCodesList) {
        List<JobPositionLiepinMappingDO> editCityList = new ArrayList<>();
        for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
            if (mappingDO.getState() != 0 && cityCodesList.contains(String.valueOf(mappingDO.getCityCode()))) {
                editCityList.add(mappingDO);
            }
        }
        return editCityList;
    }

    /**
     * 获取需要重新发布的job_position_mapping DO 实体
     *
     * @param liepinMappingDOList job_position_mapping 集合
     * @param cityCodesList       cityCode 集合
     * @return List<JobPositionLiepinMappingDO> job_position_mapping DO 实体
     * @author cjm
     * @date 2018/7/5
     */
    private List<JobPositionLiepinMappingDO> getRepublishCityList(List<JobPositionLiepinMappingDO> liepinMappingDOList, List<String> cityCodesList) {
        List<JobPositionLiepinMappingDO> republishCityList = new ArrayList<>();
        for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
            // 该title下的职位处于下架、删除状态，用于重新发布到猎聘
            if (mappingDO.getState() == 0 && cityCodesList.contains(String.valueOf(mappingDO.getCityCode()))) {
                republishCityList.add(mappingDO);
            }
        }
        return republishCityList;
    }

    /**
     * 插入一条新的jobpositionmapping数据，用来获取主键id
     *
     * @param liePinPositionVO 猎聘职位实体
     * @param cityCode         仟寻cityCode
     * @param liePinUserId     hr在猎聘的id
     * @author cjm
     * @date 2018/7/2
     */
    public JobPositionLiepinMappingDO getNewJobPositionLiepinMapping(LiePinPositionVO liePinPositionVO, String cityCode, int liePinUserId) {
        JobPositionLiepinMappingDO jobPositionLiepinMappingDO = new JobPositionLiepinMappingDO();
        jobPositionLiepinMappingDO.setPositionId(liePinPositionVO.getPositionId());
        jobPositionLiepinMappingDO.setCityCode(Integer.parseInt(cityCode));
        jobPositionLiepinMappingDO.setJobTitle(liePinPositionVO.getEjob_title());
        jobPositionLiepinMappingDO.setLiepinUserId(liePinUserId);
        jobPositionLiepinMappingDO.setState((byte) 0);
        return liepinMappingDao.addData(jobPositionLiepinMappingDO);
    }

    private HrThirdPartyAccountDO getThirdPartyAccount(Integer publisher, Integer channel) {
        // 获取第三方账号和hr账号关联表数据
        HrThirdPartyAccountHrDO hrThirdDO = hrThirdPartyDao.getHrAccountInfo(publisher, channel);

//        if (hrThirdDO == null) {
//            logger.info("=================无第三方hr账号关联数据===============");
//            return null;
//        }
        int thirdAccountId = hrThirdDO.getThirdPartyAccountId();

        return thirdPartyAccountDao.getAccountById(thirdAccountId);

    }

    /**
     * 获取猎聘职位审核状态
     *
     * @param jobPositionMappingId job_position_mapping表主键
     * @param liePinToken          hr在猎聘绑定后的token
     * @return 返回职位信息
     * @author cjm
     * @date 2018/7/2
     */
    public JSONObject getPositionAuditState(int jobPositionMappingId, String liePinToken) throws Exception {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("ejob_extRefids", jobPositionMappingId);
        String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionGet);
        logger.info("=======================获取职位信息结果httpResultJson:{}", httpResultJson);
        JSONObject httpResult = httpClientUtil.requireValidResult(httpResultJson);
        JSONObject positionInfo = httpResult.getJSONObject("data");
        if (positionInfo == null) {
            emailNotification.sendRefreshSyncStateFailEmail("mappingId:" + jobPositionMappingId + "</br>" + "httpResultJson:" + httpResultJson, null);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);
        }
        return positionInfo.getJSONObject("data");
    }

    /**
     * 编辑数据库中已经存在的职位
     *
     * @param editCityList     编辑城市的job_position_mapping集合
     * @param liePinPositionVO 猎聘职位实体
     * @param liePinToken      hr在猎聘绑定后的token
     * @return 返回职位编辑成功的数量
     * @author cjm
     * @date 2018/7/3
     */
    private int editExistPosition(List<JobPositionLiepinMappingDO> editCityList, LiePinPositionVO liePinPositionVO, String liePinToken) throws Exception {
        if (editCityList.size() > 0) {

            for (JobPositionLiepinMappingDO mappingDO : editCityList) {
                try {
                    editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                } catch (BIZException e) {
                    logger.warn("============同步已存在的城市，修改该城市的职位信息时未操作成功，message:{}===========", e.getMessage());
                    throw e;
                }
            }
            return editCityList.size();
        }
        return 0;
    }

    /**
     * 发布职位时如果是已经存在的职位，但是状态为0，则执行先上架后修改
     *
     * @return 返回上架并更新成功的职位个数
     * @author cjm
     * @date 2018/6/22
     */
    private int updateExistPosition(List<JobPositionLiepinMappingDO> republishCitys, String liePinToken, Integer positionId, LiePinPositionVO liePinPositionVO) throws Exception {

        List<String> republishIdList = republishCitys.stream().map(republishCity -> String.valueOf(republishCity.getId())).collect(Collectors.toList());

        String republishIds = String.join(",", republishIdList);

        List<Integer> republishIdIntList;
        // 更新上架后的状态
        if (republishCitys.size() > 0) {
            try {
                // 上架后返回此次上架职位的数量
                republishIdIntList = upshelfJobPosition(republishIds, liePinToken, positionId);

                logger.info("===========上架后返回此次上架职位的数量republishIdList:{}============", republishIdList);

                // 上架后向猎聘发送修改职位
                for (JobPositionLiepinMappingDO mappingDO : republishCitys) {

                    liePinPositionVO.setEjob_extRefid(String.valueOf(mappingDO.getId()));

                    editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                }
            } catch (BIZException e) {
                logger.warn("============同步已存在的城市，修改该城市的职位信息时未操作成功，message:{}===========", e.getMessage());
                throw e;
            }
            return republishIdIntList.size();
        }
        return 0;
    }

    private List<Integer> upshelfJobPosition(String republishIds, String liePinToken, Integer positionId) throws Exception {
        byte state = 1;
        JSONObject liePinJsonObject = new JSONObject();
        // 去掉末尾的逗号
        liePinJsonObject.put("ejob_extRefids", republishIds);

        String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionRepub);

        httpClientUtil.requireValidResult(httpResultJson);

        List<Integer> republishIdList = getRepublishList(republishIds);

        liepinMappingDao.updateStateAndJobId(republishIdList, state, positionId);

        return republishIdList;
    }

    /**
     * 编辑单个职位信息
     *
     * @param liePinToken 猎聘生成的hr账号token
     * @param mappingDO   job_position_mapping表记录实体
     * @author cjm
     * @date 2018/6/10
     */
    public void editSinglePosition(LiePinPositionVO liePinPositionVO, String liePinToken, JobPositionLiepinMappingDO mappingDO) throws Exception {
        // 截取 tilte
        requireValidTitle(liePinPositionVO);
        String id = mappingDO.getId() + "";
        try {
            // 由于在程序走到向猎聘发送修改请求时能确认数据库中职位状态是发布状态，所以如果hr在猎聘网站上将职位下架的话，仟寻的操作是需要先将职位上架
            confirmPositionState(id, liePinToken);

            // 猎聘修改职位api必填字段
            liePinPositionVO.setEjob_extRefid(id);

            String liepinCityCode = getValidLiepinDictCode(mappingDO.getCityCode() + "");

            liePinPositionVO.setEjob_dq(liepinCityCode);

            JSONObject liePinObject = (JSONObject) JSONObject.toJSON(liePinPositionVO);

            String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinObject, liePinToken, LiepinPositionOperateConstant.liepinPositionEdit);

            httpClientUtil.requireValidResult(httpResultJson);

            liepinMappingDao.updateState(Integer.parseInt(id), (byte) 2);

        } catch (Exception e) {
            liepinMappingDao.updateErrMsg(Integer.parseInt(id), e.getMessage());
            throw e;
        }
    }

    /**
     * 猎聘职位title长度限制为143，如果传来的职位长度大于此值，则截取
     *
     * @param liePinPositionVO 猎聘职位vo对象
     * @author cjm
     * @date 2018/7/9
     */
    public void requireValidTitle(LiePinPositionVO liePinPositionVO) {
        String title = liePinPositionVO.getEjob_title();
        logger.info("====================title长度:{}", title.length());
        if (title.length() > 143) {
            title = title.substring(0, 143);
            liePinPositionVO.setEjob_title(title);
        }
    }

    /**
     * 由于在程序走到向猎聘发送修改请求时能确认数据库中职位状态是发布状态，所以如果hr在猎聘网站上将职位下架的话，仟寻的操作是需要先将职位上架
     *
     * @param liePinToken 猎聘生成的hr账号token
     * @param mappingDOId job_position_mapping表主键id
     * @author cjm
     * @date 2018/7/4
     */
    private void confirmPositionState(String mappingDOId, String liePinToken) throws Exception {
        // 获取猎聘职位信息
        JSONObject liepinPositionInfo = getPositionAuditState(Integer.parseInt(mappingDOId), liePinToken);

        if (liepinPositionInfo == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);
        }

        if (!"PUBLISHED".equals(liepinPositionInfo.getString("ejob_status"))) {

            JSONObject liePinJsonObject = new JSONObject();
            // 去掉末尾的逗号
            liePinJsonObject.put("ejob_extRefids", mappingDOId);

            String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionRepub);

            httpClientUtil.requireValidResult(httpResultJson);
        }
    }

    /**
     * 通过仟寻citycode获取猎聘citycode
     *
     * @param cityCode 仟寻cityCode
     * @return 返回猎聘的cityCode
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
     * @param occupationList 职能code集合
     * @return 返回以逗号隔开的职能字符串
     * @author cjm
     * @date 2018/6/19
     */
    private String requireValidOccupation(List<String> occupationList) throws BIZException {
        logger.info("=================occupationList:{}=============", occupationList.toString());
        StringBuilder occupation = new StringBuilder();
        // 获取所有猎聘社招职能
        List<DictLiepinOccupationDO> allSocialOccupation = liepinOccupationDao.getAllSocialOccupation();

        List<String> allSocialCode = allSocialOccupation.stream().map(DictLiepinOccupationDO::getOtherCode).collect(Collectors.toList());

        List<String> moseekerCodeList;
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
     * @author cjm
     * @date 2018/6/11
     */
    private String getDepartmentName(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition) {
        String departmentName;
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
        // 猎聘数据库语言长度限制为79，所以这里同样限制长度
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
            features = hrCompanyFeatureList.stream().map(HrCompanyFeature::getFeature).collect(Collectors.toList());
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
        if (tags.length() < 1) {
            return "";
        }
        return tags.substring(0, tags.length() - 1);
    }

    /**
     * 过滤掉中文特殊字符，否则猎聘收不到特殊字符导致验签失败
     *
     * @param str 本来是要过滤 □ 类型的特殊字符，但是后来发现特殊字符验签也能成功了，应该是猎聘将特殊字符的过滤去掉了，目前这个方法只做非空判断
     * @return 返回过滤后的字符串
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
