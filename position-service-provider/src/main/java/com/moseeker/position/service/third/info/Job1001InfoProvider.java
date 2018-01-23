package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.service.third.ThirdPartyAccountAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountCompanyService;
import com.moseeker.position.service.third.ThirdpartyAccountSubsiteService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Job1001InfoProvider extends AbstractThirdInfoProvider {
    Logger logger= LoggerFactory.getLogger(Job1001InfoProvider.class);

    private static final String SUBSITE="subsite";

    @Autowired
    ThirdPartyAccountCompanyService companyService;
    @Autowired
    ThirdPartyAccountAddressService addressService;
    @Autowired
    ThirdpartyAccountSubsiteService subsiteService;

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB1001;
    }

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put(COMPANY,companyService.getCompanyByAccountId(accountId));
        obj.put(ADDRESS,addressService.getCompanyAddressByAccountId(accountId));
        obj.put(SUBSITE,subsiteService.getSubsiteByAccountId(accountId));


        return StructSerializer.toString(obj);
    }
}
