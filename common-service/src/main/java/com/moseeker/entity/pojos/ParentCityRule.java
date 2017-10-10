package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;

/**
 * 查找上一级城市的校验规则
 * Created by jack on 28/09/2017.
 */
public class ParentCityRule {

    private int codeLimit;
    private int codeUpper;
    private int level;

    public ParentCityRule(int code, int level) {
        codeLimit = code /10000 * 10000;
        if (level == 3) {
            codeLimit = code /100 * 100;
        } else {
            codeLimit = code /10000 * 10000;
        }
        codeUpper = (code /10000 + 1) * 10000;
        this.level = level - 1;
    }

    /**
     * 生成ParentCityRule
     * @param cityDO 城市信息
     * @return 查找城市父类规则
     */
    public static ParentCityRule instanceFromCity(DictCityDO cityDO) {
        if (cityDO == null) {
            return null;
        }
        ParentCityRule cityRule = new ParentCityRule(cityDO.getCode(), cityDO.getLevel());
        return cityRule;
    }

    /**
     * 生成查找父类的查询条件
     * @param cityDO 父类
     * @return
     */
    public static Query buildQuery(DictCityDO cityDO) {
        if (cityDO == null || cityDO.getCode() == 0 || cityDO.getLevel() <= 1) {
            return null;
        }
        int codeLimit;
        if (cityDO.getLevel() == 3) {
            codeLimit = cityDO.getCode() /100 * 100;
        } else {
            codeLimit = cityDO.getCode() /10000 * 10000;
        }
        int codeUpper = ((cityDO.getCode() /10000) + 1) * 10000;
        int level = cityDO.getLevel() -1;

        return query(codeLimit, codeUpper, level);
    }

    /**
     * 生成查询规则
     * @return 查询规则
     */
    public Query buildQuery() {
        if (codeLimit == 0 || level < 1) {
            return null;
        }
        return query(codeLimit, codeUpper, level);
    }

    private static Query query(int codeLimit, int codeUpper, int level) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(DictCity.DICT_CITY.CODE.getName(), codeLimit, ValueOp.GE))
                .and(new Condition(DictCity.DICT_CITY.CODE.getName(), codeUpper, ValueOp.LT))
                .and(new Condition(DictCity.DICT_CITY.LEVEL.getName(), level))
                .orderBy(DictCity.DICT_CITY.CODE.getName(), Order.ASC)
                .setPageNum(1)
                .setPageSize(1);
        return queryBuilder.buildQuery();
    }

    public int getCodeLimit() {
        return codeLimit;
    }

    public void setCodeLimit(int codeLimit) {
        this.codeLimit = codeLimit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCodeUpper() {
        return codeUpper;
    }

    public void setCodeUpper(int codeUpper) {
        this.codeUpper = codeUpper;
    }
}
