package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.dictdb.DictFeatureJob58Dao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionJob58MappingDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobFeatureRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.job58.Job58PositionDegree;
import com.moseeker.position.constants.position.job58.Job58PositionOperateConstant;
import com.moseeker.position.constants.position.job58.Job58WorkExperienceDegree;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.job58.dto.Job58PositionDTO;
import com.moseeker.position.service.position.job58.dto.Job58PositionParams;
import com.moseeker.position.service.position.job58.dto.Job58PositionUpshelfDTO;
import com.moseeker.position.service.position.job58.vo.Job58PositionForm;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionJob58MappingDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob58PositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * todo 全局异常码和token过期暂未处理
 * @author cjm
 * @date 2018-10-26 14:00
 **/
@Service
public class Job58PositionTransfer extends AbstractPositionTransfer<Job58PositionForm, Job58PositionDTO, Job58PositionDTO, ThirdpartyJob58PositionDO> {

    private static Logger logger = LoggerFactory.getLogger(Job58PositionTransfer.class);
    @Autowired
    private HRThirdPartyAccountHrDao hrThirdPartyHrDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private Job58RequestHandler job58RequestHandler;
    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private PositionEmailNotification emailNotification;
    @Autowired
    private JobPositionJob58MappingDao job58MappingDao;
    @Autowired
    private DictFeatureJob58Dao featureJob58Dao;

    @Override
    public Job58PositionDTO changeToThirdPartyPosition(Job58PositionForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws BIZException {
        // 发请求用
        Job58PositionDTO job58PositionDTO = new Job58PositionDTO();
        JSONObject userInfo = JSONObject.parseObject(account.getExt());
        job58PositionDTO.setOpenid(userInfo.getString("openId"));
        List<String> occupations = positionForm.getOccupation();
        String occupation = occupations.get(occupations.size() - 1);
        job58PositionDTO.setCate_id(Integer.parseInt(occupation));
        String content = "职位描述：</br>" + positionDB.getAccountabilities() + "</br>职位要求：</br>" + positionDB.getRequirement();
        job58PositionDTO.setContent(content);
        job58PositionDTO.setLocal_id(doGetCityCode(positionDB));
        // 获取仟寻账号的信息
        HrThirdPartyAccountHrDO accountHrDO = hrThirdPartyHrDao.getHrAccountByThirdPartyId(account.getId());
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getUserHrAccountById(accountHrDO.getHrAccountId());
        job58PositionDTO.setPhone(userHrAccountDO.getMobile());
        job58PositionDTO.setTitle(positionDB.getTitle());
        job58PositionDTO.setParas(parsePositionParam2Job58(positionForm, positionDB, userHrAccountDO));
        // 設置email
        job58PositionDTO.setEmail(Job58PositionOperateConstant.job58ProfileEmail);
        job58PositionDTO.setAccount_id(account.getId());
        job58PositionDTO.setPid(positionDB.getId());
        return job58PositionDTO;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(Job58PositionForm thirdPartyPosition, Job58PositionDTO pwa) {
        // 转成do入库
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setOccupation(thirdPartyPosition.getOccupation() + "");
        data.setChannel(getChannel().getValue());
        data.setSyncTime(syncTime);
        data.setThirdPartyAccountId(pwa.getAccount_id());
        data.setPositionId(pwa.getPid());
        data.setIsSynchronization((byte) PositionSync.bound.getValue());
        data.setSalaryDiscuss(thirdPartyPosition.getSalaryDiscuss() == null ? 0 : thirdPartyPosition.getSalaryDiscuss());
        data.setSalaryBottom(thirdPartyPosition.getSalaryBottom() == null ? 0 : thirdPartyPosition.getSalaryBottom());
        data.setSalaryTop(thirdPartyPosition.getSalaryTop() == null ? 0 : thirdPartyPosition.getSalaryTop());
        data.setAddressId(thirdPartyPosition.getAddressId());
        data.setAddressName(thirdPartyPosition.getAddressName());
        data.setFeature(JSON.toJSONString(thirdPartyPosition.getFeatures()));
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO> sendSyncRequest(TransferResult<Job58PositionDTO, ThirdpartyJob58PositionDO> result)
            throws TException {
        JSONObject response;
        Job58PositionDTO job58PositionDTO = result.getPositionWithAccount();
        HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();
        ThirdpartyJob58PositionDO extDO = result.getExtPosition();
        try {
            int pid = job58PositionDTO.getPid();
            job58PositionDTO = postProcessBeforeRequest(job58PositionDTO, hrThirdPartyPositionDO.getThirdPartyAccountId());
            response = job58RequestHandler.sendRequest(job58PositionDTO, Job58PositionOperateConstant.job58PositionSync);
            job58RequestHandler.checkValidResponse(response);
            // 58返回结果data中是xml格式，转为map
            Map<String, String> job58Position = job58RequestHandler.parseXml2Map(response.getString("data"));
            // 入库，此时是下架状态需要上架，如果是下架后重新发布，则更新职位id
            JobPositionJob58MappingDO job58PositionDO = addOrUpdateJob58Position(job58Position, extDO, pid);
            // 将职位上架
            upshelfJob58Position(job58PositionDTO, job58PositionDO);
        } catch (BIZException e) {
            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.failed.getValue());
            hrThirdPartyPositionDO.setSyncFailReason(e.getMessage());
            logger.warn(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("58职位同步异常:{}", e.getMessage());
            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.bindingError.getValue());
            hrThirdPartyPositionDO.setSyncFailReason("后台异常");
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinDevmails, job58PositionDTO, e, null);
        }
        hrThirdPartyPositionDO.setChannel(ChannelType.JOB58.getValue());
        TwoParam<HrThirdPartyPositionDO, ThirdpartyJob58PositionDO> twoParam = new TwoParam<>(hrThirdPartyPositionDO, extDO);
        twoParam = thirdPartyPositionDao.upsertThirdPartyPosition(twoParam);
        return twoParam;
    }

    /**
     * 上架职位
     * @param job58PositionDTO 职位同步dto
     * @param job58PositionDO 58职位mapping
     * @author  cjm
     * @date  2018/11/30
     */
    private void upshelfJob58Position(Job58PositionDTO job58PositionDTO, JobPositionJob58MappingDO job58PositionDO) throws Exception {
        Job58PositionUpshelfDTO upshelfDTO = new Job58PositionUpshelfDTO();
        upshelfDTO.setExtension("开始招聘");
        upshelfDTO.setInfoId(job58PositionDO.getInfoId());
        upshelfDTO.setUseFreeResourceOnly(false);
        upshelfDTO.setAccess_token(job58PositionDTO.getAccess_token());
        upshelfDTO.setApp_key(job58PositionDTO.getApp_key());
        upshelfDTO.setOpenid(job58PositionDTO.getOpenid());
        upshelfDTO.setTimestamp(System.currentTimeMillis());
        JSONObject response= job58RequestHandler.sendRequest(upshelfDTO, Job58PositionOperateConstant.job58PositionShelfUp);
        job58RequestHandler.checkValidResponse(response);
        job58PositionDO.setState((byte)1);
        job58MappingDao.updateData(job58PositionDO);
    }

    private JobPositionJob58MappingDO addOrUpdateJob58Position(Map<String, String> job58Position, ThirdpartyJob58PositionDO extDO, int pid) {
        JobPositionJob58MappingDO job58PositionDO = job58MappingDao.getJob58PositionByPid(pid);
        boolean addFlag = false;
        if(job58PositionDO == null){
            job58PositionDO = new JobPositionJob58MappingDO();
            addFlag = true;
        }
        job58PositionDO.setPositionId(pid);
        job58PositionDO.setInfoId(job58Position.get("infoid"));
        job58PositionDO.setState((byte)0);
        job58PositionDO.setUrl(job58Position.get("url"));
        if(addFlag){
            job58PositionDO = job58MappingDao.addData(job58PositionDO);
        }else {
            job58MappingDao.updateData(job58PositionDO);
        }

        return job58PositionDO;
    }

    /**
     * 请求前的参数构造处理
     * @param job58PositionDTO 请求dto
     * @param thirdAccountId 第三方hrid
     * @author  cjm
     * @date  2018/11/27
     * @return job58PositionDTO
     */
    private Job58PositionDTO postProcessBeforeRequest(Job58PositionDTO job58PositionDTO, int thirdAccountId) {
        HrThirdPartyAccountDO hrThirdPartyAccountDO = thirdPartyAccountDao.getAccountById(thirdAccountId);
        JSONObject userInfo = JSONObject.parseObject(hrThirdPartyAccountDO.getExt());
        String openId = userInfo.getString("openId");
        String accessToken = userInfo.getString("accessToken");
        job58PositionDTO.setOpenid(openId);
        job58PositionDTO.setAccess_token(accessToken);
        job58PositionDTO.setApp_key(Job58PositionOperateConstant.job58AppKey);
        job58PositionDTO.setTimestamp(System.currentTimeMillis());
        // 仟寻职位信息不发送至58
        job58PositionDTO.setAccount_id(null);
        job58PositionDTO.setPid(null);
        return job58PositionDTO;
    }

    @Override
    public ThirdpartyJob58PositionDO toExtThirdPartyPosition(Job58PositionForm position, Job58PositionDTO job58PositionDTO) {
        // 补填字段record
        ThirdpartyJob58PositionDO thirdpartyRecord = new ThirdpartyJob58PositionDO();
        if(position.getSalaryTop() == 0 && position.getSalaryBottom() == 0){
            thirdpartyRecord.setSalaryDiscuss((byte)1);
        }else {
            thirdpartyRecord.setSalaryDiscuss((byte)0);
        }
        thirdpartyRecord.setAddressId(position.getAddressId());
        thirdpartyRecord.setAddressName(position.getAddressName());
        thirdpartyRecord.setFeatures(JSON.toJSONString(position.getFeatures()));
        thirdpartyRecord.setFreshGraduate(position.getFreshGraduate());
        thirdpartyRecord.setOccupation(job58PositionDTO.getCate_id());
        thirdpartyRecord.setSalaryBottom(position.getSalaryBottom());
        thirdpartyRecord.setSalaryTop(position.getSalaryTop());
        thirdpartyRecord.setShowContact(position.getShowContact());
        return thirdpartyRecord;
    }

    @Override
    public ThirdpartyJob58PositionDO toExtThirdPartyPosition(Map<String, String> data) {
        return JSON.parseObject(JSON.toJSONString(data), ThirdpartyJob58PositionDO.class);
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJob58PositionDO ext) {
        // 回填页面
        Job58PositionForm form = new Job58PositionForm();
        form.setAddressId(ext.getAddressId());
        form.setAddressName(ext.getAddressName());
        form.setFeatures(JSONArray.parseArray(ext.getFeatures()).toJavaList(Integer.class));
        form.setFreshGraduate(ext.getFreshGraduate());
        List<String> occupations = new ArrayList<>();
        occupations.add(ext.getOccupation() + "");
        form.setOccupation(occupations);
        form.setSalaryBottom(ext.getSalaryBottom());
        form.setSalaryTop(ext.getSalaryTop());
        form.setSalaryDiscuss(ext.getSalaryDiscuss());
        form.setShowContact(ext.getShowContact());
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(form));
        jsonObject.put("channel", getChannel().getValue());
        return jsonObject;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB58;
    }

    @Override
    public Class<Job58PositionForm> getFormClass() {
        return Job58PositionForm.class;
    }

    @Override
    protected Job58PositionDTO createAndInitAccountInfo(Job58PositionForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        return null;
    }

    @Override
    protected Job58PositionDTO createAndInitPositionInfo(Job58PositionForm positionForm, JobPositionDO positionDB) {
        return null;
    }

    private String parsePositionParam2Job58(Job58PositionForm positionForm, JobPositionDO positionDB, UserHrAccountDO userHrAccountDO) {
        Job58PositionParams job58PositionParams = new Job58PositionParams();
        List<Dict_58jobFeatureRecord> featureRecords = featureJob58Dao.getFeatureByIds(positionForm.getFeatures());
        List<String> features = featureRecords.stream().map(Dict_58jobFeatureRecord::getName).collect(Collectors.toList());
        job58PositionParams.setZhiweiliangdian(String.join("|", features));
        job58PositionParams.setGoblianxiren(userHrAccountDO.getUsername());
        job58PositionParams.setGongzuodizhi(positionForm.getAddressId());
        // 工作年限映射
        int workExperienceDegree = Job58WorkExperienceDegree.getWorkExperienceDegree(Integer.parseInt(positionDB.getExperience())).getDegree();
        job58PositionParams.setGongzuonianxian(workExperienceDegree);
        // 是否面议 0 非面议，1 面议
        String salary;
        if(positionForm.getSalaryBottom() == 0 && positionForm.getSalaryTop() == 0){
            salary = "面议";
        }else {
            salary = positionForm.getSalaryBottom() + "_" + positionForm.getSalaryTop();
        }
        job58PositionParams.setMinxinzi(salary);
        job58PositionParams.setQzapisource(Job58PositionOperateConstant.job58ApiSource);
        // 58技术对接人表示，不传为显示联系方式，传就不显示
        job58PositionParams.setShowcontact(positionForm.getShowContact() == 1 ? null : (byte)0);
        job58PositionParams.setYingjiesheng(positionForm.getFreshGraduate());
        // 学历映射
        int degree = (int) positionDB.getDegree();
        job58PositionParams.setXueliyaoqiu(Job58PositionDegree.getPositionDegree(degree).getJob58DegreeNo());
        job58PositionParams.setZhaopinrenshu((int)positionDB.getCount() + "");
        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>() {};
        // map转xml格式发请求
        Map<String, String> params = JSONObject.parseObject(JSON.toJSONString(job58PositionParams), typeReference);
        return job58RequestHandler.map2xml(params);
    }

    /**
     * 映射58cityCode
     * @param  positionDB  职位数据
     * @author  cjm
     * @date  2018/11/27
     * @return  58cityCode
     */
    private Integer doGetCityCode(JobPositionDO positionDB) throws BIZException {
        List<List<String>> cityCodesList = getCities(positionDB);
        if (cityCodesList != null && cityCodesList.size() > 0) {
            List<String> oneList = cityCodesList.get(0);
            if (oneList != null && oneList.size() > 0) {
                return Integer.parseInt(oneList.get(0));
            } else {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_CITY_MAPPING_NONEXIST);
            }
        } else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_CITY_MAPPING_NONEXIST);
        }
    }
}
