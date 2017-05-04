package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrSearchConditionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrSearchConditionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrSearchConditionDao extends JooqCrudImpl<HrSearchConditionDO, HrSearchConditionRecord> {


	public HrSearchConditionDao(TableImpl<HrSearchConditionRecord> table, Class<HrSearchConditionDO> hrSearchConditionDOClass) {
		super(table, hrSearchConditionDOClass);
	}

   public int delResource(int hrAccountId, int id) {
		Query query =  new Query.QueryBuilder().where("hr_account_id", hrAccountId).and("id", id).buildQuery();
		try {
			HrSearchConditionRecord record = getRecord(query);
			return deleteRecord(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}
}
