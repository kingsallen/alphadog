package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictFeatureJob58Dao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobFeatureRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.position.job58.Job58FeatureVO;
import com.moseeker.position.constants.position.job58.Job58PositionOperateConstant;
import com.moseeker.position.service.position.job58.Job58RequestHandler;
import com.moseeker.position.service.position.job58.dto.Job58AddressRequestDTO;
import com.moseeker.position.service.position.job58.vo.Job58AddressVO;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DictFeatureJob58Dao dictFeatureJob58Dao;

    @Autowired
    private Job58RequestHandler<Job58AddressRequestDTO> requestHandler;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

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
            String accessToken = job58UserInfo.getString("accessToken");
            String openId = job58UserInfo.getString("openId");
            Job58AddressRequestDTO addressRequestDTO = new Job58AddressRequestDTO(Job58PositionOperateConstant.job58AppKey, System.currentTimeMillis(), accessToken, openId);
            JSONObject response = requestHandler.sendRequest(addressRequestDTO, Job58PositionOperateConstant.job58PositionAddress);
            if("0".equals(response.getString("code"))){
                List<Job58AddressVO> addressVOS = new ArrayList<>();
                String addressData = response.getString("data");
                JSONArray addressArr = JSON.parseArray(addressData);
                if(addressArr != null && addressArr.size() > 0){
                    for (Object anAddressArr : addressArr) {
                        JSONObject address = JSONObject.parseObject(JSON.toJSONString(anAddressArr));
                        Job58AddressVO addressVO = new Job58AddressVO(address.getIntValue("id"), address.getString("address"));
                        addressVOS.add(addressVO);
                    }
                }
                obj.put(ADDRESS,addressVOS);
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

    @Override
    public HrThirdPartyAccountDO getThirdPartyAccountBindResult(int hrId, HrThirdPartyAccountDO account) {
        UserHrAccountDO hrAccountDO = userHrAccountDao.getValidAccount(hrId);
        int companyId = hrAccountDO.getCompanyId();
//        return hrThirdPartyAccountDao.getThirdPartyAccountBindResult(hrId, account);
        return null;
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
