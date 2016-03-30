package com.moseeker.common.dbconnection;

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
            System.out.println(url);
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
        long currentTime = System.currentTimeMillis();
        Connection conn = getConnection();
        System.out.println("connection: " + (System.currentTimeMillis() - currentTime));
        return getCreate(conn);
    }

    private static Connection getConnection() throws SQLException {
        // System.out.println(url);
        // System.out.println(userName);
        // System.out.println(password);
        return connectionPool.getConnection();
        // return DriverManager.getConnection(url, userName, password);
    }

}

class DBPropertiesReader {

    public static ConfigPropertiesUtil getDBPropertiesReader() throws Exception {
        ConfigPropertiesUtil cpu = ConfigPropertiesUtil.getInstance();
        cpu.loadResource("mycatConfig.properties");
        return cpu;
    }

}