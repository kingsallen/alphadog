package com.moseeker.dict.service.impl;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.dao.DictCountryDao;
import com.moseeker.dict.pojo.DictCountryPojo;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictCountryService;
import org.apache.thrift.TException;
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
public class DictCountryServiceImpl implements DictCountryService.Iface {

    @Autowired
    public DictCountryDao dictCountryDao;

    @Override
    public Response getDictCountry() throws TException {
        try{
            List<DictCountryPojo> dictConstantList = dictCountryDao.getDictCountry();
            return ResponseUtils.success(dictConstantList);
        }catch (Exception e){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
