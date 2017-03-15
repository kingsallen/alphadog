package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCertConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;


@Service
public class HrEmployeeCertConfDao extends BaseDaoImpl<HrEmployeeCertConfRecord, HrEmployeeCertConf> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF;
    }
}
