/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.chat.struct;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-03-10")
public class UserChatRoomsVO implements org.apache.thrift.TBase<UserChatRoomsVO, UserChatRoomsVO._Fields>, java.io.Serializable, Cloneable, Comparable<UserChatRoomsVO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserChatRoomsVO");

  private static final org.apache.thrift.protocol.TField PAGE_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("pageNo", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("pageSize", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TOTAL_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("totalPage", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField TOTAL_ROW_FIELD_DESC = new org.apache.thrift.protocol.TField("totalRow", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField ROOMS_FIELD_DESC = new org.apache.thrift.protocol.TField("rooms", org.apache.thrift.protocol.TType.LIST, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserChatRoomsVOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserChatRoomsVOTupleSchemeFactory();

  public int pageNo; // optional
  public int pageSize; // optional
  public int totalPage; // optional
  public int totalRow; // optional
  public java.util.List<UserChatRoomVO> rooms; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE_NO((short)1, "pageNo"),
    PAGE_SIZE((short)2, "pageSize"),
    TOTAL_PAGE((short)3, "totalPage"),
    TOTAL_ROW((short)4, "totalRow"),
    ROOMS((short)5, "rooms");

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
        case 1: // PAGE_NO
          return PAGE_NO;
        case 2: // PAGE_SIZE
          return PAGE_SIZE;
        case 3: // TOTAL_PAGE
          return TOTAL_PAGE;
        case 4: // TOTAL_ROW
          return TOTAL_ROW;
        case 5: // ROOMS
          return ROOMS;
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
  private static final int __PAGENO_ISSET_ID = 0;
  private static final int __PAGESIZE_ISSET_ID = 1;
  private static final int __TOTALPAGE_ISSET_ID = 2;
  private static final int __TOTALROW_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PAGE_NO,_Fields.PAGE_SIZE,_Fields.TOTAL_PAGE,_Fields.TOTAL_ROW,_Fields.ROOMS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE_NO, new org.apache.thrift.meta_data.FieldMetaData("pageNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("pageSize", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_PAGE, new org.apache.thrift.meta_data.FieldMetaData("totalPage", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOTAL_ROW, new org.apache.thrift.meta_data.FieldMetaData("totalRow", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ROOMS, new org.apache.thrift.meta_data.FieldMetaData("rooms", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserChatRoomVO.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserChatRoomsVO.class, metaDataMap);
  }

  public UserChatRoomsVO() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserChatRoomsVO(UserChatRoomsVO other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pageNo = other.pageNo;
    this.pageSize = other.pageSize;
    this.totalPage = other.totalPage;
    this.totalRow = other.totalRow;
    if (other.isSetRooms()) {
      java.util.List<UserChatRoomVO> __this__rooms = new java.util.ArrayList<UserChatRoomVO>(other.rooms.size());
      for (UserChatRoomVO other_element : other.rooms) {
        __this__rooms.add(new UserChatRoomVO(other_element));
      }
      this.rooms = __this__rooms;
    }
  }

  public UserChatRoomsVO deepCopy() {
    return new UserChatRoomsVO(this);
  }

  @Override
  public void clear() {
    setPageNoIsSet(false);
    this.pageNo = 0;
    setPageSizeIsSet(false);
    this.pageSize = 0;
    setTotalPageIsSet(false);
    this.totalPage = 0;
    setTotalRowIsSet(false);
    this.totalRow = 0;
    this.rooms = null;
  }

  public int getPageNo() {
    return this.pageNo;
  }

  public UserChatRoomsVO setPageNo(int pageNo) {
    this.pageNo = pageNo;
    setPageNoIsSet(true);
    return this;
  }

  public void unsetPageNo() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGENO_ISSET_ID);
  }

  /** Returns true if field pageNo is set (has been assigned a value) and false otherwise */
  public boolean isSetPageNo() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGENO_ISSET_ID);
  }

  public void setPageNoIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGENO_ISSET_ID, value);
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public UserChatRoomsVO setPageSize(int pageSize) {
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    return this;
  }

  public void unsetPageSize() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGESIZE_ISSET_ID);
  }

  /** Returns true if field pageSize is set (has been assigned a value) and false otherwise */
  public boolean isSetPageSize() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGESIZE_ISSET_ID);
  }

  public void setPageSizeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGESIZE_ISSET_ID, value);
  }

  public int getTotalPage() {
    return this.totalPage;
  }

  public UserChatRoomsVO setTotalPage(int totalPage) {
    this.totalPage = totalPage;
    setTotalPageIsSet(true);
    return this;
  }

  public void unsetTotalPage() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTALPAGE_ISSET_ID);
  }

  /** Returns true if field totalPage is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalPage() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTALPAGE_ISSET_ID);
  }

  public void setTotalPageIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTALPAGE_ISSET_ID, value);
  }

  public int getTotalRow() {
    return this.totalRow;
  }

  public UserChatRoomsVO setTotalRow(int totalRow) {
    this.totalRow = totalRow;
    setTotalRowIsSet(true);
    return this;
  }

  public void unsetTotalRow() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TOTALROW_ISSET_ID);
  }

  /** Returns true if field totalRow is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalRow() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TOTALROW_ISSET_ID);
  }

  public void setTotalRowIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TOTALROW_ISSET_ID, value);
  }

  public int getRoomsSize() {
    return (this.rooms == null) ? 0 : this.rooms.size();
  }

  public java.util.Iterator<UserChatRoomVO> getRoomsIterator() {
    return (this.rooms == null) ? null : this.rooms.iterator();
  }

  public void addToRooms(UserChatRoomVO elem) {
    if (this.rooms == null) {
      this.rooms = new java.util.ArrayList<UserChatRoomVO>();
    }
    this.rooms.add(elem);
  }

  public java.util.List<UserChatRoomVO> getRooms() {
    return this.rooms;
  }

  public UserChatRoomsVO setRooms(java.util.List<UserChatRoomVO> rooms) {
    this.rooms = rooms;
    return this;
  }

  public void unsetRooms() {
    this.rooms = null;
  }

  /** Returns true if field rooms is set (has been assigned a value) and false otherwise */
  public boolean isSetRooms() {
    return this.rooms != null;
  }

  public void setRoomsIsSet(boolean value) {
    if (!value) {
      this.rooms = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PAGE_NO:
      if (value == null) {
        unsetPageNo();
      } else {
        setPageNo((java.lang.Integer)value);
      }
      break;

    case PAGE_SIZE:
      if (value == null) {
        unsetPageSize();
      } else {
        setPageSize((java.lang.Integer)value);
      }
      break;

    case TOTAL_PAGE:
      if (value == null) {
        unsetTotalPage();
      } else {
        setTotalPage((java.lang.Integer)value);
      }
      break;

    case TOTAL_ROW:
      if (value == null) {
        unsetTotalRow();
      } else {
        setTotalRow((java.lang.Integer)value);
      }
      break;

    case ROOMS:
      if (value == null) {
        unsetRooms();
      } else {
        setRooms((java.util.List<UserChatRoomVO>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE_NO:
      return getPageNo();

    case PAGE_SIZE:
      return getPageSize();

    case TOTAL_PAGE:
      return getTotalPage();

    case TOTAL_ROW:
      return getTotalRow();

    case ROOMS:
      return getRooms();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PAGE_NO:
      return isSetPageNo();
    case PAGE_SIZE:
      return isSetPageSize();
    case TOTAL_PAGE:
      return isSetTotalPage();
    case TOTAL_ROW:
      return isSetTotalRow();
    case ROOMS:
      return isSetRooms();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserChatRoomsVO)
      return this.equals((UserChatRoomsVO)that);
    return false;
  }

  public boolean equals(UserChatRoomsVO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pageNo = true && this.isSetPageNo();
    boolean that_present_pageNo = true && that.isSetPageNo();
    if (this_present_pageNo || that_present_pageNo) {
      if (!(this_present_pageNo && that_present_pageNo))
        return false;
      if (this.pageNo != that.pageNo)
        return false;
    }

    boolean this_present_pageSize = true && this.isSetPageSize();
    boolean that_present_pageSize = true && that.isSetPageSize();
    if (this_present_pageSize || that_present_pageSize) {
      if (!(this_present_pageSize && that_present_pageSize))
        return false;
      if (this.pageSize != that.pageSize)
        return false;
    }

    boolean this_present_totalPage = true && this.isSetTotalPage();
    boolean that_present_totalPage = true && that.isSetTotalPage();
    if (this_present_totalPage || that_present_totalPage) {
      if (!(this_present_totalPage && that_present_totalPage))
        return false;
      if (this.totalPage != that.totalPage)
        return false;
    }

    boolean this_present_totalRow = true && this.isSetTotalRow();
    boolean that_present_totalRow = true && that.isSetTotalRow();
    if (this_present_totalRow || that_present_totalRow) {
      if (!(this_present_totalRow && that_present_totalRow))
        return false;
      if (this.totalRow != that.totalRow)
        return false;
    }

    boolean this_present_rooms = true && this.isSetRooms();
    boolean that_present_rooms = true && that.isSetRooms();
    if (this_present_rooms || that_present_rooms) {
      if (!(this_present_rooms && that_present_rooms))
        return false;
      if (!this.rooms.equals(that.rooms))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPageNo()) ? 131071 : 524287);
    if (isSetPageNo())
      hashCode = hashCode * 8191 + pageNo;

    hashCode = hashCode * 8191 + ((isSetPageSize()) ? 131071 : 524287);
    if (isSetPageSize())
      hashCode = hashCode * 8191 + pageSize;

    hashCode = hashCode * 8191 + ((isSetTotalPage()) ? 131071 : 524287);
    if (isSetTotalPage())
      hashCode = hashCode * 8191 + totalPage;

    hashCode = hashCode * 8191 + ((isSetTotalRow()) ? 131071 : 524287);
    if (isSetTotalRow())
      hashCode = hashCode * 8191 + totalRow;

    hashCode = hashCode * 8191 + ((isSetRooms()) ? 131071 : 524287);
    if (isSetRooms())
      hashCode = hashCode * 8191 + rooms.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(UserChatRoomsVO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPageNo()).compareTo(other.isSetPageNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageNo, other.pageNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPageSize()).compareTo(other.isSetPageSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageSize, other.pageSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTotalPage()).compareTo(other.isSetTotalPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalPage, other.totalPage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTotalRow()).compareTo(other.isSetTotalRow());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalRow()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalRow, other.totalRow);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRooms()).compareTo(other.isSetRooms());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRooms()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rooms, other.rooms);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserChatRoomsVO(");
    boolean first = true;

    if (isSetPageNo()) {
      sb.append("pageNo:");
      sb.append(this.pageNo);
      first = false;
    }
    if (isSetPageSize()) {
      if (!first) sb.append(", ");
      sb.append("pageSize:");
      sb.append(this.pageSize);
      first = false;
    }
    if (isSetTotalPage()) {
      if (!first) sb.append(", ");
      sb.append("totalPage:");
      sb.append(this.totalPage);
      first = false;
    }
    if (isSetTotalRow()) {
      if (!first) sb.append(", ");
      sb.append("totalRow:");
      sb.append(this.totalRow);
      first = false;
    }
    if (isSetRooms()) {
      if (!first) sb.append(", ");
      sb.append("rooms:");
      if (this.rooms == null) {
        sb.append("null");
      } else {
        sb.append(this.rooms);
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

  private static class UserChatRoomsVOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserChatRoomsVOStandardScheme getScheme() {
      return new UserChatRoomsVOStandardScheme();
    }
  }

  private static class UserChatRoomsVOStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserChatRoomsVO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserChatRoomsVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pageNo = iprot.readI32();
              struct.setPageNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAGE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pageSize = iprot.readI32();
              struct.setPageSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOTAL_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalPage = iprot.readI32();
              struct.setTotalPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TOTAL_ROW
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalRow = iprot.readI32();
              struct.setTotalRowIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ROOMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.rooms = new java.util.ArrayList<UserChatRoomVO>(_list8.size);
                UserChatRoomVO _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new UserChatRoomVO();
                  _elem9.read(iprot);
                  struct.rooms.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setRoomsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserChatRoomsVO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPageNo()) {
        oprot.writeFieldBegin(PAGE_NO_FIELD_DESC);
        oprot.writeI32(struct.pageNo);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPageSize()) {
        oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
        oprot.writeI32(struct.pageSize);
        oprot.writeFieldEnd();
      }
      if (struct.isSetTotalPage()) {
        oprot.writeFieldBegin(TOTAL_PAGE_FIELD_DESC);
        oprot.writeI32(struct.totalPage);
        oprot.writeFieldEnd();
      }
      if (struct.isSetTotalRow()) {
        oprot.writeFieldBegin(TOTAL_ROW_FIELD_DESC);
        oprot.writeI32(struct.totalRow);
        oprot.writeFieldEnd();
      }
      if (struct.rooms != null) {
        if (struct.isSetRooms()) {
          oprot.writeFieldBegin(ROOMS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.rooms.size()));
            for (UserChatRoomVO _iter11 : struct.rooms)
            {
              _iter11.write(oprot);
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

  private static class UserChatRoomsVOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserChatRoomsVOTupleScheme getScheme() {
      return new UserChatRoomsVOTupleScheme();
    }
  }

  private static class UserChatRoomsVOTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserChatRoomsVO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserChatRoomsVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPageNo()) {
        optionals.set(0);
      }
      if (struct.isSetPageSize()) {
        optionals.set(1);
      }
      if (struct.isSetTotalPage()) {
        optionals.set(2);
      }
      if (struct.isSetTotalRow()) {
        optionals.set(3);
      }
      if (struct.isSetRooms()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPageNo()) {
        oprot.writeI32(struct.pageNo);
      }
      if (struct.isSetPageSize()) {
        oprot.writeI32(struct.pageSize);
      }
      if (struct.isSetTotalPage()) {
        oprot.writeI32(struct.totalPage);
      }
      if (struct.isSetTotalRow()) {
        oprot.writeI32(struct.totalRow);
      }
      if (struct.isSetRooms()) {
        {
          oprot.writeI32(struct.rooms.size());
          for (UserChatRoomVO _iter12 : struct.rooms)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserChatRoomsVO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.pageNo = iprot.readI32();
        struct.setPageNoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pageSize = iprot.readI32();
        struct.setPageSizeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.totalPage = iprot.readI32();
        struct.setTotalPageIsSet(true);
      }
      if (incoming.get(3)) {
        struct.totalRow = iprot.readI32();
        struct.setTotalRowIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.rooms = new java.util.ArrayList<UserChatRoomVO>(_list13.size);
          UserChatRoomVO _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new UserChatRoomVO();
            _elem14.read(iprot);
            struct.rooms.add(_elem14);
          }
        }
        struct.setRoomsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

