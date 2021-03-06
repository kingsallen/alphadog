/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.useraccounts.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-12-12")
public class ApplicationRecordsForm implements org.apache.thrift.TBase<ApplicationRecordsForm, ApplicationRecordsForm._Fields>, java.io.Serializable, Cloneable, Comparable<ApplicationRecordsForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ApplicationRecordsForm");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField POSITION_TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("position_title", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField COMPANY_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("company_name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField STATUS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("status_name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField SIGNATURE_FIELD_DESC = new org.apache.thrift.protocol.TField("signature", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ApplicationRecordsFormStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ApplicationRecordsFormTupleSchemeFactory();

  public int id; // optional
  public java.lang.String position_title; // optional
  public java.lang.String company_name; // optional
  public java.lang.String status_name; // optional
  public java.lang.String time; // optional
  public java.lang.String signature; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    POSITION_TITLE((short)2, "position_title"),
    COMPANY_NAME((short)3, "company_name"),
    STATUS_NAME((short)4, "status_name"),
    TIME((short)5, "time"),
    SIGNATURE((short)6, "signature");

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
        case 2: // POSITION_TITLE
          return POSITION_TITLE;
        case 3: // COMPANY_NAME
          return COMPANY_NAME;
        case 4: // STATUS_NAME
          return STATUS_NAME;
        case 5: // TIME
          return TIME;
        case 6: // SIGNATURE
          return SIGNATURE;
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
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.POSITION_TITLE,_Fields.COMPANY_NAME,_Fields.STATUS_NAME,_Fields.TIME,_Fields.SIGNATURE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.POSITION_TITLE, new org.apache.thrift.meta_data.FieldMetaData("position_title", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPANY_NAME, new org.apache.thrift.meta_data.FieldMetaData("company_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS_NAME, new org.apache.thrift.meta_data.FieldMetaData("status_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.SIGNATURE, new org.apache.thrift.meta_data.FieldMetaData("signature", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ApplicationRecordsForm.class, metaDataMap);
  }

  public ApplicationRecordsForm() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ApplicationRecordsForm(ApplicationRecordsForm other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetPosition_title()) {
      this.position_title = other.position_title;
    }
    if (other.isSetCompany_name()) {
      this.company_name = other.company_name;
    }
    if (other.isSetStatus_name()) {
      this.status_name = other.status_name;
    }
    if (other.isSetTime()) {
      this.time = other.time;
    }
    if (other.isSetSignature()) {
      this.signature = other.signature;
    }
  }

  public ApplicationRecordsForm deepCopy() {
    return new ApplicationRecordsForm(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.position_title = null;
    this.company_name = null;
    this.status_name = null;
    this.time = null;
    this.signature = null;
  }

  public int getId() {
    return this.id;
  }

  public ApplicationRecordsForm setId(int id) {
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

  public java.lang.String getPosition_title() {
    return this.position_title;
  }

  public ApplicationRecordsForm setPosition_title(java.lang.String position_title) {
    this.position_title = position_title;
    return this;
  }

  public void unsetPosition_title() {
    this.position_title = null;
  }

  /** Returns true if field position_title is set (has been assigned a value) and false otherwise */
  public boolean isSetPosition_title() {
    return this.position_title != null;
  }

  public void setPosition_titleIsSet(boolean value) {
    if (!value) {
      this.position_title = null;
    }
  }

  public java.lang.String getCompany_name() {
    return this.company_name;
  }

  public ApplicationRecordsForm setCompany_name(java.lang.String company_name) {
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

  public java.lang.String getStatus_name() {
    return this.status_name;
  }

  public ApplicationRecordsForm setStatus_name(java.lang.String status_name) {
    this.status_name = status_name;
    return this;
  }

  public void unsetStatus_name() {
    this.status_name = null;
  }

  /** Returns true if field status_name is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus_name() {
    return this.status_name != null;
  }

  public void setStatus_nameIsSet(boolean value) {
    if (!value) {
      this.status_name = null;
    }
  }

  public java.lang.String getTime() {
    return this.time;
  }

  public ApplicationRecordsForm setTime(java.lang.String time) {
    this.time = time;
    return this;
  }

  public void unsetTime() {
    this.time = null;
  }

  /** Returns true if field time is set (has been assigned a value) and false otherwise */
  public boolean isSetTime() {
    return this.time != null;
  }

  public void setTimeIsSet(boolean value) {
    if (!value) {
      this.time = null;
    }
  }

  public java.lang.String getSignature() {
    return this.signature;
  }

  public ApplicationRecordsForm setSignature(java.lang.String signature) {
    this.signature = signature;
    return this;
  }

  public void unsetSignature() {
    this.signature = null;
  }

  /** Returns true if field signature is set (has been assigned a value) and false otherwise */
  public boolean isSetSignature() {
    return this.signature != null;
  }

  public void setSignatureIsSet(boolean value) {
    if (!value) {
      this.signature = null;
    }
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

    case POSITION_TITLE:
      if (value == null) {
        unsetPosition_title();
      } else {
        setPosition_title((java.lang.String)value);
      }
      break;

    case COMPANY_NAME:
      if (value == null) {
        unsetCompany_name();
      } else {
        setCompany_name((java.lang.String)value);
      }
      break;

    case STATUS_NAME:
      if (value == null) {
        unsetStatus_name();
      } else {
        setStatus_name((java.lang.String)value);
      }
      break;

    case TIME:
      if (value == null) {
        unsetTime();
      } else {
        setTime((java.lang.String)value);
      }
      break;

    case SIGNATURE:
      if (value == null) {
        unsetSignature();
      } else {
        setSignature((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case POSITION_TITLE:
      return getPosition_title();

    case COMPANY_NAME:
      return getCompany_name();

    case STATUS_NAME:
      return getStatus_name();

    case TIME:
      return getTime();

    case SIGNATURE:
      return getSignature();

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
    case POSITION_TITLE:
      return isSetPosition_title();
    case COMPANY_NAME:
      return isSetCompany_name();
    case STATUS_NAME:
      return isSetStatus_name();
    case TIME:
      return isSetTime();
    case SIGNATURE:
      return isSetSignature();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ApplicationRecordsForm)
      return this.equals((ApplicationRecordsForm)that);
    return false;
  }

  public boolean equals(ApplicationRecordsForm that) {
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

    boolean this_present_position_title = true && this.isSetPosition_title();
    boolean that_present_position_title = true && that.isSetPosition_title();
    if (this_present_position_title || that_present_position_title) {
      if (!(this_present_position_title && that_present_position_title))
        return false;
      if (!this.position_title.equals(that.position_title))
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

    boolean this_present_status_name = true && this.isSetStatus_name();
    boolean that_present_status_name = true && that.isSetStatus_name();
    if (this_present_status_name || that_present_status_name) {
      if (!(this_present_status_name && that_present_status_name))
        return false;
      if (!this.status_name.equals(that.status_name))
        return false;
    }

    boolean this_present_time = true && this.isSetTime();
    boolean that_present_time = true && that.isSetTime();
    if (this_present_time || that_present_time) {
      if (!(this_present_time && that_present_time))
        return false;
      if (!this.time.equals(that.time))
        return false;
    }

    boolean this_present_signature = true && this.isSetSignature();
    boolean that_present_signature = true && that.isSetSignature();
    if (this_present_signature || that_present_signature) {
      if (!(this_present_signature && that_present_signature))
        return false;
      if (!this.signature.equals(that.signature))
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

    hashCode = hashCode * 8191 + ((isSetPosition_title()) ? 131071 : 524287);
    if (isSetPosition_title())
      hashCode = hashCode * 8191 + position_title.hashCode();

    hashCode = hashCode * 8191 + ((isSetCompany_name()) ? 131071 : 524287);
    if (isSetCompany_name())
      hashCode = hashCode * 8191 + company_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetStatus_name()) ? 131071 : 524287);
    if (isSetStatus_name())
      hashCode = hashCode * 8191 + status_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetTime()) ? 131071 : 524287);
    if (isSetTime())
      hashCode = hashCode * 8191 + time.hashCode();

    hashCode = hashCode * 8191 + ((isSetSignature()) ? 131071 : 524287);
    if (isSetSignature())
      hashCode = hashCode * 8191 + signature.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ApplicationRecordsForm other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetPosition_title()).compareTo(other.isSetPosition_title());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPosition_title()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.position_title, other.position_title);
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
    lastComparison = java.lang.Boolean.valueOf(isSetStatus_name()).compareTo(other.isSetStatus_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status_name, other.status_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTime()).compareTo(other.isSetTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.time, other.time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSignature()).compareTo(other.isSetSignature());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSignature()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.signature, other.signature);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ApplicationRecordsForm(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetPosition_title()) {
      if (!first) sb.append(", ");
      sb.append("position_title:");
      if (this.position_title == null) {
        sb.append("null");
      } else {
        sb.append(this.position_title);
      }
      first = false;
    }
    if (isSetCompany_name()) {
      if (!first) sb.append(", ");
      sb.append("company_name:");
      if (this.company_name == null) {
        sb.append("null");
      } else {
        sb.append(this.company_name);
      }
      first = false;
    }
    if (isSetStatus_name()) {
      if (!first) sb.append(", ");
      sb.append("status_name:");
      if (this.status_name == null) {
        sb.append("null");
      } else {
        sb.append(this.status_name);
      }
      first = false;
    }
    if (isSetTime()) {
      if (!first) sb.append(", ");
      sb.append("time:");
      if (this.time == null) {
        sb.append("null");
      } else {
        sb.append(this.time);
      }
      first = false;
    }
    if (isSetSignature()) {
      if (!first) sb.append(", ");
      sb.append("signature:");
      if (this.signature == null) {
        sb.append("null");
      } else {
        sb.append(this.signature);
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

  private static class ApplicationRecordsFormStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ApplicationRecordsFormStandardScheme getScheme() {
      return new ApplicationRecordsFormStandardScheme();
    }
  }

  private static class ApplicationRecordsFormStandardScheme extends org.apache.thrift.scheme.StandardScheme<ApplicationRecordsForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ApplicationRecordsForm struct) throws org.apache.thrift.TException {
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
          case 2: // POSITION_TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.position_title = iprot.readString();
              struct.setPosition_titleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COMPANY_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.company_name = iprot.readString();
              struct.setCompany_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // STATUS_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status_name = iprot.readString();
              struct.setStatus_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.time = iprot.readString();
              struct.setTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SIGNATURE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.signature = iprot.readString();
              struct.setSignatureIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ApplicationRecordsForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.position_title != null) {
        if (struct.isSetPosition_title()) {
          oprot.writeFieldBegin(POSITION_TITLE_FIELD_DESC);
          oprot.writeString(struct.position_title);
          oprot.writeFieldEnd();
        }
      }
      if (struct.company_name != null) {
        if (struct.isSetCompany_name()) {
          oprot.writeFieldBegin(COMPANY_NAME_FIELD_DESC);
          oprot.writeString(struct.company_name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.status_name != null) {
        if (struct.isSetStatus_name()) {
          oprot.writeFieldBegin(STATUS_NAME_FIELD_DESC);
          oprot.writeString(struct.status_name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.time != null) {
        if (struct.isSetTime()) {
          oprot.writeFieldBegin(TIME_FIELD_DESC);
          oprot.writeString(struct.time);
          oprot.writeFieldEnd();
        }
      }
      if (struct.signature != null) {
        if (struct.isSetSignature()) {
          oprot.writeFieldBegin(SIGNATURE_FIELD_DESC);
          oprot.writeString(struct.signature);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ApplicationRecordsFormTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ApplicationRecordsFormTupleScheme getScheme() {
      return new ApplicationRecordsFormTupleScheme();
    }
  }

  private static class ApplicationRecordsFormTupleScheme extends org.apache.thrift.scheme.TupleScheme<ApplicationRecordsForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ApplicationRecordsForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetPosition_title()) {
        optionals.set(1);
      }
      if (struct.isSetCompany_name()) {
        optionals.set(2);
      }
      if (struct.isSetStatus_name()) {
        optionals.set(3);
      }
      if (struct.isSetTime()) {
        optionals.set(4);
      }
      if (struct.isSetSignature()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetPosition_title()) {
        oprot.writeString(struct.position_title);
      }
      if (struct.isSetCompany_name()) {
        oprot.writeString(struct.company_name);
      }
      if (struct.isSetStatus_name()) {
        oprot.writeString(struct.status_name);
      }
      if (struct.isSetTime()) {
        oprot.writeString(struct.time);
      }
      if (struct.isSetSignature()) {
        oprot.writeString(struct.signature);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ApplicationRecordsForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.position_title = iprot.readString();
        struct.setPosition_titleIsSet(true);
      }
      if (incoming.get(2)) {
        struct.company_name = iprot.readString();
        struct.setCompany_nameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.status_name = iprot.readString();
        struct.setStatus_nameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.time = iprot.readString();
        struct.setTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.signature = iprot.readString();
        struct.setSignatureIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

