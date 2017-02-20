package com.moseeker.candidate.service.dao;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 候选人
 * Created by jack on 10/02/2017.
 */
public class CandidateDBDao {

    private static com.moseeker.thrift.gen.dao.service.CandidateDBDao.Iface candidateDBDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.CandidateDBDao.Iface.class);

    private static com.moseeker.thrift.gen.dao.service.UserDBDao.Iface userDBDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.UserDBDao.Iface.class);

    private static com.moseeker.thrift.gen.dao.service.JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.JobDBDao.Iface.class);

    public static Optional<CandidateCompanyDO> getCandidateCompanyByUserIDCompanyID(int userID, int companyId) throws TException {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("user_id", String.valueOf(userID));
        queryUtil.addEqualFilter("company_id", String.valueOf(companyId));
        try {
            CandidateCompanyDO candidateCompanyDO = candidateDBDao.getCandidateCompany(queryUtil);
            return Optional.of(candidateCompanyDO);
        } catch (CURDException e) {
            if(e.getCode() != 90010) {
                throw e;
            } else {
                return Optional.of(null);
            }
        }
    }

    public static void updateCandidateCompany(CandidateCompanyDO candidateCompany) throws TException {
        candidateCompany = candidateDBDao.updateCandidateCompanys(candidateCompany);
    }

    public static Optional<JobPositionDO> getPositionByID(int positionID) throws TException {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", String.valueOf(positionID));
        try {
            JobPositionDO positionDO = jobDBDao.getPosition(queryUtil);
            return Optional.of(positionDO);
        } catch (CURDException e) {
            if(e.getCode() != 90010) {
                throw e;
            } else {
                return Optional.of(null);
            }
        }
    }

    public static Optional<UserUserDO> getUserByID(int userID) throws TException {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", String.valueOf(userID));
        try {
            UserUserDO userUserDO = userDBDao.getUser(queryUtil);
            return Optional.of(userUserDO);
        } catch (CURDException e) {
            if(e.getCode() != 90010) {
                throw e;
            } else {
                return Optional.of(null);
            }
        }
    }

    public static List<CandidateRemarkDO> getCandidateRemarks(int userID, int companyId) throws TException {
        List<CandidateRemarkDO> remarkDOList = new ArrayList<>();
        List<UserHrAccountDO> hrs = new ArrayList<>();
        try {
            hrs = userDBDao.listHRFromCompany(companyId);
        } catch (CURDException e) {
            if(e.getCode() != 90010) {
                throw e;
            } else {
                //return Optional.of(null);
            }
        }
        if(hrs.size() > 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("user_id", String.valueOf(userID));
            StringBuffer hraccountIds = new StringBuffer("[");
            hrs.forEach(i -> hraccountIds.append(i.getId()).append(","));
            hraccountIds.deleteCharAt(hraccountIds.length() - 1).append("]");
            qu.addEqualFilter("hraccount_id", hraccountIds.toString());
            remarkDOList = candidateDBDao.listCandidateRemarks(qu);
        }
        return remarkDOList;
    }

    public static void saveCandidatePosition(CandidatePositionDO cp) throws TException {
        cp = candidateDBDao.updateCandidatePosition(cp);
    }

    public static Optional<CandidatePositionDO> getCandidatePosition(int positionID, int userID) {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("user_id", String.valueOf(userID));
        qu.addEqualFilter("position_id", String.valueOf(positionID));
        try {
            CandidatePositionDO candidatePositionDO = candidateDBDao.getCandidatePosition(qu);
            return Optional.of(candidatePositionDO);
        } catch (TException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static void updateCandidatePosition(CandidatePositionDO cp) throws TException {
        cp = candidateDBDao.updateCandidatePosition(cp);
    }

    public static void updateCandidateRemarks(List<CandidateRemarkDO> crs) throws TException {
        candidateDBDao.updateCandidateRemarks(crs);
    }
}
