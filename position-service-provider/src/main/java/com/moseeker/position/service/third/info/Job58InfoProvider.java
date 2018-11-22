package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.third.ThirdPartyAccountAddressService;
import com.moseeker.position.service.third.ThirdPartyAccountCompanyService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cjm
 * @date 2018-11-21 18:14
 **/
@Component
public class Job58InfoProvider extends AbstractThirdInfoProvider {

    @Autowired
    private ThirdPartyAccountCompanyService companyService;

    @Autowired
    private ThirdPartyAccountAddressService addressService;

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB58;
    }

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception {
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        // todo 工作地址根据用户id去请求58
        obj.put(ADDRESS,addressService.getCompanyAddressByAccountId(accountId));
        // todo 查库
        obj.put(FEATURE,companyService.getCompanyByAccountId(accountId));
        return obj.toJSONString();
    }
}
