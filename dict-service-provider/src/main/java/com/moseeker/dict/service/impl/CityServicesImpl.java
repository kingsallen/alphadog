package com.moseeker.dict.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.BeanUtils;

import com.moseeker.thrift.gen.dict.struct.City;
import com.moseeker.thrift.gen.dict.service.CityServices.Iface;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;

@Service
public class CityServicesImpl extends JOOQBaseServiceImpl<City, DictCityRecord> implements Iface {

    @Autowired
    protected CityDao dao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected City DBToStruct(DictCityRecord r) {
        return (City) BeanUtils.DBToStruct(City.class, r);
    }

    @Override
    protected DictCityRecord structToDB(City c) {
        return (DictCityRecord)BeanUtils.structToDB(c, DictCityRecord.class);
    }

}
