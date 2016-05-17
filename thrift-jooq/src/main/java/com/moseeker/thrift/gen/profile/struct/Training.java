/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.profile.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-5-17")
public class Training implements org.apache.thrift.TBase<Training, Training._Fields>, java.io.Serializable, Cloneable, Comparable<Training> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Training");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROFILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("profile_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField START_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("start_date", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField END_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("end_date", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField ORGNIZATION_FIELD_DESC = new org.apache.thrift.protocol.TField("orgnization", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("create_time", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TrainingStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TrainingTupleSchemeFactory());
  }

  public int id; // required
  public int profile_id; // required
  public String name; // required
  public String start_date; // required
  public String end_date; // required
  public String orgnization; // required
  public String description; // required
  public String create_time; // required
  public String update_time; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PROFILE_ID((short)2, "profile_id"),
    NAME((short)3, "name"),
    START_DATE((short)4, "start_date"),
    END_DATE((short)5, "end_date"),
    ORGNIZATION((short)6, "orgnization"),
    DESCRIPTION((short)7, "description"),
    CREATE_TIME((short)8, "create_time"),
    UPDATE_TIME((short)9, "update_time");

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
        case 2: // PROFILE_ID
          return PROFILE_ID;
        case 3: // NAME
          return NAME;
        case 4: // START_DATE
          return START_DATE;
        case 5: // END_DATE
          return END_DATE;
        case 6: // ORGNIZATION
          return ORGNIZATION;
        case 7: // DESCRIPTION
          return DESCRIPTION;
        case 8: // CREATE_TIME
          return CREATE_TIME;
        case 9: // UPDATE_TIME
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
  private static final int __PROFILE_ID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROFILE_ID, new org.apache.thrift.meta_data.FieldMetaData("profile_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_DATE, new org.apache.thrift.meta_data.FieldMetaData("start_date", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.END_DATE, new org.apache.thrift.meta_data.FieldMetaData("end_date", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.ORGNIZATION, new org.apache.thrift.meta_data.FieldMetaData("orgnization", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("create_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Training.class, metaDataMap);
  }

  public Training() {
  }

  public Training(
    int id,
    int profile_id,
    String name,
    String start_date,
    String end_date,
    String orgnization,
    String description,
    String create_time,
    String update_time)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.profile_id = profile_id;
    setProfile_idIsSet(true);
    this.name = name;
    this.start_date = start_date;
    this.end_date = end_date;
    this.orgnization = orgnization;
    this.description = description;
    this.create_time = create_time;
    this.update_time = update_time;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Training(Training other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.profile_id = other.profile_id;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetStart_date()) {
      this.start_date = other.start_date;
    }
    if (other.isSetEnd_date()) {
      this.end_date = other.end_date;
    }
    if (other.isSetOrgnization()) {
      this.orgnization = other.orgnization;
    }
    if (other.isSetDescription()) {
      this.description = other.description;
    }
    if (other.isSetCreate_time()) {
      this.create_time = other.create_time;
    }
    if (other.isSetUpdate_time()) {
      this.update_time = other.update_time;
    }
  }

  public Training deepCopy() {
    return new Training(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setProfile_idIsSet(false);
    this.profile_id = 0;
    this.name = null;
    this.start_date = null;
    this.end_date = null;
    this.orgnization = null;
    this.description = null;
    this.create_time = null;
    this.update_time = null;
  }

  public int getId() {
    return this.id;
  }

  public Training setId(int id) {
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

  public int getProfile_id() {
    return this.profile_id;
  }

  public Training setProfile_id(int profile_id) {
    this.profile_id = profile_id;
    setProfile_idIsSet(true);
    return this;
  }

  public void unsetProfile_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PROFILE_ID_ISSET_ID);
  }

  /** Returns true if field profile_id is set (has been assigned a value) and false otherwise */
  public boolean isSetProfile_id() {
    return EncodingUtils.testBit(__isset_bitfield, __PROFILE_ID_ISSET_ID);
  }

  public void setProfile_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PROFILE_ID_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public Training setName(String name) {
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

  public String getStart_date() {
    return this.start_date;
  }

  public Training setStart_date(String start_date) {
    this.start_date = start_date;
    return this;
  }

  public void unsetStart_date() {
    this.start_date = null;
  }

  /** Returns true if field start_date is set (has been assigned a value) and false otherwise */
  public boolean isSetStart_date() {
    return this.start_date != null;
  }

  public void setStart_dateIsSet(boolean value) {
    if (!value) {
      this.start_date = null;
    }
  }

  public String getEnd_date() {
    return this.end_date;
  }

  public Training setEnd_date(String end_date) {
    this.end_date = end_date;
    return this;
  }

  public void unsetEnd_date() {
    this.end_date = null;
  }

  /** Returns true if field end_date is set (has been assigned a value) and false otherwise */
  public boolean isSetEnd_date() {
    return this.end_date != null;
  }

  public void setEnd_dateIsSet(boolean value) {
    if (!value) {
      this.end_date = null;
    }
  }

  public String getOrgnization() {
    return this.orgnization;
  }

  public Training setOrgnization(String orgnization) {
    this.orgnization = orgnization;
    return this;
  }

  public void unsetOrgnization() {
    this.orgnization = null;
  }

  /** Returns true if field orgnization is set (has been assigned a value) and false otherwise */
  public boolean isSetOrgnization() {
    return this.orgnization != null;
  }

  public void setOrgnizationIsSet(boolean value) {
    if (!value) {
      this.orgnization = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public Training setDescription(String description) {
    this.description = description;
    return this;
  }

  public void unsetDescription() {
    this.description = null;
  }

  /** Returns true if field description is set (has been assigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.description = null;
    }
  }

  public String getCreate_time() {
    return this.create_time;
  }

  public Training setCreate_time(String create_time) {
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

  public Training setUpdate_time(String update_time) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case PROFILE_ID:
      if (value == null) {
        unsetProfile_id();
      } else {
        setProfile_id((Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case START_DATE:
      if (value == null) {
        unsetStart_date();
      } else {
        setStart_date((String)value);
      }
      break;

    case END_DATE:
      if (value == null) {
        unsetEnd_date();
      } else {
        setEnd_date((String)value);
      }
      break;

    case ORGNIZATION:
      if (value == null) {
        unsetOrgnization();
      } else {
        setOrgnization((String)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
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

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case PROFILE_ID:
      return Integer.valueOf(getProfile_id());

    case NAME:
      return getName();

    case START_DATE:
      return getStart_date();

    case END_DATE:
      return getEnd_date();

    case ORGNIZATION:
      return getOrgnization();

    case DESCRIPTION:
      return getDescription();

    case CREATE_TIME:
      return getCreate_time();

    case UPDATE_TIME:
      return getUpdate_time();

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
    case PROFILE_ID:
      return isSetProfile_id();
    case NAME:
      return isSetName();
    case START_DATE:
      return isSetStart_date();
    case END_DATE:
      return isSetEnd_date();
    case ORGNIZATION:
      return isSetOrgnization();
    case DESCRIPTION:
      return isSetDescription();
    case CREATE_TIME:
      return isSetCreate_time();
    case UPDATE_TIME:
      return isSetUpdate_time();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Training)
      return this.equals((Training)that);
    return false;
  }

  public boolean equals(Training that) {
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

    boolean this_present_profile_id = true;
    boolean that_present_profile_id = true;
    if (this_present_profile_id || that_present_profile_id) {
      if (!(this_present_profile_id && that_present_profile_id))
        return false;
      if (this.profile_id != that.profile_id)
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

    boolean this_present_start_date = true && this.isSetStart_date();
    boolean that_present_start_date = true && that.isSetStart_date();
    if (this_present_start_date || that_present_start_date) {
      if (!(this_present_start_date && that_present_start_date))
        return false;
      if (!this.start_date.equals(that.start_date))
        return false;
    }

    boolean this_present_end_date = true && this.isSetEnd_date();
    boolean that_present_end_date = true && that.isSetEnd_date();
    if (this_present_end_date || that_present_end_date) {
      if (!(this_present_end_date && that_present_end_date))
        return false;
      if (!this.end_date.equals(that.end_date))
        return false;
    }

    boolean this_present_orgnization = true && this.isSetOrgnization();
    boolean that_present_orgnization = true && that.isSetOrgnization();
    if (this_present_orgnization || that_present_orgnization) {
      if (!(this_present_orgnization && that_present_orgnization))
        return false;
      if (!this.orgnization.equals(that.orgnization))
        return false;
    }

    boolean this_present_description = true && this.isSetDescription();
    boolean that_present_description = true && that.isSetDescription();
    if (this_present_description || that_present_description) {
      if (!(this_present_description && that_present_description))
        return false;
      if (!this.description.equals(that.description))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_profile_id = true;
    list.add(present_profile_id);
    if (present_profile_id)
      list.add(profile_id);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_start_date = true && (isSetStart_date());
    list.add(present_start_date);
    if (present_start_date)
      list.add(start_date);

    boolean present_end_date = true && (isSetEnd_date());
    list.add(present_end_date);
    if (present_end_date)
      list.add(end_date);

    boolean present_orgnization = true && (isSetOrgnization());
    list.add(present_orgnization);
    if (present_orgnization)
      list.add(orgnization);

    boolean present_description = true && (isSetDescription());
    list.add(present_description);
    if (present_description)
      list.add(description);

    boolean present_create_time = true && (isSetCreate_time());
    list.add(present_create_time);
    if (present_create_time)
      list.add(create_time);

    boolean present_update_time = true && (isSetUpdate_time());
    list.add(present_update_time);
    if (present_update_time)
      list.add(update_time);

    return list.hashCode();
  }

  @Override
  public int compareTo(Training other) {
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
    lastComparison = Boolean.valueOf(isSetProfile_id()).compareTo(other.isSetProfile_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProfile_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.profile_id, other.profile_id);
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
    lastComparison = Boolean.valueOf(isSetStart_date()).compareTo(other.isSetStart_date());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStart_date()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.start_date, other.start_date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEnd_date()).compareTo(other.isSetEnd_date());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnd_date()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.end_date, other.end_date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrgnization()).compareTo(other.isSetOrgnization());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrgnization()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgnization, other.orgnization);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(other.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.description, other.description);
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
    StringBuilder sb = new StringBuilder("Training(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("profile_id:");
    sb.append(this.profile_id);
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
    sb.append("start_date:");
    if (this.start_date == null) {
      sb.append("null");
    } else {
      sb.append(this.start_date);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("end_date:");
    if (this.end_date == null) {
      sb.append("null");
    } else {
      sb.append(this.end_date);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orgnization:");
    if (this.orgnization == null) {
      sb.append("null");
    } else {
      sb.append(this.orgnization);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("description:");
    if (this.description == null) {
      sb.append("null");
    } else {
      sb.append(this.description);
    }
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

  private static class TrainingStandardSchemeFactory implements SchemeFactory {
    public TrainingStandardScheme getScheme() {
      return new TrainingStandardScheme();
    }
  }

  private static class TrainingStandardScheme extends StandardScheme<Training> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Training struct) throws org.apache.thrift.TException {
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
          case 2: // PROFILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.profile_id = iprot.readI32();
              struct.setProfile_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // START_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.start_date = iprot.readString();
              struct.setStart_dateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.end_date = iprot.readString();
              struct.setEnd_dateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ORGNIZATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.orgnization = iprot.readString();
              struct.setOrgnizationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.description = iprot.readString();
              struct.setDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.create_time = iprot.readString();
              struct.setCreate_timeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.update_time = iprot.readString();
              struct.setUpdate_timeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Training struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROFILE_ID_FIELD_DESC);
      oprot.writeI32(struct.profile_id);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.start_date != null) {
        oprot.writeFieldBegin(START_DATE_FIELD_DESC);
        oprot.writeString(struct.start_date);
        oprot.writeFieldEnd();
      }
      if (struct.end_date != null) {
        oprot.writeFieldBegin(END_DATE_FIELD_DESC);
        oprot.writeString(struct.end_date);
        oprot.writeFieldEnd();
      }
      if (struct.orgnization != null) {
        oprot.writeFieldBegin(ORGNIZATION_FIELD_DESC);
        oprot.writeString(struct.orgnization);
        oprot.writeFieldEnd();
      }
      if (struct.description != null) {
        oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
        oprot.writeString(struct.description);
        oprot.writeFieldEnd();
      }
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TrainingTupleSchemeFactory implements SchemeFactory {
    public TrainingTupleScheme getScheme() {
      return new TrainingTupleScheme();
    }
  }

  private static class TrainingTupleScheme extends TupleScheme<Training> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Training struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetProfile_id()) {
        optionals.set(1);
      }
      if (struct.isSetName()) {
        optionals.set(2);
      }
      if (struct.isSetStart_date()) {
        optionals.set(3);
      }
      if (struct.isSetEnd_date()) {
        optionals.set(4);
      }
      if (struct.isSetOrgnization()) {
        optionals.set(5);
      }
      if (struct.isSetDescription()) {
        optionals.set(6);
      }
      if (struct.isSetCreate_time()) {
        optionals.set(7);
      }
      if (struct.isSetUpdate_time()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetProfile_id()) {
        oprot.writeI32(struct.profile_id);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetStart_date()) {
        oprot.writeString(struct.start_date);
      }
      if (struct.isSetEnd_date()) {
        oprot.writeString(struct.end_date);
      }
      if (struct.isSetOrgnization()) {
        oprot.writeString(struct.orgnization);
      }
      if (struct.isSetDescription()) {
        oprot.writeString(struct.description);
      }
      if (struct.isSetCreate_time()) {
        oprot.writeString(struct.create_time);
      }
      if (struct.isSetUpdate_time()) {
        oprot.writeString(struct.update_time);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Training struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.profile_id = iprot.readI32();
        struct.setProfile_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.start_date = iprot.readString();
        struct.setStart_dateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.end_date = iprot.readString();
        struct.setEnd_dateIsSet(true);
      }
      if (incoming.get(5)) {
        struct.orgnization = iprot.readString();
        struct.setOrgnizationIsSet(true);
      }
      if (incoming.get(6)) {
        struct.description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
      if (incoming.get(7)) {
        struct.create_time = iprot.readString();
        struct.setCreate_timeIsSet(true);
      }
      if (incoming.get(8)) {
        struct.update_time = iprot.readString();
        struct.setUpdate_timeIsSet(true);
      }
    }
  }

}

