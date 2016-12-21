/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrWxModule;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record18;
import org.jooq.Row18;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;


/**
 * 微信模块表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxModuleRecord extends UpdatableRecordImpl<HrWxModuleRecord> implements Record18<UByte, String, String, String, String, String, String, String, Byte, String, String, Byte, Byte, UByte, String, Byte, String, String> {

    private static final long serialVersionUID = -1497007352;

    /**
     * Setter for <code>hrdb.hr_wx_module.id</code>.
     */
    public void setId(UByte value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.id</code>.
     */
    public UByte getId() {
        return (UByte) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.name</code>. 标识
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.name</code>. 标识
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.title</code>. 名称
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.title</code>. 名称
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.version</code>. 版本
     */
    public void setVersion(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.version</code>. 版本
     */
    public String getVersion() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.ability</code>. 功能描述
     */
    public void setAbility(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.ability</code>. 功能描述
     */
    public String getAbility() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.description</code>. 介绍
     */
    public void setDescription(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.description</code>. 介绍
     */
    public String getDescription() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.author</code>. 作者
     */
    public void setAuthor(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.author</code>. 作者
     */
    public String getAuthor() {
        return (String) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.url</code>. 发布页面
     */
    public void setUrl(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.url</code>. 发布页面
     */
    public String getUrl() {
        return (String) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.settings</code>. 扩展设置项
     */
    public void setSettings(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.settings</code>. 扩展设置项
     */
    public Byte getSettings() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.subscribes</code>. 订阅的消息类型
     */
    public void setSubscribes(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.subscribes</code>. 订阅的消息类型
     */
    public String getSubscribes() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.handles</code>. 能够直接处理的消息类型
     */
    public void setHandles(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.handles</code>. 能够直接处理的消息类型
     */
    public String getHandles() {
        return (String) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.isrulefields</code>. 是否有规则嵌入项
     */
    public void setIsrulefields(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.isrulefields</code>. 是否有规则嵌入项
     */
    public Byte getIsrulefields() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.home</code>. 是否微站首页嵌入
     */
    public void setHome(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.home</code>. 是否微站首页嵌入
     */
    public Byte getHome() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.issystem</code>. 是否是系统模块
     */
    public void setIssystem(UByte value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.issystem</code>. 是否是系统模块
     */
    public UByte getIssystem() {
        return (UByte) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.options</code>. 扩展功能导航项
     */
    public void setOptions(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.options</code>. 扩展功能导航项
     */
    public String getOptions() {
        return (String) get(14);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.profile</code>. 是否个人中心嵌入
     */
    public void setProfile(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.profile</code>. 是否个人中心嵌入
     */
    public Byte getProfile() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.site_menus</code>. 微站功能扩展导航项
     */
    public void setSiteMenus(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.site_menus</code>. 微站功能扩展导航项
     */
    public String getSiteMenus() {
        return (String) get(16);
    }

    /**
     * Setter for <code>hrdb.hr_wx_module.platform_menus</code>. 微站功能扩展导航项
     */
    public void setPlatformMenus(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_module.platform_menus</code>. 微站功能扩展导航项
     */
    public String getPlatformMenus() {
        return (String) get(17);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UByte> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record18 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<UByte, String, String, String, String, String, String, String, Byte, String, String, Byte, Byte, UByte, String, Byte, String, String> fieldsRow() {
        return (Row18) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<UByte, String, String, String, String, String, String, String, Byte, String, String, Byte, Byte, UByte, String, Byte, String, String> valuesRow() {
        return (Row18) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field1() {
        return HrWxModule.HR_WX_MODULE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrWxModule.HR_WX_MODULE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrWxModule.HR_WX_MODULE.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrWxModule.HR_WX_MODULE.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrWxModule.HR_WX_MODULE.ABILITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrWxModule.HR_WX_MODULE.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return HrWxModule.HR_WX_MODULE.AUTHOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return HrWxModule.HR_WX_MODULE.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return HrWxModule.HR_WX_MODULE.SETTINGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HrWxModule.HR_WX_MODULE.SUBSCRIBES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HrWxModule.HR_WX_MODULE.HANDLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return HrWxModule.HR_WX_MODULE.ISRULEFIELDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return HrWxModule.HR_WX_MODULE.HOME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field14() {
        return HrWxModule.HR_WX_MODULE.ISSYSTEM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return HrWxModule.HR_WX_MODULE.OPTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field16() {
        return HrWxModule.HR_WX_MODULE.PROFILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return HrWxModule.HR_WX_MODULE.SITE_MENUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return HrWxModule.HR_WX_MODULE.PLATFORM_MENUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAbility();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAuthor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getSubscribes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getHandles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getIsrulefields();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getHome();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte value14() {
        return getIssystem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getOptions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value16() {
        return getProfile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getSiteMenus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getPlatformMenus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value1(UByte value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value3(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value4(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value5(String value) {
        setAbility(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value6(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value7(String value) {
        setAuthor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value8(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value9(Byte value) {
        setSettings(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value10(String value) {
        setSubscribes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value11(String value) {
        setHandles(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value12(Byte value) {
        setIsrulefields(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value13(Byte value) {
        setHome(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value14(UByte value) {
        setIssystem(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value15(String value) {
        setOptions(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value16(Byte value) {
        setProfile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value17(String value) {
        setSiteMenus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord value18(String value) {
        setPlatformMenus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxModuleRecord values(UByte value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, Byte value9, String value10, String value11, Byte value12, Byte value13, UByte value14, String value15, Byte value16, String value17, String value18) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrWxModuleRecord
     */
    public HrWxModuleRecord() {
        super(HrWxModule.HR_WX_MODULE);
    }

    /**
     * Create a detached, initialised HrWxModuleRecord
     */
    public HrWxModuleRecord(UByte id, String name, String title, String version, String ability, String description, String author, String url, Byte settings, String subscribes, String handles, Byte isrulefields, Byte home, UByte issystem, String options, Byte profile, String siteMenus, String platformMenus) {
        super(HrWxModule.HR_WX_MODULE);

        set(0, id);
        set(1, name);
        set(2, title);
        set(3, version);
        set(4, ability);
        set(5, description);
        set(6, author);
        set(7, url);
        set(8, settings);
        set(9, subscribes);
        set(10, handles);
        set(11, isrulefields);
        set(12, home);
        set(13, issystem);
        set(14, options);
        set(15, profile);
        set(16, siteMenus);
        set(17, platformMenus);
    }
}