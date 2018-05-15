package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdPartyAccountJob1001SubsiteDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountJob1001SubsiteRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountJob1001SubsiteDO;
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
public class SubsiteInfoHandler extends AbstractInfoHandler<ThirdpartyAccountJob1001SubsiteDO> {
    Logger logger= LoggerFactory.getLogger(SubsiteInfoHandler.class);

    @Autowired
    ThirdPartyAccountJob1001SubsiteDao subsiteDao;

    @Override
    public List<ThirdpartyAccountJob1001SubsiteDO> buildNewData(ThirdPartyInfoData data) {
        if(StringUtils.isEmptyList(data.getSubsites())){
            return Collections.emptyList();
        }

        String currentTime = getCurrentTime();

        List<ThirdpartyAccountJob1001SubsiteDO> subsiteDOList = data.getSubsites()
                .stream()
                .map(subsite -> {
                    ThirdpartyAccountJob1001SubsiteDO subsiteDO=new ThirdpartyAccountJob1001SubsiteDO();
                    subsiteDO.setAccountId(data.getAccountId());
                    subsiteDO.setSite(subsite);
                    subsiteDO.setText(subsite);
                    subsiteDO.setCreateTime(currentTime);
                    subsiteDO.setUpdateTime(currentTime);
                    return subsiteDO;
                }).collect(Collectors.toList());
        logger.info("subsiteDOList : {}",subsiteDOList);

        return subsiteDOList;
    }

    @Override
    public boolean equals(ThirdpartyAccountJob1001SubsiteDO data1, ThirdpartyAccountJob1001SubsiteDO data2) {
        if(data1 == null || data2==null){
            return false;
        }
        return data1.getSite().equals(data2.getSite());
    }

    @Override
    protected ThirdPartyAccountJob1001SubsiteDao getDao() {
        return subsiteDao;
    }
}
