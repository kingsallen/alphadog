package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.thirdpartydb.ThirdPartyAccountSubsiteDao;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountSubsiteDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdpartyAccountSubsiteService {
    private static Logger logger= LoggerFactory.getLogger(ThirdPartyAccountDepartmentService.class);
    @Autowired
    ThirdPartyAccountSubsiteDao subsiteDao;

    //把ThirdpartyAccountDepartmentDO转换成传给前台的类型ThirdPartyAccountInfoDepartment
    public List<ThirdpartyAccountSubsiteDO> getSubsiteByAccountId(int accountId) throws TException {
        List<ThirdpartyAccountSubsiteDO> subsiteList=subsiteDao.getAddressByAccountId(accountId);
        logger.info("infoDepartmentList: {}",subsiteList);
        return subsiteList;
    }
}
