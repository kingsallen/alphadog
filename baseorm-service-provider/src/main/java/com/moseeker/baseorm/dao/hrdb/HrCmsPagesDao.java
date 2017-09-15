package com.moseeker.baseorm.dao.hrdb;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsPages;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsPagesRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsPagesDO;

@Service
public class HrCmsPagesDao extends JooqCrudImpl<HrCmsPagesDO, HrCmsPagesRecord> {
	
	public HrCmsPagesDao() {
		super(HrCmsPages.HR_CMS_PAGES, HrCmsPagesDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsPagesDao(TableImpl<HrCmsPagesRecord> table, Class<HrCmsPagesDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}
	/*
	 * 获取所有的职位配置表通过id和type
	 */
	public List<HrCmsPagesDO> getHrCmsPagesByIds(List<Integer> ids,int type){
		Query query=new Query.QueryBuilder().where(new Condition("config_id",ids.toArray(),ValueOp.IN)).and("disable",0)
				.and("type",type).buildQuery();
		 List<HrCmsPagesDO> list=this.getDatas(query);
		return list;
	}
}
