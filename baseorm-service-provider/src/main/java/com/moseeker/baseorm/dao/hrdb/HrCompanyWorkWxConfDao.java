package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyWorkwxConf;
import com.moseeker.baseorm.db.hrdb.tables.daos.HrCompanyWorkwxConfDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyWorkwxConfRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HrCompanyWorkWxConfDao extends JooqCrudImpl<HrCompanyWorkWxConfDO, HrCompanyWorkwxConfRecord> {

    public HrCompanyWorkWxConfDao() {
        super(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF, HrCompanyWorkWxConfDO.class);
    }

	public HrCompanyWorkWxConfDao(TableImpl<HrCompanyWorkwxConfRecord> table, Class<HrCompanyWorkWxConfDO> HrCompanyWorkWxConfDOClass) {
		super(table, HrCompanyWorkWxConfDOClass);
	}

	/*
	  * 获取hrcompanyConf的列表
	 */
	public HrCompanyWorkWxConfDO getByCompanyId(int companyId){
		Query query=new Query.QueryBuilder().where("company_id", companyId)
				.and(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.DISABLE.getName(), 0).buildQuery();
		getData(query);
		return getData(query);

	}

}
