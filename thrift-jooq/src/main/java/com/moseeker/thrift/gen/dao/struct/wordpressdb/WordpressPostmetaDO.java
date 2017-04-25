/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.wordpressdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-21")
public class WordpressPostmetaDO implements org.apache.thrift.TBase<WordpressPostmetaDO, WordpressPostmetaDO._Fields>, java.io.Serializable, Cloneable, Comparable<WordpressPostmetaDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WordpressPostmetaDO");

  private static final org.apache.thrift.protocol.TField META_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("metaId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField POST_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("postId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField META_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("metaKey", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField META_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("metaValue", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WordpressPostmetaDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WordpressPostmetaDOTupleSchemeFactory();

  public int metaId; // optional
  public int postId; // optional
  public java.lang.String metaKey; // optional
  public java.lang.String metaValue; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    META_ID((short)1, "metaId"),
    POST_ID((short)2, "postId"),
    META_KEY((short)3, "metaKey"),
    META_VALUE((short)4, "metaValue");

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
        case 1: // META_ID
          return META_ID;
        case 2: // POST_ID
          return POST_ID;
        case 3: // META_KEY
          return META_KEY;
        case 4: // META_VALUE
          return META_VALUE;
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
  private static final int __METAID_ISSET_ID = 0;
  private static final int __POSTID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.META_ID,_Fields.POST_ID,_Fields.META_KEY,_Fields.META_VALUE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.META_ID, new org.apache.thrift.meta_data.FieldMetaData("metaId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.POST_ID, new org.apache.thrift.meta_data.FieldMetaData("postId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.META_KEY, new org.apache.thrift.meta_data.FieldMetaData("metaKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.META_VALUE, new org.apache.thrift.meta_data.FieldMetaData("metaValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WordpressPostmetaDO.class, metaDataMap);
  }

  public WordpressPostmetaDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WordpressPostmetaDO(WordpressPostmetaDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.metaId = other.metaId;
    this.postId = other.postId;
    if (other.isSetMetaKey()) {
      this.metaKey = other.metaKey;
    }
    if (other.isSetMetaValue()) {
      this.metaValue = other.metaValue;
    }
  }

  public WordpressPostmetaDO deepCopy() {
    return new WordpressPostmetaDO(this);
  }

  @Override
  public void clear() {
    setMetaIdIsSet(false);
    this.metaId = 0;
    setPostIdIsSet(false);
    this.postId = 0;
    this.metaKey = null;
    this.metaValue = null;
  }

  public int getMetaId() {
    return this.metaId;
  }

  public WordpressPostmetaDO setMetaId(int metaId) {
    this.metaId = metaId;
    setMetaIdIsSet(true);
    return this;
  }

  public void unsetMetaId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __METAID_ISSET_ID);
  }

  /** Returns true if field metaId is set (has been assigned a value) and false otherwise */
  public boolean isSetMetaId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __METAID_ISSET_ID);
  }

  public void setMetaIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __METAID_ISSET_ID, value);
  }

  public int getPostId() {
    return this.postId;
  }

  public WordpressPostmetaDO setPostId(int postId) {
    this.postId = postId;
    setPostIdIsSet(true);
    return this;
  }

  public void unsetPostId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __POSTID_ISSET_ID);
  }

  /** Returns true if field postId is set (has been assigned a value) and false otherwise */
  public boolean isSetPostId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __POSTID_ISSET_ID);
  }

  public void setPostIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __POSTID_ISSET_ID, value);
  }

  public java.lang.String getMetaKey() {
    return this.metaKey;
  }

  public WordpressPostmetaDO setMetaKey(java.lang.String metaKey) {
    this.metaKey = metaKey;
    return this;
  }

  public void unsetMetaKey() {
    this.metaKey = null;
  }

  /** Returns true if field metaKey is set (has been assigned a value) and false otherwise */
  public boolean isSetMetaKey() {
    return this.metaKey != null;
  }

  public void setMetaKeyIsSet(boolean value) {
    if (!value) {
      this.metaKey = null;
    }
  }

  public java.lang.String getMetaValue() {
    return this.metaValue;
  }

  public WordpressPostmetaDO setMetaValue(java.lang.String metaValue) {
    this.metaValue = metaValue;
    return this;
  }

  public void unsetMetaValue() {
    this.metaValue = null;
  }

  /** Returns true if field metaValue is set (has been assigned a value) and false otherwise */
  public boolean isSetMetaValue() {
    return this.metaValue != null;
  }

  public void setMetaValueIsSet(boolean value) {
    if (!value) {
      this.metaValue = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case META_ID:
      if (value == null) {
        unsetMetaId();
      } else {
        setMetaId((java.lang.Integer)value);
      }
      break;

    case POST_ID:
      if (value == null) {
        unsetPostId();
      } else {
        setPostId((java.lang.Integer)value);
      }
      break;

    case META_KEY:
      if (value == null) {
        unsetMetaKey();
      } else {
        setMetaKey((java.lang.String)value);
      }
      break;

    case META_VALUE:
      if (value == null) {
        unsetMetaValue();
      } else {
        setMetaValue((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case META_ID:
      return getMetaId();

    case POST_ID:
      return getPostId();

    case META_KEY:
      return getMetaKey();

    case META_VALUE:
      return getMetaValue();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case META_ID:
      return isSetMetaId();
    case POST_ID:
      return isSetPostId();
    case META_KEY:
      return isSetMetaKey();
    case META_VALUE:
      return isSetMetaValue();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WordpressPostmetaDO)
      return this.equals((WordpressPostmetaDO)that);
    return false;
  }

  public boolean equals(WordpressPostmetaDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_metaId = true && this.isSetMetaId();
    boolean that_present_metaId = true && that.isSetMetaId();
    if (this_present_metaId || that_present_metaId) {
      if (!(this_present_metaId && that_present_metaId))
        return false;
      if (this.metaId != that.metaId)
        return false;
    }

    boolean this_present_postId = true && this.isSetPostId();
    boolean that_present_postId = true && that.isSetPostId();
    if (this_present_postId || that_present_postId) {
      if (!(this_present_postId && that_present_postId))
        return false;
      if (this.postId != that.postId)
        return false;
    }

    boolean this_present_metaKey = true && this.isSetMetaKey();
    boolean that_present_metaKey = true && that.isSetMetaKey();
    if (this_present_metaKey || that_present_metaKey) {
      if (!(this_present_metaKey && that_present_metaKey))
        return false;
      if (!this.metaKey.equals(that.metaKey))
        return false;
    }

    boolean this_present_metaValue = true && this.isSetMetaValue();
    boolean that_present_metaValue = true && that.isSetMetaValue();
    if (this_present_metaValue || that_present_metaValue) {
      if (!(this_present_metaValue && that_present_metaValue))
        return false;
      if (!this.metaValue.equals(that.metaValue))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMetaId()) ? 131071 : 524287);
    if (isSetMetaId())
      hashCode = hashCode * 8191 + metaId;

    hashCode = hashCode * 8191 + ((isSetPostId()) ? 131071 : 524287);
    if (isSetPostId())
      hashCode = hashCode * 8191 + postId;

    hashCode = hashCode * 8191 + ((isSetMetaKey()) ? 131071 : 524287);
    if (isSetMetaKey())
      hashCode = hashCode * 8191 + metaKey.hashCode();

    hashCode = hashCode * 8191 + ((isSetMetaValue()) ? 131071 : 524287);
    if (isSetMetaValue())
      hashCode = hashCode * 8191 + metaValue.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WordpressPostmetaDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMetaId()).compareTo(other.isSetMetaId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMetaId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metaId, other.metaId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPostId()).compareTo(other.isSetPostId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPostId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.postId, other.postId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMetaKey()).compareTo(other.isSetMetaKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMetaKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metaKey, other.metaKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMetaValue()).compareTo(other.isSetMetaValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMetaValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metaValue, other.metaValue);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WordpressPostmetaDO(");
    boolean first = true;

    if (isSetMetaId()) {
      sb.append("metaId:");
      sb.append(this.metaId);
      first = false;
    }
    if (isSetPostId()) {
      if (!first) sb.append(", ");
      sb.append("postId:");
      sb.append(this.postId);
      first = false;
    }
    if (isSetMetaKey()) {
      if (!first) sb.append(", ");
      sb.append("metaKey:");
      if (this.metaKey == null) {
        sb.append("null");
      } else {
        sb.append(this.metaKey);
      }
      first = false;
    }
    if (isSetMetaValue()) {
      if (!first) sb.append(", ");
      sb.append("metaValue:");
      if (this.metaValue == null) {
        sb.append("null");
      } else {
        sb.append(this.metaValue);
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

  private static class WordpressPostmetaDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WordpressPostmetaDOStandardScheme getScheme() {
      return new WordpressPostmetaDOStandardScheme();
    }
  }

  private static class WordpressPostmetaDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<WordpressPostmetaDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WordpressPostmetaDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // META_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.metaId = iprot.readI32();
              struct.setMetaIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POST_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.postId = iprot.readI32();
              struct.setPostIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // META_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.metaKey = iprot.readString();
              struct.setMetaKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // META_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.metaValue = iprot.readString();
              struct.setMetaValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WordpressPostmetaDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetMetaId()) {
        oprot.writeFieldBegin(META_ID_FIELD_DESC);
        oprot.writeI32(struct.metaId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPostId()) {
        oprot.writeFieldBegin(POST_ID_FIELD_DESC);
        oprot.writeI32(struct.postId);
        oprot.writeFieldEnd();
      }
      if (struct.metaKey != null) {
        if (struct.isSetMetaKey()) {
          oprot.writeFieldBegin(META_KEY_FIELD_DESC);
          oprot.writeString(struct.metaKey);
          oprot.writeFieldEnd();
        }
      }
      if (struct.metaValue != null) {
        if (struct.isSetMetaValue()) {
          oprot.writeFieldBegin(META_VALUE_FIELD_DESC);
          oprot.writeString(struct.metaValue);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WordpressPostmetaDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WordpressPostmetaDOTupleScheme getScheme() {
      return new WordpressPostmetaDOTupleScheme();
    }
  }

  private static class WordpressPostmetaDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<WordpressPostmetaDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WordpressPostmetaDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMetaId()) {
        optionals.set(0);
      }
      if (struct.isSetPostId()) {
        optionals.set(1);
      }
      if (struct.isSetMetaKey()) {
        optionals.set(2);
      }
      if (struct.isSetMetaValue()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetMetaId()) {
        oprot.writeI32(struct.metaId);
      }
      if (struct.isSetPostId()) {
        oprot.writeI32(struct.postId);
      }
      if (struct.isSetMetaKey()) {
        oprot.writeString(struct.metaKey);
      }
      if (struct.isSetMetaValue()) {
        oprot.writeString(struct.metaValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WordpressPostmetaDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.metaId = iprot.readI32();
        struct.setMetaIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.postId = iprot.readI32();
        struct.setPostIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.metaKey = iprot.readString();
        struct.setMetaKeyIsSet(true);
      }
      if (incoming.get(3)) {
        struct.metaValue = iprot.readString();
        struct.setMetaValueIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
