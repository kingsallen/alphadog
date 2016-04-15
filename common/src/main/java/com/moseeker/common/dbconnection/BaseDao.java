package com.moseeker.common.dbconnection;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TBase;
import org.jooq.impl.TableImpl;

public abstract class BaseDao<T extends TableImpl> {
	
	public List<T> getList(TBase tbase) {
		List<T> list = new ArrayList<>();
		/*DSLContext create = DatabaseConnectionHelper.getConnection().getJooqDSL();

		SelectJoinStep<Record> table = create.select().from();

		if (query.getLimit() > 0) {
			table.limit(query.getLimit());
		} else {
			table.limit(10);
		}

		if (query.getUserid() > 0) {
			table.where(COMPANYFOLLOWERS.USERID.equal(query.getUserid()));
		}

		Result<Record> result = table.fetch();
		for (Record r : result) {
			// System.out.print(r);
			Companyfollower follower = new Companyfollower();
			follower.setId(r.getValue(COMPANYFOLLOWERS.ID));
			follower.setUserid(r.getValue(COMPANYFOLLOWERS.USERID));
			follower.setCompanyid(r.getValue(COMPANYFOLLOWERS.COMPANYID));
			companyfollowers.add(follower);
		}*/
		return list;
	}
}
