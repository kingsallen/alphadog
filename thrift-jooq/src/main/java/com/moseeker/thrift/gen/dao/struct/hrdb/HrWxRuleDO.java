/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.hrdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-12")
public class HrWxRuleDO implements org.apache.thrift.TBase<HrWxRuleDO, HrWxRuleDO._Fields>, java.io.Serializable, Cloneable, Comparable<HrWxRuleDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrWxRuleDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField WECHAT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("wechatId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CID_FIELD_DESC = new org.apache.thrift.protocol.TField("cid", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField COMPONENT_FIELD_DESC = new org.apache.thrift.protocol.TField("component", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField DISPLAYORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("displayorder", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField ACCESS_LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("accessLevel", org.apache.thrift.protocol.TType.I32, (short)8);
  private static final org.apache.thrift.protocol.TField KEYWORDS_FIELD_DESC = new org.apache.thrift.protocol.TField("keywords", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrWxRuleDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrWxRuleDOTupleSchemeFactory();

  public int id; // optional
  public int wechatId; // optional
  public int cid; // optional
  public java.lang.String name; // optional
  public java.lang.String component; // optional
  public int displayorder; // optional
  public double status; // optional
  public int accessLevel; // optional
  public java.lang.String keywords; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    WECHAT_ID((short)2, "wechatId"),
    CID((short)3, "cid"),
    NAME((short)4, "name"),
    COMPONENT((short)5, "component"),
    DISPLAYORDER((short)6, "displayorder"),
    STATUS((short)7, "status"),
    ACCESS_LEVEL((short)8, "accessLevel"),
    KEYWORDS((short)9, "keywords");

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
        case 2: // WECHAT_ID
          return WECHAT_ID;
        case 3: // CID
          return CID;
        case 4: // NAME
          return NAME;
        case 5: // COMPONENT
          return COMPONENT;
        case 6: // DISPLAYORDER
          return DISPLAYORDER;
        case 7: // STATUS
          return STATUS;
        case 8: // ACCESS_LEVEL
          return ACCESS_LEVEL;
        case 9: // KEYWORDS
          return KEYWORDS;
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
  private static final int __WECHATID_ISSET_ID = 1;
  private static final int __CID_ISSET_ID = 2;
  private static final int __DISPLAYORDER_ISSET_ID = 3;
  private static final int __STATUS_ISSET_ID = 4;
  private static final int __ACCESSLEVEL_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.WECHAT_ID,_Fields.CID,_Fields.NAME,_Fields.COMPONENT,_Fields.DISPLAYORDER,_Fields.STATUS,_Fields.ACCESS_LEVEL,_Fields.KEYWORDS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WECHAT_ID, new org.apache.thrift.meta_data.FieldMetaData("wechatId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CID, new org.apache.thrift.meta_data.FieldMetaData("cid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPONENT, new org.apache.thrift.meta_data.FieldMetaData("component", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DISPLAYORDER, new org.apache.thrift.meta_data.FieldMetaData("displayorder", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.ACCESS_LEVEL, new org.apache.thrift.meta_data.FieldMetaData("accessLevel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.KEYWORDS, new org.apache.thrift.meta_data.FieldMetaData("keywords", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrWxRuleDO.class, metaDataMap);
  }

  public HrWxRuleDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrWxRuleDO(HrWxRuleDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.wechatId = other.wechatId;
    this.cid = other.cid;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetComponent()) {
      this.component = other.component;
    }
    this.displayorder = other.displayorder;
    this.status = other.status;
    this.accessLevel = other.accessLevel;
    if (other.isSetKeywords()) {
      this.keywords = other.keywords;
    }
  }

  public HrWxRuleDO deepCopy() {
    return new HrWxRuleDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setWechatIdIsSet(false);
    this.wechatId = 0;
    setCidIsSet(false);
    this.cid = 0;
    this.name = null;
    this.component = null;
    setDisplayorderIsSet(false);
    this.displayorder = 0;
    setStatusIsSet(false);
    this.status = 0.0;
    setAccessLevelIsSet(false);
    this.accessLevel = 0;
    this.keywords = null;
  }

  public int getId() {
    return this.id;
  }

  public HrWxRuleDO setId(int id) {
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

  public int getWechatId() {
    return this.wechatId;
  }

  public HrWxRuleDO setWechatId(int wechatId) {
    this.wechatId = wechatId;
    setWechatIdIsSet(true);
    return this;
  }

  public void unsetWechatId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WECHATID_ISSET_ID);
  }

  /** Returns true if field wechatId is set (has been assigned a value) and false otherwise */
  public boolean isSetWechatId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WECHATID_ISSET_ID);
  }

  public void setWechatIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WECHATID_ISSET_ID, value);
  }

  public int getCid() {
    return this.cid;
  }

  public HrWxRuleDO setCid(int cid) {
    this.cid = cid;
    setCidIsSet(true);
    return this;
  }

  public void unsetCid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CID_ISSET_ID);
  }

  /** Returns true if field cid is set (has been assigned a value) and false otherwise */
  public boolean isSetCid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CID_ISSET_ID);
  }

  public void setCidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CID_ISSET_ID, value);
  }

  public java.lang.String getName() {
    return this.name;
  }

  public HrWxRuleDO setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public java.lang.String getComponent() {
    return this.component;
  }

  public HrWxRuleDO setComponent(java.lang.String component) {
    this.component = component;
    return this;
  }

  public void unsetComponent() {
    this.component = null;
  }

  /** Returns true if field component is set (has been assigned a value) and false otherwise */
  public boolean isSetComponent() {
    return this.component != null;
  }

  public void setComponentIsSet(boolean value) {
    if (!value) {
      this.component = null;
    }
  }

  public int getDisplayorder() {
    return this.displayorder;
  }

  public HrWxRuleDO setDisplayorder(int displayorder) {
    this.displayorder = displayorder;
    setDisplayorderIsSet(true);
    return this;
  }

  public void unsetDisplayorder() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DISPLAYORDER_ISSET_ID);
  }

  /** Returns true if field displayorder is set (has been assigned a value) and false otherwise */
  public boolean isSetDisplayorder() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DISPLAYORDER_ISSET_ID);
  }

  public void setDisplayorderIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DISPLAYORDER_ISSET_ID, value);
  }

  public double getStatus() {
    return this.status;
  }

  public HrWxRuleDO setStatus(double status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
  }

  public int getAccessLevel() {
    return this.accessLevel;
  }

  public HrWxRuleDO setAccessLevel(int accessLevel) {
    this.accessLevel = accessLevel;
    setAccessLevelIsSet(true);
    return this;
  }

  public void unsetAccessLevel() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ACCESSLEVEL_ISSET_ID);
  }

  /** Returns true if field accessLevel is set (has been assigned a value) and false otherwise */
  public boolean isSetAccessLevel() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ACCESSLEVEL_ISSET_ID);
  }

  public void setAccessLevelIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ACCESSLEVEL_ISSET_ID, value);
  }

  public java.lang.String getKeywords() {
    return this.keywords;
  }

  public HrWxRuleDO setKeywords(java.lang.String keywords) {
    this.keywords = keywords;
    return this;
  }

  public void unsetKeywords() {
    this.keywords = null;
  }

  /** Returns true if field keywords is set (has been assigned a value) and false otherwise */
  public boolean isSetKeywords() {
    return this.keywords != null;
  }

  public void setKeywordsIsSet(boolean value) {
    if (!value) {
      this.keywords = null;
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

    case WECHAT_ID:
      if (value == null) {
        unsetWechatId();
      } else {
        setWechatId((java.lang.Integer)value);
      }
      break;

    case CID:
      if (value == null) {
        unsetCid();
      } else {
        setCid((java.lang.Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case COMPONENT:
      if (value == null) {
        unsetComponent();
      } else {
        setComponent((java.lang.String)value);
      }
      break;

    case DISPLAYORDER:
      if (value == null) {
        unsetDisplayorder();
      } else {
        setDisplayorder((java.lang.Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((java.lang.Double)value);
      }
      break;

    case ACCESS_LEVEL:
      if (value == null) {
        unsetAccessLevel();
      } else {
        setAccessLevel((java.lang.Integer)value);
      }
      break;

    case KEYWORDS:
      if (value == null) {
        unsetKeywords();
      } else {
        setKeywords((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case WECHAT_ID:
      return getWechatId();

    case CID:
      return getCid();

    case NAME:
      return getName();

    case COMPONENT:
      return getComponent();

    case DISPLAYORDER:
      return getDisplayorder();

    case STATUS:
      return getStatus();

    case ACCESS_LEVEL:
      return getAccessLevel();

    case KEYWORDS:
      return getKeywords();

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
    case WECHAT_ID:
      return isSetWechatId();
    case CID:
      return isSetCid();
    case NAME:
      return isSetName();
    case COMPONENT:
      return isSetComponent();
    case DISPLAYORDER:
      return isSetDisplayorder();
    case STATUS:
      return isSetStatus();
    case ACCESS_LEVEL:
      return isSetAccessLevel();
    case KEYWORDS:
      return isSetKeywords();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof HrWxRuleDO)
      return this.equals((HrWxRuleDO)that);
    return false;
  }

  public boolean equals(HrWxRuleDO that) {
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

    boolean this_present_wechatId = true && this.isSetWechatId();
    boolean that_present_wechatId = true && that.isSetWechatId();
    if (this_present_wechatId || that_present_wechatId) {
      if (!(this_present_wechatId && that_present_wechatId))
        return false;
      if (this.wechatId != that.wechatId)
        return false;
    }

    boolean this_present_cid = true && this.isSetCid();
    boolean that_present_cid = true && that.isSetCid();
    if (this_present_cid || that_present_cid) {
      if (!(this_present_cid && that_present_cid))
        return false;
      if (this.cid != that.cid)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_component = true && this.isSetComponent();
    boolean that_present_component = true && that.isSetComponent();
    if (this_present_component || that_present_component) {
      if (!(this_present_component && that_present_component))
        return false;
      if (!this.component.equals(that.component))
        return false;
    }

    boolean this_present_displayorder = true && this.isSetDisplayorder();
    boolean that_present_displayorder = true && that.isSetDisplayorder();
    if (this_present_displayorder || that_present_displayorder) {
      if (!(this_present_displayorder && that_present_displayorder))
        return false;
      if (this.displayorder != that.displayorder)
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    boolean this_present_accessLevel = true && this.isSetAccessLevel();
    boolean that_present_accessLevel = true && that.isSetAccessLevel();
    if (this_present_accessLevel || that_present_accessLevel) {
      if (!(this_present_accessLevel && that_present_accessLevel))
        return false;
      if (this.accessLevel != that.accessLevel)
        return false;
    }

    boolean this_present_keywords = true && this.isSetKeywords();
    boolean that_present_keywords = true && that.isSetKeywords();
    if (this_present_keywords || that_present_keywords) {
      if (!(this_present_keywords && that_present_keywords))
        return false;
      if (!this.keywords.equals(that.keywords))
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

    hashCode = hashCode * 8191 + ((isSetWechatId()) ? 131071 : 524287);
    if (isSetWechatId())
      hashCode = hashCode * 8191 + wechatId;

    hashCode = hashCode * 8191 + ((isSetCid()) ? 131071 : 524287);
    if (isSetCid())
      hashCode = hashCode * 8191 + cid;

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetComponent()) ? 131071 : 524287);
    if (isSetComponent())
      hashCode = hashCode * 8191 + component.hashCode();

    hashCode = hashCode * 8191 + ((isSetDisplayorder()) ? 131071 : 524287);
    if (isSetDisplayorder())
      hashCode = hashCode * 8191 + displayorder;

    hashCode = hashCode * 8191 + ((isSetStatus()) ? 131071 : 524287);
    if (isSetStatus())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(status);

    hashCode = hashCode * 8191 + ((isSetAccessLevel()) ? 131071 : 524287);
    if (isSetAccessLevel())
      hashCode = hashCode * 8191 + accessLevel;

    hashCode = hashCode * 8191 + ((isSetKeywords()) ? 131071 : 524287);
    if (isSetKeywords())
      hashCode = hashCode * 8191 + keywords.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HrWxRuleDO other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetWechatId()).compareTo(other.isSetWechatId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWechatId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.wechatId, other.wechatId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCid()).compareTo(other.isSetCid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cid, other.cid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetComponent()).compareTo(other.isSetComponent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComponent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.component, other.component);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDisplayorder()).compareTo(other.isSetDisplayorder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisplayorder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.displayorder, other.displayorder);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAccessLevel()).compareTo(other.isSetAccessLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccessLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accessLevel, other.accessLevel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetKeywords()).compareTo(other.isSetKeywords());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKeywords()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keywords, other.keywords);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HrWxRuleDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetWechatId()) {
      if (!first) sb.append(", ");
      sb.append("wechatId:");
      sb.append(this.wechatId);
      first = false;
    }
    if (isSetCid()) {
      if (!first) sb.append(", ");
      sb.append("cid:");
      sb.append(this.cid);
      first = false;
    }
    if (isSetName()) {
      if (!first) sb.append(", ");
      sb.append("name:");
      if (this.name == null) {
        sb.append("null");
      } else {
        sb.append(this.name);
      }
      first = false;
    }
    if (isSetComponent()) {
      if (!first) sb.append(", ");
      sb.append("component:");
      if (this.component == null) {
        sb.append("null");
      } else {
        sb.append(this.component);
      }
      first = false;
    }
    if (isSetDisplayorder()) {
      if (!first) sb.append(", ");
      sb.append("displayorder:");
      sb.append(this.displayorder);
      first = false;
    }
    if (isSetStatus()) {
      if (!first) sb.append(", ");
      sb.append("status:");
      sb.append(this.status);
      first = false;
    }
    if (isSetAccessLevel()) {
      if (!first) sb.append(", ");
      sb.append("accessLevel:");
      sb.append(this.accessLevel);
      first = false;
    }
    if (isSetKeywords()) {
      if (!first) sb.append(", ");
      sb.append("keywords:");
      if (this.keywords == null) {
        sb.append("null");
      } else {
        sb.append(this.keywords);
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

  private static class HrWxRuleDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrWxRuleDOStandardScheme getScheme() {
      return new HrWxRuleDOStandardScheme();
    }
  }

  private static class HrWxRuleDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrWxRuleDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrWxRuleDO struct) throws org.apache.thrift.TException {
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
          case 2: // WECHAT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.wechatId = iprot.readI32();
              struct.setWechatIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.cid = iprot.readI32();
              struct.setCidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COMPONENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.component = iprot.readString();
              struct.setComponentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DISPLAYORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.displayorder = iprot.readI32();
              struct.setDisplayorderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.status = iprot.readDouble();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ACCESS_LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.accessLevel = iprot.readI32();
              struct.setAccessLevelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // KEYWORDS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.keywords = iprot.readString();
              struct.setKeywordsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrWxRuleDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetWechatId()) {
        oprot.writeFieldBegin(WECHAT_ID_FIELD_DESC);
        oprot.writeI32(struct.wechatId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCid()) {
        oprot.writeFieldBegin(CID_FIELD_DESC);
        oprot.writeI32(struct.cid);
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.component != null) {
        if (struct.isSetComponent()) {
          oprot.writeFieldBegin(COMPONENT_FIELD_DESC);
          oprot.writeString(struct.component);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDisplayorder()) {
        oprot.writeFieldBegin(DISPLAYORDER_FIELD_DESC);
        oprot.writeI32(struct.displayorder);
        oprot.writeFieldEnd();
      }
      if (struct.isSetStatus()) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeDouble(struct.status);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAccessLevel()) {
        oprot.writeFieldBegin(ACCESS_LEVEL_FIELD_DESC);
        oprot.writeI32(struct.accessLevel);
        oprot.writeFieldEnd();
      }
      if (struct.keywords != null) {
        if (struct.isSetKeywords()) {
          oprot.writeFieldBegin(KEYWORDS_FIELD_DESC);
          oprot.writeString(struct.keywords);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrWxRuleDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrWxRuleDOTupleScheme getScheme() {
      return new HrWxRuleDOTupleScheme();
    }
  }

  private static class HrWxRuleDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrWxRuleDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrWxRuleDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetWechatId()) {
        optionals.set(1);
      }
      if (struct.isSetCid()) {
        optionals.set(2);
      }
      if (struct.isSetName()) {
        optionals.set(3);
      }
      if (struct.isSetComponent()) {
        optionals.set(4);
      }
      if (struct.isSetDisplayorder()) {
        optionals.set(5);
      }
      if (struct.isSetStatus()) {
        optionals.set(6);
      }
      if (struct.isSetAccessLevel()) {
        optionals.set(7);
      }
      if (struct.isSetKeywords()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetWechatId()) {
        oprot.writeI32(struct.wechatId);
      }
      if (struct.isSetCid()) {
        oprot.writeI32(struct.cid);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetComponent()) {
        oprot.writeString(struct.component);
      }
      if (struct.isSetDisplayorder()) {
        oprot.writeI32(struct.displayorder);
      }
      if (struct.isSetStatus()) {
        oprot.writeDouble(struct.status);
      }
      if (struct.isSetAccessLevel()) {
        oprot.writeI32(struct.accessLevel);
      }
      if (struct.isSetKeywords()) {
        oprot.writeString(struct.keywords);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrWxRuleDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.wechatId = iprot.readI32();
        struct.setWechatIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.cid = iprot.readI32();
        struct.setCidIsSet(true);
      }
      if (incoming.get(3)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.component = iprot.readString();
        struct.setComponentIsSet(true);
      }
      if (incoming.get(5)) {
        struct.displayorder = iprot.readI32();
        struct.setDisplayorderIsSet(true);
      }
      if (incoming.get(6)) {
        struct.status = iprot.readDouble();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(7)) {
        struct.accessLevel = iprot.readI32();
        struct.setAccessLevelIsSet(true);
      }
      if (incoming.get(8)) {
        struct.keywords = iprot.readString();
        struct.setKeywordsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

