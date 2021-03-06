/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb;


import com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusCity;
import com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusJobcategory;
import com.moseeker.baseorm.db.dictdb.tables.DictCarnocOccupation;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCityLiepin;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.DictCityPostcode;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;
import com.moseeker.baseorm.db.dictdb.tables.DictJob1001Occupation;
import com.moseeker.baseorm.db.dictdb.tables.DictJobsdbOccupation;
import com.moseeker.baseorm.db.dictdb.tables.DictLiepinOccupation;
import com.moseeker.baseorm.db.dictdb.tables.DictMajor;
import com.moseeker.baseorm.db.dictdb.tables.DictMarsMajor;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.DictReferralEvaluate;
import com.moseeker.baseorm.db.dictdb.tables.DictTestMobile;
import com.moseeker.baseorm.db.dictdb.tables.DictVeryeastOccupation;
import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.Dict_58jobFeature;
import com.moseeker.baseorm.db.dictdb.tables.Dict_58jobOccupation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dictdb extends SchemaImpl {

    private static final long serialVersionUID = 356159954;

    /**
     * The reference instance of <code>dictdb</code>
     */
    public static final Dictdb DICTDB = new Dictdb();

    /**
     * 51的职位表
     */
    public final Dict_51jobOccupation DICT_51JOB_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation.DICT_51JOB_OCCUPATION;

    /**
     * 58福利特色表
     */
    public final Dict_58jobFeature DICT_58JOB_FEATURE = com.moseeker.baseorm.db.dictdb.tables.Dict_58jobFeature.DICT_58JOB_FEATURE;

    /**
     * 58职能表
     */
    public final Dict_58jobOccupation DICT_58JOB_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.Dict_58jobOccupation.DICT_58JOB_OCCUPATION;

    /**
     * The table <code>dictdb.dict_alipaycampus_city</code>.
     */
    public final DictAlipaycampusCity DICT_ALIPAYCAMPUS_CITY = com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusCity.DICT_ALIPAYCAMPUS_CITY;

    /**
     * dict_alipaycampus_jobcategory[alipay校园招聘-职位类别]
     */
    public final DictAlipaycampusJobcategory DICT_ALIPAYCAMPUS_JOBCATEGORY = com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusJobcategory.DICT_ALIPAYCAMPUS_JOBCATEGORY;

    /**
     * 民航招聘的职位职能表
     */
    public final DictCarnocOccupation DICT_CARNOC_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictCarnocOccupation.DICT_CARNOC_OCCUPATION;

    /**
     * 城市字典表
     */
    public final DictCity DICT_CITY = com.moseeker.baseorm.db.dictdb.tables.DictCity.DICT_CITY;

    /**
     * The table <code>dictdb.dict_city_liepin</code>.
     */
    public final DictCityLiepin DICT_CITY_LIEPIN = com.moseeker.baseorm.db.dictdb.tables.DictCityLiepin.DICT_CITY_LIEPIN;

    /**
     * 城市字典code映射表
     */
    public final DictCityMap DICT_CITY_MAP = com.moseeker.baseorm.db.dictdb.tables.DictCityMap.DICT_CITY_MAP;

    /**
     * The table <code>dictdb.dict_city_postcode</code>.
     */
    public final DictCityPostcode DICT_CITY_POSTCODE = com.moseeker.baseorm.db.dictdb.tables.DictCityPostcode.DICT_CITY_POSTCODE;

    /**
     * 学校字典表
     */
    public final DictCollege DICT_COLLEGE = com.moseeker.baseorm.db.dictdb.tables.DictCollege.DICT_COLLEGE;

    /**
     * The table <code>dictdb.dict_constant</code>.
     */
    public final DictConstant DICT_CONSTANT = com.moseeker.baseorm.db.dictdb.tables.DictConstant.DICT_CONSTANT;

    /**
     * 城市字典表
     */
    public final DictCountry DICT_COUNTRY = com.moseeker.baseorm.db.dictdb.tables.DictCountry.DICT_COUNTRY;

    /**
     * 行业二级分类字典表
     */
    public final DictIndustry DICT_INDUSTRY = com.moseeker.baseorm.db.dictdb.tables.DictIndustry.DICT_INDUSTRY;

    /**
     * 行业一级分类字典表
     */
    public final DictIndustryType DICT_INDUSTRY_TYPE = com.moseeker.baseorm.db.dictdb.tables.DictIndustryType.DICT_INDUSTRY_TYPE;

    /**
     * 一览人才的职位表
     */
    public final DictJob1001Occupation DICT_JOB1001_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictJob1001Occupation.DICT_JOB1001_OCCUPATION;

    /**
     * JobsDB的职位职能表
     */
    public final DictJobsdbOccupation DICT_JOBSDB_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION;

    /**
     * The table <code>dictdb.dict_liepin_occupation</code>.
     */
    public final DictLiepinOccupation DICT_LIEPIN_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictLiepinOccupation.DICT_LIEPIN_OCCUPATION;

    /**
     * 专业字典表
     */
    public final DictMajor DICT_MAJOR = com.moseeker.baseorm.db.dictdb.tables.DictMajor.DICT_MAJOR;

    /**
     * 玛氏专业常量表
     */
    public final DictMarsMajor DICT_MARS_MAJOR = com.moseeker.baseorm.db.dictdb.tables.DictMarsMajor.DICT_MARS_MAJOR;

    /**
     * 职能分类字典表
     */
    public final DictPosition DICT_POSITION = com.moseeker.baseorm.db.dictdb.tables.DictPosition.DICT_POSITION;

    /**
     * 内推能力标签常量
     */
    public final DictReferralEvaluate DICT_REFERRAL_EVALUATE = com.moseeker.baseorm.db.dictdb.tables.DictReferralEvaluate.DICT_REFERRAL_EVALUATE;

    /**
     * The table <code>dictdb.dict_test_mobile</code>.
     */
    public final DictTestMobile DICT_TEST_MOBILE = com.moseeker.baseorm.db.dictdb.tables.DictTestMobile.DICT_TEST_MOBILE;

    /**
     * 最佳东方的职位职能表
     */
    public final DictVeryeastOccupation DICT_VERYEAST_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION;

    /**
     * 智联的职位表
     */
    public final DictZhilianOccupation DICT_ZHILIAN_OCCUPATION = com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION;

    /**
     * No further instances allowed
     */
    private Dictdb() {
        super("dictdb", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Dict_51jobOccupation.DICT_51JOB_OCCUPATION,
            Dict_58jobFeature.DICT_58JOB_FEATURE,
            Dict_58jobOccupation.DICT_58JOB_OCCUPATION,
            DictAlipaycampusCity.DICT_ALIPAYCAMPUS_CITY,
            DictAlipaycampusJobcategory.DICT_ALIPAYCAMPUS_JOBCATEGORY,
            DictCarnocOccupation.DICT_CARNOC_OCCUPATION,
            DictCity.DICT_CITY,
            DictCityLiepin.DICT_CITY_LIEPIN,
            DictCityMap.DICT_CITY_MAP,
            DictCityPostcode.DICT_CITY_POSTCODE,
            DictCollege.DICT_COLLEGE,
            DictConstant.DICT_CONSTANT,
            DictCountry.DICT_COUNTRY,
            DictIndustry.DICT_INDUSTRY,
            DictIndustryType.DICT_INDUSTRY_TYPE,
            DictJob1001Occupation.DICT_JOB1001_OCCUPATION,
            DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION,
            DictLiepinOccupation.DICT_LIEPIN_OCCUPATION,
            DictMajor.DICT_MAJOR,
            DictMarsMajor.DICT_MARS_MAJOR,
            DictPosition.DICT_POSITION,
            DictReferralEvaluate.DICT_REFERRAL_EVALUATE,
            DictTestMobile.DICT_TEST_MOBILE,
            DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION,
            DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION);
    }
}
