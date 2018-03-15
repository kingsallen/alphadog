package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractInfoHandler;
import org.jooq.UpdatableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyInfoHandler extends AbstractInfoHandler<ThirdpartyAccountCompanyDO>{
    Logger logger = LoggerFactory.getLogger(CompanyInfoHandler.class);

    @Autowired
    ThirdpartyAccountCompanyDao accountCompanyDao;

    @Override
    public List<ThirdpartyAccountCompanyDO> buildNewData(ThirdPartyInfoData data) {
        if (StringUtils.isEmptyList(data.getCompanies())) {
            return Collections.emptyList();
        }

        String currentTime = getCurrentTime();

        List<ThirdpartyAccountCompanyDO> companyDOList = data.getCompanies()
                .stream()
                .map(company -> {
                    ThirdpartyAccountCompanyDO companyDO = new ThirdpartyAccountCompanyDO();
                    companyDO.setAccountId(data.getAccountId());
                    companyDO.setCompanyName(company);
                    companyDO.setCreateTime(currentTime);
                    companyDO.setUpdateTime(currentTime);
                    return companyDO;
                }).collect(Collectors.toList());
        logger.info("companyDOList: {}",companyDOList);

        return companyDOList;
    }

    @Override
    public boolean equals(ThirdpartyAccountCompanyDO data1, ThirdpartyAccountCompanyDO data2) {
        return data1.getCompanyName().equals(data2.getCompanyName());
    }

    @Override
    protected ThirdpartyAccountCompanyDao getDao() {
        return accountCompanyDao;
    }
}
