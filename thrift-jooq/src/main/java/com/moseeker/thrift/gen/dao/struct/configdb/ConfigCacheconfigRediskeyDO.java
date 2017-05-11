/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.configdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-08")
public class ConfigCacheconfigRediskeyDO implements org.apache.thrift.TBase<ConfigCacheconfigRediskeyDO, ConfigCacheconfigRediskeyDO._Fields>, java.io.Serializable, Cloneable, Comparable<ConfigCacheconfigRediskeyDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ConfigCacheconfigRediskeyDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROJECT_APPID_FIELD_DESC = new org.apache.thrift.protocol.TField("projectAppid", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField KEY_IDENTIFIER_FIELD_DESC = new org.apache.thrift.protocol.TField("keyIdentifier", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.BYTE, (short)4);
  private static final org.apache.thrift.protocol.TField PATTERN_FIELD_DESC = new org.apache.thrift.protocol.TField("pattern", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField JSON_EXTRAPARAMS_FIELD_DESC = new org.apache.thrift.protocol.TField("jsonExtraparams", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField TTL_FIELD_DESC = new org.apache.thrift.protocol.TField("ttl", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("desc", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ConfigCacheconfigRediskeyDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ConfigCacheconfigRediskeyDOTupleSchemeFactory();

  public int id; // optional
  public int projectAppid; // optional
  public String keyIdentifier; // optional
  public byte type; // optional
  public String pattern; // optional
  public String jsonExtraparams; // optional
  public int ttl; // optional
  public String desc; // optional
  public String createTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PROJECT_APPID((short)2, "projectAppid"),
    KEY_IDENTIFIER((short)3, "keyIdentifier"),
    TYPE((short)4, "type"),
    PATTERN((short)5, "pattern"),
    JSON_EXTRAPARAMS((short)6, "jsonExtraparams"),
    TTL((short)7, "ttl"),
    DESC((short)8, "desc"),
    CREATE_TIME((short)9, "createTime");

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
        case 2: // PROJECT_APPID
          return PROJECT_APPID;
        case 3: // KEY_IDENTIFIER
          return KEY_IDENTIFIER;
        case 4: // TYPE
          return TYPE;
        case 5: // PATTERN
          return PATTERN;
        case 6: // JSON_EXTRAPARAMS
          return JSON_EXTRAPARAMS;
        case 7: // TTL
          return TTL;
        case 8: // DESC
          return DESC;
        case 9: // CREATE_TIME
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
  private static final int __PROJECTAPPID_ISSET_ID = 1;
  private static final int __TYPE_ISSET_ID = 2;
  private static final int __TTL_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.PROJECT_APPID,_Fields.KEY_IDENTIFIER,_Fields.TYPE,_Fields.PATTERN,_Fields.JSON_EXTRAPARAMS,_Fields.TTL,_Fields.DESC,_Fields.CREATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROJECT_APPID, new org.apache.thrift.meta_data.FieldMetaData("projectAppid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.KEY_IDENTIFIER, new org.apache.thrift.meta_data.FieldMetaData("keyIdentifier", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.PATTERN, new org.apache.thrift.meta_data.FieldMetaData("pattern", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.JSON_EXTRAPARAMS, new org.apache.thrift.meta_data.FieldMetaData("jsonExtraparams", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TTL, new org.apache.thrift.meta_data.FieldMetaData("ttl", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DESC, new org.apache.thrift.meta_data.FieldMetaData("desc", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ConfigCacheconfigRediskeyDO.class, metaDataMap);
  }

  public ConfigCacheconfigRediskeyDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ConfigCacheconfigRediskeyDO(ConfigCacheconfigRediskeyDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.projectAppid = other.projectAppid;
    if (other.isSetKeyIdentifier()) {
      this.keyIdentifier = other.keyIdentifier;
    }
    this.type = other.type;
    if (other.isSetPattern()) {
      this.pattern = other.pattern;
    }
    if (other.isSetJsonExtraparams()) {
      this.jsonExtraparams = other.jsonExtraparams;
    }
    this.ttl = other.ttl;
    if (other.isSetDesc()) {
      this.desc = other.desc;
    }
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
  }

  public ConfigCacheconfigRediskeyDO deepCopy() {
    return new ConfigCacheconfigRediskeyDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setProjectAppidIsSet(false);
    this.projectAppid = 0;
    this.keyIdentifier = null;
    setTypeIsSet(false);
    this.type = 0;
    this.pattern = null;
    this.jsonExtraparams = null;
    setTtlIsSet(false);
    this.ttl = 0;
    this.desc = null;
    this.createTime = null;
  }

  public int getId() {
    return this.id;
  }

  public ConfigCacheconfigRediskeyDO setId(int id) {
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

  public int getProjectAppid() {
    return this.projectAppid;
  }

  public ConfigCacheconfigRediskeyDO setProjectAppid(int projectAppid) {
    this.projectAppid = projectAppid;
    setProjectAppidIsSet(true);
    return this;
  }

  public void unsetProjectAppid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PROJECTAPPID_ISSET_ID);
  }

  /** Returns true if field projectAppid is set (has been assigned a value) and false otherwise */
  public boolean isSetProjectAppid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PROJECTAPPID_ISSET_ID);
  }

  public void setProjectAppidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PROJECTAPPID_ISSET_ID, value);
  }

  public String getKeyIdentifier() {
    return this.keyIdentifier;
  }

  public ConfigCacheconfigRediskeyDO setKeyIdentifier(String keyIdentifier) {
    this.keyIdentifier = keyIdentifier;
    return this;
  }

  public void unsetKeyIdentifier() {
    this.keyIdentifier = null;
  }

  /** Returns true if field keyIdentifier is set (has been assigned a value) and false otherwise */
  public boolean isSetKeyIdentifier() {
    return this.keyIdentifier != null;
  }

  public void setKeyIdentifierIsSet(boolean value) {
    if (!value) {
      this.keyIdentifier = null;
    }
  }

  public byte getType() {
    return this.type;
  }

  public ConfigCacheconfigRediskeyDO setType(byte type) {
    this.type = type;
    setTypeIsSet(true);
    return this;
  }

  public void unsetType() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TYPE_ISSET_ID);
  }

  public void setTypeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TYPE_ISSET_ID, value);
  }

  public String getPattern() {
    return this.pattern;
  }

  public ConfigCacheconfigRediskeyDO setPattern(String pattern) {
    this.pattern = pattern;
    return this;
  }

  public void unsetPattern() {
    this.pattern = null;
  }

  /** Returns true if field pattern is set (has been assigned a value) and false otherwise */
  public boolean isSetPattern() {
    return this.pattern != null;
  }

  public void setPatternIsSet(boolean value) {
    if (!value) {
      this.pattern = null;
    }
  }

  public String getJsonExtraparams() {
    return this.jsonExtraparams;
  }

  public ConfigCacheconfigRediskeyDO setJsonExtraparams(String jsonExtraparams) {
    this.jsonExtraparams = jsonExtraparams;
    return this;
  }

  public void unsetJsonExtraparams() {
    this.jsonExtraparams = null;
  }

  /** Returns true if field jsonExtraparams is set (has been assigned a value) and false otherwise */
  public boolean isSetJsonExtraparams() {
    return this.jsonExtraparams != null;
  }

  public void setJsonExtraparamsIsSet(boolean value) {
    if (!value) {
      this.jsonExtraparams = null;
    }
  }

  public int getTtl() {
    return this.ttl;
  }

  public ConfigCacheconfigRediskeyDO setTtl(int ttl) {
    this.ttl = ttl;
    setTtlIsSet(true);
    return this;
  }

  public void unsetTtl() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TTL_ISSET_ID);
  }

  /** Returns true if field ttl is set (has been assigned a value) and false otherwise */
  public boolean isSetTtl() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TTL_ISSET_ID);
  }

  public void setTtlIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TTL_ISSET_ID, value);
  }

  public String getDesc() {
    return this.desc;
  }

  public ConfigCacheconfigRediskeyDO setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  public void unsetDesc() {
    this.desc = null;
  }

  /** Returns true if field desc is set (has been assigned a value) and false otherwise */
  public boolean isSetDesc() {
    return this.desc != null;
  }

  public void setDescIsSet(boolean value) {
    if (!value) {
      this.desc = null;
    }
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public ConfigCacheconfigRediskeyDO setCreateTime(String createTime) {
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

    case PROJECT_APPID:
      if (value == null) {
        unsetProjectAppid();
      } else {
        setProjectAppid((Integer)value);
      }
      break;

    case KEY_IDENTIFIER:
      if (value == null) {
        unsetKeyIdentifier();
      } else {
        setKeyIdentifier((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((Byte)value);
      }
      break;

    case PATTERN:
      if (value == null) {
        unsetPattern();
      } else {
        setPattern((String)value);
      }
      break;

    case JSON_EXTRAPARAMS:
      if (value == null) {
        unsetJsonExtraparams();
      } else {
        setJsonExtraparams((String)value);
      }
      break;

    case TTL:
      if (value == null) {
        unsetTtl();
      } else {
        setTtl((Integer)value);
      }
      break;

    case DESC:
      if (value == null) {
        unsetDesc();
      } else {
        setDesc((String)value);
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

    case PROJECT_APPID:
      return getProjectAppid();

    case KEY_IDENTIFIER:
      return getKeyIdentifier();

    case TYPE:
      return getType();

    case PATTERN:
      return getPattern();

    case JSON_EXTRAPARAMS:
      return getJsonExtraparams();

    case TTL:
      return getTtl();

    case DESC:
      return getDesc();

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
    case PROJECT_APPID:
      return isSetProjectAppid();
    case KEY_IDENTIFIER:
      return isSetKeyIdentifier();
    case TYPE:
      return isSetType();
    case PATTERN:
      return isSetPattern();
    case JSON_EXTRAPARAMS:
      return isSetJsonExtraparams();
    case TTL:
      return isSetTtl();
    case DESC:
      return isSetDesc();
    case CREATE_TIME:
      return isSetCreateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ConfigCacheconfigRediskeyDO)
      return this.equals((ConfigCacheconfigRediskeyDO)that);
    return false;
  }

  public boolean equals(ConfigCacheconfigRediskeyDO that) {
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

    boolean this_present_projectAppid = true && this.isSetProjectAppid();
    boolean that_present_projectAppid = true && that.isSetProjectAppid();
    if (this_present_projectAppid || that_present_projectAppid) {
      if (!(this_present_projectAppid && that_present_projectAppid))
        return false;
      if (this.projectAppid != that.projectAppid)
        return false;
    }

    boolean this_present_keyIdentifier = true && this.isSetKeyIdentifier();
    boolean that_present_keyIdentifier = true && that.isSetKeyIdentifier();
    if (this_present_keyIdentifier || that_present_keyIdentifier) {
      if (!(this_present_keyIdentifier && that_present_keyIdentifier))
        return false;
      if (!this.keyIdentifier.equals(that.keyIdentifier))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (this.type != that.type)
        return false;
    }

    boolean this_present_pattern = true && this.isSetPattern();
    boolean that_present_pattern = true && that.isSetPattern();
    if (this_present_pattern || that_present_pattern) {
      if (!(this_present_pattern && that_present_pattern))
        return false;
      if (!this.pattern.equals(that.pattern))
        return false;
    }

    boolean this_present_jsonExtraparams = true && this.isSetJsonExtraparams();
    boolean that_present_jsonExtraparams = true && that.isSetJsonExtraparams();
    if (this_present_jsonExtraparams || that_present_jsonExtraparams) {
      if (!(this_present_jsonExtraparams && that_present_jsonExtraparams))
        return false;
      if (!this.jsonExtraparams.equals(that.jsonExtraparams))
        return false;
    }

    boolean this_present_ttl = true && this.isSetTtl();
    boolean that_present_ttl = true && that.isSetTtl();
    if (this_present_ttl || that_present_ttl) {
      if (!(this_present_ttl && that_present_ttl))
        return false;
      if (this.ttl != that.ttl)
        return false;
    }

    boolean this_present_desc = true && this.isSetDesc();
    boolean that_present_desc = true && that.isSetDesc();
    if (this_present_desc || that_present_desc) {
      if (!(this_present_desc && that_present_desc))
        return false;
      if (!this.desc.equals(that.desc))
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

    hashCode = hashCode * 8191 + ((isSetProjectAppid()) ? 131071 : 524287);
    if (isSetProjectAppid())
      hashCode = hashCode * 8191 + projectAppid;

    hashCode = hashCode * 8191 + ((isSetKeyIdentifier()) ? 131071 : 524287);
    if (isSetKeyIdentifier())
      hashCode = hashCode * 8191 + keyIdentifier.hashCode();

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + (int) (type);

    hashCode = hashCode * 8191 + ((isSetPattern()) ? 131071 : 524287);
    if (isSetPattern())
      hashCode = hashCode * 8191 + pattern.hashCode();

    hashCode = hashCode * 8191 + ((isSetJsonExtraparams()) ? 131071 : 524287);
    if (isSetJsonExtraparams())
      hashCode = hashCode * 8191 + jsonExtraparams.hashCode();

    hashCode = hashCode * 8191 + ((isSetTtl()) ? 131071 : 524287);
    if (isSetTtl())
      hashCode = hashCode * 8191 + ttl;

    hashCode = hashCode * 8191 + ((isSetDesc()) ? 131071 : 524287);
    if (isSetDesc())
      hashCode = hashCode * 8191 + desc.hashCode();

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ConfigCacheconfigRediskeyDO other) {
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
    lastComparison = Boolean.valueOf(isSetProjectAppid()).compareTo(other.isSetProjectAppid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProjectAppid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.projectAppid, other.projectAppid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKeyIdentifier()).compareTo(other.isSetKeyIdentifier());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKeyIdentifier()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keyIdentifier, other.keyIdentifier);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPattern()).compareTo(other.isSetPattern());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPattern()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pattern, other.pattern);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJsonExtraparams()).compareTo(other.isSetJsonExtraparams());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJsonExtraparams()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jsonExtraparams, other.jsonExtraparams);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTtl()).compareTo(other.isSetTtl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTtl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ttl, other.ttl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDesc()).compareTo(other.isSetDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.desc, other.desc);
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
    StringBuilder sb = new StringBuilder("ConfigCacheconfigRediskeyDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetProjectAppid()) {
      if (!first) sb.append(", ");
      sb.append("projectAppid:");
      sb.append(this.projectAppid);
      first = false;
    }
    if (isSetKeyIdentifier()) {
      if (!first) sb.append(", ");
      sb.append("keyIdentifier:");
      if (this.keyIdentifier == null) {
        sb.append("null");
      } else {
        sb.append(this.keyIdentifier);
      }
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      sb.append(this.type);
      first = false;
    }
    if (isSetPattern()) {
      if (!first) sb.append(", ");
      sb.append("pattern:");
      if (this.pattern == null) {
        sb.append("null");
      } else {
        sb.append(this.pattern);
      }
      first = false;
    }
    if (isSetJsonExtraparams()) {
      if (!first) sb.append(", ");
      sb.append("jsonExtraparams:");
      if (this.jsonExtraparams == null) {
        sb.append("null");
      } else {
        sb.append(this.jsonExtraparams);
      }
      first = false;
    }
    if (isSetTtl()) {
      if (!first) sb.append(", ");
      sb.append("ttl:");
      sb.append(this.ttl);
      first = false;
    }
    if (isSetDesc()) {
      if (!first) sb.append(", ");
      sb.append("desc:");
      if (this.desc == null) {
        sb.append("null");
      } else {
        sb.append(this.desc);
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

  private static class ConfigCacheconfigRediskeyDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConfigCacheconfigRediskeyDOStandardScheme getScheme() {
      return new ConfigCacheconfigRediskeyDOStandardScheme();
    }
  }

  private static class ConfigCacheconfigRediskeyDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<ConfigCacheconfigRediskeyDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ConfigCacheconfigRediskeyDO struct) throws org.apache.thrift.TException {
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
          case 2: // PROJECT_APPID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.projectAppid = iprot.readI32();
              struct.setProjectAppidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // KEY_IDENTIFIER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.keyIdentifier = iprot.readString();
              struct.setKeyIdentifierIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.type = iprot.readByte();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PATTERN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.pattern = iprot.readString();
              struct.setPatternIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // JSON_EXTRAPARAMS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.jsonExtraparams = iprot.readString();
              struct.setJsonExtraparamsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // TTL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ttl = iprot.readI32();
              struct.setTtlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.desc = iprot.readString();
              struct.setDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // CREATE_TIME
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ConfigCacheconfigRediskeyDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProjectAppid()) {
        oprot.writeFieldBegin(PROJECT_APPID_FIELD_DESC);
        oprot.writeI32(struct.projectAppid);
        oprot.writeFieldEnd();
      }
      if (struct.keyIdentifier != null) {
        if (struct.isSetKeyIdentifier()) {
          oprot.writeFieldBegin(KEY_IDENTIFIER_FIELD_DESC);
          oprot.writeString(struct.keyIdentifier);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetType()) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeByte(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.pattern != null) {
        if (struct.isSetPattern()) {
          oprot.writeFieldBegin(PATTERN_FIELD_DESC);
          oprot.writeString(struct.pattern);
          oprot.writeFieldEnd();
        }
      }
      if (struct.jsonExtraparams != null) {
        if (struct.isSetJsonExtraparams()) {
          oprot.writeFieldBegin(JSON_EXTRAPARAMS_FIELD_DESC);
          oprot.writeString(struct.jsonExtraparams);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetTtl()) {
        oprot.writeFieldBegin(TTL_FIELD_DESC);
        oprot.writeI32(struct.ttl);
        oprot.writeFieldEnd();
      }
      if (struct.desc != null) {
        if (struct.isSetDesc()) {
          oprot.writeFieldBegin(DESC_FIELD_DESC);
          oprot.writeString(struct.desc);
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

  private static class ConfigCacheconfigRediskeyDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConfigCacheconfigRediskeyDOTupleScheme getScheme() {
      return new ConfigCacheconfigRediskeyDOTupleScheme();
    }
  }

  private static class ConfigCacheconfigRediskeyDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<ConfigCacheconfigRediskeyDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ConfigCacheconfigRediskeyDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetProjectAppid()) {
        optionals.set(1);
      }
      if (struct.isSetKeyIdentifier()) {
        optionals.set(2);
      }
      if (struct.isSetType()) {
        optionals.set(3);
      }
      if (struct.isSetPattern()) {
        optionals.set(4);
      }
      if (struct.isSetJsonExtraparams()) {
        optionals.set(5);
      }
      if (struct.isSetTtl()) {
        optionals.set(6);
      }
      if (struct.isSetDesc()) {
        optionals.set(7);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetProjectAppid()) {
        oprot.writeI32(struct.projectAppid);
      }
      if (struct.isSetKeyIdentifier()) {
        oprot.writeString(struct.keyIdentifier);
      }
      if (struct.isSetType()) {
        oprot.writeByte(struct.type);
      }
      if (struct.isSetPattern()) {
        oprot.writeString(struct.pattern);
      }
      if (struct.isSetJsonExtraparams()) {
        oprot.writeString(struct.jsonExtraparams);
      }
      if (struct.isSetTtl()) {
        oprot.writeI32(struct.ttl);
      }
      if (struct.isSetDesc()) {
        oprot.writeString(struct.desc);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ConfigCacheconfigRediskeyDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.projectAppid = iprot.readI32();
        struct.setProjectAppidIsSet(true);
      }
      if (incoming.get(2)) {
        struct.keyIdentifier = iprot.readString();
        struct.setKeyIdentifierIsSet(true);
      }
      if (incoming.get(3)) {
        struct.type = iprot.readByte();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.pattern = iprot.readString();
        struct.setPatternIsSet(true);
      }
      if (incoming.get(5)) {
        struct.jsonExtraparams = iprot.readString();
        struct.setJsonExtraparamsIsSet(true);
      }
      if (incoming.get(6)) {
        struct.ttl = iprot.readI32();
        struct.setTtlIsSet(true);
      }
      if (incoming.get(7)) {
        struct.desc = iprot.readString();
        struct.setDescIsSet(true);
      }
      if (incoming.get(8)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

