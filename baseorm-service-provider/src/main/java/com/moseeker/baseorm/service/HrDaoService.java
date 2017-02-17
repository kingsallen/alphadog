package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;

import java.util.List;

public interface HrDaoService {

    HrHbConfigDO getHbConfig(CommonQuery query) throws TException;
    
    List<HrHbConfigDO> getHbConfigs(CommonQuery query) throws TException;

    HrHbPositionBindingDO getHbPositionBinding(CommonQuery query) throws TException;

    List<HrHbPositionBindingDO> getHbPositionBindings(CommonQuery query) throws TException;

    HrHbItemsDO getHbItem(CommonQuery query) throws TException;

    List<HrHbItemsDO> getHbItems(CommonQuery query) throws TException;

    HrHbScratchCardDO getHbScratchCard(CommonQuery query) throws TException;

    HrHbSendRecordDO getHbSendRecord(CommonQuery query) throws TException;

    HrEmployeeCertConfDO getEmployeeCertConf(CommonQuery query) throws TException;

    List<HrPointsConfDO> getPointsConfs(CommonQuery query) throws TException;

    List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(CommonQuery query) throws TException;
}
