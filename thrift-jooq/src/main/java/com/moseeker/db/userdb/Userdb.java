/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import com.moseeker.db.userdb.tables.CandidateVJobPositionRecom;
import com.moseeker.db.userdb.tables.UserBdUser;
import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.UserIntention;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.UserUserTmp;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.db.userdb.tables.UserWxViewer;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Userdb extends SchemaImpl {

	private static final long serialVersionUID = 252701351;

	/**
	 * The reference instance of <code>userdb</code>
	 */
	public static final Userdb USERDB = new Userdb();

	/**
	 * No further instances allowed
	 */
	private Userdb() {
		super("userdb");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM,
			UserEmployee.USER_EMPLOYEE,
			UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD,
			UserFavPosition.USER_FAV_POSITION,
			UserHrAccount.USER_HR_ACCOUNT,
			UserIntention.USER_INTENTION,
			UserSettings.USER_SETTINGS,
			UserUser.USER_USER,
			UserUserTmp.USER_USER_TMP,
			UserWxUser.USER_WX_USER,
			UserWxViewer.USER_WX_VIEWER,
			UserBdUser.USER_BD_USER);
	}
}
