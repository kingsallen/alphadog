/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-11-10")
public class PostExt implements org.apache.thrift.TBase<PostExt, PostExt._Fields>, java.io.Serializable, Cloneable, Comparable<PostExt> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PostExt");

  private static final org.apache.thrift.protocol.TField OBJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("objectId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PLATFORM_FIELD_DESC = new org.apache.thrift.protocol.TField("platform", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PostExtStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PostExtTupleSchemeFactory());
  }

  public long objectId; // required
  public String version; // required
  public String platform; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OBJECT_ID((short)1, "objectId"),
    VERSION((short)2, "version"),
    PLATFORM((short)3, "platform");

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
        case 1: // OBJECT_ID
          return OBJECT_ID;
        case 2: // VERSION
          return VERSION;
        case 3: // PLATFORM
          return PLATFORM;
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
  private static final int __OBJECTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OBJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("objectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLATFORM, new org.apache.thrift.meta_data.FieldMetaData("platform", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PostExt.class, metaDataMap);
  }

  public PostExt() {
  }

  public PostExt(
    long objectId,
    String version,
    String platform)
  {
    this();
    this.objectId = objectId;
    setObjectIdIsSet(true);
    this.version = version;
    this.platform = platform;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PostExt(PostExt other) {
    __isset_bitfield = other.__isset_bitfield;
    this.objectId = other.objectId;
    if (other.isSetVersion()) {
      this.version = other.version;
    }
    if (other.isSetPlatform()) {
      this.platform = other.platform;
    }
  }

  public PostExt deepCopy() {
    return new PostExt(this);
  }

  @Override
  public void clear() {
    setObjectIdIsSet(false);
    this.objectId = 0;
    this.version = null;
    this.platform = null;
  }

  public long getObjectId() {
    return this.objectId;
  }

  public PostExt setObjectId(long objectId) {
    this.objectId = objectId;
    setObjectIdIsSet(true);
    return this;
  }

  public void unsetObjectId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OBJECTID_ISSET_ID);
  }

  /** Returns true if field objectId is set (has been assigned a value) and false otherwise */
  public boolean isSetObjectId() {
    return EncodingUtils.testBit(__isset_bitfield, __OBJECTID_ISSET_ID);
  }

  public void setObjectIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OBJECTID_ISSET_ID, value);
  }

  public String getVersion() {
    return this.version;
  }

  public PostExt setVersion(String version) {
    this.version = version;
    return this;
  }

  public void unsetVersion() {
    this.version = null;
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return this.version != null;
  }

  public void setVersionIsSet(boolean value) {
    if (!value) {
      this.version = null;
    }
  }

  public String getPlatform() {
    return this.platform;
  }

  public PostExt setPlatform(String platform) {
    this.platform = platform;
    return this;
  }

  public void unsetPlatform() {
    this.platform = null;
  }

  /** Returns true if field platform is set (has been assigned a value) and false otherwise */
  public boolean isSetPlatform() {
    return this.platform != null;
  }

  public void setPlatformIsSet(boolean value) {
    if (!value) {
      this.platform = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OBJECT_ID:
      if (value == null) {
        unsetObjectId();
      } else {
        setObjectId((Long)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((String)value);
      }
      break;

    case PLATFORM:
      if (value == null) {
        unsetPlatform();
      } else {
        setPlatform((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OBJECT_ID:
      return Long.valueOf(getObjectId());

    case VERSION:
      return getVersion();

    case PLATFORM:
      return getPlatform();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OBJECT_ID:
      return isSetObjectId();
    case VERSION:
      return isSetVersion();
    case PLATFORM:
      return isSetPlatform();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PostExt)
      return this.equals((PostExt)that);
    return false;
  }

  public boolean equals(PostExt that) {
    if (that == null)
      return false;

    boolean this_present_objectId = true;
    boolean that_present_objectId = true;
    if (this_present_objectId || that_present_objectId) {
      if (!(this_present_objectId && that_present_objectId))
        return false;
      if (this.objectId != that.objectId)
        return false;
    }

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (!this.version.equals(that.version))
        return false;
    }

    boolean this_present_platform = true && this.isSetPlatform();
    boolean that_present_platform = true && that.isSetPlatform();
    if (this_present_platform || that_present_platform) {
      if (!(this_present_platform && that_present_platform))
        return false;
      if (!this.platform.equals(that.platform))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_objectId = true;
    list.add(present_objectId);
    if (present_objectId)
      list.add(objectId);

    boolean present_version = true && (isSetVersion());
    list.add(present_version);
    if (present_version)
      list.add(version);

    boolean present_platform = true && (isSetPlatform());
    list.add(present_platform);
    if (present_platform)
      list.add(platform);

    return list.hashCode();
  }

  @Override
  public int compareTo(PostExt other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetObjectId()).compareTo(other.isSetObjectId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjectId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.objectId, other.objectId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlatform()).compareTo(other.isSetPlatform());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlatform()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.platform, other.platform);
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
    StringBuilder sb = new StringBuilder("PostExt(");
    boolean first = true;

    sb.append("objectId:");
    sb.append(this.objectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    if (this.version == null) {
      sb.append("null");
    } else {
      sb.append(this.version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("platform:");
    if (this.platform == null) {
      sb.append("null");
    } else {
      sb.append(this.platform);
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

  private static class PostExtStandardSchemeFactory implements SchemeFactory {
    public PostExtStandardScheme getScheme() {
      return new PostExtStandardScheme();
    }
  }

  private static class PostExtStandardScheme extends StandardScheme<PostExt> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PostExt struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OBJECT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.objectId = iprot.readI64();
              struct.setObjectIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.version = iprot.readString();
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PLATFORM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.platform = iprot.readString();
              struct.setPlatformIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PostExt struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(OBJECT_ID_FIELD_DESC);
      oprot.writeI64(struct.objectId);
      oprot.writeFieldEnd();
      if (struct.version != null) {
        oprot.writeFieldBegin(VERSION_FIELD_DESC);
        oprot.writeString(struct.version);
        oprot.writeFieldEnd();
      }
      if (struct.platform != null) {
        oprot.writeFieldBegin(PLATFORM_FIELD_DESC);
        oprot.writeString(struct.platform);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PostExtTupleSchemeFactory implements SchemeFactory {
    public PostExtTupleScheme getScheme() {
      return new PostExtTupleScheme();
    }
  }

  private static class PostExtTupleScheme extends TupleScheme<PostExt> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PostExt struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetObjectId()) {
        optionals.set(0);
      }
      if (struct.isSetVersion()) {
        optionals.set(1);
      }
      if (struct.isSetPlatform()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetObjectId()) {
        oprot.writeI64(struct.objectId);
      }
      if (struct.isSetVersion()) {
        oprot.writeString(struct.version);
      }
      if (struct.isSetPlatform()) {
        oprot.writeString(struct.platform);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PostExt struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.objectId = iprot.readI64();
        struct.setObjectIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.version = iprot.readString();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.platform = iprot.readString();
        struct.setPlatformIsSet(true);
      }
    }
  }

}

