package com.moseeker.demo.crud;

import com.moseeker.baseorm.crud.*;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.common.struct.Order;
import com.moseeker.thrift.gen.common.struct.Select;
import com.moseeker.thrift.gen.common.struct.SelectOp;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * Created by zhangdi on 2017/3/22.
 */
public class CommonTest {
    TestUserDao userDao;

    @Before
    public void init() {
        AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.scan("com.moseeker.demo");
        acac.scan("com.moseeker.baseorm");
        acac.scan("com.moseeker.common.aop.iface");
        acac.refresh();
        userDao = acac.getBean(TestUserDao.class);
    }

    @Test
    public void testAddMap() throws SQLException {
        int add = userDao.add(new HashMap<String, String>() {{
            put("name", "test_add_" + System.currentTimeMillis());
            put("password", "test_add_" + System.currentTimeMillis());
        }});

        Assert.assertEquals(add, 1);
    }

    @Test
    public void testUpdateMap() throws SQLException {
        int result = UpdateCreator
                .set("name", "test_add2_" + System.currentTimeMillis())
                .set("password", "test_add2_" + System.currentTimeMillis())
                .where(UpdateCondition.like("name", "test_add_%"))
                .update(userDao);

        Assert.assertEquals(result, 1);
    }

    @Test
    public void testDelete() throws SQLException {
        int delete = userDao.delete(QueryCondition.like("name", "test_add_%"));
        Assert.assertEquals(delete, 1);
    }

    @Test
    public void testQuery() throws SQLException {

        //实现的SQL

        //select username, count(username) as username_count
        //from user_user
        //where
        //is_disable = 0 and activation = 0 and (source  = 0 or source = 7)
        //groupby username
        //orderby username asc;

        List<Map<String, Object>> objectMap = QueryCreator
                .select("username")
                .select(new Select("username", SelectOp.COUNT))
                .where(CommonCondition.equal("is_disable", 0))
                .and(CommonCondition.equal("activation", 0))
                .and(QueryCondition.equal("source", 0).or(CommonCondition.equal("source", 7)))
                .groupBy("username")
                .orderBy("username", Order.ASC)
                .pageSize(10)
                .page(1)
                .getMaps(userDao);

        List<Map<String, Object>> objectMap2 = DSL.using(DBConnHelper.DBConn.getConn(), SQLDialect.MYSQL)
                .select(UserUser.USER_USER.USERNAME, UserUser.USER_USER.USERNAME.count().as("username_count"))
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.IS_DISABLE.equal((byte) 0).and(UserUser.USER_USER.ACTIVATION.equal((byte) 0)).and(UserUser.USER_USER.SOURCE.eq((short) 0).or(UserUser.USER_USER.SOURCE.eq((short) 7))))
                .groupBy(UserUser.USER_USER.USERNAME)
                .orderBy(UserUser.USER_USER.USERNAME.asc())
                .limit(0, 10)
                .fetchMaps();

        objectMap.forEach(map -> System.out.println(map));
        System.out.println("----------------");
        objectMap2.forEach(map -> System.out.println(map));


        IntStream.range(0, objectMap.size())
                .forEach(index -> objectMap.get(index).forEach((key, value) -> {
                    System.out.println(key + "-" + value + "-" + objectMap2.get(index).get(key));
                    Assert.assertTrue(objectMap2.get(index).containsKey(key));
                    Assert.assertEquals(objectMap2.get(index).get(key), value);
                }));

    }

}
