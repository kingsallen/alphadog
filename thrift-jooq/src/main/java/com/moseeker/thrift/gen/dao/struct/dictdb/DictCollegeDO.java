/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.dictdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-04")
public class DictCollegeDO implements org.apache.thrift.TBase<DictCollegeDO, DictCollegeDO._Fields>, java.io.Serializable, Cloneable, Comparable<DictCollegeDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DictCollegeDO");

  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PROVINCE_FIELD_DESC = new org.apache.thrift.protocol.TField("province", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField LOGO_FIELD_DESC = new org.apache.thrift.protocol.TField("logo", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DictCollegeDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DictCollegeDOTupleSchemeFactory();

  public int code; // optional
  public String name; // optional
  public int province; // optional
  public String logo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CODE((short)1, "code"),
    NAME((short)2, "name"),
    PROVINCE((short)3, "province"),
    LOGO((short)4, "logo");

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
        case 1: // CODE
          return CODE;
        case 2: // NAME
          return NAME;
        case 3: // PROVINCE
          return PROVINCE;
        case 4: // LOGO
          return LOGO;
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
  private static final int __CODE_ISSET_ID = 0;
  private static final int __PROVINCE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.CODE,_Fields.NAME,_Fields.PROVINCE,_Fields.LOGO};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROVINCE, new org.apache.thrift.meta_data.FieldMetaData("province", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOGO, new org.apache.thrift.meta_data.FieldMetaData("logo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DictCollegeDO.class, metaDataMap);
  }

  public DictCollegeDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DictCollegeDO(DictCollegeDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.code = other.code;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.province = other.province;
    if (other.isSetLogo()) {
      this.logo = other.logo;
    }
  }

  public DictCollegeDO deepCopy() {
    return new DictCollegeDO(this);
  }

  @Override
  public void clear() {
    setCodeIsSet(false);
    this.code = 0;
    this.name = null;
    setProvinceIsSet(false);
    this.province = 0;
    this.logo = null;
  }

  public int getCode() {
    return this.code;
  }

  public DictCollegeDO setCode(int code) {
    this.code = code;
    setCodeIsSet(true);
    return this;
  }

  public void unsetCode() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  public void setCodeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CODE_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public DictCollegeDO setName(String name) {
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

  public int getProvince() {
    return this.province;
  }

  public DictCollegeDO setProvince(int province) {
    this.province = province;
    setProvinceIsSet(true);
    return this;
  }

  public void unsetProvince() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PROVINCE_ISSET_ID);
  }

  /** Returns true if field province is set (has been assigned a value) and false otherwise */
  public boolean isSetProvince() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PROVINCE_ISSET_ID);
  }

  public void setProvinceIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PROVINCE_ISSET_ID, value);
  }

  public String getLogo() {
    return this.logo;
  }

  public DictCollegeDO setLogo(String logo) {
    this.logo = logo;
    return this;
  }

  public void unsetLogo() {
    this.logo = null;
  }

  /** Returns true if field logo is set (has been assigned a value) and false otherwise */
  public boolean isSetLogo() {
    return this.logo != null;
  }

  public void setLogoIsSet(boolean value) {
    if (!value) {
      this.logo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case PROVINCE:
      if (value == null) {
        unsetProvince();
      } else {
        setProvince((Integer)value);
      }
      break;

    case LOGO:
      if (value == null) {
        unsetLogo();
      } else {
        setLogo((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CODE:
      return getCode();

    case NAME:
      return getName();

    case PROVINCE:
      return getProvince();

    case LOGO:
      return getLogo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CODE:
      return isSetCode();
    case NAME:
      return isSetName();
    case PROVINCE:
      return isSetProvince();
    case LOGO:
      return isSetLogo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DictCollegeDO)
      return this.equals((DictCollegeDO)that);
    return false;
  }

  public boolean equals(DictCollegeDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_code = true && this.isSetCode();
    boolean that_present_code = true && that.isSetCode();
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (this.code != that.code)
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

    boolean this_present_province = true && this.isSetProvince();
    boolean that_present_province = true && that.isSetProvince();
    if (this_present_province || that_present_province) {
      if (!(this_present_province && that_present_province))
        return false;
      if (this.province != that.province)
        return false;
    }

    boolean this_present_logo = true && this.isSetLogo();
    boolean that_present_logo = true && that.isSetLogo();
    if (this_present_logo || that_present_logo) {
      if (!(this_present_logo && that_present_logo))
        return false;
      if (!this.logo.equals(that.logo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCode()) ? 131071 : 524287);
    if (isSetCode())
      hashCode = hashCode * 8191 + code;

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetProvince()) ? 131071 : 524287);
    if (isSetProvince())
      hashCode = hashCode * 8191 + province;

    hashCode = hashCode * 8191 + ((isSetLogo()) ? 131071 : 524287);
    if (isSetLogo())
      hashCode = hashCode * 8191 + logo.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(DictCollegeDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProvince()).compareTo(other.isSetProvince());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProvince()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.province, other.province);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLogo()).compareTo(other.isSetLogo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLogo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.logo, other.logo);
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
    StringBuilder sb = new StringBuilder("DictCollegeDO(");
    boolean first = true;

    if (isSetCode()) {
      sb.append("code:");
      sb.append(this.code);
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
    if (isSetProvince()) {
      if (!first) sb.append(", ");
      sb.append("province:");
      sb.append(this.province);
      first = false;
    }
    if (isSetLogo()) {
      if (!first) sb.append(", ");
      sb.append("logo:");
      if (this.logo == null) {
        sb.append("null");
      } else {
        sb.append(this.logo);
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

  private static class DictCollegeDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DictCollegeDOStandardScheme getScheme() {
      return new DictCollegeDOStandardScheme();
    }
  }

  private static class DictCollegeDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<DictCollegeDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DictCollegeDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.code = iprot.readI32();
              struct.setCodeIsSet(true);
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
          case 3: // PROVINCE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.province = iprot.readI32();
              struct.setProvinceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LOGO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.logo = iprot.readString();
              struct.setLogoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DictCollegeDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetCode()) {
        oprot.writeFieldBegin(CODE_FIELD_DESC);
        oprot.writeI32(struct.code);
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetProvince()) {
        oprot.writeFieldBegin(PROVINCE_FIELD_DESC);
        oprot.writeI32(struct.province);
        oprot.writeFieldEnd();
      }
      if (struct.logo != null) {
        if (struct.isSetLogo()) {
          oprot.writeFieldBegin(LOGO_FIELD_DESC);
          oprot.writeString(struct.logo);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DictCollegeDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DictCollegeDOTupleScheme getScheme() {
      return new DictCollegeDOTupleScheme();
    }
  }

  private static class DictCollegeDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<DictCollegeDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DictCollegeDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCode()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetProvince()) {
        optionals.set(2);
      }
      if (struct.isSetLogo()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetCode()) {
        oprot.writeI32(struct.code);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetProvince()) {
        oprot.writeI32(struct.province);
      }
      if (struct.isSetLogo()) {
        oprot.writeString(struct.logo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DictCollegeDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.code = iprot.readI32();
        struct.setCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.province = iprot.readI32();
        struct.setProvinceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.logo = iprot.readString();
        struct.setLogoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

