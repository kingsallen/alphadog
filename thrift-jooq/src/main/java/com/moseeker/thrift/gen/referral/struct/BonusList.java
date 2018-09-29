/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.referral.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-09-27")
public class BonusList implements org.apache.thrift.TBase<BonusList, BonusList._Fields>, java.io.Serializable, Cloneable, Comparable<BonusList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BonusList");

  private static final org.apache.thrift.protocol.TField TOTAL_REDPACKETS_FIELD_DESC = new org.apache.thrift.protocol.TField("totalRedpackets", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField TOTAL_BONUS_FIELD_DESC = new org.apache.thrift.protocol.TField("totalBonus", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField BONUS_FIELD_DESC = new org.apache.thrift.protocol.TField("bonus", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new BonusListStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new BonusListTupleSchemeFactory();

  public double totalRedpackets; // optional
  public double totalBonus; // optional
  public java.util.List<Bonus> bonus; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_REDPACKETS((short)1, "totalRedpackets"),
    TOTAL_BONUS((short)2, "totalBonus"),
    BONUS((short)3, "bonus");

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
        case 1: // TOTAL_REDPACKETS
          return TOTAL_REDPACKETS;
        case 2: // TOTAL_BONUS
          return TOTAL_BONUS;
        case 3: // BONUS
          return BONUS;
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
  private static final int __TOTALREDPACKETS_ISSET_ID = 0;
  private static final int __TOTALBONUS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TOTAL_REDPACKETS,_Fields.TOTAL_BONUS,_Fields.BONUS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_REDPACKETS, new org.apache.thrift.meta_data.FieldMetaData("totalRedpackets", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.TOTAL_BONUS, new org.apache.thrift.meta_data.FieldMetaData("totalBonus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.BONUS, new org.apache.thrift.meta_data.FieldMetaData("bonus", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Bonus.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BonusList.class, metaDataMap);
  }

  public BonusList() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BonusList(BonusList other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalRedpackets = other.totalRedpackets;
    this.totalBonus = other.totalBonus;
    if (other.isSetBonus()) {
      java.util.List<Bonus> __this__bonus = new java.util.ArrayList<Bonus>(other.bonus.size());
      for (Bonus other_element : other.bonus) {
        __this__bonus.add(new Bonus(other_element));
      }
      this.bonus = __this__bonus;
    }
  }

  public BonusList deepCopy() {
    return new BonusList(this);
  }

  @Override
  public void clear() {
    setTotalRedpacketsIsSet(false);
    this.totalRedpackets = 0.0;
    setTotalBonusIsSet(false);
    this.totalBonus = 0.0;
    this.bonus = null;
  }

  public double getTotalRedpackets() {
    return this.totalRedpackets;
  }

  public BonusList setTotalRedpackets(double totalRedpackets) {
    this.totalRedpackets = totalRedpackets;
    setTotalRedpacketsIsSet(true);
    return this;
  }

  public void unsetTotalRedpackets() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTALREDPACKETS_ISSET_ID);
  }

  /** Returns true if field totalRedpackets is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalRedpackets() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTALREDPACKETS_ISSET_ID);
  }

  public void setTotalRedpacketsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTALREDPACKETS_ISSET_ID, value);
  }

  public double getTotalBonus() {
    return this.totalBonus;
  }

  public BonusList setTotalBonus(double totalBonus) {
    this.totalBonus = totalBonus;
    setTotalBonusIsSet(true);
    return this;
  }

  public void unsetTotalBonus() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTALBONUS_ISSET_ID);
  }

  /** Returns true if field totalBonus is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalBonus() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTALBONUS_ISSET_ID);
  }

  public void setTotalBonusIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTALBONUS_ISSET_ID, value);
  }

  public int getBonusSize() {
    return (this.bonus == null) ? 0 : this.bonus.size();
  }

  public java.util.Iterator<Bonus> getBonusIterator() {
    return (this.bonus == null) ? null : this.bonus.iterator();
  }

  public void addToBonus(Bonus elem) {
    if (this.bonus == null) {
      this.bonus = new java.util.ArrayList<Bonus>();
    }
    this.bonus.add(elem);
  }

  public java.util.List<Bonus> getBonus() {
    return this.bonus;
  }

  public BonusList setBonus(java.util.List<Bonus> bonus) {
    this.bonus = bonus;
    return this;
  }

  public void unsetBonus() {
    this.bonus = null;
  }

  /** Returns true if field bonus is set (has been assigned a value) and false otherwise */
  public boolean isSetBonus() {
    return this.bonus != null;
  }

  public void setBonusIsSet(boolean value) {
    if (!value) {
      this.bonus = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TOTAL_REDPACKETS:
      if (value == null) {
        unsetTotalRedpackets();
      } else {
        setTotalRedpackets((java.lang.Double)value);
      }
      break;

    case TOTAL_BONUS:
      if (value == null) {
        unsetTotalBonus();
      } else {
        setTotalBonus((java.lang.Double)value);
      }
      break;

    case BONUS:
      if (value == null) {
        unsetBonus();
      } else {
        setBonus((java.util.List<Bonus>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_REDPACKETS:
      return getTotalRedpackets();

    case TOTAL_BONUS:
      return getTotalBonus();

    case BONUS:
      return getBonus();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_REDPACKETS:
      return isSetTotalRedpackets();
    case TOTAL_BONUS:
      return isSetTotalBonus();
    case BONUS:
      return isSetBonus();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof BonusList)
      return this.equals((BonusList)that);
    return false;
  }

  public boolean equals(BonusList that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_totalRedpackets = true && this.isSetTotalRedpackets();
    boolean that_present_totalRedpackets = true && that.isSetTotalRedpackets();
    if (this_present_totalRedpackets || that_present_totalRedpackets) {
      if (!(this_present_totalRedpackets && that_present_totalRedpackets))
        return false;
      if (this.totalRedpackets != that.totalRedpackets)
        return false;
    }

    boolean this_present_totalBonus = true && this.isSetTotalBonus();
    boolean that_present_totalBonus = true && that.isSetTotalBonus();
    if (this_present_totalBonus || that_present_totalBonus) {
      if (!(this_present_totalBonus && that_present_totalBonus))
        return false;
      if (this.totalBonus != that.totalBonus)
        return false;
    }

    boolean this_present_bonus = true && this.isSetBonus();
    boolean that_present_bonus = true && that.isSetBonus();
    if (this_present_bonus || that_present_bonus) {
      if (!(this_present_bonus && that_present_bonus))
        return false;
      if (!this.bonus.equals(that.bonus))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTotalRedpackets()) ? 131071 : 524287);
    if (isSetTotalRedpackets())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(totalRedpackets);

    hashCode = hashCode * 8191 + ((isSetTotalBonus()) ? 131071 : 524287);
    if (isSetTotalBonus())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(totalBonus);

    hashCode = hashCode * 8191 + ((isSetBonus()) ? 131071 : 524287);
    if (isSetBonus())
      hashCode = hashCode * 8191 + bonus.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(BonusList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTotalRedpackets()).compareTo(other.isSetTotalRedpackets());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalRedpackets()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalRedpackets, other.totalRedpackets);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTotalBonus()).compareTo(other.isSetTotalBonus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalBonus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalBonus, other.totalBonus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBonus()).compareTo(other.isSetBonus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBonus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bonus, other.bonus);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("BonusList(");
    boolean first = true;

    if (isSetTotalRedpackets()) {
      sb.append("totalRedpackets:");
      sb.append(this.totalRedpackets);
      first = false;
    }
    if (isSetTotalBonus()) {
      if (!first) sb.append(", ");
      sb.append("totalBonus:");
      sb.append(this.totalBonus);
      first = false;
    }
    if (isSetBonus()) {
      if (!first) sb.append(", ");
      sb.append("bonus:");
      if (this.bonus == null) {
        sb.append("null");
      } else {
        sb.append(this.bonus);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BonusListStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public BonusListStandardScheme getScheme() {
      return new BonusListStandardScheme();
    }
  }

  private static class BonusListStandardScheme extends org.apache.thrift.scheme.StandardScheme<BonusList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BonusList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_REDPACKETS
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.totalRedpackets = iprot.readDouble();
              struct.setTotalRedpacketsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOTAL_BONUS
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.totalBonus = iprot.readDouble();
              struct.setTotalBonusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BONUS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.bonus = new java.util.ArrayList<Bonus>(_list8.size);
                Bonus _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new Bonus();
                  _elem9.read(iprot);
                  struct.bonus.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setBonusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BonusList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalRedpackets()) {
        oprot.writeFieldBegin(TOTAL_REDPACKETS_FIELD_DESC);
        oprot.writeDouble(struct.totalRedpackets);
        oprot.writeFieldEnd();
      }
      if (struct.isSetTotalBonus()) {
        oprot.writeFieldBegin(TOTAL_BONUS_FIELD_DESC);
        oprot.writeDouble(struct.totalBonus);
        oprot.writeFieldEnd();
      }
      if (struct.bonus != null) {
        if (struct.isSetBonus()) {
          oprot.writeFieldBegin(BONUS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.bonus.size()));
            for (Bonus _iter11 : struct.bonus)
            {
              _iter11.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BonusListTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public BonusListTupleScheme getScheme() {
      return new BonusListTupleScheme();
    }
  }

  private static class BonusListTupleScheme extends org.apache.thrift.scheme.TupleScheme<BonusList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BonusList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTotalRedpackets()) {
        optionals.set(0);
      }
      if (struct.isSetTotalBonus()) {
        optionals.set(1);
      }
      if (struct.isSetBonus()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTotalRedpackets()) {
        oprot.writeDouble(struct.totalRedpackets);
      }
      if (struct.isSetTotalBonus()) {
        oprot.writeDouble(struct.totalBonus);
      }
      if (struct.isSetBonus()) {
        {
          oprot.writeI32(struct.bonus.size());
          for (Bonus _iter12 : struct.bonus)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BonusList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.totalRedpackets = iprot.readDouble();
        struct.setTotalRedpacketsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.totalBonus = iprot.readDouble();
        struct.setTotalBonusIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.bonus = new java.util.ArrayList<Bonus>(_list13.size);
          Bonus _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new Bonus();
            _elem14.read(iprot);
            struct.bonus.add(_elem14);
          }
        }
        struct.setBonusIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
