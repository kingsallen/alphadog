/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb.tables;


import com.moseeker.db.userdb.Keys;
import com.moseeker.db.userdb.Userdb;
import com.moseeker.db.userdb.tables.records.UserWxViewerRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * 用户浏览者记录
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserWxViewer extends TableImpl<UserWxViewerRecord> {

	private static final long serialVersionUID = 2127996881;

	/**
	 * The reference instance of <code>userdb.user_wx_viewer</code>
	 */
	public static final UserWxViewer USER_WX_VIEWER = new UserWxViewer();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserWxViewerRecord> getRecordType() {
		return UserWxViewerRecord.class;
	}

	/**
	 * The column <code>userdb.user_wx_viewer.id</code>. 主key
	 */
	public final TableField<UserWxViewerRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>userdb.user_wx_viewer.sysuser_id</code>.
	 */
	public final TableField<UserWxViewerRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * The column <code>userdb.user_wx_viewer.idcode</code>.
	 */
	public final TableField<UserWxViewerRecord, String> IDCODE = createField("idcode", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>userdb.user_wx_viewer.client_type</code>.
	 */
	public final TableField<UserWxViewerRecord, Integer> CLIENT_TYPE = createField("client_type", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "");

	/**
	 * Create a <code>userdb.user_wx_viewer</code> table reference
	 */
	public UserWxViewer() {
		this("user_wx_viewer", null);
	}

	/**
	 * Create an aliased <code>userdb.user_wx_viewer</code> table reference
	 */
	public UserWxViewer(String alias) {
		this(alias, USER_WX_VIEWER);
	}

	private UserWxViewer(String alias, Table<UserWxViewerRecord> aliased) {
		this(alias, aliased, null);
	}

	private UserWxViewer(String alias, Table<UserWxViewerRecord> aliased, Field<?>[] parameters) {
		super(alias, Userdb.USERDB, aliased, parameters, "用户浏览者记录");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<UserWxViewerRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_USER_WX_VIEWER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UserWxViewerRecord> getPrimaryKey() {
		return Keys.KEY_USER_WX_VIEWER_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UserWxViewerRecord>> getKeys() {
		return Arrays.<UniqueKey<UserWxViewerRecord>>asList(Keys.KEY_USER_WX_VIEWER_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserWxViewer as(String alias) {
		return new UserWxViewer(alias, this);
	}

	/**
	 * Rename this table
	 */
	public UserWxViewer rename(String name) {
		return new UserWxViewer(name, null);
	}
}