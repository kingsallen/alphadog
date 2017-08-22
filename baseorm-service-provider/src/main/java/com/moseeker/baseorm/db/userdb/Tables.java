/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb;


import com.moseeker.baseorm.db.userdb.tables.CandidateVJobPositionRecom;
import com.moseeker.baseorm.db.userdb.tables.UserAliUser;
import com.moseeker.baseorm.db.userdb.tables.UserBdUser;
import com.moseeker.baseorm.db.userdb.tables.UserCollectPosition;
import com.moseeker.baseorm.db.userdb.tables.UserCompanyFollow;
import com.moseeker.baseorm.db.userdb.tables.UserCompanyVisitReq;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecordCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.UserFavPosition;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserIntention;
import com.moseeker.baseorm.db.userdb.tables.UserSearchCondition;
import com.moseeker.baseorm.db.userdb.tables.UserSettings;
import com.moseeker.baseorm.db.userdb.tables.UserSysAuthGroup;
import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserViewedPosition;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxViewer;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in userdb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * VIEW
     */
    public static final CandidateVJobPositionRecom CANDIDATE_V_JOB_POSITION_RECOM = com.moseeker.baseorm.db.userdb.tables.CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM;

    /**
     * 阿里用户信息表
     */
    public static final UserAliUser USER_ALI_USER = com.moseeker.baseorm.db.userdb.tables.UserAliUser.USER_ALI_USER;

    /**
     * 百度用户信息表
     */
    public static final UserBdUser USER_BD_USER = com.moseeker.baseorm.db.userdb.tables.UserBdUser.USER_BD_USER;

    /**
     * 用户职位收藏
     */
    public static final UserCollectPosition USER_COLLECT_POSITION = com.moseeker.baseorm.db.userdb.tables.UserCollectPosition.USER_COLLECT_POSITION;

    /**
     * 公司关注表
     */
    public static final UserCompanyFollow USER_COMPANY_FOLLOW = com.moseeker.baseorm.db.userdb.tables.UserCompanyFollow.USER_COMPANY_FOLLOW;

    /**
     * C端用户申请参观记录表
     */
    public static final UserCompanyVisitReq USER_COMPANY_VISIT_REQ = com.moseeker.baseorm.db.userdb.tables.UserCompanyVisitReq.USER_COMPANY_VISIT_REQ;

    /**
     * The table <code>userdb.user_employee</code>.
     */
    public static final UserEmployee USER_EMPLOYEE = com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;

    /**
     * 员工积分记录表
     */
    public static final UserEmployeePointsRecord USER_EMPLOYEE_POINTS_RECORD = com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD;

    /**
     * The table <code>userdb.user_employee_points_record_company_rel</code>.
     */
    public static final UserEmployeePointsRecordCompanyRel USER_EMPLOYEE_POINTS_RECORD_COMPANY_REL = com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecordCompanyRel.USER_EMPLOYEE_POINTS_RECORD_COMPANY_REL;

    /**
     * 用户职位收藏
     */
    public static final UserFavPosition USER_FAV_POSITION = com.moseeker.baseorm.db.userdb.tables.UserFavPosition.USER_FAV_POSITION;

    /**
     * hr账号表
     */
    public static final UserHrAccount USER_HR_ACCOUNT = com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT;

    /**
     * 用户求职意向
     */
    public static final UserIntention USER_INTENTION = com.moseeker.baseorm.db.userdb.tables.UserIntention.USER_INTENTION;

    /**
     * 用户搜索条件(qx职位搜索)
     */
    public static final UserSearchCondition USER_SEARCH_CONDITION = com.moseeker.baseorm.db.userdb.tables.UserSearchCondition.USER_SEARCH_CONDITION;

    /**
     * 用户设置表
     */
    public static final UserSettings USER_SETTINGS = com.moseeker.baseorm.db.userdb.tables.UserSettings.USER_SETTINGS;

    /**
     * sysplat用户权限
     */
    public static final UserSysAuthGroup USER_SYS_AUTH_GROUP = com.moseeker.baseorm.db.userdb.tables.UserSysAuthGroup.USER_SYS_AUTH_GROUP;

    /**
     * 第三方关联帐号表
     */
    public static final UserThirdpartyUser USER_THIRDPARTY_USER = com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser.USER_THIRDPARTY_USER;

    /**
     * 用户表
     */
    public static final UserUser USER_USER = com.moseeker.baseorm.db.userdb.tables.UserUser.USER_USER;

    /**
     * 用户查看过的职位
     */
    public static final UserViewedPosition USER_VIEWED_POSITION = com.moseeker.baseorm.db.userdb.tables.UserViewedPosition.USER_VIEWED_POSITION;

    /**
     * 微信用户表
     */
    public static final UserWxUser USER_WX_USER = com.moseeker.baseorm.db.userdb.tables.UserWxUser.USER_WX_USER;

    /**
     * 用户浏览者记录
     */
    public static final UserWxViewer USER_WX_VIEWER = com.moseeker.baseorm.db.userdb.tables.UserWxViewer.USER_WX_VIEWER;
}
