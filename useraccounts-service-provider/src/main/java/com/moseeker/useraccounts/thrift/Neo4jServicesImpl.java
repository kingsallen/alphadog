package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.neo4j.service.Neo4jServices;
import com.moseeker.useraccounts.service.Neo4jService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2018/12/18.
 */
@Service
public class Neo4jServicesImpl implements Neo4jServices.Iface {

    @Autowired
    Neo4jService service;

    @Override
    public void addNeo4jForWard(int startUserId, int endUserId, int shareChainId) throws BIZException, TException {
        try{
            service.addFriendRelation(startUserId, endUserId, shareChainId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
