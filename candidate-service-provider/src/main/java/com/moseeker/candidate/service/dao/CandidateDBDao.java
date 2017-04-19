package com.moseeker.candidate.service.dao;

import com.moseeker.candidate.constant.EmployeeType;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private static com.moseeker.thrift.gen.dao.service.HrDBDao.Iface hrDBDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.HrDBDao.Iface.class);

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

    public static void updateCandidateCompany(CandidateCompanyDO candidateCompany) throws TException  {
        try {
            candidateDBDao.updateCandidateCompanys(candidateCompany);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static CandidateCompanyDO saveCandidateCompany(CandidateCompanyDO candidateCompanyDO) {
        try {
            return candidateDBDao.saveCandidateCompanys(candidateCompanyDO);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    public static JobPositionDO getPositionByID(int positionID) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", String.valueOf(positionID));
        try {
            JobPositionDO positionDO = jobDBDao.getPosition(queryUtil);
            return positionDO;
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    public static UserUserDO getUserByID(int userID) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", String.valueOf(userID));
        try {
            UserUserDO userUserDO = userDBDao.getUser(queryUtil);
            return userUserDO;
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
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
            try {
                remarkDOList = candidateDBDao.listCandidateRemarks(qu);
            } catch (CURDException e) {
                LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            }
        }
        return remarkDOList;
    }

    public static void saveCandidatePosition(CandidatePositionDO cp) throws TException {
        cp = candidateDBDao.saveCandidatePosition(cp);
    }

    public static Optional<CandidatePositionDO> getCandidatePosition(int positionID, int userID) {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("user_id", String.valueOf(userID));
        qu.addEqualFilter("position_id", String.valueOf(positionID));
        try {
            CandidatePositionDO candidatePositionDO = candidateDBDao.getCandidatePosition(qu);
            if(candidatePositionDO.getUserId() == 0) {
                return Optional.empty();
            }
            return Optional.of(candidatePositionDO);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static void updateCandidatePosition(CandidatePositionDO cp) throws TException {
        cp = candidateDBDao.updateCandidatePosition(cp);
    }

    public static void updateCandidateRemarks(List<CandidateRemarkDO> crs) throws TException {
        candidateDBDao.updateCandidateRemarks(crs);
    }

    public static CandidateShareChainDO getCandidateShareChain(int shareChainID) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", shareChainID);
        return candidateDBDao.getCandidateShareChain(qu);
    }

    public static UserEmployeeDO getEmployee(int userID) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("sysuser_id", userID);
        return userDBDao.getEmployee(qu);
    }

    public static UserEmployeeDO getEmployee(int userID, int companyId) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("sysuser_id", userID).addEqualFilter("company_id", companyId)
                .addEqualFilter("disable", Constant.ENABLE_OLD)
                .addEqualFilter("activation", EmployeeType.AUTH_SUCCESS.getValue());

        return userDBDao.getEmployee(qu);
    }

    /**
     * 判断公司是否开启被动求职者
     * @param companyId 公司编号
     * @return 是否开启被动求职者 false,未开启被动求职者；true，开启被动求职者
     */
    public static boolean isStartPassiveSeeker(int companyId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("passive_seeker", 0);
        try {
            HrWxWechatDO hrWxWechatDO = hrDBDao.getHrWxWechatDO(queryUtil);
            if(hrWxWechatDO != null) {
                return true;
            }
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 查找推荐记录信息
     * @param postUserId 推荐人编号
     * @param clickTime 点击时间
     * @param recoms 是否推荐
     * @return 职位转发浏览记录
     */
    public static List<CandidateRecomRecordDO> listCandidateRecomRecordDO(int postUserId, String clickTime, List<Integer> recoms) {

        try {
            return candidateDBDao.listCandidateRecomRecord(postUserId, clickTime, recoms);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 过滤某个ID之后查找推荐记录信息
     * @param id 职位转发浏览记录编号
     * @param postUserId 转发者编号
     * @param clickTime 点击时间
     * @param recoms 推荐标记
     * @return 职位转发浏览记录
     */
    public static List<CandidateRecomRecordDO> listCandidateRecomRecordDOExceptId(int id, int postUserId, String clickTime, List<Integer> recoms) {
        try {
            return candidateDBDao.listCandidateRecomRecordExceptId(id, postUserId, clickTime, recoms);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找职位标题
     * @param positionIdList 职位编号
     * @return 职位信息集合
     */
    public static List<JobPositionDO> getPositionByIdList(List<Integer> positionIdList) {
        List<JobPositionDO> jobPositionDOList = new ArrayList<>();
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("title");
        queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(positionIdList));
        try {
            return jobDBDao.getPositions(queryUtil);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return jobPositionDOList;
        }
    }

    /**
     * 查找用户的基本信息
     * @param userIdList 用户编号
     * @return 用户信息集合
     */
    public static List<UserUserDO> getUserByIDList(List<Integer> userIdList) {
        List<UserUserDO> userUserDOList = new ArrayList<>();
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("nickname")
                .addSelectAttribute("headimg");
        queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(userIdList)).addEqualFilter("status", 0);
        queryUtil.setOrder("id");
        try {
            return userDBDao.listUser(queryUtil);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return userUserDOList;
        }
    }

    /**
     * 查找未推荐的职位转发记录
     * @param idList id集合
     * @return 未推荐的职位转发记录
     */
    public static List<CandidateRecomRecordDO> getCandidateRecomRecordDOByIdList(List<Integer> idList) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("position_id").addSelectAttribute("presentee_user_id");
        queryUtil.addEqualFilter("is_recom", "[1,2,3]").addEqualFilter("id", StringUtils.converToArrayStr(idList));
        queryUtil.setSortby("position_id,click_time");
        queryUtil.setPer_page(2);
        try {
            return candidateDBDao.listCandidateRecomRecords(queryUtil);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据编号查找职位转发浏览记录
     * @param id 职位转发浏览记录编号
     * @return 职位转发浏览记录
     */
    public static CandidateRecomRecordDO getCandidateRecomRecordDO(int id) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", id);
        try {
            return candidateDBDao.getCandidateRecomRecord(queryUtil);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 修改职位转发浏览记录中的推荐信息
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    public static void updateCandidateRecomRecord(CandidateRecomRecordDO candidateRecomRecordDO) throws TException {
        candidateDBDao.updateCandidateRecomRecords(candidateRecomRecordDO);
    }

    public static HrPointsConfDO getHrPointConf(int companyId, RecruitmentScheduleEnum recruitmentScheduleEnum) throws TException {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("template_id", recruitmentScheduleEnum.IMPROVE_CANDIDATE.getId());
        return hrDBDao.getPointsConf(queryUtil);
    }

    /**
     * 添加完善浏览者信息添加员工积分
     * @param userEmployeePointsRecordDO 职位转发浏览记录
     * @throws TException 异常
     */
    public static void saveEmployeePointsRecord(UserEmployeePointsRecordDO userEmployeePointsRecordDO) throws TException {
        userDBDao.saveUserEmployeePoints(userEmployeePointsRecordDO);
    }

    /**
     * 修改员工积分
     * @param id 员工编号
     * @return 员工积分
     * @throws TException 异常
     */
    public static int updateEmployeePoint(int id) throws TException {
        return userDBDao.updateUserEmployeePoint(id);
    }

    /**
     * 根据转发者、点击时间以及推荐标识查询职位转发浏览记录
     * @param postUserId 转发者编号
     * @param dateTime 点击时间
     * @param recommendFlag 推荐标识集合
     * @return 职位转发记录数量
     * @throws TException 业务异常
     */
    public static int countRecommendation(int postUserId, String dateTime, List<Integer> recommendFlag) throws TException {
        return candidateDBDao.countCandidateRecomRecordCustom(postUserId, dateTime, recommendFlag);
    }

    /**
     * 查找公司下的员工信息
     * @param companyId 公司编号
     * @return 员工集合
     */
    public static List<UserEmployeeDO> listUserEmployee(int companyId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id");
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("disable", Constant.ENABLE_OLD).addEqualFilter("activation", EmployeeType.AUTH_SUCCESS.getValue());
        try {
            return userDBDao.getUserEmployeesDO(queryUtil);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找推荐排名
     * @param employeeIdList 员工编号
     * @return 推荐排名
     */
    public static List<CandidateRecomRecordSortingDO> listSorting(List<Integer> employeeIdList) {
        try {
            return candidateDBDao.listCandidateRecomRecordSorting(employeeIdList);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    public static List<CandidatePositionDO> listCandidatePositionByUserIdPositionId(List<Map<Integer, Integer>> param) {
        try {
            return candidateDBDao.listCandidatePositionsByPositionIDUserID(param);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }
}
