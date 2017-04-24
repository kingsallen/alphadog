package com.moseeker.common.providerutils;

import com.moseeker.thrift.gen.common.struct.Order;
import com.moseeker.thrift.gen.common.struct.OrderBy;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertEquals;

/**
 * QueryUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 16, 2017</pre>
 */
public class QueryUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addEqualFilter(String key, String value)
     */
    /*@Test
    public void testAddEqualFilterForKeyValue() throws Exception {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("user_id", 1);
        assertEquals("1", queryUtil.getConditions().getValueCondition().getValue());
    }

    @Test
    public void testAddGroup() throws Exception {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addGroup("user_id");
        assertEquals(1, queryUtil.getGroups().size());
    }

    @Test
    public void testAddAttribute() throws Exception {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("user_id").addSelectAttribute("id");
        assertEquals(2, queryUtil.getAttributes().size());
    }

    @Test
    public void testOrder(){
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.setSortby("name");
        queryUtil.setOrder("desc");

        assertEquals("name",queryUtil.getOrders().get(0).field);
        assertEquals(Order.DESC,queryUtil.getOrders().get(0).order);
    }*/
} 
