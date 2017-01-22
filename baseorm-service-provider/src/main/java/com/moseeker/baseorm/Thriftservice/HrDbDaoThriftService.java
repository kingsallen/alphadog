package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.HrDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbItemsPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbPositionBindingPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbScratchCardPojo;
import com.moseeker.thrift.gen.dao.struct.HrHbSendRecordPojo;

@Service
public class HrDbDaoThriftService implements Iface {

    @Autowired
    private HrDaoService hrDaoService;


    @Override
    public HrHbConfigPojo getHbConfig(CommonQuery query) throws TException {
        return hrDaoService.getHbConfig(query);
    }

    @Override
    public HrHbPositionBindingPojo getHbPositionBinding(CommonQuery query) throws TException {
        return hrDaoService.getHbPositionBinding(query);
    }

    @Override
    public HrHbItemsPojo getHbItem(CommonQuery query) throws TException {
        return hrDaoService.getHbItem(query);
    }

    @Override
    public List<HrHbItemsPojo> getHbItems(CommonQuery query) throws TException {
        return hrDaoService.getHbItems(query);
    }

    @Override
    public HrHbScratchCardPojo getHbScratchCard(CommonQuery query) throws TException {
        return hrDaoService.getHbScratchCard(query);
    }

    @Override
    public HrHbSendRecordPojo getHbSendRecord(CommonQuery query) throws TException {
        return hrDaoService.getHbSendRecord(query);
    }
}
