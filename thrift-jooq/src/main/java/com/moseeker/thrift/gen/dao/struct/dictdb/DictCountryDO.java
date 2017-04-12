/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.dictdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-21")
public class DictCountryDO implements org.apache.thrift.TBase<DictCountryDO, DictCountryDO._Fields>, java.io.Serializable, Cloneable, Comparable<DictCountryDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DictCountryDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ENAME_FIELD_DESC = new org.apache.thrift.protocol.TField("ename", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ISO_CODE2_FIELD_DESC = new org.apache.thrift.protocol.TField("isoCode2", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ISO_CODE3_FIELD_DESC = new org.apache.thrift.protocol.TField("isoCode3", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField ICON_CLASS_FIELD_DESC = new org.apache.thrift.protocol.TField("iconClass", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField HOT_COUNTRY_FIELD_DESC = new org.apache.thrift.protocol.TField("hotCountry", org.apache.thrift.protocol.TType.BYTE, (short)8);
  private static final org.apache.thrift.protocol.TField CONTINENT_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("continentCode", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DictCountryDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DictCountryDOTupleSchemeFactory();

  public int id; // optional
  public java.lang.String name; // optional
  public java.lang.String ename; // optional
  public java.lang.String isoCode2; // optional
  public java.lang.String isoCode3; // optional
  public java.lang.String code; // optional
  public java.lang.String iconClass; // optional
  public byte hotCountry; // optional
  public int continentCode; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    NAME((short)2, "name"),
    ENAME((short)3, "ename"),
    ISO_CODE2((short)4, "isoCode2"),
    ISO_CODE3((short)5, "isoCode3"),
    CODE((short)6, "code"),
    ICON_CLASS((short)7, "iconClass"),
    HOT_COUNTRY((short)8, "hotCountry"),
    CONTINENT_CODE((short)9, "continentCode");

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
        case 2: // NAME
          return NAME;
        case 3: // ENAME
          return ENAME;
        case 4: // ISO_CODE2
          return ISO_CODE2;
        case 5: // ISO_CODE3
          return ISO_CODE3;
        case 6: // CODE
          return CODE;
        case 7: // ICON_CLASS
          return ICON_CLASS;
        case 8: // HOT_COUNTRY
          return HOT_COUNTRY;
        case 9: // CONTINENT_CODE
          return CONTINENT_CODE;
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
  private static final int __HOTCOUNTRY_ISSET_ID = 1;
  private static final int __CONTINENTCODE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.NAME,_Fields.ENAME,_Fields.ISO_CODE2,_Fields.ISO_CODE3,_Fields.CODE,_Fields.ICON_CLASS,_Fields.HOT_COUNTRY,_Fields.CONTINENT_CODE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ENAME, new org.apache.thrift.meta_data.FieldMetaData("ename", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ISO_CODE2, new org.apache.thrift.meta_data.FieldMetaData("isoCode2", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ISO_CODE3, new org.apache.thrift.meta_data.FieldMetaData("isoCode3", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ICON_CLASS, new org.apache.thrift.meta_data.FieldMetaData("iconClass", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HOT_COUNTRY, new org.apache.thrift.meta_data.FieldMetaData("hotCountry", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.CONTINENT_CODE, new org.apache.thrift.meta_data.FieldMetaData("continentCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DictCountryDO.class, metaDataMap);
  }

  public DictCountryDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DictCountryDO(DictCountryDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetEname()) {
      this.ename = other.ename;
    }
    if (other.isSetIsoCode2()) {
      this.isoCode2 = other.isoCode2;
    }
    if (other.isSetIsoCode3()) {
      this.isoCode3 = other.isoCode3;
    }
    if (other.isSetCode()) {
      this.code = other.code;
    }
    if (other.isSetIconClass()) {
      this.iconClass = other.iconClass;
    }
    this.hotCountry = other.hotCountry;
    this.continentCode = other.continentCode;
  }

  public DictCountryDO deepCopy() {
    return new DictCountryDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.name = null;
    this.ename = null;
    this.isoCode2 = null;
    this.isoCode3 = null;
    this.code = null;
    this.iconClass = null;
    setHotCountryIsSet(false);
    this.hotCountry = 0;
    setContinentCodeIsSet(false);
    this.continentCode = 0;
  }

  public int getId() {
    return this.id;
  }

  public DictCountryDO setId(int id) {
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

  public java.lang.String getName() {
    return this.name;
  }

  public DictCountryDO setName(java.lang.String name) {
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

  public java.lang.String getEname() {
    return this.ename;
  }

  public DictCountryDO setEname(java.lang.String ename) {
    this.ename = ename;
    return this;
  }

  public void unsetEname() {
    this.ename = null;
  }

  /** Returns true if field ename is set (has been assigned a value) and false otherwise */
  public boolean isSetEname() {
    return this.ename != null;
  }

  public void setEnameIsSet(boolean value) {
    if (!value) {
      this.ename = null;
    }
  }

  public java.lang.String getIsoCode2() {
    return this.isoCode2;
  }

  public DictCountryDO setIsoCode2(java.lang.String isoCode2) {
    this.isoCode2 = isoCode2;
    return this;
  }

  public void unsetIsoCode2() {
    this.isoCode2 = null;
  }

  /** Returns true if field isoCode2 is set (has been assigned a value) and false otherwise */
  public boolean isSetIsoCode2() {
    return this.isoCode2 != null;
  }

  public void setIsoCode2IsSet(boolean value) {
    if (!value) {
      this.isoCode2 = null;
    }
  }

  public java.lang.String getIsoCode3() {
    return this.isoCode3;
  }

  public DictCountryDO setIsoCode3(java.lang.String isoCode3) {
    this.isoCode3 = isoCode3;
    return this;
  }

  public void unsetIsoCode3() {
    this.isoCode3 = null;
  }

  /** Returns true if field isoCode3 is set (has been assigned a value) and false otherwise */
  public boolean isSetIsoCode3() {
    return this.isoCode3 != null;
  }

  public void setIsoCode3IsSet(boolean value) {
    if (!value) {
      this.isoCode3 = null;
    }
  }

  public java.lang.String getCode() {
    return this.code;
  }

  public DictCountryDO setCode(java.lang.String code) {
    this.code = code;
    return this;
  }

  public void unsetCode() {
    this.code = null;
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return this.code != null;
  }

  public void setCodeIsSet(boolean value) {
    if (!value) {
      this.code = null;
    }
  }

  public java.lang.String getIconClass() {
    return this.iconClass;
  }

  public DictCountryDO setIconClass(java.lang.String iconClass) {
    this.iconClass = iconClass;
    return this;
  }

  public void unsetIconClass() {
    this.iconClass = null;
  }

  /** Returns true if field iconClass is set (has been assigned a value) and false otherwise */
  public boolean isSetIconClass() {
    return this.iconClass != null;
  }

  public void setIconClassIsSet(boolean value) {
    if (!value) {
      this.iconClass = null;
    }
  }

  public byte getHotCountry() {
    return this.hotCountry;
  }

  public DictCountryDO setHotCountry(byte hotCountry) {
    this.hotCountry = hotCountry;
    setHotCountryIsSet(true);
    return this;
  }

  public void unsetHotCountry() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __HOTCOUNTRY_ISSET_ID);
  }

  /** Returns true if field hotCountry is set (has been assigned a value) and false otherwise */
  public boolean isSetHotCountry() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __HOTCOUNTRY_ISSET_ID);
  }

  public void setHotCountryIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __HOTCOUNTRY_ISSET_ID, value);
  }

  public int getContinentCode() {
    return this.continentCode;
  }

  public DictCountryDO setContinentCode(int continentCode) {
    this.continentCode = continentCode;
    setContinentCodeIsSet(true);
    return this;
  }

  public void unsetContinentCode() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CONTINENTCODE_ISSET_ID);
  }

  /** Returns true if field continentCode is set (has been assigned a value) and false otherwise */
  public boolean isSetContinentCode() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CONTINENTCODE_ISSET_ID);
  }

  public void setContinentCodeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CONTINENTCODE_ISSET_ID, value);
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

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case ENAME:
      if (value == null) {
        unsetEname();
      } else {
        setEname((java.lang.String)value);
      }
      break;

    case ISO_CODE2:
      if (value == null) {
        unsetIsoCode2();
      } else {
        setIsoCode2((java.lang.String)value);
      }
      break;

    case ISO_CODE3:
      if (value == null) {
        unsetIsoCode3();
      } else {
        setIsoCode3((java.lang.String)value);
      }
      break;

    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((java.lang.String)value);
      }
      break;

    case ICON_CLASS:
      if (value == null) {
        unsetIconClass();
      } else {
        setIconClass((java.lang.String)value);
      }
      break;

    case HOT_COUNTRY:
      if (value == null) {
        unsetHotCountry();
      } else {
        setHotCountry((java.lang.Byte)value);
      }
      break;

    case CONTINENT_CODE:
      if (value == null) {
        unsetContinentCode();
      } else {
        setContinentCode((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case NAME:
      return getName();

    case ENAME:
      return getEname();

    case ISO_CODE2:
      return getIsoCode2();

    case ISO_CODE3:
      return getIsoCode3();

    case CODE:
      return getCode();

    case ICON_CLASS:
      return getIconClass();

    case HOT_COUNTRY:
      return getHotCountry();

    case CONTINENT_CODE:
      return getContinentCode();

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
    case NAME:
      return isSetName();
    case ENAME:
      return isSetEname();
    case ISO_CODE2:
      return isSetIsoCode2();
    case ISO_CODE3:
      return isSetIsoCode3();
    case CODE:
      return isSetCode();
    case ICON_CLASS:
      return isSetIconClass();
    case HOT_COUNTRY:
      return isSetHotCountry();
    case CONTINENT_CODE:
      return isSetContinentCode();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof DictCountryDO)
      return this.equals((DictCountryDO)that);
    return false;
  }

  public boolean equals(DictCountryDO that) {
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

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_ename = true && this.isSetEname();
    boolean that_present_ename = true && that.isSetEname();
    if (this_present_ename || that_present_ename) {
      if (!(this_present_ename && that_present_ename))
        return false;
      if (!this.ename.equals(that.ename))
        return false;
    }

    boolean this_present_isoCode2 = true && this.isSetIsoCode2();
    boolean that_present_isoCode2 = true && that.isSetIsoCode2();
    if (this_present_isoCode2 || that_present_isoCode2) {
      if (!(this_present_isoCode2 && that_present_isoCode2))
        return false;
      if (!this.isoCode2.equals(that.isoCode2))
        return false;
    }

    boolean this_present_isoCode3 = true && this.isSetIsoCode3();
    boolean that_present_isoCode3 = true && that.isSetIsoCode3();
    if (this_present_isoCode3 || that_present_isoCode3) {
      if (!(this_present_isoCode3 && that_present_isoCode3))
        return false;
      if (!this.isoCode3.equals(that.isoCode3))
        return false;
    }

    boolean this_present_code = true && this.isSetCode();
    boolean that_present_code = true && that.isSetCode();
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (!this.code.equals(that.code))
        return false;
    }

    boolean this_present_iconClass = true && this.isSetIconClass();
    boolean that_present_iconClass = true && that.isSetIconClass();
    if (this_present_iconClass || that_present_iconClass) {
      if (!(this_present_iconClass && that_present_iconClass))
        return false;
      if (!this.iconClass.equals(that.iconClass))
        return false;
    }

    boolean this_present_hotCountry = true && this.isSetHotCountry();
    boolean that_present_hotCountry = true && that.isSetHotCountry();
    if (this_present_hotCountry || that_present_hotCountry) {
      if (!(this_present_hotCountry && that_present_hotCountry))
        return false;
      if (this.hotCountry != that.hotCountry)
        return false;
    }

    boolean this_present_continentCode = true && this.isSetContinentCode();
    boolean that_present_continentCode = true && that.isSetContinentCode();
    if (this_present_continentCode || that_present_continentCode) {
      if (!(this_present_continentCode && that_present_continentCode))
        return false;
      if (this.continentCode != that.continentCode)
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

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetEname()) ? 131071 : 524287);
    if (isSetEname())
      hashCode = hashCode * 8191 + ename.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsoCode2()) ? 131071 : 524287);
    if (isSetIsoCode2())
      hashCode = hashCode * 8191 + isoCode2.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsoCode3()) ? 131071 : 524287);
    if (isSetIsoCode3())
      hashCode = hashCode * 8191 + isoCode3.hashCode();

    hashCode = hashCode * 8191 + ((isSetCode()) ? 131071 : 524287);
    if (isSetCode())
      hashCode = hashCode * 8191 + code.hashCode();

    hashCode = hashCode * 8191 + ((isSetIconClass()) ? 131071 : 524287);
    if (isSetIconClass())
      hashCode = hashCode * 8191 + iconClass.hashCode();

    hashCode = hashCode * 8191 + ((isSetHotCountry()) ? 131071 : 524287);
    if (isSetHotCountry())
      hashCode = hashCode * 8191 + (int) (hotCountry);

    hashCode = hashCode * 8191 + ((isSetContinentCode()) ? 131071 : 524287);
    if (isSetContinentCode())
      hashCode = hashCode * 8191 + continentCode;

    return hashCode;
  }

  @Override
  public int compareTo(DictCountryDO other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetEname()).compareTo(other.isSetEname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ename, other.ename);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsoCode2()).compareTo(other.isSetIsoCode2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsoCode2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isoCode2, other.isoCode2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsoCode3()).compareTo(other.isSetIsoCode3());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsoCode3()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isoCode3, other.isoCode3);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIconClass()).compareTo(other.isSetIconClass());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIconClass()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.iconClass, other.iconClass);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetHotCountry()).compareTo(other.isSetHotCountry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHotCountry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hotCountry, other.hotCountry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetContinentCode()).compareTo(other.isSetContinentCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContinentCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.continentCode, other.continentCode);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("DictCountryDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
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
    if (isSetEname()) {
      if (!first) sb.append(", ");
      sb.append("ename:");
      if (this.ename == null) {
        sb.append("null");
      } else {
        sb.append(this.ename);
      }
      first = false;
    }
    if (isSetIsoCode2()) {
      if (!first) sb.append(", ");
      sb.append("isoCode2:");
      if (this.isoCode2 == null) {
        sb.append("null");
      } else {
        sb.append(this.isoCode2);
      }
      first = false;
    }
    if (isSetIsoCode3()) {
      if (!first) sb.append(", ");
      sb.append("isoCode3:");
      if (this.isoCode3 == null) {
        sb.append("null");
      } else {
        sb.append(this.isoCode3);
      }
      first = false;
    }
    if (isSetCode()) {
      if (!first) sb.append(", ");
      sb.append("code:");
      if (this.code == null) {
        sb.append("null");
      } else {
        sb.append(this.code);
      }
      first = false;
    }
    if (isSetIconClass()) {
      if (!first) sb.append(", ");
      sb.append("iconClass:");
      if (this.iconClass == null) {
        sb.append("null");
      } else {
        sb.append(this.iconClass);
      }
      first = false;
    }
    if (isSetHotCountry()) {
      if (!first) sb.append(", ");
      sb.append("hotCountry:");
      sb.append(this.hotCountry);
      first = false;
    }
    if (isSetContinentCode()) {
      if (!first) sb.append(", ");
      sb.append("continentCode:");
      sb.append(this.continentCode);
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

  private static class DictCountryDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DictCountryDOStandardScheme getScheme() {
      return new DictCountryDOStandardScheme();
    }
  }

  private static class DictCountryDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<DictCountryDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DictCountryDO struct) throws org.apache.thrift.TException {
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
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ENAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ename = iprot.readString();
              struct.setEnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ISO_CODE2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.isoCode2 = iprot.readString();
              struct.setIsoCode2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ISO_CODE3
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.isoCode3 = iprot.readString();
              struct.setIsoCode3IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.code = iprot.readString();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ICON_CLASS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.iconClass = iprot.readString();
              struct.setIconClassIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // HOT_COUNTRY
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.hotCountry = iprot.readByte();
              struct.setHotCountryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // CONTINENT_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.continentCode = iprot.readI32();
              struct.setContinentCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DictCountryDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.ename != null) {
        if (struct.isSetEname()) {
          oprot.writeFieldBegin(ENAME_FIELD_DESC);
          oprot.writeString(struct.ename);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isoCode2 != null) {
        if (struct.isSetIsoCode2()) {
          oprot.writeFieldBegin(ISO_CODE2_FIELD_DESC);
          oprot.writeString(struct.isoCode2);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isoCode3 != null) {
        if (struct.isSetIsoCode3()) {
          oprot.writeFieldBegin(ISO_CODE3_FIELD_DESC);
          oprot.writeString(struct.isoCode3);
          oprot.writeFieldEnd();
        }
      }
      if (struct.code != null) {
        if (struct.isSetCode()) {
          oprot.writeFieldBegin(CODE_FIELD_DESC);
          oprot.writeString(struct.code);
          oprot.writeFieldEnd();
        }
      }
      if (struct.iconClass != null) {
        if (struct.isSetIconClass()) {
          oprot.writeFieldBegin(ICON_CLASS_FIELD_DESC);
          oprot.writeString(struct.iconClass);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetHotCountry()) {
        oprot.writeFieldBegin(HOT_COUNTRY_FIELD_DESC);
        oprot.writeByte(struct.hotCountry);
        oprot.writeFieldEnd();
      }
      if (struct.isSetContinentCode()) {
        oprot.writeFieldBegin(CONTINENT_CODE_FIELD_DESC);
        oprot.writeI32(struct.continentCode);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DictCountryDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DictCountryDOTupleScheme getScheme() {
      return new DictCountryDOTupleScheme();
    }
  }

  private static class DictCountryDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<DictCountryDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DictCountryDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetEname()) {
        optionals.set(2);
      }
      if (struct.isSetIsoCode2()) {
        optionals.set(3);
      }
      if (struct.isSetIsoCode3()) {
        optionals.set(4);
      }
      if (struct.isSetCode()) {
        optionals.set(5);
      }
      if (struct.isSetIconClass()) {
        optionals.set(6);
      }
      if (struct.isSetHotCountry()) {
        optionals.set(7);
      }
      if (struct.isSetContinentCode()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetEname()) {
        oprot.writeString(struct.ename);
      }
      if (struct.isSetIsoCode2()) {
        oprot.writeString(struct.isoCode2);
      }
      if (struct.isSetIsoCode3()) {
        oprot.writeString(struct.isoCode3);
      }
      if (struct.isSetCode()) {
        oprot.writeString(struct.code);
      }
      if (struct.isSetIconClass()) {
        oprot.writeString(struct.iconClass);
      }
      if (struct.isSetHotCountry()) {
        oprot.writeByte(struct.hotCountry);
      }
      if (struct.isSetContinentCode()) {
        oprot.writeI32(struct.continentCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DictCountryDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ename = iprot.readString();
        struct.setEnameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.isoCode2 = iprot.readString();
        struct.setIsoCode2IsSet(true);
      }
      if (incoming.get(4)) {
        struct.isoCode3 = iprot.readString();
        struct.setIsoCode3IsSet(true);
      }
      if (incoming.get(5)) {
        struct.code = iprot.readString();
        struct.setCodeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.iconClass = iprot.readString();
        struct.setIconClassIsSet(true);
      }
      if (incoming.get(7)) {
        struct.hotCountry = iprot.readByte();
        struct.setHotCountryIsSet(true);
      }
      if (incoming.get(8)) {
        struct.continentCode = iprot.readI32();
        struct.setContinentCodeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

