package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Service
public class Dict51OccupationDao extends BaseDaoImpl<Dict_51jobOccupationRecord, Dict_51jobOccupation>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=Dict_51jobOccupation.DICT_51JOB_OCCUPATION;
	}

}
