/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.apps.positionbs.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-11-22")
public class ThirdPartyPositionForm implements org.apache.thrift.TBase<ThirdPartyPositionForm, ThirdPartyPositionForm._Fields>, java.io.Serializable, Cloneable, Comparable<ThirdPartyPositionForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThridPartyPositionForm");

  private static final org.apache.thrift.protocol.TField POSITION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("position_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField APPID_FIELD_DESC = new org.apache.thrift.protocol.TField("appid", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CHANNELS_FIELD_DESC = new org.apache.thrift.protocol.TField("channels", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ThridPartyPositionFormStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ThridPartyPositionFormTupleSchemeFactory());
  }

  public int position_id; // required
  public int appid; // required
  public List<ThirdPartyPosition> channels; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POSITION_ID((short)1, "position_id"),
    APPID((short)2, "appid"),
    CHANNELS((short)3, "channels");

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
        case 1: // POSITION_ID
          return POSITION_ID;
        case 2: // APPID
          return APPID;
        case 3: // CHANNELS
          return CHANNELS;
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
  private static final int __POSITION_ID_ISSET_ID = 0;
  private static final int __APPID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POSITION_ID, new org.apache.thrift.meta_data.FieldMetaData("position_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.APPID, new org.apache.thrift.meta_data.FieldMetaData("appid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CHANNELS, new org.apache.thrift.meta_data.FieldMetaData("channels", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ThirdPartyPosition.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThirdPartyPositionForm.class, metaDataMap);
  }

  public ThirdPartyPositionForm() {
  }

  public ThirdPartyPositionForm(
    int position_id,
    int appid,
    List<ThirdPartyPosition> channels)
  {
    this();
    this.position_id = position_id;
    setPosition_idIsSet(true);
    this.appid = appid;
    setAppidIsSet(true);
    this.channels = channels;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdPartyPositionForm(ThirdPartyPositionForm other) {
    __isset_bitfield = other.__isset_bitfield;
    this.position_id = other.position_id;
    this.appid = other.appid;
    if (other.isSetChannels()) {
      List<ThirdPartyPosition> __this__channels = new ArrayList<ThirdPartyPosition>(other.channels.size());
      for (ThirdPartyPosition other_element : other.channels) {
        __this__channels.add(new ThirdPartyPosition(other_element));
      }
      this.channels = __this__channels;
    }
  }

  public ThirdPartyPositionForm deepCopy() {
    return new ThirdPartyPositionForm(this);
  }

  @Override
  public void clear() {
    setPosition_idIsSet(false);
    this.position_id = 0;
    setAppidIsSet(false);
    this.appid = 0;
    this.channels = null;
  }

  public int getPosition_id() {
    return this.position_id;
  }

  public ThirdPartyPositionForm setPosition_id(int position_id) {
    this.position_id = position_id;
    setPosition_idIsSet(true);
    return this;
  }

  public void unsetPosition_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __POSITION_ID_ISSET_ID);
  }

  /** Returns true if field position_id is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition_id() {
    return EncodingUtils.testBit(__isset_bitfield, __POSITION_ID_ISSET_ID);
  }

  public void setPosition_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __POSITION_ID_ISSET_ID, value);
  }

  public int getAppid() {
    return this.appid;
  }

  public ThirdPartyPositionForm setAppid(int appid) {
    this.appid = appid;
    setAppidIsSet(true);
    return this;
  }

  public void unsetAppid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __APPID_ISSET_ID);
  }

  /** Returns true if field appid is set (has been assigned a value) and false otherwise */
  public boolean isSetAppid() {
    return EncodingUtils.testBit(__isset_bitfield, __APPID_ISSET_ID);
  }

  public void setAppidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __APPID_ISSET_ID, value);
  }

  public int getChannelsSize() {
    return (this.channels == null) ? 0 : this.channels.size();
  }

  public java.util.Iterator<ThirdPartyPosition> getChannelsIterator() {
    return (this.channels == null) ? null : this.channels.iterator();
  }

  public void addToChannels(ThirdPartyPosition elem) {
    if (this.channels == null) {
      this.channels = new ArrayList<ThirdPartyPosition>();
    }
    this.channels.add(elem);
  }

  public List<ThirdPartyPosition> getChannels() {
    return this.channels;
  }

  public ThirdPartyPositionForm setChannels(List<ThirdPartyPosition> channels) {
    this.channels = channels;
    return this;
  }

  public void unsetChannels() {
    this.channels = null;
  }

  /** Returns true if field channels is set (has been assigned a value) and false otherwise */
  public boolean isSetChannels() {
    return this.channels != null;
  }

  public void setChannelsIsSet(boolean value) {
    if (!value) {
      this.channels = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case POSITION_ID:
      if (value == null) {
        unsetPosition_id();
      } else {
        setPosition_id((Integer)value);
      }
      break;

    case APPID:
      if (value == null) {
        unsetAppid();
      } else {
        setAppid((Integer)value);
      }
      break;

    case CHANNELS:
      if (value == null) {
        unsetChannels();
      } else {
        setChannels((List<ThirdPartyPosition>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case POSITION_ID:
      return Integer.valueOf(getPosition_id());

    case APPID:
      return Integer.valueOf(getAppid());

    case CHANNELS:
      return getChannels();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case POSITION_ID:
      return isSetPosition_id();
    case APPID:
      return isSetAppid();
    case CHANNELS:
      return isSetChannels();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdPartyPositionForm)
      return this.equals((ThirdPartyPositionForm)that);
    return false;
  }

  public boolean equals(ThirdPartyPositionForm that) {
    if (that == null)
      return false;

    boolean this_present_position_id = true;
    boolean that_present_position_id = true;
    if (this_present_position_id || that_present_position_id) {
      if (!(this_present_position_id && that_present_position_id))
        return false;
      if (this.position_id != that.position_id)
        return false;
    }

    boolean this_present_appid = true;
    boolean that_present_appid = true;
    if (this_present_appid || that_present_appid) {
      if (!(this_present_appid && that_present_appid))
        return false;
      if (this.appid != that.appid)
        return false;
    }

    boolean this_present_channels = true && this.isSetChannels();
    boolean that_present_channels = true && that.isSetChannels();
    if (this_present_channels || that_present_channels) {
      if (!(this_present_channels && that_present_channels))
        return false;
      if (!this.channels.equals(that.channels))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_position_id = true;
    list.add(present_position_id);
    if (present_position_id)
      list.add(position_id);

    boolean present_appid = true;
    list.add(present_appid);
    if (present_appid)
      list.add(appid);

    boolean present_channels = true && (isSetChannels());
    list.add(present_channels);
    if (present_channels)
      list.add(channels);

    return list.hashCode();
  }

  @Override
  public int compareTo(ThirdPartyPositionForm other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPosition_id()).compareTo(other.isSetPosition_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position_id, other.position_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAppid()).compareTo(other.isSetAppid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAppid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.appid, other.appid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChannels()).compareTo(other.isSetChannels());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChannels()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.channels, other.channels);
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
    StringBuilder sb = new StringBuilder("ThridPartyPositionForm(");
    boolean first = true;

    sb.append("position_id:");
    sb.append(this.position_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("appid:");
    sb.append(this.appid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("channels:");
    if (this.channels == null) {
      sb.append("null");
    } else {
      sb.append(this.channels);
    }
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

  private static class ThridPartyPositionFormStandardSchemeFactory implements SchemeFactory {
    public ThridPartyPositionFormStandardScheme getScheme() {
      return new ThridPartyPositionFormStandardScheme();
    }
  }

  private static class ThridPartyPositionFormStandardScheme extends StandardScheme<ThirdPartyPositionForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThirdPartyPositionForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POSITION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.position_id = iprot.readI32();
              struct.setPosition_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // APPID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.appid = iprot.readI32();
              struct.setAppidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CHANNELS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.channels = new ArrayList<ThirdPartyPosition>(_list0.size);
                ThirdPartyPosition _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new ThirdPartyPosition();
                  _elem1.read(iprot);
                  struct.channels.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setChannelsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThirdPartyPositionForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(POSITION_ID_FIELD_DESC);
      oprot.writeI32(struct.position_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(APPID_FIELD_DESC);
      oprot.writeI32(struct.appid);
      oprot.writeFieldEnd();
      if (struct.channels != null) {
        oprot.writeFieldBegin(CHANNELS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.channels.size()));
          for (ThirdPartyPosition _iter3 : struct.channels)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThridPartyPositionFormTupleSchemeFactory implements SchemeFactory {
    public ThridPartyPositionFormTupleScheme getScheme() {
      return new ThridPartyPositionFormTupleScheme();
    }
  }

  private static class ThridPartyPositionFormTupleScheme extends TupleScheme<ThirdPartyPositionForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThirdPartyPositionForm struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPosition_id()) {
        optionals.set(0);
      }
      if (struct.isSetAppid()) {
        optionals.set(1);
      }
      if (struct.isSetChannels()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPosition_id()) {
        oprot.writeI32(struct.position_id);
      }
      if (struct.isSetAppid()) {
        oprot.writeI32(struct.appid);
      }
      if (struct.isSetChannels()) {
        {
          oprot.writeI32(struct.channels.size());
          for (ThirdPartyPosition _iter4 : struct.channels)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThirdPartyPositionForm struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.position_id = iprot.readI32();
        struct.setPosition_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.appid = iprot.readI32();
        struct.setAppidIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.channels = new ArrayList<ThirdPartyPosition>(_list5.size);
          ThirdPartyPosition _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new ThirdPartyPosition();
            _elem6.read(iprot);
            struct.channels.add(_elem6);
          }
        }
        struct.setChannelsIsSet(true);
      }
    }
  }

}

