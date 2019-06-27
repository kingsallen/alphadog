/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables;


import com.moseeker.baseorm.db.referraldb.Keys;
import com.moseeker.baseorm.db.referraldb.Referraldb;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralUploadFilesRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 用户上传文件记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralUploadFiles extends TableImpl<ReferralUploadFilesRecord> {

    private static final long serialVersionUID = -345396381;

    /**
     * The reference instance of <code>referraldb.referral_upload_files</code>
     */
    public static final ReferralUploadFiles REFERRAL_UPLOAD_FILES = new ReferralUploadFiles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReferralUploadFilesRecord> getRecordType() {
        return ReferralUploadFilesRecord.class;
    }

    /**
     * The column <code>referraldb.referral_upload_files.id</code>. 主key
     */
    public final TableField<ReferralUploadFilesRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>referraldb.referral_upload_files.fileId</code>. 前台传入文件id
     */
    public final TableField<ReferralUploadFilesRecord, String> FILEID = createField("fileId", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "前台传入文件id");

    /**
     * The column <code>referraldb.referral_upload_files.uniname</code>. 后台生成文件名称
     */
    public final TableField<ReferralUploadFilesRecord, String> UNINAME = createField("uniname", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "后台生成文件名称");

    /**
     * The column <code>referraldb.referral_upload_files.unionid</code>. 上传文件人id(可以是unionid)
     */
    public final TableField<ReferralUploadFilesRecord, String> UNIONID = createField("unionid", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "上传文件人id(可以是unionid)");

    /**
     * The column <code>referraldb.referral_upload_files.type</code>. 上传渠道：1 微信小程序 2电   扫码上传
     */
    public final TableField<ReferralUploadFilesRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER, this, "上传渠道：1 微信小程序 2电   扫码上传");

    /**
     * The column <code>referraldb.referral_upload_files.filename</code>. 文件名
     */
    public final TableField<ReferralUploadFilesRecord, String> FILENAME = createField("filename", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "文件名");

    /**
     * The column <code>referraldb.referral_upload_files.url</code>. 文件保存地址
     */
    public final TableField<ReferralUploadFilesRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "文件保存地址");

    /**
     * The column <code>referraldb.referral_upload_files.create_time</code>. 创建时间
     */
    public final TableField<ReferralUploadFilesRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>referraldb.referral_upload_files.update_time</code>. 更新时间
     */
    public final TableField<ReferralUploadFilesRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>referraldb.referral_upload_files.status</code>. 状态：0正常，1删除
     */
    public final TableField<ReferralUploadFilesRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT, this, "状态：0正常，1删除");

    /**
     * Create a <code>referraldb.referral_upload_files</code> table reference
     */
    public ReferralUploadFiles() {
        this("referral_upload_files", null);
    }

    /**
     * Create an aliased <code>referraldb.referral_upload_files</code> table reference
     */
    public ReferralUploadFiles(String alias) {
        this(alias, REFERRAL_UPLOAD_FILES);
    }

    private ReferralUploadFiles(String alias, Table<ReferralUploadFilesRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReferralUploadFiles(String alias, Table<ReferralUploadFilesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户上传文件记录表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Referraldb.REFERRALDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ReferralUploadFilesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REFERRAL_UPLOAD_FILES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ReferralUploadFilesRecord> getPrimaryKey() {
        return Keys.KEY_REFERRAL_UPLOAD_FILES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ReferralUploadFilesRecord>> getKeys() {
        return Arrays.<UniqueKey<ReferralUploadFilesRecord>>asList(Keys.KEY_REFERRAL_UPLOAD_FILES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralUploadFiles as(String alias) {
        return new ReferralUploadFiles(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferralUploadFiles rename(String name) {
        return new ReferralUploadFiles(name, null);
    }
}
