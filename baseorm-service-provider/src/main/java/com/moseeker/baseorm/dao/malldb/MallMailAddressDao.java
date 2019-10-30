package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress;
import com.moseeker.baseorm.db.malldb.tables.records.MallMailAddressRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.malldb.Tables.MALL_MAIL_ADDRESS;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-10-30
 **/
@Repository
public class MallMailAddressDao extends JooqCrudImpl<MallMailAddress, MallMailAddressRecord> {


    public MallMailAddressDao(TableImpl<MallMailAddressRecord> table, Class<MallMailAddress> mallMailAddressClass) {
        super(table, mallMailAddressClass);
    }

    public MallMailAddressDao() {
        super(MALL_MAIL_ADDRESS,MallMailAddress.class);
    }
}
