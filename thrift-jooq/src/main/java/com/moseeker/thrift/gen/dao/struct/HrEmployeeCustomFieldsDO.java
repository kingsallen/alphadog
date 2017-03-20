/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-15")
public class HrEmployeeCustomFieldsDO implements org.apache.thrift.TBase<HrEmployeeCustomFieldsDO, HrEmployeeCustomFieldsDO._Fields>, java.io.Serializable, Cloneable, Comparable<HrEmployeeCustomFieldsDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrEmployeeCustomFieldsDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("company_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField FNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("fname", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FVALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("fvalues", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField FORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("forder", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField DISABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("disable", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField MANDATORY_FIELD_DESC = new org.apache.thrift.protocol.TField("mandatory", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrEmployeeCustomFieldsDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrEmployeeCustomFieldsDOTupleSchemeFactory();

  public int id; // required
  public int company_id; // required
  public java.lang.String fname; // required
  public java.lang.String fvalues; // required
  public int forder; // required
  public int disable; // required
  public int mandatory; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    COMPANY_ID((short)2, "company_id"),
    FNAME((short)3, "fname"),
    FVALUES((short)4, "fvalues"),
    FORDER((short)5, "forder"),
    DISABLE((short)6, "disable"),
    MANDATORY((short)7, "mandatory"),
    STATUS((short)8, "status");

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
        case 2: // COMPANY_ID
          return COMPANY_ID;
        case 3: // FNAME
          return FNAME;
        case 4: // FVALUES
          return FVALUES;
        case 5: // FORDER
          return FORDER;
        case 6: // DISABLE
          return DISABLE;
        case 7: // MANDATORY
          return MANDATORY;
        case 8: // STATUS
          return STATUS;
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
  private static final int __COMPANY_ID_ISSET_ID = 1;
  private static final int __FORDER_ISSET_ID = 2;
  private static final int __DISABLE_ISSET_ID = 3;
  private static final int __MANDATORY_ISSET_ID = 4;
  private static final int __STATUS_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("company_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FNAME, new org.apache.thrift.meta_data.FieldMetaData("fname", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FVALUES, new org.apache.thrift.meta_data.FieldMetaData("fvalues", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FORDER, new org.apache.thrift.meta_data.FieldMetaData("forder", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DISABLE, new org.apache.thrift.meta_data.FieldMetaData("disable", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MANDATORY, new org.apache.thrift.meta_data.FieldMetaData("mandatory", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrEmployeeCustomFieldsDO.class, metaDataMap);
  }

  public HrEmployeeCustomFieldsDO() {
  }

  public HrEmployeeCustomFieldsDO(
    int id,
    int company_id,
    java.lang.String fname,
    java.lang.String fvalues,
    int forder,
    int disable,
    int mandatory,
    int status)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.company_id = company_id;
    setCompany_idIsSet(true);
    this.fname = fname;
    this.fvalues = fvalues;
    this.forder = forder;
    setForderIsSet(true);
    this.disable = disable;
    setDisableIsSet(true);
    this.mandatory = mandatory;
    setMandatoryIsSet(true);
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrEmployeeCustomFieldsDO(HrEmployeeCustomFieldsDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.company_id = other.company_id;
    if (other.isSetFname()) {
      this.fname = other.fname;
    }
    if (other.isSetFvalues()) {
      this.fvalues = other.fvalues;
    }
    this.forder = other.forder;
    this.disable = other.disable;
    this.mandatory = other.mandatory;
    this.status = other.status;
  }

  public HrEmployeeCustomFieldsDO deepCopy() {
    return new HrEmployeeCustomFieldsDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setCompany_idIsSet(false);
    this.company_id = 0;
    this.fname = null;
    this.fvalues = null;
    setForderIsSet(false);
    this.forder = 0;
    setDisableIsSet(false);
    this.disable = 0;
    setMandatoryIsSet(false);
    this.mandatory = 0;
    setStatusIsSet(false);
    this.status = 0;
  }

  public int getId() {
    return this.id;
  }

  public HrEmployeeCustomFieldsDO setId(int id) {
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

  public int getCompany_id() {
    return this.company_id;
  }

  public HrEmployeeCustomFieldsDO setCompany_id(int company_id) {
    this.company_id = company_id;
    setCompany_idIsSet(true);
    return this;
  }

  public void unsetCompany_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  /** Returns true if field company_id is set (has been assigned a value) and false otherwise */
  public boolean isSetCompany_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  public void setCompany_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMPANY_ID_ISSET_ID, value);
  }

  public java.lang.String getFname() {
    return this.fname;
  }

  public HrEmployeeCustomFieldsDO setFname(java.lang.String fname) {
    this.fname = fname;
    return this;
  }

  public void unsetFname() {
    this.fname = null;
  }

  /** Returns true if field fname is set (has been assigned a value) and false otherwise */
  public boolean isSetFname() {
    return this.fname != null;
  }

  public void setFnameIsSet(boolean value) {
    if (!value) {
      this.fname = null;
    }
  }

  public java.lang.String getFvalues() {
    return this.fvalues;
  }

  public HrEmployeeCustomFieldsDO setFvalues(java.lang.String fvalues) {
    this.fvalues = fvalues;
    return this;
  }

  public void unsetFvalues() {
    this.fvalues = null;
  }

  /** Returns true if field fvalues is set (has been assigned a value) and false otherwise */
  public boolean isSetFvalues() {
    return this.fvalues != null;
  }

  public void setFvaluesIsSet(boolean value) {
    if (!value) {
      this.fvalues = null;
    }
  }

  public int getForder() {
    return this.forder;
  }

  public HrEmployeeCustomFieldsDO setForder(int forder) {
    this.forder = forder;
    setForderIsSet(true);
    return this;
  }

  public void unsetForder() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FORDER_ISSET_ID);
  }

  /** Returns true if field forder is set (has been assigned a value) and false otherwise */
  public boolean isSetForder() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FORDER_ISSET_ID);
  }

  public void setForderIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FORDER_ISSET_ID, value);
  }

  public int getDisable() {
    return this.disable;
  }

  public HrEmployeeCustomFieldsDO setDisable(int disable) {
    this.disable = disable;
    setDisableIsSet(true);
    return this;
  }

  public void unsetDisable() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  /** Returns true if field disable is set (has been assigned a value) and false otherwise */
  public boolean isSetDisable() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DISABLE_ISSET_ID);
  }

  public void setDisableIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DISABLE_ISSET_ID, value);
  }

  public int getMandatory() {
    return this.mandatory;
  }

  public HrEmployeeCustomFieldsDO setMandatory(int mandatory) {
    this.mandatory = mandatory;
    setMandatoryIsSet(true);
    return this;
  }

  public void unsetMandatory() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MANDATORY_ISSET_ID);
  }

  /** Returns true if field mandatory is set (has been assigned a value) and false otherwise */
  public boolean isSetMandatory() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MANDATORY_ISSET_ID);
  }

  public void setMandatoryIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MANDATORY_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public HrEmployeeCustomFieldsDO setStatus(int status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
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

    case COMPANY_ID:
      if (value == null) {
        unsetCompany_id();
      } else {
        setCompany_id((java.lang.Integer)value);
      }
      break;

    case FNAME:
      if (value == null) {
        unsetFname();
      } else {
        setFname((java.lang.String)value);
      }
      break;

    case FVALUES:
      if (value == null) {
        unsetFvalues();
      } else {
        setFvalues((java.lang.String)value);
      }
      break;

    case FORDER:
      if (value == null) {
        unsetForder();
      } else {
        setForder((java.lang.Integer)value);
      }
      break;

    case DISABLE:
      if (value == null) {
        unsetDisable();
      } else {
        setDisable((java.lang.Integer)value);
      }
      break;

    case MANDATORY:
      if (value == null) {
        unsetMandatory();
      } else {
        setMandatory((java.lang.Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case COMPANY_ID:
      return getCompany_id();

    case FNAME:
      return getFname();

    case FVALUES:
      return getFvalues();

    case FORDER:
      return getForder();

    case DISABLE:
      return getDisable();

    case MANDATORY:
      return getMandatory();

    case STATUS:
      return getStatus();

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
    case COMPANY_ID:
      return isSetCompany_id();
    case FNAME:
      return isSetFname();
    case FVALUES:
      return isSetFvalues();
    case FORDER:
      return isSetForder();
    case DISABLE:
      return isSetDisable();
    case MANDATORY:
      return isSetMandatory();
    case STATUS:
      return isSetStatus();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof HrEmployeeCustomFieldsDO)
      return this.equals((HrEmployeeCustomFieldsDO)that);
    return false;
  }

  public boolean equals(HrEmployeeCustomFieldsDO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_company_id = true;
    boolean that_present_company_id = true;
    if (this_present_company_id || that_present_company_id) {
      if (!(this_present_company_id && that_present_company_id))
        return false;
      if (this.company_id != that.company_id)
        return false;
    }

    boolean this_present_fname = true && this.isSetFname();
    boolean that_present_fname = true && that.isSetFname();
    if (this_present_fname || that_present_fname) {
      if (!(this_present_fname && that_present_fname))
        return false;
      if (!this.fname.equals(that.fname))
        return false;
    }

    boolean this_present_fvalues = true && this.isSetFvalues();
    boolean that_present_fvalues = true && that.isSetFvalues();
    if (this_present_fvalues || that_present_fvalues) {
      if (!(this_present_fvalues && that_present_fvalues))
        return false;
      if (!this.fvalues.equals(that.fvalues))
        return false;
    }

    boolean this_present_forder = true;
    boolean that_present_forder = true;
    if (this_present_forder || that_present_forder) {
      if (!(this_present_forder && that_present_forder))
        return false;
      if (this.forder != that.forder)
        return false;
    }

    boolean this_present_disable = true;
    boolean that_present_disable = true;
    if (this_present_disable || that_present_disable) {
      if (!(this_present_disable && that_present_disable))
        return false;
      if (this.disable != that.disable)
        return false;
    }

    boolean this_present_mandatory = true;
    boolean that_present_mandatory = true;
    if (this_present_mandatory || that_present_mandatory) {
      if (!(this_present_mandatory && that_present_mandatory))
        return false;
      if (this.mandatory != that.mandatory)
        return false;
    }

    boolean this_present_status = true;
    boolean that_present_status = true;
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + id;

    hashCode = hashCode * 8191 + company_id;

    hashCode = hashCode * 8191 + ((isSetFname()) ? 131071 : 524287);
    if (isSetFname())
      hashCode = hashCode * 8191 + fname.hashCode();

    hashCode = hashCode * 8191 + ((isSetFvalues()) ? 131071 : 524287);
    if (isSetFvalues())
      hashCode = hashCode * 8191 + fvalues.hashCode();

    hashCode = hashCode * 8191 + forder;

    hashCode = hashCode * 8191 + disable;

    hashCode = hashCode * 8191 + mandatory;

    hashCode = hashCode * 8191 + status;

    return hashCode;
  }

  @Override
  public int compareTo(HrEmployeeCustomFieldsDO other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetCompany_id()).compareTo(other.isSetCompany_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompany_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.company_id, other.company_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFname()).compareTo(other.isSetFname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fname, other.fname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFvalues()).compareTo(other.isSetFvalues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFvalues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fvalues, other.fvalues);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetForder()).compareTo(other.isSetForder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.forder, other.forder);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDisable()).compareTo(other.isSetDisable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.disable, other.disable);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMandatory()).compareTo(other.isSetMandatory());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMandatory()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mandatory, other.mandatory);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HrEmployeeCustomFieldsDO(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("company_id:");
    sb.append(this.company_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fname:");
    if (this.fname == null) {
      sb.append("null");
    } else {
      sb.append(this.fname);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fvalues:");
    if (this.fvalues == null) {
      sb.append("null");
    } else {
      sb.append(this.fvalues);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("forder:");
    sb.append(this.forder);
    first = false;
    if (!first) sb.append(", ");
    sb.append("disable:");
    sb.append(this.disable);
    first = false;
    if (!first) sb.append(", ");
    sb.append("mandatory:");
    sb.append(this.mandatory);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HrEmployeeCustomFieldsDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrEmployeeCustomFieldsDOStandardScheme getScheme() {
      return new HrEmployeeCustomFieldsDOStandardScheme();
    }
  }

  private static class HrEmployeeCustomFieldsDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrEmployeeCustomFieldsDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrEmployeeCustomFieldsDO struct) throws org.apache.thrift.TException {
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
          case 2: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.company_id = iprot.readI32();
              struct.setCompany_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fname = iprot.readString();
              struct.setFnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FVALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fvalues = iprot.readString();
              struct.setFvaluesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.forder = iprot.readI32();
              struct.setForderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DISABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.disable = iprot.readI32();
              struct.setDisableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // MANDATORY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.mandatory = iprot.readI32();
              struct.setMandatoryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrEmployeeCustomFieldsDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
      oprot.writeI32(struct.company_id);
      oprot.writeFieldEnd();
      if (struct.fname != null) {
        oprot.writeFieldBegin(FNAME_FIELD_DESC);
        oprot.writeString(struct.fname);
        oprot.writeFieldEnd();
      }
      if (struct.fvalues != null) {
        oprot.writeFieldBegin(FVALUES_FIELD_DESC);
        oprot.writeString(struct.fvalues);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FORDER_FIELD_DESC);
      oprot.writeI32(struct.forder);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DISABLE_FIELD_DESC);
      oprot.writeI32(struct.disable);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MANDATORY_FIELD_DESC);
      oprot.writeI32(struct.mandatory);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrEmployeeCustomFieldsDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrEmployeeCustomFieldsDOTupleScheme getScheme() {
      return new HrEmployeeCustomFieldsDOTupleScheme();
    }
  }

  private static class HrEmployeeCustomFieldsDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrEmployeeCustomFieldsDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrEmployeeCustomFieldsDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCompany_id()) {
        optionals.set(1);
      }
      if (struct.isSetFname()) {
        optionals.set(2);
      }
      if (struct.isSetFvalues()) {
        optionals.set(3);
      }
      if (struct.isSetForder()) {
        optionals.set(4);
      }
      if (struct.isSetDisable()) {
        optionals.set(5);
      }
      if (struct.isSetMandatory()) {
        optionals.set(6);
      }
      if (struct.isSetStatus()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetCompany_id()) {
        oprot.writeI32(struct.company_id);
      }
      if (struct.isSetFname()) {
        oprot.writeString(struct.fname);
      }
      if (struct.isSetFvalues()) {
        oprot.writeString(struct.fvalues);
      }
      if (struct.isSetForder()) {
        oprot.writeI32(struct.forder);
      }
      if (struct.isSetDisable()) {
        oprot.writeI32(struct.disable);
      }
      if (struct.isSetMandatory()) {
        oprot.writeI32(struct.mandatory);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrEmployeeCustomFieldsDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.company_id = iprot.readI32();
        struct.setCompany_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.fname = iprot.readString();
        struct.setFnameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.fvalues = iprot.readString();
        struct.setFvaluesIsSet(true);
      }
      if (incoming.get(4)) {
        struct.forder = iprot.readI32();
        struct.setForderIsSet(true);
      }
      if (incoming.get(5)) {
        struct.disable = iprot.readI32();
        struct.setDisableIsSet(true);
      }
      if (incoming.get(6)) {
        struct.mandatory = iprot.readI32();
        struct.setMandatoryIsSet(true);
      }
      if (incoming.get(7)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

