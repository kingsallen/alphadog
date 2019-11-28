package com.moseeker.useraccounts.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.LeaderBoardTypeDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrLeaderBoard;
import com.moseeker.common.util.DateUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.entity.pojos.EmployeeInfo;
import com.moseeker.useraccounts.constant.LeaderBoardType;
import com.moseeker.useraccounts.domain.pojo.EmployeeLeaderBoardInfo;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
@Component
public class LeaderBoardEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SearchengineEntity searchengineEntity;

    @Autowired
    EmployeeEntity userEmployeeEntity;

    @Autowired
    LeaderBoardTypeDao leaderBoardTypeDao;

    /**
     * 指定员工的榜单信息
     * @param employeeInfo 员工信息
     * @param type 榜单类型
     * @return 榜单信息
     * @throws UserAccountException 业务异常
     */
    public EmployeeLeaderBoardInfo fetchLeaderBoardInfo(EmployeeInfo employeeInfo, LeaderBoardType type) throws UserAccountException {

        EmployeeLeaderBoardInfo info = new EmployeeLeaderBoardInfo();
        info.setId(employeeInfo.getId());
        String timeSpan = type.buildTimeSpan();

        JSONObject jsonObject = searchengineEntity.getEmployeeInfo(employeeInfo.getId());
        logger.info("fetchLeaderBoardInfo jsonObject:{}", jsonObject);

        if (jsonObject.getJSONObject("awards") != null && jsonObject.getJSONObject("awards").getJSONObject(timeSpan) != null) {
            info.setAward(jsonObject.getJSONObject("awards").getJSONObject(timeSpan).getInteger("award"));
            if(info.getAward()<0){
                info.setAward(0);
            }
            Object obj = jsonObject.getJSONObject("awards").getJSONObject(timeSpan).get("last_update_time");
            if(obj instanceof Long){
                info.setLastUpdateTime(jsonObject.getJSONObject("awards").getJSONObject(timeSpan).getLong("last_update_time"));
            }else if(obj instanceof String){
                try {
                    String lastUpdateTime =
                            jsonObject.getJSONObject("awards").getJSONObject(timeSpan).getString("last_update_time");
                    if(lastUpdateTime.contains("T")){
                        lastUpdateTime = LocalDateTime.parse(lastUpdateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    Date date = DateUtils.minuteTimeToDate(lastUpdateTime);
                    info.setLastUpdateTime(date.getTime());
                } catch (ParseException e) {
                    logger.warn(e.getMessage(),e);
                }

            }

        }
        if (info.getAward() > 0) {
            List<Integer> companyIdList = userEmployeeEntity.getCompanyIds(employeeInfo.getCompanyId());

            logger.info("fetchLeaderBoardInfo id:{}, award:{}, timeSpan:{}, cmopanyIdList:{}", employeeInfo.getId(), employeeInfo.getAward(),timeSpan, companyIdList);
            int sort = searchengineEntity.getSort(employeeInfo.getId(), info.getAward(), info.getLastUpdateTime(),timeSpan, companyIdList);
            logger.info("fetchLeaderBoardInfo sort:{}", sort);
            info.setSort(sort);
        }
        //todo delete
        logger.info("LeaderBoardEntity fetchLeaderBoardInfo info:{}", JSON.toJSONString(info));
        return info;
    }

    /**
     * 非指定员工的最后一名员工榜单信息
     * @param employeeInfo 员工信息
     * @param leaderBoardType 榜单信息
     * @return 员工榜单信息
     */
    public EmployeeLeaderBoardInfo fetchLasteaderBoardInfo(EmployeeInfo employeeInfo, LeaderBoardType leaderBoardType) {
        EmployeeLeaderBoardInfo info = new EmployeeLeaderBoardInfo();
        String timeSpan = leaderBoardType.buildTimeSpan();
        List<Integer> companyIdList = userEmployeeEntity.getCompanyIds(employeeInfo.getCompanyId());
        JSONObject lastEmployeeInfo = searchengineEntity.lastEmployeeInfo(employeeInfo.getId(), timeSpan, companyIdList);
        if (lastEmployeeInfo.getInteger("id") != null) {
            info.setId(lastEmployeeInfo.getInteger("id"));
            if (lastEmployeeInfo.getJSONObject("awards") != null && lastEmployeeInfo.getJSONObject("awards").getJSONObject(timeSpan) != null) {
                info.setAward(lastEmployeeInfo.getJSONObject("awards").getJSONObject(timeSpan).getInteger("award"));
                info.setLastUpdateTime(lastEmployeeInfo.getJSONObject("awards").getJSONObject(timeSpan).getLong("last_update_time"));
            }
            if (info.getAward() > 0) {
                info.setSort(searchengineEntity.getSort(info.getId(), info.getAward(), info.getLastUpdateTime(), timeSpan, companyIdList));
            }else{
                info.setAward(0);
            }
        }
        return info;
    }

    public HrLeaderBoard fetchLeaderBoardType(int companyId) {
        HrLeaderBoard leaderBoard = leaderBoardTypeDao.fetchOneByCompanyId(companyId);
        if (leaderBoard == null) {
            leaderBoard = new HrLeaderBoard();
            leaderBoard.setId(0);
            leaderBoard.setCompanyId(companyId);
            leaderBoard.setType((byte)2);
        }
        return leaderBoard;
    }

    public void update(int companyId, byte type) {
        leaderBoardTypeDao.upsert(companyId, type);
    }
}
