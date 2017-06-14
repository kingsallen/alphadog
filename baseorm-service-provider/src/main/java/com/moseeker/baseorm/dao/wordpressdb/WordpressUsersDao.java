package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUsers;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUsersRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUsersDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* WordpressUsersDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressUsersDao extends JooqCrudImpl<WordpressUsersDO, WordpressUsersRecord> {

    public WordpressUsersDao() {
        super(WordpressUsers.WORDPRESS_USERS, WordpressUsersDO.class);
    }

    public WordpressUsersDao(TableImpl<WordpressUsersRecord> table, Class<WordpressUsersDO> wordpressUsersDOClass) {
        super(table, wordpressUsersDOClass);
    }
}
