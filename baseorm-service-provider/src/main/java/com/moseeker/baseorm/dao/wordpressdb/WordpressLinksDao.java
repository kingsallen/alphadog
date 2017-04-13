package com.moseeker.baseorm.dao.wordpressdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressLinks;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressLinksRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressLinksDO;

/**
* @author xxx
* WordpressLinksDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class WordpressLinksDao extends StructDaoImpl<WordpressLinksDO, WordpressLinksRecord, WordpressLinks> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = WordpressLinks.WORDPRESS_LINKS;
   }
}
