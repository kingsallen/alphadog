/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.foundation.wordpress.struct;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-11-12")
public class NewsletterData implements org.apache.thrift.TBase<NewsletterData, NewsletterData._Fields>, java.io.Serializable, Cloneable, Comparable<NewsletterData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NewsletterData");

  private static final org.apache.thrift.protocol.TField SHOW_NEW_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("show_new_version", org.apache.thrift.protocol.TType.BYTE, (short)1);
  private static final org.apache.thrift.protocol.TField VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("version", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField UPDATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("update_time", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField UPDATE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("update_list", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new NewsletterDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new NewsletterDataTupleSchemeFactory());
  }

  public byte show_new_version; // required
  public String version; // required
  public String update_time; // required
  public List<String> update_list; // required
  public String url; // required
  public String title; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SHOW_NEW_VERSION((short)1, "show_new_version"),
    VERSION((short)2, "version"),
    UPDATE_TIME((short)3, "update_time"),
    UPDATE_LIST((short)4, "update_list"),
    URL((short)5, "url"),
    TITLE((short)6, "title");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SHOW_NEW_VERSION
          return SHOW_NEW_VERSION;
        case 2: // VERSION
          return VERSION;
        case 3: // UPDATE_TIME
          return UPDATE_TIME;
        case 4: // UPDATE_LIST
          return UPDATE_LIST;
        case 5: // URL
          return URL;
        case 6: // TITLE
          return TITLE;
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
  private static final int __SHOW_NEW_VERSION_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SHOW_NEW_VERSION, new org.apache.thrift.meta_data.FieldMetaData("show_new_version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.VERSION, new org.apache.thrift.meta_data.FieldMetaData("version", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.UPDATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("update_time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Datetime")));
    tmpMap.put(_Fields.UPDATE_LIST, new org.apache.thrift.meta_data.FieldMetaData("update_list", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NewsletterData.class, metaDataMap);
  }

  public NewsletterData() {
  }

  public NewsletterData(
    byte show_new_version,
    String version,
    String update_time,
    List<String> update_list,
    String url,
    String title)
  {
    this();
    this.show_new_version = show_new_version;
    setShow_new_versionIsSet(true);
    this.version = version;
    this.update_time = update_time;
    this.update_list = update_list;
    this.url = url;
    this.title = title;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NewsletterData(NewsletterData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.show_new_version = other.show_new_version;
    if (other.isSetVersion()) {
      this.version = other.version;
    }
    if (other.isSetUpdate_time()) {
      this.update_time = other.update_time;
    }
    if (other.isSetUpdate_list()) {
      List<String> __this__update_list = new ArrayList<String>(other.update_list);
      this.update_list = __this__update_list;
    }
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetTitle()) {
      this.title = other.title;
    }
  }

  public NewsletterData deepCopy() {
    return new NewsletterData(this);
  }

  @Override
  public void clear() {
    setShow_new_versionIsSet(false);
    this.show_new_version = 0;
    this.version = null;
    this.update_time = null;
    this.update_list = null;
    this.url = null;
    this.title = null;
  }

  public byte getShow_new_version() {
    return this.show_new_version;
  }

  public NewsletterData setShow_new_version(byte show_new_version) {
    this.show_new_version = show_new_version;
    setShow_new_versionIsSet(true);
    return this;
  }

  public void unsetShow_new_version() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHOW_NEW_VERSION_ISSET_ID);
  }

  /** Returns true if field show_new_version is set (has been assigned a value) and false otherwise */
  public boolean isSetShow_new_version() {
    return EncodingUtils.testBit(__isset_bitfield, __SHOW_NEW_VERSION_ISSET_ID);
  }

  public void setShow_new_versionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHOW_NEW_VERSION_ISSET_ID, value);
  }

  public String getVersion() {
    return this.version;
  }

  public NewsletterData setVersion(String version) {
    this.version = version;
    return this;
  }

  public void unsetVersion() {
    this.version = null;
  }

  /** Returns true if field version is set (has been assigned a value) and false otherwise */
  public boolean isSetVersion() {
    return this.version != null;
  }

  public void setVersionIsSet(boolean value) {
    if (!value) {
      this.version = null;
    }
  }

  public String getUpdate_time() {
    return this.update_time;
  }

  public NewsletterData setUpdate_time(String update_time) {
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

  public int getUpdate_listSize() {
    return (this.update_list == null) ? 0 : this.update_list.size();
  }

  public java.util.Iterator<String> getUpdate_listIterator() {
    return (this.update_list == null) ? null : this.update_list.iterator();
  }

  public void addToUpdate_list(String elem) {
    if (this.update_list == null) {
      this.update_list = new ArrayList<String>();
    }
    this.update_list.add(elem);
  }

  public List<String> getUpdate_list() {
    return this.update_list;
  }

  public NewsletterData setUpdate_list(List<String> update_list) {
    this.update_list = update_list;
    return this;
  }

  public void unsetUpdate_list() {
    this.update_list = null;
  }

  /** Returns true if field update_list is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdate_list() {
    return this.update_list != null;
  }

  public void setUpdate_listIsSet(boolean value) {
    if (!value) {
      this.update_list = null;
    }
  }

  public String getUrl() {
    return this.url;
  }

  public NewsletterData setUrl(String url) {
    this.url = url;
    return this;
  }

  public void unsetUrl() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean isSetUrl() {
    return this.url != null;
  }

  public void setUrlIsSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public String getTitle() {
    return this.title;
  }

  public NewsletterData setTitle(String title) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SHOW_NEW_VERSION:
      if (value == null) {
        unsetShow_new_version();
      } else {
        setShow_new_version((Byte)value);
      }
      break;

    case VERSION:
      if (value == null) {
        unsetVersion();
      } else {
        setVersion((String)value);
      }
      break;

    case UPDATE_TIME:
      if (value == null) {
        unsetUpdate_time();
      } else {
        setUpdate_time((String)value);
      }
      break;

    case UPDATE_LIST:
      if (value == null) {
        unsetUpdate_list();
      } else {
        setUpdate_list((List<String>)value);
      }
      break;

    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SHOW_NEW_VERSION:
      return Byte.valueOf(getShow_new_version());

    case VERSION:
      return getVersion();

    case UPDATE_TIME:
      return getUpdate_time();

    case UPDATE_LIST:
      return getUpdate_list();

    case URL:
      return getUrl();

    case TITLE:
      return getTitle();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SHOW_NEW_VERSION:
      return isSetShow_new_version();
    case VERSION:
      return isSetVersion();
    case UPDATE_TIME:
      return isSetUpdate_time();
    case UPDATE_LIST:
      return isSetUpdate_list();
    case URL:
      return isSetUrl();
    case TITLE:
      return isSetTitle();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof NewsletterData)
      return this.equals((NewsletterData)that);
    return false;
  }

  public boolean equals(NewsletterData that) {
    if (that == null)
      return false;

    boolean this_present_show_new_version = true;
    boolean that_present_show_new_version = true;
    if (this_present_show_new_version || that_present_show_new_version) {
      if (!(this_present_show_new_version && that_present_show_new_version))
        return false;
      if (this.show_new_version != that.show_new_version)
        return false;
    }

    boolean this_present_version = true && this.isSetVersion();
    boolean that_present_version = true && that.isSetVersion();
    if (this_present_version || that_present_version) {
      if (!(this_present_version && that_present_version))
        return false;
      if (!this.version.equals(that.version))
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

    boolean this_present_update_list = true && this.isSetUpdate_list();
    boolean that_present_update_list = true && that.isSetUpdate_list();
    if (this_present_update_list || that_present_update_list) {
      if (!(this_present_update_list && that_present_update_list))
        return false;
      if (!this.update_list.equals(that.update_list))
        return false;
    }

    boolean this_present_url = true && this.isSetUrl();
    boolean that_present_url = true && that.isSetUrl();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_show_new_version = true;
    list.add(present_show_new_version);
    if (present_show_new_version)
      list.add(show_new_version);

    boolean present_version = true && (isSetVersion());
    list.add(present_version);
    if (present_version)
      list.add(version);

    boolean present_update_time = true && (isSetUpdate_time());
    list.add(present_update_time);
    if (present_update_time)
      list.add(update_time);

    boolean present_update_list = true && (isSetUpdate_list());
    list.add(present_update_list);
    if (present_update_list)
      list.add(update_list);

    boolean present_url = true && (isSetUrl());
    list.add(present_url);
    if (present_url)
      list.add(url);

    boolean present_title = true && (isSetTitle());
    list.add(present_title);
    if (present_title)
      list.add(title);

    return list.hashCode();
  }

  @Override
  public int compareTo(NewsletterData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetShow_new_version()).compareTo(other.isSetShow_new_version());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShow_new_version()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.show_new_version, other.show_new_version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVersion()).compareTo(other.isSetVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.version, other.version);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdate_time()).compareTo(other.isSetUpdate_time());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_time()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_time, other.update_time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpdate_list()).compareTo(other.isSetUpdate_list());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_list()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_list, other.update_list);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUrl()).compareTo(other.isSetUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.url, other.url);
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
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("NewsletterData(");
    boolean first = true;

    sb.append("show_new_version:");
    sb.append(this.show_new_version);
    first = false;
    if (!first) sb.append(", ");
    sb.append("version:");
    if (this.version == null) {
      sb.append("null");
    } else {
      sb.append(this.version);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("update_time:");
    if (this.update_time == null) {
      sb.append("null");
    } else {
      sb.append(this.update_time);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("update_list:");
    if (this.update_list == null) {
      sb.append("null");
    } else {
      sb.append(this.update_list);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("url:");
    if (this.url == null) {
      sb.append("null");
    } else {
      sb.append(this.url);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("title:");
    if (this.title == null) {
      sb.append("null");
    } else {
      sb.append(this.title);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class NewsletterDataStandardSchemeFactory implements SchemeFactory {
    public NewsletterDataStandardScheme getScheme() {
      return new NewsletterDataStandardScheme();
    }
  }

  private static class NewsletterDataStandardScheme extends StandardScheme<NewsletterData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, NewsletterData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SHOW_NEW_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.show_new_version = iprot.readByte();
              struct.setShow_new_versionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.version = iprot.readString();
              struct.setVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UPDATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.update_time = iprot.readString();
              struct.setUpdate_timeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // UPDATE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.update_list = new ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.update_list.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setUpdate_listIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.url = iprot.readString();
              struct.setUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.title = iprot.readString();
              struct.setTitleIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, NewsletterData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SHOW_NEW_VERSION_FIELD_DESC);
      oprot.writeByte(struct.show_new_version);
      oprot.writeFieldEnd();
      if (struct.version != null) {
        oprot.writeFieldBegin(VERSION_FIELD_DESC);
        oprot.writeString(struct.version);
        oprot.writeFieldEnd();
      }
      if (struct.update_time != null) {
        oprot.writeFieldBegin(UPDATE_TIME_FIELD_DESC);
        oprot.writeString(struct.update_time);
        oprot.writeFieldEnd();
      }
      if (struct.update_list != null) {
        oprot.writeFieldBegin(UPDATE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.update_list.size()));
          for (String _iter3 : struct.update_list)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        oprot.writeString(struct.url);
        oprot.writeFieldEnd();
      }
      if (struct.title != null) {
        oprot.writeFieldBegin(TITLE_FIELD_DESC);
        oprot.writeString(struct.title);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class NewsletterDataTupleSchemeFactory implements SchemeFactory {
    public NewsletterDataTupleScheme getScheme() {
      return new NewsletterDataTupleScheme();
    }
  }

  private static class NewsletterDataTupleScheme extends TupleScheme<NewsletterData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, NewsletterData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetShow_new_version()) {
        optionals.set(0);
      }
      if (struct.isSetVersion()) {
        optionals.set(1);
      }
      if (struct.isSetUpdate_time()) {
        optionals.set(2);
      }
      if (struct.isSetUpdate_list()) {
        optionals.set(3);
      }
      if (struct.isSetUrl()) {
        optionals.set(4);
      }
      if (struct.isSetTitle()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetShow_new_version()) {
        oprot.writeByte(struct.show_new_version);
      }
      if (struct.isSetVersion()) {
        oprot.writeString(struct.version);
      }
      if (struct.isSetUpdate_time()) {
        oprot.writeString(struct.update_time);
      }
      if (struct.isSetUpdate_list()) {
        {
          oprot.writeI32(struct.update_list.size());
          for (String _iter4 : struct.update_list)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetUrl()) {
        oprot.writeString(struct.url);
      }
      if (struct.isSetTitle()) {
        oprot.writeString(struct.title);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NewsletterData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.show_new_version = iprot.readByte();
        struct.setShow_new_versionIsSet(true);
      }
      if (incoming.get(1)) {
        struct.version = iprot.readString();
        struct.setVersionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.update_time = iprot.readString();
        struct.setUpdate_timeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.update_list = new ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.update_list.add(_elem6);
          }
        }
        struct.setUpdate_listIsSet(true);
      }
      if (incoming.get(4)) {
        struct.url = iprot.readString();
        struct.setUrlIsSet(true);
      }
      if (incoming.get(5)) {
        struct.title = iprot.readString();
        struct.setTitleIsSet(true);
      }
    }
  }

}

