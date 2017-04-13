package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUsermeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUsermetaRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUsermetaDO;

/**
* @author xxx
* WordpressUsermetaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressUsermetaDao extends StructDaoImpl<WordpressUsermetaDO, WordpressUsermetaRecord, WordpressUsermeta> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressUsermeta.WORDPRESS_USERMETA;
   }
}
