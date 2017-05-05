package com.moseeker.baseorm.crud;

import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.query.*;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import org.jooq.*;
import org.jooq.Condition;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by jack on 02/05/2017.
 */
public class LocalQueryTest {

    private Query query;
    private LocalQuery<UserUserRecord> localQuery;

    private DSLContext context;

    @Before
    public void init() {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        query = queryBuilder.select("id").select("name")
                .where("id", 1).or("name", "hello")
                .andInnerCondition("nickname", "hello").and("activation", 1).orOutCondition("is_disable",1)
                .orInnerCondition("email", 1).andInnerCondition("source", 1).or("unionid","unionid")
                .orOutCondition("headimg", "headimg").andOutCondition("activation_code", 1)
                .groupBy("nickname").groupBy("name").groupBy("activation")
                .buildQuery();

        Connection connection = null;
        try {
            connection = DBConnHelper.DBConn.getConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        context = new DefaultDSLContext(connection, SQLDialect.MYSQL);
        localQuery = new LocalQuery<>(context, UserUser.USER_USER, query);
    }

    @Test
    public void getPage() throws Exception {
        assertEquals(1, localQuery.getPage());
    }

    @Test
    public void getPageSize() throws Exception {
        assertEquals(10, localQuery.getPageSize());
    }

    @Test
    public void buildSelect() throws Exception {
        Collection fieldCollection = localQuery.buildSelect();
        
        assertEquals(2, fieldCollection.size());
        Iterator it = fieldCollection.iterator();
        int index = 1;
        while (it.hasNext()) {
            Field field = (Field) it.next();
            if (index == 1) {
                assertEquals("id", field.getName());
            } else if (index == 2) {
                assertEquals("name", field.getName());
            }
            index ++;
        }
    }

    @Test
    public void buildGroup() throws Exception {
        Collection fieldCollection = localQuery.buildGroup();
        assertEquals(3, fieldCollection.size());
        Iterator it = fieldCollection.iterator();
        int index = 1;
        while (it.hasNext()) {
            Field field = (Field) it.next();
            if (index == 1) {
                assertEquals("nickname", field.getName());
            } else if (index == 2) {
                assertEquals("name", field.getName());
            } else if (index == 3) {
                assertEquals("activation", field.getName());
            }
            index ++;
        }
    }

    @Test
    public void buildConditions() throws Exception {

        Condition userId = UserUser.USER_USER.ID.eq(1);
        Condition name = UserUser.USER_USER.NAME.eq("hello");

        Condition nickname = UserUser.USER_USER.NICKNAME.eq("hello");
        Condition activation = UserUser.USER_USER.ACTIVATION.eq((byte)1);
        Condition nickname_activation = nickname.and(activation);

        Condition disable = UserUser.USER_USER.IS_DISABLE.eq((byte)1);

        Condition email = UserUser.USER_USER.EMAIL.eq("email");
        Condition headImg = UserUser.USER_USER.HEADIMG.eq("headimg");
        Condition eamil_headImg = email.or(headImg);

        Condition activationCode = UserUser.USER_USER.ACTIVATION_CODE.eq("1");

        Condition condition = userId.or(name).and(nickname_activation).or(disable).or(eamil_headImg).and(activationCode);

        System.out.println(context.select(UserUser.USER_USER.ID)
                .from(UserUser.USER_USER).where(condition).getSQL());

        System.out.println(userId);
        System.out.println(name);
        System.out.println(nickname);
        System.out.println(activation);
        System.out.println(nickname_activation);
        System.out.println(disable);
        System.out.println(email);
        System.out.println(headImg);
        System.out.println(eamil_headImg);
        System.out.println(activationCode);

        System.out.println("-------------------");
        System.out.println(userId.and(name).or(nickname).and(activation));
        System.out.println(userId.and(name.or(nickname_activation).or(disable).and(eamil_headImg).and(activationCode)));
        Condition condition1 = userId.and(name.or(nickname_activation).or(disable).and(eamil_headImg).and(activationCode));
        System.out.println(context.select(UserUser.USER_USER.ID)
                .from(UserUser.USER_USER).where(condition1).getSQL());

        Condition condition12 = userId.and(name);
        Condition condition13 = condition12.or(nickname_activation).or(disable).and(eamil_headImg).and(activationCode);

        System.out.println(context.select(UserUser.USER_USER.ID)
                .from(UserUser.USER_USER).where(condition13).getSQL());
    }

    @Test
    public void convertToResultQuery() throws Exception {

        ResultQuery<UserUserRecord> resultQuery = localQuery.convertToResultQuery();
        System.out.println(resultQuery.getSQL());
    }

}