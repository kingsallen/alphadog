package com.moseeker.common.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.Statistics;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Notification;

public class DatabaseConnectionHelper {

    private static BoneCP connectionPool;
    private static DatabaseConnectionHelper self;

    private BoneCP initConnectionPool() {
        BoneCP connectionPool = null;
        try {
            // register jdbc driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // read configs from properties reader
            ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
            String url = propertiesReader.get("mycat.url", String.class);
            String userName = propertiesReader.get("mycat.userName", String.class);
            String password = propertiesReader.get("mycat.password", String.class);
            Integer minConnections = propertiesReader.get("mycat.minConnections", Integer.class);
            Integer maxConnections = propertiesReader.get("mycat.maxConnections", Integer.class);
            // init connection pool
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(url);
            config.setUsername(userName);
            config.setPassword(password);
            config.setMinConnectionsPerPartition(minConnections);
            config.setMaxConnectionsPerPartition(maxConnections);
            connectionPool = new BoneCP(config);
        } catch (Exception e) {
            // send notification
            e.printStackTrace();
            Notification.sendMyCatConnectionError(e.getMessage());
        }
        return connectionPool;
    }

    public static DatabaseConnectionHelper getConnection() {
        if(self == null) {
            self = new DatabaseConnectionHelper();
        }
        return self;
    }

    private DatabaseConnectionHelper() {
        connectionPool = initConnectionPool();
    }

    public DSLContext getJooqDSL() throws SQLException {
        Connection conn = connectionPool.getConnection();
        Statistics statictics = connectionPool.getStatistics();
        System.out.println("total free:"+statictics.getTotalFree());
        System.out.println("total lease:"+statictics.getTotalLeased());
        statictics.getTotalLeased();
        return DSL.using(conn, SQLDialect.MYSQL);
    }

}
