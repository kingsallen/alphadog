/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.config;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-12-23")
public class HrAwardConfigTemplate implements org.apache.thrift.TBase<HrAwardConfigTemplate, HrAwardConfigTemplate._Fields>, java.io.Serializable, Cloneable, Comparable<HrAwardConfigTemplate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrAwardConfigTemplate");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField AWARD_FIELD_DESC = new org.apache.thrift.protocol.TField("award", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField DISABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("disable", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PRIORITY_FIELD_DESC = new org.apache.thrift.protocol.TField("priority", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField TYPE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("type_id", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField RECRUIT_ORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("recruit_order", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField REWARD_FIELD_DESC = new org.apache.thrift.protocol.TField("reward", org.apache.thrift.protocol.TType.I64, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HrAwardConfigTemplateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HrAwardConfigTemplateTupleSchemeFactory());
  }

  public int id; // optional
  public String status; // optional
  public int award; // optional
  public int disable; // optional
  public int priority; // optional
  public int type_id; // optional
  public int recruit_order; // optional
  public long reward; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    STATUS((short)2, "status"),
    AWARD((short)3, "award"),
    DISABLE((short)4, "disable"),
    PRIORITY((short)5, "priority"),
    TYPE_ID((short)6, "type_id"),
    RECRUIT_ORDER((short)7, "recruit_order"),
    REWARD((short)8, "reward");

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
        case 2: // STATUS
          return STATUS;
        case 3: // AWARD
          return AWARD;
        case 4: // DISABLE
          return DISABLE;
        case 5: // PRIORITY
          return PRIORITY;
        case 6: // TYPE_ID
          return TYPE_ID;
        case 7: // RECRUIT_ORDER
          return RECRUIT_ORDER;
        case 8: // REWARD
          return REWARD;
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
  private static final int __AWARD_ISSET_ID = 1;
  private static final int __DISABLE_ISSET_ID = 2;
  private static final int __PRIORITY_ISSET_ID = 3;
  private static final int __TYPE_ID_ISSET_ID = 4;
  private static final int __RECRUIT_ORDER_ISSET_ID = 5;
  private static final int __REWARD_ISSET_ID = 6;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.STATUS,_Fields.AWARD,_Fields.DISABLE,_Fields.PRIORITY,_Fields.TYPE_ID,_Fields.RECRUIT_ORDER,_Fields.REWARD};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AWARD, new org.apache.thrift.meta_data.FieldMetaData("award", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DISABLE, new org.apache.thrift.meta_data.FieldMetaData("disable", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRIORITY, new org.apache.thrift.meta_data.FieldMetaData("priority", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TYPE_ID, new org.apache.thrift.meta_data.FieldMetaData("type_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RECRUIT_ORDER, new org.apache.thrift.meta_data.FieldMetaData("recruit_order", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REWARD, new org.apache.thrift.meta_data.FieldMetaData("reward", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrAwardConfigTemplate.class, metaDataMap);
  }

  public HrAwardConfigTemplate() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrAwardConfigTemplate(HrAwardConfigTemplate other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetStatus()) {
      this.status = other.status;
    }
    this.award = other.award;
    this.disable = other.disable;
    this.priority = other.priority;
    this.type_id = other.type_id;
    this.recruit_order = other.recruit_order;
    this.reward = other.reward;
  }

  public HrAwardConfigTemplate deepCopy() {
    return new HrAwardConfigTemplate(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.status = null;
    setAwardIsSet(false);
    this.award = 0;
    setDisableIsSet(false);
    this.disable = 0;
    setPriorityIsSet(false);
    this.priority = 0;
    setType_idIsSet(false);
    this.type_id = 0;
    setRecruit_orderIsSet(false);
    this.recruit_order = 0;
    setRewardIsSet(false);
    this.reward = 0;
  }

  public int getId() {
    return this.id;
  }

  public HrAwardConfigTemplate setId(int id) {
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

  public String getStatus() {
    return this.status;
  }

  public HrAwardConfigTemplate setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public int getAward() {
    return this.award;
  }

  public HrAwardConfigTemplate setAward(int award) {
    this.award = award;
    setAwardIsSet(true);
    return this;
  }

  public void unsetAward() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AWARD_ISSET_ID);
  }

  /** Returns true if field award is set (has been assigned a value) and false otherwise */
  public boolean isSetAward() {
    return EncodingUtils.testBit(__isset_bitfield, __AWARD_ISSET_ID);
  }

  public void setAwardIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AWARD_ISSET_ID, value);
  }

  public int getDisable() {
    return this.disable;
  }

  public HrAwardConfigTemplate setDisable(int disable) {
    this.disable = disable;
    setDisableIsSet(true);
    return this;
  }

  public void unsetDisable() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  /** Returns true if field disable is set (has been assigned a value) and false otherwise */
  public boolean isSetDisable() {
    return EncodingUtils.testBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  public void setDisableIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DISABLE_ISSET_ID, value);
  }

  public int getPriority() {
    return this.priority;
  }

  public HrAwardConfigTemplate setPriority(int priority) {
    this.priority = priority;
    setPriorityIsSet(true);
    return this;
  }

  public void unsetPriority() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRIORITY_ISSET_ID);
  }

  /** Returns true if field priority is set (has been assigned a value) and false otherwise */
  public boolean isSetPriority() {
    return EncodingUtils.testBit(__isset_bitfield, __PRIORITY_ISSET_ID);
  }

  public void setPriorityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRIORITY_ISSET_ID, value);
  }

  public int getType_id() {
    return this.type_id;
  }

  public HrAwardConfigTemplate setType_id(int type_id) {
    this.type_id = type_id;
    setType_idIsSet(true);
    return this;
  }

  public void unsetType_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TYPE_ID_ISSET_ID);
  }

  /** Returns true if field type_id is set (has been assigned a value) and false otherwise */
  public boolean isSetType_id() {
    return EncodingUtils.testBit(__isset_bitfield, __TYPE_ID_ISSET_ID);
  }

  public void setType_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TYPE_ID_ISSET_ID, value);
  }

  public int getRecruit_order() {
    return this.recruit_order;
  }

  public HrAwardConfigTemplate setRecruit_order(int recruit_order) {
    this.recruit_order = recruit_order;
    setRecruit_orderIsSet(true);
    return this;
  }

  public void unsetRecruit_order() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECRUIT_ORDER_ISSET_ID);
  }

  /** Returns true if field recruit_order is set (has been assigned a value) and false otherwise */
  public boolean isSetRecruit_order() {
    return EncodingUtils.testBit(__isset_bitfield, __RECRUIT_ORDER_ISSET_ID);
  }

  public void setRecruit_orderIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECRUIT_ORDER_ISSET_ID, value);
  }

  public long getReward() {
    return this.reward;
  }

  public HrAwardConfigTemplate setReward(long reward) {
    this.reward = reward;
    setRewardIsSet(true);
    return this;
  }

  public void unsetReward() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REWARD_ISSET_ID);
  }

  /** Returns true if field reward is set (has been assigned a value) and false otherwise */
  public boolean isSetReward() {
    return EncodingUtils.testBit(__isset_bitfield, __REWARD_ISSET_ID);
  }

  public void setRewardIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REWARD_ISSET_ID, value);
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

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    case AWARD:
      if (value == null) {
        unsetAward();
      } else {
        setAward((Integer)value);
      }
      break;

    case DISABLE:
      if (value == null) {
        unsetDisable();
      } else {
        setDisable((Integer)value);
      }
      break;

    case PRIORITY:
      if (value == null) {
        unsetPriority();
      } else {
        setPriority((Integer)value);
      }
      break;

    case TYPE_ID:
      if (value == null) {
        unsetType_id();
      } else {
        setType_id((Integer)value);
      }
      break;

    case RECRUIT_ORDER:
      if (value == null) {
        unsetRecruit_order();
      } else {
        setRecruit_order((Integer)value);
      }
      break;

    case REWARD:
      if (value == null) {
        unsetReward();
      } else {
        setReward((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case STATUS:
      return getStatus();

    case AWARD:
      return getAward();

    case DISABLE:
      return getDisable();

    case PRIORITY:
      return getPriority();

    case TYPE_ID:
      return getType_id();

    case RECRUIT_ORDER:
      return getRecruit_order();

    case REWARD:
      return getReward();

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
    case STATUS:
      return isSetStatus();
    case AWARD:
      return isSetAward();
    case DISABLE:
      return isSetDisable();
    case PRIORITY:
      return isSetPriority();
    case TYPE_ID:
      return isSetType_id();
    case RECRUIT_ORDER:
      return isSetRecruit_order();
    case REWARD:
      return isSetReward();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HrAwardConfigTemplate)
      return this.equals((HrAwardConfigTemplate)that);
    return false;
  }

  public boolean equals(HrAwardConfigTemplate that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    boolean this_present_award = true && this.isSetAward();
    boolean that_present_award = true && that.isSetAward();
    if (this_present_award || that_present_award) {
      if (!(this_present_award && that_present_award))
        return false;
      if (this.award != that.award)
        return false;
    }

    boolean this_present_disable = true && this.isSetDisable();
    boolean that_present_disable = true && that.isSetDisable();
    if (this_present_disable || that_present_disable) {
      if (!(this_present_disable && that_present_disable))
        return false;
      if (this.disable != that.disable)
        return false;
    }

    boolean this_present_priority = true && this.isSetPriority();
    boolean that_present_priority = true && that.isSetPriority();
    if (this_present_priority || that_present_priority) {
      if (!(this_present_priority && that_present_priority))
        return false;
      if (this.priority != that.priority)
        return false;
    }

    boolean this_present_type_id = true && this.isSetType_id();
    boolean that_present_type_id = true && that.isSetType_id();
    if (this_present_type_id || that_present_type_id) {
      if (!(this_present_type_id && that_present_type_id))
        return false;
      if (this.type_id != that.type_id)
        return false;
    }

    boolean this_present_recruit_order = true && this.isSetRecruit_order();
    boolean that_present_recruit_order = true && that.isSetRecruit_order();
    if (this_present_recruit_order || that_present_recruit_order) {
      if (!(this_present_recruit_order && that_present_recruit_order))
        return false;
      if (this.recruit_order != that.recruit_order)
        return false;
    }

    boolean this_present_reward = true && this.isSetReward();
    boolean that_present_reward = true && that.isSetReward();
    if (this_present_reward || that_present_reward) {
      if (!(this_present_reward && that_present_reward))
        return false;
      if (this.reward != that.reward)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_status = true && (isSetStatus());
    list.add(present_status);
    if (present_status)
      list.add(status);

    boolean present_award = true && (isSetAward());
    list.add(present_award);
    if (present_award)
      list.add(award);

    boolean present_disable = true && (isSetDisable());
    list.add(present_disable);
    if (present_disable)
      list.add(disable);

    boolean present_priority = true && (isSetPriority());
    list.add(present_priority);
    if (present_priority)
      list.add(priority);

    boolean present_type_id = true && (isSetType_id());
    list.add(present_type_id);
    if (present_type_id)
      list.add(type_id);

    boolean present_recruit_order = true && (isSetRecruit_order());
    list.add(present_recruit_order);
    if (present_recruit_order)
      list.add(recruit_order);

    boolean present_reward = true && (isSetReward());
    list.add(present_reward);
    if (present_reward)
      list.add(reward);

    return list.hashCode();
  }

  @Override
  public int compareTo(HrAwardConfigTemplate other) {
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
    lastComparison = Boolean.valueOf(isSetAward()).compareTo(other.isSetAward());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAward()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.award, other.award);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDisable()).compareTo(other.isSetDisable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.disable, other.disable);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPriority()).compareTo(other.isSetPriority());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPriority()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.priority, other.priority);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType_id()).compareTo(other.isSetType_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type_id, other.type_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecruit_order()).compareTo(other.isSetRecruit_order());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecruit_order()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recruit_order, other.recruit_order);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReward()).compareTo(other.isSetReward());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReward()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reward, other.reward);
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
    StringBuilder sb = new StringBuilder("HrAwardConfigTemplate(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetStatus()) {
      if (!first) sb.append(", ");
      sb.append("status:");
      if (this.status == null) {
        sb.append("null");
      } else {
        sb.append(this.status);
      }
      first = false;
    }
    if (isSetAward()) {
      if (!first) sb.append(", ");
      sb.append("award:");
      sb.append(this.award);
      first = false;
    }
    if (isSetDisable()) {
      if (!first) sb.append(", ");
      sb.append("disable:");
      sb.append(this.disable);
      first = false;
    }
    if (isSetPriority()) {
      if (!first) sb.append(", ");
      sb.append("priority:");
      sb.append(this.priority);
      first = false;
    }
    if (isSetType_id()) {
      if (!first) sb.append(", ");
      sb.append("type_id:");
      sb.append(this.type_id);
      first = false;
    }
    if (isSetRecruit_order()) {
      if (!first) sb.append(", ");
      sb.append("recruit_order:");
      sb.append(this.recruit_order);
      first = false;
    }
    if (isSetReward()) {
      if (!first) sb.append(", ");
      sb.append("reward:");
      sb.append(this.reward);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HrAwardConfigTemplateStandardSchemeFactory implements SchemeFactory {
    public HrAwardConfigTemplateStandardScheme getScheme() {
      return new HrAwardConfigTemplateStandardScheme();
    }
  }

  private static class HrAwardConfigTemplateStandardScheme extends StandardScheme<HrAwardConfigTemplate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrAwardConfigTemplate struct) throws org.apache.thrift.TException {
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
          case 2: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AWARD
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.award = iprot.readI32();
              struct.setAwardIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DISABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.disable = iprot.readI32();
              struct.setDisableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PRIORITY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.priority = iprot.readI32();
              struct.setPriorityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // TYPE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type_id = iprot.readI32();
              struct.setType_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // RECRUIT_ORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.recruit_order = iprot.readI32();
              struct.setRecruit_orderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // REWARD
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.reward = iprot.readI64();
              struct.setRewardIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrAwardConfigTemplate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.status != null) {
        if (struct.isSetStatus()) {
          oprot.writeFieldBegin(STATUS_FIELD_DESC);
          oprot.writeString(struct.status);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetAward()) {
        oprot.writeFieldBegin(AWARD_FIELD_DESC);
        oprot.writeI32(struct.award);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDisable()) {
        oprot.writeFieldBegin(DISABLE_FIELD_DESC);
        oprot.writeI32(struct.disable);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPriority()) {
        oprot.writeFieldBegin(PRIORITY_FIELD_DESC);
        oprot.writeI32(struct.priority);
        oprot.writeFieldEnd();
      }
      if (struct.isSetType_id()) {
        oprot.writeFieldBegin(TYPE_ID_FIELD_DESC);
        oprot.writeI32(struct.type_id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRecruit_order()) {
        oprot.writeFieldBegin(RECRUIT_ORDER_FIELD_DESC);
        oprot.writeI32(struct.recruit_order);
        oprot.writeFieldEnd();
      }
      if (struct.isSetReward()) {
        oprot.writeFieldBegin(REWARD_FIELD_DESC);
        oprot.writeI64(struct.reward);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrAwardConfigTemplateTupleSchemeFactory implements SchemeFactory {
    public HrAwardConfigTemplateTupleScheme getScheme() {
      return new HrAwardConfigTemplateTupleScheme();
    }
  }

  private static class HrAwardConfigTemplateTupleScheme extends TupleScheme<HrAwardConfigTemplate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrAwardConfigTemplate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetStatus()) {
        optionals.set(1);
      }
      if (struct.isSetAward()) {
        optionals.set(2);
      }
      if (struct.isSetDisable()) {
        optionals.set(3);
      }
      if (struct.isSetPriority()) {
        optionals.set(4);
      }
      if (struct.isSetType_id()) {
        optionals.set(5);
      }
      if (struct.isSetRecruit_order()) {
        optionals.set(6);
      }
      if (struct.isSetReward()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
      if (struct.isSetAward()) {
        oprot.writeI32(struct.award);
      }
      if (struct.isSetDisable()) {
        oprot.writeI32(struct.disable);
      }
      if (struct.isSetPriority()) {
        oprot.writeI32(struct.priority);
      }
      if (struct.isSetType_id()) {
        oprot.writeI32(struct.type_id);
      }
      if (struct.isSetRecruit_order()) {
        oprot.writeI32(struct.recruit_order);
      }
      if (struct.isSetReward()) {
        oprot.writeI64(struct.reward);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrAwardConfigTemplate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(2)) {
        struct.award = iprot.readI32();
        struct.setAwardIsSet(true);
      }
      if (incoming.get(3)) {
        struct.disable = iprot.readI32();
        struct.setDisableIsSet(true);
      }
      if (incoming.get(4)) {
        struct.priority = iprot.readI32();
        struct.setPriorityIsSet(true);
      }
      if (incoming.get(5)) {
        struct.type_id = iprot.readI32();
        struct.setType_idIsSet(true);
      }
      if (incoming.get(6)) {
        struct.recruit_order = iprot.readI32();
        struct.setRecruit_orderIsSet(true);
      }
      if (incoming.get(7)) {
        struct.reward = iprot.readI64();
        struct.setRewardIsSet(true);
      }
    }
  }

}

