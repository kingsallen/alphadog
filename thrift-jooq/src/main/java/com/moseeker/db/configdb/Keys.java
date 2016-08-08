/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb;


import com.moseeker.db.configdb.tables.BlogPosts;
import com.moseeker.db.configdb.tables.BlogPosts2;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationGroup;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.db.configdb.tables.ConfigAtsSource;
import com.moseeker.db.configdb.tables.ConfigCacheconfigRediskey;
import com.moseeker.db.configdb.tables.ConfigPositionKenexa;
import com.moseeker.db.configdb.tables.ConfigSysAdministrator;
import com.moseeker.db.configdb.tables.ConfigSysAppTemplate;
import com.moseeker.db.configdb.tables.ConfigSysCvTpl;
import com.moseeker.db.configdb.tables.ConfigSysH5StyleTpl;
import com.moseeker.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.db.configdb.tables.ConfigSysTemplateMessageColumnConfig;
import com.moseeker.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.db.configdb.tables.ConfigSysTemplateType;
import com.moseeker.db.configdb.tables.ConfigSysTheme;
import com.moseeker.db.configdb.tables.records.BlogPosts2Record;
import com.moseeker.db.configdb.tables.records.BlogPostsRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.db.configdb.tables.records.ConfigAtsSourceRecord;
import com.moseeker.db.configdb.tables.records.ConfigCacheconfigRediskeyRecord;
import com.moseeker.db.configdb.tables.records.ConfigPositionKenexaRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysAdministratorRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysAppTemplateRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysH5StyleTplRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysTemplateMessageColumnConfigRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysTemplateTypeRecord;
import com.moseeker.db.configdb.tables.records.ConfigSysThemeRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UInteger;


/**
 * A class modelling foreign key relationships between tables of the <code>configdb</code> 
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

	public static final Identity<BlogPostsRecord, UInteger> IDENTITY_BLOG_POSTS = Identities0.IDENTITY_BLOG_POSTS;
	public static final Identity<BlogPosts2Record, UInteger> IDENTITY_BLOG_POSTS2 = Identities0.IDENTITY_BLOG_POSTS2;
	public static final Identity<ConfigAtsSourceRecord, Integer> IDENTITY_CONFIG_ATS_SOURCE = Identities0.IDENTITY_CONFIG_ATS_SOURCE;
	public static final Identity<ConfigCacheconfigRediskeyRecord, Integer> IDENTITY_CONFIG_CACHECONFIG_REDISKEY = Identities0.IDENTITY_CONFIG_CACHECONFIG_REDISKEY;
	public static final Identity<ConfigPositionKenexaRecord, Integer> IDENTITY_CONFIG_POSITION_KENEXA = Identities0.IDENTITY_CONFIG_POSITION_KENEXA;
	public static final Identity<ConfigSysAdministratorRecord, UInteger> IDENTITY_CONFIG_SYS_ADMINISTRATOR = Identities0.IDENTITY_CONFIG_SYS_ADMINISTRATOR;
	public static final Identity<ConfigSysAppTemplateRecord, Integer> IDENTITY_CONFIG_SYS_APP_TEMPLATE = Identities0.IDENTITY_CONFIG_SYS_APP_TEMPLATE;
	public static final Identity<ConfigSysCvTplRecord, Integer> IDENTITY_CONFIG_SYS_CV_TPL = Identities0.IDENTITY_CONFIG_SYS_CV_TPL;
	public static final Identity<ConfigSysH5StyleTplRecord, Integer> IDENTITY_CONFIG_SYS_H5_STYLE_TPL = Identities0.IDENTITY_CONFIG_SYS_H5_STYLE_TPL;
	public static final Identity<ConfigSysPointsConfTplRecord, Integer> IDENTITY_CONFIG_SYS_POINTS_CONF_TPL = Identities0.IDENTITY_CONFIG_SYS_POINTS_CONF_TPL;
	public static final Identity<ConfigSysTemplateMessageColumnConfigRecord, UInteger> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG;
	public static final Identity<ConfigSysTemplateMessageLibraryRecord, UInteger> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY;
	public static final Identity<ConfigSysTemplateTypeRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_TYPE = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_TYPE;
	public static final Identity<ConfigSysThemeRecord, Integer> IDENTITY_CONFIG_SYS_THEME = Identities0.IDENTITY_CONFIG_SYS_THEME;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<BlogPostsRecord> KEY_BLOG_POSTS_PRIMARY = UniqueKeys0.KEY_BLOG_POSTS_PRIMARY;
	public static final UniqueKey<BlogPosts2Record> KEY_BLOG_POSTS2_PRIMARY = UniqueKeys0.KEY_BLOG_POSTS2_PRIMARY;
	public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY;
	public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_EVENT_KEY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_EVENTS_EVENT_KEY;
	public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY;
	public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_NAME = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_GROUP_NAME;
	public static final UniqueKey<ConfigAdminnotificationGroupmembersRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUPMEMBERS_PRIMARY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_GROUPMEMBERS_PRIMARY;
	public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY;
	public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_NAME = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_NAME;
	public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_MOBILEPHONE = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_MOBILEPHONE;
	public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_WECHATOPENID = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_WECHATOPENID;
	public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_EMAIL = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_EMAIL;
	public static final UniqueKey<ConfigAtsSourceRecord> KEY_CONFIG_ATS_SOURCE_PRIMARY = UniqueKeys0.KEY_CONFIG_ATS_SOURCE_PRIMARY;
	public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PRIMARY = UniqueKeys0.KEY_CONFIG_CACHECONFIG_REDISKEY_PRIMARY;
	public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_KEY_IDENTIFIER = UniqueKeys0.KEY_CONFIG_CACHECONFIG_REDISKEY_KEY_IDENTIFIER;
	public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PATTERN = UniqueKeys0.KEY_CONFIG_CACHECONFIG_REDISKEY_PATTERN;
	public static final UniqueKey<ConfigPositionKenexaRecord> KEY_CONFIG_POSITION_KENEXA_PRIMARY = UniqueKeys0.KEY_CONFIG_POSITION_KENEXA_PRIMARY;
	public static final UniqueKey<ConfigSysAdministratorRecord> KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY;
	public static final UniqueKey<ConfigSysAppTemplateRecord> KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY;
	public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_CV_TPL_PRIMARY;
	public static final UniqueKey<ConfigSysH5StyleTplRecord> KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY;
	public static final UniqueKey<ConfigSysPointsConfTplRecord> KEY_CONFIG_SYS_POINTS_CONF_TPL_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_POINTS_CONF_TPL_PRIMARY;
	public static final UniqueKey<ConfigSysTemplateMessageColumnConfigRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG_PRIMARY;
	public static final UniqueKey<ConfigSysTemplateMessageLibraryRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY;
	public static final UniqueKey<ConfigSysTemplateTypeRecord> KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY;
	public static final UniqueKey<ConfigSysThemeRecord> KEY_CONFIG_SYS_THEME_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_THEME_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<BlogPostsRecord, UInteger> IDENTITY_BLOG_POSTS = createIdentity(BlogPosts.BLOG_POSTS, BlogPosts.BLOG_POSTS.ID);
		public static Identity<BlogPosts2Record, UInteger> IDENTITY_BLOG_POSTS2 = createIdentity(BlogPosts2.BLOG_POSTS2, BlogPosts2.BLOG_POSTS2.ID);
		public static Identity<ConfigAtsSourceRecord, Integer> IDENTITY_CONFIG_ATS_SOURCE = createIdentity(ConfigAtsSource.CONFIG_ATS_SOURCE, ConfigAtsSource.CONFIG_ATS_SOURCE.ID);
		public static Identity<ConfigCacheconfigRediskeyRecord, Integer> IDENTITY_CONFIG_CACHECONFIG_REDISKEY = createIdentity(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.ID);
		public static Identity<ConfigPositionKenexaRecord, Integer> IDENTITY_CONFIG_POSITION_KENEXA = createIdentity(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID);
		public static Identity<ConfigSysAdministratorRecord, UInteger> IDENTITY_CONFIG_SYS_ADMINISTRATOR = createIdentity(ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR, ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR.ID);
		public static Identity<ConfigSysAppTemplateRecord, Integer> IDENTITY_CONFIG_SYS_APP_TEMPLATE = createIdentity(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID);
		public static Identity<ConfigSysCvTplRecord, Integer> IDENTITY_CONFIG_SYS_CV_TPL = createIdentity(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, ConfigSysCvTpl.CONFIG_SYS_CV_TPL.ID);
		public static Identity<ConfigSysH5StyleTplRecord, Integer> IDENTITY_CONFIG_SYS_H5_STYLE_TPL = createIdentity(ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL, ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL.ID);
		public static Identity<ConfigSysPointsConfTplRecord, Integer> IDENTITY_CONFIG_SYS_POINTS_CONF_TPL = createIdentity(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID);
		public static Identity<ConfigSysTemplateMessageColumnConfigRecord, UInteger> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG = createIdentity(ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG, ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG.ID);
		public static Identity<ConfigSysTemplateMessageLibraryRecord, UInteger> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY = createIdentity(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY, ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID);
		public static Identity<ConfigSysTemplateTypeRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_TYPE = createIdentity(ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE, ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE.ID);
		public static Identity<ConfigSysThemeRecord, Integer> IDENTITY_CONFIG_SYS_THEME = createIdentity(ConfigSysTheme.CONFIG_SYS_THEME, ConfigSysTheme.CONFIG_SYS_THEME.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<BlogPostsRecord> KEY_BLOG_POSTS_PRIMARY = createUniqueKey(BlogPosts.BLOG_POSTS, BlogPosts.BLOG_POSTS.ID);
		public static final UniqueKey<BlogPosts2Record> KEY_BLOG_POSTS2_PRIMARY = createUniqueKey(BlogPosts2.BLOG_POSTS2, BlogPosts2.BLOG_POSTS2.ID);
		public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY = createUniqueKey(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ID);
		public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_EVENT_KEY = createUniqueKey(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_KEY);
		public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY = createUniqueKey(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP, ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.ID);
		public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_NAME = createUniqueKey(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP, ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.NAME);
		public static final UniqueKey<ConfigAdminnotificationGroupmembersRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUPMEMBERS_PRIMARY = createUniqueKey(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS, ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.ID);
		public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.ID);
		public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_NAME = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.NAME);
		public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_MOBILEPHONE = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.MOBILEPHONE);
		public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_WECHATOPENID = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.WECHATOPENID);
		public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_EMAIL = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.EMAIL);
		public static final UniqueKey<ConfigAtsSourceRecord> KEY_CONFIG_ATS_SOURCE_PRIMARY = createUniqueKey(ConfigAtsSource.CONFIG_ATS_SOURCE, ConfigAtsSource.CONFIG_ATS_SOURCE.ID);
		public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PRIMARY = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.ID);
		public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_KEY_IDENTIFIER = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER);
		public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PATTERN = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PATTERN);
		public static final UniqueKey<ConfigPositionKenexaRecord> KEY_CONFIG_POSITION_KENEXA_PRIMARY = createUniqueKey(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID);
		public static final UniqueKey<ConfigSysAdministratorRecord> KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY = createUniqueKey(ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR, ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR.ID);
		public static final UniqueKey<ConfigSysAppTemplateRecord> KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY = createUniqueKey(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID);
		public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_PRIMARY = createUniqueKey(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, ConfigSysCvTpl.CONFIG_SYS_CV_TPL.ID);
		public static final UniqueKey<ConfigSysH5StyleTplRecord> KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY = createUniqueKey(ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL, ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL.ID);
		public static final UniqueKey<ConfigSysPointsConfTplRecord> KEY_CONFIG_SYS_POINTS_CONF_TPL_PRIMARY = createUniqueKey(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID);
		public static final UniqueKey<ConfigSysTemplateMessageColumnConfigRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG_PRIMARY = createUniqueKey(ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG, ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG.ID);
		public static final UniqueKey<ConfigSysTemplateMessageLibraryRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY = createUniqueKey(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY, ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID);
		public static final UniqueKey<ConfigSysTemplateTypeRecord> KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY = createUniqueKey(ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE, ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE.ID);
		public static final UniqueKey<ConfigSysThemeRecord> KEY_CONFIG_SYS_THEME_PRIMARY = createUniqueKey(ConfigSysTheme.CONFIG_SYS_THEME, ConfigSysTheme.CONFIG_SYS_THEME.ID);
	}
}
