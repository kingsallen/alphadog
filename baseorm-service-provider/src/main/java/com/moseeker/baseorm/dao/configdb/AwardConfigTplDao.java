package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.thrift.gen.dao.struct.ConfigSysPointConfTplDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AwardConfigTplDao extends JooqCrudImpl<ConfigSysPointsConfTplDO, ConfigSysPointsConfTplRecord> {

	public AwardConfigTplDao(TableImpl<ConfigSysPointsConfTplRecord> table, Class<ConfigSysPointsConfTplDO> configSysPointsConfTplDOClass) {
		super(table, configSysPointsConfTplDOClass);
	}

	public List<ConfigSysPointConfTplDO> getAwardConfigTpls(Query query) {
		List<ConfigSysPointConfTplDO> tpls = new ArrayList<>();
		
		try {
			List<ConfigSysPointsConfTplRecord> records = getRecords(query);
			if(records != null) {
				tpls = BeanUtils.DBToStruct(ConfigSysPointConfTplDO.class, records);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return tpls;
	}
}
