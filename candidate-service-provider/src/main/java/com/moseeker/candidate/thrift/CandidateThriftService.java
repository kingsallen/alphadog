package com.moseeker.candidate.thrift;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import com.moseeker.thrift.gen.candidate.struct.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.Response;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 16/02/2017.
 */
@Service
public class CandidateThriftService implements CandidateService.Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Candidate candidate;

    @Override
    public void glancePosition(int userId, int positionId, int shareChainId) throws TException {
        try {
            candidate.glancePosition(userId, positionId, shareChainId);
        }  catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response changeInteresting(int user_id, int position_id, byte is_interested) throws TException {
        try {
            return candidate.changeInteresting(user_id, position_id, is_interested);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public List<CandidateList> candidateList(CandidateListParam param) throws BIZException, TException {
        try {
            return candidate.candidateList(param);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public RecommendResult getRecomendations(int companyId, List<Integer> idList) throws BIZException, TException {
        try {
            return candidate.getRecommendations(companyId, idList);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public RecommendResult recommend(RecommmendParam param) throws BIZException, TException {
        try {
            return candidate.recommend(param);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public RecomRecordResult getRecommendation(int id, int postUserId) throws BIZException, TException {
        try {
            return candidate.getRecommendation(id, postUserId);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public SortResult getRecommendatorySorting(int postUserId, int companyId) throws BIZException, TException {
        try {
            return candidate.getRecommendatorySorting(postUserId, companyId);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public RecommendResult ignore(int id, int companyId, int postUserId, String clickTime) throws BIZException, TException {
        try {
            return candidate.ignore(id, companyId, postUserId, clickTime);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CURDException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

}
