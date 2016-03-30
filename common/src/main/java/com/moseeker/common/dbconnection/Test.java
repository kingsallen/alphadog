package com.moseeker.common.dbconnection;

import com.moseeker.common.dbconnection.DatabaseConnectionHelper;
import org.jooq.*;
import org.jooq.impl.*;
import java.sql.Connection;

public class Test {

    public static void main(String[] args) {

        for(int i = 0; i < 5; i++) {
            
            new Thread(() -> {

                try {
                    DSLContext create = DatabaseConnectionHelper.getCreate();
                    Result<Record> result = create.select().from("friendrequests").fetch();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}