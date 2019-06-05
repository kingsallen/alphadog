package com.moseeker.candidate.service.dao;

import com.moseeker.baseorm.dao.candidatedb.*;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.candidate.constant.EmployeeType;
import com.moseeker.common.biztools.RecruitmentScheduleEnum;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordSortingDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
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

    private Logger logger = LoggerFactory.getLogger(CandidateDBDao.class);

    @Autowired
    CandidateCompanyDao candidateCompanyDao;

    @Autowired
    JobPositionDao jobPositionDao;

    @Autowired
    UserUserDao userDao;

    @Autowired
    UserHrAccountDao userHRAccountDao;

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
    CandidateApplicationReferralDao applicationreferralDao;

    @Autowired
    HrPointsConfDao hrPointsConfDao;


    /**
     * @param userID
     * @param companyId
     * @return
     */
    public Optional<CandidateCompanyDO> getCandidateCompanyByUserIDCompanyID(int userID, int companyId) throws BIZException {
        Query query = new Query.QueryBuilder().where("sys_user_id", String.valueOf(userID)).and("company_id", String.valueOf(companyId)).buildQuery();
        try {
            CandidateCompanyDO candidateCompanyDO = candidateCompanyDao.getCandidateCompany(query);
            if (candidateCompanyDO != null) {
                return Optional.of(candidateCompanyDO);
            } else {
                return Optional.empty();
            }
        } catch (CURDException e) {
            if (e.getCode() != 90010) {
                throw new BIZException(e.getCode(),e.getMessage());
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * 查詢
     * @param candidateCompanyId
     * @return
     * @throws TException
     */
    public int getCandidateCompanyByCandidateCompanyID(int candidateCompanyId) throws TException {
        Query query = new Query.QueryBuilder().where("candidate_company_id", String.valueOf(candidateCompanyId)).buildQuery();
        return candidatePositionDao.getCount(query);
    }

    public void updateCandidateCompany(CandidateCompanyDO candidateCompany) throws TException {
        try {
            candidateCompanyDao.updateCandidateCompanys(candidateCompany);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public void updateCandidateCompanySetPositionWxLayerQrcode(int id, byte status) throws BIZException {
        try {
            candidateCompanyDao.updateCandidateCompanySetPositionWxLayerQrcode(id, status);
        } catch (CURDException e) {
            e.printStackTrace();
            throw new BIZException(e.getCode(),e.getMessage());
        }
    }

    public void updateCandidateCompanySetPositionWxLayerProfile(int id, byte status) throws BIZException {
        try {
            candidateCompanyDao.updateCandidateCompanySetPositionWxLayerProfile(id, status);
        } catch (CURDException e) {
            e.printStackTrace();
            throw new BIZException(e.getCode(),e.getMessage());
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
        Query query = new Query.QueryBuilder().where("id", String.valueOf(positionID)).buildQuery();
        JobPositionDO positionDO = jobPositionDao.getData(query);
        return positionDO;
    }

    /**
     * 查询用户
     */
    public UserUserDO getUserByID(int userID) {
        Query query = new Query.QueryBuilder().and("id", String.valueOf(userID)).buildQuery();
        UserUserDO userUserDO = userDao.getData(query);
        return userUserDO;

    }

    public List<CandidateRemarkDO> getCandidateRemarks(int userID, int companyId) throws
            TException {
        List<CandidateRemarkDO> remarkDOList = new ArrayList<>();
        List<UserHrAccountDO> hrs = userHRAccountDao.listHRFromCompany(companyId);
        if (hrs.size() > 0) {
            List<Integer> hraccountIds = new ArrayList<>();
            hrs.forEach(i -> hraccountIds.add(i.getId()));
            Condition condition = new Condition("hraccount_id", hraccountIds, ValueOp.IN);
            Query query = new Query.QueryBuilder().where("user_id", String.valueOf(userID)).and(condition).buildQuery();
            remarkDOList = candidateRemarkDao.getDatas(query);
        }
        return remarkDOList;
    }

    public void saveCandidatePosition(CandidatePositionDO cp) throws TException {
        candidatePositionDao.addDataIgnoreDuplicate(cp);
    }

    public Optional<CandidatePositionDO> getCandidatePosition(int positionID, int userID) {
        Query query = new Query.QueryBuilder().where("user_id", String.valueOf(userID)).and("position_id", String.valueOf(positionID)).buildQuery();
        CandidatePositionDO candidatePositionDO = candidatePositionDao.getData(query);
        logger.info("getCandidatePosition candidatePositionDO:{}", candidatePositionDO);
        if (candidatePositionDO == null || candidatePositionDO.getUserId() == 0) {
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
        Query query = new Query.QueryBuilder().where("id", shareChainID).buildQuery();
        return candidateShareChainDao.getData(query);
    }


    public UserEmployeeDO getEmployee(int userID) throws TException {
        Query query = new Query.QueryBuilder().where("sysuser_id", userID).
                and("disable", Constant.ENABLE_OLD).and("activation", EmployeeType.AUTH_SUCCESS.getValue()).buildQuery();
        return userEmployeeDao.getEmployee(query);

    }

    public UserEmployeeDO getEmployee(int userID, int companyId) throws TException {
        Query query = new Query.QueryBuilder().where("sysuser_id", userID).and("company_id", companyId)
                .and("disable", Constant.ENABLE_OLD).and("activation", EmployeeType.AUTH_SUCCESS.getValue()).buildQuery();
        return userEmployeeDao.getEmployee(query);
    }

    /**
     * 判断公司是否开启被动求职者
     *
     * @param companyId 公司编号
     * @return 是否开启被动求职者 false,未开启被动求职者；true，开启被动求职者
     */
    public boolean isStartPassiveSeeker(int companyId) {
        Query query = new Query.QueryBuilder().where("company_id", companyId).and("passive_seeker", 0).buildQuery();
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(query);
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
    public List<CandidateRecomRecordDO> listCandidateRecomRecordDO(int postUserId, String
            clickTime, List<Integer> recoms, List<Integer> positionIdList) {
        return candidateRecomRecordDao.listCandidateRecomRecord(postUserId, clickTime, recoms, positionIdList);
    }


    /**
     * 推荐的结果记录是根据职位和浏览者过滤的，在推荐时，需要将被过滤的数据也给添加上
     *
     * @param candidateRecomRecordDO 推荐的数据
     */
    public List<CandidateRecomRecordDO> listFiltredCandidateRecomRecord(
            CandidateRecomRecordDO candidateRecomRecordDO) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        List<Integer> hraccountIds = new ArrayList<>();
        hraccountIds.add(1);
        hraccountIds.add(2);
        hraccountIds.add(3);
        Condition condition = new Condition("is_recom", hraccountIds, ValueOp.IN);
        query.where("position_id", candidateRecomRecordDO.getPositionId()).
                and("presentee_user_id", candidateRecomRecordDO.getPresenteeUserId()).
                and("post_user_id", candidateRecomRecordDO.getPostUserId()).
                and(condition);
        try {
            return candidateRecomRecordDao.getDatas(query.buildQuery());
        } catch (Exception e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }
    }
    public List<CandidateRecomRecordDO> listCandidateRecomRecordDONew(int postUserId, String
            clickTime, List<Integer> recoms, List<Integer> positionIdList){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = DateTime.parse(clickTime, format);
        Timestamp time=new Timestamp(dateTime.getMillis());
        //        Timestamp time1=new Timestamp(dateTime.plusDays(1).getMillis());
//        Timestamp time2=new Timestamp(dateTime.plusDays(-1).getMillis());
//        Timestamp time3=new Timestamp(dateTime.plusDays(-2).getMillis());
//        Timestamp time4=new Timestamp(dateTime.plusDays(-3).getMillis());
        Timestamp time1=new Timestamp(dateTime.plusMinutes(5).getMillis());
        Timestamp time2=new Timestamp(dateTime.plusMinutes(-5).getMillis());
        Timestamp time3=new Timestamp(dateTime.plusMinutes(-10).getMillis());
        Timestamp time4=new Timestamp(dateTime.plusMinutes(-15).getMillis());
        List<CandidateRecomRecordDO> result=this.handlerListCandidateRecomData(postUserId,recoms,positionIdList,time,time1,time2,time3,time4);
        return result;

    }

    private List<CandidateRecomRecordDO> handlerListCandidateRecomData(int postUserId, List<Integer> recoms, List<Integer> positionIdList, Timestamp time, Timestamp time1, Timestamp time2, Timestamp time3, Timestamp time4){
        List<CandidateRecomRecordDO> recommendInfo=candidateRecomRecordDao.listCandidateRecomRecord(postUserId,time,time1,recoms,positionIdList);
        List<CandidateRecomRecordDO> recommendInfo1=candidateRecomRecordDao.listCandidateRecomRecord(postUserId,time2,time,recoms,positionIdList);
        List<CandidateRecomRecordDO> recommendInfo2=candidateRecomRecordDao.listCandidateRecomRecord(postUserId,time3,time2,recoms,positionIdList);
        List<CandidateRecomRecordDO> recommendInfo3=candidateRecomRecordDao.listCandidateRecomRecord(postUserId,time4,time3,recoms,positionIdList);
        List<CandidateRecomRecordDO> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(recommendInfo)){
            result.addAll(recommendInfo);
        }
        List<CandidateRecomRecordDO> firstData=this.handlerFirstCandidateRecomData(recommendInfo,recommendInfo1);
        if(!StringUtils.isEmptyList(firstData)){
            result.addAll(firstData);
        }
        List<CandidateRecomRecordDO> secondData=this.handlerSeacondCandidateRecomData(recommendInfo,recommendInfo1,recommendInfo2);
        if(!StringUtils.isEmptyList(secondData)){
            result.addAll(secondData);
        }
        List<CandidateRecomRecordDO> thridData=this.handlerThridCandidateRecomData(recommendInfo,recommendInfo1,recommendInfo2,recommendInfo3);
        if(!StringUtils.isEmptyList(thridData)){
            result.addAll(thridData);
        }
        return result;
    }
    /*
     处理前一天的推荐数据
     */
    private List<CandidateRecomRecordDO> handlerFirstCandidateRecomData(List<CandidateRecomRecordDO> recommendInfo,List<CandidateRecomRecordDO> recommendInfo1){
        List<CandidateRecomRecordDO> result=this.filterCandidateRecomData(recommendInfo,recommendInfo1);
        result=this.filterRecomReasonData(result);
        return result;
    }
    /*
     处理前两天的推荐数据
     */
    private List<CandidateRecomRecordDO> handlerSeacondCandidateRecomData(List<CandidateRecomRecordDO> recommendInfo,List<CandidateRecomRecordDO> recommendInfo1,List<CandidateRecomRecordDO> recommendInfo2){
        List<CandidateRecomRecordDO> result=this.filterCandidateRecomData(recommendInfo1,recommendInfo2);
        result=this.filterCandidateRecomData(recommendInfo,result);
        result=this.filterRecomReasonData(result);
        return result;
    }
    /*
    处理前三天的推荐数据
    */
    private List<CandidateRecomRecordDO> handlerThridCandidateRecomData(List<CandidateRecomRecordDO> recommendInfo,List<CandidateRecomRecordDO> recommendInfo1,
                                                                        List<CandidateRecomRecordDO> recommendInfo2,List<CandidateRecomRecordDO> recommendInfo3){
        List<CandidateRecomRecordDO> result=this.filterCandidateRecomData(recommendInfo2,recommendInfo3);
        result=this.filterCandidateRecomData(recommendInfo1,result);
        result=this.filterCandidateRecomData(recommendInfo,result);
        result=this.filterRecomReasonData(result);
        return result;
    }
    /*
     过滤两个CandidateRecomRecordDO列表
     */
    private List<CandidateRecomRecordDO> filterCandidateRecomData(List<CandidateRecomRecordDO> recommendInfo,List<CandidateRecomRecordDO> originInfos){
        List<CandidateRecomRecordDO> result=new ArrayList<>();
        if(StringUtils.isEmptyList(recommendInfo)){
            return originInfos;
        }
        if(StringUtils.isEmptyList(originInfos)){
            return null;
        }
        for(CandidateRecomRecordDO originInfo:originInfos){
            int postUserId=originInfo.getPostUserId();
            boolean flag=true;
            for(CandidateRecomRecordDO recomRecordDO:recommendInfo ){
                int userId=recomRecordDO.getPostUserId();
                if(postUserId==userId){
                    flag=false;
                    break;
                }
            }
            if(flag){
                result.add(originInfo);
            }
        }
        return result;
    }
    /*
      过滤recom_reason数据
     */
    private List<CandidateRecomRecordDO> filterRecomReasonData( List<CandidateRecomRecordDO> infosList){
        List<Integer> filterIdList=this.getPostUserIdList(infosList);
        if(StringUtils.isEmptyList(filterIdList)){
            return null;
        }
        List<CandidateRecomRecordDO> result=new ArrayList<>();
        for(Integer userId:filterIdList){
            for(CandidateRecomRecordDO infos:infosList){
                int postUserId=infos.getPostUserId();
                if(postUserId==userId){
                    result.add(infos);
                }
            }
        }
        return result;
    }
    /*
     获取推荐的post_user_id
     */
    private List<Integer> getPostUserIdList(List<CandidateRecomRecordDO> infosList){
        if(StringUtils.isEmptyList(infosList)){
            return null;
        }
        List<Integer> filterUserIdList=new ArrayList<>();
        for(CandidateRecomRecordDO info:infosList){
            String recomReason=info.getRecomReason();
            int postUserId=info.getPostUserId();
            if(StringUtils.isNullOrEmpty(recomReason)){
                boolean flag=true;
                for(CandidateRecomRecordDO info1:infosList){
                    int userId=info1.getPostUserId();
                    String reason=info1.getRecomReason();
                    if(userId==postUserId&&StringUtils.isNotNullOrEmpty(reason)){
                        flag=flag;
                        break;
                    }
                }
                if(flag&&!filterUserIdList.contains(postUserId)){
                    filterUserIdList.add(postUserId);
                }

            }
        }
        return filterUserIdList;
    }
    /**
     * 过滤某个ID之后查找推荐记录信息
     *
     * @param id         职位转发浏览记录编号
     * @param postUserId 转发者编号
     * @param clickTime  点击时间
     * @param recoms     推荐标记
     * @param positionIdList 职位编号
     * @return 职位转发浏览记录
     */
    public List<CandidateRecomRecordDO> listCandidateRecomRecordDOExceptId(int id,
                                                                           int postUserId, String clickTime, List<Integer> recoms, List<Integer> positionIdList) {
        return candidateRecomRecordDao.listCandidateRecomRecordExceptId(id, postUserId, clickTime, recoms, positionIdList);
    }

    /**
     * 查找职位标题
     *
     * @param positionIdList 职位编号
     * @return 职位信息集合
     */
    public List<JobPositionDO> getPositionByIdList(List<Integer> positionIdList) {
        Query query = new Query.QueryBuilder().select("id").select("title")
                .where(new Condition("id", positionIdList, ValueOp.IN)).buildQuery();
        try {
            return jobPositionDao.getPositions(query);
        } catch (Exception e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 查找用户的基本信息
     *
     * @param userIdList 用户编号
     * @return 用户信息集合
     */
    public List<UserUserDO> getUserByIDList(List<Integer> userIdList) {
        Query query = new Query.QueryBuilder().select(UserUser.USER_USER.ID.getName())
                .select(UserUser.USER_USER.NAME.getName())
                .select(UserUser.USER_USER.NICKNAME.getName())
                .select(UserUser.USER_USER.HEADIMG.getName()).
                where(new Condition(UserUser.USER_USER.ID.getName(), userIdList, ValueOp.IN))
                .orderBy(UserUser.USER_USER.ID.getName(), Order.ASC).buildQuery();
        return userDao.getDatas(query);
    }

    /**
     * 查找未推荐的职位转发记录
     *
     *
     * @param idList id集合
     * @param positionIdList 职位ID集合
     * @return 未推荐的职位转发记录
     */
    public List<CandidateRecomRecordDO> getCandidateRecomRecordDOByIdList(List<Integer> idList, List<Integer> positionIdList) {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addSelectAttribute("id").addSelectAttribute("position_id").addSelectAttribute("presentee_user_id");
//        queryUtil.addEqualFilter("is_recom", "[1,2,3]").addEqualFilter("id", StringUtils.converToArrayStr(idList));
//        queryUtil.setSortby("position_id,click_time");
//        queryUtil.setPer_page(2);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Query query = new Query.QueryBuilder().select("id").select("position_id").select("presentee_user_id")
                .select("click_time")
                .where(new Condition("is_recom", list, ValueOp.IN))
                .and(new Condition("id", idList, ValueOp.IN))
                .and(new Condition("position_id", positionIdList, ValueOp.IN))
                .orderBy("position_id").orderBy("click_time").buildQuery();
        return candidateRecomRecordDao.getDatas(query);

    }

    /**
     * 根据编号查找职位转发浏览记录
     *
     * @param id 职位转发浏览记录编号
     * @param postUserId 推荐人
     * @return 职位转发浏览记录
     */
    public CandidateRecomRecordDO getCandidateRecomRecordDO(int id, int postUserId) {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("id", id);
        Query query = new Query.QueryBuilder().where("id", id).and("post_user_id", postUserId).buildQuery();
        return candidateRecomRecordDao.getData(query);

    }

    /**
     * 修改职位转发浏览记录中的推荐信息
     *
     * @param candidateRecomRecordDO 职位转发浏览记录
     */
    public void updateCandidateRecomRecord(CandidateRecomRecordDO candidateRecomRecordDO) throws
            TException {
        candidateRecomRecordDao.updateData(candidateRecomRecordDO);
    }

    /**
     * 批量修改职位转发浏览记录
     */
    public void updateCandidateRecomRecords
    (List<CandidateRecomRecordDO> candidateRecomRecordList) {
        try {
            candidateRecomRecordDao.updateDatas(candidateRecomRecordList);
        } catch (Exception e) {
            LoggerFactory.getLogger(CandidateDBDao.class).error(e.getMessage(), e);
        }

    }

    public HrPointsConfDO getHrPointConf(int companyId, RecruitmentScheduleEnum
            recruitmentScheduleEnum) throws TException {

        Query query = new Query.QueryBuilder().where("company_id", companyId).and("template_id", recruitmentScheduleEnum.IMPROVE_CANDIDATE.getId()).buildQuery();
        return hrPointsConfDao.getData(query);
    }

    /**
     * 根据转发者、点击时间以及推荐标识查询职位转发浏览记录
     *
     * @param postUserId    转发者编号
     * @param dateTime      点击时间
     * @param recommendFlag 推荐标识集合
     * @param positionIdList 职位编号集合
     * @return 职位转发记录数量
     * @throws TException 业务异常
     */
    public int countRecommendation(int postUserId, String
            dateTime, List<Integer> recommendFlag, List<Integer> positionIdList) throws TException {
        return candidateRecomRecordDao.countCandidateRecomRecordCustom(postUserId, dateTime, recommendFlag, positionIdList);
    }

    /**
     * 查找推荐排名
     *
     *
     * @param employeeIdList 员工编号
     * @param positionIdList 职位编号
     * @return 推荐排名
     */
    public List<CandidateRecomRecordSortingDO> listSorting(List<Integer> employeeIdList, List<Integer> positionIdList) {
        return candidateRecomRecordDao.listCandidateRecomRecordSorting(employeeIdList, positionIdList);
    }

    public List<CandidatePositionDO> listCandidatePositionByUserIdPositionId
            (List<Map<Integer, Integer>> param) {
        return candidatePositionDao.listCandidatePositionsByPositionIDUserID(param);

    }

    /**
     * 查找最新的浏览职位记录
     * @param userId
     * @param positionIdList
     * @return
     */
    public CandidatePositionRecord fetchRecentViewedPosition(int userId, List<Integer> positionIdList) {
        return candidatePositionDao.fetchRecentViewedPosition(userId, positionIdList);
    }

    /**
     * 增加申请的链路信息
     * @param applicationId 申请编号
     * @param psc_id    candidate_share_chain.id
     */
    public void  addApplicationPsc(int applicationId, int psc_id, int directReferralUserId){
        applicationreferralDao.addDataIgnoreDuplicate(applicationId, psc_id, directReferralUserId);
    }

    /**
     * 根据申请编号获取申请时的链路信息
     * @param applicationId
     * @return
     */
    public CandidateApplicationReferralDO getApplicationPscByApplicationId(int applicationId){
        return applicationreferralDao.getApplicationPscByApplication(applicationId);
    }
}
