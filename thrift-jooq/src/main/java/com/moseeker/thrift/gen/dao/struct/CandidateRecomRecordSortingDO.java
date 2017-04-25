/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-11")
public class CandidateRecomRecordSortingDO implements org.apache.thrift.TBase<CandidateRecomRecordSortingDO, CandidateRecomRecordSortingDO._Fields>, java.io.Serializable, Cloneable, Comparable<CandidateRecomRecordSortingDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CandidateRecomRecordSortingDO");

  private static final org.apache.thrift.protocol.TField POST_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("postUserId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CandidateRecomRecordSortingDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CandidateRecomRecordSortingDOTupleSchemeFactory();

  public int postUserId; // optional
  public int count; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POST_USER_ID((short)1, "postUserId"),
    COUNT((short)2, "count");

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
        case 1: // POST_USER_ID
          return POST_USER_ID;
        case 2: // COUNT
          return COUNT;
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
  private static final int __POSTUSERID_ISSET_ID = 0;
  private static final int __COUNT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.POST_USER_ID,_Fields.COUNT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POST_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("postUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CandidateRecomRecordSortingDO.class, metaDataMap);
  }

  public CandidateRecomRecordSortingDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CandidateRecomRecordSortingDO(CandidateRecomRecordSortingDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.postUserId = other.postUserId;
    this.count = other.count;
  }

  public CandidateRecomRecordSortingDO deepCopy() {
    return new CandidateRecomRecordSortingDO(this);
  }

  @Override
  public void clear() {
    setPostUserIdIsSet(false);
    this.postUserId = 0;
    setCountIsSet(false);
    this.count = 0;
  }

  public int getPostUserId() {
    return this.postUserId;
  }

  public CandidateRecomRecordSortingDO setPostUserId(int postUserId) {
    this.postUserId = postUserId;
    setPostUserIdIsSet(true);
    return this;
  }

  public void unsetPostUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __POSTUSERID_ISSET_ID);
  }

  /** Returns true if field postUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetPostUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __POSTUSERID_ISSET_ID);
  }

  public void setPostUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __POSTUSERID_ISSET_ID, value);
  }

  public int getCount() {
    return this.count;
  }

  public CandidateRecomRecordSortingDO setCount(int count) {
    this.count = count;
    setCountIsSet(true);
    return this;
  }

  public void unsetCount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been assigned a value) and false otherwise */
  public boolean isSetCount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case POST_USER_ID:
      if (value == null) {
        unsetPostUserId();
      } else {
        setPostUserId((java.lang.Integer)value);
      }
      break;

    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case POST_USER_ID:
      return getPostUserId();

    case COUNT:
      return getCount();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case POST_USER_ID:
      return isSetPostUserId();
    case COUNT:
      return isSetCount();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CandidateRecomRecordSortingDO)
      return this.equals((CandidateRecomRecordSortingDO)that);
    return false;
  }

  public boolean equals(CandidateRecomRecordSortingDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_postUserId = true && this.isSetPostUserId();
    boolean that_present_postUserId = true && that.isSetPostUserId();
    if (this_present_postUserId || that_present_postUserId) {
      if (!(this_present_postUserId && that_present_postUserId))
        return false;
      if (this.postUserId != that.postUserId)
        return false;
    }

    boolean this_present_count = true && this.isSetCount();
    boolean that_present_count = true && that.isSetCount();
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPostUserId()) ? 131071 : 524287);
    if (isSetPostUserId())
      hashCode = hashCode * 8191 + postUserId;

    hashCode = hashCode * 8191 + ((isSetCount()) ? 131071 : 524287);
    if (isSetCount())
      hashCode = hashCode * 8191 + count;

    return hashCode;
  }

  @Override
  public int compareTo(CandidateRecomRecordSortingDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPostUserId()).compareTo(other.isSetPostUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPostUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.postUserId, other.postUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCount()).compareTo(other.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, other.count);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CandidateRecomRecordSortingDO(");
    boolean first = true;

    if (isSetPostUserId()) {
      sb.append("postUserId:");
      sb.append(this.postUserId);
      first = false;
    }
    if (isSetCount()) {
      if (!first) sb.append(", ");
      sb.append("count:");
      sb.append(this.count);
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

  private static class CandidateRecomRecordSortingDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CandidateRecomRecordSortingDOStandardScheme getScheme() {
      return new CandidateRecomRecordSortingDOStandardScheme();
    }
  }

  private static class CandidateRecomRecordSortingDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<CandidateRecomRecordSortingDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CandidateRecomRecordSortingDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POST_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.postUserId = iprot.readI32();
              struct.setPostUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.count = iprot.readI32();
              struct.setCountIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CandidateRecomRecordSortingDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPostUserId()) {
        oprot.writeFieldBegin(POST_USER_ID_FIELD_DESC);
        oprot.writeI32(struct.postUserId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCount()) {
        oprot.writeFieldBegin(COUNT_FIELD_DESC);
        oprot.writeI32(struct.count);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CandidateRecomRecordSortingDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CandidateRecomRecordSortingDOTupleScheme getScheme() {
      return new CandidateRecomRecordSortingDOTupleScheme();
    }
  }

  private static class CandidateRecomRecordSortingDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<CandidateRecomRecordSortingDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CandidateRecomRecordSortingDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPostUserId()) {
        optionals.set(0);
      }
      if (struct.isSetCount()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPostUserId()) {
        oprot.writeI32(struct.postUserId);
      }
      if (struct.isSetCount()) {
        oprot.writeI32(struct.count);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CandidateRecomRecordSortingDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.postUserId = iprot.readI32();
        struct.setPostUserIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
