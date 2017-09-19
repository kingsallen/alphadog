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

    public List getAll() {
        List<Map<String, Object>> result = new ArrayList<>();
        Query query = new Query.QueryBuilder()
                .where(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.STATUS.getName(), 1)
                .buildQuery();
        List<Map<String, Object>> allData = new ArrayList<>();
        List<Dict_51jobOccupationRecord> list = getRecords(query);
        if (list != null && list.size() > 0) {
            list.forEach(r -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", r.getCode());
                map.put("parent_id", r.getParentId());
                map.put("name", r.getName());
                map.put("code_other", r.getCodeOther());
                map.put("level", r.getLevel());
                map.put("status", r.getStatus());
                allData.add(map);
            });
        }
        if (allData.size() > 0) {
            result = arrangeOccupation(allData);
        }
        return result;
    }

    public List getSingle(JSONObject obj) {
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
        List<Map<String, Object>> allData = new ArrayList<>();
        List<Dict_51jobOccupationRecord> list = getRecords(build.buildQuery());
        if (list != null && list.size() > 0) {
            list.forEach(r -> {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", r.getCode());
                map.put("parent_id", r.getParentId());
                map.put("name", r.getName());
                map.put("code_other", r.getCodeOther());
                map.put("level", r.getLevel());
                map.put("status", r.getStatus());
                allData.add(map);
            });
        }
        return allData;
    }

    private static List<Map<String, Object>> arrangeOccupation(List<Map<String, Object>> allData) {
        List<Map<String, Object>> result = arrangeFirstLevel(allData);
        arrangeSecondLevel(result, allData);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static void arrangeSecondLevel(List<Map<String, Object>> results, List<Map<String, Object>> allData) {
        results.forEach(result -> {
            Iterator<Map<String, Object>> id = allData.iterator();
            while (id.hasNext()) {
                Map<String, Object> d = id.next();
                if (((Integer) d.get("parent_id")).intValue() == ((Integer) result.get("code")).intValue()) {
                    if (result.get("children") == null) {
                        List<Map<String, Object>> children = new ArrayList<>();
                        result.put("children", children);
                    }
                    d.put("parent_id_code", result.get("code_other"));
                    ((List<Map<String, Object>>) result.get("children")).add(d);
                    id.remove();
                }
            }
        });

        results.forEach(result -> {
            if (result.get("children") != null && ((List<Map<String, Object>>) result.get("children")).size() > 0) {
                ((List<Map<String, Object>>) result.get("children")).forEach(r -> {
                    Iterator<Map<String, Object>> id = allData.iterator();
                    while (id.hasNext()) {
                        Map<String, Object> d = id.next();
                        if (((Integer) d.get("parent_id")).intValue() == ((Integer) r.get("code")).intValue()) {
                            if (r.get("children") == null) {
                                List<Map<String, Object>> children = new ArrayList<>();
                                r.put("children", children);
                            }
                            d.put("parent_id_code", r.get("code_other"));
                            ((List<Map<String, Object>>) r.get("children")).add(d);
                            id.remove();
                        }
                    }
                });
            }
        });
    }

    private static List<Map<String, Object>> arrangeFirstLevel(List<Map<String, Object>> allData) {
        List<Map<String, Object>> result = new ArrayList<>();
        Iterator<Map<String, Object>> id = allData.iterator();
        while (id.hasNext()) {
            Map<String, Object> d = id.next();
            if (d.get("parent_id") != null && ((Integer) d.get("parent_id")).intValue() == 0) {
                d.put("parent_id_code", 0);
                result.add(d);
                id.remove();
            }
        }
        return result;
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
