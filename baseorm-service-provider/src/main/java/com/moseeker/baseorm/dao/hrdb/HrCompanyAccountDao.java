package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;

/**
* @author xxx
* HrCompanyAccountDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrCompanyAccountDao extends StructDaoImpl<HrCompanyAccountDO, HrCompanyAccountRecord, HrCompanyAccount> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrCompanyAccount.HR_COMPANY_ACCOUNT;
   }
}
