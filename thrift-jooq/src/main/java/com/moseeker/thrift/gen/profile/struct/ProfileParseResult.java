/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.profile.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-09-04")
public class ProfileParseResult implements org.apache.thrift.TBase<ProfileParseResult, ProfileParseResult._Fields>, java.io.Serializable, Cloneable, Comparable<ProfileParseResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProfileParseResult");

  private static final org.apache.thrift.protocol.TField FILE_FIELD_DESC = new org.apache.thrift.protocol.TField("file", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MOBILE_FIELD_DESC = new org.apache.thrift.protocol.TField("mobile", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ProfileParseResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ProfileParseResultTupleSchemeFactory();

  public java.lang.String file; // optional
  public java.lang.String name; // optional
  public java.lang.String mobile; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE((short)1, "file"),
    NAME((short)2, "name"),
    MOBILE((short)3, "mobile");

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
        case 1: // FILE
          return FILE;
        case 2: // NAME
          return NAME;
        case 3: // MOBILE
          return MOBILE;
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
  private static final _Fields optionals[] = {_Fields.FILE,_Fields.NAME,_Fields.MOBILE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE, new org.apache.thrift.meta_data.FieldMetaData("file", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MOBILE, new org.apache.thrift.meta_data.FieldMetaData("mobile", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProfileParseResult.class, metaDataMap);
  }

  public ProfileParseResult() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProfileParseResult(ProfileParseResult other) {
    if (other.isSetFile()) {
      this.file = other.file;
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetMobile()) {
      this.mobile = other.mobile;
    }
  }

  public ProfileParseResult deepCopy() {
    return new ProfileParseResult(this);
  }

  @Override
  public void clear() {
    this.file = null;
    this.name = null;
    this.mobile = null;
  }

  public java.lang.String getFile() {
    return this.file;
  }

  public ProfileParseResult setFile(java.lang.String file) {
    this.file = file;
    return this;
  }

  public void unsetFile() {
    this.file = null;
  }

  /** Returns true if field file is set (has been assigned a value) and false otherwise */
  public boolean isSetFile() {
    return this.file != null;
  }

  public void setFileIsSet(boolean value) {
    if (!value) {
      this.file = null;
    }
  }

  public java.lang.String getName() {
    return this.name;
  }

  public ProfileParseResult setName(java.lang.String name) {
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

  public java.lang.String getMobile() {
    return this.mobile;
  }

  public ProfileParseResult setMobile(java.lang.String mobile) {
    this.mobile = mobile;
    return this;
  }

  public void unsetMobile() {
    this.mobile = null;
  }

  /** Returns true if field mobile is set (has been assigned a value) and false otherwise */
  public boolean isSetMobile() {
    return this.mobile != null;
  }

  public void setMobileIsSet(boolean value) {
    if (!value) {
      this.mobile = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FILE:
      if (value == null) {
        unsetFile();
      } else {
        setFile((java.lang.String)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case MOBILE:
      if (value == null) {
        unsetMobile();
      } else {
        setMobile((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE:
      return getFile();

    case NAME:
      return getName();

    case MOBILE:
      return getMobile();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE:
      return isSetFile();
    case NAME:
      return isSetName();
    case MOBILE:
      return isSetMobile();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ProfileParseResult)
      return this.equals((ProfileParseResult)that);
    return false;
  }

  public boolean equals(ProfileParseResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_file = true && this.isSetFile();
    boolean that_present_file = true && that.isSetFile();
    if (this_present_file || that_present_file) {
      if (!(this_present_file && that_present_file))
        return false;
      if (!this.file.equals(that.file))
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

    boolean this_present_mobile = true && this.isSetMobile();
    boolean that_present_mobile = true && that.isSetMobile();
    if (this_present_mobile || that_present_mobile) {
      if (!(this_present_mobile && that_present_mobile))
        return false;
      if (!this.mobile.equals(that.mobile))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFile()) ? 131071 : 524287);
    if (isSetFile())
      hashCode = hashCode * 8191 + file.hashCode();

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetMobile()) ? 131071 : 524287);
    if (isSetMobile())
      hashCode = hashCode * 8191 + mobile.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ProfileParseResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFile()).compareTo(other.isSetFile());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFile()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.file, other.file);
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
    lastComparison = java.lang.Boolean.valueOf(isSetMobile()).compareTo(other.isSetMobile());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMobile()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mobile, other.mobile);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ProfileParseResult(");
    boolean first = true;

    if (isSetFile()) {
      sb.append("file:");
      if (this.file == null) {
        sb.append("null");
      } else {
        sb.append(this.file);
      }
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
    if (isSetMobile()) {
      if (!first) sb.append(", ");
      sb.append("mobile:");
      if (this.mobile == null) {
        sb.append("null");
      } else {
        sb.append(this.mobile);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ProfileParseResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ProfileParseResultStandardScheme getScheme() {
      return new ProfileParseResultStandardScheme();
    }
  }

  private static class ProfileParseResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<ProfileParseResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProfileParseResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.file = iprot.readString();
              struct.setFileIsSet(true);
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
          case 3: // MOBILE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.mobile = iprot.readString();
              struct.setMobileIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProfileParseResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.file != null) {
        if (struct.isSetFile()) {
          oprot.writeFieldBegin(FILE_FIELD_DESC);
          oprot.writeString(struct.file);
          oprot.writeFieldEnd();
        }
      }
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.mobile != null) {
        if (struct.isSetMobile()) {
          oprot.writeFieldBegin(MOBILE_FIELD_DESC);
          oprot.writeString(struct.mobile);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProfileParseResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ProfileParseResultTupleScheme getScheme() {
      return new ProfileParseResultTupleScheme();
    }
  }

  private static class ProfileParseResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<ProfileParseResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProfileParseResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFile()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetMobile()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetFile()) {
        oprot.writeString(struct.file);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetMobile()) {
        oprot.writeString(struct.mobile);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProfileParseResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.file = iprot.readString();
        struct.setFileIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.mobile = iprot.readString();
        struct.setMobileIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
