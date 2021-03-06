/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.useraccounts.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-11-07")
public class FavPositionForm implements org.apache.thrift.TBase<FavPositionForm, FavPositionForm._Fields>, java.io.Serializable, Cloneable, Comparable<FavPositionForm> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FavPositionForm");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField DEPARTMENT_FIELD_DESC = new org.apache.thrift.protocol.TField("department", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CITY_FIELD_DESC = new org.apache.thrift.protocol.TField("city", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField SALARY_TOP_FIELD_DESC = new org.apache.thrift.protocol.TField("salary_top", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField SALARY_BOTTOM_FIELD_DESC = new org.apache.thrift.protocol.TField("salary_bottom", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.BYTE, (short)8);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)9);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new FavPositionFormStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new FavPositionFormTupleSchemeFactory();

  public int id; // optional
  public java.lang.String title; // optional
  public java.lang.String department; // optional
  public java.lang.String time; // optional
  public java.lang.String city; // optional
  public int salary_top; // optional
  public int salary_bottom; // optional
  public byte status; // optional
  public java.lang.String update_time; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    TITLE((short)2, "title"),
    DEPARTMENT((short)3, "department"),
    TIME((short)4, "time"),
    CITY((short)5, "city"),
    SALARY_TOP((short)6, "salary_top"),
    SALARY_BOTTOM((short)7, "salary_bottom"),
    STATUS((short)8, "status"),
    UPDATE_TIME((short)9, "update_time");

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
        case 2: // TITLE
          return TITLE;
        case 3: // DEPARTMENT
          return DEPARTMENT;
        case 4: // TIME
          return TIME;
        case 5: // CITY
          return CITY;
        case 6: // SALARY_TOP
          return SALARY_TOP;
        case 7: // SALARY_BOTTOM
          return SALARY_BOTTOM;
        case 8: // STATUS
          return STATUS;
        case 9: // UPDATE_TIME
          return UPDATE_TIME;
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
  private static final int __SALARY_TOP_ISSET_ID = 1;
  private static final int __SALARY_BOTTOM_ISSET_ID = 2;
  private static final int __STATUS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.TITLE,_Fields.DEPARTMENT,_Fields.TIME,_Fields.CITY,_Fields.SALARY_TOP,_Fields.SALARY_BOTTOM,_Fields.STATUS,_Fields.UPDATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DEPARTMENT, new org.apache.thrift.meta_data.FieldMetaData("department", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    tmpMap.put(_Fields.CITY, new org.apache.thrift.meta_data.FieldMetaData("city", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SALARY_TOP, new org.apache.thrift.meta_data.FieldMetaData("salary_top", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SALARY_BOTTOM, new org.apache.thrift.meta_data.FieldMetaData("salary_bottom", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Timestamp")));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FavPositionForm.class, metaDataMap);
  }

  public FavPositionForm() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FavPositionForm(FavPositionForm other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    if (other.isSetDepartment()) {
      this.department = other.department;
    }
    if (other.isSetTime()) {
      this.time = other.time;
    }
    if (other.isSetCity()) {
      this.city = other.city;
    }
    this.salary_top = other.salary_top;
    this.salary_bottom = other.salary_bottom;
    this.status = other.status;
    if (other.isSetUpdate_time()) {
      this.update_time = other.update_time;
    }
  }

  public FavPositionForm deepCopy() {
    return new FavPositionForm(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.title = null;
    this.department = null;
    this.time = null;
    this.city = null;
    setSalary_topIsSet(false);
    this.salary_top = 0;
    setSalary_bottomIsSet(false);
    this.salary_bottom = 0;
    setStatusIsSet(false);
    this.status = 0;
    this.update_time = null;
  }

  public int getId() {
    return this.id;
  }

  public FavPositionForm setId(int id) {
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

  public java.lang.String getTitle() {
    return this.title;
  }

  public FavPositionForm setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  public void unsetTitle() {
    this.title = null;
  }

  /** Returns true if field title is set (has been assigned a value) and false otherwise */
  public boolean isSetTitle() {
    return this.title != null;
  }

  public void setTitleIsSet(boolean value) {
    if (!value) {
      this.title = null;
    }
  }

  public java.lang.String getDepartment() {
    return this.department;
  }

  public FavPositionForm setDepartment(java.lang.String department) {
    this.department = department;
    return this;
  }

  public void unsetDepartment() {
    this.department = null;
  }

  /** Returns true if field department is set (has been assigned a value) and false otherwise */
  public boolean isSetDepartment() {
    return this.department != null;
  }

  public void setDepartmentIsSet(boolean value) {
    if (!value) {
      this.department = null;
    }
  }

  public java.lang.String getTime() {
    return this.time;
  }

  public FavPositionForm setTime(java.lang.String time) {
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

  public java.lang.String getCity() {
    return this.city;
  }

  public FavPositionForm setCity(java.lang.String city) {
    this.city = city;
    return this;
  }

  public void unsetCity() {
    this.city = null;
  }

  /** Returns true if field city is set (has been assigned a value) and false otherwise */
  public boolean isSetCity() {
    return this.city != null;
  }

  public void setCityIsSet(boolean value) {
    if (!value) {
      this.city = null;
    }
  }

  public int getSalary_top() {
    return this.salary_top;
  }

  public FavPositionForm setSalary_top(int salary_top) {
    this.salary_top = salary_top;
    setSalary_topIsSet(true);
    return this;
  }

  public void unsetSalary_top() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SALARY_TOP_ISSET_ID);
  }

  /** Returns true if field salary_top is set (has been assigned a value) and false otherwise */
  public boolean isSetSalary_top() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SALARY_TOP_ISSET_ID);
  }

  public void setSalary_topIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SALARY_TOP_ISSET_ID, value);
  }

  public int getSalary_bottom() {
    return this.salary_bottom;
  }

  public FavPositionForm setSalary_bottom(int salary_bottom) {
    this.salary_bottom = salary_bottom;
    setSalary_bottomIsSet(true);
    return this;
  }

  public void unsetSalary_bottom() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SALARY_BOTTOM_ISSET_ID);
  }

  /** Returns true if field salary_bottom is set (has been assigned a value) and false otherwise */
  public boolean isSetSalary_bottom() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SALARY_BOTTOM_ISSET_ID);
  }

  public void setSalary_bottomIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SALARY_BOTTOM_ISSET_ID, value);
  }

  public byte getStatus() {
    return this.status;
  }

  public FavPositionForm setStatus(byte status) {
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

  public java.lang.String getUpdate_time() {
    return this.update_time;
  }

  public FavPositionForm setUpdate_time(java.lang.String update_time) {
    this.update_time = update_time;
    return this;
  }

  public void unsetUpdate_time() {
    this.update_time = null;
  }

  /** Returns true if field update_time is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdate_time() {
    return this.update_time != null;
  }

  public void setUpdate_timeIsSet(boolean value) {
    if (!value) {
      this.update_time = null;
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

    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((java.lang.String)value);
      }
      break;

    case DEPARTMENT:
      if (value == null) {
        unsetDepartment();
      } else {
        setDepartment((java.lang.String)value);
      }
      break;

    case TIME:
      if (value == null) {
        unsetTime();
      } else {
        setTime((java.lang.String)value);
      }
      break;

    case CITY:
      if (value == null) {
        unsetCity();
      } else {
        setCity((java.lang.String)value);
      }
      break;

    case SALARY_TOP:
      if (value == null) {
        unsetSalary_top();
      } else {
        setSalary_top((java.lang.Integer)value);
      }
      break;

    case SALARY_BOTTOM:
      if (value == null) {
        unsetSalary_bottom();
      } else {
        setSalary_bottom((java.lang.Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((java.lang.Byte)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdate_time();
      } else {
        setUpdate_time((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case TITLE:
      return getTitle();

    case DEPARTMENT:
      return getDepartment();

    case TIME:
      return getTime();

    case CITY:
      return getCity();

    case SALARY_TOP:
      return getSalary_top();

    case SALARY_BOTTOM:
      return getSalary_bottom();

    case STATUS:
      return getStatus();

    case UPDATE_TIME:
      return getUpdate_time();

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
    case TITLE:
      return isSetTitle();
    case DEPARTMENT:
      return isSetDepartment();
    case TIME:
      return isSetTime();
    case CITY:
      return isSetCity();
    case SALARY_TOP:
      return isSetSalary_top();
    case SALARY_BOTTOM:
      return isSetSalary_bottom();
    case STATUS:
      return isSetStatus();
    case UPDATE_TIME:
      return isSetUpdate_time();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof FavPositionForm)
      return this.equals((FavPositionForm)that);
    return false;
  }

  public boolean equals(FavPositionForm that) {
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

    boolean this_present_title = true && this.isSetTitle();
    boolean that_present_title = true && that.isSetTitle();
    if (this_present_title || that_present_title) {
      if (!(this_present_title && that_present_title))
        return false;
      if (!this.title.equals(that.title))
        return false;
    }

    boolean this_present_department = true && this.isSetDepartment();
    boolean that_present_department = true && that.isSetDepartment();
    if (this_present_department || that_present_department) {
      if (!(this_present_department && that_present_department))
        return false;
      if (!this.department.equals(that.department))
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

    boolean this_present_city = true && this.isSetCity();
    boolean that_present_city = true && that.isSetCity();
    if (this_present_city || that_present_city) {
      if (!(this_present_city && that_present_city))
        return false;
      if (!this.city.equals(that.city))
        return false;
    }

    boolean this_present_salary_top = true && this.isSetSalary_top();
    boolean that_present_salary_top = true && that.isSetSalary_top();
    if (this_present_salary_top || that_present_salary_top) {
      if (!(this_present_salary_top && that_present_salary_top))
        return false;
      if (this.salary_top != that.salary_top)
        return false;
    }

    boolean this_present_salary_bottom = true && this.isSetSalary_bottom();
    boolean that_present_salary_bottom = true && that.isSetSalary_bottom();
    if (this_present_salary_bottom || that_present_salary_bottom) {
      if (!(this_present_salary_bottom && that_present_salary_bottom))
        return false;
      if (this.salary_bottom != that.salary_bottom)
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    boolean this_present_update_time = true && this.isSetUpdate_time();
    boolean that_present_update_time = true && that.isSetUpdate_time();
    if (this_present_update_time || that_present_update_time) {
      if (!(this_present_update_time && that_present_update_time))
        return false;
      if (!this.update_time.equals(that.update_time))
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

    hashCode = hashCode * 8191 + ((isSetTitle()) ? 131071 : 524287);
    if (isSetTitle())
      hashCode = hashCode * 8191 + title.hashCode();

    hashCode = hashCode * 8191 + ((isSetDepartment()) ? 131071 : 524287);
    if (isSetDepartment())
      hashCode = hashCode * 8191 + department.hashCode();

    hashCode = hashCode * 8191 + ((isSetTime()) ? 131071 : 524287);
    if (isSetTime())
      hashCode = hashCode * 8191 + time.hashCode();

    hashCode = hashCode * 8191 + ((isSetCity()) ? 131071 : 524287);
    if (isSetCity())
      hashCode = hashCode * 8191 + city.hashCode();

    hashCode = hashCode * 8191 + ((isSetSalary_top()) ? 131071 : 524287);
    if (isSetSalary_top())
      hashCode = hashCode * 8191 + salary_top;

    hashCode = hashCode * 8191 + ((isSetSalary_bottom()) ? 131071 : 524287);
    if (isSetSalary_bottom())
      hashCode = hashCode * 8191 + salary_bottom;

    hashCode = hashCode * 8191 + ((isSetStatus()) ? 131071 : 524287);
    if (isSetStatus())
      hashCode = hashCode * 8191 + (int) (status);

    hashCode = hashCode * 8191 + ((isSetUpdate_time()) ? 131071 : 524287);
    if (isSetUpdate_time())
      hashCode = hashCode * 8191 + update_time.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(FavPositionForm other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetTitle()).compareTo(other.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.title, other.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDepartment()).compareTo(other.isSetDepartment());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDepartment()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.department, other.department);
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
    lastComparison = java.lang.Boolean.valueOf(isSetCity()).compareTo(other.isSetCity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.city, other.city);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSalary_top()).compareTo(other.isSetSalary_top());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSalary_top()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.salary_top, other.salary_top);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSalary_bottom()).compareTo(other.isSetSalary_bottom());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSalary_bottom()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.salary_bottom, other.salary_bottom);
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
    lastComparison = java.lang.Boolean.valueOf(isSetUpdate_time()).compareTo(other.isSetUpdate_time());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_time()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_time, other.update_time);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("FavPositionForm(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetTitle()) {
      if (!first) sb.append(", ");
      sb.append("title:");
      if (this.title == null) {
        sb.append("null");
      } else {
        sb.append(this.title);
      }
      first = false;
    }
    if (isSetDepartment()) {
      if (!first) sb.append(", ");
      sb.append("department:");
      if (this.department == null) {
        sb.append("null");
      } else {
        sb.append(this.department);
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
    if (isSetCity()) {
      if (!first) sb.append(", ");
      sb.append("city:");
      if (this.city == null) {
        sb.append("null");
      } else {
        sb.append(this.city);
      }
      first = false;
    }
    if (isSetSalary_top()) {
      if (!first) sb.append(", ");
      sb.append("salary_top:");
      sb.append(this.salary_top);
      first = false;
    }
    if (isSetSalary_bottom()) {
      if (!first) sb.append(", ");
      sb.append("salary_bottom:");
      sb.append(this.salary_bottom);
      first = false;
    }
    if (isSetStatus()) {
      if (!first) sb.append(", ");
      sb.append("status:");
      sb.append(this.status);
      first = false;
    }
    if (isSetUpdate_time()) {
      if (!first) sb.append(", ");
      sb.append("update_time:");
      if (this.update_time == null) {
        sb.append("null");
      } else {
        sb.append(this.update_time);
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

  private static class FavPositionFormStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public FavPositionFormStandardScheme getScheme() {
      return new FavPositionFormStandardScheme();
    }
  }

  private static class FavPositionFormStandardScheme extends org.apache.thrift.scheme.StandardScheme<FavPositionForm> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FavPositionForm struct) throws org.apache.thrift.TException {
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
          case 2: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.title = iprot.readString();
              struct.setTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DEPARTMENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.department = iprot.readString();
              struct.setDepartmentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.time = iprot.readString();
              struct.setTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CITY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.city = iprot.readString();
              struct.setCityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SALARY_TOP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.salary_top = iprot.readI32();
              struct.setSalary_topIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SALARY_BOTTOM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.salary_bottom = iprot.readI32();
              struct.setSalary_bottomIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.status = iprot.readByte();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.update_time = iprot.readString();
              struct.setUpdate_timeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, FavPositionForm struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.title != null) {
        if (struct.isSetTitle()) {
          oprot.writeFieldBegin(TITLE_FIELD_DESC);
          oprot.writeString(struct.title);
          oprot.writeFieldEnd();
        }
      }
      if (struct.department != null) {
        if (struct.isSetDepartment()) {
          oprot.writeFieldBegin(DEPARTMENT_FIELD_DESC);
          oprot.writeString(struct.department);
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
      if (struct.city != null) {
        if (struct.isSetCity()) {
          oprot.writeFieldBegin(CITY_FIELD_DESC);
          oprot.writeString(struct.city);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSalary_top()) {
        oprot.writeFieldBegin(SALARY_TOP_FIELD_DESC);
        oprot.writeI32(struct.salary_top);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSalary_bottom()) {
        oprot.writeFieldBegin(SALARY_BOTTOM_FIELD_DESC);
        oprot.writeI32(struct.salary_bottom);
        oprot.writeFieldEnd();
      }
      if (struct.isSetStatus()) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeByte(struct.status);
        oprot.writeFieldEnd();
      }
      if (struct.update_time != null) {
        if (struct.isSetUpdate_time()) {
          oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
          oprot.writeString(struct.update_time);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FavPositionFormTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public FavPositionFormTupleScheme getScheme() {
      return new FavPositionFormTupleScheme();
    }
  }

  private static class FavPositionFormTupleScheme extends org.apache.thrift.scheme.TupleScheme<FavPositionForm> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FavPositionForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetTitle()) {
        optionals.set(1);
      }
      if (struct.isSetDepartment()) {
        optionals.set(2);
      }
      if (struct.isSetTime()) {
        optionals.set(3);
      }
      if (struct.isSetCity()) {
        optionals.set(4);
      }
      if (struct.isSetSalary_top()) {
        optionals.set(5);
      }
      if (struct.isSetSalary_bottom()) {
        optionals.set(6);
      }
      if (struct.isSetStatus()) {
        optionals.set(7);
      }
      if (struct.isSetUpdate_time()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetTitle()) {
        oprot.writeString(struct.title);
      }
      if (struct.isSetDepartment()) {
        oprot.writeString(struct.department);
      }
      if (struct.isSetTime()) {
        oprot.writeString(struct.time);
      }
      if (struct.isSetCity()) {
        oprot.writeString(struct.city);
      }
      if (struct.isSetSalary_top()) {
        oprot.writeI32(struct.salary_top);
      }
      if (struct.isSetSalary_bottom()) {
        oprot.writeI32(struct.salary_bottom);
      }
      if (struct.isSetStatus()) {
        oprot.writeByte(struct.status);
      }
      if (struct.isSetUpdate_time()) {
        oprot.writeString(struct.update_time);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FavPositionForm struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.title = iprot.readString();
        struct.setTitleIsSet(true);
      }
      if (incoming.get(2)) {
        struct.department = iprot.readString();
        struct.setDepartmentIsSet(true);
      }
      if (incoming.get(3)) {
        struct.time = iprot.readString();
        struct.setTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.city = iprot.readString();
        struct.setCityIsSet(true);
      }
      if (incoming.get(5)) {
        struct.salary_top = iprot.readI32();
        struct.setSalary_topIsSet(true);
      }
      if (incoming.get(6)) {
        struct.salary_bottom = iprot.readI32();
        struct.setSalary_bottomIsSet(true);
      }
      if (incoming.get(7)) {
        struct.status = iprot.readByte();
        struct.setStatusIsSet(true);
      }
      if (incoming.get(8)) {
        struct.update_time = iprot.readString();
        struct.setUpdate_timeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

