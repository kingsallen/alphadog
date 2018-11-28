/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb;


import com.moseeker.baseorm.db.candidatedb.tables.CandidateApplicationReferral;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRemark;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateSuggestPosition;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in candidatedb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 申请时推荐链路信息
     */
    public static final CandidateApplicationReferral CANDIDATE_APPLICATION_REFERRAL = com.moseeker.baseorm.db.candidatedb.tables.CandidateApplicationReferral.CANDIDATE_APPLICATION_REFERRAL;

    /**
     * 候选人表
     */
    public static final CandidateCompany CANDIDATE_COMPANY = com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany.CANDIDATE_COMPANY;

    /**
     * 候选人表相关职位表
     */
    public static final CandidatePosition CANDIDATE_POSITION = com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition.CANDIDATE_POSITION;

    /**
     * 用户分享职位访问记录
     */
    public static final CandidatePositionShareRecord CANDIDATE_POSITION_SHARE_RECORD = com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD;

    /**
     * 候选人推荐记录表
     */
    public static final CandidateRecomRecord CANDIDATE_RECOM_RECORD = com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord.CANDIDATE_RECOM_RECORD;

    /**
     * HR对候选人备注信息信息
     */
    public static final CandidateRemark CANDIDATE_REMARK = com.moseeker.baseorm.db.candidatedb.tables.CandidateRemark.CANDIDATE_REMARK;

    /**
     * 链路信息表
     */
    public static final CandidateShareChain CANDIDATE_SHARE_CHAIN = com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain.CANDIDATE_SHARE_CHAIN;

    /**
     * HR手动添加相关职位表
     */
    public static final CandidateSuggestPosition CANDIDATE_SUGGEST_POSITION = com.moseeker.baseorm.db.candidatedb.tables.CandidateSuggestPosition.CANDIDATE_SUGGEST_POSITION;
}
