package com.moseeker.dict.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryPojo;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictCountryService;

import java.util.List;
import java.util.Map;

/**
 * 国家字典数据服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictCountryServiceImpl implements DictCountryService.Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public com.moseeker.dict.service.impl.DictCountryService service;


    @Override
    public Response getDictCountry(CommonQuery query) throws TException {
        Response res=service.getDictCountry(QueryConvert.commonQueryConvertToQuery(query));
        return res;

    }
}
