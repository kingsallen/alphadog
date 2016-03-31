package com.moseeker.common.dbconnection;

/**
 * Created by chendi on 3/30/16.
 */

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;

public class DatabaseConnectionHelperTest {

    @Test
    public void connPoolTest() {
        for(int i = 0; i < 5; i++) {

            new Thread(() -> {

                try {
                    DSLContext create = DatabaseConnectionHelper.getJooqDSL();
                    Result<Record> result = create.select().from("friendrequests").fetch();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}
