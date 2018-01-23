package com.moseeker.position.service.third.info;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.position.service.third.ThirdPartyAccountDepartmentService;
import com.moseeker.position.service.third.base.AbstractThirdInfoProvider;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiePinInfoProvider extends AbstractThirdInfoProvider {
    @Autowired
    ThirdPartyAccountDepartmentService departmentService;

    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    @Override
    public String provide(ThirdPartyAccountInfoParam param) throws Exception{
        int accountId = getThirdPartyAccount(param).getThirdPartyAccountId();

        JSONObject obj=new JSONObject();
        obj.put(DEPARTMENT,departmentService.getDepartmentByAccountId(accountId));

        return StructSerializer.toString(obj);
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.LIEPIN;
    }
}
