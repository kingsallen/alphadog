/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.analytics;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-15")
public class StDquserLoginDO implements org.apache.thrift.TBase<StDquserLoginDO, StDquserLoginDO._Fields>, java.io.Serializable, Cloneable, Comparable<StDquserLoginDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StDquserLoginDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SYSUSER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sysuserId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField WECHAT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("wechatId", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new StDquserLoginDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new StDquserLoginDOTupleSchemeFactory();

  public int id; // optional
  public java.lang.String createTime; // optional
  public int sysuserId; // optional
  public int wechatId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    CREATE_TIME((short)2, "createTime"),
    SYSUSER_ID((short)3, "sysuserId"),
    WECHAT_ID((short)4, "wechatId");

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
        case 2: // CREATE_TIME
          return CREATE_TIME;
        case 3: // SYSUSER_ID
          return SYSUSER_ID;
        case 4: // WECHAT_ID
          return WECHAT_ID;
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
  private static final int __SYSUSERID_ISSET_ID = 1;
  private static final int __WECHATID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.CREATE_TIME,_Fields.SYSUSER_ID,_Fields.WECHAT_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SYSUSER_ID, new org.apache.thrift.meta_data.FieldMetaData("sysuserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WECHAT_ID, new org.apache.thrift.meta_data.FieldMetaData("wechatId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StDquserLoginDO.class, metaDataMap);
  }

  public StDquserLoginDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StDquserLoginDO(StDquserLoginDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
    this.sysuserId = other.sysuserId;
    this.wechatId = other.wechatId;
  }

  public StDquserLoginDO deepCopy() {
    return new StDquserLoginDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.createTime = null;
    setSysuserIdIsSet(false);
    this.sysuserId = 0;
    setWechatIdIsSet(false);
    this.wechatId = 0;
  }

  public int getId() {
    return this.id;
  }

  public StDquserLoginDO setId(int id) {
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

  public java.lang.String getCreateTime() {
    return this.createTime;
  }

  public StDquserLoginDO setCreateTime(java.lang.String createTime) {
    this.createTime = createTime;
    return this;
  }

  public void unsetCreateTime() {
    this.createTime = null;
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return this.createTime != null;
  }

  public void setCreateTimeIsSet(boolean value) {
    if (!value) {
      this.createTime = null;
    }
  }

  public int getSysuserId() {
    return this.sysuserId;
  }

  public StDquserLoginDO setSysuserId(int sysuserId) {
    this.sysuserId = sysuserId;
    setSysuserIdIsSet(true);
    return this;
  }

  public void unsetSysuserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SYSUSERID_ISSET_ID);
  }

  /** Returns true if field sysuserId is set (has been assigned a value) and false otherwise */
  public boolean isSetSysuserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SYSUSERID_ISSET_ID);
  }

  public void setSysuserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SYSUSERID_ISSET_ID, value);
  }

  public int getWechatId() {
    return this.wechatId;
  }

  public StDquserLoginDO setWechatId(int wechatId) {
    this.wechatId = wechatId;
    setWechatIdIsSet(true);
    return this;
  }

  public void unsetWechatId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WECHATID_ISSET_ID);
  }

  /** Returns true if field wechatId is set (has been assigned a value) and false otherwise */
  public boolean isSetWechatId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WECHATID_ISSET_ID);
  }

  public void setWechatIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WECHATID_ISSET_ID, value);
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

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.String)value);
      }
      break;

    case SYSUSER_ID:
      if (value == null) {
        unsetSysuserId();
      } else {
        setSysuserId((java.lang.Integer)value);
      }
      break;

    case WECHAT_ID:
      if (value == null) {
        unsetWechatId();
      } else {
        setWechatId((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case CREATE_TIME:
      return getCreateTime();

    case SYSUSER_ID:
      return getSysuserId();

    case WECHAT_ID:
      return getWechatId();

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
    case CREATE_TIME:
      return isSetCreateTime();
    case SYSUSER_ID:
      return isSetSysuserId();
    case WECHAT_ID:
      return isSetWechatId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof StDquserLoginDO)
      return this.equals((StDquserLoginDO)that);
    return false;
  }

  public boolean equals(StDquserLoginDO that) {
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

    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (!this.createTime.equals(that.createTime))
        return false;
    }

    boolean this_present_sysuserId = true && this.isSetSysuserId();
    boolean that_present_sysuserId = true && that.isSetSysuserId();
    if (this_present_sysuserId || that_present_sysuserId) {
      if (!(this_present_sysuserId && that_present_sysuserId))
        return false;
      if (this.sysuserId != that.sysuserId)
        return false;
    }

    boolean this_present_wechatId = true && this.isSetWechatId();
    boolean that_present_wechatId = true && that.isSetWechatId();
    if (this_present_wechatId || that_present_wechatId) {
      if (!(this_present_wechatId && that_present_wechatId))
        return false;
      if (this.wechatId != that.wechatId)
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

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    hashCode = hashCode * 8191 + ((isSetSysuserId()) ? 131071 : 524287);
    if (isSetSysuserId())
      hashCode = hashCode * 8191 + sysuserId;

    hashCode = hashCode * 8191 + ((isSetWechatId()) ? 131071 : 524287);
    if (isSetWechatId())
      hashCode = hashCode * 8191 + wechatId;

    return hashCode;
  }

  @Override
  public int compareTo(StDquserLoginDO other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSysuserId()).compareTo(other.isSetSysuserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSysuserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sysuserId, other.sysuserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetWechatId()).compareTo(other.isSetWechatId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWechatId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.wechatId, other.wechatId);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("StDquserLoginDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetCreateTime()) {
      if (!first) sb.append(", ");
      sb.append("createTime:");
      if (this.createTime == null) {
        sb.append("null");
      } else {
        sb.append(this.createTime);
      }
      first = false;
    }
    if (isSetSysuserId()) {
      if (!first) sb.append(", ");
      sb.append("sysuserId:");
      sb.append(this.sysuserId);
      first = false;
    }
    if (isSetWechatId()) {
      if (!first) sb.append(", ");
      sb.append("wechatId:");
      sb.append(this.wechatId);
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

  private static class StDquserLoginDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public StDquserLoginDOStandardScheme getScheme() {
      return new StDquserLoginDOStandardScheme();
    }
  }

  private static class StDquserLoginDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<StDquserLoginDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StDquserLoginDO struct) throws org.apache.thrift.TException {
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
          case 2: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createTime = iprot.readString();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SYSUSER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sysuserId = iprot.readI32();
              struct.setSysuserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // WECHAT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.wechatId = iprot.readI32();
              struct.setWechatIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StDquserLoginDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.createTime != null) {
        if (struct.isSetCreateTime()) {
          oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
          oprot.writeString(struct.createTime);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSysuserId()) {
        oprot.writeFieldBegin(SYSUSER_ID_FIELD_DESC);
        oprot.writeI32(struct.sysuserId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetWechatId()) {
        oprot.writeFieldBegin(WECHAT_ID_FIELD_DESC);
        oprot.writeI32(struct.wechatId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StDquserLoginDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public StDquserLoginDOTupleScheme getScheme() {
      return new StDquserLoginDOTupleScheme();
    }
  }

  private static class StDquserLoginDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<StDquserLoginDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StDquserLoginDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(1);
      }
      if (struct.isSetSysuserId()) {
        optionals.set(2);
      }
      if (struct.isSetWechatId()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
      if (struct.isSetSysuserId()) {
        oprot.writeI32(struct.sysuserId);
      }
      if (struct.isSetWechatId()) {
        oprot.writeI32(struct.wechatId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StDquserLoginDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sysuserId = iprot.readI32();
        struct.setSysuserIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.wechatId = iprot.readI32();
        struct.setWechatIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

