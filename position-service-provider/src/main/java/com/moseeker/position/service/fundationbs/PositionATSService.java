package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.Position.PositionSource;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.position.pojo.JobPositionFailMess;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class PositionATSService {
    Logger logger= LoggerFactory.getLogger(PositionATSService.class);

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private PositionService service;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    /**
     * ATS谷露新增职位
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response insertGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if(isDataEmpty(batchHandlerJobPostion)){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            //更新，需要判断职位是否存在，不存在才能新增
            if(checkGllueJobPositionExist(batchHandlerJobPostion)){
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
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response updateGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if(isDataEmpty(batchHandlerJobPostion)){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            //更新，需要判断职位是否存在，存在，才能更新
            if(!checkGllueJobPositionExist(batchHandlerJobPostion)){
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
     * @param batchHandlerJobPostion
     * @return
     * @throws TException
     */
    public Response republishPosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            if(isDataEmpty(batchHandlerJobPostion)){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

            if(postionObj.getCompany_id() == 0 || postionObj.getPublisher() == 0 || postionObj.getSource_id() == 0 || StringUtils.isNullOrEmpty(postionObj.getJobnumber())){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }

            JobPositionRecord jobPositionRecord=jobPositionDao.getUniquePosition(
                    postionObj.getCompany_id(),
                    PositionSource.ATS.getCode(),
                    postionObj.getSource_id(),
                    postionObj.getJobnumber());

            if(jobPositionRecord==null){
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
            }

            if(jobPositionRecord.getStatus().intValue() == PositionStatus.ACTIVED.getValue()){
                return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_ALREADY_ACTIVE);
            }

            JSONObject updateField=new JSONObject();
            updateField.put("status", PositionStatus.ACTIVED.getValue());
            updateField.put("id",jobPositionRecord.getId());

            JSONObject param=new JSONObject();
            param.put("id",jobPositionRecord.getId());
            param.put("accountId",postionObj.getPublisher());
            param.put("updateField",updateField);

            Response response=service.updatePosition(param.toJSONString());

            return addPositionUrlOnlySuccess(response,jobPositionRecord.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    /**
     * 下架谷露职位
     * @param batchHandlerJobPostion
     * @return
     */
    public Response revokeGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion){
        if(isDataEmpty(batchHandlerJobPostion)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        JobPostrionObj postionObj = batchHandlerJobPostion.getData().get(0);

        if(postionObj.getCompany_id() == 0 || postionObj.getPublisher() == 0 || postionObj.getSource_id() == 0 || StringUtils.isNullOrEmpty(postionObj.getJobnumber())){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }

        JobPositionRecord jobPositionRecord=jobPositionDao.getUniquePosition(
                postionObj.getCompany_id(),
                PositionSource.ATS.getCode(),
                postionObj.getSource_id(),
                postionObj.getJobnumber());

        if(jobPositionRecord==null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
        }

        if(jobPositionRecord.getStatus().intValue() == PositionStatus.BANNED.getValue()){
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_ALREADY_BANNED);
        }

        JSONObject updateField=new JSONObject();
        updateField.put("status", PositionStatus.BANNED.getValue());
        updateField.put("priority", 10);
        updateField.put("stopDate", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        updateField.put("id",jobPositionRecord.getId());

        JSONObject param=new JSONObject();
        param.put("id",jobPositionRecord.getId());
        param.put("accountId",postionObj.getPublisher());
        param.put("updateField",updateField);

        Response response=service.updatePosition(param.toJSONString());

        //解除绑定，绑定在hr_third_part_position当中，传递position和要修改的is_synchronization
        Condition condition=new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),jobPositionRecord.getId());
        thirdPartyPositionDao.disable(Arrays.asList(condition));

        return addPositionUrlOnlySuccess(response,jobPositionRecord.getId());
    }

    /**
     * 在谷露返回参数中加上职位url,只有success才返回
     * @param id
     * @return
     */
    public Response addPositionUrlOnlySuccess(Response response, int id){
        try {
            if(response.getStatus()==0 && response.getMessage().equals("success")){
                ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
                configUtils.loadResource("setting.properties");
                String url=configUtils.get("gllue.url", String.class);

                url=url+"position/index/pid/"+id;

                Map<String,String> data=new HashMap<>();
                data.put("url",url);

                return ResponseUtils.success(url);
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
    private Response callBatchHandlerJobPostion(BatchHandlerJobPostion batchHandlerJobPostion) throws BIZException {
        batchHandlerJobPostion.setNodelete(true);
        batchHandlerJobPostion.setIsCreateDeparment(false);

        JobPostionResponse response=service.batchHandlerJobPostion(batchHandlerJobPostion);

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

}
