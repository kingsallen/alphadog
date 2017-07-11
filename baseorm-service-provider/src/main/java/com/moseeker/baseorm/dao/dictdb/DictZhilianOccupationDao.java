package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxx
 *         DictZhilianOccupationDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class DictZhilianOccupationDao extends JooqCrudImpl<DictZhilianOccupationDO, DictZhilianOccupationRecord> {

    public DictZhilianOccupationDao() {
        super(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION, DictZhilianOccupationDO.class);
    }

    public DictZhilianOccupationDao(TableImpl<DictZhilianOccupationRecord> table, Class<DictZhilianOccupationDO> dictZhilianOccupationDOClass) {
        super(table, dictZhilianOccupationDOClass);
    }

    public List<DictZhilianOccupationDO> getFullOccupations(String occupation) {
        List<DictZhilianOccupationDO> fullOccupations = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(occupation)) return fullOccupations;

        String currentField = DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE_OTHER.getName();
        Object currentValue = occupation;

        Query query = null;

        DictZhilianOccupationDO dictZhilianOccupationDO;

        for (int i = 0; i < 4; i++) {

            query = new Query.QueryBuilder().where(currentField, currentValue).buildQuery();

            dictZhilianOccupationDO = getData(query);

            if (dictZhilianOccupationDO == null) {
                break;
            } else {
                fullOccupations.add(0, dictZhilianOccupationDO);
                if (dictZhilianOccupationDO.getParentId() == 0) {
                    break;
                }
                currentField = DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE.getName();
                currentValue = dictZhilianOccupationDO.getParentId();
            }
        }

        return fullOccupations;

    }
}
