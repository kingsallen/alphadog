/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.position.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-26")
public class RpExtInfo implements org.apache.thrift.TBase<RpExtInfo, RpExtInfo._Fields>, java.io.Serializable, Cloneable, Comparable<RpExtInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RpExtInfo");

  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField REMAIN_FIELD_DESC = new org.apache.thrift.protocol.TField("remain", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField EMPLOYEE_ONLY_FIELD_DESC = new org.apache.thrift.protocol.TField("employee_only", org.apache.thrift.protocol.TType.BOOL, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RpExtInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RpExtInfoTupleSchemeFactory();

  public int pid; // required
  public int remain; // required
  public boolean employee_only; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PID((short)1, "pid"),
    REMAIN((short)2, "remain"),
    EMPLOYEE_ONLY((short)3, "employee_only");

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
        case 1: // PID
          return PID;
        case 2: // REMAIN
          return REMAIN;
        case 3: // EMPLOYEE_ONLY
          return EMPLOYEE_ONLY;
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
  private static final int __PID_ISSET_ID = 0;
  private static final int __REMAIN_ISSET_ID = 1;
  private static final int __EMPLOYEE_ONLY_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REMAIN, new org.apache.thrift.meta_data.FieldMetaData("remain", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.EMPLOYEE_ONLY, new org.apache.thrift.meta_data.FieldMetaData("employee_only", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RpExtInfo.class, metaDataMap);
  }

  public RpExtInfo() {
  }

  public RpExtInfo(
    int pid,
    int remain,
    boolean employee_only)
  {
    this();
    this.pid = pid;
    setPidIsSet(true);
    this.remain = remain;
    setRemainIsSet(true);
    this.employee_only = employee_only;
    setEmployee_onlyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RpExtInfo(RpExtInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pid = other.pid;
    this.remain = other.remain;
    this.employee_only = other.employee_only;
  }

  public RpExtInfo deepCopy() {
    return new RpExtInfo(this);
  }

  @Override
  public void clear() {
    setPidIsSet(false);
    this.pid = 0;
    setRemainIsSet(false);
    this.remain = 0;
    setEmployee_onlyIsSet(false);
    this.employee_only = false;
  }

  public int getPid() {
    return this.pid;
  }

  public RpExtInfo setPid(int pid) {
    this.pid = pid;
    setPidIsSet(true);
    return this;
  }

  public void unsetPid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PID_ISSET_ID);
  }

  /** Returns true if field pid is set (has been assigned a value) and false otherwise */
  public boolean isSetPid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PID_ISSET_ID);
  }

  public void setPidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PID_ISSET_ID, value);
  }

  public int getRemain() {
    return this.remain;
  }

  public RpExtInfo setRemain(int remain) {
    this.remain = remain;
    setRemainIsSet(true);
    return this;
  }

  public void unsetRemain() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __REMAIN_ISSET_ID);
  }

  /** Returns true if field remain is set (has been assigned a value) and false otherwise */
  public boolean isSetRemain() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __REMAIN_ISSET_ID);
  }

  public void setRemainIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __REMAIN_ISSET_ID, value);
  }

  public boolean isEmployee_only() {
    return this.employee_only;
  }

  public RpExtInfo setEmployee_only(boolean employee_only) {
    this.employee_only = employee_only;
    setEmployee_onlyIsSet(true);
    return this;
  }

  public void unsetEmployee_only() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __EMPLOYEE_ONLY_ISSET_ID);
  }

  /** Returns true if field employee_only is set (has been assigned a value) and false otherwise */
  public boolean isSetEmployee_only() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __EMPLOYEE_ONLY_ISSET_ID);
  }

  public void setEmployee_onlyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __EMPLOYEE_ONLY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((java.lang.Integer)value);
      }
      break;

    case REMAIN:
      if (value == null) {
        unsetRemain();
      } else {
        setRemain((java.lang.Integer)value);
      }
      break;

    case EMPLOYEE_ONLY:
      if (value == null) {
        unsetEmployee_only();
      } else {
        setEmployee_only((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PID:
      return getPid();

    case REMAIN:
      return getRemain();

    case EMPLOYEE_ONLY:
      return isEmployee_only();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PID:
      return isSetPid();
    case REMAIN:
      return isSetRemain();
    case EMPLOYEE_ONLY:
      return isSetEmployee_only();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof RpExtInfo)
      return this.equals((RpExtInfo)that);
    return false;
  }

  public boolean equals(RpExtInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pid = true;
    boolean that_present_pid = true;
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (this.pid != that.pid)
        return false;
    }

    boolean this_present_remain = true;
    boolean that_present_remain = true;
    if (this_present_remain || that_present_remain) {
      if (!(this_present_remain && that_present_remain))
        return false;
      if (this.remain != that.remain)
        return false;
    }

    boolean this_present_employee_only = true;
    boolean that_present_employee_only = true;
    if (this_present_employee_only || that_present_employee_only) {
      if (!(this_present_employee_only && that_present_employee_only))
        return false;
      if (this.employee_only != that.employee_only)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + pid;

    hashCode = hashCode * 8191 + remain;

    hashCode = hashCode * 8191 + ((employee_only) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(RpExtInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPid()).compareTo(other.isSetPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pid, other.pid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRemain()).compareTo(other.isSetRemain());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRemain()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.remain, other.remain);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEmployee_only()).compareTo(other.isSetEmployee_only());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmployee_only()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.employee_only, other.employee_only);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("RpExtInfo(");
    boolean first = true;

    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("remain:");
    sb.append(this.remain);
    first = false;
    if (!first) sb.append(", ");
    sb.append("employee_only:");
    sb.append(this.employee_only);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'pid' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'remain' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'employee_only' because it's a primitive and you chose the non-beans generator.
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

  private static class RpExtInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RpExtInfoStandardScheme getScheme() {
      return new RpExtInfoStandardScheme();
    }
  }

  private static class RpExtInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<RpExtInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RpExtInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pid = iprot.readI32();
              struct.setPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REMAIN
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.remain = iprot.readI32();
              struct.setRemainIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EMPLOYEE_ONLY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.employee_only = iprot.readBool();
              struct.setEmployee_onlyIsSet(true);
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
      if (!struct.isSetPid()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'pid' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetRemain()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'remain' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetEmployee_only()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'employee_only' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RpExtInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PID_FIELD_DESC);
      oprot.writeI32(struct.pid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REMAIN_FIELD_DESC);
      oprot.writeI32(struct.remain);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(EMPLOYEE_ONLY_FIELD_DESC);
      oprot.writeBool(struct.employee_only);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RpExtInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RpExtInfoTupleScheme getScheme() {
      return new RpExtInfoTupleScheme();
    }
  }

  private static class RpExtInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<RpExtInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RpExtInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.pid);
      oprot.writeI32(struct.remain);
      oprot.writeBool(struct.employee_only);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RpExtInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.pid = iprot.readI32();
      struct.setPidIsSet(true);
      struct.remain = iprot.readI32();
      struct.setRemainIsSet(true);
      struct.employee_only = iprot.readBool();
      struct.setEmployee_onlyIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

