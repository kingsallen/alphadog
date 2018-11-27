package com.moseeker.dict.thrift;

import com.moseeker.dict.service.impl.ReferralEvaluateService;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictReferralEvaluateDO;
import com.moseeker.thrift.gen.dict.service.DictReferralEvaluateService;
import java.util.List;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 常量字典服务
 * <p>
 *
 * Created by zzh on 16/5/26.
 */
@Service
public class DictReferralEvaluateServiceImpl implements DictReferralEvaluateService.Iface {

    @Autowired
    public ReferralEvaluateService evaluateService;


    @Override
    public List<DictReferralEvaluateDO> getDictReferralEvalute(int code) throws TException {
        return evaluateService.getReferralEvaluate(code);
    }
}
