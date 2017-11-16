package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Dict51OccupationDao extends JooqCrudImpl<Dict51jobOccupationDO, Dict_51jobOccupationRecord> {

    public Dict51OccupationDao() {
        super(Dict_51jobOccupation.DICT_51JOB_OCCUPATION, Dict51jobOccupationDO.class);
    }

    public Dict51OccupationDao(TableImpl<Dict_51jobOccupationRecord> table, Class<Dict51jobOccupationDO> dict51jobOccupationDOClass) {
        super(table, dict51jobOccupationDOClass);
    }

    public List<Dict51jobOccupationDO> getAllOccupation(){
        Query query = new Query.QueryBuilder()
                .where(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.STATUS.getName(), 1)
                .buildQuery();
        return getDatas(query);
    }


    public List<Dict51jobOccupationDO> getSingle(JSONObject obj) {
        Integer level = obj.getInteger("level");
        Integer id = obj.getInteger("code");
        Integer parentId = obj.getInteger("parent_id");
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.STATUS.getName(), 1);
        if (id != null) {
            build.and(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE.getName(), id);
        }
        if (parentId != null) {
            build.and(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.PARENT_ID.getName(), parentId);
        }
        if (level != null) {
            build.and(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.LEVEL.getName(), level);
        }
        return getDatas(build.buildQuery());
    }

    public List<Dict51jobOccupationDO> getFullOccupations(String occupation) {
        List<Dict51jobOccupationDO> fullOccupations = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(occupation)) return fullOccupations;

        String currentField = Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE_OTHER.getName();
        Object currentValue = occupation;

        Query query = null;

        Dict51jobOccupationDO dict51jobOccupationDO;

        for (int i = 0; i < 4; i++) {

            query = new Query.QueryBuilder().where(currentField, currentValue).buildQuery();

            dict51jobOccupationDO = getData(query);

            if (dict51jobOccupationDO == null) {
                break;
            } else {
                fullOccupations.add(0, dict51jobOccupationDO);
                if (dict51jobOccupationDO.getParentId() == 0) {
                    break;
                }
                currentField = Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE.getName();
                currentValue = dict51jobOccupationDO.getParentId();
            }
        }

        return fullOccupations;

    }
}
