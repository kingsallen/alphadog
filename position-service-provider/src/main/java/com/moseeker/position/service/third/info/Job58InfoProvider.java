package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictFeatureJob58Dao;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobFeatureRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.job58.Job58FeatureVO;
import com.moseeker.position.service.position.job58.Job58RequestHandler;
import com.moseeker.position.service.position.job58.dto.Base58UserInfoDTO;
import com.moseeker.position.service.position.job58.dto.Job58AddressRequestDTO;
import com.moseeker.position.service.position.job58.vo.Job58AddressVO;
import com.moseeker.position.service.third.ThirdPartyAccountAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountCompanyService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjm
 * @date 2018-11-21 18:14
 **/
@Component
public class Job58InfoProvider extends AbstractThirdInfoProvider {

    @Autowired
    private Environment env;

    @Autowired
    private DictFeatureJob58Dao dictFeatureJob58Dao;

    @Autowired
    private Job58RequestHandler<Job58AddressRequestDTO> requestHandler;

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB58;
    }

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();
        HrThirdPartyAccountDO hrThirdPartyAccountDO = hrThirdPartyAccountDao.getAccountById(accountId);
        JSONObject obj=new JSONObject();
        // todo 需要处理token过期的情况
        if(!StringUtils.isNullOrEmpty(hrThirdPartyAccountDO.getExt())){
            JSONObject job58UserInfo = JSONObject.parseObject(hrThirdPartyAccountDO.getExt());
            String appKey = env.getProperty("58job_api_app_key");
            String accessToken = job58UserInfo.getString("accessToken");
            String openId = job58UserInfo.getString("openId");
            Job58AddressRequestDTO addressRequestDTO = new Job58AddressRequestDTO(appKey, System.currentTimeMillis(), accessToken, openId);
            JSONObject response = requestHandler.sendRequest(addressRequestDTO, env.getProperty("58job_position_workaddress"));
            if("0".equals(response.getString("code"))){
                Job58AddressVO addressVO = new Job58AddressVO(response.getIntValue("id"), response.getString("address"));
                obj.put(ADDRESS,addressVO);
            }else {
                if("109".equals(response.getString("code"))){
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_58_TOKEN_EXPIRE);
                }
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_58_ADDRESS_FAILED);
            }
        }
        obj.put(FEATURE, getAll58Features());
        return obj.toJSONString();
    }

    /**
     * 获取58所有的福利特色
     * @author  cjm
     * @date  2018/11/23
     * @return id-name list
     */
    private List<Job58FeatureVO> getAll58Features() {
        List<Dict_58jobFeatureRecord> featureRecords = dictFeatureJob58Dao.getAllFeature();
        List<Job58FeatureVO> featureVOS = new ArrayList<>();
        featureRecords.forEach(record -> featureVOS.add(new Job58FeatureVO(record.getId(), record.getName())));
        return featureVOS;
    }
}
