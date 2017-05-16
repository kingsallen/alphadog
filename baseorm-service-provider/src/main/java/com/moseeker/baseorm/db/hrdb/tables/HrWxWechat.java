/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;

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


/**
 * 微信公众号表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechat extends TableImpl<HrWxWechatRecord> {

	private static final long serialVersionUID = 1837217236;

	/**
	 * The reference instance of <code>hrdb.hr_wx_wechat</code>
	 */
	public static final HrWxWechat HR_WX_WECHAT = new HrWxWechat();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrWxWechatRecord> getRecordType() {
		return HrWxWechatRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_wx_wechat.id</code>.
	 */
	public final TableField<HrWxWechatRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_wx_wechat.company_id</code>. 所属公司id, company.id
	 */
	public final TableField<HrWxWechatRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "所属公司id, company.id");

	/**
	 * The column <code>hrdb.hr_wx_wechat.type</code>. 公众号类型, 0:订阅号, 1:服务号
	 */
	public final TableField<HrWxWechatRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "公众号类型, 0:订阅号, 1:服务号");

	/**
	 * The column <code>hrdb.hr_wx_wechat.signature</code>. 公众号ID匿名化
	 */
	public final TableField<HrWxWechatRecord, String> SIGNATURE = createField("signature", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "公众号ID匿名化");

	/**
	 * The column <code>hrdb.hr_wx_wechat.name</code>. 名称
	 */
	public final TableField<HrWxWechatRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaulted(true), this, "名称");

	/**
	 * The column <code>hrdb.hr_wx_wechat.alias</code>. 别名
	 */
	public final TableField<HrWxWechatRecord, String> ALIAS = createField("alias", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "别名");

	/**
	 * The column <code>hrdb.hr_wx_wechat.username</code>. 用户名
	 */
	public final TableField<HrWxWechatRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "用户名");

	/**
	 * The column <code>hrdb.hr_wx_wechat.password</code>. 密码
	 */
	public final TableField<HrWxWechatRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), this, "密码");

	/**
	 * The column <code>hrdb.hr_wx_wechat.token</code>. 开发者token
	 */
	public final TableField<HrWxWechatRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), this, "开发者token");

	/**
	 * The column <code>hrdb.hr_wx_wechat.appid</code>. 开发者appid
	 */
	public final TableField<HrWxWechatRecord, String> APPID = createField("appid", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "开发者appid");

	/**
	 * The column <code>hrdb.hr_wx_wechat.secret</code>. 开发者secret
	 */
	public final TableField<HrWxWechatRecord, String> SECRET = createField("secret", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "开发者secret");

	/**
	 * The column <code>hrdb.hr_wx_wechat.welcome</code>. welcome message
	 */
	public final TableField<HrWxWechatRecord, Integer> WELCOME = createField("welcome", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "welcome message");

	/**
	 * The column <code>hrdb.hr_wx_wechat.default</code>. default message
	 */
	public final TableField<HrWxWechatRecord, Integer> DEFAULT = createField("default", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "default message");

	/**
	 * The column <code>hrdb.hr_wx_wechat.qrcode</code>. 关注公众号的二维码
	 */
	public final TableField<HrWxWechatRecord, String> QRCODE = createField("qrcode", org.jooq.impl.SQLDataType.VARCHAR.length(300).nullable(false).defaulted(true), this, "关注公众号的二维码");

	/**
	 * The column <code>hrdb.hr_wx_wechat.passive_seeker</code>. 被动求职者开关，0= 开启, 1=不开启
	 */
	public final TableField<HrWxWechatRecord, Byte> PASSIVE_SEEKER = createField("passive_seeker", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "被动求职者开关，0= 开启, 1=不开启");

	/**
	 * The column <code>hrdb.hr_wx_wechat.third_oauth</code>. 授权大岂第三方平台0：未授权 1：授权了
	 */
	public final TableField<HrWxWechatRecord, Byte> THIRD_OAUTH = createField("third_oauth", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "授权大岂第三方平台0：未授权 1：授权了");

	/**
	 * The column <code>hrdb.hr_wx_wechat.hr_register</code>. 是否启用免费雇主注册，0：不启用，1：启用
	 */
	public final TableField<HrWxWechatRecord, Byte> HR_REGISTER = createField("hr_register", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否启用免费雇主注册，0：不启用，1：启用");

	/**
	 * The column <code>hrdb.hr_wx_wechat.access_token_create_time</code>. access_token最新更新时间
	 */
	public final TableField<HrWxWechatRecord, Integer> ACCESS_TOKEN_CREATE_TIME = createField("access_token_create_time", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "access_token最新更新时间");

	/**
	 * The column <code>hrdb.hr_wx_wechat.access_token_expired</code>. access_token过期时间
	 */
	public final TableField<HrWxWechatRecord, Integer> ACCESS_TOKEN_EXPIRED = createField("access_token_expired", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "access_token过期时间");

	/**
	 * The column <code>hrdb.hr_wx_wechat.access_token</code>. access_token
	 */
	public final TableField<HrWxWechatRecord, String> ACCESS_TOKEN = createField("access_token", org.jooq.impl.SQLDataType.VARCHAR.length(600).nullable(false).defaulted(true), this, "access_token");

	/**
	 * The column <code>hrdb.hr_wx_wechat.jsapi_ticket</code>. jsapi_ticket
	 */
	public final TableField<HrWxWechatRecord, String> JSAPI_TICKET = createField("jsapi_ticket", org.jooq.impl.SQLDataType.VARCHAR.length(600).nullable(false).defaulted(true), this, "jsapi_ticket");

	/**
	 * The column <code>hrdb.hr_wx_wechat.authorized</code>. 是否授权0：无关，1：授权2：解除授权
	 */
	public final TableField<HrWxWechatRecord, Byte> AUTHORIZED = createField("authorized", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否授权0：无关，1：授权2：解除授权");

	/**
	 * The column <code>hrdb.hr_wx_wechat.unauthorized_time</code>. 解除授权的时间戳
	 */
	public final TableField<HrWxWechatRecord, Integer> UNAUTHORIZED_TIME = createField("unauthorized_time", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "解除授权的时间戳");

	/**
	 * The column <code>hrdb.hr_wx_wechat.authorizer_refresh_token</code>. 第三方授权的刷新token，用来刷access_token
	 */
	public final TableField<HrWxWechatRecord, String> AUTHORIZER_REFRESH_TOKEN = createField("authorizer_refresh_token", org.jooq.impl.SQLDataType.VARCHAR.length(58).nullable(false).defaulted(true), this, "第三方授权的刷新token，用来刷access_token");

	/**
	 * The column <code>hrdb.hr_wx_wechat.create_time</code>. 创建时间
	 */
	public final TableField<HrWxWechatRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>hrdb.hr_wx_wechat.update_time</code>. 修改时间
	 */
	public final TableField<HrWxWechatRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "修改时间");

	/**
	 * The column <code>hrdb.hr_wx_wechat.hr_chat</code>. IM聊天开关，0：不开启，1：开启
	 */
	public final TableField<HrWxWechatRecord, Byte> HR_CHAT = createField("hr_chat", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "IM聊天开关，0：不开启，1：开启");

	/**
	 * The column <code>hrdb.hr_wx_wechat.show_qx_qrcode</code>. 显示仟寻聚合号二维码, 0:不允许，1:允许
	 */
	public final TableField<HrWxWechatRecord, Integer> SHOW_QX_QRCODE = createField("show_qx_qrcode", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "显示仟寻聚合号二维码, 0:不允许，1:允许");

	/**
	 * The column <code>hrdb.hr_wx_wechat.show_custom_theme</code>. show_custom_theme, 用于表示是否可以开启企业自定义颜色配置 0是否 1是开启
	 */
	public final TableField<HrWxWechatRecord, Integer> SHOW_CUSTOM_THEME = createField("show_custom_theme", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "show_custom_theme, 用于表示是否可以开启企业自定义颜色配置 0是否 1是开启");

	/**
	 * Create a <code>hrdb.hr_wx_wechat</code> table reference
	 */
	public HrWxWechat() {
		this("hr_wx_wechat", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_wx_wechat</code> table reference
	 */
	public HrWxWechat(String alias) {
		this(alias, HR_WX_WECHAT);
	}

	private HrWxWechat(String alias, Table<HrWxWechatRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrWxWechat(String alias, Table<HrWxWechatRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "微信公众号表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrWxWechatRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_WX_WECHAT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrWxWechatRecord> getPrimaryKey() {
		return Keys.KEY_HR_WX_WECHAT_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrWxWechatRecord>> getKeys() {
		return Arrays.<UniqueKey<HrWxWechatRecord>>asList(Keys.KEY_HR_WX_WECHAT_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxWechat as(String alias) {
		return new HrWxWechat(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrWxWechat rename(String name) {
		return new HrWxWechat(name, null);
	}
}
