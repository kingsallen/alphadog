package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;

import java.util.List;

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
		Query query=new Query.QueryBuilder().where(new Condition("company_id",ids.toArray(),ValueOp.IN)).and("newjd_status",2)
				.buildQuery();
		 List<HrCompanyConfDO> list=this.getDatas(query);
		return list;
		
	}
}
