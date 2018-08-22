package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.config.HrAwardConfigTemplate;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ConfigSysPointsConfTplDao 实现类 （groovy 生成）
* 2017-03-20
*/
@Repository
public class ConfigSysPointsConfTplDao extends JooqCrudImpl<ConfigSysPointsConfTplDO, ConfigSysPointsConfTplRecord> {

    public ConfigSysPointsConfTplDao() {
        super(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, ConfigSysPointsConfTplDO.class);
    }

	public ConfigSysPointsConfTplDao(TableImpl<ConfigSysPointsConfTplRecord> table, Class<ConfigSysPointsConfTplDO> configSysPointsConfTplDOClass) {
		super(table, configSysPointsConfTplDOClass);
	}

   public List<HrAwardConfigTemplate> findRecruitProcesses(int companyId) throws Exception{
		List<HrAwardConfigTemplate> list=new ArrayList<HrAwardConfigTemplate>();
		SelectOnConditionStep<Record9<Integer, Integer, Integer, Integer, Integer, Integer, String, Long, Integer>> table =create.select(
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.DISABLE,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.PRIORITY,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.TYPE_ID,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.STATUS,
				HrPointsConf.HR_POINTS_CONF.REWARD, HrPointsConf.HR_POINTS_CONF.ID)
		.from(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
		.leftJoin(HrPointsConf.HR_POINTS_CONF).on("hrdb.hr_points_conf.template_id=configdb.config_sys_points_conf_tpl.id ").and(HrPointsConf.HR_POINTS_CONF.COMPANY_ID.eq(companyId));
		Result<Record9<Integer, Integer, Integer, Integer, Integer, Integer, String, Long, Integer>> result=table.fetch();
		if(result!=null&&result.size()>0){
			HrAwardConfigTemplate config=null;
			for(Record9<Integer, Integer, Integer, Integer, Integer, Integer, String, Long, Integer> r:result){
				config=new HrAwardConfigTemplate();
				config.setId(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID));
				config.setAward(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD));
				config.setDisable(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.DISABLE));
				config.setPriority(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.PRIORITY));
				config.setRecruit_order(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER));
				config.setType_id(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.TYPE_ID));
				config.setStatus(r.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.STATUS));
				if(r.getValue(HrPointsConf.HR_POINTS_CONF.REWARD)==null){
					 config.setReward(0);
				}else{
					 config.setReward(r.getValue(HrPointsConf.HR_POINTS_CONF.REWARD));
				}
                config.setPoints_conf_id(r.getValue(HrPointsConf.HR_POINTS_CONF.ID) == null ? 0 : r.getValue(HrPointsConf.HR_POINTS_CONF.ID));
				list.add(config);
			}
		}
		return list;
	}


    public ConfigSysPointsConfTplRecord getEmployeeVerified() {
    	return
				create.selectFrom(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
				.where(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.STATUS.eq(Constant.POINTS_CONF_EMPLOYEE_VERIFIED))
				.limit(1)
				.fetchOne();
    }
}
