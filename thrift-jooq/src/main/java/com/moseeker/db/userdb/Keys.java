/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb;


import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.db.userdb.tables.UserWxViewer;
import com.moseeker.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxViewerRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;


/**
 * A class modelling foreign key relationships between tables of the <code>userdb</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<UserEmployeeRecord, Integer> IDENTITY_USER_EMPLOYEE = Identities0.IDENTITY_USER_EMPLOYEE;
	public static final Identity<UserEmployeePointsRecordRecord, Integer> IDENTITY_USER_EMPLOYEE_POINTS_RECORD = Identities0.IDENTITY_USER_EMPLOYEE_POINTS_RECORD;
	public static final Identity<UserFavPositionRecord, UInteger> IDENTITY_USER_FAV_POSITION = Identities0.IDENTITY_USER_FAV_POSITION;
	public static final Identity<UserHrAccountRecord, Integer> IDENTITY_USER_HR_ACCOUNT = Identities0.IDENTITY_USER_HR_ACCOUNT;
	public static final Identity<UserSettingsRecord, UInteger> IDENTITY_USER_SETTINGS = Identities0.IDENTITY_USER_SETTINGS;
	public static final Identity<UserUserRecord, UInteger> IDENTITY_USER_USER = Identities0.IDENTITY_USER_USER;
	public static final Identity<UserWxUserRecord, ULong> IDENTITY_USER_WX_USER = Identities0.IDENTITY_USER_WX_USER;
	public static final Identity<UserWxViewerRecord, UInteger> IDENTITY_USER_WX_VIEWER = Identities0.IDENTITY_USER_WX_VIEWER;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<UserEmployeeRecord> KEY_USER_EMPLOYEE_PRIMARY = UniqueKeys0.KEY_USER_EMPLOYEE_PRIMARY;
	public static final UniqueKey<UserEmployeePointsRecordRecord> KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY = UniqueKeys0.KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY;
	public static final UniqueKey<UserFavPositionRecord> KEY_USER_FAV_POSITION_PRIMARY = UniqueKeys0.KEY_USER_FAV_POSITION_PRIMARY;
	public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_PRIMARY = UniqueKeys0.KEY_USER_HR_ACCOUNT_PRIMARY;
	public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_WXUSER_ID = UniqueKeys0.KEY_USER_HR_ACCOUNT_WXUSER_ID;
	public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_PRIMARY = UniqueKeys0.KEY_USER_SETTINGS_PRIMARY;
	public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_UID = UniqueKeys0.KEY_USER_SETTINGS_UID;
	public static final UniqueKey<UserUserRecord> KEY_USER_USER_PRIMARY = UniqueKeys0.KEY_USER_USER_PRIMARY;
	public static final UniqueKey<UserUserRecord> KEY_USER_USER_UK_USER_USERNAME = UniqueKeys0.KEY_USER_USER_UK_USER_USERNAME;
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
		public static Identity<UserEmployeeRecord, Integer> IDENTITY_USER_EMPLOYEE = createIdentity(UserEmployee.USER_EMPLOYEE, UserEmployee.USER_EMPLOYEE.ID);
		public static Identity<UserEmployeePointsRecordRecord, Integer> IDENTITY_USER_EMPLOYEE_POINTS_RECORD = createIdentity(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD, UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID);
		public static Identity<UserFavPositionRecord, UInteger> IDENTITY_USER_FAV_POSITION = createIdentity(UserFavPosition.USER_FAV_POSITION, UserFavPosition.USER_FAV_POSITION.ID);
		public static Identity<UserHrAccountRecord, Integer> IDENTITY_USER_HR_ACCOUNT = createIdentity(UserHrAccount.USER_HR_ACCOUNT, UserHrAccount.USER_HR_ACCOUNT.ID);
		public static Identity<UserSettingsRecord, UInteger> IDENTITY_USER_SETTINGS = createIdentity(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.ID);
		public static Identity<UserUserRecord, UInteger> IDENTITY_USER_USER = createIdentity(UserUser.USER_USER, UserUser.USER_USER.ID);
		public static Identity<UserWxUserRecord, ULong> IDENTITY_USER_WX_USER = createIdentity(UserWxUser.USER_WX_USER, UserWxUser.USER_WX_USER.ID);
		public static Identity<UserWxViewerRecord, UInteger> IDENTITY_USER_WX_VIEWER = createIdentity(UserWxViewer.USER_WX_VIEWER, UserWxViewer.USER_WX_VIEWER.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<UserEmployeeRecord> KEY_USER_EMPLOYEE_PRIMARY = createUniqueKey(UserEmployee.USER_EMPLOYEE, UserEmployee.USER_EMPLOYEE.ID);
		public static final UniqueKey<UserEmployeePointsRecordRecord> KEY_USER_EMPLOYEE_POINTS_RECORD_PRIMARY = createUniqueKey(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD, UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID);
		public static final UniqueKey<UserFavPositionRecord> KEY_USER_FAV_POSITION_PRIMARY = createUniqueKey(UserFavPosition.USER_FAV_POSITION, UserFavPosition.USER_FAV_POSITION.ID);
		public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_PRIMARY = createUniqueKey(UserHrAccount.USER_HR_ACCOUNT, UserHrAccount.USER_HR_ACCOUNT.ID);
		public static final UniqueKey<UserHrAccountRecord> KEY_USER_HR_ACCOUNT_WXUSER_ID = createUniqueKey(UserHrAccount.USER_HR_ACCOUNT, UserHrAccount.USER_HR_ACCOUNT.WXUSER_ID);
		public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_PRIMARY = createUniqueKey(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.ID);
		public static final UniqueKey<UserSettingsRecord> KEY_USER_SETTINGS_UID = createUniqueKey(UserSettings.USER_SETTINGS, UserSettings.USER_SETTINGS.USER_ID);
		public static final UniqueKey<UserUserRecord> KEY_USER_USER_PRIMARY = createUniqueKey(UserUser.USER_USER, UserUser.USER_USER.ID);
		public static final UniqueKey<UserUserRecord> KEY_USER_USER_UK_USER_USERNAME = createUniqueKey(UserUser.USER_USER, UserUser.USER_USER.USERNAME);
		public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_PRIMARY = createUniqueKey(UserWxUser.USER_WX_USER, UserWxUser.USER_WX_USER.ID);
		public static final UniqueKey<UserWxUserRecord> KEY_USER_WX_USER_WECHAT_ID = createUniqueKey(UserWxUser.USER_WX_USER, UserWxUser.USER_WX_USER.WECHAT_ID, UserWxUser.USER_WX_USER.OPENID);
		public static final UniqueKey<UserWxViewerRecord> KEY_USER_WX_VIEWER_PRIMARY = createUniqueKey(UserWxViewer.USER_WX_VIEWER, UserWxViewer.USER_WX_VIEWER.ID);
	}
}
