package com.moseeker.candidate.thrift;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import com.moseeker.thrift.gen.candidate.struct.CandidateList;
import com.moseeker.thrift.gen.candidate.struct.CandidateListParam;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 16/02/2017.
 */
@Service
public class CandidateThriftService implements CandidateService.Iface {

    @Autowired
    private Candidate candidate;

    @Override
    public void glancePosition(int userId, int positionId, int shareChainId) throws TException {
        candidate.glancePosition(userId, positionId, shareChainId);
    }
    
    @Override
    public Response changeInteresting(int user_id, int position_id, byte is_interested) throws TException {
    		return candidate.changeInteresting(user_id, position_id, is_interested);
    }

    @Override
    public List<CandidateList> candidateList(CandidateListParam param) throws BIZException, TException {
        return candidate.candidateList(param);
    }

}
