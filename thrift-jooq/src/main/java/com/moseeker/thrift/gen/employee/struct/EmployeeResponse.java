/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.employee.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-16")
public class EmployeeResponse implements org.apache.thrift.TBase<EmployeeResponse, EmployeeResponse._Fields>, java.io.Serializable, Cloneable, Comparable<EmployeeResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EmployeeResponse");

  private static final org.apache.thrift.protocol.TField BIND_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("bindStatus", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField EMPLOYEE_FIELD_DESC = new org.apache.thrift.protocol.TField("employee", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new EmployeeResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new EmployeeResponseTupleSchemeFactory();

  /**
   * 
   * @see BindStatus
   */
  public BindStatus bindStatus; // required
  public Employee employee; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see BindStatus
     */
    BIND_STATUS((short)1, "bindStatus"),
    EMPLOYEE((short)2, "employee");

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
        case 1: // BIND_STATUS
          return BIND_STATUS;
        case 2: // EMPLOYEE
          return EMPLOYEE;
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
  private static final _Fields optionals[] = {_Fields.EMPLOYEE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BIND_STATUS, new org.apache.thrift.meta_data.FieldMetaData("bindStatus", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, BindStatus.class)));
    tmpMap.put(_Fields.EMPLOYEE, new org.apache.thrift.meta_data.FieldMetaData("employee", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Employee.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EmployeeResponse.class, metaDataMap);
  }

  public EmployeeResponse() {
  }

  public EmployeeResponse(
    BindStatus bindStatus)
  {
    this();
    this.bindStatus = bindStatus;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EmployeeResponse(EmployeeResponse other) {
    if (other.isSetBindStatus()) {
      this.bindStatus = other.bindStatus;
    }
    if (other.isSetEmployee()) {
      this.employee = new Employee(other.employee);
    }
  }

  public EmployeeResponse deepCopy() {
    return new EmployeeResponse(this);
  }

  @Override
  public void clear() {
    this.bindStatus = null;
    this.employee = null;
  }

  /**
   * 
   * @see BindStatus
   */
  public BindStatus getBindStatus() {
    return this.bindStatus;
  }

  /**
   * 
   * @see BindStatus
   */
  public EmployeeResponse setBindStatus(BindStatus bindStatus) {
    this.bindStatus = bindStatus;
    return this;
  }

  public void unsetBindStatus() {
    this.bindStatus = null;
  }

  /** Returns true if field bindStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetBindStatus() {
    return this.bindStatus != null;
  }

  public void setBindStatusIsSet(boolean value) {
    if (!value) {
      this.bindStatus = null;
    }
  }

  public Employee getEmployee() {
    return this.employee;
  }

  public EmployeeResponse setEmployee(Employee employee) {
    this.employee = employee;
    return this;
  }

  public void unsetEmployee() {
    this.employee = null;
  }

  /** Returns true if field employee is set (has been assigned a value) and false otherwise */
  public boolean isSetEmployee() {
    return this.employee != null;
  }

  public void setEmployeeIsSet(boolean value) {
    if (!value) {
      this.employee = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case BIND_STATUS:
      if (value == null) {
        unsetBindStatus();
      } else {
        setBindStatus((BindStatus)value);
      }
      break;

    case EMPLOYEE:
      if (value == null) {
        unsetEmployee();
      } else {
        setEmployee((Employee)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case BIND_STATUS:
      return getBindStatus();

    case EMPLOYEE:
      return getEmployee();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case BIND_STATUS:
      return isSetBindStatus();
    case EMPLOYEE:
      return isSetEmployee();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof EmployeeResponse)
      return this.equals((EmployeeResponse)that);
    return false;
  }

  public boolean equals(EmployeeResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_bindStatus = true && this.isSetBindStatus();
    boolean that_present_bindStatus = true && that.isSetBindStatus();
    if (this_present_bindStatus || that_present_bindStatus) {
      if (!(this_present_bindStatus && that_present_bindStatus))
        return false;
      if (!this.bindStatus.equals(that.bindStatus))
        return false;
    }

    boolean this_present_employee = true && this.isSetEmployee();
    boolean that_present_employee = true && that.isSetEmployee();
    if (this_present_employee || that_present_employee) {
      if (!(this_present_employee && that_present_employee))
        return false;
      if (!this.employee.equals(that.employee))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetBindStatus()) ? 131071 : 524287);
    if (isSetBindStatus())
      hashCode = hashCode * 8191 + bindStatus.getValue();

    hashCode = hashCode * 8191 + ((isSetEmployee()) ? 131071 : 524287);
    if (isSetEmployee())
      hashCode = hashCode * 8191 + employee.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(EmployeeResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetBindStatus()).compareTo(other.isSetBindStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBindStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bindStatus, other.bindStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEmployee()).compareTo(other.isSetEmployee());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmployee()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.employee, other.employee);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("EmployeeResponse(");
    boolean first = true;

    sb.append("bindStatus:");
    if (this.bindStatus == null) {
      sb.append("null");
    } else {
      sb.append(this.bindStatus);
    }
    first = false;
    if (isSetEmployee()) {
      if (!first) sb.append(", ");
      sb.append("employee:");
      if (this.employee == null) {
        sb.append("null");
      } else {
        sb.append(this.employee);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (bindStatus == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'bindStatus' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (employee != null) {
      employee.validate();
    }
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

  private static class EmployeeResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EmployeeResponseStandardScheme getScheme() {
      return new EmployeeResponseStandardScheme();
    }
  }

  private static class EmployeeResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<EmployeeResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EmployeeResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BIND_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.bindStatus = com.moseeker.thrift.gen.employee.struct.BindStatus.findByValue(iprot.readI32());
              struct.setBindStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EMPLOYEE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.employee = new Employee();
              struct.employee.read(iprot);
              struct.setEmployeeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EmployeeResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.bindStatus != null) {
        oprot.writeFieldBegin(BIND_STATUS_FIELD_DESC);
        oprot.writeI32(struct.bindStatus.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.employee != null) {
        if (struct.isSetEmployee()) {
          oprot.writeFieldBegin(EMPLOYEE_FIELD_DESC);
          struct.employee.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EmployeeResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EmployeeResponseTupleScheme getScheme() {
      return new EmployeeResponseTupleScheme();
    }
  }

  private static class EmployeeResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<EmployeeResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EmployeeResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.bindStatus.getValue());
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetEmployee()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetEmployee()) {
        struct.employee.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EmployeeResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.bindStatus = com.moseeker.thrift.gen.employee.struct.BindStatus.findByValue(iprot.readI32());
      struct.setBindStatusIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.employee = new Employee();
        struct.employee.read(iprot);
        struct.setEmployeeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

