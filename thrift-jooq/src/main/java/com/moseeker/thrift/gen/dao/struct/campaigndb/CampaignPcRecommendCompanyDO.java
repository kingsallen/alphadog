/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.campaigndb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-06-28")
public class CampaignPcRecommendCompanyDO implements org.apache.thrift.TBase<CampaignPcRecommendCompanyDO, CampaignPcRecommendCompanyDO._Fields>, java.io.Serializable, Cloneable, Comparable<CampaignPcRecommendCompanyDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CampaignPcRecommendCompanyDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MODULE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MODULE_DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("moduleDescription", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField COMPANY_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("companyIds", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField DISABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("disable", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("updateTime", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CampaignPcRecommendCompanyDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CampaignPcRecommendCompanyDOTupleSchemeFactory();

  public int id; // optional
  public java.lang.String moduleName; // optional
  public java.lang.String moduleDescription; // optional
  public java.lang.String companyIds; // optional
  public int disable; // optional
  public java.lang.String createTime; // optional
  public java.lang.String updateTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    MODULE_NAME((short)2, "moduleName"),
    MODULE_DESCRIPTION((short)3, "moduleDescription"),
    COMPANY_IDS((short)4, "companyIds"),
    DISABLE((short)5, "disable"),
    CREATE_TIME((short)6, "createTime"),
    UPDATE_TIME((short)7, "updateTime");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

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
        case 2: // MODULE_NAME
          return MODULE_NAME;
        case 3: // MODULE_DESCRIPTION
          return MODULE_DESCRIPTION;
        case 4: // COMPANY_IDS
          return COMPANY_IDS;
        case 5: // DISABLE
          return DISABLE;
        case 6: // CREATE_TIME
          return CREATE_TIME;
        case 7: // UPDATE_TIME
          return UPDATE_TIME;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __DISABLE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.MODULE_NAME,_Fields.MODULE_DESCRIPTION,_Fields.COMPANY_IDS,_Fields.DISABLE,_Fields.CREATE_TIME,_Fields.UPDATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MODULE_NAME, new org.apache.thrift.meta_data.FieldMetaData("moduleName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MODULE_DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("moduleDescription", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPANY_IDS, new org.apache.thrift.meta_data.FieldMetaData("companyIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DISABLE, new org.apache.thrift.meta_data.FieldMetaData("disable", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("updateTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CampaignPcRecommendCompanyDO.class, metaDataMap);
  }

  public CampaignPcRecommendCompanyDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CampaignPcRecommendCompanyDO(CampaignPcRecommendCompanyDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetModuleName()) {
      this.moduleName = other.moduleName;
    }
    if (other.isSetModuleDescription()) {
      this.moduleDescription = other.moduleDescription;
    }
    if (other.isSetCompanyIds()) {
      this.companyIds = other.companyIds;
    }
    this.disable = other.disable;
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
    if (other.isSetUpdateTime()) {
      this.updateTime = other.updateTime;
    }
  }

  public CampaignPcRecommendCompanyDO deepCopy() {
    return new CampaignPcRecommendCompanyDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.moduleName = null;
    this.moduleDescription = null;
    this.companyIds = null;
    setDisableIsSet(false);
    this.disable = 0;
    this.createTime = null;
    this.updateTime = null;
  }

  public int getId() {
    return this.id;
  }

  public CampaignPcRecommendCompanyDO setId(int id) {
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

  public java.lang.String getModuleName() {
    return this.moduleName;
  }

  public CampaignPcRecommendCompanyDO setModuleName(java.lang.String moduleName) {
    this.moduleName = moduleName;
    return this;
  }

  public void unsetModuleName() {
    this.moduleName = null;
  }

  /** Returns true if field moduleName is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleName() {
    return this.moduleName != null;
  }

  public void setModuleNameIsSet(boolean value) {
    if (!value) {
      this.moduleName = null;
    }
  }

  public java.lang.String getModuleDescription() {
    return this.moduleDescription;
  }

  public CampaignPcRecommendCompanyDO setModuleDescription(java.lang.String moduleDescription) {
    this.moduleDescription = moduleDescription;
    return this;
  }

  public void unsetModuleDescription() {
    this.moduleDescription = null;
  }

  /** Returns true if field moduleDescription is set (has been assigned a value) and false otherwise */
  public boolean isSetModuleDescription() {
    return this.moduleDescription != null;
  }

  public void setModuleDescriptionIsSet(boolean value) {
    if (!value) {
      this.moduleDescription = null;
    }
  }

  public java.lang.String getCompanyIds() {
    return this.companyIds;
  }

  public CampaignPcRecommendCompanyDO setCompanyIds(java.lang.String companyIds) {
    this.companyIds = companyIds;
    return this;
  }

  public void unsetCompanyIds() {
    this.companyIds = null;
  }

  /** Returns true if field companyIds is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyIds() {
    return this.companyIds != null;
  }

  public void setCompanyIdsIsSet(boolean value) {
    if (!value) {
      this.companyIds = null;
    }
  }

  public int getDisable() {
    return this.disable;
  }

  public CampaignPcRecommendCompanyDO setDisable(int disable) {
    this.disable = disable;
    setDisableIsSet(true);
    return this;
  }

  public void unsetDisable() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  /** Returns true if field disable is set (has been assigned a value) and false otherwise */
  public boolean isSetDisable() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  public void setDisableIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DISABLE_ISSET_ID, value);
  }

  public java.lang.String getCreateTime() {
    return this.createTime;
  }

  public CampaignPcRecommendCompanyDO setCreateTime(java.lang.String createTime) {
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

  public java.lang.String getUpdateTime() {
    return this.updateTime;
  }

  public CampaignPcRecommendCompanyDO setUpdateTime(java.lang.String updateTime) {
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

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Integer)value);
      }
      break;

    case MODULE_NAME:
      if (value == null) {
        unsetModuleName();
      } else {
        setModuleName((java.lang.String)value);
      }
      break;

    case MODULE_DESCRIPTION:
      if (value == null) {
        unsetModuleDescription();
      } else {
        setModuleDescription((java.lang.String)value);
      }
      break;

    case COMPANY_IDS:
      if (value == null) {
        unsetCompanyIds();
      } else {
        setCompanyIds((java.lang.String)value);
      }
      break;

    case DISABLE:
      if (value == null) {
        unsetDisable();
      } else {
        setDisable((java.lang.Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.String)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdateTime();
      } else {
        setUpdateTime((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case MODULE_NAME:
      return getModuleName();

    case MODULE_DESCRIPTION:
      return getModuleDescription();

    case COMPANY_IDS:
      return getCompanyIds();

    case DISABLE:
      return getDisable();

    case CREATE_TIME:
      return getCreateTime();

    case UPDATE_TIME:
      return getUpdateTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case MODULE_NAME:
      return isSetModuleName();
    case MODULE_DESCRIPTION:
      return isSetModuleDescription();
    case COMPANY_IDS:
      return isSetCompanyIds();
    case DISABLE:
      return isSetDisable();
    case CREATE_TIME:
      return isSetCreateTime();
    case UPDATE_TIME:
      return isSetUpdateTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CampaignPcRecommendCompanyDO)
      return this.equals((CampaignPcRecommendCompanyDO)that);
    return false;
  }

  public boolean equals(CampaignPcRecommendCompanyDO that) {
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

    boolean this_present_moduleName = true && this.isSetModuleName();
    boolean that_present_moduleName = true && that.isSetModuleName();
    if (this_present_moduleName || that_present_moduleName) {
      if (!(this_present_moduleName && that_present_moduleName))
        return false;
      if (!this.moduleName.equals(that.moduleName))
        return false;
    }

    boolean this_present_moduleDescription = true && this.isSetModuleDescription();
    boolean that_present_moduleDescription = true && that.isSetModuleDescription();
    if (this_present_moduleDescription || that_present_moduleDescription) {
      if (!(this_present_moduleDescription && that_present_moduleDescription))
        return false;
      if (!this.moduleDescription.equals(that.moduleDescription))
        return false;
    }

    boolean this_present_companyIds = true && this.isSetCompanyIds();
    boolean that_present_companyIds = true && that.isSetCompanyIds();
    if (this_present_companyIds || that_present_companyIds) {
      if (!(this_present_companyIds && that_present_companyIds))
        return false;
      if (!this.companyIds.equals(that.companyIds))
        return false;
    }

    boolean this_present_disable = true && this.isSetDisable();
    boolean that_present_disable = true && that.isSetDisable();
    if (this_present_disable || that_present_disable) {
      if (!(this_present_disable && that_present_disable))
        return false;
      if (this.disable != that.disable)
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

    boolean this_present_updateTime = true && this.isSetUpdateTime();
    boolean that_present_updateTime = true && that.isSetUpdateTime();
    if (this_present_updateTime || that_present_updateTime) {
      if (!(this_present_updateTime && that_present_updateTime))
        return false;
      if (!this.updateTime.equals(that.updateTime))
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

    hashCode = hashCode * 8191 + ((isSetModuleName()) ? 131071 : 524287);
    if (isSetModuleName())
      hashCode = hashCode * 8191 + moduleName.hashCode();

    hashCode = hashCode * 8191 + ((isSetModuleDescription()) ? 131071 : 524287);
    if (isSetModuleDescription())
      hashCode = hashCode * 8191 + moduleDescription.hashCode();

    hashCode = hashCode * 8191 + ((isSetCompanyIds()) ? 131071 : 524287);
    if (isSetCompanyIds())
      hashCode = hashCode * 8191 + companyIds.hashCode();

    hashCode = hashCode * 8191 + ((isSetDisable()) ? 131071 : 524287);
    if (isSetDisable())
      hashCode = hashCode * 8191 + disable;

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    hashCode = hashCode * 8191 + ((isSetUpdateTime()) ? 131071 : 524287);
    if (isSetUpdateTime())
      hashCode = hashCode * 8191 + updateTime.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(CampaignPcRecommendCompanyDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetModuleName()).compareTo(other.isSetModuleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleName, other.moduleName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetModuleDescription()).compareTo(other.isSetModuleDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetModuleDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moduleDescription, other.moduleDescription);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCompanyIds()).compareTo(other.isSetCompanyIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyIds, other.companyIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDisable()).compareTo(other.isSetDisable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.disable, other.disable);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUpdateTime()).compareTo(other.isSetUpdateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateTime, other.updateTime);
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
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CampaignPcRecommendCompanyDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetModuleName()) {
      if (!first) sb.append(", ");
      sb.append("moduleName:");
      if (this.moduleName == null) {
        sb.append("null");
      } else {
        sb.append(this.moduleName);
      }
      first = false;
    }
    if (isSetModuleDescription()) {
      if (!first) sb.append(", ");
      sb.append("moduleDescription:");
      if (this.moduleDescription == null) {
        sb.append("null");
      } else {
        sb.append(this.moduleDescription);
      }
      first = false;
    }
    if (isSetCompanyIds()) {
      if (!first) sb.append(", ");
      sb.append("companyIds:");
      if (this.companyIds == null) {
        sb.append("null");
      } else {
        sb.append(this.companyIds);
      }
      first = false;
    }
    if (isSetDisable()) {
      if (!first) sb.append(", ");
      sb.append("disable:");
      sb.append(this.disable);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CampaignPcRecommendCompanyDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CampaignPcRecommendCompanyDOStandardScheme getScheme() {
      return new CampaignPcRecommendCompanyDOStandardScheme();
    }
  }

  private static class CampaignPcRecommendCompanyDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<CampaignPcRecommendCompanyDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CampaignPcRecommendCompanyDO struct) throws org.apache.thrift.TException {
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
          case 2: // MODULE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moduleName = iprot.readString();
              struct.setModuleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MODULE_DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.moduleDescription = iprot.readString();
              struct.setModuleDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMPANY_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.companyIds = iprot.readString();
              struct.setCompanyIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DISABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.disable = iprot.readI32();
              struct.setDisableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createTime = iprot.readString();
              struct.setCreateTimeIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CampaignPcRecommendCompanyDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.moduleName != null) {
        if (struct.isSetModuleName()) {
          oprot.writeFieldBegin(MODULE_NAME_FIELD_DESC);
          oprot.writeString(struct.moduleName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.moduleDescription != null) {
        if (struct.isSetModuleDescription()) {
          oprot.writeFieldBegin(MODULE_DESCRIPTION_FIELD_DESC);
          oprot.writeString(struct.moduleDescription);
          oprot.writeFieldEnd();
        }
      }
      if (struct.companyIds != null) {
        if (struct.isSetCompanyIds()) {
          oprot.writeFieldBegin(COMPANY_IDS_FIELD_DESC);
          oprot.writeString(struct.companyIds);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDisable()) {
        oprot.writeFieldBegin(DISABLE_FIELD_DESC);
        oprot.writeI32(struct.disable);
        oprot.writeFieldEnd();
      }
      if (struct.createTime != null) {
        if (struct.isSetCreateTime()) {
          oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
          oprot.writeString(struct.createTime);
          oprot.writeFieldEnd();
        }
      }
      if (struct.updateTime != null) {
        if (struct.isSetUpdateTime()) {
          oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
          oprot.writeString(struct.updateTime);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CampaignPcRecommendCompanyDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CampaignPcRecommendCompanyDOTupleScheme getScheme() {
      return new CampaignPcRecommendCompanyDOTupleScheme();
    }
  }

  private static class CampaignPcRecommendCompanyDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<CampaignPcRecommendCompanyDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CampaignPcRecommendCompanyDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetModuleName()) {
        optionals.set(1);
      }
      if (struct.isSetModuleDescription()) {
        optionals.set(2);
      }
      if (struct.isSetCompanyIds()) {
        optionals.set(3);
      }
      if (struct.isSetDisable()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(5);
      }
      if (struct.isSetUpdateTime()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetModuleName()) {
        oprot.writeString(struct.moduleName);
      }
      if (struct.isSetModuleDescription()) {
        oprot.writeString(struct.moduleDescription);
      }
      if (struct.isSetCompanyIds()) {
        oprot.writeString(struct.companyIds);
      }
      if (struct.isSetDisable()) {
        oprot.writeI32(struct.disable);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
      if (struct.isSetUpdateTime()) {
        oprot.writeString(struct.updateTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CampaignPcRecommendCompanyDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.moduleName = iprot.readString();
        struct.setModuleNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.moduleDescription = iprot.readString();
        struct.setModuleDescriptionIsSet(true);
      }
      if (incoming.get(3)) {
        struct.companyIds = iprot.readString();
        struct.setCompanyIdsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.disable = iprot.readI32();
        struct.setDisableIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.updateTime = iprot.readString();
        struct.setUpdateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

