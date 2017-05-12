package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
* @author xxx
* DictCollegeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCollegeDao extends JooqCrudImpl<DictCollegeDO, DictCollegeRecord> {

    public DictCollegeDao() {
        super(DictCollege.DICT_COLLEGE, DictCollegeDO.class);
    }

    public DictCollegeDao(TableImpl<DictCollegeRecord> table, Class<DictCollegeDO> dictCollegeDOClass) {
        super(table, dictCollegeDOClass);
    }

    @SuppressWarnings("unchecked")
    public List getJoinedResults(Query query) {
       return create.select(DictCollege.DICT_COLLEGE.CODE.as("college_code"),
                DictCollege.DICT_COLLEGE.NAME.as("college_name"),
                DictCollege.DICT_COLLEGE.LOGO.as("college_logo"),
                DictCity.DICT_CITY.CODE.as("province_code"),
                DictCity.DICT_CITY.NAME.as("province_name"))
                .from(DictCollege.DICT_COLLEGE)
                .join(DictCity.DICT_CITY)
                .on(DictCollege.DICT_COLLEGE.PROVINCE.equal(DictCity.DICT_CITY.CODE)).fetch();
    }
}
