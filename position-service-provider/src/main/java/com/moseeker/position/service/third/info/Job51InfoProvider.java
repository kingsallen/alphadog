package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.service.third.ThirdPartyAccountAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountCompanyService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Job51InfoProvider extends AbstractThirdInfoProvider {
    @Autowired
    ThirdPartyAccountCompanyService companyService;
    @Autowired
    ThirdPartyAccountAddressService addressService;

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put(ADDRESS,addressService.getCompanyAddressByAccountId(accountId));
        obj.put(COMPANY,companyService.getCompanyByAccountId(accountId));

        return obj.toJSONString();
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB51;
    }
}
