package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.ConfigSysPointConfTplDO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AwardConfigTplDao extends BaseDaoImpl<ConfigSysPointsConfTplRecord, ConfigSysPointsConfTpl> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL;
	}

	public List<ConfigSysPointConfTplDO> getAwardConfigTpls(CommonQuery query) {
		List<ConfigSysPointConfTplDO> tpls = new ArrayList<>();
		
		try {
			List<ConfigSysPointsConfTplRecord> records = getResources(query);
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
