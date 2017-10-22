package com.moseeker.position.thrift;

import com.moseeker.position.service.third.ThirdPartyAccountInfoService;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import com.moseeker.thrift.gen.thirdpart.service.ThirdPartyAccountInfoService.Iface;

import java.util.List;

public class ThirdPartyAccountInfoServiceImpl implements Iface {

    @Autowired
    ThirdPartyAccountInfoService service;


    @Override
    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws TException {
        return service.getAllInfo(param);
    }

    @Override
    public List<ThirdpartyAccountCityDO> getCity(ThirdPartyAccountInfoParam param) throws TException {
        return service.getCity(service.getThirdPartyAccount(param).getThirdPartyAccountId());
    }

    @Override
    public List<ThirdpartyAccountCompanyAddressDO> getCompanyAddress(ThirdPartyAccountInfoParam param) throws TException {
        return service.getCompanyAddress(service.getThirdPartyAccount(param).getThirdPartyAccountId());
    }

    @Override
    public List<ThirdpartyAccountCompanyDO> getCompany(ThirdPartyAccountInfoParam param) throws TException {
        return service.getCompany(service.getThirdPartyAccount(param).getThirdPartyAccountId());
    }

    @Override
    public List<ThirdpartyAccountDepartmentDO> getDepartment(ThirdPartyAccountInfoParam param) throws TException {
        return service.getDepartment(service.getThirdPartyAccount(param).getThirdPartyAccountId());
    }


}
