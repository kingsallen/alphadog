package com.moseeker.dict.service.impl;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.dao.DictConstantDao;
import com.moseeker.dict.pojo.DictConstantPojo;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 取得常量字典json数据
     * <p>
     *
     * */
    @Override
    public Response getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws TException {
        try{
            Map<Integer, List<DictConstantPojo>> dictConstantMap = dictConstantDao.getDictConstantJsonByParentCode(parentCodeList);
            return ResponseUtils.success(dictConstantMap);
        }catch (Exception e){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
