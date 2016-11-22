package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
@Service
public class DictZpinOccupationDao extends BaseDaoImpl<DictZhilianOccupationRecord, DictZhilianOccupation>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION;
	}

}
