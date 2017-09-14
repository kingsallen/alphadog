package com.moseeker.dict.service.impl;


import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryPojo;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 国家字典数据服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictCountryService {

    private static final Logger log = LoggerFactory.getLogger(DictCountryService.class);

    @Autowired
    public DictCountryDao dictCountryDao;

    @CounterIface
    public Response getDictCountry(Query query) throws TException {
        try{
            List<DictCountryPojo> dictConstantList = dictCountryDao.getDatas(query,DictCountryPojo.class);
            return ResponseUtils.success(dictConstantList);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
