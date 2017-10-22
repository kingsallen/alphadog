package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ThirdPartyAccountInfoService {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdpartyAccountCityDao cityDao;
    @Autowired
    ThirdpartyAccountCompanyDao companyDao;
    @Autowired
    ThirdpartyAccountCompanyAddressDao addressDao;
    @Autowired
    ThirdpartyAccountDepartmentDao departmentDao;

    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws TException {

        int accountId=getThirdPartyAccount(param).getThirdPartyAccountId();

        ThirdPartyAccountInfo info=new ThirdPartyAccountInfo();

        info.setCity(getCity(accountId));
        info.setAddress(getCompanyAddress(accountId));
        info.setCompany(getCompany(accountId));
        info.setDepartment(getDepartment(accountId));

        return info;
    }

    public HrThirdPartyAccountHrDO getThirdPartyAccount(ThirdPartyAccountInfoParam param) throws TException{
        HrThirdPartyAccountHrDO hrThirdPartyAccountHrDO=hrThirdPartyAccountHrDao.getData(param.getHr_id(),param.getChannel());
        if(hrThirdPartyAccountHrDO == null || hrThirdPartyAccountHrDO.getId() == 0){
            throw new BIZException(1,param.getHr_id()+"账号在渠道"+param.getChannel()+"没有绑定的第三方账号");
        }
        return hrThirdPartyAccountHrDO;
    }



    public List<ThirdpartyAccountCityDO> getCity(int accountId) throws TException {
        List<ThirdpartyAccountCityDO> list=cityDao.getCityByAccountId(accountId);
        return list;
    }



    public List<ThirdpartyAccountCompanyAddressDO> getCompanyAddress(int accountId) throws TException {
        List<ThirdpartyAccountCompanyAddressDO> list=addressDao.getAddressByAccountId(accountId);
        return list;
    }



    public List<ThirdpartyAccountCompanyDO> getCompany(int accountId) throws TException {
        List<ThirdpartyAccountCompanyDO> list=companyDao.getCompanyByAccountId(accountId);
        return list;
    }



    public List<ThirdpartyAccountDepartmentDO> getDepartment(int accountId) throws TException {
        List<ThirdpartyAccountDepartmentDO> list=departmentDao.getDepartmentByAccountId(accountId);
        return list;
    }
}
