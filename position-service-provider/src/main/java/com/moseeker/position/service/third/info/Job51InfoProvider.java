package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.third.ThirdPartyAccountInfoAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountInfoCompanyService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Job51InfoProvider extends AbstractThirdInfoProvider {
    @Autowired
    ThirdPartyAccountInfoCompanyService companyService;
    @Autowired
    ThirdPartyAccountInfoAddressService addressService;

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put("address",addressService.getInfoCompanyAddress(accountId));
        obj.put("company",companyService.getInfoCompany(accountId));

        return obj.toJSONString();
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB51;
    }
}
