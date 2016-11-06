package com.moseeker.dict.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.DictConstantService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;

/**
 * 常量字典服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictConstantServiceImpl implements DictConstanService.Iface {

    @Autowired
    public DictConstantService service;

    /**
     * 取得常量字典json数据
     * <p>
     *
     * */
    @Override
    public Response getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws TException {
        return service.getDictConstantJsonByParentCode(parentCodeList);
    }
}
