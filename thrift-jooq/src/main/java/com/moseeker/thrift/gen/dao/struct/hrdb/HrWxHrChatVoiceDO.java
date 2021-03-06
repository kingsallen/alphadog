/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.hrdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-16")
public class HrWxHrChatVoiceDO implements org.apache.thrift.TBase<HrWxHrChatVoiceDO, HrWxHrChatVoiceDO._Fields>, java.io.Serializable, Cloneable, Comparable<HrWxHrChatVoiceDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrWxHrChatVoiceDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CHAT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("chatId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField SERVER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("serverId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField DURATION_FIELD_DESC = new org.apache.thrift.protocol.TField("duration", org.apache.thrift.protocol.TType.BYTE, (short)4);
  private static final org.apache.thrift.protocol.TField LOCAL_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("localUrl", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("updateTime", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrWxHrChatVoiceDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrWxHrChatVoiceDOTupleSchemeFactory();

  public int id; // optional
  public int chatId; // optional
  public String serverId; // optional
  public byte duration; // optional
  public String localUrl; // optional
  public String createTime; // optional
  public String updateTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    CHAT_ID((short)2, "chatId"),
    SERVER_ID((short)3, "serverId"),
    DURATION((short)4, "duration"),
    LOCAL_URL((short)5, "localUrl"),
    CREATE_TIME((short)6, "createTime"),
    UPDATE_TIME((short)7, "updateTime");

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
        case 1: // ID
          return ID;
        case 2: // CHAT_ID
          return CHAT_ID;
        case 3: // SERVER_ID
          return SERVER_ID;
        case 4: // DURATION
          return DURATION;
        case 5: // LOCAL_URL
          return LOCAL_URL;
        case 6: // CREATE_TIME
          return CREATE_TIME;
        case 7: // UPDATE_TIME
          return UPDATE_TIME;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __CHATID_ISSET_ID = 1;
  private static final int __DURATION_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID, _Fields.CHAT_ID, _Fields.SERVER_ID, _Fields.DURATION, _Fields.LOCAL_URL, _Fields.CREATE_TIME, _Fields.UPDATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CHAT_ID, new org.apache.thrift.meta_data.FieldMetaData("chatId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SERVER_ID, new org.apache.thrift.meta_data.FieldMetaData("serverId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DURATION, new org.apache.thrift.meta_data.FieldMetaData("duration", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.LOCAL_URL, new org.apache.thrift.meta_data.FieldMetaData("localUrl", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("updateTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrWxHrChatVoiceDO.class, metaDataMap);
  }

  public HrWxHrChatVoiceDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrWxHrChatVoiceDO(HrWxHrChatVoiceDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.chatId = other.chatId;
    if (other.isSetServerId()) {
      this.serverId = other.serverId;
    }
    this.duration = other.duration;
    if (other.isSetLocalUrl()) {
      this.localUrl = other.localUrl;
    }
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
    if (other.isSetUpdateTime()) {
      this.updateTime = other.updateTime;
    }
  }

  public HrWxHrChatVoiceDO deepCopy() {
    return new HrWxHrChatVoiceDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setChatIdIsSet(false);
    this.chatId = 0;
    this.serverId = null;
    setDurationIsSet(false);
    this.duration = 0;
    this.localUrl = null;
    this.createTime = null;
    this.updateTime = null;
  }

  public int getId() {
    return this.id;
  }

  public HrWxHrChatVoiceDO setId(int id) {
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

  public int getChatId() {
    return this.chatId;
  }

  public HrWxHrChatVoiceDO setChatId(int chatId) {
    this.chatId = chatId;
    setChatIdIsSet(true);
    return this;
  }

  public void unsetChatId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CHATID_ISSET_ID);
  }

  /** Returns true if field chatId is set (has been assigned a value) and false otherwise */
  public boolean isSetChatId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CHATID_ISSET_ID);
  }

  public void setChatIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CHATID_ISSET_ID, value);
  }

  public String getServerId() {
    return this.serverId;
  }

  public HrWxHrChatVoiceDO setServerId(String serverId) {
    this.serverId = serverId;
    return this;
  }

  public void unsetServerId() {
    this.serverId = null;
  }

  /** Returns true if field serverId is set (has been assigned a value) and false otherwise */
  public boolean isSetServerId() {
    return this.serverId != null;
  }

  public void setServerIdIsSet(boolean value) {
    if (!value) {
      this.serverId = null;
    }
  }

  public byte getDuration() {
    return this.duration;
  }

  public HrWxHrChatVoiceDO setDuration(byte duration) {
    this.duration = duration;
    setDurationIsSet(true);
    return this;
  }

  public void unsetDuration() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DURATION_ISSET_ID);
  }

  /** Returns true if field duration is set (has been assigned a value) and false otherwise */
  public boolean isSetDuration() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DURATION_ISSET_ID);
  }

  public void setDurationIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DURATION_ISSET_ID, value);
  }

  public String getLocalUrl() {
    return this.localUrl;
  }

  public HrWxHrChatVoiceDO setLocalUrl(String localUrl) {
    this.localUrl = localUrl;
    return this;
  }

  public void unsetLocalUrl() {
    this.localUrl = null;
  }

  /** Returns true if field localUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetLocalUrl() {
    return this.localUrl != null;
  }

  public void setLocalUrlIsSet(boolean value) {
    if (!value) {
      this.localUrl = null;
    }
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public HrWxHrChatVoiceDO setCreateTime(String createTime) {
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

  public String getUpdateTime() {
    return this.updateTime;
  }

  public HrWxHrChatVoiceDO setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public void unsetUpdateTime() {
    this.updateTime = null;
  }

  /** Returns true if field updateTime is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdateTime() {
    return this.updateTime != null;
  }

  public void setUpdateTimeIsSet(boolean value) {
    if (!value) {
      this.updateTime = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case CHAT_ID:
      if (value == null) {
        unsetChatId();
      } else {
        setChatId((Integer)value);
      }
      break;

    case SERVER_ID:
      if (value == null) {
        unsetServerId();
      } else {
        setServerId((String)value);
      }
      break;

    case DURATION:
      if (value == null) {
        unsetDuration();
      } else {
        setDuration((Byte)value);
      }
      break;

    case LOCAL_URL:
      if (value == null) {
        unsetLocalUrl();
      } else {
        setLocalUrl((String)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((String)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdateTime();
      } else {
        setUpdateTime((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case CHAT_ID:
      return getChatId();

    case SERVER_ID:
      return getServerId();

    case DURATION:
      return getDuration();

    case LOCAL_URL:
      return getLocalUrl();

    case CREATE_TIME:
      return getCreateTime();

    case UPDATE_TIME:
      return getUpdateTime();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case CHAT_ID:
      return isSetChatId();
    case SERVER_ID:
      return isSetServerId();
    case DURATION:
      return isSetDuration();
    case LOCAL_URL:
      return isSetLocalUrl();
    case CREATE_TIME:
      return isSetCreateTime();
    case UPDATE_TIME:
      return isSetUpdateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HrWxHrChatVoiceDO)
      return this.equals((HrWxHrChatVoiceDO)that);
    return false;
  }

  public boolean equals(HrWxHrChatVoiceDO that) {
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

    boolean this_present_chatId = true && this.isSetChatId();
    boolean that_present_chatId = true && that.isSetChatId();
    if (this_present_chatId || that_present_chatId) {
      if (!(this_present_chatId && that_present_chatId))
        return false;
      if (this.chatId != that.chatId)
        return false;
    }

    boolean this_present_serverId = true && this.isSetServerId();
    boolean that_present_serverId = true && that.isSetServerId();
    if (this_present_serverId || that_present_serverId) {
      if (!(this_present_serverId && that_present_serverId))
        return false;
      if (!this.serverId.equals(that.serverId))
        return false;
    }

    boolean this_present_duration = true && this.isSetDuration();
    boolean that_present_duration = true && that.isSetDuration();
    if (this_present_duration || that_present_duration) {
      if (!(this_present_duration && that_present_duration))
        return false;
      if (this.duration != that.duration)
        return false;
    }

    boolean this_present_localUrl = true && this.isSetLocalUrl();
    boolean that_present_localUrl = true && that.isSetLocalUrl();
    if (this_present_localUrl || that_present_localUrl) {
      if (!(this_present_localUrl && that_present_localUrl))
        return false;
      if (!this.localUrl.equals(that.localUrl))
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

    boolean this_present_updateTime = true && this.isSetUpdateTime();
    boolean that_present_updateTime = true && that.isSetUpdateTime();
    if (this_present_updateTime || that_present_updateTime) {
      if (!(this_present_updateTime && that_present_updateTime))
        return false;
      if (!this.updateTime.equals(that.updateTime))
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

    hashCode = hashCode * 8191 + ((isSetChatId()) ? 131071 : 524287);
    if (isSetChatId())
      hashCode = hashCode * 8191 + chatId;

    hashCode = hashCode * 8191 + ((isSetServerId()) ? 131071 : 524287);
    if (isSetServerId())
      hashCode = hashCode * 8191 + serverId.hashCode();

    hashCode = hashCode * 8191 + ((isSetDuration()) ? 131071 : 524287);
    if (isSetDuration())
      hashCode = hashCode * 8191 + (int) (duration);

    hashCode = hashCode * 8191 + ((isSetLocalUrl()) ? 131071 : 524287);
    if (isSetLocalUrl())
      hashCode = hashCode * 8191 + localUrl.hashCode();

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    hashCode = hashCode * 8191 + ((isSetUpdateTime()) ? 131071 : 524287);
    if (isSetUpdateTime())
      hashCode = hashCode * 8191 + updateTime.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HrWxHrChatVoiceDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChatId()).compareTo(other.isSetChatId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChatId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chatId, other.chatId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetServerId()).compareTo(other.isSetServerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serverId, other.serverId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDuration()).compareTo(other.isSetDuration());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDuration()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.duration, other.duration);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLocalUrl()).compareTo(other.isSetLocalUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLocalUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.localUrl, other.localUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdateTime()).compareTo(other.isSetUpdateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateTime, other.updateTime);
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
    StringBuilder sb = new StringBuilder("HrWxHrChatVoiceDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetChatId()) {
      if (!first) sb.append(", ");
      sb.append("chatId:");
      sb.append(this.chatId);
      first = false;
    }
    if (isSetServerId()) {
      if (!first) sb.append(", ");
      sb.append("serverId:");
      if (this.serverId == null) {
        sb.append("null");
      } else {
        sb.append(this.serverId);
      }
      first = false;
    }
    if (isSetDuration()) {
      if (!first) sb.append(", ");
      sb.append("duration:");
      sb.append(this.duration);
      first = false;
    }
    if (isSetLocalUrl()) {
      if (!first) sb.append(", ");
      sb.append("localUrl:");
      if (this.localUrl == null) {
        sb.append("null");
      } else {
        sb.append(this.localUrl);
      }
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
    if (isSetUpdateTime()) {
      if (!first) sb.append(", ");
      sb.append("updateTime:");
      if (this.updateTime == null) {
        sb.append("null");
      } else {
        sb.append(this.updateTime);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HrWxHrChatVoiceDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrWxHrChatVoiceDOStandardScheme getScheme() {
      return new HrWxHrChatVoiceDOStandardScheme();
    }
  }

  private static class HrWxHrChatVoiceDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrWxHrChatVoiceDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrWxHrChatVoiceDO struct) throws org.apache.thrift.TException {
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
          case 2: // CHAT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.chatId = iprot.readI32();
              struct.setChatIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SERVER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serverId = iprot.readString();
              struct.setServerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DURATION
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.duration = iprot.readByte();
              struct.setDurationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // LOCAL_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.localUrl = iprot.readString();
              struct.setLocalUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createTime = iprot.readString();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.updateTime = iprot.readString();
              struct.setUpdateTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrWxHrChatVoiceDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetChatId()) {
        oprot.writeFieldBegin(CHAT_ID_FIELD_DESC);
        oprot.writeI32(struct.chatId);
        oprot.writeFieldEnd();
      }
      if (struct.serverId != null) {
        if (struct.isSetServerId()) {
          oprot.writeFieldBegin(SERVER_ID_FIELD_DESC);
          oprot.writeString(struct.serverId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDuration()) {
        oprot.writeFieldBegin(DURATION_FIELD_DESC);
        oprot.writeByte(struct.duration);
        oprot.writeFieldEnd();
      }
      if (struct.localUrl != null) {
        if (struct.isSetLocalUrl()) {
          oprot.writeFieldBegin(LOCAL_URL_FIELD_DESC);
          oprot.writeString(struct.localUrl);
          oprot.writeFieldEnd();
        }
      }
      if (struct.createTime != null) {
        if (struct.isSetCreateTime()) {
          oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
          oprot.writeString(struct.createTime);
          oprot.writeFieldEnd();
        }
      }
      if (struct.updateTime != null) {
        if (struct.isSetUpdateTime()) {
          oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
          oprot.writeString(struct.updateTime);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrWxHrChatVoiceDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrWxHrChatVoiceDOTupleScheme getScheme() {
      return new HrWxHrChatVoiceDOTupleScheme();
    }
  }

  private static class HrWxHrChatVoiceDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrWxHrChatVoiceDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrWxHrChatVoiceDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetChatId()) {
        optionals.set(1);
      }
      if (struct.isSetServerId()) {
        optionals.set(2);
      }
      if (struct.isSetDuration()) {
        optionals.set(3);
      }
      if (struct.isSetLocalUrl()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(5);
      }
      if (struct.isSetUpdateTime()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetChatId()) {
        oprot.writeI32(struct.chatId);
      }
      if (struct.isSetServerId()) {
        oprot.writeString(struct.serverId);
      }
      if (struct.isSetDuration()) {
        oprot.writeByte(struct.duration);
      }
      if (struct.isSetLocalUrl()) {
        oprot.writeString(struct.localUrl);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
      if (struct.isSetUpdateTime()) {
        oprot.writeString(struct.updateTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrWxHrChatVoiceDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.chatId = iprot.readI32();
        struct.setChatIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.serverId = iprot.readString();
        struct.setServerIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.duration = iprot.readByte();
        struct.setDurationIsSet(true);
      }
      if (incoming.get(4)) {
        struct.localUrl = iprot.readString();
        struct.setLocalUrlIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.updateTime = iprot.readString();
        struct.setUpdateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

