package com.moseeker.company.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.company.service.impl.TalentPoolService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolNewServices.Iface;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TalentpoolNewThriftServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentpoolNewThriftServiceImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private TalentPoolService service;


    @Override
    public Response getCompanyTagList(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{

            return service.getCompanyTagList(hr_id,company_id,page_number, page_size);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response deleteCompanyTagByIds(int hr_id, int company_id, List<Integer> company_tag_ids) throws BIZException, TException {
        try{
            return service.deleteCompanyTags(hr_id,company_id,company_tag_ids);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCompanyIdInfo(int hr_id, int company_id, int company_tag_id) throws BIZException, TException {
        try{
            Map<String, Object> result =  service.getCompanyTagInfo(hr_id,company_id, company_tag_id);
            if(result != null && result.get("responseStatus")!=null) {
                int resultStatus = (Integer)result.get("responseStatus");
                if (resultStatus == 0) {
                    if(result.get("data") != null) {
                        Map<String, Object> resultData = (Map<String, Object>)result.get("data");
                        String result1 = JSON.toJSONString(resultData, serializeConfig);
                        return ResponseUtils.successWithoutStringify(result1);
                    }else{
                        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
                    }
                } else if (resultStatus == -1) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
                } else if (resultStatus == -2) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
                } else if (resultStatus == -3) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
                } else {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
                }
            }else{
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id) throws BIZException, TException {
        try{
            return service.addCompanyTag(companyTagDO, hr_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updateCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id) throws BIZException, TException {
        try{
            return service.updateCompanyTag(companyTagDO, hr_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addProfileContent(int userId, int accountId, String content) throws BIZException, TException {
        return service.addProfileComment(userId, accountId, content);
    }

    @Override
    public Response deleteHrAutoMaticTagByIds(int hr_id, int company_id, List<Integer> tag_ids) throws BIZException, TException {
        try{
            return service.deleteHrAutoTags(hr_id,company_id,tag_ids);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addHrAutoMaticTag(TalentpoolHrAutomaticTagDO hrAutoTagDO, int company_id) throws BIZException, TException {
        try{
            return service.addHrAutomaticTag(hrAutoTagDO,company_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response updateHrAutoMaticTag(TalentpoolHrAutomaticTagDO hrAutoTagDO, int company_id) throws BIZException, TException {
        try{
            return service.updateHrAutoTag(hrAutoTagDO,company_id);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getHrAutoMaticTagList(int hr_id, int company_id, int page_number, int page_size) throws BIZException, TException {
        try{
            return service.getHrAutoTagList(hr_id,company_id,page_number,page_size);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }
}

