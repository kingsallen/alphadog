package com.moseeker.common.dbconnection;

import org.jooq.*;

public class Test {

    public static void main(String[] args) {

        for(int i = 0; i < 5; i++) {
            
            new Thread(() -> {

                try {
                    DSLContext dsl = DatabaseConnectionHelper.getJooqDSL();
                    Result<Record> result = dsl.select().from("friendrequests").fetch();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}