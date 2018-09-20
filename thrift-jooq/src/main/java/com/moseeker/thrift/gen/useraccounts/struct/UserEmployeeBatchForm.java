/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.useraccounts.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-11-07")
public class UserEmployeeBatchForm implements org.apache.thrift.TBase<UserEmployeeBatchForm, UserEmployeeBatchForm._Fields>, java.io.Serializable, Cloneable, Comparable<UserEmployeeBatchForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserEmployeeBatchForm");

  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("company_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField DEL_NOT_INCLUDE_FIELD_DESC = new org.apache.thrift.protocol.TField("del_not_include", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField AS_TASK_FIELD_DESC = new org.apache.thrift.protocol.TField("as_task", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserEmployeeBatchFormStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserEmployeeBatchFormTupleSchemeFactory();

  public java.util.List<UserEmployeeStruct> data; // required
  public int company_id; // required
  public boolean del_not_include; // required
  public boolean as_task; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATA((short)1, "data"),
    COMPANY_ID((short)2, "company_id"),
    DEL_NOT_INCLUDE((short)3, "del_not_include"),
    AS_TASK((short)4, "as_task");

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
        case 1: // DATA
          return DATA;
        case 2: // COMPANY_ID
          return COMPANY_ID;
        case 3: // DEL_NOT_INCLUDE
          return DEL_NOT_INCLUDE;
        case 4: // AS_TASK
          return AS_TASK;
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
  private static final int __COMPANY_ID_ISSET_ID = 0;
  private static final int __DEL_NOT_INCLUDE_ISSET_ID = 1;
  private static final int __AS_TASK_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST,
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserEmployeeStruct.class))));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("company_id", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DEL_NOT_INCLUDE, new org.apache.thrift.meta_data.FieldMetaData("del_not_include", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.AS_TASK, new org.apache.thrift.meta_data.FieldMetaData("as_task", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserEmployeeBatchForm.class, metaDataMap);
  }

  public UserEmployeeBatchForm() {
  }

  public UserEmployeeBatchForm(
    java.util.List<UserEmployeeStruct> data,
    int company_id,
    boolean del_not_include,
    boolean as_task)
  {
    this();
    this.data = data;
    this.company_id = company_id;
    setCompany_idIsSet(true);
    this.del_not_include = del_not_include;
    setDel_not_includeIsSet(true);
    this.as_task = as_task;
    setAs_taskIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserEmployeeBatchForm(UserEmployeeBatchForm other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetData()) {
      java.util.List<UserEmployeeStruct> __this__data = new java.util.ArrayList<UserEmployeeStruct>(other.data.size());
      for (UserEmployeeStruct other_element : other.data) {
        __this__data.add(new UserEmployeeStruct(other_element));
      }
      this.data = __this__data;
    }
    this.company_id = other.company_id;
    this.del_not_include = other.del_not_include;
    this.as_task = other.as_task;
  }

  public UserEmployeeBatchForm deepCopy() {
    return new UserEmployeeBatchForm(this);
  }

  @Override
  public void clear() {
    this.data = null;
    setCompany_idIsSet(false);
    this.company_id = 0;
    setDel_not_includeIsSet(false);
    this.del_not_include = false;
    setAs_taskIsSet(false);
    this.as_task = false;
  }

  public int getDataSize() {
    return (this.data == null) ? 0 : this.data.size();
  }

  public java.util.Iterator<UserEmployeeStruct> getDataIterator() {
    return (this.data == null) ? null : this.data.iterator();
  }

  public void addToData(UserEmployeeStruct elem) {
    if (this.data == null) {
      this.data = new java.util.ArrayList<UserEmployeeStruct>();
    }
    this.data.add(elem);
  }

  public java.util.List<UserEmployeeStruct> getData() {
    return this.data;
  }

  public UserEmployeeBatchForm setData(java.util.List<UserEmployeeStruct> data) {
    this.data = data;
    return this;
  }

  public void unsetData() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean isSetData() {
    return this.data != null;
  }

  public void setDataIsSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public int getCompany_id() {
    return this.company_id;
  }

  public UserEmployeeBatchForm setCompany_id(int company_id) {
    this.company_id = company_id;
    setCompany_idIsSet(true);
    return this;
  }

  public void unsetCompany_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  /** Returns true if field company_id is set (has been assigned a value) and false otherwise */
  public boolean isSetCompany_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  public void setCompany_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMPANY_ID_ISSET_ID, value);
  }

  public boolean isDel_not_include() {
    return this.del_not_include;
  }

  public UserEmployeeBatchForm setDel_not_include(boolean del_not_include) {
    this.del_not_include = del_not_include;
    setDel_not_includeIsSet(true);
    return this;
  }

  public void unsetDel_not_include() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DEL_NOT_INCLUDE_ISSET_ID);
  }

  /** Returns true if field del_not_include is set (has been assigned a value) and false otherwise */
  public boolean isSetDel_not_include() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DEL_NOT_INCLUDE_ISSET_ID);
  }

  public void setDel_not_includeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DEL_NOT_INCLUDE_ISSET_ID, value);
  }

  public boolean isAs_task() {
    return this.as_task;
  }

  public UserEmployeeBatchForm setAs_task(boolean as_task) {
    this.as_task = as_task;
    setAs_taskIsSet(true);
    return this;
  }

  public void unsetAs_task() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __AS_TASK_ISSET_ID);
  }

  /** Returns true if field as_task is set (has been assigned a value) and false otherwise */
  public boolean isSetAs_task() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __AS_TASK_ISSET_ID);
  }

  public void setAs_taskIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __AS_TASK_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case DATA:
      if (value == null) {
        unsetData();
      } else {
        setData((java.util.List<UserEmployeeStruct>)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompany_id();
      } else {
        setCompany_id((java.lang.Integer)value);
      }
      break;

    case DEL_NOT_INCLUDE:
      if (value == null) {
        unsetDel_not_include();
      } else {
        setDel_not_include((java.lang.Boolean)value);
      }
      break;

    case AS_TASK:
      if (value == null) {
        unsetAs_task();
      } else {
        setAs_task((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DATA:
      return getData();

    case COMPANY_ID:
      return getCompany_id();

    case DEL_NOT_INCLUDE:
      return isDel_not_include();

    case AS_TASK:
      return isAs_task();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DATA:
      return isSetData();
    case COMPANY_ID:
      return isSetCompany_id();
    case DEL_NOT_INCLUDE:
      return isSetDel_not_include();
    case AS_TASK:
      return isSetAs_task();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserEmployeeBatchForm)
      return this.equals((UserEmployeeBatchForm)that);
    return false;
  }

  public boolean equals(UserEmployeeBatchForm that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    boolean this_present_company_id = true;
    boolean that_present_company_id = true;
    if (this_present_company_id || that_present_company_id) {
      if (!(this_present_company_id && that_present_company_id))
        return false;
      if (this.company_id != that.company_id)
        return false;
    }

    boolean this_present_del_not_include = true;
    boolean that_present_del_not_include = true;
    if (this_present_del_not_include || that_present_del_not_include) {
      if (!(this_present_del_not_include && that_present_del_not_include))
        return false;
      if (this.del_not_include != that.del_not_include)
        return false;
    }

    boolean this_present_as_task = true;
    boolean that_present_as_task = true;
    if (this_present_as_task || that_present_as_task) {
      if (!(this_present_as_task && that_present_as_task))
        return false;
      if (this.as_task != that.as_task)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetData()) ? 131071 : 524287);
    if (isSetData())
      hashCode = hashCode * 8191 + data.hashCode();

    hashCode = hashCode * 8191 + company_id;

    hashCode = hashCode * 8191 + ((del_not_include) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((as_task) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(UserEmployeeBatchForm other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetData()).compareTo(other.isSetData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCompany_id()).compareTo(other.isSetCompany_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompany_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.company_id, other.company_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDel_not_include()).compareTo(other.isSetDel_not_include());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDel_not_include()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.del_not_include, other.del_not_include);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAs_task()).compareTo(other.isSetAs_task());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAs_task()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.as_task, other.as_task);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserEmployeeBatchForm(");
    boolean first = true;

    sb.append("data:");
    if (this.data == null) {
      sb.append("null");
    } else {
      sb.append(this.data);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("company_id:");
    sb.append(this.company_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("del_not_include:");
    sb.append(this.del_not_include);
    first = false;
    if (!first) sb.append(", ");
    sb.append("as_task:");
    sb.append(this.as_task);
    first = false;
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

  private static class UserEmployeeBatchFormStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserEmployeeBatchFormStandardScheme getScheme() {
      return new UserEmployeeBatchFormStandardScheme();
    }
  }

  private static class UserEmployeeBatchFormStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserEmployeeBatchForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserEmployeeBatchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.data = new java.util.ArrayList<UserEmployeeStruct>(_list0.size);
                UserEmployeeStruct _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new UserEmployeeStruct();
                  _elem1.read(iprot);
                  struct.data.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setDataIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.company_id = iprot.readI32();
              struct.setCompany_idIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DEL_NOT_INCLUDE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.del_not_include = iprot.readBool();
              struct.setDel_not_includeIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // AS_TASK
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.as_task = iprot.readBool();
              struct.setAs_taskIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserEmployeeBatchForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.data != null) {
        oprot.writeFieldBegin(DATA_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.data.size()));
          for (UserEmployeeStruct _iter3 : struct.data)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
      oprot.writeI32(struct.company_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DEL_NOT_INCLUDE_FIELD_DESC);
      oprot.writeBool(struct.del_not_include);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(AS_TASK_FIELD_DESC);
      oprot.writeBool(struct.as_task);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserEmployeeBatchFormTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserEmployeeBatchFormTupleScheme getScheme() {
      return new UserEmployeeBatchFormTupleScheme();
    }
  }

  private static class UserEmployeeBatchFormTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserEmployeeBatchForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserEmployeeBatchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetData()) {
        optionals.set(0);
      }
      if (struct.isSetCompany_id()) {
        optionals.set(1);
      }
      if (struct.isSetDel_not_include()) {
        optionals.set(2);
      }
      if (struct.isSetAs_task()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetData()) {
        {
          oprot.writeI32(struct.data.size());
          for (UserEmployeeStruct _iter4 : struct.data)
          {
            _iter4.write(oprot);
          }
        }
      }
      if (struct.isSetCompany_id()) {
        oprot.writeI32(struct.company_id);
      }
      if (struct.isSetDel_not_include()) {
        oprot.writeBool(struct.del_not_include);
      }
      if (struct.isSetAs_task()) {
        oprot.writeBool(struct.as_task);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserEmployeeBatchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.data = new java.util.ArrayList<UserEmployeeStruct>(_list5.size);
          UserEmployeeStruct _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new UserEmployeeStruct();
            _elem6.read(iprot);
            struct.data.add(_elem6);
          }
        }
        struct.setDataIsSet(true);
      }
      if (incoming.get(1)) {
        struct.company_id = iprot.readI32();
        struct.setCompany_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.del_not_include = iprot.readBool();
        struct.setDel_not_includeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.as_task = iprot.readBool();
        struct.setAs_taskIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

