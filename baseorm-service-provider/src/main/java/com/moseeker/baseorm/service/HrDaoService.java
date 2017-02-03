package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbItemsPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbPositionBindingPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbScratchCardPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbSendRecordPojo;
import org.apache.thrift.TException;

import java.util.List;

public interface HrDaoService {

    HrHbConfigPojo getHbConfig(CommonQuery query) throws TException;
    
    List<HrHbConfigPojo> getHbConfigs(CommonQuery query) throws TException;

    HrHbPositionBindingPojo getHbPositionBinding(CommonQuery query) throws TException;

    List<HrHbPositionBindingPojo> getHbPositionBindings(CommonQuery query) throws TException;

    HrHbItemsPojo getHbItem(CommonQuery query) throws TException;

    List<HrHbItemsPojo> getHbItems(CommonQuery query) throws TException;

    HrHbScratchCardPojo getHbScratchCard(CommonQuery query) throws TException;

    HrHbSendRecordPojo getHbSendRecord(CommonQuery query) throws TException;
    
}
