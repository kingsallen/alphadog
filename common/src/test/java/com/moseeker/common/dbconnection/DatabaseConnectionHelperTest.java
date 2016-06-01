package com.moseeker.common.dbconnection;

/**
 * Created by chendi on 3/30/16.
 */

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;

import com.moseeker.common.dbutils.DatabaseConnectionHelper;

public class DatabaseConnectionHelperTest {

    @Test
    public void connPoolTest() {
        for(int i = 0; i < 1; i++) {

            new Thread(() -> {

                try {
                    DatabaseConnectionHelper db = DatabaseConnectionHelper.getConnection();
                    DSLContext dsl = db.getJooqDSL();
                    Result<Record> result = dsl.select().from("configDB.adminnotification_events").fetch();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
