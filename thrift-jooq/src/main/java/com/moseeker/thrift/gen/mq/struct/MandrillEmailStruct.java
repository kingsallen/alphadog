/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.mq.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2019-10-31")
public class MandrillEmailStruct implements org.apache.thrift.TBase<MandrillEmailStruct, MandrillEmailStruct._Fields>, java.io.Serializable, Cloneable, Comparable<MandrillEmailStruct> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MandrillEmailStruct");

  private static final org.apache.thrift.protocol.TField TEMPLATE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("templateName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TO_EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("to_email", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TO_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("to_name", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField MERGE_VARS_FIELD_DESC = new org.apache.thrift.protocol.TField("mergeVars", org.apache.thrift.protocol.TType.MAP, (short)4);
  private static final org.apache.thrift.protocol.TField FROM_EMAIL_FIELD_DESC = new org.apache.thrift.protocol.TField("from_email", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField FROM_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("from_name", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField SUBJECT_FIELD_DESC = new org.apache.thrift.protocol.TField("subject", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MandrillEmailStructStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MandrillEmailStructTupleSchemeFactory();

  public java.lang.String templateName; // required
  public java.lang.String to_email; // required
  public java.lang.String to_name; // optional
  public java.util.Map<java.lang.String,java.lang.String> mergeVars; // optional
  public java.lang.String from_email; // optional
  public java.lang.String from_name; // optional
  public java.lang.String subject; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TEMPLATE_NAME((short)1, "templateName"),
    TO_EMAIL((short)2, "to_email"),
    TO_NAME((short)3, "to_name"),
    MERGE_VARS((short)4, "mergeVars"),
    FROM_EMAIL((short)5, "from_email"),
    FROM_NAME((short)6, "from_name"),
    SUBJECT((short)7, "subject");

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
        case 1: // TEMPLATE_NAME
          return TEMPLATE_NAME;
        case 2: // TO_EMAIL
          return TO_EMAIL;
        case 3: // TO_NAME
          return TO_NAME;
        case 4: // MERGE_VARS
          return MERGE_VARS;
        case 5: // FROM_EMAIL
          return FROM_EMAIL;
        case 6: // FROM_NAME
          return FROM_NAME;
        case 7: // SUBJECT
          return SUBJECT;
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
  private static final _Fields optionals[] = {_Fields.TO_NAME,_Fields.MERGE_VARS,_Fields.FROM_EMAIL,_Fields.FROM_NAME,_Fields.SUBJECT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TEMPLATE_NAME, new org.apache.thrift.meta_data.FieldMetaData("templateName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TO_EMAIL, new org.apache.thrift.meta_data.FieldMetaData("to_email", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TO_NAME, new org.apache.thrift.meta_data.FieldMetaData("to_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MERGE_VARS, new org.apache.thrift.meta_data.FieldMetaData("mergeVars", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.FROM_EMAIL, new org.apache.thrift.meta_data.FieldMetaData("from_email", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FROM_NAME, new org.apache.thrift.meta_data.FieldMetaData("from_name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUBJECT, new org.apache.thrift.meta_data.FieldMetaData("subject", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MandrillEmailStruct.class, metaDataMap);
  }

  public MandrillEmailStruct() {
  }

  public MandrillEmailStruct(
    java.lang.String templateName,
    java.lang.String to_email)
  {
    this();
    this.templateName = templateName;
    this.to_email = to_email;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MandrillEmailStruct(MandrillEmailStruct other) {
    if (other.isSetTemplateName()) {
      this.templateName = other.templateName;
    }
    if (other.isSetTo_email()) {
      this.to_email = other.to_email;
    }
    if (other.isSetTo_name()) {
      this.to_name = other.to_name;
    }
    if (other.isSetMergeVars()) {
      java.util.Map<java.lang.String,java.lang.String> __this__mergeVars = new java.util.HashMap<java.lang.String,java.lang.String>(other.mergeVars);
      this.mergeVars = __this__mergeVars;
    }
    if (other.isSetFrom_email()) {
      this.from_email = other.from_email;
    }
    if (other.isSetFrom_name()) {
      this.from_name = other.from_name;
    }
    if (other.isSetSubject()) {
      this.subject = other.subject;
    }
  }

  public MandrillEmailStruct deepCopy() {
    return new MandrillEmailStruct(this);
  }

  @Override
  public void clear() {
    this.templateName = null;
    this.to_email = null;
    this.to_name = null;
    this.mergeVars = null;
    this.from_email = null;
    this.from_name = null;
    this.subject = null;
  }

  public java.lang.String getTemplateName() {
    return this.templateName;
  }

  public MandrillEmailStruct setTemplateName(java.lang.String templateName) {
    this.templateName = templateName;
    return this;
  }

  public void unsetTemplateName() {
    this.templateName = null;
  }

  /** Returns true if field templateName is set (has been assigned a value) and false otherwise */
  public boolean isSetTemplateName() {
    return this.templateName != null;
  }

  public void setTemplateNameIsSet(boolean value) {
    if (!value) {
      this.templateName = null;
    }
  }

  public java.lang.String getTo_email() {
    return this.to_email;
  }

  public MandrillEmailStruct setTo_email(java.lang.String to_email) {
    this.to_email = to_email;
    return this;
  }

  public void unsetTo_email() {
    this.to_email = null;
  }

  /** Returns true if field to_email is set (has been assigned a value) and false otherwise */
  public boolean isSetTo_email() {
    return this.to_email != null;
  }

  public void setTo_emailIsSet(boolean value) {
    if (!value) {
      this.to_email = null;
    }
  }

  public java.lang.String getTo_name() {
    return this.to_name;
  }

  public MandrillEmailStruct setTo_name(java.lang.String to_name) {
    this.to_name = to_name;
    return this;
  }

  public void unsetTo_name() {
    this.to_name = null;
  }

  /** Returns true if field to_name is set (has been assigned a value) and false otherwise */
  public boolean isSetTo_name() {
    return this.to_name != null;
  }

  public void setTo_nameIsSet(boolean value) {
    if (!value) {
      this.to_name = null;
    }
  }

  public int getMergeVarsSize() {
    return (this.mergeVars == null) ? 0 : this.mergeVars.size();
  }

  public void putToMergeVars(java.lang.String key, java.lang.String val) {
    if (this.mergeVars == null) {
      this.mergeVars = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.mergeVars.put(key, val);
  }

  public java.util.Map<java.lang.String,java.lang.String> getMergeVars() {
    return this.mergeVars;
  }

  public MandrillEmailStruct setMergeVars(java.util.Map<java.lang.String,java.lang.String> mergeVars) {
    this.mergeVars = mergeVars;
    return this;
  }

  public void unsetMergeVars() {
    this.mergeVars = null;
  }

  /** Returns true if field mergeVars is set (has been assigned a value) and false otherwise */
  public boolean isSetMergeVars() {
    return this.mergeVars != null;
  }

  public void setMergeVarsIsSet(boolean value) {
    if (!value) {
      this.mergeVars = null;
    }
  }

  public java.lang.String getFrom_email() {
    return this.from_email;
  }

  public MandrillEmailStruct setFrom_email(java.lang.String from_email) {
    this.from_email = from_email;
    return this;
  }

  public void unsetFrom_email() {
    this.from_email = null;
  }

  /** Returns true if field from_email is set (has been assigned a value) and false otherwise */
  public boolean isSetFrom_email() {
    return this.from_email != null;
  }

  public void setFrom_emailIsSet(boolean value) {
    if (!value) {
      this.from_email = null;
    }
  }

  public java.lang.String getFrom_name() {
    return this.from_name;
  }

  public MandrillEmailStruct setFrom_name(java.lang.String from_name) {
    this.from_name = from_name;
    return this;
  }

  public void unsetFrom_name() {
    this.from_name = null;
  }

  /** Returns true if field from_name is set (has been assigned a value) and false otherwise */
  public boolean isSetFrom_name() {
    return this.from_name != null;
  }

  public void setFrom_nameIsSet(boolean value) {
    if (!value) {
      this.from_name = null;
    }
  }

  public java.lang.String getSubject() {
    return this.subject;
  }

  public MandrillEmailStruct setSubject(java.lang.String subject) {
    this.subject = subject;
    return this;
  }

  public void unsetSubject() {
    this.subject = null;
  }

  /** Returns true if field subject is set (has been assigned a value) and false otherwise */
  public boolean isSetSubject() {
    return this.subject != null;
  }

  public void setSubjectIsSet(boolean value) {
    if (!value) {
      this.subject = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TEMPLATE_NAME:
      if (value == null) {
        unsetTemplateName();
      } else {
        setTemplateName((java.lang.String)value);
      }
      break;

    case TO_EMAIL:
      if (value == null) {
        unsetTo_email();
      } else {
        setTo_email((java.lang.String)value);
      }
      break;

    case TO_NAME:
      if (value == null) {
        unsetTo_name();
      } else {
        setTo_name((java.lang.String)value);
      }
      break;

    case MERGE_VARS:
      if (value == null) {
        unsetMergeVars();
      } else {
        setMergeVars((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    case FROM_EMAIL:
      if (value == null) {
        unsetFrom_email();
      } else {
        setFrom_email((java.lang.String)value);
      }
      break;

    case FROM_NAME:
      if (value == null) {
        unsetFrom_name();
      } else {
        setFrom_name((java.lang.String)value);
      }
      break;

    case SUBJECT:
      if (value == null) {
        unsetSubject();
      } else {
        setSubject((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TEMPLATE_NAME:
      return getTemplateName();

    case TO_EMAIL:
      return getTo_email();

    case TO_NAME:
      return getTo_name();

    case MERGE_VARS:
      return getMergeVars();

    case FROM_EMAIL:
      return getFrom_email();

    case FROM_NAME:
      return getFrom_name();

    case SUBJECT:
      return getSubject();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TEMPLATE_NAME:
      return isSetTemplateName();
    case TO_EMAIL:
      return isSetTo_email();
    case TO_NAME:
      return isSetTo_name();
    case MERGE_VARS:
      return isSetMergeVars();
    case FROM_EMAIL:
      return isSetFrom_email();
    case FROM_NAME:
      return isSetFrom_name();
    case SUBJECT:
      return isSetSubject();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MandrillEmailStruct)
      return this.equals((MandrillEmailStruct)that);
    return false;
  }

  public boolean equals(MandrillEmailStruct that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_templateName = true && this.isSetTemplateName();
    boolean that_present_templateName = true && that.isSetTemplateName();
    if (this_present_templateName || that_present_templateName) {
      if (!(this_present_templateName && that_present_templateName))
        return false;
      if (!this.templateName.equals(that.templateName))
        return false;
    }

    boolean this_present_to_email = true && this.isSetTo_email();
    boolean that_present_to_email = true && that.isSetTo_email();
    if (this_present_to_email || that_present_to_email) {
      if (!(this_present_to_email && that_present_to_email))
        return false;
      if (!this.to_email.equals(that.to_email))
        return false;
    }

    boolean this_present_to_name = true && this.isSetTo_name();
    boolean that_present_to_name = true && that.isSetTo_name();
    if (this_present_to_name || that_present_to_name) {
      if (!(this_present_to_name && that_present_to_name))
        return false;
      if (!this.to_name.equals(that.to_name))
        return false;
    }

    boolean this_present_mergeVars = true && this.isSetMergeVars();
    boolean that_present_mergeVars = true && that.isSetMergeVars();
    if (this_present_mergeVars || that_present_mergeVars) {
      if (!(this_present_mergeVars && that_present_mergeVars))
        return false;
      if (!this.mergeVars.equals(that.mergeVars))
        return false;
    }

    boolean this_present_from_email = true && this.isSetFrom_email();
    boolean that_present_from_email = true && that.isSetFrom_email();
    if (this_present_from_email || that_present_from_email) {
      if (!(this_present_from_email && that_present_from_email))
        return false;
      if (!this.from_email.equals(that.from_email))
        return false;
    }

    boolean this_present_from_name = true && this.isSetFrom_name();
    boolean that_present_from_name = true && that.isSetFrom_name();
    if (this_present_from_name || that_present_from_name) {
      if (!(this_present_from_name && that_present_from_name))
        return false;
      if (!this.from_name.equals(that.from_name))
        return false;
    }

    boolean this_present_subject = true && this.isSetSubject();
    boolean that_present_subject = true && that.isSetSubject();
    if (this_present_subject || that_present_subject) {
      if (!(this_present_subject && that_present_subject))
        return false;
      if (!this.subject.equals(that.subject))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTemplateName()) ? 131071 : 524287);
    if (isSetTemplateName())
      hashCode = hashCode * 8191 + templateName.hashCode();

    hashCode = hashCode * 8191 + ((isSetTo_email()) ? 131071 : 524287);
    if (isSetTo_email())
      hashCode = hashCode * 8191 + to_email.hashCode();

    hashCode = hashCode * 8191 + ((isSetTo_name()) ? 131071 : 524287);
    if (isSetTo_name())
      hashCode = hashCode * 8191 + to_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetMergeVars()) ? 131071 : 524287);
    if (isSetMergeVars())
      hashCode = hashCode * 8191 + mergeVars.hashCode();

    hashCode = hashCode * 8191 + ((isSetFrom_email()) ? 131071 : 524287);
    if (isSetFrom_email())
      hashCode = hashCode * 8191 + from_email.hashCode();

    hashCode = hashCode * 8191 + ((isSetFrom_name()) ? 131071 : 524287);
    if (isSetFrom_name())
      hashCode = hashCode * 8191 + from_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetSubject()) ? 131071 : 524287);
    if (isSetSubject())
      hashCode = hashCode * 8191 + subject.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(MandrillEmailStruct other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTemplateName()).compareTo(other.isSetTemplateName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTemplateName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.templateName, other.templateName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTo_email()).compareTo(other.isSetTo_email());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTo_email()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.to_email, other.to_email);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTo_name()).compareTo(other.isSetTo_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTo_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.to_name, other.to_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMergeVars()).compareTo(other.isSetMergeVars());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMergeVars()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mergeVars, other.mergeVars);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFrom_email()).compareTo(other.isSetFrom_email());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFrom_email()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.from_email, other.from_email);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFrom_name()).compareTo(other.isSetFrom_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFrom_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.from_name, other.from_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSubject()).compareTo(other.isSetSubject());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubject()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subject, other.subject);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MandrillEmailStruct(");
    boolean first = true;

    sb.append("templateName:");
    if (this.templateName == null) {
      sb.append("null");
    } else {
      sb.append(this.templateName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("to_email:");
    if (this.to_email == null) {
      sb.append("null");
    } else {
      sb.append(this.to_email);
    }
    first = false;
    if (isSetTo_name()) {
      if (!first) sb.append(", ");
      sb.append("to_name:");
      if (this.to_name == null) {
        sb.append("null");
      } else {
        sb.append(this.to_name);
      }
      first = false;
    }
    if (isSetMergeVars()) {
      if (!first) sb.append(", ");
      sb.append("mergeVars:");
      if (this.mergeVars == null) {
        sb.append("null");
      } else {
        sb.append(this.mergeVars);
      }
      first = false;
    }
    if (isSetFrom_email()) {
      if (!first) sb.append(", ");
      sb.append("from_email:");
      if (this.from_email == null) {
        sb.append("null");
      } else {
        sb.append(this.from_email);
      }
      first = false;
    }
    if (isSetFrom_name()) {
      if (!first) sb.append(", ");
      sb.append("from_name:");
      if (this.from_name == null) {
        sb.append("null");
      } else {
        sb.append(this.from_name);
      }
      first = false;
    }
    if (isSetSubject()) {
      if (!first) sb.append(", ");
      sb.append("subject:");
      if (this.subject == null) {
        sb.append("null");
      } else {
        sb.append(this.subject);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MandrillEmailStructStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MandrillEmailStructStandardScheme getScheme() {
      return new MandrillEmailStructStandardScheme();
    }
  }

  private static class MandrillEmailStructStandardScheme extends org.apache.thrift.scheme.StandardScheme<MandrillEmailStruct> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MandrillEmailStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TEMPLATE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.templateName = iprot.readString();
              struct.setTemplateNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TO_EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.to_email = iprot.readString();
              struct.setTo_emailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TO_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.to_name = iprot.readString();
              struct.setTo_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MERGE_VARS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map10 = iprot.readMapBegin();
                struct.mergeVars = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map10.size);
                java.lang.String _key11;
                java.lang.String _val12;
                for (int _i13 = 0; _i13 < _map10.size; ++_i13)
                {
                  _key11 = iprot.readString();
                  _val12 = iprot.readString();
                  struct.mergeVars.put(_key11, _val12);
                }
                iprot.readMapEnd();
              }
              struct.setMergeVarsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FROM_EMAIL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.from_email = iprot.readString();
              struct.setFrom_emailIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // FROM_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.from_name = iprot.readString();
              struct.setFrom_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SUBJECT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.subject = iprot.readString();
              struct.setSubjectIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MandrillEmailStruct struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.templateName != null) {
        oprot.writeFieldBegin(TEMPLATE_NAME_FIELD_DESC);
        oprot.writeString(struct.templateName);
        oprot.writeFieldEnd();
      }
      if (struct.to_email != null) {
        oprot.writeFieldBegin(TO_EMAIL_FIELD_DESC);
        oprot.writeString(struct.to_email);
        oprot.writeFieldEnd();
      }
      if (struct.to_name != null) {
        if (struct.isSetTo_name()) {
          oprot.writeFieldBegin(TO_NAME_FIELD_DESC);
          oprot.writeString(struct.to_name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.mergeVars != null) {
        if (struct.isSetMergeVars()) {
          oprot.writeFieldBegin(MERGE_VARS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.mergeVars.size()));
            for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter14 : struct.mergeVars.entrySet())
            {
              oprot.writeString(_iter14.getKey());
              oprot.writeString(_iter14.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.from_email != null) {
        if (struct.isSetFrom_email()) {
          oprot.writeFieldBegin(FROM_EMAIL_FIELD_DESC);
          oprot.writeString(struct.from_email);
          oprot.writeFieldEnd();
        }
      }
      if (struct.from_name != null) {
        if (struct.isSetFrom_name()) {
          oprot.writeFieldBegin(FROM_NAME_FIELD_DESC);
          oprot.writeString(struct.from_name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.subject != null) {
        if (struct.isSetSubject()) {
          oprot.writeFieldBegin(SUBJECT_FIELD_DESC);
          oprot.writeString(struct.subject);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MandrillEmailStructTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MandrillEmailStructTupleScheme getScheme() {
      return new MandrillEmailStructTupleScheme();
    }
  }

  private static class MandrillEmailStructTupleScheme extends org.apache.thrift.scheme.TupleScheme<MandrillEmailStruct> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MandrillEmailStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTemplateName()) {
        optionals.set(0);
      }
      if (struct.isSetTo_email()) {
        optionals.set(1);
      }
      if (struct.isSetTo_name()) {
        optionals.set(2);
      }
      if (struct.isSetMergeVars()) {
        optionals.set(3);
      }
      if (struct.isSetFrom_email()) {
        optionals.set(4);
      }
      if (struct.isSetFrom_name()) {
        optionals.set(5);
      }
      if (struct.isSetSubject()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetTemplateName()) {
        oprot.writeString(struct.templateName);
      }
      if (struct.isSetTo_email()) {
        oprot.writeString(struct.to_email);
      }
      if (struct.isSetTo_name()) {
        oprot.writeString(struct.to_name);
      }
      if (struct.isSetMergeVars()) {
        {
          oprot.writeI32(struct.mergeVars.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter15 : struct.mergeVars.entrySet())
          {
            oprot.writeString(_iter15.getKey());
            oprot.writeString(_iter15.getValue());
          }
        }
      }
      if (struct.isSetFrom_email()) {
        oprot.writeString(struct.from_email);
      }
      if (struct.isSetFrom_name()) {
        oprot.writeString(struct.from_name);
      }
      if (struct.isSetSubject()) {
        oprot.writeString(struct.subject);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MandrillEmailStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.templateName = iprot.readString();
        struct.setTemplateNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.to_email = iprot.readString();
        struct.setTo_emailIsSet(true);
      }
      if (incoming.get(2)) {
        struct.to_name = iprot.readString();
        struct.setTo_nameIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map16 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.mergeVars = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map16.size);
          java.lang.String _key17;
          java.lang.String _val18;
          for (int _i19 = 0; _i19 < _map16.size; ++_i19)
          {
            _key17 = iprot.readString();
            _val18 = iprot.readString();
            struct.mergeVars.put(_key17, _val18);
          }
        }
        struct.setMergeVarsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.from_email = iprot.readString();
        struct.setFrom_emailIsSet(true);
      }
      if (incoming.get(5)) {
        struct.from_name = iprot.readString();
        struct.setFrom_nameIsSet(true);
      }
      if (incoming.get(6)) {
        struct.subject = iprot.readString();
        struct.setSubjectIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

