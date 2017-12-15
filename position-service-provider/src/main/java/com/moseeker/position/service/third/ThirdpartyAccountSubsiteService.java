package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.thirdpartydb.ThirdPartyAccountJob1001SubsiteDao;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountJob1001SubsiteDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdpartyAccountSubsiteService {
    private static Logger logger= LoggerFactory.getLogger(ThirdPartyAccountDepartmentService.class);
    @Autowired
    ThirdPartyAccountJob1001SubsiteDao subsiteDao;

    //把ThirdpartyAccountDepartmentDO转换成传给前台的类型ThirdPartyAccountInfoDepartment
    public List<ThirdpartyAccountJob1001SubsiteDO> getSubsiteByAccountId(int accountId) throws TException {
        List<ThirdpartyAccountJob1001SubsiteDO> subsiteList=subsiteDao.getAddressByAccountId(accountId);
        logger.info("infoSubsiteList: {}",subsiteList);
        return subsiteList;
    }
}
