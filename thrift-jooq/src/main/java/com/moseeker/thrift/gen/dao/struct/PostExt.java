/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-26")
public class PostExt implements org.apache.thrift.TBase<PostExt, PostExt._Fields>, java.io.Serializable, Cloneable, Comparable<PostExt> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PostExt");

  private static final org.apache.thrift.protocol.TField OBJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("objectId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PLATFORM_FIELD_DESC = new org.apache.thrift.protocol.TField("platform", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PostExtStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PostExtTupleSchemeFactory();

  public long objectId; // required
  public java.lang.String version; // required
  public java.lang.String platform; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OBJECT_ID((short)1, "objectId"),
    VERSION((short)2, "version"),
    PLATFORM((short)3, "platform");

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
  private static final int __OBJECTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OBJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("objectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLATFORM, new org.apache.thrift.meta_data.FieldMetaData("platform", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PostExt.class, metaDataMap);
  }

  public PostExt() {
  }

  public PostExt(
    long objectId,
    java.lang.String version,
    java.lang.String platform)
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
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __OBJECTID_ISSET_ID);
  }

  /** Returns true if field objectId is set (has been assigned a value) and false otherwise */
  public boolean isSetObjectId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __OBJECTID_ISSET_ID);
  }

  public void setObjectIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __OBJECTID_ISSET_ID, value);
  }

  public java.lang.String getVersion() {
    return this.version;
  }

  public PostExt setVersion(java.lang.String version) {
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

  public java.lang.String getPlatform() {
    return this.platform;
  }

  public PostExt setPlatform(java.lang.String platform) {
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

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case OBJECT_ID:
      if (value == null) {
        unsetObjectId();
      } else {
        setObjectId((java.lang.Long)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((java.lang.String)value);
      }
      break;

    case PLATFORM:
      if (value == null) {
        unsetPlatform();
      } else {
        setPlatform((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OBJECT_ID:
      return getObjectId();

    case VERSION:
      return getVersion();

    case PLATFORM:
      return getPlatform();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OBJECT_ID:
      return isSetObjectId();
    case VERSION:
      return isSetVersion();
    case PLATFORM:
      return isSetPlatform();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PostExt)
      return this.equals((PostExt)that);
    return false;
  }

  public boolean equals(PostExt that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

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
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(objectId);

    hashCode = hashCode * 8191 + ((isSetVersion()) ? 131071 : 524287);
    if (isSetVersion())
      hashCode = hashCode * 8191 + version.hashCode();

    hashCode = hashCode * 8191 + ((isSetPlatform()) ? 131071 : 524287);
    if (isSetPlatform())
      hashCode = hashCode * 8191 + platform.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PostExt other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetObjectId()).compareTo(other.isSetObjectId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjectId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.objectId, other.objectId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPlatform()).compareTo(other.isSetPlatform());
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
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PostExt(");
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PostExtStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PostExtStandardScheme getScheme() {
      return new PostExtStandardScheme();
    }
  }

  private static class PostExtStandardScheme extends org.apache.thrift.scheme.StandardScheme<PostExt> {

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

  private static class PostExtTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PostExtTupleScheme getScheme() {
      return new PostExtTupleScheme();
    }
  }

  private static class PostExtTupleScheme extends org.apache.thrift.scheme.TupleScheme<PostExt> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PostExt struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
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
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
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

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

