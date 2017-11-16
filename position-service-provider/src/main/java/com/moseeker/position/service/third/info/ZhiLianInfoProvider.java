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
public class ZhiLianInfoProvider extends AbstractThirdInfoProvider {
    @Autowired
    ThirdPartyAccountInfoCompanyService companyService;
    @Autowired
    ThirdPartyAccountInfoAddressService addressService;

    @Override
    public ChannelType getChannel() {
        return ChannelType.ZHILIAN;
    }

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put("company",companyService.getInfoCompany(accountId));
        obj.put("address",addressService.getInfoCompanyAddress(accountId));

        return obj.toJSONString();
    }
}
