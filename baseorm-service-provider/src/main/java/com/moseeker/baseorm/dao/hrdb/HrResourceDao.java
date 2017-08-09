package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrResourceDO;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrResourceDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrResourceDao extends JooqCrudImpl<HrResourceDO, HrResourceRecord> {

    public HrResourceDao() {
        super(HrResource.HR_RESOURCE, HrResourceDO.class);
    }

    public HrResourceDao(TableImpl<HrResourceRecord> table, Class<HrResourceDO> hrResourceDOClass) {
        super(table, hrResourceDOClass);
    }
	 /*
	  * 通过res.id列表获取资源列表
	  */
	 public List<HrResourceDO> getHrCmsResourceByIdList(List<Integer> ids){
			Query query=new Query.QueryBuilder().where(new Condition("id",ids.toArray(),ValueOp.IN)).and("disable",0).buildQuery();
		    List<HrResourceDO> list=this.getDatas(query);
			return list;
	 }
}
