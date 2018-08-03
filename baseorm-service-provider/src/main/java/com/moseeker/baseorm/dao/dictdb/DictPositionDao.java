package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* DictPositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictPositionDao extends JooqCrudImpl<DictPositionDO, DictPositionRecord> {

    public DictPositionDao() {
        super(DictPosition.DICT_POSITION, DictPositionDO.class);
    }

    public DictPositionDao(TableImpl<DictPositionRecord> table, Class<DictPositionDO> dictPositionDOClass) {
        super(table, dictPositionDOClass);
    }

    public List<DictPositionRecord> getIndustriesByParentCode(int parentCode) {
        return create.selectFrom(DictPosition.DICT_POSITION)
                .where(DictPosition.DICT_POSITION.PARENT.equal(parentCode)).fetch();
    }

    public List<DictPositionRecord> getPositionsByCodes(List<Integer> positionCodes) {
        List<DictPositionRecord> records = new ArrayList<>();
        if(positionCodes != null && positionCodes.size() > 0) {
            SelectWhereStep<DictPositionRecord> select = create.selectFrom(DictPosition.DICT_POSITION);
            SelectConditionStep<DictPositionRecord> selectCondition = null;
            for(int i=0; i<positionCodes.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(DictPosition.DICT_POSITION.CODE.equal((int)(positionCodes.get(i))));
                } else {
                    selectCondition.or(DictPosition.DICT_POSITION.CODE.equal((int)(positionCodes.get(i))));
                }
            }
            records = selectCondition.fetch();
        }
        return records;
    }

    public DictPositionRecord getPositionByCode(int positionCode) {
        DictPositionRecord record = null;
        if(positionCode > 0) {
            Result<DictPositionRecord> result = create.selectFrom(DictPosition.DICT_POSITION)
                    .where(DictPosition.DICT_POSITION.CODE.equal((int)(positionCode)))
                    .limit(1).fetch();
            if(result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }

    /**
     * 根据职能名称查找只能数据
     * @param positionNameList 只能名称列表
     * @return 职能数据
     */
    public List<DictPositionDO> getByNameList(List<String> positionNameList) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder
                .where(new com.moseeker.common.util.query.Condition(
                        DictPosition.DICT_POSITION.NAME.getName(), positionNameList, ValueOp.IN));
        return getDatas(queryBuilder.buildQuery());
    }
}
