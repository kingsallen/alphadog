package com.moseeker.common.dbconnection;


import com.moseeker.common.util.ConfigPropertiesUtil;
import org.jooq.*;
import org.jooq.impl.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.moseeker.common.util.ConfigPropertiesUtil;

public class DatabaseConnectionHelper {

    private static BoneCP connectionPool = null;
    private static BoneCPConfig config = null;
    private static String url = null;
    private static String userName = null;
    private static String password = null;
    private static Integer minConnections = null;
    private static Integer maxConnections = null;

    static {
        try {
        	ConfigPropertiesUtil dbPropertiesReader = DBPropertiesReader.getDBPropertiesReader();
            url = dbPropertiesReader.get("mycat.url", String.class); // read from .properties file
            userName = dbPropertiesReader.get("mycat.userName", String.class);
            password = dbPropertiesReader.get("mycat.password", String.class);
            minConnections = dbPropertiesReader.get("mycat.minConnections", Integer.class);
            maxConnections = dbPropertiesReader.get("mycat.maxConnections", Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            config = new BoneCPConfig();
            config.setJdbcUrl(url);
            config.setUsername(userName);
            config.setPassword(password);
            config.setMinConnectionsPerPartition(minConnections);
            config.setMaxConnectionsPerPartition(maxConnections);
            long currentTime = System.currentTimeMillis();
            connectionPool = new BoneCP(config);
            System.out.println("first connection: " + (System.currentTimeMillis() - currentTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DSLContext getCreate(Connection conn) throws SQLException {
        return DSL.using(conn, SQLDialect.MYSQL);
    }

    public static DSLContext getCreate() throws SQLException {
        Connection conn = getConnection();
        return getCreate(conn);
    }

    private static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

}


class DBPropertiesReader {

    private static String myCatConfigPropertiesFileName = "mycatConfig.properties";

    public static ConfigPropertiesUtil getDBPropertiesReader() throws Exception {
        ConfigPropertiesUtil myCatConfigPropertiesUtil = ConfigPropertiesUtil.getInstance();
        myCatConfigPropertiesUtil.loadResource(myCatConfigPropertiesFileName);
        return myCatConfigPropertiesUtil;
    }

}