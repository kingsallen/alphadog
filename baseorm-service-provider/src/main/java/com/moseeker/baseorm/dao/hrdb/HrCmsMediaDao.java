package com.moseeker.baseorm.dao.hrdb;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsMedia;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsMediaRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsMediaDO;
@Service
public class HrCmsMediaDao extends JooqCrudImpl<HrCmsMediaDO, HrCmsMediaRecord> {
	
	public HrCmsMediaDao() {
		super(HrCmsMedia.HR_CMS_MEDIA, HrCmsMediaDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsMediaDao(TableImpl<HrCmsMediaRecord> table, Class<HrCmsMediaDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}
	 /*
	  * 根据module.id的列表获取HrCmsMdeia列表
	  */
	 public List<HrCmsMediaDO> getHrCmsMediaDOByModuleIdList(List<Integer> ids){
		 Query query=new Query.QueryBuilder().where(new Condition("module_id",ids.toArray(),ValueOp.IN)).and("disable",0).and("is_show",0)
					.orderBy("module_id").orderBy("orders").buildQuery();
			 List<HrCmsMediaDO> list=this.getDatas(query);
			return list;
	 }
}
