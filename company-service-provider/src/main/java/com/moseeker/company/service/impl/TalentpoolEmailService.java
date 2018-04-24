package com.moseeker.company.service.impl;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.entity.Constant.EmailAccountConsumptionType;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2018/4/20.
 */
@Service
public class TalentpoolEmailService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentpoolEmailService(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private TalentPoolEntity talentPoolEntity;

    @Autowired
    private TalentPoolEmailEntity talentPoolEmailEntity;

    /**
     * 获取公司邮件剩余额度
     * @param hr_id         HR编号
     * @param company_id    公司编号
     * @return
     */
   public Response getCompanyEmailBalance(int hr_id, int company_id){
       HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
       if(companyDO == null){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
       }
       int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
       if(flag == -1){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
       }else if(flag == -2){
           return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
       }else if(flag == -3){
           return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
       }
       HrCompanyEmailInfo info = talentPoolEmailEntity.getHrCompanyEmailInfoByCompanyId(company_id);
       Map<String, Object> infoMap = new HashMap<>();
       infoMap.put("number", info.getBalance());
       return ResponseUtils.success(infoMap);
   }

    /**
     * 获取公司的邮件列表
     * @param hr_id         hr编号
     * @param company_id    公司编号
     * @return
     */
    public Response getCompanyEmailTemlateList(int hr_id, int company_id){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        List<TalentpoolEmail> info = talentPoolEmailEntity.getTalentpoolEmailByCompanyId(company_id);
        List<Map<String, Object>> email_list = new ArrayList<>();
        if(info != null && info.size()>0){
            List<Integer> idList = info.stream().map(m -> m.getConfigId()).collect(Collectors.toList());
            List<ConfigSysTemplateMessageLibraryDO> libraryDOS = talentPoolEmailEntity.getConfigSysTemplateMessageLibraryDOByIdList(idList);
            if(libraryDOS != null && libraryDOS.size()>0){
                for(ConfigSysTemplateMessageLibraryDO tempalte : libraryDOS){
                    for(TalentpoolEmail email : info){
                        if(tempalte.getId() == email.getConfigId()){
                            Map<String, Object> emailInfo = new HashMap<>();
                            emailInfo.put("id",email.getId());
                            emailInfo.put("company_id",email.getCompanyId());
                            emailInfo.put("type",tempalte.getId());
                            emailInfo.put("name",tempalte.getTitle());
                            emailInfo.put("send_condition",tempalte.getSendCondition());
                            emailInfo.put("send_to",tempalte.getSendto());
                            emailInfo.put("send_time",tempalte.getSendtime());
                            emailInfo.put("status",email.getDisable());
                            email_list.add(emailInfo);
                            break;
                        }
                    }
                }
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("email_list",email_list);
        return ResponseUtils.success(data);
    }

    /**
     * 获取邮件的具体信息
     * @param hr_id         HR编号
     * @param company_id    公司编号
     * @param type          邮件类型
     * @return
     */
    public Response getCompanyEmailTemlateInfo(int hr_id, int company_id, int type){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        List<TalentpoolEmail> info = talentPoolEmailEntity.getTalentpoolEmailByCompanyIdAndConfigId(company_id, type);
        Map<String, Object> data = new HashMap<>();
        if(info != null && info.size()>0){
            ConfigSysTemplateMessageLibraryDO libraryDO = talentPoolEmailEntity.getConfigSysTemplateMessageLibraryDOById(type);
            if(libraryDO != null){
                for(TalentpoolEmail email : info){
                    if(email.getConfigId() == libraryDO.getId()){
                        List<String> symbol = new ArrayList<>();
                        symbol.add("#职位名称#");
                        symbol.add("#求职者姓名#");
                        symbol.add("#HR姓名#");
                        symbol.add("#公司简称#");
                        symbol.add("#公众号名称#");
                        data.put("symbol", symbol);
                        data.put("context", email.getContext());
                        data.put("inscribe", email.getInscribe());
                        data.put("default_text", libraryDO.getFirst());
                        data.put("default_sign", libraryDO.getRemark());
                        data.put("id", email.getId());
                    }
                }
            }
        }
        return ResponseUtils.success(data);
    }


    public Response updateEmailInfo(int hr_id, int company_id, int type, int disable, String context, String inscribe){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        int result = talentPoolEmailEntity.updateEmailInfo(company_id, type, disable, context, inscribe);
        if(result >0 ) {
            return ResponseUtils.success("");
        }else{
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
        }
    }


    public EmailAccountForm fetchEmailAccounts(int companyId, String companyName, int pageNumber, int pageSize) throws CommonException {
        return talentPoolEntity.fetchEmailAccounts(companyId, companyName, pageNumber, pageSize);
    }

    public EmailAccountConsumptionForm fetchEmailAccountConsumption(int companyId, EmailAccountConsumptionType emailAccountConsumptionType, int pageNumber, int pageSize) {
        return talentPoolEntity.fetchEmailAccountConsumption(companyId, emailAccountConsumptionType, pageNumber, pageSize);
    }

    public int rechargeEmailAccount(int companyId, int lost) {
        talentPoolEntity.rechargeEmailAccount(companyId, lost);
    }
}
