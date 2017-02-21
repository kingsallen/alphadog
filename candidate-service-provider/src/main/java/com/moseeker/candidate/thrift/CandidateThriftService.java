package com.moseeker.candidate.thrift;

import com.moseeker.candidate.service.Candidate;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
