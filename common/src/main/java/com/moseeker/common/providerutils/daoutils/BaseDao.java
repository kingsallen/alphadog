package com.moseeker.common.providerutils.daoutils;

import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;


//todo 基础接口里不应该出现jooq相关的类
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

@SuppressWarnings("rawtypes")
public interface BaseDao<S extends TBase> {
	
	S getResource(CommonQuery query) throws TException;

	List<S> getResources(CommonQuery query) throws TException;
	
	int getResourceCount(CommonQuery query) throws TException ;

	int postResources(List<S> structs) throws TException;

	int putResources(List<S> structs) throws TException;

	int delResources(List<S> structs) throws TException;

	int postResource(S struct) throws TException;

	int putResource(S struct) throws TException;

	int delResource(S struct) throws TException;

}
