package com.moseeker.dict.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.dao.DictCountryDao;
import com.moseeker.dict.pojo.DictCountryPojo;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 国家字典数据服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictCountryService {

    @Autowired
    public DictCountryDao dictCountryDao;

    @CounterIface
    public Response getDictCountry() throws TException {
        try{
            List<DictCountryPojo> dictConstantList = dictCountryDao.getDictCountry();
            return ResponseUtils.success(dictConstantList);
        }catch (Exception e){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
