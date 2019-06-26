package com.moseeker.baseorm.config;

import com.jolbox.bonecp.BoneCPDataSource;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.baseorm.exception.ExceptionTranslator;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by jack on 22/03/2017.
 */
@Configuration
@ComponentScan("com.moseeker.baseorm")
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment env;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean(destroyMethod = "close")
    public DataSource getDataSource() {
        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
        String driverClass = propertiesReader.get("mycat.classname", String.class);
        String url = propertiesReader.get("mycat.url", String.class);
        String userName = propertiesReader.get("mycat.userName", String.class);
        String password = propertiesReader.get("mycat.password", String.class);
        Integer minConnections = propertiesReader.get("mycat.minConnections", Integer.class);
        Integer maxConnections = propertiesReader.get("mycat.maxConnections", Integer.class);
        Integer idleMaxAgeInMinutes = propertiesReader.get("mycat.idleMaxAgeInMinutes", Integer.class);
        Integer acquireRetryDelayInMs = propertiesReader.get("mycat.acquireRetryDelayInMs", Integer.class);
        Integer acquireRetryAttempts = propertiesReader.get("mycat.acquireRetryAttempts", Integer.class);

        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();

        boneCPDataSource.setJdbcUrl(url);
        boneCPDataSource.setDriverClass(driverClass);
        boneCPDataSource.setUser(userName);
        boneCPDataSource.setUsername(userName);
        boneCPDataSource.setPassword(password);
        boneCPDataSource.setMinConnectionsPerPartition(minConnections);
        boneCPDataSource.setMaxConnectionsPerPartition(maxConnections);
        boneCPDataSource.setIdleMaxAgeInMinutes(idleMaxAgeInMinutes);
        boneCPDataSource.setAcquireRetryDelayInMs(acquireRetryDelayInMs);
        boneCPDataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        return boneCPDataSource;
    }

    @Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(getDataSource());
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(lazyConnectionDataSource());
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public ExceptionTranslator jooqToSpringExceptionTransformer() {
        return new ExceptionTranslator();
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(
                jooqToSpringExceptionTransformer()
        ));

        ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();

        String sqlDialectName = propertiesReader.get("mycat.dialect", String.class);
        logger.info("sqlDialectName:" + sqlDialectName);
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);
        return jooqConfiguration;
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(getDataSource());
        return initializer;
    }
}
