/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.mq.service;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-08-04")
public class MqService {

  public interface Iface {

    public com.moseeker.thrift.gen.common.struct.Response messageTemplateNotice(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException;

  }

  public interface AsyncIface {

    public void messageTemplateNotice(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct, AsyncMethodCallback resultHandler) throws TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public com.moseeker.thrift.gen.common.struct.Response messageTemplateNotice(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException
    {
      send_messageTemplateNotice(messageTemplateNoticeStruct);
      return recv_messageTemplateNotice();
    }

    public void send_messageTemplateNotice(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException
    {
      messageTemplateNotice_args args = new messageTemplateNotice_args();
      args.setMessageTemplateNoticeStruct(messageTemplateNoticeStruct);
      sendBase("messageTemplateNotice", args);
    }

    public com.moseeker.thrift.gen.common.struct.Response recv_messageTemplateNotice() throws TException
    {
      messageTemplateNotice_result result = new messageTemplateNotice_result();
      receiveBase(result, "messageTemplateNotice");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "messageTemplateNotice failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void messageTemplateNotice(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct, AsyncMethodCallback resultHandler) throws TException {
      checkReady();
      messageTemplateNotice_call method_call = new messageTemplateNotice_call(messageTemplateNoticeStruct, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class messageTemplateNotice_call extends org.apache.thrift.async.TAsyncMethodCall {
      private com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct;
      public messageTemplateNotice_call(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct, AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.messageTemplateNoticeStruct = messageTemplateNoticeStruct;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("messageTemplateNotice", org.apache.thrift.protocol.TMessageType.CALL, 0));
        messageTemplateNotice_args args = new messageTemplateNotice_args();
        args.setMessageTemplateNoticeStruct(messageTemplateNoticeStruct);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public com.moseeker.thrift.gen.common.struct.Response getResult() throws TException {
        if (getState() != State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_messageTemplateNotice();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("messageTemplateNotice", new messageTemplateNotice());
      return processMap;
    }

    public static class messageTemplateNotice<I extends Iface> extends org.apache.thrift.ProcessFunction<I, messageTemplateNotice_args> {
      public messageTemplateNotice() {
        super("messageTemplateNotice");
      }

      public messageTemplateNotice_args getEmptyArgsInstance() {
        return new messageTemplateNotice_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public messageTemplateNotice_result getResult(I iface, messageTemplateNotice_args args) throws TException {
        messageTemplateNotice_result result = new messageTemplateNotice_result();
        result.success = iface.messageTemplateNotice(args.messageTemplateNoticeStruct);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("messageTemplateNotice", new messageTemplateNotice());
      return processMap;
    }

    public static class messageTemplateNotice<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, messageTemplateNotice_args, com.moseeker.thrift.gen.common.struct.Response> {
      public messageTemplateNotice() {
        super("messageTemplateNotice");
      }

      public messageTemplateNotice_args getEmptyArgsInstance() {
        return new messageTemplateNotice_args();
      }

      public AsyncMethodCallback<com.moseeker.thrift.gen.common.struct.Response> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<com.moseeker.thrift.gen.common.struct.Response>() { 
          public void onComplete(com.moseeker.thrift.gen.common.struct.Response o) {
            messageTemplateNotice_result result = new messageTemplateNotice_result();
            result.success = o;
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            messageTemplateNotice_result result = new messageTemplateNotice_result();
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, messageTemplateNotice_args args, AsyncMethodCallback<com.moseeker.thrift.gen.common.struct.Response> resultHandler) throws TException {
        iface.messageTemplateNotice(args.messageTemplateNoticeStruct,resultHandler);
      }
    }

  }

  public static class messageTemplateNotice_args implements org.apache.thrift.TBase<messageTemplateNotice_args, messageTemplateNotice_args._Fields>, java.io.Serializable, Cloneable, Comparable<messageTemplateNotice_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("messageTemplateNotice_args");

    private static final org.apache.thrift.protocol.TField MESSAGE_TEMPLATE_NOTICE_STRUCT_FIELD_DESC = new org.apache.thrift.protocol.TField("messageTemplateNoticeStruct", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new messageTemplateNotice_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new messageTemplateNotice_argsTupleSchemeFactory());
    }

    public com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      MESSAGE_TEMPLATE_NOTICE_STRUCT((short)1, "messageTemplateNoticeStruct");

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
          case 1: // MESSAGE_TEMPLATE_NOTICE_STRUCT
            return MESSAGE_TEMPLATE_NOTICE_STRUCT;
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
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.MESSAGE_TEMPLATE_NOTICE_STRUCT, new org.apache.thrift.meta_data.FieldMetaData("messageTemplateNoticeStruct", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(messageTemplateNotice_args.class, metaDataMap);
    }

    public messageTemplateNotice_args() {
    }

    public messageTemplateNotice_args(
      com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct)
    {
      this();
      this.messageTemplateNoticeStruct = messageTemplateNoticeStruct;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public messageTemplateNotice_args(messageTemplateNotice_args other) {
      if (other.isSetMessageTemplateNoticeStruct()) {
        this.messageTemplateNoticeStruct = new com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct(other.messageTemplateNoticeStruct);
      }
    }

    public messageTemplateNotice_args deepCopy() {
      return new messageTemplateNotice_args(this);
    }

    @Override
    public void clear() {
      this.messageTemplateNoticeStruct = null;
    }

    public com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct getMessageTemplateNoticeStruct() {
      return this.messageTemplateNoticeStruct;
    }

    public messageTemplateNotice_args setMessageTemplateNoticeStruct(com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct) {
      this.messageTemplateNoticeStruct = messageTemplateNoticeStruct;
      return this;
    }

    public void unsetMessageTemplateNoticeStruct() {
      this.messageTemplateNoticeStruct = null;
    }

    /** Returns true if field messageTemplateNoticeStruct is set (has been assigned a value) and false otherwise */
    public boolean isSetMessageTemplateNoticeStruct() {
      return this.messageTemplateNoticeStruct != null;
    }

    public void setMessageTemplateNoticeStructIsSet(boolean value) {
      if (!value) {
        this.messageTemplateNoticeStruct = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case MESSAGE_TEMPLATE_NOTICE_STRUCT:
        if (value == null) {
          unsetMessageTemplateNoticeStruct();
        } else {
          setMessageTemplateNoticeStruct((com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case MESSAGE_TEMPLATE_NOTICE_STRUCT:
        return getMessageTemplateNoticeStruct();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case MESSAGE_TEMPLATE_NOTICE_STRUCT:
        return isSetMessageTemplateNoticeStruct();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof messageTemplateNotice_args)
        return this.equals((messageTemplateNotice_args)that);
      return false;
    }

    public boolean equals(messageTemplateNotice_args that) {
      if (that == null)
        return false;

      boolean this_present_messageTemplateNoticeStruct = true && this.isSetMessageTemplateNoticeStruct();
      boolean that_present_messageTemplateNoticeStruct = true && that.isSetMessageTemplateNoticeStruct();
      if (this_present_messageTemplateNoticeStruct || that_present_messageTemplateNoticeStruct) {
        if (!(this_present_messageTemplateNoticeStruct && that_present_messageTemplateNoticeStruct))
          return false;
        if (!this.messageTemplateNoticeStruct.equals(that.messageTemplateNoticeStruct))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_messageTemplateNoticeStruct = true && (isSetMessageTemplateNoticeStruct());
      list.add(present_messageTemplateNoticeStruct);
      if (present_messageTemplateNoticeStruct)
        list.add(messageTemplateNoticeStruct);

      return list.hashCode();
    }

    @Override
    public int compareTo(messageTemplateNotice_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetMessageTemplateNoticeStruct()).compareTo(other.isSetMessageTemplateNoticeStruct());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetMessageTemplateNoticeStruct()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageTemplateNoticeStruct, other.messageTemplateNoticeStruct);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("messageTemplateNotice_args(");
      boolean first = true;

      sb.append("messageTemplateNoticeStruct:");
      if (this.messageTemplateNoticeStruct == null) {
        sb.append("null");
      } else {
        sb.append(this.messageTemplateNoticeStruct);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check for sub-struct validity
      if (messageTemplateNoticeStruct != null) {
        messageTemplateNoticeStruct.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class messageTemplateNotice_argsStandardSchemeFactory implements SchemeFactory {
      public messageTemplateNotice_argsStandardScheme getScheme() {
        return new messageTemplateNotice_argsStandardScheme();
      }
    }

    private static class messageTemplateNotice_argsStandardScheme extends StandardScheme<messageTemplateNotice_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, messageTemplateNotice_args struct) throws TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // MESSAGE_TEMPLATE_NOTICE_STRUCT
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.messageTemplateNoticeStruct = new com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct();
                struct.messageTemplateNoticeStruct.read(iprot);
                struct.setMessageTemplateNoticeStructIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, messageTemplateNotice_args struct) throws TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.messageTemplateNoticeStruct != null) {
          oprot.writeFieldBegin(MESSAGE_TEMPLATE_NOTICE_STRUCT_FIELD_DESC);
          struct.messageTemplateNoticeStruct.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class messageTemplateNotice_argsTupleSchemeFactory implements SchemeFactory {
      public messageTemplateNotice_argsTupleScheme getScheme() {
        return new messageTemplateNotice_argsTupleScheme();
      }
    }

    private static class messageTemplateNotice_argsTupleScheme extends TupleScheme<messageTemplateNotice_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, messageTemplateNotice_args struct) throws TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetMessageTemplateNoticeStruct()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetMessageTemplateNoticeStruct()) {
          struct.messageTemplateNoticeStruct.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, messageTemplateNotice_args struct) throws TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.messageTemplateNoticeStruct = new com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct();
          struct.messageTemplateNoticeStruct.read(iprot);
          struct.setMessageTemplateNoticeStructIsSet(true);
        }
      }
    }

  }

  public static class messageTemplateNotice_result implements org.apache.thrift.TBase<messageTemplateNotice_result, messageTemplateNotice_result._Fields>, java.io.Serializable, Cloneable, Comparable<messageTemplateNotice_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("messageTemplateNotice_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new messageTemplateNotice_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new messageTemplateNotice_resultTupleSchemeFactory());
    }

    public com.moseeker.thrift.gen.common.struct.Response success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

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
          case 0: // SUCCESS
            return SUCCESS;
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
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.moseeker.thrift.gen.common.struct.Response.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(messageTemplateNotice_result.class, metaDataMap);
    }

    public messageTemplateNotice_result() {
    }

    public messageTemplateNotice_result(
      com.moseeker.thrift.gen.common.struct.Response success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public messageTemplateNotice_result(messageTemplateNotice_result other) {
      if (other.isSetSuccess()) {
        this.success = new com.moseeker.thrift.gen.common.struct.Response(other.success);
      }
    }

    public messageTemplateNotice_result deepCopy() {
      return new messageTemplateNotice_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public com.moseeker.thrift.gen.common.struct.Response getSuccess() {
      return this.success;
    }

    public messageTemplateNotice_result setSuccess(com.moseeker.thrift.gen.common.struct.Response success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((com.moseeker.thrift.gen.common.struct.Response)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof messageTemplateNotice_result)
        return this.equals((messageTemplateNotice_result)that);
      return false;
    }

    public boolean equals(messageTemplateNotice_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      List<Object> list = new ArrayList<Object>();

      boolean present_success = true && (isSetSuccess());
      list.add(present_success);
      if (present_success)
        list.add(success);

      return list.hashCode();
    }

    @Override
    public int compareTo(messageTemplateNotice_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("messageTemplateNotice_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class messageTemplateNotice_resultStandardSchemeFactory implements SchemeFactory {
      public messageTemplateNotice_resultStandardScheme getScheme() {
        return new messageTemplateNotice_resultStandardScheme();
      }
    }

    private static class messageTemplateNotice_resultStandardScheme extends StandardScheme<messageTemplateNotice_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, messageTemplateNotice_result struct) throws TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new com.moseeker.thrift.gen.common.struct.Response();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
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

      public void write(org.apache.thrift.protocol.TProtocol oprot, messageTemplateNotice_result struct) throws TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class messageTemplateNotice_resultTupleSchemeFactory implements SchemeFactory {
      public messageTemplateNotice_resultTupleScheme getScheme() {
        return new messageTemplateNotice_resultTupleScheme();
      }
    }

    private static class messageTemplateNotice_resultTupleScheme extends TupleScheme<messageTemplateNotice_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, messageTemplateNotice_result struct) throws TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, messageTemplateNotice_result struct) throws TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new com.moseeker.thrift.gen.common.struct.Response();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}
