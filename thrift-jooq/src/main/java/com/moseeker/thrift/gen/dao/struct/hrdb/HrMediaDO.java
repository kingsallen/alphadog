/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.dao.struct.hrdb;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-04")
public class HrMediaDO implements org.apache.thrift.TBase<HrMediaDO, HrMediaDO._Fields>, java.io.Serializable, Cloneable, Comparable<HrMediaDO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HrMediaDO");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LONGTEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("longtext", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ATTRS_FIELD_DESC = new org.apache.thrift.protocol.TField("attrs", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField SUB_TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("subTitle", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField LINK_FIELD_DESC = new org.apache.thrift.protocol.TField("link", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField RES_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("resId", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HrMediaDOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HrMediaDOTupleSchemeFactory();

  public int id; // optional
  public String longtext; // optional
  public String attrs; // optional
  public String title; // optional
  public String subTitle; // optional
  public String link; // optional
  public int resId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    LONGTEXT((short)2, "longtext"),
    ATTRS((short)3, "attrs"),
    TITLE((short)4, "title"),
    SUB_TITLE((short)5, "subTitle"),
    LINK((short)6, "link"),
    RES_ID((short)7, "resId");

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
        case 1: // ID
          return ID;
        case 2: // LONGTEXT
          return LONGTEXT;
        case 3: // ATTRS
          return ATTRS;
        case 4: // TITLE
          return TITLE;
        case 5: // SUB_TITLE
          return SUB_TITLE;
        case 6: // LINK
          return LINK;
        case 7: // RES_ID
          return RES_ID;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __RESID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ID,_Fields.LONGTEXT,_Fields.ATTRS,_Fields.TITLE,_Fields.SUB_TITLE,_Fields.LINK,_Fields.RES_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LONGTEXT, new org.apache.thrift.meta_data.FieldMetaData("longtext", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ATTRS, new org.apache.thrift.meta_data.FieldMetaData("attrs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUB_TITLE, new org.apache.thrift.meta_data.FieldMetaData("subTitle", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LINK, new org.apache.thrift.meta_data.FieldMetaData("link", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RES_ID, new org.apache.thrift.meta_data.FieldMetaData("resId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HrMediaDO.class, metaDataMap);
  }

  public HrMediaDO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HrMediaDO(HrMediaDO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetLongtext()) {
      this.longtext = other.longtext;
    }
    if (other.isSetAttrs()) {
      this.attrs = other.attrs;
    }
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    if (other.isSetSubTitle()) {
      this.subTitle = other.subTitle;
    }
    if (other.isSetLink()) {
      this.link = other.link;
    }
    this.resId = other.resId;
  }

  public HrMediaDO deepCopy() {
    return new HrMediaDO(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.longtext = null;
    this.attrs = null;
    this.title = null;
    this.subTitle = null;
    this.link = null;
    setResIdIsSet(false);
    this.resId = 0;
  }

  public int getId() {
    return this.id;
  }

  public HrMediaDO setId(int id) {
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

  public String getLongtext() {
    return this.longtext;
  }

  public HrMediaDO setLongtext(String longtext) {
    this.longtext = longtext;
    return this;
  }

  public void unsetLongtext() {
    this.longtext = null;
  }

  /** Returns true if field longtext is set (has been assigned a value) and false otherwise */
  public boolean isSetLongtext() {
    return this.longtext != null;
  }

  public void setLongtextIsSet(boolean value) {
    if (!value) {
      this.longtext = null;
    }
  }

  public String getAttrs() {
    return this.attrs;
  }

  public HrMediaDO setAttrs(String attrs) {
    this.attrs = attrs;
    return this;
  }

  public void unsetAttrs() {
    this.attrs = null;
  }

  /** Returns true if field attrs is set (has been assigned a value) and false otherwise */
  public boolean isSetAttrs() {
    return this.attrs != null;
  }

  public void setAttrsIsSet(boolean value) {
    if (!value) {
      this.attrs = null;
    }
  }

  public String getTitle() {
    return this.title;
  }

  public HrMediaDO setTitle(String title) {
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

  public String getSubTitle() {
    return this.subTitle;
  }

  public HrMediaDO setSubTitle(String subTitle) {
    this.subTitle = subTitle;
    return this;
  }

  public void unsetSubTitle() {
    this.subTitle = null;
  }

  /** Returns true if field subTitle is set (has been assigned a value) and false otherwise */
  public boolean isSetSubTitle() {
    return this.subTitle != null;
  }

  public void setSubTitleIsSet(boolean value) {
    if (!value) {
      this.subTitle = null;
    }
  }

  public String getLink() {
    return this.link;
  }

  public HrMediaDO setLink(String link) {
    this.link = link;
    return this;
  }

  public void unsetLink() {
    this.link = null;
  }

  /** Returns true if field link is set (has been assigned a value) and false otherwise */
  public boolean isSetLink() {
    return this.link != null;
  }

  public void setLinkIsSet(boolean value) {
    if (!value) {
      this.link = null;
    }
  }

  public int getResId() {
    return this.resId;
  }

  public HrMediaDO setResId(int resId) {
    this.resId = resId;
    setResIdIsSet(true);
    return this;
  }

  public void unsetResId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RESID_ISSET_ID);
  }

  /** Returns true if field resId is set (has been assigned a value) and false otherwise */
  public boolean isSetResId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RESID_ISSET_ID);
  }

  public void setResIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RESID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case LONGTEXT:
      if (value == null) {
        unsetLongtext();
      } else {
        setLongtext((String)value);
      }
      break;

    case ATTRS:
      if (value == null) {
        unsetAttrs();
      } else {
        setAttrs((String)value);
      }
      break;

    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((String)value);
      }
      break;

    case SUB_TITLE:
      if (value == null) {
        unsetSubTitle();
      } else {
        setSubTitle((String)value);
      }
      break;

    case LINK:
      if (value == null) {
        unsetLink();
      } else {
        setLink((String)value);
      }
      break;

    case RES_ID:
      if (value == null) {
        unsetResId();
      } else {
        setResId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case LONGTEXT:
      return getLongtext();

    case ATTRS:
      return getAttrs();

    case TITLE:
      return getTitle();

    case SUB_TITLE:
      return getSubTitle();

    case LINK:
      return getLink();

    case RES_ID:
      return getResId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case LONGTEXT:
      return isSetLongtext();
    case ATTRS:
      return isSetAttrs();
    case TITLE:
      return isSetTitle();
    case SUB_TITLE:
      return isSetSubTitle();
    case LINK:
      return isSetLink();
    case RES_ID:
      return isSetResId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HrMediaDO)
      return this.equals((HrMediaDO)that);
    return false;
  }

  public boolean equals(HrMediaDO that) {
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

    boolean this_present_longtext = true && this.isSetLongtext();
    boolean that_present_longtext = true && that.isSetLongtext();
    if (this_present_longtext || that_present_longtext) {
      if (!(this_present_longtext && that_present_longtext))
        return false;
      if (!this.longtext.equals(that.longtext))
        return false;
    }

    boolean this_present_attrs = true && this.isSetAttrs();
    boolean that_present_attrs = true && that.isSetAttrs();
    if (this_present_attrs || that_present_attrs) {
      if (!(this_present_attrs && that_present_attrs))
        return false;
      if (!this.attrs.equals(that.attrs))
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

    boolean this_present_subTitle = true && this.isSetSubTitle();
    boolean that_present_subTitle = true && that.isSetSubTitle();
    if (this_present_subTitle || that_present_subTitle) {
      if (!(this_present_subTitle && that_present_subTitle))
        return false;
      if (!this.subTitle.equals(that.subTitle))
        return false;
    }

    boolean this_present_link = true && this.isSetLink();
    boolean that_present_link = true && that.isSetLink();
    if (this_present_link || that_present_link) {
      if (!(this_present_link && that_present_link))
        return false;
      if (!this.link.equals(that.link))
        return false;
    }

    boolean this_present_resId = true && this.isSetResId();
    boolean that_present_resId = true && that.isSetResId();
    if (this_present_resId || that_present_resId) {
      if (!(this_present_resId && that_present_resId))
        return false;
      if (this.resId != that.resId)
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

    hashCode = hashCode * 8191 + ((isSetLongtext()) ? 131071 : 524287);
    if (isSetLongtext())
      hashCode = hashCode * 8191 + longtext.hashCode();

    hashCode = hashCode * 8191 + ((isSetAttrs()) ? 131071 : 524287);
    if (isSetAttrs())
      hashCode = hashCode * 8191 + attrs.hashCode();

    hashCode = hashCode * 8191 + ((isSetTitle()) ? 131071 : 524287);
    if (isSetTitle())
      hashCode = hashCode * 8191 + title.hashCode();

    hashCode = hashCode * 8191 + ((isSetSubTitle()) ? 131071 : 524287);
    if (isSetSubTitle())
      hashCode = hashCode * 8191 + subTitle.hashCode();

    hashCode = hashCode * 8191 + ((isSetLink()) ? 131071 : 524287);
    if (isSetLink())
      hashCode = hashCode * 8191 + link.hashCode();

    hashCode = hashCode * 8191 + ((isSetResId()) ? 131071 : 524287);
    if (isSetResId())
      hashCode = hashCode * 8191 + resId;

    return hashCode;
  }

  @Override
  public int compareTo(HrMediaDO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLongtext()).compareTo(other.isSetLongtext());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLongtext()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.longtext, other.longtext);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAttrs()).compareTo(other.isSetAttrs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAttrs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.attrs, other.attrs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTitle()).compareTo(other.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.title, other.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubTitle()).compareTo(other.isSetSubTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subTitle, other.subTitle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLink()).compareTo(other.isSetLink());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLink()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.link, other.link);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResId()).compareTo(other.isSetResId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resId, other.resId);
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
    StringBuilder sb = new StringBuilder("HrMediaDO(");
    boolean first = true;

    if (isSetId()) {
      sb.append("id:");
      sb.append(this.id);
      first = false;
    }
    if (isSetLongtext()) {
      if (!first) sb.append(", ");
      sb.append("longtext:");
      if (this.longtext == null) {
        sb.append("null");
      } else {
        sb.append(this.longtext);
      }
      first = false;
    }
    if (isSetAttrs()) {
      if (!first) sb.append(", ");
      sb.append("attrs:");
      if (this.attrs == null) {
        sb.append("null");
      } else {
        sb.append(this.attrs);
      }
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
    if (isSetSubTitle()) {
      if (!first) sb.append(", ");
      sb.append("subTitle:");
      if (this.subTitle == null) {
        sb.append("null");
      } else {
        sb.append(this.subTitle);
      }
      first = false;
    }
    if (isSetLink()) {
      if (!first) sb.append(", ");
      sb.append("link:");
      if (this.link == null) {
        sb.append("null");
      } else {
        sb.append(this.link);
      }
      first = false;
    }
    if (isSetResId()) {
      if (!first) sb.append(", ");
      sb.append("resId:");
      sb.append(this.resId);
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

  private static class HrMediaDOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrMediaDOStandardScheme getScheme() {
      return new HrMediaDOStandardScheme();
    }
  }

  private static class HrMediaDOStandardScheme extends org.apache.thrift.scheme.StandardScheme<HrMediaDO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HrMediaDO struct) throws org.apache.thrift.TException {
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
          case 2: // LONGTEXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.longtext = iprot.readString();
              struct.setLongtextIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ATTRS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.attrs = iprot.readString();
              struct.setAttrsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.title = iprot.readString();
              struct.setTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SUB_TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.subTitle = iprot.readString();
              struct.setSubTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // LINK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.link = iprot.readString();
              struct.setLinkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // RES_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.resId = iprot.readI32();
              struct.setResIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HrMediaDO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetId()) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeI32(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.longtext != null) {
        if (struct.isSetLongtext()) {
          oprot.writeFieldBegin(LONGTEXT_FIELD_DESC);
          oprot.writeString(struct.longtext);
          oprot.writeFieldEnd();
        }
      }
      if (struct.attrs != null) {
        if (struct.isSetAttrs()) {
          oprot.writeFieldBegin(ATTRS_FIELD_DESC);
          oprot.writeString(struct.attrs);
          oprot.writeFieldEnd();
        }
      }
      if (struct.title != null) {
        if (struct.isSetTitle()) {
          oprot.writeFieldBegin(TITLE_FIELD_DESC);
          oprot.writeString(struct.title);
          oprot.writeFieldEnd();
        }
      }
      if (struct.subTitle != null) {
        if (struct.isSetSubTitle()) {
          oprot.writeFieldBegin(SUB_TITLE_FIELD_DESC);
          oprot.writeString(struct.subTitle);
          oprot.writeFieldEnd();
        }
      }
      if (struct.link != null) {
        if (struct.isSetLink()) {
          oprot.writeFieldBegin(LINK_FIELD_DESC);
          oprot.writeString(struct.link);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetResId()) {
        oprot.writeFieldBegin(RES_ID_FIELD_DESC);
        oprot.writeI32(struct.resId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HrMediaDOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HrMediaDOTupleScheme getScheme() {
      return new HrMediaDOTupleScheme();
    }
  }

  private static class HrMediaDOTupleScheme extends org.apache.thrift.scheme.TupleScheme<HrMediaDO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HrMediaDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetLongtext()) {
        optionals.set(1);
      }
      if (struct.isSetAttrs()) {
        optionals.set(2);
      }
      if (struct.isSetTitle()) {
        optionals.set(3);
      }
      if (struct.isSetSubTitle()) {
        optionals.set(4);
      }
      if (struct.isSetLink()) {
        optionals.set(5);
      }
      if (struct.isSetResId()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetLongtext()) {
        oprot.writeString(struct.longtext);
      }
      if (struct.isSetAttrs()) {
        oprot.writeString(struct.attrs);
      }
      if (struct.isSetTitle()) {
        oprot.writeString(struct.title);
      }
      if (struct.isSetSubTitle()) {
        oprot.writeString(struct.subTitle);
      }
      if (struct.isSetLink()) {
        oprot.writeString(struct.link);
      }
      if (struct.isSetResId()) {
        oprot.writeI32(struct.resId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HrMediaDO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.longtext = iprot.readString();
        struct.setLongtextIsSet(true);
      }
      if (incoming.get(2)) {
        struct.attrs = iprot.readString();
        struct.setAttrsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.title = iprot.readString();
        struct.setTitleIsSet(true);
      }
      if (incoming.get(4)) {
        struct.subTitle = iprot.readString();
        struct.setSubTitleIsSet(true);
      }
      if (incoming.get(5)) {
        struct.link = iprot.readString();
        struct.setLinkIsSet(true);
      }
      if (incoming.get(6)) {
        struct.resId = iprot.readI32();
        struct.setResIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

