package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyJob58PositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.position.constants.position.job58.Job58PositionDegree;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.job58.dto.Base58UserInfoDTO;
import com.moseeker.position.service.position.job58.dto.Job58PositionDTO;
import com.moseeker.position.service.position.job58.dto.Job58PositionParams;
import com.moseeker.position.service.position.job58.vo.Job58PositionForm;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-10-26 14:00
 **/
@Service
public class Job58PositionTransfer extends AbstractPositionTransfer<Job58PositionForm,Job58PositionDTO,Job58PositionDTO,ThirdpartyJob58PositionRecord> {

    private static Logger logger = LoggerFactory.getLogger(Job58PositionTransfer.class);
    @Autowired
    private HRThirdPartyAccountHrDao hrThirdPartyDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private Job58RequestHandler job58RequestHandler;

    private static final String API_SOURCE = "qianxun008";

    @Override
    public Job58PositionDTO changeToThirdPartyPosition(Job58PositionForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        // 发请求用
        Job58PositionDTO job58PositionDTO = new Job58PositionDTO();
        JSONObject userInfo = JSONObject.parseObject(account.getExt());
        job58PositionDTO.setOpenid(userInfo.getString("openId"));
        job58PositionDTO.setCate_id(positionForm.getOccupation());
        String content = "职位描述：</br>" + positionDB.getAccountabilities() + "</br>职位要求：</br>" + positionDB.getRequirement();
        job58PositionDTO.setContent(content);
        job58PositionDTO.setLocal_id(doGetCityCode(positionDB));
        // todo 获取仟寻账号的信息
        HrThirdPartyAccountHrDO accountHrDO = hrThirdPartyDao.getHrAccountByThirdPartyId(account.getId());
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getUserHrAccountById(accountHrDO.getHrAccountId());
        job58PositionDTO.setPhone(userHrAccountDO.getMobile());
        job58PositionDTO.setTitle(positionDB.getTitle());
        job58PositionDTO.setParas(parsePositionParam2Job58(positionForm, positionDB, userHrAccountDO));
        // todo 設置email
        job58PositionDTO.setEmail("");
        job58PositionDTO.setAccount_id(account.getId());
        job58PositionDTO.setPid(positionDB.getId());
        return null;
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
    public HrThirdPartyPositionDO toThirdPartyPosition(Job58PositionForm thirdPartyPosition, Job58PositionDTO pwa) {
        // 转成do入库
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        logger.info("================syncTime:{}===============", syncTime);
        data.setSyncTime(syncTime);
        data.setOccupation(thirdPartyPosition.getOccupation() + "");
        data.setChannel(getChannel().getValue());
        data.setThirdPartyAccountId(pwa.getAccount_id());
        data.setPositionId(pwa.getPid());
        data.setIsSynchronization((byte) PositionSync.bound.getValue());
        data.setSalaryDiscuss(thirdPartyPosition.getSalaryDiscuss());
        data.setSalaryBottom(thirdPartyPosition.getSalaryBottom());
        data.setSalaryTop(thirdPartyPosition.getSalaryTop());
        data.setAddressId(thirdPartyPosition.getAddressId());
        data.setAddressName(thirdPartyPosition.getAddressName());
        data.setFeature(JSON.toJSONString(thirdPartyPosition.getFeatures()));
        return data;
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO,ThirdpartyJob58PositionRecord> sendSyncRequest(TransferResult<Job58PositionDTO,ThirdpartyJob58PositionRecord> result)
            throws TException {
        Job58PositionDTO job58PositionDTO = result.getPositionWithAccount();
        HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();
        return null;
    }

    @Override
    public ThirdpartyJob58PositionRecord toExtThirdPartyPosition(Job58PositionForm position, Job58PositionDTO job58PositionDTO) {
        // 补填字段record
        ThirdpartyJob58PositionRecord thirdpartyRecord = new ThirdpartyJob58PositionRecord();
        thirdpartyRecord.setAddressId(position.getAddressId());
        thirdpartyRecord.setAddressName(position.getAddressName());
        thirdpartyRecord.setFeatures(JSON.toJSONString(position.getFeatures()));
        thirdpartyRecord.setFreshGraduate(position.getFreshGraduate());
        thirdpartyRecord.setOccupation(position.getOccupation());
        // todo 设置职位id
//        thirdpartyRecord.setPid(position.get);
        thirdpartyRecord.setSalaryBottom(position.getSalaryBottom());
        thirdpartyRecord.setSalaryTop(position.getSalaryTop());
        thirdpartyRecord.setShowContact(position.getShowContact());
        return thirdpartyRecord;
    }

    @Override
    public ThirdpartyJob58PositionRecord toExtThirdPartyPosition(Map<String, String> data) {
        return JSON.parseObject(JSON.toJSONString(data),ThirdpartyJob58PositionRecord.class);
    }
    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJob58PositionRecord ext) {
        // 回填页面
        Job58PositionForm form = new Job58PositionForm();
        form.setAddressId(ext.getAddressId());
        form.setAddressName(ext.getAddressName());
        form.setFeatures(JSONArray.parseArray(ext.getFeatures()).toJavaList(Integer.class));
        form.setFreshGraduate(ext.getFreshGraduate());
        form.setOccupation(ext.getOccupation());
        form.setSalaryBottom(ext.getSalaryBottom());
        form.setSalaryTop(ext.getSalaryTop());
        form.setSalaryDiscuss(ext.getSalaryDiscuss());
        form.setShowContact(ext.getShowContact());
        return JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
    }

    @Override
    protected Job58PositionDTO createAndInitAccountInfo(Job58PositionForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        return null;
    }

    @Override
    protected Job58PositionDTO createAndInitPositionInfo(Job58PositionForm positionForm, JobPositionDO positionDB) throws Exception {
        return null;
    }

    private String parsePositionParam2Job58(Job58PositionForm positionForm, JobPositionDO positionDB, UserHrAccountDO userHrAccountDO) throws IOException {
        Job58PositionParams job58PositionParams = new Job58PositionParams();
        // todo 需要判断福利特色的必填
        List<String> features = positionForm.getFeatures().stream().map(String::valueOf).collect(Collectors.toList());
        job58PositionParams.setFulibaozhang(String.join("|", features));
        job58PositionParams.setGoblianxiren(userHrAccountDO.getUsername());
        job58PositionParams.setGongzuodizhi(positionForm.getAddressId());
        job58PositionParams.setGongzuonianxian(Integer.parseInt(positionDB.getExperience()));
        int salaryDiscuss = positionForm.getSalaryDiscuss();
        job58PositionParams.setMinxinzi(salaryDiscuss == 1 ? "面议" : positionForm.getSalaryBottom() + "_" + positionForm.getSalaryTop());
        job58PositionParams.setQzapisource(API_SOURCE);
        job58PositionParams.setShowcontact(positionForm.getShowContact());
        job58PositionParams.setYingjiesheng(positionForm.getFreshGraduate());
        int degree = (int) positionDB.getDegree();
        job58PositionParams.setXueliyaoqiu(Job58PositionDegree.getPositionDegree(degree).getJob58DegreeNo());
        job58PositionParams.setZhaopinrenshu(positionDB.getCount() + "");
        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String, String>>(){};
        Map<String, String> params = JSONObject.parseObject(JSON.toJSONString(job58PositionParams), typeReference);
        return job58RequestHandler.map2xml(params);
    }

    private Integer doGetCityCode(JobPositionDO positionDB) throws BIZException {
        List<List<String>> cityCodesList = getCities(positionDB);
        if(cityCodesList != null && cityCodesList.size() > 0){
            List<String> oneList = cityCodesList.get(0);
            if(oneList != null && oneList.size() > 0){
                return Integer.parseInt(oneList.get(0));
            }else {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_CITY_MAPPING_NONEXIST);
            }
        }else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_CITY_MAPPING_NONEXIST);
        }
    }
}
