package com.moseeker.useraccounts.config;

import java.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于neo4j的事务管理
 * Created by moseeker on 2018/12/20.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(value = "neo4jTransactionManager")
public @interface Neo4jTransactional {
}
