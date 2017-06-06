package com.moseeker.candidate.service.dao;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by jack on 31/05/2017.
 */
public class CandidateDBDaoTest {

//    private static com.moseeker.thrift.gen.dao.service.CandidateDBDao.Iface candidateDBDao = ServiceManager.SERVICEMANAGER
//            .getService(com.moseeker.thrift.gen.dao.service.CandidateDBDao.Iface.class);
//    //@Test
//    public void getCandidateCompanyByUserIDCompanyID() throws Exception {
//
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("sys_user_id", String.valueOf(36));
//        queryUtil.addEqualFilter("company_id", String.valueOf(39978));
//        try {
//            CandidateCompanyDO candidateCompanyDO = candidateDBDao.getCandidateCompany(queryUtil);
//            System.out.println("candidateCompanyDO:"+candidateCompanyDO);
//        } catch (CURDException e) {
//            if(e.getCode() != 90010) {
//                e.printStackTrace();
//            } else {
//                System.out.println("未能找到任何数据");
//            }
//
//        }
//    }

}