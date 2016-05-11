/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.common.struct;

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

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2016-05-11")
public class CommonQuery implements org.apache.thrift.TBase<CommonQuery, CommonQuery._Fields>, java.io.Serializable, Cloneable, Comparable<CommonQuery> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CommonQuery");

  private static final org.apache.thrift.protocol.TField APPID_FIELD_DESC = new org.apache.thrift.protocol.TField("appid", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("page", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PER_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("per_page", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField SORTBY_FIELD_DESC = new org.apache.thrift.protocol.TField("sortby", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("order", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField FIELDS_FIELD_DESC = new org.apache.thrift.protocol.TField("fields", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField NOCACHE_FIELD_DESC = new org.apache.thrift.protocol.TField("nocache", org.apache.thrift.protocol.TType.BOOL, (short)7);
  private static final org.apache.thrift.protocol.TField EQUAL_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("equalFilter", org.apache.thrift.protocol.TType.MAP, (short)8);

  private static final SchemeFactory STANDARD_SCHEME_FACTORY = new CommonQueryStandardSchemeFactory();
  private static final SchemeFactory TUPLE_SCHEME_FACTORY = new CommonQueryTupleSchemeFactory();

  public int appid; // required
  public int page; // optional
  public int per_page; // optional
  public String sortby; // optional
  public String order; // optional
  public String fields; // optional
  public boolean nocache; // optional
  public Map<String,String> equalFilter; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    APPID((short)1, "appid"),
    PAGE((short)2, "page"),
    PER_PAGE((short)3, "per_page"),
    SORTBY((short)4, "sortby"),
    ORDER((short)5, "order"),
    FIELDS((short)6, "fields"),
    NOCACHE((short)7, "nocache"),
    EQUAL_FILTER((short)8, "equalFilter");

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
        case 1: // APPID
          return APPID;
        case 2: // PAGE
          return PAGE;
        case 3: // PER_PAGE
          return PER_PAGE;
        case 4: // SORTBY
          return SORTBY;
        case 5: // ORDER
          return ORDER;
        case 6: // FIELDS
          return FIELDS;
        case 7: // NOCACHE
          return NOCACHE;
        case 8: // EQUAL_FILTER
          return EQUAL_FILTER;
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
  private static final int __APPID_ISSET_ID = 0;
  private static final int __PAGE_ISSET_ID = 1;
  private static final int __PER_PAGE_ISSET_ID = 2;
  private static final int __NOCACHE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PAGE,_Fields.PER_PAGE,_Fields.SORTBY,_Fields.ORDER,_Fields.FIELDS,_Fields.NOCACHE,_Fields.EQUAL_FILTER};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.APPID, new org.apache.thrift.meta_data.FieldMetaData("appid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE, new org.apache.thrift.meta_data.FieldMetaData("page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PER_PAGE, new org.apache.thrift.meta_data.FieldMetaData("per_page", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SORTBY, new org.apache.thrift.meta_data.FieldMetaData("sortby", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDER, new org.apache.thrift.meta_data.FieldMetaData("order", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FIELDS, new org.apache.thrift.meta_data.FieldMetaData("fields", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NOCACHE, new org.apache.thrift.meta_data.FieldMetaData("nocache", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.EQUAL_FILTER, new org.apache.thrift.meta_data.FieldMetaData("equalFilter", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CommonQuery.class, metaDataMap);
  }

  public CommonQuery() {
    this.nocache = false;

  }

  public CommonQuery(
    int appid)
  {
    this();
    this.appid = appid;
    setAppidIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CommonQuery(CommonQuery other) {
    __isset_bitfield = other.__isset_bitfield;
    this.appid = other.appid;
    this.page = other.page;
    this.per_page = other.per_page;
    if (other.isSetSortby()) {
      this.sortby = other.sortby;
    }
    if (other.isSetOrder()) {
      this.order = other.order;
    }
    if (other.isSetFields()) {
      this.fields = other.fields;
    }
    this.nocache = other.nocache;
    if (other.isSetEqualFilter()) {
      Map<String,String> __this__equalFilter = new HashMap<String,String>(other.equalFilter);
      this.equalFilter = __this__equalFilter;
    }
  }

  public CommonQuery deepCopy() {
    return new CommonQuery(this);
  }

  @Override
  public void clear() {
    setAppidIsSet(false);
    this.appid = 0;
    setPageIsSet(false);
    this.page = 0;
    setPer_pageIsSet(false);
    this.per_page = 0;
    this.sortby = null;
    this.order = null;
    this.fields = null;
    this.nocache = false;

    this.equalFilter = null;
  }

  public int getAppid() {
    return this.appid;
  }

  public CommonQuery setAppid(int appid) {
    this.appid = appid;
    setAppidIsSet(true);
    return this;
  }

  public void unsetAppid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __APPID_ISSET_ID);
  }

  /** Returns true if field appid is set (has been assigned a value) and false otherwise */
  public boolean isSetAppid() {
    return EncodingUtils.testBit(__isset_bitfield, __APPID_ISSET_ID);
  }

  public void setAppidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __APPID_ISSET_ID, value);
  }

  public int getPage() {
    return this.page;
  }

  public CommonQuery setPage(int page) {
    this.page = page;
    setPageIsSet(true);
    return this;
  }

  public void unsetPage() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  /** Returns true if field page is set (has been assigned a value) and false otherwise */
  public boolean isSetPage() {
    return EncodingUtils.testBit(__isset_bitfield, __PAGE_ISSET_ID);
  }

  public void setPageIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PAGE_ISSET_ID, value);
  }

  public int getPer_page() {
    return this.per_page;
  }

  public CommonQuery setPer_page(int per_page) {
    this.per_page = per_page;
    setPer_pageIsSet(true);
    return this;
  }

  public void unsetPer_page() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PER_PAGE_ISSET_ID);
  }

  /** Returns true if field per_page is set (has been assigned a value) and false otherwise */
  public boolean isSetPer_page() {
    return EncodingUtils.testBit(__isset_bitfield, __PER_PAGE_ISSET_ID);
  }

  public void setPer_pageIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PER_PAGE_ISSET_ID, value);
  }

  public String getSortby() {
    return this.sortby;
  }

  public CommonQuery setSortby(String sortby) {
    this.sortby = sortby;
    return this;
  }

  public void unsetSortby() {
    this.sortby = null;
  }

  /** Returns true if field sortby is set (has been assigned a value) and false otherwise */
  public boolean isSetSortby() {
    return this.sortby != null;
  }

  public void setSortbyIsSet(boolean value) {
    if (!value) {
      this.sortby = null;
    }
  }

  public String getOrder() {
    return this.order;
  }

  public CommonQuery setOrder(String order) {
    this.order = order;
    return this;
  }

  public void unsetOrder() {
    this.order = null;
  }

  /** Returns true if field order is set (has been assigned a value) and false otherwise */
  public boolean isSetOrder() {
    return this.order != null;
  }

  public void setOrderIsSet(boolean value) {
    if (!value) {
      this.order = null;
    }
  }

  public String getFields() {
    return this.fields;
  }

  public CommonQuery setFields(String fields) {
    this.fields = fields;
    return this;
  }

  public void unsetFields() {
    this.fields = null;
  }

  /** Returns true if field fields is set (has been assigned a value) and false otherwise */
  public boolean isSetFields() {
    return this.fields != null;
  }

  public void setFieldsIsSet(boolean value) {
    if (!value) {
      this.fields = null;
    }
  }

  public boolean isNocache() {
    return this.nocache;
  }

  public CommonQuery setNocache(boolean nocache) {
    this.nocache = nocache;
    setNocacheIsSet(true);
    return this;
  }

  public void unsetNocache() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NOCACHE_ISSET_ID);
  }

  /** Returns true if field nocache is set (has been assigned a value) and false otherwise */
  public boolean isSetNocache() {
    return EncodingUtils.testBit(__isset_bitfield, __NOCACHE_ISSET_ID);
  }

  public void setNocacheIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NOCACHE_ISSET_ID, value);
  }

  public int getEqualFilterSize() {
    return (this.equalFilter == null) ? 0 : this.equalFilter.size();
  }

  public void putToEqualFilter(String key, String val) {
    if (this.equalFilter == null) {
      this.equalFilter = new HashMap<String,String>();
    }
    this.equalFilter.put(key, val);
  }

  public Map<String,String> getEqualFilter() {
    return this.equalFilter;
  }

  public CommonQuery setEqualFilter(Map<String,String> equalFilter) {
    this.equalFilter = equalFilter;
    return this;
  }

  public void unsetEqualFilter() {
    this.equalFilter = null;
  }

  /** Returns true if field equalFilter is set (has been assigned a value) and false otherwise */
  public boolean isSetEqualFilter() {
    return this.equalFilter != null;
  }

  public void setEqualFilterIsSet(boolean value) {
    if (!value) {
      this.equalFilter = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case APPID:
      if (value == null) {
        unsetAppid();
      } else {
        setAppid((Integer)value);
      }
      break;

    case PAGE:
      if (value == null) {
        unsetPage();
      } else {
        setPage((Integer)value);
      }
      break;

    case PER_PAGE:
      if (value == null) {
        unsetPer_page();
      } else {
        setPer_page((Integer)value);
      }
      break;

    case SORTBY:
      if (value == null) {
        unsetSortby();
      } else {
        setSortby((String)value);
      }
      break;

    case ORDER:
      if (value == null) {
        unsetOrder();
      } else {
        setOrder((String)value);
      }
      break;

    case FIELDS:
      if (value == null) {
        unsetFields();
      } else {
        setFields((String)value);
      }
      break;

    case NOCACHE:
      if (value == null) {
        unsetNocache();
      } else {
        setNocache((Boolean)value);
      }
      break;

    case EQUAL_FILTER:
      if (value == null) {
        unsetEqualFilter();
      } else {
        setEqualFilter((Map<String,String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case APPID:
      return getAppid();

    case PAGE:
      return getPage();

    case PER_PAGE:
      return getPer_page();

    case SORTBY:
      return getSortby();

    case ORDER:
      return getOrder();

    case FIELDS:
      return getFields();

    case NOCACHE:
      return isNocache();

    case EQUAL_FILTER:
      return getEqualFilter();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case APPID:
      return isSetAppid();
    case PAGE:
      return isSetPage();
    case PER_PAGE:
      return isSetPer_page();
    case SORTBY:
      return isSetSortby();
    case ORDER:
      return isSetOrder();
    case FIELDS:
      return isSetFields();
    case NOCACHE:
      return isSetNocache();
    case EQUAL_FILTER:
      return isSetEqualFilter();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CommonQuery)
      return this.equals((CommonQuery)that);
    return false;
  }

  public boolean equals(CommonQuery that) {
    if (that == null)
      return false;

    boolean this_present_appid = true;
    boolean that_present_appid = true;
    if (this_present_appid || that_present_appid) {
      if (!(this_present_appid && that_present_appid))
        return false;
      if (this.appid != that.appid)
        return false;
    }

    boolean this_present_page = true && this.isSetPage();
    boolean that_present_page = true && that.isSetPage();
    if (this_present_page || that_present_page) {
      if (!(this_present_page && that_present_page))
        return false;
      if (this.page != that.page)
        return false;
    }

    boolean this_present_per_page = true && this.isSetPer_page();
    boolean that_present_per_page = true && that.isSetPer_page();
    if (this_present_per_page || that_present_per_page) {
      if (!(this_present_per_page && that_present_per_page))
        return false;
      if (this.per_page != that.per_page)
        return false;
    }

    boolean this_present_sortby = true && this.isSetSortby();
    boolean that_present_sortby = true && that.isSetSortby();
    if (this_present_sortby || that_present_sortby) {
      if (!(this_present_sortby && that_present_sortby))
        return false;
      if (!this.sortby.equals(that.sortby))
        return false;
    }

    boolean this_present_order = true && this.isSetOrder();
    boolean that_present_order = true && that.isSetOrder();
    if (this_present_order || that_present_order) {
      if (!(this_present_order && that_present_order))
        return false;
      if (!this.order.equals(that.order))
        return false;
    }

    boolean this_present_fields = true && this.isSetFields();
    boolean that_present_fields = true && that.isSetFields();
    if (this_present_fields || that_present_fields) {
      if (!(this_present_fields && that_present_fields))
        return false;
      if (!this.fields.equals(that.fields))
        return false;
    }

    boolean this_present_nocache = true && this.isSetNocache();
    boolean that_present_nocache = true && that.isSetNocache();
    if (this_present_nocache || that_present_nocache) {
      if (!(this_present_nocache && that_present_nocache))
        return false;
      if (this.nocache != that.nocache)
        return false;
    }

    boolean this_present_equalFilter = true && this.isSetEqualFilter();
    boolean that_present_equalFilter = true && that.isSetEqualFilter();
    if (this_present_equalFilter || that_present_equalFilter) {
      if (!(this_present_equalFilter && that_present_equalFilter))
        return false;
      if (!this.equalFilter.equals(that.equalFilter))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + appid;

    hashCode = hashCode * 8191 + ((isSetPage()) ? 131071 : 524287);
    if (isSetPage())
      hashCode = hashCode * 8191 + page;

    hashCode = hashCode * 8191 + ((isSetPer_page()) ? 131071 : 524287);
    if (isSetPer_page())
      hashCode = hashCode * 8191 + per_page;

    hashCode = hashCode * 8191 + ((isSetSortby()) ? 131071 : 524287);
    if (isSetSortby())
      hashCode = hashCode * 8191 + sortby.hashCode();

    hashCode = hashCode * 8191 + ((isSetOrder()) ? 131071 : 524287);
    if (isSetOrder())
      hashCode = hashCode * 8191 + order.hashCode();

    hashCode = hashCode * 8191 + ((isSetFields()) ? 131071 : 524287);
    if (isSetFields())
      hashCode = hashCode * 8191 + fields.hashCode();

    hashCode = hashCode * 8191 + ((isSetNocache()) ? 131071 : 524287);
    if (isSetNocache())
      hashCode = hashCode * 8191 + ((nocache) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetEqualFilter()) ? 131071 : 524287);
    if (isSetEqualFilter())
      hashCode = hashCode * 8191 + equalFilter.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(CommonQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetAppid()).compareTo(other.isSetAppid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAppid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.appid, other.appid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPage()).compareTo(other.isSetPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page, other.page);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPer_page()).compareTo(other.isSetPer_page());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPer_page()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.per_page, other.per_page);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSortby()).compareTo(other.isSetSortby());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSortby()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sortby, other.sortby);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrder()).compareTo(other.isSetOrder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.order, other.order);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFields()).compareTo(other.isSetFields());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFields()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fields, other.fields);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNocache()).compareTo(other.isSetNocache());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNocache()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nocache, other.nocache);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEqualFilter()).compareTo(other.isSetEqualFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEqualFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.equalFilter, other.equalFilter);
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
    StringBuilder sb = new StringBuilder("CommonQuery(");
    boolean first = true;

    sb.append("appid:");
    sb.append(this.appid);
    first = false;
    if (isSetPage()) {
      if (!first) sb.append(", ");
      sb.append("page:");
      sb.append(this.page);
      first = false;
    }
    if (isSetPer_page()) {
      if (!first) sb.append(", ");
      sb.append("per_page:");
      sb.append(this.per_page);
      first = false;
    }
    if (isSetSortby()) {
      if (!first) sb.append(", ");
      sb.append("sortby:");
      if (this.sortby == null) {
        sb.append("null");
      } else {
        sb.append(this.sortby);
      }
      first = false;
    }
    if (isSetOrder()) {
      if (!first) sb.append(", ");
      sb.append("order:");
      if (this.order == null) {
        sb.append("null");
      } else {
        sb.append(this.order);
      }
      first = false;
    }
    if (isSetFields()) {
      if (!first) sb.append(", ");
      sb.append("fields:");
      if (this.fields == null) {
        sb.append("null");
      } else {
        sb.append(this.fields);
      }
      first = false;
    }
    if (isSetNocache()) {
      if (!first) sb.append(", ");
      sb.append("nocache:");
      sb.append(this.nocache);
      first = false;
    }
    if (isSetEqualFilter()) {
      if (!first) sb.append(", ");
      sb.append("equalFilter:");
      if (this.equalFilter == null) {
        sb.append("null");
      } else {
        sb.append(this.equalFilter);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CommonQueryStandardSchemeFactory implements SchemeFactory {
    public CommonQueryStandardScheme getScheme() {
      return new CommonQueryStandardScheme();
    }
  }

  private static class CommonQueryStandardScheme extends StandardScheme<CommonQuery> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CommonQuery struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // APPID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.appid = iprot.readI32();
              struct.setAppidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.page = iprot.readI32();
              struct.setPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PER_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.per_page = iprot.readI32();
              struct.setPer_pageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SORTBY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sortby = iprot.readString();
              struct.setSortbyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.order = iprot.readString();
              struct.setOrderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // FIELDS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fields = iprot.readString();
              struct.setFieldsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // NOCACHE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.nocache = iprot.readBool();
              struct.setNocacheIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // EQUAL_FILTER
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.equalFilter = new HashMap<String,String>(2*_map0.size);
                String _key1;
                String _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readString();
                  _val2 = iprot.readString();
                  struct.equalFilter.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setEqualFilterIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CommonQuery struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(APPID_FIELD_DESC);
      oprot.writeI32(struct.appid);
      oprot.writeFieldEnd();
      if (struct.isSetPage()) {
        oprot.writeFieldBegin(PAGE_FIELD_DESC);
        oprot.writeI32(struct.page);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPer_page()) {
        oprot.writeFieldBegin(PER_PAGE_FIELD_DESC);
        oprot.writeI32(struct.per_page);
        oprot.writeFieldEnd();
      }
      if (struct.sortby != null) {
        if (struct.isSetSortby()) {
          oprot.writeFieldBegin(SORTBY_FIELD_DESC);
          oprot.writeString(struct.sortby);
          oprot.writeFieldEnd();
        }
      }
      if (struct.order != null) {
        if (struct.isSetOrder()) {
          oprot.writeFieldBegin(ORDER_FIELD_DESC);
          oprot.writeString(struct.order);
          oprot.writeFieldEnd();
        }
      }
      if (struct.fields != null) {
        if (struct.isSetFields()) {
          oprot.writeFieldBegin(FIELDS_FIELD_DESC);
          oprot.writeString(struct.fields);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetNocache()) {
        oprot.writeFieldBegin(NOCACHE_FIELD_DESC);
        oprot.writeBool(struct.nocache);
        oprot.writeFieldEnd();
      }
      if (struct.equalFilter != null) {
        if (struct.isSetEqualFilter()) {
          oprot.writeFieldBegin(EQUAL_FILTER_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.equalFilter.size()));
            for (Map.Entry<String, String> _iter4 : struct.equalFilter.entrySet())
            {
              oprot.writeString(_iter4.getKey());
              oprot.writeString(_iter4.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CommonQueryTupleSchemeFactory implements SchemeFactory {
    public CommonQueryTupleScheme getScheme() {
      return new CommonQueryTupleScheme();
    }
  }

  private static class CommonQueryTupleScheme extends TupleScheme<CommonQuery> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CommonQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetAppid()) {
        optionals.set(0);
      }
      if (struct.isSetPage()) {
        optionals.set(1);
      }
      if (struct.isSetPer_page()) {
        optionals.set(2);
      }
      if (struct.isSetSortby()) {
        optionals.set(3);
      }
      if (struct.isSetOrder()) {
        optionals.set(4);
      }
      if (struct.isSetFields()) {
        optionals.set(5);
      }
      if (struct.isSetNocache()) {
        optionals.set(6);
      }
      if (struct.isSetEqualFilter()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetAppid()) {
        oprot.writeI32(struct.appid);
      }
      if (struct.isSetPage()) {
        oprot.writeI32(struct.page);
      }
      if (struct.isSetPer_page()) {
        oprot.writeI32(struct.per_page);
      }
      if (struct.isSetSortby()) {
        oprot.writeString(struct.sortby);
      }
      if (struct.isSetOrder()) {
        oprot.writeString(struct.order);
      }
      if (struct.isSetFields()) {
        oprot.writeString(struct.fields);
      }
      if (struct.isSetNocache()) {
        oprot.writeBool(struct.nocache);
      }
      if (struct.isSetEqualFilter()) {
        {
          oprot.writeI32(struct.equalFilter.size());
          for (Map.Entry<String, String> _iter5 : struct.equalFilter.entrySet())
          {
            oprot.writeString(_iter5.getKey());
            oprot.writeString(_iter5.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CommonQuery struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.appid = iprot.readI32();
        struct.setAppidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.page = iprot.readI32();
        struct.setPageIsSet(true);
      }
      if (incoming.get(2)) {
        struct.per_page = iprot.readI32();
        struct.setPer_pageIsSet(true);
      }
      if (incoming.get(3)) {
        struct.sortby = iprot.readString();
        struct.setSortbyIsSet(true);
      }
      if (incoming.get(4)) {
        struct.order = iprot.readString();
        struct.setOrderIsSet(true);
      }
      if (incoming.get(5)) {
        struct.fields = iprot.readString();
        struct.setFieldsIsSet(true);
      }
      if (incoming.get(6)) {
        struct.nocache = iprot.readBool();
        struct.setNocacheIsSet(true);
      }
      if (incoming.get(7)) {
        {
          org.apache.thrift.protocol.TMap _map6 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.equalFilter = new HashMap<String,String>(2*_map6.size);
          String _key7;
          String _val8;
          for (int _i9 = 0; _i9 < _map6.size; ++_i9)
          {
            _key7 = iprot.readString();
            _val8 = iprot.readString();
            struct.equalFilter.put(_key7, _val8);
          }
        }
        struct.setEqualFilterIsSet(true);
      }
    }
  }

  private static <S extends IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

