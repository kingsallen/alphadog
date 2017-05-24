package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantPojo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictConstantDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictConstantDao extends JooqCrudImpl<DictConstantDO, DictConstantRecord> {

    public DictConstantDao() {
        super(DictConstant.DICT_CONSTANT, DictConstantDO.class);
    }

    public DictConstantDao(TableImpl<DictConstantRecord> table, Class<DictConstantDO> dictConstantDOClass) {
        super(table, dictConstantDOClass);
    }

    /**
     * 获取常量字典
     *
     * @param parentCodeList 类型code集合
     * @return
     * @throws Exception
     */
    public Map<Integer, List<DictConstantPojo>> getDictConstantJsonByParentCode(List<Integer> parentCodeList) throws Exception{
        Condition condition = null;
        Map<Integer, List<DictConstantPojo>> map = new HashMap<Integer, List<DictConstantPojo>>();
        // parentCodeList 为空时, 返回所有字典常量
        if(parentCodeList == null){
            condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(0);
            List<DictConstantPojo> dictConstantPOJOList1 = create.select().from(DictConstant.DICT_CONSTANT).
                    where(condition).fetchInto(DictConstantPojo.class);

            for (DictConstantPojo dictConstantPOJO: dictConstantPOJOList1) {
                condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(dictConstantPOJO.getCode());
                List<DictConstantPojo> dictConstantPOJOList2 = create.select().from(DictConstant.DICT_CONSTANT).
                        where(condition).orderBy(DictConstant.DICT_CONSTANT.PRIORITY.asc()).fetchInto(DictConstantPojo.class);
                map.put(dictConstantPOJO.getCode(), dictConstantPOJOList2);
            }
            // 单个parent_code
        }else if(parentCodeList.size() == 1){
            condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodeList.get(0));
            List<DictConstantPojo> dictConstantPOJOList1 = create.select().from(DictConstant.DICT_CONSTANT).
                    where(condition).orderBy(DictConstant.DICT_CONSTANT.PRIORITY.asc()).fetchInto(DictConstantPojo.class);
            map.put(parentCodeList.get(0), dictConstantPOJOList1);
            // 多个parent_code
        }else{
            for (Integer parentCode: parentCodeList) {
                condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCode);
                List<DictConstantPojo> dictConstantPOJOList1 = create.select().from(DictConstant.DICT_CONSTANT).
                        where(condition).orderBy(DictConstant.DICT_CONSTANT.PRIORITY.asc()).fetchInto(DictConstantPojo.class);
                map.put(parentCode, dictConstantPOJOList1);
            }
        }
        return map;
    }

    /**
     * 根据parentCode和code获取唯一一条常量的描述
     * <p></p>
     *
     * @param parentCode 父类编码
     * @param code 常量编码
     * @return
     * @throws Exception
     */
    public DictConstantPojo getDictConstantJson(Integer parentCode, Integer code) throws Exception {
        Condition condition = null;
            condition = DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCode)
                    .and(DictConstant.DICT_CONSTANT.CODE.equal(code));

            Record record = create.selectCount().from(DictConstant.DICT_CONSTANT).where(condition).fetchOne();
            int count = (Integer) record.getValue(0);
            if (count > 0 ){
                List<DictConstantPojo> dictConstantPOJOList = create.select().from(DictConstant.DICT_CONSTANT).
                        where(condition).fetchInto(DictConstantPojo.class);

                return dictConstantPOJOList != null && dictConstantPOJOList.size() > 0 ? dictConstantPOJOList.get(0):null;
            }
        return null;
    }

    public List<DictConstantRecord> getCitiesByParentCodes(List<Integer> parentCodes) {
        List<DictConstantRecord> records = new ArrayList<>();
        if(parentCodes != null && parentCodes.size() > 0) {
            SelectWhereStep<DictConstantRecord> select = create.selectFrom(DictConstant.DICT_CONSTANT);
            SelectConditionStep<DictConstantRecord> selectCondition = null;
            for(int i=0; i<parentCodes.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodes.get(i)));
                } else {
                    selectCondition.or(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodes.get(i)));
                }
            }
            records = selectCondition.fetch();
        }
        return records;
    }

    public DictConstantRecord getCityByCode(int city_code) {
        DictConstantRecord record = null;
        if(city_code > 0) {
            Result<DictConstantRecord> result = create.selectFrom(DictConstant.DICT_CONSTANT)
                    .where(DictConstant.DICT_CONSTANT.CODE.equal(city_code))
                    .limit(1).fetch();
            if(result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }
}
