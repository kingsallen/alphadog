package com.moseeker.demo.contant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * Created by jack on 22/03/2017.
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // instantiate, configure and return DataSource
        return null;
    }

    /*@Configuration
    @Import(DataSourceConfig.class)
    public class AppConfig {
        @Inject
        DataSourceConfig dataConfig;

        @Bean
        public MyBean myBean() {
            // reference the dataSource() bean method
            return new MyBean(dataConfig.dataSource());
        }
    }*/
}
