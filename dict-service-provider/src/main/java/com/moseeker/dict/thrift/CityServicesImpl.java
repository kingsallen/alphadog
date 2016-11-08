package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.CityServices;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices.Iface;


@Service
public class CityServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityServices service; 

    public Response getResources(CommonQuery query) throws TException {
    		return service.getResources(query);
    }

    public Response getCitiesResponse(boolean transform, int level) {
        return service.getCitiesResponse(transform, level);
    }

    // --------------------- new api ----------------------------------

    public Response getAllCities(int level) {
        return service.getAllCities(level);
    }

    public Response getCitiesById(int id) throws TException {
    		return service.getCitiesById(id);
    }

    public Response getCitiesResponseById(int id) {
        return service.getCitiesResponseById(id);
    }
}
