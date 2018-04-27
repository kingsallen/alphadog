package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.AbstractThirdPartyPositionDao;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyZhilianPositionAddress;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyZhilianPositionAddressRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 这个类是thirdparty_zhilian_position_address的dao，
 * 对thirdparty_zhilian_position_address的操作请使用这个对象
 */
@Repository
public class ThirdpartyZhilianPositionAddressDao extends JooqCrudImpl<ThirdpartyZhilianPositionAddressDO, ThirdpartyZhilianPositionAddressRecord> {

    public ThirdpartyZhilianPositionAddressDao() {
        super(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS, ThirdpartyZhilianPositionAddressDO.class);
    }


    public ThirdpartyZhilianPositionAddressDao(TableImpl<ThirdpartyZhilianPositionAddressRecord> table, Class<ThirdpartyZhilianPositionAddressDO> doClass) {
        super(table, doClass);
    }


    public int deleteByPid(int pid){
        if(pid <= 0){
            return 0;
        }
        return create.deleteFrom(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS)
                .where(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS.PID.eq(pid))
                .execute();
    }

    public int deleteByPids(List<Integer> pids){
        if(StringUtils.isEmptyList(pids)){
            return 0;
        }
        return create.deleteFrom(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS)
                .where(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS.PID.in(pids))
                .execute();
    }
}
