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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-5-3")
public class SchoolJob implements org.apache.thrift.TBase<SchoolJob, SchoolJob._Fields>, java.io.Serializable, Cloneable, Comparable<SchoolJob> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SchoolJob");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PROFILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("profile_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField START_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("startDate", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField END_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("endDate", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField END_UNTIL_NOW_FIELD_DESC = new org.apache.thrift.protocol.TField("end_until_now", org.apache.thrift.protocol.TType.I16, (short)5);
  private static final org.apache.thrift.protocol.TField POSITION_FIELD_DESC = new org.apache.thrift.protocol.TField("position", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("create_time", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SchoolJobStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SchoolJobTupleSchemeFactory());
  }

  public int id; // required
  public int profile_id; // required
  public String startDate; // required
  public String endDate; // required
  public short end_until_now; // required
  public String position; // required
  public String description; // required
  public String create_time; // required
  public String update_time; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PROFILE_ID((short)2, "profile_id"),
    START_DATE((short)3, "startDate"),
    END_DATE((short)4, "endDate"),
    END_UNTIL_NOW((short)5, "end_until_now"),
    POSITION((short)6, "position"),
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
        case 3: // START_DATE
          return START_DATE;
        case 4: // END_DATE
          return END_DATE;
        case 5: // END_UNTIL_NOW
          return END_UNTIL_NOW;
        case 6: // POSITION
          return POSITION;
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
  private static final int __END_UNTIL_NOW_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROFILE_ID, new org.apache.thrift.meta_data.FieldMetaData("profile_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.START_DATE, new org.apache.thrift.meta_data.FieldMetaData("startDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.END_DATE, new org.apache.thrift.meta_data.FieldMetaData("endDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.END_UNTIL_NOW, new org.apache.thrift.meta_data.FieldMetaData("end_until_now", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.POSITION, new org.apache.thrift.meta_data.FieldMetaData("position", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("create_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SchoolJob.class, metaDataMap);
  }

  public SchoolJob() {
  }

  public SchoolJob(
    int id,
    int profile_id,
    String startDate,
    String endDate,
    short end_until_now,
    String position,
    String description,
    String create_time,
    String update_time)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.profile_id = profile_id;
    setProfile_idIsSet(true);
    this.startDate = startDate;
    this.endDate = endDate;
    this.end_until_now = end_until_now;
    setEnd_until_nowIsSet(true);
    this.position = position;
    this.description = description;
    this.create_time = create_time;
    this.update_time = update_time;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SchoolJob(SchoolJob other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.profile_id = other.profile_id;
    if (other.isSetStartDate()) {
      this.startDate = other.startDate;
    }
    if (other.isSetEndDate()) {
      this.endDate = other.endDate;
    }
    this.end_until_now = other.end_until_now;
    if (other.isSetPosition()) {
      this.position = other.position;
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

  public SchoolJob deepCopy() {
    return new SchoolJob(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setProfile_idIsSet(false);
    this.profile_id = 0;
    this.startDate = null;
    this.endDate = null;
    setEnd_until_nowIsSet(false);
    this.end_until_now = 0;
    this.position = null;
    this.description = null;
    this.create_time = null;
    this.update_time = null;
  }

  public int getId() {
    return this.id;
  }

  public SchoolJob setId(int id) {
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

  public SchoolJob setProfile_id(int profile_id) {
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

  public String getStartDate() {
    return this.startDate;
  }

  public SchoolJob setStartDate(String startDate) {
    this.startDate = startDate;
    return this;
  }

  public void unsetStartDate() {
    this.startDate = null;
  }

  /** Returns true if field startDate is set (has been assigned a value) and false otherwise */
  public boolean isSetStartDate() {
    return this.startDate != null;
  }

  public void setStartDateIsSet(boolean value) {
    if (!value) {
      this.startDate = null;
    }
  }

  public String getEndDate() {
    return this.endDate;
  }

  public SchoolJob setEndDate(String endDate) {
    this.endDate = endDate;
    return this;
  }

  public void unsetEndDate() {
    this.endDate = null;
  }

  /** Returns true if field endDate is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDate() {
    return this.endDate != null;
  }

  public void setEndDateIsSet(boolean value) {
    if (!value) {
      this.endDate = null;
    }
  }

  public short getEnd_until_now() {
    return this.end_until_now;
  }

  public SchoolJob setEnd_until_now(short end_until_now) {
    this.end_until_now = end_until_now;
    setEnd_until_nowIsSet(true);
    return this;
  }

  public void unsetEnd_until_now() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __END_UNTIL_NOW_ISSET_ID);
  }

  /** Returns true if field end_until_now is set (has been assigned a value) and false otherwise */
  public boolean isSetEnd_until_now() {
    return EncodingUtils.testBit(__isset_bitfield, __END_UNTIL_NOW_ISSET_ID);
  }

  public void setEnd_until_nowIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __END_UNTIL_NOW_ISSET_ID, value);
  }

  public String getPosition() {
    return this.position;
  }

  public SchoolJob setPosition(String position) {
    this.position = position;
    return this;
  }

  public void unsetPosition() {
    this.position = null;
  }

  /** Returns true if field position is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition() {
    return this.position != null;
  }

  public void setPositionIsSet(boolean value) {
    if (!value) {
      this.position = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public SchoolJob setDescription(String description) {
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

  public SchoolJob setCreate_time(String create_time) {
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

  public SchoolJob setUpdate_time(String update_time) {
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

    case START_DATE:
      if (value == null) {
        unsetStartDate();
      } else {
        setStartDate((String)value);
      }
      break;

    case END_DATE:
      if (value == null) {
        unsetEndDate();
      } else {
        setEndDate((String)value);
      }
      break;

    case END_UNTIL_NOW:
      if (value == null) {
        unsetEnd_until_now();
      } else {
        setEnd_until_now((Short)value);
      }
      break;

    case POSITION:
      if (value == null) {
        unsetPosition();
      } else {
        setPosition((String)value);
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

    case START_DATE:
      return getStartDate();

    case END_DATE:
      return getEndDate();

    case END_UNTIL_NOW:
      return Short.valueOf(getEnd_until_now());

    case POSITION:
      return getPosition();

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
    case START_DATE:
      return isSetStartDate();
    case END_DATE:
      return isSetEndDate();
    case END_UNTIL_NOW:
      return isSetEnd_until_now();
    case POSITION:
      return isSetPosition();
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
    if (that instanceof SchoolJob)
      return this.equals((SchoolJob)that);
    return false;
  }

  public boolean equals(SchoolJob that) {
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

    boolean this_present_startDate = true && this.isSetStartDate();
    boolean that_present_startDate = true && that.isSetStartDate();
    if (this_present_startDate || that_present_startDate) {
      if (!(this_present_startDate && that_present_startDate))
        return false;
      if (!this.startDate.equals(that.startDate))
        return false;
    }

    boolean this_present_endDate = true && this.isSetEndDate();
    boolean that_present_endDate = true && that.isSetEndDate();
    if (this_present_endDate || that_present_endDate) {
      if (!(this_present_endDate && that_present_endDate))
        return false;
      if (!this.endDate.equals(that.endDate))
        return false;
    }

    boolean this_present_end_until_now = true;
    boolean that_present_end_until_now = true;
    if (this_present_end_until_now || that_present_end_until_now) {
      if (!(this_present_end_until_now && that_present_end_until_now))
        return false;
      if (this.end_until_now != that.end_until_now)
        return false;
    }

    boolean this_present_position = true && this.isSetPosition();
    boolean that_present_position = true && that.isSetPosition();
    if (this_present_position || that_present_position) {
      if (!(this_present_position && that_present_position))
        return false;
      if (!this.position.equals(that.position))
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

    boolean present_startDate = true && (isSetStartDate());
    list.add(present_startDate);
    if (present_startDate)
      list.add(startDate);

    boolean present_endDate = true && (isSetEndDate());
    list.add(present_endDate);
    if (present_endDate)
      list.add(endDate);

    boolean present_end_until_now = true;
    list.add(present_end_until_now);
    if (present_end_until_now)
      list.add(end_until_now);

    boolean present_position = true && (isSetPosition());
    list.add(present_position);
    if (present_position)
      list.add(position);

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
  public int compareTo(SchoolJob other) {
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
    lastComparison = Boolean.valueOf(isSetStartDate()).compareTo(other.isSetStartDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startDate, other.startDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEndDate()).compareTo(other.isSetEndDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDate, other.endDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEnd_until_now()).compareTo(other.isSetEnd_until_now());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnd_until_now()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.end_until_now, other.end_until_now);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPosition()).compareTo(other.isSetPosition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position, other.position);
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
    StringBuilder sb = new StringBuilder("SchoolJob(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("profile_id:");
    sb.append(this.profile_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("startDate:");
    if (this.startDate == null) {
      sb.append("null");
    } else {
      sb.append(this.startDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("endDate:");
    if (this.endDate == null) {
      sb.append("null");
    } else {
      sb.append(this.endDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("end_until_now:");
    sb.append(this.end_until_now);
    first = false;
    if (!first) sb.append(", ");
    sb.append("position:");
    if (this.position == null) {
      sb.append("null");
    } else {
      sb.append(this.position);
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

  private static class SchoolJobStandardSchemeFactory implements SchemeFactory {
    public SchoolJobStandardScheme getScheme() {
      return new SchoolJobStandardScheme();
    }
  }

  private static class SchoolJobStandardScheme extends StandardScheme<SchoolJob> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SchoolJob struct) throws org.apache.thrift.TException {
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
          case 3: // START_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.startDate = iprot.readString();
              struct.setStartDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // END_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.endDate = iprot.readString();
              struct.setEndDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_UNTIL_NOW
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.end_until_now = iprot.readI16();
              struct.setEnd_until_nowIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // POSITION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.position = iprot.readString();
              struct.setPositionIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SchoolJob struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PROFILE_ID_FIELD_DESC);
      oprot.writeI32(struct.profile_id);
      oprot.writeFieldEnd();
      if (struct.startDate != null) {
        oprot.writeFieldBegin(START_DATE_FIELD_DESC);
        oprot.writeString(struct.startDate);
        oprot.writeFieldEnd();
      }
      if (struct.endDate != null) {
        oprot.writeFieldBegin(END_DATE_FIELD_DESC);
        oprot.writeString(struct.endDate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(END_UNTIL_NOW_FIELD_DESC);
      oprot.writeI16(struct.end_until_now);
      oprot.writeFieldEnd();
      if (struct.position != null) {
        oprot.writeFieldBegin(POSITION_FIELD_DESC);
        oprot.writeString(struct.position);
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

  private static class SchoolJobTupleSchemeFactory implements SchemeFactory {
    public SchoolJobTupleScheme getScheme() {
      return new SchoolJobTupleScheme();
    }
  }

  private static class SchoolJobTupleScheme extends TupleScheme<SchoolJob> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SchoolJob struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetProfile_id()) {
        optionals.set(1);
      }
      if (struct.isSetStartDate()) {
        optionals.set(2);
      }
      if (struct.isSetEndDate()) {
        optionals.set(3);
      }
      if (struct.isSetEnd_until_now()) {
        optionals.set(4);
      }
      if (struct.isSetPosition()) {
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
      if (struct.isSetStartDate()) {
        oprot.writeString(struct.startDate);
      }
      if (struct.isSetEndDate()) {
        oprot.writeString(struct.endDate);
      }
      if (struct.isSetEnd_until_now()) {
        oprot.writeI16(struct.end_until_now);
      }
      if (struct.isSetPosition()) {
        oprot.writeString(struct.position);
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
    public void read(org.apache.thrift.protocol.TProtocol prot, SchoolJob struct) throws org.apache.thrift.TException {
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
        struct.startDate = iprot.readString();
        struct.setStartDateIsSet(true);
      }
      if (incoming.get(3)) {
        struct.endDate = iprot.readString();
        struct.setEndDateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.end_until_now = iprot.readI16();
        struct.setEnd_until_nowIsSet(true);
      }
      if (incoming.get(5)) {
        struct.position = iprot.readString();
        struct.setPositionIsSet(true);
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

