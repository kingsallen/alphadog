package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictCountryService;

/**
 * 国家字典数据服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictCountryServiceImpl implements DictCountryService.Iface {

    @Autowired
    public com.moseeker.dict.service.impl.DictCountryService service;

    @Override
    public Response getDictCountry() throws TException {
        return service.getDictCountry();
    }
}
