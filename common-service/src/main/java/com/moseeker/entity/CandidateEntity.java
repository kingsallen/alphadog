package com.moseeker.entity;

import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 潜在候选人基础业务
 * Created by jack on 17/08/2017.
 */
@Service
@CounterIface
public class CandidateEntity {

    @Autowired
    CandidateCompanyDao candidateCompanyDao;

    /**
     * 查找公司下正常的潜在候选人
     * @param companyID 公司编号
     * @return 潜在候选人列表
     * @throws CommonException
     */
    public List<CandidateCompanyDO> getCandidateCompanyByCompanyID(int companyID) throws CommonException {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(CandidateCompany.CANDIDATE_COMPANY.COMPANY_ID.getName(), companyID))
                .and(CandidateCompany.CANDIDATE_COMPANY.STATUS.getName(), AbleFlag.ENABLE.getValue());
        return candidateCompanyDao.getDatas(queryBuilder.buildQuery());
    }
}
