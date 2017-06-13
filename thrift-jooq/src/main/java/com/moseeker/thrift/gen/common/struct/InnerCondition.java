/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.common.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-23")
public class InnerCondition implements org.apache.thrift.TBase<InnerCondition, InnerCondition._Fields>, java.io.Serializable, Cloneable, Comparable<InnerCondition> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InnerCondition");

  private static final org.apache.thrift.protocol.TField FIRST_CONDITION_FIELD_DESC = new org.apache.thrift.protocol.TField("firstCondition", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SECOND_CONDITION_FIELD_DESC = new org.apache.thrift.protocol.TField("secondCondition", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField CONDITION_OP_FIELD_DESC = new org.apache.thrift.protocol.TField("conditionOp", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new InnerConditionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new InnerConditionTupleSchemeFactory();

  public Condition firstCondition; // required
  public Condition secondCondition; // required
  /**
   * 
   * @see ConditionOp
   */
  public ConditionOp conditionOp; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIRST_CONDITION((short)1, "firstCondition"),
    SECOND_CONDITION((short)2, "secondCondition"),
    /**
     * 
     * @see ConditionOp
     */
    CONDITION_OP((short)3, "conditionOp");

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
        case 1: // FIRST_CONDITION
          return FIRST_CONDITION;
        case 2: // SECOND_CONDITION
          return SECOND_CONDITION;
        case 3: // CONDITION_OP
          return CONDITION_OP;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIRST_CONDITION, new org.apache.thrift.meta_data.FieldMetaData("firstCondition", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "Condition")));
    tmpMap.put(_Fields.SECOND_CONDITION, new org.apache.thrift.meta_data.FieldMetaData("secondCondition", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "Condition")));
    tmpMap.put(_Fields.CONDITION_OP, new org.apache.thrift.meta_data.FieldMetaData("conditionOp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ConditionOp.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InnerCondition.class, metaDataMap);
  }

  public InnerCondition() {
  }

  public InnerCondition(
    Condition firstCondition,
    Condition secondCondition,
    ConditionOp conditionOp)
  {
    this();
    this.firstCondition = firstCondition;
    this.secondCondition = secondCondition;
    this.conditionOp = conditionOp;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InnerCondition(InnerCondition other) {
    if (other.isSetFirstCondition()) {
      this.firstCondition = new Condition(other.firstCondition);
    }
    if (other.isSetSecondCondition()) {
      this.secondCondition = new Condition(other.secondCondition);
    }
    if (other.isSetConditionOp()) {
      this.conditionOp = other.conditionOp;
    }
  }

  public InnerCondition deepCopy() {
    return new InnerCondition(this);
  }

  @Override
  public void clear() {
    this.firstCondition = null;
    this.secondCondition = null;
    this.conditionOp = null;
  }

  public Condition getFirstCondition() {
    return this.firstCondition;
  }

  public InnerCondition setFirstCondition(Condition firstCondition) {
    this.firstCondition = firstCondition;
    return this;
  }

  public void unsetFirstCondition() {
    this.firstCondition = null;
  }

  /** Returns true if field firstCondition is set (has been assigned a value) and false otherwise */
  public boolean isSetFirstCondition() {
    return this.firstCondition != null;
  }

  public void setFirstConditionIsSet(boolean value) {
    if (!value) {
      this.firstCondition = null;
    }
  }

  public Condition getSecondCondition() {
    return this.secondCondition;
  }

  public InnerCondition setSecondCondition(Condition secondCondition) {
    this.secondCondition = secondCondition;
    return this;
  }

  public void unsetSecondCondition() {
    this.secondCondition = null;
  }

  /** Returns true if field secondCondition is set (has been assigned a value) and false otherwise */
  public boolean isSetSecondCondition() {
    return this.secondCondition != null;
  }

  public void setSecondConditionIsSet(boolean value) {
    if (!value) {
      this.secondCondition = null;
    }
  }

  /**
   * 
   * @see ConditionOp
   */
  public ConditionOp getConditionOp() {
    return this.conditionOp;
  }

  /**
   * 
   * @see ConditionOp
   */
  public InnerCondition setConditionOp(ConditionOp conditionOp) {
    this.conditionOp = conditionOp;
    return this;
  }

  public void unsetConditionOp() {
    this.conditionOp = null;
  }

  /** Returns true if field conditionOp is set (has been assigned a value) and false otherwise */
  public boolean isSetConditionOp() {
    return this.conditionOp != null;
  }

  public void setConditionOpIsSet(boolean value) {
    if (!value) {
      this.conditionOp = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FIRST_CONDITION:
      if (value == null) {
        unsetFirstCondition();
      } else {
        setFirstCondition((Condition)value);
      }
      break;

    case SECOND_CONDITION:
      if (value == null) {
        unsetSecondCondition();
      } else {
        setSecondCondition((Condition)value);
      }
      break;

    case CONDITION_OP:
      if (value == null) {
        unsetConditionOp();
      } else {
        setConditionOp((ConditionOp)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FIRST_CONDITION:
      return getFirstCondition();

    case SECOND_CONDITION:
      return getSecondCondition();

    case CONDITION_OP:
      return getConditionOp();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FIRST_CONDITION:
      return isSetFirstCondition();
    case SECOND_CONDITION:
      return isSetSecondCondition();
    case CONDITION_OP:
      return isSetConditionOp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof InnerCondition)
      return this.equals((InnerCondition)that);
    return false;
  }

  public boolean equals(InnerCondition that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_firstCondition = true && this.isSetFirstCondition();
    boolean that_present_firstCondition = true && that.isSetFirstCondition();
    if (this_present_firstCondition || that_present_firstCondition) {
      if (!(this_present_firstCondition && that_present_firstCondition))
        return false;
      if (!this.firstCondition.equals(that.firstCondition))
        return false;
    }

    boolean this_present_secondCondition = true && this.isSetSecondCondition();
    boolean that_present_secondCondition = true && that.isSetSecondCondition();
    if (this_present_secondCondition || that_present_secondCondition) {
      if (!(this_present_secondCondition && that_present_secondCondition))
        return false;
      if (!this.secondCondition.equals(that.secondCondition))
        return false;
    }

    boolean this_present_conditionOp = true && this.isSetConditionOp();
    boolean that_present_conditionOp = true && that.isSetConditionOp();
    if (this_present_conditionOp || that_present_conditionOp) {
      if (!(this_present_conditionOp && that_present_conditionOp))
        return false;
      if (!this.conditionOp.equals(that.conditionOp))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFirstCondition()) ? 131071 : 524287);
    if (isSetFirstCondition())
      hashCode = hashCode * 8191 + firstCondition.hashCode();

    hashCode = hashCode * 8191 + ((isSetSecondCondition()) ? 131071 : 524287);
    if (isSetSecondCondition())
      hashCode = hashCode * 8191 + secondCondition.hashCode();

    hashCode = hashCode * 8191 + ((isSetConditionOp()) ? 131071 : 524287);
    if (isSetConditionOp())
      hashCode = hashCode * 8191 + conditionOp.getValue();

    return hashCode;
  }

  @Override
  public int compareTo(InnerCondition other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFirstCondition()).compareTo(other.isSetFirstCondition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFirstCondition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.firstCondition, other.firstCondition);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSecondCondition()).compareTo(other.isSetSecondCondition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSecondCondition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.secondCondition, other.secondCondition);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetConditionOp()).compareTo(other.isSetConditionOp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetConditionOp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.conditionOp, other.conditionOp);
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
    StringBuilder sb = new StringBuilder("InnerCondition(");
    boolean first = true;

    sb.append("firstCondition:");
    if (this.firstCondition == null) {
      sb.append("null");
    } else {
      sb.append(this.firstCondition);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("secondCondition:");
    if (this.secondCondition == null) {
      sb.append("null");
    } else {
      sb.append(this.secondCondition);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("conditionOp:");
    if (this.conditionOp == null) {
      sb.append("null");
    } else {
      sb.append(this.conditionOp);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class InnerConditionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public InnerConditionStandardScheme getScheme() {
      return new InnerConditionStandardScheme();
    }
  }

  private static class InnerConditionStandardScheme extends org.apache.thrift.scheme.StandardScheme<InnerCondition> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InnerCondition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FIRST_CONDITION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.firstCondition = new Condition();
              struct.firstCondition.read(iprot);
              struct.setFirstConditionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SECOND_CONDITION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.secondCondition = new Condition();
              struct.secondCondition.read(iprot);
              struct.setSecondConditionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CONDITION_OP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.conditionOp = ConditionOp.findByValue(iprot.readI32());
              struct.setConditionOpIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, InnerCondition struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.firstCondition != null) {
        oprot.writeFieldBegin(FIRST_CONDITION_FIELD_DESC);
        struct.firstCondition.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.secondCondition != null) {
        oprot.writeFieldBegin(SECOND_CONDITION_FIELD_DESC);
        struct.secondCondition.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.conditionOp != null) {
        oprot.writeFieldBegin(CONDITION_OP_FIELD_DESC);
        oprot.writeI32(struct.conditionOp.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InnerConditionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public InnerConditionTupleScheme getScheme() {
      return new InnerConditionTupleScheme();
    }
  }

  private static class InnerConditionTupleScheme extends org.apache.thrift.scheme.TupleScheme<InnerCondition> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InnerCondition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFirstCondition()) {
        optionals.set(0);
      }
      if (struct.isSetSecondCondition()) {
        optionals.set(1);
      }
      if (struct.isSetConditionOp()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetFirstCondition()) {
        struct.firstCondition.write(oprot);
      }
      if (struct.isSetSecondCondition()) {
        struct.secondCondition.write(oprot);
      }
      if (struct.isSetConditionOp()) {
        oprot.writeI32(struct.conditionOp.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InnerCondition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.firstCondition = new Condition();
        struct.firstCondition.read(iprot);
        struct.setFirstConditionIsSet(true);
      }
      if (incoming.get(1)) {
        struct.secondCondition = new Condition();
        struct.secondCondition.read(iprot);
        struct.setSecondConditionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.conditionOp = ConditionOp.findByValue(iprot.readI32());
        struct.setConditionOpIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
