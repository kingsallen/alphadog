/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.candidate.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-04-06")
public class Canidate implements org.apache.thrift.TBase<Canidate, Canidate._Fields>, java.io.Serializable, Cloneable, Comparable<Canidate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Canidate");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PRESENTEE_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeUserId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PRESENTEE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PRESENTEE_FRIEND_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeFriendId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField PRESENTEE_FRIEND_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeFriendName", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField PRESENTEE_LOGO_FIELD_DESC = new org.apache.thrift.protocol.TField("presenteeLogo", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField IS_RECOM_FIELD_DESC = new org.apache.thrift.protocol.TField("isRecom", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CanidateStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CanidateTupleSchemeFactory();

  public int id; // optional
  public int presenteeUserId; // optional
  public java.lang.String presenteeName; // optional
  public int presenteeFriendId; // optional
  public java.lang.String presenteeFriendName; // optional
  public java.lang.String presenteeLogo; // optional
  public int isRecom; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    PRESENTEE_USER_ID((short)2, "presenteeUserId"),
    PRESENTEE_NAME((short)3, "presenteeName"),
    PRESENTEE_FRIEND_ID((short)4, "presenteeFriendId"),
    PRESENTEE_FRIEND_NAME((short)5, "presenteeFriendName"),
    PRESENTEE_LOGO((short)6, "presenteeLogo"),
    IS_RECOM((short)7, "isRecom");

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
        case 1: // ID
          return ID;
        case 2: // PRESENTEE_USER_ID
          return PRESENTEE_USER_ID;
        case 3: // PRESENTEE_NAME
          return PRESENTEE_NAME;
        case 4: // PRESENTEE_FRIEND_ID
          return PRESENTEE_FRIEND_ID;
        case 5: // PRESENTEE_FRIEND_NAME
          return PRESENTEE_FRIEND_NAME;
        case 6: // PRESENTEE_LOGO
          return PRESENTEE_LOGO;
        case 7: // IS_RECOM
          return IS_RECOM;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __PRESENTEEUSERID_ISSET_ID = 1;
  private static final int __PRESENTEEFRIENDID_ISSET_ID = 2;
  private static final int __ISRECOM_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.PRESENTEE_USER_ID,_Fields.PRESENTEE_NAME,_Fields.PRESENTEE_FRIEND_ID,_Fields.PRESENTEE_FRIEND_NAME,_Fields.PRESENTEE_LOGO,_Fields.IS_RECOM};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRESENTEE_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("presenteeUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRESENTEE_NAME, new org.apache.thrift.meta_data.FieldMetaData("presenteeName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRESENTEE_FRIEND_ID, new org.apache.thrift.meta_data.FieldMetaData("presenteeFriendId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRESENTEE_FRIEND_NAME, new org.apache.thrift.meta_data.FieldMetaData("presenteeFriendName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRESENTEE_LOGO, new org.apache.thrift.meta_data.FieldMetaData("presenteeLogo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_RECOM, new org.apache.thrift.meta_data.FieldMetaData("isRecom", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Canidate.class, metaDataMap);
  }

  public Canidate() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Canidate(Canidate other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.presenteeUserId = other.presenteeUserId;
    if (other.isSetPresenteeName()) {
      this.presenteeName = other.presenteeName;
    }
    this.presenteeFriendId = other.presenteeFriendId;
    if (other.isSetPresenteeFriendName()) {
      this.presenteeFriendName = other.presenteeFriendName;
    }
    if (other.isSetPresenteeLogo()) {
      this.presenteeLogo = other.presenteeLogo;
    }
    this.isRecom = other.isRecom;
  }

  public Canidate deepCopy() {
    return new Canidate(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setPresenteeUserIdIsSet(false);
    this.presenteeUserId = 0;
    this.presenteeName = null;
    setPresenteeFriendIdIsSet(false);
    this.presenteeFriendId = 0;
    this.presenteeFriendName = null;
    this.presenteeLogo = null;
    setIsRecomIsSet(false);
    this.isRecom = 0;
  }

  public int getId() {
    return this.id;
  }

  public Canidate setId(int id) {
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

  public int getPresenteeUserId() {
    return this.presenteeUserId;
  }

  public Canidate setPresenteeUserId(int presenteeUserId) {
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

  public java.lang.String getPresenteeName() {
    return this.presenteeName;
  }

  public Canidate setPresenteeName(java.lang.String presenteeName) {
    this.presenteeName = presenteeName;
    return this;
  }

  public void unsetPresenteeName() {
    this.presenteeName = null;
  }

  /** Returns true if field presenteeName is set (has been assigned a value) and false otherwise */
  public boolean isSetPresenteeName() {
    return this.presenteeName != null;
  }

  public void setPresenteeNameIsSet(boolean value) {
    if (!value) {
      this.presenteeName = null;
    }
  }

  public int getPresenteeFriendId() {
    return this.presenteeFriendId;
  }

  public Canidate setPresenteeFriendId(int presenteeFriendId) {
    this.presenteeFriendId = presenteeFriendId;
    setPresenteeFriendIdIsSet(true);
    return this;
  }

  public void unsetPresenteeFriendId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PRESENTEEFRIENDID_ISSET_ID);
  }

  /** Returns true if field presenteeFriendId is set (has been assigned a value) and false otherwise */
  public boolean isSetPresenteeFriendId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PRESENTEEFRIENDID_ISSET_ID);
  }

  public void setPresenteeFriendIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PRESENTEEFRIENDID_ISSET_ID, value);
  }

  public java.lang.String getPresenteeFriendName() {
    return this.presenteeFriendName;
  }

  public Canidate setPresenteeFriendName(java.lang.String presenteeFriendName) {
    this.presenteeFriendName = presenteeFriendName;
    return this;
  }

  public void unsetPresenteeFriendName() {
    this.presenteeFriendName = null;
  }

  /** Returns true if field presenteeFriendName is set (has been assigned a value) and false otherwise */
  public boolean isSetPresenteeFriendName() {
    return this.presenteeFriendName != null;
  }

  public void setPresenteeFriendNameIsSet(boolean value) {
    if (!value) {
      this.presenteeFriendName = null;
    }
  }

  public java.lang.String getPresenteeLogo() {
    return this.presenteeLogo;
  }

  public Canidate setPresenteeLogo(java.lang.String presenteeLogo) {
    this.presenteeLogo = presenteeLogo;
    return this;
  }

  public void unsetPresenteeLogo() {
    this.presenteeLogo = null;
  }

  /** Returns true if field presenteeLogo is set (has been assigned a value) and false otherwise */
  public boolean isSetPresenteeLogo() {
    return this.presenteeLogo != null;
  }

  public void setPresenteeLogoIsSet(boolean value) {
    if (!value) {
      this.presenteeLogo = null;
    }
  }

  public int getIsRecom() {
    return this.isRecom;
  }

  public Canidate setIsRecom(int isRecom) {
    this.isRecom = isRecom;
    setIsRecomIsSet(true);
    return this;
  }

  public void unsetIsRecom() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ISRECOM_ISSET_ID);
  }

  /** Returns true if field isRecom is set (has been assigned a value) and false otherwise */
  public boolean isSetIsRecom() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ISRECOM_ISSET_ID);
  }

  public void setIsRecomIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ISRECOM_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Integer)value);
      }
      break;

    case PRESENTEE_USER_ID:
      if (value == null) {
        unsetPresenteeUserId();
      } else {
        setPresenteeUserId((java.lang.Integer)value);
      }
      break;

    case PRESENTEE_NAME:
      if (value == null) {
        unsetPresenteeName();
      } else {
        setPresenteeName((java.lang.String)value);
      }
      break;

    case PRESENTEE_FRIEND_ID:
      if (value == null) {
        unsetPresenteeFriendId();
      } else {
        setPresenteeFriendId((java.lang.Integer)value);
      }
      break;

    case PRESENTEE_FRIEND_NAME:
      if (value == null) {
        unsetPresenteeFriendName();
      } else {
        setPresenteeFriendName((java.lang.String)value);
      }
      break;

    case PRESENTEE_LOGO:
      if (value == null) {
        unsetPresenteeLogo();
      } else {
        setPresenteeLogo((java.lang.String)value);
      }
      break;

    case IS_RECOM:
      if (value == null) {
        unsetIsRecom();
      } else {
        setIsRecom((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case PRESENTEE_USER_ID:
      return getPresenteeUserId();

    case PRESENTEE_NAME:
      return getPresenteeName();

    case PRESENTEE_FRIEND_ID:
      return getPresenteeFriendId();

    case PRESENTEE_FRIEND_NAME:
      return getPresenteeFriendName();

    case PRESENTEE_LOGO:
      return getPresenteeLogo();

    case IS_RECOM:
      return getIsRecom();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case PRESENTEE_USER_ID:
      return isSetPresenteeUserId();
    case PRESENTEE_NAME:
      return isSetPresenteeName();
    case PRESENTEE_FRIEND_ID:
      return isSetPresenteeFriendId();
    case PRESENTEE_FRIEND_NAME:
      return isSetPresenteeFriendName();
    case PRESENTEE_LOGO:
      return isSetPresenteeLogo();
    case IS_RECOM:
      return isSetIsRecom();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Canidate)
      return this.equals((Canidate)that);
    return false;
  }

  public boolean equals(Canidate that) {
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

    boolean this_present_presenteeUserId = true && this.isSetPresenteeUserId();
    boolean that_present_presenteeUserId = true && that.isSetPresenteeUserId();
    if (this_present_presenteeUserId || that_present_presenteeUserId) {
      if (!(this_present_presenteeUserId && that_present_presenteeUserId))
        return false;
      if (this.presenteeUserId != that.presenteeUserId)
        return false;
    }

    boolean this_present_presenteeName = true && this.isSetPresenteeName();
    boolean that_present_presenteeName = true && that.isSetPresenteeName();
    if (this_present_presenteeName || that_present_presenteeName) {
      if (!(this_present_presenteeName && that_present_presenteeName))
        return false;
      if (!this.presenteeName.equals(that.presenteeName))
        return false;
    }

    boolean this_present_presenteeFriendId = true && this.isSetPresenteeFriendId();
    boolean that_present_presenteeFriendId = true && that.isSetPresenteeFriendId();
    if (this_present_presenteeFriendId || that_present_presenteeFriendId) {
      if (!(this_present_presenteeFriendId && that_present_presenteeFriendId))
        return false;
      if (this.presenteeFriendId != that.presenteeFriendId)
        return false;
    }

    boolean this_present_presenteeFriendName = true && this.isSetPresenteeFriendName();
    boolean that_present_presenteeFriendName = true && that.isSetPresenteeFriendName();
    if (this_present_presenteeFriendName || that_present_presenteeFriendName) {
      if (!(this_present_presenteeFriendName && that_present_presenteeFriendName))
        return false;
      if (!this.presenteeFriendName.equals(that.presenteeFriendName))
        return false;
    }

    boolean this_present_presenteeLogo = true && this.isSetPresenteeLogo();
    boolean that_present_presenteeLogo = true && that.isSetPresenteeLogo();
    if (this_present_presenteeLogo || that_present_presenteeLogo) {
      if (!(this_present_presenteeLogo && that_present_presenteeLogo))
        return false;
      if (!this.presenteeLogo.equals(that.presenteeLogo))
        return false;
    }

    boolean this_present_isRecom = true && this.isSetIsRecom();
    boolean that_present_isRecom = true && that.isSetIsRecom();
    if (this_present_isRecom || that_present_isRecom) {
      if (!(this_present_isRecom && that_present_isRecom))
        return false;
      if (this.isRecom != that.isRecom)
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

    hashCode = hashCode * 8191 + ((isSetPresenteeUserId()) ? 131071 : 524287);
    if (isSetPresenteeUserId())
      hashCode = hashCode * 8191 + presenteeUserId;

    hashCode = hashCode * 8191 + ((isSetPresenteeName()) ? 131071 : 524287);
    if (isSetPresenteeName())
      hashCode = hashCode * 8191 + presenteeName.hashCode();

    hashCode = hashCode * 8191 + ((isSetPresenteeFriendId()) ? 131071 : 524287);
    if (isSetPresenteeFriendId())
      hashCode = hashCode * 8191 + presenteeFriendId;

    hashCode = hashCode * 8191 + ((isSetPresenteeFriendName()) ? 131071 : 524287);
    if (isSetPresenteeFriendName())
      hashCode = hashCode * 8191 + presenteeFriendName.hashCode();

    hashCode = hashCode * 8191 + ((isSetPresenteeLogo()) ? 131071 : 524287);
    if (isSetPresenteeLogo())
      hashCode = hashCode * 8191 + presenteeLogo.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsRecom()) ? 131071 : 524287);
    if (isSetIsRecom())
      hashCode = hashCode * 8191 + isRecom;

    return hashCode;
  }

  @Override
  public int compareTo(Canidate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
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
    lastComparison = java.lang.Boolean.valueOf(isSetPresenteeName()).compareTo(other.isSetPresenteeName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPresenteeName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.presenteeName, other.presenteeName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPresenteeFriendId()).compareTo(other.isSetPresenteeFriendId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPresenteeFriendId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.presenteeFriendId, other.presenteeFriendId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPresenteeFriendName()).compareTo(other.isSetPresenteeFriendName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPresenteeFriendName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.presenteeFriendName, other.presenteeFriendName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPresenteeLogo()).compareTo(other.isSetPresenteeLogo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPresenteeLogo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.presenteeLogo, other.presenteeLogo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsRecom()).compareTo(other.isSetIsRecom());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsRecom()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isRecom, other.isRecom);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Canidate(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetPresenteeUserId()) {
      if (!first) sb.append(", ");
      sb.append("presenteeUserId:");
      sb.append(this.presenteeUserId);
      first = false;
    }
    if (isSetPresenteeName()) {
      if (!first) sb.append(", ");
      sb.append("presenteeName:");
      if (this.presenteeName == null) {
        sb.append("null");
      } else {
        sb.append(this.presenteeName);
      }
      first = false;
    }
    if (isSetPresenteeFriendId()) {
      if (!first) sb.append(", ");
      sb.append("presenteeFriendId:");
      sb.append(this.presenteeFriendId);
      first = false;
    }
    if (isSetPresenteeFriendName()) {
      if (!first) sb.append(", ");
      sb.append("presenteeFriendName:");
      if (this.presenteeFriendName == null) {
        sb.append("null");
      } else {
        sb.append(this.presenteeFriendName);
      }
      first = false;
    }
    if (isSetPresenteeLogo()) {
      if (!first) sb.append(", ");
      sb.append("presenteeLogo:");
      if (this.presenteeLogo == null) {
        sb.append("null");
      } else {
        sb.append(this.presenteeLogo);
      }
      first = false;
    }
    if (isSetIsRecom()) {
      if (!first) sb.append(", ");
      sb.append("isRecom:");
      sb.append(this.isRecom);
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

  private static class CanidateStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CanidateStandardScheme getScheme() {
      return new CanidateStandardScheme();
    }
  }

  private static class CanidateStandardScheme extends org.apache.thrift.scheme.StandardScheme<Canidate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Canidate struct) throws org.apache.thrift.TException {
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
          case 2: // PRESENTEE_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.presenteeUserId = iprot.readI32();
              struct.setPresenteeUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRESENTEE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.presenteeName = iprot.readString();
              struct.setPresenteeNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PRESENTEE_FRIEND_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.presenteeFriendId = iprot.readI32();
              struct.setPresenteeFriendIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PRESENTEE_FRIEND_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.presenteeFriendName = iprot.readString();
              struct.setPresenteeFriendNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // PRESENTEE_LOGO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.presenteeLogo = iprot.readString();
              struct.setPresenteeLogoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // IS_RECOM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.isRecom = iprot.readI32();
              struct.setIsRecomIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Canidate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPresenteeUserId()) {
        oprot.writeFieldBegin(PRESENTEE_USER_ID_FIELD_DESC);
        oprot.writeI32(struct.presenteeUserId);
        oprot.writeFieldEnd();
      }
      if (struct.presenteeName != null) {
        if (struct.isSetPresenteeName()) {
          oprot.writeFieldBegin(PRESENTEE_NAME_FIELD_DESC);
          oprot.writeString(struct.presenteeName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetPresenteeFriendId()) {
        oprot.writeFieldBegin(PRESENTEE_FRIEND_ID_FIELD_DESC);
        oprot.writeI32(struct.presenteeFriendId);
        oprot.writeFieldEnd();
      }
      if (struct.presenteeFriendName != null) {
        if (struct.isSetPresenteeFriendName()) {
          oprot.writeFieldBegin(PRESENTEE_FRIEND_NAME_FIELD_DESC);
          oprot.writeString(struct.presenteeFriendName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.presenteeLogo != null) {
        if (struct.isSetPresenteeLogo()) {
          oprot.writeFieldBegin(PRESENTEE_LOGO_FIELD_DESC);
          oprot.writeString(struct.presenteeLogo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsRecom()) {
        oprot.writeFieldBegin(IS_RECOM_FIELD_DESC);
        oprot.writeI32(struct.isRecom);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CanidateTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CanidateTupleScheme getScheme() {
      return new CanidateTupleScheme();
    }
  }

  private static class CanidateTupleScheme extends org.apache.thrift.scheme.TupleScheme<Canidate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Canidate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetPresenteeUserId()) {
        optionals.set(1);
      }
      if (struct.isSetPresenteeName()) {
        optionals.set(2);
      }
      if (struct.isSetPresenteeFriendId()) {
        optionals.set(3);
      }
      if (struct.isSetPresenteeFriendName()) {
        optionals.set(4);
      }
      if (struct.isSetPresenteeLogo()) {
        optionals.set(5);
      }
      if (struct.isSetIsRecom()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetPresenteeUserId()) {
        oprot.writeI32(struct.presenteeUserId);
      }
      if (struct.isSetPresenteeName()) {
        oprot.writeString(struct.presenteeName);
      }
      if (struct.isSetPresenteeFriendId()) {
        oprot.writeI32(struct.presenteeFriendId);
      }
      if (struct.isSetPresenteeFriendName()) {
        oprot.writeString(struct.presenteeFriendName);
      }
      if (struct.isSetPresenteeLogo()) {
        oprot.writeString(struct.presenteeLogo);
      }
      if (struct.isSetIsRecom()) {
        oprot.writeI32(struct.isRecom);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Canidate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.presenteeUserId = iprot.readI32();
        struct.setPresenteeUserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.presenteeName = iprot.readString();
        struct.setPresenteeNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.presenteeFriendId = iprot.readI32();
        struct.setPresenteeFriendIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.presenteeFriendName = iprot.readString();
        struct.setPresenteeFriendNameIsSet(true);
      }
      if (incoming.get(5)) {
        struct.presenteeLogo = iprot.readString();
        struct.setPresenteeLogoIsSet(true);
      }
      if (incoming.get(6)) {
        struct.isRecom = iprot.readI32();
        struct.setIsRecomIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

