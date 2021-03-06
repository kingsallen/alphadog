/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.company.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-04-25")
public class EmailAccountForm implements org.apache.thrift.TBase<EmailAccountForm, EmailAccountForm._Fields>, java.io.Serializable, Cloneable, Comparable<EmailAccountForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EmailAccountForm");

  private static final org.apache.thrift.protocol.TField TOTAL_FIELD_DESC = new org.apache.thrift.protocol.TField("total", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("page_number", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PAGE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("page_size", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("company_id", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField EMAIL_ACCOUNTS_FIELD_DESC = new org.apache.thrift.protocol.TField("email_accounts", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new EmailAccountFormStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new EmailAccountFormTupleSchemeFactory();

  public int total; // optional
  public int page_number; // optional
  public int page_size; // optional
  public int company_id; // optional
  public java.util.List<EmailAccountInfo> email_accounts; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL((short)1, "total"),
    PAGE_NUMBER((short)2, "page_number"),
    PAGE_SIZE((short)3, "page_size"),
    COMPANY_ID((short)4, "company_id"),
    EMAIL_ACCOUNTS((short)5, "email_accounts");

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
        case 1: // TOTAL
          return TOTAL;
        case 2: // PAGE_NUMBER
          return PAGE_NUMBER;
        case 3: // PAGE_SIZE
          return PAGE_SIZE;
        case 4: // COMPANY_ID
          return COMPANY_ID;
        case 5: // EMAIL_ACCOUNTS
          return EMAIL_ACCOUNTS;
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
  private static final int __TOTAL_ISSET_ID = 0;
  private static final int __PAGE_NUMBER_ISSET_ID = 1;
  private static final int __PAGE_SIZE_ISSET_ID = 2;
  private static final int __COMPANY_ID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TOTAL,_Fields.PAGE_NUMBER,_Fields.PAGE_SIZE,_Fields.COMPANY_ID,_Fields.EMAIL_ACCOUNTS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL, new org.apache.thrift.meta_data.FieldMetaData("total", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("page_number", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("page_size", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("company_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.EMAIL_ACCOUNTS, new org.apache.thrift.meta_data.FieldMetaData("email_accounts", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, EmailAccountInfo.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EmailAccountForm.class, metaDataMap);
  }

  public EmailAccountForm() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EmailAccountForm(EmailAccountForm other) {
    __isset_bitfield = other.__isset_bitfield;
    this.total = other.total;
    this.page_number = other.page_number;
    this.page_size = other.page_size;
    this.company_id = other.company_id;
    if (other.isSetEmail_accounts()) {
      java.util.List<EmailAccountInfo> __this__email_accounts = new java.util.ArrayList<EmailAccountInfo>(other.email_accounts.size());
      for (EmailAccountInfo other_element : other.email_accounts) {
        __this__email_accounts.add(new EmailAccountInfo(other_element));
      }
      this.email_accounts = __this__email_accounts;
    }
  }

  public EmailAccountForm deepCopy() {
    return new EmailAccountForm(this);
  }

  @Override
  public void clear() {
    setTotalIsSet(false);
    this.total = 0;
    setPage_numberIsSet(false);
    this.page_number = 0;
    setPage_sizeIsSet(false);
    this.page_size = 0;
    setCompany_idIsSet(false);
    this.company_id = 0;
    this.email_accounts = null;
  }

  public int getTotal() {
    return this.total;
  }

  public EmailAccountForm setTotal(int total) {
    this.total = total;
    setTotalIsSet(true);
    return this;
  }

  public void unsetTotal() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTAL_ISSET_ID);
  }

  /** Returns true if field total is set (has been assigned a value) and false otherwise */
  public boolean isSetTotal() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTAL_ISSET_ID);
  }

  public void setTotalIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTAL_ISSET_ID, value);
  }

  public int getPage_number() {
    return this.page_number;
  }

  public EmailAccountForm setPage_number(int page_number) {
    this.page_number = page_number;
    setPage_numberIsSet(true);
    return this;
  }

  public void unsetPage_number() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID);
  }

  /** Returns true if field page_number is set (has been assigned a value) and false otherwise */
  public boolean isSetPage_number() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID);
  }

  public void setPage_numberIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGE_NUMBER_ISSET_ID, value);
  }

  public int getPage_size() {
    return this.page_size;
  }

  public EmailAccountForm setPage_size(int page_size) {
    this.page_size = page_size;
    setPage_sizeIsSet(true);
    return this;
  }

  public void unsetPage_size() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID);
  }

  /** Returns true if field page_size is set (has been assigned a value) and false otherwise */
  public boolean isSetPage_size() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID);
  }

  public void setPage_sizeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGE_SIZE_ISSET_ID, value);
  }

  public int getCompany_id() {
    return this.company_id;
  }

  public EmailAccountForm setCompany_id(int company_id) {
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

  public int getEmail_accountsSize() {
    return (this.email_accounts == null) ? 0 : this.email_accounts.size();
  }

  public java.util.Iterator<EmailAccountInfo> getEmail_accountsIterator() {
    return (this.email_accounts == null) ? null : this.email_accounts.iterator();
  }

  public void addToEmail_accounts(EmailAccountInfo elem) {
    if (this.email_accounts == null) {
      this.email_accounts = new java.util.ArrayList<EmailAccountInfo>();
    }
    this.email_accounts.add(elem);
  }

  public java.util.List<EmailAccountInfo> getEmail_accounts() {
    return this.email_accounts;
  }

  public EmailAccountForm setEmail_accounts(java.util.List<EmailAccountInfo> email_accounts) {
    this.email_accounts = email_accounts;
    return this;
  }

  public void unsetEmail_accounts() {
    this.email_accounts = null;
  }

  /** Returns true if field email_accounts is set (has been assigned a value) and false otherwise */
  public boolean isSetEmail_accounts() {
    return this.email_accounts != null;
  }

  public void setEmail_accountsIsSet(boolean value) {
    if (!value) {
      this.email_accounts = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TOTAL:
      if (value == null) {
        unsetTotal();
      } else {
        setTotal((java.lang.Integer)value);
      }
      break;

    case PAGE_NUMBER:
      if (value == null) {
        unsetPage_number();
      } else {
        setPage_number((java.lang.Integer)value);
      }
      break;

    case PAGE_SIZE:
      if (value == null) {
        unsetPage_size();
      } else {
        setPage_size((java.lang.Integer)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompany_id();
      } else {
        setCompany_id((java.lang.Integer)value);
      }
      break;

    case EMAIL_ACCOUNTS:
      if (value == null) {
        unsetEmail_accounts();
      } else {
        setEmail_accounts((java.util.List<EmailAccountInfo>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL:
      return getTotal();

    case PAGE_NUMBER:
      return getPage_number();

    case PAGE_SIZE:
      return getPage_size();

    case COMPANY_ID:
      return getCompany_id();

    case EMAIL_ACCOUNTS:
      return getEmail_accounts();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TOTAL:
      return isSetTotal();
    case PAGE_NUMBER:
      return isSetPage_number();
    case PAGE_SIZE:
      return isSetPage_size();
    case COMPANY_ID:
      return isSetCompany_id();
    case EMAIL_ACCOUNTS:
      return isSetEmail_accounts();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof EmailAccountForm)
      return this.equals((EmailAccountForm)that);
    return false;
  }

  public boolean equals(EmailAccountForm that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_total = true && this.isSetTotal();
    boolean that_present_total = true && that.isSetTotal();
    if (this_present_total || that_present_total) {
      if (!(this_present_total && that_present_total))
        return false;
      if (this.total != that.total)
        return false;
    }

    boolean this_present_page_number = true && this.isSetPage_number();
    boolean that_present_page_number = true && that.isSetPage_number();
    if (this_present_page_number || that_present_page_number) {
      if (!(this_present_page_number && that_present_page_number))
        return false;
      if (this.page_number != that.page_number)
        return false;
    }

    boolean this_present_page_size = true && this.isSetPage_size();
    boolean that_present_page_size = true && that.isSetPage_size();
    if (this_present_page_size || that_present_page_size) {
      if (!(this_present_page_size && that_present_page_size))
        return false;
      if (this.page_size != that.page_size)
        return false;
    }

    boolean this_present_company_id = true && this.isSetCompany_id();
    boolean that_present_company_id = true && that.isSetCompany_id();
    if (this_present_company_id || that_present_company_id) {
      if (!(this_present_company_id && that_present_company_id))
        return false;
      if (this.company_id != that.company_id)
        return false;
    }

    boolean this_present_email_accounts = true && this.isSetEmail_accounts();
    boolean that_present_email_accounts = true && that.isSetEmail_accounts();
    if (this_present_email_accounts || that_present_email_accounts) {
      if (!(this_present_email_accounts && that_present_email_accounts))
        return false;
      if (!this.email_accounts.equals(that.email_accounts))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTotal()) ? 131071 : 524287);
    if (isSetTotal())
      hashCode = hashCode * 8191 + total;

    hashCode = hashCode * 8191 + ((isSetPage_number()) ? 131071 : 524287);
    if (isSetPage_number())
      hashCode = hashCode * 8191 + page_number;

    hashCode = hashCode * 8191 + ((isSetPage_size()) ? 131071 : 524287);
    if (isSetPage_size())
      hashCode = hashCode * 8191 + page_size;

    hashCode = hashCode * 8191 + ((isSetCompany_id()) ? 131071 : 524287);
    if (isSetCompany_id())
      hashCode = hashCode * 8191 + company_id;

    hashCode = hashCode * 8191 + ((isSetEmail_accounts()) ? 131071 : 524287);
    if (isSetEmail_accounts())
      hashCode = hashCode * 8191 + email_accounts.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(EmailAccountForm other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTotal()).compareTo(other.isSetTotal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.total, other.total);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPage_number()).compareTo(other.isSetPage_number());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage_number()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page_number, other.page_number);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPage_size()).compareTo(other.isSetPage_size());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage_size()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page_size, other.page_size);
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
    lastComparison = java.lang.Boolean.valueOf(isSetEmail_accounts()).compareTo(other.isSetEmail_accounts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmail_accounts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.email_accounts, other.email_accounts);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("EmailAccountForm(");
    boolean first = true;

    if (isSetTotal()) {
      sb.append("total:");
      sb.append(this.total);
      first = false;
    }
    if (isSetPage_number()) {
      if (!first) sb.append(", ");
      sb.append("page_number:");
      sb.append(this.page_number);
      first = false;
    }
    if (isSetPage_size()) {
      if (!first) sb.append(", ");
      sb.append("page_size:");
      sb.append(this.page_size);
      first = false;
    }
    if (isSetCompany_id()) {
      if (!first) sb.append(", ");
      sb.append("company_id:");
      sb.append(this.company_id);
      first = false;
    }
    if (isSetEmail_accounts()) {
      if (!first) sb.append(", ");
      sb.append("email_accounts:");
      if (this.email_accounts == null) {
        sb.append("null");
      } else {
        sb.append(this.email_accounts);
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

  private static class EmailAccountFormStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EmailAccountFormStandardScheme getScheme() {
      return new EmailAccountFormStandardScheme();
    }
  }

  private static class EmailAccountFormStandardScheme extends org.apache.thrift.scheme.StandardScheme<EmailAccountForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EmailAccountForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.total = iprot.readI32();
              struct.setTotalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAGE_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page_number = iprot.readI32();
              struct.setPage_numberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PAGE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page_size = iprot.readI32();
              struct.setPage_sizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.company_id = iprot.readI32();
              struct.setCompany_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // EMAIL_ACCOUNTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.email_accounts = new java.util.ArrayList<EmailAccountInfo>(_list0.size);
                EmailAccountInfo _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new EmailAccountInfo();
                  _elem1.read(iprot);
                  struct.email_accounts.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setEmail_accountsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EmailAccountForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotal()) {
        oprot.writeFieldBegin(TOTAL_FIELD_DESC);
        oprot.writeI32(struct.total);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPage_number()) {
        oprot.writeFieldBegin(PAGE_NUMBER_FIELD_DESC);
        oprot.writeI32(struct.page_number);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPage_size()) {
        oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
        oprot.writeI32(struct.page_size);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCompany_id()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.company_id);
        oprot.writeFieldEnd();
      }
      if (struct.email_accounts != null) {
        if (struct.isSetEmail_accounts()) {
          oprot.writeFieldBegin(EMAIL_ACCOUNTS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.email_accounts.size()));
            for (EmailAccountInfo _iter3 : struct.email_accounts)
            {
              _iter3.write(oprot);
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

  private static class EmailAccountFormTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EmailAccountFormTupleScheme getScheme() {
      return new EmailAccountFormTupleScheme();
    }
  }

  private static class EmailAccountFormTupleScheme extends org.apache.thrift.scheme.TupleScheme<EmailAccountForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EmailAccountForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTotal()) {
        optionals.set(0);
      }
      if (struct.isSetPage_number()) {
        optionals.set(1);
      }
      if (struct.isSetPage_size()) {
        optionals.set(2);
      }
      if (struct.isSetCompany_id()) {
        optionals.set(3);
      }
      if (struct.isSetEmail_accounts()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTotal()) {
        oprot.writeI32(struct.total);
      }
      if (struct.isSetPage_number()) {
        oprot.writeI32(struct.page_number);
      }
      if (struct.isSetPage_size()) {
        oprot.writeI32(struct.page_size);
      }
      if (struct.isSetCompany_id()) {
        oprot.writeI32(struct.company_id);
      }
      if (struct.isSetEmail_accounts()) {
        {
          oprot.writeI32(struct.email_accounts.size());
          for (EmailAccountInfo _iter4 : struct.email_accounts)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EmailAccountForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.total = iprot.readI32();
        struct.setTotalIsSet(true);
      }
      if (incoming.get(1)) {
        struct.page_number = iprot.readI32();
        struct.setPage_numberIsSet(true);
      }
      if (incoming.get(2)) {
        struct.page_size = iprot.readI32();
        struct.setPage_sizeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.company_id = iprot.readI32();
        struct.setCompany_idIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.email_accounts = new java.util.ArrayList<EmailAccountInfo>(_list5.size);
          EmailAccountInfo _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new EmailAccountInfo();
            _elem6.read(iprot);
            struct.email_accounts.add(_elem6);
          }
        }
        struct.setEmail_accountsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

