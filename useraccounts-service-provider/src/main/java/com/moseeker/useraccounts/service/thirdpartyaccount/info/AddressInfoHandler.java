package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractInfoHandler;
import org.jooq.UpdatableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressInfoHandler extends AbstractInfoHandler<ThirdpartyAccountCompanyAddressDO>{
    Logger logger= LoggerFactory.getLogger(AddressInfoHandler.class);

    @Autowired
    ThirdpartyAccountCompanyAddressDao accountCompanyAddressDao;

    @Override
    public List<ThirdpartyAccountCompanyAddressDO> buildNewData(ThirdPartyInfoData data) {
        if (StringUtils.isEmptyList(data.getAddresses())) {
            return Collections.emptyList();
        }

        String currentTime = getCurrentTime();

        List<ThirdpartyAccountCompanyAddressDO> addressDOList = data.getAddresses()
                .stream()
                .map(address -> {
                    ThirdpartyAccountCompanyAddressDO addressDO = new ThirdpartyAccountCompanyAddressDO();
                    addressDO.setAccountId(data.getAccountId());
                    addressDO.setAddress(address.getAddress());
                    addressDO.setCity(address.getCity());
                    addressDO.setCreateTime(currentTime);
                    addressDO.setUpdateTime(currentTime);
                    return addressDO;
                }).collect(Collectors.toList());
        logger.info("addressDOList : {}",addressDOList);

        return addressDOList;
    }

    @Override
    public boolean equals(ThirdpartyAccountCompanyAddressDO data1, ThirdpartyAccountCompanyAddressDO data2) {
        return data1.getAddress().equals(data2.getAddress())
                && data1.getCity().equals(data2.getCity());
    }

    @Override
    protected ThirdpartyAccountCompanyAddressDao getDao() {
        return accountCompanyAddressDao;
    }
}
