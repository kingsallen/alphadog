package com.moseeker.demo.config;

import com.jolbox.bonecp.BoneCPDataSource;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.demo.exception.ExceptionTranslator;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment env;

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

    /*@Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());

        return dataSourceTransactionManager;
    }*/

    /*@Bean(name="transactionAwareDataSource")
    public DataSource transactionAwareDataSourceProxy() {
        TransactionAwareDataSourceProxy transactionAwareDataSourceProxy = new TransactionAwareDataSourceProxy(dataSource());
        return transactionAwareDataSourceProxy;
    }*/

    /*@Bean(name="connectionProvider")
    public DataSourceConnectionProvider connectionProvider() {
        DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(transactionAwareDataSourceProxy());
        return dataSourceConnectionProvider;
    }*/

    /*@Bean(name="dsl")
    public DefaultDSLContext defaultDSLContext() {
        DefaultDSLContext defaultDSLContext = new DefaultDSLContext(connectionProvider(), SQLDialect.MYSQL);
        return defaultDSLContext;
    }

    @Bean(name = "exceptionTranslator")
    public ExceptionTranslator exceptionTranslator() {
        ExceptionTranslator exceptionTranslator = new ExceptionTranslator();
        return exceptionTranslator;
    }

    @Bean(name="defaultExecuteListenerProvider")
    public DefaultExecuteListenerProvider defaultExecuteListenerProvider() {
        DefaultExecuteListenerProvider defaultExecuteListenerProvider = new DefaultExecuteListenerProvider(exceptionTranslator());
        return defaultExecuteListenerProvider;
    }*/

    /*@Bean(name="config")
    public DefaultConfiguration defaultConfiguration() {
        DefaultExecuteListenerProvider exceptionTranslator = defaultExecuteListenerProvider();
        ExecuteListenerProvider[] listenerProviders = {exceptionTranslator};
        //DefaultConfiguration defaultConfiguration = new DefaultConfiguration(connectionProvider(), null, null, listenerProviders, null, SQLDialect.MYSQL, null, null);
        //return defaultConfiguration;
    }*/


    /*<!-- Configure the DSL object, optionally overriding jOOQ Exceptions with Spring Exceptions -->
    <bean id="dsl" class="org.jooq.impl.DefaultDSLContext">
        <constructor-arg ref="config" />
    </bean>


    <!-- Invoking an internal, package-private constructor for the example
    Implement your own Configuration for more reliable behaviour -->
    <bean class="org.jooq.impl.DefaultConfiguration" name="config">
        <constructor-arg index="0" ref="connectionProvider" />
        <constructor-arg index="1"><null /></constructor-arg>
        <constructor-arg index="2"><null /></constructor-arg>
        <constructor-arg index="3">
            <list>
                <bean class="org.jooq.impl.DefaultExecuteListenerProvider">
                    <constructor-arg index="0" ref="exceptionTranslator"/>
                </bean>
            </list>
        </constructor-arg>
        <constructor-arg index="4"><null /></constructor-arg>
        <constructor-arg index="5"><value type="org.jooq.SQLDialect">H2</value></constructor-arg>
        <constructor-arg index="6"><null /></constructor-arg>
        <constructor-arg index="7"><null /></constructor-arg>
    </bean>*/
}
