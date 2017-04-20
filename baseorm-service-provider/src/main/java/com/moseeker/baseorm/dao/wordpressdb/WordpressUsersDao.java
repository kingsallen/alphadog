package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUsers;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUsersRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUsersDO;

/**
* @author xxx
* WordpressUsersDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressUsersDao extends StructDaoImpl<WordpressUsersDO, WordpressUsersRecord, WordpressUsers> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressUsers.WORDPRESS_USERS;
   }
}
