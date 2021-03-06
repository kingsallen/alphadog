/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.referral.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-04-25")
public class ReferralProgressQueryInfo implements org.apache.thrift.TBase<ReferralProgressQueryInfo, ReferralProgressQueryInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ReferralProgressQueryInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReferralProgressQueryInfo");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PRESENTEE_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeUserId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField APPLY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("applyId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("companyId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PROGRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("progress", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ReferralProgressQueryInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ReferralProgressQueryInfoTupleSchemeFactory();

  public int userId; // optional
  public int presenteeUserId; // optional
  public int applyId; // optional
  public int companyId; // optional
  public int progress; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "userId"),
    PRESENTEE_USER_ID((short)2, "presenteeUserId"),
    APPLY_ID((short)3, "applyId"),
    COMPANY_ID((short)4, "companyId"),
    PROGRESS((short)5, "progress");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // PRESENTEE_USER_ID
          return PRESENTEE_USER_ID;
        case 3: // APPLY_ID
          return APPLY_ID;
        case 4: // COMPANY_ID
          return COMPANY_ID;
        case 5: // PROGRESS
          return PROGRESS;
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
  private static final int __USERID_ISSET_ID = 0;
  private static final int __PRESENTEEUSERID_ISSET_ID = 1;
  private static final int __APPLYID_ISSET_ID = 2;
  private static final int __COMPANYID_ISSET_ID = 3;
  private static final int __PROGRESS_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.USER_ID,_Fields.PRESENTEE_USER_ID,_Fields.APPLY_ID,_Fields.COMPANY_ID,_Fields.PROGRESS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRESENTEE_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("presenteeUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.APPLY_ID, new org.apache.thrift.meta_data.FieldMetaData("applyId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("companyId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PROGRESS, new org.apache.thrift.meta_data.FieldMetaData("progress", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReferralProgressQueryInfo.class, metaDataMap);
  }

  public ReferralProgressQueryInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReferralProgressQueryInfo(ReferralProgressQueryInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.userId = other.userId;
    this.presenteeUserId = other.presenteeUserId;
    this.applyId = other.applyId;
    this.companyId = other.companyId;
    this.progress = other.progress;
  }

  public ReferralProgressQueryInfo deepCopy() {
    return new ReferralProgressQueryInfo(this);
  }

  @Override
  public void clear() {
    setUserIdIsSet(false);
    this.userId = 0;
    setPresenteeUserIdIsSet(false);
    this.presenteeUserId = 0;
    setApplyIdIsSet(false);
    this.applyId = 0;
    setCompanyIdIsSet(false);
    this.companyId = 0;
    setProgressIsSet(false);
    this.progress = 0;
  }

  public int getUserId() {
    return this.userId;
  }

  public ReferralProgressQueryInfo setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);
    return this;
  }

  public void unsetUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
  }

  public int getPresenteeUserId() {
    return this.presenteeUserId;
  }

  public ReferralProgressQueryInfo setPresenteeUserId(int presenteeUserId) {
    this.presenteeUserId = presenteeUserId;
    setPresenteeUserIdIsSet(true);
    return this;
  }

  public void unsetPresenteeUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PRESENTEEUSERID_ISSET_ID);
  }

  /** Returns true if field presenteeUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetPresenteeUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PRESENTEEUSERID_ISSET_ID);
  }

  public void setPresenteeUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PRESENTEEUSERID_ISSET_ID, value);
  }

  public int getApplyId() {
    return this.applyId;
  }

  public ReferralProgressQueryInfo setApplyId(int applyId) {
    this.applyId = applyId;
    setApplyIdIsSet(true);
    return this;
  }

  public void unsetApplyId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __APPLYID_ISSET_ID);
  }

  /** Returns true if field applyId is set (has been assigned a value) and false otherwise */
  public boolean isSetApplyId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __APPLYID_ISSET_ID);
  }

  public void setApplyIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __APPLYID_ISSET_ID, value);
  }

  public int getCompanyId() {
    return this.companyId;
  }

  public ReferralProgressQueryInfo setCompanyId(int companyId) {
    this.companyId = companyId;
    setCompanyIdIsSet(true);
    return this;
  }

  public void unsetCompanyId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  /** Returns true if field companyId is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  public void setCompanyIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMPANYID_ISSET_ID, value);
  }

  public int getProgress() {
    return this.progress;
  }

  public ReferralProgressQueryInfo setProgress(int progress) {
    this.progress = progress;
    setProgressIsSet(true);
    return this;
  }

  public void unsetProgress() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PROGRESS_ISSET_ID);
  }

  /** Returns true if field progress is set (has been assigned a value) and false otherwise */
  public boolean isSetProgress() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PROGRESS_ISSET_ID);
  }

  public void setProgressIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PROGRESS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((java.lang.Integer)value);
      }
      break;

    case PRESENTEE_USER_ID:
      if (value == null) {
        unsetPresenteeUserId();
      } else {
        setPresenteeUserId((java.lang.Integer)value);
      }
      break;

    case APPLY_ID:
      if (value == null) {
        unsetApplyId();
      } else {
        setApplyId((java.lang.Integer)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompanyId();
      } else {
        setCompanyId((java.lang.Integer)value);
      }
      break;

    case PROGRESS:
      if (value == null) {
        unsetProgress();
      } else {
        setProgress((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUserId();

    case PRESENTEE_USER_ID:
      return getPresenteeUserId();

    case APPLY_ID:
      return getApplyId();

    case COMPANY_ID:
      return getCompanyId();

    case PROGRESS:
      return getProgress();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserId();
    case PRESENTEE_USER_ID:
      return isSetPresenteeUserId();
    case APPLY_ID:
      return isSetApplyId();
    case COMPANY_ID:
      return isSetCompanyId();
    case PROGRESS:
      return isSetProgress();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ReferralProgressQueryInfo)
      return this.equals((ReferralProgressQueryInfo)that);
    return false;
  }

  public boolean equals(ReferralProgressQueryInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    boolean this_present_presenteeUserId = true && this.isSetPresenteeUserId();
    boolean that_present_presenteeUserId = true && that.isSetPresenteeUserId();
    if (this_present_presenteeUserId || that_present_presenteeUserId) {
      if (!(this_present_presenteeUserId && that_present_presenteeUserId))
        return false;
      if (this.presenteeUserId != that.presenteeUserId)
        return false;
    }

    boolean this_present_applyId = true && this.isSetApplyId();
    boolean that_present_applyId = true && that.isSetApplyId();
    if (this_present_applyId || that_present_applyId) {
      if (!(this_present_applyId && that_present_applyId))
        return false;
      if (this.applyId != that.applyId)
        return false;
    }

    boolean this_present_companyId = true && this.isSetCompanyId();
    boolean that_present_companyId = true && that.isSetCompanyId();
    if (this_present_companyId || that_present_companyId) {
      if (!(this_present_companyId && that_present_companyId))
        return false;
      if (this.companyId != that.companyId)
        return false;
    }

    boolean this_present_progress = true && this.isSetProgress();
    boolean that_present_progress = true && that.isSetProgress();
    if (this_present_progress || that_present_progress) {
      if (!(this_present_progress && that_present_progress))
        return false;
      if (this.progress != that.progress)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetUserId()) ? 131071 : 524287);
    if (isSetUserId())
      hashCode = hashCode * 8191 + userId;

    hashCode = hashCode * 8191 + ((isSetPresenteeUserId()) ? 131071 : 524287);
    if (isSetPresenteeUserId())
      hashCode = hashCode * 8191 + presenteeUserId;

    hashCode = hashCode * 8191 + ((isSetApplyId()) ? 131071 : 524287);
    if (isSetApplyId())
      hashCode = hashCode * 8191 + applyId;

    hashCode = hashCode * 8191 + ((isSetCompanyId()) ? 131071 : 524287);
    if (isSetCompanyId())
      hashCode = hashCode * 8191 + companyId;

    hashCode = hashCode * 8191 + ((isSetProgress()) ? 131071 : 524287);
    if (isSetProgress())
      hashCode = hashCode * 8191 + progress;

    return hashCode;
  }

  @Override
  public int compareTo(ReferralProgressQueryInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPresenteeUserId()).compareTo(other.isSetPresenteeUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPresenteeUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.presenteeUserId, other.presenteeUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetApplyId()).compareTo(other.isSetApplyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApplyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.applyId, other.applyId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCompanyId()).compareTo(other.isSetCompanyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyId, other.companyId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetProgress()).compareTo(other.isSetProgress());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProgress()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.progress, other.progress);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ReferralProgressQueryInfo(");
    boolean first = true;

    if (isSetUserId()) {
      sb.append("userId:");
      sb.append(this.userId);
      first = false;
    }
    if (isSetPresenteeUserId()) {
      if (!first) sb.append(", ");
      sb.append("presenteeUserId:");
      sb.append(this.presenteeUserId);
      first = false;
    }
    if (isSetApplyId()) {
      if (!first) sb.append(", ");
      sb.append("applyId:");
      sb.append(this.applyId);
      first = false;
    }
    if (isSetCompanyId()) {
      if (!first) sb.append(", ");
      sb.append("companyId:");
      sb.append(this.companyId);
      first = false;
    }
    if (isSetProgress()) {
      if (!first) sb.append(", ");
      sb.append("progress:");
      sb.append(this.progress);
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

  private static class ReferralProgressQueryInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReferralProgressQueryInfoStandardScheme getScheme() {
      return new ReferralProgressQueryInfoStandardScheme();
    }
  }

  private static class ReferralProgressQueryInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<ReferralProgressQueryInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReferralProgressQueryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRESENTEE_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.presenteeUserId = iprot.readI32();
              struct.setPresenteeUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // APPLY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.applyId = iprot.readI32();
              struct.setApplyIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.companyId = iprot.readI32();
              struct.setCompanyIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PROGRESS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.progress = iprot.readI32();
              struct.setProgressIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReferralProgressQueryInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetUserId()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPresenteeUserId()) {
        oprot.writeFieldBegin(PRESENTEE_USER_ID_FIELD_DESC);
        oprot.writeI32(struct.presenteeUserId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetApplyId()) {
        oprot.writeFieldBegin(APPLY_ID_FIELD_DESC);
        oprot.writeI32(struct.applyId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCompanyId()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.companyId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetProgress()) {
        oprot.writeFieldBegin(PROGRESS_FIELD_DESC);
        oprot.writeI32(struct.progress);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReferralProgressQueryInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReferralProgressQueryInfoTupleScheme getScheme() {
      return new ReferralProgressQueryInfoTupleScheme();
    }
  }

  private static class ReferralProgressQueryInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<ReferralProgressQueryInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReferralProgressQueryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUserId()) {
        optionals.set(0);
      }
      if (struct.isSetPresenteeUserId()) {
        optionals.set(1);
      }
      if (struct.isSetApplyId()) {
        optionals.set(2);
      }
      if (struct.isSetCompanyId()) {
        optionals.set(3);
      }
      if (struct.isSetProgress()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
      if (struct.isSetPresenteeUserId()) {
        oprot.writeI32(struct.presenteeUserId);
      }
      if (struct.isSetApplyId()) {
        oprot.writeI32(struct.applyId);
      }
      if (struct.isSetCompanyId()) {
        oprot.writeI32(struct.companyId);
      }
      if (struct.isSetProgress()) {
        oprot.writeI32(struct.progress);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReferralProgressQueryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.presenteeUserId = iprot.readI32();
        struct.setPresenteeUserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.applyId = iprot.readI32();
        struct.setApplyIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.companyId = iprot.readI32();
        struct.setCompanyIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.progress = iprot.readI32();
        struct.setProgressIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

