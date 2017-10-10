package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrAppExportFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppExportFieldsRecord;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppExportFieldsDO;

/**
* @author xxx
* HrAppExportFieldsDao 实现类 （groovy 生成）
* 2017-09-07
*/
@Repository
public class HrAppExportFieldsDao extends JooqCrudImpl<HrAppExportFieldsDO, HrAppExportFieldsRecord> {


   public HrAppExportFieldsDao() {
        super(HrAppExportFields.HR_APP_EXPORT_FIELDS, HrAppExportFieldsDO.class);
   }
}
