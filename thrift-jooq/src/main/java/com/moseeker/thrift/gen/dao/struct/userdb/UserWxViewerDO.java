/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.userdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-12")
public class UserWxViewerDO implements org.apache.thrift.TBase<UserWxViewerDO, UserWxViewerDO._Fields>, java.io.Serializable, Cloneable, Comparable<UserWxViewerDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserWxViewerDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField SYSUSER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sysuserId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField IDCODE_FIELD_DESC = new org.apache.thrift.protocol.TField("idcode", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CLIENT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("clientType", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserWxViewerDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserWxViewerDOTupleSchemeFactory();

  public int id; // optional
  public int sysuserId; // optional
  public java.lang.String idcode; // optional
  public int clientType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    SYSUSER_ID((short)2, "sysuserId"),
    IDCODE((short)3, "idcode"),
    CLIENT_TYPE((short)4, "clientType");

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
        case 2: // SYSUSER_ID
          return SYSUSER_ID;
        case 3: // IDCODE
          return IDCODE;
        case 4: // CLIENT_TYPE
          return CLIENT_TYPE;
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
  private static final int __CLIENTTYPE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.SYSUSER_ID,_Fields.IDCODE,_Fields.CLIENT_TYPE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SYSUSER_ID, new org.apache.thrift.meta_data.FieldMetaData("sysuserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.IDCODE, new org.apache.thrift.meta_data.FieldMetaData("idcode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("clientType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserWxViewerDO.class, metaDataMap);
  }

  public UserWxViewerDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserWxViewerDO(UserWxViewerDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.sysuserId = other.sysuserId;
    if (other.isSetIdcode()) {
      this.idcode = other.idcode;
    }
    this.clientType = other.clientType;
  }

  public UserWxViewerDO deepCopy() {
    return new UserWxViewerDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setSysuserIdIsSet(false);
    this.sysuserId = 0;
    this.idcode = null;
    setClientTypeIsSet(false);
    this.clientType = 0;
  }

  public int getId() {
    return this.id;
  }

  public UserWxViewerDO setId(int id) {
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

  public int getSysuserId() {
    return this.sysuserId;
  }

  public UserWxViewerDO setSysuserId(int sysuserId) {
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

  public java.lang.String getIdcode() {
    return this.idcode;
  }

  public UserWxViewerDO setIdcode(java.lang.String idcode) {
    this.idcode = idcode;
    return this;
  }

  public void unsetIdcode() {
    this.idcode = null;
  }

  /** Returns true if field idcode is set (has been assigned a value) and false otherwise */
  public boolean isSetIdcode() {
    return this.idcode != null;
  }

  public void setIdcodeIsSet(boolean value) {
    if (!value) {
      this.idcode = null;
    }
  }

  public int getClientType() {
    return this.clientType;
  }

  public UserWxViewerDO setClientType(int clientType) {
    this.clientType = clientType;
    setClientTypeIsSet(true);
    return this;
  }

  public void unsetClientType() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CLIENTTYPE_ISSET_ID);
  }

  /** Returns true if field clientType is set (has been assigned a value) and false otherwise */
  public boolean isSetClientType() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CLIENTTYPE_ISSET_ID);
  }

  public void setClientTypeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CLIENTTYPE_ISSET_ID, value);
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

    case SYSUSER_ID:
      if (value == null) {
        unsetSysuserId();
      } else {
        setSysuserId((java.lang.Integer)value);
      }
      break;

    case IDCODE:
      if (value == null) {
        unsetIdcode();
      } else {
        setIdcode((java.lang.String)value);
      }
      break;

    case CLIENT_TYPE:
      if (value == null) {
        unsetClientType();
      } else {
        setClientType((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case SYSUSER_ID:
      return getSysuserId();

    case IDCODE:
      return getIdcode();

    case CLIENT_TYPE:
      return getClientType();

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
    case SYSUSER_ID:
      return isSetSysuserId();
    case IDCODE:
      return isSetIdcode();
    case CLIENT_TYPE:
      return isSetClientType();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserWxViewerDO)
      return this.equals((UserWxViewerDO)that);
    return false;
  }

  public boolean equals(UserWxViewerDO that) {
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

    boolean this_present_sysuserId = true && this.isSetSysuserId();
    boolean that_present_sysuserId = true && that.isSetSysuserId();
    if (this_present_sysuserId || that_present_sysuserId) {
      if (!(this_present_sysuserId && that_present_sysuserId))
        return false;
      if (this.sysuserId != that.sysuserId)
        return false;
    }

    boolean this_present_idcode = true && this.isSetIdcode();
    boolean that_present_idcode = true && that.isSetIdcode();
    if (this_present_idcode || that_present_idcode) {
      if (!(this_present_idcode && that_present_idcode))
        return false;
      if (!this.idcode.equals(that.idcode))
        return false;
    }

    boolean this_present_clientType = true && this.isSetClientType();
    boolean that_present_clientType = true && that.isSetClientType();
    if (this_present_clientType || that_present_clientType) {
      if (!(this_present_clientType && that_present_clientType))
        return false;
      if (this.clientType != that.clientType)
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

    hashCode = hashCode * 8191 + ((isSetSysuserId()) ? 131071 : 524287);
    if (isSetSysuserId())
      hashCode = hashCode * 8191 + sysuserId;

    hashCode = hashCode * 8191 + ((isSetIdcode()) ? 131071 : 524287);
    if (isSetIdcode())
      hashCode = hashCode * 8191 + idcode.hashCode();

    hashCode = hashCode * 8191 + ((isSetClientType()) ? 131071 : 524287);
    if (isSetClientType())
      hashCode = hashCode * 8191 + clientType;

    return hashCode;
  }

  @Override
  public int compareTo(UserWxViewerDO other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetIdcode()).compareTo(other.isSetIdcode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdcode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.idcode, other.idcode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetClientType()).compareTo(other.isSetClientType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClientType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clientType, other.clientType);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserWxViewerDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetSysuserId()) {
      if (!first) sb.append(", ");
      sb.append("sysuserId:");
      sb.append(this.sysuserId);
      first = false;
    }
    if (isSetIdcode()) {
      if (!first) sb.append(", ");
      sb.append("idcode:");
      if (this.idcode == null) {
        sb.append("null");
      } else {
        sb.append(this.idcode);
      }
      first = false;
    }
    if (isSetClientType()) {
      if (!first) sb.append(", ");
      sb.append("clientType:");
      sb.append(this.clientType);
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

  private static class UserWxViewerDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserWxViewerDOStandardScheme getScheme() {
      return new UserWxViewerDOStandardScheme();
    }
  }

  private static class UserWxViewerDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserWxViewerDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserWxViewerDO struct) throws org.apache.thrift.TException {
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
          case 2: // SYSUSER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sysuserId = iprot.readI32();
              struct.setSysuserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IDCODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.idcode = iprot.readString();
              struct.setIdcodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CLIENT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.clientType = iprot.readI32();
              struct.setClientTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserWxViewerDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSysuserId()) {
        oprot.writeFieldBegin(SYSUSER_ID_FIELD_DESC);
        oprot.writeI32(struct.sysuserId);
        oprot.writeFieldEnd();
      }
      if (struct.idcode != null) {
        if (struct.isSetIdcode()) {
          oprot.writeFieldBegin(IDCODE_FIELD_DESC);
          oprot.writeString(struct.idcode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetClientType()) {
        oprot.writeFieldBegin(CLIENT_TYPE_FIELD_DESC);
        oprot.writeI32(struct.clientType);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserWxViewerDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserWxViewerDOTupleScheme getScheme() {
      return new UserWxViewerDOTupleScheme();
    }
  }

  private static class UserWxViewerDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserWxViewerDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserWxViewerDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetSysuserId()) {
        optionals.set(1);
      }
      if (struct.isSetIdcode()) {
        optionals.set(2);
      }
      if (struct.isSetClientType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetSysuserId()) {
        oprot.writeI32(struct.sysuserId);
      }
      if (struct.isSetIdcode()) {
        oprot.writeString(struct.idcode);
      }
      if (struct.isSetClientType()) {
        oprot.writeI32(struct.clientType);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserWxViewerDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sysuserId = iprot.readI32();
        struct.setSysuserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.idcode = iprot.readString();
        struct.setIdcodeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.clientType = iprot.readI32();
        struct.setClientTypeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

