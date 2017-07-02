package com.moseeker.baseorm.dao.hrdb;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsModule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsModuleRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsModuleDO;
@Service
public class HrCmsModuleDao extends JooqCrudImpl<HrCmsModuleDO, HrCmsModuleRecord> {
	
	public HrCmsModuleDao() {
		super(HrCmsModule.HR_CMS_MODULE, HrCmsModuleDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsModuleDao(TableImpl<HrCmsModuleRecord> table, Class<HrCmsModuleDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}
	 /*
	  * 根据page的列表，获取hrcmsmodule列表
	  */
	 public List<HrCmsModuleDO> getHrCmsModuleDOBypageIdList(List<Integer> ids){
		 Query query=new Query.QueryBuilder().where(new Condition("page_id",ids.toArray(),ValueOp.IN)).and("disable",0)
					.orderBy("page_id").orderBy("order").buildQuery();
			 List<HrCmsModuleDO> list=this.getDatas(query);
			return list;
	 }
}
