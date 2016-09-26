/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.mq.struct;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-9-26")
public class MessageTemplateNoticeStruct implements org.apache.thrift.TBase<MessageTemplateNoticeStruct, MessageTemplateNoticeStruct._Fields>, java.io.Serializable, Cloneable, Comparable<MessageTemplateNoticeStruct> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MessageTemplateNoticeStruct");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("user_id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField SYS_TEMPLATE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sys_template_id", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("company_id", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.MAP, (short)5);
  private static final org.apache.thrift.protocol.TField ENABLE_QX_RETRY_FIELD_DESC = new org.apache.thrift.protocol.TField("enable_qx_retry", org.apache.thrift.protocol.TType.BYTE, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MessageTemplateNoticeStructStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MessageTemplateNoticeStructTupleSchemeFactory());
  }

  public int user_id; // optional
  public int sys_template_id; // optional
  public String url; // optional
  public int company_id; // optional
  public Map<String,MessageTplDataCol> data; // optional
  public byte enable_qx_retry; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "user_id"),
    SYS_TEMPLATE_ID((short)2, "sys_template_id"),
    URL((short)3, "url"),
    COMPANY_ID((short)4, "company_id"),
    DATA((short)5, "data"),
    ENABLE_QX_RETRY((short)6, "enable_qx_retry");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // SYS_TEMPLATE_ID
          return SYS_TEMPLATE_ID;
        case 3: // URL
          return URL;
        case 4: // COMPANY_ID
          return COMPANY_ID;
        case 5: // DATA
          return DATA;
        case 6: // ENABLE_QX_RETRY
          return ENABLE_QX_RETRY;
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
  private static final int __USER_ID_ISSET_ID = 0;
  private static final int __SYS_TEMPLATE_ID_ISSET_ID = 1;
  private static final int __COMPANY_ID_ISSET_ID = 2;
  private static final int __ENABLE_QX_RETRY_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.USER_ID,_Fields.SYS_TEMPLATE_ID,_Fields.URL,_Fields.COMPANY_ID,_Fields.DATA,_Fields.ENABLE_QX_RETRY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("user_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SYS_TEMPLATE_ID, new org.apache.thrift.meta_data.FieldMetaData("sys_template_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("company_id", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MessageTplDataCol.class))));
    tmpMap.put(_Fields.ENABLE_QX_RETRY, new org.apache.thrift.meta_data.FieldMetaData("enable_qx_retry", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MessageTemplateNoticeStruct.class, metaDataMap);
  }

  public MessageTemplateNoticeStruct() {
    this.enable_qx_retry = (byte)1;

  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MessageTemplateNoticeStruct(MessageTemplateNoticeStruct other) {
    __isset_bitfield = other.__isset_bitfield;
    this.user_id = other.user_id;
    this.sys_template_id = other.sys_template_id;
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    this.company_id = other.company_id;
    if (other.isSetData()) {
      Map<String,MessageTplDataCol> __this__data = new HashMap<String,MessageTplDataCol>(other.data.size());
      for (Map.Entry<String, MessageTplDataCol> other_element : other.data.entrySet()) {

        String other_element_key = other_element.getKey();
        MessageTplDataCol other_element_value = other_element.getValue();

        String __this__data_copy_key = other_element_key;

        MessageTplDataCol __this__data_copy_value = new MessageTplDataCol(other_element_value);

        __this__data.put(__this__data_copy_key, __this__data_copy_value);
      }
      this.data = __this__data;
    }
    this.enable_qx_retry = other.enable_qx_retry;
  }

  public MessageTemplateNoticeStruct deepCopy() {
    return new MessageTemplateNoticeStruct(this);
  }

  @Override
  public void clear() {
    setUser_idIsSet(false);
    this.user_id = 0;
    setSys_template_idIsSet(false);
    this.sys_template_id = 0;
    this.url = null;
    setCompany_idIsSet(false);
    this.company_id = 0;
    this.data = null;
    this.enable_qx_retry = (byte)1;

  }

  public int getUser_id() {
    return this.user_id;
  }

  public MessageTemplateNoticeStruct setUser_id(int user_id) {
    this.user_id = user_id;
    setUser_idIsSet(true);
    return this;
  }

  public void unsetUser_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  /** Returns true if field user_id is set (has been assigned a value) and false otherwise */
  public boolean isSetUser_id() {
    return EncodingUtils.testBit(__isset_bitfield, __USER_ID_ISSET_ID);
  }

  public void setUser_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USER_ID_ISSET_ID, value);
  }

  public int getSys_template_id() {
    return this.sys_template_id;
  }

  public MessageTemplateNoticeStruct setSys_template_id(int sys_template_id) {
    this.sys_template_id = sys_template_id;
    setSys_template_idIsSet(true);
    return this;
  }

  public void unsetSys_template_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SYS_TEMPLATE_ID_ISSET_ID);
  }

  /** Returns true if field sys_template_id is set (has been assigned a value) and false otherwise */
  public boolean isSetSys_template_id() {
    return EncodingUtils.testBit(__isset_bitfield, __SYS_TEMPLATE_ID_ISSET_ID);
  }

  public void setSys_template_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SYS_TEMPLATE_ID_ISSET_ID, value);
  }

  public String getUrl() {
    return this.url;
  }

  public MessageTemplateNoticeStruct setUrl(String url) {
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

  public int getCompany_id() {
    return this.company_id;
  }

  public MessageTemplateNoticeStruct setCompany_id(int company_id) {
    this.company_id = company_id;
    setCompany_idIsSet(true);
    return this;
  }

  public void unsetCompany_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  /** Returns true if field company_id is set (has been assigned a value) and false otherwise */
  public boolean isSetCompany_id() {
    return EncodingUtils.testBit(__isset_bitfield, __COMPANY_ID_ISSET_ID);
  }

  public void setCompany_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMPANY_ID_ISSET_ID, value);
  }

  public int getDataSize() {
    return (this.data == null) ? 0 : this.data.size();
  }

  public void putToData(String key, MessageTplDataCol val) {
    if (this.data == null) {
      this.data = new HashMap<String,MessageTplDataCol>();
    }
    this.data.put(key, val);
  }

  public Map<String,MessageTplDataCol> getData() {
    return this.data;
  }

  public MessageTemplateNoticeStruct setData(Map<String,MessageTplDataCol> data) {
    this.data = data;
    return this;
  }

  public void unsetData() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean isSetData() {
    return this.data != null;
  }

  public void setDataIsSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public byte getEnable_qx_retry() {
    return this.enable_qx_retry;
  }

  public MessageTemplateNoticeStruct setEnable_qx_retry(byte enable_qx_retry) {
    this.enable_qx_retry = enable_qx_retry;
    setEnable_qx_retryIsSet(true);
    return this;
  }

  public void unsetEnable_qx_retry() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ENABLE_QX_RETRY_ISSET_ID);
  }

  /** Returns true if field enable_qx_retry is set (has been assigned a value) and false otherwise */
  public boolean isSetEnable_qx_retry() {
    return EncodingUtils.testBit(__isset_bitfield, __ENABLE_QX_RETRY_ISSET_ID);
  }

  public void setEnable_qx_retryIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ENABLE_QX_RETRY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUser_id();
      } else {
        setUser_id((Integer)value);
      }
      break;

    case SYS_TEMPLATE_ID:
      if (value == null) {
        unsetSys_template_id();
      } else {
        setSys_template_id((Integer)value);
      }
      break;

    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case COMPANY_ID:
      if (value == null) {
        unsetCompany_id();
      } else {
        setCompany_id((Integer)value);
      }
      break;

    case DATA:
      if (value == null) {
        unsetData();
      } else {
        setData((Map<String,MessageTplDataCol>)value);
      }
      break;

    case ENABLE_QX_RETRY:
      if (value == null) {
        unsetEnable_qx_retry();
      } else {
        setEnable_qx_retry((Byte)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return Integer.valueOf(getUser_id());

    case SYS_TEMPLATE_ID:
      return Integer.valueOf(getSys_template_id());

    case URL:
      return getUrl();

    case COMPANY_ID:
      return Integer.valueOf(getCompany_id());

    case DATA:
      return getData();

    case ENABLE_QX_RETRY:
      return Byte.valueOf(getEnable_qx_retry());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUser_id();
    case SYS_TEMPLATE_ID:
      return isSetSys_template_id();
    case URL:
      return isSetUrl();
    case COMPANY_ID:
      return isSetCompany_id();
    case DATA:
      return isSetData();
    case ENABLE_QX_RETRY:
      return isSetEnable_qx_retry();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MessageTemplateNoticeStruct)
      return this.equals((MessageTemplateNoticeStruct)that);
    return false;
  }

  public boolean equals(MessageTemplateNoticeStruct that) {
    if (that == null)
      return false;

    boolean this_present_user_id = true && this.isSetUser_id();
    boolean that_present_user_id = true && that.isSetUser_id();
    if (this_present_user_id || that_present_user_id) {
      if (!(this_present_user_id && that_present_user_id))
        return false;
      if (this.user_id != that.user_id)
        return false;
    }

    boolean this_present_sys_template_id = true && this.isSetSys_template_id();
    boolean that_present_sys_template_id = true && that.isSetSys_template_id();
    if (this_present_sys_template_id || that_present_sys_template_id) {
      if (!(this_present_sys_template_id && that_present_sys_template_id))
        return false;
      if (this.sys_template_id != that.sys_template_id)
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

    boolean this_present_company_id = true && this.isSetCompany_id();
    boolean that_present_company_id = true && that.isSetCompany_id();
    if (this_present_company_id || that_present_company_id) {
      if (!(this_present_company_id && that_present_company_id))
        return false;
      if (this.company_id != that.company_id)
        return false;
    }

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    boolean this_present_enable_qx_retry = true && this.isSetEnable_qx_retry();
    boolean that_present_enable_qx_retry = true && that.isSetEnable_qx_retry();
    if (this_present_enable_qx_retry || that_present_enable_qx_retry) {
      if (!(this_present_enable_qx_retry && that_present_enable_qx_retry))
        return false;
      if (this.enable_qx_retry != that.enable_qx_retry)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_user_id = true && (isSetUser_id());
    list.add(present_user_id);
    if (present_user_id)
      list.add(user_id);

    boolean present_sys_template_id = true && (isSetSys_template_id());
    list.add(present_sys_template_id);
    if (present_sys_template_id)
      list.add(sys_template_id);

    boolean present_url = true && (isSetUrl());
    list.add(present_url);
    if (present_url)
      list.add(url);

    boolean present_company_id = true && (isSetCompany_id());
    list.add(present_company_id);
    if (present_company_id)
      list.add(company_id);

    boolean present_data = true && (isSetData());
    list.add(present_data);
    if (present_data)
      list.add(data);

    boolean present_enable_qx_retry = true && (isSetEnable_qx_retry());
    list.add(present_enable_qx_retry);
    if (present_enable_qx_retry)
      list.add(enable_qx_retry);

    return list.hashCode();
  }

  @Override
  public int compareTo(MessageTemplateNoticeStruct other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUser_id()).compareTo(other.isSetUser_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user_id, other.user_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSys_template_id()).compareTo(other.isSetSys_template_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSys_template_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sys_template_id, other.sys_template_id);
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
    lastComparison = Boolean.valueOf(isSetCompany_id()).compareTo(other.isSetCompany_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompany_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.company_id, other.company_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetData()).compareTo(other.isSetData());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetData()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEnable_qx_retry()).compareTo(other.isSetEnable_qx_retry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEnable_qx_retry()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.enable_qx_retry, other.enable_qx_retry);
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
    StringBuilder sb = new StringBuilder("MessageTemplateNoticeStruct(");
    boolean first = true;

    if (isSetUser_id()) {
      sb.append("user_id:");
      sb.append(this.user_id);
      first = false;
    }
    if (isSetSys_template_id()) {
      if (!first) sb.append(", ");
      sb.append("sys_template_id:");
      sb.append(this.sys_template_id);
      first = false;
    }
    if (isSetUrl()) {
      if (!first) sb.append(", ");
      sb.append("url:");
      if (this.url == null) {
        sb.append("null");
      } else {
        sb.append(this.url);
      }
      first = false;
    }
    if (isSetCompany_id()) {
      if (!first) sb.append(", ");
      sb.append("company_id:");
      sb.append(this.company_id);
      first = false;
    }
    if (isSetData()) {
      if (!first) sb.append(", ");
      sb.append("data:");
      if (this.data == null) {
        sb.append("null");
      } else {
        sb.append(this.data);
      }
      first = false;
    }
    if (isSetEnable_qx_retry()) {
      if (!first) sb.append(", ");
      sb.append("enable_qx_retry:");
      sb.append(this.enable_qx_retry);
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

  private static class MessageTemplateNoticeStructStandardSchemeFactory implements SchemeFactory {
    public MessageTemplateNoticeStructStandardScheme getScheme() {
      return new MessageTemplateNoticeStructStandardScheme();
    }
  }

  private static class MessageTemplateNoticeStructStandardScheme extends StandardScheme<MessageTemplateNoticeStruct> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MessageTemplateNoticeStruct struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.user_id = iprot.readI32();
              struct.setUser_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SYS_TEMPLATE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sys_template_id = iprot.readI32();
              struct.setSys_template_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.url = iprot.readString();
              struct.setUrlIsSet(true);
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
          case 5: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.data = new HashMap<String,MessageTplDataCol>(2*_map0.size);
                String _key1;
                MessageTplDataCol _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readString();
                  _val2 = new MessageTplDataCol();
                  _val2.read(iprot);
                  struct.data.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setDataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ENABLE_QX_RETRY
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.enable_qx_retry = iprot.readByte();
              struct.setEnable_qx_retryIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MessageTemplateNoticeStruct struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetUser_id()) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeI32(struct.user_id);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSys_template_id()) {
        oprot.writeFieldBegin(SYS_TEMPLATE_ID_FIELD_DESC);
        oprot.writeI32(struct.sys_template_id);
        oprot.writeFieldEnd();
      }
      if (struct.url != null) {
        if (struct.isSetUrl()) {
          oprot.writeFieldBegin(URL_FIELD_DESC);
          oprot.writeString(struct.url);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCompany_id()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.company_id);
        oprot.writeFieldEnd();
      }
      if (struct.data != null) {
        if (struct.isSetData()) {
          oprot.writeFieldBegin(DATA_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.data.size()));
            for (Map.Entry<String, MessageTplDataCol> _iter4 : struct.data.entrySet())
            {
              oprot.writeString(_iter4.getKey());
              _iter4.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetEnable_qx_retry()) {
        oprot.writeFieldBegin(ENABLE_QX_RETRY_FIELD_DESC);
        oprot.writeByte(struct.enable_qx_retry);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MessageTemplateNoticeStructTupleSchemeFactory implements SchemeFactory {
    public MessageTemplateNoticeStructTupleScheme getScheme() {
      return new MessageTemplateNoticeStructTupleScheme();
    }
  }

  private static class MessageTemplateNoticeStructTupleScheme extends TupleScheme<MessageTemplateNoticeStruct> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MessageTemplateNoticeStruct struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUser_id()) {
        optionals.set(0);
      }
      if (struct.isSetSys_template_id()) {
        optionals.set(1);
      }
      if (struct.isSetUrl()) {
        optionals.set(2);
      }
      if (struct.isSetCompany_id()) {
        optionals.set(3);
      }
      if (struct.isSetData()) {
        optionals.set(4);
      }
      if (struct.isSetEnable_qx_retry()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetUser_id()) {
        oprot.writeI32(struct.user_id);
      }
      if (struct.isSetSys_template_id()) {
        oprot.writeI32(struct.sys_template_id);
      }
      if (struct.isSetUrl()) {
        oprot.writeString(struct.url);
      }
      if (struct.isSetCompany_id()) {
        oprot.writeI32(struct.company_id);
      }
      if (struct.isSetData()) {
        {
          oprot.writeI32(struct.data.size());
          for (Map.Entry<String, MessageTplDataCol> _iter5 : struct.data.entrySet())
          {
            oprot.writeString(_iter5.getKey());
            _iter5.getValue().write(oprot);
          }
        }
      }
      if (struct.isSetEnable_qx_retry()) {
        oprot.writeByte(struct.enable_qx_retry);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MessageTemplateNoticeStruct struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.user_id = iprot.readI32();
        struct.setUser_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sys_template_id = iprot.readI32();
        struct.setSys_template_idIsSet(true);
      }
      if (incoming.get(2)) {
        struct.url = iprot.readString();
        struct.setUrlIsSet(true);
      }
      if (incoming.get(3)) {
        struct.company_id = iprot.readI32();
        struct.setCompany_idIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TMap _map6 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.data = new HashMap<String,MessageTplDataCol>(2*_map6.size);
          String _key7;
          MessageTplDataCol _val8;
          for (int _i9 = 0; _i9 < _map6.size; ++_i9)
          {
            _key7 = iprot.readString();
            _val8 = new MessageTplDataCol();
            _val8.read(iprot);
            struct.data.put(_key7, _val8);
          }
        }
        struct.setDataIsSet(true);
      }
      if (incoming.get(5)) {
        struct.enable_qx_retry = iprot.readByte();
        struct.setEnable_qx_retryIsSet(true);
      }
    }
  }

}

