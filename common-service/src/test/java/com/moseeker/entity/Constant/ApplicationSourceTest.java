package com.moseeker.entity.Constant;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import org.jooq.Param;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Test;

import static org.jooq.impl.DSL.param;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectOne;
import static org.junit.Assert.*;

/**
 * Created by jack on 20/08/2017.
 */
public class ApplicationSourceTest {

    @Test
    public void exist() throws Exception {
        assertEquals(true, ApplicationSource.ENTERPRISE.exist(2));
        assertEquals(false, ApplicationSource.ENTERPRISE.exist(1));
        assertEquals(true, ApplicationSource.ENTERPRISE.exist(3));

        assertEquals(true, ApplicationSource.ALIPAY.exist(64));
        assertEquals(false, ApplicationSource.ALIPAY.exist(63));
        assertEquals(true, ApplicationSource.ALIPAY.exist(65));

        assertEquals(true, ApplicationSource.DELEGATE.exist(256));
        assertEquals(true, ApplicationSource.DELEGATE.exist(257));
        assertEquals(false, ApplicationSource.DELEGATE.exist(1));

        assertEquals(false, ApplicationSource.GATHER.exist(1));
        assertEquals(true, ApplicationSource.GATHER.exist(4));
        assertEquals(true, ApplicationSource.GATHER.exist(5));

    }

    @Test
    public void andSource() throws Exception {
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(1));
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(1));
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(ApplicationSource.PC));
    }

    @Test
    public void testChannelToOrigin() {
        int origin = ApplicationSource.channelToOrigin(6);
        System.out.println(origin);
    }

    @Test
    public void testSQL() {
        Param<Integer> companyIdParam = param(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), 1);
        Param<Byte> activationParam = param(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), (byte)3);
        Param<String> cnameParam = param(UserEmployee.USER_EMPLOYEE.CNAME.getName(), "wjf");
        Param<String> customFieldParam = param(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.getName(), "1111");
        DefaultDSLContext create = new DefaultDSLContext(SQLDialect.MYSQL);
        String sql = create.insertInto(UserEmployee.USER_EMPLOYEE)
                .columns(UserEmployee.USER_EMPLOYEE.COMPANY_ID,
                        UserEmployee.USER_EMPLOYEE.ACTIVATION,
                        UserEmployee.USER_EMPLOYEE.CNAME,
                        UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD)
                .select(
                        select(
                                companyIdParam, activationParam, cnameParam, customFieldParam
                        )
                                .whereNotExists(
                                        selectOne()
                                                .from(UserEmployee.USER_EMPLOYEE)
                                                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(1))
                                                .and(UserEmployee.USER_EMPLOYEE.CNAME.eq("wjf"))
                                                .and(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.eq("1111"))
                                )
                )
                .returning().getSQL();
        System.out.println(sql);
    }
}