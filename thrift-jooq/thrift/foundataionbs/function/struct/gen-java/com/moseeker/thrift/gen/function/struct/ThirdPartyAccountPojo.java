/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.function.struct;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-11-8")
public class ThirdPartyAccountPojo implements org.apache.thrift.TBase<ThirdPartyAccountPojo, ThirdPartyAccountPojo._Fields>, java.io.Serializable, Cloneable, Comparable<ThirdPartyAccountPojo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThirdPartyAccountPojo");

  private static final org.apache.thrift.protocol.TField USERNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("username", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PASSWORD_FIELD_DESC = new org.apache.thrift.protocol.TField("password", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MEMBER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("memberName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField REMAIN_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("remainNum", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField SYNC_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("syncTime", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CHANNEL_FIELD_DESC = new org.apache.thrift.protocol.TField("channel", org.apache.thrift.protocol.TType.BYTE, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ThirdPartyAccountPojoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ThirdPartyAccountPojoTupleSchemeFactory());
  }

  public String username; // required
  public String password; // required
  public String memberName; // required
  public int remainNum; // required
  public String syncTime; // required
  public byte channel; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USERNAME((short)1, "username"),
    PASSWORD((short)2, "password"),
    MEMBER_NAME((short)3, "memberName"),
    REMAIN_NUM((short)4, "remainNum"),
    SYNC_TIME((short)5, "syncTime"),
    CHANNEL((short)6, "channel");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // USERNAME
          return USERNAME;
        case 2: // PASSWORD
          return PASSWORD;
        case 3: // MEMBER_NAME
          return MEMBER_NAME;
        case 4: // REMAIN_NUM
          return REMAIN_NUM;
        case 5: // SYNC_TIME
          return SYNC_TIME;
        case 6: // CHANNEL
          return CHANNEL;
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
  private static final int __REMAINNUM_ISSET_ID = 0;
  private static final int __CHANNEL_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USERNAME, new org.apache.thrift.meta_data.FieldMetaData("username", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PASSWORD, new org.apache.thrift.meta_data.FieldMetaData("password", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_NAME, new org.apache.thrift.meta_data.FieldMetaData("memberName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REMAIN_NUM, new org.apache.thrift.meta_data.FieldMetaData("remainNum", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SYNC_TIME, new org.apache.thrift.meta_data.FieldMetaData("syncTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHANNEL, new org.apache.thrift.meta_data.FieldMetaData("channel", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThirdPartyAccountPojo.class, metaDataMap);
  }

  public ThirdPartyAccountPojo() {
  }

  public ThirdPartyAccountPojo(
    String username,
    String password,
    String memberName,
    int remainNum,
    String syncTime,
    byte channel)
  {
    this();
    this.username = username;
    this.password = password;
    this.memberName = memberName;
    this.remainNum = remainNum;
    setRemainNumIsSet(true);
    this.syncTime = syncTime;
    this.channel = channel;
    setChannelIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdPartyAccountPojo(ThirdPartyAccountPojo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUsername()) {
      this.username = other.username;
    }
    if (other.isSetPassword()) {
      this.password = other.password;
    }
    if (other.isSetMemberName()) {
      this.memberName = other.memberName;
    }
    this.remainNum = other.remainNum;
    if (other.isSetSyncTime()) {
      this.syncTime = other.syncTime;
    }
    this.channel = other.channel;
  }

  public ThirdPartyAccountPojo deepCopy() {
    return new ThirdPartyAccountPojo(this);
  }

  @Override
  public void clear() {
    this.username = null;
    this.password = null;
    this.memberName = null;
    setRemainNumIsSet(false);
    this.remainNum = 0;
    this.syncTime = null;
    setChannelIsSet(false);
    this.channel = 0;
  }

  public String getUsername() {
    return this.username;
  }

  public ThirdPartyAccountPojo setUsername(String username) {
    this.username = username;
    return this;
  }

  public void unsetUsername() {
    this.username = null;
  }

  /** Returns true if field username is set (has been assigned a value) and false otherwise */
  public boolean isSetUsername() {
    return this.username != null;
  }

  public void setUsernameIsSet(boolean value) {
    if (!value) {
      this.username = null;
    }
  }

  public String getPassword() {
    return this.password;
  }

  public ThirdPartyAccountPojo setPassword(String password) {
    this.password = password;
    return this;
  }

  public void unsetPassword() {
    this.password = null;
  }

  /** Returns true if field password is set (has been assigned a value) and false otherwise */
  public boolean isSetPassword() {
    return this.password != null;
  }

  public void setPasswordIsSet(boolean value) {
    if (!value) {
      this.password = null;
    }
  }

  public String getMemberName() {
    return this.memberName;
  }

  public ThirdPartyAccountPojo setMemberName(String memberName) {
    this.memberName = memberName;
    return this;
  }

  public void unsetMemberName() {
    this.memberName = null;
  }

  /** Returns true if field memberName is set (has been assigned a value) and false otherwise */
  public boolean isSetMemberName() {
    return this.memberName != null;
  }

  public void setMemberNameIsSet(boolean value) {
    if (!value) {
      this.memberName = null;
    }
  }

  public int getRemainNum() {
    return this.remainNum;
  }

  public ThirdPartyAccountPojo setRemainNum(int remainNum) {
    this.remainNum = remainNum;
    setRemainNumIsSet(true);
    return this;
  }

  public void unsetRemainNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REMAINNUM_ISSET_ID);
  }

  /** Returns true if field remainNum is set (has been assigned a value) and false otherwise */
  public boolean isSetRemainNum() {
    return EncodingUtils.testBit(__isset_bitfield, __REMAINNUM_ISSET_ID);
  }

  public void setRemainNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REMAINNUM_ISSET_ID, value);
  }

  public String getSyncTime() {
    return this.syncTime;
  }

  public ThirdPartyAccountPojo setSyncTime(String syncTime) {
    this.syncTime = syncTime;
    return this;
  }

  public void unsetSyncTime() {
    this.syncTime = null;
  }

  /** Returns true if field syncTime is set (has been assigned a value) and false otherwise */
  public boolean isSetSyncTime() {
    return this.syncTime != null;
  }

  public void setSyncTimeIsSet(boolean value) {
    if (!value) {
      this.syncTime = null;
    }
  }

  public byte getChannel() {
    return this.channel;
  }

  public ThirdPartyAccountPojo setChannel(byte channel) {
    this.channel = channel;
    setChannelIsSet(true);
    return this;
  }

  public void unsetChannel() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CHANNEL_ISSET_ID);
  }

  /** Returns true if field channel is set (has been assigned a value) and false otherwise */
  public boolean isSetChannel() {
    return EncodingUtils.testBit(__isset_bitfield, __CHANNEL_ISSET_ID);
  }

  public void setChannelIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CHANNEL_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USERNAME:
      if (value == null) {
        unsetUsername();
      } else {
        setUsername((String)value);
      }
      break;

    case PASSWORD:
      if (value == null) {
        unsetPassword();
      } else {
        setPassword((String)value);
      }
      break;

    case MEMBER_NAME:
      if (value == null) {
        unsetMemberName();
      } else {
        setMemberName((String)value);
      }
      break;

    case REMAIN_NUM:
      if (value == null) {
        unsetRemainNum();
      } else {
        setRemainNum((Integer)value);
      }
      break;

    case SYNC_TIME:
      if (value == null) {
        unsetSyncTime();
      } else {
        setSyncTime((String)value);
      }
      break;

    case CHANNEL:
      if (value == null) {
        unsetChannel();
      } else {
        setChannel((Byte)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USERNAME:
      return getUsername();

    case PASSWORD:
      return getPassword();

    case MEMBER_NAME:
      return getMemberName();

    case REMAIN_NUM:
      return Integer.valueOf(getRemainNum());

    case SYNC_TIME:
      return getSyncTime();

    case CHANNEL:
      return Byte.valueOf(getChannel());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USERNAME:
      return isSetUsername();
    case PASSWORD:
      return isSetPassword();
    case MEMBER_NAME:
      return isSetMemberName();
    case REMAIN_NUM:
      return isSetRemainNum();
    case SYNC_TIME:
      return isSetSyncTime();
    case CHANNEL:
      return isSetChannel();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdPartyAccountPojo)
      return this.equals((ThirdPartyAccountPojo)that);
    return false;
  }

  public boolean equals(ThirdPartyAccountPojo that) {
    if (that == null)
      return false;

    boolean this_present_username = true && this.isSetUsername();
    boolean that_present_username = true && that.isSetUsername();
    if (this_present_username || that_present_username) {
      if (!(this_present_username && that_present_username))
        return false;
      if (!this.username.equals(that.username))
        return false;
    }

    boolean this_present_password = true && this.isSetPassword();
    boolean that_present_password = true && that.isSetPassword();
    if (this_present_password || that_present_password) {
      if (!(this_present_password && that_present_password))
        return false;
      if (!this.password.equals(that.password))
        return false;
    }

    boolean this_present_memberName = true && this.isSetMemberName();
    boolean that_present_memberName = true && that.isSetMemberName();
    if (this_present_memberName || that_present_memberName) {
      if (!(this_present_memberName && that_present_memberName))
        return false;
      if (!this.memberName.equals(that.memberName))
        return false;
    }

    boolean this_present_remainNum = true;
    boolean that_present_remainNum = true;
    if (this_present_remainNum || that_present_remainNum) {
      if (!(this_present_remainNum && that_present_remainNum))
        return false;
      if (this.remainNum != that.remainNum)
        return false;
    }

    boolean this_present_syncTime = true && this.isSetSyncTime();
    boolean that_present_syncTime = true && that.isSetSyncTime();
    if (this_present_syncTime || that_present_syncTime) {
      if (!(this_present_syncTime && that_present_syncTime))
        return false;
      if (!this.syncTime.equals(that.syncTime))
        return false;
    }

    boolean this_present_channel = true;
    boolean that_present_channel = true;
    if (this_present_channel || that_present_channel) {
      if (!(this_present_channel && that_present_channel))
        return false;
      if (this.channel != that.channel)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_username = true && (isSetUsername());
    list.add(present_username);
    if (present_username)
      list.add(username);

    boolean present_password = true && (isSetPassword());
    list.add(present_password);
    if (present_password)
      list.add(password);

    boolean present_memberName = true && (isSetMemberName());
    list.add(present_memberName);
    if (present_memberName)
      list.add(memberName);

    boolean present_remainNum = true;
    list.add(present_remainNum);
    if (present_remainNum)
      list.add(remainNum);

    boolean present_syncTime = true && (isSetSyncTime());
    list.add(present_syncTime);
    if (present_syncTime)
      list.add(syncTime);

    boolean present_channel = true;
    list.add(present_channel);
    if (present_channel)
      list.add(channel);

    return list.hashCode();
  }

  @Override
  public int compareTo(ThirdPartyAccountPojo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUsername()).compareTo(other.isSetUsername());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUsername()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.username, other.username);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPassword()).compareTo(other.isSetPassword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassword()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.password, other.password);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMemberName()).compareTo(other.isSetMemberName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMemberName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.memberName, other.memberName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRemainNum()).compareTo(other.isSetRemainNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRemainNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.remainNum, other.remainNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSyncTime()).compareTo(other.isSetSyncTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSyncTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.syncTime, other.syncTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChannel()).compareTo(other.isSetChannel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChannel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.channel, other.channel);
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ThirdPartyAccountPojo(");
    boolean first = true;

    sb.append("username:");
    if (this.username == null) {
      sb.append("null");
    } else {
      sb.append(this.username);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("password:");
    if (this.password == null) {
      sb.append("null");
    } else {
      sb.append(this.password);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("memberName:");
    if (this.memberName == null) {
      sb.append("null");
    } else {
      sb.append(this.memberName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("remainNum:");
    sb.append(this.remainNum);
    first = false;
    if (!first) sb.append(", ");
    sb.append("syncTime:");
    if (this.syncTime == null) {
      sb.append("null");
    } else {
      sb.append(this.syncTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("channel:");
    sb.append(this.channel);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ThirdPartyAccountPojoStandardSchemeFactory implements SchemeFactory {
    public ThirdPartyAccountPojoStandardScheme getScheme() {
      return new ThirdPartyAccountPojoStandardScheme();
    }
  }

  private static class ThirdPartyAccountPojoStandardScheme extends StandardScheme<ThirdPartyAccountPojo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThirdPartyAccountPojo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USERNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.username = iprot.readString();
              struct.setUsernameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PASSWORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.password = iprot.readString();
              struct.setPasswordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MEMBER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.memberName = iprot.readString();
              struct.setMemberNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REMAIN_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.remainNum = iprot.readI32();
              struct.setRemainNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SYNC_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.syncTime = iprot.readString();
              struct.setSyncTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CHANNEL
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.channel = iprot.readByte();
              struct.setChannelIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThirdPartyAccountPojo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.username != null) {
        oprot.writeFieldBegin(USERNAME_FIELD_DESC);
        oprot.writeString(struct.username);
        oprot.writeFieldEnd();
      }
      if (struct.password != null) {
        oprot.writeFieldBegin(PASSWORD_FIELD_DESC);
        oprot.writeString(struct.password);
        oprot.writeFieldEnd();
      }
      if (struct.memberName != null) {
        oprot.writeFieldBegin(MEMBER_NAME_FIELD_DESC);
        oprot.writeString(struct.memberName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(REMAIN_NUM_FIELD_DESC);
      oprot.writeI32(struct.remainNum);
      oprot.writeFieldEnd();
      if (struct.syncTime != null) {
        oprot.writeFieldBegin(SYNC_TIME_FIELD_DESC);
        oprot.writeString(struct.syncTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CHANNEL_FIELD_DESC);
      oprot.writeByte(struct.channel);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThirdPartyAccountPojoTupleSchemeFactory implements SchemeFactory {
    public ThirdPartyAccountPojoTupleScheme getScheme() {
      return new ThirdPartyAccountPojoTupleScheme();
    }
  }

  private static class ThirdPartyAccountPojoTupleScheme extends TupleScheme<ThirdPartyAccountPojo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThirdPartyAccountPojo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUsername()) {
        optionals.set(0);
      }
      if (struct.isSetPassword()) {
        optionals.set(1);
      }
      if (struct.isSetMemberName()) {
        optionals.set(2);
      }
      if (struct.isSetRemainNum()) {
        optionals.set(3);
      }
      if (struct.isSetSyncTime()) {
        optionals.set(4);
      }
      if (struct.isSetChannel()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetUsername()) {
        oprot.writeString(struct.username);
      }
      if (struct.isSetPassword()) {
        oprot.writeString(struct.password);
      }
      if (struct.isSetMemberName()) {
        oprot.writeString(struct.memberName);
      }
      if (struct.isSetRemainNum()) {
        oprot.writeI32(struct.remainNum);
      }
      if (struct.isSetSyncTime()) {
        oprot.writeString(struct.syncTime);
      }
      if (struct.isSetChannel()) {
        oprot.writeByte(struct.channel);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThirdPartyAccountPojo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.username = iprot.readString();
        struct.setUsernameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.password = iprot.readString();
        struct.setPasswordIsSet(true);
      }
      if (incoming.get(2)) {
        struct.memberName = iprot.readString();
        struct.setMemberNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.remainNum = iprot.readI32();
        struct.setRemainNumIsSet(true);
      }
      if (incoming.get(4)) {
        struct.syncTime = iprot.readString();
        struct.setSyncTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.channel = iprot.readByte();
        struct.setChannelIsSet(true);
      }
    }
  }

}
