package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractInfoHandler;
import org.jooq.UpdatableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentInfoHandler extends AbstractInfoHandler<ThirdpartyAccountDepartmentDO> {
    Logger logger = LoggerFactory.getLogger(DepartmentInfoHandler.class);

    @Autowired
    ThirdpartyAccountDepartmentDao departmentDao;

    @Override
    public List<ThirdpartyAccountDepartmentDO> buildNewData(ThirdPartyInfoData data) {
        if (StringUtils.isEmptyList(data.getDepartments())) {
            return Collections.emptyList();
        }

        String currentTime = getCurrentTime();

        List<ThirdpartyAccountDepartmentDO> departmentDOList = data.getDepartments()
                .stream()
                .map(department -> {
                    ThirdpartyAccountDepartmentDO departmentDO = new ThirdpartyAccountDepartmentDO();
                    departmentDO.setAccountId(data.getAccountId());
                    departmentDO.setDepartmentName(department);
                    departmentDO.setCreateTime(currentTime);
                    departmentDO.setUpdateTime(currentTime);
                    return departmentDO;
                }).collect(Collectors.toList());
        logger.info("departmentDOList : {}",departmentDOList);

        return departmentDOList;
    }

    @Override
    public boolean equals(ThirdpartyAccountDepartmentDO data1, ThirdpartyAccountDepartmentDO data2) {
        return data1.getDepartmentName().equals(data2.getDepartmentName());
    }

    @Override
    protected <R extends UpdatableRecord<R>> JooqCrudImpl<ThirdpartyAccountDepartmentDO, R> getDao() {
        return null;
    }
}
