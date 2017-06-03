/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb;


import com.moseeker.baseorm.db.userdb.tables.*;
import com.moseeker.baseorm.db.userdb.tables.records.*;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships between tables of the <code>userdb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<UserBdUserRecord, ULong> IDENTITY_USER_BD_USER = Identities0.IDENTITY_USER_BD_USER;
    public static final Identity<UserCollectPositionRecord, Integer> IDENTITY_USER_COLLECT_POSITION = Identities0.IDENTITY_USER_COLLECT_POSITION;
    public static final Identity<UserEmployeeRecord, Integer> IDENTITY_USER_EMPLOYEE = Identities0.IDENTITY_USER_EMPLOYEE;
    public static final Identity<UserEmployeePointsRecordRecord, Integer> IDENTITY_USER_EMPLOYEE_POINTS_RECORD = Identities0.IDENTITY_USER_EMPLOYEE_POINTS_RECORD;
    public static final Identity<UserFavPositionRecord, UInteger> IDENTITY_USER_FAV_POSITION = Identities0.IDENTITY_USER_FAV_POSITION;
    public static final Identity<UserHrAccountRecord, Integer> IDENTITY_USER_HR_ACCOUNT = Identities0.IDENTITY_USER_HR_ACCOUNT;
    public static final Identity<UserSearchConditionRecord, Integer> IDENTITY_USER_SEARCH_CONDITION = Identities0.IDENTITY_USER_SEARCH_CONDITION;
    public static final Identity<UserSettingsRecord, UInteger> IDENTITY_USER_SETTINGS = Identities0.IDENTITY_USER_SETTINGS;
    public static final Identity<UserThirdpartyUserRecord, ULong> IDENTITY_USER_THIRDPARTY_USER = Identities0.IDENTITY_USER_THIRDPARTY_USER;
    public static final Identity<UserUserRecord, UInteger> IDENTITY_USER_USER = Identities0.IDENTITY_USER_USER;
    public static final Identity<UserViewedPositionRecord, Integer> IDENTITY_USER_VIEWED_POSITION = Identities0.IDENTITY_USER_VIEWED_POSITION;
    public static final Identity<UserWxUserRecord, ULong> IDENTITY_USER_WX_USER = Identities0.IDENTITY_USER_WX_USER;
    public static final Identity<UserWxViewerRecord, UInteger> IDENTITY_USER_WX_VIEWER = Identities0.IDENTITY_USER_WX_VIEWER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<UserBdUserRecord> KEY_USER_BD_USER_PRIMARY = UniqueKeys0.KEY_USER_BD_USER_PRIMARY;
    public static final UniqueKey<UserBdUserRecord> KEY_USER_BD_USER_UID = UniqueKeys0.KEY_USER_BD_USER_UID;
    public static final UniqueKey<UserCollectPositionRecord> KEY_USER_COLLECT_POSITION_PRIMARY = UniqueKeys0.KEY_USER_COLLECT_POSITION_PRIMARY;
    public static final UniqueKey<UserEmployeeRecord> KEY_USER_EMPLOYEE_PRIMARY = UniqueKeys0.KEY_USER_EMPLOYEE_PRIMARY;
    public static final UniqueKey<UserEmployeePointsRecordRecord> KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY = UniqueKeys0.KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY;
    public static final UniqueKey<UserFavPositionRecord> KEY_USER_FAV_POSITION_PRIMARY = UniqueKeys0.KEY_USER_FAV_POSITION_PRIMARY;
    public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_PRIMARY = UniqueKeys0.KEY_USER_HR_ACCOUNT_PRIMARY;
    public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_WXUSER_ID = UniqueKeys0.KEY_USER_HR_ACCOUNT_WXUSER_ID;
    public static final UniqueKey<UserSearchConditionRecord> KEY_USER_SEARCH_CONDITION_PRIMARY = UniqueKeys0.KEY_USER_SEARCH_CONDITION_PRIMARY;
    public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_PRIMARY = UniqueKeys0.KEY_USER_SETTINGS_PRIMARY;
    public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_UID = UniqueKeys0.KEY_USER_SETTINGS_UID;
    public static final UniqueKey<UserThirdpartyUserRecord> KEY_USER_THIRDPARTY_USER_PRIMARY = UniqueKeys0.KEY_USER_THIRDPARTY_USER_PRIMARY;
    public static final UniqueKey<UserThirdpartyUserRecord> KEY_USER_THIRDPARTY_USER_USER_ID = UniqueKeys0.KEY_USER_THIRDPARTY_USER_USER_ID;
    public static final UniqueKey<UserUserRecord> KEY_USER_USER_PRIMARY = UniqueKeys0.KEY_USER_USER_PRIMARY;
    public static final UniqueKey<UserUserRecord> KEY_USER_USER_UK_USER_USERNAME = UniqueKeys0.KEY_USER_USER_UK_USER_USERNAME;
    public static final UniqueKey<UserUserNameEmail1128Record> KEY_USER_USER_NAME_EMAIL1128_PRIMARY = UniqueKeys0.KEY_USER_USER_NAME_EMAIL1128_PRIMARY;
    public static final UniqueKey<UserUserTmpRecord> KEY_USER_USER_TMP_PRIMARY = UniqueKeys0.KEY_USER_USER_TMP_PRIMARY;
    public static final UniqueKey<UserViewedPositionRecord> KEY_USER_VIEWED_POSITION_PRIMARY = UniqueKeys0.KEY_USER_VIEWED_POSITION_PRIMARY;
    public static final UniqueKey<UserViewedPositionRecord> KEY_USER_VIEWED_POSITION_USER_POSITION_UNIQUE = UniqueKeys0.KEY_USER_VIEWED_POSITION_USER_POSITION_UNIQUE;
    public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_PRIMARY = UniqueKeys0.KEY_USER_WX_USER_PRIMARY;
    public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_WECHAT_ID = UniqueKeys0.KEY_USER_WX_USER_WECHAT_ID;
    public static final UniqueKey<UserWxViewerRecord> KEY_USER_WX_VIEWER_PRIMARY = UniqueKeys0.KEY_USER_WX_VIEWER_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<UserBdUserRecord, ULong> IDENTITY_USER_BD_USER = createIdentity(UserBdUser.USER_BD_USER, UserBdUser.USER_BD_USER.ID);
        public static Identity<UserCollectPositionRecord, Integer> IDENTITY_USER_COLLECT_POSITION = createIdentity(UserCollectPosition.USER_COLLECT_POSITION, UserCollectPosition.USER_COLLECT_POSITION.ID);
        public static Identity<UserEmployeeRecord, Integer> IDENTITY_USER_EMPLOYEE = createIdentity(UserEmployee.USER_EMPLOYEE, UserEmployee.USER_EMPLOYEE.ID);
        public static Identity<UserEmployeePointsRecordRecord, Integer> IDENTITY_USER_EMPLOYEE_POINTS_RECORD = createIdentity(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD, UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID);
        public static Identity<UserFavPositionRecord, UInteger> IDENTITY_USER_FAV_POSITION = createIdentity(UserFavPosition.USER_FAV_POSITION, UserFavPosition.USER_FAV_POSITION.ID);
        public static Identity<UserHrAccountRecord, Integer> IDENTITY_USER_HR_ACCOUNT = createIdentity(UserHrAccount.USER_HR_ACCOUNT, UserHrAccount.USER_HR_ACCOUNT.ID);
        public static Identity<UserSearchConditionRecord, Integer> IDENTITY_USER_SEARCH_CONDITION = createIdentity(UserSearchCondition.USER_SEARCH_CONDITION, UserSearchCondition.USER_SEARCH_CONDITION.ID);
        public static Identity<UserSettingsRecord, UInteger> IDENTITY_USER_SETTINGS = createIdentity(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.ID);
        public static Identity<UserThirdpartyUserRecord, ULong> IDENTITY_USER_THIRDPARTY_USER = createIdentity(UserThirdpartyUser.USER_THIRDPARTY_USER, UserThirdpartyUser.USER_THIRDPARTY_USER.ID);
        public static Identity<UserUserRecord, UInteger> IDENTITY_USER_USER = createIdentity(UserUser.USER_USER, UserUser.USER_USER.ID);
        public static Identity<UserViewedPositionRecord, Integer> IDENTITY_USER_VIEWED_POSITION = createIdentity(UserViewedPosition.USER_VIEWED_POSITION, UserViewedPosition.USER_VIEWED_POSITION.ID);
        public static Identity<UserWxUserRecord, ULong> IDENTITY_USER_WX_USER = createIdentity(UserWxUser.USER_WX_USER, UserWxUser.USER_WX_USER.ID);
        public static Identity<UserWxViewerRecord, UInteger> IDENTITY_USER_WX_VIEWER = createIdentity(UserWxViewer.USER_WX_VIEWER, UserWxViewer.USER_WX_VIEWER.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<UserBdUserRecord> KEY_USER_BD_USER_PRIMARY = createUniqueKey(UserBdUser.USER_BD_USER, "KEY_user_bd_user_PRIMARY", UserBdUser.USER_BD_USER.ID);
        public static final UniqueKey<UserBdUserRecord> KEY_USER_BD_USER_UID = createUniqueKey(UserBdUser.USER_BD_USER, "KEY_user_bd_user_uid", UserBdUser.USER_BD_USER.UID);
        public static final UniqueKey<UserCollectPositionRecord> KEY_USER_COLLECT_POSITION_PRIMARY = createUniqueKey(UserCollectPosition.USER_COLLECT_POSITION, "KEY_user_collect_position_PRIMARY", UserCollectPosition.USER_COLLECT_POSITION.ID);
        public static final UniqueKey<UserEmployeeRecord> KEY_USER_EMPLOYEE_PRIMARY = createUniqueKey(UserEmployee.USER_EMPLOYEE, "KEY_user_employee_PRIMARY", UserEmployee.USER_EMPLOYEE.ID);
        public static final UniqueKey<UserEmployeePointsRecordRecord> KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY = createUniqueKey(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD, "KEY_user_employee_points_record_PRIMARY", UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID);
        public static final UniqueKey<UserFavPositionRecord> KEY_USER_FAV_POSITION_PRIMARY = createUniqueKey(UserFavPosition.USER_FAV_POSITION, "KEY_user_fav_position_PRIMARY", UserFavPosition.USER_FAV_POSITION.ID);
        public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_PRIMARY = createUniqueKey(UserHrAccount.USER_HR_ACCOUNT, "KEY_user_hr_account_PRIMARY", UserHrAccount.USER_HR_ACCOUNT.ID);
        public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_WXUSER_ID = createUniqueKey(UserHrAccount.USER_HR_ACCOUNT, "KEY_user_hr_account_wxuser_id", UserHrAccount.USER_HR_ACCOUNT.WXUSER_ID);
        public static final UniqueKey<UserSearchConditionRecord> KEY_USER_SEARCH_CONDITION_PRIMARY = createUniqueKey(UserSearchCondition.USER_SEARCH_CONDITION, "KEY_user_search_condition_PRIMARY", UserSearchCondition.USER_SEARCH_CONDITION.ID);
        public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_PRIMARY = createUniqueKey(UserSettings.USER_SETTINGS, "KEY_user_settings_PRIMARY", UserSettings.USER_SETTINGS.ID);
        public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_UID = createUniqueKey(UserSettings.USER_SETTINGS, "KEY_user_settings_uid", UserSettings.USER_SETTINGS.USER_ID);
        public static final UniqueKey<UserThirdpartyUserRecord> KEY_USER_THIRDPARTY_USER_PRIMARY = createUniqueKey(UserThirdpartyUser.USER_THIRDPARTY_USER, "KEY_user_thirdparty_user_PRIMARY", UserThirdpartyUser.USER_THIRDPARTY_USER.ID);
        public static final UniqueKey<UserThirdpartyUserRecord> KEY_USER_THIRDPARTY_USER_USER_ID = createUniqueKey(UserThirdpartyUser.USER_THIRDPARTY_USER, "KEY_user_thirdparty_user_user_id", UserThirdpartyUser.USER_THIRDPARTY_USER.USER_ID, UserThirdpartyUser.USER_THIRDPARTY_USER.SOURCE_ID);
        public static final UniqueKey<UserUserRecord> KEY_USER_USER_PRIMARY = createUniqueKey(UserUser.USER_USER, "KEY_user_user_PRIMARY", UserUser.USER_USER.ID);
        public static final UniqueKey<UserUserRecord> KEY_USER_USER_UK_USER_USERNAME = createUniqueKey(UserUser.USER_USER, "KEY_user_user_uk_user_username", UserUser.USER_USER.USERNAME);
        public static final UniqueKey<UserUserNameEmail1128Record> KEY_USER_USER_NAME_EMAIL1128_PRIMARY = createUniqueKey(UserUserNameEmail1128.USER_USER_NAME_EMAIL1128, "KEY_user_user_name_email1128_PRIMARY", UserUserNameEmail1128.USER_USER_NAME_EMAIL1128.ID);
        public static final UniqueKey<UserUserTmpRecord> KEY_USER_USER_TMP_PRIMARY = createUniqueKey(UserUserTmp.USER_USER_TMP, "KEY_user_user_tmp_PRIMARY", UserUserTmp.USER_USER_TMP.ID);
        public static final UniqueKey<UserViewedPositionRecord> KEY_USER_VIEWED_POSITION_PRIMARY = createUniqueKey(UserViewedPosition.USER_VIEWED_POSITION, "KEY_user_viewed_position_PRIMARY", UserViewedPosition.USER_VIEWED_POSITION.ID);
        public static final UniqueKey<UserViewedPositionRecord> KEY_USER_VIEWED_POSITION_USER_POSITION_UNIQUE = createUniqueKey(UserViewedPosition.USER_VIEWED_POSITION, "KEY_user_viewed_position_user_position_unique", UserViewedPosition.USER_VIEWED_POSITION.USER_ID, UserViewedPosition.USER_VIEWED_POSITION.POSITION_ID);
        public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_PRIMARY = createUniqueKey(UserWxUser.USER_WX_USER, "KEY_user_wx_user_PRIMARY", UserWxUser.USER_WX_USER.ID);
        public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_WECHAT_ID = createUniqueKey(UserWxUser.USER_WX_USER, "KEY_user_wx_user_wechat_id", UserWxUser.USER_WX_USER.WECHAT_ID, UserWxUser.USER_WX_USER.OPENID);
        public static final UniqueKey<UserWxViewerRecord> KEY_USER_WX_VIEWER_PRIMARY = createUniqueKey(UserWxViewer.USER_WX_VIEWER, "KEY_user_wx_viewer_PRIMARY", UserWxViewer.USER_WX_VIEWER.ID);
    }
}
