package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.service.Impl.DictDaoServiceImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dict.service.DictOccupationDao.Iface;

@Service
public class DictDaoThriftService implements Iface {

    @Autowired
    private DictDaoServiceImpl dictService;

    @Autowired
    private DictCityDao dictCityDao;

    @Override
    public Response getOccupations51() throws TException {
        // TODO Auto-generated method stub
        return dictService.occupations51();
    }

    @Override
    public Response getOccupationsZPin() throws TException {
        // TODO Auto-generated method stub
        return dictService.occupationsZPin();
    }

    @Override
    public Response getOccupation51(CommonQuery query) throws TException {
        // TODO Auto-generated method stub
        return dictService.occupation51(query);
    }

    @Override
    public Response getOccupationZPin(CommonQuery query) throws TException {
        // TODO Auto-generated method stub
        return dictService.occupationZPin(query);
    }

    @Override
    public DictCityDO dictCityDO(CommonQuery query) throws TException {
        return dictCityDao.findResource(query);
    }
}
