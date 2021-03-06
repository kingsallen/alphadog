/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.campaigndb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-04")
public class CampaignEdmUserrecommendedPositionsDO implements org.apache.thrift.TBase<CampaignEdmUserrecommendedPositionsDO, CampaignEdmUserrecommendedPositionsDO._Fields>, java.io.Serializable, Cloneable, Comparable<CampaignEdmUserrecommendedPositionsDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CampaignEdmUserrecommendedPositionsDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CAMPAIGN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("campaignId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField POSITION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("positionId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField SCORE_FIELD_DESC = new org.apache.thrift.protocol.TField("score", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CampaignEdmUserrecommendedPositionsDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CampaignEdmUserrecommendedPositionsDOTupleSchemeFactory();

  public int id; // optional
  public int campaignId; // optional
  public int userId; // optional
  public int positionId; // optional
  public double score; // optional
  public String createTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    CAMPAIGN_ID((short)2, "campaignId"),
    USER_ID((short)3, "userId"),
    POSITION_ID((short)4, "positionId"),
    SCORE((short)5, "score"),
    CREATE_TIME((short)6, "createTime");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

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
        case 1: // ID
          return ID;
        case 2: // CAMPAIGN_ID
          return CAMPAIGN_ID;
        case 3: // USER_ID
          return USER_ID;
        case 4: // POSITION_ID
          return POSITION_ID;
        case 5: // SCORE
          return SCORE;
        case 6: // CREATE_TIME
          return CREATE_TIME;
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
  private static final int __CAMPAIGNID_ISSET_ID = 1;
  private static final int __USERID_ISSET_ID = 2;
  private static final int __POSITIONID_ISSET_ID = 3;
  private static final int __SCORE_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.CAMPAIGN_ID,_Fields.USER_ID,_Fields.POSITION_ID,_Fields.SCORE,_Fields.CREATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CAMPAIGN_ID, new org.apache.thrift.meta_data.FieldMetaData("campaignId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.POSITION_ID, new org.apache.thrift.meta_data.FieldMetaData("positionId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SCORE, new org.apache.thrift.meta_data.FieldMetaData("score", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CampaignEdmUserrecommendedPositionsDO.class, metaDataMap);
  }

  public CampaignEdmUserrecommendedPositionsDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CampaignEdmUserrecommendedPositionsDO(CampaignEdmUserrecommendedPositionsDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.campaignId = other.campaignId;
    this.userId = other.userId;
    this.positionId = other.positionId;
    this.score = other.score;
    if (other.isSetCreateTime()) {
      this.createTime = other.createTime;
    }
  }

  public CampaignEdmUserrecommendedPositionsDO deepCopy() {
    return new CampaignEdmUserrecommendedPositionsDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setCampaignIdIsSet(false);
    this.campaignId = 0;
    setUserIdIsSet(false);
    this.userId = 0;
    setPositionIdIsSet(false);
    this.positionId = 0;
    setScoreIsSet(false);
    this.score = 0.0;
    this.createTime = null;
  }

  public int getId() {
    return this.id;
  }

  public CampaignEdmUserrecommendedPositionsDO setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getCampaignId() {
    return this.campaignId;
  }

  public CampaignEdmUserrecommendedPositionsDO setCampaignId(int campaignId) {
    this.campaignId = campaignId;
    setCampaignIdIsSet(true);
    return this;
  }

  public void unsetCampaignId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CAMPAIGNID_ISSET_ID);
  }

  /** Returns true if field campaignId is set (has been assigned a value) and false otherwise */
  public boolean isSetCampaignId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CAMPAIGNID_ISSET_ID);
  }

  public void setCampaignIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CAMPAIGNID_ISSET_ID, value);
  }

  public int getUserId() {
    return this.userId;
  }

  public CampaignEdmUserrecommendedPositionsDO setUserId(int userId) {
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

  public int getPositionId() {
    return this.positionId;
  }

  public CampaignEdmUserrecommendedPositionsDO setPositionId(int positionId) {
    this.positionId = positionId;
    setPositionIdIsSet(true);
    return this;
  }

  public void unsetPositionId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __POSITIONID_ISSET_ID);
  }

  /** Returns true if field positionId is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __POSITIONID_ISSET_ID);
  }

  public void setPositionIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __POSITIONID_ISSET_ID, value);
  }

  public double getScore() {
    return this.score;
  }

  public CampaignEdmUserrecommendedPositionsDO setScore(double score) {
    this.score = score;
    setScoreIsSet(true);
    return this;
  }

  public void unsetScore() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SCORE_ISSET_ID);
  }

  /** Returns true if field score is set (has been assigned a value) and false otherwise */
  public boolean isSetScore() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SCORE_ISSET_ID);
  }

  public void setScoreIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SCORE_ISSET_ID, value);
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public CampaignEdmUserrecommendedPositionsDO setCreateTime(String createTime) {
    this.createTime = createTime;
    return this;
  }

  public void unsetCreateTime() {
    this.createTime = null;
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return this.createTime != null;
  }

  public void setCreateTimeIsSet(boolean value) {
    if (!value) {
      this.createTime = null;
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

    case CAMPAIGN_ID:
      if (value == null) {
        unsetCampaignId();
      } else {
        setCampaignId((Integer)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;

    case POSITION_ID:
      if (value == null) {
        unsetPositionId();
      } else {
        setPositionId((Integer)value);
      }
      break;

    case SCORE:
      if (value == null) {
        unsetScore();
      } else {
        setScore((Double)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case CAMPAIGN_ID:
      return getCampaignId();

    case USER_ID:
      return getUserId();

    case POSITION_ID:
      return getPositionId();

    case SCORE:
      return getScore();

    case CREATE_TIME:
      return getCreateTime();

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
    case CAMPAIGN_ID:
      return isSetCampaignId();
    case USER_ID:
      return isSetUserId();
    case POSITION_ID:
      return isSetPositionId();
    case SCORE:
      return isSetScore();
    case CREATE_TIME:
      return isSetCreateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CampaignEdmUserrecommendedPositionsDO)
      return this.equals((CampaignEdmUserrecommendedPositionsDO)that);
    return false;
  }

  public boolean equals(CampaignEdmUserrecommendedPositionsDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_campaignId = true && this.isSetCampaignId();
    boolean that_present_campaignId = true && that.isSetCampaignId();
    if (this_present_campaignId || that_present_campaignId) {
      if (!(this_present_campaignId && that_present_campaignId))
        return false;
      if (this.campaignId != that.campaignId)
        return false;
    }

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    boolean this_present_positionId = true && this.isSetPositionId();
    boolean that_present_positionId = true && that.isSetPositionId();
    if (this_present_positionId || that_present_positionId) {
      if (!(this_present_positionId && that_present_positionId))
        return false;
      if (this.positionId != that.positionId)
        return false;
    }

    boolean this_present_score = true && this.isSetScore();
    boolean that_present_score = true && that.isSetScore();
    if (this_present_score || that_present_score) {
      if (!(this_present_score && that_present_score))
        return false;
      if (this.score != that.score)
        return false;
    }

    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (!this.createTime.equals(that.createTime))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetId()) ? 131071 : 524287);
    if (isSetId())
      hashCode = hashCode * 8191 + id;

    hashCode = hashCode * 8191 + ((isSetCampaignId()) ? 131071 : 524287);
    if (isSetCampaignId())
      hashCode = hashCode * 8191 + campaignId;

    hashCode = hashCode * 8191 + ((isSetUserId()) ? 131071 : 524287);
    if (isSetUserId())
      hashCode = hashCode * 8191 + userId;

    hashCode = hashCode * 8191 + ((isSetPositionId()) ? 131071 : 524287);
    if (isSetPositionId())
      hashCode = hashCode * 8191 + positionId;

    hashCode = hashCode * 8191 + ((isSetScore()) ? 131071 : 524287);
    if (isSetScore())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(score);

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(CampaignEdmUserrecommendedPositionsDO other) {
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
    lastComparison = Boolean.valueOf(isSetCampaignId()).compareTo(other.isSetCampaignId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCampaignId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.campaignId, other.campaignId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionId()).compareTo(other.isSetPositionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionId, other.positionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetScore()).compareTo(other.isSetScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScore()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.score, other.score);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
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
  public String toString() {
    StringBuilder sb = new StringBuilder("CampaignEdmUserrecommendedPositionsDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetCampaignId()) {
      if (!first) sb.append(", ");
      sb.append("campaignId:");
      sb.append(this.campaignId);
      first = false;
    }
    if (isSetUserId()) {
      if (!first) sb.append(", ");
      sb.append("userId:");
      sb.append(this.userId);
      first = false;
    }
    if (isSetPositionId()) {
      if (!first) sb.append(", ");
      sb.append("positionId:");
      sb.append(this.positionId);
      first = false;
    }
    if (isSetScore()) {
      if (!first) sb.append(", ");
      sb.append("score:");
      sb.append(this.score);
      first = false;
    }
    if (isSetCreateTime()) {
      if (!first) sb.append(", ");
      sb.append("createTime:");
      if (this.createTime == null) {
        sb.append("null");
      } else {
        sb.append(this.createTime);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CampaignEdmUserrecommendedPositionsDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CampaignEdmUserrecommendedPositionsDOStandardScheme getScheme() {
      return new CampaignEdmUserrecommendedPositionsDOStandardScheme();
    }
  }

  private static class CampaignEdmUserrecommendedPositionsDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<CampaignEdmUserrecommendedPositionsDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CampaignEdmUserrecommendedPositionsDO struct) throws org.apache.thrift.TException {
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
          case 2: // CAMPAIGN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.campaignId = iprot.readI32();
              struct.setCampaignIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // POSITION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.positionId = iprot.readI32();
              struct.setPositionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SCORE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.score = iprot.readDouble();
              struct.setScoreIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createTime = iprot.readString();
              struct.setCreateTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CampaignEdmUserrecommendedPositionsDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCampaignId()) {
        oprot.writeFieldBegin(CAMPAIGN_ID_FIELD_DESC);
        oprot.writeI32(struct.campaignId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUserId()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPositionId()) {
        oprot.writeFieldBegin(POSITION_ID_FIELD_DESC);
        oprot.writeI32(struct.positionId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetScore()) {
        oprot.writeFieldBegin(SCORE_FIELD_DESC);
        oprot.writeDouble(struct.score);
        oprot.writeFieldEnd();
      }
      if (struct.createTime != null) {
        if (struct.isSetCreateTime()) {
          oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
          oprot.writeString(struct.createTime);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CampaignEdmUserrecommendedPositionsDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CampaignEdmUserrecommendedPositionsDOTupleScheme getScheme() {
      return new CampaignEdmUserrecommendedPositionsDOTupleScheme();
    }
  }

  private static class CampaignEdmUserrecommendedPositionsDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<CampaignEdmUserrecommendedPositionsDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CampaignEdmUserrecommendedPositionsDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCampaignId()) {
        optionals.set(1);
      }
      if (struct.isSetUserId()) {
        optionals.set(2);
      }
      if (struct.isSetPositionId()) {
        optionals.set(3);
      }
      if (struct.isSetScore()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetCampaignId()) {
        oprot.writeI32(struct.campaignId);
      }
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
      if (struct.isSetPositionId()) {
        oprot.writeI32(struct.positionId);
      }
      if (struct.isSetScore()) {
        oprot.writeDouble(struct.score);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeString(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CampaignEdmUserrecommendedPositionsDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.campaignId = iprot.readI32();
        struct.setCampaignIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.positionId = iprot.readI32();
        struct.setPositionIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.score = iprot.readDouble();
        struct.setScoreIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTime = iprot.readString();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

