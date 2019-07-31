package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;

import java.util.List;

import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyMobotConfDO;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrCompanyConfDao extends JooqCrudImpl<HrCompanyConfDO, HrCompanyConfRecord> {

    public HrCompanyConfDao() {
        super(HrCompanyConf.HR_COMPANY_CONF, HrCompanyConfDO.class);
    }

	public HrCompanyConfDao(TableImpl<HrCompanyConfRecord> table, Class<HrCompanyConfDO> hrCompanyConfDOClass) {
		super(table, hrCompanyConfDOClass);
	}
	
	/*
	获取hrcompanyConf的列表
	 */
	public List<HrCompanyConfDO> getHrCompanyConfByCompanyIds(List<Integer> ids){
		if(StringUtils.isEmptyList(ids)){
			return null;
		}
		Query query=new Query.QueryBuilder().where(new Condition("company_id",ids.toArray(),ValueOp.IN)).and("newjd_status",2)
				.buildQuery();
		 List<HrCompanyConfDO> list=this.getDatas(query);
		return list;
		
	}

	/*
	获取hrcompanyConf的列表
	 */
	public HrCompanyConfDO getHrCompanyConfByCompanyId(int id){
		if(id == 0){
			return null;
		}
		Query query=new Query.QueryBuilder().where("company_id",id)
				.buildQuery();
		HrCompanyConfDO companyConfDO=this.getData(query);
		return companyConfDO;

	}

	public int updateMallSwitch(int companyId, int state) {
		return create.update(HrCompanyConf.HR_COMPANY_CONF)
				.set(HrCompanyConf.HR_COMPANY_CONF.MALL_SWITCH, (byte)state)
				.where(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID.eq(companyId))
				.execute();
	}

	public int updateMallDefaultRule(int companyId, int state, String rule) {
		return create.update(HrCompanyConf.HR_COMPANY_CONF)
				.set(HrCompanyConf.HR_COMPANY_CONF.MALL_GOODS_METHOD_STATE, (byte)state)
				.set(HrCompanyConf.HR_COMPANY_CONF.MALL_GOODS_METHOD, rule)
				.where(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID.eq(companyId))
				.execute();
	}

	public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf getConfbyCompanyId(int id){
		if(id == 0){
			return null;
		}
		com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf data=create.selectFrom(HrCompanyConf.HR_COMPANY_CONF).where(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID.eq(id)).fetchOneInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf.class);
		return data;

	}

    public HrCompanyMobotConfDO getMobotConf(int companyId) {
		return create.select(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID,HrCompanyConf.HR_COMPANY_CONF.MOBOT_HEAD_IMG,HrCompanyConf.HR_COMPANY_CONF.MOBOT_NAME,HrCompanyConf.HR_COMPANY_CONF.MOBOT_WELCOME)
				.from(HrCompanyConf.HR_COMPANY_CONF)
				.where(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID.eq(companyId))
				.fetchOneInto(HrCompanyMobotConfDO.class);
    }

	public int updateMobotConf(HrCompanyMobotConfDO mobotConf) {
		HrCompanyConfRecord record = new HrCompanyConfRecord();
		record.setCompanyId(mobotConf.getCompanyId());

		create.update(HrCompanyConf.HR_COMPANY_CONF);
		if(mobotConf.getMobotHeadImg() != null) {
			record.setMobotHeadImg(mobotConf.getMobotHeadImg());
		}

		if(mobotConf.getMobotName() != null) {
			record.setMobotName(mobotConf.getMobotName());
		}

		if(mobotConf.getMobotWelcome() != null) {
			record.setMobotWelcome(mobotConf.getMobotWelcome());
		}

		create.attach(record);
		return record.update();
	}
}
