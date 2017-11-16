package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.third.ThirdPartyAccountInfoDepartmentService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiePinInfoProvider extends AbstractThirdInfoProvider {
    @Autowired
    ThirdPartyAccountInfoDepartmentService departmentService;

    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception{
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put("department",departmentService.getInfoDepartment(accountId));

        return obj.toJSONString();
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.LIEPIN;
    }
}
