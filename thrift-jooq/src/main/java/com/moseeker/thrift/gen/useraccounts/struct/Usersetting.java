/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.useraccounts.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-02-23")
public class Usersetting implements org.apache.thrift.TBase<Usersetting, Usersetting._Fields>, java.io.Serializable, Cloneable, Comparable<Usersetting> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Usersetting");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("user_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField BANNER_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("banner_url", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PRIVACY_POLICY_FIELD_DESC = new org.apache.thrift.protocol.TField("privacy_policy", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UsersettingStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UsersettingTupleSchemeFactory();

  public int id; // optional
  public int user_id; // optional
  public java.lang.String banner_url; // optional
  public int privacy_policy; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    USER_ID((short)2, "user_id"),
    BANNER_URL((short)3, "banner_url"),
    PRIVACY_POLICY((short)4, "privacy_policy");

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
        case 2: // USER_ID
          return USER_ID;
        case 3: // BANNER_URL
          return BANNER_URL;
        case 4: // PRIVACY_POLICY
          return PRIVACY_POLICY;
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
  private static final int __USER_ID_ISSET_ID = 1;
  private static final int __PRIVACY_POLICY_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.USER_ID,_Fields.BANNER_URL,_Fields.PRIVACY_POLICY};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("user_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BANNER_URL, new org.apache.thrift.meta_data.FieldMetaData("banner_url", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRIVACY_POLICY, new org.apache.thrift.meta_data.FieldMetaData("privacy_policy", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Usersetting.class, metaDataMap);
  }

  public Usersetting() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Usersetting(Usersetting other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.user_id = other.user_id;
    if (other.isSetBanner_url()) {
      this.banner_url = other.banner_url;
    }
    this.privacy_policy = other.privacy_policy;
  }

  public Usersetting deepCopy() {
    return new Usersetting(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setUser_idIsSet(false);
    this.user_id = 0;
    this.banner_url = null;
    setPrivacy_policyIsSet(false);
    this.privacy_policy = 0;
  }

  public int getId() {
    return this.id;
  }

  public Usersetting setId(int id) {
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

  public int getUser_id() {
    return this.user_id;
  }

  public Usersetting setUser_id(int user_id) {
    this.user_id = user_id;
    setUser_idIsSet(true);
    return this;
  }

  public void unsetUser_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  /** Returns true if field user_id is set (has been assigned a value) and false otherwise */
  public boolean isSetUser_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  public void setUser_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USER_ID_ISSET_ID, value);
  }

  public java.lang.String getBanner_url() {
    return this.banner_url;
  }

  public Usersetting setBanner_url(java.lang.String banner_url) {
    this.banner_url = banner_url;
    return this;
  }

  public void unsetBanner_url() {
    this.banner_url = null;
  }

  /** Returns true if field banner_url is set (has been assigned a value) and false otherwise */
  public boolean isSetBanner_url() {
    return this.banner_url != null;
  }

  public void setBanner_urlIsSet(boolean value) {
    if (!value) {
      this.banner_url = null;
    }
  }

  public int getPrivacy_policy() {
    return this.privacy_policy;
  }

  public Usersetting setPrivacy_policy(int privacy_policy) {
    this.privacy_policy = privacy_policy;
    setPrivacy_policyIsSet(true);
    return this;
  }

  public void unsetPrivacy_policy() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PRIVACY_POLICY_ISSET_ID);
  }

  /** Returns true if field privacy_policy is set (has been assigned a value) and false otherwise */
  public boolean isSetPrivacy_policy() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PRIVACY_POLICY_ISSET_ID);
  }

  public void setPrivacy_policyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PRIVACY_POLICY_ISSET_ID, value);
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

    case USER_ID:
      if (value == null) {
        unsetUser_id();
      } else {
        setUser_id((java.lang.Integer)value);
      }
      break;

    case BANNER_URL:
      if (value == null) {
        unsetBanner_url();
      } else {
        setBanner_url((java.lang.String)value);
      }
      break;

    case PRIVACY_POLICY:
      if (value == null) {
        unsetPrivacy_policy();
      } else {
        setPrivacy_policy((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case USER_ID:
      return getUser_id();

    case BANNER_URL:
      return getBanner_url();

    case PRIVACY_POLICY:
      return getPrivacy_policy();

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
    case USER_ID:
      return isSetUser_id();
    case BANNER_URL:
      return isSetBanner_url();
    case PRIVACY_POLICY:
      return isSetPrivacy_policy();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Usersetting)
      return this.equals((Usersetting)that);
    return false;
  }

  public boolean equals(Usersetting that) {
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

    boolean this_present_user_id = true && this.isSetUser_id();
    boolean that_present_user_id = true && that.isSetUser_id();
    if (this_present_user_id || that_present_user_id) {
      if (!(this_present_user_id && that_present_user_id))
        return false;
      if (this.user_id != that.user_id)
        return false;
    }

    boolean this_present_banner_url = true && this.isSetBanner_url();
    boolean that_present_banner_url = true && that.isSetBanner_url();
    if (this_present_banner_url || that_present_banner_url) {
      if (!(this_present_banner_url && that_present_banner_url))
        return false;
      if (!this.banner_url.equals(that.banner_url))
        return false;
    }

    boolean this_present_privacy_policy = true && this.isSetPrivacy_policy();
    boolean that_present_privacy_policy = true && that.isSetPrivacy_policy();
    if (this_present_privacy_policy || that_present_privacy_policy) {
      if (!(this_present_privacy_policy && that_present_privacy_policy))
        return false;
      if (this.privacy_policy != that.privacy_policy)
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

    hashCode = hashCode * 8191 + ((isSetUser_id()) ? 131071 : 524287);
    if (isSetUser_id())
      hashCode = hashCode * 8191 + user_id;

    hashCode = hashCode * 8191 + ((isSetBanner_url()) ? 131071 : 524287);
    if (isSetBanner_url())
      hashCode = hashCode * 8191 + banner_url.hashCode();

    hashCode = hashCode * 8191 + ((isSetPrivacy_policy()) ? 131071 : 524287);
    if (isSetPrivacy_policy())
      hashCode = hashCode * 8191 + privacy_policy;

    return hashCode;
  }

  @Override
  public int compareTo(Usersetting other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetUser_id()).compareTo(other.isSetUser_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user_id, other.user_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBanner_url()).compareTo(other.isSetBanner_url());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBanner_url()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.banner_url, other.banner_url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPrivacy_policy()).compareTo(other.isSetPrivacy_policy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrivacy_policy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.privacy_policy, other.privacy_policy);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Usersetting(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetUser_id()) {
      if (!first) sb.append(", ");
      sb.append("user_id:");
      sb.append(this.user_id);
      first = false;
    }
    if (isSetBanner_url()) {
      if (!first) sb.append(", ");
      sb.append("banner_url:");
      if (this.banner_url == null) {
        sb.append("null");
      } else {
        sb.append(this.banner_url);
      }
      first = false;
    }
    if (isSetPrivacy_policy()) {
      if (!first) sb.append(", ");
      sb.append("privacy_policy:");
      sb.append(this.privacy_policy);
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

  private static class UsersettingStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UsersettingStandardScheme getScheme() {
      return new UsersettingStandardScheme();
    }
  }

  private static class UsersettingStandardScheme extends org.apache.thrift.scheme.StandardScheme<Usersetting> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Usersetting struct) throws org.apache.thrift.TException {
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
          case 2: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.user_id = iprot.readI32();
              struct.setUser_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BANNER_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.banner_url = iprot.readString();
              struct.setBanner_urlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PRIVACY_POLICY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.privacy_policy = iprot.readI32();
              struct.setPrivacy_policyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Usersetting struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUser_id()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.user_id);
        oprot.writeFieldEnd();
      }
      if (struct.banner_url != null) {
        if (struct.isSetBanner_url()) {
          oprot.writeFieldBegin(BANNER_URL_FIELD_DESC);
          oprot.writeString(struct.banner_url);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetPrivacy_policy()) {
        oprot.writeFieldBegin(PRIVACY_POLICY_FIELD_DESC);
        oprot.writeI32(struct.privacy_policy);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UsersettingTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UsersettingTupleScheme getScheme() {
      return new UsersettingTupleScheme();
    }
  }

  private static class UsersettingTupleScheme extends org.apache.thrift.scheme.TupleScheme<Usersetting> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Usersetting struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetUser_id()) {
        optionals.set(1);
      }
      if (struct.isSetBanner_url()) {
        optionals.set(2);
      }
      if (struct.isSetPrivacy_policy()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetUser_id()) {
        oprot.writeI32(struct.user_id);
      }
      if (struct.isSetBanner_url()) {
        oprot.writeString(struct.banner_url);
      }
      if (struct.isSetPrivacy_policy()) {
        oprot.writeI32(struct.privacy_policy);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Usersetting struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.user_id = iprot.readI32();
        struct.setUser_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.banner_url = iprot.readString();
        struct.setBanner_urlIsSet(true);
      }
      if (incoming.get(3)) {
        struct.privacy_policy = iprot.readI32();
        struct.setPrivacy_policyIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

