/**
 * Autogenerated by Thrift Compiler (0.9.3)
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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-01-04")
public class Talentpool implements org.apache.thrift.TBase<Talentpool, Talentpool._Fields>, java.io.Serializable, Cloneable, Comparable<Talentpool> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Talentpool");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HR_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("hr_account_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField APPLIER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("applier_id", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("create_time", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TalentpoolStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TalentpoolTupleSchemeFactory());
  }

  public int id; // required
  public int hr_account_id; // required
  public int applier_id; // required
  public String create_time; // required
  public String update_time; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    HR_ACCOUNT_ID((short)2, "hr_account_id"),
    APPLIER_ID((short)3, "applier_id"),
    CREATE_TIME((short)4, "create_time"),
    UPDATE_TIME((short)5, "update_time"),
    STATUS((short)6, "status");

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
        case 1: // ID
          return ID;
        case 2: // HR_ACCOUNT_ID
          return HR_ACCOUNT_ID;
        case 3: // APPLIER_ID
          return APPLIER_ID;
        case 4: // CREATE_TIME
          return CREATE_TIME;
        case 5: // UPDATE_TIME
          return UPDATE_TIME;
        case 6: // STATUS
          return STATUS;
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
  private static final int __HR_ACCOUNT_ID_ISSET_ID = 1;
  private static final int __APPLIER_ID_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HR_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("hr_account_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.APPLIER_ID, new org.apache.thrift.meta_data.FieldMetaData("applier_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("create_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Talentpool.class, metaDataMap);
  }

  public Talentpool() {
  }

  public Talentpool(
    int id,
    int hr_account_id,
    int applier_id,
    String create_time,
    String update_time,
    int status)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.hr_account_id = hr_account_id;
    setHr_account_idIsSet(true);
    this.applier_id = applier_id;
    setApplier_idIsSet(true);
    this.create_time = create_time;
    this.update_time = update_time;
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Talentpool(Talentpool other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.hr_account_id = other.hr_account_id;
    this.applier_id = other.applier_id;
    if (other.isSetCreate_time()) {
      this.create_time = other.create_time;
    }
    if (other.isSetUpdate_time()) {
      this.update_time = other.update_time;
    }
    this.status = other.status;
  }

  public Talentpool deepCopy() {
    return new Talentpool(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setHr_account_idIsSet(false);
    this.hr_account_id = 0;
    setApplier_idIsSet(false);
    this.applier_id = 0;
    this.create_time = null;
    this.update_time = null;
    setStatusIsSet(false);
    this.status = 0;
  }

  public int getId() {
    return this.id;
  }

  public Talentpool setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getHr_account_id() {
    return this.hr_account_id;
  }

  public Talentpool setHr_account_id(int hr_account_id) {
    this.hr_account_id = hr_account_id;
    setHr_account_idIsSet(true);
    return this;
  }

  public void unsetHr_account_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HR_ACCOUNT_ID_ISSET_ID);
  }

  /** Returns true if field hr_account_id is set (has been assigned a value) and false otherwise */
  public boolean isSetHr_account_id() {
    return EncodingUtils.testBit(__isset_bitfield, __HR_ACCOUNT_ID_ISSET_ID);
  }

  public void setHr_account_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HR_ACCOUNT_ID_ISSET_ID, value);
  }

  public int getApplier_id() {
    return this.applier_id;
  }

  public Talentpool setApplier_id(int applier_id) {
    this.applier_id = applier_id;
    setApplier_idIsSet(true);
    return this;
  }

  public void unsetApplier_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __APPLIER_ID_ISSET_ID);
  }

  /** Returns true if field applier_id is set (has been assigned a value) and false otherwise */
  public boolean isSetApplier_id() {
    return EncodingUtils.testBit(__isset_bitfield, __APPLIER_ID_ISSET_ID);
  }

  public void setApplier_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __APPLIER_ID_ISSET_ID, value);
  }

  public String getCreate_time() {
    return this.create_time;
  }

  public Talentpool setCreate_time(String create_time) {
    this.create_time = create_time;
    return this;
  }

  public void unsetCreate_time() {
    this.create_time = null;
  }

  /** Returns true if field create_time is set (has been assigned a value) and false otherwise */
  public boolean isSetCreate_time() {
    return this.create_time != null;
  }

  public void setCreate_timeIsSet(boolean value) {
    if (!value) {
      this.create_time = null;
    }
  }

  public String getUpdate_time() {
    return this.update_time;
  }

  public Talentpool setUpdate_time(String update_time) {
    this.update_time = update_time;
    return this;
  }

  public void unsetUpdate_time() {
    this.update_time = null;
  }

  /** Returns true if field update_time is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdate_time() {
    return this.update_time != null;
  }

  public void setUpdate_timeIsSet(boolean value) {
    if (!value) {
      this.update_time = null;
    }
  }

  public int getStatus() {
    return this.status;
  }

  public Talentpool setStatus(int status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
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

    case HR_ACCOUNT_ID:
      if (value == null) {
        unsetHr_account_id();
      } else {
        setHr_account_id((Integer)value);
      }
      break;

    case APPLIER_ID:
      if (value == null) {
        unsetApplier_id();
      } else {
        setApplier_id((Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreate_time();
      } else {
        setCreate_time((String)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdate_time();
      } else {
        setUpdate_time((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case HR_ACCOUNT_ID:
      return getHr_account_id();

    case APPLIER_ID:
      return getApplier_id();

    case CREATE_TIME:
      return getCreate_time();

    case UPDATE_TIME:
      return getUpdate_time();

    case STATUS:
      return getStatus();

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
    case HR_ACCOUNT_ID:
      return isSetHr_account_id();
    case APPLIER_ID:
      return isSetApplier_id();
    case CREATE_TIME:
      return isSetCreate_time();
    case UPDATE_TIME:
      return isSetUpdate_time();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Talentpool)
      return this.equals((Talentpool)that);
    return false;
  }

  public boolean equals(Talentpool that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_hr_account_id = true;
    boolean that_present_hr_account_id = true;
    if (this_present_hr_account_id || that_present_hr_account_id) {
      if (!(this_present_hr_account_id && that_present_hr_account_id))
        return false;
      if (this.hr_account_id != that.hr_account_id)
        return false;
    }

    boolean this_present_applier_id = true;
    boolean that_present_applier_id = true;
    if (this_present_applier_id || that_present_applier_id) {
      if (!(this_present_applier_id && that_present_applier_id))
        return false;
      if (this.applier_id != that.applier_id)
        return false;
    }

    boolean this_present_create_time = true && this.isSetCreate_time();
    boolean that_present_create_time = true && that.isSetCreate_time();
    if (this_present_create_time || that_present_create_time) {
      if (!(this_present_create_time && that_present_create_time))
        return false;
      if (!this.create_time.equals(that.create_time))
        return false;
    }

    boolean this_present_update_time = true && this.isSetUpdate_time();
    boolean that_present_update_time = true && that.isSetUpdate_time();
    if (this_present_update_time || that_present_update_time) {
      if (!(this_present_update_time && that_present_update_time))
        return false;
      if (!this.update_time.equals(that.update_time))
        return false;
    }

    boolean this_present_status = true;
    boolean that_present_status = true;
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_hr_account_id = true;
    list.add(present_hr_account_id);
    if (present_hr_account_id)
      list.add(hr_account_id);

    boolean present_applier_id = true;
    list.add(present_applier_id);
    if (present_applier_id)
      list.add(applier_id);

    boolean present_create_time = true && (isSetCreate_time());
    list.add(present_create_time);
    if (present_create_time)
      list.add(create_time);

    boolean present_update_time = true && (isSetUpdate_time());
    list.add(present_update_time);
    if (present_update_time)
      list.add(update_time);

    boolean present_status = true;
    list.add(present_status);
    if (present_status)
      list.add(status);

    return list.hashCode();
  }

  @Override
  public int compareTo(Talentpool other) {
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
    lastComparison = Boolean.valueOf(isSetHr_account_id()).compareTo(other.isSetHr_account_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHr_account_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hr_account_id, other.hr_account_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetApplier_id()).compareTo(other.isSetApplier_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApplier_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.applier_id, other.applier_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreate_time()).compareTo(other.isSetCreate_time());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreate_time()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.create_time, other.create_time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdate_time()).compareTo(other.isSetUpdate_time());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_time()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_time, other.update_time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
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
    StringBuilder sb = new StringBuilder("Talentpool(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("hr_account_id:");
    sb.append(this.hr_account_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("applier_id:");
    sb.append(this.applier_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("create_time:");
    if (this.create_time == null) {
      sb.append("null");
    } else {
      sb.append(this.create_time);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("update_time:");
    if (this.update_time == null) {
      sb.append("null");
    } else {
      sb.append(this.update_time);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
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

  private static class TalentpoolStandardSchemeFactory implements SchemeFactory {
    public TalentpoolStandardScheme getScheme() {
      return new TalentpoolStandardScheme();
    }
  }

  private static class TalentpoolStandardScheme extends StandardScheme<Talentpool> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Talentpool struct) throws org.apache.thrift.TException {
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
          case 2: // HR_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.hr_account_id = iprot.readI32();
              struct.setHr_account_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // APPLIER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.applier_id = iprot.readI32();
              struct.setApplier_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.create_time = iprot.readString();
              struct.setCreate_timeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.update_time = iprot.readString();
              struct.setUpdate_timeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Talentpool struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(HR_ACCOUNT_ID_FIELD_DESC);
      oprot.writeI32(struct.hr_account_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(APPLIER_ID_FIELD_DESC);
      oprot.writeI32(struct.applier_id);
      oprot.writeFieldEnd();
      if (struct.create_time != null) {
        oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
        oprot.writeString(struct.create_time);
        oprot.writeFieldEnd();
      }
      if (struct.update_time != null) {
        oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
        oprot.writeString(struct.update_time);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TalentpoolTupleSchemeFactory implements SchemeFactory {
    public TalentpoolTupleScheme getScheme() {
      return new TalentpoolTupleScheme();
    }
  }

  private static class TalentpoolTupleScheme extends TupleScheme<Talentpool> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Talentpool struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetHr_account_id()) {
        optionals.set(1);
      }
      if (struct.isSetApplier_id()) {
        optionals.set(2);
      }
      if (struct.isSetCreate_time()) {
        optionals.set(3);
      }
      if (struct.isSetUpdate_time()) {
        optionals.set(4);
      }
      if (struct.isSetStatus()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetHr_account_id()) {
        oprot.writeI32(struct.hr_account_id);
      }
      if (struct.isSetApplier_id()) {
        oprot.writeI32(struct.applier_id);
      }
      if (struct.isSetCreate_time()) {
        oprot.writeString(struct.create_time);
      }
      if (struct.isSetUpdate_time()) {
        oprot.writeString(struct.update_time);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Talentpool struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.hr_account_id = iprot.readI32();
        struct.setHr_account_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.applier_id = iprot.readI32();
        struct.setApplier_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.create_time = iprot.readString();
        struct.setCreate_timeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.update_time = iprot.readString();
        struct.setUpdate_timeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}
