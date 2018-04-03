package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyCompanyChannelConf;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyCompanyChannelConfRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyCompanyChannelConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ThirdpartyCompanyChannelConfDao extends JooqCrudImpl<ThirdpartyCompanyChannelConfDO, ThirdpartyCompanyChannelConfRecord> {

    public ThirdpartyCompanyChannelConfDao() {
        super(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF, ThirdpartyCompanyChannelConfDO.class);
    }


    public ThirdpartyCompanyChannelConfDao(TableImpl<ThirdpartyCompanyChannelConfRecord> table, Class<ThirdpartyCompanyChannelConfDO> doClass) {
        super(table, doClass);
    }

    /**
     * 根据公司ID获取配置的渠道号
     * @param companyId 公司ID
     * @return
     */
    public List<ThirdpartyCompanyChannelConfDO> getConfByCompanyId(int companyId){
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.COMPANY_ID.getName(), companyId).buildQuery();
        return getDatas(query);
    }
}
