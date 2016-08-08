/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.historydb;


import com.moseeker.db.historydb.tables.HistoryJobApplication;
import com.moseeker.db.historydb.tables.records.HistoryJobApplicationRecord;

import javax.annotation.Generated;

import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>historydb</code> 
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


	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<HistoryJobApplicationRecord> KEY_HISTORY_JOB_APPLICATION_PRIMARY = UniqueKeys0.KEY_HISTORY_JOB_APPLICATION_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<HistoryJobApplicationRecord> KEY_HISTORY_JOB_APPLICATION_PRIMARY = createUniqueKey(HistoryJobApplication.HISTORY_JOB_APPLICATION, HistoryJobApplication.HISTORY_JOB_APPLICATION.ID);
	}
}
