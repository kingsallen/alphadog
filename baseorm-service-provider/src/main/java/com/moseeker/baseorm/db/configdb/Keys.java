/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb;


import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationChannel;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroup;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.baseorm.db.configdb.tables.ConfigAtsSource;
import com.moseeker.baseorm.db.configdb.tables.ConfigCacheconfigRediskey;
import com.moseeker.baseorm.db.configdb.tables.ConfigCronjobs;
import com.moseeker.baseorm.db.configdb.tables.ConfigHbBalance;
import com.moseeker.baseorm.db.configdb.tables.ConfigOmsSwitchManagement;
import com.moseeker.baseorm.db.configdb.tables.ConfigPositionKenexa;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysAdministrator;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysAppTemplate;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysCvTpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysH5StyleTpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageColumnConfig;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateType;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTheme;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAtsSourceRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCacheconfigRediskeyRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigCronjobsRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigHbBalanceRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigOmsSwitchManagementRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigPositionKenexaRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAdministratorRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAppTemplateRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysH5StyleTplRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageColumnConfigRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateTypeRecord;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysThemeRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>configdb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ConfigAdminnotificationChannelRecord, Integer> IDENTITY_CONFIG_ADMINNOTIFICATION_CHANNEL = Identities0.IDENTITY_CONFIG_ADMINNOTIFICATION_CHANNEL;
    public static final Identity<ConfigAtsSourceRecord, Integer> IDENTITY_CONFIG_ATS_SOURCE = Identities0.IDENTITY_CONFIG_ATS_SOURCE;
    public static final Identity<ConfigCacheconfigRediskeyRecord, Integer> IDENTITY_CONFIG_CACHECONFIG_REDISKEY = Identities0.IDENTITY_CONFIG_CACHECONFIG_REDISKEY;
    public static final Identity<ConfigCronjobsRecord, Integer> IDENTITY_CONFIG_CRONJOBS = Identities0.IDENTITY_CONFIG_CRONJOBS;
    public static final Identity<ConfigHbBalanceRecord, Integer> IDENTITY_CONFIG_HB_BALANCE = Identities0.IDENTITY_CONFIG_HB_BALANCE;
    public static final Identity<ConfigOmsSwitchManagementRecord, Integer> IDENTITY_CONFIG_OMS_SWITCH_MANAGEMENT = Identities0.IDENTITY_CONFIG_OMS_SWITCH_MANAGEMENT;
    public static final Identity<ConfigPositionKenexaRecord, Integer> IDENTITY_CONFIG_POSITION_KENEXA = Identities0.IDENTITY_CONFIG_POSITION_KENEXA;
    public static final Identity<ConfigSysAdministratorRecord, Integer> IDENTITY_CONFIG_SYS_ADMINISTRATOR = Identities0.IDENTITY_CONFIG_SYS_ADMINISTRATOR;
    public static final Identity<ConfigSysAppTemplateRecord, Integer> IDENTITY_CONFIG_SYS_APP_TEMPLATE = Identities0.IDENTITY_CONFIG_SYS_APP_TEMPLATE;
    public static final Identity<ConfigSysCvTplRecord, Integer> IDENTITY_CONFIG_SYS_CV_TPL = Identities0.IDENTITY_CONFIG_SYS_CV_TPL;
    public static final Identity<ConfigSysH5StyleTplRecord, Integer> IDENTITY_CONFIG_SYS_H5_STYLE_TPL = Identities0.IDENTITY_CONFIG_SYS_H5_STYLE_TPL;
    public static final Identity<ConfigSysPointsConfTplRecord, Integer> IDENTITY_CONFIG_SYS_POINTS_CONF_TPL = Identities0.IDENTITY_CONFIG_SYS_POINTS_CONF_TPL;
    public static final Identity<ConfigSysTemplateMessageColumnConfigRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG;
    public static final Identity<ConfigSysTemplateMessageLibraryRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY;
    public static final Identity<ConfigSysTemplateTypeRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_TYPE = Identities0.IDENTITY_CONFIG_SYS_TEMPLATE_TYPE;
    public static final Identity<ConfigSysThemeRecord, Integer> IDENTITY_CONFIG_SYS_THEME = Identities0.IDENTITY_CONFIG_SYS_THEME;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ConfigAdminnotificationChannelRecord> KEY_CONFIG_ADMINNOTIFICATION_CHANNEL_PRIMARY = UniqueKeys0.KEY_CONFIG_ADMINNOTIFICATION_CHANNEL_PRIMARY;
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
    public static final UniqueKey<ConfigCronjobsRecord> KEY_CONFIG_CRONJOBS_PRIMARY = UniqueKeys0.KEY_CONFIG_CRONJOBS_PRIMARY;
    public static final UniqueKey<ConfigHbBalanceRecord> KEY_CONFIG_HB_BALANCE_PRIMARY = UniqueKeys0.KEY_CONFIG_HB_BALANCE_PRIMARY;
    public static final UniqueKey<ConfigOmsSwitchManagementRecord> KEY_CONFIG_OMS_SWITCH_MANAGEMENT_PRIMARY = UniqueKeys0.KEY_CONFIG_OMS_SWITCH_MANAGEMENT_PRIMARY;
    public static final UniqueKey<ConfigOmsSwitchManagementRecord> KEY_CONFIG_OMS_SWITCH_MANAGEMENT_MODULE_NAME和COMPANY_ID = UniqueKeys0.KEY_CONFIG_OMS_SWITCH_MANAGEMENT_MODULE_NAME和COMPANY_ID;
    public static final UniqueKey<ConfigPositionKenexaRecord> KEY_CONFIG_POSITION_KENEXA_PRIMARY = UniqueKeys0.KEY_CONFIG_POSITION_KENEXA_PRIMARY;
    public static final UniqueKey<ConfigSysAdministratorRecord> KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY;
    public static final UniqueKey<ConfigSysAppTemplateRecord> KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY;
    public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_PRIMARY = UniqueKeys0.KEY_CONFIG_SYS_CV_TPL_PRIMARY;
    public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_FIELD_NAME_KEY = UniqueKeys0.KEY_CONFIG_SYS_CV_TPL_FIELD_NAME_KEY;
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
        public static Identity<ConfigAdminnotificationChannelRecord, Integer> IDENTITY_CONFIG_ADMINNOTIFICATION_CHANNEL = createIdentity(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL, ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL.ID);
        public static Identity<ConfigAtsSourceRecord, Integer> IDENTITY_CONFIG_ATS_SOURCE = createIdentity(ConfigAtsSource.CONFIG_ATS_SOURCE, ConfigAtsSource.CONFIG_ATS_SOURCE.ID);
        public static Identity<ConfigCacheconfigRediskeyRecord, Integer> IDENTITY_CONFIG_CACHECONFIG_REDISKEY = createIdentity(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.ID);
        public static Identity<ConfigCronjobsRecord, Integer> IDENTITY_CONFIG_CRONJOBS = createIdentity(ConfigCronjobs.CONFIG_CRONJOBS, ConfigCronjobs.CONFIG_CRONJOBS.ID);
        public static Identity<ConfigHbBalanceRecord, Integer> IDENTITY_CONFIG_HB_BALANCE = createIdentity(ConfigHbBalance.CONFIG_HB_BALANCE, ConfigHbBalance.CONFIG_HB_BALANCE.ID);
        public static Identity<ConfigOmsSwitchManagementRecord, Integer> IDENTITY_CONFIG_OMS_SWITCH_MANAGEMENT = createIdentity(ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT, ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.ID);
        public static Identity<ConfigPositionKenexaRecord, Integer> IDENTITY_CONFIG_POSITION_KENEXA = createIdentity(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID);
        public static Identity<ConfigSysAdministratorRecord, Integer> IDENTITY_CONFIG_SYS_ADMINISTRATOR = createIdentity(ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR, ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR.ID);
        public static Identity<ConfigSysAppTemplateRecord, Integer> IDENTITY_CONFIG_SYS_APP_TEMPLATE = createIdentity(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID);
        public static Identity<ConfigSysCvTplRecord, Integer> IDENTITY_CONFIG_SYS_CV_TPL = createIdentity(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, ConfigSysCvTpl.CONFIG_SYS_CV_TPL.ID);
        public static Identity<ConfigSysH5StyleTplRecord, Integer> IDENTITY_CONFIG_SYS_H5_STYLE_TPL = createIdentity(ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL, ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL.ID);
        public static Identity<ConfigSysPointsConfTplRecord, Integer> IDENTITY_CONFIG_SYS_POINTS_CONF_TPL = createIdentity(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID);
        public static Identity<ConfigSysTemplateMessageColumnConfigRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG = createIdentity(ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG, ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG.ID);
        public static Identity<ConfigSysTemplateMessageLibraryRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY = createIdentity(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY, ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID);
        public static Identity<ConfigSysTemplateTypeRecord, Integer> IDENTITY_CONFIG_SYS_TEMPLATE_TYPE = createIdentity(ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE, ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE.ID);
        public static Identity<ConfigSysThemeRecord, Integer> IDENTITY_CONFIG_SYS_THEME = createIdentity(ConfigSysTheme.CONFIG_SYS_THEME, ConfigSysTheme.CONFIG_SYS_THEME.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ConfigAdminnotificationChannelRecord> KEY_CONFIG_ADMINNOTIFICATION_CHANNEL_PRIMARY = createUniqueKey(ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL, "KEY_config_adminnotification_channel_PRIMARY", ConfigAdminnotificationChannel.CONFIG_ADMINNOTIFICATION_CHANNEL.ID);
        public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY = createUniqueKey(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, "KEY_config_adminnotification_events_PRIMARY", ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ID);
        public static final UniqueKey<ConfigAdminnotificationEventsRecord> KEY_CONFIG_ADMINNOTIFICATION_EVENTS_EVENT_KEY = createUniqueKey(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, "KEY_config_adminnotification_events_event_key", ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_KEY);
        public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY = createUniqueKey(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP, "KEY_config_adminnotification_group_PRIMARY", ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.ID);
        public static final UniqueKey<ConfigAdminnotificationGroupRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUP_NAME = createUniqueKey(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP, "KEY_config_adminnotification_group_name", ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.NAME);
        public static final UniqueKey<ConfigAdminnotificationGroupmembersRecord> KEY_CONFIG_ADMINNOTIFICATION_GROUPMEMBERS_PRIMARY = createUniqueKey(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS, "KEY_config_adminnotification_groupmembers_PRIMARY", ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.ID);
        public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, "KEY_config_adminnotification_members_PRIMARY", ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.ID);
        public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_NAME = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, "KEY_config_adminnotification_members_name", ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.NAME);
        public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_MOBILEPHONE = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, "KEY_config_adminnotification_members_mobilephone", ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.MOBILEPHONE);
        public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_WECHATOPENID = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, "KEY_config_adminnotification_members_wechatopenid", ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.WECHATOPENID);
        public static final UniqueKey<ConfigAdminnotificationMembersRecord> KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_EMAIL = createUniqueKey(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, "KEY_config_adminnotification_members_email", ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.EMAIL);
        public static final UniqueKey<ConfigAtsSourceRecord> KEY_CONFIG_ATS_SOURCE_PRIMARY = createUniqueKey(ConfigAtsSource.CONFIG_ATS_SOURCE, "KEY_config_ats_source_PRIMARY", ConfigAtsSource.CONFIG_ATS_SOURCE.ID);
        public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PRIMARY = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, "KEY_config_cacheconfig_rediskey_PRIMARY", ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.ID);
        public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_KEY_IDENTIFIER = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, "KEY_config_cacheconfig_rediskey_key_identifier", ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.KEY_IDENTIFIER, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID);
        public static final UniqueKey<ConfigCacheconfigRediskeyRecord> KEY_CONFIG_CACHECONFIG_REDISKEY_PATTERN = createUniqueKey(ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY, "KEY_config_cacheconfig_rediskey_pattern", ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PATTERN, ConfigCacheconfigRediskey.CONFIG_CACHECONFIG_REDISKEY.PROJECT_APPID);
        public static final UniqueKey<ConfigCronjobsRecord> KEY_CONFIG_CRONJOBS_PRIMARY = createUniqueKey(ConfigCronjobs.CONFIG_CRONJOBS, "KEY_config_cronjobs_PRIMARY", ConfigCronjobs.CONFIG_CRONJOBS.ID);
        public static final UniqueKey<ConfigHbBalanceRecord> KEY_CONFIG_HB_BALANCE_PRIMARY = createUniqueKey(ConfigHbBalance.CONFIG_HB_BALANCE, "KEY_config_hb_balance_PRIMARY", ConfigHbBalance.CONFIG_HB_BALANCE.ID);
        public static final UniqueKey<ConfigOmsSwitchManagementRecord> KEY_CONFIG_OMS_SWITCH_MANAGEMENT_PRIMARY = createUniqueKey(ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT, "KEY_config_oms_switch_management_PRIMARY", ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.ID);
        public static final UniqueKey<ConfigOmsSwitchManagementRecord> KEY_CONFIG_OMS_SWITCH_MANAGEMENT_MODULE_NAME和COMPANY_ID = createUniqueKey(ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT, "KEY_config_oms_switch_management_module_name和company_id", ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME, ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID);
        public static final UniqueKey<ConfigPositionKenexaRecord> KEY_CONFIG_POSITION_KENEXA_PRIMARY = createUniqueKey(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, "KEY_config_position_kenexa_PRIMARY", ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID);
        public static final UniqueKey<ConfigSysAdministratorRecord> KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY = createUniqueKey(ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR, "KEY_config_sys_administrator_PRIMARY", ConfigSysAdministrator.CONFIG_SYS_ADMINISTRATOR.ID);
        public static final UniqueKey<ConfigSysAppTemplateRecord> KEY_CONFIG_SYS_APP_TEMPLATE_PRIMARY = createUniqueKey(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, "KEY_config_sys_app_template_PRIMARY", ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID);
        public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_PRIMARY = createUniqueKey(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, "KEY_config_sys_cv_tpl_PRIMARY", ConfigSysCvTpl.CONFIG_SYS_CV_TPL.ID);
        public static final UniqueKey<ConfigSysCvTplRecord> KEY_CONFIG_SYS_CV_TPL_FIELD_NAME_KEY = createUniqueKey(ConfigSysCvTpl.CONFIG_SYS_CV_TPL, "KEY_config_sys_cv_tpl_field_name_key", ConfigSysCvTpl.CONFIG_SYS_CV_TPL.FIELD_NAME);
        public static final UniqueKey<ConfigSysH5StyleTplRecord> KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY = createUniqueKey(ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL, "KEY_config_sys_h5_style_tpl_PRIMARY", ConfigSysH5StyleTpl.CONFIG_SYS_H5_STYLE_TPL.ID);
        public static final UniqueKey<ConfigSysPointsConfTplRecord> KEY_CONFIG_SYS_POINTS_CONF_TPL_PRIMARY = createUniqueKey(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, "KEY_config_sys_points_conf_tpl_PRIMARY", ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID);
        public static final UniqueKey<ConfigSysTemplateMessageColumnConfigRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG_PRIMARY = createUniqueKey(ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG, "KEY_config_sys_template_message_column_config_PRIMARY", ConfigSysTemplateMessageColumnConfig.CONFIG_SYS_TEMPLATE_MESSAGE_COLUMN_CONFIG.ID);
        public static final UniqueKey<ConfigSysTemplateMessageLibraryRecord> KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY = createUniqueKey(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY, "KEY_config_sys_template_message_library_PRIMARY", ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID);
        public static final UniqueKey<ConfigSysTemplateTypeRecord> KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY = createUniqueKey(ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE, "KEY_config_sys_template_type_PRIMARY", ConfigSysTemplateType.CONFIG_SYS_TEMPLATE_TYPE.ID);
        public static final UniqueKey<ConfigSysThemeRecord> KEY_CONFIG_SYS_THEME_PRIMARY = createUniqueKey(ConfigSysTheme.CONFIG_SYS_THEME, "KEY_config_sys_theme_PRIMARY", ConfigSysTheme.CONFIG_SYS_THEME.ID);
    }
}
