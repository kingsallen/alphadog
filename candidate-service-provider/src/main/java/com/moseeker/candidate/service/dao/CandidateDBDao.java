package com.moseeker.candidate.service.dao;

import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateRecomRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateRemarkDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsDao;
import com.moseeker.baseorm.dao.userdb.UserHRAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.candidate.constant.EmployeeType;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordSortingDO;
import com.moseeker.thrift.gen.dao.struct.CandidateRemarkDO;
import com.moseeker.thrift.gen.dao.struct.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;

import org.apache.thrift.TException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 候选人
 * Created by jack on 10/02/2017.
 */
@Service
public class CandidateDBDao {

    @Autowired
    CandidateCompanyDao candidateCompanyDao;

    @Autowired
    JobPositionDao jobPositionDao;

    @Autowired
    UserUserDao userDao;

    @Autowired
    UserHRAccountDao userHRAccountDao;

    @Autowired
    CandidateRemarkDao candidateRemarkDao;


    @Autowired
    CandidatePositionDao candidatePositionDao;


    @Autowired
    CandidateShareChainDao candidateShareChainDao;

    @Autowired
    UserEmployeeDao userEmployeeDao;

    @Autowired
    HrWxWechatDao hrWxWechatDao;

    @Autowired
    CandidateRecomRecordDao candidateRecomRecordDao;

    @Autowired
    HrPointsConfDao hrPointsConfDao;

    @Autowired
    UserEmployeePointsDao userEmployeePointsDao;

    /**
     *
     * @param userID
     * @param companyId
     * @return
     */
    public Optional<CandidateCompanyDO> getCandidateCompanyByUserIDCompanyID(int userID, int companyId) throws TException {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("user_id", String.valueOf(userID));
//        queryUtil.addEqualFilter("company_id", String.valueOf(companyId));
        Query query = new Query.QueryBuilder().and("user_id", String.valueOf(userID)).and("company_id", String.valueOf(companyId)).buildQuery();
        try {
            CandidateCompanyDO candidateCompanyDO = candidateCompanyDao.getCandidateCompany(query);
            return Optional.of(candidateCompanyDO);
        } catch (CURDException e) {
            if (e.getCode() != 90010) {
                throw e;
            } else {
                return Optional.of(null);
            }
        }
    }

    public void updateCandidateCompany(CandidateCompanyDO candidateCompany) throws TException {
        try {
            candidateCompanyDao.updateCandidateCompanys(candidateCompany);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public CandidateCompanyDO saveCandidateCompany(CandidateCompanyDO candidateCompanyDO) {
        try {
            return candidateCompanyDao.saveCandidateCompany(candidateCompanyDO);
        } catch (TException e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查询职位
     */
    public JobPositionDO getPositionByID(int positionID) {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("id", String.valueOf(positionID));

        Query query = new Query.QueryBuilder().and("id", String.valueOf(positionID)).buildQuery();
        JobPositionDO positionDO = jobPositionDao.getData(query);
        return positionDO;
    }

    public UserUserDO getUserByID(int userID) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", String.valueOf(userID));
        UserUserDO userUserDO = userDao.getData(queryUtil);
        return userUserDO;
    }

    public List<CandidateRemarkDO> getCandidateRemarks(int userID, int companyId) throws TException {
        List<CandidateRemarkDO> remarkDOList = new ArrayList<>();
        List<UserHrAccountDO> hrs = new ArrayList<>();
        try {
            hrs = userHRAccountDao.listHRFromCompany(companyId);
        } catch (CURDException e) {
            if (e.getCode() != 90010) {
                throw e;
            } else {
                //return Optional.of(null);
            }
        }
        if (hrs.size() > 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("user_id", String.valueOf(userID));
            StringBuffer hraccountIds = new StringBuffer("[");
            hrs.forEach(i -> hraccountIds.append(i.getId()).append(","));
            hraccountIds.deleteCharAt(hraccountIds.length() - 1).append("]");
            qu.addEqualFilter("hraccount_id", hraccountIds.toString());
            remarkDOList = candidateRemarkDao.getDatas(qu);
        }
        return remarkDOList;
    }

    public void saveCandidatePosition(CandidatePositionDO cp) throws TException {
        candidatePositionDao.addData(cp);
    }

    public Optional<CandidatePositionDO> getCandidatePosition(int positionID, int userID) {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("user_id", String.valueOf(userID));
        qu.addEqualFilter("position_id", String.valueOf(positionID));
        CandidatePositionDO candidatePositionDO = candidatePositionDao.getData(qu);
        if (candidatePositionDO.getUserId() == 0) {
            return Optional.empty();
        }
        return Optional.of(candidatePositionDO);
    }

    public void updateCandidatePosition(CandidatePositionDO cp) throws TException {
        candidatePositionDao.updateData(cp);
    }

    public void updateCandidateRemarks(List<CandidateRemarkDO> crs) throws TException {
        candidateRemarkDao.updateDatas(crs);
    }

    public CandidateShareChainDO getCandidateShareChain(int shareChainID) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", shareChainID);
        return candidateShareChainDao.getData(qu);
    }

    public UserEmployeeDO getEmployee(int userID) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("sysuser_id", userID);
        return userEmployeeDao.getEmployee(qu);
    }

    public UserEmployeeDO getEmployee(int userID, int companyId) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("sysuser_id", userID).addEqualFilter("company_id", companyId)
                .addEqualFilter("disable", Constant.ENABLE_OLD)
                .addEqualFilter("activation", EmployeeType.AUTH_SUCCESS.getValue());

        return userEmployeeDao.getEmployee(qu);
    }

    /**
     * 判断公司是否开启被动求职者
     *
     * @param companyId 公司编号
     * @return 是否开启被动求职者 false,未开启被动求职者；true，开启被动求职者
     */
    public boolean isStartPassiveSeeker(int companyId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("passive_seeker", 0);
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(queryUtil);
        if (hrWxWechatDO != null) {
            return true;
        }
        return false;
    }

    /**
     * 查找推荐记录信息
     *
     * @param postUserId 推荐人编号
     * @param clickTime  点击时间
     * @param recoms     是否推荐
     * @return 职位转发浏览记录
     */
    public List<CandidateRecomRecordDO> listCandidateRecomRecordDO(int postUserId, String clickTime, List<Integer> recoms) {
        return candidateRecomRecordDao.listCandidateRecomRecord(postUserId, clickTime, recoms);
    }

    /**
     * 过滤某个ID之后查找推荐记录信息
     *
     * @param id         职位转发浏览记录编号
     * @param postUserId 转发者编号
     * @param clickTime  点击时间
     * @param recoms     推荐标记
     * @return 职位转发浏览记录
     */
    public List<CandidateRecomRecordDO> listCandidateRecomRecordDOExceptId(int id, int postUserId, String clickTime, List<Integer> recoms) {
        return candidateRecomRecordDao.listCandidateRecomRecordExceptId(id, postUserId, clickTime, recoms);
    }

    /**
     * 查找职位标题
     *
     * @param positionIdList 职位编号
     * @return 职位信息集合
     */
    public List<JobPositionDO> getPositionByIdList(List<Integer> positionIdList) {
        List<JobPositionDO> jobPositionDOList = new ArrayList<>();
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("title");
        queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(positionIdList));
        return jobPositionDao.getPositions(queryUtil);
    }

    /**
     * 查找用户的基本信息
     *
     * @param userIdList 用户编号
     * @return 用户信息集合
     */
    public List<UserUserDO> getUserByIDList(List<Integer> userIdList) {
        List<UserUserDO> userUserDOList = new ArrayList<>();
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("nickname")
                .addSelectAttribute("headimg");
        queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(userIdList)).addEqualFilter("status", 0);
        queryUtil.orderBy("id", Order.ASC);
//        queryUtil.setOrder("id");
        return userDao.getDatas(queryUtil);
    }

    /**
     * 查找未推荐的职位转发记录
     *
     * @param idList id集合
     * @return 未推荐的职位转发记录
     */
    public List<CandidateRecomRecordDO> getCandidateRecomRecordDOByIdList(List<Integer> idList) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id").addSelectAttribute("position_id").addSelectAttribute("presentee_user_id");
        queryUtil.addEqualFilter("is_recom", "[1,2,3]").addEqualFilter("id", StringUtils.converToArrayStr(idList));
//        queryUtil.setSortby("position_id,click_time");
        queryUtil.setPer_page(2);
        return candidateRecomRecordDao.getDatas(queryUtil);

    }

    /**
     * 根据编号查找职位转发浏览记录
     *
     * @param id 职位转发浏览记录编号
     * @return 职位转发浏览记录
     */
    public CandidateRecomRecordDO getCandidateRecomRecordDO(int id) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("id", id);
        return candidateRecomRecordDao.getData(queryUtil);

    }

    /**
     * 修改职位转发浏览记录中的推荐信息
     *
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    public void updateCandidateRecomRecord(CandidateRecomRecordDO candidateRecomRecordDO) throws TException {
        candidateRecomRecordDao.updateData(candidateRecomRecordDO);
    }

    public HrPointsConfDO getHrPointConf(int companyId, RecruitmentScheduleEnum recruitmentScheduleEnum) throws TException {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("template_id", recruitmentScheduleEnum.IMPROVE_CANDIDATE.getId());
        return hrPointsConfDao.getData(queryUtil);
    }

    /**
     * 添加完善浏览者信息添加员工积分
     *
     * @param userEmployeePointsRecordDO 职位转发浏览记录
     * @throws TException 异常
     */
    public void saveEmployeePointsRecord(UserEmployeePointsRecordDO userEmployeePointsRecordDO) throws TException {
        userEmployeePointsDao.addData(userEmployeePointsRecordDO);
    }

    /**
     * 修改员工积分
     *
     * @param id 员工编号
     * @return 员工积分
     * @throws TException 异常
     */
    public int updateEmployeePoint(int id) throws TException {
        return userEmployeeDao.updateUserEmployeePoint(id);
    }

    /**
     * 根据转发者、点击时间以及推荐标识查询职位转发浏览记录
     *
     * @param postUserId    转发者编号
     * @param dateTime      点击时间
     * @param recommendFlag 推荐标识集合
     * @return 职位转发记录数量
     * @throws TException 业务异常
     */
    public int countRecommendation(int postUserId, String dateTime, List<Integer> recommendFlag) throws TException {
        return candidateRecomRecordDao.countCandidateRecomRecordCustom(postUserId, dateTime, recommendFlag);
    }

    /**
     * 查找公司下的员工信息
     *
     * @param companyId 公司编号
     * @return 员工集合
     */
    public List<UserEmployeeDO> listUserEmployee(int companyId) {
        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addSelectAttribute("id");
        queryUtil.addEqualFilter("company_id", companyId).addEqualFilter("disable", Constant.ENABLE_OLD).addEqualFilter("activation", EmployeeType.AUTH_SUCCESS.getValue());
        return userEmployeeDao.getDatas(queryUtil);
    }

    /**
     * 查找推荐排名
     *
     * @param employeeIdList 员工编号
     * @return 推荐排名
     */
    public List<CandidateRecomRecordSortingDO> listSorting(List<Integer> employeeIdList) {
        return candidateRecomRecordDao.listCandidateRecomRecordSorting(employeeIdList);
    }

    public List<CandidatePositionDO> listCandidatePositionByUserIdPositionId(List<Map<Integer, Integer>> param) {
        return candidatePositionDao.listCandidatePositionsByPositionIDUserID(param);

    }
}
