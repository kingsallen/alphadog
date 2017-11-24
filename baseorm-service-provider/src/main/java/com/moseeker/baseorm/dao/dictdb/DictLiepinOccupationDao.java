package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictLiepinOccupation;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictLiepinOccupationRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DictLiepinOccupationDao extends JooqCrudImpl<DictLiepinOccupationDO, DictLiepinOccupationRecord> {

    public DictLiepinOccupationDao() {
        super(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION, DictLiepinOccupationDO.class);
    }

    public DictLiepinOccupationDao(TableImpl<DictLiepinOccupationRecord> table, Class<DictLiepinOccupationDO> dictLiepinOccupationDO) {
        super(table, dictLiepinOccupationDO);
    }

    public List<DictLiepinOccupationDO> getAllOccupation(){
        Query query = new Query.QueryBuilder().where(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.STATUS.getName(), 1).buildQuery();
        return getDatas(query);
    }

    public List<DictLiepinOccupationDO> getSingle(JSONObject obj) {
        Integer level = obj.getInteger("level");
        Integer id = obj.getInteger("code");
        Integer parentId = obj.getInteger("parent_id");
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.STATUS.getName(), 1);
        if (id != null) {
            build.and(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.CODE.getName(), id);
        }
        if (parentId != null) {
            build.and(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.PARENT_ID.getName(), parentId);
        }
        if (level != null) {
            build.and(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.LEVEL.getName(), level);
        }
        return getDatas(build.buildQuery());
    }

    public List<DictLiepinOccupationDO> getFullOccupations(String occupation) {
        List<DictLiepinOccupationDO> fullOccupations = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(occupation)) return fullOccupations;

        String currentField = DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.OTHER_CODE.getName();
        Object currentValue = occupation;

        Query query = null;

        DictLiepinOccupationDO dictLiepinOccupationDO;

        for (int i = 0; i < 4; i++) {

            query = new Query.QueryBuilder().where(currentField, currentValue).buildQuery();

            dictLiepinOccupationDO = getData(query);

            if (dictLiepinOccupationDO == null) {
                break;
            } else {
                fullOccupations.add(0, dictLiepinOccupationDO);
                if (dictLiepinOccupationDO.getParentId() == 0) {
                    break;
                }
                currentField = DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.CODE.getName();
                currentValue = dictLiepinOccupationDO.getParentId();
            }
        }

        return fullOccupations;

    }
}
