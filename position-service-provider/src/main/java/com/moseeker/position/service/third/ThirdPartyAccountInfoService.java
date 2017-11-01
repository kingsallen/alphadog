package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyAccountDepartment;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.thrift.gen.thirdpart.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAccountInfoService {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdPartyAccountInfoCityService cityService;
    @Autowired
    ThirdPartyAccountInfoCompanyService companyService;
    @Autowired
    ThirdPartyAccountInfoAddressService addressService;
    @Autowired
    ThirdPartyAccountInfoDepartmentService departmentService;



    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    public ThirdPartyAccountInfo getAllInfo(ThirdPartyAccountInfoParam param) throws Exception {

        int accountId=getThirdPartyAccount(param).getThirdPartyAccountId();

        ThirdPartyAccountInfo info=new ThirdPartyAccountInfo();
        info.setCity(cityService.getInfoCity(accountId));
        info.setAddress(addressService.getInfoCompanyAddress(accountId));
        info.setCompany(companyService.getInfoCompany(accountId));
        info.setDepartment(departmentService.getInfoDepartment(accountId));

        return info;
    }

    /**
     * 获取HR账号在某个渠道下的第三方账
     * @param param
     * @return
     * @throws TException 当没有获取HR账号在某个渠道下的第三方账号时，抛出异常
     */
    public HrThirdPartyAccountHrDO getThirdPartyAccount(ThirdPartyAccountInfoParam param) throws Exception{
        long hrId=param.getHrId();
        int channel=param.getChannel();

        logger.info("获取"+hrId+"HR在渠道"+channel+"下的第三方账号");
        HrThirdPartyAccountHrDO hrThirdPartyAccountHrDO=hrThirdPartyAccountHrDao.getData(hrId,channel);

        if(hrThirdPartyAccountHrDO == null || hrThirdPartyAccountHrDO.getId() == 0){
            throw new BIZException(ConstantErrorCodeMessage.NO_BIND_THIRD_PARTY_ACCOUNT_STATUS,param.getHrId()+"账号在渠道"+param.getChannel()+"没有绑定的第三方账号");
        }

        logger.info("获取到的第三方账号为:"+hrThirdPartyAccountHrDO.getThirdPartyAccountId());
        return hrThirdPartyAccountHrDO;
    }











}
