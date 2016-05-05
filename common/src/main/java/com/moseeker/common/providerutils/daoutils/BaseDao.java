package com.moseeker.common.providerutils.daoutils;

import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;


//todo 基础接口里不应该出现jooq相关的类
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

/**
 * 
 * 定义了一些比较通用的底层数据操作的接口 
 * <p>Company: MoSeeker</P>  
 * <p>date: May 5, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 * @param <S> 基于thrift通信的数据结构
 */
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
