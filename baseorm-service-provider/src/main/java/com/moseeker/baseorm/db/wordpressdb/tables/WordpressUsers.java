/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUsersRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;


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
public class WordpressUsers extends TableImpl<WordpressUsersRecord> {

	private static final long serialVersionUID = -1861713339;

	/**
	 * The reference instance of <code>wordpressdb.wordpress_users</code>
	 */
	public static final WordpressUsers WORDPRESS_USERS = new WordpressUsers();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordpressUsersRecord> getRecordType() {
		return WordpressUsersRecord.class;
	}

	/**
	 * The column <code>wordpressdb.wordpress_users.ID</code>.
	 */
	public final TableField<WordpressUsersRecord, ULong> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_login</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_LOGIN = createField("user_login", org.jooq.impl.SQLDataType.VARCHAR.length(60).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_pass</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_PASS = createField("user_pass", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_nicename</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_NICENAME = createField("user_nicename", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_email</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_EMAIL = createField("user_email", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_url</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_URL = createField("user_url", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_registered</code>.
	 */
	public final TableField<WordpressUsersRecord, Timestamp> USER_REGISTERED = createField("user_registered", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_activation_key</code>.
	 */
	public final TableField<WordpressUsersRecord, String> USER_ACTIVATION_KEY = createField("user_activation_key", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.user_status</code>.
	 */
	public final TableField<WordpressUsersRecord, Integer> USER_STATUS = createField("user_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_users.display_name</code>.
	 */
	public final TableField<WordpressUsersRecord, String> DISPLAY_NAME = createField("display_name", org.jooq.impl.SQLDataType.VARCHAR.length(250).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>wordpressdb.wordpress_users</code> table reference
	 */
	public WordpressUsers() {
		this("wordpress_users", null);
	}

	/**
	 * Create an aliased <code>wordpressdb.wordpress_users</code> table reference
	 */
	public WordpressUsers(String alias) {
		this(alias, WORDPRESS_USERS);
	}

	private WordpressUsers(String alias, Table<WordpressUsersRecord> aliased) {
		this(alias, aliased, null);
	}

	private WordpressUsers(String alias, Table<WordpressUsersRecord> aliased, Field<?>[] parameters) {
		super(alias, Wordpressdb.WORDPRESSDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordpressUsersRecord, ULong> getIdentity() {
		return Keys.IDENTITY_WORDPRESS_USERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordpressUsersRecord> getPrimaryKey() {
		return Keys.KEY_WORDPRESS_USERS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordpressUsersRecord>> getKeys() {
		return Arrays.<UniqueKey<WordpressUsersRecord>>asList(Keys.KEY_WORDPRESS_USERS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressUsers as(String alias) {
		return new WordpressUsers(alias, this);
	}

	/**
	 * Rename this table
	 */
	public WordpressUsers rename(String name) {
		return new WordpressUsers(name, null);
	}
}