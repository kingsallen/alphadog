package com.moseeker.common.util.query;

import com.moseeker.common.util.exception.ConditionNotExist;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 25/04/2017.
 */
public class QueryTest {

    //@Test
    public void testAttribute() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.select("id").select("name").where("id", 1).or("name", "hello").buildQuery();
        List<Select> selectList = query.getAttributes();
        assertEquals("id", selectList.get(0).getField());
        assertEquals("name", selectList.get(1).getField());
        Condition condition = query.getConditions();
        assertEquals(condition.getField(), "id");
        assertEquals(condition.getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getCondition().getField(), "name");
        assertEquals(condition.getConditionJoin().getCondition().getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getOp(), ConditionOp.OR);
    }

    //@Test
    public void testAttribute1() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.select("id").select("name").select("").buildQuery();
        List<Select> selectList = query.getAttributes();
        assertEquals("id", selectList.get(0).getField());
        assertEquals("name", selectList.get(1).getField());
        assertEquals(2, selectList.size());
    }

    //@Test
    public void testAttribute3() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        String field = null;
        Query query = queryBuilder.select(field).buildQuery();
        List<Select> selectList = query.getAttributes();
        assertEquals(0, selectList.size());
    }

    //@Test
    public void testConditions() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.select("id").select("name").where("id", 1).or("name", "hello").andInnerCondition("nickname", "hello").and("activation", 1).orOutCondition("status",1).buildQuery();
        List<Select> selectList = query.getAttributes();
        assertEquals("id", selectList.get(0).getField());
        assertEquals("name", selectList.get(1).getField());
        Condition condition = query.getConditions();
        assertEquals(condition.getField(), "id");
        assertEquals(condition.getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getCondition().getField(), "name");
        assertEquals(condition.getConditionJoin().getCondition().getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getOp(), ConditionOp.OR);
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getField(), "nickname");
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getValue(), "hello");
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getOp(), ConditionOp.AND);
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getConditionJoin().getCondition().getField(), "activation");
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getConditionJoin().getCondition().getValue(), 1);
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getConditionJoin().getCondition().getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getCondition().getConditionInnerJoin().getCondition().getConditionJoin().getOp(), ConditionOp.AND);
        assertEquals(condition.getConditionJoin().getCondition().getConditionJoin().getCondition().getField(), "status");
        assertEquals(condition.getConditionJoin().getCondition().getConditionJoin().getCondition().getValue(), 1);
        assertEquals(condition.getConditionJoin().getCondition().getConditionJoin().getCondition().getValueOp(), ValueOp.EQ);
        assertEquals(condition.getConditionJoin().getCondition().getConditionJoin().getOp(), ConditionOp.OR);
    }

    //@Test(expected = ConditionNotExist.class)
    public void testConditions1() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.orOutCondition("status",1).buildQuery();
    }

    //@Test(expected = ConditionNotExist.class)
    public void testConditions4() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.andOutCondition("status",1).buildQuery();
    }

    //@Test(expected = ConditionNotExist.class)
    public void testConditions5() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.andInnerCondition("status",1).buildQuery();
    }

    //@Test(expected = ConditionNotExist.class)
    public void testConditions6() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.orInnerCondition("status",1).buildQuery();
    }

    //@Test
    public void testConditions2() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.and("status",1).buildQuery();
        Condition condition = query.getConditions();
        assertEquals(condition.getField(), "status");
        assertEquals(condition.getValue(), 1);
        assertEquals(condition.getValueOp(), ValueOp.EQ);
    }

    //@Test
    public void testConditions3() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.or("status",1).buildQuery();
        Condition condition = query.getConditions();
        assertEquals(condition.getField(), "status");
        assertEquals(condition.getValue(), 1);
        assertEquals(condition.getValueOp(), ValueOp.EQ);
    }

    //@Test
    public void testGroup() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.groupBy("id").groupBy("").buildQuery();
        List<String> list = query.getGroups();
        assertEquals(1, list.size());
        assertEquals("id", list.get(0));
    }

    //@Test
    public void testGroup1() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.groupBy("id").groupBy("name").buildQuery();
        List<String> list = query.getGroups();
        assertEquals(2, list.size());
        assertEquals("id", list.get(0));
        assertEquals("name", list.get(1));
    }

    //@Test
    public void testOrder() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        OrderBy orderBy = new OrderBy("nickname", Order.DESC);
        Query query = queryBuilder.orderBy("id").orderBy("").orderBy("name", Order.DESC).orderBy(orderBy).buildQuery();
        List<OrderBy> orderByList = query.getOrders();
        assertEquals(3, orderByList.size());
        assertEquals("id", orderByList.get(0).getField());
        assertEquals(Order.ASC, orderByList.get(0).getOrder());
        assertEquals("name", orderByList.get(1).getField());
        assertEquals("nickname", orderByList.get(2).getField());
        assertEquals(Order.DESC, orderByList.get(2).getOrder());
    }
}