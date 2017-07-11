/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.position.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-06-26")
public class ThirdPartyPositionForSynchronizationWithAccount implements org.apache.thrift.TBase<ThirdPartyPositionForSynchronizationWithAccount, ThirdPartyPositionForSynchronizationWithAccount._Fields>, java.io.Serializable, Cloneable, Comparable<ThirdPartyPositionForSynchronizationWithAccount> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThirdPartyPositionForSynchronizationWithAccount");

  private static final org.apache.thrift.protocol.TField USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("user_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PASSWORD_FIELD_DESC = new org.apache.thrift.protocol.TField("password", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MEMBER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("member_name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField POSITION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("position_id", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CHANNEL_FIELD_DESC = new org.apache.thrift.protocol.TField("channel", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField POSITION_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("position_info", org.apache.thrift.protocol.TType.STRUCT, (short)6);
  private static final org.apache.thrift.protocol.TField COMPANY_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("company_name", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("account_id", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ThirdPartyPositionForSynchronizationWithAccountStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ThirdPartyPositionForSynchronizationWithAccountTupleSchemeFactory();

  public java.lang.String user_name; // required
  public java.lang.String password; // required
  public java.lang.String member_name; // required
  public int position_id; // required
  public int channel; // required
  public ThirdPartyPositionForSynchronization position_info; // required
  public java.lang.String company_name; // required
  public int account_id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_NAME((short)1, "user_name"),
    PASSWORD((short)2, "password"),
    MEMBER_NAME((short)3, "member_name"),
    POSITION_ID((short)4, "position_id"),
    CHANNEL((short)5, "channel"),
    POSITION_INFO((short)6, "position_info"),
    COMPANY_NAME((short)7, "company_name"),
    ACCOUNT_ID((short)8, "account_id");

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
        case 1: // USER_NAME
          return USER_NAME;
        case 2: // PASSWORD
          return PASSWORD;
        case 3: // MEMBER_NAME
          return MEMBER_NAME;
        case 4: // POSITION_ID
          return POSITION_ID;
        case 5: // CHANNEL
          return CHANNEL;
        case 6: // POSITION_INFO
          return POSITION_INFO;
        case 7: // COMPANY_NAME
          return COMPANY_NAME;
        case 8: // ACCOUNT_ID
          return ACCOUNT_ID;
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
  private static final int __POSITION_ID_ISSET_ID = 0;
  private static final int __CHANNEL_ISSET_ID = 1;
  private static final int __ACCOUNT_ID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("user_name", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PASSWORD, new org.apache.thrift.meta_data.FieldMetaData("password", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MEMBER_NAME, new org.apache.thrift.meta_data.FieldMetaData("member_name", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POSITION_ID, new org.apache.thrift.meta_data.FieldMetaData("position_id", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CHANNEL, new org.apache.thrift.meta_data.FieldMetaData("channel", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.POSITION_INFO, new org.apache.thrift.meta_data.FieldMetaData("position_info", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ThirdPartyPositionForSynchronization.class)));
    tmpMap.put(_Fields.COMPANY_NAME, new org.apache.thrift.meta_data.FieldMetaData("company_name", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("account_id", org.apache.thrift.TFieldRequirementType.DEFAULT,
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThirdPartyPositionForSynchronizationWithAccount.class, metaDataMap);
  }

  public ThirdPartyPositionForSynchronizationWithAccount() {
    this.user_name = "";

    this.password = "";

    this.member_name = "";

    this.company_name = "";

  }

  public ThirdPartyPositionForSynchronizationWithAccount(
          java.lang.String user_name,
          java.lang.String password,
          java.lang.String member_name,
          int position_id,
          int channel,
          ThirdPartyPositionForSynchronization position_info,
          java.lang.String company_name,
          int account_id)
  {
    this();
    this.user_name = user_name;
    this.password = password;
    this.member_name = member_name;
    this.position_id = position_id;
    setPosition_idIsSet(true);
    this.channel = channel;
    setChannelIsSet(true);
    this.position_info = position_info;
    this.company_name = company_name;
    this.account_id = account_id;
    setAccount_idIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdPartyPositionForSynchronizationWithAccount(ThirdPartyPositionForSynchronizationWithAccount other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUser_name()) {
      this.user_name = other.user_name;
    }
    if (other.isSetPassword()) {
      this.password = other.password;
    }
    if (other.isSetMember_name()) {
      this.member_name = other.member_name;
    }
    this.position_id = other.position_id;
    this.channel = other.channel;
    if (other.isSetPosition_info()) {
      this.position_info = new ThirdPartyPositionForSynchronization(other.position_info);
    }
    if (other.isSetCompany_name()) {
      this.company_name = other.company_name;
    }
    this.account_id = other.account_id;
  }

  public ThirdPartyPositionForSynchronizationWithAccount deepCopy() {
    return new ThirdPartyPositionForSynchronizationWithAccount(this);
  }

  @Override
  public void clear() {
    this.user_name = "";

    this.password = "";

    this.member_name = "";

    setPosition_idIsSet(false);
    this.position_id = 0;
    setChannelIsSet(false);
    this.channel = 0;
    this.position_info = null;
    this.company_name = "";

    setAccount_idIsSet(false);
    this.account_id = 0;
  }

  public java.lang.String getUser_name() {
    return this.user_name;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setUser_name(java.lang.String user_name) {
    this.user_name = user_name;
    return this;
  }

  public void unsetUser_name() {
    this.user_name = null;
  }

  /** Returns true if field user_name is set (has been assigned a value) and false otherwise */
  public boolean isSetUser_name() {
    return this.user_name != null;
  }

  public void setUser_nameIsSet(boolean value) {
    if (!value) {
      this.user_name = null;
    }
  }

  public java.lang.String getPassword() {
    return this.password;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  public void unsetPassword() {
    this.password = null;
  }

  /** Returns true if field password is set (has been assigned a value) and false otherwise */
  public boolean isSetPassword() {
    return this.password != null;
  }

  public void setPasswordIsSet(boolean value) {
    if (!value) {
      this.password = null;
    }
  }

  public java.lang.String getMember_name() {
    return this.member_name;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setMember_name(java.lang.String member_name) {
    this.member_name = member_name;
    return this;
  }

  public void unsetMember_name() {
    this.member_name = null;
  }

  /** Returns true if field member_name is set (has been assigned a value) and false otherwise */
  public boolean isSetMember_name() {
    return this.member_name != null;
  }

  public void setMember_nameIsSet(boolean value) {
    if (!value) {
      this.member_name = null;
    }
  }

  public int getPosition_id() {
    return this.position_id;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setPosition_id(int position_id) {
    this.position_id = position_id;
    setPosition_idIsSet(true);
    return this;
  }

  public void unsetPosition_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __POSITION_ID_ISSET_ID);
  }

  /** Returns true if field position_id is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __POSITION_ID_ISSET_ID);
  }

  public void setPosition_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __POSITION_ID_ISSET_ID, value);
  }

  public int getChannel() {
    return this.channel;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setChannel(int channel) {
    this.channel = channel;
    setChannelIsSet(true);
    return this;
  }

  public void unsetChannel() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CHANNEL_ISSET_ID);
  }

  /** Returns true if field channel is set (has been assigned a value) and false otherwise */
  public boolean isSetChannel() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CHANNEL_ISSET_ID);
  }

  public void setChannelIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CHANNEL_ISSET_ID, value);
  }

  public ThirdPartyPositionForSynchronization getPosition_info() {
    return this.position_info;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setPosition_info(ThirdPartyPositionForSynchronization position_info) {
    this.position_info = position_info;
    return this;
  }

  public void unsetPosition_info() {
    this.position_info = null;
  }

  /** Returns true if field position_info is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition_info() {
    return this.position_info != null;
  }

  public void setPosition_infoIsSet(boolean value) {
    if (!value) {
      this.position_info = null;
    }
  }

  public java.lang.String getCompany_name() {
    return this.company_name;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setCompany_name(java.lang.String company_name) {
    this.company_name = company_name;
    return this;
  }

  public void unsetCompany_name() {
    this.company_name = null;
  }

  /** Returns true if field company_name is set (has been assigned a value) and false otherwise */
  public boolean isSetCompany_name() {
    return this.company_name != null;
  }

  public void setCompany_nameIsSet(boolean value) {
    if (!value) {
      this.company_name = null;
    }
  }

  public int getAccount_id() {
    return this.account_id;
  }

  public ThirdPartyPositionForSynchronizationWithAccount setAccount_id(int account_id) {
    this.account_id = account_id;
    setAccount_idIsSet(true);
    return this;
  }

  public void unsetAccount_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ACCOUNT_ID_ISSET_ID);
  }

  /** Returns true if field account_id is set (has been assigned a value) and false otherwise */
  public boolean isSetAccount_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ACCOUNT_ID_ISSET_ID);
  }

  public void setAccount_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ACCOUNT_ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
      case USER_NAME:
        if (value == null) {
          unsetUser_name();
        } else {
          setUser_name((java.lang.String)value);
        }
        break;

      case PASSWORD:
        if (value == null) {
          unsetPassword();
        } else {
          setPassword((java.lang.String)value);
        }
        break;

      case MEMBER_NAME:
        if (value == null) {
          unsetMember_name();
        } else {
          setMember_name((java.lang.String)value);
        }
        break;

      case POSITION_ID:
        if (value == null) {
          unsetPosition_id();
        } else {
          setPosition_id((java.lang.Integer)value);
        }
        break;

      case CHANNEL:
        if (value == null) {
          unsetChannel();
        } else {
          setChannel((java.lang.Integer)value);
        }
        break;

      case POSITION_INFO:
        if (value == null) {
          unsetPosition_info();
        } else {
          setPosition_info((ThirdPartyPositionForSynchronization)value);
        }
        break;

      case COMPANY_NAME:
        if (value == null) {
          unsetCompany_name();
        } else {
          setCompany_name((java.lang.String)value);
        }
        break;

      case ACCOUNT_ID:
        if (value == null) {
          unsetAccount_id();
        } else {
          setAccount_id((java.lang.Integer)value);
        }
        break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
      case USER_NAME:
        return getUser_name();

      case PASSWORD:
        return getPassword();

      case MEMBER_NAME:
        return getMember_name();

      case POSITION_ID:
        return getPosition_id();

      case CHANNEL:
        return getChannel();

      case POSITION_INFO:
        return getPosition_info();

      case COMPANY_NAME:
        return getCompany_name();

      case ACCOUNT_ID:
        return getAccount_id();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
      case USER_NAME:
        return isSetUser_name();
      case PASSWORD:
        return isSetPassword();
      case MEMBER_NAME:
        return isSetMember_name();
      case POSITION_ID:
        return isSetPosition_id();
      case CHANNEL:
        return isSetChannel();
      case POSITION_INFO:
        return isSetPosition_info();
      case COMPANY_NAME:
        return isSetCompany_name();
      case ACCOUNT_ID:
        return isSetAccount_id();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdPartyPositionForSynchronizationWithAccount)
      return this.equals((ThirdPartyPositionForSynchronizationWithAccount)that);
    return false;
  }

  public boolean equals(ThirdPartyPositionForSynchronizationWithAccount that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_user_name = true && this.isSetUser_name();
    boolean that_present_user_name = true && that.isSetUser_name();
    if (this_present_user_name || that_present_user_name) {
      if (!(this_present_user_name && that_present_user_name))
        return false;
      if (!this.user_name.equals(that.user_name))
        return false;
    }

    boolean this_present_password = true && this.isSetPassword();
    boolean that_present_password = true && that.isSetPassword();
    if (this_present_password || that_present_password) {
      if (!(this_present_password && that_present_password))
        return false;
      if (!this.password.equals(that.password))
        return false;
    }

    boolean this_present_member_name = true && this.isSetMember_name();
    boolean that_present_member_name = true && that.isSetMember_name();
    if (this_present_member_name || that_present_member_name) {
      if (!(this_present_member_name && that_present_member_name))
        return false;
      if (!this.member_name.equals(that.member_name))
        return false;
    }

    boolean this_present_position_id = true;
    boolean that_present_position_id = true;
    if (this_present_position_id || that_present_position_id) {
      if (!(this_present_position_id && that_present_position_id))
        return false;
      if (this.position_id != that.position_id)
        return false;
    }

    boolean this_present_channel = true;
    boolean that_present_channel = true;
    if (this_present_channel || that_present_channel) {
      if (!(this_present_channel && that_present_channel))
        return false;
      if (this.channel != that.channel)
        return false;
    }

    boolean this_present_position_info = true && this.isSetPosition_info();
    boolean that_present_position_info = true && that.isSetPosition_info();
    if (this_present_position_info || that_present_position_info) {
      if (!(this_present_position_info && that_present_position_info))
        return false;
      if (!this.position_info.equals(that.position_info))
        return false;
    }

    boolean this_present_company_name = true && this.isSetCompany_name();
    boolean that_present_company_name = true && that.isSetCompany_name();
    if (this_present_company_name || that_present_company_name) {
      if (!(this_present_company_name && that_present_company_name))
        return false;
      if (!this.company_name.equals(that.company_name))
        return false;
    }

    boolean this_present_account_id = true;
    boolean that_present_account_id = true;
    if (this_present_account_id || that_present_account_id) {
      if (!(this_present_account_id && that_present_account_id))
        return false;
      if (this.account_id != that.account_id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetUser_name()) ? 131071 : 524287);
    if (isSetUser_name())
      hashCode = hashCode * 8191 + user_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetPassword()) ? 131071 : 524287);
    if (isSetPassword())
      hashCode = hashCode * 8191 + password.hashCode();

    hashCode = hashCode * 8191 + ((isSetMember_name()) ? 131071 : 524287);
    if (isSetMember_name())
      hashCode = hashCode * 8191 + member_name.hashCode();

    hashCode = hashCode * 8191 + position_id;

    hashCode = hashCode * 8191 + channel;

    hashCode = hashCode * 8191 + ((isSetPosition_info()) ? 131071 : 524287);
    if (isSetPosition_info())
      hashCode = hashCode * 8191 + position_info.hashCode();

    hashCode = hashCode * 8191 + ((isSetCompany_name()) ? 131071 : 524287);
    if (isSetCompany_name())
      hashCode = hashCode * 8191 + company_name.hashCode();

    hashCode = hashCode * 8191 + account_id;

    return hashCode;
  }

  @Override
  public int compareTo(ThirdPartyPositionForSynchronizationWithAccount other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUser_name()).compareTo(other.isSetUser_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user_name, other.user_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPassword()).compareTo(other.isSetPassword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPassword()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.password, other.password);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMember_name()).compareTo(other.isSetMember_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMember_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.member_name, other.member_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPosition_id()).compareTo(other.isSetPosition_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position_id, other.position_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetChannel()).compareTo(other.isSetChannel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChannel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.channel, other.channel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPosition_info()).compareTo(other.isSetPosition_info());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition_info()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position_info, other.position_info);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCompany_name()).compareTo(other.isSetCompany_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompany_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.company_name, other.company_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAccount_id()).compareTo(other.isSetAccount_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccount_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.account_id, other.account_id);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ThirdPartyPositionForSynchronizationWithAccount(");
    boolean first = true;

    sb.append("user_name:");
    if (this.user_name == null) {
      sb.append("null");
    } else {
      sb.append(this.user_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("password:");
    if (this.password == null) {
      sb.append("null");
    } else {
      sb.append(this.password);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("member_name:");
    if (this.member_name == null) {
      sb.append("null");
    } else {
      sb.append(this.member_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("position_id:");
    sb.append(this.position_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("channel:");
    sb.append(this.channel);
    first = false;
    if (!first) sb.append(", ");
    sb.append("position_info:");
    if (this.position_info == null) {
      sb.append("null");
    } else {
      sb.append(this.position_info);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("company_name:");
    if (this.company_name == null) {
      sb.append("null");
    } else {
      sb.append(this.company_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("account_id:");
    sb.append(this.account_id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (position_info != null) {
      position_info.validate();
    }
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

  private static class ThirdPartyPositionForSynchronizationWithAccountStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThirdPartyPositionForSynchronizationWithAccountStandardScheme getScheme() {
      return new ThirdPartyPositionForSynchronizationWithAccountStandardScheme();
    }
  }

  private static class ThirdPartyPositionForSynchronizationWithAccountStandardScheme extends org.apache.thrift.scheme.StandardScheme<ThirdPartyPositionForSynchronizationWithAccount> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThirdPartyPositionForSynchronizationWithAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.user_name = iprot.readString();
              struct.setUser_nameIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PASSWORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.password = iprot.readString();
              struct.setPasswordIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MEMBER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.member_name = iprot.readString();
              struct.setMember_nameIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // POSITION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.position_id = iprot.readI32();
              struct.setPosition_idIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CHANNEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.channel = iprot.readI32();
              struct.setChannelIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // POSITION_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.position_info = new ThirdPartyPositionForSynchronization();
              struct.position_info.read(iprot);
              struct.setPosition_infoIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // COMPANY_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.company_name = iprot.readString();
              struct.setCompany_nameIsSet(true);
            } else {
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.account_id = iprot.readI32();
              struct.setAccount_idIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThirdPartyPositionForSynchronizationWithAccount struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.user_name != null) {
        oprot.writeFieldBegin(USER_NAME_FIELD_DESC);
        oprot.writeString(struct.user_name);
        oprot.writeFieldEnd();
      }
      if (struct.password != null) {
        oprot.writeFieldBegin(PASSWORD_FIELD_DESC);
        oprot.writeString(struct.password);
        oprot.writeFieldEnd();
      }
      if (struct.member_name != null) {
        oprot.writeFieldBegin(MEMBER_NAME_FIELD_DESC);
        oprot.writeString(struct.member_name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(POSITION_ID_FIELD_DESC);
      oprot.writeI32(struct.position_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CHANNEL_FIELD_DESC);
      oprot.writeI32(struct.channel);
      oprot.writeFieldEnd();
      if (struct.position_info != null) {
        oprot.writeFieldBegin(POSITION_INFO_FIELD_DESC);
        struct.position_info.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.company_name != null) {
        oprot.writeFieldBegin(COMPANY_NAME_FIELD_DESC);
        oprot.writeString(struct.company_name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ACCOUNT_ID_FIELD_DESC);
      oprot.writeI32(struct.account_id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThirdPartyPositionForSynchronizationWithAccountTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThirdPartyPositionForSynchronizationWithAccountTupleScheme getScheme() {
      return new ThirdPartyPositionForSynchronizationWithAccountTupleScheme();
    }
  }

  private static class ThirdPartyPositionForSynchronizationWithAccountTupleScheme extends org.apache.thrift.scheme.TupleScheme<ThirdPartyPositionForSynchronizationWithAccount> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThirdPartyPositionForSynchronizationWithAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUser_name()) {
        optionals.set(0);
      }
      if (struct.isSetPassword()) {
        optionals.set(1);
      }
      if (struct.isSetMember_name()) {
        optionals.set(2);
      }
      if (struct.isSetPosition_id()) {
        optionals.set(3);
      }
      if (struct.isSetChannel()) {
        optionals.set(4);
      }
      if (struct.isSetPosition_info()) {
        optionals.set(5);
      }
      if (struct.isSetCompany_name()) {
        optionals.set(6);
      }
      if (struct.isSetAccount_id()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetUser_name()) {
        oprot.writeString(struct.user_name);
      }
      if (struct.isSetPassword()) {
        oprot.writeString(struct.password);
      }
      if (struct.isSetMember_name()) {
        oprot.writeString(struct.member_name);
      }
      if (struct.isSetPosition_id()) {
        oprot.writeI32(struct.position_id);
      }
      if (struct.isSetChannel()) {
        oprot.writeI32(struct.channel);
      }
      if (struct.isSetPosition_info()) {
        struct.position_info.write(oprot);
      }
      if (struct.isSetCompany_name()) {
        oprot.writeString(struct.company_name);
      }
      if (struct.isSetAccount_id()) {
        oprot.writeI32(struct.account_id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThirdPartyPositionForSynchronizationWithAccount struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.user_name = iprot.readString();
        struct.setUser_nameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.password = iprot.readString();
        struct.setPasswordIsSet(true);
      }
      if (incoming.get(2)) {
        struct.member_name = iprot.readString();
        struct.setMember_nameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.position_id = iprot.readI32();
        struct.setPosition_idIsSet(true);
      }
      if (incoming.get(4)) {
        struct.channel = iprot.readI32();
        struct.setChannelIsSet(true);
      }
      if (incoming.get(5)) {
        struct.position_info = new ThirdPartyPositionForSynchronization();
        struct.position_info.read(iprot);
        struct.setPosition_infoIsSet(true);
      }
      if (incoming.get(6)) {
        struct.company_name = iprot.readString();
        struct.setCompany_nameIsSet(true);
      }
      if (incoming.get(7)) {
        struct.account_id = iprot.readI32();
        struct.setAccount_idIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

