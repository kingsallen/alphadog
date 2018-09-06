package com.moseeker.dict.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.CityServices;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices.Iface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CityServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityServices service; 

    public Response getResources(CommonQuery query) throws TException {
    		return service.getResources(query);
    }

    @Override
    public Response getProvinceAndCity() throws TException {
        try{
            Map<String,Object> result=service.getProvinceCity();
            if(StringUtils.isEmptyMap(result)){
                return ResponseUtils.success(new HashMap<>());
            }
            return ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCityByProvince(List<Integer> codeList) throws TException {
        try{
            return service.getCityCodeByProvine(codeList);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
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

    @Override
    public Response getAllCitiesByLevelOrUsing(String level, int is_using, int hot_city) throws TException {
        return service.getAllCitiesByLevelOrUsing(level, is_using, hot_city);
    }

    public Response getCitiesResponseById(int id) {
        return service.getCitiesResponseById(id);
    }
}
