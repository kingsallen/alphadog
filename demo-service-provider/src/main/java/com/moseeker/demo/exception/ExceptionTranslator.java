package com.moseeker.demo.exception;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 22/03/2017.
 */
@Component
public class ExceptionTranslator extends DefaultExecuteListener {
    @Override
    public void exception(ExecuteContext ctx) {
        // [#4391] Translate only SQLExceptions
        if (ctx.sqlException() != null) {
            SQLDialect dialect = ctx.dialect();
            SQLExceptionTranslator translator = (dialect != null)
                    ? new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName())
                    : new SQLStateSQLExceptionTranslator();

            ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
        }
    }
}
