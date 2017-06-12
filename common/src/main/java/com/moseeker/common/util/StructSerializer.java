package com.moseeker.common.util;

import com.google.gson.*;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class StructSerializer {
    private final ByteArrayOutputStream baos_;
    private final TIOStreamTransport transport_;
    private TProtocol protocol_;

    public StructSerializer(TProtocolFactory protocolFactory) {
        this.baos_ = new ByteArrayOutputStream();
        this.transport_ = new TIOStreamTransport(this.baos_);
        this.protocol_ = protocolFactory.getProtocol(this.transport_);
    }

    private byte[] serialize(TBase base) throws TException {
        this.baos_.reset();
        base.write(this.protocol_);
        return this.baos_.toByteArray();
    }

    private String toString(TBase base, String charset) throws TException {
        try {
            return new String(this.serialize(base), charset);
        } catch (UnsupportedEncodingException var4) {
            throw new TException("JVM DOES NOT SUPPORT ENCODING: " + charset);
        }
    }

    public static String toString(Object object, String charset) {
        //将TBase类型转换成JsonString
        StructSerializer serializer = new StructSerializer(new TSimpleJSONProtocol.Factory());
        //处理其它类型
        GsonBuilder builder = new GsonBuilder();
        //继承TBase的对象转换Adapter
        builder.registerTypeHierarchyAdapter(TBase.class, new TBaseJsonSerializer(charset, serializer));
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    public static String toString(Object object) {
        return toString(object, "utf-8");
    }

    static class TBaseJsonSerializer<T extends TBase<?,?>> implements JsonSerializer<T> {
        String charset;
        StructSerializer structSerializer;
        JsonParser jsonParser = new JsonParser();

        public TBaseJsonSerializer(String charset, StructSerializer structSerializer) {
            this.charset = charset;
            this.structSerializer = structSerializer;
        }

        @Override
        public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
            try {
                return jsonParser.parse(structSerializer.toString(src, charset));
            } catch (TException e) {
                e.printStackTrace();
                return new JsonPrimitive("{}");
            }
        }
    }
}