/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables.records;


import com.moseeker.db.configdb.tables.ConfigSysAppTemplate;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 申请字段模板表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysAppTemplateRecord extends UpdatableRecordImpl<ConfigSysAppTemplateRecord> implements Record10<Integer, String, String, Byte, Byte, Byte, Byte, String, String, Integer> {

	private static final long serialVersionUID = -829965450;

	/**
	 * Setter for <code>configDB.config_sys_app_template.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.enname</code>. 申请字段英文名称
	 */
	public void setEnname(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.enname</code>. 申请字段英文名称
	 */
	public String getEnname() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.chname</code>. 申请字段中文名称
	 */
	public void setChname(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.chname</code>. 申请字段中文名称
	 */
	public String getChname() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.priority</code>. 排序
	 */
	public void setPriority(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.priority</code>. 排序
	 */
	public Byte getPriority() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.display</code>. 是否显示0：是，1：否
	 */
	public void setDisplay(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.display</code>. 是否显示0：是，1：否
	 */
	public Byte getDisplay() {
		return (Byte) getValue(4);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.required</code>. 是否必填0：是，1：否
	 */
	public void setRequired(Byte value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.required</code>. 是否必填0：是，1：否
	 */
	public Byte getRequired() {
		return (Byte) getValue(5);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.type</code>. 模板类型
	 */
	public void setType(Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.type</code>. 模板类型
	 */
	public Byte getType() {
		return (Byte) getValue(6);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.remark</code>. 备注
	 */
	public void setRemark(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.remark</code>. 备注
	 */
	public String getRemark() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.entitle</code>. 字段英文名称
	 */
	public void setEntitle(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.entitle</code>. 字段英文名称
	 */
	public String getEntitle() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>configDB.config_sys_app_template.parent_id</code>. 字段父子关系
	 */
	public void setParentId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>configDB.config_sys_app_template.parent_id</code>. 字段父子关系
	 */
	public Integer getParentId() {
		return (Integer) getValue(9);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, String, String, Byte, Byte, Byte, Byte, String, String, Integer> fieldsRow() {
		return (Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, String, String, Byte, Byte, Byte, Byte, String, String, Integer> valuesRow() {
		return (Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ENNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.CHNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field4() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.PRIORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.DISPLAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field6() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.REQUIRED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field7() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.REMARK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ENTITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.PARENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getEnname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getChname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value4() {
		return getPriority();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value5() {
		return getDisplay();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value6() {
		return getRequired();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value7() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getEntitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getParentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value2(String value) {
		setEnname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value3(String value) {
		setChname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value4(Byte value) {
		setPriority(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value5(Byte value) {
		setDisplay(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value6(Byte value) {
		setRequired(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value7(Byte value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value8(String value) {
		setRemark(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value9(String value) {
		setEntitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord value10(Integer value) {
		setParentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAppTemplateRecord values(Integer value1, String value2, String value3, Byte value4, Byte value5, Byte value6, Byte value7, String value8, String value9, Integer value10) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ConfigSysAppTemplateRecord
	 */
	public ConfigSysAppTemplateRecord() {
		super(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE);
	}

	/**
	 * Create a detached, initialised ConfigSysAppTemplateRecord
	 */
	public ConfigSysAppTemplateRecord(Integer id, String enname, String chname, Byte priority, Byte display, Byte required, Byte type, String remark, String entitle, Integer parentId) {
		super(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE);

		setValue(0, id);
		setValue(1, enname);
		setValue(2, chname);
		setValue(3, priority);
		setValue(4, display);
		setValue(5, required);
		setValue(6, type);
		setValue(7, remark);
		setValue(8, entitle);
		setValue(9, parentId);
	}
}
