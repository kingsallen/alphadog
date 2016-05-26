package com.moseeker.dict.service.impl;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.dao.DictConstantDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 常量字典服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictConstantServiceImpl implements DictConstanService.Iface {

    @Autowired
    public DictConstantDao dictConstantDao;

    @Override
    public Response getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws TException {
        try{
            String dictConstantJson = dictConstantDao.getDictConstantJsonByParentCode(parentCodeList);
            ResponseUtils.success(dictConstantJson);
        }catch (Exception e){

        }
        return null;
    }
}
