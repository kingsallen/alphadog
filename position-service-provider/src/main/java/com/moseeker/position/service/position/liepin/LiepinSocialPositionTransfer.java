package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCityLiePinDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCityLiepin;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityLiepinRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.constants.position.LiePinPositionDegree;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityLiePinDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.Result;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LiepinSocialPositionTransfer extends LiepinPositionTransfer<LiePinPositionVO, LiePinPositionVO> {

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private DictCityLiePinDao dictCityLiePinDao;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private LiePinReceiverHandler receiverHandler;

    public static final String LP_USER_SYNC_JOB = "https://apidev1.liepin.com/e/job/createEJob.json";
    private static final String LP_USER_REPUB_JOB = "https://apidev1.liepin.com/e/job/rePublishEjob.json";

    @Override
    public LiePinPositionVO changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO moseekerJobPosition, HrThirdPartyAccountDO account) throws Exception {
        LiePinPositionVO liePinPositionVO = new LiePinPositionVO();
        liePinPositionVO.setEjob_extRefid(String.valueOf(moseekerJobPosition.getId()));
        liePinPositionVO.setEjob_extfield(null);
        liePinPositionVO.setCount((int)moseekerJobPosition.getCount());
        liePinPositionVO.setPublisher(moseekerJobPosition.getPublisher());
        liePinPositionVO.setEjob_title(moseekerJobPosition.getTitle());
        List<String> occupationList = positionForm.getOccupation();
        String occupationStr = "";

        // 猎聘api职能个数不超过3个
        if (occupationList != null && occupationList.size() > 3) {
            occupationStr = occupationList.get(0) + "," + occupationList.get(1) + "," + occupationList.get(2);
        } else if (occupationList != null) {
            for (String str : occupationList) {
                occupationStr = occupationStr + str + ",";
            }
            occupationStr = occupationStr.substring(0, occupationStr.length() - 1);
        }

        liePinPositionVO.setEjob_jobtitle(occupationStr);


        // 获取城市code，多个城市用逗号隔开
        int positionId = moseekerJobPosition.getId();
        liePinPositionVO.setPositionId(positionId);
        // 映射城市code
        mappingCityCode(positionId, liePinPositionVO);

        liePinPositionVO.setEjob_privacyreq(null);
        liePinPositionVO.setEjob_salarydiscuss(positionForm.isSalaryDiscuss() ? "0" : "1");
        // 单位 : 年/万
        liePinPositionVO.setEjob_salarylow((float) positionForm.getSalaryBottom() * positionForm.getSalaryMonth() / 10000);
        liePinPositionVO.setEjob_salaryhigh((float) positionForm.getSalaryTop() * positionForm.getSalaryMonth() / 10000);
        liePinPositionVO.setDetail_workyears(moseekerJobPosition.getExperience());
        liePinPositionVO.setDetail_sex(moseekerJobPosition.getGender() == 0 ? "女" : moseekerJobPosition.getGender() == 1 ? "男" : null);
        liePinPositionVO.setDetail_agelow(moseekerJobPosition.getAge() == 0 ? 20 : (int) moseekerJobPosition.getAge());
        liePinPositionVO.setDetail_agehigh(65);

        int degree = (int) moseekerJobPosition.getDegree();
        liePinPositionVO.setDetail_edulevel(LiePinPositionDegree.getPositionDegree(degree).getLiePinDegreeNo());

        liePinPositionVO.setDetail_edulevel_tz(null);
        liePinPositionVO.setDetail_report2(null);
        liePinPositionVO.setDetail_subordinate(moseekerJobPosition.getUnderlings() == 0 ? 0 : (int) moseekerJobPosition.getUnderlings());
        liePinPositionVO.setDetail_duty(moseekerJobPosition.getAccountabilities());
        liePinPositionVO.setDetail_require(moseekerJobPosition.getRequirement());
        String departmentName = null;
        if(StringUtils.isNotNullOrEmpty(positionForm.getDepartmentName())){
            departmentName = positionForm.getDepartmentName();
        }else{
            departmentName = moseekerJobPosition.getDepartment();
        }
        liePinPositionVO.setDetail_dept(departmentName);
        // 每个亮点八个字，最多十六个
        String feature = getValidFeature(moseekerJobPosition);
        liePinPositionVO.setDetail_tags(feature);

        // 映射语言要求
        mappingLanguageRequire(liePinPositionVO, moseekerJobPosition);

        liePinPositionVO.setDetail_special(moseekerJobPosition.getMajorRequired());
        if (moseekerJobPosition.getProfile_cc_mail_enabled() == 1) {
            liePinPositionVO.setEmail_list_array(moseekerJobPosition.getHrEmail());
        }
        liePinPositionVO.setEjob_level(null);
        return liePinPositionVO;
    }

    private void mappingCityCode(int positionId, LiePinPositionVO liePinPositionVO) {
        List<JobPositionCityDO> dicCityList = jobPositionCityDao.getPositionCitysByPid(positionId);
        StringBuilder citysCode = new StringBuilder();
        if (!dicCityList.isEmpty()) {
            for (JobPositionCityDO dictCity : dicCityList) {
                citysCode.append(dictCity.getCode()).append(",");
            }
        } else {
            throw new RuntimeException("该职位查不到发布地区");
        }
        liePinPositionVO.setEjob_dq(citysCode.substring(0, citysCode.length() - 1));
    }

    private void mappingLanguageRequire(LiePinPositionVO liePinPositionVO, JobPositionDO moseekerJobPosition) {
        String language = moseekerJobPosition.getLanguage();
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
        liePinPositionVO.setDdetail_language_yueyu(yueyu);
        liePinPositionVO.setDetail_language_other(1);
        liePinPositionVO.setDetail_language_content(language);
    }

    /**
     * 将职位亮点格式化为猎聘api的格式
     *
     * @param moseekerJobPosition JobPositionDO
     * @return String
     * @author cjm
     * @date 2018/6/7
     */
    private String getValidFeature(JobPositionDO moseekerJobPosition) {
        String feature = moseekerJobPosition.getFeature();
        StringBuilder tags = new StringBuilder();
        int index = 0;
        if (StringUtils.isNotNullOrEmpty(feature)) {
            String[] features = feature.split("#");
            for (String str : features) {
                // 8个字的不参与
                if (str.length() > 8) {
                    continue;
                }
                tags.append(str).append(",");
                if (++index >= 16) {
                    break;
                }
            }
        }else{
            return null;
        }
        return tags.substring(0, tags.length() - 1);
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition thirdPartyPosition, LiePinPositionVO pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
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
        data.setSalaryDiscuss(thirdPartyPosition.isSalaryDiscuss() ? 0 : 1);
        data.setSalaryBottom(thirdPartyPosition.getSalaryBottom());
        data.setSalaryTop(thirdPartyPosition.getSalaryTop());
        data.setPracticeSalary(thirdPartyPosition.getPracticeSalary());
        data.setPracticePerWeek(thirdPartyPosition.getPracticePerWeek());
        data.setPracticeSalaryUnit(thirdPartyPosition.getPracticeSalaryUnit());
        data.setCompanyId(thirdPartyPosition.getCompanyId());
        data.setCompanyName(thirdPartyPosition.getCompanyName());
        data.setAddressId(thirdPartyPosition.getAddressId());
        data.setAddressName(thirdPartyPosition.getAddressName());
        data.setDepartmentId(thirdPartyPosition.getDepartmentId());
        data.setDepartmentName(thirdPartyPosition.getDepartmentName());
        data.setCount(pwa.getCount());

        logger.info("回写到第三方职位对象:{}", data);
        return data;
    }

    @Override
    public boolean extTransferCheck(JobPositionDO positionDB) {
        return positionDB.getCandidateSource() == 0;
    }

    @Override
    public void sendSyncRequest(TransferResult<LiePinPositionVO, LiePinPositionVO> result) throws TException {
        LiePinPositionVO liePinPositionVO = result.getPositionWithAccount();
        // 这个city是citycodes字符串，是要发布的城市
        String citys = liePinPositionVO.getEjob_dq();
        String[] cityCodesArr = citys.split("[,，]");
        List<String> errCityCodeList = new ArrayList<>();

        // 当出现token失效导致发布失败时，认为该职位在猎聘尚未发布，不在jobliepinmapping表中保存数据，默认鉴权成功
        boolean flag = true;

        if (cityCodesArr.length >= 1) {

            // 获取猎聘的token
            Integer publisher = liePinPositionVO.getPublisher();
            Result dbResult = thirdPartyAccountDao.getThirdPartyAccountTokenByHrId(publisher, 2);
            HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();
            if (null == dbResult || dbResult.size() == 0) {
                logger.info("===============猎聘token查询为空================");
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_TOKEN_NONEXISTS);
            }
            String liePinToken = String.valueOf(dbResult.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2));
            int thirdPartAccountId = (int) dbResult.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID);
            hrThirdPartyPositionDO.setThirdPartyAccountId(thirdPartAccountId);

            // 当发布职位时多个城市时，如果每个新的城市都发布成功，才视为仟寻职位同步成功，否则视为同步失败
            int cityNum = cityCodesArr.length;
            int successSyncNum = 0;
            int successRePublishNum = 0;
            Integer positionId = liePinPositionVO.getPositionId();
            List<String> cityCodesList = Arrays.asList(cityCodesArr);

            // 查出该所有职位名称
            List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByTitle(liePinPositionVO.getEjob_title());
            List<String> cityCodesListDb = new ArrayList<>();
            Map<String, Object> cityIdCodeMap = new HashMap<>();
            // 数据库中状态不为1的城市
            List<String> republishCity = new ArrayList<>();
            for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                // 该title下的职位处于下架、删除状态，用于重新发布到猎聘
                if(mappingDO.getState() != 1){
                    republishCity.add(String.valueOf(mappingDO.getCityCode()));
                    cityIdCodeMap.put(String.valueOf(mappingDO.getCityCode()), mappingDO.getId());
                }else if(mappingDO.getState() == 1 && cityCodesList.contains(String.valueOf(mappingDO.getCityCode()))){
                    // 如果本次发布中的城市之前已经发布过，将城市数量减一
                    cityNum --;
                }
                cityCodesListDb.add(String.valueOf(mappingDO.getCityCode()));
            }

            String errorMsg = "";
            StringBuilder republishIds = new StringBuilder();
            for (String cityCode : cityCodesList) {
                // 只要有相同title和城市的职位，就不发布
                if (liepinMappingDOList.size() > 0) {
                    // 如果存在已同步记录信息，需要判断下是否要是否是新的地区或者title
                    if(cityCodesListDb.contains(cityCode)){
                        // 状态不为1的城市中如果包括本次发布城市
                        if(republishCity.contains(cityCode)){
                            republishIds.append(cityIdCodeMap.get(cityCode)).append(",");
                        }
                        continue;
                    }
                }

                DictCityLiePinDO dictCityLiePinDO = dictCityLiePinDao.getLiepinDictCodeByCode(cityCode);

                if (dictCityLiePinDO == null) {
                    DictCityDO dictCityDO = new DictCityDO();
                    dictCityDO.setCode(Integer.parseInt(cityCode));
                    List<DictCityDO> dictCityDOS = dictCityDao.getMoseekerLevels(dictCityDO);
                    if (dictCityDOS != null && dictCityDOS.size() > 1) {
                        for (int i = 1; i < dictCityDOS.size() && dictCityLiePinDO == null; i++) {
                            dictCityLiePinDO = dictCityLiePinDao.getLiepinDictCodeByCode(String.valueOf(dictCityDOS.get(i).getCode()));
                        }
                    }else{
                        logger.info("===============citycode:{}=============", cityCode);
                        throw ExceptionUtils.getBizException("错误的仟寻citycode，查不到该code的所有城市level");
                    }
                }
                String liepinCityCode = "";
                if(dictCityLiePinDO != null){
                    liepinCityCode = dictCityLiePinDO.getCode();
                }else{
                    throw ExceptionUtils.getBizException("错误的仟寻citycode，查不到该code的所有城市level");
                }

                // 生成职位发布到猎聘时需要的id 先查一遍是否有重复数据，如果没有，插入一条mapping数据
                JobPositionLiepinMappingDO jobPositionLiepinMappingDO = liepinMappingDao.getDataByPidAndCityCode(positionId, cityCode);
                if(jobPositionLiepinMappingDO == null){
                    jobPositionLiepinMappingDO = new JobPositionLiepinMappingDO();
                    jobPositionLiepinMappingDO.setJobId(positionId);
                    jobPositionLiepinMappingDO.setCityCode(Integer.parseInt(cityCode));
                    jobPositionLiepinMappingDO.setJobTitle(liePinPositionVO.getEjob_title());
                    jobPositionLiepinMappingDO = liepinMappingDao.addData(jobPositionLiepinMappingDO);
                }

                liePinPositionVO.setEjob_extRefid(jobPositionLiepinMappingDO.getId() + "");
                liePinPositionVO.setEjob_dq(liepinCityCode);
                // 构造请求数据
                JSONObject liePinJsonObject = (JSONObject) JSONObject.toJSON(liePinPositionVO);
                String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
                liePinJsonObject.put("t", t);
                String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
                liePinJsonObject.put("sign", sign);

                //设置请求头
                Map<String, String> headers = new HashMap<>();
                headers.put("channel", "qianxun");
                headers.put("token", liePinToken);
                String httpResultJson = null;
                try {
                    httpResultJson = HttpClientUtil.sentHttpPostRequest(LP_USER_SYNC_JOB, headers, liePinJsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    errCityCodeList.add(cityCode);
                    logger.info("============职位同步时http请求异常============");
                    continue;
                }

                JSONObject httpResult = JSONObject.parseObject(httpResultJson);
                logger.info("==============httpResult:{}===============", httpResult);
                String thirdPositionId = "";
                byte state = 0;
                if (null != httpResult && httpResult.getIntValue("code") == 0) {
                    String data = httpResult.getString("data");
                    thirdPositionId = data.substring(1, data.length() - 1);
                    state = 1;
                    successSyncNum++;
                    logger.info("==============hrThirdPartyPositionDO:{}================", hrThirdPartyPositionDO);
                } else if (null != httpResult) {
                    if(httpResult.getIntValue("code") == 1001){
                        // 鉴权失败
                        flag = false;
                    }
                    errorMsg = httpResult.getString("message");
                } else {
                    errorMsg = "http请求失败";
                    errCityCodeList.add(cityCode);
                }
                // 如果鉴权失败，将mapping记录删除
                int id = jobPositionLiepinMappingDO.getId();
                if(!flag){
                    liepinMappingDao.deleteData(jobPositionLiepinMappingDO);
                }else{
                    // 更改数据库mapping的状态
                    liepinMappingDao.updateJobInfoById(id, StringUtils.isNullOrEmpty(thirdPositionId) ? null : Integer.parseInt(thirdPositionId), state, errorMsg);
                }

            }


            try{
                // 更新重新发布后的状态
                if(republishIds.length() > 0){
                    JSONObject liePinJsonObject = new JSONObject();
                    liePinJsonObject.put("ejob_extRefids", republishIds.substring(0, republishIds.length() - 1));
                    String httpResultJson = receiverHandler.sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_REPUB_JOB);
                    receiverHandler.requireValidResult(httpResultJson);
                    List<Integer> republishIdList = getRepublishList(republishIds.toString());
                    successRePublishNum = republishIdList.size();
                    byte state = 1;
                    liepinMappingDao.updateState(republishIdList, state);
                }
            }catch (Exception e){
                logger.info("=================发布职位时，向猎聘发送重新上架失败:message{}================", e.getMessage());
                errorMsg = e.getMessage();
            }

            logger.info("============cityNum:{},successSyncNum:{}==============", cityNum, successSyncNum);
            if (successSyncNum + successRePublishNum == cityNum) {
                hrThirdPartyPositionDO.setIsSynchronization(1);
            } else {
                if (StringUtils.isNullOrEmpty(errorMsg)) {
                    hrThirdPartyPositionDO.setIsSynchronization(4);
                    hrThirdPartyPositionDO.setSyncFailReason("http请求失败，城市code:" + errCityCodeList.toString());
                } else {
                    hrThirdPartyPositionDO.setIsSynchronization(3);
                    hrThirdPartyPositionDO.setSyncFailReason("猎聘:" + errorMsg);
                }
            }
            TwoParam<HrThirdPartyPositionDO, HrThirdPartyPositionDO> twoParam = new TwoParam<>(hrThirdPartyPositionDO, null);
            thirdPartyPositionDao.upsertThirdPartyPosition(twoParam);
        }
    }

    private List<Integer> getRepublishList(String republishIds) {
        String[] ids = republishIds.split(",");
        List<Integer> list = new ArrayList<>();
        for(String id : ids){
            if(StringUtils.isNotNullOrEmpty(id)){
                list.add(Integer.parseInt(id));
            }
        }
        return list;
    }
}
