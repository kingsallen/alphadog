package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyCompanyChannelConfDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.constants.CompanyType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.Position.PositionSource;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.pojo.ChannelTypePojo;
import com.moseeker.position.pojo.JobPositionFailMess;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.HrCompanyFeatureDO;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyCompanyChannelConfDO;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PositionATSService {
    Logger logger = LoggerFactory.getLogger(PositionATSService.class);

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private PositionService service;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private PositionQxService positionQxService;


    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private ThirdpartyCompanyChannelConfDao thirdpartyCompanyChannelConfDao;

    @Autowired
    List<AbstractPositionTransfer> transferList;

    /**
     * 获取所有可同步的渠道信息
     * 这里获取的channel不是所有channelType的类型
     * 而是所有实现了AbstractPositionTransfer的渠道类型
     * 因为不是所有的ChannelType都可以同步，
     * 但是可同步的渠道必须实现AbstractPositionTransfer
     * @return  可同步的渠道信息
     */
    public List<ChannelTypePojo> getSyncChannel(){
        return transferList.stream().map(p->{
            ChannelTypePojo channelTypePojo = new ChannelTypePojo();
            channelTypePojo.setCode(p.getChannel().getValue());
            channelTypePojo.setText(p.getChannel().getAlias());
            return channelTypePojo;
        }).distinct().collect(Collectors.toList());
    }

    /**
     * 更新公司可同步渠道配置，没有的新增，并且删除 不在传入参数中的数据
     * 如果传入的channel为空列表，则会删除所有可同步渠道，deleteAllChannelByCompanyId
     * @param company_id    公司ID
     * @param channel       需要配置的渠道号
     * @return  配置结果
     */
    public List<ThirdpartyCompanyChannelConfDO> updateCompanyChannelConf(int company_id,List<Integer> channel) throws BIZException {
        if(company_id == 0 || channel == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        HrCompanyDO companyDO = companyDao.getCompanyById(company_id);
        if(companyDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }

        //必须是付费公司
        if(companyDO.getType() != CompanyType.PAY.getCode()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.ONLY_PAY_COMPANY_CAN_CONF_CHANNEL);
        }

        // 只能更新母公司
        if(companyDO.getParentId() != 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.SUB_COMPANY_CANT_CONF_CHANNEL);
        }

        // 渠道号必须有效
        List<ChannelTypePojo> channelTypePojoList = getSyncChannel();
        out:
        for(int c : channel){
            for(ChannelTypePojo ctp : channelTypePojoList){
                if(c == ctp.getCode()){
                    continue out;
                }
            }
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.WRONG_SYNC_CHANNEL);
        }

        List<ThirdpartyCompanyChannelConfDO> confs = thirdpartyCompanyChannelConfDao.getConfByCompanyId(company_id);

        //找出在数据库，但不在本次要设置的渠道，后面删除
        List<ThirdpartyCompanyChannelConfDO> toBeDeleted = new ArrayList<>();
        out:
        for(ThirdpartyCompanyChannelConfDO conf:confs){
            for(Integer c:channel){
                if(conf.getChannel() == c){
                    continue out;
                }
            }
            toBeDeleted.add(conf);
        }

        //找出在本次要设置的渠道，但不在数据库，后面新增
        List<ThirdpartyCompanyChannelConfDO> toBeAdd = new ArrayList<>();
        out:
        for(Integer c:channel){
            for(ThirdpartyCompanyChannelConfDO conf:confs){
                if(conf.getChannel() == c){
                    continue out;
                }
            }
            ThirdpartyCompanyChannelConfDO temp = new ThirdpartyCompanyChannelConfDO();
            temp.setChannel(c);
            temp.setCompanyId(company_id);

            toBeAdd.add(temp);
        }


        thirdpartyCompanyChannelConfDao.deleteDatas(toBeDeleted);
        logger.info("be delete company channel conf:{}",toBeDeleted);
        thirdpartyCompanyChannelConfDao.addAllData(toBeAdd);
        logger.info("be add company channel conf:{}",toBeAdd);

        return thirdpartyCompanyChannelConfDao.getConfByCompanyId(company_id);
    }

    /**
     * 根据公司ID获取配置的可同步渠道
     * @param company_id    公司ID
     * @return  配置的可同步渠道号
     * @throws BIZException
     */
    public List<Integer> getCompanyChannelConfByCompanyId(int company_id) throws BIZException {
        HrCompanyDO companyDO = companyDao.getCompanyById(company_id);
        if(companyDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }

        // 如果是子公司，需要用母公司查询配置，因为只有母公司才能配置可发布渠道
        if(companyDO.getParentId() != 0){
            company_id = companyDO.getParentId();
        }

        List<ThirdpartyCompanyChannelConfDO> result = thirdpartyCompanyChannelConfDao.getConfByCompanyId(company_id);
        if(StringUtils.isEmptyList(result)){
            return Collections.emptyList();
        }

        return result.stream().map(c->c.getChannel()).collect(Collectors.toList());
    }

    /**
     * ATS谷露新增职位
     *
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response insertGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if (isDataEmpty(batchHandlerJobPostion)) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            //来源应该位9
            if (batchHandlerJobPostion.getData().get(0).getSource() != PositionSource.ATS.getCode()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.SOURCE_ERROR);
            }

            //更新，需要判断职位是否存在，不存在才能新增
            if (checkGllueJobPositionExist(batchHandlerJobPostion)) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_ALREADY_EXIST);
            }

            return callBatchHandlerJobPostion(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    /**
     * ATS谷露更新职位
     *
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response updateGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if (isDataEmpty(batchHandlerJobPostion)) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            //更新，需要判断职位是否存在，存在，才能更新
            if (!checkGllueJobPositionExist(batchHandlerJobPostion)) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
            }

            return callBatchHandlerJobPostion(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    /**
     * ATS谷露下架的职位重新发布
     *
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response republishPosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if (isDataEmpty(batchHandlerJobPostion)) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

            if (postionObj.getCompany_id() == 0 || postionObj.getPublisher() == 0 || postionObj.getSource_id() == 0 || StringUtils.isNullOrEmpty(postionObj.getJobnumber())) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }

            JobPositionRecord jobPositionRecord = jobPositionDao.getUniquePosition(
                    postionObj.getCompany_id(),
                    PositionSource.ATS.getCode(),
                    postionObj.getSource_id(),
                    postionObj.getJobnumber());

            if (jobPositionRecord == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
            }

            if (jobPositionRecord.getStatus().intValue() == PositionStatus.ACTIVED.getValue()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_ALREADY_ACTIVE);
            }

            JSONObject updateField = new JSONObject();
            updateField.put("status", PositionStatus.ACTIVED.getValue());
            updateField.put("id", jobPositionRecord.getId());

            JSONObject param = new JSONObject();
            param.put("id", jobPositionRecord.getId());
            param.put("accountId", postionObj.getPublisher());
            param.put("updateField", updateField);

            Response response = service.updatePosition(param.toJSONString());

            return addPositionUrlOnlySuccess(response, jobPositionRecord.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    /**
     * 下架谷露职位
     *
     * @param batchHandlerJobPostion
     * @return
     */
    public Response revokeGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) {
        if (isDataEmpty(batchHandlerJobPostion)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

        if (postionObj.getCompany_id() == 0 || postionObj.getPublisher() == 0 || postionObj.getSource_id() == 0 || StringUtils.isNullOrEmpty(postionObj.getJobnumber())) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }

        JobPositionRecord jobPositionRecord = jobPositionDao.getUniquePosition(
                postionObj.getCompany_id(),
                PositionSource.ATS.getCode(),
                postionObj.getSource_id(),
                postionObj.getJobnumber());

        if (jobPositionRecord == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
        }

        if (jobPositionRecord.getStatus().intValue() == PositionStatus.BANNED.getValue()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_ALREADY_BANNED);
        }

        JSONObject updateField = new JSONObject();
        updateField.put("status", PositionStatus.BANNED.getValue());
        updateField.put("priority", 10);
        updateField.put("stopDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        updateField.put("id", jobPositionRecord.getId());

        JSONObject param = new JSONObject();
        param.put("id", jobPositionRecord.getId());
        param.put("accountId", postionObj.getPublisher());
        param.put("updateField", updateField);

        Response response = service.updatePosition(param.toJSONString());

        //解除绑定，绑定在hr_third_part_position当中，传递position和要修改的is_synchronization
        thirdPartyPositionDao.disable(Arrays.asList(jobPositionRecord.getId()));

        return addPositionUrlOnlySuccess(response, jobPositionRecord.getId());
    }

    /**
     * ATS职位修改福利特色
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public JobPostionResponse atsUpdatePositionFeature(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        if(batchHandlerJobPostion == null || StringUtils.isEmptyList(batchHandlerJobPostion.data)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        // 查找职位ID，以及新增不存在的福利特色
        initPidAndFeature(batchHandlerJobPostion);

        // 更新职位福利特色
        return updatePositionFeature(batchHandlerJobPostion);
    }

    /**
     * 更新职位福利特色，默认所有福利特色存在
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    JobPostionResponse updatePositionFeature(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        if(batchHandlerJobPostion == null || StringUtils.isEmptyList(batchHandlerJobPostion.getData())){
            return new JobPostionResponse();
        }

        // 返回新增或者更新失败的职位信息
        List<JobPositionFailMess> jobPositionFailMessPojos = new ArrayList<>();

        int companyId=batchHandlerJobPostion.getData().get(0).getCompany_id();

        // 因为之前新增了不存在的福利特色，所以重新按照公司ID再查询一遍福利特色
        Map<String,HrCompanyFeature> featureMap = getCompanyFeatureGroupByName(companyId);

        // 给职位设置福利特色
        for (JobPostrionObj jobPositionHandlerDate : batchHandlerJobPostion.getData()){
            if(jobPositionHandlerDate.getId() == 0){
                service.handlerFailMess(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL,jobPositionFailMessPojos,jobPositionHandlerDate);
                continue;
            }
            List<Integer> featureIds = new ArrayList<>();

            String feature = jobPositionHandlerDate.getFeature();
            if(StringUtils.isNotNullOrEmpty(feature)){
                for(String featureName:feature.split("#")){
                    if(!featureMap.containsKey(featureName)){
                        logger.error("ats update position feature error companyId:{} feature:{}",companyId,featureName);
                        continue;
                    }
                    featureIds.add(featureMap.get(featureName).getId());
                }
            }

            positionQxService.updatePositionFeatureList(jobPositionHandlerDate.getId(),featureIds);
        }

        JobPostionResponse response = new JobPostionResponse();
        response.setJobPositionFailMessPojolist(jobPositionFailMessPojos);

        return response;
    }

    /**
     * 查找职位ID,默认福利特色都存在
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    BatchHandlerJobPostion initPidAndFeature(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        int companyId = batchHandlerJobPostion.getData().get(0).getCompany_id();

        Map<String,HrCompanyFeature> featureMap = getCompanyFeatureGroupByName(companyId);

        List<HrCompanyFeatureDO> companyFeatureAddList = new ArrayList<>();

        for (JobPostrionObj jobPositionHandlerDate : batchHandlerJobPostion.getData()){
            if(jobPositionHandlerDate.getId() == 0) {

                JobPositionRecord jobPositionRecord = jobPositionDao.getUniquePosition(
                        jobPositionHandlerDate.getCompany_id(),
                        PositionSource.ATS.getCode(),
                        jobPositionHandlerDate.getSource_id(),
                        jobPositionHandlerDate.getJobnumber());

                if (jobPositionRecord == null) {
                    continue;
                }

                jobPositionHandlerDate.setId(jobPositionRecord.getId());
            }

            String feature = jobPositionHandlerDate.getFeature();
            if(feature == null){
                continue;
            }

            for(String featureName:feature.split("#")){
                if (!featureMap.containsKey(featureName)){
                    companyFeatureAddList.add(buildCompanyFeature(featureName,companyId));

                    //防止重复往companyFeatureAddList里加入需要添加的福利特色
                    featureMap.put(featureName,null);
                }
            }

        }
//        companyServices.addCompanyFeatures(companyFeatureAddList);

        return batchHandlerJobPostion;
    }

    /**
     * 构造需要添加的福利特色类
     * @param featureName   福利特色名称
     * @param companyId     公司ID
     * @return
     */
    private HrCompanyFeatureDO buildCompanyFeature(String featureName,int companyId){
        HrCompanyFeatureDO companyFeature = new HrCompanyFeatureDO();
        companyFeature.setDisable(1);
        companyFeature.setFeature(featureName);
        companyFeature.setCompany_id(companyId);
        return companyFeature;
    }

    /**
     * 根据公司ID获取所有福利特色，并按照名称分组
     * @param companyId 公司名称
     * @return
     * @throws TException
     */
    Map<String,HrCompanyFeature> getCompanyFeatureGroupByName(int companyId) throws TException {
        Response response = companyServices.getFeatureByCompanyId(companyId);
        String data = response.getData();
        if(StringUtils.isNullOrEmpty(data)){
            return new HashMap<>();
        }
        TypeReference<List<HrCompanyFeature>> typeRef = new TypeReference<List<HrCompanyFeature>>(){};
        List<HrCompanyFeature> features= JSON.parseObject(data,typeRef);
        if(StringUtils.isEmptyList(features)){
            return new HashMap<>();
        }
        return features.stream().filter(f->f.getCompanyId().equals(companyId)).collect(Collectors.toMap(f->f.getFeature(),f->f));
    }

    /**
     * 在谷露返回参数中加上职位url,只有success才返回
     * @param id
     * @return
     */
    private Response addPositionUrlOnlySuccess(Response response, int id){
        try {
            if(response.getStatus()==0 && response.getMessage().equals("success")){
                ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
                configUtils.loadResource("setting.properties");
                String url=configUtils.get("gllue.url", String.class);

                url=url+"position/index/pid/"+id;

                Map<String,String> data=new HashMap<>();
                data.put("url",url);

                return ResponseUtils.success(data);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断批量传递的职位数据是否为空
     * @param batchHandlerJobPostion
     * @return
     */
    private boolean isDataEmpty(BatchHandlerJobPostion batchHandlerJobPostion){
        return batchHandlerJobPostion == null || batchHandlerJobPostion.getData() == null || batchHandlerJobPostion.getData().isEmpty();
    }

    /**
     * 调用批量修改职位，设置初始值，以及对返回值的处理，从list返回值改为一个返回值
     * @param batchHandlerJobPostion 要批量修改的职位
     * @return
     * @throws BIZException
     */
    private Response callBatchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        batchHandlerJobPostion.setNodelete(true);
        batchHandlerJobPostion.setIsCreateDeparment(false);

        JobPostionResponse response=service.batchHandlerJobPostionAdapter(batchHandlerJobPostion);

        JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

        JobPositionRecord jobPositionRecord=jobPositionDao.getUniquePosition(
                postionObj.getCompany_id(),
                PositionSource.ATS.getCode(),
                postionObj.getSource_id(),
                postionObj.getJobnumber());

        return handleJobPostionResponse(response,jobPositionRecord);
    }

    /**
     * 处理返回值，从list返回值改为一个返回值
     * @param jobPostionResponse
     * @param jobPositionRecord
     * @return
     */
    private Response handleJobPostionResponse(JobPostionResponse jobPostionResponse, JobPositionRecord jobPositionRecord){
        if(StringUtils.isEmptyList(jobPostionResponse.getJobPositionFailMessPojolist())){
            if(jobPostionResponse.getInsertCounts()==0 && jobPostionResponse.getUpdateCounts()==0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
            }
            return addPositionUrlOnlySuccess(ResponseUtils.success(""),jobPositionRecord.getId());
        }else{
            JobPositionFailMess failMess=jobPostionResponse.getJobPositionFailMessPojolist().get(0);
            return ResponseUtils.fail(failMess.getStatus(),failMess.getMessage());
        }
    }

    /**
     * 检测ATS谷露职位参数是否存在
     * @param batchHandlerJobPostion
     * @return true：存在，false：不存在
     */
    private boolean checkGllueJobPositionExist(BatchHandlerJobPostion batchHandlerJobPostion){
        JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

        return jobPositionDao.positionAlreadyExist(
                postionObj.getCompany_id(),
                PositionSource.ATS.getCode(),
                postionObj.getSource_id(),
                postionObj.getJobnumber());
    }

    class FeatureThread extends Thread{
        private BatchHandlerJobPostion batchHandlerJobPostion;

        public FeatureThread(BatchHandlerJobPostion batchHandlerJobPostion){
            this.batchHandlerJobPostion = batchHandlerJobPostion;
        }

        @Override
        public void run() {
            try {
                atsUpdatePositionFeature(batchHandlerJobPostion);
            } catch (TException e) {
                logger.error("ats update position feature error:{}",e);
            }
        }
    }

}
