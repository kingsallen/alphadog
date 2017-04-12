/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-15")
public class HrChatUnreadCountDO implements org.apache.thrift.TBase<HrChatUnreadCountDO, HrChatUnreadCountDO._Fields>, java.io.Serializable, Cloneable, Comparable<HrChatUnreadCountDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrChatUnreadCountDO");

  private static final org.apache.thrift.protocol.TField ROOM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("roomId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HR_UNREAD_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("hrUnreadCount", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField USER_UNREAD_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("userUnreadCount", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField HR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("hrId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrChatUnreadCountDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrChatUnreadCountDOTupleSchemeFactory();

  public int roomId; // optional
  public int hrUnreadCount; // optional
  public int userUnreadCount; // optional
  public int hrId; // optional
  public int userId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROOM_ID((short)1, "roomId"),
    HR_UNREAD_COUNT((short)2, "hrUnreadCount"),
    USER_UNREAD_COUNT((short)3, "userUnreadCount"),
    HR_ID((short)4, "hrId"),
    USER_ID((short)5, "userId");

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
        case 1: // ROOM_ID
          return ROOM_ID;
        case 2: // HR_UNREAD_COUNT
          return HR_UNREAD_COUNT;
        case 3: // USER_UNREAD_COUNT
          return USER_UNREAD_COUNT;
        case 4: // HR_ID
          return HR_ID;
        case 5: // USER_ID
          return USER_ID;
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
  private static final int __ROOMID_ISSET_ID = 0;
  private static final int __HRUNREADCOUNT_ISSET_ID = 1;
  private static final int __USERUNREADCOUNT_ISSET_ID = 2;
  private static final int __HRID_ISSET_ID = 3;
  private static final int __USERID_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ROOM_ID,_Fields.HR_UNREAD_COUNT,_Fields.USER_UNREAD_COUNT,_Fields.HR_ID,_Fields.USER_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ROOM_ID, new org.apache.thrift.meta_data.FieldMetaData("roomId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HR_UNREAD_COUNT, new org.apache.thrift.meta_data.FieldMetaData("hrUnreadCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_UNREAD_COUNT, new org.apache.thrift.meta_data.FieldMetaData("userUnreadCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HR_ID, new org.apache.thrift.meta_data.FieldMetaData("hrId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrChatUnreadCountDO.class, metaDataMap);
  }

  public HrChatUnreadCountDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrChatUnreadCountDO(HrChatUnreadCountDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.roomId = other.roomId;
    this.hrUnreadCount = other.hrUnreadCount;
    this.userUnreadCount = other.userUnreadCount;
    this.hrId = other.hrId;
    this.userId = other.userId;
  }

  public HrChatUnreadCountDO deepCopy() {
    return new HrChatUnreadCountDO(this);
  }

  @Override
  public void clear() {
    setRoomIdIsSet(false);
    this.roomId = 0;
    setHrUnreadCountIsSet(false);
    this.hrUnreadCount = 0;
    setUserUnreadCountIsSet(false);
    this.userUnreadCount = 0;
    setHrIdIsSet(false);
    this.hrId = 0;
    setUserIdIsSet(false);
    this.userId = 0;
  }

  public int getRoomId() {
    return this.roomId;
  }

  public HrChatUnreadCountDO setRoomId(int roomId) {
    this.roomId = roomId;
    setRoomIdIsSet(true);
    return this;
  }

  public void unsetRoomId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ROOMID_ISSET_ID);
  }

  /** Returns true if field roomId is set (has been assigned a value) and false otherwise */
  public boolean isSetRoomId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ROOMID_ISSET_ID);
  }

  public void setRoomIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ROOMID_ISSET_ID, value);
  }

  public int getHrUnreadCount() {
    return this.hrUnreadCount;
  }

  public HrChatUnreadCountDO setHrUnreadCount(int hrUnreadCount) {
    this.hrUnreadCount = hrUnreadCount;
    setHrUnreadCountIsSet(true);
    return this;
  }

  public void unsetHrUnreadCount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __HRUNREADCOUNT_ISSET_ID);
  }

  /** Returns true if field hrUnreadCount is set (has been assigned a value) and false otherwise */
  public boolean isSetHrUnreadCount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __HRUNREADCOUNT_ISSET_ID);
  }

  public void setHrUnreadCountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __HRUNREADCOUNT_ISSET_ID, value);
  }

  public int getUserUnreadCount() {
    return this.userUnreadCount;
  }

  public HrChatUnreadCountDO setUserUnreadCount(int userUnreadCount) {
    this.userUnreadCount = userUnreadCount;
    setUserUnreadCountIsSet(true);
    return this;
  }

  public void unsetUserUnreadCount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USERUNREADCOUNT_ISSET_ID);
  }

  /** Returns true if field userUnreadCount is set (has been assigned a value) and false otherwise */
  public boolean isSetUserUnreadCount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USERUNREADCOUNT_ISSET_ID);
  }

  public void setUserUnreadCountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USERUNREADCOUNT_ISSET_ID, value);
  }

  public int getHrId() {
    return this.hrId;
  }

  public HrChatUnreadCountDO setHrId(int hrId) {
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

  public int getUserId() {
    return this.userId;
  }

  public HrChatUnreadCountDO setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);
    return this;
  }

  public void unsetUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ROOM_ID:
      if (value == null) {
        unsetRoomId();
      } else {
        setRoomId((java.lang.Integer)value);
      }
      break;

    case HR_UNREAD_COUNT:
      if (value == null) {
        unsetHrUnreadCount();
      } else {
        setHrUnreadCount((java.lang.Integer)value);
      }
      break;

    case USER_UNREAD_COUNT:
      if (value == null) {
        unsetUserUnreadCount();
      } else {
        setUserUnreadCount((java.lang.Integer)value);
      }
      break;

    case HR_ID:
      if (value == null) {
        unsetHrId();
      } else {
        setHrId((java.lang.Integer)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ROOM_ID:
      return getRoomId();

    case HR_UNREAD_COUNT:
      return getHrUnreadCount();

    case USER_UNREAD_COUNT:
      return getUserUnreadCount();

    case HR_ID:
      return getHrId();

    case USER_ID:
      return getUserId();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ROOM_ID:
      return isSetRoomId();
    case HR_UNREAD_COUNT:
      return isSetHrUnreadCount();
    case USER_UNREAD_COUNT:
      return isSetUserUnreadCount();
    case HR_ID:
      return isSetHrId();
    case USER_ID:
      return isSetUserId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof HrChatUnreadCountDO)
      return this.equals((HrChatUnreadCountDO)that);
    return false;
  }

  public boolean equals(HrChatUnreadCountDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_roomId = true && this.isSetRoomId();
    boolean that_present_roomId = true && that.isSetRoomId();
    if (this_present_roomId || that_present_roomId) {
      if (!(this_present_roomId && that_present_roomId))
        return false;
      if (this.roomId != that.roomId)
        return false;
    }

    boolean this_present_hrUnreadCount = true && this.isSetHrUnreadCount();
    boolean that_present_hrUnreadCount = true && that.isSetHrUnreadCount();
    if (this_present_hrUnreadCount || that_present_hrUnreadCount) {
      if (!(this_present_hrUnreadCount && that_present_hrUnreadCount))
        return false;
      if (this.hrUnreadCount != that.hrUnreadCount)
        return false;
    }

    boolean this_present_userUnreadCount = true && this.isSetUserUnreadCount();
    boolean that_present_userUnreadCount = true && that.isSetUserUnreadCount();
    if (this_present_userUnreadCount || that_present_userUnreadCount) {
      if (!(this_present_userUnreadCount && that_present_userUnreadCount))
        return false;
      if (this.userUnreadCount != that.userUnreadCount)
        return false;
    }

    boolean this_present_hrId = true && this.isSetHrId();
    boolean that_present_hrId = true && that.isSetHrId();
    if (this_present_hrId || that_present_hrId) {
      if (!(this_present_hrId && that_present_hrId))
        return false;
      if (this.hrId != that.hrId)
        return false;
    }

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetRoomId()) ? 131071 : 524287);
    if (isSetRoomId())
      hashCode = hashCode * 8191 + roomId;

    hashCode = hashCode * 8191 + ((isSetHrUnreadCount()) ? 131071 : 524287);
    if (isSetHrUnreadCount())
      hashCode = hashCode * 8191 + hrUnreadCount;

    hashCode = hashCode * 8191 + ((isSetUserUnreadCount()) ? 131071 : 524287);
    if (isSetUserUnreadCount())
      hashCode = hashCode * 8191 + userUnreadCount;

    hashCode = hashCode * 8191 + ((isSetHrId()) ? 131071 : 524287);
    if (isSetHrId())
      hashCode = hashCode * 8191 + hrId;

    hashCode = hashCode * 8191 + ((isSetUserId()) ? 131071 : 524287);
    if (isSetUserId())
      hashCode = hashCode * 8191 + userId;

    return hashCode;
  }

  @Override
  public int compareTo(HrChatUnreadCountDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetRoomId()).compareTo(other.isSetRoomId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoomId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roomId, other.roomId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetHrUnreadCount()).compareTo(other.isSetHrUnreadCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHrUnreadCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hrUnreadCount, other.hrUnreadCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUserUnreadCount()).compareTo(other.isSetUserUnreadCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserUnreadCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userUnreadCount, other.userUnreadCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = java.lang.Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HrChatUnreadCountDO(");
    boolean first = true;

    if (isSetRoomId()) {
      sb.append("roomId:");
      sb.append(this.roomId);
      first = false;
    }
    if (isSetHrUnreadCount()) {
      if (!first) sb.append(", ");
      sb.append("hrUnreadCount:");
      sb.append(this.hrUnreadCount);
      first = false;
    }
    if (isSetUserUnreadCount()) {
      if (!first) sb.append(", ");
      sb.append("userUnreadCount:");
      sb.append(this.userUnreadCount);
      first = false;
    }
    if (isSetHrId()) {
      if (!first) sb.append(", ");
      sb.append("hrId:");
      sb.append(this.hrId);
      first = false;
    }
    if (isSetUserId()) {
      if (!first) sb.append(", ");
      sb.append("userId:");
      sb.append(this.userId);
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

  private static class HrChatUnreadCountDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrChatUnreadCountDOStandardScheme getScheme() {
      return new HrChatUnreadCountDOStandardScheme();
    }
  }

  private static class HrChatUnreadCountDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrChatUnreadCountDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrChatUnreadCountDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ROOM_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.roomId = iprot.readI32();
              struct.setRoomIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HR_UNREAD_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.hrUnreadCount = iprot.readI32();
              struct.setHrUnreadCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_UNREAD_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userUnreadCount = iprot.readI32();
              struct.setUserUnreadCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // HR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.hrId = iprot.readI32();
              struct.setHrIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrChatUnreadCountDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetRoomId()) {
        oprot.writeFieldBegin(ROOM_ID_FIELD_DESC);
        oprot.writeI32(struct.roomId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetHrUnreadCount()) {
        oprot.writeFieldBegin(HR_UNREAD_COUNT_FIELD_DESC);
        oprot.writeI32(struct.hrUnreadCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUserUnreadCount()) {
        oprot.writeFieldBegin(USER_UNREAD_COUNT_FIELD_DESC);
        oprot.writeI32(struct.userUnreadCount);
        oprot.writeFieldEnd();
      }
      if (struct.isSetHrId()) {
        oprot.writeFieldBegin(HR_ID_FIELD_DESC);
        oprot.writeI32(struct.hrId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUserId()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.userId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrChatUnreadCountDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrChatUnreadCountDOTupleScheme getScheme() {
      return new HrChatUnreadCountDOTupleScheme();
    }
  }

  private static class HrChatUnreadCountDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrChatUnreadCountDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrChatUnreadCountDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetRoomId()) {
        optionals.set(0);
      }
      if (struct.isSetHrUnreadCount()) {
        optionals.set(1);
      }
      if (struct.isSetUserUnreadCount()) {
        optionals.set(2);
      }
      if (struct.isSetHrId()) {
        optionals.set(3);
      }
      if (struct.isSetUserId()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetRoomId()) {
        oprot.writeI32(struct.roomId);
      }
      if (struct.isSetHrUnreadCount()) {
        oprot.writeI32(struct.hrUnreadCount);
      }
      if (struct.isSetUserUnreadCount()) {
        oprot.writeI32(struct.userUnreadCount);
      }
      if (struct.isSetHrId()) {
        oprot.writeI32(struct.hrId);
      }
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrChatUnreadCountDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.roomId = iprot.readI32();
        struct.setRoomIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.hrUnreadCount = iprot.readI32();
        struct.setHrUnreadCountIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userUnreadCount = iprot.readI32();
        struct.setUserUnreadCountIsSet(true);
      }
      if (incoming.get(3)) {
        struct.hrId = iprot.readI32();
        struct.setHrIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
