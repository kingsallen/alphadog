package com.moseeker.dict.service.impl;

import com.moseeker.baseorm.dao.dictdb.DictReferralEvaluateDao;
import com.moseeker.baseorm.db.dictdb.tables.records.DictReferralEvaluateRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.dict.enums.ReferralEaluateType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictReferralEvaluateDO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2018/11/26.
 */
@Service
public class DictReferralEvaluateService {

    @Autowired
    DictReferralEvaluateDao evaluateDao;

    public List<DictReferralEvaluateDO> getReferralEvaluate(int code) {
        ReferralEaluateType type = ReferralEaluateType.instanceFromByte(code);
        List<DictReferralEvaluateRecord> list = evaluateDao.getDictReferralEvaluate();
        if(!StringUtils.isEmptyList(list)){
            List<DictReferralEvaluateDO> evaluateDOS = list.stream()
                    .filter(ft -> ft.getCode() == (ft.getCode()|type.getValue()))
                    .map(m -> {
                        DictReferralEvaluateDO evaluateDO = new DictReferralEvaluateDO();
                        evaluateDO.setId(m.getId());
                        evaluateDO.setCode(m.getCode());
                        evaluateDO.setTag(m.getTag());
                        evaluateDO.setTagEn(m.getTagEn());
                        return evaluateDO;
                    }).collect(Collectors.toList());
            return evaluateDOS;
        }
        return new ArrayList<>();
    }
}
