package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.neo4j.service.Neo4jServices;
import com.moseeker.thrift.gen.neo4j.struct.EmployeeCompany;
import com.moseeker.thrift.gen.neo4j.struct.UserDepth;
import com.moseeker.useraccounts.pojo.neo4j.EmployeeCompanyVO;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import com.moseeker.useraccounts.service.Neo4jService;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<EmployeeCompany> fetchUserThreeDepthEmployee(int userId, int companyId) throws BIZException, TException {
        List<EmployeeCompanyVO> employeeCompanyVOS = service.fetchUserThreeDepthEmployee(userId, companyId);
        List<EmployeeCompany> list = new ArrayList<>();
        if(!StringUtils.isEmptyList(employeeCompanyVOS)){
            list = employeeCompanyVOS.stream().map(m -> {
                EmployeeCompany depth = new EmployeeCompany();
                BeanUtils.copyProperties(m, depth);
                return depth;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<UserDepth> fetchEmployeeThreeDepthUser(int userId) throws BIZException, TException {
        List<UserDepthVO> result = service.fetchEmployeeThreeDepthUser(userId);
        List<UserDepth> list = new ArrayList<>();
        if(!StringUtils.isEmptyList(result)){
            list = result.stream().map(m -> {
                UserDepth depth = new UserDepth();
                BeanUtils.copyProperties(m, depth);
                return depth;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<Integer> fetchShortestPath(int startUserId, int endUserId, int companyId) throws BIZException, TException {
        return service.fetchShortestPath(startUserId,  endUserId,  companyId);
    }
}
