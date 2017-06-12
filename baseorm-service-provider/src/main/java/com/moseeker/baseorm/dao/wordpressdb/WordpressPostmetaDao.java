package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPostmeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.dao.struct.PostExt;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressPostmetaDO;
import org.apache.thrift.TException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WordpressPostmetaDao extends JooqCrudImpl<WordpressPostmetaDO, WordpressPostmetaRecord> {

	public WordpressPostmetaDao() {
		super(WordpressPostmeta.WORDPRESS_POSTMETA, WordpressPostmetaDO.class);
	}

	public WordpressPostmetaDao(TableImpl<WordpressPostmetaRecord> table, Class<WordpressPostmetaDO> wordpressPostmetaDOClass) {
		super(table, wordpressPostmetaDOClass);
	}

	public List<WordpressPostmetaRecord> getPostExt(long objectId, List<String> keys) {
		List<WordpressPostmetaRecord> records = null;
			records = create.selectFrom(WordpressPostmeta.WORDPRESS_POSTMETA).where(WordpressPostmeta.WORDPRESS_POSTMETA.POST_ID
					.equal((long)(objectId)).and(WordpressPostmeta.WORDPRESS_POSTMETA.META_KEY.in(keys)))
					.fetch();
		return records;
	}

    public PostExt getPostExt(long objectId) throws TException {
        PostExt postExt = new PostExt();
        try {
            List<String> keys = new ArrayList<>();
            keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_VERSION);
            keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_PLATFORM);
            List<WordpressPostmetaRecord> records = getPostExt(objectId, keys);
            if(records != null) {
                postExt.setObjectId(objectId);
                records.forEach(record -> {
                    if(record.getMetaKey() != null && record.getMetaKey().equals(Constant.WORDPRESS_POST_CUSTOMFIELD_VERSION)) {
                        postExt.setVersion(record.getMetaValue());
                    }
                    if(record.getMetaKey() != null && record.getMetaKey().equals(Constant.WORDPRESS_POST_CUSTOMFIELD_PLATFORM)) {
                        postExt.setPlatform(record.getMetaValue());
                    }
                });
                return postExt;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return postExt;
    }
}
