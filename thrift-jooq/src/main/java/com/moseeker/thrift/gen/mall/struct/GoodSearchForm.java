/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.mall.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-10-17")
public class GoodSearchForm implements org.apache.thrift.TBase<GoodSearchForm, GoodSearchForm._Fields>, java.io.Serializable, Cloneable, Comparable<GoodSearchForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GoodSearchForm");

  private static final org.apache.thrift.protocol.TField PAGE_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("page_number", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("page_size", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("company_id", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("state", org.apache.thrift.protocol.TType.BYTE, (short)4);
  private static final org.apache.thrift.protocol.TField HR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("hr_id", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GoodSearchFormStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GoodSearchFormTupleSchemeFactory();

  public int page_number; // optional
  public int page_size; // optional
  public int company_id; // optional
  public byte state; // optional
  public int hr_id; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE_NUMBER((short)1, "page_number"),
    PAGE_SIZE((short)2, "page_size"),
    COMPANY_ID((short)3, "company_id"),
    STATE((short)4, "state"),
    HR_ID((short)5, "hr_id");

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
        case 1: // PAGE_NUMBER
          return PAGE_NUMBER;
        case 2: // PAGE_SIZE
          return PAGE_SIZE;
        case 3: // COMPANY_ID
          return COMPANY_ID;
        case 4: // STATE
          return STATE;
        case 5: // HR_ID
          return HR_ID;
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
  private static final int __PAGE_NUMBER_ISSET_ID = 0;
  private static final int __PAGE_SIZE_ISSET_ID = 1;
  private static final int __COMPANY_ID_ISSET_ID = 2;
  private static final int __STATE_ISSET_ID = 3;
  private static final int __HR_ID_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PAGE_NUMBER,_Fields.PAGE_SIZE,_Fields.COMPANY_ID,_Fields.STATE,_Fields.HR_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("page_number", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("page_size", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("company_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATE, new org.apache.thrift.meta_data.FieldMetaData("state", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.HR_ID, new org.apache.thrift.meta_data.FieldMetaData("hr_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GoodSearchForm.class, metaDataMap);
  }

  public GoodSearchForm() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GoodSearchForm(GoodSearchForm other) {
    __isset_bitfield = other.__isset_bitfield;
    this.page_number = other.page_number;
    this.page_size = other.page_size;
    this.company_id = other.company_id;
    this.state = other.state;
    this.hr_id = other.hr_id;
  }

  public GoodSearchForm deepCopy() {
    return new GoodSearchForm(this);
  }

  @Override
  public void clear() {
    setPage_numberIsSet(false);
    this.page_number = 0;
    setPage_sizeIsSet(false);
    this.page_size = 0;
    setCompany_idIsSet(false);
    this.company_id = 0;
    setStateIsSet(false);
    this.state = 0;
    setHr_idIsSet(false);
    this.hr_id = 0;
  }

  public int getPage_number() {
    return this.page_number;
  }

  public GoodSearchForm setPage_number(int page_number) {
    this.page_number = page_number;
    setPage_numberIsSet(true);
    return this;
  }

  public void unsetPage_number() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID);
  }

  /** Returns true if field page_number is set (has been assigned a value) and false otherwise */
  public boolean isSetPage_number() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID);
  }

  public void setPage_numberIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID, value);
  }

  public int getPage_size() {
    return this.page_size;
  }

  public GoodSearchForm setPage_size(int page_size) {
    this.page_size = page_size;
    setPage_sizeIsSet(true);
    return this;
  }

  public void unsetPage_size() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID);
  }

  /** Returns true if field page_size is set (has been assigned a value) and false otherwise */
  public boolean isSetPage_size() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID);
  }

  public void setPage_sizeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID, value);
  }

  public int getCompany_id() {
    return this.company_id;
  }

  public GoodSearchForm setCompany_id(int company_id) {
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

  public byte getState() {
    return this.state;
  }

  public GoodSearchForm setState(byte state) {
    this.state = state;
    setStateIsSet(true);
    return this;
  }

  public void unsetState() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __STATE_ISSET_ID);
  }

  /** Returns true if field state is set (has been assigned a value) and false otherwise */
  public boolean isSetState() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __STATE_ISSET_ID);
  }

  public void setStateIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __STATE_ISSET_ID, value);
  }

  public int getHr_id() {
    return this.hr_id;
  }

  public GoodSearchForm setHr_id(int hr_id) {
    this.hr_id = hr_id;
    setHr_idIsSet(true);
    return this;
  }

  public void unsetHr_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __HR_ID_ISSET_ID);
  }

  /** Returns true if field hr_id is set (has been assigned a value) and false otherwise */
  public boolean isSetHr_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __HR_ID_ISSET_ID);
  }

  public void setHr_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __HR_ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE_NUMBER:
      if (value == null) {
        unsetPage_number();
      } else {
        setPage_number((Integer)value);
      }
      break;

    case PAGE_SIZE:
      if (value == null) {
        unsetPage_size();
      } else {
        setPage_size((Integer)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompany_id();
      } else {
        setCompany_id((Integer)value);
      }
      break;

    case STATE:
      if (value == null) {
        unsetState();
      } else {
        setState((Byte)value);
      }
      break;

    case HR_ID:
      if (value == null) {
        unsetHr_id();
      } else {
        setHr_id((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE_NUMBER:
      return getPage_number();

    case PAGE_SIZE:
      return getPage_size();

    case COMPANY_ID:
      return getCompany_id();

    case STATE:
      return getState();

    case HR_ID:
      return getHr_id();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE_NUMBER:
      return isSetPage_number();
    case PAGE_SIZE:
      return isSetPage_size();
    case COMPANY_ID:
      return isSetCompany_id();
    case STATE:
      return isSetState();
    case HR_ID:
      return isSetHr_id();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GoodSearchForm)
      return this.equals((GoodSearchForm)that);
    return false;
  }

  public boolean equals(GoodSearchForm that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_page_number = true && this.isSetPage_number();
    boolean that_present_page_number = true && that.isSetPage_number();
    if (this_present_page_number || that_present_page_number) {
      if (!(this_present_page_number && that_present_page_number))
        return false;
      if (this.page_number != that.page_number)
        return false;
    }

    boolean this_present_page_size = true && this.isSetPage_size();
    boolean that_present_page_size = true && that.isSetPage_size();
    if (this_present_page_size || that_present_page_size) {
      if (!(this_present_page_size && that_present_page_size))
        return false;
      if (this.page_size != that.page_size)
        return false;
    }

    boolean this_present_company_id = true && this.isSetCompany_id();
    boolean that_present_company_id = true && that.isSetCompany_id();
    if (this_present_company_id || that_present_company_id) {
      if (!(this_present_company_id && that_present_company_id))
        return false;
      if (this.company_id != that.company_id)
        return false;
    }

    boolean this_present_state = true && this.isSetState();
    boolean that_present_state = true && that.isSetState();
    if (this_present_state || that_present_state) {
      if (!(this_present_state && that_present_state))
        return false;
      if (this.state != that.state)
        return false;
    }

    boolean this_present_hr_id = true && this.isSetHr_id();
    boolean that_present_hr_id = true && that.isSetHr_id();
    if (this_present_hr_id || that_present_hr_id) {
      if (!(this_present_hr_id && that_present_hr_id))
        return false;
      if (this.hr_id != that.hr_id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPage_number()) ? 131071 : 524287);
    if (isSetPage_number())
      hashCode = hashCode * 8191 + page_number;

    hashCode = hashCode * 8191 + ((isSetPage_size()) ? 131071 : 524287);
    if (isSetPage_size())
      hashCode = hashCode * 8191 + page_size;

    hashCode = hashCode * 8191 + ((isSetCompany_id()) ? 131071 : 524287);
    if (isSetCompany_id())
      hashCode = hashCode * 8191 + company_id;

    hashCode = hashCode * 8191 + ((isSetState()) ? 131071 : 524287);
    if (isSetState())
      hashCode = hashCode * 8191 + (int) (state);

    hashCode = hashCode * 8191 + ((isSetHr_id()) ? 131071 : 524287);
    if (isSetHr_id())
      hashCode = hashCode * 8191 + hr_id;

    return hashCode;
  }

  @Override
  public int compareTo(GoodSearchForm other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPage_number()).compareTo(other.isSetPage_number());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage_number()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page_number, other.page_number);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPage_size()).compareTo(other.isSetPage_size());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage_size()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page_size, other.page_size);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompany_id()).compareTo(other.isSetCompany_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompany_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.company_id, other.company_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetState()).compareTo(other.isSetState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.state, other.state);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHr_id()).compareTo(other.isSetHr_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHr_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hr_id, other.hr_id);
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
    StringBuilder sb = new StringBuilder("GoodSearchForm(");
    boolean first = true;

    if (isSetPage_number()) {
      sb.append("page_number:");
      sb.append(this.page_number);
      first = false;
    }
    if (isSetPage_size()) {
      if (!first) sb.append(", ");
      sb.append("page_size:");
      sb.append(this.page_size);
      first = false;
    }
    if (isSetCompany_id()) {
      if (!first) sb.append(", ");
      sb.append("company_id:");
      sb.append(this.company_id);
      first = false;
    }
    if (isSetState()) {
      if (!first) sb.append(", ");
      sb.append("state:");
      sb.append(this.state);
      first = false;
    }
    if (isSetHr_id()) {
      if (!first) sb.append(", ");
      sb.append("hr_id:");
      sb.append(this.hr_id);
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

  private static class GoodSearchFormStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GoodSearchFormStandardScheme getScheme() {
      return new GoodSearchFormStandardScheme();
    }
  }

  private static class GoodSearchFormStandardScheme extends org.apache.thrift.scheme.StandardScheme<GoodSearchForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GoodSearchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page_number = iprot.readI32();
              struct.setPage_numberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAGE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page_size = iprot.readI32();
              struct.setPage_sizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.company_id = iprot.readI32();
              struct.setCompany_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.state = iprot.readByte();
              struct.setStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // HR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.hr_id = iprot.readI32();
              struct.setHr_idIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GoodSearchForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPage_number()) {
        oprot.writeFieldBegin(PAGE_NUMBER_FIELD_DESC);
        oprot.writeI32(struct.page_number);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPage_size()) {
        oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
        oprot.writeI32(struct.page_size);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCompany_id()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.company_id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetState()) {
        oprot.writeFieldBegin(STATE_FIELD_DESC);
        oprot.writeByte(struct.state);
        oprot.writeFieldEnd();
      }
      if (struct.isSetHr_id()) {
        oprot.writeFieldBegin(HR_ID_FIELD_DESC);
        oprot.writeI32(struct.hr_id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GoodSearchFormTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GoodSearchFormTupleScheme getScheme() {
      return new GoodSearchFormTupleScheme();
    }
  }

  private static class GoodSearchFormTupleScheme extends org.apache.thrift.scheme.TupleScheme<GoodSearchForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GoodSearchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPage_number()) {
        optionals.set(0);
      }
      if (struct.isSetPage_size()) {
        optionals.set(1);
      }
      if (struct.isSetCompany_id()) {
        optionals.set(2);
      }
      if (struct.isSetState()) {
        optionals.set(3);
      }
      if (struct.isSetHr_id()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPage_number()) {
        oprot.writeI32(struct.page_number);
      }
      if (struct.isSetPage_size()) {
        oprot.writeI32(struct.page_size);
      }
      if (struct.isSetCompany_id()) {
        oprot.writeI32(struct.company_id);
      }
      if (struct.isSetState()) {
        oprot.writeByte(struct.state);
      }
      if (struct.isSetHr_id()) {
        oprot.writeI32(struct.hr_id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GoodSearchForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.page_number = iprot.readI32();
        struct.setPage_numberIsSet(true);
      }
      if (incoming.get(1)) {
        struct.page_size = iprot.readI32();
        struct.setPage_sizeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.company_id = iprot.readI32();
        struct.setCompany_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.state = iprot.readByte();
        struct.setStateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.hr_id = iprot.readI32();
        struct.setHr_idIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

