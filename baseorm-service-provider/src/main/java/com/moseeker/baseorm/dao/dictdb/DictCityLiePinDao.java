package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCityLiepin;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityLiepinRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityLiePinDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author cjm
 *         DictCityLiePinDao 实现类
 *         2018-6-4
 */
@Repository
public class DictCityLiePinDao extends JooqCrudImpl<DictCityLiePinDO, DictCityLiepinRecord> {

    public DictCityLiePinDao() {
        super(DictCityLiepin.DICT_CITY_LIEPIN, DictCityLiePinDO.class);
    }

    public DictCityLiePinDao(TableImpl<DictCityLiepinRecord> table, Class<DictCityLiePinDO> dictCityLiePinDOClass) {
        super(table, dictCityLiePinDOClass);
    }


    public Result getLiepinDictCodeByPid(int pid) {

        return create.select(DictCityLiepin.DICT_CITY_LIEPIN.CODE)
                .from(DictCityLiepin.DICT_CITY_LIEPIN)
                .join(JobPositionCity.JOB_POSITION_CITY)
                .on(DictCityLiepin.DICT_CITY_LIEPIN.OTHERCODE.eq(JobPositionCity.JOB_POSITION_CITY.CODE.toString()))
                .where(JobPositionCity.JOB_POSITION_CITY.PID.eq(pid))
                .fetch();
    }

    public DictCityLiePinDO getLiepinDictCodeByCode(String code) {
        Query query = new Query.QueryBuilder()
                .where(new Condition(DictCityLiepin.DICT_CITY_LIEPIN.OTHERCODE.getName(), code))
                .buildQuery();
        return getData(query);
    }
}
