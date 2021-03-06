/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.profile.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-05-05")
public class IntentionCity implements org.apache.thrift.TBase<IntentionCity, IntentionCity._Fields>, java.io.Serializable, Cloneable, Comparable<IntentionCity> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IntentionCity");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROFILE_INTENTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("profile_intention_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CITY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("city_code", org.apache.thrift.protocol.TType.I16, (short)3);
  private static final org.apache.thrift.protocol.TField CITY_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("city_name", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new IntentionCityStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new IntentionCityTupleSchemeFactory();

  public int id; // optional
  public int profile_intention_id; // optional
  public short city_code; // optional
  public java.lang.String city_name; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PROFILE_INTENTION_ID((short)2, "profile_intention_id"),
    CITY_CODE((short)3, "city_code"),
    CITY_NAME((short)4, "city_name");

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
        case 2: // PROFILE_INTENTION_ID
          return PROFILE_INTENTION_ID;
        case 3: // CITY_CODE
          return CITY_CODE;
        case 4: // CITY_NAME
          return CITY_NAME;
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
  private static final int __PROFILE_INTENTION_ID_ISSET_ID = 1;
  private static final int __CITY_CODE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.PROFILE_INTENTION_ID,_Fields.CITY_CODE,_Fields.CITY_NAME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROFILE_INTENTION_ID, new org.apache.thrift.meta_data.FieldMetaData("profile_intention_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CITY_CODE, new org.apache.thrift.meta_data.FieldMetaData("city_code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.CITY_NAME, new org.apache.thrift.meta_data.FieldMetaData("city_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IntentionCity.class, metaDataMap);
  }

  public IntentionCity() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IntentionCity(IntentionCity other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.profile_intention_id = other.profile_intention_id;
    this.city_code = other.city_code;
    if (other.isSetCity_name()) {
      this.city_name = other.city_name;
    }
  }

  public IntentionCity deepCopy() {
    return new IntentionCity(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setProfile_intention_idIsSet(false);
    this.profile_intention_id = 0;
    setCity_codeIsSet(false);
    this.city_code = 0;
    this.city_name = null;
  }

  public int getId() {
    return this.id;
  }

  public IntentionCity setId(int id) {
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

  public int getProfile_intention_id() {
    return this.profile_intention_id;
  }

  public IntentionCity setProfile_intention_id(int profile_intention_id) {
    this.profile_intention_id = profile_intention_id;
    setProfile_intention_idIsSet(true);
    return this;
  }

  public void unsetProfile_intention_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PROFILE_INTENTION_ID_ISSET_ID);
  }

  /** Returns true if field profile_intention_id is set (has been assigned a value) and false otherwise */
  public boolean isSetProfile_intention_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PROFILE_INTENTION_ID_ISSET_ID);
  }

  public void setProfile_intention_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PROFILE_INTENTION_ID_ISSET_ID, value);
  }

  public short getCity_code() {
    return this.city_code;
  }

  public IntentionCity setCity_code(short city_code) {
    this.city_code = city_code;
    setCity_codeIsSet(true);
    return this;
  }

  public void unsetCity_code() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CITY_CODE_ISSET_ID);
  }

  /** Returns true if field city_code is set (has been assigned a value) and false otherwise */
  public boolean isSetCity_code() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CITY_CODE_ISSET_ID);
  }

  public void setCity_codeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CITY_CODE_ISSET_ID, value);
  }

  public java.lang.String getCity_name() {
    return this.city_name;
  }

  public IntentionCity setCity_name(java.lang.String city_name) {
    this.city_name = city_name;
    return this;
  }

  public void unsetCity_name() {
    this.city_name = null;
  }

  /** Returns true if field city_name is set (has been assigned a value) and false otherwise */
  public boolean isSetCity_name() {
    return this.city_name != null;
  }

  public void setCity_nameIsSet(boolean value) {
    if (!value) {
      this.city_name = null;
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

    case PROFILE_INTENTION_ID:
      if (value == null) {
        unsetProfile_intention_id();
      } else {
        setProfile_intention_id((java.lang.Integer)value);
      }
      break;

    case CITY_CODE:
      if (value == null) {
        unsetCity_code();
      } else {
        setCity_code((java.lang.Short)value);
      }
      break;

    case CITY_NAME:
      if (value == null) {
        unsetCity_name();
      } else {
        setCity_name((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case PROFILE_INTENTION_ID:
      return getProfile_intention_id();

    case CITY_CODE:
      return getCity_code();

    case CITY_NAME:
      return getCity_name();

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
    case PROFILE_INTENTION_ID:
      return isSetProfile_intention_id();
    case CITY_CODE:
      return isSetCity_code();
    case CITY_NAME:
      return isSetCity_name();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof IntentionCity)
      return this.equals((IntentionCity)that);
    return false;
  }

  public boolean equals(IntentionCity that) {
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

    boolean this_present_profile_intention_id = true && this.isSetProfile_intention_id();
    boolean that_present_profile_intention_id = true && that.isSetProfile_intention_id();
    if (this_present_profile_intention_id || that_present_profile_intention_id) {
      if (!(this_present_profile_intention_id && that_present_profile_intention_id))
        return false;
      if (this.profile_intention_id != that.profile_intention_id)
        return false;
    }

    boolean this_present_city_code = true && this.isSetCity_code();
    boolean that_present_city_code = true && that.isSetCity_code();
    if (this_present_city_code || that_present_city_code) {
      if (!(this_present_city_code && that_present_city_code))
        return false;
      if (this.city_code != that.city_code)
        return false;
    }

    boolean this_present_city_name = true && this.isSetCity_name();
    boolean that_present_city_name = true && that.isSetCity_name();
    if (this_present_city_name || that_present_city_name) {
      if (!(this_present_city_name && that_present_city_name))
        return false;
      if (!this.city_name.equals(that.city_name))
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

    hashCode = hashCode * 8191 + ((isSetProfile_intention_id()) ? 131071 : 524287);
    if (isSetProfile_intention_id())
      hashCode = hashCode * 8191 + profile_intention_id;

    hashCode = hashCode * 8191 + ((isSetCity_code()) ? 131071 : 524287);
    if (isSetCity_code())
      hashCode = hashCode * 8191 + city_code;

    hashCode = hashCode * 8191 + ((isSetCity_name()) ? 131071 : 524287);
    if (isSetCity_name())
      hashCode = hashCode * 8191 + city_name.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(IntentionCity other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetProfile_intention_id()).compareTo(other.isSetProfile_intention_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProfile_intention_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.profile_intention_id, other.profile_intention_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCity_code()).compareTo(other.isSetCity_code());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCity_code()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.city_code, other.city_code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCity_name()).compareTo(other.isSetCity_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCity_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.city_name, other.city_name);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("IntentionCity(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetProfile_intention_id()) {
      if (!first) sb.append(", ");
      sb.append("profile_intention_id:");
      sb.append(this.profile_intention_id);
      first = false;
    }
    if (isSetCity_code()) {
      if (!first) sb.append(", ");
      sb.append("city_code:");
      sb.append(this.city_code);
      first = false;
    }
    if (isSetCity_name()) {
      if (!first) sb.append(", ");
      sb.append("city_name:");
      if (this.city_name == null) {
        sb.append("null");
      } else {
        sb.append(this.city_name);
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

  private static class IntentionCityStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IntentionCityStandardScheme getScheme() {
      return new IntentionCityStandardScheme();
    }
  }

  private static class IntentionCityStandardScheme extends org.apache.thrift.scheme.StandardScheme<IntentionCity> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IntentionCity struct) throws org.apache.thrift.TException {
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
          case 2: // PROFILE_INTENTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.profile_intention_id = iprot.readI32();
              struct.setProfile_intention_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CITY_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.city_code = iprot.readI16();
              struct.setCity_codeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CITY_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.city_name = iprot.readString();
              struct.setCity_nameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, IntentionCity struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProfile_intention_id()) {
        oprot.writeFieldBegin(PROFILE_INTENTION_ID_FIELD_DESC);
        oprot.writeI32(struct.profile_intention_id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCity_code()) {
        oprot.writeFieldBegin(CITY_CODE_FIELD_DESC);
        oprot.writeI16(struct.city_code);
        oprot.writeFieldEnd();
      }
      if (struct.city_name != null) {
        if (struct.isSetCity_name()) {
          oprot.writeFieldBegin(CITY_NAME_FIELD_DESC);
          oprot.writeString(struct.city_name);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IntentionCityTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IntentionCityTupleScheme getScheme() {
      return new IntentionCityTupleScheme();
    }
  }

  private static class IntentionCityTupleScheme extends org.apache.thrift.scheme.TupleScheme<IntentionCity> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IntentionCity struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetProfile_intention_id()) {
        optionals.set(1);
      }
      if (struct.isSetCity_code()) {
        optionals.set(2);
      }
      if (struct.isSetCity_name()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetProfile_intention_id()) {
        oprot.writeI32(struct.profile_intention_id);
      }
      if (struct.isSetCity_code()) {
        oprot.writeI16(struct.city_code);
      }
      if (struct.isSetCity_name()) {
        oprot.writeString(struct.city_name);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IntentionCity struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.profile_intention_id = iprot.readI32();
        struct.setProfile_intention_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.city_code = iprot.readI16();
        struct.setCity_codeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.city_name = iprot.readString();
        struct.setCity_nameIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

