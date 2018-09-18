package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateRecomRecordDao;
import com.moseeker.baseorm.dao.configdb.AwardConfigTplDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserCollectPositionDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserCollectPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 个人中心片段业务处理类
 * @author jack
 *
 */
@Component
public class UserCenterBizTools {

    Logger logger = LoggerFactory.getLogger(UserCenterBizTools.class);

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private AwardConfigTplDao awardConfigTplDao;

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;


    @Autowired
    private CandidateRecomRecordDao candidateRecomRecordDao;

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private UserCollectPositionDao collectPositionDao;

    @Autowired
    EmployeeEntity employeeEntity;

    /**
     * 查找用户的申请记录
     * @param userId 用户编号
     * @return 申请记录集合
     * @throws CommonException 业务异常异常
     */
    public List<JobApplicationDO> getAppsForUser(int userId) throws CommonException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("applier_id", String.valueOf(userId)).and("disable", AbleFlag.OLDENABLE.getValueStr()).and("email_status", AbleFlag.OLDENABLE.getValueStr());
        qu.orderBy("submit_time", Order.DESC);
        try {
            return applicationDao.getDatas(qu.buildQuery(), JobApplicationDO.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查找用户的申请记录
     * @param userId 用户信息
     * @param companyId 公司信息
     * @return 申请记录
     */
    public List<JobApplicationRecord> getAppsForUserAndCompany(int userId, int companyId) {
        return applicationDao.getByApplierIdAndCompanyId(userId, companyId);
    }

    /**
     * 查找职位信息
     * @param ids 职位编号数组
     * @return 职位数据集合
     * @throws CommonException 业务异常
     */
    public List<JobPositionDO> getPositions(Collection ids) throws CommonException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where(new Condition("id", ids, ValueOp.IN));
        return positionDao.getDatas(qu.buildQuery(), JobPositionDO.class);
    }

    /**
     * 查找公司信息
     * @param ids 公司信息编号
     * @return 公司信息集合
     * @throws CommonException 业务异常
     */
    public List<Hrcompany> getCompanies(Collection ids) throws CommonException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where(new Condition("id", ids, ValueOp.IN));
        return companyDao.getCompanies(qu.buildQuery());
    }

    /**
     * 查找招聘进度积分配置模板
     * (todo 可以走缓存)
     * @return 聘进度积分配置模板集合
     * @throws CommonException
     */
    public List<ConfigSysPointsConfTplDO> getAwardConfigTpls() throws CommonException {
        return awardConfigTplDao.getAwardConfigTpls(new Query.QueryBuilder().buildQuery());
    }

    /**
     * 查找职位搜藏
     * @param userId 用户编号
     * @return 感兴趣职位集合
     * @throws CommonException 业务异常
     */
    public List<UserCollectPositionDO> getFavPositions(int userId) throws CommonException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("user_id", String.valueOf(userId));
        qu.and("status", String.valueOf("0"));
        List<UserCollectPositionDO> collectPositions = collectPositionDao.getDatas(qu.buildQuery());

        return collectPositions;
    }

    /**
     * 查找转发浏览记录。如果存在post_user_id == repost_user_id的情况，则将repost_user_id置为0
     * @param userId 用户编号
     * @param type 类型 1：表示所有相关的浏览记录，2：表示被推荐的浏览用户，3：表示提交申请的浏览记录
     * @param positionIdList 职位编号
     * @param referenceIdList 被推荐人
     * @param pageNo 页码
     * @param pageSize 每页显示的数量   @return 转发浏览记录集合
     * @throws CommonException
     */
    public List<CandidateRecomRecordDO> listCandidateRecomRecords(int userId, int type, List<Integer> positionIdList,
                                                                  List<Integer> referenceIdList, int pageNo,
                                                                  int pageSize) throws CommonException {
        List<CandidateRecomRecordDO> recomRecordDOList = new ArrayList<>();
        switch (type) {
            case 1:			//查找所有相关的职位转发记录
                Query.QueryBuilder qu = new Query.QueryBuilder();
                qu.select("id").select("app_id")
                        .select("click_time").select("recom_time").select("is_recom")
                        .select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID.getName())
                        .select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.getName())
                        .select("presentee_user_id").select("position_id");
                qu.where("post_user_id", userId).and(new Condition("position_id", positionIdList, ValueOp.IN));
                if (referenceIdList != null && referenceIdList.size() > 0) {
                    qu.and(new Condition(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.getName(), referenceIdList, ValueOp.NIN));
                }
                qu.groupBy("presentee_user_id").groupBy("position_id");
                qu.orderBy("id", Order.DESC);
                qu.setPageNum(pageNo);
                qu.setPageSize(pageSize);
                recomRecordDOList = candidateRecomRecordDao.getDatas(qu.buildQuery(), CandidateRecomRecordDO.class);
                break;
            case 2:			//查找被推荐的职位转发记录
                recomRecordDOList = candidateRecomRecordDao.listInterestedCandidateRecomRecordByUserPositions(userId, positionIdList, pageNo, pageSize);
                break;
            case 3:			//查找申请的职位转发记录
                recomRecordDOList = candidateRecomRecordDao.listCandidateRecomRecordsForAppliedByUserPositions(userId, positionIdList, pageNo, pageSize);
                break;
            default:
        }
        //如果存在post_user_id == repost_user_id的情况，则将repost_user_id置为0
        if (recomRecordDOList != null && recomRecordDOList.size() > 0) {
            for (CandidateRecomRecordDO candidateRecomRecordDO : recomRecordDOList) {
                if (candidateRecomRecordDO.getRepostUserId() == candidateRecomRecordDO.getPostUserId()) {
                    candidateRecomRecordDO.setRepostUserId(0);
                }
            }
        }
        return recomRecordDOList;
    }

    /**
     * 根据转发者查找转发记录
     * @param userId 用户编号
     * @param referenceIdList
     *@param positionIdList 公司下的职位编号  @return
     */
    public int countCandidateRecomRecord(int userId, List<Integer> positionIdList, List<Integer> referenceIdList) {
        int count = 0;
        try {
            count = candidateRecomRecordDao.countCandidateRecomRecordDistinctPresenteePosition(userId, positionIdList,
                    referenceIdList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    /**
     * 根据转发者查找已经被推荐的转发记录
     * @param userId
     * @return
     */
    public int countRecommendedCandidateRecomRecord(int userId) {
        int count = 0;
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("post_user_id", userId).and("is_recom", 0);
        try {
            count = candidateRecomRecordDao.getCount(qu.buildQuery());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    /**
     * 根据转发者查找已经被感兴趣的转发记录
     * @param userId 用户编号
     * @param positionIdList 职位编号
     * @return
     */
    public int countInterestedCandidateRecomRecord(int userId, List<Integer> positionIdList) {
        int count = 0;
        try {
            count = candidateRecomRecordDao.countInterestedCandidateRecomRecordByUserPosition(userId, positionIdList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    /**
     * 根据转发者查找已经申请的转发记录
     * @param userId 用户编号
     * @param positionIdList 职位编号
     * @return
     */
    public int countAppliedCandidateRecomRecord(int userId, List<Integer> positionIdList) {
        int count = 0;
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("post_user_id", userId);
        try {
            count = candidateRecomRecordDao.countAppliedCandidateRecomRecordByUserPosition(userId, positionIdList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    /**
     * 数组转成逗号隔开，开始和结束用中括号括起来的字符创。如[1,2,3]
     * @param array 整型数组
     * @return 字符串
     */
    private String arrayToString(int ...array) {
        StringBuffer sb = new StringBuffer(array.length * 2 + 2);
        sb.append("[");
        for(int i : array) {
            sb.append(i);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 查找职位编号和职位标题。
     * @param positionIDSet 职位编号集合
     * @return 浏览者信息集合
     */
    public List<JobPositionDO> listJobPositions(Set<Integer> positionIDSet) {
        List<JobPositionDO> positionList = new ArrayList<>();
        if(positionIDSet != null && positionIDSet.size() > 0) {
            Query.QueryBuilder queryUtil = new Query.QueryBuilder();
            queryUtil.select("id").select("title");
            queryUtil.where(new Condition("id", positionIDSet, ValueOp.IN));
            try {
                positionList = positionDao.getDatas(queryUtil.buildQuery(), JobPositionDO.class);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return positionList;
    }

    /**
     * 查找浏览者信息
     * @param presenteeIDSet 浏览者编号
     * @return 浏览者信息集合
     */
    public List<UserUserDO> listPresentees(Set<Integer> presenteeIDSet) {
        List<UserUserDO> users = new ArrayList<>();

        if(presenteeIDSet != null && presenteeIDSet.size() > 0) {
            Query.QueryBuilder queryUtil = new Query.QueryBuilder();
            queryUtil.select("id").select("name").select("nickname").select("headimg");
            queryUtil.where(new Condition("id", presenteeIDSet, ValueOp.IN));
            try {
                users = userDao.getDatas(queryUtil.buildQuery());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return users;
    }

    /**
     * 查询转发用户信息
     * @param repostIDSet 转发用户编号集合
     * @return 转发用户信息集合
     */
    public List<UserUserDO> listReposts(Set<Integer> repostIDSet) {
        List<UserUserDO> users = new ArrayList<>();
        if(repostIDSet != null && repostIDSet.size() > 0) {
            Query.QueryBuilder queryUtil = new Query.QueryBuilder();
            queryUtil.select("id").select("name").select("nickname");
            queryUtil.where(new Condition("id", repostIDSet, ValueOp.IN));
            try {
                users = userDao.getDatas(queryUtil.buildQuery());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return users;
    }

    /**
     * 查询申请信息集合
     * @param appIDSet 申请编号集合
     * @return 申请信息集合
     */
    public List<JobApplicationDO> listApps(Set<Integer> appIDSet) {
        List<JobApplicationDO> apps = new ArrayList<>();

        if(appIDSet != null && appIDSet.size() > 0) {
            Query.QueryBuilder queryUtil = new Query.QueryBuilder();
            queryUtil.where(new Condition("id", appIDSet, ValueOp.IN));
            queryUtil.select("id").select("applier_name").select("submit_time").select("app_tpl_id");
            try {
                apps = applicationDao.getDatas(queryUtil.buildQuery());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return apps;
    }

    /**
     * 查找候选人查看职位的记录
     * @param cps 候选人的职位编号和用户编号的集合
     * @return 候选人浏览职位的集合
     */
    public List<CandidatePositionDO> listCandidatePositionsByPositionIDUserID(List<Map<Integer, Integer>> cps) {
        try {
            return candidatePositionDao.listCandidatePositionsByPositionIDUserID(cps);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }

    }

    /**
     * 根据申请编号，查找排除不合适记录的最后一条操作记录
     * @param rejectAppIdSet 申请编号
     * @return 最后一条操作记录
     */
    public List<HrOperationRecordDO> listLastHrOperationRecordPassedReject(Set<Integer> rejectAppIdSet) {
        List<HrOperationRecordDO> hrOperationRecordDOList = new ArrayList<>();

        if(rejectAppIdSet != null && rejectAppIdSet.size() > 0) {
            try {
                hrOperationRecordDOList = hrOperationRecordDao.listLatestOperationRecordByAppIdSet(rejectAppIdSet);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return hrOperationRecordDOList;

    }

    /**
     * 查找申请记录集合信息
     * @param appId 申请记录编号
     * @return 申请记录集合
     * @throws CommonException thrift异常
     */
    public JobApplicationDO getApplication(int appId) throws CommonException {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.select("id").select("applier_id").select("email_status")
                .select("apply_type").select("app_tpl_id").select("position_id")
                .select("company_id").select("recommender_user_id");
        qu.where("id", appId).and("disable", AbleFlag.OLDENABLE.getValueStr());

        return applicationDao.getData(qu.buildQuery());
    }

    /**
     * 查找职位名称
     * @param positionId 职位编号
     * @return 职位信息
     */
    public JobPositionDO getPosition(int positionId) {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.select("id").select("title");
        qu.where("id", positionId);
        try {
            return positionDao.getData(qu.buildQuery());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查询公司名称
     * @param companyId
     * @return
     */
    public HrCompanyDO getCompany(int companyId) {
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.select("id").select("name").select("abbreviation");
        qu.where("id", companyId);
        try {
            return companyDao.getData(qu.buildQuery());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据申请编号查找该申请记录的HR操作记录
     * @param appId 申请记录
     * @return 操作记录
     */
    public List<HrOperationRecordDO> listHrOperationRecord(int appId) {
        Query.QueryBuilder queryUtil = new Query.QueryBuilder();
        queryUtil.where("app_id", appId);
        queryUtil.select("id").select("app_id").select("opt_time").select("operate_tpl_id");
        queryUtil.orderBy("opt_time");
        queryUtil.setPageNum(0);
        queryUtil.setPageSize(Integer.MAX_VALUE);
        try {
            return hrOperationRecordDao.getDatas(queryUtil.buildQuery());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找查找推荐记录相关的职位编号
     * @param userId 用户编号
     * @return
     */
    public List<Integer> listPositionIdByUserId(int userId) {
        try {
            UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(userId);
            if (employeeDO != null) {
                List<Integer> companyIdList = employeeEntity.getCompanyIds(employeeDO.getCompanyId());
                return positionDao.listPositionIdByCompanyIdList(companyIdList);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
