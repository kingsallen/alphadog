package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.common.util.query.Query;
import com.moseeker.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AwardConfigTplDao extends JooqCrudImpl<ConfigSysPointsConfTplDO, ConfigSysPointsConfTplRecord> {

	public AwardConfigTplDao(TableImpl<ConfigSysPointsConfTplRecord> table, Class<ConfigSysPointsConfTplDO> configSysPointsConfTplDOClass) {
		super(table, configSysPointsConfTplDOClass);
	}

	public List<ConfigSysPointsConfTplDO> getAwardConfigTpls(Query query) {
		return getDatas(query);
	}
}
