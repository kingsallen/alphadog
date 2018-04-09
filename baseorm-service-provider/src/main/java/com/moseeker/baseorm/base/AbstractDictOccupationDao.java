package com.moseeker.baseorm.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDictOccupationDao<S,R extends UpdatableRecord<R>> extends JooqCrudImpl<S, R> implements IChannelType {

    public AbstractDictOccupationDao(TableImpl<R> table, Class<S> sClass) {
        super(table, sClass);
    }

    public List<S> getAllOccupation(){
        Query query = new Query.QueryBuilder().where(statusCondition()).buildQuery();
        return getDatas(query);
    }

    public List<S> getSingle(JSONObject obj){
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(statusCondition());
        queryCondition(obj).forEach(c->{
            if(c!=null){
                build.and(c);
            }
        });
        return getDatas(build.buildQuery());
    }

    //查询职位职能的状态条件
    protected abstract Condition statusCondition();

    protected List<Condition> queryCondition(JSONObject obj){
        List<Condition> conditions=new ArrayList<>();
        queryEQParam(obj).forEach((k,v)->{
            if(v!=null){
                conditions.add(new Condition(k,v));
            }
        });
        return conditions;
    }

    protected abstract Map<String,Object> queryEQParam(JSONObject obj);

    public List<S> getFullOccupations(String occupation){
        List<S> fullOccupations = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(occupation)) return fullOccupations;


        Query query = null;
        Condition condition=new Condition(otherCodeName(),occupation);

        S s=null;

        while (true) {

            query = new Query.QueryBuilder().where(condition).buildQuery();

            s = getData(query);

            if (s == null) {
                break;
            } else {
                fullOccupations.add(0, s);
                if (isTopOccupation(s)) {
                    break;
                }
                condition=conditionToSearchFather(s);
            }
        }
        logger.info("get Full Occupations ", JSON.toJSONString(fullOccupations));
        return fullOccupations;
    }

    //是否第一层职能职位
    protected abstract boolean isTopOccupation(S s);

    //用来寻找父职能职位的参数
    protected abstract Condition conditionToSearchFather(S s);

    //第三方职位职能code的参数名称
    protected abstract String otherCodeName();

    //删除所有
    public abstract int deleteAll();

}
