/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.chat.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-10")
public class HrVO implements org.apache.thrift.TBase<HrVO, HrVO._Fields>, java.io.Serializable, Cloneable, Comparable<HrVO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrVO");

  private static final org.apache.thrift.protocol.TField HR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("hrId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HR_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("hrName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField HR_HEAD_IMG_FIELD_DESC = new org.apache.thrift.protocol.TField("hrHeadImg", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrVOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrVOTupleSchemeFactory();

  public int hrId; // optional
  public java.lang.String hrName; // optional
  public java.lang.String hrHeadImg; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    HR_ID((short)1, "hrId"),
    HR_NAME((short)2, "hrName"),
    HR_HEAD_IMG((short)3, "hrHeadImg");

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
        case 1: // HR_ID
          return HR_ID;
        case 2: // HR_NAME
          return HR_NAME;
        case 3: // HR_HEAD_IMG
          return HR_HEAD_IMG;
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
  private static final int __HRID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.HR_ID,_Fields.HR_NAME,_Fields.HR_HEAD_IMG};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.HR_ID, new org.apache.thrift.meta_data.FieldMetaData("hrId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HR_NAME, new org.apache.thrift.meta_data.FieldMetaData("hrName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HR_HEAD_IMG, new org.apache.thrift.meta_data.FieldMetaData("hrHeadImg", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrVO.class, metaDataMap);
  }

  public HrVO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrVO(HrVO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.hrId = other.hrId;
    if (other.isSetHrName()) {
      this.hrName = other.hrName;
    }
    if (other.isSetHrHeadImg()) {
      this.hrHeadImg = other.hrHeadImg;
    }
  }

  public HrVO deepCopy() {
    return new HrVO(this);
  }

  @Override
  public void clear() {
    setHrIdIsSet(false);
    this.hrId = 0;
    this.hrName = null;
    this.hrHeadImg = null;
  }

  public int getHrId() {
    return this.hrId;
  }

  public HrVO setHrId(int hrId) {
    this.hrId = hrId;
    setHrIdIsSet(true);
    return this;
  }

  public void unsetHrId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __HRID_ISSET_ID);
  }

  /** Returns true if field hrId is set (has been assigned a value) and false otherwise */
  public boolean isSetHrId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __HRID_ISSET_ID);
  }

  public void setHrIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __HRID_ISSET_ID, value);
  }

  public java.lang.String getHrName() {
    return this.hrName;
  }

  public HrVO setHrName(java.lang.String hrName) {
    this.hrName = hrName;
    return this;
  }

  public void unsetHrName() {
    this.hrName = null;
  }

  /** Returns true if field hrName is set (has been assigned a value) and false otherwise */
  public boolean isSetHrName() {
    return this.hrName != null;
  }

  public void setHrNameIsSet(boolean value) {
    if (!value) {
      this.hrName = null;
    }
  }

  public java.lang.String getHrHeadImg() {
    return this.hrHeadImg;
  }

  public HrVO setHrHeadImg(java.lang.String hrHeadImg) {
    this.hrHeadImg = hrHeadImg;
    return this;
  }

  public void unsetHrHeadImg() {
    this.hrHeadImg = null;
  }

  /** Returns true if field hrHeadImg is set (has been assigned a value) and false otherwise */
  public boolean isSetHrHeadImg() {
    return this.hrHeadImg != null;
  }

  public void setHrHeadImgIsSet(boolean value) {
    if (!value) {
      this.hrHeadImg = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case HR_ID:
      if (value == null) {
        unsetHrId();
      } else {
        setHrId((java.lang.Integer)value);
      }
      break;

    case HR_NAME:
      if (value == null) {
        unsetHrName();
      } else {
        setHrName((java.lang.String)value);
      }
      break;

    case HR_HEAD_IMG:
      if (value == null) {
        unsetHrHeadImg();
      } else {
        setHrHeadImg((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case HR_ID:
      return getHrId();

    case HR_NAME:
      return getHrName();

    case HR_HEAD_IMG:
      return getHrHeadImg();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case HR_ID:
      return isSetHrId();
    case HR_NAME:
      return isSetHrName();
    case HR_HEAD_IMG:
      return isSetHrHeadImg();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof HrVO)
      return this.equals((HrVO)that);
    return false;
  }

  public boolean equals(HrVO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_hrId = true && this.isSetHrId();
    boolean that_present_hrId = true && that.isSetHrId();
    if (this_present_hrId || that_present_hrId) {
      if (!(this_present_hrId && that_present_hrId))
        return false;
      if (this.hrId != that.hrId)
        return false;
    }

    boolean this_present_hrName = true && this.isSetHrName();
    boolean that_present_hrName = true && that.isSetHrName();
    if (this_present_hrName || that_present_hrName) {
      if (!(this_present_hrName && that_present_hrName))
        return false;
      if (!this.hrName.equals(that.hrName))
        return false;
    }

    boolean this_present_hrHeadImg = true && this.isSetHrHeadImg();
    boolean that_present_hrHeadImg = true && that.isSetHrHeadImg();
    if (this_present_hrHeadImg || that_present_hrHeadImg) {
      if (!(this_present_hrHeadImg && that_present_hrHeadImg))
        return false;
      if (!this.hrHeadImg.equals(that.hrHeadImg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetHrId()) ? 131071 : 524287);
    if (isSetHrId())
      hashCode = hashCode * 8191 + hrId;

    hashCode = hashCode * 8191 + ((isSetHrName()) ? 131071 : 524287);
    if (isSetHrName())
      hashCode = hashCode * 8191 + hrName.hashCode();

    hashCode = hashCode * 8191 + ((isSetHrHeadImg()) ? 131071 : 524287);
    if (isSetHrHeadImg())
      hashCode = hashCode * 8191 + hrHeadImg.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HrVO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetHrId()).compareTo(other.isSetHrId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHrId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hrId, other.hrId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetHrName()).compareTo(other.isSetHrName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHrName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hrName, other.hrName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetHrHeadImg()).compareTo(other.isSetHrHeadImg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHrHeadImg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hrHeadImg, other.hrHeadImg);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HrVO(");
    boolean first = true;

    if (isSetHrId()) {
      sb.append("hrId:");
      sb.append(this.hrId);
      first = false;
    }
    if (isSetHrName()) {
      if (!first) sb.append(", ");
      sb.append("hrName:");
      if (this.hrName == null) {
        sb.append("null");
      } else {
        sb.append(this.hrName);
      }
      first = false;
    }
    if (isSetHrHeadImg()) {
      if (!first) sb.append(", ");
      sb.append("hrHeadImg:");
      if (this.hrHeadImg == null) {
        sb.append("null");
      } else {
        sb.append(this.hrHeadImg);
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

  private static class HrVOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrVOStandardScheme getScheme() {
      return new HrVOStandardScheme();
    }
  }

  private static class HrVOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrVO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // HR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.hrId = iprot.readI32();
              struct.setHrIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HR_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hrName = iprot.readString();
              struct.setHrNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // HR_HEAD_IMG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hrHeadImg = iprot.readString();
              struct.setHrHeadImgIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrVO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetHrId()) {
        oprot.writeFieldBegin(HR_ID_FIELD_DESC);
        oprot.writeI32(struct.hrId);
        oprot.writeFieldEnd();
      }
      if (struct.hrName != null) {
        if (struct.isSetHrName()) {
          oprot.writeFieldBegin(HR_NAME_FIELD_DESC);
          oprot.writeString(struct.hrName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.hrHeadImg != null) {
        if (struct.isSetHrHeadImg()) {
          oprot.writeFieldBegin(HR_HEAD_IMG_FIELD_DESC);
          oprot.writeString(struct.hrHeadImg);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrVOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrVOTupleScheme getScheme() {
      return new HrVOTupleScheme();
    }
  }

  private static class HrVOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrVO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetHrId()) {
        optionals.set(0);
      }
      if (struct.isSetHrName()) {
        optionals.set(1);
      }
      if (struct.isSetHrHeadImg()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetHrId()) {
        oprot.writeI32(struct.hrId);
      }
      if (struct.isSetHrName()) {
        oprot.writeString(struct.hrName);
      }
      if (struct.isSetHrHeadImg()) {
        oprot.writeString(struct.hrHeadImg);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.hrId = iprot.readI32();
        struct.setHrIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.hrName = iprot.readString();
        struct.setHrNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.hrHeadImg = iprot.readString();
        struct.setHrHeadImgIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
