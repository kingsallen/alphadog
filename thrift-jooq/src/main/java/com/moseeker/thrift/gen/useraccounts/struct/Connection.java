/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.useraccounts.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-01-02")
public class Connection implements org.apache.thrift.TBase<Connection, Connection._Fields>, java.io.Serializable, Cloneable, Comparable<Connection> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Connection");

  private static final org.apache.thrift.protocol.TField PNODES_FIELD_DESC = new org.apache.thrift.protocol.TField("pnodes", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField UID_FIELD_DESC = new org.apache.thrift.protocol.TField("uid", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField DEGREE_FIELD_DESC = new org.apache.thrift.protocol.TField("degree", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField AVATAR_FIELD_DESC = new org.apache.thrift.protocol.TField("avatar", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ConnectionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ConnectionTupleSchemeFactory();

  public java.util.List<Integer> pnodes; // optional
  public int uid; // optional
  public int degree; // optional
  public String avatar; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PNODES((short)1, "pnodes"),
    UID((short)2, "uid"),
    DEGREE((short)3, "degree"),
    AVATAR((short)4, "avatar");

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
        case 1: // PNODES
          return PNODES;
        case 2: // UID
          return UID;
        case 3: // DEGREE
          return DEGREE;
        case 4: // AVATAR
          return AVATAR;
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
  private static final int __UID_ISSET_ID = 0;
  private static final int __DEGREE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PNODES,_Fields.UID,_Fields.DEGREE,_Fields.AVATAR};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PNODES, new org.apache.thrift.meta_data.FieldMetaData("pnodes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.UID, new org.apache.thrift.meta_data.FieldMetaData("uid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DEGREE, new org.apache.thrift.meta_data.FieldMetaData("degree", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.AVATAR, new org.apache.thrift.meta_data.FieldMetaData("avatar", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Connection.class, metaDataMap);
  }

  public Connection() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Connection(Connection other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPnodes()) {
      java.util.List<Integer> __this__pnodes = new java.util.ArrayList<Integer>(other.pnodes);
      this.pnodes = __this__pnodes;
    }
    this.uid = other.uid;
    this.degree = other.degree;
    if (other.isSetAvatar()) {
      this.avatar = other.avatar;
    }
  }

  public Connection deepCopy() {
    return new Connection(this);
  }

  @Override
  public void clear() {
    this.pnodes = null;
    setUidIsSet(false);
    this.uid = 0;
    setDegreeIsSet(false);
    this.degree = 0;
    this.avatar = null;
  }

  public int getPnodesSize() {
    return (this.pnodes == null) ? 0 : this.pnodes.size();
  }

  public java.util.Iterator<Integer> getPnodesIterator() {
    return (this.pnodes == null) ? null : this.pnodes.iterator();
  }

  public void addToPnodes(int elem) {
    if (this.pnodes == null) {
      this.pnodes = new java.util.ArrayList<Integer>();
    }
    this.pnodes.add(elem);
  }

  public java.util.List<Integer> getPnodes() {
    return this.pnodes;
  }

  public Connection setPnodes(java.util.List<Integer> pnodes) {
    this.pnodes = pnodes;
    return this;
  }

  public void unsetPnodes() {
    this.pnodes = null;
  }

  /** Returns true if field pnodes is set (has been assigned a value) and false otherwise */
  public boolean isSetPnodes() {
    return this.pnodes != null;
  }

  public void setPnodesIsSet(boolean value) {
    if (!value) {
      this.pnodes = null;
    }
  }

  public int getUid() {
    return this.uid;
  }

  public Connection setUid(int uid) {
    this.uid = uid;
    setUidIsSet(true);
    return this;
  }

  public void unsetUid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __UID_ISSET_ID);
  }

  /** Returns true if field uid is set (has been assigned a value) and false otherwise */
  public boolean isSetUid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __UID_ISSET_ID);
  }

  public void setUidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __UID_ISSET_ID, value);
  }

  public int getDegree() {
    return this.degree;
  }

  public Connection setDegree(int degree) {
    this.degree = degree;
    setDegreeIsSet(true);
    return this;
  }

  public void unsetDegree() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DEGREE_ISSET_ID);
  }

  /** Returns true if field degree is set (has been assigned a value) and false otherwise */
  public boolean isSetDegree() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DEGREE_ISSET_ID);
  }

  public void setDegreeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DEGREE_ISSET_ID, value);
  }

  public String getAvatar() {
    return this.avatar;
  }

  public Connection setAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public void unsetAvatar() {
    this.avatar = null;
  }

  /** Returns true if field avatar is set (has been assigned a value) and false otherwise */
  public boolean isSetAvatar() {
    return this.avatar != null;
  }

  public void setAvatarIsSet(boolean value) {
    if (!value) {
      this.avatar = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PNODES:
      if (value == null) {
        unsetPnodes();
      } else {
        setPnodes((java.util.List<Integer>)value);
      }
      break;

    case UID:
      if (value == null) {
        unsetUid();
      } else {
        setUid((Integer)value);
      }
      break;

    case DEGREE:
      if (value == null) {
        unsetDegree();
      } else {
        setDegree((Integer)value);
      }
      break;

    case AVATAR:
      if (value == null) {
        unsetAvatar();
      } else {
        setAvatar((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PNODES:
      return getPnodes();

    case UID:
      return getUid();

    case DEGREE:
      return getDegree();

    case AVATAR:
      return getAvatar();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PNODES:
      return isSetPnodes();
    case UID:
      return isSetUid();
    case DEGREE:
      return isSetDegree();
    case AVATAR:
      return isSetAvatar();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Connection)
      return this.equals((Connection)that);
    return false;
  }

  public boolean equals(Connection that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pnodes = true && this.isSetPnodes();
    boolean that_present_pnodes = true && that.isSetPnodes();
    if (this_present_pnodes || that_present_pnodes) {
      if (!(this_present_pnodes && that_present_pnodes))
        return false;
      if (!this.pnodes.equals(that.pnodes))
        return false;
    }

    boolean this_present_uid = true && this.isSetUid();
    boolean that_present_uid = true && that.isSetUid();
    if (this_present_uid || that_present_uid) {
      if (!(this_present_uid && that_present_uid))
        return false;
      if (this.uid != that.uid)
        return false;
    }

    boolean this_present_degree = true && this.isSetDegree();
    boolean that_present_degree = true && that.isSetDegree();
    if (this_present_degree || that_present_degree) {
      if (!(this_present_degree && that_present_degree))
        return false;
      if (this.degree != that.degree)
        return false;
    }

    boolean this_present_avatar = true && this.isSetAvatar();
    boolean that_present_avatar = true && that.isSetAvatar();
    if (this_present_avatar || that_present_avatar) {
      if (!(this_present_avatar && that_present_avatar))
        return false;
      if (!this.avatar.equals(that.avatar))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPnodes()) ? 131071 : 524287);
    if (isSetPnodes())
      hashCode = hashCode * 8191 + pnodes.hashCode();

    hashCode = hashCode * 8191 + ((isSetUid()) ? 131071 : 524287);
    if (isSetUid())
      hashCode = hashCode * 8191 + uid;

    hashCode = hashCode * 8191 + ((isSetDegree()) ? 131071 : 524287);
    if (isSetDegree())
      hashCode = hashCode * 8191 + degree;

    hashCode = hashCode * 8191 + ((isSetAvatar()) ? 131071 : 524287);
    if (isSetAvatar())
      hashCode = hashCode * 8191 + avatar.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Connection other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPnodes()).compareTo(other.isSetPnodes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPnodes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pnodes, other.pnodes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUid()).compareTo(other.isSetUid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uid, other.uid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDegree()).compareTo(other.isSetDegree());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDegree()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.degree, other.degree);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAvatar()).compareTo(other.isSetAvatar());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAvatar()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.avatar, other.avatar);
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
    StringBuilder sb = new StringBuilder("Connection(");
    boolean first = true;

    if (isSetPnodes()) {
      sb.append("pnodes:");
      if (this.pnodes == null) {
        sb.append("null");
      } else {
        sb.append(this.pnodes);
      }
      first = false;
    }
    if (isSetUid()) {
      if (!first) sb.append(", ");
      sb.append("uid:");
      sb.append(this.uid);
      first = false;
    }
    if (isSetDegree()) {
      if (!first) sb.append(", ");
      sb.append("degree:");
      sb.append(this.degree);
      first = false;
    }
    if (isSetAvatar()) {
      if (!first) sb.append(", ");
      sb.append("avatar:");
      if (this.avatar == null) {
        sb.append("null");
      } else {
        sb.append(this.avatar);
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

  private static class ConnectionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectionStandardScheme getScheme() {
      return new ConnectionStandardScheme();
    }
  }

  private static class ConnectionStandardScheme extends org.apache.thrift.scheme.StandardScheme<Connection> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Connection struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PNODES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list140 = iprot.readListBegin();
                struct.pnodes = new java.util.ArrayList<Integer>(_list140.size);
                int _elem141;
                for (int _i142 = 0; _i142 < _list140.size; ++_i142)
                {
                  _elem141 = iprot.readI32();
                  struct.pnodes.add(_elem141);
                }
                iprot.readListEnd();
              }
              struct.setPnodesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.uid = iprot.readI32();
              struct.setUidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DEGREE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.degree = iprot.readI32();
              struct.setDegreeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // AVATAR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.avatar = iprot.readString();
              struct.setAvatarIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Connection struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pnodes != null) {
        if (struct.isSetPnodes()) {
          oprot.writeFieldBegin(PNODES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.pnodes.size()));
            for (int _iter143 : struct.pnodes)
            {
              oprot.writeI32(_iter143);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetUid()) {
        oprot.writeFieldBegin(UID_FIELD_DESC);
        oprot.writeI32(struct.uid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDegree()) {
        oprot.writeFieldBegin(DEGREE_FIELD_DESC);
        oprot.writeI32(struct.degree);
        oprot.writeFieldEnd();
      }
      if (struct.avatar != null) {
        if (struct.isSetAvatar()) {
          oprot.writeFieldBegin(AVATAR_FIELD_DESC);
          oprot.writeString(struct.avatar);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ConnectionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ConnectionTupleScheme getScheme() {
      return new ConnectionTupleScheme();
    }
  }

  private static class ConnectionTupleScheme extends org.apache.thrift.scheme.TupleScheme<Connection> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Connection struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPnodes()) {
        optionals.set(0);
      }
      if (struct.isSetUid()) {
        optionals.set(1);
      }
      if (struct.isSetDegree()) {
        optionals.set(2);
      }
      if (struct.isSetAvatar()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetPnodes()) {
        {
          oprot.writeI32(struct.pnodes.size());
          for (int _iter144 : struct.pnodes)
          {
            oprot.writeI32(_iter144);
          }
        }
      }
      if (struct.isSetUid()) {
        oprot.writeI32(struct.uid);
      }
      if (struct.isSetDegree()) {
        oprot.writeI32(struct.degree);
      }
      if (struct.isSetAvatar()) {
        oprot.writeString(struct.avatar);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Connection struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list145 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.pnodes = new java.util.ArrayList<Integer>(_list145.size);
          int _elem146;
          for (int _i147 = 0; _i147 < _list145.size; ++_i147)
          {
            _elem146 = iprot.readI32();
            struct.pnodes.add(_elem146);
          }
        }
        struct.setPnodesIsSet(true);
      }
      if (incoming.get(1)) {
        struct.uid = iprot.readI32();
        struct.setUidIsSet(true);
      }
      if (incoming.get(2)) {
        struct.degree = iprot.readI32();
        struct.setDegreeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.avatar = iprot.readString();
        struct.setAvatarIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

