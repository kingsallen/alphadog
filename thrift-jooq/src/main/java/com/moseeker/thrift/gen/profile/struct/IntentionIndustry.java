/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.profile.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-05-05")
public class IntentionIndustry implements org.apache.thrift.TBase<IntentionIndustry, IntentionIndustry._Fields>, java.io.Serializable, Cloneable, Comparable<IntentionIndustry> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IntentionIndustry");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROFILE_INTENTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("profile_intention_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField INDUSTRY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("industry_code", org.apache.thrift.protocol.TType.I16, (short)3);
  private static final org.apache.thrift.protocol.TField INDUSTRY_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("industry_name", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new IntentionIndustryStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new IntentionIndustryTupleSchemeFactory();

  public int id; // optional
  public int profile_intention_id; // optional
  public short industry_code; // optional
  public java.lang.String industry_name; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PROFILE_INTENTION_ID((short)2, "profile_intention_id"),
    INDUSTRY_CODE((short)3, "industry_code"),
    INDUSTRY_NAME((short)4, "industry_name");

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
        case 3: // INDUSTRY_CODE
          return INDUSTRY_CODE;
        case 4: // INDUSTRY_NAME
          return INDUSTRY_NAME;
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
  private static final int __INDUSTRY_CODE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.PROFILE_INTENTION_ID,_Fields.INDUSTRY_CODE,_Fields.INDUSTRY_NAME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROFILE_INTENTION_ID, new org.apache.thrift.meta_data.FieldMetaData("profile_intention_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.INDUSTRY_CODE, new org.apache.thrift.meta_data.FieldMetaData("industry_code", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.INDUSTRY_NAME, new org.apache.thrift.meta_data.FieldMetaData("industry_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IntentionIndustry.class, metaDataMap);
  }

  public IntentionIndustry() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IntentionIndustry(IntentionIndustry other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.profile_intention_id = other.profile_intention_id;
    this.industry_code = other.industry_code;
    if (other.isSetIndustry_name()) {
      this.industry_name = other.industry_name;
    }
  }

  public IntentionIndustry deepCopy() {
    return new IntentionIndustry(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setProfile_intention_idIsSet(false);
    this.profile_intention_id = 0;
    setIndustry_codeIsSet(false);
    this.industry_code = 0;
    this.industry_name = null;
  }

  public int getId() {
    return this.id;
  }

  public IntentionIndustry setId(int id) {
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

  public IntentionIndustry setProfile_intention_id(int profile_intention_id) {
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

  public short getIndustry_code() {
    return this.industry_code;
  }

  public IntentionIndustry setIndustry_code(short industry_code) {
    this.industry_code = industry_code;
    setIndustry_codeIsSet(true);
    return this;
  }

  public void unsetIndustry_code() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INDUSTRY_CODE_ISSET_ID);
  }

  /** Returns true if field industry_code is set (has been assigned a value) and false otherwise */
  public boolean isSetIndustry_code() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INDUSTRY_CODE_ISSET_ID);
  }

  public void setIndustry_codeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INDUSTRY_CODE_ISSET_ID, value);
  }

  public java.lang.String getIndustry_name() {
    return this.industry_name;
  }

  public IntentionIndustry setIndustry_name(java.lang.String industry_name) {
    this.industry_name = industry_name;
    return this;
  }

  public void unsetIndustry_name() {
    this.industry_name = null;
  }

  /** Returns true if field industry_name is set (has been assigned a value) and false otherwise */
  public boolean isSetIndustry_name() {
    return this.industry_name != null;
  }

  public void setIndustry_nameIsSet(boolean value) {
    if (!value) {
      this.industry_name = null;
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

    case INDUSTRY_CODE:
      if (value == null) {
        unsetIndustry_code();
      } else {
        setIndustry_code((java.lang.Short)value);
      }
      break;

    case INDUSTRY_NAME:
      if (value == null) {
        unsetIndustry_name();
      } else {
        setIndustry_name((java.lang.String)value);
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

    case INDUSTRY_CODE:
      return getIndustry_code();

    case INDUSTRY_NAME:
      return getIndustry_name();

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
    case INDUSTRY_CODE:
      return isSetIndustry_code();
    case INDUSTRY_NAME:
      return isSetIndustry_name();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof IntentionIndustry)
      return this.equals((IntentionIndustry)that);
    return false;
  }

  public boolean equals(IntentionIndustry that) {
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

    boolean this_present_industry_code = true && this.isSetIndustry_code();
    boolean that_present_industry_code = true && that.isSetIndustry_code();
    if (this_present_industry_code || that_present_industry_code) {
      if (!(this_present_industry_code && that_present_industry_code))
        return false;
      if (this.industry_code != that.industry_code)
        return false;
    }

    boolean this_present_industry_name = true && this.isSetIndustry_name();
    boolean that_present_industry_name = true && that.isSetIndustry_name();
    if (this_present_industry_name || that_present_industry_name) {
      if (!(this_present_industry_name && that_present_industry_name))
        return false;
      if (!this.industry_name.equals(that.industry_name))
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

    hashCode = hashCode * 8191 + ((isSetIndustry_code()) ? 131071 : 524287);
    if (isSetIndustry_code())
      hashCode = hashCode * 8191 + industry_code;

    hashCode = hashCode * 8191 + ((isSetIndustry_name()) ? 131071 : 524287);
    if (isSetIndustry_name())
      hashCode = hashCode * 8191 + industry_name.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(IntentionIndustry other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetIndustry_code()).compareTo(other.isSetIndustry_code());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIndustry_code()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.industry_code, other.industry_code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIndustry_name()).compareTo(other.isSetIndustry_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIndustry_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.industry_name, other.industry_name);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("IntentionIndustry(");
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
    if (isSetIndustry_code()) {
      if (!first) sb.append(", ");
      sb.append("industry_code:");
      sb.append(this.industry_code);
      first = false;
    }
    if (isSetIndustry_name()) {
      if (!first) sb.append(", ");
      sb.append("industry_name:");
      if (this.industry_name == null) {
        sb.append("null");
      } else {
        sb.append(this.industry_name);
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

  private static class IntentionIndustryStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IntentionIndustryStandardScheme getScheme() {
      return new IntentionIndustryStandardScheme();
    }
  }

  private static class IntentionIndustryStandardScheme extends org.apache.thrift.scheme.StandardScheme<IntentionIndustry> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IntentionIndustry struct) throws org.apache.thrift.TException {
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
          case 3: // INDUSTRY_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.industry_code = iprot.readI16();
              struct.setIndustry_codeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INDUSTRY_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.industry_name = iprot.readString();
              struct.setIndustry_nameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, IntentionIndustry struct) throws org.apache.thrift.TException {
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
      if (struct.isSetIndustry_code()) {
        oprot.writeFieldBegin(INDUSTRY_CODE_FIELD_DESC);
        oprot.writeI16(struct.industry_code);
        oprot.writeFieldEnd();
      }
      if (struct.industry_name != null) {
        if (struct.isSetIndustry_name()) {
          oprot.writeFieldBegin(INDUSTRY_NAME_FIELD_DESC);
          oprot.writeString(struct.industry_name);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IntentionIndustryTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IntentionIndustryTupleScheme getScheme() {
      return new IntentionIndustryTupleScheme();
    }
  }

  private static class IntentionIndustryTupleScheme extends org.apache.thrift.scheme.TupleScheme<IntentionIndustry> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IntentionIndustry struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetProfile_intention_id()) {
        optionals.set(1);
      }
      if (struct.isSetIndustry_code()) {
        optionals.set(2);
      }
      if (struct.isSetIndustry_name()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetProfile_intention_id()) {
        oprot.writeI32(struct.profile_intention_id);
      }
      if (struct.isSetIndustry_code()) {
        oprot.writeI16(struct.industry_code);
      }
      if (struct.isSetIndustry_name()) {
        oprot.writeString(struct.industry_name);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IntentionIndustry struct) throws org.apache.thrift.TException {
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
        struct.industry_code = iprot.readI16();
        struct.setIndustry_codeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.industry_name = iprot.readString();
        struct.setIndustry_nameIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

