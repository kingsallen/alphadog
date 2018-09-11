package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dict.struct.College;
import com.moseeker.thrift.gen.dict.struct.CollegeBasic;
import com.moseeker.thrift.gen.dict.struct.CollegeProvince;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 *         DictCollegeDao 实现类 （groovy 生成）
 *         2017-03-21
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
    public List<College> getJoinedResults(Query query) {
        return create.select(DictCollege.DICT_COLLEGE.CODE.as("college_code"),
                DictCollege.DICT_COLLEGE.NAME.as("college_name"),
                DictCollege.DICT_COLLEGE.LOGO.as("college_logo"),
                DictCountry.DICT_COUNTRY.ID.as("country_code"),
                DictCountry.DICT_COUNTRY.ENAME.as("country_ename"),
                DictCountry.DICT_COUNTRY.NAME.as("country_name"))
                .from(DictCollege.DICT_COLLEGE)
                .join(DictCountry.DICT_COUNTRY)
                .on(DictCollege.DICT_COLLEGE.COUNTRY_CODE.equal(DictCountry.DICT_COUNTRY.ID))
                .fetchInto(College.class);
    }

    /**
     *获取中国大陆的院校数据， 43 是中国的国家字典表id， 710000台湾省 ，810000香港，820000澳门
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CollegeProvince> getCollegeByDomestic() {
        return create.select(DictCollege.DICT_COLLEGE.CODE.as("college_code"),
                DictCollege.DICT_COLLEGE.NAME.as("college_name"),
                DictCollege.DICT_COLLEGE.LOGO.as("college_logo"),
                DictCity.DICT_CITY.CODE.as("province_code"),
                DictCity.DICT_CITY.NAME.as("province_name"))
                .from(DictCollege.DICT_COLLEGE)
                .join(DictCity.DICT_CITY)
                .on(DictCollege.DICT_COLLEGE.PROVINCE.equal(DictCity.DICT_CITY.CODE))
                .where(DictCollege.DICT_COLLEGE.COUNTRY_CODE.eq(43))
                .fetchInto(CollegeProvince.class);
    }


    /**
     *根据国家编号获取国家对应院校
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CollegeBasic> getCollegeByAbroad(int countryCode) {
        return create.select(DictCollege.DICT_COLLEGE.CODE,
                DictCollege.DICT_COLLEGE.NAME,
                DictCollege.DICT_COLLEGE.LOGO)
                .from(DictCollege.DICT_COLLEGE)
                .where(DictCollege.DICT_COLLEGE.COUNTRY_CODE.eq(countryCode))
                .fetchInto(CollegeBasic.class);
    }

    public List<DictCollegeDO> getCollegesByIDs(List<Integer> ids) {
        List<DictCollegeDO> result = null;
        if (ids != null && ids.size() > 0) {
            Query q = new Query.QueryBuilder().where(Condition.buildCommonCondition("code", ids, ValueOp.IN)).buildQuery();
            result = getDatas(q);
        }

        return result;
    }

    public DictCollegeDO getCollegeByID(int code) {
        return getData(new Query.QueryBuilder().where("code", code).buildQuery());
    }

    public Map<String, DictCollegeDO> getCollegeMap(){
        List<DictCollegeDO> collegeDOList = create.selectFrom(DictCollege.DICT_COLLEGE)
                .fetchInto(DictCollegeDO.class);
        Map<String, DictCollegeDO> params = new HashMap<>();
        if(!StringUtils.isEmptyList(collegeDOList)){
            for(DictCollegeDO college : collegeDOList){
                params.put(college.getName(),college);
            }
        }
        return params;
    }
}
