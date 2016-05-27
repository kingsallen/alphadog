/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dict.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-05-26")
public class City implements org.apache.thrift.TBase<City, City._Fields>, java.io.Serializable, Cloneable, Comparable<City> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("City");

  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("level", org.apache.thrift.protocol.TType.BYTE, (short)3);
  private static final org.apache.thrift.protocol.TField HOT_CITY_FIELD_DESC = new org.apache.thrift.protocol.TField("hot_city", org.apache.thrift.protocol.TType.BYTE, (short)4);
  private static final org.apache.thrift.protocol.TField ENAME_FIELD_DESC = new org.apache.thrift.protocol.TField("ename", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField IS_USING_FIELD_DESC = new org.apache.thrift.protocol.TField("is_using", org.apache.thrift.protocol.TType.BYTE, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CityStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CityTupleSchemeFactory());
  }

  public int code; // required
  public String name; // required
  public byte level; // required
  public byte hot_city; // required
  public String ename; // required
  public byte is_using; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CODE((short)1, "code"),
    NAME((short)2, "name"),
    LEVEL((short)3, "level"),
    HOT_CITY((short)4, "hot_city"),
    ENAME((short)5, "ename"),
    IS_USING((short)6, "is_using");

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
        case 1: // CODE
          return CODE;
        case 2: // NAME
          return NAME;
        case 3: // LEVEL
          return LEVEL;
        case 4: // HOT_CITY
          return HOT_CITY;
        case 5: // ENAME
          return ENAME;
        case 6: // IS_USING
          return IS_USING;
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
  private static final int __CODE_ISSET_ID = 0;
  private static final int __LEVEL_ISSET_ID = 1;
  private static final int __HOT_CITY_ISSET_ID = 2;
  private static final int __IS_USING_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LEVEL, new org.apache.thrift.meta_data.FieldMetaData("level", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.HOT_CITY, new org.apache.thrift.meta_data.FieldMetaData("hot_city", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.ENAME, new org.apache.thrift.meta_data.FieldMetaData("ename", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_USING, new org.apache.thrift.meta_data.FieldMetaData("is_using", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(City.class, metaDataMap);
  }

  public City() {
  }

  public City(
    int code,
    String name,
    byte level,
    byte hot_city,
    String ename,
    byte is_using)
  {
    this();
    this.code = code;
    setCodeIsSet(true);
    this.name = name;
    this.level = level;
    setLevelIsSet(true);
    this.hot_city = hot_city;
    setHot_cityIsSet(true);
    this.ename = ename;
    this.is_using = is_using;
    setIs_usingIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public City(City other) {
    __isset_bitfield = other.__isset_bitfield;
    this.code = other.code;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.level = other.level;
    this.hot_city = other.hot_city;
    if (other.isSetEname()) {
      this.ename = other.ename;
    }
    this.is_using = other.is_using;
  }

  public City deepCopy() {
    return new City(this);
  }

  @Override
  public void clear() {
    setCodeIsSet(false);
    this.code = 0;
    this.name = null;
    setLevelIsSet(false);
    this.level = 0;
    setHot_cityIsSet(false);
    this.hot_city = 0;
    this.ename = null;
    setIs_usingIsSet(false);
    this.is_using = 0;
  }

  public int getCode() {
    return this.code;
  }

  public City setCode(int code) {
    this.code = code;
    setCodeIsSet(true);
    return this;
  }

  public void unsetCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return EncodingUtils.testBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  public void setCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CODE_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public City setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public byte getLevel() {
    return this.level;
  }

  public City setLevel(byte level) {
    this.level = level;
    setLevelIsSet(true);
    return this;
  }

  public void unsetLevel() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LEVEL_ISSET_ID);
  }

  /** Returns true if field level is set (has been assigned a value) and false otherwise */
  public boolean isSetLevel() {
    return EncodingUtils.testBit(__isset_bitfield, __LEVEL_ISSET_ID);
  }

  public void setLevelIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LEVEL_ISSET_ID, value);
  }

  public byte getHot_city() {
    return this.hot_city;
  }

  public City setHot_city(byte hot_city) {
    this.hot_city = hot_city;
    setHot_cityIsSet(true);
    return this;
  }

  public void unsetHot_city() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HOT_CITY_ISSET_ID);
  }

  /** Returns true if field hot_city is set (has been assigned a value) and false otherwise */
  public boolean isSetHot_city() {
    return EncodingUtils.testBit(__isset_bitfield, __HOT_CITY_ISSET_ID);
  }

  public void setHot_cityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HOT_CITY_ISSET_ID, value);
  }

  public String getEname() {
    return this.ename;
  }

  public City setEname(String ename) {
    this.ename = ename;
    return this;
  }

  public void unsetEname() {
    this.ename = null;
  }

  /** Returns true if field ename is set (has been assigned a value) and false otherwise */
  public boolean isSetEname() {
    return this.ename != null;
  }

  public void setEnameIsSet(boolean value) {
    if (!value) {
      this.ename = null;
    }
  }

  public byte getIs_using() {
    return this.is_using;
  }

  public City setIs_using(byte is_using) {
    this.is_using = is_using;
    setIs_usingIsSet(true);
    return this;
  }

  public void unsetIs_using() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IS_USING_ISSET_ID);
  }

  /** Returns true if field is_using is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_using() {
    return EncodingUtils.testBit(__isset_bitfield, __IS_USING_ISSET_ID);
  }

  public void setIs_usingIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IS_USING_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case LEVEL:
      if (value == null) {
        unsetLevel();
      } else {
        setLevel((Byte)value);
      }
      break;

    case HOT_CITY:
      if (value == null) {
        unsetHot_city();
      } else {
        setHot_city((Byte)value);
      }
      break;

    case ENAME:
      if (value == null) {
        unsetEname();
      } else {
        setEname((String)value);
      }
      break;

    case IS_USING:
      if (value == null) {
        unsetIs_using();
      } else {
        setIs_using((Byte)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CODE:
      return getCode();

    case NAME:
      return getName();

    case LEVEL:
      return getLevel();

    case HOT_CITY:
      return getHot_city();

    case ENAME:
      return getEname();

    case IS_USING:
      return getIs_using();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CODE:
      return isSetCode();
    case NAME:
      return isSetName();
    case LEVEL:
      return isSetLevel();
    case HOT_CITY:
      return isSetHot_city();
    case ENAME:
      return isSetEname();
    case IS_USING:
      return isSetIs_using();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof City)
      return this.equals((City)that);
    return false;
  }

  public boolean equals(City that) {
    if (that == null)
      return false;

    boolean this_present_code = true;
    boolean that_present_code = true;
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (this.code != that.code)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_level = true;
    boolean that_present_level = true;
    if (this_present_level || that_present_level) {
      if (!(this_present_level && that_present_level))
        return false;
      if (this.level != that.level)
        return false;
    }

    boolean this_present_hot_city = true;
    boolean that_present_hot_city = true;
    if (this_present_hot_city || that_present_hot_city) {
      if (!(this_present_hot_city && that_present_hot_city))
        return false;
      if (this.hot_city != that.hot_city)
        return false;
    }

    boolean this_present_ename = true && this.isSetEname();
    boolean that_present_ename = true && that.isSetEname();
    if (this_present_ename || that_present_ename) {
      if (!(this_present_ename && that_present_ename))
        return false;
      if (!this.ename.equals(that.ename))
        return false;
    }

    boolean this_present_is_using = true;
    boolean that_present_is_using = true;
    if (this_present_is_using || that_present_is_using) {
      if (!(this_present_is_using && that_present_is_using))
        return false;
      if (this.is_using != that.is_using)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_code = true;
    list.add(present_code);
    if (present_code)
      list.add(code);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_level = true;
    list.add(present_level);
    if (present_level)
      list.add(level);

    boolean present_hot_city = true;
    list.add(present_hot_city);
    if (present_hot_city)
      list.add(hot_city);

    boolean present_ename = true && (isSetEname());
    list.add(present_ename);
    if (present_ename)
      list.add(ename);

    boolean present_is_using = true;
    list.add(present_is_using);
    if (present_is_using)
      list.add(is_using);

    return list.hashCode();
  }

  @Override
  public int compareTo(City other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLevel()).compareTo(other.isSetLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.level, other.level);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHot_city()).compareTo(other.isSetHot_city());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHot_city()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hot_city, other.hot_city);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEname()).compareTo(other.isSetEname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ename, other.ename);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIs_using()).compareTo(other.isSetIs_using());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_using()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_using, other.is_using);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("City(");
    boolean first = true;

    sb.append("code:");
    sb.append(this.code);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("level:");
    sb.append(this.level);
    first = false;
    if (!first) sb.append(", ");
    sb.append("hot_city:");
    sb.append(this.hot_city);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ename:");
    if (this.ename == null) {
      sb.append("null");
    } else {
      sb.append(this.ename);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_using:");
    sb.append(this.is_using);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CityStandardSchemeFactory implements SchemeFactory {
    public CityStandardScheme getScheme() {
      return new CityStandardScheme();
    }
  }

  private static class CityStandardScheme extends StandardScheme<City> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, City struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.code = iprot.readI32();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.level = iprot.readByte();
              struct.setLevelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // HOT_CITY
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.hot_city = iprot.readByte();
              struct.setHot_cityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ENAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ename = iprot.readString();
              struct.setEnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IS_USING
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.is_using = iprot.readByte();
              struct.setIs_usingIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, City struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CODE_FIELD_DESC);
      oprot.writeI32(struct.code);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(LEVEL_FIELD_DESC);
      oprot.writeByte(struct.level);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(HOT_CITY_FIELD_DESC);
      oprot.writeByte(struct.hot_city);
      oprot.writeFieldEnd();
      if (struct.ename != null) {
        oprot.writeFieldBegin(ENAME_FIELD_DESC);
        oprot.writeString(struct.ename);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_USING_FIELD_DESC);
      oprot.writeByte(struct.is_using);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CityTupleSchemeFactory implements SchemeFactory {
    public CityTupleScheme getScheme() {
      return new CityTupleScheme();
    }
  }

  private static class CityTupleScheme extends TupleScheme<City> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, City struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCode()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetLevel()) {
        optionals.set(2);
      }
      if (struct.isSetHot_city()) {
        optionals.set(3);
      }
      if (struct.isSetEname()) {
        optionals.set(4);
      }
      if (struct.isSetIs_using()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCode()) {
        oprot.writeI32(struct.code);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetLevel()) {
        oprot.writeByte(struct.level);
      }
      if (struct.isSetHot_city()) {
        oprot.writeByte(struct.hot_city);
      }
      if (struct.isSetEname()) {
        oprot.writeString(struct.ename);
      }
      if (struct.isSetIs_using()) {
        oprot.writeByte(struct.is_using);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, City struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.code = iprot.readI32();
        struct.setCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.level = iprot.readByte();
        struct.setLevelIsSet(true);
      }
      if (incoming.get(3)) {
        struct.hot_city = iprot.readByte();
        struct.setHot_cityIsSet(true);
      }
      if (incoming.get(4)) {
        struct.ename = iprot.readString();
        struct.setEnameIsSet(true);
      }
      if (incoming.get(5)) {
        struct.is_using = iprot.readByte();
        struct.setIs_usingIsSet(true);
      }
    }
  }

}

