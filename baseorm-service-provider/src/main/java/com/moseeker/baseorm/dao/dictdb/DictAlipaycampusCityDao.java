package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusCityRecord;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictAlipaycampusCityDO;

/**
* @author xxx
* DictAlipaycampusCityDao 实现类 （groovy 生成）
* 2017-07-11
*/
@Repository
public class DictAlipaycampusCityDao extends JooqCrudImpl<DictAlipaycampusCityDO, DictAlipaycampusCityRecord> {


   public DictAlipaycampusCityDao() {
        super(DictAlipaycampusCity.DICT_ALIPAYCAMPUS_CITY, DictAlipaycampusCityDO.class);
   }
}
