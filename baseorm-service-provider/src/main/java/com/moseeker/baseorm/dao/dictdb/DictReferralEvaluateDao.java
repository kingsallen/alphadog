package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.DictReferralEvaluate;
import static com.moseeker.baseorm.db.dictdb.tables.DictReferralEvaluate.DICT_REFERRAL_EVALUATE;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictReferralEvaluateRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictReferralEvaluateDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.*;
import static org.jooq.impl.DSL.using;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictConstantDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictReferralEvaluateDao extends JooqCrudImpl<DictReferralEvaluateDO, DictReferralEvaluateRecord> {

    public DictReferralEvaluateDao() {
        super(DICT_REFERRAL_EVALUATE, DictReferralEvaluateDO.class);
    }

    public DictReferralEvaluateDao(TableImpl<DictReferralEvaluateRecord> table, Class<DictReferralEvaluateDO> dictReferralEvaluateDOClass) {
        super(table, dictReferralEvaluateDOClass);
    }


    public List<DictReferralEvaluateRecord> getDictReferralEvaluate() {
        List<DictReferralEvaluateRecord> records = create.selectFrom(DICT_REFERRAL_EVALUATE)
                .fetch();
        return records;
    }

}
