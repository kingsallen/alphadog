package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress;
import com.moseeker.baseorm.db.malldb.tables.records.MallMailAddressRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.malldb.tables.MallMailAddress.MALL_MAIL_ADDRESS;
import static org.jooq.impl.DSL.using;


/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-10-30
 **/
@Repository
public class MallMailAddressDao extends com.moseeker.baseorm.db.malldb.tables.daos.MallMailAddressDao {

    @Autowired
    private DSLContext create;

    public MallMailAddressDao(Configuration configuration) {
        super(configuration);
    }

    public MallMailAddress save(MallMailAddress address){
        MallMailAddressRecord record = new MallMailAddressRecord();
        BeanUtils.copyProperties(address,record);
        create.execute("set names utf8mb4");
        create.attach(record);
        record.insert();
        return record.into(MallMailAddress.class);
    }

    public List<MallMailAddress> getAddressByIdList(List<Integer> ids){
        return create.selectFrom(MALL_MAIL_ADDRESS)
                .where(MALL_MAIL_ADDRESS.ID.in(ids))
                .fetchInto(MallMailAddress.class);
    }
}
