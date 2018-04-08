package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxx
 *         DictZhilianOccupationDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class DictZhilianOccupationDao extends AbstractDictOccupationDao<DictZhilianOccupationDO, DictZhilianOccupationRecord> {

    public DictZhilianOccupationDao() {
        super(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION, DictZhilianOccupationDO.class);
    }

    public DictZhilianOccupationDao(TableImpl<DictZhilianOccupationRecord> table, Class<DictZhilianOccupationDO> dictZhilianOccupationDOClass) {
        super(table, dictZhilianOccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> map=new HashMap<>();
        map.put(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE.getName(), obj.getIntValue("code"));
        map.put(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.PARENT_ID.getName(), obj.getIntValue("parent_id"));
        map.put(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.LEVEL.getName(), obj.getIntValue("level"));
        return map;
    }


    @Override
    protected boolean isTopOccupation(DictZhilianOccupationDO dictZhilianOccupationDO) {
        return dictZhilianOccupationDO!=null && dictZhilianOccupationDO.getParentId()==0;
    }

    @Override
    protected Condition conditionToSearchFather(DictZhilianOccupationDO dictZhilianOccupationDO) {
        return new Condition(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE.getName(),dictZhilianOccupationDO.getParentId());
    }

    public int deleteAll(){
        Condition condition=new Condition(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE.getName(),0, ValueOp.NEQ);
        return delete(condition);
    }

    @Override
    protected String otherCodeName() {
        return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE_OTHER.getName();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
