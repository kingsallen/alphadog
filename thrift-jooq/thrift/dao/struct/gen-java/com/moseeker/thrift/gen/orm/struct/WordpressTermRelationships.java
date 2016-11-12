/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.orm.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-11-12")
public class WordpressTermRelationships implements org.apache.thrift.TBase<WordpressTermRelationships, WordpressTermRelationships._Fields>, java.io.Serializable, Cloneable, Comparable<WordpressTermRelationships> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WordpressTermRelationships");

  private static final org.apache.thrift.protocol.TField OBJECT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("objectId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TERM_TAXONOMY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("termTaxonomyId", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new WordpressTermRelationshipsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new WordpressTermRelationshipsTupleSchemeFactory());
  }

  public long objectId; // required
  public long termTaxonomyId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OBJECT_ID((short)1, "objectId"),
    TERM_TAXONOMY_ID((short)2, "termTaxonomyId");

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
        case 2: // TERM_TAXONOMY_ID
          return TERM_TAXONOMY_ID;
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
  private static final int __TERMTAXONOMYID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OBJECT_ID, new org.apache.thrift.meta_data.FieldMetaData("objectId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TERM_TAXONOMY_ID, new org.apache.thrift.meta_data.FieldMetaData("termTaxonomyId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WordpressTermRelationships.class, metaDataMap);
  }

  public WordpressTermRelationships() {
  }

  public WordpressTermRelationships(
    long objectId,
    long termTaxonomyId)
  {
    this();
    this.objectId = objectId;
    setObjectIdIsSet(true);
    this.termTaxonomyId = termTaxonomyId;
    setTermTaxonomyIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WordpressTermRelationships(WordpressTermRelationships other) {
    __isset_bitfield = other.__isset_bitfield;
    this.objectId = other.objectId;
    this.termTaxonomyId = other.termTaxonomyId;
  }

  public WordpressTermRelationships deepCopy() {
    return new WordpressTermRelationships(this);
  }

  @Override
  public void clear() {
    setObjectIdIsSet(false);
    this.objectId = 0;
    setTermTaxonomyIdIsSet(false);
    this.termTaxonomyId = 0;
  }

  public long getObjectId() {
    return this.objectId;
  }

  public WordpressTermRelationships setObjectId(long objectId) {
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

  public long getTermTaxonomyId() {
    return this.termTaxonomyId;
  }

  public WordpressTermRelationships setTermTaxonomyId(long termTaxonomyId) {
    this.termTaxonomyId = termTaxonomyId;
    setTermTaxonomyIdIsSet(true);
    return this;
  }

  public void unsetTermTaxonomyId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TERMTAXONOMYID_ISSET_ID);
  }

  /** Returns true if field termTaxonomyId is set (has been assigned a value) and false otherwise */
  public boolean isSetTermTaxonomyId() {
    return EncodingUtils.testBit(__isset_bitfield, __TERMTAXONOMYID_ISSET_ID);
  }

  public void setTermTaxonomyIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TERMTAXONOMYID_ISSET_ID, value);
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

    case TERM_TAXONOMY_ID:
      if (value == null) {
        unsetTermTaxonomyId();
      } else {
        setTermTaxonomyId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OBJECT_ID:
      return Long.valueOf(getObjectId());

    case TERM_TAXONOMY_ID:
      return Long.valueOf(getTermTaxonomyId());

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
    case TERM_TAXONOMY_ID:
      return isSetTermTaxonomyId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof WordpressTermRelationships)
      return this.equals((WordpressTermRelationships)that);
    return false;
  }

  public boolean equals(WordpressTermRelationships that) {
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

    boolean this_present_termTaxonomyId = true;
    boolean that_present_termTaxonomyId = true;
    if (this_present_termTaxonomyId || that_present_termTaxonomyId) {
      if (!(this_present_termTaxonomyId && that_present_termTaxonomyId))
        return false;
      if (this.termTaxonomyId != that.termTaxonomyId)
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

    boolean present_termTaxonomyId = true;
    list.add(present_termTaxonomyId);
    if (present_termTaxonomyId)
      list.add(termTaxonomyId);

    return list.hashCode();
  }

  @Override
  public int compareTo(WordpressTermRelationships other) {
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
    lastComparison = Boolean.valueOf(isSetTermTaxonomyId()).compareTo(other.isSetTermTaxonomyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTermTaxonomyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.termTaxonomyId, other.termTaxonomyId);
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
    StringBuilder sb = new StringBuilder("WordpressTermRelationships(");
    boolean first = true;

    sb.append("objectId:");
    sb.append(this.objectId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("termTaxonomyId:");
    sb.append(this.termTaxonomyId);
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

  private static class WordpressTermRelationshipsStandardSchemeFactory implements SchemeFactory {
    public WordpressTermRelationshipsStandardScheme getScheme() {
      return new WordpressTermRelationshipsStandardScheme();
    }
  }

  private static class WordpressTermRelationshipsStandardScheme extends StandardScheme<WordpressTermRelationships> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WordpressTermRelationships struct) throws org.apache.thrift.TException {
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
          case 2: // TERM_TAXONOMY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.termTaxonomyId = iprot.readI64();
              struct.setTermTaxonomyIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WordpressTermRelationships struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(OBJECT_ID_FIELD_DESC);
      oprot.writeI64(struct.objectId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TERM_TAXONOMY_ID_FIELD_DESC);
      oprot.writeI64(struct.termTaxonomyId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WordpressTermRelationshipsTupleSchemeFactory implements SchemeFactory {
    public WordpressTermRelationshipsTupleScheme getScheme() {
      return new WordpressTermRelationshipsTupleScheme();
    }
  }

  private static class WordpressTermRelationshipsTupleScheme extends TupleScheme<WordpressTermRelationships> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WordpressTermRelationships struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetObjectId()) {
        optionals.set(0);
      }
      if (struct.isSetTermTaxonomyId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetObjectId()) {
        oprot.writeI64(struct.objectId);
      }
      if (struct.isSetTermTaxonomyId()) {
        oprot.writeI64(struct.termTaxonomyId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WordpressTermRelationships struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.objectId = iprot.readI64();
        struct.setObjectIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.termTaxonomyId = iprot.readI64();
        struct.setTermTaxonomyIdIsSet(true);
      }
    }
  }

}

