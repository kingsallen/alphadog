package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.records.HrGroupCompanyRelRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by YYF
 *
 * Date: 2017/6/28
 *
 * Project_name :alphadog
 */
@Repository
public class HrGroupCompanyRelDao extends JooqCrudImpl<HrGroupCompanyRelDO, HrGroupCompanyRelRecord> {

    public HrGroupCompanyRelDao() {
        super(HrGroupCompanyRel.HR_GROUP_COMPANY_REL, HrGroupCompanyRelDO.class);
    }

    public HrGroupCompanyRelDao(TableImpl<HrGroupCompanyRelRecord> table, Class<HrGroupCompanyRelDO> hrGroupCompanyRelDOClass) {
        super(table, hrGroupCompanyRelDOClass);
    }

    public List<Integer> getGroupCompanyRelDoByCompanyIds(List<Integer> companyIdList){

        if(!StringUtils.isEmptyList(companyIdList)){
            List<HrGroupCompanyRelDO> relDOList = create.selectFrom(HrGroupCompanyRel.HR_GROUP_COMPANY_REL)
                    .where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID.in(companyIdList))
                    .fetchInto(HrGroupCompanyRelDO.class);
            if(!StringUtils.isEmptyList(relDOList)){
                List<Integer> relIdList = relDOList.stream().map(m -> m.getGroupId()).collect(Collectors.toList());
                List<HrGroupCompanyRelDO> relList = create.selectFrom(HrGroupCompanyRel.HR_GROUP_COMPANY_REL)
                        .where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.GROUP_ID.in(relIdList))
                        .fetchInto(HrGroupCompanyRelDO.class);

                companyIdList.addAll(relList.stream().map(m -> m.getCompanyId()).collect(Collectors.toList()));
            }
        }
        return companyIdList;
    }
}
