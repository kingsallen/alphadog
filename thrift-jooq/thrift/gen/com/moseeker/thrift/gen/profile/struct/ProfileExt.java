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
public class ProfileExt implements org.apache.thrift.TBase<ProfileExt, ProfileExt._Fields>, java.io.Serializable, Cloneable, Comparable<ProfileExt> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProfileExt");

  private static final org.apache.thrift.protocol.TField PROFILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("profile_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HOMEPAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("homepage", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ASSESSMENT_FIELD_DESC = new org.apache.thrift.protocol.TField("assessment", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField INTEREST_FIELD_DESC = new org.apache.thrift.protocol.TField("interest", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("create_time", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProfileExtStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProfileExtTupleSchemeFactory());
  }

  public int profile_id; // required
  public String homepage; // required
  public String assessment; // required
  public String interest; // required
  public String create_time; // required
  public String update_time; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PROFILE_ID((short)1, "profile_id"),
    HOMEPAGE((short)2, "homepage"),
    ASSESSMENT((short)3, "assessment"),
    INTEREST((short)4, "interest"),
    CREATE_TIME((short)5, "create_time"),
    UPDATE_TIME((short)6, "update_time");

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
        case 1: // PROFILE_ID
          return PROFILE_ID;
        case 2: // HOMEPAGE
          return HOMEPAGE;
        case 3: // ASSESSMENT
          return ASSESSMENT;
        case 4: // INTEREST
          return INTEREST;
        case 5: // CREATE_TIME
          return CREATE_TIME;
        case 6: // UPDATE_TIME
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
  private static final int __PROFILE_ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PROFILE_ID, new org.apache.thrift.meta_data.FieldMetaData("profile_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HOMEPAGE, new org.apache.thrift.meta_data.FieldMetaData("homepage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ASSESSMENT, new org.apache.thrift.meta_data.FieldMetaData("assessment", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INTEREST, new org.apache.thrift.meta_data.FieldMetaData("interest", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("create_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProfileExt.class, metaDataMap);
  }

  public ProfileExt() {
  }

  public ProfileExt(
    int profile_id,
    String homepage,
    String assessment,
    String interest,
    String create_time,
    String update_time)
  {
    this();
    this.profile_id = profile_id;
    setProfile_idIsSet(true);
    this.homepage = homepage;
    this.assessment = assessment;
    this.interest = interest;
    this.create_time = create_time;
    this.update_time = update_time;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProfileExt(ProfileExt other) {
    __isset_bitfield = other.__isset_bitfield;
    this.profile_id = other.profile_id;
    if (other.isSetHomepage()) {
      this.homepage = other.homepage;
    }
    if (other.isSetAssessment()) {
      this.assessment = other.assessment;
    }
    if (other.isSetInterest()) {
      this.interest = other.interest;
    }
    if (other.isSetCreate_time()) {
      this.create_time = other.create_time;
    }
    if (other.isSetUpdate_time()) {
      this.update_time = other.update_time;
    }
  }

  public ProfileExt deepCopy() {
    return new ProfileExt(this);
  }

  @Override
  public void clear() {
    setProfile_idIsSet(false);
    this.profile_id = 0;
    this.homepage = null;
    this.assessment = null;
    this.interest = null;
    this.create_time = null;
    this.update_time = null;
  }

  public int getProfile_id() {
    return this.profile_id;
  }

  public ProfileExt setProfile_id(int profile_id) {
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

  public String getHomepage() {
    return this.homepage;
  }

  public ProfileExt setHomepage(String homepage) {
    this.homepage = homepage;
    return this;
  }

  public void unsetHomepage() {
    this.homepage = null;
  }

  /** Returns true if field homepage is set (has been assigned a value) and false otherwise */
  public boolean isSetHomepage() {
    return this.homepage != null;
  }

  public void setHomepageIsSet(boolean value) {
    if (!value) {
      this.homepage = null;
    }
  }

  public String getAssessment() {
    return this.assessment;
  }

  public ProfileExt setAssessment(String assessment) {
    this.assessment = assessment;
    return this;
  }

  public void unsetAssessment() {
    this.assessment = null;
  }

  /** Returns true if field assessment is set (has been assigned a value) and false otherwise */
  public boolean isSetAssessment() {
    return this.assessment != null;
  }

  public void setAssessmentIsSet(boolean value) {
    if (!value) {
      this.assessment = null;
    }
  }

  public String getInterest() {
    return this.interest;
  }

  public ProfileExt setInterest(String interest) {
    this.interest = interest;
    return this;
  }

  public void unsetInterest() {
    this.interest = null;
  }

  /** Returns true if field interest is set (has been assigned a value) and false otherwise */
  public boolean isSetInterest() {
    return this.interest != null;
  }

  public void setInterestIsSet(boolean value) {
    if (!value) {
      this.interest = null;
    }
  }

  public String getCreate_time() {
    return this.create_time;
  }

  public ProfileExt setCreate_time(String create_time) {
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

  public ProfileExt setUpdate_time(String update_time) {
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
    case PROFILE_ID:
      if (value == null) {
        unsetProfile_id();
      } else {
        setProfile_id((Integer)value);
      }
      break;

    case HOMEPAGE:
      if (value == null) {
        unsetHomepage();
      } else {
        setHomepage((String)value);
      }
      break;

    case ASSESSMENT:
      if (value == null) {
        unsetAssessment();
      } else {
        setAssessment((String)value);
      }
      break;

    case INTEREST:
      if (value == null) {
        unsetInterest();
      } else {
        setInterest((String)value);
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
    case PROFILE_ID:
      return Integer.valueOf(getProfile_id());

    case HOMEPAGE:
      return getHomepage();

    case ASSESSMENT:
      return getAssessment();

    case INTEREST:
      return getInterest();

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
    case PROFILE_ID:
      return isSetProfile_id();
    case HOMEPAGE:
      return isSetHomepage();
    case ASSESSMENT:
      return isSetAssessment();
    case INTEREST:
      return isSetInterest();
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
    if (that instanceof ProfileExt)
      return this.equals((ProfileExt)that);
    return false;
  }

  public boolean equals(ProfileExt that) {
    if (that == null)
      return false;

    boolean this_present_profile_id = true;
    boolean that_present_profile_id = true;
    if (this_present_profile_id || that_present_profile_id) {
      if (!(this_present_profile_id && that_present_profile_id))
        return false;
      if (this.profile_id != that.profile_id)
        return false;
    }

    boolean this_present_homepage = true && this.isSetHomepage();
    boolean that_present_homepage = true && that.isSetHomepage();
    if (this_present_homepage || that_present_homepage) {
      if (!(this_present_homepage && that_present_homepage))
        return false;
      if (!this.homepage.equals(that.homepage))
        return false;
    }

    boolean this_present_assessment = true && this.isSetAssessment();
    boolean that_present_assessment = true && that.isSetAssessment();
    if (this_present_assessment || that_present_assessment) {
      if (!(this_present_assessment && that_present_assessment))
        return false;
      if (!this.assessment.equals(that.assessment))
        return false;
    }

    boolean this_present_interest = true && this.isSetInterest();
    boolean that_present_interest = true && that.isSetInterest();
    if (this_present_interest || that_present_interest) {
      if (!(this_present_interest && that_present_interest))
        return false;
      if (!this.interest.equals(that.interest))
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

    boolean present_profile_id = true;
    list.add(present_profile_id);
    if (present_profile_id)
      list.add(profile_id);

    boolean present_homepage = true && (isSetHomepage());
    list.add(present_homepage);
    if (present_homepage)
      list.add(homepage);

    boolean present_assessment = true && (isSetAssessment());
    list.add(present_assessment);
    if (present_assessment)
      list.add(assessment);

    boolean present_interest = true && (isSetInterest());
    list.add(present_interest);
    if (present_interest)
      list.add(interest);

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
  public int compareTo(ProfileExt other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetHomepage()).compareTo(other.isSetHomepage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHomepage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.homepage, other.homepage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAssessment()).compareTo(other.isSetAssessment());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssessment()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assessment, other.assessment);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInterest()).compareTo(other.isSetInterest());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInterest()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.interest, other.interest);
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
    StringBuilder sb = new StringBuilder("ProfileExt(");
    boolean first = true;

    sb.append("profile_id:");
    sb.append(this.profile_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("homepage:");
    if (this.homepage == null) {
      sb.append("null");
    } else {
      sb.append(this.homepage);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("assessment:");
    if (this.assessment == null) {
      sb.append("null");
    } else {
      sb.append(this.assessment);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("interest:");
    if (this.interest == null) {
      sb.append("null");
    } else {
      sb.append(this.interest);
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

  private static class ProfileExtStandardSchemeFactory implements SchemeFactory {
    public ProfileExtStandardScheme getScheme() {
      return new ProfileExtStandardScheme();
    }
  }

  private static class ProfileExtStandardScheme extends StandardScheme<ProfileExt> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProfileExt struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PROFILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.profile_id = iprot.readI32();
              struct.setProfile_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HOMEPAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.homepage = iprot.readString();
              struct.setHomepageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ASSESSMENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.assessment = iprot.readString();
              struct.setAssessmentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INTEREST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.interest = iprot.readString();
              struct.setInterestIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.create_time = iprot.readString();
              struct.setCreate_timeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // UPDATE_TIME
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProfileExt struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PROFILE_ID_FIELD_DESC);
      oprot.writeI32(struct.profile_id);
      oprot.writeFieldEnd();
      if (struct.homepage != null) {
        oprot.writeFieldBegin(HOMEPAGE_FIELD_DESC);
        oprot.writeString(struct.homepage);
        oprot.writeFieldEnd();
      }
      if (struct.assessment != null) {
        oprot.writeFieldBegin(ASSESSMENT_FIELD_DESC);
        oprot.writeString(struct.assessment);
        oprot.writeFieldEnd();
      }
      if (struct.interest != null) {
        oprot.writeFieldBegin(INTEREST_FIELD_DESC);
        oprot.writeString(struct.interest);
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

  private static class ProfileExtTupleSchemeFactory implements SchemeFactory {
    public ProfileExtTupleScheme getScheme() {
      return new ProfileExtTupleScheme();
    }
  }

  private static class ProfileExtTupleScheme extends TupleScheme<ProfileExt> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProfileExt struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetProfile_id()) {
        optionals.set(0);
      }
      if (struct.isSetHomepage()) {
        optionals.set(1);
      }
      if (struct.isSetAssessment()) {
        optionals.set(2);
      }
      if (struct.isSetInterest()) {
        optionals.set(3);
      }
      if (struct.isSetCreate_time()) {
        optionals.set(4);
      }
      if (struct.isSetUpdate_time()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetProfile_id()) {
        oprot.writeI32(struct.profile_id);
      }
      if (struct.isSetHomepage()) {
        oprot.writeString(struct.homepage);
      }
      if (struct.isSetAssessment()) {
        oprot.writeString(struct.assessment);
      }
      if (struct.isSetInterest()) {
        oprot.writeString(struct.interest);
      }
      if (struct.isSetCreate_time()) {
        oprot.writeString(struct.create_time);
      }
      if (struct.isSetUpdate_time()) {
        oprot.writeString(struct.update_time);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProfileExt struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.profile_id = iprot.readI32();
        struct.setProfile_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.homepage = iprot.readString();
        struct.setHomepageIsSet(true);
      }
      if (incoming.get(2)) {
        struct.assessment = iprot.readString();
        struct.setAssessmentIsSet(true);
      }
      if (incoming.get(3)) {
        struct.interest = iprot.readString();
        struct.setInterestIsSet(true);
      }
      if (incoming.get(4)) {
        struct.create_time = iprot.readString();
        struct.setCreate_timeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.update_time = iprot.readString();
        struct.setUpdate_timeIsSet(true);
      }
    }
  }

}

