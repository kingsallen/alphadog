/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.configdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-01-17")
public class ConfigOmsSwitchManagementDO implements org.apache.thrift.TBase<ConfigOmsSwitchManagementDO, ConfigOmsSwitchManagementDO._Fields>, java.io.Serializable, Cloneable, Comparable<ConfigOmsSwitchManagementDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ConfigOmsSwitchManagementDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("companyId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField MODULE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleName", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField MODULE_PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleParam", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IS_VALID_FIELD_DESC = new org.apache.thrift.protocol.TField("isValid", org.apache.thrift.protocol.TType.BYTE, (short)5);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("updateTime", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ConfigOmsSwitchManagementDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ConfigOmsSwitchManagementDOTupleSchemeFactory();

  public int id; // optional
  public int companyId; // optional
  public int moduleName; // optional
  public String moduleParam; // optional
  public byte isValid; // optional
  public int version; // optional
  public String updateTime; // optional
  public String createTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    COMPANY_ID((short)2, "companyId"),
    MODULE_NAME((short)3, "moduleName"),
    MODULE_PARAM((short)4, "moduleParam"),
    IS_VALID((short)5, "isValid"),
    VERSION((short)6, "version"),
    UPDATE_TIME((short)7, "updateTime"),
    CREATE_TIME((short)8, "createTime");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // COMPANY_ID
          return COMPANY_ID;
        case 3: // MODULE_NAME
          return MODULE_NAME;
        case 4: // MODULE_PARAM
          return MODULE_PARAM;
        case 5: // IS_VALID
          return IS_VALID;
        case 6: // VERSION
          return VERSION;
        case 7: // UPDATE_TIME
          return UPDATE_TIME;
        case 8: // CREATE_TIME
          return CREATE_TIME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __COMPANYID_ISSET_ID = 1;
  private static final int __MODULENAME_ISSET_ID = 2;
  private static final int __ISVALID_ISSET_ID = 3;
  private static final int __VERSION_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.COMPANY_ID,_Fields.MODULE_NAME,_Fields.MODULE_PARAM,_Fields.IS_VALID,_Fields.VERSION,_Fields.UPDATE_TIME,_Fields.CREATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("companyId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MODULE_NAME, new org.apache.thrift.meta_data.FieldMetaData("moduleName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MODULE_PARAM, new org.apache.thrift.meta_data.FieldMetaData("moduleParam", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_VALID, new org.apache.thrift.meta_data.FieldMetaData("isValid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("updateTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ConfigOmsSwitchManagementDO.class, metaDataMap);
  }

  public ConfigOmsSwitchManagementDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ConfigOmsSwitchManagementDO(ConfigOmsSwitchManagementDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.companyId = other.companyId;
    this.moduleName = other.moduleName;
    if (other.isSetModuleParam()) {
      this.moduleParam = other.moduleParam;
    }
    this.isValid = other.isValid;
    this.version = other.version;
    if (other.isSetUpdateTime()) {
      this.updateTime = other.updateTime;
    }
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
  }

  public ConfigOmsSwitchManagementDO deepCopy() {
    return new ConfigOmsSwitchManagementDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setCompanyIdIsSet(false);
    this.companyId = 0;
    setModuleNameIsSet(false);
    this.moduleName = 0;
    this.moduleParam = null;
    setIsValidIsSet(false);
    this.isValid = 0;
    setVersionIsSet(false);
    this.version = 0;
    this.updateTime = null;
    this.createTime = null;
  }

  public int getId() {
    return this.id;
  }

  public ConfigOmsSwitchManagementDO setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getCompanyId() {
    return this.companyId;
  }

  public ConfigOmsSwitchManagementDO setCompanyId(int companyId) {
    this.companyId = companyId;
    setCompanyIdIsSet(true);
    return this;
  }

  public void unsetCompanyId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  /** Returns true if field companyId is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  public void setCompanyIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMPANYID_ISSET_ID, value);
  }

  public int getModuleName() {
    return this.moduleName;
  }

  public ConfigOmsSwitchManagementDO setModuleName(int moduleName) {
    this.moduleName = moduleName;
    setModuleNameIsSet(true);
    return this;
  }

  public void unsetModuleName() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MODULENAME_ISSET_ID);
  }

  /** Returns true if field moduleName is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleName() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MODULENAME_ISSET_ID);
  }

  public void setModuleNameIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MODULENAME_ISSET_ID, value);
  }

  public String getModuleParam() {
    return this.moduleParam;
  }

  public ConfigOmsSwitchManagementDO setModuleParam(String moduleParam) {
    this.moduleParam = moduleParam;
    return this;
  }

  public void unsetModuleParam() {
    this.moduleParam = null;
  }

  /** Returns true if field moduleParam is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleParam() {
    return this.moduleParam != null;
  }

  public void setModuleParamIsSet(boolean value) {
    if (!value) {
      this.moduleParam = null;
    }
  }

  public byte getIsValid() {
    return this.isValid;
  }

  public ConfigOmsSwitchManagementDO setIsValid(byte isValid) {
    this.isValid = isValid;
    setIsValidIsSet(true);
    return this;
  }

  public void unsetIsValid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ISVALID_ISSET_ID);
  }

  /** Returns true if field isValid is set (has been assigned a value) and false otherwise */
  public boolean isSetIsValid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ISVALID_ISSET_ID);
  }

  public void setIsValidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ISVALID_ISSET_ID, value);
  }

  public int getVersion() {
    return this.version;
  }

  public ConfigOmsSwitchManagementDO setVersion(int version) {
    this.version = version;
    setVersionIsSet(true);
    return this;
  }

  public void unsetVersion() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __VERSION_ISSET_ID);
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __VERSION_ISSET_ID);
  }

  public void setVersionIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __VERSION_ISSET_ID, value);
  }

  public String getUpdateTime() {
    return this.updateTime;
  }

  public ConfigOmsSwitchManagementDO setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public void unsetUpdateTime() {
    this.updateTime = null;
  }

  /** Returns true if field updateTime is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdateTime() {
    return this.updateTime != null;
  }

  public void setUpdateTimeIsSet(boolean value) {
    if (!value) {
      this.updateTime = null;
    }
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public ConfigOmsSwitchManagementDO setCreateTime(String createTime) {
    this.createTime = createTime;
    return this;
  }

  public void unsetCreateTime() {
    this.createTime = null;
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return this.createTime != null;
  }

  public void setCreateTimeIsSet(boolean value) {
    if (!value) {
      this.createTime = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompanyId();
      } else {
        setCompanyId((Integer)value);
      }
      break;

    case MODULE_NAME:
      if (value == null) {
        unsetModuleName();
      } else {
        setModuleName((Integer)value);
      }
      break;

    case MODULE_PARAM:
      if (value == null) {
        unsetModuleParam();
      } else {
        setModuleParam((String)value);
      }
      break;

    case IS_VALID:
      if (value == null) {
        unsetIsValid();
      } else {
        setIsValid((Byte)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((Integer)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdateTime();
      } else {
        setUpdateTime((String)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case COMPANY_ID:
      return getCompanyId();

    case MODULE_NAME:
      return getModuleName();

    case MODULE_PARAM:
      return getModuleParam();

    case IS_VALID:
      return getIsValid();

    case VERSION:
      return getVersion();

    case UPDATE_TIME:
      return getUpdateTime();

    case CREATE_TIME:
      return getCreateTime();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case COMPANY_ID:
      return isSetCompanyId();
    case MODULE_NAME:
      return isSetModuleName();
    case MODULE_PARAM:
      return isSetModuleParam();
    case IS_VALID:
      return isSetIsValid();
    case VERSION:
      return isSetVersion();
    case UPDATE_TIME:
      return isSetUpdateTime();
    case CREATE_TIME:
      return isSetCreateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ConfigOmsSwitchManagementDO)
      return this.equals((ConfigOmsSwitchManagementDO)that);
    return false;
  }

  public boolean equals(ConfigOmsSwitchManagementDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_companyId = true && this.isSetCompanyId();
    boolean that_present_companyId = true && that.isSetCompanyId();
    if (this_present_companyId || that_present_companyId) {
      if (!(this_present_companyId && that_present_companyId))
        return false;
      if (this.companyId != that.companyId)
        return false;
    }

    boolean this_present_moduleName = true && this.isSetModuleName();
    boolean that_present_moduleName = true && that.isSetModuleName();
    if (this_present_moduleName || that_present_moduleName) {
      if (!(this_present_moduleName && that_present_moduleName))
        return false;
      if (this.moduleName != that.moduleName)
        return false;
    }

    boolean this_present_moduleParam = true && this.isSetModuleParam();
    boolean that_present_moduleParam = true && that.isSetModuleParam();
    if (this_present_moduleParam || that_present_moduleParam) {
      if (!(this_present_moduleParam && that_present_moduleParam))
        return false;
      if (!this.moduleParam.equals(that.moduleParam))
        return false;
    }

    boolean this_present_isValid = true && this.isSetIsValid();
    boolean that_present_isValid = true && that.isSetIsValid();
    if (this_present_isValid || that_present_isValid) {
      if (!(this_present_isValid && that_present_isValid))
        return false;
      if (this.isValid != that.isValid)
        return false;
    }

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (this.version != that.version)
        return false;
    }

    boolean this_present_updateTime = true && this.isSetUpdateTime();
    boolean that_present_updateTime = true && that.isSetUpdateTime();
    if (this_present_updateTime || that_present_updateTime) {
      if (!(this_present_updateTime && that_present_updateTime))
        return false;
      if (!this.updateTime.equals(that.updateTime))
        return false;
    }

    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (!this.createTime.equals(that.createTime))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetId()) ? 131071 : 524287);
    if (isSetId())
      hashCode = hashCode * 8191 + id;

    hashCode = hashCode * 8191 + ((isSetCompanyId()) ? 131071 : 524287);
    if (isSetCompanyId())
      hashCode = hashCode * 8191 + companyId;

    hashCode = hashCode * 8191 + ((isSetModuleName()) ? 131071 : 524287);
    if (isSetModuleName())
      hashCode = hashCode * 8191 + moduleName;

    hashCode = hashCode * 8191 + ((isSetModuleParam()) ? 131071 : 524287);
    if (isSetModuleParam())
      hashCode = hashCode * 8191 + moduleParam.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsValid()) ? 131071 : 524287);
    if (isSetIsValid())
      hashCode = hashCode * 8191 + (int) (isValid);

    hashCode = hashCode * 8191 + ((isSetVersion()) ? 131071 : 524287);
    if (isSetVersion())
      hashCode = hashCode * 8191 + version;

    hashCode = hashCode * 8191 + ((isSetUpdateTime()) ? 131071 : 524287);
    if (isSetUpdateTime())
      hashCode = hashCode * 8191 + updateTime.hashCode();

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ConfigOmsSwitchManagementDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompanyId()).compareTo(other.isSetCompanyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyId, other.companyId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetModuleName()).compareTo(other.isSetModuleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleName, other.moduleName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetModuleParam()).compareTo(other.isSetModuleParam());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleParam()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleParam, other.moduleParam);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsValid()).compareTo(other.isSetIsValid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsValid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isValid, other.isValid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdateTime()).compareTo(other.isSetUpdateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateTime, other.updateTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ConfigOmsSwitchManagementDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetCompanyId()) {
      if (!first) sb.append(", ");
      sb.append("companyId:");
      sb.append(this.companyId);
      first = false;
    }
    if (isSetModuleName()) {
      if (!first) sb.append(", ");
      sb.append("moduleName:");
      sb.append(this.moduleName);
      first = false;
    }
    if (isSetModuleParam()) {
      if (!first) sb.append(", ");
      sb.append("moduleParam:");
      if (this.moduleParam == null) {
        sb.append("null");
      } else {
        sb.append(this.moduleParam);
      }
      first = false;
    }
    if (isSetIsValid()) {
      if (!first) sb.append(", ");
      sb.append("isValid:");
      sb.append(this.isValid);
      first = false;
    }
    if (isSetVersion()) {
      if (!first) sb.append(", ");
      sb.append("version:");
      sb.append(this.version);
      first = false;
    }
    if (isSetUpdateTime()) {
      if (!first) sb.append(", ");
      sb.append("updateTime:");
      if (this.updateTime == null) {
        sb.append("null");
      } else {
        sb.append(this.updateTime);
      }
      first = false;
    }
    if (isSetCreateTime()) {
      if (!first) sb.append(", ");
      sb.append("createTime:");
      if (this.createTime == null) {
        sb.append("null");
      } else {
        sb.append(this.createTime);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ConfigOmsSwitchManagementDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConfigOmsSwitchManagementDOStandardScheme getScheme() {
      return new ConfigOmsSwitchManagementDOStandardScheme();
    }
  }

  private static class ConfigOmsSwitchManagementDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<ConfigOmsSwitchManagementDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ConfigOmsSwitchManagementDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.companyId = iprot.readI32();
              struct.setCompanyIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MODULE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.moduleName = iprot.readI32();
              struct.setModuleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MODULE_PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moduleParam = iprot.readString();
              struct.setModuleParamIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_VALID
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.isValid = iprot.readByte();
              struct.setIsValidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.version = iprot.readI32();
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.updateTime = iprot.readString();
              struct.setUpdateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createTime = iprot.readString();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ConfigOmsSwitchManagementDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCompanyId()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.companyId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetModuleName()) {
        oprot.writeFieldBegin(MODULE_NAME_FIELD_DESC);
        oprot.writeI32(struct.moduleName);
        oprot.writeFieldEnd();
      }
      if (struct.moduleParam != null) {
        if (struct.isSetModuleParam()) {
          oprot.writeFieldBegin(MODULE_PARAM_FIELD_DESC);
          oprot.writeString(struct.moduleParam);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsValid()) {
        oprot.writeFieldBegin(IS_VALID_FIELD_DESC);
        oprot.writeByte(struct.isValid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetVersion()) {
        oprot.writeFieldBegin(VERSION_FIELD_DESC);
        oprot.writeI32(struct.version);
        oprot.writeFieldEnd();
      }
      if (struct.updateTime != null) {
        if (struct.isSetUpdateTime()) {
          oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
          oprot.writeString(struct.updateTime);
          oprot.writeFieldEnd();
        }
      }
      if (struct.createTime != null) {
        if (struct.isSetCreateTime()) {
          oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
          oprot.writeString(struct.createTime);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ConfigOmsSwitchManagementDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConfigOmsSwitchManagementDOTupleScheme getScheme() {
      return new ConfigOmsSwitchManagementDOTupleScheme();
    }
  }

  private static class ConfigOmsSwitchManagementDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<ConfigOmsSwitchManagementDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ConfigOmsSwitchManagementDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCompanyId()) {
        optionals.set(1);
      }
      if (struct.isSetModuleName()) {
        optionals.set(2);
      }
      if (struct.isSetModuleParam()) {
        optionals.set(3);
      }
      if (struct.isSetIsValid()) {
        optionals.set(4);
      }
      if (struct.isSetVersion()) {
        optionals.set(5);
      }
      if (struct.isSetUpdateTime()) {
        optionals.set(6);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetCompanyId()) {
        oprot.writeI32(struct.companyId);
      }
      if (struct.isSetModuleName()) {
        oprot.writeI32(struct.moduleName);
      }
      if (struct.isSetModuleParam()) {
        oprot.writeString(struct.moduleParam);
      }
      if (struct.isSetIsValid()) {
        oprot.writeByte(struct.isValid);
      }
      if (struct.isSetVersion()) {
        oprot.writeI32(struct.version);
      }
      if (struct.isSetUpdateTime()) {
        oprot.writeString(struct.updateTime);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ConfigOmsSwitchManagementDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.companyId = iprot.readI32();
        struct.setCompanyIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.moduleName = iprot.readI32();
        struct.setModuleNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.moduleParam = iprot.readString();
        struct.setModuleParamIsSet(true);
      }
      if (incoming.get(4)) {
        struct.isValid = iprot.readByte();
        struct.setIsValidIsSet(true);
      }
      if (incoming.get(5)) {
        struct.version = iprot.readI32();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(6)) {
        struct.updateTime = iprot.readString();
        struct.setUpdateTimeIsSet(true);
      }
      if (incoming.get(7)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

