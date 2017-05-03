package com.moseeker.baseorm.service;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import org.apache.thrift.TException;

import java.util.List;

public interface HrDaoService {

    HrHbConfigDO getHbConfig(Query query) throws TException;
    
    List<HrHbConfigDO> getHbConfigs(Query query) throws TException;

    HrHbPositionBindingDO getHbPositionBinding(Query query) throws TException;

    List<HrHbPositionBindingDO> getHbPositionBindings(Query query) throws TException;

    HrHbItemsDO getHbItem(Query query) throws TException;

    List<HrHbItemsDO> getHbItems(Query query) throws TException;

    HrHbScratchCardDO getHbScratchCard(Query query) throws TException;

    HrHbSendRecordDO getHbSendRecord(Query query) throws TException;

    HrEmployeeCertConfDO getEmployeeCertConf(Query query) throws TException;

    List<HrPointsConfDO> getPointsConfs(Query query) throws TException;

    List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(Query query) throws TException;

    List<HrCompanyAccountDO> getHrCompanyAccounts(Query query) throws TException;
}
